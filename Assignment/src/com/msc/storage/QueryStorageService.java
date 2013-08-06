package com.msc.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.msc.model.SearchQuery;

public class QueryStorageService {
	private static final Logger log = Logger
			.getLogger(QueryStorageService.class.getName());

	public static List<SearchQuery> saveLatestQueries(String queryStr) {
		List<SearchQuery> latestQueries = new ArrayList<SearchQuery>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Key searchKey = KeyFactory.createKey("SearchQuery", "query");
		Entity entity = new Entity("SearchQuery", searchKey);
		entity.setProperty("queryStr", queryStr);
		entity.setProperty("date", new Date());
		datastore.put(entity);

		Query query = new Query("SearchQuery", searchKey).addSort("date",
				Query.SortDirection.DESCENDING);
		List<Entity> results = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(100));

		for (int i = 0; i < results.size(); i++) {
			log.info("Query: " + results.get(i).getProperty("queryStr")
					+ "Date: " + results.get(i).getProperty("time"));
			if (i < 10) {
				latestQueries.add(new SearchQuery(String.valueOf(results.get(i)
						.getProperty("queryStr")), (Date) results.get(i)
						.getProperty("date")));
			} else {
				// Remove the stale entries from the database.
				datastore.delete(results.get(i).getKey());
			}
		}

		return latestQueries;
	}

}
