package com.msc.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.msc.json.JsonReader;
import com.msc.model.SearchQuery;
import com.msc.model.SearchResult;
import com.msc.storage.QueryStorageService;

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
		List<SearchResult> searchResults = new ArrayList<SearchResult>();
		String query;
		try {
			// TODO: Fill the Business logic here.
			query = req.getParameter("query");
			log.info(query);

			String strUrl = "http://data.nasa.gov/api/get_search_results/?search="
					+ query;
			JSONObject jsonObj = JsonReader.readJsonFromUrl(strUrl);
			log.info(jsonObj.toString());

			// Read all the title and content values from the JSon objects.
			JSONObject json = JsonReader.readJsonFromUrlWithNoTimeouts(strUrl);
			JSONArray jA = json.getJSONArray("posts");
			for (int i = 0; i < jA.length(); i++) {
				JSONObject jo1 = jA.getJSONObject(i);
				String content = jo1.get("content").toString();
				String title = jo1.getString("title_plain").toString();
				searchResults.add(new SearchResult(title, content));
				// log.info("Title: " + title);
				// log.info("Content: " + content);
			}

		} catch (JSONException e) {
			throw new RuntimeException(
					"An exception was thrown while reading from the JSon object",
					e);
		}

		// saveLatestResults(searchResults);
		List<SearchQuery> latestQueries = QueryStorageService
				.saveLatestQueries(query);
		// Modified the code to use tag libraries instead of jsp scriptlets
		// here.
		req.setAttribute("searchResult", searchResults);
		req.setAttribute("latestQueries", latestQueries);
		req.getRequestDispatcher("/search.jsp").forward(req, resp);
	}

}
