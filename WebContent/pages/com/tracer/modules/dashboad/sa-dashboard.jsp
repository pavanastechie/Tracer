<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"	prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@page import="com.tracer.common.Constants"%>
<script type="text/javascript">
<%-- var chartDivArray = [];
  var circleId = "<%=request.getSession().getAttribute(Constants.ASSIGNED_CIRCLE_ID)%>";
  var authCode ="<%=request.getSession().getAttribute(Constants.AUTH_CODE)%>";
  var requestData = {};
  requestData["circleId"] = 1;
   var contextPath = "<%=Constants.TRACER_RWS_URL%>";
  chartDivArray.push("ChartDiv1");
  chartDivArray.push("ChartDiv2");
  chartDivArray.push("ChartDiv3");
  chartDivArray.push("ChartDiv4");
  $(document).ready(function(){
    $('.dbaplybtn').click(function() {
    	$('.dashboard-error').text("").hide();
      var selectedList = "";
      var selectedLength = $('#dashboard-reports-list li').find('input:checked').length;
      if(selectedLength < 4) {
        $('.dashboard-error').text("Please select atleast four reports").show();
      } else if(selectedLength > 4) {
       $('.dashboard-error').text("Please select maximum four reports").show();
      } else {
        selectedList = getSelectedCheckboxes();
        document.dashboardForm.action = "<%=request.getContextPath()%>/dashboard.do?method=saveDashBoardDetails&selectedCheckboxes="+selectedList;
        document.dashboardForm.submit();
      }
    }); 

    if($('#nonPerformedRunnersReport').is(':checked')) {
    	var divName = chartDivArray.pop();
    	 requestData["authCode"] = authCode;
    	 requestData["fromDate"] = "";
    	  requestData["toDate"] = "";
    	  requestData["noOfRecords"] = "";
    	  requestData["regionId"] = "";
    	  requestData["zoneId"] = "";
    	  requestData["hubId"] = "";
      if(divName != undefined)
        displayNonPerformedRunnersReport(divName,contextPath,requestData);
      requestData = {};
    }
    if($('#topRunnersReport').is(':checked')) {
    	var divName = chartDivArray.pop();
    	 requestData["fromDate"] = "";
   	  requestData["authCode"] = authCode;
       requestData["toDate"] = "";
       requestData["noOfRecords"] = "";
       requestData["regionId"] = "";
       requestData["zoneId"] = "";
       requestData["hubId"] = "";
      if(divName != undefined)
        displayTopRunnersReport(divName,contextPath,requestData);
      requestData = {};
    }
    if($('#topDistributorsReport').is(':checked')) {
    	var divName = chartDivArray.pop();
    	requestData["fromDate"] = "";
   	 requestData["authCode"] = authCode;
       requestData["toDate"] = "";
       requestData["noOfRecords"] = "";
       requestData["regionId"] = "";
       requestData["zoneId"] = "";
       requestData["hubId"] = "";
      if(divName != undefined)
        displayTopDistributorsReport(divName,contextPath,requestData);
      requestData = {};
    }
    if($('#topRegionsReport').is(':checked')) {
    	var divName = chartDivArray.pop();
    	requestData["fromDate"] = "";
   	 requestData["authCode"] = authCode;
       requestData["toDate"] = "";
       requestData["noOfRecords"] ="";
      if(divName != undefined)
        displayTopRegionsReport(divName,contextPath,requestData);
      requestData = {};
    }
    if($('#topZonesReport').is(':checked')) {
    	var divName = chartDivArray.pop();
    	requestData["fromDate"] = "";
  	  requestData["authCode"] = authCode;
      requestData["toDate"] = "";
      requestData["noOfRecords"] = "";
      requestData["regionId"] = "";
      if(divName != undefined)
        displayTopZonesReport(divName,contextPath,requestData);
      requestData = {};
    }
    if($('#topHubsReport').is(':checked')) {
      var divName = chartDivArray.pop();
      requestData["fromDate"] = "";
      requestData["authCode"] = authCode;
      requestData["toDate"] = "";
      requestData["noOfRecords"] = "";
      requestData["regionId"] = "";
      requestData["zoneId"] = "";
      if(divName != undefined)
        displayTopHubsReport(divName,contextPath,requestData);
      requestData = {};
    } 
  }); --%>
  
  
</script>
    <div class="clear"></div>
  <html:form action="/dashboard.do">
  <div id="form-content">
    <div id="titleheading">
      <div class="navtitle1"> Admin Dashboard</div><div class="dashboard-error"></div>
      <br />
      <div id="titleheadingbar">
        <div class="navtitle"><i class="fa fa-dashboard"></i> Admin  Dashboard</div>
        <%-- <div id="nav2">
          <ul>
            <li><a href="#"><i
                class="fa fa-chevron-down dropdowndashbd"></i>Customize Dashboard</a>
              <ul id="dashboard-reports-list">
                <li><a href="#"><html:checkbox property="attendanceReport" styleId="attendanceReport" name="dashboardForm" styleClass="reports-checkbox"></html:checkbox>&nbsp;&nbsp;&nbsp;<i class="fa fa-clock-o"></i>&nbsp;&nbsp; Daily Attendance of Runners</a></li>
                <li><a href="#"><html:checkbox property="visitsVsBeatPlanReport" styleId="visitsVsBeatPlanReport" name="dashboardForm" styleClass="reports-checkbox"></html:checkbox>&nbsp;&nbsp;&nbsp;<i class="fa fa-calendar"></i>&nbsp;&nbsp; Visits VS Beat Plan</a></li>
                <li><a href="#"><html:checkbox property="nonPerformedRunnersReport" styleId="nonPerformedRunnersReport" name="dashboardForm" styleClass="reports-checkbox"></html:checkbox>&nbsp;&nbsp;&nbsp;<i class="fa fa-list-ul"></i>&nbsp;&nbsp; Non performing Runners Vs Target</a></li>
                <li><a href="#"><html:checkbox property="topRunnersReport" styleId="topRunnersReport" name="dashboardForm" styleClass="reports-checkbox"></html:checkbox>&nbsp;&nbsp;&nbsp;<i class="fa fa-male"></i> &nbsp;&nbsp; Top Runners</a></li>
                <li><a href="#"><html:checkbox property="topDistributorsReport" styleId="topDistributorsReport" name="dashboardForm" styleClass="reports-checkbox"></html:checkbox>&nbsp;&nbsp;&nbsp;<i class="fa fa-star-o"></i>&nbsp;&nbsp; Top Distributors</a></li>
                <li><a href="#"><html:checkbox property="topRegionsReport" styleId="topRegionsReport" name="dashboardForm" styleClass="reports-checkbox"></html:checkbox>&nbsp;&nbsp;&nbsp;<i class="fa fa-trophy "></i>&nbsp;&nbsp; Top Regions</a></li>
                <li><a href="#"><html:checkbox property="topZonesReport" styleId="topZonesReport" name="dashboardForm" styleClass="reports-checkbox"></html:checkbox>&nbsp;&nbsp;&nbsp;<i class="fa fa-certificate"></i>&nbsp;&nbsp; Top Zones</a></li>
                <li><a href="#"><html:checkbox property="topHubsReport" styleId="topHubsReport" name="dashboardForm" styleClass="reports-checkbox"></html:checkbox>&nbsp;&nbsp;&nbsp;<i class="fa fa-road"></i>  &nbsp;&nbsp; Top Hubs</a></li>
                <div><input type="button"  class="btn btn-primary dbaplybtn" value="Apply"><i class="fa fa-check"></i></div>
              </ul>
           </li>
          </ul>
        </div> --%>
      </div>
      <!---center starts--->

     <!--  <div id="left-div">
      	<div class="dashbddiv">
          <div id="ChartDiv1" style="width:500px;height:500px;display:inline-block"></div>
        </div>
        <br/><br/><br/>
        <div class="dashbddiv">
         <div id="ChartDiv2" style="width:500px;height:500px;display:inline-block"></div>
       	</div>
     </div>

      <div id="right-div">
      	<div class="dashbddiv2">
         <div id="ChartDiv3" style="width:500px;height:500px;display:inline-block"></div>
        </div>
        
        <br/><br/><br/>
        <div class="dashbddiv2">
          <div id="ChartDiv4" style="width:500px;height:500px;display:inline-block"></div>
      	</div>
      </div> -->

    </div>
    <!---center ends--->
    
    </div>
    <input type="hidden" name="method" />
    </html:form>