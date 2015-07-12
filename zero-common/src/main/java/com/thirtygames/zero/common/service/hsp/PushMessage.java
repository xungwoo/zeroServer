package com.thirtygames.zero.common.service.hsp;

import java.text.MessageFormat;
import java.util.EnumSet;
import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.thirtygames.zero.common.etc.util.LanguageCode;

@Slf4j
public class PushMessage {

	@RequiredArgsConstructor
	public enum PushType {
		BossClear;
	}
	
	@RequiredArgsConstructor
	public enum FormatType {
		NoArg, UseArg;
	}

	@RequiredArgsConstructor
	public enum MsgType {

		BossClear_Korean(PushType.BossClear, LanguageCode.Korean, FormatType.UseArg, "{0}님의 도움으로 {1} 클리어 완료! 보상을 확인하세요."), 
		BossClear_English(PushType.BossClear, LanguageCode.English, FormatType.UseArg, "{0}'s help cleared {1} stage! Check your reward.");

		@Getter
		private final PushType type;

		@Getter
		private final LanguageCode languageCode;
		
		@Getter
		private final FormatType formatType;
		
		@Getter
		private final String text;
		
		public String getTextByArgs(Object[] args) {
			String result = this.text;
			if (this.formatType.equals(FormatType.UseArg)) {
				result = this.getFormattingMessage(result, args);
			}
			return result;
		}

		public static Set<MsgType> koreans = EnumSet.range(BossClear_Korean, BossClear_Korean);
		public static Set<MsgType> englishes = EnumSet.range(BossClear_English, BossClear_English);

		private static final java.util.Map<PushType, MsgType> $CODE_LOOKUP_KOREAN = new java.util.HashMap<PushType, MsgType>();
		static {
			for (MsgType status : MsgType.koreans) {
				$CODE_LOOKUP_KOREAN.put(status.type, status);
			}
		}

		private static final java.util.Map<PushType, MsgType> $CODE_LOOKUP_ENGLISH = new java.util.HashMap<PushType, MsgType>();
		static {
			for (MsgType status : MsgType.englishes) {
				$CODE_LOOKUP_ENGLISH.put(status.type, status);
			}
		}

		public static MsgType findByCode(final PushType pushType, LanguageCode languageCode) {
			switch (languageCode) {
			case Korean:
				if ($CODE_LOOKUP_KOREAN.containsKey(pushType)) {
					return $CODE_LOOKUP_KOREAN.get(pushType);
				}
				throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'MsgType' has no value for 'code = %s'", pushType));
			case English:
				if ($CODE_LOOKUP_ENGLISH.containsKey(pushType)) {
					return $CODE_LOOKUP_ENGLISH.get(pushType);
				}
				throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'MsgType' has no value for 'code = %s'", pushType));
			default:
				throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'MsgType' has no value for 'code = %s'", pushType));
			}
		}
		
		private String getFormattingMessage(String str, Object[] args) {
			MessageFormat form = new MessageFormat(str);
			return form.format(args);
		}

	}
	
	
	@RequiredArgsConstructor
	public enum Errors {
		PUSH_SUCCESS(0),
		PUSH_ERROR_SYSTEM(1048577),
		PUSH_ERROR_LOAD_FAIL(1048578),
		PUSH_ERROR_DATABASE(1048581),
		PUSH_ERROR_SERVICEID(1048582),
		PUSH_ERROR_INVALID_VALUE(1048583),
		PUSH_ERROR_SENDINFO_NOT_EXIST(1048584);
		
		@Getter
		private final int code;
		
		
		@Getter
		private static final java.util.Map<java.lang.Integer, Errors> $CODE_LOOKUP = new java.util.HashMap<java.lang.Integer, Errors>();
		static {
			for (Errors status : Errors.values()) {
				$CODE_LOOKUP.put(status.code, status);
			}
		}
		
		public static Errors findByCode(final int code) {
			if ($CODE_LOOKUP.containsKey(code)) {
				return $CODE_LOOKUP.get(code);
			}
			throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'Errors' has no value for 'code = %s'", code));
		}			
	}
	
}
