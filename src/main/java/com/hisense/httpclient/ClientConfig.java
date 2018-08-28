/**   
* @Title: ClientConfig.java 
* @Package com.hisense.httpclient 
* @Description: TODO
* @author xuejian3
* @date 2018年8月19日 下午3:30:09   
*/
package com.hisense.httpclient;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


/** 
* @ClassName: ClientConfig 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @date 2018年8月19日 下午3:30:09 
*  
*/
public class ClientConfig {
	
	public void configClient() throws IOException {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String url="http://127.0.0.1:80";
		RequestConfig defaultConfig = RequestConfig.custom().build();
		RequestConfig clientConfig = RequestConfig.copy(defaultConfig)
									.setSocketTimeout(2000)
									.setConnectionRequestTimeout(1000)
									.setConnectTimeout(50)
									.build();
		
		HttpGet getRequest = new HttpGet(url);
		getRequest.setConfig(clientConfig);
		InputStream instream=null;
		try {
			CloseableHttpResponse response = httpclient.execute(getRequest);
			StatusLine sline = response.getStatusLine();
			
			System.out.println("===========statusline========");
			System.out.println(sline.getReasonPhrase());
			System.out.println(sline.getStatusCode());
			System.out.println(sline.getProtocolVersion().getProtocol());
			System.out.println(sline.getProtocolVersion().getMajor());
			System.out.println(sline.getProtocolVersion().getMinor());
			Header[] headers = response.getAllHeaders();
			System.out.println("=============headers===========");
			System.out.println(headers.toString());
			for(Header header : headers) {
				System.out.println("header.key=" + header.getName() + "header.value= " + header.getValue());
			}
			System.out.println("=============last headers===========");
			HttpEntity entity = response.getEntity();
			System.out.println(entity.getContentLength());
			Header header = entity.getContentType();
//			Header header = entity.getContentEncoding();
			System.out.println("----------------xj-----------");
			System.out.println(header.getName() + "=========" + header.getValue());
			instream = entity.getContent();
			int n = -1;
			System.out.println("====================xj============");
			do {
				n = instream.read();
//				System.out.print((char)n);
			} while (n != -1);
		}catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}finally {
			instream.close();
		}
		System.out.println("----end");
//		if (response.getStatusLine().getStatusCode() == 200 ) {
//			System.out.println("successfully");
//			HttpEntity entity = response.getEntity();
//			if (entity != null) {
//				InputStream instream = entity.getContent();
////				System.out.println(instream.toString());
//				Thread.sleep(5000);
//				instream.close();
//			}
//			Thread.sleep(5000);
//		}
//		response.close();
	}
	
			
	public static void main(String[] args) throws ClientProtocolException, IOException, InterruptedException {
		ClientConfig test = new ClientConfig();
		test.configClient();
	}

}
