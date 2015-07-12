package com.thirtygames.zero.admin.form;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.collect.Lists;
import com.thirtygames.zero.common.model.equipment.Equipment;
import com.thirtygames.zero.common.model.equipment.meta.EquipCategory;

public class EquipmentForm implements CsvUploadForm<Equipment> {
	private List<String[]> dataRows;
	
	private Integer totalRequestCount = 0;
	
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
			throw new Exception("파일에 데이터가 존재하지 않습니다.");
		}
		
		String[] header = dataRows.get(0);
		
		int columnIndex = 0;
		int columnCount = 0;
		for (String column : header) {
			String headerName = StringUtils.deleteWhitespace(column);
			
			columnIndex++;
		}
		
		if (columnCount != columnIndex) {
			throw new RuntimeException("컬럼 개수가 맞지않습니다.");
		}
	}

	@Override
	public List<Equipment> readForm() {
		int rowCount = dataRows.size();
		List<Equipment> result = Lists.newArrayListWithCapacity(rowCount);
		for (int rowIndex = 1; rowIndex <= rowCount - 1; rowIndex++) {
			totalRequestCount++;
			String[] row = dataRows.get(rowIndex);
		}
		return result;
	}
}
