<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
  prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<%@ page import="org.json.JSONArray, org.json.JSONObject"%>
<!DOCTYPE html>
<html>
  <head>
    <script type="text/javascript">
      var dependentProperties = [];
      var visitNoList = [];
      var deletedDependentProperties = [];
      var myString;
      function loadRunnerBeatPlanDetails(dependentProperties){
       console.log(dependentProperties);
       myString=JSON.stringify(dependentProperties);
       console.log(myString);
        var extensions = {
                   "sFilter": "dataTables_filter manage_custom_filter_class",
                   "sLength": "dataTables_length manage_custom_length_class",
                   "sWrapper": "dataTables_wrapper",
                   "sStripeOdd": "dataTables-odd-row",
                   "sStripeEven ": "dataTables-even-row"
               };
         $.extend($.fn.dataTableExt.oStdClasses , extensions);
         $("#beatPlanDetails").dataTable({
             "sDom": 'T<"clear">lfrtip',
              "sPaginationType": "full_numbers",
              "oLanguage": {
                  "sProcessing": "<i class='fa fa-spinner fa-spin'></i>",
                  "sEmptyTable": "No Data Available"
              },
              "bJQueryUI": true,
              "bProcessing": true,
              "sAjaxDataProp":"",
              "bDestroy":true,
              "aaData":dependentProperties,
              "aoColumns": [
                          {"aTargets": [ 0 ], "mDataProp": "beatPlanName", "bSortable": true, "sClass": "manage-users-row-bpName"},
                          {"aTargets": [ 1 ], "mDataProp": "distributorsName", "bSortable": true, "sClass": "manage-users-distname"},
                          {"aTargets": [ 2 ], "mDataProp": "runnersName", "bSortable": true, "sClass": "manage-users-row-runnerName" },
                          {"aTargets": [ 3 ], "mDataProp": "schedule", "bSortable": true, "sClass": "manage-users-row-schedule" },
                          {"aTargets": [ 4 ], "mDataProp": "visitNo", "bSortable": true, "sClass": "manage-users-row-visitNo" },
                          {"aTargets": [ 5 ], "mDataProp": "type", "bSortable": false, "sClass": "manage-beat_plan-type" },
           {
                "aTargets" : [ 6 ],
                "sClass" : "manage-users-row-buttons",
                "mDataProp" : "beatPlanId",
                "mRender" : function(data, type, full) {
                 return '<div class="manage-sms-row-buttons-delete"><a id="beat_plan_runner_delete_'+data+'" href="javascript:void(0)" data-beatPlanId='+data+' title=Delete><i class="fa fa-trash-o"></i></a></div>';
                }
               } ],
             "oTableTools" : {
              "sSwfPath" : "<%=request.getContextPath()%>/js/dataTables/swf/copy_csv_xls_pdf.swf",
                 "aButtons": [
                              {
                                  "sExtends": "csv",
                                  "sFileName": "Runner Beat Plan.csv",
                                  "mColumns": [ 0, 1,2,3,4]
                              },
                              {
                                  "sExtends": "xls",
                                  "sFileName": "Runner Beat Plan.xls",
                                  "mColumns": [ 0, 1,2,3,4]
                              },
                              {
                                  "sExtends": "pdf",
                                  "sFileName": "Runner Beat Plan.pdf",
                                  "mColumns": [ 0, 1,2,3,4]
                              },
                          ]
              },
              "aaSorting": [[ 0, "asc" ]]
          });
      }
      $(document).on('click','.manage-sms-row-buttons-delete a',function(e) {
       console.log(this);
       var that = $(this);
       var beatPlanIdToUpdate = $(this).attr('data-beatPlanId');
       var beatPlanName =$(this).parents('tr').find('td.manage-users-row-bpName').text();
       var distributorsName =$(this).parents('tr').find('td.manage-users-distname').text();
       var runnersName =$(this).parents('tr').find('td.manage-users-row-runnerName').text();
       var schedule =$(this).parents('tr').find('td.manage-users-row-schedule').text();
       var visitNo =$(this).parents('tr').find('td.manage-users-row-visitNo').text();
       var type =$(this).parents('tr').find('td.manage-beat_plan-type').text();
        e.preventDefault();
        $("<div title='Delete Beat Plan'>Are you sure you want to delete this BeatPlan?</div>").dialog({
            resizable: false,
            modal: true,
            buttons: {
              "Cancel": function() {
           $( this ).dialog( "close" );
              },
            "Delete BeatPlan": function() {
             if(type=="edit")
          {
           deleteRecord(parseInt(beatPlanIdToUpdate),beatPlanName,distributorsName,runnersName,schedule,parseInt(visitNo));
           that.parents('tr').addClass('hide');
          }
          else if(type=="add")
          {
           cleaner(dependentProperties,beatPlanIdToUpdate,beatPlanName,distributorsName,runnersName,schedule,parseInt(visitNo));
           that.parents('tr').addClass('hide');
          }
            $( this ).dialog( "close" );
          }
            }
          });
      });
      
      //Called when clicked on Edit Button in Datatable 
      $(document).on('click','.manage-sms-row-buttons-edit a',function(e) {
      var zoneId = $(this).parents('tr').find('td.manage-beat_plan-zone-id').text();
      var hubId = $(this).parents('tr').find('td.manage-beat_plan-hub-id').text();
      
      $('#visitNo').val($(this).attr('data-visitNo'));
      $('#schedule').val(visitFrequency);
      
      $('#addToBeatPlan').hide();
      $('#updateToBeatPlan').show();
      
      });
       
      function saveBeatPlanDetails()
      {
         $('.custom-error').remove();
       if(dependentProperties.length > 0)
        {
        console.log(JSON.stringify(dependentProperties));
         document.getElementById("runnerBeatPlanData").value=JSON.stringify(dependentProperties);
        document.getElementById("addRunnerBeatPlanForm").submit();
       } else {
        $('#errors-container ul')
          .append(
            '<li class="custom-error">Please select atleast one beat plan</li>')
          .show();
       }
      }
      
      function updateBeatPlanDetails()
      {
       $('.custom-error').remove();
       if(dependentProperties.length > 0)
        {
         console.log(JSON.stringify($.merge(dependentProperties, deletedDependentProperties)));
         document.getElementById("runnerBeatPlanData").value=JSON.stringify($.merge(dependentProperties, deletedDependentProperties));
          document.beatPlansForm.action = "<%=request.getContextPath()%>/beatPlans.do?method=updateRunnerBeatPlan";
          document.getElementById("addRunnerBeatPlanForm").submit();
         
        }
       else
        {
        $('#errors-container ul').append('<li class="custom-error">Please select atleast one beat plan</li>').show();
        }
      }
      
      function deleteRecord(beatPlanIdToUpdate,beatPlanName,distributorsName,runnersName,schedule,visitNo)
      {
       for (var i = 0, l = dependentProperties.length; i < l; i++) {
              if (dependentProperties[i].beatPlanId === beatPlanIdToUpdate && dependentProperties[i].beatPlanName === beatPlanName &&  dependentProperties[i].runnersName=== runnersName &&  dependentProperties[i].schedule=== schedule &&  parseInt(dependentProperties[i].visitNo) === visitNo) {
               dependentProperties[i].status = "inactive";
               deletedDependentProperties.push(dependentProperties[i]);
              dependentProperties.splice(i, 1);
                  break;
              }
          }
       
       console.log(dependentProperties);
      }
      
      function cleaner(arr, id,beatPlanName,distributorsName,runnersName,schedule,visitNo) {
          for (var i = 0; i < arr.length; i++) {
              var cur = arr[i];
              if (cur.beatPlanId === id && cur.beatPlanName === beatPlanName &&  cur.runnersName=== runnersName &&  cur.schedule=== schedule &&  parseInt(cur.visitNo) === visitNo) {
                  arr.splice(i, 1);
                  break;
              }
          }
          console.log(dependentProperties);
      }
      
      function updateToBeatPlan() {
       var exist = {};
       if(dependentProperties.length > 0) {
         for (var i = 0, l = dependentProperties.length; i < l; i++) {
                    dependentProperties[i].visitNo = 2 ;
                    dependentProperties[i].schedule = 14.40;
                }
            }
        console.log(exist);
        console.log(dependentProperties);
        
        $('#addToBeatPlan').show();
        $('#updateToBeatPlan').hide();
        
        loadDistributorBeatPlanDetails(dependentProperties);
        addRunnerBeatFormForm.resetForm();
       }
      
      
      function OnHourShowCallback(hour) {
          if ((hour > 20) || (hour < 6)) {
              return false; // not valid
          }
          return true; // valid
      }
      function OnMinuteShowCallback(hour, minute) {
          if ((hour == 20) && (minute >= 30)) { return false; } // not valid
          if ((hour == 6) && (minute < 30)) { return false; }   // not valid
          return true;  // valid
      }
       
      $(document).ready(function(){
       
       $('#schedule').timepicker({
              onHourShow: OnHourShowCallback,
              onMinuteShow: OnMinuteShowCallback,
              showPeriodLabels: false,
          });
               $('#beatPlanId').change(function() {
                var selectedValue = this.value;
             $.ajax({
                    type: "GET",
                    contentType : "application/json; charset=utf-8",
                   url: "<%=request.getContextPath()%>/beatPlans.do?method=getDistributorsAndRunnersList&type=distributors&selectedId="
                      + selectedValue,
                    success : function(data) {
                     $(
                       '#distributorsId')
                       .html(
                         '<option value="">Please Select...</option>');
                     $(
                       '#distributorsId')
                       .append(
                         data);
                    }
                   });
             $.ajax({
                    type: "GET",
                    contentType : "application/json; charset=utf-8",
                   url: "<%=request.getContextPath()%>/beatPlans.do?method=getDistributorsAndRunnersList&type=runners&selectedId="
                      + selectedValue,
                    success : function(data) {
                     $(
                       '#runnersId')
                       .html(
                         '<option value="">Please Select...</option>');
                     $(
                       '#runnersId')
                       .append(
                         data);
                    }
                   });
             $.ajax({
                    type: "GET",
                    contentType : "application/json; charset=utf-8",
                    url: "<%=request.getContextPath()%>/beatPlans.do?method=getDistributorsAndRunnersList&type=beatPlanDetails&selectedId="
                      + selectedValue,
                    success : function(data) {
                     console.log(data);
                     console
                       .log(data.HUB_NAME);
                     $(
                       '#beatPlanDetailsTableId tr td#hub_name_td')
                       .text(
                         data.HUB_NAME);
                     $(
                       '#beatPlanDetailsTableId tr td#zone_name_td')
                       .text(
                         data.ZONE_NAME);
                     $(
                       '#beatPlanDetailsTableId tr td#region_name_td')
                       .text(
                         data.REGION_NAME);
                     $(
                       '#beatPlanDetailsTableId tr td#circle_name_td')
                       .text(
                         data.CIRCLE_NAME);
      
                    }
                   });
                });       
            $('#distributorsId').change(function() {
              $("#visitNoHint span").empty();
             var selectedValue = this.value;
             $.ajax({
              type: "GET",
              contentType : "application/json; charset=utf-8",
              url: "<%=request.getContextPath()%>/beatPlans.do?method=getVisitNumbersList&distributorId="+ selectedValue,
              success: function(data) {
               $('#visitNo').html('<option value="">Please Select...</option>');
               if(data.length>0)
                {
               for(var i=0;i<data.length;i++)
                {
                visitNoList.push(data[i]);
                $('#visitNo').append("<option value=" + data[i] + ">"+ data[i]+"</option>");
                }
                }
               else
                {
                $("#visitNoHint span").text("All the visits are already assigned !!").css('color','#FF0000');
                }
              }
             });
            });
            function addToBeatPlan() {
             var obj = {};
             obj["beatPlanName"] = $(
               "#beatPlanId option:selected").text();
             obj["beatPlanId"] = $("#beatPlanId").val();
             obj["distributorsName"] = $(
               "#distributorsId option:selected").text();
             obj["runnersName"] = $("#runnersId option:selected")
               .text();
             obj["runnersId"] = $("#runnersId").val();
             obj["visitNo"] = $("#visitNo").val();
             obj["distributorId"] = $("#distributorsId").val();
             obj["schedule"] = $("#schedule").val();
             obj["type"] = "add";
             obj["status"] = "active";
             obj["bpuid"]=0;
      
             
             $('#beatPlanId').prop('disabled', 'disabled').addClass('form-control');
             
             dependentProperties.push(obj);
             console.log(dependentProperties);
             visitNoList.splice(0,1);
             myString = JSON.stringify(dependentProperties);
             loadRunnerBeatPlanDetails(dependentProperties);
            }
      
            $('#addToBeatPlan').click(function() {
             if ($("#addRunnerBeatPlanForm").valid()) {
              addToBeatPlan();
              addRunnerBeatFormForm.resetForm();
             } else {
              $('.custom-error').remove();
             }
            });
            
            $.validator.addMethod("checkVisitNo",function(value) {
             var exist = true;
             if(dependentProperties.length == 0)
              {
               exist = true;
              } 
             else
              {
               for ( var i = 0; i < dependentProperties.length; i++) 
               {
                if (dependentProperties[i].distributorId == $('#distributorsId').val() && dependentProperties[i].visitNo==value) 
                {
                exist = false;
                break;
                }
               }
              }
              return exist;
              },"Please select different Visit No from the list");
            
            $.validator.addMethod("checkVisitCount",function(value) {
             var exist = true;
             if(dependentProperties.length == 0)
              {
               exist = true;
              } 
             else
              {
               for ( var i = 0; i < dependentProperties.length; i++) 
               {
                if (dependentProperties[i].distributorId == $('#distributorsId').val() && dependentProperties[i].visitNo==value) 
                {
                exist = false;
                break;
                }
               }
              }
              return exist;
              },"Please select top most visit number which is not assigned");
      
            var addRunnerBeatFormForm=$('#addRunnerBeatPlanForm')
              .validate(
                {
                 messages : {
                  beatPlanId : {
                   required : 'Please enter Beat Plan Name',
                  },
                  runnersId : {
                   required : 'Please Select Runner',
                  },
                  distributorsId : {
                   required : 'Please Select Distributor',
                  },
                  visitNo : {
                   required : 'Please Select Visit No',
                   checkVisitNo :'Visit no already assigned',
                   //checkVisitCount:'Please select top most visit number which is not assigned.',
                  },
                  schedule : {
                   required : 'Please Select Schedule',
                  },
                 },
                 rules : {
      
                  beatPlanDetails : {
                   dataLength : true,
                  },
                  beatPlanId : {
                   required : true,
                  },
                  runnersId : {
                   required : true,
                  },
                  distributorsId : {
                   required : true,
                  },
                  visitNo : {
                   required : true,
                   checkVisitNo :true,
                   //checkVisitCount:true,
                  },
                  schedule : {
                   required : true,
                  },
                 },
                 errorLabelContainer : $('#errors-container ul'),
                 errorElement : 'li',
                });
      <%String viewType = (String) session.getAttribute("viewType");
        if (viewType != null && viewType.equalsIgnoreCase("edit")) {
         JSONArray beatPlanJson = (JSONArray) session
           .getAttribute("runnerBeatplandetailsjson");%>
       console.log('inside');
      <%for (int i = 0, size = beatPlanJson.length(); i < size; i++) {
        JSONObject objectInArray = beatPlanJson.getJSONObject(i);%>
       dependentProperties.push(
      <%=objectInArray%>
       );
      <%}%>
        $('#beatPlanId').trigger('change');
        $('#beatPlanId').prop('disabled', 'disabled').addClass('form-control');
       loadRunnerBeatPlanDetails(dependentProperties);
       $(".navtitle1").empty();
       $(".navtitle1").html('<bean:message key="label.runner.beat.plan" /> ');
       $(".navtitle").empty();
       $(".navtitle").html('<div class="navtitle">  <i class="fa fa-user"></i> <bean:message key="label.beat.plans" /> / <bean:message key="label.edit.runners.beat.plan" /></div>');
      <%}%>
       });
    </script>
  </head>
  <body onload="loadRunnerBeatPlanDetails(dependentProperties)">
    <div class="clear"></div>
    <div id="form-content">
      <html:form styleId="addRunnerBeatPlanForm"
        action="/beatPlans.do?method=saveRunnerBeatPlan">
        <div id="titleheading">
          <div class="navtitle1">
            <bean:message key="label.runner.beat.plan" />
          </div>
          <br />
          <div id="titleheadingbar">
            <div class="navtitle">
              <i class="fa fa-list"></i>
              <bean:message key="label.beat.plan" />
              / 
              <label>
                <bean:message key="label.add.runner.beat.plan" />
              </label>
            </div>
          </div>
          <div id='errors-container'>
            <ul></ul>
          </div>
          <div class="divstyleright addBeatPlanBlock">
            <div class="divheading10">
              <bean:message key="label.runner.beat.plan" />
            </div>
            <div class="divcontent10 innerAddBlock" id="panel5">
              <div class="left-content">
                <label>
                  <bean:message key="label.beat.plan" />
                </label>
                <html:select property="beatPlanId" styleClass="reportsDropdown"
                  styleId="beatPlanId">
                  <html:option value="">
                    <bean:message key="label.select.beat.plan" />
                  </html:option>
                  <logic:notEmpty name="beatPlansForm"
                    property="beatPlanDetailsList">
                    <html:optionsCollection property="beatPlanDetailsList"
                      label="beatPlanName" value="beatPlanId" />
                  </logic:notEmpty>
                </html:select>
                <html:hidden property="runnerBeatPlanData"
                  styleId="runnerBeatPlanData" />
                <label>
                  <bean:message key="label.distributor" />
                </label>
                <html:select property="distributorsId"
                  styleClass="reportsDropdown" styleId="distributorsId">
                  <html:option value="">
                    <bean:message key="label.select.distributor" />
                  </html:option>
                  <logic:notEmpty name="beatPlansForm"
                    property="distributorDetailsList">
                    <html:optionsCollection property="distributorDetailsList"
                      label="distributorName" value="distributorId" />
                  </logic:notEmpty>
                </html:select>
                <label>
                  <bean:message key="label.schedule" />
                </label>
                <html:text property="schedule" styleId="schedule"
                  styleClass="timepicker" maxlength="150" readonly="true" />
              </div>
              <div class="left-content rightinputs">
                <label>
                  <bean:message key="label.runner" />
                </label>
                <html:select property="runnersId" styleClass="reportsDropdown"
                  styleId="runnersId">
                  <html:option value="">
                    <bean:message key="label.select.runner" />
                  </html:option>
                  <logic:notEmpty name="beatPlansForm"
                    property="runnerDetailsList">
                    <html:optionsCollection property="runnerDetailsList"
                      label="userName" value="userId" />
                  </logic:notEmpty>
                </html:select>
                <label id="visitNoHint">
                  <bean:message key="label.visit.no"/>
                  <span></span>
                </label>
                <html:select property="visitNo" styleClass="reportsDropdown"
                  styleId="visitNo">
                  <html:option value="">Select Visit No</html:option>
                </html:select>
                <div class="btplnbtn">
                  <a title="Add BeatPlan" id="addToBeatPlan"
                    class="btn btn-primary" onclick="showAllPanels();">
                    <bean:message
                      key="label.add.beat.plan" />
                  </a>
                </div>
              </div>
            </div>
            <div class="divcontent17">
              <div>
                <table width="100%" align="center" cellpadding="0"
                  cellspacing="0" border="0"
                  class="records-page beatPlanDetailsTable"
                  id="beatPlanDetailsTableId">
                  <tr>
                    <th class="runcaffont1">Hub Name</th>
                    <th class="runcaffont1">Zone Name</th>
                    <th class="runcaffont1">Region Name</th>
                    <th class="runcaffont1">Circle Name</th>
                  </tr>
                  <tr>
                    <td id="hub_name_td"></td>
                    <td id="zone_name_td"></td>
                    <td id="region_name_td"></td>
                    <td id="circle_name_td"></td>
                  </tr>
                </table>
              </div>
            </div>
            <div class="divheading3" id="flip4">
              Runners<i class="fa fa-chevron-down downarrow"></i>
            </div>
            <div class="divcontent7" id="panel4">
              <div>
                <table id="beatPlanDetails" width="100%" align="center"
                  cellpadding="0" cellspacing="0" border="0" class="records-page">
                  <thead>
                    <tr class="manage-users-content-headings">
                      <th class="manage-users-content-heading-name" style="width: 18%">BeatPlan</th>
                      <th class="manage-users-content-heading-hub" style="width: 18%">Distributor</th>
                      <th class="manage-users-content-heading-reporting-to" style="width: 18%">Runner</th>
                      <th class="manage-users-content-heading-contact-number" style="width: 18%">Schedule</th>
                      <th class="manage-users-content-heading-contact-number" style="width: 18%">Visit No</th>
                      <th class="manage-users-buttons123" style="width: 10%"></th>
                      <th class="manage-users-buttons123" style="width: 10%"></th>
                    </tr>
                  </thead>
                  <tbody>
                  </tbody>
                </table>
              </div>
            </div>
            <div class="bottombuttons">
              <%
                String type = (String) session.getAttribute("viewType");
                 if (type.equalsIgnoreCase("edit")) {
                %>
              <a id="updateBeatPlan" onclick="updateBeatPlanDetails()"
                title="Update" class="btn btn-primary actionButtons"> Update</a>
              <%
                } else {
                %>
              <a id="saveBeatPlan" onclick="saveBeatPlanDetails()" title="Save"
                class="btn btn-primary actionButtons"> Save</a>
              <%
                }
                %>
              <a id="beat_plan_runner_cancel_hr" href="<%=request.getContextPath()%>/beatPlans.do?method=showManageRunnerBeatPlansPage" title="Cancel" class="btn btn-primary actionButtons"> Cancel</a>
            </div>
          </div>
        </div>
        <input type="hidden" name="method" />
        <input type="hidden" name="<%=Constants.TOKEN_KEY%>"value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>" />
      </html:form>
    </div>
  </body>
</html>