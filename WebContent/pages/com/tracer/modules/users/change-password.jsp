<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<!DOCTYPE html>
<html>
  <head>
   <script src="https://www.google.com/recaptcha/api.js"></script>
    <script>
      $(document).ready(function() {
         $.validator.addMethod("regex", function(value, element, regexp) {
           var re = new RegExp(regexp);
           return this.optional(element) || re.test(value);
         }, "");

         $.validator.addMethod("checkPassword", function(value, element, param) {
           return this.optional(element) || value === param;
         }, "Please provide your correct current password");
         
         $('#newPassword').bind('cut copy paste', function(event) {
               event.preventDefault();
         });
         $('#confirmNewPassword').bind('cut copy paste', function(event) {
               event.preventDefault();
         });
         $.validator.addMethod("notEqual", function(value, element, param) {
             return this.optional(element) || value !== param;
         }, "Please choose a value!");
             
         $('#changePasswordForm').validate(
           {
              messages : {
                currentPassword : {
                  required : 'Current Password required',
                  checkPassword : 'Please provide your correct current password',
                },
                newPassword : {
                  required : 'New Password required',
                  regex : 'New Password should contain: Minimum 8 characters with 1 Uppercase Alphabet, 1 Lowercase Alphabet and 1 Number',
                  notEqual:'New Password should not be same as Current Password'
                },
                confirmNewPassword : {
                  required : 'Confirm New Password required',
                  regex : 'Please enter the same password as New Password',
                  equalTo : 'Please enter the same password as New Password'
                },
              },
              rules : {
                currentPassword : {
                  required : true,
                  checkPassword : '<%=session.getAttribute("actualPassword")%>'
                },
                newPassword : {
                  required : true,
                  regex :  /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/,
                  notEqual :'<%=session.getAttribute("actualPassword")%>'

                },
                confirmNewPassword : {
                  required : true,
                  equalTo : "#newPassword",
                },
              },
              errorLabelContainer : $('#errors-container ul'),
              errorElement : 'li',
            });
          // hiding Date of Anniversary input for Single/NeverMarried selections.
       });
      
      function successNotification(msg) {
        notif({
          msg : msg,
          type : "success"
        });
      }
      
      function failureNotification() {
        notif({
          msg : "Failed to add Distributor Beat Plan !!",
          type : "fail"
        });
      }
      <%if (request.getAttribute("updateSuccess") != null) {%>
      successNotification("Password Changed Successfully !!");
      <%} else if (request.getAttribute("errorMessage") != null) {%>
      successNotification("Problem in Updating Password !!");
      <%}%>
      
    </script>
  </head>
  <body>
    <div class="clear"></div>
    <div id="form-content">
      <html:form styleId="changePasswordForm" action="/user.do?method=updatePassword">
        <div id="titleheading">
          <div class="navtitle1">
            <bean:message key="label.change.password" />
          </div>
          <br />
          <div id="titleheadingbar">
            <div class="navtitle">
              <i class="fa fa-wrench menu-icon-blue"></i>
              <bean:message key="label.settings" />
              /
              <bean:message key="label.change.password" />
            </div>
          </div>
          <div id='errors-container'>
            <ul></ul>
          </div>
          <div id='errors'></div>
           <logic:messagesPresent message="true">
            <ul id="messsages1">
              <html:messages id="msg" message="true">
                <li>
                  <bean:write name="msg" filter="false"/>
                </li>
              </html:messages>
            </ul>
          </logic:messagesPresent>
          <div class="changePasswordBlock">
            <div class="divheading3">
              <bean:message key="label.change.password" />
            </div>
            <div class="divcontent3" id="panel3">
              <div class="left-content">
                <label>
                  <bean:message key="label.current.password" />
                </label>
                <html:password property="currentPassword" styleId="currentPassword" >
                </html:password>
              </div>
              <div class="left-content ">
                <label>
                  <bean:message key="label.new.password" />
                </label>
                <html:password property="newPassword" styleId="newPassword">
                </html:password>
              </div>
              <div class="left-content ">
                <label>
                  <bean:message key="label.confirm.new.password" />
                </label>
                <html:password property="confirmNewPassword" styleId="confirmNewPassword">
                </html:password>
              </div>
              <div class="left-content ">
              <div class="g-recaptcha" data-sitekey="6Le21QcTAAAAAJPKCO8Z2r0qRcRfpBZNS3gPq5TL"></div>
              </div>
              
            </div>
            <div class="chpwdbtn">
              <input id="change_password_submit_btn" type="submit" value="Update Password" class="btn btn-primary" />
            </div>
          </div>
        </div>
    </div>
    <input type="hidden" name="<%=Constants.TOKEN_KEY%>"value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>" />
    </html:form>
  </body>
</html>