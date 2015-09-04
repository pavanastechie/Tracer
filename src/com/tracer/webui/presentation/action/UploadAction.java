/**
 * @author Dileepkumar
 */
package com.tracer.webui.presentation.action;

import static com.tracer.common.Constants.*;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tracer.dao.model.DistributorDetails;
import com.tracer.dao.model.UploadedCafDetails;
import com.tracer.service.UploadManager;
import com.tracer.util.StringUtil;
import com.tracer.webui.presentation.form.UploadForm;

public class UploadAction extends BaseDispatchAction {

  protected transient final Log log = LogFactory.getLog(getClass());

  // ========================================================================

  public ActionForward showUploadCAFsPage(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showUploadCAFsPage");
    HttpSession session;
    String pageToForward = ADD_ONLINE_CAFS_PAGE;
    UploadManager uploadManager = null;
    boolean areCAFsUploadedToday = false;
    ActionMessages actionMessages = new ActionMessages();
    
    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        session.removeAttribute(UPLOADED_CAF_LIST);
        uploadManager = (UploadManager) getBean(UPLOAD_MANAGER);
        areCAFsUploadedToday = uploadManager.areCAFsUploadedToday();
        
        if(areCAFsUploadedToday) {
          actionMessages.add("upload.caf.daily.once", new ActionMessage("upload.caf.daily.once")); 
          saveMessages(request, actionMessages);
          pageToForward = INFO_PAGE;
        } else {
          pageToForward = ADD_ONLINE_CAFS_PAGE;
        }
      } else {
        pageToForward = SESSION_INACTIVE_PAGE;
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showUploadCAFsPage");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the ActionForward showUploadCAFsPage");
    return mapping.findForward(pageToForward);
  }

  // ==============================================================================

  @SuppressWarnings("unchecked")
  public ActionForward getCAFsFromExcelTemplate(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getCAFsFromExcelTemplate");
    HttpSession session = null;
    UploadManager uploadManager = null;
    List<UploadedCafDetails> uploadCaflist = null;
    boolean isProperSheet = false;
    String pageToForward = VERIFY_ONLINE_CAFS_PAGE;
    ActionMessages actionMessages = new ActionMessages();
    
    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {

        if (session.getAttribute(UPLOADED_CAF_LIST) != null) {
          uploadCaflist = (List<UploadedCafDetails>) session.getAttribute(UPLOADED_CAF_LIST);
        }
        
        if (uploadCaflist == null) {
          uploadManager = (UploadManager) getBean(UPLOAD_MANAGER);
          boolean areCAFsUploadedToday = uploadManager.areCAFsUploadedToday();
          
          if(areCAFsUploadedToday) {
            actionMessages.add("upload.caf.daily.once", new ActionMessage("upload.caf.daily.once")); 
            saveMessages(request, actionMessages);
            pageToForward = INFO_PAGE;
          } else {
            UploadForm uploadForm = (UploadForm) form;
            FormFile file = uploadForm.getFile();
            List<UploadedCafDetails> resultList = null;
          
            if (file != null) {
              InputStream inputFile = file.getInputStream();
              List<UploadedCafDetails> storeDataList = new ArrayList<UploadedCafDetails>();
              org.apache.poi.ss.usermodel.Sheet sheet = null;
              org.apache.poi.ss.usermodel.Workbook wb_xssf = null;
              String fileExtn = FilenameUtils.getExtension(file.getFileName());
            
              if (fileExtn.equalsIgnoreCase("xlsx") || fileExtn.equalsIgnoreCase("xls")) {
                wb_xssf = WorkbookFactory.create(inputFile);
              }
            
              for(int i = 0; i < 10; i++) {
                if(isProperSheet){
                  break;
                }
                sheet = wb_xssf.getSheetAt(i);
                Iterator<Row> rowIterator = sheet.iterator();
                
                if(rowIterator.hasNext()) {
                  isProperSheet =  isProperSheet(rowIterator.next());
                }
              
                if(isProperSheet) {
                  int recordId = 0;

                  while (rowIterator.hasNext()) {
                    int mandatoryDataFieldsCount = 0;
                    UploadedCafDetails uploadDetails = new UploadedCafDetails();
                    Row row = rowIterator.next();
                   
                    // for retrieving the data not the headings
                    if (row.getRowNum() == 0) {
                      continue;
                    }
                    Cell mobileNo_cell = row.getCell(0);
                    if(mobileNo_cell != null) {
                      mobileNo_cell.setCellType(Cell.CELL_TYPE_STRING);
                      if(StringUtil.isNotNull(mobileNo_cell.toString())) {
                        uploadDetails.setMobileNo(mobileNo_cell.toString());
                        mandatoryDataFieldsCount++;
                      }	
                    }
                    
                    Cell dealerCode_cell = row.getCell(4);
                    if(dealerCode_cell != null) {
                      dealerCode_cell.setCellType(Cell.CELL_TYPE_STRING);
                      if(StringUtil.isNotNull(dealerCode_cell.toString())) {
                        uploadDetails.setOfscCode(dealerCode_cell.toString());
                        mandatoryDataFieldsCount++;
                      }
                    }
                    
                    Cell channelName_cell = row.getCell(5);
                    if(channelName_cell != null) {
                      channelName_cell.setCellType(Cell.CELL_TYPE_STRING);
                      if(StringUtil.isNotNull(channelName_cell.toString())) {
                        uploadDetails.setDistributorName(channelName_cell.toString());
                        mandatoryDataFieldsCount++;
                      }
                    }

                    if(mandatoryDataFieldsCount >= 3) {
                      recordId++;
                      uploadDetails.setRecordId(recordId);
                      Cell orderNo_cell = row.getCell(1);
                      orderNo_cell.setCellType(Cell.CELL_TYPE_STRING);
                      uploadDetails.setOrderNumber(orderNo_cell.toString());

                      Cell subAgentCode_cell = row.getCell(3);
                      subAgentCode_cell.setCellType(Cell.CELL_TYPE_STRING);
                      uploadDetails.setSubAgentCode(subAgentCode_cell.toString());
                    
                      Cell type_cell = row.getCell(6);
                      type_cell.setCellType(Cell.CELL_TYPE_STRING);
                      uploadDetails.setCafType(type_cell.toString());
                     
                      Cell statusCode_cell = row.getCell(8);
                      statusCode_cell.setCellType(Cell.CELL_TYPE_STRING);
                      uploadDetails.setConnectionStatus(statusCode_cell.toString());
                     
                      uploadDetails.setStatus(ACTIVE);
                      uploadDetails.setDateTime(new Date());
                      
                      storeDataList.add(uploadDetails);
                    }
                  } // End of while 
                }
              } // End of for loop
              resultList = uploadManager.validateUploadedCAFs(storeDataList);
              session.setAttribute(UPLOADED_CAF_LIST, resultList);
            }
          }
        }
      } else {
        pageToForward = SESSION_INACTIVE_PAGE;
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward getCAFsFromExcelTemplate");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the ActionForward getCAFsFromExcelTemplate");
    return mapping.findForward(pageToForward);
  }

  // ==============================================================================
  
  private boolean isProperSheet(Row row) {
    boolean isProperSheet = false;
    
    try {
      Cell mobileNoHeading_cell = row.getCell(0);
      mobileNoHeading_cell.setCellType(Cell.CELL_TYPE_STRING);
      String mobileNoHeading = mobileNoHeading_cell.toString();
    
      if(StringUtil.isNotNull(mobileNoHeading)) {
        if("MOBILE_NO".equalsIgnoreCase(mobileNoHeading) || "MOBILE_NUMBER".equalsIgnoreCase(mobileNoHeading)) {
          isProperSheet = true;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return isProperSheet;
  }
  
  //==============================================================================
  
  @SuppressWarnings("unchecked")
  public ActionForward saveUploadedCAFs(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward saveUploadedCAFs");
    UploadManager uploadManager = null;
    HttpSession session = null;
    List<UploadedCafDetails> uploadedCafList = null;
    boolean invalidCafFound = false;
    String pageToForward = VERIFY_ONLINE_CAFS_PAGE;
    int cafStatus = 0;
    ActionMessages actionMessages = new ActionMessages();
    UploadForm uploadForm = null;
    String dateTime = null;
    Date date = null;
    
    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        uploadManager = (UploadManager) getBean(UPLOAD_MANAGER);
        boolean areCAFsUploadedToday = uploadManager.areCAFsUploadedToday();
          
        if(areCAFsUploadedToday) {
          actionMessages.add("upload.caf.daily.once", new ActionMessage("upload.caf.daily.once")); 
          saveMessages(request, actionMessages);
          pageToForward = INFO_PAGE;
        } else {
          uploadedCafList = (List<UploadedCafDetails>) session.getAttribute(UPLOADED_CAF_LIST);
          
          // Check whether there are invalid caf's to upload
          if(uploadedCafList != null && uploadedCafList.size() > 0) {
            for(UploadedCafDetails uploadedCafDetails : uploadedCafList) {
              cafStatus = Integer.parseInt(uploadedCafDetails.getCafStatus());
              
              if(cafStatus == Integer.parseInt(DISTRIBUTOR_NOT_FOUND) || cafStatus == Integer.parseInt(DISTRIBUTOR_IN_ACTIVE)) {
                invalidCafFound = true;
                break;
              }
            }
          }
          
          if(invalidCafFound) {
            actionMessages.add("unable.save.uploaded.caf", new ActionMessage("unable.save.uploaded.caf"));
            saveMessages(request, actionMessages);
          } else {
            uploadManager.saveUploadedCAFs(uploadedCafList);
            session.removeAttribute(UPLOADED_CAF_LIST);
            actionMessages.add("upload.caf.successful", new ActionMessage("upload.caf.successful", uploadedCafList.size()));
            saveMessages(request, actionMessages);
            date = new Date();
            dateTime = new SimpleDateFormat(DATE_FORMAT).format(date);
            uploadForm = (UploadForm) form;
            uploadForm.setDateTime(dateTime);
            pageToForward = VIEW_ONLINE_CAFS_PAGE;
          }  
        }
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward saveUploadedCAFs");
      log.error(e);
    }
    log.info("END of the ActionForward saveUploadedCAFs");
    return mapping.findForward(pageToForward);
  }

  // ==============================================================================

  @SuppressWarnings("unchecked")
  public ActionForward deleteUploadedCAFs(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward deleteUploadedCAFs");
    UploadForm uploadForm = (UploadForm) form;
    HttpSession session = null;
    String pageToForward = VERIFY_ONLINE_CAFS_PAGE;

    try {
      session = request.getSession(false);
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        List<UploadedCafDetails> uploadedCafDetails = (List<UploadedCafDetails>) session.getAttribute(UPLOADED_CAF_LIST);
        int recordId = uploadForm.getRecordId();
        Iterator<UploadedCafDetails> it = uploadedCafDetails.iterator();
        
        while (it.hasNext()) {
          UploadedCafDetails ucd = (UploadedCafDetails) it.next();
          if (ucd.getRecordId() == recordId) {
            it.remove();
            break;
          }
        }
        session.setAttribute(UPLOADED_CAF_LIST, uploadedCafDetails);
      } else {
        pageToForward = SESSION_INACTIVE_PAGE;
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward deleteUploadedCAFs");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the ActionForward deleteUploadedCAFs");
    return mapping.findForward(pageToForward);
  }

  // ==============================================================================
  
  public void validateUploadedCAFs(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward validateUploadedCAFs");
    HttpSession session = null;
    
    try {
      session = request.getSession(false);
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {

      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward validateUploadedCAFs");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the ActionForward validateUploadedCAFs");
  }

  // ==============================================================================
  
  public ActionForward showUploadedCAFs(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showUploadedCAFs");
    HttpSession session = null;
    UploadForm uploadForm = null;
    String dateTime = null;
    Date date = null;
    String pageToForward = VIEW_ONLINE_CAFS_PAGE;
    
    try {
      uploadForm = (UploadForm) form;
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        dateTime = uploadForm.getDateTime();
        
        if(StringUtil.isNull(dateTime)) {
          date = new Date();
          dateTime = new SimpleDateFormat(DATE_FORMAT).format(date);
          uploadForm.setDateTime(dateTime);
        }
      } else {
        pageToForward = SESSION_INACTIVE_PAGE;
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showUploadedCAFs");
      log.error(e);
    }
    log.info("END of the ActionForward showUploadedCAFs");
    return mapping.findForward(pageToForward);
  }

  // ==============================================================================
  
  public void getUploadedCAFs(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getUploadedCAFs");
    UploadForm uploadForm = null;
    String date = null;
    String currentdate = null;
    UploadManager uploadManager = null;
    JSONArray jsonArrayObj = null;
    String cafStatus = NEW_CAF;
    int cafStatusAsInt = 0;
    String cafStatusAsString = "New CAF";
    DistributorDetails distributorDetails = null;

    try {
      uploadForm = (UploadForm) form;
      date = uploadForm.getDateTime();
      jsonArrayObj = new JSONArray();
      
      if (date == null) {
        currentdate = StringUtil.convertUIDateToDBDate(null);
      } else {
        currentdate = StringUtil.convertUIDateToDBDate(date);
      }
      uploadManager = (UploadManager) getBean(UPLOAD_MANAGER);
      List<UploadedCafDetails> uploadedCafDetailsList = uploadManager.getUploadedCAFs(currentdate);

      if (uploadedCafDetailsList != null) {
        for (int i = 0; i < uploadedCafDetailsList.size(); i++) {
          distributorDetails = uploadedCafDetailsList.get(i).getDistributorDetails();
          JSONObject storeJsonObj = new JSONObject();
          cafStatus = uploadedCafDetailsList.get(i).getCafStatus();
          cafStatusAsInt = Integer.parseInt(cafStatus);
          storeJsonObj.put("mobileNo", uploadedCafDetailsList.get(i).getMobileNo());
          storeJsonObj.put("orderNumber", uploadedCafDetailsList.get(i).getOrderNumber());
          storeJsonObj.put("ofscCode", uploadedCafDetailsList.get(i).getOfscCode());
          storeJsonObj.put("distributorName", distributorDetails.getDistributorName());
          storeJsonObj.put("location", distributorDetails.getLocation());
          
          // Need to implement enums
          switch (cafStatusAsInt) {
            case 0: cafStatusAsString = "New CAF";
                    break;
            case 1: cafStatusAsString = "Accepted";
                    break;
            case 2: cafStatusAsString = "Rejected";
                    break;
            case 3: cafStatusAsString = "Customer not interested";
                    break;
            case 4: cafStatusAsString = "Incomplete documentation";
                    break;
            case 5: cafStatusAsString = "Distributor not found";
                    storeJsonObj.put("showDeleteBtn", false);
                    break;
            case 6: cafStatusAsString = "Distributor in-active";
                    storeJsonObj.put("showDeleteBtn", false);
                    break;
            default:break;
          }
          storeJsonObj.put("cafStatusAsString", cafStatusAsString);
          jsonArrayObj.put(storeJsonObj);
        }
        response.setContentType("application/json");
        response.getWriter().print(jsonArrayObj);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward getUploadedCAFs");
      log.error(e);
    }
    log.info("END of the ActionForward getUploadedCAFs");
  }

  // ==============================================================================

  @SuppressWarnings("unchecked")
  public void verifyCAFsUploaded(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    List<UploadedCafDetails> uploadedCafDetailsList = null;
    JSONArray jsonArrayObj = new JSONArray();
    HttpSession session = null;
    String cafStatus = NEW_CAF;
    int cafStatusAsInt = 0;
    String cafStatusAsString = "New CAF";

    try {
      session = request.getSession(false);
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        uploadedCafDetailsList = (List<UploadedCafDetails>) session.getAttribute(UPLOADED_CAF_LIST);

        if(uploadedCafDetailsList != null && uploadedCafDetailsList.size() > 0) {
          for (int i = 0; i < uploadedCafDetailsList.size(); i++) {
            cafStatus = uploadedCafDetailsList.get(i).getCafStatus();
            cafStatusAsInt = Integer.parseInt(cafStatus);
            JSONObject storeJsonObj = new JSONObject();
            storeJsonObj.put("mobileNo", uploadedCafDetailsList.get(i).getMobileNo());
            storeJsonObj.put("orderNumber", uploadedCafDetailsList.get(i).getOrderNumber());
            storeJsonObj.put("dateTime", uploadedCafDetailsList.get(i).getDateTime());
            storeJsonObj.put("subAgentCode", uploadedCafDetailsList.get(i).getSubAgentCode());
            storeJsonObj.put("ofscCode", uploadedCafDetailsList.get(i).getOfscCode());
            storeJsonObj.put("distributorName", uploadedCafDetailsList.get(i).getDistributorName());
            storeJsonObj.put("cafType", uploadedCafDetailsList.get(i).getCafType());
            storeJsonObj.put("connectionStatus", uploadedCafDetailsList.get(i).getConnectionStatus());
            //storeJsonObj.put("status", uploadedCafDetailsList.get(i).getStatus());
            storeJsonObj.put("recordId", uploadedCafDetailsList.get(i).getRecordId());
            storeJsonObj.put("showDeleteBtn", true);

            // Need to implement enums
            switch (cafStatusAsInt) {
              case 0: cafStatusAsString = "New CAF";
                      break;
              case 1: cafStatusAsString = "Accepted";
                      break;
              case 2: cafStatusAsString = "Rejected";
                      break;
              case 3: cafStatusAsString = "Customer not interested";
                      break;
              case 4: cafStatusAsString = "Incomplete documentation";
                      break;
              case 5: cafStatusAsString = "Distributor not found";
                      storeJsonObj.put("showDeleteBtn", false);
                      break;
              case 6: cafStatusAsString = "Distributor in-active";
                      storeJsonObj.put("showDeleteBtn", false);
                      break;
              default:break;
            }
            storeJsonObj.put("cafStatusAsString", cafStatusAsString);
            jsonArrayObj.put(storeJsonObj);
          }    
        }
      }
      response.setContentType("application/json");
      response.getWriter().print(jsonArrayObj);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // ==============================================================================
  
  public ActionForward deleteAllDistributorNotFoundRecords(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward deleteAllDistributorNotFoundRecords");
    HttpSession session = null;
    String pageToForward = VERIFY_ONLINE_CAFS_PAGE;
    ActionMessages actionMessages = new ActionMessages();
    int removedDistributorNotFoundRecords = 0;
    
    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        @SuppressWarnings("unchecked")
		List<UploadedCafDetails> uploadedCafsList = ((List<UploadedCafDetails>) session.getAttribute(UPLOADED_CAF_LIST));
        
        if(uploadedCafsList != null && uploadedCafsList.size() > 0) {
          Iterator<UploadedCafDetails> it = uploadedCafsList.iterator();
          
          while (it.hasNext()) {
            UploadedCafDetails ucd = (UploadedCafDetails) it.next();
            if (ucd.getCafStatus().equalsIgnoreCase(DISTRIBUTOR_NOT_FOUND)) {
              it.remove();
              removedDistributorNotFoundRecords++;
            }
          }
          session.setAttribute(UPLOADED_CAF_LIST, uploadedCafsList);
          actionMessages.add("delete.invalid.caf.records", new ActionMessage("delete.invalid.caf.records", removedDistributorNotFoundRecords)); 
          saveMessages(request, actionMessages);
        }
      } else {
        pageToForward = SESSION_INACTIVE_PAGE;
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward deleteAllDistributorNotFoundRecords");
      log.error(e);
    }
    log.info("END of the ActionForward deleteAllDistributorNotFoundRecords");
    return mapping.findForward(pageToForward);
  }

  // ==============================================================================
  
  public ActionForward deleteAllDistributorInActiveRecords(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward deleteAllDistributorInActiveRecords");
    HttpSession session = null;
    String pageToForward = VERIFY_ONLINE_CAFS_PAGE;
    ActionMessages actionMessages = new ActionMessages();
    int removedDistributorInActiveRecords = 0;
    
    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        @SuppressWarnings("unchecked")
		List<UploadedCafDetails> uploadedCafsList = ((List<UploadedCafDetails>) session.getAttribute(UPLOADED_CAF_LIST));
        
        if(uploadedCafsList != null && uploadedCafsList.size() > 0) {
          Iterator<UploadedCafDetails> it = uploadedCafsList.iterator();
          
          while (it.hasNext()) {
            UploadedCafDetails ucd = (UploadedCafDetails) it.next();
            if (ucd.getCafStatus().equalsIgnoreCase(DISTRIBUTOR_IN_ACTIVE)) {
              it.remove();
              removedDistributorInActiveRecords++;
            }
          }
          session.setAttribute(UPLOADED_CAF_LIST, uploadedCafsList);
          actionMessages.add("delete.invalid.caf.records", new ActionMessage("delete.invalid.caf.records", removedDistributorInActiveRecords)); 
          saveMessages(request, actionMessages);
        }
      } else {
        pageToForward = SESSION_INACTIVE_PAGE;
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward deleteAllDistributorInActiveRecords");
      log.error(e);
    }
    log.info("END of the ActionForward deleteAllDistributorInActiveRecords");
    return mapping.findForward(pageToForward);
  }

  // ==============================================================================
  
  
}
