package com.thirtygames.zero.common.etc.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.FilterProvider;

/**
 * The Class JacksonUtil.
 */
public class JacksonUtil {
	/** The mapper. */
	private final ObjectMapper mapper;

	public static final String[] ALLOWED_DATE_PATTERNS = new String[] {"yyyy.MM.dd HH:mm", "yyyy.MM.dd"};

	/**
	 * Instantiates a new jackson util.
	 */
	private JacksonUtil() {
		mapper = new ObjectMapper();
		mapper.getSerializationConfig().with(MapperFeature.AUTO_DETECT_GETTERS);
		mapper.getSerializationConfig().with(MapperFeature.AUTO_DETECT_IS_GETTERS);
		mapper.getSerializationConfig().without(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		//mapper.getSerializationConfig().withSerializationInclusion(Inclusion.NON_NULL); //WRITE_NULL_PROPERTIES 대체
		mapper.getDeserializationConfig().without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		//CustomSerializerFactory serializerFactory = new CustomSerializerFactory();
		//mapper.setSerializerFactory(serializerFactory);
	}

	/**
	 * Gets the single instance of JacksonUtil.
	 *
	 * @return single instance of JacksonUtil
	 */
	public static JacksonUtil getInstance() {
		return new JacksonUtil();
	}

	/**
	 * Gets the mapper.
	 *
	 * @return the mapper
	 */
	public static ObjectMapper getMapper() {
		return getInstance().mapper;
	}

	/**
	 * To json.
	 *
	 * @param object the object
	 * @return the string
	 */
	public static String toJson(Object object) {
		try {
			return getMapper().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * To json.
	 *
	 * @param object the object
	 * @return the string
	 */
	public static String toJson(FilterProvider filterProvider, Object object) {
		try {
			return createInstance().mapper.writer(filterProvider).writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * To object.
	 *
	 * @param <T> the generic type
	 * @param jsonStr the json str
	 * @param cls the cls
	 * @return the t
	 */
	public static <T> T toObject(String jsonStr, Class<T> cls) {
		try {
			return getMapper().readValue(jsonStr, cls);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * To object.
	 *
	 * @param <T> the generic type
	 * @param jsonStr the json str
	 * @param typeReference the type reference
	 * @return the t
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toObject(String jsonStr, TypeReference<?> typeReference) {
		try {
			return (T)getMapper().readValue(jsonStr, typeReference);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the single instance of JacksonUtil.
	 *
	 * @return single instance of JacksonUtil
	 */
	public static JacksonUtil createInstance() {
		return new JacksonUtil();
	}

	public static class DateSerializer extends JsonSerializer<Date> {
		@Override
		public void serialize(Date value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");
			jgen.writeString(formatter.format(value));
		}
	}

	public static class DateDeserializer extends JsonDeserializer<Date> {
		@Override
		public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			String date = jp.getText();
			try {
				return DateUtils.parseDate(date, ALLOWED_DATE_PATTERNS);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}

	}
}
