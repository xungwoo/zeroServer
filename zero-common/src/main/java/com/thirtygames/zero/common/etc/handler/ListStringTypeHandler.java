package com.thirtygames.zero.common.etc.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

import com.google.common.collect.Lists;

@SuppressWarnings("rawtypes")
public class ListStringTypeHandler extends BaseTypeHandler implements TypeHandler {
	/**
	 * @param ps
	 * @param index
	 * @param parameter
	 * @param jdbcType
	 * @throws SQLException
	 * @see org.apache.ibatis.type.BaseTypeHandler#setParameter(java.sql.PreparedStatement, int, java.lang.Object, org.apache.ibatis.type.JdbcType)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setParameter(PreparedStatement ps, int index, Object parameter, JdbcType jdbcType) throws SQLException {
		super.setParameter(ps, index, parameter, jdbcType);
	}
	
	/**
	 * @param ps
	 * @param i
	 * @param parameter
	 * @param jdbcType
	 * @throws SQLException
	 * @see org.apache.ibatis.type.BaseTypeHandler#setNonNullParameter(java.sql.PreparedStatement, int, java.lang.Object, org.apache.ibatis.type.JdbcType)
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, (String)makeListToString(parameter));
	}

	/**
	 * @param rs
	 * @param columnName
	 * @return
	 * @throws SQLException
	 * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.ResultSet, java.lang.String)
	 */
	@Override
	public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return getResult(rs.getString(columnName));
	}

	/**
	 * @param cs
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.CallableStatement, int)
	 */
	@Override
	public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return getResult(cs.getString(columnIndex));
	}
	
	/**
	 * @param rs
	 * @param columnName
	 * @return
	 * @throws SQLException
	 * @see org.apache.ibatis.type.BaseTypeHandler#getResult(java.sql.ResultSet, java.lang.String)
	 */
	@Override
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		return getResult(rs.getString(columnName));
	}
	
	/**
	 * @param cs
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 * @see org.apache.ibatis.type.BaseTypeHandler#getResult(java.sql.CallableStatement, int)
	 */
	@Override
	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return getResult(cs.getString(columnIndex));
	}

	/**
	 * Gets the result.
	 *
	 * @param identifier the identifier
	 * @return the result
	 */
	public Object getResult(String identifier) {
		try {
			if (identifier != null) {
				return makeStringToList(identifier);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new TypeException("Can't make list", e);
		}
	}
	
	/**
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String makeListToString(Object parameter) {
		if (parameter == null) {
			return "";
		}
		return StringUtils.join((List<String>)parameter, ",");
	}
	
	/**
	 * @param identifier
	 * @return
	 */
	private Object makeStringToList(String identifier) {
		return Lists.newArrayList(StringUtils.split(identifier, ","));
	}

	@Override
	public Object getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		return getResult(rs.getString(columnIndex));
	}
}
