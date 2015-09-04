<script type="text/javascript">
    $(document).ready(function(){
      $('.hubPerformanceReport').click(function() {
          location.href = "<%=request.getContextPath()%>/reports.do?method=showHubPerformanceReportPage";
      });
      $('.zonePerformanceReport').click(function() {
          location.href = "<%=request.getContextPath()%>/reports.do?method=showZonePerformanceReportPage";
      });
   });
</script>
<div class="clear"></div>
<div id="form-content">
<div id="titleheading">
  <div class="navtitle1">Team leader reports Dashboard</div>
  <br />
  <div id="titleheadingbar">
    <div class="navtitle"><i class="fa fa-clipboard"></i>  Reports /  <label>Team leader reports dashboard</label></div>
    </div>
    
    
    <div class="report-users tsm_report_dashboard">
        <div class="report-box1 clsgreen zonePerformanceReport">
          <div class="users clsgreen">
            <i class="fa fa-calendar"></i>
          </div>
          <div id="text-data">Zone Performance</div>
        </div>
        <div class="report-box1 clsbox2 hubPerformanceReport">
          <div class="users clsbox2">
          <i class="fa fa-road"></i>
          </div>
        <div id="text-data">Hub Performance</div>
        </div>   
      </div>
  </div>
</div>
