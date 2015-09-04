<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%> 
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script>
      //======================================================================
      var  oTable;
      
      function loadDEOActivityData() {
        getCAFsToDataEntry();
        getCAFsToSubmit();
      }
      
      //======================================================================
      
      function downloadCAFImage() {
        document.dataEntryForm.action = "<%=request.getContextPath()%>/dataEntry.do?method=downloadCAF";
        document.dataEntryForm.submit(); 
      }
      //======================================================================
    
      function getCAFsToDataEntry() {
        var extensions = {
          "sFilter": "dataTables_filter manage_custom_filter_class",
          "sLength": "dataTables_length manage_custom_length_class",
          "sWrapper": "dataTables_wrapper",
          "sStripeOdd": "dataTables-odd-row",
          "sStripeEven ": "dataTables-even-row"
        };
        $.extend($.fn.dataTableExt.oStdClasses , extensions);
        
        oTable = $(".cafs-to-data-entry-table").dataTable({
          "sDom": 'T<"clear">lfrtip',
          "sPaginationType": "full_numbers",
          "bJQueryUI": true,
          "bProcessing": true,
          "oLanguage": {
            "sProcessing": "<i class='fa fa-spinner fa-spin'></i>",
            "sEmptyTable": "No Data Available"
          },
          "sAjaxDataProp":"",
          "sAjaxSource": "dataEntry.do?method=getCAFsToDataEntry",
          "aoColumnDefs": [
            {"aTargets": [ 0 ], "mData": "barCode", "bSortable": true, "sClass": "manage-sms-row-category"},
            {"aTargets": [ 1 ], "mData": "scannedCAFLocation", "bSortable": true, "sClass": "manage-sms-row-content" },
            {"aTargets": [ 1 ],"sClass": "manage-sms-content-heading-content" ,"bSortable": false,"mData": "scannedCAFLocation", "mRender": function ( data, type, full ) {
                return '<div class="manage-sms-row-buttons-edit"><a href="#" title="Download CAF Image" onclick="downloadCAFImage()"><i class="fa fa-download"></i> Click here to Download the CAF Image </a> </div>';
                }
              },
            {"aTargets": [ 2 ],"sClass": "manage-sms-content-heading-content" ,"bSortable": false,"mData": "cafId", "mRender": function ( data, type, full ) {
              return '<div class="manage-sms-row-buttons-edit"><a href="dataEntry.do?method=showCAFDataEntryPage" title="Save CAF Data"><i class="fa fa-edit"></i> Click here to Navigate to Data Entry</a> </div>';
              }
            },
          ],
          "oTableTools": {
            "sSwfPath": "<%=request.getContextPath()%>/js/dataTables/swf/copy_csv_xls_pdf.swf", "aButtons": [
              {
                 "sExtends": "csv",
                 "sFileName": "CAFS_To_Data_Entry.csv",
                 "mColumns": [ 0, 1]
               },
               {
                 "sExtends": "xls",
                 "sFileName": "CAFS_To_Data_Entry.xls",
                 "mColumns": [ 0, 1]
               },
               {
                 "sExtends": "pdf",
                 "sFileName": "CAFS_To_Data_Entry.pdf",
                 "mColumns": [ 0, 1]
               },
             ]
          },
          "aaSorting": [[ 1, "desc" ]]
        });
      }
      
      //======================================================================
      
      function getCAFsToSubmit() {
        var extensions = {
          "sFilter": "dataTables_filter manage_custom_filter_class",
          "sLength": "dataTables_length manage_custom_length_class",
          "sWrapper": "dataTables_wrapper",
          "sStripeOdd": "dataTables-odd-row",
          "sStripeEven ": "dataTables-even-row"
        };
        $.extend($.fn.dataTableExt.oStdClasses , extensions);
        
        oTable = $(".cafs-to-submit-table").dataTable({
          "sDom": 'T<"clear">lfrtip',
          "sPaginationType": "full_numbers",
          "bJQueryUI": true,
          "bProcessing": true,
          "oLanguage": {
            "sProcessing": "<i class='fa fa-spinner fa-spin'></i>",
            "sEmptyTable": "No Data Available"
          },
          "sAjaxDataProp":"",
          "sAjaxSource": "dataEntry.do?method=getCAFsToSubmit",
          "aoColumnDefs": [
            {"aTargets": [ 0 ], "mData": "barCode", "bSortable": true, "sClass": "manage-sms-row-category"},
            {"aTargets": [ 1 ], "mData": "saleDate", "bSortable": true, "sClass": "manage-sms-row-content" },
            {"aTargets": [ 2 ], "mData": "cellNo", "bSortable": true, "sClass": "manage-sms-row-content" },
            {"aTargets": [ 3 ], "mData": "firstName", "bSortable": true, "sClass": "manage-sms-row-content" },
            {"aTargets": [ 4 ], "mData": "lastName", "bSortable": true, "sClass": "manage-sms-row-content" },
            {"aTargets": [ 5 ], "mData": "city", "bSortable": true, "sClass": "manage-sms-row-content" },
            {"aTargets": [ 6 ], "mData": "state", "bSortable": true, "sClass": "manage-sms-row-content" },
            {"aTargets": [ 7 ], "mData": "pinCode", "bSortable": true, "sClass": "manage-sms-row-content" },
          ],
          "oTableTools": {
            "sSwfPath": "<%=request.getContextPath()%>/js/dataTables/swf/copy_csv_xls_pdf.swf", "aButtons": [
              {
                 "sExtends": "csv",
                 "sFileName": "CAFS_To_Submit.csv",
                 "mColumns": [ 0, 1, 2, 3, 4, 5, 6, 7]
               },
               {
                 "sExtends": "xls",
                 "sFileName": "CAFS_To_Submit.xls",
                 "mColumns": [ 0, 1, 2, 3, 4, 5, 6, 7]
               },
               {
                 "sExtends": "pdf",
                 "sFileName": "CAFS_To_Submit.pdf",
                 "mColumns": [ 0, 1, 2, 3, 4, 5, 6, 7]
               },
             ]
          },
          "aaSorting": [[ 1, "desc" ]]
        });
      }
      
      function successNotification(message) {
        notif({
          msg : message+" !!",
          type : "success",
          position: "center"
        });
      }
      <% if(request.getAttribute("successMessage") != null) {  %>
        successNotification("<%= request.getAttribute("successMessage") %>");
        <% request.removeAttribute("successMessage");
       } %>
      //======================================================================
    </script>
  </head>
  <body onload="loadDEOActivityData()">
  <div class="clear"></div>
    <html:form action="/dataEntry.do">
      <div id="form-content">
        <div id="titleheading">
          <div class="navtitle1">Data Entry Operator Activity Dashboard</div>
          <div class="dashboard-error"></div>
          <br />
          <div id="titleheadingbar">
            <div class="navtitle">
              <i class="fa fa-dashboard"></i> Data Entry Operator Activity Dashboard
            </div>
          </div>
          
          <div class="table-data">
            <div class="table-heading blue-table-heading">CAFs for Data Entry</div>
            <div id="table-content">
              <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="records-page cafs-to-data-entry-table">
                <thead>
                  <tr class="manage-sms-content-headings">
                    <th class="manage-sms-content-heading-category">CAF Bar Code</th>
                    <th class="manage-sms-content-heading-content">Download Scanned CAF</th>
                    <th class="manage-sms-content-heading-content">Link to Data Entry</th>
                  </tr>
                </thead>
                <tbody>
                </tbody>
              </table>
              <div id="status"></div>
            </div>
          </div>
          <br/>
          <br/>
          <div class="table-data">
            <div class="table-heading orange-table-heading">CAFs to Submit</div>
            <div id="table-content">
              <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="records-page cafs-to-submit-table">
                <thead>
                  <tr class="manage-sms-content-headings">
                    <th class="manage-sms-content-heading-category">CAF Bar Code</th>
                    <th class="manage-sms-content-heading-content">Sale Date</th>
                    <th class="manage-sms-content-heading-content">Cell Number</th>
                    <th class="manage-sms-content-heading-content">First Name</th>
                    <th class="manage-sms-content-heading-content">Last Name</th>
                    <th class="manage-sms-content-heading-content">City</th>
                    <th class="manage-sms-content-heading-content">State</th>
                    <th class="manage-sms-content-heading-content">Pin Code</th>
                  </tr>
                </thead>
                <tbody>
                </tbody>
              </table>
              <div id="status"></div>
            </div>
          </div>
          
          <div class="submit-caf-bottom-buttons">
            <a href="<%=request.getContextPath()%>/dataEntry.do?method=showDEODashboardPage&actionPerformed=submit" title="Submit" class="btn btn-primary">
            Submit
            </a>
          </div>
        </div>
      </div>
    </html:form>
  </body>
</html>