package com.msc.json;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {	
	public static void main(String args[]) throws IOException, JSONException {
		/*
		 * File f1 = new File("."); String p1 = f1.getCanonicalPath(); p1 =
		 * p1+"/src/main/resources/content.json"; String content = readFile(p1);
		 * System.out.println(content); JSONObject jOb = new
		 * JSONObject(content); //jOb. System.out.println("done");
		 */
		JSONObject json = readJsonFromUrl("http://data.nasa.gov/api/get_search_results/?search=saturn");
		System.out.println(json.toString());
		try {
			System.out.println(json.get("id"));
		} catch (JSONException ex) {
			System.out.println(ex.getMessage());
		}
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException,
			JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	public static String readFile(String fName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fName));
		String content = null;
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				// sb.append('\n');
				line = br.readLine();
			}
			String everything = sb.toString();
			content = everything;
		} finally {
			br.close();
		}
		return content;
	}

}
