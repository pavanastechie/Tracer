<%@page import="java.util.List,java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%> 
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<%@ page import="org.json.JSONArray, org.json.JSONObject"%>
<!DOCTYPE html>
<html>
  <head>
    <script type="text/javascript">
      $('document').ready(function() {
        $( "#upload" ).change(function() {
                 var ext = this.value.match(/\.([^\.]+)$/)[1];
                 switch(ext)
                 {
                 case 'xls':
                   break;
               case 'xlsx':
                   break;
               case 'XLS':
                   break;
               case 'XLSX':
                   break;
               default:
                   alert('Please Upload only Excel files');
                   this.value='';
                 }
           });
      
      $('#uploadbtn').click(function() {
         
         var ajaxOptions = {
                url: "<%=request.getContextPath()%>/upload.do?method=getCAFsFromExcelTemplate",
                type: 'POST',
                data: data, 
                cache: false,
                dataType: 'json',
                processData: false, 
                contentType: false,
               };
           var promise = $.ajax(ajaxOptions);
           promise.done(function(){
            alert("upload"); 
           });
      }); 
      
      // validation for upload button
      $('#uploadForm').validate(
          {
            messages : {
                file : {
                required : 'Please upload proper excel file.',
              },
            },
            rules : {
                file : {
                required : true,
              },
            },
            errorLabelContainer : $('#errors-container ul'),
            errorElement : 'li',
          });
      });
    </script>
  </head>
  <body>
    <div class="clear"></div>
    <html:form action="/upload.do?method=getCAFsFromExcelTemplate" method="post" enctype="multipart/form-data" styleId="uploadForm">
      <div id="form-content">
        <div id="titleheading">
          <div class="navtitle1">
            <bean:message key="label.online.caf" />
          </div>
          <br />
          <div id="titleheadingbar">
            <div class="navtitle">
              <i class="fa fa-upload"></i>
              <bean:message key="label.upload" />
              /
              <bean:message key="label.online.caf" />
            </div>
          </div>
        </div>
        <div>
          <div class="upload-divheading" id="flip">
            <bean:message key="label.upload.caf" />
            <i class="fa fa-chevron-down downarrow"></i>
          </div>
          <div class="upload-divcontent" id="panel">
            <div id='errors-container'>
              <ul></ul>
            </div>
            <div class="upload-content">
              <html:file property="file" value="Upload" styleId="upload"  accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
              <button value="Upload" title="Upload" id="uploadbtn" class="btn btn-primary btnupload">Upload</button>
            </div>
          </div>
        </div>
        <input type="hidden" name="method" />
        <input type="hidden" name="<%=Constants.TOKEN_KEY%>" value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>">
      </div>
    </html:form>
  </body>
</html>