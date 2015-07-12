package com.thirtygames.zero.common.etc.system;

import java.net.InetAddress;
import java.net.UnknownHostException;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SystemInfo {
	private static SystemInfo instance = new SystemInfo();
	private String hostname = "";
	private String address = "";	
	private RunMode runMode;
	
	private static final String TEST_SERVER_ADDRESS = "106.245.225.162";
	//private static final String DEV_SERVER_ADDRESS = "106.245.225.162";
	
	private SystemInfo() {
		try {
			hostname = InetAddress.getLocalHost().getHostName();
			address = InetAddress.getLocalHost().getHostAddress();
			
			if (TEST_SERVER_ADDRESS.equals(address)) {
				runMode = RunMode.TEST;
			}
			
		} catch (UnknownHostException e) {
		}

		runMode = RunMode.findByCode(System.getProperty("mode"));
		log.debug("ServerInfo :::::::::::::" + hostname);
		log.debug("ServerInfo :::::::::::::" + address);
	}

	public static SystemInfo getInstance() {
		return instance;
	}
	
	
	@RequiredArgsConstructor
	public enum RunMode {
		REAL("real"), ALPHA("alpha"), TEST("test"), DEV("dev"), LOCAL("local");
		
		@Getter
		private final String code;

		@Getter
		private static final java.util.Map<String, RunMode> $CODE_LOOKUP = new java.util.HashMap<String, RunMode>();
		static {
			for (RunMode status : RunMode.values()) {
				$CODE_LOOKUP.put(status.code, status);
			}
		}

		public static RunMode findByCode(final String code) {
			if ($CODE_LOOKUP.containsKey(code)) {
				return $CODE_LOOKUP.get(code);
			}
			throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'RunMode' has no value for 'code = %s'", code));
		}		
	}
	
	
	
	
	

}
