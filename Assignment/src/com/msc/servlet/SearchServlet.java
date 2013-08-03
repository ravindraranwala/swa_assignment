package com.msc.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.msc.json.JsonReader;

public class SearchServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(SearchServlet.class
			.getName());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		StringBuffer searchResult = new StringBuffer("");

		try {
			// TODO: Fill the Business logic here.
			String query = req.getParameter("query");
			log.info(query);

			String strUrl = "http://data.nasa.gov/api/get_search_results/?search="
					+ query;
			JSONObject jsonObj = JsonReader.readJsonFromUrl(strUrl);
			log.info(jsonObj.toString());

			// Read all the title and content values from the JSon objects.
			JSONObject json = JsonReader.readJsonFromUrl(strUrl);
			JSONArray jA = json.getJSONArray("posts");
			for (int i = 0; i < jA.length(); i++) {
				JSONObject jo1 = jA.getJSONObject(i);
				String content = jo1.get("content").toString();
				String title = jo1.getString("title_plain").toString();

				// Append the title and content into the search result.
				searchResult = searchResult.append(title + "\n" + content
						+ "\n\n\n\n");
				log.info("Title: " + title);
				log.info("Content: " + content);
			}

		} catch (JSONException e) {
			throw new RuntimeException(
					"An exception was thrown while reading from the JSon object",
					e);
		}

		String helloMsg = "Hello World !";
		resp.sendRedirect("/search.jsp?searchResult=" + searchResult);
	}
}
