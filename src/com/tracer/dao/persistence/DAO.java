package com.tracer.dao.persistence;

import com.tracer.dao.model.BaseObject;

/**
 * <p>Data Access Object (DAO) interface.   This is an interface
 * used to tag our DAO classes and to provide common methods to all DAOs.
 * </p>
 * <p><a href="DAO.java.html"><i>View Source</i></a>
 * </p>
 *
 */
public interface DAO {

	/**
	 * Removes object from cache
	 *
	 * @param object Object to be removed from cache
	 */
	public void removeFromCache(BaseObject object);

	/**
	 * Reloads object from database (synchronizes state)
	 * @param object Object to reload
	 */
	public void reload(Object object);
}