<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
  <head>
    <script type="text/javascript">
      $(document).ready(function() {
        $('#report-error-msg').text('');
      });
      function getHubPerformanceReport(){
          var extensions = {
                  "sFilter": "dataTables_filter manage_custom_filter_class",
                  "sLength": "dataTables_length manage_custom_length_class",
                  "sWrapper": "dataTables_wrapper",
                  "sStripeOdd": "dataTables-odd-row",
                  "sStripeEven ": "dataTables-even-row"
              };
          /* $.extend($.fn.dataTableExt.oJUIClasses, extensions); */
          $.extend($.fn.dataTableExt.oStdClasses , extensions);
            $("#upload-onlineCaf-table").dataTable({
                "sDom": 'T<"clear">lfrtip',
                "sPaginationType": "full_numbers",
                "oLanguage": {
                    "sProcessing": "<i class='fa fa-spinner fa-spin'></i>",
                    "sEmptyTable": "No Data Available"
                },
                "bJQueryUI": true,
                "bProcessing": false,
                "sAjaxDataProp":"",
              "sAjaxSource": "reports.do?method=getHubPerformanceReport",
                "aoColumns": [
                            {"aTargets": [ 0 ], "mDataProp": "date", "bSortable": true, "sClass": "upload-onlineCaf-row-mobileNo"},
                            {"aTargets": [ 1 ], "mDataProp": "hubName", "bSortable": true, "sClass": "upload-onlineCaf-row-orderNumber" },
                            {"aTargets": [ 2 ], "mDataProp": "runnerName", "bSortable": true, "sClass": "upload-onlineCaf-row-distributorName" },
                            {"aTargets": [ 3 ], "mDataProp": "distributorName", "bSortable": true, "sClass": "upload-onlineCaf-row-distributorName" },
                            {"aTargets": [ 4 ], "mDataProp": "ofscCode", "bSortable": true, "sClass": "upload-onlineCaf-row-status" },
                            {"aTargets": [ 5 ], "mDataProp": "scheduleTime", "bSortable": true, "sClass": "upload-onlineCaf-row-status" },
                            {"aTargets": [ 6 ], "mDataProp": "visitedTime", "bSortable": true, "sClass": "upload-onlineCaf-row-status" },
                            {"aTargets": [ 7 ], "mDataProp": "cafsCollected", "bSortable": true, "sClass": "upload-onlineCaf-row-status" },
                        ],
                        "oTableTools": {
                        "sSwfPath": "<%=request.getContextPath()%>/js/dataTables/swf/copy_csv_xls_pdf.swf",
                        "aButtons": [
                                     {
                                         "sExtends": "csv",
                                         "sFileName": "Hub_Performance_Details_Report.csv",
                                         "mColumns": [0,1,2,3,4,5,6,7]
                                     },
                                     {
                                         "sExtends": "xls",
                                         "sFileName": "Hub_Performance_Details_Report.xls",
                                         "mColumns": [0,1,2,3,4,5,6,7]
                                     },
                                     {
                                         "sExtends": "pdf",
                                         "sFileName": "Hub_Performance_Details_Report.pdf",
                                         "mColumns": [0,1,2,3,4,5,6,7]
                                     },
                                 ]
                     },
                     "aaSorting": [[ 0, "asc" ]]
            });
        };
        
        function showHubPerformanceReportPage() {
          var hubId = document.forms[0].hubId.value;
          if(hubId > 0) {
            document.forms[0].action ="reports.do?method=showHubPerformanceReportPage";
            document.forms[0].submit();
          } else {
            $('#report-error-msg').text('Please select Hub');
            return false;
          }
        }
        
    </script>
  </head>
  <body onload="getHubPerformanceReport()">
    <div class="clear"></div>
    <html:form action="/reports.do">
      <div id="form-content">
        <div id="titleheading">
          <div class="navtitle1">
            Hub Performance Report
          </div>
          <br />
          <div id="titleheadingbar">
      <div class="navtitle"><i class="fa fa-clipboard"></i>  <bean:message key="label.reports"/> /  <label>Hub Performance Report</label></div>
      <div id="nav3">
      <ul>
        <li>
          <a href="#"><i class="fa fa-chevron-down dropdowndashbd"></i>Reports</a>
          <ul>
            <li><a href="<%=request.getContextPath()%>/reports.do?method=showHubPerformanceReportPage"><i class="fa fa-road"></i> &nbsp;&nbsp;&nbsp;Hub Performance Report</a></li>
            <li><a href="<%=request.getContextPath()%>/reports.do?method=showZonePerformanceReportPage"><i class="fa fa-calendar"></i> &nbsp;&nbsp;&nbsp; Zone Performance Report</a></li>
        </li>
      </ul>
    </div>
    </div>
        </div>
        <div id="report-error-msg"></div>
        <div>
          <div class="upload-divheading" id="flip">
            Search Input Details
            <i class="fa fa-chevron-down downarrow"></i> 
          </div>
          <div class="upload-divcontent" id="panel">
            <div id='errors-container'>
              <ul></ul>
            </div>
            <div class="search-input-details-div">
              <bean:message key="label.reports.hubName"/>
                <html:select  property="hubId" styleClass="performance-report-dd" styleId="hubsList">
                <html:option value="0"><bean:message key="label.reports.select.hub"/></html:option>
                  <logic:notEmpty name="reportsForm" property="hubDetailsList">
                    <html:optionsCollection  property="hubDetailsList" label="hubName" value="hubId" />
                  </logic:notEmpty>
                </html:select>
                <bean:message key="label.reports.dateFrom"/>
              <html:text property="fromDate" styleClass="runnerTextbox datePicker" readonly="true">
                <logic:notEmpty property="fromDate" name="reportsForm">
                  <bean:write name="reportsForm" property="fromDate" />
                </logic:notEmpty>
              </html:text>
             <bean:message key="label.reports.dateTo"/>
               <html:text property="toDate" styleClass="runnerTextbox datePicker" readonly="true">
                <logic:notEmpty property="toDate" name="reportsForm">
                  <bean:write name="reportsForm" property="toDate" />
                </logic:notEmpty>
              </html:text>
              <input type="button" value="GO" class="btn btn-primary gobtn" id="gobtn"  onclick="showHubPerformanceReportPage()" />
            </div>
          </div>
        </div>
        <div class="gapdiv"></div>
        <div class="table-data-upload">
          <div class="table-heading">
            Hub Performance Details
          </div>
          <div id="table-content">
            <table id="upload-onlineCaf-table" class="records-page">
              <thead>
                <tr class="manage-distributors-content-headings datatable-header">
                  <th class="upload-online-content-heading-mobileNo">Date</th>
                  <th class="upload-onlineCaf-row-orderNumber">Hub</th>
                  <th class="upload-onlineCaf-row-orderNumber">Runner</th>
                  <th class="upload-onlineCaf-row-orderNumber">Distributor</th>
                  <th class="upload-onlineCaf-row-orderNumber">OFSC Code</th>
                  <th class="upload-onlineCaf-row-orderNumber">Schedule Time</th>
                  <th class="upload-onlineCaf-row-orderNumber">Visited Time</th>
                  <th class="upload-online-content-heading-status">CAFs Collected</th>
                </tr>
              </thead>
              <tbody>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </html:form>
  </body>
</html>