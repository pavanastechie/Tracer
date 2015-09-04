function getSelectedCheckboxes() {
  var selectedCheckboxes = [];
  var selectedList = "";
    if($('#attendanceReport').is(':checked')) {
      selectedCheckboxes.push("attendanceReport");
    } 
    if($('#visitsVsBeatPlanReport').is(':checked')) {
      selectedCheckboxes.push("visitsVsBeatPlanReport");
    } 
    if($('#nonPerformedRunnersReport').is(':checked')) {
      selectedCheckboxes.push("nonPerformedRunnersReport");
    } 
    if($('#topRunnersReport').is(':checked')) {
      selectedCheckboxes.push("topRunnersReport");
    } 
    if($('#topDistributorsReport').is(':checked')) {
      selectedCheckboxes.push("topDistributorsReport");
    } 
    if($('#topRegionsReport').is(':checked')) {
      selectedCheckboxes.push("topRegionsReport");
    } 
    if($('#topZonesReport').is(':checked')) {
    	selectedCheckboxes.push("topZonesReport");
    } 
    if($('#topHubsReport').is(':checked')) {
      selectedCheckboxes.push("topHubsReport");
    } 
    for ( var i = 0; i < selectedCheckboxes.length; i++) {
      selectedList = selectedList + selectedCheckboxes[i] +",";
    }
  return selectedList;
};
function displayAttendanceReport(divName,contextPath,requestData) {
   var url = contextPath+"api/reports/attendance/get"; 
	/*var url = contextPath+"/js/jChartFX/data/attendance-data.json";*/
  var chart1 = new cfx.Chart();

  /*var url =  contextPath + "/js/jChartFX/data/attendance-data.json";*/
    var promise = getChartsData(url,requestData);
    promise.done(function (responseData) {
    	if(responseData.errorMessage != "undefined") {
       	  if(responseData.attendanceDetails != null) {
       		chart1.setDataSource(responseData.attendanceDetails);
       	    chart1.getData().setSeries(1);
       	    chart1.getAxisY().setMin(0);
       	    chart1.getAxisY().setMax(100);
       	    var series2 = chart1.getSeries().getItem(0);
       	    series2.setGallery(cfx.Gallery.Bar);
       	    chart1.getAnimations().getLoad().setEnabled(true);
       	    chart1.getAllSeries().getPointLabels().setVisible(true);
       	    chart1.getAnimations().getLoad().setEnabled(true);
       	    chart1.getLegendBox().setDock(cfx.DockArea.Bottom);
            var divHolder = document.getElementById(divName);
            $(divHolder).prepend('<div class="dashboard-report-title">Attendance report of runners</div>');
            chart1.create(divHolder);
            }
       	  } 
    });
    promise.fail(function (data) {
      console.log("unable to process request for line chart data");
    });
};

function displayVisitsVsBeatPlanReport(divName,contextPath,requestData) {
	var url = contextPath+"api/reports/visitvsbeatplan/get"; 
	var chart1 = new cfx.Chart();
  /* var url =  contextPath + "/js/jChartFX/data/visits-beatplan-data.json";*/
	var promise = getChartsData(url,requestData);
    promise.done(function (responseData) {
    	var promise = getChartsData(url,requestData);
        if(responseData.errorMessage != "undefined") {
          if(responseData.visitsVsBeatPlanDetails != null) {
              chart1.setDataSource(responseData.visitsVsBeatPlanDetails);
              var rose = new cfx.Rose.Rose();
          	chart1.setGalleryAttributes(rose);

          	var data = chart1.getData();
          	data.setSeries(2);
          	data.setPoints(6);

          	chart1.getSeries().getItem(0).setText("Estimated");
          	chart1.getSeries().getItem(1).setText("Actual");
          	chart1.getAxisX().getGrids().getMajor().setVisible(true);
          	chart1.getAnimations().getLoad().setEnabled(true);
          	chart1.getLegendBox().setDock(cfx.DockArea.Bottom);
              var divHolder = document.getElementById(divName);
              $(divHolder).prepend('<div class="dashboard-report-title">Report showing estimated and actual beat plan details</div>');
              chart1.create(divHolder);
              }
         } 
    });
    promise.fail(function (data) {
      console.log("unable to process request for line chart data");
    });
};

  function displayNonPerformedRunnersReport(divName,contextPath,requestData) {
	  var chart1 = new cfx.Chart();
  	chart1 = new cfx.Chart();
   /* var url =  contextPath + "/js/jChartFX/data/non-performers-data.json";*/
    var url = contextPath+"api/reports/nonperformed/runners/get";
    var promise = getChartsData(url,requestData);
      promise.done(function (responseData) {
    	  if(responseData.errorMessage != "undefined") {
              if(responseData.nonPerformedRunners != null) {
                  chart1.setDataSource(responseData.nonPerformedRunners);
                  chart1.getData().setSeries(2);
                  chart1.getAxisY().setMin(0);
                  chart1.getAxisY().setMax(1000);
                  var series1 = chart1.getSeries().getItem(1);
                  var series2 = chart1.getSeries().getItem(0);
                  series1.setGallery(cfx.Gallery.Lines);
                  series2.setGallery(cfx.Gallery.Bar);
                  chart1.getAnimations().getLoad().setEnabled(true);
                  var divHolder = document.getElementById(divName);
                  $(divHolder).prepend('<div class="dashboard-report-title">Report showing non performing runners vs target</div>');
                  chart1.create(divHolder);
                  }
             }
      });
      promise.fail(function (data) {
        console.log("unable to process request for line chart data");
      });
  };
  
  function displayTopRunnersReport(divName,contextPath,requestData) {
    /*var url =  contextPath + "/js/jChartFX/data/funnelchart-data.json";*/
    var url = contextPath+"api/reports/top/runners/get";
    var chart1 = new cfx.Chart();
    var promise = getChartsData(url,requestData);
    promise.done(function (responseData) {
    	 if(responseData.errorMessage != "undefined") {
             if(responseData.topRunners != null && responseData.topRunners.length > 0) {
                 chart1.setDataSource(responseData.topRunners);
                 var data = chart1.getData();
                 var funnel = new cfx.Funnel.Funnel();
                 chart1.setGalleryAttributes(funnel);
                 funnel.setTipHeight(10);
                 data.setSeries(1);
                 data.setPoints(responseData.topRunners.length);
                 chart1.getLegendBox().setDock(cfx.DockArea.Right);

                 chart1.getAllSeries().getPointLabels().setVisible(true);
                 chart1.getAllSeries().getPointLabels().setFormat("%v");
                 chart1.getAnimations().getLoad().setEnabled(true);
                 var divHolder = document.getElementById(divName);
                 $(divHolder).prepend('<div class="dashboard-report-title">Top runners based on number of CAF'+"'s"+' collected</div>');
                 chart1.create(divHolder);
                 }
            }
    });
    promise.fail(function (data) {
      console.log("unable to process request for line chart data");
    });
  };
  
  function displayTopDistributorsReport(divName,contextPath,requestData) {
   /*var url =  contextPath + "/js/jChartFX/data/top-distributors.json";*/
    var url = contextPath+"api/reports/top/distributors/get";
    var promise = getChartsData(url,requestData);
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
                  var divHolder = document.getElementById(divName);
                  $(divHolder).prepend('<div class="dashboard-report-title">Top distributors based on CAF'+"'s"+' collected</div>');
                  chart1.create(divHolder);
                  }
             }
      });
      promise.fail(function (data) {
        console.log("unable to process request for line chart data");
      });
  };
  
  function displayTopRegionsReport(divName,contextPath,requestData) {
  var url = contextPath+"api/reports/top/regions/get";
  /* var url =  contextPath + "/js/jChartFX/data/top-regions.json";*/
   var chart1 = new cfx.Chart();
    var promise = getChartsData(url,requestData);
      promise.done(function (responseData) {
    	  if(responseData.errorMessage != "undefined") {
              if(responseData.topRegions != null) {
                  chart1.setDataSource(responseData.topRegions);
                  var treeMap = new cfx.treemap.TreeMap();
                  chart1.setGalleryAttributes(treeMap);
                  
                  var fields = chart1.getDataSourceSettings().getFields();
                  var field1 = new cfx.FieldMap();
                  field1.setName("regionName");
                  field1.setUsage(cfx.FieldUsage.Label);
                  fields.add(field1);
                  var field2 = new cfx.FieldMap();
                  field2.setName("cafCount");
                  field2.setUsage(cfx.FieldUsage.Value);
                  fields.add(field2);
                   chart1.getAnimations().getLoad().setEnabled(true);
                  var divHolder = document.getElementById(divName);
                  $(divHolder).prepend('<div class="dashboard-report-title">Top regions based on CAF'+"'s"+' collected</div>');
                  chart1.create(divHolder);
                  }
             }
      });
      promise.fail(function (data) {
        console.log("unable to process request for line chart data");
      });
  };
  
  function displayTopZonesReport(divName,contextPath,requestData) {
	var url = contextPath+"api/reports/top/zones/get"; 
    var chart1 = new cfx.Chart();
  /* var url =  contextPath + "/js/jChartFX/data/top-zones-data.json";*/
    var promise = getChartsData(url,requestData);
      promise.done(function (responseData) {
    	  if(responseData.errorMessage != "undefined") {
              if(responseData.topZones != null) {
                  chart1.setDataSource(responseData.topZones);
                  var overlayBubble = new cfx.overlaybubble.OverlayBubble();
                	chart1.setGalleryAttributes(overlayBubble);
                  chart1.getAnimations().getLoad().setEnabled(true);
                  var divHolder = document.getElementById(divName);
                  $(divHolder).prepend('<div class="dashboard-report-title">Top zones based on CAF'+"'s"+' collected</div>');
                  chart1.create(divHolder);
                  }
             }
      });
      promise.fail(function (data) {
        console.log("unable to process request for line chart data");
      });
  };
  
  function displayTopHubsReport(divName,contextPath,requestData) {
	  var url = contextPath+"api/reports/top/hubs/get"; 
	  var chart1 = new cfx.Chart();
   	
    /*var url = contextPath + "/js/jChartFX/data/top-hubs-data.json";*/
    var promise = getChartsData(url,requestData);
    promise.done(function (responseData) {
    	if(responseData.errorMessage != "undefined") {
            if(responseData.topHubs != null) {
                chart1.setDataSource(responseData.topHubs);
                chart1.setGallery(cfx.Gallery.Pie);
                var data = chart1.getData();
                data.setPoints(6);
                data.setSeries(1);
                chart1.getAllSeries().getPointLabels().setVisible(true);
                chart1.getAllSeries().getPointLabels().setFormat("%v");
                chart1.getAnimations().getLoad().setEnabled(true);
                var divHolder = document.getElementById(divName);
                $(divHolder).prepend('<div class="dashboard-report-title">Top hubs based on CAF'+"'s"+' collected</div>');
                chart1.create(divHolder);
                }
           }
    });
    promise.fail(function (data) {
      console.log("unable to process request for pie chart data");
    });
  };
  
  function getChartsData(url,data) {
    var requestData =  JSON.stringify(data);
    var ajaxOptions = {
            type: "POST",
            url: url,
            data:requestData,
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
          };
      var promise = $.ajax(ajaxOptions);
      return promise;
  };