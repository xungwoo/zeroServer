package com.thirtygames.zero.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;

public class FriendServiceImplTest {
	
	@Ignore
	@Test
	public void containPerfTest() {
		
		List<String> strList = new ArrayList<String>();
		Set<String> strSet = new HashSet<String>();
		Map<String, Integer> strMap = new HashMap<String, Integer>();
		
		String targetId = "";
		for (int i=0; i<=1000000; i++) {
			String id = UUID.randomUUID().toString();
			strList.add(id);
			strSet.add(id);
			strMap.put(id, 0);
			
			if (i == 1000000) {
				targetId = id;
			}
		}
		
		long start1 = System.currentTimeMillis();
		strList.contains(targetId);
		long end1 = System.currentTimeMillis();
		System.out.println( "strList : " + ( end1 - start1 ) / 10000.0);		
		
		long start2 = System.currentTimeMillis();
		strSet.contains(targetId);
		long end2 = System.currentTimeMillis();
		System.out.println( "strSet : " + ( end2 - start2 ) / 10000.0);
		
		long start3 = System.currentTimeMillis();
		strMap.containsKey(targetId);
		long end3 = System.currentTimeMillis();
		System.out.println( "strMap : " + ( end3 - start3 ) / 10000.0);
		
	}

}
