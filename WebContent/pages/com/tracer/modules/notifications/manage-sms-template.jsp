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
      function loadSMS(){
       var extensions = {
              "sFilter": "dataTables_filter manage_custom_filter_class",
              "sLength": "dataTables_length manage_custom_length_class",
              "sWrapper": "dataTables_wrapper",
              "sStripeOdd": "dataTables-odd-row",
              "sStripeEven ": "dataTables-even-row"
          };
       //$.extend($.fn.dataTableExt.oJUIClasses, extensions);
       $.extend($.fn.dataTableExt.oStdClasses , extensions);
         oTable = $(".manage-sms-template-table").dataTable({
               "sDom": 'T<"clear">lfrtip',
               //"sDom": '<"H"TCfr>t<"F"ip>',
              "sPaginationType": "full_numbers",
              "bJQueryUI": true,
              "bProcessing": true,
              "oLanguage": {
                  "sProcessing": "<i class='fa fa-spinner fa-spin'></i>",
                  "sEmptyTable": "No Data Available"
              },
              "sAjaxDataProp":"",
              "sAjaxSource": "notification.do?method=getSmsData",
              "aoColumnDefs": [
                          {"aTargets": [ 0 ], "mData": "smsCategory", "bSortable": true, "sClass": "manage-sms-row-category"},
                          {"aTargets": [ 1 ], "mData": "smsContent", "bSortable": true, "sClass": "manage-sms-row-content" },
                          {"aTargets": [ 2 ],"sClass": "manage-sms-row-buttons" ,"bSortable": false,"mData": "smsTemplateId",
                            "mRender": function ( data, type, full ) {
                            return '<div class="manage-sms-row-buttons-edit"><a id="sms_templates_manage_edit_hr_'+data+'" href="notification.do?method=showEditSmsTemplate&id='+data+'" title=Edit><i class="fa fa-edit"></i></a> </div>'+
                                '<div class="manage-sms-row-buttons-delete"><a id="sms_templates_manage_delete_hr_'+data+'" href="notification.do?method=deleteSMSTemplate&id='+data+'" name="manageForm" title=Delete><i class="fa fa-trash-o"></i></a></div>';
                                }
                          },
                      ],
             "oTableTools": {
                 "sSwfPath": "<%=request.getContextPath()%>/js/dataTables/swf/copy_csv_xls_pdf.swf",
                 "aButtons": [
                              {
                                  "sExtends": "csv",
                                  "sFileName": "SMS Templates.csv",
                                  "mColumns": [ 0, 1]
                              },
                              {
                                  "sExtends": "xls",
                                  "sFileName": "SMS Templates.xls",
                                  "mColumns": [ 0, 1]
                              },
                              {
                                  "sExtends": "pdf",
                                  "sFileName": "SMS Templates.pdf",
                                  "mColumns": [ 0, 1]
                              },
                          ]
              },
              "aaSorting": [[ 1, "desc" ]]
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
                "<div title='Are you sure?'> Do you want to delete the sms template?</div>")
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
    </script>
  </head>
  <body onload="loadSMS()">
    <div class="clear"></div>
    <div id="form-content">
      <div id="titleheading">
        <div class="navtitle1">Manage SMS Templates</div>
        <br />
        <div id="titleheadingbar">
          <div class="navtitle">
            <i class="fa fa-envelope "></i></label>
            <bean:message key="label.sms.template" />
            /
            <bean:message key="label.manage.sms" />
          </div>
        </div>
        <div class="table-data">
          <div class="table-heading">SMS Template Details</div>
          <div id="table-content">
            <table width="100%" align="center" cellpadding="0" cellspacing="0"
              border="0" class="records-page manage-sms-template-table">
              <thead>
                <tr class="manage-sms-content-headings">
                  <th class="manage-sms-content-heading-category">SMS Category</th>
                  <th class="manage-sms-content-heading-content">SMS Content</th>
                  <th class="manage-sms-content-buttons"></th>
                </tr>
              </thead>
              <tbody>
              </tbody>
              <!-- <tr bgColor="#f1f1f1"  class="manage-sms-row">
                <td class="manage-sms-row-category">category1</td>
                <td  class="manage-sms-row-content">content1</td>
                <td class="manage-sms-row-buttons">
                  <div class="manage-sms-row-buttons-edit"><a href="notification.do?method=showEditSmsTemplate"><i class="fa fa-edit"></i></a> </div>
                  <div class="manage-sms-row-buttons-view"><a href="notification.do?method=showEditSmsTemplate&type=view"><i class="fa fa-file-text-o"></i></a></div>
                  <div class="manage-sms-row-buttons-delete"><a href="#" onclick="return displayAlertToDelete('category1', 2);"><i class="fa fa-trash-o"></i></a></div>
                </td>
                </tr> -->
            </table>
            <div id="status"></div>
          </div>
        </div>
      </div>
    </div>
    </div>
  </body>
</html>