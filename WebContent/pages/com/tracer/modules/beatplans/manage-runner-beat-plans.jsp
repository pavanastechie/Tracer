<%@page import="org.json.JSONArray"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
  prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<%@ page import="org.json.*"%>
<!DOCTYPE html>
<html>
  <head>
    <script type="text/javascript">
      function loadRunnerBeatPlanDetails(){
          var extensions = {
                  "sFilter": "dataTables_filter manage_custom_filter_class",
                  "sLength": "dataTables_length manage_custom_length_class",
                  "sWrapper": "dataTables_wrapper",
                  "sStripeOdd": "dataTables-odd-row",
                  "sStripeEven ": "dataTables-even-row"
              }
        /*   $.extend($.fn.dataTableExt.oJUIClasses, extensions); */
         $.extend($.fn.dataTableExt.oStdClasses , extensions);
            $("#manage-beat-plan-table").dataTable({
                "sDom": 'T<"clear">lfrtip',
                "sPaginationType": "full_numbers",
                "bJQueryUI": true,
                "bProcessing": true,
                "sAjaxDataProp":"",
                "sAjaxSource": "beatPlans.do?method=getRunnerBeatPlanDetailsList",
                "aoColumns":  [
                                {"aTargets": [ 1 ], "mDataProp": "beatPlanName", "bSortable": true, "sClass": "manage-users-content-heading-last-name"},
                                {"aTargets": [ 2 ], "mDataProp": "beatPlanCode", "bSortable": true, "sClass": "manage-users-row-name"},
                                {"aTargets": [ 3 ], "mDataProp": "hubName", "bSortable": true, "sClass": "manage-users-row-name"},
                                {"aTargets": [ 4 ], "mDataProp": "zoneName", "bSortable": true, "sClass": "manage-users-row-name"},
                                {"aTargets": [ 5 ], "mDataProp": "regionName", "bSortable": true, "sClass": "manage-users-row-name"},
                                {"aTargets": [ 6 ], "mDataProp": "circleName", "bSortable": true, "sClass": "manage-users-row-name"},
                                {"aTargets": [ 7 ], "bSortable": false, "sClass": "manage-sms-row-buttons" ,"mData": "beatPlanId",
                                "mRender": function ( data, type, full ) {
                                    return '<div class="manage-sms-row-buttons-edit"><a id="beat_plan_manage_runner_edit_'+data+'" href="beatPlans.do?method=editRunnerBeatPlan&viewType=edit&beat_plan_id='+data+'" title=Edit><i class="fa fa-edit"></i></a> </div>'+
                                      '<div class="manage-sms-row-buttons-delete"><a id="beat_plan_manage_runner_delete_'+data+'" href="beatPlans.do?method=deleteRunnerBeatPlan&beat_plan_id='+data +'" title=Delete><i class="fa fa-trash-o"></i></a></div>';  
                                }
                              }
                          ],
               "oTableTools": {
                   "sSwfPath": "<%=request.getContextPath()%>/js/dataTables/swf/copy_csv_xls_pdf.swf",
                  "aButtons" : [ {
                    "sExtends" : "csv",
                    "sFileName" : "Runner Beat Plan.csv",
                    "mColumns" : [ 0, 1, 2, 3, 4, 5 ]
                  }, {
                    "sExtends" : "xls",
                    "sFileName" : "Runner Beat Plan.xls",
                    "mColumns" : [ 0, 1, 2, 3, 4, 5 ]
                  }, {
                    "sExtends" : "pdf",
                    "sFileName" : "Runner Beat Plan.pdf",
                    "mColumns" : [ 0, 1, 2, 3, 4, 5 ]
                  }, ]
                },
                "aaSorting" : [ [ 0, "asc" ] ]
              });
      }
      $(document)
        .on(
            'click',
            '.manage-sms-row-buttons-delete a',
            function(e) {
              var link = this;
              e.preventDefault();
              $(
                  "<div title='Delete BeatPlan'>Are you sure ? Do you want to delete the BeatPlan?</div>")
                  .dialog({
                    resizable : false,
                    modal : true,
                    buttons : {
                      "Cancel" : function() {
                        $(this).dialog("close");
                      },
                      "Delete" : function() {
                        window.location = link.href;
                        $(this).dialog("close");
                      }
                    }
                  });
            });
      function successNotification(msg) {
      notif({
        msg : msg,
        type : "success",
        position: "center"
      });
      }
      function failureNotification() {
      notif({
          type: "warning",
          msg: msg,
          position: "center",
          bgcolor: "#FF0000",
          autohide: false
        });
      }
      <%if (request.getAttribute("successMessage") != null) {%>
      successNotification('<%=request.getAttribute("successMessage")%>');
      <%} else if (request.getAttribute("errorMessage") != null) {%>
      failureNotification('<%=request.getAttribute("errorMessage")%>');
      <%}%>
    </script>
  </head>
  <body onload="loadRunnerBeatPlanDetails();">
    <div class="clear"></div>
    <div id="success"></div>
    <div id="form-content">
      <div id="titleheading">
        <div class="navtitle1">Manage Runner Beat Plan</div>
        <br />
        <div id="titleheadingbar">
          <div class="navtitle">
            <i class="fa fa-user"></i> Beat Plans / <label>Manage Runner Beat Plan</label>
          </div>
        </div>
        <div class="table-data">
          <div class="table-heading">Runner Beat Plan Details</div>
          <div id="table-content">
            <table id="manage-beat-plan-table" width="100%" align="center"
              cellpadding="0" cellspacing="0" border="0" class="records-page">
              <thead>
                <tr class="manage-users-content-headings">
                  <th class="manage-users-content-heading-name" style="width: 15%">Beat Plan Name</th>
                  <th class="manage-users-content-heading-hub" style="width: 15%">Beat Plan Code</th>
                  <th class="manage-users-content-heading-contact-number" style="width: 15%">Hub Name</th>
                  <th class="manage-users-content-heading-reporting-to" style="width: 15%">Zone Name</th>
                  <th class="manage-users-content-heading-reporting-to" style="width: 15%">Region Name</th>
                  <th class="manage-users-buttons" style="width: 15%">Circle Name</th>
                  <th class="manage-users-content-heading-distid123" style="width:10%;"></th>
                </tr>
              </thead>
              <tbody>
              </tbody>
            </table>
            <div id="status"></div>
          </div>
        </div>
      </div>
    </div>
    <input type="hidden" name="<%=Constants.TOKEN_KEY%>"
      value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>">
  </body>
</html>