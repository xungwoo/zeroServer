package com.thirtygames.zero.admin.form;

import java.util.List;

public interface CsvUploadForm<T> {
	public void initialize(String filename) throws Exception;
	
	public List<T> readForm();
}
