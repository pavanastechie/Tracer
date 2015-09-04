<%@page import="com.tracer.common.Constants.*"%>
<%@page import="com.tracer.util.UniqueCodeGenerator"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<%@ page import="com.tracer.dao.model.RegionDetails"%>
<%@ page import="com.tracer.dao.model.ZoneDetails"%>
<%@ page import="com.tracer.dao.model.HubDetails"%>
<%@ page import="java.net.URLEncoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <%
      request.setCharacterEncoding("UTF-8");
      response.setCharacterEncoding("UTF-8");
      %>
    <script type="text/javascript">
      google.load('visualization', '1', {packages: ['orgchart']});
        var regionlist = [];
        var zonelist = [];
        var hublist = [];
        
        <%if (session.getAttribute("regionsList") != null) {
        List sessionRegionList = (List) session.getAttribute("regionsList");
        for (int i = 0; i < sessionRegionList.size(); i++) {
          RegionDetails regionDetails = (RegionDetails) sessionRegionList.get(i);%>
            var regionObj = new Object();
            regionObj.regionId = '<%=regionDetails.getRegionId() == null ? "0" : regionDetails.getRegionId()%>';
            regionObj.regionName = "<%=regionDetails.getRegionName()%>";
            regionObj.regionCode = "<%=regionDetails.getRegionCode()%>";
            regionObj.circleName = "<%=regionDetails.getCircleDetails().getCircleName()%>";
            regionObj.circleCode = "<%=regionDetails.getCircleDetails().getCircleCode()%>";
            regionlist.push(regionObj);
          <%}
        }%>
        
        <%if (session.getAttribute("zonesList") != null) {
        List sessionZonesList = (List) session.getAttribute("zonesList");
        for (int i = 0; i < sessionZonesList.size(); i++) {
          ZoneDetails zoneDetails = (ZoneDetails) sessionZonesList.get(i);%>
            var zoneObj = new Object();
            zoneObj.zoneId = '<%=zoneDetails.getZoneId() == null ? "0" : zoneDetails.getZoneId()%>';
            zoneObj.zoneName = "<%=zoneDetails.getZoneName()%>";
            zoneObj.zoneCode = "<%=zoneDetails.getZoneCode()%>";
            zoneObj.regionName = "<%=zoneDetails.getRegionDetails().getRegionName()%>";
            zoneObj.regionCode = "<%=zoneDetails.getRegionDetails().getRegionCode()%>";
            zonelist.push(zoneObj);
        <%}
        }%>
        
        <%if (session.getAttribute("hubsList") != null) {
        List sessionHubsList = (List) session.getAttribute("hubsList");
        for (int i = 0; i < sessionHubsList.size(); i++) {
          HubDetails hubDetails = (HubDetails) sessionHubsList.get(i);%>
            var hubObj = new Object();
            hubObj.hubId = "<%=hubDetails.getHubId() == null ? "0" : hubDetails.getHubId()%>";
            hubObj.hubName = "<%=hubDetails.getHubName()%>";
            hubObj.hubCode = "<%=hubDetails.getHubCode()%>";
            hubObj.zoneName = "<%=hubDetails.getZoneDetails().getZoneName()%>";
            hubObj.zoneCode = "<%=hubDetails.getZoneDetails().getZoneCode()%>";
            hublist.push(hubObj);
        <%}
        }%>
        
        function disableAddCircleButton() {
          <%if (session.getAttribute("circleCode") != null) {%>
              $(".addCircle").prop("disabled", true);
              $(".circles-add-table .circleName").prop('readonly', true);
          <%} else {%>
              $(".addCircle").prop("disabled", false);
              $(".circles-add-table .circleName").prop('readonly', false);
          <%}%>
          
          $("#circle-name:first").focus();
        }
      
        $(document).on("click", ".addCircle", function (e) {
          var self = $(this);
          var isCircleExist = false;
          
          var circleName = self.parents('tr').children('td').find('.circleName').val().trim();
          if(circleName == '') {
            $('#errors-container').text('Enter Circle name');
            return false;
          }
          
          $.ajax({
            type: "GET",
            url: "<%=request.getContextPath()%>/circles.do?method=isCircleNameExists&circleName="+circleName,
            async: false,
            success: function(data) {
              isCircleExist = data;
            }
          });
          if(isCircleExist == 1) {
            $('#errors-container').text('Circle name already exist');
            return false;
          }
        });
        
        
        /**  region related javascript  **/
        function removeAddRegionPopup() {
          $(".modal-backdrop").hide();
        }
       
        function addRegionToOverlay(form) {
           $("#addRegion").prop("disabled", true);
           $("#doneRegion").prop("disabled", false);
           $('.error-overlay').css("display", "block");
          if(form.regionName.value.trim() == '') return;
          var exist = {};
          
          if(regionlist.length > 0) {
            exist = _.where(regionlist, {regionName: form.regionName.value.trim(), circleName: form.circleNameInRegionModal.value.trim()});
          }
          
          if (!_.isEmpty(exist)) {
            $('#regionModal .error-overlay').removeClass('hide');
            return;
          }
          else {
            $('#regionModal .error-overlay').addClass('hide');
            var regionObj = new Object();
            regionObj.regionId = '0';
            regionObj.regionName = form.regionName.value.trim();
            
            $.ajax({
                  type: "GET",
                  contentType : "application/json; charset=utf-8",
                  url: "<%=request.getContextPath()%>/circles.do?method=getRegionCode",
                  async: false,
                  dataType: "json",
                  success: function(data) {
                    regionObj.regionCode = data.unique_region_code;
                  }
              });
            regionObj.circleCode = form.circleCodeInRegionModal.value.trim();
            regionObj.circleName = form.circleNameInRegionModal.value.trim();
            regionlist.push(regionObj);
          }
          
          var regionModelBody = $("#regionModal").find('.modal-body .regions-add-table-overlay');
          var table = $("#regionModal").find('.modal-body .regions-add-table-overlay');
          if(table.find('tr').length == 0) {
            regionModelBody.append('<tr class="regionheading"><th>Region Name</th><th>Circle Name</th><th></th></tr>');
            $("#doneRegion").prop("disabled", true);
          } 
          var noOfRecords = $('tr.regiontable').length;
          noOfRecords = noOfRecords + 1;
          regionModelBody.append('<tr class="regiontable"><td class="region-name">'+form.regionName.value+'</td><td>'+regionObj.circleName+'</td><td><a href="javascript:void(0);" class="regionRemove" title=Remove id="circle_region_remove_hl_'+noOfRecords+'" ><i class="fa fa-times fa-2"></i></a></td></tr>');
          if(table.find('tr').length > 1) {
            $("#doneRegion").prop("disabled", false);
          }
          form.regionName.value='';
        }
       
        function sendRegionList(status) {
          var url = "<%=request.getContextPath()%>/circles.do?method=addRegion&regionsList=" + JSON.stringify(regionlist);
          if(status != undefined) {
            url = url + "&status="+status;
          }
              $.ajax({
                type: "GET",
                contentType : "application/json; charset=utf-8",
                url: url,
                dataType: "html",
                success: function(data) {
                window.location='<%=request.getContextPath()%>/circles.do?method=showAddCirclePage';
                }
            });
        }
           
        $(document).on("click", ".addRegion", function (e) {
        
          var self = $(this);
          var circleName = self.parents('tr').children('td.circleName').text().trim();
          var circleCode = self.parents('tr').children('td.circleCode').text().trim();
          
          $("#circleNameInRegionModal").val(circleName);
          $("#circleCodeInRegionModal").val(circleCode);
          $("#regionModal .modal-body #regionName").focus();
          $("#regionName").val("");
          $('.error-overlay').css("display", "none");
          $("#addRegion").prop("disabled", true);
          $("#doneRegion").prop("disabled", true);
          $("#regionName").keyup(function(){
            if($("#regionName").val() == "")
              {
                $("#addRegion").prop("disabled", true);
              }
            else
              {
                $("#addRegion").prop("disabled", false);
              }
              });
        });
      
        
        $(document).on('click', '.region-delete', function(e) {
              var that = $(this);
              e.preventDefault();
              var regionCode = that.parents('tr').children('td.regionCode').text().trim();
              var isRegionDeletable = '';
              var promise = '';
              <%-- <%
        if (session.getAttribute("viewType") != null && session.getAttribute("viewType").equals("edit")) { %>
          var region = _.where(regionlist, {regionCode: regionCode});
          promise = $.ajax({
            type: "GET",
            contentType : "application/json; charset=utf-8",
            url: "<%=request.getContextPath()%>/circles.do?method=isRegionDeletable&regionId="+region[0].regionId,
            dataType: "json",
            success: function(data) {
              isRegionDeletable = data.isRegionDeletable;
            }
          });
        <%
          }
        %> --%>
              /* if(isRegionDeletable == "OK") { */
                $("<div title='Delete Region'>This deletion will remove all its corresponding Zones and Hubs(if any). Do you want to continue?</div>")
                .dialog({
                  resizable : false,
                  modal : true,
                  buttons : {
                  "No" : function() {
                    $(this).dialog("close");
                  },
                  "Yes" : function() {
                    var id = that.parents('div').attr('id');
                    
                    if(id == 'regions-container') {
                      var regionCode = that.parents('tr').children('td.regionCode').text().trim();
                      var regionName = that.parents('tr').children('td.regionName').text().trim();
                      
                      zonelist = _.reject(zonelist, (function(item) { return item.regionName == regionName && item.regionCode == regionCode}));
                      
                      hublist = _.filter(hublist, function(item1){
                        return _.some(this,function(item2){
                          return item2['zoneName'] == item1['zoneName'] && item2['zoneCode'] == item1['zoneCode'] ;
                        });
                      }, zonelist);
                      
                      regionlist = _.reject(regionlist, (function(item) { return item.regionCode == regionCode}));
                      
                      sendHubList();
                      sendZoneList();
                      sendRegionList();
                    }
                    $(this).dialog("close");
                    }
                  }
                });
              /* } else {
                $('#errors-container').text(isRegionDeletable);
              } */
            });
        
        
       
        /* close of region realted javascript */
       
        /* start of zone related javascript **/
        
        
        function removeAddZonePopup() {
          $(".modal-backdrop").hide();
        }
       
        function addZoneToOverlay(form) {
           $("#addZone").prop("disabled", true);
           $("#doneZone").prop("disabled", false);
           $('.error-overlay').css("display", "block");
          if(form.zoneName.value.trim() == '') return;
          var exist = {};
          if(zonelist.length > 0) {
            exist = _.where(zonelist, {zoneName: form.zoneName.value.trim(), regionName: form.regionNameInZoneModal.value.trim()});
          }
          
          if (!_.isEmpty(exist)) {
            $('#myZoneModal .error-overlay').removeClass('hide');
            return;
          }
          else {
            $('#myZoneModal .error-overlay').addClass('hide');
            var zoneObj = new Object();
            zoneObj.zoneId = '0';
            zoneObj.zoneName = form.zoneName.value.trim();
            $.ajax({
                  type: "GET",
                  contentType : "application/json; charset=utf-8",
                  url: "<%=request.getContextPath()%>/circles.do?method=getZoneCode",
                  dataType: "json",
                  success: function(data) {
                    zoneObj.zoneCode = data.unique_zone_code;
                  }
              });
            <%-- zoneObj.zoneCode = '<%=URLEncoder.encode(UniqueCodeGenerator.getInstance().getZoneCode(), "UTF-8")%>' --%>
            zoneObj.regionCode = form.regionCodeInZoneModal.value.trim();
            zoneObj.regionName = form.regionNameInZoneModal.value.trim();
            zonelist.push(zoneObj);
          }
          var noOfRecords = $('tr.regiontable').length;
          noOfRecords = noOfRecords + 1;
          var zoneModelBody = $("#myZoneModal").find('.modal-body .zones-add-table-overlay');
          var table = $("#myZoneModal").find('.modal-body .zones-add-table-overlay');
          if(table.find('tr').length == 0) {
            zoneModelBody.append('<tr class="zoneheading"><th>Zone Name</th><th>Region Name</th><th></th></tr>');     
            $("#doneZone").prop("disabled", true);
          }
          zoneModelBody.append('<tr class="regiontable"><td id="zone-name">'+form.zoneName.value+'</td><td>'+zoneObj.regionName+
              '</td><td><a href="javascript:void(0);" title=Remove class="zoneRemove" id="circle_region_zone_remove_hl_'+noOfRecords+'"><i class="fa fa-times fa-2"></i></a></td></tr>');
          if(table.find('tr').length > 1) {
            $("#doneZone").prop("disabled", false);
          }
          form.zoneName.value='';
        }
       
          function sendZoneList(status) {
            var url = "<%=request.getContextPath()%>/circles.do?method=addZone&zonesList=" + JSON.stringify(zonelist);
            if(status != undefined) {
              url = url + "&status="+status;
            }
            $.ajax({
                    type: "GET",
                    contentType : "application/json; charset=utf-8",
                    url: url,
                    dataType: "html",
                    success: function(data) {
                      window.location='<%=request.getContextPath()%>/circles.do?method=showAddCirclePage';
                    }
                  });
            }
      
           $(document).on("click", ".addZone", function (e) {
      
            var self = $(this);
            var regionName = self.parents('tr').children('td.regionName').text().trim();
            
            var regionCode = self.parents('tr').children('td.regionCode').text().trim();
            $("#regionNameInZoneModal").val(regionName);
            $("#regionCodeInZoneModal").val(regionCode);
            $("#zoneName").val("");
            $('.error-overlay').css("display", "none");
            $("#addZone").prop("disabled", true);
            $("#doneZone").prop("disabled", true);
            $("#zoneName").keyup(function(){
              if($("#zoneName").val() == "")
                {
                  $("#addZone").prop("disabled", true);
                }
              else
                {
                  $("#addZone").prop("disabled", false);
                }
                });
            
            
          });
           
           $(document).on('click', '.zone-delete', function(e) {
             var that = $(this);
              e.preventDefault();
              
              var zoneCode = that.parents('tr').children('td.zoneCode').text().trim();
              var isZoneDeletable = '';
              var promise = '';
              <%-- <%
        if (session.getAttribute("viewType") != null && session.getAttribute("viewType").equals("edit")) { %>
          var zone = _.where(zonelist, {zoneCode: zoneCode});
          promise = $.ajax({
            type: "GET",
            contentType : "application/json; charset=utf-8",
            url: "<%=request.getContextPath()%>/circles.do?method=isZoneDeletable&zoneId="+zone[0].zoneId,
            dataType: "json",
            success: function(data) {
              isZoneDeletable = data.isZoneDeletable;
            }
          });
        <%
          }
        %> --%>
              /* promise.done(function() {
                if(isZoneDeletable == "OK") { */
                  $("<div title='Delete Zone'>This deletion will remove all its corresponding Hubs(if any). Do you want to continue?</div>")
                  .dialog({
                    resizable : false,
                    modal : true,
                    buttons : {
                      "No" : function() {
                          $(this).dialog("close");
                        },
                       "Yes" : function() {
                          var id = that.parents('div').attr('id');
                      
                          if(id == 'zones-container') {
                            var zoneCode = that.parents('tr').children('td.zoneCode').text().trim();
                            var zoneName = that.parents('tr').children('td.zoneName').text().trim();
                            
                            hublist = _.reject(hublist, (function(item) { return item.zoneName == zoneName && item.zoneCode == zoneCode}));
                            zonelist = _.reject(zonelist, (function(item) { return item.zoneCode == zoneCode}));
                            
                            sendHubList();
                            sendZoneList();
                          }
                          $(this).dialog("close");
                        }
                    }
                  });
                /* } else {
                  $('#errors-container').text(isZoneDeletable);
                }
              });*/
              
              });
           
      
        /* close of zone realted javascript and js */
        
        /*   start of hub related javascript and js */
        
         function removeAddHubPopup() {
          $(".modal-backdrop").hide();
        }
       
           function addHubToOverlay(form) {
           $("#addHub").prop("disabled", true);
           $("#doneHub").prop("disabled", false);
           $('.error-overlay').css("display", "block");
          console.log("addHubToOverlay started");
          console.log("Hub Lstttt");
          console.log(hublist);
          if(form.hubName.value.trim() == '') return;
          var exist = {};
          if(hublist.length > 0) {
            exist = _.where(hublist, {hubName: form.hubName.value.trim(), zoneName: form.zoneNameInHubModal.value.trim()});
          }
          
          if (!_.isEmpty(exist)) {
            $('#myHubModal .error-overlay').removeClass('hide');
            return;
          }
          else {
            $('#myHubModal .error-overlay').addClass('hide');
            var hubObj = new Object();
            hubObj.hubId = '0';
            hubObj.hubName = form.hubName.value.trim();
            $.ajax({
                  type: "GET",
                  contentType : "application/json; charset=utf-8",
                  url: "<%=request.getContextPath()%>/circles.do?method=getHubCode",
                  dataType: "json",
                  success: function(data) {
                    hubObj.hubCode = data.unique_hub_code;
                  }
              });
            <%-- hubObj.hubCode = '<%=URLEncoder.encode(UniqueCodeGenerator.getInstance().getHubCode(), "UTF-8")%>'; --%>
            hubObj.zoneCode = form.zoneCodeInHubModal.value.trim();
            hubObj.zoneName = form.zoneNameInHubModal.value.trim();
            hublist.push(hubObj);
          }
          
          var hubModelBody = $("#myHubModal").find('.modal-body .hubs-add-table-overlay');
          var table = $("#myHubModal").find('.modal-body .hubs-add-table-overlay');
          if(table.find('tr').length == 0) {
            hubModelBody.append('<tr class="hubheading"><th>Hub Name</th><th>Zone Name</th><th></th></tr>');      
            $("#doneHub").prop("disabled", true);
          }
          var noOfRecords = $('tr.regiontable').length;
          noOfRecords = noOfRecords + 1;
          hubModelBody.append('<tr class="regiontable"><td id="hub-name">'+form.hubName.value+'</td><td>'+hubObj.zoneName+
              '</td><td><a href="javascript:void(0);" title=Remove class="hubRemove" id="circle_region_zone_hub_remove_hl_'+noOfRecords+'"><i class="fa fa-times fa-2"></i></a></td></tr>');
          if(table.find('tr').length > 1) {
            $("#doneHub").prop("disabled", false);
          }
          form.hubName.value='';
        }
       
        function sendHubList(status) {
          var url = "<%=request.getContextPath()%>/circles.do?method=addHub&hubsList=" + JSON.stringify(hublist);
          if(status != undefined) {
            url = url + "&status="+status;
          }
          $.ajax({
            type: "GET",
            contentType : "application/json; charset=utf-8",
            url: url,
            dataType: "html",
            success: function(data) {
              window.location='<%=request.getContextPath()%>/circles.do?method=showAddCirclePage';
                    }
          });
        }
      
        $(document).on("click", ".addHub", function(e) {
              var self = $(this);
              var zoneName = self.parents('tr').children('td.zoneName')
                  .text().trim();
      
              var zoneCode = self.parents('tr').children('td.zoneCode')
                  .text().trim();
      
              $("#zoneNameInHubModal").val(zoneName);
              $("#zoneCodeInHubModal").val(zoneCode);
              $("#hubName").val("");
              $(".hub-name-tb").focus();
              $('.error-overlay').css("display", "none");
              $("#addHub").prop("disabled", true);
              $("#doneHub").prop("disabled", true);
              $("#hubName").keyup(function(){
                if($("#hubName").val() == "")
                  {
                    $("#addHub").prop("disabled", true);
                  }
                else
                  {
                    $("#addHub").prop("disabled", false);
                  }
                  });
              
            });
      
        $(document).on('click', '.hub-delete', function(e) {
          var that = $(this);
              e.preventDefault();
              
              var hubCode = that.parents('tr').children('td.hubCode').text().trim();
              var isHubDeletable = '';
              var promise ='';
              <%-- <%
        if (session.getAttribute("viewType") != null && session.getAttribute("viewType").equals("edit")) { %>
          var hub = _.where(hublist, {hubCode: hubCode});
          promise = $.ajax({
            type: "GET",
            contentType : "application/json; charset=utf-8",
            url: "<%=request.getContextPath()%>/circles.do?method=isHubDeletable&hubId="+hub[0].hubId,
            dataType: "json",
            success: function(data) {
              isHubDeletable = data.isHubDeletable;
            }
          });
        <%
          }
        %> --%>
              /* promise.done(function() {
                if(isHubDeletable == "OK") { */
                  $("<div title='Delete Hub'>Do you want to delete this Hub?</div>")
                  .dialog({
                    resizable : false,
                    modal : true,
                    buttons : {
                      "No" : function() {
                          $(this).dialog("close");
                        },
                      "Yes" : function() {
                          var id = that.parents('div').attr('id');
                          if(id == 'hubs-container') {
                            var hubCode = that.parents('tr').children('td.hubCode').text().trim();
                            var hubName = that.parents('tr').children('td.hubName').text().trim();
                            console.log(hublist);
                            hublist = _.reject(hublist, (function(item) { return item.hubCode == hubCode}));
                            sendHubList();
                          }
                          $(this).dialog("close");
                        }
                    }
                  });
                /* } else {
                  $('#errors-container').text(isHubDeletable);
                }
              }); */
              });
        /* close of zone realted javascript and js */
        
        function saveCircles() {
          var circleName = $('.circles-add-table').find('.circleName').val().trim();
          console.log("circleNamecircleName   " +circleName);
          if(circleName == '') {
            $('#errors-container').text('Enter Circle name');
            return false;
          } else {
            if(regionlist.length > 0) {
              return true;        
            } else {
              $('#errors-container').text('Entered Circle must have atleast one Region');
              return false;
            }
          }
        }
    </script>
  </head>
  <body onload="disableAddCircleButton()">
    <div class="clear"></div>
    <div id="circles-top-container">
      <div id="titleheading">
        <div></div>
        <div class="navtitle1">
          <%
            if (session.getAttribute("viewType") != null && session.getAttribute("viewType").equals("edit")) {
            %>
          <bean:message key="label.edit.circle" />
          <%
            } else {
            %>
          <bean:message key="label.add.circle" />
          <%
            }
            %>
        </div>
        <br />
        <div id="titleheadingbar">
          <div class="navtitle">
            <i class="fa fa-dot-circle-o"></i>
            <bean:message key="label.circle" />
            /
            <%
              if (session.getAttribute("viewType") != null
                  && session.getAttribute("viewType").equals("edit")) {
              %>
            <bean:message key="label.edit.circle" />
            <%
              } else {
              %>
            <bean:message key="label.add.circle" />
            <%
              }
              %>
          </div>
        </div>
        <div id='errors-container'>
          <logic:notEmpty name="circleErrorMessage" scope="request">
            <bean:write name="circleErrorMessage" scope="request" />
          </logic:notEmpty>
        </div>
        <!-- Circle Container and circle related data -->
        <div id="circles-container">
          <table width="95%" cellspacing="0" cellpadding="0" border="0" class="circles-add-table">
            <tbody>
              <tr>
                <th>
                  <bean:message key="Circle"></bean:message>
                </th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
              </tr>
              <tr>
                <html:form action="/circles.do?method=addCircle">
                  <td>
                    <bean:message key="circle.name"></bean:message>
                  </td>
                  <td>
                    <html:text property="circleName" styleClass="circleName form-control" styleId="circle-name" />
                  </td>
                  <td style="width: 13%;"></td>
                  <td>
                    <logic:notEmpty name="circlesForm" property="circleCode">
                      <bean:write name='circlesForm' property='circleCode' />
                    </logic:notEmpty>
                    <logic:empty name="circlesForm" property="circleCode">
                      <html:text property="circleCode" styleClass="form-control" readonly="true" value='<%=UniqueCodeGenerator.getInstance().getCircleCode()%>' />
                  </td>
                  </logic:empty>
                  <%
                    if (session.getAttribute("viewType") != null && session.getAttribute("viewType").equals("edit")) {
                    %>
                  <td>
                    <html:submit property="submit" value="Update Circle" styleClass="addCircle btn btn-primary regbtn" styleId="circle_update_submit_btn"/>
                  </td>
                  <%
                    } else {
                    %>
                  <td>
                    <html:submit property="submit" value="Add Circle" styleClass="addCircle btn btn-primary regbtn" title="Add Circle" styleId="circle_add_submit_btn"/>
                  </td>
                  <%
                    }
                    %>
                  <%-- <html:submit property="submit" value="Add Circle"
                    styleId="addCircle" styleClass="addRegion btn btn-primary regbtn" /></td>--%>
                </html:form>
              </tr>
          </table>
          <table>
            <tr>
              <logic:notEmpty name="circlesForm" property="circleName"
                scope="session">
                <table width="95%" cellspacing="0" cellpadding="0"
                  class="circles-list-table">
                  <tbody>
                    <tr>
                      <th>
                        <bean:message key="circle.name"></bean:message>
                      </th>
                      <th class="circle-code-under-region">Circle Code</th>
                      <th></th>
                      <th>&nbsp;</th>
                    </tr>
                    <tr>
                      <html:form action="/circles.do?method=addRegion">
                        <td class='circleName'>
                          <bean:write name="circlesForm"
                            property="circleName" />
                        </td>
                        <td class='circleCode'>
                          <bean:write name="circlesForm"
                            property="circleCode" />
                        </td>
                        <td>
                          <a class="circle-view" data-circle-name= "<bean:write name="circlesForm" property="circleName" />" 
                            data-circle-code= "<bean:write name="circlesForm" property="circleCode" />"
                            data-toggle="modal" data-target="#viewOrgChart" href='#' title="View" id="circle_view_hr">
                            <i class="fa fa-eye fa-2 circle-icons"></i>
                          </a>
                        </td>
                        <td>
                          <button class="addRegion btn btn-primary regbtn" data-toggle="modal" data-target="#regionModal"
                            property="submit" name="Add Region" title="Add Region" id="circle_region_add_btn">
                            <bean:message key="add.region"></bean:message>
                          </button>
                        </td>
                      </html:form>
                    </tr>
                  </tbody>
                </table>
              </logic:notEmpty>
            </tr>
            </tbody>
          </table>
        </div>
        <!-- close of circle container and related data -->
        <script>
          $('.circle-view').on(
              'click',
              function() {
                 var circleName = $(this).attr("data-circle-name");
                 var circleCode = $(this).attr("data-circle-code");
                 var requestData ={};
                 requestData.circleName = circleName;
                 requestData.circleCode = circleCode;
                 var request = [];
                 request.push(requestData);
                 $('#org-chart-data').empty();
                 $('#orgChartName').text("Below is the Org chart for the circle : "+circleName);
                 $('.org-chart-header').css("background-color","#2980B9");
                 $.ajax({
                        type: "GET",
                        contentType : "application/json; charset=utf-8",
                        url: "<%=request.getContextPath()%>/circles.do?method=getOrgChartCircleList&requestData=" + JSON.stringify(request),
                        async: false,
                        dataType: "json",
                        success: function(responseData) {
                         var data = new google.visualization.DataTable();
                         data.addColumn('string', 'Name','name');
                         data.addColumn('string', 'Belongs To','belong');
                         data.addColumn('string', 'ToolTip');
                         if(responseData.regionsOfCircle != null && responseData.regionsOfCircle.length > 0) {
                           data.addRows(responseData.regionsOfCircle);
                           var chart = new google.visualization.OrgChart(document.getElementById('org-chart-data'));
                           chart.draw(data, {allowHtml:true,size:'large'});
                         } 
                         else {
                           $('#org-chart-data').text("No regions exists under the circle : "+circleName);
                         }
                        }
                    });
              
              });
        </script>
        <!-- region container data and related data  -->
        <!-- Modal for region -->
        <div class="modal fade fontmodelcircle" id="regionModal"
          tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
          aria-hidden="true">
          <div class="modal-dialog modeldiv-top">
            <div class="modal-content">
              <div class="modal-header ">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="region_close_btn">&times;</button>
                <h4 class="modal-title" id="myModalLabel">
                  <bean:message key="add.region"></bean:message>
                </h4>
                <div class="error-overlay hide" id="clrerror">
                  <span>
                    <bean:message key="label.region.exist"></bean:message>
                  </span>
                </div>
              </div>
              <div class="modal-body">
                <div id="regionId" align="center">
                  <form action="javascript:void(0);" name="regionFormOverlay"
                    onsubmit="addRegionToOverlay(this);" class="regionmodel">
                    <bean:message key="label.region.name"></bean:message>
                    <input type="text" id='regionName' name="regionName"
                      class="region-name-tb" autofocus="autofocus" autocomplete="off" /> <input type="submit" value="Add"
                      Id="addRegion" class="btn btn-primary regionbtn" title="Add" />
                    <input type='hidden' value='' name='circleNameInRegionModal'
                      id='circleNameInRegionModal' /> <input type='hidden' value=''
                      name='circleCodeInRegionModal' id='circleCodeInRegionModal' />
                  </form>
                  <%-- <html:form action="/circles.do?method=addRegion">
                    <span><bean:message key="region.name"></bean:message>
                    <html:text property="regionName" styleId="regionName"/></span>
                    <html:button property="submit" value="Add"
                      onclick="addRegionToOverlay(this);"  styleId="addRegion" styleClass="btn btn-primary"/>
                    </html:form> --%>
                </div>
                <table width="95%" cellspacing="0" cellpadding="0" class="regions-add-table-overlay"> </table>
              </div>
              <div class="modal-footer">
                <!-- <button type="button" class="btn btn-default" data-dismiss="modal">Close</button> -->
                <form action="javascript:void(0);">
                  <input type="hidden" name="regionsList" /> <input type="submit"
                    class="btn btn-primary region-done-btn" value="Done" Id="doneRegion"
                    title="Done" onclick="sendRegionList();" />
                </form>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->
        <logic:notEmpty name="circlesForm" property="circleName">
          <div id="regions-container">
            <logic:notEmpty name="regionsList" scope="session">
              <table width="95%" cellspacing="0" cellpadding="0"
                class="circles-add-table-region">
                <tbody>
                  <tr>
                    <th>
                      <bean:message key="region.name" />
                    </th>
                    <th>
                      <bean:message key="region.code" />
                    </th>
                    <th>
                      <bean:message key="circle.name" />
                    </th>
                    <th></th>
                    <th></th>
                    <th></th>
                  </tr>
                  <html:form action="/circles.do?method=addZone" method="POST">
                    <%
                      List regionsList = null;
                      RegionDetails regionDetails = null;
                      if (request.getSession() != null && request.getSession().getAttribute("regionsList") != null) {
                              regionsList = (List) request.getSession().getAttribute("regionsList");
                              if (regionsList.size() > 0) {
                               int sNo = 0;
                                for (int i = 0; i < regionsList.size(); i++) {
                                  sNo = i + 1;
                                  regionDetails = (RegionDetails) regionsList.get(i);
                      %>
                    <tr>
                      <td class="regionName"><%=regionDetails.getRegionName()%></td>
                      <td class="regionCode"><%=regionDetails.getRegionCode()%></td>
                      <td><%=regionDetails.getCircleDetails().getCircleName()%></td>
                      <td></td>
                      <td>
                        <a class="region-view" data-region-name= "<%=regionDetails.getRegionName()%>" data-region-code= "<%=regionDetails.getRegionCode()%>" data-toggle="modal" data-target="#viewOrgChart" href='#' title="View" id="circle_region_view_hl_<%=sNo%>">
                          <i class="fa fa-eye fa-2 circle-icons"></i>
                        </a>
                        <a class="region-edit hide" href='#' title="Edit" id="circle_region_edit_hl_<%=sNo%>">
                          <i class="fa fa-pencil-square-o fa-2 circle-icons"></i>
                        </a> 
                        <a class="region-delete" href="#" title="Delete" id="circle_region_delete_hl_<%=sNo%>">
                          <i class="fa fa-trash-o fa-2 circle-icons"></i>
                        </a>
                        <button class="addZone btn btn-primary zonebtn" data-toggle="modal" data-target="#myZoneModal" property="submit" name="Add Zone" title="Add Zone" id="circle_region_zone_add_btn_<%=sNo%>">
                          Add Zone
                        </button>
                      </td>
                    </tr>
                    <%
                      }
                      }
                      }
                      %>
                  </html:form>
                </tbody>
              </table>
            </logic:notEmpty>
          </div>
        </logic:notEmpty>
        <script>
          $('.regions-add-table-overlay').on(
              'click',
              'td a.regionRemove',
              function() {
                regionlist = _.without(regionlist, _.findWhere(regionlist, {regionName : $(this).parents('tr').find('td.region-name').text().trim()}));
                if (regionlist.length == 0)
                  $(this).parents('table').children().remove();
                  $(this).parents('tr').remove();
                
                var regionModelBody = $("#regionModal").find('.modal-body .regions-add-table-overlay');
                var table = $("#regionModal").find('.modal-body .regions-add-table-overlay');
                if(table.find('tr').length == 0) {
                  $("#doneRegion").prop("disabled", true);
                }
                regionModelBody.append('<tr class="regiontable"><td class="region-name">'+form.regionName.value+'</td><td>'+regionObj.circleName+'</td><td><a href="javascript:void(0);" class="regionRemove" title=Remove><i class="fa fa-times fa-2"></i></a></td></tr>');
                if(table.find('tr').length > 1) {
                  $("#doneRegion").prop("disabled", false);
                }
                form.regionName.value='';
              });
          $('.region-view').on(
              'click',
              function() {
                 var regionName = $(this).attr("data-region-name");
                 var regionCode = $(this).attr("data-region-code");
                 var requestData ={};
                 requestData.regionName = regionName;
                 requestData.regionCode = regionCode;
                 var request = [];
                 request.push(requestData);
                 $('#org-chart-data').empty();
                 $('#orgChartName').text("Below is the Org chart for the region : "+regionName);
                 $('.org-chart-header').css("background-color","#16A085");
                 $.ajax({
                        type: "GET",
                        contentType : "application/json; charset=utf-8",
                        url: "<%=request.getContextPath()%>/circles.do?method=getOrgChartZonesList&requestData=" + JSON.stringify(request),
                        async: false,
                        dataType: "json",
                        success: function(responseData) {
                         var data = new google.visualization.DataTable();
                         data.addColumn('string', 'Name','name');
                         data.addColumn('string', 'Belongs To','belong');
                         data.addColumn('string', 'ToolTip');
                         if(responseData.zonesOfRegion != null && responseData.zonesOfRegion.length > 0) {
                           data.addRows(responseData.zonesOfRegion);
                           var chart = new google.visualization.OrgChart(document.getElementById('org-chart-data'));
                           chart.draw(data, {allowHtml:true,size:'large'});
                         } else {
                           $('#org-chart-data').text("No zones exists under the region : "+regionName);
                         }
                        }
                    });
              
              });
        </script>
        <!--  end region container data and related data-->
        <!-- logic for zones container and zone related data -->
        <logic:notEmpty name="circlesForm" property="circleName">
          <logic:notEmpty name="regionsList" scope="session">
            <div id="zones-container">
              <logic:notEmpty name="zonesList" scope="session">
                <table width="95%" cellspacing="0" cellpadding="0"
                  class="circles-add-table-zone">
                  <tbody>
                    <tr>
                      <th>
                        <bean:message key="zone.name" />
                      </th>
                      <th>
                        <bean:message key="zone.code" />
                      </th>
                      <th>
                        <bean:message key="region.name" />
                      </th>
                      <th></th>
                      <th></th>
                      <th></th>
                    </tr>
                    <html:form action="/circles.do?method=addHub" method="POST">
                      <%
                        List zonesList = null;
                        ZoneDetails zoneDetails = null;
                        if (request.getSession() != null && request.getSession().getAttribute("zonesList") != null) {
                          zonesList = (List) request.getSession().getAttribute("zonesList");
                          if (zonesList.size() > 0) {
                            int sNo = 0;
                            for (int i = 0; i < zonesList.size(); i++) {
                              sNo = i +1;
                              zoneDetails = (ZoneDetails) zonesList.get(i);
                        %>
                      <tr>
                        <td class="zoneName"><%=zoneDetails.getZoneName()%></td>
                        <td class="zoneCode"><%=zoneDetails.getZoneCode()%></td>
                        <td><%=zoneDetails.getRegionDetails().getRegionName()%></td>
                        <td></td>
                        <td>
                          <a class="zone-view" data-zone-name= "<%=zoneDetails.getZoneName()%>" data-zone-code= "<%=zoneDetails.getZoneCode()%>" 
                            data-toggle="modal" data-target="#viewOrgChart" href='#' title="View" id="circle_region_zone_view_hr_<%=sNo%>">
                            <i class="fa fa-eye fa-2 circle-icons"></i>
                          </a>
                          <a class="zone-edit hide" href='#' title="Edit" id="circle_region_zone_edit_hr_<%=sNo%>">
                            <i class="fa fa-pencil-square-o fa-2 circle-icons"></i>
                          </a>
                          <a class="zone-delete" href="#" title="Delete" id="circle_region_zone_delete_hr_<%=sNo%>">
                            <i class="fa fa-trash-o fa-2 circle-icons"></i>
                          </a>
                          <button class="addHub btn btn-primary zonebtn" data-toggle="modal" data-target="#myHubModal" 
                            property="submit" name="Add Hub" title="Add Hub" id="circle_region_zone_hub_add_btn_<%=sNo%>">
                            <bean:message key="add.hub"></bean:message>
                          </button>
                        </td>
                      </tr>
                      <%
                        }
                        }
                        } else {
                          out.println("zoneslist from session is null");
                        }
                        %>
                    </html:form>
                  </tbody>
                </table>
              </logic:notEmpty>
            </div>
          </logic:notEmpty>
        </logic:notEmpty>
        <script>
          $('.regions-add-table-overlay').on(
              'click',
              'td a.regionRemove',
              function() {
                regionlist = _.without(regionlist, _.findWhere(regionlist, {regionName : $(this).parents('tr').find('td.region-name').text().trim()}));
                if (regionlist.length == 0)
                  $(this).parents('table').children().remove();
                  $(this).parents('tr').remove();
                
                var regionModelBody = $("#regionModal").find('.modal-body .regions-add-table-overlay');
                var table = $("#regionModal").find('.modal-body .regions-add-table-overlay');
                if(table.find('tr').length == 0) {
                  $("#doneRegion").prop("disabled", true);
                }
                regionModelBody.append('<tr class="regiontable"><td class="region-name">'+form.regionName.value+'</td><td>'+regionObj.circleName+'</td><td><a href="javascript:void(0);" class="regionRemove" title=Remove><i class="fa fa-times fa-2"></i></a></td></tr>');
                if(table.find('tr').length > 1) {
                  $("#doneRegion").prop("disabled", false);
                }
                form.regionName.value='';
              });
          
          
          $('.zones-add-table-overlay').on(
              'click',
              'td a.zoneRemove',
              function() {
                zonelist = _.without(list, _.findWhere(zonelist, {zoneName : $(this).parents('tr').find('td#zone-name').text().trim()}));
                if (zonelist.length == 0)
                  $(this).parents('table').children().remove();
                $(this).parents('tr').remove();           
              });
          $('.zone-view').on(
              'click',
              function() {
                 var zoneName = $(this).attr("data-zone-name");
                 var zoneCode = $(this).attr("data-zone-code");
                 var requestData ={};
                 requestData.zoneName = zoneName;
                 requestData.zoneCode = zoneCode;
                 var request = [];
                 request.push(requestData);
                 $('#orgChartName').text("Below is the Org chart for the zone : "+zoneName);
                 $('.org-chart-header').css("background-color","#E74C3C");
                 $('#org-chart-data').empty();
                 $.ajax({
                        type: "GET",
                        contentType : "application/json; charset=utf-8",
                        url: "<%=request.getContextPath()%>/circles.do?method=getOrgChartHubsList&requestData=" + JSON.stringify(request),
                        async: false,
                        dataType: "json",
                        success: function(responseData) {
                         var data = new google.visualization.DataTable();
                         data.addColumn('string', 'Name','name');
                         data.addColumn('string', 'Belongs To','belong');
                         data.addColumn('string', 'ToolTip');
                         if(responseData.hubsOfZone != null && responseData.hubsOfZone.length > 0) {
                           data.addRows(responseData.hubsOfZone);
                           var chart = new google.visualization.OrgChart(document.getElementById('org-chart-data'));
                           chart.draw(data, {allowHtml:true,size:'large'});
                         } else {
                           $('#org-chart-data').text("No hubs exists under the zone : "+zoneName);
                         }
                        }
                    });
                /*  var data = new google.visualization.DataTable();
                 data.addColumn('string', 'Name','name');
                 data.addColumn('string', 'Belongs To','belong');
                 data.addColumn('string', 'ToolTip');
                 data.addRows([
                      ['Zone1', '', ''],
                      ['Hub1', 'Zone1', ''],['Hub2', 'Zone1', ''],['Hub3', 'Zone1', ''],['Hub4', 'Zone1', ''],
                      ['Hub5', 'Zone1', ''],['Hub6', 'Zone1', ''],['Hub7', 'Zone1', ''],['Hub8', 'Zone1', ''],
                      ['Hub9', 'Zone1', ''],['Hub10', 'Zone1', ''],['Hub11', 'Zone1', ''],['Hub12', 'Zone1', ''],
                      ['Hub13', 'Zone1', ''],['Hub14', 'Zone1', ''],['Hub15', 'Zone1', ''],['Hub16', 'Zone1', '']
                    ]);
                    var chart = new google.visualization.OrgChart(document.getElementById('org-chart-data'));
                    chart.draw(data, {allowHtml:true}); */
              
              });
          
        </script>
        <!-- end of logic for zones container and zone related data -->
        <!-- logic for zones container and zone related data -->
        <!-- Modal for zone -->
        <div class="modal fade" id="myZoneModal" tabindex="-1" role="dialog"
          aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-dialog modeldiv-top">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="zone_close_btn">&times;</button>
                <h4 class="modal-title" id="myModalLabel">
                  <bean:message key="add.zone"></bean:message>
                </h4>
                <div class="error-overlay hide">
                  <span>
                    <bean:message key="label.zone.exist"></bean:message>
                  </span>
                </div>
              </div>
              <div class="modal-body">
                <div id="zoneId" align="center">
                  <form action="javascript:void(0);" name="zoneFormOverlay"
                    onsubmit="addZoneToOverlay(this);" class="regionmodel">
                    <bean:message key="label.zone.name"></bean:message>
                    <input type="text" id='zoneName' name="zoneName"
                      class="zone-name-tb" autocomplete="off"/> <input type="submit" value="Add"
                      Id="addZone" class="btn btn-primary zonebtn1" title="Add" /> <input
                      type='hidden' value='' name='regionNameInZoneModal'
                      id='regionNameInZoneModal' /> <input type='hidden' value=''
                      name='regionCodeInZoneModal' id='regionCodeInZoneModal' />
                  </form>
                  <%-- <html:form action="/circles.do?method=addRegion">
                    <span><bean:message key="region.name"></bean:message>
                    <html:text property="regionName" styleId="regionName"/></span>
                    <html:button property="submit" value="Add"
                      onclick="addRegionToOverlay(this);"  styleId="addRegion" styleClass="btn btn-primary"/>
                    </html:form> --%>
                </div>
                <table width="95%" cellspacing="0" cellpadding="0"
                  class="zones-add-table-overlay">
                </table>
              </div>
              <div class="modal-footer">
                <!-- <button type="button" class="btn btn-default" data-dismiss="modal">Close</button> -->
                <form action="javascript:void(0);">
                  <input type="hidden" name="zonesList" /> <input type="submit"
                    class="btn btn-primary region-done-btn" value="Done" Id="doneZone"
                    onclick="sendZoneList();" title="Done" />
                </form>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->
        <logic:notEmpty name="circlesForm" property="circleName">
          <logic:notEmpty name="regionsList" scope="session">
            <logic:notEmpty name="zonesList" scope="session">
              <div id="hubs-container">
                <logic:notEmpty name="hubsList" scope="session">
                  <table width="95%" cellspacing="0" cellpadding="0"
                    class="circles-add-table-hub">
                    <tbody>
                      <tr>
                        <th>
                          <bean:message key="label.hub.name" />
                        </th>
                        <th>
                          <bean:message key="label.hub.code" />
                        </th>
                        <th>
                          <bean:message key="zone.name" />
                        </th>
                        <th></th>
                        <th></th>
                      </tr>
                      <html:form action="/circles.do?method=addHub" method="POST">
                        <%
                          List hubsList = null;
                          HubDetails hubDetails = null;
                          if (request.getSession() != null && request.getSession().getAttribute("hubsList") != null) {
                            hubsList = (List) request.getSession().getAttribute("hubsList");
                            if (hubsList.size() > 0) {
                              int sNo = 0;
                              for (int i = 0; i < hubsList.size(); i++) {
                                sNo = i + 1;
                                hubDetails = (HubDetails) hubsList.get(i);
                          %>
                        <tr>
                          <td class="hubName"><%=hubDetails.getHubName()%></td>
                          <td class="hubCode"><%=hubDetails.getHubCode()%></td>
                          <td><%=hubDetails.getZoneDetails().getZoneName()%></td>
                          <td></td>
                          <td>
                            <a class="hub-edit hide" href='#' title="Edit" id="circle_region_zone_hub_edit_hr_<%=sNo%>"><i class="fa fa-pencil-square-o fa-2 circle-icons"></i></a> 
                            <a class="hub-delete" href="#" title="Delete" id="circle_region_zone_hub_delete_hr_<%=sNo%>"><i class="fa fa-trash-o fa-2 circle-icons"></i></a>
                          </td>
                        </tr>
                        <%
                          }
                          }
                          } else {
                            out.println("hubslist from session is null");
                          }
                          %>
                      </html:form>
                    </tbody>
                  </table>
                </logic:notEmpty>
              </div>
            </logic:notEmpty>
          </logic:notEmpty>
        </logic:notEmpty>
        <!-- Modal for Hub -->
        <div class="modal fade" id="myHubModal" tabindex="-1" role="dialog"
          aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-dialog  modeldiv-top">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="hub_close_btn">&times;</button>
                <h4 class="modal-title" id="myModalLabel">
                  <bean:message key="add.hub"></bean:message>
                </h4>
                <div class="error-overlay hide">
                  <span>
                    <bean:message key="label.hub.exist"></bean:message>
                  </span>
                </div>
              </div>
              <div class="modal-body">
                <div id="hubId" align="center">
                  <form action="javascript:void(0);" name="hubFormOverlay" onsubmit="addHubToOverlay(this);" class="regionmodel">
                    <bean:message key="label.hub.name"></bean:message>
                    <input type="text" id='hubName' name="hubName" class="hub-name-tb" autocomplete="off"/> 
                    <input type="submit" value="Add" Id="addHub" class="btn btn-primary zonebtn1" title="Add" /> 
                    <input type='hidden' value='' name='zoneNameInHubModal' id='zoneNameInHubModal' /> 
                    <input type='hidden' value='' name='zoneCodeInHubModal' id='zoneCodeInHubModal' />
                  </form>
                  <%-- <html:form action="/circles.do?method=addRegion">
                    <span><bean:message key="region.name"></bean:message>
                    <html:text property="regionName" styleId="regionName"/></span>
                    <html:button property="submit" value="Add"
                      onclick="addRegionToOverlay(this);"  styleId="addRegion" styleClass="btn btn-primary"/>
                    </html:form> --%>
                </div>
                <table width="95%" cellspacing="0" cellpadding="0"
                  class="hubs-add-table-overlay">
                </table>
              </div>
              <div class="modal-footer">
                <!-- <button type="button" class="btn btn-default" data-dismiss="modal">Close</button> -->
                <form action="javascript:void(0);">
                  <input type="hidden" name="hubsList" /> <input type="submit"
                    class="btn btn-primary region-done-btn" value="Done" Id="doneHub"
                    onclick="sendHubList();" title="Done" />
                </form>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->
        <script>
          $('.hubs-add-table-overlay').on(
              'click',
              'td a.hubRemove',
              function() {
                hublist = _.without(hublist, _.findWhere(hublist, {
                  hubName : $(this).parents('tr').find('td#hub-name').text().trim()
                }));
                if (hublist.length == 0)
                  $(this).parents('table').children().remove();
                $(this).parents('tr').remove();
              });
        </script>
        <!-- end of logic for zones container and zone related data -->
        <html:form action="/circles.do?method=saveCircles" method="POST">
          <div class="savecircle">
            <%
              if (session.getAttribute("viewType") != null && session.getAttribute("viewType").equals("edit")) {
              %>
            <input class="btn btn-primary" type="submit" value="Update" class="btn btn-primary" title="Update" id="circle_update_btn"/>
            <%
              } else {
              %>
            <input class="btn btn-primary" type="submit" value="Save" onclick="return saveCircles();" class="btn btn-primary" title="Save" id="circle_save_btn"/>
            <%
              }
              %>
            <a href="<%=request.getContextPath()%>/circles.do?method=showManageCirclesPage" title="Cancel" class="btn btn-primary" id="circle_cancel_btn">
              <bean:message
                key="label.cancel" />
            </a>
          </div>
        </html:form>
        <div class="modal fade" id="viewOrgChart" tabindex="-1" role="dialog"
          aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content org-chart-info">
              <div class="modal-header org-chart-header">
                <h4 class="modal-title" id="orgChartName">
                </h4>
                <span title="close" class="close org-chart-close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-times"></i></span>
              </div>
              <div class="modal-body" id="org-chart-data">
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
      </div>
    </div>
  </body>
</html>
