/**
 * @author Dileepkumar
 */
package com.tracer.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracer.dao.model.UploadedCafDetails;
import com.tracer.dao.persistence.UploadDAO;
import com.tracer.service.UploadManager;

public class UploadManagerImpl implements UploadManager {
  protected transient final Log log = LogFactory.getLog(UploadManagerImpl.class);
  
  protected UploadDAO uploadDAO = null;
  
  //========================================================================
  
  public void setUploadDAO(UploadDAO uploadDAO) {
    
    try {
      this.uploadDAO = uploadDAO;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //========================================================================

  @Override
  public List<UploadedCafDetails> validateUploadedCAFs(List<UploadedCafDetails> uploadedCAFs) throws Exception {
    return uploadDAO.validateUploadedCAFs(uploadedCAFs);
  }

  //========================================================================
  
  @Override
  public List<UploadedCafDetails> saveUploadedCAFs(List<UploadedCafDetails> uploadedCAFs) throws Exception {
    return uploadDAO.saveUploadedCAFs(uploadedCAFs);
  }

  //========================================================================
  
  @Override
  public List<UploadedCafDetails> getUploadedCAFs(String date) throws Exception {
    return uploadDAO.getUploadedCAFs(date);
  }

  //========================================================================
  
  @Override
  public boolean areCAFsUploadedToday() throws Exception {
    return uploadDAO.areCAFsUploadedToday();
  }

  //========================================================================
}
