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

		try {
			// TODO: Fill the Business logic here.
			String query = req.getParameter("query");
			log.info(query);

			String strUrl = "http://data.nasa.gov/api/get_search_results/?search="
					+ query;
			JSONObject jsonObj = JsonReader.readJsonFromUrl(strUrl);
			log.info(jsonObj.toString());

			// Read all the title values from the JSon objects.
			// log.info(jsonObj.getString("title"));
		} catch (JSONException e) {
			throw new RuntimeException(
					"An exception was thrown while reading from the JSon objec	t",
					e);
		}

	}
}
