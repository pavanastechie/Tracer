<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"	prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@page import="com.tracer.common.Constants"%>
<script type="text/javascript">
var roleId;
var authCode;
var saId;
var cbmId;
var rbmId;
var zbmId;
var asmId;
var tsmId;
var circleId = "";
var regionId = "";
var zoneId = "";
var hubId = "";
var requestData = {};
var contextPath = "<%=Constants.TRACER_RWS_URL%>";
    $(document).ready(function(){
      roleId ="<%=request.getSession().getAttribute(Constants.ROLE_ID)%>";
      authCode ="<%=request.getSession().getAttribute(Constants.AUTH_CODE)%>";
      saId ="<%=Constants.SYSTEM_ADMIN%>";
      cbmId ="<%=Constants.CIRCLE_MANAGER%>";
      rbmId ="<%=Constants.REGION_MANAGER%>";
      zbmId ="<%=Constants.ZONE_MANAGER%>";
      asmId ="<%=Constants.HUB_MANAGER%>";
      tsmId ="<%=Constants.TEAM_LEADER%>";
      if(roleId == saId || roleId == saId ) {
          $('.rbm-visits-vs-beatplan').remove();
          $('.zbm-visits-vs-beatplan').remove();
          $('.asm-visits-vs-beatplan').remove();
          $('.tsm-visits-vs-beatplan').remove();
      } else if(roleId == cbmId) {
    	    $('.rbm-visits-vs-beatplan').remove();
          $('.zbm-visits-vs-beatplan').remove();
          $('.asm-visits-vs-beatplan').remove();
          $('.tsm-visits-vs-beatplan').remove();
      } else if(roleId == rbmId) {
    	    $('.sa-visits-vs-beatplan').remove();
          $('.zbm-visits-vs-beatplan').remove();
          $('.asm-visits-vs-beatplan').remove();
          $('.tsm-visits-vs-beatplan').remove();
      } else if(roleId == zbmId) {
    	    $('.sa-visits-vs-beatplan').remove();
          $('.rbm-visits-vs-beatplan').remove();
          $('.asm-visits-vs-beatplan').remove();
          $('.tsm-visits-vs-beatplan').remove();
      }  else if(roleId == asmId) {
    	    $('.sa-visits-vs-beatplan').remove();
          $('.rbm-visits-vs-beatplan').remove();
          $('.zbm-visits-vs-beatplan').remove();
          $('.tsm-visits-vs-beatplan').remove();
      } else if(roleId == tsmId) {
          hubId = "<%=request.getSession().getAttribute(Constants.ASSIGNED_HUB_ID)%>";
          $('.sa-visits-vs-beatplan').remove();
          $('.rbm-visits-vs-beatplan').remove();
          $('.zbm-visits-vs-beatplan').remove();
          $('.asm-visits-vs-beatplan').remove();
      }
      $('#topRunner').click(function() {
        $('#ChartDiv').empty();
        $('.hide-reports-table').hide();
        $('.reportError').text("").hide();
        // Removes all the rows in the table except the first
        $("#visits-table").find("tr:gt(0)").remove();
        var reportsData = {};
        var isValid = true;
        var fromDate = document.reportsForm.fromDate.value || "";
        var toDate = document.reportsForm.toDate.value || "";
        isValid = isValidDetails();
        if(isValid) {
        	<%-- var url = "<%=request.getContextPath()%>/js/jChartFX/data/visits-beatplan-data.json"; --%>
        	 var url = contextPath+"api/reports/visitvsbeatplan/get";
          requestData["authCode"] = authCode;
          requestData["fromDate"] = fromDate;
          requestData["toDate"] = toDate;
          requestData["noOfRecords"] = 5;
          requestData["hubId"] = hubId;
          
          requestData = JSON.stringify(requestData);
        	var chart1 = new cfx.Chart();
        	var rose = new cfx.Rose.Rose();
        	chart1.setGalleryAttributes(rose);

        	var data = chart1.getData();
        	data.setSeries(2);
        	data.setPoints(6);

        	chart1.getSeries().getItem(0).setText("plannedVisits");
        	chart1.getSeries().getItem(1).setText("actualVisits");
        	chart1.getAnimations().getLoad().setEnabled(true);
        	 var title = new cfx.TitleDockable();
           title.setText("Report showing planned & actual beat plan details");
          chart1.getTitles().add(title);
        	chart1.getAxisX().getGrids().getMajor().setVisible(true);
        	chart1.getLegendBox().setDock(cfx.DockArea.Bottom);
        	var promise = callRestAPI(url, requestData);
            promise.done(function (responseData) {
                if(responseData.errorMessage != "undefined") {
              	  if(responseData.visitsVsBeatPlanDetails != null) {
             		  chart1.setDataSource(responseData.visitsVsBeatPlanDetails);
                   var divHolder = document.getElementById('ChartDiv');
                   chart1.create(divHolder);
                   $('.hide-reports-table').show();
                   for(var i= 0; i<responseData.visitsVsBeatPlanDetails.length;i++) {
                      var html = '<tr><td>'+responseData.visitsVsBeatPlanDetails[i].beatPlanName+'</td><td>'+responseData.visitsVsBeatPlanDetails[i].plannedVisits+'</td><td>'+responseData.visitsVsBeatPlanDetails[i].actualVisits+'</td><td>'+responseData.visitsVsBeatPlanDetails[i].date+'</td></tr>';
                      $('#visits-table').append(html);
                   }
              	  } else {
                        $('.reportError').text("No Data Available").show();
                    }
              	} else {
                 $('.reportError').text(responseData.errorMessage).show();
                }
                requestData = {};
              });
            promise.fail(function (data) {
              alert("unable to process request for line chart data");
            });
        }
      });
      
     $('#circlesList').change(function() {
        document.reportsForm.method.value = 'getRegionsList'; 
        document.reportsForm.action = "<%=request.getContextPath()%>/reports.do";
        document.reportsForm.submit();
     });
     
     $('#regionsList').change(function() {
         document.reportsForm.method.value = 'getZonesList'; 
         document.reportsForm.action = "<%=request.getContextPath()%>/reports.do";
         document.reportsForm.submit();
      });
      
       $('#zonesList').change(function() {
        document.reportsForm.method.value = 'getHubsList'; 
        document.reportsForm.action = "<%=request.getContextPath()%>/reports.do";
        document.reportsForm.submit();
     });
       
       function isValidDetails() {
           var fromDate = document.reportsForm.fromDate.value || "";
           var toDate = document.reportsForm.toDate.value || "";
           var fromDateCheck = fromDate.split("/");
           var toDateCheck = toDate.split("/");
           var fromDateTime = new Date(fromDateCheck[0], fromDateCheck[1] - 1 , fromDateCheck[2]);
           var toDateTime = new Date(toDateCheck[0], toDateCheck[1] - 1 , toDateCheck[2]);
           var diffDays = Math.round(Math.abs((new Date(toDate).getTime() - new Date(fromDate).getTime())/(1000*60*60*24)));
           if(roleId == cbmId || roleId == saId ) {
        	   var cId = document.reportsForm.circleId.value || "";
        	   var rId = document.reportsForm.regionId.value || "";
        	   var zId = document.reportsForm.zoneId.value || "";
        	   var hId = document.reportsForm.hubId.value || "";
          	 if(cId != null && cId == 0) {
               $('.reportError').text("Please select a circle").show();
               return false;
             } else if(rId != null && rId == 0) {
               $('.reportError').text("Please select a region").show();
            	 return false;
             } else if(zId != null && zId == 0) {
                 $('.reportError').text("Please select a zone").show();
            	 return false;
             } else if(hId != null && hId == 0) {
                 $('.reportError').text("Please select a hub").show();
                 return false;
              } else {
                  hubId = hId;
              }
           } else if(roleId == rbmId) {
        	   var rId = document.reportsForm.regionId.value || "";
        	   var zId = document.reportsForm.zoneId.value || "";
        	   var hId = document.reportsForm.hubId.value || "";
          	 if(rId != null && rId == 0) {
               $('.reportError').text("Please select a region").show();
            	 return false;
             } else if(zId != null && zId == 0) {
                 $('.reportError').text("Please select a zone").show();
            	 return false;
             } else if(hId != null && hId == 0) {
                 $('.reportError').text("Please select a hub").show();
                 return false;
              } else {
                  hubId = hId;
              }
           } else if(roleId == zbmId) {
        	   var zId = document.reportsForm.zoneId.value || "";
        	   var hId = document.reportsForm.hubId.value || "";
          	 if(zId != null && zId == 0) {
                   $('.reportError').text("Please select a zone").show();
                	 return false;
             } else if(hId != null && hId == 0) {
                 $('.reportError').text("Please select a hub").show();
                 return false;
              } else {
                  hubId = hId;
              }
           }  else if(roleId == asmId) {
        	   var hId = document.reportsForm.hubId.value || "";
          	 if(hId != null && hId == 0) {
                $('.reportError').text("Please select a hub").show();
                return false;
             } else {
               hubId = hId;
             }
          	 
           } 
           if ((fromDate == "" && toDate != "") || (fromDate != "" && toDate == "")) {
             $('.reportError').text("Please Select From and To Dates").show();
             return false;
           } else if(fromDateTime > toDateTime ){
          	 $('.reportError').text("To Date should be greater than From Date").show();
          	 return false;
           } else if(fromDate != "" && toDate != ""){
               if(diffDays != 6) {
                   $('.reportError').text("From  and To Date Range should be exact 7 days").show();
                    return false;
                }
            } 
           return true;
         };
         function callRestAPI(url,data) {
             var ajaxOptions = {
                    type: "POST",
                    url: url,
                    data:data,
                    contentType: "application/json; charset=utf-8",
                    dataType: 'json',
                  };
              var promise = $.ajax(ajaxOptions);
              return promise;
            };
   });
</script>
<div class="clear"></div>
<html:form action="/reports.do">
<div id="form-content">
  <div id="titleheading">
    <div class="navtitle1"><bean:message key="label.reports.visits.vs.beatplan"/></div>
    <br />
    <div id="titleheadingbar">
      <div class="navtitle"><i class="fa fa-clipboard"></i>  <bean:message key="label.reports"/> /  <label><bean:message key="label.reports.visits.vs.beatplan"/></label></div>
      <div id="nav3">
       <%long roleId = 0L;
          if(request.getSession().getAttribute(Constants.ROLE_ID) != null) {
            roleId = (Long) request.getSession().getAttribute(Constants.ROLE_ID);
          }
        %>
      <ul>
        <li>
          <a href="#"><i class="fa fa-chevron-down dropdowndashbd"></i>Reports</a>
          <ul>
          <%if(roleId == Constants.SYSTEM_ADMIN || roleId == Constants.TEAM_LEADER) { %>
            <li><a href="<%=request.getContextPath()%>/reports.do?method=showAttendanceReportPage"><i class="fa fa-clock-o"></i> &nbsp;&nbsp;&nbsp;Daily Attendance of Runners</a></li>
           <%}%>
            <li><a href="<%=request.getContextPath()%>/reports.do?method=showVisitsVsBeatplanReportPage"><i class="fa fa-calendar"></i> &nbsp;&nbsp;&nbsp; Visits VS Beat Plan</a></li>
            <%if(roleId == Constants.SYSTEM_ADMIN || roleId == Constants.CIRCLE_MANAGER || roleId == Constants.TEAM_LEADER || roleId == Constants.HUB_MANAGER) { %>
            <li><a href="<%=request.getContextPath()%>/reports.do?method=showNonPerformedRunnersReportPage"><i class="fa fa-briefcase"></i>&nbsp;&nbsp; Non performing Runners Vs Target</a></li>
            <%} %>
            <li><a href="<%=request.getContextPath()%>/reports.do?method=showTopRunnersReportPage"><i class="fa fa-male"></i> &nbsp;&nbsp; Top Runners</a></li>
            <li><a href="<%=request.getContextPath()%>/reports.do?method=showTopDistributorsReportPage"><i class="fa fa-star-o"></i>&nbsp;&nbsp; Top Distributors</a></li>
            <%if(roleId == Constants.SYSTEM_ADMIN || roleId == Constants.CIRCLE_MANAGER || roleId == Constants.REGION_MANAGER) { %>
            <li><a href="<%=request.getContextPath()%>/reports.do?method=showTopRegionsReportPage"><i class="fa fa-trophy "></i>&nbsp;&nbsp; Top Regions</a></li>
           <%} %>
           <%if(roleId == Constants.SYSTEM_ADMIN || roleId == Constants.CIRCLE_MANAGER || roleId == Constants.REGION_MANAGER || roleId == Constants.ZONE_MANAGER) { %>
            <li><a href="<%=request.getContextPath()%>/reports.do?method=showTopZonesReportPage"><i class="fa fa-certificate"></i>&nbsp;&nbsp; Top Zones</a></li>
            <%} %>
              <%if(roleId != Constants.TEAM_LEADER) { %>
            <li><a href="<%=request.getContextPath()%>/reports.do?method=showTopHubsReportPage"><i class="fa fa-road"></i> &nbsp;&nbsp;Top Hubs</a></li>
            <%} %>
          </ul>
        </li>
      </ul>
    </div>
    </div>
    <div class="reportError"></div>
    <div class="divstyleright reportsBlock">
    <div class="divheading10">
     <bean:message key="label.reports.visits.vs.beatplan"/>
    </div>
    <div class="reportContent innerAddBlock sa-visits-vs-beatplan" id="panel5">
      <div class="displayTable">
        <div class="displayTableRow">
          <div class="displayTableCell reportsTableCellDrpdown" id="circlesListContainer">
           <div class="reportsLabel"><bean:message key="label.reports.circleName"/></div>
           <html:select  property="circleId" styleClass="reportsDropdown" styleId="circlesList">
             <html:option value="0"><bean:message key="label.reports.selectCircle"/></html:option>
             <logic:notEmpty name="reportsForm" property="circleDetailsList">
               <html:optionsCollection  property="circleDetailsList" label="circleName" value="circleId" />
             </logic:notEmpty>
           </html:select>
          </div>
          <div class="displayTableCell reportsTableCellDrpdown" id="regionsListContainer">
           <div class="reportsLabel"><bean:message key="label.reports.regionName"/></div>
          <html:select  property="regionId" styleClass="reportsDropdown" styleId="regionsList">
          <html:option value="0"><bean:message key="label.reports.selectRegion"/></html:option>
             <logic:notEmpty name="reportsForm" property="regionDetailsList">
               <html:optionsCollection  property="regionDetailsList" label="regionName" value="regionId" />
             </logic:notEmpty>
           </html:select>
          </div>
          <div class="displayTableCell reportsTableCellDrpdown" id="zonesListContainer">
           <div class="reportsLabel"><bean:message key="label.reports.zoneName"/></div>
           <html:select  property="zoneId" styleClass="reportsDropdown" styleId="zonesList">
           <html:option value="0"><bean:message key="label.reports.selectZone"/></html:option>
             <logic:notEmpty name="reportsForm" property="zoneDetailsList">
               <html:optionsCollection  property="zoneDetailsList" label="zoneName" value="zoneId" />
             </logic:notEmpty>
           </html:select>
        </div>
      </div>
       <div class="displayTableRow">
          <div class="displayTableCell reportsTableCellDrpdown" id="hubsListContainer">
           <div class="reportsLabel"><bean:message key="label.reports.hubName"/></div>
          <html:select  property="hubId" styleClass="reportsDropdown" styleId="hubssList">
          <html:option value="0"><bean:message key="label.reports.selectHub"/></html:option>
             <logic:notEmpty name="reportsForm" property="hubDetailsList">
               <html:optionsCollection  property="hubDetailsList" label="hubName" value="hubId" />
             </logic:notEmpty>
           </html:select>
          </div>
          <div class="displayTableCell reportsTableCellTxtbox">
           <div class="reportsLabel"><bean:message key="label.reports.dateFrom"/></div>
           <html:text property="fromDate" styleClass="reportsTxtBox datePicker" styleId="reportDateFrom" readonly="true"></html:text>
          </div>
          <div class="displayTableCell reportsTableCellTxtbox">
           <div class="reportsLabel"><bean:message key="label.reports.dateTo"/></div>
           <html:text property="toDate" styleClass="reportsTxtBox datePicker" styleId="reportDateTo" readonly="true"></html:text>
          </div>
        </div>
         </div>
         <div class="reportsBtn">
            <html:button property="" value="Generate" styleClass="btn btn-primary generatebtn" styleId="topRunner"></html:button>
         </div>
    </div>
    <div class="reportContent innerAddBlock rbm-visits-vs-beatplan" id="panel5">
      <div class="displayTable">
        <div class="displayTableRow">
          <div class="displayTableCell reportsTableCellDrpdown" id="regionsListContainer">
           <div class="reportsLabel"><bean:message key="label.reports.regionName"/></div>
          <html:select  property="regionId" styleClass="reportsDropdown" styleId="regionsList">
          <html:option value="0"><bean:message key="label.reports.selectRegion"/></html:option>
             <logic:notEmpty name="reportsForm" property="regionDetailsList">
               <html:optionsCollection  property="regionDetailsList" label="regionName" value="regionId" />
             </logic:notEmpty>
           </html:select>
          </div>
          <div class="displayTableCell reportsTableCellDrpdown" id="zonesListContainer">
           <div class="reportsLabel"><bean:message key="label.reports.zoneName"/></div>
           <html:select  property="zoneId" styleClass="reportsDropdown" styleId="zonesList">
           <html:option value="0"><bean:message key="label.reports.selectZone"/></html:option>
             <logic:notEmpty name="reportsForm" property="zoneDetailsList">
               <html:optionsCollection  property="zoneDetailsList" label="zoneName" value="zoneId" />
             </logic:notEmpty>
           </html:select>
        </div>
        <div class="displayTableCell reportsTableCellDrpdown" id="hubsListContainer">
           <div class="reportsLabel"><bean:message key="label.reports.hubName"/></div>
          <html:select  property="hubId" styleClass="reportsDropdown" styleId="hubssList">
          <html:option value="0"><bean:message key="label.reports.selectHub"/></html:option>
             <logic:notEmpty name="reportsForm" property="hubDetailsList">
               <html:optionsCollection  property="hubDetailsList" label="hubName" value="hubId" />
             </logic:notEmpty>
           </html:select>
          </div>
      </div>
       <div class="displayTableRow">
          <div class="displayTableCell reportsTableCellTxtbox">
           <div class="reportsLabel"><bean:message key="label.reports.dateFrom"/></div>
           <html:text property="fromDate" styleClass="reportsTxtBox datePicker" styleId="reportDateFrom" readonly="true"></html:text>
          </div>
          <div class="displayTableCell reportsTableCellTxtbox">
           <div class="reportsLabel"><bean:message key="label.reports.dateTo"/></div>
           <html:text property="toDate" styleClass="reportsTxtBox datePicker" styleId="reportDateTo" readonly="true"></html:text>
          </div>
        </div>
         </div>
         <div class="reportsBtn">
            <html:button property="" value="Generate" styleClass="btn btn-primary generatebtn" styleId="topRunner"></html:button>
         </div>
    </div>
    <div class="reportContent innerAddBlock zbm-visits-vs-beatplan" id="panel5">
      <div class="displayTable">
        <div class="displayTableRow">
          <div class="displayTableCell reportsTableCellDrpdown" id="zonesListContainer">
           <div class="reportsLabel"><bean:message key="label.reports.zoneName"/></div>
           <html:select  property="zoneId" styleClass="reportsDropdown" styleId="zonesList">
           <html:option value="0"><bean:message key="label.reports.selectZone"/></html:option>
             <logic:notEmpty name="reportsForm" property="zoneDetailsList">
               <html:optionsCollection  property="zoneDetailsList" label="zoneName" value="zoneId" />
             </logic:notEmpty>
           </html:select>
        </div>
         <div class="displayTableCell reportsTableCellDrpdown" id="hubsListContainer">
           <div class="reportsLabel"><bean:message key="label.reports.hubName"/></div>
          <html:select  property="hubId" styleClass="reportsDropdown" styleId="hubssList">
          <html:option value="0"><bean:message key="label.reports.selectHub"/></html:option>
             <logic:notEmpty name="reportsForm" property="hubDetailsList">
               <html:optionsCollection  property="hubDetailsList" label="hubName" value="hubId" />
             </logic:notEmpty>
           </html:select>
          </div>
          <div class="displayTableCell reportsTableCellTxtbox">
           <div class="reportsLabel"><bean:message key="label.reports.dateFrom"/></div>
           <html:text property="fromDate" styleClass="reportsTxtBox datePicker" styleId="reportDateFrom" readonly="true"></html:text>
          </div>
      </div>
       <div class="displayTableRow">
          <div class="displayTableCell reportsTableCellTxtbox">
           <div class="reportsLabel"><bean:message key="label.reports.dateTo"/></div>
           <html:text property="toDate" styleClass="reportsTxtBox datePicker" styleId="reportDateTo" readonly="true"></html:text>
          </div>
        </div>
         </div>
         <div class="reportsBtn">
            <html:button property="" value="Generate" styleClass="btn btn-primary generatebtn" styleId="topRunner"></html:button>
         </div>
    </div>
    <div class="reportContent innerAddBlock asm-visits-vs-beatplan" id="panel5">
      <div class="displayTable">
       <div class="displayTableRow">
          <div class="displayTableCell reportsTableCellDrpdown" id="hubsListContainer">
           <div class="reportsLabel"><bean:message key="label.reports.hubName"/></div>
          <html:select  property="hubId" styleClass="reportsDropdown" styleId="hubssList">
          <html:option value="0"><bean:message key="label.reports.selectHub"/></html:option>
             <logic:notEmpty name="reportsForm" property="hubDetailsList">
               <html:optionsCollection  property="hubDetailsList" label="hubName" value="hubId" />
             </logic:notEmpty>
           </html:select>
          </div>
          <div class="displayTableCell reportsTableCellTxtbox">
           <div class="reportsLabel"><bean:message key="label.reports.dateFrom"/></div>
           <html:text property="fromDate" styleClass="reportsTxtBox datePicker" styleId="reportDateFrom" readonly="true"></html:text>
          </div>
          <div class="displayTableCell reportsTableCellTxtbox">
           <div class="reportsLabel"><bean:message key="label.reports.dateTo"/></div>
           <html:text property="toDate" styleClass="reportsTxtBox datePicker" styleId="reportDateTo" readonly="true"></html:text>
          </div>
        </div>
         </div>
         <div class="reportsBtn">
            <html:button property="" value="Generate" styleClass="btn btn-primary generatebtn" styleId="topRunner"></html:button>
         </div>
    </div>
    <div class="reportContent innerAddBlock tsm-visits-vs-beatplan" id="panel5">
      <div class="displayTable">
       <div class="displayTableRow">
          <div class="displayTableCell reportsTableCellTxtbox">
           <div class="reportsLabel"><bean:message key="label.reports.dateFrom"/></div>
           <html:text property="fromDate" styleClass="reportsTxtBox datePicker" styleId="reportDateFrom" readonly="true"></html:text>
          </div>
          <div class="displayTableCell reportsTableCellTxtbox">
           <div class="reportsLabel"><bean:message key="label.reports.dateTo"/></div>
           <html:text property="toDate" styleClass="reportsTxtBox datePicker" styleId="reportDateTo" readonly="true"></html:text>
          </div>
        </div>
         </div>
         <div class="reportsBtn">
            <html:button property="" value="Generate" styleClass="btn btn-primary generatebtn" styleId="topRunner"></html:button>
         </div>
    </div>
   <div id="ChartDiv"  style="width:500px;height:400px;display:inline-block;float:left; margin-top: 8px;"></div>
    <div  style="width:520px;height:400px;display:inline-block">
    <div id="reportcontent" class="hide-reports-table">
      <table id="visits-table" width="100%" align="center" cellpadding="0" cellspacing="0"
        border="0" class="records-page">
        <tr class="reporttable">
          <th>BeatPlan Name</th>
          <th>Planned Visits</th>
          <th>Actual Visits</th>
          <th>Date</th>
        </tr>
      </table>
    </div></div>
    </div> 
  </div>
</div>
<input type="hidden" name="method" />
</html:form>