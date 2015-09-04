<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%> 
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<html>
  <head>
    <script>
      $(document).ready(function() {
       
      $.validator.addMethod("math", function(value, element, regexp) {
        var re = new RegExp(regexp);
        return this.optional(element) || re.test(value);
      }, "Please Enter a valid Phone number");
      
      $.validator.addMethod("alphaspace", function(value, element, regexp) {
        var re = new RegExp(regexp);
        return this.optional(element) || re.test(value);
      });
      $.validator.addMethod("element", function(value, element, regexp) {
        var re = new RegExp(regexp);
        return this.optional(element) || re.test(value);
      }, "Please enter a valid OFSC Code");
      
      var validatorAddDistributorForm =  $('#addDistributorForm').validate({
          messages : {
            distributorName : {
              required : 'Distributor Name required',
              alphaspace : 'Please provide alphabets for Distributor Name'
            },
            distributorBarCode : {
              required : 'Distributor Bar Code required'
            },
            distributorCode : {
              required : 'Distributor Code required',
              number : 'Should be a Number'
            },
            lattitude : {
              required : 'Latitude required',
              math : 'Please enter a valid Latitude'
            },
            longitude : {
              required : 'Longitude required',
              math : 'Please enter a valid Longitude'
            },
            ofscCode : {
              required : 'OFSC Code required',
              minlength: 'OFSC Code should be atleast 10 characters'
            },
            primaryContactNo : {
              required : 'Primary contact number required',
              minlength: 'Phone No should be atleast 13 characters'
            },
            primaryEmail : {
              required : 'Primary Email required',
              email : 'Please enter correct Email address'
            },
            address : {
              required : 'Address required'
            },
            country : {
              required : 'Country required'
            },
            pinCode : {
              required : 'Pin Code required',
              number : 'Pin Code should be a Number',
              minlength: 'Pin Code should be atleast 6 characters'
            },
            state : {
              required : 'State required',
              maxlength : 'Maximum of 50 Characters are allowed for State'
            },
            city : {
              required : 'City required',
              maxlength : 'Maximum of 50 Characters are allowed for City',
              alphaspace : 'Please provide alphabets for City'
            },
            district : {
              maxlength : 'Maximum of 50 Characters are allowed for District',
              alphaspace : 'Please provide alphabets for District'
            },
            hubName : {
              required : 'Hub required'
            },
          },
          rules : {
            distributorName : {
              required : true,
              maxlength : 150,
              alphaspace : /^[a-zA-Z]+( [a-zA-z]+)*$/
            },
            distributorBarCode : {
              required : true,
      
            },
            distributorCode : {
              required : true
            },
            lattitude : {
              required : true,
              math : /^([-+]?[1-8]?\d(?:\.\d{1,18})?|90(?:\.0{1,6})?)$/,
              minlength :8
            },
            longitude : {
              required : true,
              math : /^([-+]?(?:1[0-7]|[1-9])?\d(?:\.\d{1,17})?|180(?:\.0{1,6})?)$/,
              minlength :8
            },
            ofscCode : {
              required : true,
              element : /^[a-zA-Z0-9]*$/,
              maxlength : 10,
            },
            primaryContactNo : {
              required : true,
              math :/^\d{10}$/,
              maxlength : 10,
              minlength: 10
            },
            primaryEmail : {
              required : true,
              email : true,
              maxlength : 150
            },
            secondaryContactNo : {
                math : /^\d{10}$/,
                maxlength : 10,
                minlength: 10
              },
              secondaryEmail : {
                email : true
              },
            address : {
              required : true,
              maxlength:256
            },
            country : {
              required : true
            },
            pinCode : {
              required : true,
              number : true,
              maxlength:6,
              minlength :6
            },
            state : {
              required : true,
              maxlength : 50
            },
            city : {
              required : true,
              maxlength : 50,
              alphaspace : /^[a-zA-Z]+( [a-zA-z]+)*$/
            },
            district : {
                maxlength : 50,
                alphaspace : /^[a-zA-Z]+( [a-zA-z]+)*$/
              },
            hubName : {
              required : true
            },
          },
          errorLabelContainer : $('#errors-container ul'),
          errorElement : 'li',
        });
      
      $(".resetAddDistributorForm").click(function() {
       validatorAddDistributorForm.resetForm();
       $("#prev_profile_image_file img").attr('src', '<%=request.getContextPath()%>/images/profile_img.png');
      });
      });
      function checkTypeofActivity() {
      var method = getParameterByName("method");
      if(method == 'showEditDistributorPage'){
        $(".navtitle1").empty();
        $(".navtitle1").html('<bean:message key="label.edit.distributor" />');
        $(".navtitle").empty();
        $(".navtitle").html('<div class="navtitle"><i class="fa fa-user"></i> <bean:message key="label.distributor" /> / <bean:message key="label.edit.distributor" /></div>');
        $(".bottombuttons").empty();
        $(".bottombuttons").html('<div class="updatedistbtn"><input id="distributor_update_btn" type="submit" value="Update" class="btn btn-primary" onclick="showAllPanels();"/> <a id="distributor_update_cancel_btn" href="<%=request.getContextPath()%>/distributor.do?method=showManageDistributorsPage" title="Cancel" class="btn btn-primary"><bean:message key="label.cancel" /></a> </div>');
      
      }
      }
      function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
            results = regex.exec(location.search);
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
      <html:form action="/distributor.do?method=addDistributor" styleId="addDistributorForm" enctype="multipart/form-data">
        <div id="titleheading">
          <div></div>
          <div class="navtitle1">
            <bean:message key="label.add.distributor" />
          </div>
          <br />
          <div id="titleheadingbar">
            <div class="navtitle">
              <i class="fa fa-map-marker"></i>
              <bean:message key="label.distributor" />
              /
              <%
                if (session.getAttribute("viewType") != null && session.getAttribute("viewType").equals("edit")) {
                %>
              <bean:message key="label.edit.distributor"/>
              <%
                } else {
                %>
              <bean:message key="label.add.distributor"/>
              <%
                }
                %>
            </div>
          </div>
          <div id='errors-container'>
            <span class="manditorytext"> Fields marked as * are Mandatory fields</span>
            <ul>
            </ul>
          </div>
          <div class="divstyleleft">
            <div class="divheading1" id="flip">
              <bean:message key="label.distributor.details" />
              <i class="fa fa-chevron-down downarrow"></i>
            </div>
            <div class="divcontent1" id="panel">
              <div class="left-content">
                <label id="distributorName">
                  <bean:message
                    key="label.distributor.name" />
                  *
                </label>
                <html:text property="distributorName" styleId="distributor_name_tf" styleClass="req" maxlength="150" />
                <label id="distributorBarCode">
                  <bean:message
                    key="label.distributor.bar.code" />
                  *
                </label>
                <html:text property="distributorBarCode" styleId="distributor_bar_code_tf" styleClass="req" maxlength="15" />
                <label id="distributorCode">
                  <bean:message
                    key="label.distributor.code" />
                  *
                </label>
                <html:text property="distributorCode" styleId="distributor_code_tf" styleClass="form-control" maxlength="15"  readonly="true"/>
              </div>
              <div class="left-content rightinputs">
                <label id="lattitudeLbl">
                  <bean:message key="label.lattitude" />
                  *
                </label>
                <html:text property="lattitude" styleId="lattitude_tf" styleClass="req" maxlength="22" />
                <label id="longitude">
                  <bean:message key="label.longitude" />
                  *
                </label>
                <html:text property="longitude" styleId="longitude_tf" styleClass="req" maxlength="22" />
                <label id="ofscCode">
                  <bean:message key="label.ofsc.code" />
                  *
                </label>
                <html:text property="ofscCode" styleId="ofsc_code_tf" styleClass="req" maxlength="10" />
              </div>
            </div>
            <div class="divheading5" id="flip1">
              <bean:message key="label.address.details" />
              <i class="fa fa-chevron-down downarrow"></i>
            </div>
            <div class="divcontent5" id="panel1">
              <div class="left-content">
                <label id="address">
                  <bean:message key="label.address" />
                  *
                </label>
                <html:textarea property="address" styleId="address_ta" styleClass="req"></html:textarea>
                <label id="city">
                  <bean:message key="label.city" />
                  *
                </label>
                <html:text property="city" styleId="city_tf " maxlength="50" styleClass="req" />
                <label id="district">
                  <bean:message key="label.district" />
                </label>
                <html:text property="district" styleId="district_tf" maxlength="50" styleClass="reqValid" />
              </div>
              <div class="left-content rightinputs">
                <label id="location">
                  <bean:message key="label.location" />
                </label>
                <html:text property="location" styleId="location_tf" maxlength="50" styleClass="reqValid" />
                <label id="state">
                  <bean:message key="label.state" />
                  *
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
                <html:select property="country" styleId="country_dd">
                  <html:option value="1">India</html:option>
                </html:select>
                <label id="pinCode">
                  <bean:message key="label.pincode" />
                  *
                </label>
                <html:text property="pinCode" styleId="pincode_tf" styleClass="req" maxlength="6" />
              </div>
            </div>
          </div>
          <div class="divstyleright">
            <div class="add-dist-heading-primary" id="flip3">
              <bean:message key="label.primarty.contact.details" />
              <i class="fa fa-chevron-down downarrow"></i>
            </div>
            <div class="divcontent3" id="panel3">
              <div class="left-content">
                <label id="primaryContactNo">
                  <bean:message key="label.phone" />
                  *
                </label>
                <div class="input-group input-group-sm">
                  <span class="input-group-addon">+91</span>
                  <html:text property="primaryContactNo" styleId="primary_contact_no_tf" maxlength="10" styleClass="contactNo form-control" />
                </div>
              </div>
              <div class="left-content rightinputs">
                <label id="primaryEmail">
                  <bean:message key="label.email" />
                  *
                </label>
                <html:text property="primaryEmail" styleId="primary_email_tf" maxlength="150" styleClass="req" />
              </div>
            </div>
            <div class="divheading6" id="flip4">
              <bean:message key="label.secondary.contact.details" />
              <i class="fa fa-chevron-down downarrow"></i>
            </div>
            <div class="add-dist-box-secondary" id="panel4">
              <div class="left-content">
                <label id="secondaryContactNo">
                  <bean:message key="label.phone" />
                </label>
                <div class="input-group input-group-sm">
                  <span class="input-group-addon">+91</span>
                  <html:text property="secondaryContactNo" styleId="secondary_contact_no_tf" maxlength="10" styleClass="contactNo form-control" />
                </div>
              </div>
              <div class="left-content rightinputs">
                <label id="secondaryEmail">
                  <bean:message
                    key="label.email" />
                </label>
                <html:text property="secondaryEmail" styleId="secondary_email_tf" maxlength="150" styleClass="reqValid" />
              </div>
            </div>
            <div class="divheading7" id="flip5">
              <bean:message key="label.other.details" />
              <i class="fa fa-chevron-down downarrow"></i>
            </div>
            <div class="add-dist-box-other" id="panel5">
              <div class="left-content">
                <label>
                  <bean:message key="label.license.details" />
                </label>
                <html:text property="licenceInfo" styleId="license_tf" maxlength="100" />
                <label id="hubName">
                  <bean:message key="label.hub" />
                  *
                </label>
                <logic:equal name="distributorForm" property="assignedToBeatPlan" value="true">
                  <html:select property="hubName" styleClass="form-control" disabled="true" styleId="hub_dd1">
                    <html:option value="">
                      <bean:message key="label.please.select" />
                    </html:option>
                    <logic:notEmpty name="distributorForm" property="hubDetailsList">
                      <html:optionsCollection property="hubDetailsList" label="hubName" value="hubId" />
                    </logic:notEmpty>
                  </html:select>
                </logic:equal>
                <logic:equal name="distributorForm" property="assignedToBeatPlan" value="false">
                  <html:select property="hubName" styleId="hub_dd2">
                    <html:option value="">
                      <bean:message key="label.please.select" />
                    </html:option>
                    <logic:notEmpty name="distributorForm" property="hubDetailsList">
                      <html:optionsCollection property="hubDetailsList" label="hubName" value="hubId" />
                    </logic:notEmpty>
                  </html:select>
                </logic:equal>
              </div>
              <div class="left-content rightinputs">
                <label for="photo">
                  <bean:message key="label.photo" />
                </label>
                <logic:notEmpty name="distributorForm" property="photoPath">
                  <div id="prev_profile_image_file">
                    <bean:define name="distributorForm" id="photoPath" property="photoPath"/>
                    <img alt="" class="profileImg" src="<%=request.getContextPath()%>/user.do?method=getImage&imagePath=<%= photoPath%>">
                  </div>
                  <html:file property="file"  styleClass="profile_image_file" styleId="profile_image_file" onchange="imageReader(this);"/>
                </logic:notEmpty>
                <logic:empty name="distributorForm" property="photoPath">
                  <div id="prev_profile_image_file">
                    <img alt="" class="profileImg" src="<%=request.getContextPath()%>/images/profile_img.png">
                  </div>
                  <html:file property="file" styleClass="profile_image_file" styleId="profile_image_file" onchange="imageReader(this);"/>
                </logic:empty>
              </div>
            </div>
          </div>
          <div class="bottombuttons">
            <%
              if (session.getAttribute("viewType") != null && session.getAttribute("viewType").equals("edit")) {
              %>
            <input id="distributor_update_btn" type="submit" value="Update" class="btn btn-primary" onclick="showAllPanels();"/>
            <%
              } else {
              %>
            <input id="distributor_save_btn" type="submit" value="Save" class="btn btn-primary" onclick="showAllPanels();"/>
            <%
              }
              %>
            <html:reset styleId="distributor_save_reset_btn" title="Reset" value="Reset" styleClass="resetAddDistributorForm btn btn-primary"/>
            <a id="distributor_save_cancel_btn" href="<%=request.getContextPath()%>/distributor.do?method=showManageDistributorsPage" title="Cancel" class="btn btn-primary">
              <bean:message key="label.cancel" />
            </a>
          </div>
        </div>
        <input type="hidden" name="method" />
        <input type="hidden" name="<%=Constants.TOKEN_KEY%>" value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>">
      </html:form>
    </div>
  </body>
</html>
