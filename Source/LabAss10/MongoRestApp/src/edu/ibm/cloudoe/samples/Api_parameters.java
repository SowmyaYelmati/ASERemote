package com.ibm.cloudoe.samples;

import java.net.URLEncoder;
import org.xml.sax.SAXException;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import java.io.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

@Path("/Alchemy")
public class Api_parameters {
	public static final String OUTPUT_XML = "json";// change the format here
	private String outputMode = OUTPUT_XML;
	private String url;

	public String getUrl() {
		System.out.println("Get Url is" + url);
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		System.out.println("setUrl is " + url);
	}

	public String getOutputMode() {
		return outputMode;
	}

	@GET
	@Path("/search/{SearchKey}/{SearchUrl: (.+)?}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response data(@PathParam("SearchKey") String SearchKey, @PathParam("SearchUrl") String SearchUrl)
			throws JSONException {
		System.out.println("Search Key is" + SearchKey);
		System.out.println("Search Url is" + SearchUrl);
		AlchemyAPI alchemyObj = new AlchemyAPI();
		String obj = null;
		try {
			obj = alchemyObj.URLGetAuthor(SearchKey, SearchUrl);

		} catch (XPathExpressionException | IOException | SAXException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("object is:" + obj);

		JSONObject object = new JSONObject(obj);

		return Response.ok(object.toString()).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209600").build();
	}

	@GET
	@Path("testing")
	@Produces({ MediaType.APPLICATION_JSON })
	public String jsonData() {
		AlchemyAPI alchemyObj = new AlchemyAPI();
		String obj = null;
		try {
			obj = alchemyObj.URLGetAuthor("URLGetRankedConcepts",
					"http://www.politico.com/blogs/media/2012/02/detroit-news-ed-upset-over-romney-edit-115247.html");
		} catch (XPathExpressionException | IOException | SAXException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Printed as a String, but can be converted to the reqd format using
		// proper libraries
		System.out.println("object is:" + obj);
		return obj;
	}

	public static void main(String[] args)
			throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {

		AlchemyApi_params alechemy = new AlchemyApi_params();
		String output = alechemy.jsonData();

	}

	public String getParameterString() {
		String retString = "";
		try {
			if (url != null)
				retString += "&url=" + URLEncoder.encode(url, "UTF-8");
			if (outputMode != null)
				retString += "&outputMode=" + outputMode;
		} catch (UnsupportedEncodingException e) {
			retString = "";
		}
		return retString;
	}
}