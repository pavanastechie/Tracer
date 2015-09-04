<script type="text/javascript">
    $(document).ready(function(){
      $('.topRunners').click(function() {
        location.href = "<%=request.getContextPath()%>/reports.do?method=showTopRunnersReportPage";
      });

      $('.visitsVsBeatPlan').click(function() {
          location.href = "<%=request.getContextPath()%>/reports.do?method=showVisitsVsBeatplanReportPage";
      });
      
      $('.zones').click(function() {
          location.href = "<%=request.getContextPath()%>/reports.do?method=showTopZonesReportPage";
      });
      
      $('.distributors').click(function() {
          location.href = "<%=request.getContextPath()%>/reports.do?method=showTopDistributorsReportPage";
      });

      $('.hubs').click(function() {
          location.href = "<%=request.getContextPath()%>/reports.do?method=showTopHubsReportPage";
      });
   });
</script>
<div class="clear"></div>
<div id="form-content">
<div id="titleheading">
  <div class="navtitle1">Zone manager reports dashboard</div>
  <br />
  <div id="titleheadingbar">
    <div class="navtitle"><i class="fa fa-clipboard"></i>  Reports /  <label>Zone manager reports dashboard</label></div>
    </div>
    <div class="report-users">
                    <div class="report-box1 clsbox4 zones">
          <div class="users clsbox4">
          <i class="fa fa-certificate"></i>
          </div>
          <div id="text-data">Top Zones</div>
        </div> 
                 <div class="report-box1 clsbox5 hubs">
          <div class="users clsbox5">
            <i class="fa fa-road"></i>
          </div>
          <div id="text-data">Top Hubs</div>
        </div> 
                  <div class="report-box1 clsbox2 distributors">
          <div class="users clsbox2">
          <i class="fa fa-star-o"></i>
          </div>
        <div id="text-data">Top Distributors</div>
        </div> 
        <div class="report-box1 clsbox1 topRunners">
          <div class="users clsbox1">
            <i class="fa fa-male"></i>
          </div>
          <div id="text-data">Top Runners</div>
        </div>   

      </div>
<!--       <div class="report-users">
              <div class="report-box1 clsgreen visitsVsBeatPlan">
          <div class="users clsgreen">
            <i class="fa fa-calendar"></i>
          </div>
          <div id="text-data">Visits <br>Vs<br> Beat Plan</div>
        </div>
      </div> -->
  </div>
</div>
