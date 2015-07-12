package com.thirtygames.zero.common.etc.spring.view;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.view.AbstractView;

import com.thirtygames.zero.common.etc.util.FileAttachmentUtil;

public class GenericCsvView extends AbstractView {
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericCsvView.class);

	public static final String RESOURCE_KEY = "resource";

	public static final String FILE_NAME_KEY = "fileName";

	private boolean shouldBeRemoved = false;

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Resource resource = (Resource)model.get(RESOURCE_KEY);
		String fileName = (String)model.get(FILE_NAME_KEY);

		if (resource == null || StringUtils.isEmpty(fileName)) {
			throw new IllegalArgumentException("Model에서 csv 관련 리소스를 찾을 수가 없네여~ (대소문자 주의!!) >.<");
		}

		setResponseHeader(resource, fileName, request, response);
		copyResponseBody(resource, response);
		removeResourceFileIfNecessary(resource);
	}

	private void setResponseHeader(Resource resource, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType(getContentType());
		response.setContentLength((int)resource.contentLength());
		response.setHeader("Content-Disposition", String.format("attachment; %s", FileAttachmentUtil.getDownloadFileName(request.getHeader("User-Agent"), fileName, "utf-8")));
	}

	private void copyResponseBody(Resource resource, HttpServletResponse response) throws IOException {
		InputStream is = resource.getInputStream();
		try {
			OutputStream os = response.getOutputStream();
			IOUtils.copy(is, os);
			os.flush();
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	private void removeResourceFileIfNecessary(Resource resource) {
		if (shouldBeRemoved) {
			try {
				File file = resource.getFile();
				if (file != null && file.exists() && file.isFile()) {
					FileUtils.forceDelete(file);
				}
			} catch (IOException ioe) {
				LOGGER.error(ioe.getMessage(), ioe);
			}
		}
	}

	public boolean isShouldBeRemoved() {
		return shouldBeRemoved;
	}

	public void setShouldBeRemoved(boolean shouldBeRemoved) {
		this.shouldBeRemoved = shouldBeRemoved;
	}
}
