<%@page import="java.util.Date"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@page import="com.tracer.common.Constants"%>
<%@page import="java.text.SimpleDateFormat" %>  
<script type="text/javascript">
$(document).ready(function(){
	 $('.trackRunner').click(function(e) {
		 var runnnerId = $(e.target).attr("data-runner-id");
	   document.dashboardForm.action = "<%=request.getContextPath()%>/track.do?method=showRunnerCAFCollectionDetailsPage&id="+runnnerId;
	   document.dashboardForm.submit();
	 });
		<%SimpleDateFormat ft = new SimpleDateFormat ("EEEE, dd MMM yyyy 'at' hh:mm:ss a");
		String date= ft.format(new Date());%>
	 $('#current-date').text("<%=date%>");
});
</script>
<div class="clear"></div>
<html:form action="/dashboard.do">
  <div id="form-content">
    <div id="titleheading">
      <div class="navtitle1">Team Leader Dashboard</div>
      <div class="dashboard-error">Please Select Four Reports</div>
      <br />
      <div id="titleheadingbar">
        <div class="navtitle"><i class="fa fa-dashboard"></i> Team Leader Dashboard</div>
      </div>
      <div class="tsmdate">Displaying the Team Leader's Dashboard as of : <span id="current-date"></span> <span class="dashboard-hubname">Hub Name: <%=request.getSession().getAttribute(Constants.ASSIGNED_HUB_NAME)%></span></div>
      <div>
        <table width="95%" align="center" cellpadding="0" cellspacing="0" border="1px solid #000000;" class="tsmtable">
          <tr class="tsmheadercols">
            <td  colspan="2">&nbsp;</td>
            <td  colspan="2">Visit Information</td>
            <td  colspan="2">10:00 A.M</td>
            <td  colspan="2">12:00 P.M</td>
            <td  colspan="2">2:00 PM</td>
            <td  colspan="2">4:00 PM</td>
            <td  colspan="2">6:00 PM</td>
            </td>
          </tr>
          <tr class="tsmheader">
            <td>Runner Name</td>
            <td>Total CAFs collected</td>
            <td>Total Visits</td>
            <td>Actual Visits</td>
            <td>Target</td>
            <td>Actual</td>
            <td>Target</td>
            <td>Actual</td>
            <td>Target</td>
            <td>Actual</td>
            <td>Target</td>
            <td>Actual</td>
            <td>Target</td>
            <td>Actual</td>
          </tr>
          <logic:notEmpty property="runnersCAFTrack" name="dashboardForm">
            <logic:iterate id="runnerCAFTrackDetails" name="dashboardForm" property="runnersCAFTrack" type="com.tracer.dao.model.RunnerCAFTrackDetails">
              <tr>
                <td class="trackRunner" title="Click to Track Runner" data-runner-id="<bean:write property="runnerUserId" name="runnerCAFTrackDetails"/>"><bean:write property="runnerName" name="runnerCAFTrackDetails"/></td>
                <td><bean:write property="totalCAFCollected" name="runnerCAFTrackDetails"/></td>
              
                <td><bean:write property="totalVisits" name="runnerCAFTrackDetails"/></td>
                <td><bean:write property="totalVisitsCompleted" name="runnerCAFTrackDetails"/></td>
              
                <td class="help-cursor" title="<bean:write property="slot1Target" name="runnerCAFTrackDetails"/> Visits"><bean:write property="slot1TargetPercentage" name="runnerCAFTrackDetails"/></td>
                <td title="<bean:write property="slot1Achieved" name="runnerCAFTrackDetails"/> Visits"><bean:write property="slot1AchievedPercentage" name="runnerCAFTrackDetails"/></td>
              
                <td class="help-cursor" title="<bean:write property="slot2Target" name="runnerCAFTrackDetails"/> Visits"><bean:write property="slot2TargetPercentage" name="runnerCAFTrackDetails"/></td>
                <td title="<bean:write property="slot2Achieved" name="runnerCAFTrackDetails"/> Visits"><bean:write property="slot2AchievedPercentage" name="runnerCAFTrackDetails"/></td>
              
                <td class="help-cursor" title="<bean:write property="slot3Target" name="runnerCAFTrackDetails"/> Visits"><bean:write property="slot3TargetPercentage" name="runnerCAFTrackDetails"/></td>
                <td title="<bean:write property="slot3Achieved" name="runnerCAFTrackDetails"/> Visits"><bean:write property="slot3AchievedPercentage" name="runnerCAFTrackDetails"/></td>
              
                <td class="help-cursor" title="<bean:write property="slot4Target" name="runnerCAFTrackDetails"/> Visits"><bean:write property="slot4TargetPercentage" name="runnerCAFTrackDetails"/></td>
                <td title="<bean:write property="slot4Achieved" name="runnerCAFTrackDetails"/> Visits"><bean:write property="slot4AchievedPercentage" name="runnerCAFTrackDetails"/></td>
              
                <td class="help-cursor" title="<bean:write property="slot5Target" name="runnerCAFTrackDetails"/> Visits"><bean:write property="slot5TargetPercentage" name="runnerCAFTrackDetails"/></td>
                <td title="<bean:write property="slot5Achieved" name="runnerCAFTrackDetails"/> Visits"><bean:write property="slot5AchievedPercentage" name="runnerCAFTrackDetails"/></td>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
        </table>
        <%--   <a href="<%=request.getContextPath()%>/pages/com/tracer/modules/dashboad/export-tsm-dashboard.xls" >Excel</a> --%>
      </div>
    </div>
  </div>
  <input type="hidden" name="method" />
</html:form>