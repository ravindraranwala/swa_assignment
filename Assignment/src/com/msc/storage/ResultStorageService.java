package com.msc.storage;

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
import com.google.appengine.api.datastore.Text;
import com.msc.model.SearchResult;

public class ResultStorageService {
	private static final Logger log = Logger
			.getLogger(ResultStorageService.class.getName());

	public static void saveLatestResults(List<SearchResult> searchResults) {
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

		if (results.size() > 10) {
			for (int i = 10; i < results.size(); i++) {
				datastore.delete(results.get(i).getKey());
			}
		}
	}

}
