package com.msc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		// TODO: Fill the Business logic here.
		String query = req.getParameter("query");
		log.info(query);
		BufferedReader reader = null;
		List<String> resultList = null;

		// Send the query to the nasa data and fetch the results back.
		try {
			URL url = new URL(
					"http://data.nasa.gov/api/get_search_results/?search="
							+ query);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));

			String line = null;
			resultList = new ArrayList<String>();

			while ((line = reader.readLine()) != null) {
				// TODO: need to filter out the title and content here.
				// Add the read line into the result list.
				resultList.add(line + "\n");
				log.info(line + "\n");
			}
		} finally {
			reader.close();
		}

	}
}
