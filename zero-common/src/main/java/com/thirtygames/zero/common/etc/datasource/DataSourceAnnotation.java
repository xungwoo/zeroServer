package com.thirtygames.zero.common.etc.datasource;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DataSourceAnnotation {
	DataSourceType value() default DataSourceType.ZERO_INDEX;
}
