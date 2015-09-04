/**
 * @author Jp
 *
 */
package com.tracer.common;

public class Constants {
  public static final String CONFIG = "appConfig";
  public static final String DATASOURCE_NAME = "dataSourceName";
  public static final String CONTEXT_PATH = "contextPath";
  public static final String DAO_TYPE = "daoType";
  public static final String DAO_TYPE_HIBERNATE = "hibernate";
  public static final String SMTP = "smtp";
  public static final String SMTP_GMAIL_HOST = "smtp.gmail.com";
  public static final int MAIL_SMTP_PORT = 587; // 587 is the port number of
  public static String TRACER_RWS_URL = "";
                                                // yahoo mail
  public static final String SMTP_MAIL_YAHOO_HOST = "smtp.mail.yahoo.co.in";
  public static final String EMAIL_FROM_ADDRESS = "email.from.address";
  public static final String EMAIL_FROM_ADDRESS_PASSWORD = "email.from.address.password";
  public static final String APPLICATION_RESOURCES = "ApplicationResources";
  public static final String LOGIN_ERROR = "loginError";
  public static final String LOG_OUT = "logout";
  public static final String SPAM_VALUE = "spamValue";
  public static final String USER_NAME = "userName";
  public static final String NAME = "name";
  public static final String METHOD = "method";
  public static final String DATE_FORMAT = "MM/dd/yyyy";
  public static final String DATE_FORMAT1 = "MM-dd-yyyy";
  public static final String DATE_FORMAT_DB = "yyyy-MM-dd";
  public static final String DATE_TIME_FORMAT = "MM-dd-yyyy HH:mm";
  public static final String TIME_FORMAT = "HH:mm";
  public static final String PAGE_TITLE = "pageTitle";
  public static final String EDIT = "edit";
  public static final String NEW = "new";
  public static final String VIEW_TYPE = "viewType";

  public static final String JSON = "application/json";

  public static final String IN_ACTIVE = "0";
  public static final String ACTIVE = "1";

  public static final String IN_ACTIVE_STRING = "In Active";
  public static final String ACTIVE_STRING = "Active";

  public static final String ROLE_ID = "roleId";
  public static final String USER_ID = "userId";
  public static final String AUTH_CODE = "authCode";
  public static final String ROLE = "role";
  public static final String[] ROLES = { "SA", "CBM", "RBM", "ZBM", "ASM", "TSM", "TSE" };
  public static final String[] ROLE_NAMES = { "Super Administrator", "Circle Manager", "Region Manager", "Zone Manager", "Hub Manager", "Team Leader", "Runner" };

  public static final int SYSTEM_ADMIN = 1;
  public static final int CIRCLE_MANAGER = 2;
  public static final int REGION_MANAGER = 3;
  public static final int ZONE_MANAGER = 4;
  public static final int HUB_MANAGER = 5;
  public static final int TEAM_LEADER = 6;
  public static final int RUNNER = 7;
  public static final int DEO = 8;

  public static final String STATUS = "status";
  public static final String ERROR_MESSAGE = "errorMessage";
  public static final String SUCCESS = "success";
  public static final String FAILURE = "failure";
  public static final String INVALID_INPUT = "Invalid Input";
  public static final String RESULT = "result";

  // Constants for manager names.
  public static final String BEAT_PLANS_MANAGER = "beatPlansManager";
  public static final String CIRCLES_MANAGER = "circlesManager";
  public static final String DISTRIBUTOR_MANAGER = "distributorManager";
  public static final String LOGIN_MANAGER = "loginManager";
  public static final String NOTIFICATION_MANAGER = "notificationManager";
  public static final String REPORTS_MANAGER = "reportsManager";
  public static final String TRACK_MANAGER = "trackManager";
  public static final String USER_MANAGER = "userManager";
  public static final String UPLOAD_MANAGER = "uploadManager";
  public static final String DATA_ENTRY_MANAGER = "dataEntryManager";
  public static final String BEAT_PLAN_BULK_UPLOAD_MANAGER = "beatPlanBulkUploadManager";
  
  public static final String USER_DETAILS = "UserDetails";
  public static final String USER_REPORTING_TO_DETAILS = "UserReportingToDetails";
  public static final String USER_TO_CIRCLE_DETAILS = "UserToCircleDetails";
  public static final String USER_TO_REGION_DETAILS = "UserToRegionDetails";
  public static final String USER_TO_ZONE_DETAILS = "UserToZoneDetails";
  public static final String USER_TO_HUB_DETAILS = "UserToHubDetails";

  public static final String DISTRIBUTOR_DETAILS = "DistributorDetails";
  public static final String DISTRIBUTOR_TO_HUB_DETAILS = "DistributorToHubDetails";
  public static final String IS_ASSINGED_TO_BEAT_PLAN = "isAssingedToBeatPlan";

  public static final String BEAT_PLAN_DETAILS = "BeatPlanDetails";
  public static final String BEAT_PLAN_TO_DISTRIBUTOR_DETAILS = "BeatPlanToDistributorDetails";
  public static final String BEAT_PLAN_TO_USER_DETAILS = "BeatPlanToUserDetails";
  public static final String CIRCLE_DETAILS = "circleDetails";
  public static final String CIRCLES = "circlesList";
  public static final String REGIONS = "regionsList";
  public static final String ZONES = "zonesList";
  public static final String HUBS = "hubsList";
  public static final String REGION_ID = "regionId";
  public static final String ZONE_ID = "zoneId";
  public static final String HUB_ID = "hubId";

  // Constants for mapping action forward names.
  public static final String LOGIN_PAGE = "loginPage";
  public static final String FORGOT_PASSWORD_PAGE = "forgotpasswordPage";
  public static final String EMAIL_SENT_SUCCESS = "emailSentSuccess";
  public static final String LINK_INVALID_PAGE = "linkInvalidPage";
  public static final String RESET_PASSWORD_PAGE = "resetPasswordPage";
  public static final String SHOW_LOGIN_METHOD_FORWARD = "showLoginMethodForward";
  public static final String SHOW_DASHBOARD_METHOD_FORWARD = "showDashboardMethodForward";
  public static final String ACCOUNT_INACTIVE_PAGE = "accountInactivePage";
  public static final String ACCESS_DENIED = "accessDenied";
  public static final String UNAUTH_INVALID_TOKEN_ERROR_PAGE = "unAuthInvalidTokenErrorPage";
  public static final String INVALID_TOKEN_ERROR_PAGE = "invalidTokenErrorPage";
  public static final String SAVE_ATTENDANCE_PAGE = "saveAttendancePage";

  public static final String CBM_DASHBOARD_PAGE = "cbmDashboardPage";
  public static final String RBM_DASHBOARD_PAGE = "rbmDashboardPage";
  public static final String ZBM_DASHBOARD_PAGE = "zbmDashboardPage";
  public static final String ASM_DASHBOARD_PAGE = "asmDashboardPage";
  public static final String TSM_DASHBOARD_PAGE = "tsmDashboardPage";
  public static final String TSE_DASHBOARD_PAGE = "tseDashboardPage";
  public static final String SA_DASHBOARD_PAGE = "saDashboardPage";
  public static final String HOME_PAGE = "homePage"; // This is a dummy page
  public static final String DEO_DASHBOARD_PAGE = "deoDashboardPage";

  public static final String ADD_DISTRIBUTOR_BEAT_PLAN_PAGE = "addDistributorBeatPlanPage";
  public static final String ADD_RUNNER_BEAT_PLAN_PAGE = "addRunnerBeatPlanPage";
  public static final String MANAGE_BEAT_PLANS_PAGE = "manageBeatPlansPage";
  public static final String MANAGE_RUNNER_BEAT_PLANS_PAGE = "manageRunnerBeatPlansPage";

  public static final String ADD_DISTRIBUTOR_PAGE = "addDistributorPage";
  public static final String MANAGE_DISTRIBUTORS_PAGE = "manageDistributorsPage";
  public static final String SHOW_MANAGE_DISTRIBUTORS_METHOD_FORWARD = "showManageDistributorsMethodForward";
  public static final String UPDATE_SUCCESS = "updateSuccess";

  public static final String TRACK_RUNNERS_PAGE = "trackRunnersPage";
  public static final String TRACK_TEAM_LEADERS_PAGE = "trackTeamLeaderPage";
  public static final String RUNNER_CAF_COLLECTION_DETAILS_PAGE = "runnerCAFCollectionDetailsPage";
  public static final String TEAM_LEADER_CAF_COLLECTION_DETAILS_PAGE = "teamLeaderCAFCollectionDetailsPage";

  public static final String RUNNER_ADD_PAGE = "addRunnerPage";
  public static final String TEAM_LEADER_ADD_PAGE = "addTeamLeaderPage";
  public static final String MANAGER_ADD_PAGE = "addManagerPage";
  public static final String MANAGE_USERS_PAGE = "manageUsersPage";
  public static final String EDIT_PROFILE_PAGE = "editProfilePage";
  public static final String CHANGE_PASSWORD_PAGE = "changePasswordPage";
  
  public static final String MANAGER_UPDATE_PAGE = "updateManagerPage";
  public static final String RUNNER_UPDATE_PAGE = "updateRunnerPage";
  public static final String TEAM_LEADER_UPDATE_PAGE = "updateTeamLeaderPage";

  // constants for Circles.
  public static final String MANAGE_CIRCLES_LIST = "manageCirclesPage";
  public static final String ADD_CIRCLE = "addCircle";
  public static final String UPDATE_CIRCLE = "updateCircle";
  public static final String DELETE_CIRCLE = "deleteCircle";
  public static final String DISPLAY_CIRCLE = "displayCircle";
  public static final String LIST_CIRCLES = "listCircles";
  public static final String ADD_REGION = "addRegion";
  public static final String UPDATE_REGION = "updateRegion";
  public static final String DELETE_REGION = "deleteRegion";
  public static final String DISPLAY_REGION = "displayRegion";
  public static final String LIST_REGIONS = "listRegions";
  public static final String ADD_ZONE = "addZone";
  public static final String UPDATE_ZONE = "updateZone";
  public static final String DELETE_ZONE = "deleteZone";
  public static final String DISPLAY_ZONE = "displayZone";
  public static final String LIST_ZONES = "listZones";
  public static final String ADD_HUB = "addHub";
  public static final String UPDATE_HUB = "updateHub";
  public static final String DELETE_HUB = "deleteHub";
  public static final String DISPLAY_HUB = "displayHub";
  public static final String LIST_HUBS = "listHubs";
  public static final String CIRCLE_ERROR_MESSAGE = "circleErrorMessage";
  public static final String DISTRIBUTOR_BEAT_PLAN_ERROR_MESSAGE = "distributorBeatPlanErrorMessage";
  public static final String RUNNER_BEAT_PLAN_ERROR_MESSAGE = "runnerBeatPlanErrorMessage";

  public static final String ADD_CIRCLE_SUCCESS_MESSAGE = "<i class=\"fa fa-check-circle-o\"></i>   <span id=\"circle_add_success\">Circle added successfully</span> !!";
  public static final String EDIT_CIRCLE_SUCCESS_MESSAGE = "<i class=\"fa fa-check-circle-o\"></i>  <span id=\"circle_update_success\">Circle updated successfully</span> !!";
  public static final String DELETE_CIRCLE_SUCCESS_MESSAGE = "<i class=\"fa fa-times-circle\"></i>   <span id=\"circle_delete_success\">Circle deleted successfully</span> !!";
  public static final String ADD_DISTRIBUTOR_BEAT_PLAN_SUCCESS_MESSAGE = "<i class=\"fa fa-check-circle-o\"></i>    Distributor Beat Plan added successfully !!";
  public static final String ADD_RUNNER_BEAT_PLAN_SUCCESS_MESSAGE = "<i class=\"fa fa-check-circle-o\"></i>   Runner Beat Plan added successfully !!";
  public static final String EDIT_DISTRIBUTOR_BEAT_PLAN_SUCCESS_MESSAGE = "<i class=\"fa fa-check-circle-o\"></i>   Distributor Beat Plan updated successfully !!";
  public static final String EDIT_RUNNER_BEAT_PLAN_SUCCESS_MESSAGE = "<i class=\"fa fa-check-circle-o\"></i>   Runner Beat Plan updated successfully !!";
  public static final String DELETE_DISTRIBUTOR_BEAT_PLAN_SUCCESS_MESSAGE = "<i class=\"fa fa-times-circle\"></i>   Distributor Beat Plan deleted successfully !!";
  public static final String DELETE_RUNNER_BEAT_PLAN_SUCCESS_MESSAGE = "<i class=\"fa fa-times-circle\"></i>   Runner Beat Plan deleted successfully !!";
  public static final String RUNNER_DELETE_SUCCESS_MESSAGE = "deleteSuccess";

  // constants for Notifications
  public static final String MANAGE_SMS_TEMPLATE = "manageSmsTemplate";
  public static final String ADD_SMS_TEMPLATE = "addSmsTemplate";
  public static final String ADD_SMS_TEMPLATE_SUCCESS_MESSAGE = "<i class=\"fa fa-check-circle-o\"></i>   SMS Template added successfully !!";
  public static final String EDIT_SMS_TEMPLATE_SUCCESS_MESSAGE = "<i class=\"fa fa-check-circle-o\"></i>    SMS Template updated successfully !!";
  public static final String ADD_SMS_TEMPLATE_ERROR_MESSAGE = "Failed to add SMS Template";
  public static final String EDIT_SMS_TEMPLATE_ERROR_MESSAGE = "Failed to update SMS Template";

  public static final String CIRCLE_NAME = "circleName";
  public static final String CIRCLE_CODE = "circleCode";
  public static final String REGION_NAME = "regionName";
  public static final String CIRCLE_ID = "circleId";
  public static final String CIRCLE_VIEW_TYPE = "circleViewType";
  public static final String EDIT_CIRCLE_ID = "editcircleId";

  // constants for Users
  public static final String USER_VIEW_TYPE = "userViewType";
  public static final String EDIT_USER_ID = "editUserId";
  public static final String SUCCESS_MESSAGE = "successMessage";
  public static final String DELETE_SUCCESS_MESSAGE = "deleteSuccessMessage";
  public static final String DELETE_USER_SUCCESS_MESSAGE = "<i class=\"fa fa-times-circle\"></i>   User deleted successfully !!";
  public static final String ADD_USER_SUCCESS_MESSAGE = "<i class=\"fa fa-check-circle-o\"></i>   User added successfully !!";
  public static final String EDIT_USER_SUCCESS_MESSAGE = "<i class=\"fa fa-check-circle-o\"></i>   User updated successfully !!";
  public static final String ACTIVATE_USER_SUCCESS_MESSAGE = "<i class=\"fa fa-times-circle\"></i>   User account activated successfully !!";

  // constants for Distributors
  public static final String DISTRIBUTOR_ID = "distributorId";
  public static final String ADD_DISTRIBUTOR_SUCCESS_MESSAGE = "<i class=\"fa fa-check-circle-o\"></i>   Distributor added successfully !!";
  public static final String EDIT_DISTRIBUTOR_SUCCESS_MESSAGE = "<i class=\"fa fa-check-circle-o\"></i>  Distributor Updated successfully !!";
  public static final String DELETE_DISTRIBUTOR_SUCCESS_MESSAGE = "<i class=\"fa fa-times-circle\"></i>  Distributor deleted successfully !!";
  public static final String DELETE_DISTRIBUTOR_FAILURE_MESSAGE = "<i class=\"fa fa-times-circle\"></i>  Cannot delete, as distributor is assigned to beat plan";
  public static final String DELETE_USER_FAILURE_MESSAGE = "<i class=\"fa fa-times-circle\"></i>  Cannot delete, as runner is assigned to beat plan";
  public static final String ACTIVATE_DISTRIBUTOR_SUCCESS_MESSAGE = "<i class=\"fa fa-times-circle\"></i>   Distributor activated successfully !!";

  // constants for Reports
  public static final String REPORTS_DASHBOARD_PAGE = "reportsDashboardPage";
  public static final String TOP_RUNNERS_REPORT_PAGE = "topRunnersReportPage";
  public static final String ATTENDANCE_REPORT_PAGE = "attendanceReportPage";
  public static final String VISITS_VS_BEATPLAN_REPORT_PAGE = "visitsVsBeatplanReportPage";
  public static final String NON_PERFORMED_RUNNERS_REPORT_PAGE = "nonPerformedRunnersReportPage";
  public static final String TOP_DISTRIBUTORS_REPORT_PAGE = "topDistributorsReportPage";
  public static final String TOP_REGIONS_REPORT_PAGE = "topRegionsReportPage";
  public static final String TOP_ZONES_REPORT_PAGE = "topZonesReportPage";
  public static final String TOP_HUBS_REPORT_PAGE = "topHubsReportPage";
  public static final String TOP_RUNNERS_REPORT = "topRunnersReport";
  public static final String TOP_DISTRIBUTORS_REPORT = "topDistributorsReport";
  public static final String TOP_REGIONS_REPORT = "topRegionsReport";
  public static final String TOP_ZONES_REPORT = "topZonesReport";
  public static final String TOP_HUBS_REPORT = "topHubsReport";
  public static final String ATTENDANCE_REPORT = "attendance";
  public static final String NONPERFORMED_RUNNERS_REPORT = "nonPerformedRunnersReportReport";
  public static final String VISITS_VS_BEATPLAN_REPORT = "visitsVsBeatPlanReportReport";
  public static final String RBM_REPORTS_DASHBOARD_PAGE = "rbmReportsDashboardPage";
  public static final String ZBM_REPORTS_DASHBOARD_PAGE = "zbmReportsDashboardPage";
  public static final String ASM_REPORTS_DASHBOARD_PAGE = "asmReportsDashboardPage";
  public static final String TSM_REPORTS_DASHBOARD_PAGE = "tsmReportsDashboardPage";
  public static final String CBM_REPORTS_DASHBOARD_PAGE = "cbmReportsDashboardPage";
  
  public static final String HUB_PERFORMANCE_REPORT = "hubPerformanceReport";
  public static final String ZONE_PERFORMANCE_REPORT = "zonePerformanceReport";
  
  public static final String CAF_DATA_ENTRY_PAGE = "cafDataEntryPage";

  // Constants for Runners
  public static final String RUNNER_DATE = "runnerDate";
  public static final String RUNNER_TIME = "runnerTime";
  public static final String HUB_NAME = "hubName";
  public static final String RUNNER_NAME = "runnerName";
  public static final String RUNNER_CODE = "runnerCode";
  public static final String BEAT_PLAN_NAME = "beatPlanName";
  public static final String RUNNER_VISITED_LOCATION_LIST = "runnerVisittedLocationsList";
  public static final String RUNNER_TRACK_DETAILS_LIST = "runnerTrackDetailsList";

  public static final String ZONE_NAME = "zoneName";
  public static final String DISTRIBUTORS = "dsitributors";
  public static final String RUNNERS = "runners";
  public static final String SESSION_INACTIVE_PAGE = "sessionInactivePage";
  public static final String INFO_OR_ERROR_PAGE = "infoOrErrorPage";
  public static final String INFO_PAGE = "infoPage";
  

  public static final String ASSIGNED_CIRCLE_ID = "assignedCircleId";
  public static final String ASSIGNED_REGION_ID = "assignedRegionId";
  public static final String ASSIGNED_ZONE_ID = "assignedZoneId";
  public static final String ASSIGNED_HUB_ID = "assignedHubId";
  public static final String ASSIGNED_HUB_NAME = "assignedHubName";

  public static final String OK = "OK";
  public static final String NO = "NO";

  public static final String PHONE_NO = "phoneNo";
  
  public static String APP_DATA_PATH = "/usr/local/apache-tomcat-7.0.42/webapps/tracer_app_data";
  public static String UPLOAD_PHOTOS_PATH = "user_photos";
  public static String UPLOAD_FILES_PATH = "user_files";
  
  public static String SMS_API_STATUS = "off";

  // Constants for Upload 
  public static final String ADD_ONLINE_CAFS_PAGE = "uploadOnlineCafsPage";
  public static final String VIEW_ONLINE_CAFS_PAGE = "viewOnlineCafsPage";
  public static final String VERIFY_ONLINE_CAFS_PAGE = "verifyUploadedOnlineCafsPage";
  
  public static final String UPLOADED_CAF_LIST = "uploadedCaflist";
  
  //Constants for Bulk Upload
  public static final String BEAT_PLAN_BULK_UPLOAD_PAGE = "beatPlanBulkUploadPage";
  public static final String UPLOAD_BEAT_PLAN = "uploadBeatPlan";
  public static final String MANAGE_DISTRIBUTOR_BEAT_PLANS_PAGE = "manageDistributorBeatPlansPage";
  
  // Constants supported in BeatPlan template excel
  public static final short CIRCLE_NAME_COL_NO = 0;
  public static final short REGION_NAME_COL_NO = 0;
  public static short ZONE_NAME_COL_NO = 0;
  public static short HUB_NAME_COL_NO = 0;
  public static short DISTRIBUTOR_NAME_COL_NO = 0;
  public static final short DISTRIBUTOR_PHONE_NO_COL_NO = 0;
  public static short DISTRIBUTOR_TOWN_COL_NO = 0;
  public static short DISTRIBUTOR_DISTRICT_COL_NO = 0;
  public static short OFSC_CODE_COL_NO = 0;
  public static  short PICKUP_FREQUENCY_COL_NO = 0;
  public static  short ASM_COL_NO = 0;
  public static  short ASM_MOB_NO_COL_NO = 0;
  public static  short TSE_OR_TSM_NAME_COL_NO = 0;
  public static  short TSE_OR_TSM_MOB_NO_COL_NO = 0;
  public static  short TSE_OR_TSM_ROLE_COL_NO = 0;
  public static  short REGION_COL_NO = 0;
  public static  short DISTRIBUTOR_MOBILE_NO_COL_NO = 0;
  public static  String ROLE_TSM = "TSM";
  public static  String ROLE_TSE = "TSE";
  public static  String ROLE_ASM = "ASM";
  public static  String INVALID_MOBILE_ERROR_MESSAGE = "Invalid Mobile number";
  
  
  //Upload online cafs
  public static final String NEW_CAF = "0";
  public static final String CAF_ACCEPTED = "1";
  public static final String CAF_REJECTED = "2";
  public static final String CUSTOMER_NOT_INTERESTED = "3";
  public static final String IN_COMPLETE_DOCUMENTATION = "4";
  // Non DB Fields
  public static final String DISTRIBUTOR_NOT_FOUND = "5";
  public static final String DISTRIBUTOR_IN_ACTIVE = "6";
  
  public static final String INVALID_CAFS = "<i class=\"fa fa-check-circle-o\"></i>   Invalid CAFs !!";
  
}