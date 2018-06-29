/**
 * Project Name:springlearn
 * File Name:HttpUtil.java
 * Package Name:com.jing.web.util.http
 * Date:2016年1月21日下午6:52:13
 * Copyright (c) 2016, jingshouyan@126.com All Rights Reserved.
 *
*/

package io.jing.server.sms.util.http;

import io.jing.base.util.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * ClassName:HttpUtil <br/>
 * Function: http请求工具类 <br/>
 * Date: 2016年1月21日 下午6:52:13 <br/>
 * 
 * @author bxy-jing
 * @version
 * @since JDK 1.6
 * @see
 */
@Slf4j
public class HttpUtil {


	public static final String DEFAULT_CHARSET = "utf-8";

	/**
	 * 
	 * get:使用httpClient发送get请求. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response get(String url) {
		return get(url, null, null, DEFAULT_CHARSET);
	}

	/**
	 * 
	 * get:使用httpClient发送get请求. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param params
	 *            参数
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response get(String url, Map<String, String> params) {
		return get(url, params, null, DEFAULT_CHARSET);
	}

	/**
	 * 
	 * get:使用httpClient发送get请求. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param params
	 *            参数
	 * @param headers
	 *            请求头
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response get(String url, Map<String, String> params, Map<String, String> headers) {
		return get(url, params, headers, DEFAULT_CHARSET);
	}

	/**
	 * 
	 * post:使用httpClient发送post请求. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param params
	 *            参数
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response post(String url, Map<String, String> params) {
		return post(url, params, null, DEFAULT_CHARSET);
	}

	/**
	 * 
	 * postJson:使用httpClient发送post json请求 . <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param object
	 *            需要传递的对象
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response postJson(String url, Object object) {
		return postJson(url, object, null, DEFAULT_CHARSET);
	}

	/**
	 * 
	 * postJson:使用httpClient发送post json请求 . <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param object
	 *            需要传递的对象
	 * @param headers
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response postJson(String url, Object object, Map<String, String> headers) {
		return postJson(url, object, headers, DEFAULT_CHARSET);
	}

	/**
	 * 
	 * postJson:使用httpClient发送post json请求 . <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param object
	 *            需要传递的对象
	 * @param headers
	 * @param charset
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response postJson(String url, Object object, Map<String, String> headers, String charset) {
		if (null == headers) {
			headers = new HashMap<String, String>();
		}
		headers.put("Content-Type", "application/json");
		String data = JsonUtil.toJsonString(object);
		return postStr(url, data, headers, charset);
	}

	/**
	 * 
	 * post:使用httpClient发送post请求. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param params
	 *            参数
	 * @param headers
	 *            请求头
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response post(String url, Map<String, String> params, Map<String, String> headers) {
		return post(url, params, headers, DEFAULT_CHARSET);
	}

	/**
	 * 
	 * postStr:使用httpClient发送post请求. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param data
	 *            post数据
	 * @param headers
	 *            请求头
	 * @param charset
	 *            编码格式
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response postStr(String url, String data, Map<String, String> headers, String charset) {
		log.debug("post-send|url>>>{}|data>>>{}|headers>>>{}", url, data, headers);
		long startTime = System.currentTimeMillis();
		Response response = new Response();
		CloseableHttpClient client = createClient(url);
		HttpPost httpPost = new HttpPost(url);
		RequestConfig config = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		httpPost.setConfig(config);
		httpPost.setHeaders(map2Headers(headers));
		CloseableHttpResponse httpResponse = null;
		try {
			StringEntity stringEntity = new StringEntity(data, charset);
			httpPost.setEntity(stringEntity);
			httpResponse = client.execute(httpPost);
			response = formatResponse(httpResponse, charset);		
		} catch (Exception e) {
			response.setReasonPhrase(e.getMessage());
			response.setException(e);
			log.error("err",e);
		} finally {
			close(client, httpResponse);
		}
		long endTime = System.currentTimeMillis();
		log.info("post-response|url>>>{}|used>>>{}ms|response>>>{}", url, endTime - startTime,
				JsonUtil.toJsonString(response));

		return response;
	}

	/**
	 * 
	 * post:使用httpClient发送post请求. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param params
	 *            参数
	 * @param headers
	 *            请求头
	 * @param charset
	 *            编码格式
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response post(String url, Map<String, String> params, Map<String, String> headers, String charset) {
		String paramsStr = URLEncodedUtils.format(map2NameValuePairs(params), charset);
		if (null == headers) {
			headers = new HashMap<String, String>();
		}
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		return postStr(url, paramsStr, headers, charset);
	}

	/**
	 * 
	 * get:使用httpClient发送get请求. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param params
	 *            参数
	 * @param headers
	 *            请求头
	 * @param charset
	 *            编码格式
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response get(String url, Map<String, String> params, Map<String, String> headers, String charset) {
		log.info("get-send|url>>>{}|params>>>{}|headers>>>{}", url, params, headers);
		long startTime = System.currentTimeMillis();
		Response response = new Response();
		CloseableHttpClient client = createClient(url);
		if (null != params && !params.isEmpty()) {
			if (url.indexOf("?") == -1) {
				url += "?";
			}
			String paramsStr = URLEncodedUtils.format(map2NameValuePairs(params), charset);
			if (!url.endsWith("?")) {
				url += "&";
			}
			url += paramsStr;
		}
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeaders(map2Headers(headers));
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = client.execute(httpGet);
			response = formatResponse(httpResponse, charset);
		} catch (IOException e) {
			response.setReasonPhrase(e.getMessage());
			log.error("err",e);

		}
		long endTime = System.currentTimeMillis();
		log.info("get-response|url>>>{}|used>>>{}ms|response>>>{}", url, endTime - startTime,
				JsonUtil.toJsonString(response));
		return response;
	}

	/**
	 * 
	 * upload:httpClient上传文件. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param files
	 *            文件map
	 * @return
	 * @since JDK 1.6
	 */
	public static Response upload(String url, Map<String, String> files) {
		return upload(url, files, null);
	}

	/**
	 * 
	 * upload:httpClient上传文件. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param files
	 *            文件map
	 * @param params
	 *            其他参数
	 * @return
	 * @since JDK 1.6
	 */
	public static Response upload(String url, Map<String, String> files, Map<String, String> params) {
		return upload(url, files, params, null);
	}

	/**
	 * 
	 * upload:httpClient上传文件. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param files
	 *            文件map
	 * @param params
	 *            其他参数
	 * @param headers
	 *            头文件
	 * @return
	 * @since JDK 1.6
	 */
	public static Response upload(String url, Map<String, String> files, Map<String, String> params,
			Map<String, String> headers) {
		return upload(url, files, params, headers, DEFAULT_CHARSET);
	}

	/**
	 * 
	 * upload:httpClient上传文件. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param files
	 *            文件map
	 * @param params
	 *            其他参数
	 * @param headers
	 *            头文件
	 * @param charset
	 * @return
	 * @since JDK 1.6
	 */
	public static Response upload(String url, Map<String, String> files, Map<String, String> params,
			Map<String, String> headers, String charset) {
		log.info("upload-send|url>>>{}|files>>>{}|params>>>{}|headers>>>{}", url, files, params, headers);
		long startTime = System.currentTimeMillis();
		Response response = new Response();
		CloseableHttpClient client = createClient(url);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeaders(map2Headers(headers));
		CloseableHttpResponse httpResponse = null;
		try {
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setCharset(Charset.forName(charset));
			if (null != files && !files.isEmpty()) {
				for (Entry<String, String> entry : files.entrySet()) {
					builder.addPart(entry.getKey(), new FileBody(new File(entry.getValue())));
				}
			}
			if (null != params && !params.isEmpty()) {
				for (Entry<String, String> entry : params.entrySet()) {
					builder.addTextBody(entry.getKey(), entry.getValue(), ContentType.TEXT_PLAIN);
				}
			}
			HttpEntity entity = builder.build();
			httpPost.setEntity(entity);
			httpResponse = client.execute(httpPost);
			response = formatResponse(httpResponse, charset);
		} catch (Exception e) {
			response.setReasonPhrase(e.getMessage());
			log.error("err",e);
		} finally {
			close(client, httpResponse);
		}

		long endTime = System.currentTimeMillis();
		log.info("upload-response|url>>>{}|used>>>{}ms|response>>>{}", url, endTime - startTime,
				JsonUtil.toJsonString(response));
		return response;
	}

	private static List<NameValuePair> map2NameValuePairs(Map<String, String> map) {
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		if (null != map && !map.isEmpty()) {
			for (Entry<String, String> param : map.entrySet()) {
				formParams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
			}
		}
		return formParams;
	}

	/**
	 * 
	 * formatResponse:将CloseableHttpResponse转换成自定义Response对象. <br/>
	 *
	 * @author bxy-jing
	 * @param httpResponse
	 * @param charset
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @since JDK 1.6
	 */
	private static Response formatResponse(CloseableHttpResponse httpResponse, String charset)
			throws ParseException, IOException {
		Response response = new Response();
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		String reasonPhrase = httpResponse.getStatusLine().getReasonPhrase();
		HttpEntity entity = httpResponse.getEntity();
		Header[] retHeaders = httpResponse.getAllHeaders();
		String body = EntityUtils.toString(entity, charset);
		response.setStatusCode(statusCode);
		response.setReasonPhrase(reasonPhrase);
		response.setBody(body);
		response.setHeaders(headers2Map(retHeaders));
		return response;
	}

	/**
	 * 
	 * headers2Map:header数组转map. <br/>
	 *
	 * @author bxy-jing
	 * @param headers
	 *            header数组
	 * @return map
	 * @since JDK 1.6
	 */
	private static Map<String, String> headers2Map(Header[] headers) {
		Map<String, String> map = new HashMap<String, String>();
		if (null != headers && headers.length > 0) {
			for (Header header : headers) {
				map.put(header.getName(), header.getValue());
			}
		}
		return map;
	}

	/**
	 * 
	 * map2Headers:map对象转Header数组 <br/>
	 *
	 * @author bxy-jing
	 * @param map
	 *            map对象
	 * @return Header数组
	 * @since JDK 1.6
	 */
	private static Header[] map2Headers(Map<String, String> map) {
		if (null != map && !map.isEmpty()) {
			int size = map.size();
			Header[] headers = new Header[size];
			int i = 0;
			for (Entry<String, String> entry : map.entrySet()) {
				headers[i] = new BasicHeader(entry.getKey(), entry.getValue());
				i++;
			}
			return headers;
		} else {
			return new Header[] {};
		}
	}

	/**
	 * 
	 * close:关闭链接 <br/>
	 *
	 * @author bxy-jing
	 * @param client
	 * @param response
	 * @since JDK 1.6
	 */
	private static void close(CloseableHttpClient client, CloseableHttpResponse response) {
		if (null != response) {
			try {
				response.close();
			} catch (IOException e) {
				log.error("err",e);
			}
		}
		if (null != client) {
			try {
				client.close();
			} catch (IOException e) {
				log.error("err",e);
			}
		}
	}

	/**
	 * 
	 * createClient:根据url创建对应的httpClient（http/https）. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @return
	 * @since JDK 1.6
	 */
	public static CloseableHttpClient createClient(String url) {
		CloseableHttpClient client;
		if (url.toLowerCase().startsWith("https://")) {
			client = createSSLClientDefault();
		} else {
			client = createClientDefault();
		}
		return client;
	}

	/**
	 * 
	 * createClientDefault:创建http httpClient. <br/>
	 *
	 * @author bxy-jing
	 * @return
	 * @since JDK 1.6
	 */
	public static CloseableHttpClient createClientDefault() {
		CloseableHttpClient client = HttpClients.createDefault();
		return client;
	}

	/**
	 * 
	 * createSSLClientDefault:创建https httpClient <br/>
	 *
	 * @author bxy-jing
	 * @return
	 * @since JDK 1.6
	 */
	public static CloseableHttpClient createSSLClientDefault() {
		CloseableHttpClient httpsclient = new DefaultHttpClient();
		SSLContext ctx = null;
		try {
			ctx = SSLContext.getInstance("TLSv1.2");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			log.error("err",e);
		}
		X509TrustManager tm = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
			}
			@Override
			public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
			}
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			ctx.init(null, new TrustManager[] { tm }, null);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			log.error("err",e);
		}
		SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		ClientConnectionManager ccm = httpsclient.getConnectionManager();
		SchemeRegistry sr = ccm.getSchemeRegistry();
		sr.register(new Scheme("https", 443, ssf));

		return httpsclient;
	}



}
