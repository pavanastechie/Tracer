<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<body>
  <div class="clear"></div>
  <div id="form-content">
    <html:form styleId="addUserForm" action="/user.do?method=addRunner" enctype="multipart/form-data">
      <div id="titleheading">
        <div class="navtitle1">
          CAF Data
        </div>
        <br />
        <div id="titleheadingbar">
          <div class="navtitle">
            <i class="fa fa-floppy-o"></i>
            CAF Data
          </div>
        </div>
        <div id='errors-container'>
          <span class="manditorytext"> Fields marked as * are Mandatory fields</span>
          <ul>
          </ul>
        </div>
        <div class="divstyleleft">
          <div class="divheading1" id="flip">
            Connection Details
            <i class="fa fa-chevron-down downarrow"></i>
          </div>
          <div class="divcontent1" id="panel">
            <div class="left-content">
              <label>Cell Number *</label>
              <html:text property="userName" styleId="userName" maxlength="32"/>
              <label>CIF Number *</label>
              <html:text property="userName" styleId="userName" maxlength="32"/>
              <label>Sale Date *</label>
              <html:text property="dateOfBirth" styleClass="dobdatePicker" readonly="true" styleId="date_of_birth_dp"/>
              <label>Type of Subscriber *</label>
              <html:select styleId="hubId" property="hubId">
                <logic:notEmpty name="userForm" property="hubDetailsList">
                  <html:option value=""><bean:message key="label.please.select" /></html:option>
                  <html:optionsCollection property="hubDetailsList" label="hubName" value="hubId" />
                </logic:notEmpty>
              </html:select>
              <label>Previous Service Provider</label>
              <html:text property="password" styleClass="form-control" maxlength="20" styleId="password_tf"/>
            </div>
            <div class="left-content rightinputs">
              <label>Mobile Connections Count *</label>
              <html:text property="password" styleClass="form-control" maxlength="20" styleId="password_tf"/>
              <div>
              <label>Is MNP *</label>
              <html:select property="bloodGroup" styleId="blood_group_dd">
                <html:option value=""><bean:message key="label.please.select" /></html:option>
                <html:option value="1"><bean:message key="label.blood.group.o+ve" /></html:option>
                <html:option value="2"><bean:message key="label.blood.group.o-ve" /></html:option>
                <html:option value="3"><bean:message key="label.blood.group.ab+ve" /></html:option>
                <html:option value="4"><bean:message key="lable.blood.group.a" /></html:option>
                <html:option value="5"><bean:message key="lable.blood.group.b" /></html:option>
              </html:select>
               </div>
               <div>
               <label>DND *</label>
              <html:select property="bloodGroup" styleId="blood_group_dd">
                <html:option value=""><bean:message key="label.please.select" /></html:option>
                <html:option value="1"><bean:message key="label.blood.group.o+ve" /></html:option>
                <html:option value="2"><bean:message key="label.blood.group.o-ve" /></html:option>
                <html:option value="3"><bean:message key="label.blood.group.ab+ve" /></html:option>
                <html:option value="4"><bean:message key="lable.blood.group.a" /></html:option>
                <html:option value="5"><bean:message key="lable.blood.group.b" /></html:option>
              </html:select>
               </div>
              <label>Out Circle Customer *</label>
              <html:select styleId="reportingToUserID" property="reportingToUserID">
                <html:option value="">
                  <bean:message key="label.please.select" />
                </html:option>
                <logic:notEmpty name="userForm" property="userReportingToList">
                  <html:optionsCollection property="userReportingToList" label="name" value="userId" />
                </logic:notEmpty>
              </html:select>
              <label>Previous Circle</label>
              <html:text property="password" styleClass="form-control" maxlength="20" styleId="password_tf"/>
            </div>
          </div>
          
          <div class="divheading5" id="flip1">
            <bean:message key="label.address.details"/> (RA)
            <i class="fa fa-chevron-down downarrow"></i>
          </div>
          <div class="divcontent2 caf-form-divcontent2" id="panel1">
            <div class="left-content">
              <label>House Number RA *</label>
              <html:text property="city" styleId="city_tf" maxlength="50" />
              <label>Confirm House Number RA *</label>
              <html:text property="city" styleId="city_tf" maxlength="50" />
              <label>Street Address RA *</label>
              <html:text property="district" styleId="district_tf" maxlength="50" />
              <label>Confirm Street Address RA *</label>
              <html:text property="district" styleId="district_tf" maxlength="50" />
              <label><bean:message key="label.country" /> *</label>
              <html:select property="country" disabled="true" styleId="country_dd">
                <html:option value="1">India</html:option>
              </html:select>
            </div>
            <div class="left-content rightinputs">
              <label>Locality RA *</label>
              <html:text property="district" styleId="district_tf" maxlength="50" />
              <label>Confirm Locality RA *</label>
              <html:text property="district" styleId="district_tf" maxlength="50" />
              <label>City RA *</label>
              <html:text property="district" styleId="district_tf" maxlength="50" />
              <label>State RA *</label>
              <html:text property="district" styleId="district_tf" maxlength="50" />
              <label>Pin Code RA *</label>
              <html:text property="pinCode" styleId="pincode_tf" maxlength="6" />
            </div>
          </div>
          <div class="divheading7" id="flip5">
            Supporting Document Details
            <i class="fa fa-chevron-down downarrow"></i>
          </div>
          <div class="divcontent7  caf-form-divcontent7" id="panel5">
            <div class="left-content">
              <label>Photo Identity ID Proof *</label>
              <html:text property="userName" styleId="userName" maxlength="32"/>
              <label>Document NO(I) *</label>
              <html:text property="userName" styleId="userName" maxlength="32"/>
              <label>Date of Issue(I) *</label>
              <html:text property="dateOfBirth" styleClass="dobdatePicker" readonly="true" styleId="date_of_birth_dp"/>
              <label>Place of Issue(I) *</label>
              <html:text property="userName" styleId="userName" maxlength="32"/>
              <label>Issuing Authority(I Address) *</label>
              <html:text property="password" styleClass="form-control" maxlength="20" styleId="password_tf"/>
              <label>Form 60 *</label>
              <html:text property="password" styleClass="form-control" maxlength="20" styleId="password_tf"/>
            </div>
            <div class="left-content rightinputs">
              <label>Photo Address ID Proof *</label>
              <html:text property="password" styleClass="form-control" maxlength="20" styleId="password_tf"/>
              <div>
              <label>Document NO(A) *</label>
              <html:text property="password" styleClass="form-control" maxlength="20" styleId="password_tf"/>
               </div>
               <div>
               <label>Date of Issue(A) *</label>
               <html:text property="dateOfBirth" styleClass="dobdatePicker" readonly="true" styleId="date_of_birth_dp"/>
               </div>
              <label>Place of Issue(A) *</label>
              <html:text property="password" styleClass="form-control" maxlength="20" styleId="password_tf"/>
              <label>Issuing Authority(A Address) *</label>
              <html:text property="password" styleClass="form-control" maxlength="20" styleId="password_tf"/>
              <label>PAN/GIR/UID Number</label>
              <html:text property="password" styleClass="form-control" maxlength="20" styleId="password_tf"/>
            </div>
          </div>
        </div>
        <div class="divstyleright">
          <div class="divheading2" id="flip2">
            <bean:message key="label.person.details" />
            <i class="fa fa-chevron-down downarrow"></i>
          </div>
          <div class="divcontent2 caf-form-divcontent2" id="panel2">
            <div class="left-content">
              <div>
                <label>Gender *</label>
                <html:radio property="gender" value="M"  styleClass="genderRadioButtons" styleId="rdbMale">
                  <bean:message key="label.male" />
                </html:radio>
                <html:radio property="gender" value="F" styleClass="genderRadioButtons" styleId="rdbFemale">
                  <bean:message key="label.female" />
                </html:radio>
              </div>
              <label class="space-for-gendel">
                Title *
              </label>
              <html:select property="bloodGroup" styleId="blood_group_dd">
                <html:option value=""><bean:message key="label.please.select" /></html:option>
                <html:option value="1"><bean:message key="label.blood.group.o+ve" /></html:option>
                <html:option value="2"><bean:message key="label.blood.group.o-ve" /></html:option>
                <html:option value="3"><bean:message key="label.blood.group.ab+ve" /></html:option>
                <html:option value="4"><bean:message key="lable.blood.group.a" /></html:option>
                <html:option value="5"><bean:message key="lable.blood.group.b" /></html:option>
              </html:select>
              <label><bean:message key="label.first.name" />*</label>
              <html:text property="name" styleId="first_name_tf" maxlength="150" />
              <label><bean:message key="label.last.name" />*</label>
              <html:text property="lastName" styleId="last_name_tf" maxlength="100" />
              <label>Middle Name</label>
              <html:text property="lastName" styleId="last_name_tf" maxlength="100" />
            </div>
            <div class="left-content rightinputs">
              <label><bean:message key="label.date.of.birth" />*</label>
              <html:text property="dateOfBirth" styleClass="dobdatePicker" readonly="true" styleId="date_of_birth_dp"/>
              <label>
                <bean:message key="label.marital.status" />
              </label>
              <html:select property="maritalStatus" styleId="marital_status_dd">
                <html:option value="0"><bean:message key="label.please.select" /></html:option>
                <html:option value="1"><bean:message key="label.marital.status.married" /></html:option>
                <html:option value="2"><bean:message key="label.marital.status.married.with.children" /></html:option>
                <html:option value="3"><bean:message key="label.marital.status.no.children.engaged" /></html:option>
                <html:option value="4"><bean:message key="label.marital.status.single" /></html:option>
                <html:option value="5"><bean:message key="label.marital.status.never.married" /></html:option>
                <html:option value="6"><bean:message key="label.marital.status.formerly.married" /></html:option>
              </html:select>
              <label> Nationality * </label>
              <html:text property="name" styleId="first_name_tf" maxlength="150" />
              <label> Father / Husband Name * </label>
              <html:text property="name" styleId="first_name_tf" maxlength="150" />
              <label> Language * </label>
              <html:text property="name" styleId="first_name_tf" maxlength="150" />
            </div>
          </div>
          <div class="divheading3" id="flip3">
            Address Details (PA)
            <i class="fa fa-chevron-down downarrow"></i>
          </div>
          <div class="divcontent2 caf-form-divcontent2" id="panel3">
            <div class="left-content">
              <label>House Number PA *</label>
              <html:text property="city" styleId="city_tf" maxlength="50" />
              <label>Confirm House Number PA *</label>
              <html:text property="city" styleId="city_tf" maxlength="50" />
              <label>Street Address PA *</label>
              <html:text property="district" styleId="district_tf" maxlength="50" />
              <label>Confirm Street Address PA *</label>
              <html:text property="district" styleId="district_tf" maxlength="50" />
              <label><bean:message key="label.country" /> *</label>
              <html:select property="country" disabled="true" styleId="country_dd">
                <html:option value="1">India</html:option>
              </html:select>
            </div>
            <div class="left-content rightinputs">
              <label>Locality PA *</label>
              <html:text property="district" styleId="district_tf" maxlength="50" />
              <label>Confirm Locality PA *</label>
              <html:text property="district" styleId="district_tf" maxlength="50" />
              <label>City PA *</label>
              <html:text property="district" styleId="district_tf" maxlength="50" />
              <label>State PA *</label>
              <html:text property="district" styleId="district_tf" maxlength="50" />
              <label>Pin Code PA *</label>
              <html:text property="pinCode" styleId="pincode_tf" maxlength="6" />
            </div>
          </div>
          <div class="divheading6" id="flip4">
            Local Reference Details
            <i class="fa fa-chevron-down downarrow"></i>
          </div>
          <div class="divcontent6 caf-form-divcontent6" id="panel4">
            <div class="left-content">
              <label>Local Reference Address</label>
              <html:text property="userName" styleId="userName" maxlength="32"/>
              <label>Local Reference Name</label>
              <html:text property="userName" styleId="userName" maxlength="32"/>
              <label>Local Reference FirstName</label>
              <html:text property="dateOfBirth" styleClass="dobdatePicker" readonly="true" styleId="date_of_birth_dp"/>
              <label>Local Reference MiddleName</label>
              <html:text property="userName" styleId="userName" maxlength="32"/>
              <label>Local Reference LastName</label>
              <html:text property="password" styleClass="form-control" maxlength="20" styleId="password_tf"/>
              <label>Local Reference Phone Number</label>
              <html:text property="password" styleClass="form-control" maxlength="20" styleId="password_tf"/>
            </div>
            <div class="left-content rightinputs">
              <label>Local Referee Address</label>
              <html:text property="password" styleClass="form-control" maxlength="20" styleId="password_tf"/>
              <div>
              <label>Local Reference Done Call Status</label>
              <html:text property="password" styleClass="form-control" maxlength="20" styleId="password_tf"/>
               </div>
               <div>
               <label>Name of Local Reference Contacted by POS</label>
               <html:text property="dateOfBirth" styleClass="dobdatePicker" readonly="true" styleId="date_of_birth_dp"/>
               </div>
              <label>Date of Call</label>
              <html:text property="password" styleClass="form-control" maxlength="20" styleId="password_tf"/>
              <label>Time of Call</label>
              <html:text property="password" styleClass="form-control" maxlength="20" styleId="password_tf"/>
              <label>Name of Tele Caller</label>
              <html:text property="password" styleClass="form-control" maxlength="20" styleId="password_tf"/>
            </div>
          </div>
        </div>
        <div class="bottombuttons">
          <a href="<%=request.getContextPath()%>/dataEntry.do?method=showDEODashboardPage&actionPerformed=save" title="Save" class="btn btn-primary">
            Save
          </a>
          <html:reset value="Reset" styleClass="resetAddUserForm btn btn-primary" styleId="user_runner_add_reset_btn"/>
          <a href="<%=request.getContextPath()%>/dataEntry.do?method=showDEODashboardPage" title="Cancel" class="btn btn-primary">
            <bean:message key="label.cancel" />
          </a>
        </div>
      </div>
      <input type="hidden" name="<%=Constants.TOKEN_KEY%>" value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>" />
    </html:form>
  </div>
</body>