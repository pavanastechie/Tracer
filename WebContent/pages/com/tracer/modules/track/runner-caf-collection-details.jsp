<%@page import="com.tracer.util.UniqueCodeGenerator"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<%@ page import="java.net.URLEncoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>Way points in directions</title>
    <style>
      html,body,#map-canvas {
      height: 100%;
      margin: 0px;
      padding: 0px
      }
    </style>
    <script
      src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
    <script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/styledmarker/src/StyledMarker.js"></script>
    <script>
      function initialize() {
       var map;
        var lattitude, longitude, location, dateTime;
        var centerLongitude,centerLattitude;
        var flightPlanCoordinates = [];
        <logic:iterate id="runnervisittedLocationDetails" name="trackForm" indexId="trackIndex" property = "runnervisittedLocationDetails" type="com.tracer.dao.model.RunnerVisittedLocationDetails">
           centerLattitude = '<bean:write name="runnervisittedLocationDetails" property="lattitude"/>';
           centerLongitude = '<bean:write name="runnervisittedLocationDetails" property="longitude"/>';
           location = '<bean:write name="runnervisittedLocationDetails" property="location"/>';
         </logic:iterate>
         <logic:empty property="runnervisittedLocationDetails" name="trackForm">
           centerLattitude = '<bean:message key="label.track.google.map.default.lattitude" />';
           centerLongitude = '<bean:message key="label.track.google.map.default.longitude" />';
        </logic:empty>
        var mapOptions = {
          zoom: 13,
          center: new google.maps.LatLng(centerLattitude, centerLongitude),
          mapTypeId: google.maps.MapTypeId.TERRAIN 
        };
        map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
      
      //start of map for Distributor//
      setMarkers(map, sites);
      infowindow = new google.maps.InfoWindow({
        content: "loading..."
       });
       //END of map for Distributor//
        
        function toggleBounce(type) {
          if (type.getAnimation() != null) {4466
            type.setAnimation(null);
          } else {
           type.setAnimation(google.maps.Animation.BOUNCE);
          }
        }
        <logic:iterate id="runnervisittedLocationDetails" name="trackForm" indexId="trackIndex" property = "runnervisittedLocationDetails" type="com.tracer.dao.model.RunnerVisittedLocationDetails">
          lattitude = '<bean:write name="runnervisittedLocationDetails" property="lattitude"/>';
          longitude = '<bean:write name="runnervisittedLocationDetails" property="longitude"/>';
          location = '<bean:write name="runnervisittedLocationDetails" property="location"/>';
          dateTime = '<bean:write name="runnervisittedLocationDetails" property="dateTime"/>';
      
          if(lattitude != 0 && longitude != 0){
            flightPlanCoordinates.push(new google.maps.LatLng(lattitude,longitude ));
          }
          <bean:size id="count" name="trackForm"  property = "runnervisittedLocationDetails" />
          <%
        if (trackIndex==(count-1)) {
        %> var image = {
               url: '<%=request.getContextPath()%>/images/user.gif',
                 size: new google.maps.Size(30, 30),
                 origin: new google.maps.Point(0, 0),
                 anchor: new google.maps.Point(20, 15),
                 scaledSize: new google.maps.Size(30, 30)
                  };
          <%
        }
        else if (trackIndex==0) {
          %> var image = {
                   url: '<%=request.getContextPath()%>/images/runner.png',
                     size: new google.maps.Size(30, 30),
                     origin: new google.maps.Point(0, 0),
                     anchor: new google.maps.Point(20, 15),
                     scaledSize: new google.maps.Size(30, 30)
                      };
              <%
        } 
        else {
        %>
            var image = {
                 url: '<%=request.getContextPath()%>/images/halt.png',
                 size: new google.maps.Size(30, 30),
               origin: new google.maps.Point(0, 0),
               anchor: new google.maps.Point(20, 15),
               scaledSize: new google.maps.Size(30, 30)
                  };
          <%
        }
        %>
      var marker = new google.maps.Marker({
            flat: true,
        icon: image,
        clickable: false,
        suppressInfoWindows:false,
        map: map,
        optimized: false,
              visible: true,
              animation: google.maps.Animation.DROP,
              position: new google.maps.LatLng(lattitude, longitude)
            }); 
      
             google.maps.event.addListener(marker, 'click', function() {
                  infowindow.setContent(location);
                  infowindow.open(map, this);
            }); 
        </logic:iterate>
      /*   if(lattitude != 0 && longitude != 0){
         var marker = new google.maps.Marker({
               map:map,
               draggable:true,
               icon: image,
               animation: google.maps.Animation.DROP,
               position: new google.maps.LatLng(lattitude, longitude)
          });
       } */
      
        var flightPath = new google.maps.Polyline({
          path: flightPlanCoordinates,
          geodesic: true,
          strokeColor: '#00B9FF',
          strokeOpacity: 1.0,
          strokeWeight: 2
        });
      
        flightPath.setMap(map);
      }
      
      //start of map for Distributor//
      var sites = [
         <logic:iterate id="runnerTrackDetails" name="trackForm" indexId="trackIndex" 
           property = "runnerTrackDetails" type="com.tracer.dao.model.RunnerTrackDetails">
         [<bean:write name="runnerTrackDetails" property="distributorLattitude"/>, 
          <bean:write name="runnerTrackDetails" property="distributorLongitude"/>, 
          <bean:write name="runnerTrackDetails" property="distributorNo"/>, 
          '<bean:write name="runnerTrackDetails" property="distributorName"/>'],
        </logic:iterate>
      ];
                   function setMarkers(map, markers) {
                   
                       for (var i = 0; i < markers.length; i++) {
                           var sites = markers[i];
                           var siteLatLng = new google.maps.LatLng(sites[0], sites[1]);
                           var marker = new google.maps.Marker({
                               position: siteLatLng,
                               animation: google.maps.Animation.DROP,
                               map: map,
                               zIndex: sites[2],
                               html: sites[3]
                           });
      
                           var contentString = "Some content";
      
                           google.maps.event.addListener(marker, "click", function () {
                               infowindow.setContent(this.html);
                               infowindow.open(map, this);
                           });
                       }
                  
                   var flightPlanCoordinates = [
                    <logic:iterate id="runnerTrackDetails" name="trackForm" indexId="trackIndex" 
                      property = "runnerTrackDetails" type="com.tracer.dao.model.RunnerTrackDetails">
                    new google.maps.LatLng(<bean:write name="runnerTrackDetails" property="distributorLattitude"/>, <bean:write name="runnerTrackDetails" property="distributorLongitude"/>),
                    </logic:iterate>
                   ];
      
                 var flightPath = new google.maps.Polyline({
                    path: flightPlanCoordinates,
                    strokeColor: '#000000',
                    strokeOpacity: 1.0,
                    strokeWeight: 2
                 });
                       flightPath.setMap(map);
                   }
             //start of map for Distributor//
      google.maps.event.addDomListener(window, 'load', initialize);
      
    </script>
    <script type="text/javascript">
      //======================================================================
        
      function getTemplateDetails(){
        var categoryId=$("#sms-template-category").val();
        if(categoryId == ""){
            return false;
        }
        else{
          $("#mySMS .error-overlay").text("");
          $("#sms-template-content").val("");
          $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/notification.do?method=getSMSTemplate",
            data:"smsCategoryId="+categoryId,
            success: function(response){
              response = jQuery.parseJSON(response);
              if((response.result).toLowerCase() == "ok"){
                $("#sms-template-content").val(response.smsContent);
              }
              else{
                alert("No Template Available");
              }
            },
            error: function(response){
              console.log("error..."+response);
            }
          });
        }
      }
      
      //======================================================================
        
      function sendSMS() {
        var smsContent = $("#mySMS #sms-template-content").val();
        var data ={};
        if($("#sms-template-category").val() == ""){
          $("#mySMS .error-overlay").text("please select Category");
          return false;
        }
        else if(smsContent == ""){
          $("#mySMS .error-overlay").text("please enter Content ");
          return false;
        }
        else{
          data["smsContent"] = smsContent;
          data["runnerId"] = '<bean:write name="trackForm" property="runnerId"/>';
          var request = [];
          request.push(data);
          $.ajax({
            type: "GET",
            url: "<%=request.getContextPath()%>/notification.do?method=sendSMS&smsData=" + JSON.stringify(request),
            contentType: 'application/json',
            success: function(response){
              if(response != "" && response.toLowerCase() == "ok"){
                clearSmsPopUpValues();
                $("#mySMS").modal("hide");
                alert("SMS sent Successfully");
              }
              else{
                alert("Failed to Send SMS");
              }
            },
            error: function(response){
              alert("Failed to Send SMS");
            }
           });
        }
      }
      
      //======================================================================
      function clearSmsPopUpValues(){
        $("#mySMS #sms-template-content").val("");
        $("#mySMS #sms-template-category").val("");
        $("#mySMS .error-overlay").text("");
      }
      
      //======================================================================  
        function getSmsCategories(){
          clearSmsPopUpValues();
          $("#mySMS").modal("show");
          
          if($("#sms-template-category option").length <=1 ){
            $.ajax({
              type: "GET",
              url: "<%=request.getContextPath()%>/notification.do?method=getSMSCategories",
              success: function(data){
                var categories = jQuery.parseJSON(data);
                if(data != null && categories.length >0){
                  $.each(categories, function (index, category) {
                    $("#sms-template-category").append("<option value='"+category.smsCategoryId+"'>"+category.smsCategoryName+"</option>");
                  });
                }
                else{
                  alert("No Sms Categories Available");
                }
              },
              error: function(response){
                alert("Failed to get SMS catgories.Please Try again later.");
              }
            });
          }
        }
      
      //======================================================================
        
        function clearCafSignatureValues(){
          $("#caf-digital-signature").text("");
          $(".caf-signature-error-overlay").text("");
        }
      
      //======================================================================
        
        function clearCafPhotoValues(){
            $("#caf-photo").text("");
            $(".caf-photo-error-overlay").text("");
          }
      
      //======================================================================
        
        function clearRunnerPhotoValues(){
            $("#runner-photo").text("");
            $(".runner-photo-error-overlay").text("");
          }
      
      //======================================================================
    	  
        
        function viewDistributorPhoto(cafId){
          
          if(cafId != "" && cafId != null){
            $.ajax({
              type: "GET",
              url: "<%=request.getContextPath()%>/track.do?method=getCAFDetails&need=distPhoto&cafId="+cafId,
              success: function(data){
                if(data != null && data != ""){
                  var info = jQuery.parseJSON(data);
                  if(info.distributorPhoto != "" && info.distributorPhoto != null){
                    info.distributorPhoto = "/"+info.distributorPhoto;
                    $("#caf-photo").append("<img src='"+info.distributorPhoto+"' alt=''/>");
                    $(".caf-photo-error-overlay").text("");
                  }
                  else{
                    $(".caf-photo-error-overlay").text("Distributor Photo is not available");
                  }
                }
                else{
                  $(".caf-photo-error-overlay").text("Distributor Photo is not available");
                }
              },
              error: function(response){
                console("Failed to get Distributor Photo.");
              }
            });
          }
          else{
            $(".caf-photo-error-overlay").text("No Data available");
          }
        }
      
      //======================================================================
        
        function viewCAFSignature(cafId){
          if(cafId != "" && cafId != null){
            $.ajax({
              type: "GET",
              url: "<%=request.getContextPath()%>/track.do?method=getCAFDetails&need=digSign&cafId="+cafId,
              success: function(data){
                if(data != null && data != ""){
                  var info = jQuery.parseJSON(data);
                  if(info.distributorSignature != "" && info.distributorSignature != null){
                    info.distributorSignature = "/"+info.distributorSignature;
                    $("#caf-digital-signature").append("<img src='"+info.distributorSignature+"' alt=''/>");
                    $(".caf-signature-error-overlay").text("");
                  }
                  else{
                    $(".caf-signature-error-overlay").text("Signature is not available");
                  }
                }
                else{
                  $(".caf-signature-error-overlay").text("Signature is not available");
                }
              },
              error: function(response){
                console("Failed to get Distributor Digital Signature.");
              }
            });
          }
          else{
            $(".caf-signature-error-overlay").text("No Data available");
          }
        }
      
      //======================================================================
        
      function showRunnerCAFCollectionDetailsPage(){
         var runnnerId = document.trackForm.runnerUserId.value;
         document.trackForm.action = "<%=request.getContextPath()%>/track.do?method=showRunnerCAFCollectionDetailsPage&id="+runnnerId;
         document.trackForm.submit(); 
        }
      
      //======================================================================
        
      <%-- function showRunnerCAFCollectionDetailsPageOnDate(){
        var t = document.trackForm.runnerUserId.value;
         var runnnerId = document.trackForm.runnerUserId.value;
         document.trackForm.action = "<%=request.getContextPath()%>/track.do?method=showRunnerCAFCollectionDetailsPage&id="+runnnerId;
         document.trackForm.submit(); 
        }
        
        //====================================================================== --%>
        
         function viewRunnerPhoto(cafId){
           if(cafId != "" && cafId != null) {
            $.ajax({
              type: "GET",
              url: "<%=request.getContextPath()%>/track.do?method=getCAFDetails&need=runnerPhoto&cafId="+cafId,
              success: function(data){
                
                if(data != null && data != ""){
                  var info = jQuery.parseJSON(data);
                  
                  if(info.runnerPhoto != "" && info.runnerPhoto != null){
                    info.runnerPhoto = "/"+info.runnerPhoto;
                    $("#runner-photo").append("<img src='"+info.runnerPhoto+"' alt=''/>");
                    $(".runner-photo-error-overlay").text("");
                  }
                  else{
                    $(".runner-photo-error-overlay").text("Runner Photo is not available");
                  }
                }
                else{
                  $(".runner-photo-error-overlay").text("Runner Photo is not available");
                }
              },
              error: function(response){
                console("Failed to get Runner Photo.");
              }
            });
          } else {
            $(".runner-photo-error-overlay").text("No Data available");
          }
        }
      
      //======================================================================
      	
       function downloadReport() {
         var runnnerId = document.trackForm.runnerUserId.value;
         document.trackForm.action = "<%=request.getContextPath()%>/track.do?method=downloadReport&runnerUserId="+runnnerId;
         document.trackForm.submit(); 
        }
      
      //======================================================================
      
      function showScannedCAFs(cafId) {
        if(cafId != "" && cafId != null) {
          $.ajax({
            type: "GET",
            url: "<%=request.getContextPath()%>/track.do?method=getCAFDetails&need=scannedCAFs&cafId="+cafId,
            success: function(data){
              if(data != null && data != "") {
                var parsedRespJson = jQuery.parseJSON(data);
                var scannedCAFs = parsedRespJson.scannedCAFs;
                var dataHTML = "<table border='1' class='scanned-cafs-table'><th>CAF Bar Code</th><th>CAF Image</th>";
                if(scannedCAFs != null && scannedCAFs.length > 0) {
                  for(var i = 0; i < scannedCAFs.length; i++) {
                    dataHTML = dataHTML + "<tr><td>"+scannedCAFs[i].barCode+"</td><td><a href='#' onclick='showScannedCAFImage("+i+")'>Click Here to view CAF Image</a></td></tr>";
                  }
                } else {
                  dataHTML = dataHTML + "<tr><td colspan='2'>No CAF(s) scanned at this distributor location.</td></tr>";
                }
                dataHTML = dataHTML + "</table>";
                $("#scanned-cafs-body-div").append(dataHTML);
              } else {
                //$(".runner-photo-error-overlay").text("Runner Photo is not available");
              }
            },
            error: function(response){
              console("Failed to get the scanned CAFs.");
            }
          });
        } else {
          //$(".runner-photo-error-overlay").text("No Data available");
        }
      }
    
      //======================================================================
    	  
       function clearScannedCAFs(){
         $("#scanned-cafs-body-div").text("");
       }
      
      //======================================================================
    
      function showScannedCAFImage(index) {
        if(index >= 0) {
          $.ajax({
            type: "GET",
            url: "<%=request.getContextPath()%>/track.do?method=getScannedCAFImagePath&index="+index,
            success: function(data){
              if(data != null && data != ""){
                var parsedRespJSON = jQuery.parseJSON(data);
                if(parsedRespJSON.scannedCAFImagePath != "" && parsedRespJSON.scannedCAFImagePath != null){
                  parsedRespJSON.scannedCAFImagePath = "/"+parsedRespJSON.scannedCAFImagePath;
                  $("#scanned-caf-image").empty();
                  $("#scanned-caf-image").append("<img src='"+parsedRespJSON.scannedCAFImagePath+"' alt=''/>");
                  //$(".scanned-caf-image-error-overlay").text("");
                } else{
                  //$(".scanned-caf-image-error-overlay").text("Scanned CAF Image is not available");
                }
              } else{
                //$(".scanned-caf-image-error-overlay").text("Scanned CAF Image is not available");
              }
            },
            error: function(response){
              console("Failed to getScanned CAF Image.");
            }
          });
        } else {
          //$(".scanned-caf-image-error-overlay").text("No Data available");
        }
      }
      
      //======================================================================
    
      function clearScannedCAFImage() {
         $("#scanned-caf-image").text("");
         $(".scanned-caf-image-error-overlay").text("");
       }
      
      //======================================================================
      
    </script>
  </head>
  <body>
    <html:form action="/track.do">
      <div class="clear"></div>
      <div id="form-content">
        <div id="titleheading">
          <div class="navtitle1">
            <bean:message key="label.track.runners.caf.collection.details" />
          </div>
          <br />
          <div id="titleheadingbar">
            <div class="navtitle">
              <i class="fa fa-search"></i>
              <bean:message key="label.track" />
              / 
              <label>
                <bean:message key="label.runners" />
              </label>
              / 
              <label>
                <bean:message key="label.track.runners.caf.collection.details" />
              </label>
            </div>
          </div>
          <div id="table-content">
            <table width="100%" align="center" cellpadding="0" cellspacing="0"
              border="0" class="records-page">
              <tr>
                <th class="runcaffont1">Date</th>
                <th class="runcaffont1">Time</th>
                <th class="runcaffont1">Runner</th>
                <th class="runcaffont1">Runner Code</th>
                <th class="runcaffont1">Hub Name</th>
                <!-- <th class="runcaffont1">Beat Plan</th> -->
              </tr>
              <tr>
                <td>
                  <html:text property="runnerDate" styleClass="runnerTextbox datePicker" readonly="true">
                    <logic:notEmpty property="runnerDate" name="trackForm">
                      <bean:write name="trackForm" property="runnerDate" />
                    </logic:notEmpty>
                  </html:text>
                  <button class="btn btn-primary gobtn" onclick="showRunnerCAFCollectionDetailsPage()">GO</button>
                </td>
                <td>
                  <logic:notEmpty property="runnerTime" name="trackForm">
                    <bean:write name="trackForm" property="runnerTime" />
                  </logic:notEmpty>
                </td>
                <td>
                  <logic:notEmpty property="runnerName" name="trackForm">
                    <bean:write name="trackForm" property="runnerName" />
                  </logic:notEmpty>
                </td>
                <td>
                  <bean:write name="trackForm" property="runnerCode" />
                </td>
                <td>
                  <logic:notEmpty property="hubName" name="trackForm">
                    <bean:write name="trackForm" property="hubName" />
                  </logic:notEmpty>
                </td>
                <%--  <td>
                  <logic:notEmpty property="beatPlanName" name="trackForm" >
                  <bean:write name="trackForm" property="beatPlanName" scope="session"/>
                  </logic:notEmpty>
                  </td>  --%>
              </tr>
            </table>
            <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="records-page">
              <tr>
                <td class="runnerBeatPlanContainer">
                  <div id="map-canvas" style="float: left; width: 100%; height: 100%;"></div>
                  <!-- <img class="runnerBeatPlanImage" title="Runner Beat Plan" src="images/Runner_Beat_Plan.png" alt="Runner Beat Plan" />  -->
                </td>
              </tr>
            </table>
            <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="records-page caf-records">
              <tr>
                <td colspan="2">
                  <html:select property="runnerUserId" styleClass="runner-dropdown">
                    <logic:notEmpty property="runnersList" name="trackForm">
                      <html:optionsCollection property="runnersList" label="name" value="userId" />
                    </logic:notEmpty>
                  </html:select>
                  <button class="btn btn-primary runner-dropdown" onclick="showRunnerCAFCollectionDetailsPage()">GO</button>
                  <span class="runner-caf-coll-icon" title="Refresh" onclick="showRunnerCAFCollectionDetailsPage()"><i class="fa fa-refresh"></i></span>
                  <span class="runner-caf-coll-icon" title="Send SMS" data-toggle="modal" data-target="#mySMS" onclick="getSmsCategories()"><i class="fa fa-envelope"></i></span>
                  <span class="runner-caf-coll-icon" title="Download" onclick="downloadReport()"><i class="fa fa-download"></i></span>
                </td>
                <td colspan=5 class="runners-delay-colors">
                  <div class="states-box states-box-red">
                    <div class="red-box"></div>
                    > 30 mins & above
                  </div>
                  <div class="states-box states-box-orange">
                    <div class="orange-box"></div>
                    > 15 < 30 mins
                  </div>
                  <div class="states-box states-box-green">
                    <div class="green-box"></div>
                    < 15 mins
                  </div>
                </td>
              </tr>
              <tr class="runners-headings-content">
                <th style="padding-left: 20px; height: 35px;">Distributor</th>
                <th style="padding-left: 40px;">CAF Count</th>
                <th style="padding-left: 20px;">Scheduled time</th>
                <th style="padding-left: 19px;">Visited time</th>
                <th style="padding-left: 32px;">Time difference</th>
                <th></th>
                <th></th>
              </tr>
              <logic:empty property="runnerTrackDetails" name="trackForm">
                <tr bgColor="#f1f1f1">
                  <td colspan="5" style="text-align: center">
                    <bean:message key="label.track.no.data.available" />
                  </td>
                </tr>
              </logic:empty>
              <logic:notEmpty property="runnerTrackDetails" name="trackForm">
                <logic:iterate id="runnerTrackDetails" name="trackForm" indexId="trackIndex" property="runnerTrackDetails" type="com.tracer.dao.model.RunnerTrackDetails">
                  <%
                    if (trackIndex % 2 == 0) {
                    %>
                  <tr bgColor="#f1f1f1">
                    <%
                      } else {
                      %>
                  <tr>
                    <%
                      }
                      %>
                    <td class="runner-distributor-name" style="width: 22%;">
                      <bean:write property="distributorName" name="runnerTrackDetails" />
                    </td>
                    <logic:equal value="0" property="totalCafCount" name="runnerTrackDetails">
                      <td class="runner-caf-count help-cursor" title="
                      <bean:write property="remarks" name="runnerTrackDetails"/>
                      ">
                    </logic:equal>
                    <logic:greaterThan value="0" property="totalCafCount" name="runnerTrackDetails">
                      <td class="runner-caf-count">
                    </logic:greaterThan>
                    <logic:empty property="totalCafCount" name="runnerTrackDetails">
                    <td class="runner-caf-count">
                    </logic:empty>
                    <bean:write property="totalCafCount" name="runnerTrackDetails"/>
                    </td>
                    <td class="runner-schedule-time">
                      <bean:write property="scheduleDateTime" name="runnerTrackDetails" />
                    </td>
                    <td class="runner-caf-date-time">
                      <bean:write property="visittedDateTime" name="runnerTrackDetails" />
                    </td>
                    <logic:notEmpty property="totalCafCount"
                      name="runnerTrackDetails">
                      <td class="runner-delay-time">
                        <bean:write property="delayHours" name="runnerTrackDetails" />
                      </td>
                      <td>
                        <logic:equal value="false" property="earlyVisit" name="runnerTrackDetails">
                          <logic:lessThan value="0" property="delayTime" parameter="delayTime" name="runnerTrackDetails">
                            <logic:greaterThan value="-15" property="delayTime" parameter="delayTime" name="runnerTrackDetails">
                              <div class="td-green"></div>
                            </logic:greaterThan>
                            <logic:lessEqual value="-30" property="delayTime" parameter="delayTime" name="runnerTrackDetails">
                              <div class="td-red"></div>
                            </logic:lessEqual>
                            <logic:lessThan value="-15" property="delayTime" parameter="delayTime" name="runnerTrackDetails">
                              <logic:greaterThan value="-30" property="delayTime" parameter="delayTime" name="runnerTrackDetails">
                                <div class="td-orange"></div>
                              </logic:greaterThan>
                            </logic:lessThan>
                          </logic:lessThan>
                          <logic:greaterEqual value="0" property="delayTime" parameter="delayTime" name="runnerTrackDetails">
                            <div class="td-green"></div>
                          </logic:greaterEqual>
                        </logic:equal>
                        <logic:equal value="true" property="earlyVisit" name="runnerTrackDetails">
                          <span style="font-size:14px; font-weight:600; color:#2D6CA2;">Early visit !</span>
                        </logic:equal>
                      </td>
                      <td class="runner-info">
                        <div>
                          <a href="#" data-toggle="modal" data-target="#distributorPhoto"
                            onclick="viewDistributorPhoto(<bean:write property='cafCollectionId' name='runnerTrackDetails'/>)"
                            title="View distributor photo"><i class="fa fa-picture-o"></i></a>
                        </div>
                        <div>
                          <a href="#" data-toggle="modal" data-target="#myCAFSignature"
                            onclick="viewCAFSignature(<bean:write property='cafCollectionId' name='runnerTrackDetails'/>)"
                            title="View digital signature"><i class="fa fa-pencil-square"></i></a>
                        </div>
                        <div>
                          <a href="#" data-toggle="modal" data-target="#myRunnerPhoto"
                            onclick="viewRunnerPhoto(<bean:write property='cafCollectionId' name='runnerTrackDetails'/>)"
                            title="View runner photo"><i class="fa fa-user"></i></a>
                        </div>
                        <div>
                          <a href="#" data-toggle="modal" data-target="#scannedCAFs"
                            onclick="showScannedCAFs(<bean:write property='cafCollectionId' name='runnerTrackDetails'/>)"
                            title="View Scanned CAFs"><i class="fa fa-files-o"></i></a>
                        </div>
                        <logic:notEmpty property="remarks" name="runnerTrackDetails">
                        <div>
                          <a href="#" title="<bean:write property='remarks' name='runnerTrackDetails'/>"><i class="fa fa-info-circle icon-red"></i></a>
                        </div>
                        </logic:notEmpty>
                      </td>
                    </logic:notEmpty>
                    <logic:empty property="totalCafCount" name="runnerTrackDetails">
                      <td></td>
                      <td></td>
                      <td></td>
                    </logic:empty>
                  </tr>
                </logic:iterate>
              </logic:notEmpty>
            </table>
            <div id="status"></div>
          </div>
        </div>
      </div>
      <!-- Modal for SMS -->
      <div class="modal fade" id="mySMS" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modeldiv-top">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true" onclick="clearSmsPopUpValues()">&times;</button>
              <h4 class="modal-title" id="myModalLabel">
                <bean:message key="label.send.sms"></bean:message>
              </h4>
              <div class="error-overlay">
                <br />
              </div>
            </div>
            <form action="javascript:void(0);" name="sendSMSOverlay"
              onsubmit="sendSMS(this);" class="send-sms">
              <div class="modal-body">
                <div class="sms-template-details">
                  <div class="sms-template-category-details">
                    <div class="sms-template-category-label">
                      <label>SMS Category*</label>
                    </div>
                    <div class="sms-template-category-options">
                      <select id="sms-template-category"
                        class="sms-template-category"
                        onchange="javascript:getTemplateDetails()" size="1">
                        <option value="">Please Select Category</option>
                      </select>
                    </div>
                  </div>
                  <div class="sms-template-content">
                    <div class="sms-template-content-label">
                      <label>SMS Content*</label>
                    </div>
                    <div class="sms-template-content-text">
                      <textarea id="sms-template-content" class="form-control"
                        rows="4" cols="55"></textarea>
                    </div>
                  </div>
                </div>
              </div>
              <div class="modal-footer">
                <input type="button" class="btn btn-primary region-done-btn" value="Send SMS" onclick="sendSMS()"/>
              </div>
            </form>
          </div>
        </div>
      </div>
      <!-- /.modal -->
      <!-- Modal for Distributor Photo -->
      <div class="modal fade" id="distributorPhoto" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel" aria-hidden="true"
        data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog">
          <div class="modal-content runner-distributor-info">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true" onclick="clearCafPhotoValues()">&times;</button>
              <h4 class="modal-title" id="myModalLabel">
                <bean:message key="label.distributor.picture"></bean:message>
              </h4>
            </div>
            <div class="modal-body">
              <div class="caf-photo-error-overlay">
                <br />
              </div>
              <div id="caf-photo" class="runner-distributor-images"></div>
            </div>
          </div>
        </div>
      </div>
      <!-- /.modal -->
      <!-- Modal for Distributor Signature-->
      <div class="modal fade" id="myCAFSignature" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel" aria-hidden="true"
        data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog">
          <div class="modal-content runner-distributor-info">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true" onclick="clearCafSignatureValues()">&times;</button>
              <h4 class="modal-title" id="myModalLabel">
                <bean:message key="label.distributor.signature"></bean:message>
              </h4>
            </div>
            <div class="modal-body">
              <div class="caf-signature-error-overlay">
                <br />
              </div>
              <div id="caf-digital-signature" class="runner-distributor-images"></div>
            </div>
          </div>
        </div>
      </div>
      <!-- /.modal -->
      <!-- Modal for Runner Photo -->
      <div class="modal fade" id="myRunnerPhoto" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel" aria-hidden="true"
        data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog">
          <div class="modal-content runner-distributor-info">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true" onclick="clearRunnerPhotoValues()">&times;</button>
              <h4 class="modal-title" id="myModalLabel">
                <bean:message key="label.runner.photo"></bean:message>
              </h4>
            </div>
            <div class="modal-body">
              <div class="runner-photo-error-overlay">
                <br />
              </div>
              <div id="runner-photo" class="runner-distributor-images"></div>
            </div>
          </div>
        </div>
      </div>
      <!-- /.modal -->
      <div class="modal fade" id="scannedCAFs" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel" aria-hidden="true"
        data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog">
          <div class="modal-content scanned-cafs-div">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true" onclick="clearScannedCAFs()">&times;</button>
              <h4 class="modal-title" id="myModalLabel">
                <bean:message key="label.scanned.cafs"></bean:message>
              </h4>
            </div>
            <div class="modal-body" id="scanned-cafs-body-div">
            </div>
            <div id="upload-scanned-cafs-body-div" class="modal-body">
              <table border="1" class="scanned-cafs-table">
                <tbody>
                  <tr>
                    <!-- <td><div class="upload-scanned-cafs-body-div-iframe"><iframe src="http://111.93.29.222/Vodafone"></iframe></div></td> -->
                    <td><div class="scanned-caf-image-div" id="scanned-caf-image"><img src="<%=request.getContextPath()%>/images/default_caf.png" /></div></td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      <%-- <div class="modal fade" id="scannedCAFImage" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel" aria-hidden="true"
        data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog">
          <div class="modal-content runner-distributor-info">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true" onclick="clearScannedCAFImage()">&times;</button>
              <h4 class="modal-title" id="myModalLabel">
                <bean:message key="label.scanned.caf.image"></bean:message>
              </h4>
            </div>
            <div class="modal-body">
              <div class="scanned-caf-image-error-overlay">
                <br />
              </div>
              <div id="scanned-caf-image" class="runner-distributor-images"></div>
            </div>
          </div>
        </div>
      </div> --%>
    </html:form>
  </body>
</html>