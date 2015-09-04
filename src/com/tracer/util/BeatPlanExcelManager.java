/**
 * @author Bhargava
 *
 */
package com.tracer.util;

import static com.tracer.common.Constants.ASM_COL_NO;
import static com.tracer.common.Constants.ASM_MOB_NO_COL_NO;
import static com.tracer.common.Constants.DISTRIBUTOR_DISTRICT_COL_NO;
import static com.tracer.common.Constants.DISTRIBUTOR_MOBILE_NO_COL_NO;
import static com.tracer.common.Constants.DISTRIBUTOR_NAME_COL_NO;
import static com.tracer.common.Constants.DISTRIBUTOR_TOWN_COL_NO;
import static com.tracer.common.Constants.HUB_NAME_COL_NO;
import static com.tracer.common.Constants.INVALID_MOBILE_ERROR_MESSAGE;
import static com.tracer.common.Constants.OFSC_CODE_COL_NO;
import static com.tracer.common.Constants.PICKUP_FREQUENCY_COL_NO;
import static com.tracer.common.Constants.REGION_COL_NO;
import static com.tracer.common.Constants.ROLE_ASM;
import static com.tracer.common.Constants.ROLE_TSE;
import static com.tracer.common.Constants.ROLE_TSM;
import static com.tracer.common.Constants.TSE_OR_TSM_MOB_NO_COL_NO;
import static com.tracer.common.Constants.TSE_OR_TSM_NAME_COL_NO;
import static com.tracer.common.Constants.TSE_OR_TSM_ROLE_COL_NO;
import static com.tracer.common.Constants.ZONE_NAME_COL_NO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.tracer.dao.model.BeatPlanCircleDetails;
import com.tracer.dao.model.BeatPlanDistributorDetails;
import com.tracer.dao.model.BeatPlanHubDetails;
import com.tracer.dao.model.BeatPlanRegionDetails;
import com.tracer.dao.model.BeatPlanUserDetails;
import com.tracer.dao.model.BeatPlanZoneDetails;
import com.tracer.dao.model.DistributorVisitSchduleDetails;

public class BeatPlanExcelManager {

protected transient final Log log = LogFactory.getLog(getClass());

  // ==========================================================================

  private static BeatPlanExcelManager beatPlanExcelManager = null;

  // ==========================================================================

  // Making BeatPlanExcelManager as Singleton class
  private BeatPlanExcelManager() { }

  // ==========================================================================

  public static synchronized BeatPlanExcelManager getInstance() {
    if (beatPlanExcelManager == null)
      beatPlanExcelManager = new BeatPlanExcelManager();
      return beatPlanExcelManager;
  }

  // ===================================================================

  /*
   * Returns the beatPlanCircleDetails object. - 1. Reads the content from the Excel file and
   * set them to beatPlanCircleDetails object. 2. Returns the beatPlanCircleDetails object filled with list of
   * excel file content.
   * 
   * Specified by: readBeatPlanExcelFile(...) in fileName Parameters: InputStream
   * fileInputStream that is to be read. Returns: beatPlanCircleDetails object filled with list of
   * excel file content.
   */
  public BeatPlanCircleDetails readBeatPlanExcelFile(InputStream fileInputStream) {
    log.info("START of the method readBeatPlanExcelFile");
    POIFSFileSystem poiFSFileSystem = null;
    HSSFWorkbook workBook = null;
    HSSFSheet hssfSheet = null;
    BeatPlanCircleDetails beatPlanCircleDetails = null;
    BeatPlanHubDetails beatPlanHubDetails = null;
    BeatPlanZoneDetails beatPlanZoneDetails = null;
    BeatPlanUserDetails beatPlanUserDetails = null;
    String zoneName = null;
    String hubName = null;
    String asmName = null;
    String asmMobileNumber = null;
    String tseOrTsmName = null;
    String tseOrTsmMobileNumber = null;
    String tseOrTsmRole = null;
    String regionName = null;
    String role = null;
    List<BeatPlanRegionDetails> beatPlanRegions = null;

    try {
      if (fileInputStream != null) {
        poiFSFileSystem = new POIFSFileSystem(fileInputStream);
        workBook = new HSSFWorkbook(poiFSFileSystem);

        if (workBook != null && workBook.getNumberOfSheets() > 0) {
          hssfSheet = workBook.getSheetAt(0);

          if (hssfSheet != null) {
            HSSFRow headerRow = hssfSheet.getRow(0);

            for (short c = 0; c < headerRow.getLastCellNum(); c++) {
              updateHeaderColumnNo(getCellValue(headerRow.getCell(c)), c);
            }

            if (hssfSheet.getLastRowNum() > 0) {
              beatPlanCircleDetails = new BeatPlanCircleDetails();
              //beatPlanCircleDetails.setCircleName("TestCircleName");
              beatPlanRegions = new ArrayList<BeatPlanRegionDetails>();
            }

            for (int i = 1; i <= hssfSheet.getLastRowNum(); i++) {
              HSSFRow hssfRow = hssfSheet.getRow(i);
              zoneName = getCellValue(hssfRow.getCell(ZONE_NAME_COL_NO));
              hubName = getCellValue(hssfRow.getCell(HUB_NAME_COL_NO));
              asmName = getCellValue(hssfRow.getCell(ASM_COL_NO));
              asmMobileNumber = getCellValue(hssfRow.getCell(ASM_MOB_NO_COL_NO));
              tseOrTsmName = getCellValue(hssfRow.getCell(TSE_OR_TSM_NAME_COL_NO));
              tseOrTsmMobileNumber = getCellValue(hssfRow.getCell(TSE_OR_TSM_MOB_NO_COL_NO));
              tseOrTsmRole = getCellValue(hssfRow.getCell(TSE_OR_TSM_ROLE_COL_NO));
              regionName = getCellValue(hssfRow.getCell(REGION_COL_NO));
              BeatPlanDistributorDetails beatPlanDistributorDetails = new BeatPlanDistributorDetails();
              beatPlanDistributorDetails.setDistTempId(i);
              beatPlanDistributorDetails.setDistributorName(getCellValue(hssfRow.getCell(DISTRIBUTOR_NAME_COL_NO)));
              beatPlanDistributorDetails.setOfscCode(getCellValue(hssfRow.getCell(OFSC_CODE_COL_NO)));
              beatPlanDistributorDetails.setPickupFrequency(getCellValue(hssfRow.getCell(PICKUP_FREQUENCY_COL_NO)));
              beatPlanDistributorDetails.setMobileNumber(getCellValue(hssfRow.getCell(DISTRIBUTOR_MOBILE_NO_COL_NO)));
              
              if (!StringUtil.isValidMobileNumber(beatPlanDistributorDetails.getMobileNumber())) {
                beatPlanDistributorDetails.setErrorMessage(INVALID_MOBILE_ERROR_MESSAGE);
              }
              
              beatPlanDistributorDetails.setTown(getCellValue(hssfRow.getCell(DISTRIBUTOR_TOWN_COL_NO)));
              beatPlanDistributorDetails.setDistrict(getCellValue(hssfRow.getCell(DISTRIBUTOR_DISTRICT_COL_NO)));
              
              BeatPlanRegionDetails beatPlanRegionDetails = getRegion(beatPlanCircleDetails, regionName);
              if (StringUtil.isNotNull(regionName) && beatPlanRegionDetails == null) {
                beatPlanRegionDetails = new BeatPlanRegionDetails();
                beatPlanRegionDetails.setRegionName(regionName);
                beatPlanRegions.add(beatPlanRegionDetails);
                beatPlanCircleDetails.setBeatPlanRegions(beatPlanRegions);
              }
              setZone(beatPlanRegionDetails, zoneName, hubName, beatPlanDistributorDetails);
              
              if(tseOrTsmRole.equalsIgnoreCase(ROLE_TSM)) {
                role = ROLE_TSM;
              } else if(tseOrTsmRole.equalsIgnoreCase(ROLE_TSE)) {
                role = ROLE_TSE;
              }
              beatPlanZoneDetails = getZone(beatPlanRegionDetails, zoneName);
              beatPlanHubDetails = getHub(beatPlanZoneDetails, hubName);
              beatPlanUserDetails = new BeatPlanUserDetails();
              setUser(beatPlanHubDetails, asmName, ROLE_ASM, asmMobileNumber);
              setUser(beatPlanHubDetails, tseOrTsmName, role, tseOrTsmMobileNumber);
              
              if(ROLE_TSE.equals(role)) {
                assignDistributorAndSchedule(beatPlanHubDetails, tseOrTsmMobileNumber, beatPlanDistributorDetails.getDistTempId());
              }
              
              if(!StringUtil.isValidMobileNumber(asmMobileNumber) || !StringUtil.isValidMobileNumber(tseOrTsmMobileNumber)) {
                beatPlanUserDetails.setErrorMessage(INVALID_MOBILE_ERROR_MESSAGE);
              }
              
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("Problem in the method readBeatPlanExcelFile");
      log.error(e);
    }
    log.info("END of the method readBeatPlanExcelFile");
    return beatPlanCircleDetails;
  }
  
  // ==========================================================================
  
  private void assignDistributorAndSchedule(BeatPlanHubDetails beatPlanHubDetails, String runnerMobileNo, long distTempId) {
    log.info("START of the method assignDistributorAndSchedule");
    List<BeatPlanUserDetails> bpUsers = null;
    List<DistributorVisitSchduleDetails> distributorVisitSchdule = null;
    String distributorVisitscheduleTime = null;
    String distributorNewVisitScheduleTime = null;
    DistributorVisitSchduleDetails distributorVisitSchduleDetails = null;
    DateFormat dateFormat = null;
    Date date = null;
    Calendar calendar = null;
    
    try {
      bpUsers = beatPlanHubDetails.getBeatPlanUsers();
      
      if(bpUsers != null && bpUsers.size() > 0) {
        for (BeatPlanUserDetails beatPlanUserDetails : bpUsers) {
          if (runnerMobileNo.equals(beatPlanUserDetails.getMobileNumber())) {
            distributorVisitSchdule = beatPlanUserDetails.getDistributorVisitSchdule();
            if(distributorVisitSchdule != null && distributorVisitSchdule.size() > 0) {
              distributorVisitscheduleTime = distributorVisitSchdule.get(distributorVisitSchdule.size() - 1).getScheduleTime();
              dateFormat = new SimpleDateFormat("HH:mm");
              date = dateFormat.parse(distributorVisitscheduleTime); 
              calendar = Calendar.getInstance();
              calendar.setTime(date);
              calendar.add(Calendar.HOUR, 1);
              distributorNewVisitScheduleTime = dateFormat.format(calendar.getTime());
            } else {
              distributorVisitSchdule = new ArrayList<DistributorVisitSchduleDetails>();
              distributorNewVisitScheduleTime = "10:00";
            }
            distributorVisitSchduleDetails = new DistributorVisitSchduleDetails();
            distributorVisitSchduleDetails.setDistTempId(distTempId);
            distributorVisitSchduleDetails.setScheduleTime(distributorNewVisitScheduleTime);
            distributorVisitSchdule.add(distributorVisitSchduleDetails);
            beatPlanUserDetails.setDistributorVisitSchdule(distributorVisitSchdule);
            break;
          }
        }
      }
    } catch (Exception e) {
      log.error("Problem in the method assignDistributorAndSchedule");
      log.error(e);
    }
    log.info("END of the method assignDistributorAndSchedule");
  }

  //==========================================================================
  
  private void setZone(BeatPlanRegionDetails beatPlanRegionDetails, String zoneName, String hubName, BeatPlanDistributorDetails beatPlanDistributorDetails) {
    log.info("START of the method setZone");
    BeatPlanZoneDetails beatPlanZoneDetails = null;
    List<BeatPlanZoneDetails> beatPlanZones = null;
    
    try {
      beatPlanZoneDetails = getZone(beatPlanRegionDetails, zoneName);

      if (beatPlanRegionDetails.getBeatPlanZones() == null) {
        beatPlanZones = new ArrayList<BeatPlanZoneDetails>();
      } else {
        beatPlanZones = beatPlanRegionDetails.getBeatPlanZones();
      }

      if (beatPlanZoneDetails == null) {
        beatPlanZoneDetails = new BeatPlanZoneDetails();
        beatPlanZoneDetails.setZoneName(zoneName);
        beatPlanZones.add(beatPlanZoneDetails);
        beatPlanRegionDetails.setBeatPlanZones(beatPlanZones);
      }
      setHub(beatPlanZoneDetails, hubName, beatPlanDistributorDetails);
    } catch (Exception e) {
        log.error("Problem in the method setZone");
        log.error(e);
    }
    log.info("END of the method setZone");
  }

  // ==========================================================================

  private void setUser(BeatPlanHubDetails beatPlanHubDetails, String userName, String role, String mobileNumber) {
    log.info("START of the method setUser");
    BeatPlanUserDetails beatPlanUserDetails = null;
    List<BeatPlanUserDetails> beatPlanUsers = null;
    
    try {
     if (beatPlanHubDetails != null) {
       beatPlanUsers = beatPlanHubDetails.getBeatPlanUsers();
            
       if (beatPlanUsers != null && beatPlanUsers.size() > 0) {
         for (BeatPlanUserDetails bpUserDetails : beatPlanUsers) {
           if (userName.equals(bpUserDetails.getUserName()) && role.equals(bpUserDetails.getRole()) && mobileNumber.equals(bpUserDetails.getMobileNumber())) {
             beatPlanUserDetails = bpUserDetails;
             break;
           }
         }
         if(beatPlanUserDetails == null) {
           beatPlanUserDetails = new BeatPlanUserDetails();
           beatPlanUserDetails.setMobileNumber(mobileNumber);
           beatPlanUserDetails.setRole(role);
           beatPlanUserDetails.setUserName(userName);
         }
       } else {
         beatPlanUsers = new ArrayList<BeatPlanUserDetails>();
         beatPlanUserDetails = new BeatPlanUserDetails();
         beatPlanUserDetails.setMobileNumber(mobileNumber);
         beatPlanUserDetails.setRole(role);
         beatPlanUserDetails.setUserName(userName);
       }
       if(!StringUtil.isValidMobileNumber(mobileNumber)) {
         beatPlanUserDetails.setErrorMessage(INVALID_MOBILE_ERROR_MESSAGE);
       }
       beatPlanUsers.add(beatPlanUserDetails);
       beatPlanHubDetails.setBeatPlanUsers(beatPlanUsers);
     }
    } catch (Exception e) {
        log.error("Problem in the method setUser");
        log.error(e);
    }
    log.info("END of the method setUser");
  }

  //===================================================================================

  private void setHub(BeatPlanZoneDetails beatPlanZoneDetails, String hubName, BeatPlanDistributorDetails beatPlanDistributorDetails) {
    log.info("START of the method setHub");
    BeatPlanHubDetails beatPlanHubDetails = null;
    List<BeatPlanHubDetails> beatPlanHubs = null;
    
    try {
      beatPlanHubDetails = getHub(beatPlanZoneDetails, hubName);

      if (beatPlanZoneDetails.getBeatPlanHubs() == null) {
        beatPlanHubs = new ArrayList<BeatPlanHubDetails>();
      } else {
        beatPlanHubs = beatPlanZoneDetails.getBeatPlanHubs();
      }

      if (beatPlanHubDetails == null) {
        beatPlanHubDetails = new BeatPlanHubDetails();
        beatPlanHubDetails.setHubName(hubName);
        beatPlanHubs.add(beatPlanHubDetails);
        beatPlanZoneDetails.setBeatPlanHubs(beatPlanHubs);
      }
      BeatPlanDistributorDetails bpDistributorDetails = getDistributor(beatPlanHubDetails, beatPlanDistributorDetails.getDistributorName());
      List<BeatPlanDistributorDetails> beatPlanDistributors = new ArrayList<BeatPlanDistributorDetails>();

      if (bpDistributorDetails == null) {
        bpDistributorDetails = beatPlanDistributorDetails;
        beatPlanDistributors.add(bpDistributorDetails);
        beatPlanHubDetails.setBeatPlanDistributors(beatPlanDistributors);
      }
    } catch (Exception e) {
        log.error("Problem in the method setHub");
        log.error(e);
    }
    log.info("END of the method setHub");
  }

  // ==========================================================================

  private BeatPlanRegionDetails getRegion(BeatPlanCircleDetails beatPlanCircleDetails, String regionName) {
    log.info("START of the method getRegion");
    
    try {
      if (beatPlanCircleDetails != null && StringUtil.isNotNull(regionName)) {
        List<BeatPlanRegionDetails> beatPlanRegions = beatPlanCircleDetails.getBeatPlanRegions();
        if (beatPlanRegions != null && beatPlanRegions.size() > 0) {
          for (BeatPlanRegionDetails beatPlanRegionDetails : beatPlanRegions) {
            if (regionName.equals(beatPlanRegionDetails.getRegionName())) {
              return beatPlanRegionDetails;
            }
          }
        }
      }
    } catch (Exception e) {
        log.error("Problem in the method getRegion");
        log.error(e);
    }
    log.info("END of the method getRegion");
    return null;
  }

  // ==========================================================================

  private BeatPlanZoneDetails getZone(BeatPlanRegionDetails beatPlanRegionDetails, String zoneName) {
    log.info("START of the method getZone");
    
    try {
      if (beatPlanRegionDetails != null) {
        List<BeatPlanZoneDetails> beatPlanZones = beatPlanRegionDetails.getBeatPlanZones();
        if (beatPlanZones != null && beatPlanZones.size() > 0) {
          for (BeatPlanZoneDetails beatPlanZoneDetails : beatPlanZones) {
            if (zoneName.equals(beatPlanZoneDetails.getZoneName())) {
              return beatPlanZoneDetails;
            }
          }
        }
      }
    } catch (Exception e) {
        log.error("Problem in the method getZone");
        log.error(e);
    }
    log.info("END of the method getZone");
    return null;
  }

  // ==========================================================================

  private BeatPlanHubDetails getHub(BeatPlanZoneDetails beatPlanZoneDetails, String hubName) {
    log.info("START of the method getHub");
    
    try {
      if (beatPlanZoneDetails != null) {
        List<BeatPlanHubDetails> beatPlanHubs = beatPlanZoneDetails.getBeatPlanHubs();
        if (beatPlanHubs != null && beatPlanHubs.size() > 0) {
          for (BeatPlanHubDetails beatPlanHubDetails : beatPlanHubs) {
            if (hubName.equals(beatPlanHubDetails.getHubName())) {
              return beatPlanHubDetails;
            }
          }
        }
      }
    } catch (Exception e) {
        log.error("Problem in the method getHub");
        log.error(e);
    }
    log.info("END of the method getHub");
    return null;
  }

  // ==========================================================================

  private BeatPlanDistributorDetails getDistributor(BeatPlanHubDetails beatPlanHubDetails, String distributorName) {
    log.info("START of the method getDistributor");
    
    try {
      if (beatPlanHubDetails != null) {
        List<BeatPlanDistributorDetails> beatPlanDistributors = beatPlanHubDetails.getBeatPlanDistributors();
        if (beatPlanDistributors != null
            && beatPlanDistributors.size() > 0) {
            for (BeatPlanDistributorDetails beatPlanDistributorDetails : beatPlanDistributors)
              if (distributorName.equals(beatPlanDistributorDetails.getDistributorName())) {
                return beatPlanDistributorDetails;
              }
        }
      }
    } catch (Exception e) {
        log.error("Problem in the method getDistributor");
        log.error(e);
    }
    log.info("END of the method getDistributor");
    return null;
  }

  // =========================================================================

  private String getCellValue(HSSFCell hssfCell) {
    log.info("START of the method getCellValue");
    String cellValue = "";
    DecimalFormat decimalFormat;
    
    try {
      if (hssfCell != null) {
        if (HSSFCell.CELL_TYPE_STRING == hssfCell.getCellType()) {
          cellValue = hssfCell.getStringCellValue();
        } else if (HSSFCell.CELL_TYPE_NUMERIC == hssfCell.getCellType()) {
          decimalFormat = new DecimalFormat("##");
          cellValue = decimalFormat.format(hssfCell.getNumericCellValue());
        } else if(HSSFCell.CELL_TYPE_FORMULA == hssfCell.getCellType()) {
          cellValue = hssfCell.getStringCellValue();
        }
      }
    } catch (Exception e) {
        log.error("Problem in the method getCellValue");
        log.error(e);
    }
    log.info("END of the method getCellValue");
    return cellValue;
  }

  // ==========================================================================

  private void updateHeaderColumnNo(String cellValue, short no) {
    log.info("START of the method updateHeaderColumnNo");
    
    try {
      if (StringUtil.isNotNull(cellValue)) {
        if (cellValue.equalsIgnoreCase("OFS Code") || cellValue.equalsIgnoreCase("OFSC Code")) {
          OFSC_CODE_COL_NO = no;
        } else if (cellValue.equalsIgnoreCase("Distributor Name") || cellValue.equalsIgnoreCase("Distributor")) {
          DISTRIBUTOR_NAME_COL_NO = no;
        } else if (cellValue.equalsIgnoreCase("New Zone") || cellValue.equalsIgnoreCase("Zone")) {
          ZONE_NAME_COL_NO = no;
        } else if (cellValue.equalsIgnoreCase("New CAF Pickup Frequency") || cellValue.equalsIgnoreCase("CAF Pickup Frequency")) {
          PICKUP_FREQUENCY_COL_NO = no;
        } else if (cellValue.equalsIgnoreCase("HUB Name") || cellValue.equalsIgnoreCase("Hub")) {
          HUB_NAME_COL_NO = no; 
        } else if (cellValue.equalsIgnoreCase("ASM") || cellValue.equalsIgnoreCase("ASM Name")) {
          ASM_COL_NO = no;
        } else if (cellValue.equalsIgnoreCase("ASM NO") || cellValue.equalsIgnoreCase("ASM Number")) {
          ASM_MOB_NO_COL_NO = no;
        } else if (cellValue.equalsIgnoreCase("TSE/TSM Name")) {
          TSE_OR_TSM_NAME_COL_NO = no;
        } else if (cellValue.equalsIgnoreCase("TSE/TSM NO") || cellValue.equalsIgnoreCase("TSE/TSM Number")) {
          TSE_OR_TSM_MOB_NO_COL_NO = no;
        } else if (cellValue.equalsIgnoreCase("TSM/TSE") ||cellValue.equalsIgnoreCase("TSE/TSM")) {
          TSE_OR_TSM_ROLE_COL_NO = no;
        } else if (cellValue.equalsIgnoreCase("Region") || cellValue.equalsIgnoreCase("New Region")) {
          REGION_COL_NO = no;
        } else if (cellValue.equalsIgnoreCase("Dist Demo No") || cellValue.equalsIgnoreCase("Dist Mob No")) {
          DISTRIBUTOR_MOBILE_NO_COL_NO = no;
        } else if (cellValue.equalsIgnoreCase("Town") || cellValue.equalsIgnoreCase("Dist Town")) {
          DISTRIBUTOR_TOWN_COL_NO = no;
        } else if (cellValue.equalsIgnoreCase("District") || cellValue.equalsIgnoreCase("Dist District")) {
          DISTRIBUTOR_DISTRICT_COL_NO = no;
        }
      }
    } catch (Exception e) {
        log.error("Problem in the method updateHeaderColumnNo");
        log.error(e);
  }
    log.info("END of the method updateHeaderColumnNo");
  }

  // ==========================================================================
  
  /*
   * Returns BeatPlanValidationErrors object. - 1. Validates the map object 2.
   * Returns BeatPlanValidationErrors object with the validation errors if
   * any.
   * 
   * Specified by: validateBeatPlanExcelFile(...) in map object Parameters:
   * map object. Returns: BeatPlanValidationErrors object with validation
   * errors if any
   */
  public BeatPlanValidationErrors validateBeatPlanExcelFile(BeatPlanCircleDetails beatPlanCircleDetails) {
    return null;
  }
  
  // ==========================================================================
  
  public static void main(String[] args) throws FileNotFoundException {
    String fileName = "D:/TraceRContinua/Documents/Prepaid R1-Data.xls";
    InputStream inputStream = new FileInputStream(fileName);
    BeatPlanExcelManager.getInstance().readBeatPlanExcelFile(inputStream);
  }
}
