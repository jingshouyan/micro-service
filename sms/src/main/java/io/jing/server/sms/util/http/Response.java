/**
 * Project Name:springlearn
 * File Name:Response.java
 * Package Name:com.jing.web.util.http
 * Date:2016年1月22日下午3:23:01
 * Copyright (c) 2016, jingshouyan@126.com All Rights Reserved.
 *
*/

package io.jing.server.sms.util.http;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@Getter@Setter
@ToString
public class Response {
	private int statusCode;
	private String reasonPhrase;
	private String body;
	private Map<String,String> headers;
	private Exception exception;
}

