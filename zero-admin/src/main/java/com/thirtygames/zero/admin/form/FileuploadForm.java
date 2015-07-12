package com.thirtygames.zero.admin.form;

import org.springframework.web.multipart.MultipartFile;

public class FileuploadForm {
	
	/** The uploaded file. */
	private MultipartFile uploadedFile;

	/**
	 * <pre>
	 * Gets the uploaded file.
	 * </pre>
	 *
	 * @return the uploaded file
	 */
	public MultipartFile getUploadedFile() {
		return uploadedFile;
	}

	/**
	 * <pre>
	 * Sets the uploaded file.
	 * </pre>
	 *
	 * @param uploadedFile the new uploaded file
	 */
	public void setUploadedFile(MultipartFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	@Override
	public String toString() {
		return "FileuploadForm [uploadedFile=" + uploadedFile + "]";
	}
	
}
