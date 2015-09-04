<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%> 
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<%@ page import="com.tracer.webui.presentation.form.NotificationForm" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <script>
  var  oTable;
  function loadTeamLeaders(){
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
          "sAjaxSource": "track.do?method=getTeamLeaderDetails",
          "aoColumnDefs": [
                      {"aTargets": [ 0 ], "mData": "runnerName", "bSortable": true, "sClass": "manage-teamleader-row-category"},
                      {"aTargets": [ 1 ], "mData": "temLeaderCode", "bSortable": true, "sClass": "manage-teamleader-row-content" },
                      {"aTargets": [ 2 ], "mData": "contactNo", "bSortable": true, "sClass": "manage-teamleader-row-content" },
                      {"aTargets": [ 3 ], "mData": "reportingTo", "bSortable": true, "sClass": "manage-teamleader-row-content" },
                      {"aTargets": [ 4 ],"sClass": "manage-teamleaders-row-button" ,"bSortable": false,"mData": "runnerId",
                        "mRender": function ( data, type, full ) {
                            return '<div class="manage-teamleader-button"><a href="track.do?method=showTrackRunnersPage&id='+data+'" title=Track><i class="fa fa-search manageRunner"></i></a> </div>';
                        }
                      },
                  ],
         "oTableTools": {
             "sSwfPath": "<%=request.getContextPath()%>/js/dataTables/swf/copy_csv_xls_pdf.swf",
             "aButtons": [
                          {
                              "sExtends": "csv",
                              "sFileName": "Team Leaders.csv",
                              "mColumns": [ 0, 1,2,3]
                          },
                          {
                              "sExtends": "xls",
                              "sFileName": "Team Leaders.xls",
                              "mColumns": [ 0, 1,2,3]
                          },
                          {
                              "sExtends": "pdf",
                              "sFileName": "Team Leaders.pdf",
                              "mColumns": [ 0, 1,2,3]
                          },
                      ]
          },
          "aaSorting": [[ 1, "desc" ]]
      });
  }
  </script>
</head>
<body onload="loadTeamLeaders()">
  <div class="clear"></div>
<div id="form-content">
  <div id="titleheading">
    <div class="navtitle1">Track Team Leaders</div>
    <br />
    <div id="titleheadingbar">
      <div class="navtitle"><i class="fa fa-envelope "></i></label>
      <bean:message key="label.track" />
            /
            <bean:message key="label.track.team.leaders" />
    </div></div>

    <div class="table-data">
      <div class="table-heading">Team Leader Details</div>
      <div id="table-content">
        <table width="100%" align="center" cellpadding="0" cellspacing="0"
          border="0" class="records-page runners-template-table">
          <thead>
          <tr class="teamleaders-content-headings">
            <th class="teamleaders-content-heading-name">Name</th>
            <th class="teamleaders-content-heading-content">Team Leader Code</th>
            <th class="teamleaders-content-heading-name">Contact Number</th>
            <th class="teamleaders-content-heading-content">Reporting To</th>
            <th class="teamleaders-content-heading-manage-button">Track</th>
          </tr>
          </thead>
          <tbody>
        </table>
        <div id="status"></div>
      </div>
    </div>
    </div>
  </div>
  </div>
</body>
</html>