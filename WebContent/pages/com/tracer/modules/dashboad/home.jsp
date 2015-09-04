<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%> 
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
    <script type="text/javascript">
    $(document).ready(function(){
      displayPieChart();
      displayAreaChart();
      displayLineChart();
      displayBarChart();
   });
    function callAPI(url) {
      var ajaxOptions = {
            type: "POST",
            url: url,
            contentType: "application/json; charset=utf-8",
            dataType : "json",
          };
      var promise = $.ajax(ajaxOptions);
      return promise;
    };

    function displayPieChart() {
        var chart1 = new cfx.Chart();
        chart1.setGallery(cfx.Gallery.Pie);
        var data = chart1.getData();
        data.setPoints(3);
        data.setSeries(2);
        var titles = chart1.getTitles();
        var title = new cfx.TitleDockable();
        title.setText("CapForms Collected in Year 2013");
        titles.add(title);
        chart1.getAllSeries().getPointLabels().setVisible(true);
        chart1.getAnimations().getLoad().setEnabled(true);
        var url = "<%=request.getContextPath()%>/js/jChartFX/data/piechart-data.json";
        var promise = callAPI(url);
        promise.done(function (data) {
          chart1.setDataSource(data);
          var divHolder = document.getElementById('ChartDiv1');
          chart1.create(divHolder);
        });
        promise.fail(function (data) {
          alert("unable to process request for hubs");
        });
    };
    
    function displayAreaChart() {
    	var chart1 = new cfx.Chart();
        chart1.getData().setSeries(1);
        chart1.getAxisY().setMin(500);
        chart1.getAxisY().setMax(2000);
        var series2 = chart1.getSeries().getItem(0);
        series2.setGallery(cfx.Gallery.Area);
        chart1.getAnimations().getLoad().setEnabled(true);
        var url = "<%=request.getContextPath()%>/js/jChartFX/data/areachart-data.json";
        var promise = callAPI(url);
        promise.done(function (data) {
          chart1.setDataSource(data);
          var divHolder = document.getElementById('ChartDiv3');
          chart1.create(divHolder);
        });
        promise.fail(function (data) {
          alert("unable to process request for area chart data");
        });
    };
    
    function displayLineChart() {
    	var chart1 = new cfx.Chart();
    	chart1 = new cfx.Chart();
    	chart1.setGallery(cfx.Gallery.Lines);
    	var titles = chart1.getTitles();
    	var title = new cfx.TitleDockable();
    	title.setText("Vehicles Production by Month");
    	titles.add(title);
    	chart1.getAnimations().getLoad().setEnabled(true);
        var url = "<%=request.getContextPath()%>/js/jChartFX/data/linechart-data.json";
        var promise = callAPI(url);
        promise.done(function (data) {
          chart1.setDataSource(data);
          var divHolder = document.getElementById('ChartDiv2');
          chart1.create(divHolder);
        });
        promise.fail(function (data) {
          alert("unable to process request for line chart data");
        });
    };
    
    function displayBarChart() {
    	var chart1 = new cfx.Chart();
        chart1.getData().setSeries(1);
        chart1.getAxisY().setMin(500);
        chart1.getAxisY().setMax(2000);
        var series2 = chart1.getSeries().getItem(0);
        series2.setGallery(cfx.Gallery.Bar);
        chart1.getAnimations().getLoad().setEnabled(true);
        var url = "<%=request.getContextPath()%>/js/jChartFX/data/barchart-data.json";
        var promise = callAPI(url);
        promise.done(function (data) {
          chart1.setDataSource(data);
          var divHolder = document.getElementById('ChartDiv4');
          chart1.create(divHolder);
        });
        promise.fail(function (data) {
          alert("unable to process request for line chart data");
        });
    };
    </script>
  </head>
<body>

  <div class="center">
  <div id="ChartDiv1" style="width:500px;height:400px;display:inline-block"></div>
  <div id="ChartDiv2" style="width:500px;height:400px;display:inline-block"></div>
  <div id="ChartDiv3" style="width:500px;height:400px;display:inline-block"></div>
  <div id="ChartDiv4" style="width:500px;height:400px;display:inline-block"></div>
</div>
<div>

</div>
</body>
</html>