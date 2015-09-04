<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%> 
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<!DOCTYPE HTML>
<html>
  <head>
    <script type="text/javascript">
      function loadCircleDetails(){
          var extensions = {
                  "sFilter": "dataTables_filter manage_custom_filter_class",
                  "sLength": "dataTables_length manage_custom_length_class",
                  "sWrapper": "dataTables_wrapper",
                  "sStripeOdd": "dataTables-odd-row",
                  "sStripeEven ": "dataTables-even-row"   
              }
         /*  $.extend($.fn.dataTableExt.oJUIClasses, extensions); */
          $.extend($.fn.dataTableExt.oStdClasses , extensions);
            $("#manage-circles-table").dataTable({
                "sDom": 'T<"clear">lfrtip',
                "sPaginationType": "full_numbers",
                "oLanguage": {
                    "sProcessing": "<i class='fa fa-spinner fa-spin'></i>",
                    "sEmptyTable": "No Data Available"
                },
                "bJQueryUI": true,
                "bProcessing": true,
                "sAjaxDataProp":"",
                "sAjaxSource": "circles.do?method=getCirclesDetails",
                "aoColumns": [
                            {"aTargets": [ 0 ], "mDataProp": "circleName", "bSortable": true, "sClass": "manage-circles-row-circleName"},
                            {"aTargets": [ 1 ], "mDataProp": "managerNames", "bSortable": true, "sClass": "manage-circles-row-managersName" },
                            {"aTargets": [ 2 ], "mDataProp": "regionsCount", "bSortable": true, "sClass": "manage-circles-row-regionsCount" },
                            {"aTargets": [ 3 ], "mDataProp": "zonesCount", "bSortable": true, "sClass": "manage-circles-row-zonesCount"},
                            {"aTargets": [ 4 ], "mDataProp": "hubsCount", "bSortable": true, "sClass": "manage-circles-row-hubsCount"},
                            {"aTargets": [ 5 ], "sClass": "manage-sms-row-buttons" ,"mData": "circleId", "bSortable": false,
                              "mRender": function ( data, type, full ) {
                                return '<div class="manage-sms-row-buttons-edit"><a href="circles.do?method=editCirclesPage&viewType=edit&circleId='+data+'" title=Edit id="circles_manage_edit_hr_'+data+'"><i class="fa fa-edit"></i></a> </div>'+
                                  '<div class="manage-sms-row-buttons-delete"><a href="circles.do?method=deleteCirclesDetails&circleId='+data +'" title=Delete id="circles_manage_delete_hr_'+data+'"><i class="fa fa-trash-o"></i></a></div>';
                              }
                            }
                        ],
               "oTableTools": {
                   "sSwfPath": "<%=request.getContextPath()%>/js/dataTables/swf/copy_csv_xls_pdf.swf",
                   "aButtons": [
                                {
                                    "sExtends": "csv",
                                    "sFileName": "Circles_Details.csv",
                                    "mColumns": [ 0, 1,2,3,4]
                                },
                                {
                                    "sExtends": "xls",
                                    "sFileName": "Circles_Details.xls",
                                    "mColumns": [ 0, 1,2,3,4]
                                },
                                {
                                    "sExtends": "pdf",
                                    "sFileName": "Circles_Details.pdf",
                                    "mColumns": [ 0, 1,2,3,4]
                                },
                            ]
                },
                "aaSorting": [[ 0, "asc" ]]
            });
        }
      
      $(document).on('click','.manage-sms-row-buttons-delete a', function(e) {
            var link = this;
            e.preventDefault();
            $("<div title='Delete Circle'>Are you sure you want to delete this circle?</div>").dialog({
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
          msg :msg,
          type : "success",
          position: "center"
        });
      }
      
      function errorNotification(msg) {
        notif({
            type: "warning",
            msg: msg,
            position: "center",
            bgcolor: "#FF0000",
            autohide: false
          });
      }
      <% if(request.getAttribute("successMessage") != null) { %>
        successNotification('<%=request.getAttribute("successMessage")%>');
      <% }else if(request.getAttribute("errorMessage") != null) { %>
          errorNotification("Circle cannot be added at this time !!");
      <% } %>
    </script>
  </head>
  <body onload="loadCircleDetails();">
    <div class="clear"></div>
    <div id="form-content">
      <div id="titleheading">
        <div class="navtitle1">
          <bean:message key="title.circle.details"></bean:message>
        </div>
        <br />
        <div id="titleheadingbar">
          <div class="navtitle">
            <i class="fa fa-user"></i> 
            <bean:message key="label.breadcrum.circles"/>
            <bean:message key="label.manage.circles"/>
          </div>
        </div>
        <div class="table-data">
          <div class="table-heading">
            <bean:message key="title.circle.details"/>
          </div>
          <div id="table-content">
            <table id="manage-circles-table" width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="records-page">
              <thead>
                <tr class="manage-circles-content-headings">
                  <th class="manage-circles-content-heading-name" style="width: 15%">
                    <bean:message key="circle.name"/>
                  </th>
                  <th class="manage-circles-content-heading-manager-name" style="width: 15%">
                    <bean:message key="label.manager.name"/>
                  </th>
                  <th class="manage-circles-content-heading-no-regions" style="width: 15%">
                    <bean:message key="label.no.regions"/>
                  </th>
                  <th class="manage-circles-content-heading-no-zones" style="width: 15%">
                    <bean:message key="label.no.zones"/>
                  </th>
                  <th class="manage-circles-content-heading-no-hubs" style="width: 15%">
                    <bean:message key="label.no.hubs"/>
                  </th>
                  <th class="manage-circles-buttons"></th>
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
  </body>
</html>