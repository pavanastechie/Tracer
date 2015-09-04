/**
 * @author Jp
 *
 */
package com.tracer.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracer.dao.persistence.DataEntryDAO;
import com.tracer.service.DataEntryManager;

public class DataEntryManagerImpl implements DataEntryManager {
  protected transient final Log log = LogFactory.getLog(DataEntryManagerImpl.class);
  protected DataEntryDAO dataEntryDAO = null;
  
  //========================================================================
  
  public void setDataEntryDAO(DataEntryDAO dataEntryDAO) {
    
    try {
      this.dataEntryDAO = dataEntryDAO;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //========================================================================
  

}