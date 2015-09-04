<%@page import="com.tracer.dao.model.UserDetails"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
  prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<!DOCTYPE html>
<html>
  <head>
    <script>
      $(document).ready(function() {
          $.validator.addMethod("phone", function(value, element,
              regexp) {
            var re = new RegExp(regexp);
            return this.optional(element) || re.test(value);
          }, "Please Enter a valid Phone number");
          
          $.validator.addMethod("isPhoneNumberExists", function(value, element) {
            var url = "<%=request.getContextPath()%>/user.do?method=isOfficePhoneNoExists";
            url = url+"&userId="+<%= (Long) session.getAttribute("userId")%>;
            var isExist = false;
            $.ajax({ 
              url: url+"&role="+<%= (Long) session.getAttribute("roleId")%>+"&phoneNo="+value,
              cache: false,
              async: false,
              success: function(data) { 
                console.log(data.phoneNoExists);
                isExist = data.phoneNoExists; 
              }
            });
            return !isExist;
          }, "Phone number already exist");
          
          $.validator.addMethod("math", function(value, element,
              regexp) {
            var re = new RegExp(regexp);
            return this.optional(element) || re.test(value);
          }, "Please Enter only Alphabets");
          
          $.validator.addMethod("alphaspace", function(value, element, regexp) {
            var re = new RegExp(regexp);
             return this.optional(element) || re.test(value);
          });
          $('#editUserForm').validate({
            messages : {
              gender : {
                required : 'Gender required'
              },
              name : {
                required : 'Name required',
                math : 'First Name should contain only Alphabets'
              },
              lastName : {
                required : 'Last Name required',
                math : 'Last Name should contain only Alphabets'
              },
              dateOfBirth : {
                required : 'Date of birth required'
              },
              address : {
                required : 'Address required'
              },
              primaryContactNo : {
                number : 'Primary Contact should be a Number',
                required : 'Primary contact number required'
              },
              primaryEmail : {
                required : 'Primary email required',
                email : 'Please enter correct email address'
              },
              secondaryContactNo : {
                number : 'Secondary Contact should be a Number',
                maxlength : 'Maximm of 10 Digits are allowed'
              },
              secondaryEmail : {
                email : 'Please enter correct email address'
              },
              dateOfAnniversary : {
                date : 'Should be in Date Format'
              },
              officePhone : {
                number : 'Office Phone should be only a Number',
                maxlength : 'Maximum of 10 Digits are allowed for Office Phone'
              },
              imeiNo : {
                number : 'IMEI should be only a Number',
                maxlength : 'Maximum of 16 Digits are allowed for IMEI Number'
              },
              city : {
                maxlength : 'Maximum of 50 Characters are allowed for City',
                alphaspace : 'Please provide alphabets for City'
              },
              district : {
                maxlength : 'Maximum of 50 Characters are allowed for District',
                alphaspace : 'Please provide alphabets for District'
              },
              state : {
                maxlength : 'Maximum of 50 Characters are allowed for State',
                alphaspace : 'Please provide alphabets for State'
              },
              country : {
                maxlength : 'Maximum of 50 Characters are allowed for Country'
              },
              pinCode : {
                number : 'Pin Code should be only a Number',
                maxlength : 'Maximum of 10 Digits are allowed for Pin Code'
              },
            },
            rules : {
              gender : {
                required : true
              },
              name : {
                required : true,
                maxlength : 32,
                math : /^[a-zA-Z]+( [a-zA-z]+)*$/
              },
              lastName : {
                required : true,
                maxlength : 100,
                math : /^[a-zA-Z]+( [a-zA-z]+)*$/
              },
              dateOfBirth : {
                required : true,
                date : true
              },
              address : {
                required : true,
                maxlength : 256
              },
              primaryContactNo : {
                isPhoneNumberExists: true,
                required : true,
                phone : /^\d{10}$/,
                maxlength : 10,
                minlength: 10
              },
              primaryEmail : {
                required : true,
                email : true,
                maxlength : 150
              },
              secondaryContactNo : {
                phone : /^\d{10}$/,
                maxlength : 10,
                minlength: 10
              },
              secondaryEmail : {
                email : true,
                maxlength : 150
              },
              dateOfAnniversary : {
                date : true
              },
              officePhone : {
                isPhoneNumberExists: true,
                phone : /^\d{10}$/,
                number : true,
                maxlength : 10,
                required: true
              },
              imeiNo : {
                number : true,
                maxlength : 16
              },
              city : {
                maxlength : 50,
                alphaspace : /^[a-zA-Z]+( [a-zA-z]+)*$/
              },
              district : {
                maxlength : 50,
                alphaspace : /^[a-zA-Z]+( [a-zA-z]+)*$/
              },
              state : {
                maxlength : 50,
                alphaspace : /^[a-zA-Z]+( [a-zA-z]+)*$/
              },
              country : {
                maxlength : 50
              },
              pinCode : {
                number : true,
                maxlength : 10
              },
            },
            errorLabelContainer : $('#errors-container ul'),
            errorElement : 'li',
          });
      
          // hiding Date of Anniversary input for Single/NeverMarried selections.
          $('#maritalStatus').on('change', function() {
            if (this.value == 3 || this.value == 4) {
              $("#dateOfAnniversarySpan").addClass('hide');
            } else {
              $("#dateOfAnniversarySpan").removeClass('hide');
            }
          });
      
        });
        
      function successNotification(msg) {
        notif({
          msg : msg,
          type : "success"
        });
      }
      
      function failureNotification() {
        notif({
          msg : "Failed to update the profile !!",
          type : "fail"
        });
      }
      <%if (request.getAttribute("updateSuccess") != null) {%>
      successNotification("Profile Updatd Successfully !!");
      <%} else if (request.getAttribute("errorMessage") != null) {%>
      successNotification("Problem in Updating Profile !!");
      <%}%>
      
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
  <body>
    <%
      UserDetails userDetails = (UserDetails) request.getAttribute("editProfileDetails");
      %>
    <div class="clear"></div>
    <div id="form-content">
      <html:form styleId="editUserForm" action="/user.do?method=updateEditProfile" enctype="multipart/form-data">
        <div id="titleheading">
          <div class="navtitle1">
            <bean:message key="label.edit.profile" />
          </div>
          <br />
          <div id="titleheadingbar">
            <div class="navtitle">
              <i class="fa fa-cog menu-icon-blue"></i>
              <bean:message key="label.settings" />
              /
              <bean:message key="label.edit.profile" />
            </div>
          </div>
          <div id='errors-container'>
            <span class="manditorytext"> Fields marked as * are Mandatory fields</span>
            <ul></ul>
          </div>
          <div class="divstyleright editProfileLeftBlock">
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
                  <html:radio property="gender" value="M" styleClass="genderRadioButtons" styleId="rdbMale">
                    <bean:message key="label.male" />
                  </html:radio>
                  <html:radio property="gender" value="F" styleClass="genderRadioButtons" styleId="rdbFemale">
                    <bean:message key="label.female" />
                  </html:radio>
                </div>
                <label>
                  <bean:message key="label.first.name" />
                  *
                </label>
                <html:text property="name" styleId="first_name_tf" />
                <label>
                  <bean:message key="label.last.name" />
                  *
                </label>
                <html:text property="lastName" styleId="last_name_tf" />
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
                  <html:file property="file"  styleClass="profile_image_file" styleId="profile_image_file" onchange="imageReader(this);"/>
                </logic:notEmpty>
                <logic:empty name="userForm" property="photoPath">
                  <div id="prev_profile_image_file">
                    <img alt="" class="profileImg" src="<%=request.getContextPath()%>/images/profile_img.png">
                  </div>
                  <html:file property="file" styleClass="profile_image_file" styleId="profile_image_file" onchange="imageReader(this);"/>
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
                <%-- <label>
                  <bean:message key="label.anniversary.details" />
                </label>
                <html:text property="dateOfAnniversary" styleId="date_of_anniversary_tf" styleClass="anniversarydatePicker"></html:text> --%>
              </div>
            </div>
            <%
              Long roleId = (Long) session.getAttribute("roleId");
                if (roleId == 1 || roleId == 6) {
              %>
            <div class="divheading7" id="flip5">
              <bean:message key="label.other.details" />
              <i class="fa fa-chevron-down downarrow"></i>
            </div>
            <div class="divcontent7" id="panel5">
              <div class="left-content">
                <label>
                  <bean:message key="label.nearest.police.station" />
                </label>
                <html:textarea property="nearestPoliceStation" styleId="nearest_police_station_ta" />
              </div>
              <div class="left-content rightinputs">
                <label>
                  <bean:message key="label.office.phone.number" />
                </label>
                <div class="input-group input-group-sm">
                  <span class="input-group-addon">+91</span>
                  <html:text property="officePhone" styleId="office_phone_number_tf" styleClass="form-control" maxlength="10"/>
                </div>
                </button>
                <label>
                  <bean:message key="label.imei.number" />
                </label>
                <html:text property="imeiNo" styleId="imei_number_tf" />
              </div>
            </div>
            <%
              }
              %>
          </div>
          <div class="divstyleleft editProfileRightBlock">
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
                <html:textarea property="address" styleId="address_ta"></html:textarea>
                <label>
                  <bean:message key="label.city" />
                </label>
                <html:text property="city" styleId="city_tf" />
                <label>
                  <bean:message key="label.district" />
                </label>
                <html:text property="district" styleId="district_tf" />
              </div>
              <div class="left-content rightinputs">
                <label>
                  <bean:message key="label.state" />
                </label>
                <html:text styleId="state_tf" property="state" />
                <label>
                  <bean:message key="label.country" />
                </label>
                <html:select property="country" styleId="country_dd">
                  <html:option value="1">
                    <bean:message key="lable.country.value1" />
                  </html:option>
                </html:select>
                <label>
                  <bean:message key="label.pincode" />
                </label>
                <html:text property="pinCode" styleId="pincode_tf"></html:text>
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
                  <html:text property="primaryContactNo" styleId="primary_contact_no_tf" styleClass="form-control" maxlength="10"/>
                </div>
              </div>
              <div class="left-content rightinputs">
                <label>
                  <bean:message key="label.email" />
                  *
                </label>
                <html:text property="primaryEmail" styleId="primary_email_tf"></html:text>
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
                  <html:text property="secondaryContactNo" styleId="secondary_contact_no_tf" styleClass="form-control" maxlength="10"/>
                </div>
              </div>
              <div class="left-content rightinputs">
                <label>
                  <bean:message key="label.email" />
                </label>
                <html:text property="secondaryEmail" styleId="secondary_email_tf"></html:text>
              </div>
            </div>
          </div>
          <div class="editprofilebtn">
            <input id="update_profile_submit_btn" type="submit" value="Update Profile" class="btn btn-primary update-profile" />
          </div>
        </div>
      </html:form>
    </div>
  </body>
</html>