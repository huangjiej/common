package com.hummingbird.common.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.hummingbird.common.exception.RequestException;
import com.hummingbird.common.util.json.JSONObject;

/**  
 * HTTP请求对象 
 */  
public class HttpRequester {   
    
    private static final Log log = LogFactory.getLog(HttpRequester.class);
    
    public String sendPost(String url,Map<String, String> params){
    	return send(url,"POST",params);
    }
    public String sendPostNoparams(String url,Map<String, String> params){
    	return send(url,"POST",null);
    }
    public String sendGet(String url,Map<String, String> params){
    	return send(url,"GET",params);
    }
    
    
    private String send(String url,String method,Map<String, String> params){
    	StringBuffer temp;
		HttpMethodBase httpMethod = null;
		try {
			HttpClient httpClient=new HttpClient();
			if("GET".equals(method)){
				StringBuilder sb = new StringBuilder();
				if(params!=null){
					for (String varName : params.keySet()) {
						sb = sb.length() > 0 ? sb.append("&" + URLEncoder.encode(varName,"UTF-8") + "=" + URLEncoder.encode(params.get(varName).toString(),"UTF-8")) :
							sb.append(URLEncoder.encode(varName, "UTF-8") + "=" + URLEncoder.encode(params.get(varName).toString(), "UTF-8"));
					}
				}
				if(StringUtils.isNotBlank(sb.toString())){
					httpMethod = new GetMethod(url);
				}
			}else{
				UTF8PostMethod postMethod = new UTF8PostMethod(url);
				if(params!=null){
					for (String varName : params.keySet()) {
						postMethod.setParameter(varName, params.get(varName));
					}
				}
				httpMethod=postMethod;
			}
			log.debug(httpMethod.getURI().toString());
			int code = httpClient.executeMethod(httpMethod);
			if (code != HttpStatus.SC_OK) {
				throw new RuntimeException("服务器错误：" + code);
			}
			InputStream in = httpMethod.getResponseBodyAsStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();

		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new RuntimeException("服务器错误：" + e.getMessage());
		} finally {
			httpMethod.releaseConnection();
		}
		return temp.toString();
    }

    /**
     * 向指定URL发送HTTP请求，并解析返回结果
     * @param url
     * @param parameters
     * @return
     */
    public boolean isCallBackSuccess(String url,Map<String, String> parameters){
    	boolean returnValue=false;	             
	    try {
	    	log.debug("response url"+url);
			String returnStr = sendPost(url,parameters); 
			log.debug("response returnStr"+returnStr);
			if(!StringUtils.isBlank(returnStr)){
				JSONObject obj = new JSONObject(returnStr);
				if(obj!=null){
					if("0".equals(obj.getString("errcode"))){
						returnValue=true;
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return returnValue;
    }
    public boolean isCallBackSuccessByStream(String url,String parameters){
    	boolean returnValue=false;	             
	    try {
	    	log.debug("response url"+url);
			String returnStr = postRequest(url,parameters); 
			log.debug("response returnStr"+returnStr);
			if(!StringUtils.isBlank(returnStr)){
				JSONObject obj = new JSONObject(returnStr);
				if(obj!=null){
					if("0".equals(obj.getString("errcode"))){
						returnValue=true;
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return returnValue;
    }
    public String postRequest(String url, String data) throws RequestException {
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setEntity(new StringEntity(data, "utf-8"));
			DefaultHttpClient client = new DefaultHttpClient();
			// 请求超时
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
			// 读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					60000);
			HttpResponse resp = client.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(resp.getEntity(), "utf-8");
				log.debug( url + "\n" + data + "\n===============\n"
						+ result);
				return result;
			} else {
//				Logger.d(TAG, url + "\n================\n retCode="
//						+ resp.getStatusLine().getStatusCode());
				return null;
			}
		} catch (UnsupportedEncodingException e) {
//			Logger.d(TAG, "json post request UnsupportedEncodingException");
//			e.printStackTrace();
//			return null;
			throw new RequestException("请求失败，字符编码不正确",e);
		} catch (ClientProtocolException e) {
//			Logger.d(TAG, "json post request ClientProtocolException");
			throw new RequestException("请求失败，协议不正确",e);
//			e.printStackTrace();
//			return null;
		} catch (IOException e) {
//			Logger.d(TAG, "json post request IOException");
//			e.printStackTrace();
			throw new RequestException("请求出错",e);
		}
	}

}  
