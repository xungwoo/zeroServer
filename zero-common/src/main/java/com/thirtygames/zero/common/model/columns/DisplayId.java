package com.thirtygames.zero.common.model.columns;

import java.util.Map;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

@Slf4j
public class DisplayId {
	public int depth; // 트리구조로 조회시 사용

	public String id;

	public String parentId;

	public String expression;

	public String typeClassName;

	public String logicalName;

	public String exceptionTypeName;

	public DisplayId(String id, String expression, String typeClassName,
			DisplayId parents, String exceptionTypeName) {

		this.id = id;

		this.expression = expression;

		this.typeClassName = typeClassName;

		this.parents = parents;

		this.exceptionTypeName = exceptionTypeName;

	}

	public DisplayId(String id, String typeClassName) {

		this.id = id;

		boolean isFirstBinding = false;

		try {

			this.bindingClass = Class.forName("com.nbp.nmp.order.entity."
					+ typeClassName);

			isFirstBinding = true;

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		}

		if (!isFirstBinding) {

			try {

				this.bindingClass = Class
						.forName("com.nbp.nmp.order2.domain.wrapper."
								+ typeClassName);

			} catch (ClassNotFoundException e) {

				e.printStackTrace();

			}

		}

		this.typeClassName = typeClassName;

	}

	/**

	 * 

	 */

	public DisplayId() {

	}

	public String toSimpleString() {

		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SIMPLE_STYLE);

	}

	private Class<?> bindingClass;

	public DisplayId parents;

	public String getPath() {

		if (parents != null && parents.getPath() != null) {

			return parents.getPath() + "." + expression;

		}

		return expression;

	}

	private DisplayId getTheTopParentsId() {

		if (parents != null) {

			return parents.getTheTopParentsId();

		}

		return this;

	}

	public String getExpression() {

		return expression;

	}

	public String getId() {

		return id;

	}

	public void setId(String id) {

		this.id = id;

	}

	public String getParentId() {

		return parentId;

	}

	public void setParentId(String parentId) {

		this.parentId = parentId;

	}

	public String getTypeClassName() {

		return typeClassName;

	}

	public void setTypeClassName(String typeClassName) {

		this.typeClassName = typeClassName;

	}

	public String getLogicalName() {

		return logicalName;

	}

	public void setLogicalName(String logicalName) {

		this.logicalName = logicalName;

	}

	public void setExpression(String expression) {

		this.expression = expression;

	}

	public DisplayId getParents() {

		return parents;

	}

	public void setParents(DisplayId parents) {

		this.parents = parents;

	}

	public String getExceptionTypeName() {

		return exceptionTypeName;

	}

	public void setExceptionTypeName(String exceptionTypeName) {

		this.exceptionTypeName = exceptionTypeName;

	}

	public Object getValue(Object targetObject) {

		SpelExpressionParser parser = new SpelExpressionParser();

		Expression expression = parser.parseExpression(getPath());

		if (expression == null || targetObject == null) {

			return null;

		}

		Class<?> clazz = targetObject.getClass();

		Pattern p = Pattern.compile("\\$\\$EnhancerByCGLIB\\$\\$.*");

		String targetClassName = clazz.getSimpleName().replaceAll(p.pattern(),
				"");

		try {

			if (getTheTopParentsId().getTypeClassName() == null) {

				return null;

			}

			if (targetClassName.equals(getTheTopParentsId().getTypeClassName())) {

				Object returnObject = expression.getValue(targetObject);
				return returnObject;

			}

			return null;

		} catch (Exception e) {
			return null;

		}

	}

	public Object getObjectFor(Map<DisplayId, Object> bindings) {

		Object rootObject = bindings.get(this.parents);

		SpelExpressionParser parser = new SpelExpressionParser();

		Expression expression = parser.parseExpression(getPath());

		if (expression == null || rootObject == null) {

			return null;

		}

		try {
			return expression.getValue(rootObject);

		} catch (Exception e) {
			return null;

		}

	}

	public int getDepth() {

		return depth;

	}

	public void setDepth(int depth) {

		this.depth = depth;

	}

}
