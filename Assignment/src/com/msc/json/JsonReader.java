package com.msc.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {
	public static void main(String args[]) throws IOException, JSONException {

		JSONObject json = readJsonFromUrl("http://data.nasa.gov/api/get_search_results/?search=saturn");
		// System.out.println(json.toString());
		try {
			// System.out.println(json.get("posts"));
			JSONArray jA = json.getJSONArray("posts");
			for (int i = 0; i < jA.length(); i++) {
				JSONObject jo1 = jA.getJSONObject(i);
				JSONObject jo2 = jo1.getJSONObject("custom_fields");
				String content = jo1.get("content").toString();
				String title = jo1.getString("title_plain").toString();
				String url = jo1.get("url").toString();
				String moreInfo = jo2.get("more_info_link").toString();
				System.out.println("content: " + content + "url: " + url
						+ "\nmoreinfo: " + moreInfo + " title_plain " + title
						+ "\n");
			}
			// System.out.println(json.getJSONArray("posts"));
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

	public static JSONObject readJsonFromUrl(String urlStr) throws IOException,
			JSONException {
		InputStream is = new URL(urlStr).openStream();
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

	public static JSONObject readJsonFromUrlWithNoTimeouts(String urlStr)
			throws IOException, JSONException {
		URLConnection connection = new URL(urlStr).openConnection();
		connection.setConnectTimeout(0);
		connection.setReadTimeout(0);

		InputStream is = connection.getInputStream();

		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}
}
