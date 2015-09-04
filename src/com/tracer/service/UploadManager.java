/**
 * @author Dileepkumar
 */
package com.tracer.service;

import java.util.List;

import com.tracer.dao.model.UploadedCafDetails;

public interface UploadManager {
  
  public List<UploadedCafDetails> validateUploadedCAFs(List<UploadedCafDetails> uploadedCAFs) throws Exception;
  
  public List<UploadedCafDetails> saveUploadedCAFs(List<UploadedCafDetails> uploadedCAFs) throws Exception;
  
  public List<UploadedCafDetails> getUploadedCAFs(String date) throws Exception;
  
  public boolean areCAFsUploadedToday() throws Exception;
}
