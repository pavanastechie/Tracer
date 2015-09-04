/**
 * @author Dileepkumar
 */
package com.tracer.dao.persistence.impl;

import static com.tracer.common.Constants.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracer.dao.model.DistributorDetails;
import com.tracer.dao.model.UploadedCafDetails;
import com.tracer.dao.persistence.UploadDAO;
import com.tracer.util.StringUtil;

public class UploadDAOHibernate extends BaseDAOHibernate implements UploadDAO {

  protected transient final Log log = LogFactory.getLog(UploadDAOHibernate.class);

  //========================================================================
   
  @SuppressWarnings("unchecked")
  @Override
  public List<UploadedCafDetails> validateUploadedCAFs(List<UploadedCafDetails> uploadedCAFs) throws Exception {
    log.info("START of a method validateUploadedCAFs");
    List<UploadedCafDetails> validatedUploadedCAFs = null;
    StringBuffer hqlQuery = null;
    List<DistributorDetails> distributors = null;
    DistributorDetails distributorDetails = null;
    
    try {
      if(uploadedCAFs != null && uploadedCAFs.size() > 0) {
        validatedUploadedCAFs = new ArrayList<UploadedCafDetails>();
        
        for(UploadedCafDetails uploadedCafDetails : uploadedCAFs) {
          //select * from distributor_details dd where dd.distributor_name like 'Sri PALLAVI Marketing%' and dd.ofsc_code='HYD027' 
          hqlQuery = new StringBuffer(" SELECT dd from DistributorDetails as dd where dd.distributorName like '"+uploadedCafDetails.getDistributorName()+"%'");
          hqlQuery.append(" and dd.ofscCode = '"+uploadedCafDetails.getOfscCode()+"'");
          distributors = getHibernateTemplate().find(hqlQuery.toString());
          
          if(distributors != null && distributors.size() > 0) {
            distributorDetails = distributors.get(0);
            
            if(IN_ACTIVE == distributorDetails.getStatus()) {
              uploadedCafDetails.setCafStatus(DISTRIBUTOR_IN_ACTIVE); 
            } else {
              uploadedCafDetails.setCafStatus(NEW_CAF); 	
            }
            uploadedCafDetails.setDistributorDetails(distributorDetails);
          } else {
            uploadedCafDetails.setCafStatus(DISTRIBUTOR_NOT_FOUND);  
          }
          validatedUploadedCAFs.add(uploadedCafDetails);
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method validateUploadedCAFs");
      e.printStackTrace();
    }
    log.info("END of a method validateUploadedCAFs");
    return validatedUploadedCAFs;
  }

  //========================================================================
  
  @Override
  public List<UploadedCafDetails> saveUploadedCAFs(List<UploadedCafDetails> uploadedCAFs) throws Exception {
    log.info("START of a method saveUploadedCAFs");
    DateFormat dbDateFormat = new SimpleDateFormat(DATE_FORMAT_DB);
    String date = null;
    List<UploadedCafDetails> todaysUploadedCAFs = null;

    try {
      if(uploadedCAFs != null && uploadedCAFs.size() > 0) {
        for(UploadedCafDetails uploadedCafDetails : uploadedCAFs) {
          if(uploadedCafDetails != null) {
            getHibernateTemplate().save(uploadedCafDetails);
          }
        }
      }
      date = dbDateFormat.format(new Date());
      todaysUploadedCAFs = getUploadedCAFs(date);
    } catch (Exception e) {
      log.error("PROBLEM in the method saveUploadedCAFs");
      e.printStackTrace();
    }
    log.info("END of a method saveUploadedCAFs");
    return todaysUploadedCAFs;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<UploadedCafDetails> getUploadedCAFs(String date) throws Exception {
    log.info("START of a method getUploadedCAFs");
    List<UploadedCafDetails> resultList = null;
    StringBuffer hqlQuery = null;
    
    try {
      if(StringUtil.isNotNull(date)) {
        hqlQuery = new StringBuffer("SELECT ucd FROM UploadedCafDetails as ucd WHERE ");
        hqlQuery.append(" cast(ucd.dateTime as date) = '"+date+"'"); 
        hqlQuery.append(" order by ucd.dateTime");
        resultList = getHibernateTemplate().find(hqlQuery.toString());
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getUploadedCAFs");
      e.printStackTrace();
    }
    log.info("END of a method getUploadedCAFs");
    return resultList;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public boolean areCAFsUploadedToday() throws Exception {
    log.info("START of a method areCAFsUploadedToday");
    List<UploadedCafDetails> resultList = null;
    StringBuffer hqlQuery = null;
    boolean acreCAFsUploadedToday = false;
    
    try {
      hqlQuery = new StringBuffer("SELECT ucd FROM UploadedCafDetails as ucd WHERE ");
      hqlQuery.append(" cast(ucd.dateTime as date) = DATE(NOW()) "); 
      resultList = getHibernateTemplate().find(hqlQuery.toString());
      
      if(resultList != null && resultList.size() > 0) {
        acreCAFsUploadedToday = true;
      } else {
        acreCAFsUploadedToday = false;
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method areCAFsUploadedToday");
      e.printStackTrace();
    }
    log.info("END of a method areCAFsUploadedToday");
    return acreCAFsUploadedToday;
  }
  
  //========================================================================
}
