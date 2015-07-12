package com.thirtygames.zero.admin.form;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thirtygames.zero.common.model.admintool.AdminPost;

public class PostUserForm implements CsvUploadForm<AdminPost> {
	private List<String[]> dataRows;
	
	private Integer totalRequestCount = 0;
	
	private Map<PostEnum, Integer> csvHeaderMapper = Maps.newHashMap();
	
	public void initialize(String filename) throws Exception {
		CSVReader reader = null;
		
		try {
			reader = new CSVReader(new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename)))));
			dataRows = reader.readAll();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			throw new RuntimeException("csv 파일을 읽을 수 없습니다.");
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		this.initializeColumns();
	}

	private void initializeColumns() throws Exception {
		// 컬럼이 모두 들어있는지 확인
		if (dataRows == null || dataRows.isEmpty() || dataRows.size() < 2) {
			throw new RuntimeException("파일에 데이터가 존재하지 않습니다.");
		}
		
		String[] header = dataRows.get(0);
		
		int columnIndex = 0;
		for (String column : header) {
			String headerName = StringUtils.deleteWhitespace(column);
			for (PostEnum postEnum : PostEnum.values()) {
				if (StringUtils.equalsIgnoreCase(headerName, postEnum.getName())) {
					csvHeaderMapper.put(postEnum, columnIndex);
					break;
				}
			}
			columnIndex++;
		}
		
		if (csvHeaderMapper.size() != columnIndex) {
			throw new RuntimeException("컬럼 개수가 맞지않습니다.");
		}
	}

	@Override
	public List<AdminPost> readForm() {
		int rowCount = dataRows.size();
		List<AdminPost> result = Lists.newArrayListWithCapacity(rowCount);
		for (int rowIndex = 1; rowIndex <= rowCount - 1; rowIndex++) {
			totalRequestCount++;
			String[] row = dataRows.get(rowIndex);
			AdminPost post = new AdminPost();
			
			for (PostEnum postEnum : csvHeaderMapper.keySet()) {
				try {
					String columnItem = row[csvHeaderMapper.get(postEnum)];
				
					Field field = post.getClass().getDeclaredField(postEnum.getName());
					field.setAccessible(true);
					
					if (postEnum.getConverter() == null) {
						field.set(post, columnItem);
					} else {
						field.set(post, postEnum.getConverter().convert(columnItem));
					}
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
			
			result.add(post);
		}
		return result;
	}
}
