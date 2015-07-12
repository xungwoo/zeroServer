package com.thirtygames.zero.common.etc.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;

import com.thirtygames.zero.common.etc.datasource.ContextHolder;

@Slf4j
@Intercepts({ @Signature(type = StatementHandler.class, method = "update", args = { Statement.class }), @Signature(type = StatementHandler.class, method = "query", args = { Statement.class, ResultHandler.class }) })
public class MybatisQueryLogInterceptor implements Interceptor {

	private static final String MYBATIS_QUERY_SPECIAL_CHARACTER = "\\?";

	@Override
	public Object intercept(Invocation invocation) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		StatementHandler handler = (StatementHandler) invocation.getTarget();
		log.debug("######### DataSource::" + ContextHolder.getDataSource());
		BoundSql boundSql = handler.getBoundSql();

		// query include "?"
		String sql = boundSql.getSql();
		Object param = handler.getParameterHandler().getParameterObject();

		if (param == null) {
			sql = sql.replaceFirst(MYBATIS_QUERY_SPECIAL_CHARACTER, "''");

		} else {
			if (param instanceof Integer || param instanceof Long || param instanceof Float || param instanceof Double || param instanceof String || param instanceof Boolean || param instanceof Date) {
				sql = sql.replaceFirst(MYBATIS_QUERY_SPECIAL_CHARACTER, makeSqlParamString(param));

			} else if (param instanceof Map) {
				List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
				for (ParameterMapping mapping : paramMapping) {
					String propValue = mapping.getProperty();
					//TODO foreach 문 처리방식 추가필요!
					if (propValue.indexOf("__frch") < 0) {
						Object value;
						if (propValue.contains("model")) {
							Object model = ((Map<?, ?>) param).get("model");
							String fieldName = propValue.replaceFirst("model.", "");
							Field field = model.getClass().getDeclaredField(fieldName);
							field.setAccessible(true);
							value = field.get(model);
						} else {
							value = ((Map<?, ?>) param).get(propValue);
						}
						sql = getSqlString(boundSql, sql, value);
					}
				}
			} else {
				Class<? extends Object> paramClass = param.getClass();
				List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
				for (ParameterMapping mapping : paramMapping) {
					String propValue = mapping.getProperty();
					if (propValue.indexOf("__frch") < 0) {
						Field field = paramClass.getDeclaredField(propValue);
						field.setAccessible(true);
						// Class<?> javaType = mapping.getJavaType();
						sql = getSqlString(boundSql, sql, field.get(param));
					}
				}
			}

		}

		log.debug("## SQL ===============================================================\n" + sql);
		log.debug("=====================================================================");

		return invocation.proceed(); // 쿼리 실행
	}
	
	private String getSqlString(BoundSql boundSql, String sql, Object param) throws NoSuchFieldException, IllegalAccessException {
		if (param == null) {
			sql = sql.replaceFirst(MYBATIS_QUERY_SPECIAL_CHARACTER, "''");
		} else if (param instanceof Integer || param instanceof Long || param instanceof Float || param instanceof Double || param instanceof String || param instanceof Boolean || param instanceof Date ) {
			sql = sql.replaceFirst(MYBATIS_QUERY_SPECIAL_CHARACTER, makeSqlParamString(param));
		} else if (param instanceof Map) {
			List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
			for (ParameterMapping mapping : paramMapping) {
				String propValue = mapping.getProperty();
				Object value = ((Map<?, ?>) param).get(propValue);
				sql = sql.replaceFirst(MYBATIS_QUERY_SPECIAL_CHARACTER, makeSqlParamString(value));
			}
		} else {
			Class<? extends Object> paramClass = param.getClass();
			List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
			for (ParameterMapping mapping : paramMapping) {
				String propValue = mapping.getProperty();
				Field field = paramClass.getDeclaredField(propValue);
				field.setAccessible(true);
				// Class<?> javaType = mapping.getJavaType();
				sql = sql.replaceFirst(MYBATIS_QUERY_SPECIAL_CHARACTER, makeSqlParamString(field.get(param)));
			}
		}
		return sql;	
	}

	private String makeSqlParamString(Object param) {
		String result = "";
		if (param instanceof String) {
			result = "'" + param + "'";
		} else {
			result = param.toString();
		}
		return result;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

}

