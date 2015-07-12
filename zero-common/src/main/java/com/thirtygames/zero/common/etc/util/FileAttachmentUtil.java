package com.thirtygames.zero.common.etc.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;

public class FileAttachmentUtil {
	public static String getDownloadFileName(String userAgent, String filename,
		String charSet) {
		try {
			if (userAgent.contains("MSIE") || userAgent.contains("Trident/[0-9]\\.[0-9]")) {
				return FileAttachmentUtil.toRawEncoding(filename, charSet);
			} else if (userAgent.contains("Opera")) {
				return FileAttachmentUtil.toRFC2231(filename, charSet);
			} else {
				return FileAttachmentUtil.toRFC2041(filename, charSet);
			}
		} catch (UnsupportedEncodingException e) {
			return FileAttachmentUtil.toRawEncoding(filename, charSet);
		} catch (Exception e) {
			return FileAttachmentUtil.toRawEncoding(filename, charSet);
		}
	}
	
	/**
	 * filename="=?UTF-8?B?VGhpcyBpcyBhbiBlbmNvZGVkIHN0cmluZw==
	 * 
	 * @param filename
	 * @param charSet
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String toRFC2041(String filename, String charSet) throws UnsupportedEncodingException {
		StringBuilder encodedResult = new StringBuilder();

		String base64Filename = new String(Base64.encodeBase64(filename.getBytes(charSet)), "ISO-8859-1");
		encodedResult.append("filename=\"=?").append(charSet).append("?B?").append(base64Filename).append("?=\"");

		return encodedResult.toString();
	}

	/**
	 * filename*=UTF-8''%41.gif
	 * 
	 * @param filename
	 * @param charSet
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String toRFC2231(String filename, String charSet) throws UnsupportedEncodingException {
		StringBuilder encodedResult = new StringBuilder();

		encodedResult.append("filename*=" + charSet + "''");
		String hexFilename = FileAttachmentUtil.toHexFilename(filename, charSet);
		encodedResult.append(hexFilename);

		return encodedResult.toString();
	}

	public static String toRawEncoding(String filename, String charSet) {
		StringBuilder encodedResult = new StringBuilder();

		encodedResult.append("filename=\"");

		String hexFilename;
		try {
			hexFilename = FileAttachmentUtil.toHexFilename(filename, charSet);
		} catch (UnsupportedEncodingException e) {
			hexFilename = filename;
		}
		encodedResult.append(hexFilename);

		encodedResult.append("\"");

		return encodedResult.toString();
	}

	public static String toHexFilename(String filename, String charSet) throws UnsupportedEncodingException {
		StringBuilder encodedName = new StringBuilder();

		String basename = FilenameUtils.getBaseName(filename);
		String extension = FilenameUtils.getExtension(filename);

		byte[] bytes = basename.getBytes(charSet);

		for (byte value : bytes) {
			encodedName.append("%").append(Integer.toHexString(value & 0xff));
		}

		if (extension != null && extension.length() > 0) {
			encodedName.append(".").append(extension);
		}

		return encodedName.toString();
	}
}
