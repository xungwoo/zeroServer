package com.thirtygames.zero.common.etc.network;

import gherkin.formatter.model.DataTableRow;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;

@Slf4j
public class URLConnector {

    public static class HTTPResponse {
        public int code;
        public String errorMessage;
        public String contentType;
        public String content;

        public HTTPResponse(int code, String errorMsg, String contentType, String content) {
            this.code = code;
            this.errorMessage = errorMsg;
            this.contentType = contentType;
            this.content = content;
        }
    }

    //private static Logger _log = LoggerFactory.getLogger(URLConnector.class);

    /**
     * Execute an url request. Similar to hit an end point with browser.
     * Retrurn the response messsage (html), null if the execution fail.
     *
     * @param url
     * @param headersParam
     * @return String
     */
    public static HTTPResponse httpCall(String url, Map<String, String> headersParam, List<DataTableRow> queryParam) {
        try {
            StringBuilder queryBuilder = null;
            if (queryParam != null) {
                queryBuilder = new StringBuilder();
                for (DataTableRow row : queryParam) {
                    if (row.getCells().size() == 2) {
                        if (queryBuilder.length() > 0) {
                            queryBuilder.append("&");
                        }
                        queryBuilder.append(row.getCells().get(0));
                        queryBuilder.append("=");
                        queryBuilder.append(URLEncoder.encode(row.getCells().get(1), "UTF-8"));
                        log.debug("Add queryParameter {}={}", row.getCells().get(0), row.getCells().get(1));
                    } else {
                        Assert.fail("The query parameter row hasn't two columns.");
                    }
                }

            }


            URL target;
            if (queryBuilder == null) {
                target = new URL(url);
            } else {
                target = new URL(url + "?" + queryBuilder.toString());
            }
            log.info("Build HttpURLConnection for {}.", target.toExternalForm());


            HttpURLConnection conn = (HttpURLConnection) target
                    .openConnection();
            log.debug("Set up header informations");
            if (null != headersParam) {
                for (String key : headersParam.keySet()) {
                    conn.addRequestProperty(key, headersParam.get(key));
                    log.info(String.format("Add header | %s:%s", key, headersParam.get(key)));
                }
            }
            log.info("Request URL: " + url);
            String contentString = "";
            if (conn.getResponseCode() == 200 || conn.getResponseCode() == 201) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                contentString = sb.toString();
            }

            String errorString = "";
            if (conn.getErrorStream() != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                errorString = sb.toString();
            }

            HTTPResponse returnValue = new HTTPResponse(conn.getResponseCode(), errorString, conn.getContentType(), contentString);
            log.info("Response Code = " + returnValue.code);
            return returnValue;
        } catch (Exception e) {
            log.error("Error while connect url {}.", url, e);
            Assert.fail();
        }
        return null;
    }
    

    /**
     *
     * @param url
     * @param jsonParams
     * @return HTTPResponse
     */
    public static HTTPResponse httpCallPostJson(String url, String jsonParams) {
    	try {
    		URL target = new URL(url);
    		log.info("Build HttpURLConnection for {}.", target.toExternalForm());
    		
    		HttpURLConnection conn = (HttpURLConnection) target.openConnection();
    		conn.setDoOutput(true);
   			conn.setRequestMethod("POST");
   			conn.setRequestProperty( "Content-Type", "application/json");
   			
    		OutputStream wr = conn.getOutputStream();
    		wr.write(jsonParams.getBytes());
    		wr.flush();
    		wr.close();
    		
    		log.debug("jsonParams::" + jsonParams);
    		
    		
    		log.info("Request URL: " + url);
    		String contentString = "";
    		if (conn.getResponseCode() == 200 || conn.getResponseCode() == 201) {
    			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    			StringBuilder sb = new StringBuilder();
    			String line;
    			while ((line = br.readLine()) != null) {
    				sb.append(line);
    			}
    			br.close();
    			contentString = sb.toString();
    		}
    		
    		String errorString = "";
    		if (conn.getErrorStream() != null) {
    			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
    			StringBuilder sb = new StringBuilder();
    			String line;
    			while ((line = br.readLine()) != null) {
    				sb.append(line);
    			}
    			br.close();
    			errorString = sb.toString();
    		}
    		
    		HTTPResponse returnValue = new HTTPResponse(conn.getResponseCode(), errorString, conn.getContentType(), contentString);
    		log.info("Response Code = " + returnValue.code);
    		log.info("Response = " + returnValue);
    		return returnValue;
    	} catch (Exception e) {
    		log.error("Error while connect url {}.", url, e);
    		Assert.fail();
    	}
    	return null;
    }    


}
