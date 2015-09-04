<%@page import="org.json.JSONArray"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<%@page import="org.json.JSONArray, org.json.JSONObject"%>
<script type="text/javascript">
  var dependentProperties = [];
  var deletedDependentProperties = [];
  var myString;
  var add=true;
  var distributorIdToUpdate;
  function loadDistributorBeatPlanDetails(dependentProperties){
    console.log(dependentProperties);
    var extensions = {
      "sFilter": "dataTables_filter manage_custom_filter_class",
      "sLength": "dataTables_length manage_custom_length_class",
      "sWrapper": "dataTables_wrapper",
      "sStripeOdd": "dataTables-odd-row",
      "sStripeEven ": "dataTables-even-row"
    };
    $.extend($.fn.dataTableExt.oStdClasses , extensions);
    $("#beatPlanDetails").dataTable({
          "sDom": 'T<"clear">lfrtip',
          "sPaginationType": "full_numbers",
          "oLanguage": {
              "sProcessing": "<i class='fa fa-spinner fa-spin'></i>",
              "sEmptyTable": "No Data Available"
          },
          "bJQueryUI": true,
          "bProcessing": true,
          "sAjaxDataProp":"",
          "bDestroy":true,
          "aaData":dependentProperties,
          "aoColumns": [
                      {"aTargets": [ 0 ], "mDataProp": "beatPlanName", "bSortable": true, "sClass": "manage-beat-plan-name"},
                      {"aTargets": [ 1 ], "mDataProp": "circleName", "bSortable": true, "sClass": "manage-beat_plan-circle-name" },
                      {"aTargets": [ 2 ], "mDataProp": "regionsName", "bSortable": true, "sClass": "manage-beat_plan-region-name" },
                      {"aTargets": [ 3 ], "mDataProp": "zoneName", "bSortable": true, "sClass": "manage-beat_plan-zone-name" },
                      {"aTargets": [ 4 ], "mDataProp": "hubName", "bSortable": true, "sClass": "manage-beat_plan-hub-name" },
                      {"aTargets": [ 5 ], "mDataProp": "distributorName", "bSortable": true, "sClass": "manage-beat_plan-distributor-name" },
                      {"aTargets": [ 6 ], "mDataProp": "visitFrequency", "bSortable": true, "sClass": "manage-beat_plan-visit-frequency" },                  
                      {"aTargets": [ 7 ], "mDataProp": "circleId", "bSortable": true, "sClass": "manage-beat_plan-circle-id" },
                      {"aTargets": [ 8 ], "mDataProp": "regionId", "bSortable": true, "sClass": "manage-beat_plan-region-id" },                  
                      {"aTargets": [ 9 ], "mDataProp": "zoneId", "bSortable": true, "sClass": "manage-beat_plan-zone-id" },                  
                      {"aTargets": [ 10 ], "mDataProp": "hubId", "bSortable": true, "sClass": "manage-beat_plan-hub-id" },
                      {"aTargets": [ 11 ], "mDataProp": "type", "bSortable": true, "sClass": "manage-beat_plan-type" },
                      {"aTargets": [ 12 ], "mDataProp": "isBeatPlanAssigned", "bSortable": true, "sClass": "manage-beat_plan-type123" },
                      {"aTargets": [ 13 ], "sClass": "manage-sms-row-buttons" ,"bSortable": false,"mDataProp": "distributorId",
                        "mRender": function ( data, type, full ) {
                          return '<div class="manage-sms-row-buttons-edit"><a id="beat_plan_dist_edit_'+data+'" href="javascript:void(0)" data-distributorId='+data+'  title=Edit><i class="fa fa-edit"></i></a></div>'+
                            '<div class="manage-sms-row-buttons-delete"><a id="beat_plan_dist_delete_'+data+'" href="javascript:void(0)" data-distributorId='+data+'  title=Delete class='+ full.isBeatPlanAssigned +'><i class="fa fa-trash-o"></i></a></div>';
                        }
                      }
                  ],
         "oTableTools": {
             "sSwfPath": "<%=request.getContextPath()%>/js/dataTables/swf/copy_csv_xls_pdf.swf",
             "aButtons": [
                          {
                              "sExtends": "csv",
                              "sFileName": "Distributor Beat Plan.csv",
                              "mColumns": [ 0, 1,2,3,4,5,6]
                          },
                          {
                              "sExtends": "xls",
                              "sFileName": "Distributor Beat Plan.xls",
                              "mColumns": [ 0, 1,2,3,4,5,6]
                          },
                          {
                              "sExtends": "pdf",
                              "sFileName": "Distributor Beat Plan.pdf",
                              "mColumns": [ 0, 1,2,3,4,5,6]
                          },
                      ]
          },
          "aaSorting": [[ 0, "asc" ]]
      });
  }
  $(document).on('click','.manage-sms-row-buttons-delete a',function(e) {
      console.log(this);
      var that = $(this);
      var distributorIdToUpdate = $(this).attr('data-distributorId');
      console.log(distributorIdToUpdate);
      var visitFrequency =$(this).parents('tr').find('td.manage-beat_plan-visit-frequency').text();
      var distributorName =$(this).parents('tr').find('td.manage-beat_plan-distributor-name').text();
      var type = $(this).parents('tr').find('td.manage-beat_plan-type').text();
      e.preventDefault();
      $("<div title='Delete Beat Plan'>Are you sure you want to delete this Beat Plan?</div>").dialog({
          resizable: false,
          modal: true,
          buttons: {"Cancel": function() {
              $( this ).dialog( "close" );
            },
            "Delete BeatPlan": function() {
              if(type=="edit")
                {
              deleteRecord(parseInt(distributorIdToUpdate),visitFrequency);
              that.parents('tr').addClass('hide');
              if($("#distributorId option[value="+ distributorIdToUpdate +"]").length == 0)
                {
                $('#distributorId').append("<option value=" + distributorIdToUpdate + ">"+  distributorName +"</option>");
                }
                }
              else if(type=="add")
                {
                cleaner(dependentProperties,distributorIdToUpdate);
                that.parents('tr').addClass('hide');
                }
              $( this ).dialog( "close" );
            },
            
          }
        });
    
  });
  
  //Called when clicked on Edit Button in Datatable 
  $(document).on('click','.manage-sms-row-buttons-edit a',function(e) {
    console.log(this);
    console.log($(this).attr('data-distributorId'));
    var beatPlanName = $(this).parents('tr').find(
    'td.manage-beat-plan-name').text().trim();
    var circleName =$(this).parents('tr').find('td.manage-beat_plan-circle-name').text();
    var regionName =$(this).parents('tr').find('td.manage-beat_plan-region-name').text();
    var zoneName =$(this).parents('tr').find('td.manage-beat_plan-zone-name').text();
    var hubName =$(this).parents('tr').find('td.manage-beat_plan-hub-name').text();
    var distributorName =$(this).parents('tr').find('td.manage-beat_plan-distributor-name').text();
    var visitFrequency =$(this).parents('tr').find('td.manage-beat_plan-visit-frequency').text();
    var distributorId =$(this).parents('tr').find('td.manage-beat_plan-dist-id-row-buttons').text();
    var circleId = $(this).parents('tr').find('td.manage-beat_plan-circle-id').text();
    var regionId = $(this).parents('tr').find('td.manage-beat_plan-region-id').text();
    var zoneId = $(this).parents('tr').find('td.manage-beat_plan-zone-id').text();
    var hubId = $(this).parents('tr').find('td.manage-beat_plan-hub-id').text();
  
    console.log(beatPlanName);
    console.log(circleName);
    console.log(regionName);
    console.log(zoneName);
    console.log(hubName);
    console.log(distributorName);
    console.log(visitFrequency);
    console.log(distributorId);
    
    console.log("circle id is:"+circleId);
    distributorIdToUpdate = parseInt($(this).attr('data-distributorId'));
    if($("#distributorId option[value="+ distributorIdToUpdate +"]").length == 0)
      {
    $('#distributorId').append("<option value=" + $(this).attr('data-distributorId') + ">"+  distributorName +"</option>")
      }
    $('#distributorId').val($(this).attr('data-distributorId'));
    $('#visitFrequency').val(visitFrequency);
    
    $('#addToBeatPlan').hide();
    add=false;
    $('#updateToBeatPlan').show();
    
      });
      
  
    function saveBeatPlanDetails()
    {
      $('.custom-error').remove();
      if(dependentProperties.length > 0)
        {
          console.log(JSON.stringify($.merge(dependentProperties, deletedDependentProperties)));
          document.getElementById("beatPlanData").value=JSON.stringify($.merge(dependentProperties, deletedDependentProperties));
          document.getElementById("beatPlansForm").submit();
           
        }
      else
        {
        $('#errors-container ul').append('<li class="custom-error">Please select atleast one beat plan</li>').show();
        }
    }
    
  
    function updateBeatPlanDetails()
    {
      $('.custom-error').remove();
      if(dependentProperties.length > 0)
        {
          console.log(JSON.stringify($.merge(dependentProperties, deletedDependentProperties)));
          document.getElementById("beatPlanData").value=JSON.stringify($.merge(dependentProperties, deletedDependentProperties));
           document.beatPlansForm.action = "<%=request.getContextPath()%>/beatPlans.do?method=updateDistributorBeatPlan";
           document.getElementById("beatPlansForm").submit();
           
        }
      else
        {
        $('#errors-container ul').append('<li class="custom-error">Please select atleast one beat plan</li>').show();
        }
    }
    
    
    
    function deleteRecord(distributorIdToUpdate,visitFrequency)
    {
      for (var i = 0, l = dependentProperties.length; i < l; i++) {
            if (dependentProperties[i].distributorId == distributorIdToUpdate && dependentProperties[i].visitFrequency == visitFrequency) {
              dependentProperties[i].status = "inactive";
              deletedDependentProperties.push(dependentProperties[i]);
              dependentProperties.splice(i, 1);
              break;
            }
        }
      
      console.log(dependentProperties);
    }
    
    function cleaner(arr, id) {
        for (var i = 0; i < arr.length; i++) {
            var cur = arr[i];
            if (cur.distributorId == id) {
                arr.splice(i, 1);
                break;
            }
        }
        console.log(dependentProperties);
    }
    
    function addToBeatPlan() {
      /* $("#beatPlansForm").submit(); */
      var obj= {};
      obj["beatPlanName"] = $('#beatPlanName').val();
      obj["visitFrequency"] = $('#visitFrequency').val();
      obj["distributorName"] = $('#distributorId option:selected').text();
      obj["hubName"] = $('#hubId option:selected').text();
      obj["zoneName"] =  $('#zoneId option:selected').text();
      obj["regionsName"] = $('#regionId option:selected').text();
      obj["circleName"] =  $('#circleId option:selected').text();
  
      obj["circleId"] = parseInt($('#circleId').val());
      obj["regionId"] = parseInt($('#regionId').val());
      obj["zoneId"] = parseInt($('#zoneId').val());
      obj["hubId"] = parseInt($('#hubId').val());
      obj["distributorId"] = parseInt($('#distributorId').val());
      obj["status"] = "active";
      obj["type"] = "add";
      obj["beatPlanDistributorAssId"] = 0;
      obj["isBeatPlanAssigned"]="false";
      
  
      dependentProperties.push(obj);
      console.log(dependentProperties);
      myString=JSON.stringify(dependentProperties);
      
      loadDistributorBeatPlanDetails(dependentProperties);
      }
    
    function updateToBeatPlan() {
      var exist = {};
      if(dependentProperties.length > 0) {
      //  exist = _.where(dependentProperties, {distributorId: distributorIdToUpdate, beatPlanName: document.querySelector('#beatPlanName').value,circleName:document.getElementById('circleId').selectedOptions[0].text});
        
         for (var i = 0, l = dependentProperties.length; i < l; i++) {
                if (dependentProperties[i].distributorId === distributorIdToUpdate && dependentProperties[i].beatPlanName === $('#beatPlanName').val() && dependentProperties[i].circleName === $("#circleId option:selected").text()) {
                    dependentProperties[i].distributorName = document.getElementById('distributorId').selectedOptions[0].text;
                     dependentProperties[i].distributorId = parseInt(document.querySelector('#distributorId').value);
                     dependentProperties[i].visitFrequency =  document.querySelector('#visitFrequency').value;
                    break;
                }
            }
         add=true;
        console.log(exist);
        console.log(dependentProperties);
        
        $('#addToBeatPlan').show();
        $('#updateToBeatPlan').hide();
        
        loadDistributorBeatPlanDetails(dependentProperties);
      }
    }
      
      $(document).ready(function(){
      
          $('#circleId').change(function() {
            var selectedValue = this.value;
          $.ajax({
                type: "GET",
                contentType : "application/json; charset=utf-8",
                url: "<%=request.getContextPath()%>/beatPlans.do?method=getRegionsList&selectedId="+ selectedValue,
                success : function(data) {
                  $('#regionId').html('<option value="">Please Select...</option>');
                  $('#regionId').append(data);
                }
            });
      });
           
      $('#regionId').change(function() {
        var selectedValue = this.value;
          $.ajax({
                type: "GET",
                contentType : "application/json; charset=utf-8",
                url: "<%=request.getContextPath()%>/beatPlans.do?method=getZonesList&selectedId="+ selectedValue,
                success : function(data) {
                  $('#zoneId').html('<option value="">Please Select...</option>');
                  $('#zoneId').append(data);
                }
            });
      });
            
      $('#zoneId').change(function() {
        var selectedValue = this.value;
          $.ajax({
                type: "GET",
                contentType : "application/json; charset=utf-8",
                url: "<%=request.getContextPath()%>/beatPlans.do?method=getHubsList&selectedId="+ selectedValue,
                success : function(data) {
                  $('#hubId').html('<option value="">Please Select...</option>');
                  $('#hubId').append(data);
                }
            });
      });
  
      $('#hubId').change(function() {
        var selectedValue = this.value;
          $.ajax({
                type: "GET",
                contentType : "application/json; charset=utf-8",
                url: "<%=request.getContextPath()%>/beatPlans.do?method=getDistributorsList&selectedId="
                                  + selectedValue,
                              success : function(data) {
                                $('#distributorId')
                                    .html(
                                        '<option value="">Please Select...</option>');
                                $('#distributorId')
                                    .append(
                                        data);
                              }
                            });
                      });
  
              $('#distributorId').change(function() {
                $('#beatPlanName').prop('disabled', 'disabled').addClass('form-control');
                $('#circleId').prop('disabled', 'disabled').addClass('form-control');
                $('#zoneId').prop('disabled', 'disabled').addClass('form-control').addClass('form-control');
                $('#hubId').prop('disabled', 'disabled').addClass('form-control');
                $('#regionId').prop('disabled', 'disabled').addClass('form-control');
              });
  
              $('#addToBeatPlan').click(function() {
                if ($("#beatPlansForm").valid()) {
                  addToBeatPlan();
                  showAllPanels();
                } else {
                  $('.custom-error').remove();
                }
              });
  
              $('#updateToBeatPlan').click(function() {
                if ($("#beatPlansForm").valid()) {
                  updateToBeatPlan();
                } else {
                  $('.custom-error').remove();
                }
              });
  
              $.validator.addMethod("dataLength", function(value, element, regexp) {
                return dependentProperties.length > 0;
              }, "Please Add");
              $.validator.addMethod("alphaspace", function(value, element, regexp) {
                var re = new RegExp(regexp);
                return this.optional(element) || re.test(value);
              });
              $.validator
                  .addMethod(
                      "checkDistributor",
                      function(value) {
                        var exist = true;
                        if(dependentProperties.length == 0)
                          {
                          exist = true;
                        } 
                        else
                          {
                          for ( var i = 0; i < dependentProperties.length; i++) {
                            console.log(dependentProperties[i].distributorId == value);
                            if(add){
                            if (dependentProperties[i].distributorId == value && dependentProperties[i].status=="active") {
                              exist = false;
                              break;
                            }
                            }
                          }
                          }
                        return exist;
                      },
                      "Please select different Distributor from the list");
  
              $('#beatPlansForm')
                  .validate(
                      {
                        messages : {
                          beatPlanName : {
                            required : 'Please enter Beat Plan Name',
                            alphaspace : 'Please provide alphabets for beat plan name'
                          },
                          regionId : {
                            required : ' Please Select Region',
                          },
                          hubId : {
                            required : 'Please Select Hub ',
                          },
                          visitFrequency : {
                            required : 'Please Select Visit Frequency',
                          },
                          circleId : {
                            required : 'Please Select Circle',
                          },
                          zoneId : {
                            required : 'Please Select Zone',
                          },
                          distributorId : {
                            required : 'Please Select Distributor',
                            checkDistributor : 'Please select different Distributor',
                          },
                        },
                        rules : {
                          beatPlanDetails : {
                            dataLength : true,
                          },
                          beatPlanName : {
                            required : true,
                            alphaspace : /^[a-zA-Z]+( [a-zA-z]+)*$/
                          },
                          regionId : {
                            required : true,
                          },
                          hubId : {
                            required : true,
                          },
                          visitFrequency : {
                            required : true,
                          },
                          circleId : {
                            required : true,
                          },
                          zoneId : {
                            required : true,
                          },
                          distributorId : {
                            required :true,
                            checkDistributor :true,
                          },
                        },
                        errorLabelContainer : $('#errors-container ul'),
                        errorElement : 'li',
                      });
  <%String viewType = (String) session.getAttribute("viewType");
    if (viewType != null && viewType.equalsIgnoreCase("edit")) {
      JSONArray beatPlanJson = (JSONArray) session
          .getAttribute("beatplandetailsjson");%>
    console.log('inside');
  <%for (int i = 0, size = beatPlanJson.length(); i < size; i++) {
    JSONObject objectInArray = beatPlanJson.getJSONObject(i);%>
    dependentProperties.push(
  <%=objectInArray%>
    );
  <%}%>
    loadDistributorBeatPlanDetails(dependentProperties);
              $('#beatPlanName').prop('disabled', 'disabled');
              $('#circleId').prop('disabled', 'disabled');
              $('#zoneId').prop('disabled', 'disabled');
              $('#hubId').prop('disabled', 'disabled');
              $('#regionId').prop('disabled', 'disabled');
              
              $(".navtitle1").empty();
              $(".navtitle1").html('<bean:message key="label.distributor.beat.plan" /> ');
              $(".navtitle").empty();
              $(".navtitle").html('<div class="navtitle">   <i class="fa fa-user"></i> <bean:message key="label.beat.plans" /> / <bean:message key="label.edit.distributor.beat.plan" /></div>');
  <%}%>
    });
</script>
<body onload="loadDistributorBeatPlanDetails(dependentProperties)">
  <div class="clear"></div>
  <div id="form-content">
  <html:form action="/beatPlans.do?method=saveDistributorBeatPlan"
    styleId="beatPlansForm">
    <div id="titleheading">
      <div class="navtitle1">
        <bean:message key="label.distributor.beat.plan" />
      </div>
      <br />
      <div id="titleheadingbar">
        <div class="navtitle">
          <i class="fa fa-list"></i>
          <bean:message key="label.beat.plans" /> / <label><bean:message key="label.add.distributor.beat.plan" /></label>
        </div>
      </div>
      <div id='errors-container'>
        <ul></ul>
      </div>
      <div class="divstyleright addBeatPlanBlock">
        <div class="divheading10">
          <bean:message key="label.beat.plan" />
        </div>
        <div class="divcontent10 innerAddBlockSuperAdmin" id="panel5">
          <div class="left-content">
            <div>
              <label>
                <bean:message key="label.beat.plan.name" />
              </label>
              <html:text property="beatPlanName" styleId="beatPlanName" styleClass="req" maxlength="150" />
              <html:hidden property="beatPlanData" styleId="beatPlanData" />
            </div>
            <label>
              <bean:message key="label.reports.regionName" />
            </label>
            <html:select property="regionId" styleClass="reportsDropdown" styleId="regionId">
              <html:option value="">
                <bean:message key="label.reports.selectRegion" />
              </html:option>
              <logic:notEmpty name="beatPlansForm"
                property="regionDetailsList">
                <html:optionsCollection property="regionDetailsList"
                  label="regionName" value="regionId" />
              </logic:notEmpty>
            </html:select>
            <label>
              <bean:message key="label.hub" />
            </label>
            <html:select property="hubId" styleClass="reportsDropdown"
              styleId="hubId">
              <html:option value="">
                <bean:message key="label.reports.selectHub" />
              </html:option>
              <logic:notEmpty name="beatPlansForm" property="hubDetailsList">
                <html:optionsCollection property="hubDetailsList"
                  label="hubName" value="hubId" />
              </logic:notEmpty>
            </html:select>
            <label>
              <bean:message key="label.visit.frequency" />
            </label>
            <html:select property="visitFrequency" styleClass="req"
              styleId="visitFrequency">
              <option value="">
                <bean:message key="label.visit.select.visit.frequency" />
              </option>
              <option value="1">
                <bean:message key="label.visit.frequency.once" />
              </option>
              <option value="2">
                <bean:message key="label.visit.frequency.twice" />
              </option>
              <option value="3">
                <bean:message key="label.visit.frequency.thrice" />
              </option>
              <option value="4">
                <bean:message key="label.visit.frequency.four" />
              </option>
              <option value="5">
                <bean:message key="label.visit.frequency.five" />
              </option>
              <option value="6">
                <bean:message key="label.visit.frequency.six" />
              </option>
              <option value="7">
                <bean:message key="label.visit.frequency.seven" />
              </option>
              <option value="8">
                <bean:message key="label.visit.frequency.eight" />
              </option>
              <option value="9">
                <bean:message key="label.visit.frequency.nine" />
              </option>
            </html:select>
          </div>
          <div class="left-content rightinputs">
            <label>
              <bean:message key="label.reports.circleName" />
            </label>
            <html:select property="circleId" styleClass="reportsDropdown"
              styleId="circleId">
              <html:option value="">
                <bean:message key="label.reports.selectCircle" />
              </html:option>
              <logic:notEmpty name="beatPlansForm"
                property="circleDetailsList">
                <html:optionsCollection property="circleDetailsList"
                  label="circleName" value="circleId" />
              </logic:notEmpty>
            </html:select>
            <label>
              <bean:message key="label.reports.zoneName" />
            </label>
            <html:select property="zoneId" styleClass="reportsDropdown"
              styleId="zoneId">
              <html:option value="">
                <bean:message key="label.reports.selectZone" />
              </html:option>
              <logic:notEmpty name="beatPlansForm" property="zoneDetailsList">
                <html:optionsCollection property="zoneDetailsList"
                  label="zoneName" value="zoneId" />
              </logic:notEmpty>
            </html:select>
            <label>
              <bean:message key="label.distributor.name" />
            </label>
            <html:select property="distributorId"
              styleClass="distributorClass" styleId="distributorId">
              <html:option value="">
                <bean:message key="label.select.distributor" />
              </html:option>
              <logic:notEmpty name="beatPlansForm"
                property="distributorDetailsList">
                <html:optionsCollection property="distributorDetailsList"
                  label="distributorName" value="distributorId" />
              </logic:notEmpty>
            </html:select>
            <div class="btplnbtn">
              <a title="Add BeatPlan" id="addToBeatPlan"
                class="btn btn-primary">
                <bean:message
                  key="label.add.beat.plan" />
              </a>
              <a title="Update"
                id="updateToBeatPlan" class="btn btn-primary">
                <bean:message
                  key="label.update.beat.plan" />
              </a>
            </div>
          </div>
        </div>
        <div class="divheading3" id="flip4">
          Distributors<i class="fa fa-chevron-down downarrow"></i>
        </div>
        <div class="divcontent7" id="panel4">
          <div>
            <table id="beatPlanDetails" width="100%" align="center"
              cellpadding="0" cellspacing="0" border="0" class="records-page"
              name="beatPlanDetails">
              <thead>
                <tr class="manage-users-content-headings">
                  <th class="manage-users-content-heading-name" style="width: 12%">Beat Plan</th>
                  <th class="manage-users-content-heading-hub" style="width: 15%">Circle Name</th>
                  <th class="manage-users-content-heading-reporting-to" style="width: 15%">Region Name</th>
                  <th class="manage-users-content-heading-reporting-to" style="width: 16%">Zone Name</th>
                  <th class="manage-users-content-heading-contact-number" style="width: 13%">Hub Name</th>
                  <th class="manage-users-content-heading-role" style="width: 12%">Distributor</th>
                  <th class="manage-users-content-heading-zone" style="width: 12%">Visit Frequency</th>
                  <th class="manage-users-content-heading-distid"></th>
                  <th class="manage-users-content-heading-distid"></th>
                  <th class="manage-users-content-heading-distid"></th>
                  <th class="manage-users-content-heading-distid"></th>
                  <th class="manage-users-content-heading-distid"></th>
                  <th class="manage-users-content-heading-distid"></th>
                  <th class="manage-users-content-heading-distid"></th>
                </tr>
              </thead>
              <tbody>
              </tbody>
            </table>
          </div>
        </div>
        <div class="bottombuttons">
          <%
            String type = (String) session.getAttribute("viewType");
              if (type.equalsIgnoreCase("edit")) {
            %>
          <a id="updateBeatPlan" onclick="updateBeatPlanDetails()"
            title="Update" class="btn btn-primary actionButtons"> Update</a>
          <%
            } else {
            %>
          <a id="saveBeatPlan" onclick="saveBeatPlanDetails()" title="Save"
            class="btn btn-primary actionButtons"> Save</a>
          <%
            }
            %>
          <a id="beat_plan_dist_cancel_hr" href="<%=request.getContextPath()%>/beatPlans.do?method=showManageDistributorBeatPlansPage" title="Cancel" class="btn btn-primary actionButtons"> Cancel</a>
        </div>
      </div>
    </div>
    <input type="hidden" name="method" />
    <input type="hidden" name="<%=Constants.TOKEN_KEY%>" value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>" />
  </html:form>
  <!--  </div> -->
</body>
