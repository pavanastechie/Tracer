<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%> 
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<!DOCTYPE HTML>
<html>
  <head>
    <script type="text/javascript">
      function loadDistributorDetails(){
          var extensions = {
                  "sFilter": "dataTables_filter manage_custom_filter_class",
                  "sLength": "dataTables_length manage_custom_length_class",
                  "sWrapper": "dataTables_wrapper",
                  "sStripeOdd": "dataTables-odd-row",
                  "sStripeEven ": "dataTables-even-row"
              };
          /* $.extend($.fn.dataTableExt.oJUIClasses, extensions); */
          $.extend($.fn.dataTableExt.oStdClasses , extensions);
            $("#manage-distributors-table").dataTable({
                "sDom": 'T<"clear">lfrtip',
                "sPaginationType": "full_numbers",
                "oLanguage": {
                    "sProcessing": "<i class='fa fa-spinner fa-spin'></i>",
                    "sEmptyTable": "No Data Available"
                },
                "bJQueryUI": true,
                "bProcessing": true,
                "sAjaxDataProp":"",
                "sAjaxSource": "distributor.do?method=getDistributorsDetails",
                "aoColumns": [
                            {"aTargets": [ 0 ], "mDataProp": "distributorName", "bSortable": true, "sClass": "manage-distributors-row-name"},
                            {"aTargets": [ 1 ], "mDataProp": "ofscCode", "bSortable": true, "sClass": "manage-distributors-row-code" },
                            {"aTargets": [ 2 ], "mDataProp": "contactNo", "bSortable": true, "sClass": "manage-distributors-row-phoneNumber" },
                            {"aTargets": [ 3 ], "mDataProp": "hubName", "bSortable": true, "sClass": "manage-distributors-row-hub"},
                            {"aTargets": [ 4 ], "mDataProp": "city", "bSortable": true, "sClass": "manage-distributors-row-city"},
                            {"aTargets": [ 5 ], "mDataProp": "state", "bSortable": true, "sClass": "manage-distributors-row-state"},
                            {"aTargets": [ 6 ], "sClass": "manage-sms-row-buttons" ,"mData": "distributorId","bSortable": false,
                              "mRender": function ( data, type, full ) {
                                return '<div class="manage-sms-row-buttons-edit"><a id="distributor_manage_edit_hr_'+data+'" href="distributor.do?method=showEditDistributorPage&distributorId='+data+'" title=Edit class='+ full.showDeleteBtn +'><i class="fa fa-edit"></i></a> </div>'+
                                  '<div class="manage-sms-row-buttons-delete "><a id="distributor_manage_delete_hr_'+data+'" href="distributor.do?method=deleteDistributor&distributorId='+data +'" title=Delete class='+ full.showDeleteBtn +'><i class="fa fa-trash-o"></i></a></div>'+
                                  '<div class="manage-sms-row-buttons-activate "><a id="distributor_manage_activate_hr_'+data+'" href="distributor.do?method=activateDistributor&distributorId='+data +'" title=Activate class='+ full.showActivateBtn +'><i class="fa fa-check-square-o"></i></a></div>';
                              }
                            }
                        ],
                        "oTableTools": {
                        "sSwfPath": "<%=request.getContextPath()%>/js/dataTables/swf/copy_csv_xls_pdf.swf",
                        "aButtons": [
                                     {
                                         "sExtends": "csv",
                                         "sFileName": "Distributors.csv",
                                         "mColumns": [ 0, 1,2,3,4,5]
                                     },
                                     {
                                         "sExtends": "xls",
                                         "sFileName": "Distributors.xls",
                                         "mColumns": [ 0, 1,2,3,4,5]
                                     },
                                     {
                                         "sExtends": "pdf",
                                         "sFileName": "Distributors.pdf",
                                         "mColumns": [ 0, 1,2,3,4,5]
                                     },
                                 ]
                     },
                     "aaSorting": [[ 0, "asc" ]]
            });
        }
      $(document).on('click','.manage-sms-row-buttons-delete a',function(e) {
          var link = this;
          e.preventDefault();
          $("<div title='Delete Distributor'>Are you sure ? Do you want to delete the Distributor ?</div>").dialog({
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
      
      $(document).on('click','.manage-sms-row-buttons-activate a',function(e) {
        var link = this;
        e.preventDefault();
        $("<div title='Activate Distributor'>Are you sure ? Do you want to activate the Distributor ?</div>").dialog({
            resizable: false,
            modal: true,
            buttons: {
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
       msg: msg,
       type: "warning",
       position: "center",
       bgcolor: "#FF0000",
       autohide: false
      });
      }
      
      <% if(request.getAttribute("successMessage") != null) { %>
      successNotification('<%=request.getAttribute("successMessage")%>');
      <% } else if(request.getAttribute("errorMessage") != null) { %>
      errorNotification("Distributor cannot be added at this time !!");
      <% } else if(request.getAttribute("deleteDistributorErrorMessage") != null) { %>
      errorNotification('<%=request.getAttribute("deleteDistributorErrorMessage")%>');
      <% } %>
    </script>
  </head>
  <body onload="loadDistributorDetails();">
    <div class="clear"></div>
    <div id="form-content">
      <div id="titleheading">
        <div class="navtitle1">Manage Distributors</div>
        <br />
        <div id="titleheadingbar">
          <div class="navtitle">
            <i class="fa fa-map-marker"></i> 
            <bean:message key="label.distributors" />
            /  
            <bean:message key="label.manage.distributors" />
            <label></label>
          </div>
        </div>
        <div class="table-data">
          <div class="table-heading">
            <bean:message key="label.distributor.details" />
          </div>
          <div id="table-content">
            <table id="manage-distributors-table" width="100%" align="center" cellpadding="0" cellspacing="0"
              border="0" class="records-page">
              <thead>
                <tr class="manage-distributors-content-headings datatable-header">
                  <th class="manage-distributors-content-heading-name" style="width: 15%; padding-left: 2px;">Distributor Name</th>
                  <th class="manage-distributors-content-heading-code" style="width: 15%">OFSC Code</th>
                  <th class="manage-distributors-content-heading-phoneNumber" style="width: 15%">Contact Number</th>
                  <th class="manage-distributors-content-heading-hub" style="width: 15%">Hub</th>
                  <th class="manage-distributors-content-heading-city" style="width: 15%">City</th>
                  <th class="manage-distributors-content-heading-state" style="width: 15%">State</th>
                  <th class="manage-distributors-buttons"></th>
                </tr>
              </thead>
              <tbody>
              </tbody>
            </table>
            <div id="status"></div>
            <div id="dialog" title="Delete Confirmation">
            </div>
          </div>
        </div>
      </div>
    </div>
    <input type="hidden" name="<%=Constants.TOKEN_KEY%>" value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>">
  </body>
</html>