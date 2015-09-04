<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<!DOCTYPE html>
<html>
  <head>
    <script>
      $(document).ready(function() {
        $.validator.addMethod("math", function(value, element,
            regexp) {
          var re = new RegExp(regexp);
          return this.optional(element) || re.test(value);
        }, "Please enter only Alphabets");
            
        $.validator.addMethod("element", function(value, element,
            regexp) {
          var re = new RegExp(regexp);
          return this.optional(element) || re.test(value);
        }, "User Name should be only Alphanumeric characters");
        
        $.validator.addMethod("phone", function(value, element,
            regexp) {
          var re = new RegExp(regexp);
          return this.optional(element) || re.test(value);
        }, "Please enter a valid Primary Contact number");
        
        $.validator.addMethod("mobile", function(value, element,
            regexp) {
          var re = new RegExp(regexp);
          return this.optional(element) || re.test(value);
        }, "Please enter a valid Office Phone number");
        
        $.validator.addMethod("isPhoneNumberExists", function(value, element) {
          var url = "<%=request.getContextPath()%>/user.do?method=isOfficePhoneNoExists";
          <% if(request.getParameter("userId") != null) {%>
              url = url+"&userId="+<%= request.getParameter("userId")%>;
          <%
        }
        %>
          var isExist = false;
          $.ajax({ 
            url: url+"&role=5&phoneNo="+value,
            async: false,
            success: function(data) { 
              isExist = data.phoneNoExists; 
            }
          });
          return !isExist;
        }, "Phone number already exists");
        
        $.validator.addMethod("isUserExists", function(value, element) {
          var viewType = <%=(String) request.getParameter("viewType")%>;
          if(viewType == 'edit') 
            return false;
          
          var url = "<%=request.getContextPath()%>/user.do?method=isUserExists";
          var isExist = false;
          $.ajax({ 
            url: url+"&userName="+value,
            async: false,
            success: function(data) { 
              isExist = data.userNameExists; 
            }
          });
          return !isExist;
        }, "User Name already exists");
        
        var validatorAddUserForm =    $('#addUserForm').validate({
          messages : {
            userName : {
              required : 'User name required'
            },
            gender : {
              required : 'Gender required'
            },
            name : {
              required : 'First Name required',
              math : 'First Name should contain only Alphabets'
            },
            lastName : {
              required : 'Last name required',
              math : 'Last Name should contain only Alphabets'
            },
            dateOfBirth : {
              required : 'Date of birth required'
            },
            address : {
              required : 'Address required'
            },
            primaryContactNo : {
              required : 'Primary contact number required'
            },
            primaryEmail : {
              required : 'Primary email required'
            },
            officePhone : {
              required : 'Office phone number is required'
            },
            imeiNo : {
              required : 'IMEI number is required'
            },
            roleName : {
              required : 'Role required'
            },
            hubId : {
              required : 'Hub required'
            },
            reportingToUserID : {
              required : 'Reporting to user required'
            },
            pinCode : {
               number : 'Pin Code should be a Number',
             minlength: 'Pin Code should be atleast 6 characters'
           },
           city : {
             maxlength : 'Maximum of 50 Characters are allowed for City',
             minlength: 'Please enter atleast 3 characters for City',
            math :" Please enter only alphabets for City"
           },
           district : {
             maxlength : 'Maximum of 50 Characters are allowed for District',
             minlength: 'Please enter atleast 3 characters for District',
            math :" Please enter only alphabets for District"
             },
          },
          rules : {
            userName : {
              required : true,
              minlength :5,
              isUserExists: true,
              element : /^[a-zA-Z0-9]*$/,
              
            },
            gender : {
              required : true
            },
            name : {
              required : true,
              minlength :5,
              math : /^[a-zA-Z]+( [a-zA-z]+)*$/
            },
            lastName : {
              required : true,
              minlength :1,
              math : /^[a-zA-Z]+( [a-zA-z]+)*$/
            },
            dateOfBirth : {
              required : true,
              date : true
            },
            dateOfAnniversary : {
              date : true
            },
            address : {
              required : true,
              minlength:5,
              maxlength : 256
            },
            primaryContactNo : {
              required : true,
              phone : /^\d{10}$/,
                    maxlength : 10,
                   minlength: 10,
                     isPhoneNumberExists: true
            },
            primaryEmail : {
              required : true,
              email : true,
            },
            secondaryContactNo : {
              phone : /^\d{10}$/,
              maxlength : 10,
              minlength: 10
            },
            secondaryEmail : {
              email : true
            },
            officePhone : {
              mobile : /^\d{10}$/,
              maxlength : 10,
              minlength: 10
            },
            imeiNo : {
              required : true
            },
            roleName : {
              required : true
            },
            hubId : {
              required : true
            },
            reportingToUserID : {
              required : true
            },
            pinCode : {
             number : true,
             maxlength:6,
             minlength :6
           },
          /*  state : {
            minlength : 3,
             maxlength : 50,
             math : /^[a-zA-Z]+( [a-zA-z]+)*$/
           }, */
           city : {
            minlength : 3,
             maxlength : 50,
             math : /^[a-zA-Z]+( [a-zA-z]+)*$/
           },
           district : {
            minlength : 3,
             maxlength : 50,
             math : /^[a-zA-Z]+( [a-zA-z]+)*$/
           },
          },
          errorLabelContainer : $('#errors-container ul'),
          errorElement : 'li',
        });
      
        $(".resetAddUserForm").click(function() {
          validatorAddUserForm.resetForm();
          $("#prev_profile_image_file img").attr('src', '<%=request.getContextPath()%>/images/profile_img.png');
        });
      
        // hiding Date of Anniversary input for Single/NeverMarried selections.
        $('#maritalStatus').on('change', function() {
          if (this.value == 4 || this.value == 5) {
            $("#dateOfAnniversarySpan").addClass('hide');
          } else {
            $("#dateOfAnniversarySpan").removeClass('hide');
          }
        });
      
        $('#roleId').change(function() {
          $('#reportingToUserID').html('<option value="">Please Select...</option>');
          var selectedRoleId = this.value;
          var methodName = '';
          switch (selectedRoleId) {
          case '2':
            $('#hubNameLabel').text('Circles');
            methodName = "getCirclesList";
            $('#reportingToUserID').prop('disabled', true).addClass('form-control');
            $('#reportingToUserID').removeClass('error');
            break;
          case '3':
            $('#hubNameLabel').text('Regions');
            methodName = "getRegionsList";
            $('#reportingToUserID').prop('disabled', false).removeClass('form-control');
            break;
          case '4':
            $('#hubNameLabel').text('Zones');
            methodName = "getZonesList";
            $('#reportingToUserID').prop('disabled', false).removeClass('form-control');
            break;
          case '5':
            $('#hubNameLabel').text('Hubs');
            methodName = "getHubsList";
            $('#reportingToUserID').prop('disabled', false).removeClass('form-control');
            break;
      
          default:
            break;
          }
          $.ajax({
              type: "GET",
              contentType : "application/json; charset=utf-8",
              url: "<%=request.getContextPath()%>/user.do?method=" + methodName,
              success: function(data) {
                $('#hubId').html('<option value="">Please Select...</option>');
                $('#hubId').append(data);
              }
            });
          
        });
        
        $('#hubId').change(function() {
          var selectedValue = this.value;
          var selectedRoleId = $('#roleId').val();
          $.ajax({
              type: "GET",
              contentType : "application/json; charset=utf-8",
              url: "<%=request.getContextPath()%>/user.do?method=getManagersList&selectedRoleId="+ selectedRoleId +"&selectedId=" + selectedValue,
              success: function(data) {
                $('#reportingToUserID').html('<option value="">Please Select...</option>');
                $('#reportingToUserID').append(data);
              }
            });
        });
      
      });
      function checkTypeofActivity() {
      
        var method = getParameterByName("viewType");
        if (method == 'edit') {
          $(".navtitle1").empty();
          $(".navtitle1").html('<bean:message key="label.edit.manager" /> ');
          $(".navtitle").empty();
          $(".navtitle").html('<div class="navtitle">   <i class="fa fa-user"></i> <bean:message key="label.users" /> / <bean:message key="label.edit.manager" /></div>');
          
          $('#userName').prop('readonly', 'true').addClass('form-control');
          $('#roleId').prop('disabled', 'true').addClass('form-control');
          $('#hubId').prop('disabled', 'true').addClass('form-control');
          $('#reportingToUserID').prop('disabled', 'true').addClass('form-control');
          
          $(".bottombuttons").empty();
          $(".bottombuttons").html('<div class="updatedistbtn"> <input styleId="user_manager_update_btn" type="submit" value="Update" class="btn btn-primary" onclick="showAllPanels();"/> <a id="user_manager_update_cancel_btn" href="<%=request.getContextPath()%>/user.do?method=showManageUsersPage" title="Cancel" class="btn btn-primary"><bean:message key="label.cancel" /></a> </div>');
          
          var roleId = $("#roleId").val();
          switch (roleId) {
          case '2':
            $('#hubNameLabel').text('Circles');
            break;
          case '3':
            $('#hubNameLabel').text('Regions');
            break;
          case '4':
            $('#hubNameLabel').text('Zones');
            break;
          case '5':
            $('#hubNameLabel').text('Hubs');
            break;
      
          default:
            break;
          }
        }
      }
      function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex.exec(location.search);
        return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
      }
      
      function imageReader(input) {
        var str =input.files[0].type;
        var split_value=str.split("/");
        var img = $('#prev_profile_image_file').find('img');
        var defaultImgSrc = '<%=request.getContextPath()%>/images/profile_img.png';
        if (input.files && input.files[0]) {
          if(split_value[0] == 'image' || split_value[0] =='application' || split_value[0] =='text' || split_value[0] =='') {
            // allows only jpg, jpeg, gif, png
            if(split_value[1] =='jpeg'||split_value[1] =='gif'||split_value[1] =='png'||split_value[1] =='jpg') {
              if(input.files[0].size <= 50000) { // equal or below to 50 kb
                var reader = new FileReader();
                reader.onload = function (e) {
                  img.attr('src', e.target.result);
                };
                reader.readAsDataURL(input.files[0]);
              } else {
                img.attr('src', defaultImgSrc);
                $('#profile_image_file').val('');
                alert('Image size should be below 50 kb');
              }
            } else {
              img.attr('src', defaultImgSrc);
              $('#profile_image_file').val('');
              alert('Please select files of type jpg, gif, png');
            }
          }
          
        }
      }
    </script>
  </head>
  <body onload="checkTypeofActivity()">
    <div class="clear"></div>
    <div id="form-content">
      <html:form styleId="addUserForm" action="/user.do?method=addManager" enctype="multipart/form-data">
        <div id="titleheading">
          <div class="navtitle1">
            <bean:message key="label.register.manager" />
          </div>
          <br />
          <div id="titleheadingbar">
            <div class="navtitle">
              <i class="fa fa-user"></i>
              <bean:message key="label.users" />
              /
              <bean:message key="label.add.manager" />
            </div>
          </div>
          <div id='errors-container'>
            <span class="manditorytext"> Fields marked as * are Mandatory fields</span>
            <ul></ul>
          </div>
          <div class="divstyleleft">
            <div class="divheading1" id="flip">
              <bean:message key="label.authentication.details" />
              <i class="fa fa-chevron-down downarrow"></i>
            </div>
            <div class="divcontent1" id="panel">
              <div class="left-content">
                <label>
                  <bean:message key="label.user.name" />
                  *
                </label>
                <html:text property="userName" styleId="userName" maxlength="32" />
                <label id='hubNameLabel'>
                  <bean:message key="label.hub" />
                </label>
                <html:select styleId="hubId" property="hubId">
                  <html:option value="">
                    <bean:message key="label.please.select" />
                  </html:option>
                  <logic:notEmpty name="userForm" property="hubDetailsList">
                    <html:optionsCollection property="hubDetailsList"
                      label="hubName" value="hubId" />
                  </logic:notEmpty>
                  <logic:notEmpty name="userForm" property="zoneDetailsList">
                    <html:optionsCollection property="zoneDetailsList"
                      label="zoneName" value="zoneId" />
                  </logic:notEmpty>
                  <logic:notEmpty name="userForm" property="regionDetailsList">
                    <html:optionsCollection property="regionDetailsList"
                      label="regionName" value="regionId" />
                  </logic:notEmpty>
                  <logic:notEmpty name="userForm" property="circleDetailsList">
                    <html:optionsCollection property="circleDetailsList"
                      label="circleName" value="circleId" />
                  </logic:notEmpty>
                </html:select>
              </div>
              <div class="left-content rightinputs">
                <%long roleId = 0L;
                  if(request.getSession().getAttribute(com.tracer.common.Constants.ROLE_ID) != null) {
                  roleId = (Long) request.getSession().getAttribute(com.tracer.common.Constants.ROLE_ID);
                  }%>
                <label>
                  <bean:message key="label.role" />
                  *
                </label>
                <html:select property="roleName" styleId="roleId">
                  <html:option value="">
                    <bean:message key="label.please.select" />
                  </html:option>
                  <%if(roleId == com.tracer.common.Constants.SYSTEM_ADMIN) { %>
                  <html:option value="2">
                    <bean:message key="label.cbm" />
                  </html:option>
                  <%}  if(roleId == com.tracer.common.Constants.SYSTEM_ADMIN || roleId == com.tracer.common.Constants.CIRCLE_MANAGER) { %>
                  <html:option value="3">
                    <bean:message key="label.rbm" />
                  </html:option>
                  <%}  if(roleId == com.tracer.common.Constants.SYSTEM_ADMIN || roleId == com.tracer.common.Constants.REGION_MANAGER) { %>
                  <html:option value="4">
                    <bean:message key="label.zbm" />
                  </html:option>
                  <%}  if(roleId == com.tracer.common.Constants.SYSTEM_ADMIN || roleId == com.tracer.common.Constants.ZONE_MANAGER) { %>
                  <html:option value="5">
                    <bean:message key="label.asm" />
                  </html:option>
                  <%}%>
                </html:select>
                <label>
                  <bean:message key="label.reporting.to" />
                  *
                </label>
                <html:select styleId="reportingToUserID" property="reportingToUserID">
                  <html:option value="">
                    <bean:message key="label.please.select" />
                  </html:option>
                  <logic:notEmpty name="userForm" property="userReportingToList">
                    <html:optionsCollection property="userReportingToList"
                      label="name" value="userId" />
                  </logic:notEmpty>
                </html:select>
              </div>
            </div>
            <div class="divheading5" id="flip1">
              <bean:message key="label.address.details" />
              <i class="fa fa-chevron-down downarrow"></i>
            </div>
            <div class="divcontent5" id="panel1">
              <div class="left-content">
                <label>
                  <bean:message key="label.address" />
                  *
                </label>
                <html:textarea property="address" styleClass="right-content" styleId="address_ta"></html:textarea>
                <label>
                  <bean:message key="label.city" />
                </label>
                <html:text property="city" styleId="city_tf" maxlength="50" />
                <label>
                  <bean:message key="label.district" />
                </label>
                <html:text property="district" styleId="district_tf" maxlength="50" />
              </div>
              <div class="left-content rightinputs">
                <label>
                  <bean:message key="label.state" />
                </label>
                <html:select property="state" styleId="state_dd">
                  <html:option value="Andhra Pradesh">Andhra Pradesh</html:option>
                  <html:option value="Arunachal Pradesh">Arunachal Pradesh</html:option>
                  <html:option value="Assam">Assam</html:option>
                  <html:option value="Bihar">Bihar</html:option>
                  <html:option value="Chhattisgarh">Chhattisgarh</html:option>
                  <html:option value="Goa">Goa</html:option>
                  <html:option value="Gujarat">Gujarat</html:option>
                  <html:option value="Haryana">Haryana</html:option>
                  <html:option value="Himachal Pradesh">Himachal Pradesh</html:option>
                  <html:option value="Jammu & Kashmir">Jammu & Kashmir</html:option>
                  <html:option value="Jharkhand">Jharkhand</html:option>
                  <html:option value="Karnataka">Karnataka</html:option>
                  <html:option value="Kerala">Kerala</html:option>
                  <html:option value="MadhyaPradesh">MadhyaPradesh</html:option>
                  <html:option value="Maharashtra">Maharashtra</html:option>
                  <html:option value="Manipur">Manipur</html:option>
                  <html:option value="Meghalaya">Meghalaya</html:option>
                  <html:option value="Mizoram">Mizoram</html:option>
                  <html:option value="Nagaland">Nagaland</html:option>
                  <html:option value="Odisha">Odisha</html:option>
                  <html:option value="Punjab">Punjab</html:option>
                  <html:option value="Rajasthan">Rajasthan</html:option>
                  <html:option value="Sikkim">Sikkim</html:option>
                  <html:option value="Tamil Nadu">Tamil Nadu</html:option>
                  <html:option value="Telangana">Telangana</html:option>
                  <html:option value="Tripura">Tripura</html:option>
                  <html:option value="Uttarakhand">Uttarakhand</html:option>
                  <html:option value="Uttar Pradesh">Uttar Pradesh</html:option>
                  <html:option value="West Bengal">West Bengal</html:option>
                </html:select>
                <label>
                  <bean:message key="label.country" />
                </label>
                <html:select property="country" disabled="true" styleId="country_dd">
                  <html:option value="1">India</html:option>
                </html:select>
                <label>
                  <bean:message key="label.pincode" />
                </label>
                <html:text property="pinCode" styleId="pincode_tf" maxlength="6" />
              </div>
            </div>
          </div>
          <div class="divstyleright">
            <div class="divheading2" id="flip2">
              <bean:message key="label.person.details" />
              <i class="fa fa-chevron-down downarrow"></i>
            </div>
            <div class="divcontent2" id="panel2">
              <div class="left-content">
                <div>
                  <label>
                    <bean:message key="label.gender" />
                    *
                  </label>
                  <html:radio property="gender" value="M"
                    styleClass="genderRadioButtons" styleId="rdbMale">
                    <bean:message key="label.male" />
                  </html:radio>
                  <html:radio property="gender" value="F"
                    styleClass="genderRadioButtons" styleId="rdbFemale">
                    <bean:message key="label.female" />
                  </html:radio>
                </div>
                <label>
                  <bean:message key="label.first.name" />
                  *
                </label>
                <html:text property="name" styleId="first_name_tf" maxlength="150" />
                <label>
                  <bean:message key="label.last.name" />
                  *
                </label>
                <html:text property="lastName" styleId="last_name_tf" maxlength="100" />
                <label>
                  <bean:message key="label.date.of.birth" />
                  *
                </label>
                <html:text property="dateOfBirth" styleClass="dobdatePicker" styleId="date_of_birth_dp"/>
                <label>
                  <bean:message key="label.blood.group" />
                </label>
                <html:select property="bloodGroup" styleId="blood_group_dd">
                  <html:option value="">
                    <bean:message key="label.please.select" />
                  </html:option>
                  <html:option value="1">
                    <bean:message key="label.blood.group.o+ve" />
                  </html:option>
                  <html:option value="2">
                    <bean:message key="label.blood.group.o-ve" />
                  </html:option>
                  <html:option value="3">
                    <bean:message key="label.blood.group.ab+ve" />
                  </html:option>
                  <html:option value="4">
                    <bean:message key="lable.blood.group.a" />
                  </html:option>
                  <html:option value="5">
                    <bean:message key="lable.blood.group.b" />
                  </html:option>
                </html:select>
              </div>
              <div class="left-content rightinputs">
                <label for="photo">
                  <bean:message key="label.photo" />
                </label>
                <logic:notEmpty name="userForm" property="photoPath">
                  <div id="prev_profile_image_file">
                    <bean:define name="userForm" id="photoPath" property="photoPath"/>
                    <img alt="" class="profileImg" src="<%=request.getContextPath()%>/user.do?method=getImage&imagePath=<%= photoPath%>">
                  </div>
                  <%-- <html:file property="file"  styleClass="profile_image_file" styleId="profile_image_file" onchange="imageReader(this);"/> --%>
                </logic:notEmpty>
                <logic:empty name="userForm" property="photoPath">
                  <div id="prev_profile_image_file">
                    <img alt="" class="profileImg" src="<%=request.getContextPath()%>/images/profile_img.png">
                  </div>
                  <%-- <html:file property="file" styleClass="profile_image_file" styleId="profile_image_file" onchange="imageReader(this);"/> --%>
                </logic:empty>
                <label>
                  <bean:message key="label.marital.status" />
                </label>
                <html:select property="maritalStatus" styleId="marital_status_dd">
                  <html:option value="0">
                    <bean:message key="label.please.select" />
                  </html:option>
                  <html:option value="1">
                    <bean:message key="label.marital.status.married" />
                  </html:option>
                  <html:option value="2">
                    <bean:message key="label.marital.status.married.with.children" />
                  </html:option>
                  <html:option value="3">
                    <bean:message key="label.marital.status.no.children.engaged" />
                  </html:option>
                  <html:option value="4">
                    <bean:message key="label.marital.status.single" />
                  </html:option>
                  <html:option value="5">
                    <bean:message key="label.marital.status.never.married" />
                  </html:option>
                  <html:option value="6">
                    <bean:message key="label.marital.status.formerly.married" />
                  </html:option>
                </html:select>
                <span id='dateOfAnniversarySpan' class="hide">
                  <label>
                    <bean:message
                      key="label.date.of.anniversary" />
                  </label>
                  <html:text property="dateOfAnniversary" styleId="date_of_anniversary_tf" styleClass="anniversarydatePicker" />
                </span>
              </div>
            </div>
            <div class="divheading3" id="flip3">
              <bean:message key="label.primarty.contact.details" />
              <i class="fa fa-chevron-down downarrow"></i>
            </div>
            <div class="divcontent3" id="panel3">
              <div class="left-content">
                <label>
                  <bean:message key="label.phone" />
                  *
                </label>
                <div class="input-group input-group-sm">
                  <span class="input-group-addon">+91</span>
                  <html:text property="primaryContactNo" styleId="primary_contact_no_tf" maxlength="10" styleClass="contactNo form-control"/>
                </div>
              </div>
              <div class="left-content rightinputs">
                <label>
                  <bean:message key="label.email" />
                  *
                </label>
                <html:text property="primaryEmail" styleId="primary_email_tf" maxlength="150" />
              </div>
            </div>
            <div class="divheading6" id="flip4">
              <bean:message key="label.secondary.contact.details" />
              <i class="fa fa-chevron-down downarrow"></i>
            </div>
            <div class="divcontent6" id="panel4">
              <div class="left-content">
                <label>
                  <bean:message key="label.phone" />
                </label>
                <div class="input-group input-group-sm">
                  <span class="input-group-addon">+91</span>
                  <html:text property="secondaryContactNo" styleId="secondary_contact_no_tf" maxlength="10" styleClass="contactNo form-control"/>
                </div>
              </div>
              <div class="left-content rightinputs">
                <label>
                  <bean:message key="label.email" />
                </label>
                <html:text property="secondaryEmail" styleId="secondary_email_tf" maxlength="150" />
              </div>
            </div>
          </div>
          <div class="bottombuttons">
            <html:submit styleId="user_manager_add_save_btn" styleClass="btn btn-primary" value="Save" onclick="showAllPanels();"><i class="fa fa-check-square-o"></i></html:submit>
            <html:reset styleId="user_manager_add_reset_btn" value="Reset" styleClass="resetAddUserForm btn btn-primary" />
            <a id="user_manager_add_cancel_btn"  href="<%=request.getContextPath()%>/user.do?method=showManageUsersPage" title="Cancel" class="btn btn-primary">
              <bean:message key="label.cancel" />
            </a>
          </div>
        </div>
        <input type="hidden" name="method" />
        <input type="hidden" name="<%=Constants.TOKEN_KEY%>"
          value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>" />
      </html:form>
    </div>
  </body>
</html>