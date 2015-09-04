<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%> 
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<%@ page import="com.tracer.webui.presentation.form.NotificationForm" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <% response.setHeader("Cache-Control", "no-cache"); %>
    <script>
      var  oTable;
      var runnersBeatPlanTable;
      
      //======================================================================
      
      function loadRunners() {
        var extensions = {
          "sFilter": "dataTables_filter manage_custom_filter_class",
          "sLength": "dataTables_length manage_custom_length_class",
          "sWrapper": "dataTables_wrapper",
          "sStripeOdd": "dataTables-odd-row",
          "sStripeEven ": "dataTables-even-row"
        };
        $.extend($.fn.dataTableExt.oStdClasses , extensions);
        oTable = $(".runners-template-table").dataTable({
          "sDom": 'T<"clear">lfrtip',
          "sPaginationType": "full_numbers",
          "bJQueryUI": false,
          "bProcessing": true,
          "oLanguage": {
            "sProcessing": "<i class='fa fa-spinner fa-spin'></i>",
            "sEmptyTable": "No Data Available"
          },
          "sAjaxDataProp":"",
          "sAjaxSource": "track.do?method=getRunnersDetails&id=<bean:write name='trackForm' property='runnersHeadUserId'/>",
          "aoColumnDefs": [
            {"aTargets": [ 0 ], "mData": "runnerName", "bSortable": true, "sClass": "manage-runner-row-category"},
            {"aTargets": [ 1 ], "mData": "temLeaderCode", "bSortable": true, "sClass": "manage-runner-row-content" },
            {"aTargets": [ 2 ], "mData": "contactNo", "bSortable": true, "sClass": "manage-runner-row-content" },
            {"aTargets": [ 3 ], "mData": "reportingTo", "bSortable": true, "sClass": "manage-runner-row-content" },
            {"aTargets": [ 4 ],"sClass": "manage-runners-row-button" ,"bSortable": false,"mData": "runnerId", "mRender": function ( data, type, full ) {
              return '<div class="manage-runner-button"><a href="track.do?method=showRunnerCAFCollectionDetailsPage&id='+data+'" title=Track><i class="fa fa-search manageRunner"></i></a> </div>'+
                '<div class="manage-runner-button"><a href="#" title="Send SMS" data-toggle="modal" data-target="#mySMS" onclick="getSmsCategories('+data+')"><i class="fa fa-envelope runner-sms-icon"></i></a> </div>'+
                '<div class="manage-runner-button"><a href="#" title="View Beat Plan" data-toggle="modal" data-target="#myBeatPlan" onclick="getRunnerBeatPlanDetails('+data+')"><i class="fa fa-file-text-o runner-view-beat-plan-icon"></i></a> </div>' +
                '<div class="manage-runner-button"><a href="#" title="View Attendance" data-toggle="modal" data-target="#viewAttendance" onclick="showRunnerAttendace('+data+')"><i class="fa fa-user runner-view-beat-plan-icon '+full.attendanceClass+'"></i></a> </div>';
              }
            },
          ],
          "oTableTools": {
            "sSwfPath": "<%=request.getContextPath()%>/js/dataTables/swf/copy_csv_xls_pdf.swf",
            "aButtons": [
	            {
	              "sExtends": "csv",
	              "sFileName": "Track Runners.csv",
	              "mColumns": [ 0, 1,2,3]
	            },
	            {
                "sExtends": "xls",
                "sFileName": "Track Runners.xls",
                "mColumns": [ 0, 1,2,3]
	            },
	            {
                "sExtends": "pdf",
                "sFileName": "Track Runners.pdf",
                "mColumns": [ 0, 1,2,3]
	            },
            ]
          },
          "aaSorting": [[ 1, "desc" ]]
        });
      }
      
      //======================================================================
      
      function loadRunnerBeatPlanDetails(beatPlanDetails) {
        var extensions = {
          "sWrapper": "dataTables_wrapper",
          "sStripeOdd": "dataTables-odd-row",
          "sStripeEven ": "dataTables-even-row"
        };
        $.extend($.fn.dataTableExt.oStdClasses , extensions);
        runnersBeatPlanTable = null;
        runnersBeatPlanTable = $(".runner-beat-plan-table").dataTable({
          "sPaginationType": "full_numbers",
          "bJQueryUI": false,
          "bProcessing": true,
          "bFilter": false,
          "bLengthChange": false,
          "oLanguage": {
            "sProcessing": "<i class='fa fa-spinner fa-spin'></i>",
            "sEmptyTable": "No Data Available"
          },
          "aaData":beatPlanDetails, "aoColumnDefs": [
             {"aTargets": [ 0 ], "mData": "scheduleTime", "bSortable": true, "sClass": "manage-runner-beat-plan-schedule-time"},
             {"aTargets": [ 1 ], "mData": "distributorName", "bSortable": true, "sClass": "manage-runner-beat-plan-distributor-name"},
             {"aTargets": [ 2 ], "mData": "visitNo", "bSortable": true, "sClass": "manage-runner-beat-plan-visit-frequency"},
             {"aTargets": [ 3 ], "mData": "hubName", "bSortable": true, "sClass": "manage-runner-beat-plan-hub-name"},
             {"aTargets": [ 4 ], "mData": "zoneName","bSortable": false,"sClass": "manage-runner-beat-plan-zone-name"},
             {"aTargets": [ 5 ], "mData": "regionName","bSortable": false,"sClass": "manage-runner-beat-plan-region-name"},
             {"aTargets": [ 6 ], "mData": "circleName","bSortable": false,"sClass": "manage-runner-beat-plan-circle-name"}
           ],
           "aaSorting": [[ 6, "desc" ]],
           "fnInitComplete": function(oSettings, json) {
             console.log( 'DataTables has finished its initialisation.' +json);
           }
         });
      }
      
      //======================================================================
      
      function destroyRunnerBeatPlanTable() {
        runnersBeatPlanTable.fnDestroy();
      }
      
      //======================================================================
      
      var runnerId = "";
      
      function getTemplateDetails() {
        var categoryId=$("#sms-template-category").val();
        
        if(categoryId == "") {
          return false;
        } else {
          $("#mySMS .error-overlay").text("");
          $("#sms-template-content").val("");
          $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/notification.do?method=getSMSTemplate",
            data:"smsCategoryId="+categoryId,
            success: function(response) {
              response = jQuery.parseJSON(response);
              if((response.result).toLowerCase() == "ok"){
                $("#sms-template-content").val(response.smsContent);
              } else {
                alert("No Template Available");
              }
            },
            error: function(response) {
              console.log("error..."+response);
            }
          });
        }
      }
      
      //======================================================================
      
      function sendSMS() {
        var smsContent = $("#mySMS #sms-template-content").val();
        
        if($("#sms-template-category").val() == ""){
          $("#mySMS .error-overlay").text("please select Category");
          return false;
        } else if(smsContent == "") {
          $("#mySMS .error-overlay").text("please enter Content ");
          return false;
        } else {
          var data ={};
          data["smsContent"] = smsContent;
          data["runnerId"] = "'"+runnerId+"'";
          var request = [];
          request.push(data);
          $.ajax({
            type: "GET",
            url: "<%=request.getContextPath()%>/notification.do?method=sendSMS&smsData=" + JSON.stringify(request),
            contentType: 'application/json',
            success: function(response){
              if(response != "" && response.toLowerCase() == "ok"){
                clearSmsPopUpValues();
                $("#mySMS").modal("hide");
                alert("SMS sent Successfully");
              } else{
                alert("Failed to Send SMS");
              }
            },
            error: function(response){
              alert("Failed to Send SMS");
            }
          });
        }
      }
      
      //======================================================================
      
      function clearSmsPopUpValues(){
        $("#mySMS #sms-template-content").val("");
        $("#mySMS #sms-template-category").val("");
        $("#mySMS .error-overlay").text("");
      }
      
      //======================================================================
      
      function getSmsCategories(id) {
        runnerId = id;
        clearSmsPopUpValues();
        $("#mySMS").modal("show");
        
        if($("#sms-template-category option").length <= 1 ) {
          $.ajax({
            type: "GET",
            url: "<%=request.getContextPath()%>/notification.do?method=getSMSCategories",
            success: function(data) {
              var categories = jQuery.parseJSON(data);
              if(data != null && categories.length > 0) {
                $.each(categories, function (index, category) {
                  $("#sms-template-category").append("<option value='"+category.smsCategoryId+"'>"+category.smsCategoryName+"</option>");
                });
              } else {
                alert("No Sms Categories Available");
              }
            },
            error: function(response) {
              alert("Failed to get SMS catgories.Please Try again later.");
            }
          });
        }
      }
      
      //======================================================================
          
      function getRunnerBeatPlanDetails(runnerId) {
        $.ajax({
          type: "GET",
          contentType: 'application/json',
          url: "<%=request.getContextPath()%>/track.do?method=getRunnerBeatPlanDetails&runnerId="+runnerId,
          success: function(response) {
            var beatPlanDetails = jQuery.parseJSON(response);
            if(beatPlanDetails.length > 0) {
              var planDetails = [];
              for(var i = 0; i < beatPlanDetails.length; i++) {
                if(beatPlanDetails[i].beatPlanName != undefined) {
                  $("#runner-beat-plan-name").text(beatPlanDetails[i].beatPlanName);
                } else {
                  planDetails.push(beatPlanDetails[i]);
                }
              }
              loadRunnerBeatPlanDetails(planDetails);
            }
          },
          error: function(response){
            console.log("error..."+response);
          }
        });
      }
          
      //======================================================================
          
      function showRunnerAttendace(runnerUserId) {
        $.ajax({
          type: "GET",
          contentType: 'application/json',
          url: "<%=request.getContextPath()%>/track.do?method=getRunnerAttendanceDetails&runnerUserId="+runnerUserId,
          success: function(response) {
            var runnerAttendanceDetails = jQuery.parseJSON(response);
            $("#attendance-time").text(runnerAttendanceDetails.time);
            $("#attendance-lattitude").text(runnerAttendanceDetails.lattitude);
            $("#attendance-longitude").text(runnerAttendanceDetails.longitude);
            
            if(runnerAttendanceDetails.isRunnerPresent == true && runnerAttendanceDetails.runnerPhoto != "" && runnerAttendanceDetails.runnerPhoto != null){
              runnerAttendanceDetails.runnerPhoto = "/"+runnerAttendanceDetails.runnerPhoto;
              $("#runner-photo").append("<img src='"+runnerAttendanceDetails.runnerPhoto+"' alt=''/>");
              $(".runner-photo-error-overlay").text("");
            } else {
              $(".runner-photo-error-overlay").text("Runner is Absent");
            }
          },
          error: function(response){
            console.log("error..."+response);
          }
        });
      }
          
      //======================================================================
      
      function clearRunnerPhotoValues(){
        $("#runner-photo").text("");
        $(".runner-photo-error-overlay").text("");
      }
      
      //======================================================================
    </script>
  </head>
  <body onload="loadRunners()">
    <div class="clear"></div>
    <html:hidden property="runnersHeadUserId" name="trackForm" styleId="trackRunnersForUserId"/>
    <div id="form-content">
      <div id="titleheading">
        <div class="navtitle1">Track Runners</div>
        <br />
        <div id="titleheadingbar">
          <div class="navtitle">
            <i class="fa fa-envelope "></i></label>
            <bean:message key="label.track" />
            /
            <bean:message key="label.runners" />
          </div>
        </div>
        <div class="table-data">
          <div class="table-heading">Runners Details</div>
          <div id="table-content">
            <table width="100%" align="center" cellpadding="0" cellspacing="0"
              border="0" class="records-page runners-template-table">
              <thead>
                <tr class="runners-content-headings">
                  <th class="runners-content-heading-name">Runner Name</th>
                  <th class="runners-content-heading-content">Runner Code</th>
                  <th class="runners-content-heading-name">Contact Number</th>
                  <th class="runners-content-heading-content">Reporting To</th>
                  <th class="runners-content-heading-manage-button">&nbsp;</th>
                </tr>
              </thead>
              <tbody>
            </table>
            <div id="status"></div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- Modal for SMS -->
    <div class="modal fade" id="mySMS" tabindex="-1" role="dialog"
      aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog modeldiv-top">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"
              aria-hidden="true" onclick="clearSmsPopUpValues()">&times;</button>
            <h4 class="modal-title" id="myModalLabel">
              <bean:message key="label.send.sms"></bean:message>
            </h4>
            <div class="error-overlay"><br/></div>
          </div>
          <form action="javascript:void(0);" name="sendSMSOverlay" onsubmit="sendSMS(this);"  class="send-sms">
            <div class="modal-body">
              <div class="sms-template-details">
                <div class="sms-template-category-details">
                  <div class="sms-template-category-label"><label>SMS Category*</label></div>
                  <div class="sms-template-category-options">
                    <select id="sms-template-category" class="sms-template-category" onchange="javascript:getTemplateDetails()" size="1">
                      <option value="">Please Select Category</option>
                    </select>
                  </div>
                </div>
                <div class="sms-template-content">
                  <div class="sms-template-content-label"><label>SMS Content*</label></div>
                  <div class="sms-template-content-text"><textarea  id="sms-template-content" class="form-control" rows="4" cols="55"></textarea></div>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <input type="submit" class="btn btn-primary region-done-btn" value="Send SMS"/>
            </div>
          </form>
        </div>
        <!-- /.modal-content -->
      </div>
      <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
    
    <!-- Modal for View Beat Plan-->
    <div class="modal fade" id="myBeatPlan" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" onclick="destroyRunnerBeatPlanTable()" data-dismiss="modal"
              aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">Beat Plan Name:</h4>
            <span id="runner-beat-plan-name"></span>
            <div class="error-overlay hide">
              <span>
                <bean:message key="label.hub.exist"></bean:message>
              </span>
            </div>
          </div>
          <div class="modal-body">
            <table width="100%" align="center" cellpadding="0" cellspacing="0"
              border="0" class="records-page runner-beat-plan-table">
              <thead>
                <tr class="runner-beat-plan-content-headings">
                  <th class="runner-beat-plan-content-heading-schedule-time">Schedule Time</th>
                  <th class="runner-beat-plan-content-heading-distributor">Distributor Name</th>
                  <th class="runner-beat-plan-content-heading-visit-frequency">Visit No</th>
                  <th class="runner-beat-plan-content-heading-hub">Hub</th>
                  <th class="runner-beat-plan-content-heading-zone">Zone</th>
                  <th class="runner-beat-plan-content-heading-region">Region</th>
                  <th class="runner-beat-plan-content-heading-circle">Circle</th>
                </tr>
              </thead>
              <tbody>
            </table>
          </div>
        </div>
        <!-- /.modal-content -->
      </div>
      <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
    
    <!-- Modal for Runner Photo -->
    <div class="modal fade" id="viewAttendance" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
      <div class="modal-dialog">
        <div class="modal-content runner-distributor-info">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="clearRunnerPhotoValues()">&times;</button>
            <h4 class="modal-title" id="myModalLabel">
              <bean:message key="label.runner.attendance"></bean:message>
            </h4>
          </div>
          <div>
             <span class="attendance-details-modal">
              <b>Time:</b> <span id="attendance-time"></span>
            </span>
            <br />
            <span class="attendance-details-modal">
              <b>Lattitude: </b><span id="attendance-lattitude"> </span><b>&nbsp;&nbsp;&nbsp;Longitude: </b><span id="attendance-longitude"></span>
            </span>
          </div>
          <div class="modal-body">
            <div class="runner-photo-error-overlay">
              <br />
            </div>
            <div id="runner-photo" class="runner-distributor-images"></div>
          </div>
        </div>
      </div>
    </div>
    <!-- /.modal -->
  </body>
</html>