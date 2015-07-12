package com.thirtygames.zero.common.etc.document;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class DefaultColumnDescriptor implements ColumnDescriptor {

	/**

	 * 

	 */

	private static final int DEFAULT_COLUMN_WIDTH = 10;

	private String headerName;

	private Expression expression;

	private CellType cellType;

	private CellHandler cellHandler;

	/**
	 * 
	 * Set the width (in units of 1/256th of a character width)
	 * 
	 * <p>
	 * 
	 * The maximum column width for an individual cell is 255 characters.
	 * 
	 * This value represents the number of characters that can be displayed
	 * 
	 * in a cell that is formatted with the standard font.
	 * 
	 * </p>
	 */

	private int width = DEFAULT_COLUMN_WIDTH;

	public DefaultColumnDescriptor(String headerName, Expression expression) {

		this.headerName = headerName;

		this.expression = expression;

	}

	public DefaultColumnDescriptor(String headerName, Expression expression,
			CellType cellType) {

		this(headerName, expression);

		this.cellType = cellType;

	}

	public DefaultColumnDescriptor(String headerName, Expression expression,
			CellType cellType, CellHandler cellHandler) {

		this(headerName, expression, cellType);

		this.cellHandler = cellHandler;

	}

	public DefaultColumnDescriptor(String headerName, Expression expression,
			CellType cellType, CellHandler cellHandler, int width) {

		this(headerName, expression, cellType, cellHandler);

		this.width = width;

	}

	public void setValue(EvaluationContext context, Object value) {

		expression.setValue(context, value);

	}

	public Object getValue(EvaluationContext context) {

		Object value = null;

		try {

			value = expression.getValue(context);

		} catch (SpelEvaluationException see) {

		}

		return value;

	}

	@Override
	public Object getValue(Object root) {

		return getValue(new StandardEvaluationContext(root));

	}

	public Class<?> getValueType(EvaluationContext context) {

		return expression.getValueType(context);

	}

	public Class<?> getValueType(Object root) {

		return getValueType(new StandardEvaluationContext(root));

	}

	@Override
	public String getHeaderName() {

		return headerName;

	}

	@Override
	public CellType getCellType() {

		return cellType;

	}

	@Override
	public CellHandler getCellHandler() {

		return cellHandler;

	}

	@Override
	public int getWidth() {

		return width;

	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);

	}

}
