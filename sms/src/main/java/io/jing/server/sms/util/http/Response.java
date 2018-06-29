/**
 * Project Name:springlearn
 * File Name:Response.java
 * Package Name:com.jing.web.util.http
 * Date:2016年1月22日下午3:23:01
 * Copyright (c) 2016, jingshouyan@126.com All Rights Reserved.
 *
*/

package io.jing.server.sms.util.http;

import java.util.Map;

/**
 * ClassName:Response <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年1月22日 下午3:23:01 <br/>
 * @author   bxy-jing
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Response {
	private int statusCode;
	private String reasonPhrase;
	private String body;
	private Map<String,String> headers;
	private Exception exception;
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getReasonPhrase() {
		return reasonPhrase;
	}
	public void setReasonPhrase(String reasonPhrase) {
		this.reasonPhrase = reasonPhrase;
	}
}

