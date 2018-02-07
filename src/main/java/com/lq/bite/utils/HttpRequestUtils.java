package com.lq.bite.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestUtils {
	public static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);
	/**  
	 * 发送GET请求  
	 * @param path 请求路径  
	 * @param params 请求参数  
	 * @param encoding 编码  
	 * @return 请求是否成功  
	 */  
	public static String sendGETRequest(String path, String param, String ecoding) throws Exception{  
	    // http://192.168.1.100:8080/web/ManageServlet?title=xxx&timelength=90  
	    StringBuilder url = new StringBuilder(path);  
	    url.append("?");  
	    url.append(param);
	    logger.info("url="+url.toString());
	    HttpURLConnection conn = (HttpURLConnection)new URL(url.toString()).openConnection();  
	    conn.setConnectTimeout(5000);  
	    conn.setRequestMethod("GET");  
	    if(conn.getResponseCode() == 200){  
	    	BufferedReader in = null;
		    String result = "";
		 // 定义BufferedReader输入流来读取URL的响应
	        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line;
	        while ((line = in.readLine()) != null) {
	            result += line;
	        }
	        return result;
	    }  
	    return null;  
	}  

	/**  
	 * 发送Post请求  
	 * @param path 请求路径  
	 * @param params 请求参数  
	 * @param encoding 编码  
	 * @return 请求是否成功  
	 */  
	public static String sendPOSTRequest(String path, String param, String encoding) throws Exception{  
	    byte[] entity = param.getBytes();//生成实体数据  
	    HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();  
	    conn.setDoOutput(true);
	    conn.setConnectTimeout(5000);  
	    conn.setRequestMethod("POST");  
	    conn.setDoOutput(true);//允许对外输出数据  
	    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
	    conn.setRequestProperty("Content-Length", String.valueOf(entity.length));  
	    OutputStream outStream = conn.getOutputStream();  
	    outStream.write(entity);  
	    if(conn.getResponseCode() == 200){  
	    	BufferedReader in = null;
		    String result = "";
		 // 定义BufferedReader输入流来读取URL的响应
	        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line;
	        while ((line = in.readLine()) != null) {
	            result += line;
	        }
	        return result;
	    }  
	    return null;  
	}  
    
    public static void main(String[] args) throws Exception{
      // String param = "key=41axh-7sdgq-xtsw2-i2dwd-8cu4e-tvy52-883i6&signature=8df4d8dff1e5f5a6e2ab58bf129c76fdb2fe8db5e859f4578d7f995c871b6a09&nonce=1234567";
    	String param = "key=ngnbx-ut82d-63xw5-2rqqw-9ps1h-d483n-iq462&signature=6778333581225a83d1f458d9a52f58be8216d07ac4d80edf109675c57bcba83b&nonce=123456";
    	String flag = sendPOSTRequest("https://api.coinegg.com/api/v1/balance", param,"utf-8");
       System.out.println(flag);
    }
}
