package com.fairchild.love;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.net.Uri;
import android.util.Log;

public class HttpService {
	public static boolean isAlive(String jsessionid) {
    	StringBuffer sb = new StringBuffer(Constants.HTTP_BASE);
    	sb.append("isAlive.xhtml");
    	
    	String httpURL = sb.toString();
    	
    	boolean isAlive = false;
    	
		try {
			HttpGet httpGet = new HttpGet(httpURL);
			httpGet.addHeader("Cookie", Constants.MAPKEY_JSESSIONID + "=" + jsessionid);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpGet);

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			Log.d("[HttpService.isAlive]", "Getting " + httpURL);
			Log.d("[HttpService.isAlive]", "JSESSIONID  " + jsessionid);
			Log.d("[HttpService.isAlive]", "Http response status code: " + statusCode);
			
			if (HttpStatus.SC_OK == statusCode) {
				HttpEntity entity = httpResponse.getEntity();
				String responseText = EntityUtils.toString(entity);
				Log.d("[HttpService.isAlive]", "Succeed to get " + httpURL);
				isAlive = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return isAlive;
	}
	
	public static Map<String, String> confessionsReceived(String username, String jsessionid) throws Exception {	
    	StringBuffer sb = new StringBuffer(Constants.HTTP_BASE);
    	sb.append("confessionsReceived.xhtml?")
    	.append("username=").append(username);
    	
    	String httpURL = sb.toString();
    	
		Map<String,String> map = new HashMap<String, String>();
		try {
			HttpGet httpGet = new HttpGet(httpURL);
			httpGet.addHeader("Cookie", Constants.MAPKEY_JSESSIONID + "=" + jsessionid);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpGet);

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			Log.d("[HttpService.confessionsReceived]", "Getting " + httpURL);
			Log.d("[HttpService.confessionsReceived]", "JSESSIONID  " + jsessionid);
			Log.d("[HttpService.confessionsReceived]", "Http response status code: " + statusCode);
			if (HttpStatus.SC_OK == statusCode) {
				HttpEntity entity = httpResponse.getEntity();
				String responseText = EntityUtils.toString(entity);
				map.put("responseText", responseText);

				Log.d("[HttpService.confessionsReceived]", "Succeed to get " + httpURL);
				Log.d("[HttpService.confessionsReceived]", "Http response: " + map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
		/*
		 * HttpURLConnection conn = (HttpURLConnection)new URL(path).openConnection(); 
		 * conn.setConnectTimeout(5 * 1000);
		 * conn.setRequestMethod("GET"); 
		 * if (200 == conn.getResponseCode()) {
		 *   InputStream is = conn.getInputStream();
		 *   JSONArray jsonArray = new JSONArray(new String(readData(is)));
		 *   is.close();
		 * 
		 *   for (int i = 0; i < jsonArray.length(); i++) {
		 *     JSONObject item = jsonArray.getJSONObject(i); 
		 *     String from = item.getString("from");
		 *     String said = item.getString("said");
		 *     Map<String, String> map = new HashMap<String, String>();
		 *     map.put("from", from);
		 *     map.put("said", said);
		 *     list.add(map); 
		 *   } 
		 * }
		 */
	}

	public static byte[] readData(InputStream is) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while (-1 != (len = is.read(buffer))) {
			os.write(buffer, 0, len);
		}
		os.close();

		return os.toByteArray();
	}

	public static Map<String, String> register(String username, String password) {
    	StringBuffer sb = new StringBuffer(Constants.HTTP_BASE);
    	sb.append("register.xhtml?")
    	.append("username=").append(username).append("&")
    	.append("password=").append(password);
    	String httpURL = sb.toString();
    	
		Map<String,String> map = new HashMap<String, String>();
		try {
			HttpGet httpGet = new HttpGet(httpURL);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpGet);

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			Log.d("[HttpService.register]", "Getting " + httpURL);
			Log.d("[HttpService.register]", "Http response status code: " + statusCode);
			if (HttpStatus.SC_OK == statusCode) {
				HttpEntity entity = httpResponse.getEntity();
				String responseText = EntityUtils.toString(entity);
				map.put("reponseText", responseText);
				CookieStore cookieStore = httpClient.getCookieStore();
				List<Cookie> cookies = cookieStore.getCookies();
				for (int i = 0; i < cookies.size(); i++) {
					if (Constants.MAPKEY_JSESSIONID.equals(cookies.get(i).getName())) {
						String jsessionid = cookies.get(i).getValue();
						map.put(Constants.MAPKEY_JSESSIONID, jsessionid);
						break;
					}
				}
				Log.d("[HttpService.register]", "Succeed to get " + httpURL);
				Log.d("[HttpService.register]", "Http response: " + map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String,String> login(String username, String password) {
    	StringBuffer sb = new StringBuffer(Constants.HTTP_BASE);
    	sb.append("login.xhtml?")
    	.append("username=").append(username).append("&")
    	.append("password=").append(password);
    	String httpURL = sb.toString();
    	
		Map<String,String> map = new HashMap<String, String>();
		try {
			HttpGet httpGet = new HttpGet(httpURL);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpGet);

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			Log.d("[HttpService.login]", "Getting " + httpURL);
			Log.d("[HttpService.login]", "Http response status code: " + statusCode);
			if (HttpStatus.SC_OK == statusCode) {
				HttpEntity entity = httpResponse.getEntity();
				String responseText = EntityUtils.toString(entity);
				map.put("reponseText", responseText);
				CookieStore cookieStore = httpClient.getCookieStore();
				List<Cookie> cookies = cookieStore.getCookies();
				for (int i = 0; i < cookies.size(); i++) {
					if (Constants.MAPKEY_JSESSIONID.equals(cookies.get(i).getName())) {
						String jsessionid = cookies.get(i).getValue();
						map.put(Constants.MAPKEY_JSESSIONID, jsessionid);
						break;
					}
				}
				Log.d("[HttpService.login]", "Succeed to get " + httpURL);
				Log.d("[HttpService.login]", "Http response: " + map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String,String> logout(String jsessionid) {
    	StringBuffer sb = new StringBuffer(Constants.HTTP_BASE);
    	sb.append("logout.xhtml");
    	String httpURL = sb.toString();
    	
		Map<String,String> map = new HashMap<String, String>();
		try {
			HttpGet httpGet = new HttpGet(httpURL);
			httpGet.addHeader("Cookie", Constants.MAPKEY_JSESSIONID + "="+jsessionid);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpGet);

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			Log.d("[HttpService.logout]", "Getting " + httpURL);
			Log.d("[HttpService.logout]", "JSESSIONID  " + jsessionid);
			Log.d("[HttpService.logout]", "Http response status code: " + statusCode);
			if (HttpStatus.SC_OK == statusCode) {
				HttpEntity entity = httpResponse.getEntity();
				String responseText = EntityUtils.toString(entity);
				map.put("reponseText", responseText);
				
				Log.d("[HttpService.logout]", "Succeed to get " + httpURL);
				Log.d("[HttpService.logout]", "Http response: " + map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, String> createConfession(String from, String to, String said, String jsessionid) {
    	StringBuffer sb = new StringBuffer(Constants.HTTP_BASE);
    	sb.append("createConfession.xhtml?")
    	.append("from=").append(Uri.encode(from)).append("&")
    	.append("to=").append(Uri.encode(to)).append("&")
    	.append("said=").append(Uri.encode(said));
    	
    	String httpURL = sb.toString();
    	
		Map<String,String> map = new HashMap<String, String>();
		try {
			HttpGet httpGet = new HttpGet(httpURL);
			httpGet.addHeader("Cookie", Constants.MAPKEY_JSESSIONID + "="+jsessionid);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpGet);

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			Log.d("[HttpService.createConfession]", "Getting " + httpURL);
			Log.d("[HttpService.createConfession]", "JSESSIONID  " + jsessionid);
			Log.d("[HttpService.createConfession]", "Http response status code: " + statusCode);
			if (HttpStatus.SC_OK == statusCode) {
				HttpEntity entity = httpResponse.getEntity();
				String responseText = EntityUtils.toString(entity);
				map.put("reponseText", responseText);
				Log.d("[HttpService.createConfession]", "Succeed to get " + httpURL);
				Log.d("[HttpService.createConfession]", "Http response: " + map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public String httpPostRequest(String httpURL, List<NameValuePair> params, String jsessionid) {
		try {
			HttpPost httpPost = new HttpPost(httpURL);
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			httpPost.setEntity(httpEntity);
			if (null != jsessionid) {
				httpPost.setHeader("Cookie", Constants.MAPKEY_JSESSIONID + "=" + jsessionid);
			}
			DefaultHttpClient httpClient = new DefaultHttpClient();

			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()) {
				HttpEntity responseEntity = httpResponse.getEntity();
				return EntityUtils.toString(responseEntity);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
