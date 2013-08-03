package com.msc.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;
import com.msc.json.JsonReader;
import com.msc.model.SearchResult;

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
				searchResults.add(new SearchResult(title, content));
				// log.info("Title: " + title);
				// log.info("Content: " + content);
			}

		} catch (JSONException e) {
			throw new RuntimeException(
					"An exception was thrown while reading from the JSon object",
					e);
		}

		saveLatestResults(searchResults);
		// Modified the code to use tag libraries instead of jsp scriptlets
		// here.
		req.setAttribute("searchResult", searchResults);
		req.getRequestDispatcher("/search.jsp").forward(req, resp);

		// We are NOT using jsp scriptlets anymore.
		// resp.sendRedirect("/search.jsp?searchResult=" + searchResult);
	}

	private void saveLatestResults(List<SearchResult> searchResults) {
		Key searchKey = KeyFactory.createKey("SearchResult", "search");
		Entity resultEntity = null;
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		// Add the latests search results here.
		for (SearchResult searchResult : searchResults) {
			resultEntity = new Entity("SearchResult", searchKey);
			resultEntity.setProperty("title", searchResult.getTitle());
			resultEntity.setProperty("content",
					new Text(searchResult.getContent()));
			resultEntity.setProperty("date", new Date());

			datastore.put(resultEntity);
		}

		// Remove the stale/obsoleted search results here.
		Query query = new Query("SearchResult", searchKey).addSort("date",
				Query.SortDirection.DESCENDING);
		List<Entity> results = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(100));
		for (Entity entity : results) {
			log.info("Title: " + entity.getProperty("title") + " Content: "
					+ entity.getProperty("content") + " Date: "
					+ entity.getProperty("date"));
		}
	}
}
