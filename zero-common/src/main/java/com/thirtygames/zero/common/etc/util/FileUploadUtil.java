package com.thirtygames.zero.common.etc.util;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtil.class);
	
	public String getUploadFileName(MultipartFile uploadedFile, String filePath, String fileNamePrefix) throws IOException {
		LOGGER.warn("*************************  Uploaded File Info *************************************");
		LOGGER.warn("Original Name : " + uploadedFile.getOriginalFilename());
		LOGGER.warn("Name : " + uploadedFile.getName());
		LOGGER.warn("ContentType : " + uploadedFile.getContentType());
		LOGGER.warn("Size : " + uploadedFile.getSize());
		LOGGER.warn("*****************************************************************************");
		// TODO: Upload Target 디렉토리 설정. transferTo 메서드를 사용하여 대상 디렉토리에 파일을 복사한다.
		// TODO: File Rename 정책 및 디렉토리 정책에 대한 작업이 필요함.
		File targetDir = new File(filePath);
		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}
			
		File targetFile = File.createTempFile(fileNamePrefix, getExtention(uploadedFile.getOriginalFilename()), targetDir);
		targetFile.delete();
		
		uploadedFile.transferTo(targetFile);
		return targetFile.getName();
	}
	
	public File getUploadFile(MultipartFile uploadedFile, String filePath, String fileNamePrefix) throws IOException {
		LOGGER.warn("*************************  Uploaded File Info *************************************");
		LOGGER.warn("Original Name : " + uploadedFile.getOriginalFilename());
		LOGGER.warn("Name : " + uploadedFile.getName());
		LOGGER.warn("ContentType : " + uploadedFile.getContentType());
		LOGGER.warn("Size : " + uploadedFile.getSize());
		LOGGER.warn("*****************************************************************************");
		// TODO: Upload Target 디렉토리 설정. transferTo 메서드를 사용하여 대상 디렉토리에 파일을 복사한다.
		// TODO: File Rename 정책 및 디렉토리 정책에 대한 작업이 필요함.
		File targetDir = new File(filePath);
		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}
			
		File targetFile = File.createTempFile(fileNamePrefix, getExtention(uploadedFile.getOriginalFilename()), targetDir);
		targetFile.delete();
		
		uploadedFile.transferTo(targetFile);
		return targetFile;
	}
	
	/**
	 * <pre>
	 * Gets the extention.
	 * </pre>
	 *
	 * @param filename the filename
	 * @return the extention
	 */
	private String getExtention(String filename) {
		return filename.indexOf('.') >= 0 ? filename.substring(filename.lastIndexOf(".")) : "";
	}
}
