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
               "sAjaxSource": "upload.do?method=verifyCAFsUploaded",
                "aoColumns": [
                            {"aTargets": [ 0 ], "mDataProp": "mobileNo", "bSortable": true, "sClass": "upload-onlineCaf-row-mobileNo"},
                            {"aTargets": [ 1 ], "mDataProp": "orderNumber", "bSortable": true, "sClass": "upload-onlineCaf-row-orderNumber" },
                            {"aTargets": [ 2 ], "mDataProp": "ofscCode", "bSortable": true,"mData": "ofscCode", "sClass": "upload-onlineCaf-row-ofscCode" },
                            {"aTargets": [ 3 ], "mDataProp": "distributorName", "bSortable": true, "sClass": "upload-onlineCaf-row-distributorName" },
                            {"aTargets": [ 4 ], "mDataProp": "cafStatusAsString", "bSortable": true, "sClass": "upload-onlineCaf-row-status"},
                            {"aTargets": [ 5 ], "bSortable": false,"mData": "recordId", "sClass": "manage-sms-row-button",
                              "mRender": function (data, type, row) {
                                    if (data) {
                                      
                                         return '<div id="dvDel" class="manage-sms-row-button-delete "><a id="upload_delete_uploaded_caf_'+data+'" href="upload.do?method=deleteUploadedCAFs&recordId='+data+'" title=Delete class='+ row.showDeleteBtn +'><i class="fa fa-trash-o"></i></a></div>';
                                    }
                                }  
                            },
                        ],
                        "oTableTools": {
                        "sSwfPath": "<%=request.getContextPath()%>/js/dataTables/swf/copy_csv_xls_pdf.swf",
                        "aButtons": [
                                     {
                                         "sExtends": "csv",
                                         "sFileName": "OnlineCAF.csv",
                                         "mColumns": [ 0, 1,2,3,4]
                                     },
                                     {
                                         "sExtends": "xls",
                                         "sFileName": "OnlineCAF.xls",
                                         "mColumns": [ 0, 1,2,3,4]
                                     },
                                     {
                                         "sExtends": "pdf",
                                         "sFileName": "OnlineCAF.pdf",
                                         "mColumns": [ 0, 1,2,3,4]
                                     },
                                 ]
                     },
                     "aaSorting": [[ 0, "asc" ]]
            });
            
        };    
        
        $(document).on('click','.manage-sms-row-button-delete a',function(e) {
          var link = this;
          e.preventDefault();
          $("<div title='Delete Record'>Are you sure ? Do you want to delete this record ?</div>").dialog({
              resizable: false,
              modal: true,
              buttons: {
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
    </script>
  </head>
  <body onload="loadOnlineCafDetails()">
    <div class="clear"></div>
    <div id="form-content">
      <div id="titleheading">
        <div class="navtitle1">
          <bean:message key="label.verify.uploaded.online.caf" />
        </div>
        <br />
        <div id="titleheadingbar">
          <div class="navtitle">
            <i class="fa fa-upload"></i>
            <bean:message key="label.upload" />
            /
            <bean:message key="label.verify.uploaded.online.caf" />
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
                <th class="upload-online-content-heading-ofscCode" style="width: 15%">OFSC Code</th>
                <th class="upload-onlineCaf-row-distributorName"  style="width: 35%">Distributor Name</th>
                <th class="upload-online-content-heading-status" style="width: 5%" >Status</th>
                <th class="manage-sms-row-button"></th>
              </tr>
            </thead>
            <tbody>
            </tbody>
          </table>
        </div>
      </div>
      <div class="verifybtns">
        <a href="<%=request.getContextPath()%>/upload.do?method=deleteAllDistributorInActiveRecords" title="Delete all distributor in-active records" class="btn btn-primary " id="delAllDNFRbtn">Delete all distributor in-active records</a>
        <a href="<%=request.getContextPath()%>/upload.do?method=deleteAllDistributorNotFoundRecords" title="Delete all distributor not found records" class="btn btn-primary " id="delAllDIARbtn">Delete all distributor not found records</a>
        <a href="<%=request.getContextPath()%>/upload.do?method=saveUploadedCAFs" title="Save" id="savebtn" class="btn btn-primary ">Save</a>
        <a href="<%=request.getContextPath()%>/upload.do?method=showUploadCAFsPage" title="Cancel" id="cancelbtn" class="btn btn-primary ">Cancel</a>
      </div>
    </div>
  </body>