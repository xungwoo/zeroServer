package com.thirtygames.zero.common.util;

import java.util.Map;

import org.junit.Test;

import com.thirtygames.zero.common.etc.util.GenericUtil;

public class GenericUtilsTest {

	@Test
    public void testGetClassOfGenericTypeIn() throws Exception {
        Class<String> stringClass = (Class<String>) GenericUtil.getClassOfGenericTypeIn(SampleClass.class, 0);
        //assertEqual(stringClass.toString(), "class java.lang.String");

        Class<Map> mapClass = (Class<Map>) GenericUtil.getClassOfGenericTypeIn(SampleClass.class, 1);
        //assertThat(mapClass.toString(), is("interface java.util.Map"));
    }

    class SampleGenericClass<S, M>{}
    @SuppressWarnings("rawtypes")
	class SampleClass extends SampleGenericClass<String, Map> {}
}
