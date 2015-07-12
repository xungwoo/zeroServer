package com.thirtygames.zero.common.model.admintool;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserAccount extends GridFilter implements Serializable {
	private static final long serialVersionUID = 4198430771880920709L;
	
	String memberNo; //HSP
	String accountId;
	String nickName;
	String channelId;
	Integer channelType;
	Boolean channelCertification;
	
	String password; // channeler 인증시 불필요
	Integer profileType;
	String profileUrl;

	String authToken;
	Date authTokenValidYmdt;
	String language;
	String country;
	Integer localTimeZone;
	
	Integer title;
	Integer extraInventory;
	Long tutorial;
	String maxClearStage;
	Integer maxClearMode;	
	
	String lastSyncYmdt;
	String genYmdt;
	String modYmdt;
	
	Boolean isBlock;
	Boolean sendLog;
	Boolean withdraw;
	
	String memo;
	
	@RequiredArgsConstructor
	public enum Title {
		Title0(0, "탐험가"),
		Title1(1, "죽음의 정복자"),
		Title2(2, "자연의 숭배자"),
		Title3(3, "바다의 항해사"),
		Title4(4, "무한의 지배자"),
		Title5(5, "백전용사"),
		Title6(6, "전장의 지배자"),
		Title7(7, "위대한 영웅"),
		Title8(8, "무한의 영웅"),
		Title9(9, "기사단장"),
		Title10(10, "끈기있는 탐험가"),
		Title11(11, "지름신 강림"),
		Title12(12, "도살자"),
		Title13(13, "무기의 달인"),
		Title14(14, "행운의 모험가"),
		Title15(15, "보물의 주인"),
		Title16(16, "노련한 대장장이"),
		Title17(17, "위대한 발명가"),
		Title18(18, "빛나는 별"),
		Title19(19, "연금술사");
		
		@Getter
		private final int code;
		
		@Getter
		private final String text;
		
		@Getter
		private static final java.util.Map<Integer, String> $CODE_LOOKUP = new java.util.HashMap<Integer, String>();
		static {
			for (Title status : Title.values()) {
				$CODE_LOOKUP.put(status.code, status.text);
			}
		}
		
		public static String findByCode(final int code) {
			if ($CODE_LOOKUP.containsKey(code)) {
				return $CODE_LOOKUP.get(code);
			}
			
			return Title0.text;
		}	
	}

}
