package com.thirtygames.zero.common.etc.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LanguageCode {

//	witch (language) {
//    case SystemLanguage.Afrikaans: return "af";
//    case SystemLanguage.Arabic: return "ar";
//    case SystemLanguage.Basque: return "eu";
//    case SystemLanguage.Belarusian: return "be";
//    case SystemLanguage.Bulgarian: return "bg";
//    case SystemLanguage.Catalan: return "ca";
//    case SystemLanguage.Chinese: return "zh";
//    case SystemLanguage.Czech: return "cs";
//    case SystemLanguage.Danish: return "da";
//    case SystemLanguage.Dutch: return "nl";
//    case SystemLanguage.English: return "en";
//    case SystemLanguage.Estonian: return "et";
//    case SystemLanguage.Faroese: return "fo";
//    case SystemLanguage.Finnish: return "fi";
//    case SystemLanguage.French: return "fr";
//    case SystemLanguage.German: return "de";
//    case SystemLanguage.Greek: return "el";
//    case SystemLanguage.Hebrew: return "he";
//    case SystemLanguage.Icelandic: return "is";
//    case SystemLanguage.Indonesian: return "id";
//    case SystemLanguage.Japanese: return "ja";
//    case SystemLanguage.Korean: return "ko";
//    case SystemLanguage.Latvian: return "lv";
//    case SystemLanguage.Lithuanian: return "lt";
//    case SystemLanguage.Norwegian: return "no";
//    case SystemLanguage.Polish: return "pl";
//    case SystemLanguage.Portuguese: return "pt";
//    case SystemLanguage.Romanian: return "ro";
//    case SystemLanguage.Russian: return "ru";
//    case SystemLanguage.SerboCroatian: return "hr";
//    case SystemLanguage.Slovak: return "sk";
//    case SystemLanguage.Slovenian: return "sl";
//    case SystemLanguage.Spanish: return "es";
//    case SystemLanguage.Swedish: return "sv";
//    case SystemLanguage.Thai: return "th";
//    case SystemLanguage.Turkish: return “tr”;
//    case SystemLanguage.Ukrainian: return “uk”;
//    case SystemLanguage.Vietnamese: return “vi”;
//    case SystemLanguage.Hungarian: return “hu”;
//    case SystemLanguage.Unknown: return “en”;
//  }
	
	Korean("ko", 0),
	English("en", 1);
	
	@Getter
	private final String code;
	
	@Getter
	private final int index;
	
	private static final java.util.Map<java.lang.String, LanguageCode> $CODE_LOOKUP = new java.util.HashMap<java.lang.String, LanguageCode>();
	static {
		for (LanguageCode status : LanguageCode.values()) {
			$CODE_LOOKUP.put(status.code, status);
		}
	}
	
	public static LanguageCode findByCode(final String code) {
		if ($CODE_LOOKUP.containsKey(code)) {
			return $CODE_LOOKUP.get(code);
		}
		
		return English;
	}	
}
