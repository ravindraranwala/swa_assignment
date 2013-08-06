package com.msc.model;

import java.util.Date;

/**
 * This class represents a <code>SearchQuery</code> object.
 * 
 * @author Ravindra
 * 
 */
public class SearchQuery {
	private String queryStr;

	private Date date;

	/**
	 * Constructs a <code>SearchQuery</code> instance with the given queryStr
	 * and date values.
	 * 
	 * @param queryStr
	 *            query string to be stored.
	 * @param date
	 *            date time value in which it is stored.
	 */
	public SearchQuery(String queryStr, Date date) {
		super();
		this.queryStr = queryStr;
		this.date = date;
	}

	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
