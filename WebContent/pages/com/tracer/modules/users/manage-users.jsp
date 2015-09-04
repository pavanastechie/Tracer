<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<!DOCTYPE HTML>
<html>
  <head>
    <script type="text/javascript">
      function loadUserDetails(){
          var extensions = {
                  "sFilter": "dataTables_filter manage_custom_filter_class",
                  "sLength": "dataTables_length manage_custom_length_class",
                  "sWrapper": "dataTables_wrapper",
                  "sStripeOdd": "dataTables-odd-row",
                  "sStripeEven ": "dataTables-even-row"
              }
        /*   $.extend($.fn.dataTableExt.oJUIClasses, extensions); */
         $.extend($.fn.dataTableExt.oStdClasses , extensions);
            $("#manage-users-table").dataTable({
                "sDom": 'T<"clear">lfrtip',
                "sPaginationType": "full_numbers",
                "oLanguage": {
                    "sProcessing": "<i class='fa fa-spinner fa-spin'></i>",
                    "sEmptyTable": "No Data Available"
                },
                "bJQueryUI": true,
                "bProcessing": true,
                "sAjaxDataProp":"",
                "sAjaxSource": "user.do?method=getUsersDetails",
                "aoColumns": [
                              {"aTargets": [ 0 ], "mDataProp": "lastName", "bSortable": true, "sClass": "manage-users-content-heading-last-name"},
                            {"aTargets": [ 1 ], "mDataProp": "name", "bSortable": true, "sClass": "manage-users-row-name"},
                            {"aTargets": [ 2 ], "mDataProp": "gender", "bSortable": true, "sClass": "manage-users-row-gender"},
                            {"aTargets": [ 3 ], "mDataProp": "role", "bSortable": true, "sClass": "manage-users-row-role" },
                            {"aTargets": [ 4 ], "mDataProp": "contactNumber", "bSortable": true, "sClass": "manage-users-row-contactNumber" },
                            {"aTargets": [ 5 ], "mDataProp": "reportingTo", "bSortable": true, "sClass": "manage-users-row-reportingTo"},
                            {"aTargets": [ 6 ], "mDataProp": "status",  "bSortable": true, "sClass": "manage-users-row-status" },
                            {"aTargets": [ 7 ], "bSortable": false, "sClass": "manage-sms-row-buttons" ,"mData": "userId",
                              "mRender": function ( data, type, full ) {
                                return '<div class="manage-sms-row-buttons-edit"><a href="userId='+data+'" title=Edit id="users_manage_edit_hr_'+data+'" class='+ full.showDeleteBtn +'><i class="fa fa-edit"></i></a> </div>'+
                                    '<div class="manage-sms-row-buttons-delete"><a href="user.do?method=deleteUser&userId='+data +'" title=Delete id="users_manage_delete_hr_'+data+'" class='+ full.showDeleteBtn +'><i class="fa fa-trash-o"></i></a></div>' +
                                    '<div class="manage-sms-row-buttons-activate"><a href="user.do?method=activateUser&userId='+data +'" title=Activate id="users_manage_activate_hr_'+data+'" class='+ full.showActivateBtn +'><i class="fa fa-check-square-o"></i></a></div>';
                              }
                            }
                        ],
               "oTableTools": {
                   "sSwfPath": "<%=request.getContextPath()%>/js/dataTables/swf/copy_csv_xls_pdf.swf",
                  "aButtons" : [ {
                    "sExtends" : "csv",
                    "sFileName" : "Users_Details.csv",
                    "mColumns" : [ 0, 1, 2, 3, 4, 5, 6 ]
                  }, {
                    "sExtends" : "xls",
                    "sFileName" : "Users_Details.xls",
                    "mColumns" : [ 0, 1, 2, 3, 4, 5, 6 ]
                  }, {
                    "sExtends" : "pdf",
                    "sFileName" : "Users_Details.pdf",
                    "mColumns" : [ 0, 1, 2, 3, 4, 5, 6 ]
                  }, ]
                },
                "aaSorting" : [ [ 0, "asc" ] ]
              });
      }
      $(document).on(
            'click',
            '.manage-sms-row-buttons-delete a',
            function(e) {
              var link = this;
              e.preventDefault();
              $(
                  "<div title='Delete User'>Are you sure you want to delete this user?</div>")
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
      
      $(document).on(
        'click',
        '.manage-sms-row-buttons-edit a',
        function(e) {
          var userType = $(this).parents('tr').find('td.manage-users-row-role').text().trim();
          var href = $(this).attr('href');
          if(userType == 'TSE') {
            $(this).attr('href', 'user.do?method=showUpdateRunnerPage&' + href);
          } else if(userType == 'TSM') {
            $(this).attr('href', 'user.do?method=showUpdateTeamLeaderPage&' + href);
          } else {
            $(this).attr('href', 'user.do?method=showUpdateManagerPage&' + href);
          }
        });
      
      $(document).on(
          'click',
          '.manage-sms-row-buttons-activate a',
          function(e) {
            var link = this;
            e.preventDefault();
            $(
                "<div title='Activate User'>Are you sure you want to activate this user?</div>")
                .dialog({
                  resizable : false,
                  modal : true,
                  buttons : {
                    "Cancel" : function() {
                      $(this).dialog("close");
                      },
                     "Activate" : function() {
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
      
      function errorNotification(msg) {
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
      <% }else if(request.getAttribute("errorMessage") != null) { %>
      errorNotification("User cannot be added at this time !!");
      <% } else if(request.getAttribute("deleteUserErrorMessage") != null) { %>
      errorNotification('<%=request.getAttribute("deleteUserErrorMessage")%>');
      <% } %>
      
    </script>
  </head>
  <body onload="loadUserDetails();">
    <div class="clear"></div>
    <div id="success"></div>
    <div id="form-content">
      <div id="titleheading">
        <div class="navtitle1">Users Details</div>
        <br />
        <div id="titleheadingbar">
          <div class="navtitle">
            <i class="fa fa-user"></i> Users / <label>Manage Users</label>
          </div>
        </div>
        <div class="table-data">
          <div class="table-heading">Users Details</div>
          <div id="table-content">
            <table id="manage-users-table" width="100%" align="center"
              cellpadding="0" cellspacing="0" border="0" class="records-page">
              <thead>
                <tr class="manage-users-content-headings">
                  <th class="manage-users-content-heading-last-name" style="width: 15%">Last Name</th>
                  <th class="manage-users-content-heading-name" style="width: 15%">First Name</th>
                  <th class="manage-users-content-heading-gender" style="width: 15%">Gender</th>
                  <th class="manage-users-content-heading-role" style="width: 15%">Role</th>
                  <th class="manage-users-content-heading-contact-number" style="width: 15%">Phone Number</th>
                  <th class="manage-users-content-heading-reporting-to" style="width: 15%">Reporting To</th>
                  <!-- <th class="manage-users-content-heading-hub">Hub</th>
                    <th class="manage-users-content-heading-zone">Zone</th>
                    <th class="manage-users-content-heading-region">Region</th>
                    <th class="manage-users-content-heading-circle">Circle</th> -->
                  <th class="manage-users-content-heading-status" style="width: 15%">Status</th>
                  <th class="manage-users-buttons"></th>
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
    <input type="hidden" name="<%=Constants.TOKEN_KEY%>" value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>">
  </body>
</html>