/**
 * @author Jp
 *
 */
package com.tracer.listener;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Method;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.ByteConverter;
import org.apache.commons.beanutils.converters.CharacterConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import static com.tracer.common.Constants.*;

import com.tracer.common.Constants;

public class StartupListener extends ContextLoaderListener implements ServletContextListener {
  
  public static final String DEFAULT_SESSION_FACTORY_BEAN_NAME = "sessionFactory";
  protected String sessionFactoryBeanName = DEFAULT_SESSION_FACTORY_BEAN_NAME;
  protected transient Log log = LogFactory.getLog(StartupListener.class);
  protected ServletContext servletContext = null;
  protected ApplicationContext applicationContext = null;
  protected SessionFactory sessionFactory = null;
  protected String contextPath = null;
  ResourceBundle resourceBundle = null;
  
  //========================================================================
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public void contextInitialized(ServletContextEvent event) {
    log.info("Starting of the contextInitialized method of StartupListener");
    
    if (log.isInfoEnabled()) {
      log.info("Initializing context...");
    }
    super.contextInitialized(event);
    servletContext = event.getServletContext();
    contextPath = servletContext.getInitParameter(Constants.CONTEXT_PATH);

    if (contextPath == null) {
      if (log.isWarnEnabled()) {
        log.warn("No 'contextPath' context parameter, using '/'");
      }
      contextPath = "/";
    } else if (!contextPath.startsWith("/")) {
      contextPath = "/" + contextPath;
    }
    String daoType = servletContext.getInitParameter(Constants.DAO_TYPE);

    if ( daoType == null ) {
      if ( log.isWarnEnabled() ) {
        log.warn("No 'daoType' context parameter, using Hibernate");
      }
      daoType = Constants.DAO_TYPE_HIBERNATE;
    }
    Map config = (HashMap) servletContext.getAttribute(Constants.CONFIG);

    if (config == null) {
      config = new HashMap();
    }
    config.put(Constants.DAO_TYPE, daoType);
    servletContext.setAttribute(Constants.CONFIG, config);

    if ( log.isDebugEnabled() ) {
      log.debug("daoType: " + daoType);
    }
    
    resourceBundle = ResourceBundle.getBundle("ApplicationResources");
    
    if(resourceBundle != null) {
      TRACER_RWS_URL = resourceBundle.getString("tracer.rws.url");
      APP_DATA_PATH = resourceBundle.getString("tracer.app.data.location");
      UPLOAD_PHOTOS_PATH = resourceBundle.getString("tracer.user.photos.location");
      UPLOAD_FILES_PATH = resourceBundle.getString("tracer.user.files.location");
      UPLOAD_PHOTOS_PATH = APP_DATA_PATH.concat("/").concat(UPLOAD_PHOTOS_PATH);
      UPLOAD_FILES_PATH = APP_DATA_PATH.concat("/").concat(UPLOAD_FILES_PATH);
      
      SMS_API_STATUS = resourceBundle.getString("tracer.sms.api.status");
    }

    sessionFactory = lookupSessionFactory();
    Session session = null;
    boolean participate = false;
    
    if ( TransactionSynchronizationManager.hasResource(sessionFactory) ) {
      participate = true;
    } else {
      if (log.isDebugEnabled()) {
        log.debug("Opening temporary Hibernate session in StartupListener");
      }
      session = getSession(sessionFactory);
      TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
    }
    applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    
    if (!participate) {
      TransactionSynchronizationManager.unbindResource(sessionFactory);
      if (log.isDebugEnabled()) {
        log.debug("Closing temporary Hibernate session in StartupListener");
      }
      closeSession(session, sessionFactory);
    }
    registerConverters();
    createUploadFolders();
    log.info("End of the contextInitialized method of StartupListener");
  }
  
  //========================================================================
  
  protected SessionFactory lookupSessionFactory() {
    log.info("Starting of the lookupSessionFactory method of StartupListener");
    if (log.isDebugEnabled()) {
      log.debug("Using session factory '" + getSessionFactoryBeanName()+ "' for StartupListener");
    }
    WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    log.info("End of the lookupSessionFactory method of StartupListener");
    return (SessionFactory) wac.getBean(getSessionFactoryBeanName());
  }

  // ========================================================================

  protected String getSessionFactoryBeanName() {
    log.info("Starting of the getSessionFactoryBeanName method of StartupListener");
    log.info("End of the getSessionFactoryBeanName method of StartupListener");
    return sessionFactoryBeanName;
  }

  // ========================================================================

  protected Session getSession(SessionFactory sessionFactory)
      throws DataAccessResourceFailureException {
    log.info("Starting of the getSession method of StartupListener");
    Session session = SessionFactoryUtils.getSession(sessionFactory, true);
    session.setFlushMode(FlushMode.NEVER);
    log.info("End of the getSession method of StartupListener");
    return session;
  }

  // ========================================================================

  protected void closeSession(Session session, SessionFactory sessionFactory) {
    log.info("Starting of the closeSession method of StartupListener");
    SessionFactoryUtils.releaseSession(session, sessionFactory);
    log.info("End of the closeSession method of StartupListener");
  }
  
  // ========================================================================
  protected void registerConverters() {
    log.info("Starting of the registerConverters method of StartupListener");
    ConvertUtils.register(new BooleanConverter(null), Boolean.TYPE);
    ConvertUtils.register(new BooleanConverter(null), Boolean.class);
    ConvertUtils.register(new ByteConverter(null), Byte.TYPE);
    ConvertUtils.register(new ByteConverter(null), Byte.class);
    ConvertUtils.register(new CharacterConverter(null), Character.TYPE);
    ConvertUtils.register(new CharacterConverter(null), Character.class);
    ConvertUtils.register(new DoubleConverter(null), Double.TYPE);
    ConvertUtils.register(new DoubleConverter(null), Double.class);
    ConvertUtils.register(new FloatConverter(null), Float.TYPE);
    ConvertUtils.register(new FloatConverter(null), Float.class);
    ConvertUtils.register(new IntegerConverter(null), Integer.TYPE);
    ConvertUtils.register(new IntegerConverter(null), Integer.class);
    ConvertUtils.register(new LongConverter(null), Long.TYPE);
    ConvertUtils.register(new LongConverter(null), Long.class);
    ConvertUtils.register(new ShortConverter(null), Short.TYPE);
    ConvertUtils.register(new ShortConverter(null), Short.class);
    log.info("End of the registerConverters method of StartupListener");
  }
  
  // =======================================================================
  
  public void createUploadFolders() {
    log.info("Starting of the createUploadFolders method of StartupListener");

    try {
      File rootUploadPath = new File(APP_DATA_PATH);
      File uploadPhotosPath = new File(UPLOAD_PHOTOS_PATH);
      File uploadFilesPath = new File(UPLOAD_FILES_PATH);

      if (!rootUploadPath.exists()) {
        rootUploadPath.mkdir();
      }

      if (!uploadPhotosPath.exists()) {
        uploadPhotosPath.mkdir();
        log.info("Created folder to store uploaded images, path : "+ uploadPhotosPath);
      }

      if (!uploadFilesPath.exists()) {
        uploadFilesPath.mkdir();
        log.info("Created folder to store uploaded files, path : "+ uploadFilesPath);
      }
    } catch (Exception e) {
      log.error("Problem in the method createUploadFolders");
    }
    log.info("End of the createUploadFolders method of StartupListener");
  }
  
  // ========================================================================
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public void contextDestroyed(ServletContextEvent event) {
    log.info("Starting of the contextDestroyed method of StartupListener");
    ServletContext servletContext = event.getServletContext();

    try {
      if(sessionFactory != null) {
        Session session = SessionFactoryUtils.getSession(sessionFactory, false);
        closeSession(session, sessionFactory);
      }
    } catch (Exception ex) {
      log.error("Exception occured in contextDestroyed", ex);
    }

    try {
      if(sessionFactory != null) {
        sessionFactory.close();
      }
    } catch (Exception ex) {
      log.error("Exception occured in contextDestroyed", ex);
    }
    sessionFactory = null;

    super.contextDestroyed(event);
    ConvertUtils.deregister();
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    if (classLoader == null) {
      classLoader = this.getClass().getClassLoader();
    }

    try {
      Context environment = (Context) new InitialContext().lookup("java:comp/env");
      String dataSourceName = servletContext.getInitParameter(Constants.DATASOURCE_NAME);
      DataSource dataSource = (DataSource) environment.lookup(dataSourceName);
      Method method = dataSource.getClass().getMethod("close", (Class[]) null);
      method.invoke(dataSource, (Object[]) null);
    } catch (Exception ex) {
      log.error("Exception occured in contextDestroyed", ex);
    }

    try {
      for (Enumeration e = DriverManager.getDrivers(); e.hasMoreElements();) {
        Driver driver = (Driver) e.nextElement();
        
        if (driver.getClass().getClassLoader() == classLoader) {
          DriverManager.deregisterDriver(driver);
        }
      }
    } catch (Exception ex) {
      log.error("Exception occured in contextDestroyed", ex);
    }
    servletContext = null;
    applicationContext = null;
    log = null;

    try {
      Class c = classLoader.loadClass("org.apache.commons.logging.LogFactory");
      Method m = c.getMethod("release", new Class[] { ClassLoader.class });
      m.invoke(null, new Object[] { classLoader });
    } catch (Exception ex) {
      log.error("Exception occured in contextDestroyed", ex);
    }

    try {
      Class clazz = classLoader.loadClass("org.apache.log4j.LogManager");
      Method method = clazz.getMethod("shutdown", (Class[]) null);
      method.invoke(null, (Object[]) null);
    } catch (Exception ex) {
      log.error("Exception occured in contextDestroyed", ex);
    }
    Introspector.flushCaches();
    log.info("End of the contextDestroyed method of StartupListener");
  }

  // ========================================================================
}