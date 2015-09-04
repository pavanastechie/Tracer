/*
 *  Copyright 2004 Blandware (http://www.blandware.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.tracer.common;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>This class contains information to add to some query
 * </p>
 *
 */
public class QueryInfo implements Serializable {

	private static final long serialVersionUID = 6419225225469707789L;

	/**
	 * WHERE clause without 'WHERE' keyword
	 */
	private String whereClause = new String();

	/**
	 * ORDER BY clause without 'ORDER BY' keyword
	 */
	private String orderByClause = new String();

	/**
	 * Number of records to include in result set
	 */
	private Integer limit;

	/**
	 * Value to start counting records from
	 */
	private Integer offset;

	/**
	 * Map of queryParameters to put in query
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map queryParameters = Collections.synchronizedMap(new HashMap());

	/**
	 * Creates new instance of QueryInfo
	 */
	public QueryInfo() {
	}

	/**
	 * Creates new instance of QueryInfo
	 *
	 * @param whereClause   WHERE clause without 'WHERE' keyword
	 * @param orderByClause ORDER BY clause without 'ORDER BY' keyword
	 * @param limit         Number of records to include in result set
	 * @param offset        Value to start counting records from
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public QueryInfo(String whereClause, String orderByClause, Integer limit, Integer offset) {
		this.whereClause = whereClause;
		this.orderByClause = orderByClause;
		this.limit = limit;
		this.offset = offset;
		queryParameters = Collections.synchronizedMap(new HashMap());
	}

    /**
     * Gets the WHERE clause
     *
     * @return the WHERE clause
     */
	public String getWhereClause() {
		return whereClause;
	}

    /**
     * Sets the WHERE clause
     *
     * @param whereClause the WHERE clause to set
     */
	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

    /**
     * Gets the ORDER BY clause
     *
     * @return the ORDER BY clause
     */
	public String getOrderByClause() {
		return orderByClause;
	}

    /**
     * Sets the ORDER BY clause
     *
     * @param orderByClause the ORDER BY clause to set
     */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

    /**
     * Gets the limit (the maximum number of returned objects)
     *
     * @return the limit
     */
	public Integer getLimit() {
		return limit;
	}

    /**
     * Sets the limit (the maximum number of returned objects)
     *
     * @param limit the limit to set
     */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

    /**
     * Gets offset (the number of first object to return)
     *
     * @return the offset
     */
	public Integer getOffset() {
		return offset;
	}

    /**
     * Sets offset (the number of first object to return)
     *
     * @param offset the offset to set
     */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

    /**
     * Sets parameters that will be added to query
     *
     * @return the query parameters
     */
	@SuppressWarnings("rawtypes")
	public Map getQueryParameters() {
		return queryParameters;
	}

    /**
     * Gets parameters that will be added to query
     *
     * @param queryParameters the query parameters to set
     */
	@SuppressWarnings("rawtypes")
	public void setQueryParameters(Map queryParameters) {
		this.queryParameters = queryParameters;
	}

}
