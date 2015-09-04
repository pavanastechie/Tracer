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
          if(roleId == cbmId || roleId == saId ) {
            $('.rbm-distributors').remove();
            $('.zbm-distributors').remove();
            $('.asm-distributors').remove();
            $('.tsm-distributors').remove();
          } else if(roleId == rbmId) {
        	  $('.sa-distributors').remove();
              $('.zbm-distributors').remove();
              $('.asm-distributors').remove();
              $('.tsm-distributors').remove();
          } else if(roleId == zbmId) {
        	  $('.sa-distributors').remove();
              $('.rbm-distributors').remove();
              $('.asm-distributors').remove();   
              $('.tsm-distributors').remove();
          }  else if(roleId == asmId) {
        	  $('.sa-distributors').remove();
              $('.rbm-distributors').remove();
              $('.zbm-distributors').remove();
              $('.tsm-distributors').remove();
          } else if(roleId == tsmId) {
        	  hubId = "<%=request.getSession().getAttribute(Constants.ASSIGNED_HUB_ID)%>";
        	  $('.sa-distributors').remove();
              $('.rbm-distributors').remove();
              $('.zbm-distributors').remove();
              $('.asm-distributors').remove();
          }
      $('#topDistributor').click(function() {
    	  $('#ChartDiv').empty();
          $('.reportError').text("").hide();
          $('.hide-reports-table').hide();
          // Removes all the rows in the table except the first
          $("#top-distributors").find("tr:gt(0)").remove();
          var reportsData = {};
          var isValid = true;
          var fromDate = document.reportsForm.fromDate.value || "";
          var toDate = document.reportsForm.toDate.value || "";
      	<%-- var url = "<%=request.getContextPath()%>/js/jChartFX/data/top-distributors.json";  --%>
       var url = contextPath+"api/reports/top/distributors/get";
         
          isValid = isValidDetails();
          if(isValid) {
        	  requestData["authCode"] = authCode;
              requestData["fromDate"] = fromDate;
              requestData["toDate"] = toDate;
              requestData["noOfRecords"] = 5;
              requestData["circleId"] = circleId;
              requestData["regionId"] = regionId;
              requestData["zoneId"] = zoneId;
              requestData["hubId"] = hubId;
              
              requestData = JSON.stringify(requestData);
        	  var promise = callRestAPI(url, requestData);
              promise.done(function (responseData) {
               var chart1 = new cfx.Chart();
               if(responseData.errorMessage != "undefined") {
               	  if(responseData.topDistributors != null) {
               		 chart1.setDataSource(responseData.topDistributors);
                     chart1.setGallery(cfx.Gallery.Pyramid);
                     var fields = chart1.getDataSourceSettings().getFields();

                     var field1 = new cfx.FieldMap();
                     field1.setName("Distributor Name");
                     field1.setUsage(cfx.FieldUsage.RowHeading);
                     fields.add(field1);

                     var fieldVal = new cfx.FieldMap();
                     fieldVal.setName("CAF Count");
                     fieldVal.setUsage(cfx.FieldUsage.Value);
                     fields.add(fieldVal);
                     var data = chart1.getData();
                     data.setSeries(1);

                     chart1.getAllSeries().getPointLabels().setVisible(true);
                     chart1.getLegendBox().setDock(cfx.DockArea.Bottom);
                     chart1.getView3D().setEnabled(true);
                      var divHolder = document.getElementById('ChartDiv');
                      chart1.create(divHolder);
                      $('.hide-reports-table').show();
                    for(var i= 0; i<responseData.topDistributors.length;i++) {
                       var html = '<tr><td>'+responseData.topDistributors[i].distributorName+'</td><td>'+responseData.topDistributors[i].cafCount+'</td><td>'+responseData.topDistributors[i].hubName+'</td></tr>';
                       $('#top-distributors').append(html);
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
            	  requestData = {};
                console.log("unable to process request for line chart data");
              });
          };
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
             } else if(hId != null && hId != 0){
               hubId = hId;
             } else if(zId != null && zId != 0) {
              zoneId = zId;
             } else if(rId != null && rId != 0) {
              regionId = rId;
             } else {
          	   circleId = cId;
             }
           } else if(roleId == rbmId) {
          	 var rId = document.reportsForm.regionId.value || "";
          	 var zId = document.reportsForm.zoneId.value || "";
             var hId = document.reportsForm.hubId.value || "";
          	 if(rId != null && rId == 0) {
               $('.reportError').text("Please select a region").show();
            	 return false;
             } else if(hId != null && hId != 0){
               hubId = hId;
             } else if(zId != null && zId != 0) {
               zoneId = zId;
             } else {
          	   regionId = rId;
             }
           } else if(roleId == zbmId) {
          	 var zId = document.reportsForm.zoneId.value || "";
          	 var hId = document.reportsForm.hubId.value || "";
          	 if(zId != null && zId == 0) {
               $('.reportError').text("Please select a zone").show();
               return false;
             } else if(hId != null && hId != 0){
               hubId = hId;
             } else {
          	  zoneId = zId;
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
    <div class="navtitle1"><bean:message key="label.reports.topDistributors"/></div>
    <br />
    <div id="titleheadingbar">
      <div class="navtitle"><i class="fa fa-clipboard"></i>  <bean:message key="label.reports"/> /  <label><bean:message key="label.reports.topDistributors"/></label></div>
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
          <%-- <%if(roleId == Constants.SYSTEM_ADMIN || roleId == Constants.TEAM_LEADER) { %>
            <li><a href="<%=request.getContextPath()%>/reports.do?method=showAttendanceReportPage"><i class="fa fa-clock-o"></i> &nbsp;&nbsp;&nbsp;Daily Attendance of Runners</a></li>
           <%}%> 
            <li><a href="<%=request.getContextPath()%>/reports.do?method=showVisitsVsBeatplanReportPage"><i class="fa fa-calendar"></i> &nbsp;&nbsp;&nbsp; Visits VS Beat Plan</a></li>--%>
             <%if(roleId == Constants.SYSTEM_ADMIN || roleId == Constants.CIRCLE_MANAGER || roleId == Constants.REGION_MANAGER) { %>
            <li><a href="<%=request.getContextPath()%>/reports.do?method=showTopRegionsReportPage"><i class="fa fa-trophy "></i>&nbsp;&nbsp; Top Regions</a></li>
           <%} %>
            <%if(roleId == Constants.SYSTEM_ADMIN || roleId == Constants.CIRCLE_MANAGER || roleId == Constants.REGION_MANAGER || roleId == Constants.ZONE_MANAGER) { %>
            <li><a href="<%=request.getContextPath()%>/reports.do?method=showTopZonesReportPage"><i class="fa fa-certificate"></i>&nbsp;&nbsp; Top Zones</a></li>
            <%} %>
             <%if(roleId != Constants.TEAM_LEADER) { %>
            <li><a href="<%=request.getContextPath()%>/reports.do?method=showTopHubsReportPage"><i class="fa fa-road"></i> &nbsp;&nbsp;Top Hubs</a></li>
            <%} %>
            <li><a href="<%=request.getContextPath()%>/reports.do?method=showTopDistributorsReportPage"><i class="fa fa-star-o"></i>&nbsp;&nbsp; Top Distributors</a></li>
            <%if(roleId == Constants.ZONE_MANAGER || roleId == Constants.HUB_MANAGER) { %>
            <li><a href="<%=request.getContextPath()%>/reports.do?method=showTopRunnersReportPage"><i class="fa fa-male"></i> &nbsp;&nbsp; Top Runners</a></li>
            <%} %>
            <%if(roleId == Constants.SYSTEM_ADMIN || roleId == Constants.HUB_MANAGER) { %>
            <li><a href="<%=request.getContextPath()%>/reports.do?method=showNonPerformedRunnersReportPage"><i class="fa fa-briefcase"></i>&nbsp;&nbsp; Non performing Runners Vs Target</a></li>
            <%} %>
          </ul>
        </li>
      </ul>
    </div>
    </div>
    <div class="reportError"></div>
    <div class="divstyleright reportsBlock">
    <div class="reportheading">
     <bean:message key="label.reports.topDistributors"/>
    </div>
    <div class="reportContent innerAddBlock sa-distributors" id="panel5">
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
            <html:button property="" value="Generate" styleClass="btn btn-primary generatebtn" styleId="topDistributor"></html:button>
         </div>
    </div>
    <div class="reportContent innerAddBlock rbm-distributors" id="panel5">
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
            <html:button property="" value="Generate" styleClass="btn btn-primary generatebtn" styleId="topDistributor"></html:button>
         </div>
    </div>
    <div class="reportContent innerAddBlock zbm-distributors" id="panel5">
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
       <div class="displayTableCell reportsTableCellTxtbox">
           <div class="reportsLabel"><bean:message key="label.reports.dateTo"/></div>
           <html:text property="toDate" styleClass="reportsTxtBox datePicker" styleId="reportDateTo" readonly="true"></html:text>
          </div>
         </div>
         <div class="reportsBtn">
            <html:button property="" value="Generate" styleClass="btn btn-primary generatebtn" styleId="topDistributor"></html:button>
         </div>
    </div>
    <div class="reportContent innerAddBlock asm-distributors" id="panel5">
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
            <html:button property="" value="Generate" styleClass="btn btn-primary generatebtn" styleId="topDistributor"></html:button>
         </div>
    </div>
    <div class="reportContent innerAddBlock tsm-distributors" id="panel5">
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
            <html:button property="" value="Generate" styleClass="btn btn-primary generatebtn" styleId="topDistributor"></html:button>
         </div>
    </div>
    <div id="ChartDiv"  style="width:500px;height:500px;display:inline-block;float:left; margin-top: 8px;"></div>
    <div  style="width:520px;height:400px;display:inline-block">
    <div id="reportcontent" class="hide-reports-table">
      <table id="top-distributors" width="100%" align="center" cellpadding="0" cellspacing="0"
        border="0" class="records-page">
        <tr class="reporttable">
          <th>Distributor Name</th>
          <th>CAF Count</th>
          <th>Hub</th>
        </tr>
      </table>
    </div></div>
    </div>
</div>
<input type="hidden" name="method" />
</html:form>