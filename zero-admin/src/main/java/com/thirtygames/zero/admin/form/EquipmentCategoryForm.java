package com.thirtygames.zero.admin.form;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.thirtygames.zero.common.model.equipment.meta.EquipCategory;

import au.com.bytecode.opencsv.CSVReader;

public class EquipmentCategoryForm implements CsvUploadForm<EquipCategory> {
	private List<String[]> dataRows;
	
	private Integer totalRequestCount = 0;
	
	private int codeLevelIndex;
	
	private int categoryCodeIndex;
	
	private int categoryNameIndex;
	
	private int subCategoryCodeIndex;
	
	private int subCategoryNameIndex;
	
	@Override
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
			if (StringUtils.equals(headerName, "코드레벨")) {
				codeLevelIndex = columnIndex;
				columnCount++;
			}
			if (StringUtils.equals(headerName, "카테고리코드")) {
				categoryCodeIndex = columnIndex;
				columnCount++;
			}
			if (StringUtils.equals(headerName, "카테고리명")) {
				categoryNameIndex = columnIndex;
				columnCount++;
			}
			if (StringUtils.equals(headerName, "서브카테고리코드")) {
				subCategoryCodeIndex = columnIndex;
				columnCount++;
			}
			if (StringUtils.equals(headerName, "서브카테고리명")) {
				subCategoryNameIndex = columnIndex;
				columnCount++;
			}
			
			columnIndex++;
		}
		
		if (columnCount != columnIndex) {
			throw new RuntimeException("컬럼 개수가 맞지않습니다.");
		}
	}

	@Override
	public List<EquipCategory> readForm() {
		int rowCount = dataRows.size();
		List<EquipCategory> result = Lists.newArrayListWithCapacity(rowCount);
		for (int rowIndex = 1; rowIndex <= rowCount - 1; rowIndex++) {
			totalRequestCount++;
			String[] row = dataRows.get(rowIndex);
			EquipCategory equipCategory = new EquipCategory();
			
			String codeLevel = row[codeLevelIndex];
			String categoryCode = row[categoryCodeIndex];
			String categoryName = row[categoryNameIndex];
			String subCategoryCode = row[subCategoryCodeIndex];
			String subCategoryName = row[subCategoryNameIndex];
			
			// null 이어서는 안되는 필드 확인
			
			if (StringUtils.isNotEmpty(codeLevel)) {
				equipCategory.setCodeLevel(Integer.valueOf(codeLevel));
			}
			if (StringUtils.isNotEmpty(categoryCode)) {
				equipCategory.setCategoryCode(Integer.valueOf(categoryCode));
			}
			if (StringUtils.isNotEmpty(categoryName)) {
				equipCategory.setCategoryName(categoryName);
			}
			if (StringUtils.isNotEmpty(subCategoryCode)) {
				equipCategory.setSubCategoryCode(Integer.valueOf(subCategoryCode));
			}
			if (StringUtils.isNotEmpty(subCategoryName)) {
				equipCategory.setSubCategoryName(subCategoryName);
			}
			
			result.add(equipCategory);
		}
		return result;
	}
}
