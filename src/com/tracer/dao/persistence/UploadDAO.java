/**
 * @author Dileepkumar
 */
package com.tracer.dao.persistence;

import java.util.List;

import com.tracer.dao.model.UploadedCafDetails;

public interface UploadDAO {
  
  public List<UploadedCafDetails> validateUploadedCAFs(List<UploadedCafDetails> uploadedCAFs) throws Exception;
  
  public List<UploadedCafDetails> saveUploadedCAFs(List<UploadedCafDetails> uploadedCAFs) throws Exception;
  
  public List<UploadedCafDetails> getUploadedCAFs(String date) throws Exception;
  
  public boolean areCAFsUploadedToday() throws Exception;

}
