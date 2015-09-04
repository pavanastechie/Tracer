<%@page import="com.tracer.util.BeatPlanExcelManager"%>
<%@page import="com.tracer.dao.model.BeatPlanCircleDetails"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
  prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Bulk Upload</title>
    <script type="text/javascript">
      $('document').ready(function() {
      $('#beatplanuploadbtn').click(function() {
         
         var ajaxOptions = {
                url: "<%=request.getContextPath()%>/beatPlanBulkUpload.do?method=uploadBeatPlan",
                type: 'POST',
                data: data, 
                cache: false,
                dataType: 'json',
                processData: false, 
                contentType: false,
               };
         var extensions = {
                 "sFilter": "dataTables_filter manage_custom_filter_class",
                 "sLength": "dataTables_length manage_custom_length_class",
                 "sWrapper": "dataTables_wrapper",
                 "sStripeOdd": "dataTables-odd-row",
                 "sStripeEven ": "dataTables-even-row"
             }
        $.extend($.fn.dataTableExt.oStdClasses , extensions);
         $("#manage-beat-plan-table").dataTable({
             
           });
      });
      });
    </script>
  </head>
  <body>
    <div class="clear"></div>
    <html:form action="/beatPlanBulkUpload.do?method=uploadBeatPlan"
      method="post" enctype="multipart/form-data"
      styleId="beatPlanBulkUploadForm">
      <div id="form-content">
        <div id="titleheading">
          <div class="navtitle1">
            <bean:message key="label.beat.plan.bulk.upload" />
          </div>
          <br />
          <div id="titleheadingbar">
            <div class="navtitle">
              <i class="fa fa-upload"></i>
              <bean:message key="label.beat.plans" />
              /
              <bean:message key="label.beat.plan.bulk.upload" />
            </div>
          </div>
            <div id='errors-container'>
              <ul></ul>
            </div>
            <div class="divstyleright addBeatPlanBlock">
            <div class="divheading10"> Upload Beat Plan </div>
            <div id="panel5" class="divcontent10 innerAddBlockSuperAdmin innerBeatPlanBulkUpload">
            <div class="beat-plan-upload">
            <html:file property="file" value="Upload" styleId="upload" />
              <button value="Upload" title="Upload" id="beatplanuploadbtn"
                class="btn btn-primary btnupload btnbeatplanupload">Upload</button>
            </div>
            </div>
            <!-- <div id="flip4" class="divheading3">Beat Plan</div> -->
            <!-- <div id="panel4" class="divcontent7">
            <div class="load-beat-plan">
            <table id="load-beat-plan-table" width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="load-beatplan-page">
              <thead>
                <tr class="load-beatplan-content-headings">
                  <th class="load-beatplan-region-content-heading-name" style="width: 15%">Region</th>
                  <th class="load-beatplan-zone-content-heading-name" style="width: 15%">Zone</th>
                  <th class="load-beatplan-hub-content-heading-name" style="width: 15%">Hub</th>
                  <th class="load-beatplan-distributor-content-heading-name" style="width: 15%">Distributor</th>
                </tr>
              </thead>
              <tbody>
              </tbody>
            </table>
            </div>
          </div> -->
           <div class="bottombuttons">
            <a class="btn btn-primary actionButtons" id="beat-plan-save"
              href="<%=request.getContextPath()%>/beatPlanBulkUpload.do?method=showManageDistributorBeatPlansPage">Save</a>
          </div>
        </div>
          </div>
          </div>
    </html:form>
  </body>
</html>