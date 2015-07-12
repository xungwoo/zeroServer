package com.thirtygames.zero.admin.form;

import com.thirtygames.zero.admin.form.converter.BooleanConverter;
import com.thirtygames.zero.admin.form.converter.IntegerConverter;
import com.thirtygames.zero.admin.form.converter.LongConverter;
import com.thirtygames.zero.admin.form.converter.TypeConverter;

public enum PostEnum {
	POST_ADMIN_KEY("postAdminKey", true, new LongConverter()),
	POST_TYPE("postType", false, new IntegerConverter()),
	ACCOUNT_ID("accountId", false, null),
	LANG("lang", true, null),
	TEXT("text", true, null),
	FROM_ACCOUNT_ID("fromAccountId", true, null),
	FROM_NICK_NAME("fromNickName", true, null),
	FROM_PROFILE_URL("fromProfileUrl", true, null),
	FROM_LEAGUE("fromLeague", true, new IntegerConverter()),
	REWARD_TYPE("rewardType", true, new IntegerConverter()),
	REWARD("reward", true, new LongConverter()),
	REWARD_DONE("rewardDone", true, new BooleanConverter()),
	START_YMDT("startYmdt", true, null),
	EXPIRE_YMDT("expireYmdt", true, null);
	
	String name;
	
	boolean isNullable;
	
	TypeConverter<String, ?> converter;
	
	PostEnum(String name, boolean isNullable, TypeConverter<String, ?> converter) {
		this.name = name;
		this.isNullable = isNullable;
		this.converter = converter;
	}

	public String getName() {
		return name;
	}


	public boolean isNullable() {
		return isNullable;
	}

	public TypeConverter<String, ?> getConverter() {
		return converter;
	}
	
}
