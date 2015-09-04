<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%> 
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<%@ page import="com.tracer.webui.presentation.form.NotificationForm" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script>
      function loadButtons(){
        var method = getParameterByName("method");
        var type = getParameterByName("type");
        if(method == "showAddSms"){
          $(".add-sms-template-edit").hide();
          $(".smstempbtn").removeClass("add-sms-less-width");
        }
        else if(method == "showEditSmsTemplate" && type == "view"){
          $(".add-sms-template-content-text >textarea").attr("disabled", true);
          $(".add-sms-template-edit").show();
          $(".add-sms-template-reset").hide();
          $(".add-sms-template-save").hide();
          $(".add-sms-template-category").attr("disabled", true);
          $(".smstempbtn").addClass("add-sms-less-width");
          $("#titleheadingbar").html('<div class="navtitle"><i class="fa fa-envelope "></i> SMS Template  /  <label>Edit SMS Template</label></div>');
          
        }
        else if(method == "showEditSmsTemplate"){
          $(".add-sms-template-reset").hide();
          $(".add-sms-template-edit").hide();
          $(".smstempbtn").addClass("add-sms-less-width");
          $("#titleheadingbar").html('<div class="navtitle"><i class="fa fa-envelope "></i> SMS Template  /  <label>Edit SMS Template</label></div>');
        }
      }
      function getParameterByName(name) {
          name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
          var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
              results = regex.exec(location.search);
          return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
      }
      function validateNotificationTemplate(){
        var categoryId = $(".add-sms-template-category").val();
        var smsContent = $(".add-sms-template-content-text >textarea").val();
        if(categoryId != "" && smsContent != ""){
          document.forms[0].action='notification.do?method=addSmsTemplate';
          document.forms[0].submit();
        }
      }
      $(document).ready(function() {
      var validatorAddSmsTemplate = $('#addSmsTemplate').validate({
            messages:{
              smsCategoryId: {required:"Please Select SMS Category"},
              smsContent: {required:"Please enter SMS Content"},
            },
              rules: {
                smsCategoryId: {
                  required: true
                },
                smsContent: {
                  required: true,
                  maxlength:160
                },
              },
              errorLabelContainer: '#error-container ul',
              errorElement : 'li',
          });
          $(".resetAddSmsTemplate").click(function() {
            validatorAddSmsTemplate.resetForm();
          });
      
      });
      function errorNotification(message) {
          notif({
            msg : message+" !!",
            type : "error"
          });
        }
        <% if(request.getAttribute("errorMessage") != null) {  %>
          errorNotification("<%= request.getAttribute("errorMessage") %>");
        <% request.removeAttribute("errorMessage");
        } %>
    </script>
  </head>
  <body onload="loadButtons()">
    <div class="clear"></div>
    <div id="form-content">
      <div id="titleheading" class="add-sms-template">
        <div class="navtitle1">SMS Template</div>
        <br />
        <logic:equal name="ERROR_MESSAGE" value="" scope="session">
        </logic:equal>
        <div id="titleheadingbar">
          <div class="navtitle"><i class="fa fa-envelope "></i> SMS Template  /  <label>Add SMS Template</label></div>
        </div>
        <br/>
        <div id='error-container'>
          <span class="manditorytext"> Fields marked as * are Mandatory fields</span>
          <ul></ul>
        </div>
        <html:form action="/notification.do?method=addSmsTemplate" styleId="addSmsTemplate">
          <div class="divstyleleft">
            <div class="divheading1">SMS Details</div>
            <div id="panel" class="divcontent1">
              <input type="hidden" name="categoryId" property="smsTemplateId" />
              <input type="hidden" name="categoryId" property="smsCategoryId" />
              <br/>
              <div class="add-sms-template-details">
                <div class="add-sms-template-category-details">
                  <div class="add-sms-template-category-label"><label>SMS Category*</label></div>
                  <div class="add-sms-template-category-options">
                    <html:select property="smsCategoryId" styleClass="add-sms-template-category" styleId="sms_template_category_dd">
                      <html:option value="">Please Select Category</html:option>
                      <html:optionsCollection property="categoryDetails" label="smsCategoryName" value="smsCategoryId"/>
                    </html:select>
                  </div>
                </div>
                <div class="add-sms-template-content">
                  <div class="add-sms-template-content-label"><label>SMS Content*</label></div>
                  <div class="add-sms-template-content-text">
                    <html:textarea property="smsContent" styleClass="form-control" rows="4" cols="55" styleId="sms_template_content_ta"/>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="smstempbtn">
            <input type="submit" class="btn btn-primary add-sms-template-save" value="Save" id="sms_template_save_btn"/>
            <html:reset styleId="sms_template_save_reset_btn" value="Reset" styleClass="resetAddSmsTemplate btn btn-primary add-sms-template-reset"/>
            <a id="sms_template_save_cancel_btn" href="<%=request.getContextPath()%>/notification.do?method=manageSmsTemplate" title="Cancel" class="btn btn-primary">
              <bean:message key="label.cancel" />
            </a>
            <input id="sms_template_edit_btn" type="button" class="btn btn-primary add-sms-template-edit" value="Edit" onclick="document.forms[0].action='notification.do?method=showEditSmsTemplate';document.forms[0].submit()"/>
          </div>
        </html:form>
      </div>
    </div>
  </body>
</html>