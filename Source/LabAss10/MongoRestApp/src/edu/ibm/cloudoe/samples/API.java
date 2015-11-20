package com.ibm.cloud.samples;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class API {
	

	private static String baseURL = "https://gateway-a.watsonplatform.net/calls/";

	private static String key = "68516df5e5c635b0b5a6ab32ca7cd20b4401e86e";
	 
	public String URLGetAuthor(String searchKey,String url)
			throws IOException, SAXException,
			ParserConfigurationException, XPathExpressionException
		{
			System.out.println("Search Key is " + searchKey);
			return URLGetAuthor(searchKey,url, new AlchemyApi_params());
		}
	
	public String URLGetAuthor(String searchKey,String url, AlchemyApi_params params)
			throws IOException, SAXException, ParserConfigurationException,
			XPathExpressionException
		{
			params.setUrl(url);
			return GET(searchKey, "url", params);
		}
	
	private String GET(String callName, String callPrefix, AlchemyApi_params params)
			throws IOException, SAXException,
			ParserConfigurationException, XPathExpressionException
		{
			System.out.println("callName is " + callName);
			StringBuilder uri = new StringBuilder();
			uri.append(baseURL).append(callPrefix).append('/').append(callName)
			.append('?').append("apikey=").append(key);
			uri.append(params.getParameterString());
			URL url = new URL(uri.toString());
			System.out.println(url);
			HttpURLConnection handle = (HttpURLConnection) url.openConnection();
			System.out.println("handle is:" + handle);
			handle.setDoOutput(true);
			//System.out.println("in 1");
			return doRequest(handle, params.getOutputMode());
			
		}
	private String doRequest(HttpURLConnection handle, String outputMode)
			throws IOException
			{
				try{
				    int status = handle.getResponseCode();
				    System.out.println("handle message is: "+ handle.getInputStream());
				    switch (status) {
			            case 200:
			            case 201:
			                BufferedReader br = new BufferedReader(new InputStreamReader(handle.getInputStream()));
			                //System.out.println("br dats is :" + br);
			                StringBuilder sb = new StringBuilder();			                
			                String line;
			                while ((line = br.readLine()) != null) {
			                    sb.append(line+"\n");
			                    //System.out.println("sb dats is :" + sb);
			                }
			                br.close();
			        
			                System.out.println("sb:" + sb);
			                return sb.toString();
			                //return sb.toString();
			              
			        }
					}
				catch(IOException ex){
						System.out.println("IO Exception ");
					}
				return "";
			}
}
