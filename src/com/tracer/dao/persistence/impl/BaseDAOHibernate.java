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
package com.tracer.dao.persistence.impl;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.tracer.common.QueryInfo;
import com.tracer.dao.model.BaseObject;
import com.tracer.dao.persistence.DAO;

import java.util.List;


/**
 * <p>This class serves as the Base class for all other DAOs - namely to hold
 * common methods that they might all use.</p>
 * <p><a href="BaseDAOHibernate.java.html"><i>View Source</i></a></p>
 *
 */
public class BaseDAOHibernate extends HibernateDaoSupport implements DAO {

	protected transient Log log = LogFactory.getLog(BaseDAOHibernate.class);
	   
	/**
	 * @see com.blandware.atleap.persistence.core.DAO#removeFromCache(com.blandware.atleap.model.core.BaseObject)
	 */
	public void removeFromCache(BaseObject object) {
		log.info("Starting of the removeFromCache method of BaseDAOHibernate");
		if (object != null)
		    getHibernateTemplate().evict(object);
		log.info("End of the removeFromCache method of BaseDAOHibernate");
		}

	/**
	 * @see com.blandware.atleap.persistence.core.DAO#reload(Object)
	 */
	public void reload(Object object) {
		log.info("Starting of the reload method of BaseDAOHibernate");
		getHibernateTemplate().refresh(object);
		log.info("End of the reload method of BaseDAOHibernate");
		}

	/**
	 * Executes find using <code>org.springframework.orm.hibernate3.HibernateCallback</code>
     * with no additional query info, args, cache region; the query is not cacheable
     *
	 * @param hql         Query to execute
	 * @return List of entities
	 * @see #executeFind(String, com.blandware.atleap.common.util.QueryInfo, Object[], org.hibernate.type.Type[], boolean, String)
	 * @see org.springframework.orm.hibernate3.HibernateCallback
	 */
	@SuppressWarnings("rawtypes")
	protected List executeFind(String hql) {
		log.info("Starting of the executeFind method of BaseDAOHibernate");
		log.info("End of the executeFind method of BaseDAOHibernate");
		return executeFind(hql, null, null, null, false, null);
	}


	/**
	 * Executes find using <code>org.springframework.orm.hibernate3.HibernateCallback</code>
     * with no additional query info and args
	 *
     * @param hql         Query to execute
	 * @param cacheable   <code>true</code> if the query is cacheable
	 * @param cacheRegion region of cache. E.g. one that used in configuration file of EHCahce (ehcache.xml)
	 * @return List of entities
	 * @see #executeFind(String, com.blandware.atleap.common.util.QueryInfo, Object[], org.hibernate.type.Type[], boolean, String)
	 * @see org.springframework.orm.hibernate3.HibernateCallback
	 */
	@SuppressWarnings("rawtypes")
	protected List executeFind(String hql, boolean cacheable, String cacheRegion) {
		log.info("Starting of the executeFind method of BaseDAOHibernate");
		log.info("End of the executeFind method of BaseDAOHibernate");
		return executeFind(hql, null, null, null, cacheable, cacheRegion);
	}

	/**
	 * Executes find using <code>org.springframework.orm.hibernate3.HibernateCallback</code>
     * with no additional query info; the query is not cacheable (so no cache
     * region is supplied); with args, but not their types
	 *
     * @param hql         Query to execute
	 * @param args        Arguments to add to query. If <code>null</code>, nothing will be added.
	 * @return List of entities
	 * @see #executeFind(String, com.blandware.atleap.common.util.QueryInfo, Object[], org.hibernate.type.Type[], boolean, String)
	 * @see org.springframework.orm.hibernate3.HibernateCallback
	 */
	@SuppressWarnings("rawtypes")
	protected List executeFind(String hql, Object[] args) {
		log.info("Starting of the executeFind method of BaseDAOHibernate");
		log.info("End of the executeFind method of BaseDAOHibernate");
		return executeFind(hql, null, args, null, false, null);
	}

	/**
	 * Executes find using <code>org.springframework.orm.hibernate3.HibernateCallback</code>
     * with no additional query info; the query is not cacheable (so no cache
     * region is supplied); with args and their types
	 *
     * @param hql         Query to execute
	 * @param args        Arguments to add to query. If <code>null</code>, nothing will be added.
	 * @param types       Types of arguments. If <code>null</code>, Hibernate will determine types by itself.
	 * @return List of entities
	 * @see #executeFind(String, com.blandware.atleap.common.util.QueryInfo, Object[], org.hibernate.type.Type[], boolean, String)
	 * @see org.springframework.orm.hibernate3.HibernateCallback
	 */
	@SuppressWarnings("rawtypes")
	protected List executeFind(String hql, Object[] args, Type[] types) {
		log.info("Starting of the executeFind method of BaseDAOHibernate");
		log.info("End of the executeFind method of BaseDAOHibernate");
		return executeFind(hql, null, args, types, false, null);
	}

	/**
	 * Executes find using <code>org.springframework.orm.hibernate3.HibernateCallback</code>
     * with no additional query info; with args but not their types
	 *
     * @param hql         Query to execute
	 * @param args        Arguments to add to query. If <code>null</code>, nothing will be added.
	 * @param cacheable   <code>true</code> if the query is cacheable
	 * @param cacheRegion region of cache. E.g. one that used in configuration file of EHCahce (ehcache.xml)
	 * @return List of entities
	 * @see #executeFind(String, com.blandware.atleap.common.util.QueryInfo, Object[], org.hibernate.type.Type[], boolean, String)
	 * @see org.springframework.orm.hibernate3.HibernateCallback
	 */
	@SuppressWarnings("rawtypes")
	protected List executeFind(String hql, Object[] args, boolean cacheable, String cacheRegion) {
		log.info("Starting of the executeFind method of BaseDAOHibernate");
		log.info("End of the executeFind method of BaseDAOHibernate");
		return executeFind(hql, null, args, null, cacheable, cacheRegion);
	}

	/**
	 * Executes find using <code>org.springframework.orm.hibernate3.HibernateCallback</code>
     * with no additional query info; with arguments and their types
	 *
     * @param hql         Query to execute
	 * @param args        Arguments to add to query. If <code>null</code>, nothing will be added.
	 * @param types       Types of arguments. If <code>null</code>, Hibernate will determine types by itself.
	 * @param cacheable   <code>true</code> if the query is cacheable
	 * @param cacheRegion region of cache. E.g. one that used in configuration file of EHCahce (ehcache.xml)
	 * @return List of entities
	 * @see #executeFind(String, com.blandware.atleap.common.util.QueryInfo, Object[], org.hibernate.type.Type[], boolean, String)
	 * @see org.springframework.orm.hibernate3.HibernateCallback
	 */
	@SuppressWarnings("rawtypes")
	protected List executeFind(String hql, Object[] args, Type[] types, boolean cacheable, String cacheRegion) {
		log.info("Starting of the executeFind method of BaseDAOHibernate");
		log.info("End of the executeFind method of BaseDAOHibernate");
		return executeFind(hql, null, args, types, cacheable, cacheRegion);
	}

	/**
	 * Executes find using <code>org.springframework.orm.hibernate3.HibernateCallback</code>
     * with additional query info, but without any arguments; the query is not
     * cacheable
	 *
     * @param hql         Query to execute
	 * @param queryInfo   Object with additional information for query, currently offset and limit
	 * @return List of entities
	 * @see #executeFind(String, com.blandware.atleap.common.util.QueryInfo, Object[], org.hibernate.type.Type[], boolean, String)
	 * @see org.springframework.orm.hibernate3.HibernateCallback
     * @see com.blandware.atleap.common.util.QueryInfo
	 */
	@SuppressWarnings("rawtypes")
	protected List executeFind(String hql, QueryInfo queryInfo) {
		log.info("Starting of the executeFind method of BaseDAOHibernate");
		log.info("End of the executeFind method of BaseDAOHibernate");
		return executeFind(hql, queryInfo, null, null, false, null);
	}

	/**
	 * Executes find using <code>org.springframework.orm.hibernate3.HibernateCallback</code>
     * with additional query info; with arguments but without their types; the
     * query is not cacheable
	 *
     * @param hql         Query to execute
	 * @param queryInfo   Object with additional information for query, currently offset and limit
	 * @param args        Arguments to add to query. If <code>null</code>, nothing will be added.
	 * @return List of entities
	 * @see #executeFind(String, com.blandware.atleap.common.util.QueryInfo, Object[], org.hibernate.type.Type[], boolean, String)
	 * @see org.springframework.orm.hibernate3.HibernateCallback
     * @see com.blandware.atleap.common.util.QueryInfo
	 */
	@SuppressWarnings("rawtypes")
	protected List executeFind(String hql, QueryInfo queryInfo, Object[] args) {
		log.info("Starting of the executeFind method of BaseDAOHibernate");
		log.info("End of the executeFind method of BaseDAOHibernate");
		return executeFind(hql, queryInfo, args, null, false, null);
	}

	/**
	 * Executes find using <code>org.springframework.orm.hibernate3.HibernateCallback</code>
     * with additional query info; with arguments and their types; the query is
     * not cacheable
	 *
     * @param hql         Query to execute
	 * @param queryInfo   Object with additional information for query, currently offset and limit
	 * @param args        Arguments to add to query. If <code>null</code>, nothing will be added.
	 * @param types       Types of arguments. If <code>null</code>, Hibernate will determine types by itself.
	 * @return List of entities
	 * @see #executeFind(String, com.blandware.atleap.common.util.QueryInfo, Object[], org.hibernate.type.Type[], boolean, String)
	 * @see org.springframework.orm.hibernate3.HibernateCallback
     * @see com.blandware.atleap.common.util.QueryInfo
	 */
	@SuppressWarnings("rawtypes")
	protected List executeFind(String hql, QueryInfo queryInfo, Object[] args, Type[] types) {
		log.info("Starting of the executeFind method of BaseDAOHibernate");
		log.info("End of the executeFind method of BaseDAOHibernate");
		return executeFind(hql, queryInfo, args, types, false, null);
	}

    /**
     * Executes find using <code>org.springframework.orm.hibernate3.HibernateCallback</code>
     * with additional query info; with arguments but without their types
     *
     * @param hql         Query to execute
	 * @param queryInfo   Object with additional information for query, currently offset and limit
	 * @param args        Arguments to add to query. If <code>null</code>, nothing will be added.
	 * @param cacheable   <code>true</code> if the query is cacheable
	 * @param cacheRegion region of cache. E.g. one that used in configuration file of EHCahce (ehcache.xml)
	 * @return List of entities
     * @see #executeFind(String, com.blandware.atleap.common.util.QueryInfo, Object[], org.hibernate.type.Type[], boolean, String)
     * @see org.springframework.orm.hibernate3.HibernateCallback
     * @see com.blandware.atleap.common.util.QueryInfo
     */
    @SuppressWarnings("rawtypes")
	protected List executeFind(String hql, QueryInfo queryInfo, Object[] args, boolean cacheable, String cacheRegion) {
    	log.info("Starting of the executeFind method of BaseDAOHibernate");
    	log.info("End of the executeFind method of BaseDAOHibernate");
		return executeFind(hql, queryInfo, args, null, cacheable, cacheRegion);
    }

	/**
	 * Executes find using <code>org.springframework.orm.hibernate3.HibernateCallback</code>
     * with additional query info; with arguments and their types
	 *
	 * @param hql         Query to execute
	 * @param queryInfo   Object with additional information for query, currently offset and limit
	 * @param args        Arguments to add to query. If <code>null</code>, nothing will be added.
	 * @param types       Types of arguments. If <code>null</code>, Hibernate will determine types by itself.
	 * @param cacheable   <code>true</code> if the query is cacheable
	 * @param cacheRegion region of cache. E.g. one that used in configuration file of EHCahce (ehcache.xml)
	 * @return List of found entities
	 * @see org.springframework.orm.hibernate3.HibernateCallback
     * @see com.blandware.atleap.common.util.QueryInfo
	 */
	@SuppressWarnings("rawtypes")
	protected List executeFind(final String hql, final QueryInfo queryInfo, final Object[] args, final Type[] types, final boolean cacheable, final String cacheRegion) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				query.setCacheable(cacheable);
				if ( cacheRegion != null ) {
					query.setCacheRegion(cacheRegion);
				}
				if ( args != null ) {
					for ( int i = 0; i < args.length; i++ ) {
						Object arg = args[i];
						Type type = null;
						if ( types != null && i < types.length ) {
							type = types[i];
						}
						if ( type == null ) {
							query.setParameter(i, arg);
						} else {
							query.setParameter(i, arg, type);
						}
					}
				}
				if ( queryInfo != null ) {
					if ( queryInfo.getLimit() != null ) {
						query.setMaxResults(queryInfo.getLimit().intValue());
					}
					if ( queryInfo.getOffset() != null ) {
						query.setFirstResult(queryInfo.getOffset().intValue());
					}
				}
				return query.list();
			}
		});
	}


	/**
	 * Returns unique result matching specified query using <code>org.springframework.orm.hibernate3.HibernateCallback</code>
     * with no arguments; the query is not cacheable
	 *
     * @param hql         Query to execute
	 * @return Unique result matching specified query
	 * @see #findUniqueResult(String, Object[], org.hibernate.type.Type[], boolean, String)
	 * @see org.springframework.orm.hibernate3.HibernateCallback
	 */
	public Object findUniqueResult(String hql) {
		log.info("Starting of the findUniqueResult method of BaseDAOHibernate");
		log.info("End of the findUniqueResult method of BaseDAOHibernate");
		return findUniqueResult(hql, null, null, false, null);
	}

	/**
	 * Returns unique result matching specified query using <code>org.springframework.orm.hibernate3.HibernateCallback</code>
     * with no arguments
	 *
     * @param hql         Query to execute
	 * @param cacheable   <code>true</code> if the query is cacheable
	 * @param cacheRegion region of cache. E.g. one that used in configuration file of EHCahce (ehcache.xml)
	 * @return Unique result matching specified query
	 * @see #findUniqueResult(String, Object[], org.hibernate.type.Type[], boolean, String)
	 * @see org.springframework.orm.hibernate3.HibernateCallback
	 */
	public Object findUniqueResult(String hql, boolean cacheable, String cacheRegion) {
		log.info("Starting of the findUniqueResult method of BaseDAOHibernate");
		log.info("End of the findUniqueResult method of BaseDAOHibernate");
		return findUniqueResult(hql, null, null, cacheable, cacheRegion);
	}

	/**
	 * Returns unique result matching specified query using <code>org.springframework.orm.hibernate3.HibernateCallback</code>
     * with arguments but without their types; the query is not cacheable
	 *
     * @param hql         Query to execute
	 * @param args        Arguments to set
	 * @return Unique result matching specified query
	 * @see #findUniqueResult(String, Object[], org.hibernate.type.Type[], boolean, String)
	 * @see org.springframework.orm.hibernate3.HibernateCallback
	 */
	public Object findUniqueResult(String hql, Object[] args) {
		log.info("Starting of the findUniqueResult method of BaseDAOHibernate");
		log.info("End of the findUniqueResult method of BaseDAOHibernate");
		return findUniqueResult(hql, args, null, false, null);
	}

	/**
	 * Returns unique result matching specified query using <code>org.springframework.orm.hibernate3.HibernateCallback</code>
     * with arguments but without their types
	 *
     * @param hql         Query to execute
	 * @param args        Arguments to set
	 * @param cacheable   <code>true</code> if the query is cacheable
	 * @param cacheRegion region of cache. E.g. one that used in configuration file of EHCahce (ehcache.xml)
	 * @return Unique result matching specified query
	 * @see #findUniqueResult(String, Object[], org.hibernate.type.Type[], boolean, String)
	 * @see org.springframework.orm.hibernate3.HibernateCallback
	 */
	public Object findUniqueResult(String hql, Object[] args, boolean cacheable, String cacheRegion) {
		log.info("Starting of the findUniqueResult method of BaseDAOHibernate");
		log.info("End of the findUniqueResult method of BaseDAOHibernate");
		return findUniqueResult(hql, args, null, cacheable, cacheRegion);
	}

	/**
	 * Returns unique result matching specified query using <code>org.springframework.orm.hibernate3.HibernateCallback</code>
     * with arguments and their types
	 *
     * @param hql         Query to execute
	 * @param args        Arguments to set
	 * @param types       Types of arguments
	 * @return Unique result matching specified query
	 * @see #findUniqueResult(String, Object[], org.hibernate.type.Type[], boolean, String)
	 * @see org.springframework.orm.hibernate3.HibernateCallback
	 */
	public Object findUniqueResult(String hql, Object[] args, Type[] types) {
		log.info("Starting of the findUniqueResult method of BaseDAOHibernate");
		log.info("End of the findUniqueResult method of BaseDAOHibernate");
		return findUniqueResult(hql, args, types, false, null);
	}

	/**
	 * Returns unique result matching specified query using <code>org.springframework.orm.hibernate3.HibernateCallback</code>
     * with arguments and their types
	 *
	 * @param hql         Query to execute
	 * @param args        Arguments to set
	 * @param types       Types of arguments
	 * @param cacheable   <code>true</code> if the query is cacheable
	 * @param cacheRegion region of cache. E.g. one that used in configuration file of EHCahce (ehcache.xml)
	 * @return Unique result matching specified query
	 */
	public Object findUniqueResult(final String hql, final Object[] args, final Type[] types, final boolean cacheable, final String cacheRegion) {
				return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				query.setCacheable(cacheable);
				if ( cacheRegion != null ) {
					query.setCacheRegion(cacheRegion);
				}
				if ( args != null ) {
					for ( int i = 0; i < args.length; i++ ) {
						Object arg = args[i];
						Type type = null;
						if ( types != null && i < types.length ) {
							type = types[i];
						}
						if ( type == null ) {
							query.setParameter(i, arg);
						} else {
							query.setParameter(i, arg, type);
						}
					}
				}
				return query.uniqueResult();
			}
		});
	}

    /**
     * <p>Execute some update/delete HQL<p>
     * <p>CAUTION!!! Be careful it will work only with ast parser (not with org.hibernate.hql.classic.ClassicQueryTranslatorFactory)<p>
     *
     * @param hql         Query to execute
     */
    public void executeUpdate(final String hql) {
    	log.info("Starting of the executeUpdate method of BaseDAOHibernate");
		executeUpdate(hql, null, null);
		log.info("End of the executeUpdate method of BaseDAOHibernate");
		}

    /**
     * <p>Execute some update/delete HQL</p>
     * <p>CAUTION!!! Be careful it will work only with ast parser (not with org.hibernate.hql.classic.ClassicQueryTranslatorFactory)</p>
     *
     * @param hql         Query to execute
     * @param args        Arguments to set
     */
    public void executeUpdate(final String hql, final Object[] args) {
    	log.info("Starting of the executeUpdate method of BaseDAOHibernate");
		 executeUpdate(hql, args, null);
		 log.info("End of the executeUpdate method of BaseDAOHibernate");
			}

    /**
     * <p>Execute some update/delete HQL</p>
     * <p>CAUTION!!! Be careful it will work only with ast parser (not with org.hibernate.hql.classic.ClassicQueryTranslatorFactory)</p>
     *
     * @param hql         Query to execute
     * @param args        Arguments to set
     * @param types       Types of arguments
     */
    public void executeUpdate(final String hql, final Object[] args, final Type[] types) {
        getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                if ( args != null ) {
                    for ( int i = 0; i < args.length; i++ ) {
                        Object arg = args[i];
                        Type type = null;
                        if ( types != null && i < types.length ) {
                            type = types[i];
                        }
                        if ( type == null ) {
                            query.setParameter(i, arg);
                        } else {
                            query.setParameter(i, arg, type);
                        }
                    }
                }
                query.executeUpdate();
                return null;
            }
        });
    }
}