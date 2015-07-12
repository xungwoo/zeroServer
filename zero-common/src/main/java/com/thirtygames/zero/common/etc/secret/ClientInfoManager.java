package com.thirtygames.zero.common.etc.secret;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;


@Slf4j
@Component
@Scope(value = "singleton")
public class ClientInfoManager {
	private static ClientInfoManager instance = new ClientInfoManager();

	public static ClientInfoManager getInstance() {
		return instance;
	}
	
	private static final String SECRET_KEY = "bDk7TqSnb/teON3ASoGfymw5O06kp2/7";
	
	private Map<String, String> timeStampMap = new HashMap<String, String>();
	
	public List<String> validate(String accountId, String timeStampHeader, String clientVersion, String clientPlatform, String accessToken) throws Exception {

		boolean isDebug = false;
		String systemRunMode = System.getProperty("mode");
		if (("alpha".equals(systemRunMode) || "local".equals(systemRunMode) || "dev".equals(systemRunMode) || "test".equals(systemRunMode)) && ("[Fantastic 30Games!]".equals(accessToken))) {
			isDebug = true;
		}
		
		if (!isDebug) {
			timeStampHeader = this.TripleDesDecrypt(timeStampHeader);
		}
		
		List<String> tsList = this.getTimeStampList(timeStampHeader);
		if (isDebug && "FANTASTIC30GAMES".equals(tsList.get(2))) {
			return tsList;
		}
		
		String requestTimeStamp = tsList.get(0);
		if (this.isDuplicateRequest(accountId, requestTimeStamp)) {
			throw new RestException(Errors.DuplicateRequest);
		};
		this.timeStampMap.put(accountId, requestTimeStamp);
		
		return tsList;
	}
	
	private List<String> getTimeStampList(String timeStampHeader) {
		List<String> timeStampList = Splitter.on(':').trimResults().splitToList(timeStampHeader);
		log.debug("timeStampList::" + timeStampList);
		if (timeStampList == null || timeStampList.size() < 3) {
			throw new RestException(Errors.InvalidTimeStamp);
		}
		return timeStampList;
	}
	
	public boolean isDuplicateRequest(String accountId, String timeStamp) {
		return timeStamp.equals(this.timeStampMap.get(accountId)) ? true : false;
	}
	
	
	public String TripleDesDecrypt(String encryptedText) throws Exception {
	    byte[] message = Base64.decodeBase64(encryptedText.getBytes("utf-8"));
	    byte[] keyBytes = Base64.decodeBase64(SECRET_KEY.getBytes("utf-8"));
	    
	    //System.out.println((int)keyBytes.length);
	    SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
	    SecretKey key = factory.generateSecret(new DESedeKeySpec(keyBytes));
		
		Cipher decipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		decipher.init(Cipher.DECRYPT_MODE, key);
		
		byte[] plainText = decipher.doFinal(message);
		return new String(plainText, "UTF-8");
	}		
}


