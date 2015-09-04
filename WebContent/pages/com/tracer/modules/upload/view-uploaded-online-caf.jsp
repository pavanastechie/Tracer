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
      function loadOnlineCafDetails(){
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
              "sAjaxSource": "upload.do?method=getUploadedCAFs",
                "aoColumns": [
                            {"aTargets": [ 0 ], "mDataProp": "mobileNo", "bSortable": true, "sClass": "upload-onlineCaf-row-mobileNo"},
                            {"aTargets": [ 1 ], "mDataProp": "orderNumber", "bSortable": true, "sClass": "upload-onlineCaf-row-orderNumber" },
                            {"aTargets": [ 2 ], "mDataProp": "distributorName", "bSortable": true, "sClass": "upload-onlineCaf-row-distributorName" },
                            {"aTargets": [ 3 ], "mDataProp": "location", "bSortable": true, "sClass": "upload-onlineCaf-row-distributorName" },
                            {"aTargets": [ 4 ], "mDataProp": "cafStatusAsString", "bSortable": true, "sClass": "upload-onlineCaf-row-status" },
                        ],
                        "oTableTools": {
                        "sSwfPath": "<%=request.getContextPath()%>/js/dataTables/swf/copy_csv_xls_pdf.swf",
                        "aButtons": [
                                     {
                                         "sExtends": "csv",
                                         "sFileName": "OnlineCAF.csv",
                                         "mColumns": [ 0,1,2,3]
                                     },
                                     {
                                         "sExtends": "xls",
                                         "sFileName": "OnlineCAF.xls",
                                         "mColumns": [ 0,1,2,3]
                                     },
                                     {
                                         "sExtends": "pdf",
                                         "sFileName": "OnlineCAF.pdf",
                                         "mColumns": [ 0,1,2,3]
                                     },
                                 ]
                     },
                     "aaSorting": [[ 0, "asc" ]]
            });
        };
        
        function submitForm() {
            document.forms[0].action ="upload.do?method=showUploadedCAFs";
            document.forms[0].submit();
        }
        
    </script>
  </head>
  <body onload="loadOnlineCafDetails()">
    <div class="clear"></div>
    <html:form action="/upload.do">
      <div id="form-content">
        <div id="titleheading">
          <div class="navtitle1">
            <bean:message key="label.view.uploaded.online.caf" />
          </div>
          <br />
          <div id="titleheadingbar">
            <div class="navtitle">
              <i class="fa fa-upload"></i>
              <bean:message key="label.upload" />
              / 
              <bean:message key="label.view.uploaded.online.caf" />
            </div>
          </div>
        </div>
        <logic:messagesPresent message="true">
          <ul id="messsages1">
            <html:messages id="msg" message="true">
              <li>
                <bean:write name="msg" filter="false"/>
              </li>
            </html:messages>
          </ul>
        </logic:messagesPresent>
        <div>
          <div class="upload-divheading" id="flip">
            <bean:message key="label.view.caf" />
            <i class="fa fa-chevron-down downarrow"></i> 
          </div>
          <div class="upload-divcontent" id="panel">
            <div id='errors-container'>
              <ul></ul>
            </div>
            <div class="upload-content">
              <html:text property="dateTime" styleClass="runnerTextbox datePicker" readonly="true">
                <logic:notEmpty property="dateTime" name="uploadForm">
                  <bean:write name="uploadForm" property="dateTime" />
                </logic:notEmpty>
              </html:text>
              <html:submit value="GO" styleClass="btn btn-primary gobtn" styleId="gobtn"  onclick="submitForm()">GO</html:submit>
            </div>
          </div>
        </div>
        <div class="gapdiv"></div>
        <div class="table-data-upload">
          <div class="table-heading">
            <bean:message key="label.caf.details" />
          </div>
          <div id="table-content">
            <table id="upload-onlineCaf-table" class="records-page">
              <thead>
                <tr class="manage-distributors-content-headings datatable-header">
                  <th class="upload-online-content-heading-mobileNo" style="width: 15%; padding-left: 2px;">Mobile No</th>
                  <th class="upload-onlineCaf-row-orderNumber" style="width: 15%">Order Number</th>
                  <th class="upload-onlineCaf-row-orderNumber" style="width: 25%">Distributor Name</th>
                  <th class="upload-onlineCaf-row-orderNumber" style="width: 15%">Location</th>
                  <th class="upload-online-content-heading-status" style="width: 10%" >Status</th>
                  <th class="manage-users-buttons"></th>
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