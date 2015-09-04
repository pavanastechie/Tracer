<%@page import="com.tracer.common.Constants"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">

<!-- Function to prevent back click!>
function noBack() { window.history.forward(); }
noBack();
window.onload = noBack;
window.onpageshow = function (evt) { if (evt.persisted) noBack(); }
window.onunload = function () { void (0); }
</script>
</head>
<body>
<div id="dashboard-top">
<div class="logoPlaceHolder"><a id="home_hl" title="TraceR" alt ="TraceR" role="button" href="<%=request.getContextPath()%>/dashboard.do?method=showDashboard" ><img src="images/tracer_logo.png"></a></div>
<%long roleId = 0L;
  if(request.getSession().getAttribute(Constants.ROLE_ID) != null) {
    roleId = (Long) request.getSession().getAttribute(Constants.ROLE_ID);
  }
%>
<%if(roleId == Constants.SYSTEM_ADMIN) { %>
<div id="main-menu" class="main-menu1">
<%} else if(roleId == Constants.DEO) { %>
<div id="main-menu" class="main-menu2">
<%} else {%>
<div id="main-menu">
<%} %>
  <ul class="nav nav-pills">
    <li class="dropdown"><a id="drop1" title="Home" alt = "Home" role="button" href="<%=request.getContextPath()%>/dashboard.do?method=showDashboard" class="shortcut-link3">
      <i class="fa fa-home"></i></a>
    </li>
    <%if(roleId != Constants.SYSTEM_ADMIN && roleId != Constants.DEO) { %>
    <li class="dropdown"><a id="drop4" title="Reports" alt="Reports" role="button" href="<%=request.getContextPath()%>/reports.do?method=showReportsDashboard" class="shortcut-link">
      <i class="fa fa-clipboard"></i>
      </a>
    </li>
    <%} %>
    
    <%if(roleId != Constants.DEO) { %>
    <li class="dropdown">
      <a id="drop5" role="button" data-toggle="dropdown" href="#" class="shortcut-link1" title="Manage" alt = "Manage">
      <i class="fa fa-cogs"></i> 
      <b class="caret"></b>
      </a>
      <ul id="menu2" class="dropdown-menu pull-right" role="menu" aria-labelledby="drop5">
      <li class="dropdown-submenu menu-icon-manageheader">&nbsp;
      </li>
        <%if(roleId == Constants.SYSTEM_ADMIN || roleId == Constants.CIRCLE_MANAGER) { %>
        <li class="dropdown-submenu">
          <a id="circles_hl" tabindex="-1" href="#" title="Circles" alt="Circles"><i class="fa fa-dot-circle-o menu-icon-blue circle-icon-padding" ></i>Circles</a>
          <ul class="dropdown-menu circles-dropdown-menu">
            <%if(roleId == Constants.SYSTEM_ADMIN) { %>
            <li><a id="circles_add_hl" href="<%=request.getContextPath()%>/circles.do?method=showNewAddCirclePage" title="Add Circles" alt="Add Circles"><i class="fa fa-plus menu-icon-blue"></i>Add</a></li>
            <%} %>
            <%if(roleId == Constants.SYSTEM_ADMIN || roleId == Constants.CIRCLE_MANAGER) { %>
            <li><a id="circles_manage_hl" href="<%=request.getContextPath()%>/circles.do?method=showManageCirclesPage" title="Manage Circles" alt="Manage Circles"> <i class="fa fa-pencil menu-icon-blue"></i>Manage</a></li>
             <%} %>
          </ul>
        </li>
        <li role="presentation" class="divider"></li>
        <%} %>
        <%if(roleId == Constants.SYSTEM_ADMIN || roleId == Constants.CIRCLE_MANAGER || roleId == Constants.REGION_MANAGER || roleId == Constants.ZONE_MANAGER || roleId == Constants.HUB_MANAGER) { %>
        <li class="dropdown-submenu">
          <a id="users_hl" tabindex="-1" href="#" title="Users" alt="Users"><i class="fa fa-user menu-icon-blue users-icon-padding"></i>Users</a>
          <%if(roleId == Constants.CIRCLE_MANAGER || roleId == Constants.REGION_MANAGER) { %>
          <ul class="dropdown-menu users-dropdown-menu-circle-manager">
          <%} %>
          <%if(roleId == Constants.SYSTEM_ADMIN  || roleId == Constants.HUB_MANAGER) { %>
          <ul class="dropdown-menu users-dropdown-menu">
          <%} %>
          <%if(roleId == Constants.ZONE_MANAGER ) { %>
          <ul class="dropdown-menu users-dropdown-menu-zone-manager">
          <%} %>
          
            <%if(roleId == Constants.SYSTEM_ADMIN || roleId == Constants.HUB_MANAGER) { %>
            <li><a id="users_add_runner_hl" href="<%=request.getContextPath()%>/user.do?method=showAddRunnerPage" title="Add Runner" alt="Add Runner"><i class="fa fa-plus menu-icon-blue"></i>Add Runner</a></li>
            <li><a id="users_add_tl_hl" href="<%=request.getContextPath()%>/user.do?method=showAddTeamLeaderPage" title="Add Team Leader" alt="Add Team Leader"><i class="fa fa-plus menu-icon-blue"></i>Add Team Leader</a></li>
            <%} %>
            <%if(roleId == Constants.SYSTEM_ADMIN || roleId == Constants.CIRCLE_MANAGER || roleId == Constants.REGION_MANAGER || roleId == Constants.ZONE_MANAGER) { %>
            <li><a id="users_add_manager1_hl" href="<%=request.getContextPath()%>/user.do?method=showAddManagerPage" title="Add Manager" alt="Add Manager"><i class="fa fa-plus menu-icon-blue"></i>Add Manager</a></li>
            <%} %>
            <%if(roleId == Constants.SYSTEM_ADMIN || roleId == Constants.CIRCLE_MANAGER || roleId == Constants.REGION_MANAGER || roleId == Constants.ZONE_MANAGER || roleId == Constants.HUB_MANAGER) { %>
            <li><a id="users_add_manager2_hl" href="<%=request.getContextPath()%>/user.do?method=showManageUsersPage" title="Manage" alt="Manage"><i class="fa fa-pencil menu-icon-blue"></i>Manage</a></li>
            <%} %>
          </ul>
        </li>
        <li role="presentation" class="divider"></li>
        <%} %>
        <%if(roleId == Constants.SYSTEM_ADMIN || roleId == Constants.HUB_MANAGER) { %>
        <li class="dropdown-submenu">
          <a id="beat_plans_hl" tabindex="-1" href="#" title="Beat Plans" alt="Beat Plans"><i class="fa fa-list menu-icon-blue beat-plans-padding"></i>Beat Plans</a>
          <ul class="dropdown-menu beatplans-dropdown-menu">
             <%if(roleId == Constants.SYSTEM_ADMIN) { %>
            <li><a id="beat_plans_distributors_add_hl" href="<%=request.getContextPath()%>/beatPlans.do?method=showAddDistributorBeatPlanPage" title="Add Distributor's" alt="Add Distributor's"><i class="fa fa-plus menu-icon-blue"></i>Add Distributor's</a></li>
            <%} %>
            <li><a id="beat_plans_runners_add_hl" href="<%=request.getContextPath()%>/beatPlans.do?method=showAddRunnerBeatPlanPage" title="Add Runner's" alt="Add Runner's"><i class="fa fa-plus menu-icon-blue"></i>Add Runner's</a></li>
            <li><a id="beat_plans_manage_distributor_bp_hl" href="<%=request.getContextPath()%>/beatPlans.do?method=showManageDistributorBeatPlansPage" title="Manage" alt="Manage"><i class="fa fa-pencil menu-icon-blue"></i>Manage Distributor Beat Plan</a></li>
            <li><a id="beat_plans_manage_runner_bp_hl" href="<%=request.getContextPath()%>/beatPlans.do?method=showManageRunnerBeatPlansPage" title="Manage" alt="Manage"><i class="fa fa-pencil menu-icon-blue"></i>Manage Runner Beat Plan</a></li>
             <%if(roleId == Constants.SYSTEM_ADMIN) { %>
            <li><a id="beat_plan_bulk_upload_hl" href="<%=request.getContextPath()%>/beatPlanBulkUpload.do?method=showBeatPlanBulkUploadPage" title="Beat Plan Bulk Upload" alt="Beat Plan Bulk Upload"><i class="fa fa-upload menu-icon-blue"></i>Bulk Upload</a></li>
            <%} %>
          </ul>
        </li>
        <li role="presentation" class="divider"></li>
        <%} %>
        <%if(roleId == Constants.HUB_MANAGER || roleId == Constants.TEAM_LEADER) { %>
        <li class="dropdown-submenu">
          <a id="track_hl" tabindex="-1" href="#" title="Track" alt="Track"><i class="fa fa-search menu-icon-blue track-icon-padding"></i>Track</a>
          <ul class="dropdown-menu ">
            <%if(roleId == Constants.TEAM_LEADER) { %>
            <li><a id="track_runners_hl" href="<%=request.getContextPath()%>/track.do?method=showTrackRunnersPage" title="Runners" alt="Runners"><i class="fa fa-male menu-icon-blue"></i>Runners</a></li>
            <%} %>
            <%if(roleId == Constants.HUB_MANAGER) { %>
            <li><a id="track_teamleaders_hl" href="<%=request.getContextPath()%>/track.do?method=showTrackTeamLeadersPage" title="Team Leaders" alt="Team Leaders"><i class="fa fa-male menu-icon-blue"></i>Team Leaders</a></li>
            <%} %>
          </ul>
        </li>
         <li role="presentation" class="divider"></li>
        <%} %>
        <%if(roleId == Constants.SYSTEM_ADMIN) { %>
        <li class="dropdown-submenu">
          <a id="distributor_hl" tabindex="-1" href="#" title="Distributors" alt="Distributors"><i class="fa fa-map-marker menu-icon-blue distributors-icon-padding"></i>Distributors</a>
          <ul class="dropdown-menu distributors-dropdown-menu">
            <li><a id="distributor_add_hl" href="<%=request.getContextPath()%>/distributor.do?method=showAddDistributorPage" title="Add Distributors" alt="Add Distributors"><i class="fa fa-plus menu-icon-blue"></i>Add</a></li>
            <li><a id="distributor_manage_hl" href="<%=request.getContextPath()%>/distributor.do?method=showManageDistributorsPage" title="Manage Distributors" alt="Manage Distributors"><i class="fa fa-pencil menu-icon-blue"></i>Manage</a></li>
          </ul>
        </li>
        <li role="presentation" class="divider"></li>
        <%} %>
        <%if(roleId == Constants.SYSTEM_ADMIN) { %>
        <li class="dropdown-submenu">
          <a id="notifications_hl" tabindex="-1" href="#" title="Notifications" alt="Notifications"><i class="fa fa-briefcase menu-icon-blue notifications-icon-padding"></i>Notifications</a>
          <ul class="dropdown-menu notifications-dropdown-menu">
            <li class="dropdown-submenu">
              <a id="notifications_sms_templates_hl" href="#" title="SMS Templates" alt="SMS Templates"><i class="fa fa-envelope menu-icon-blue"></i>SMS Templates</a>
              <ul class="dropdown-menu smstemplates-dropdown-menu">
                <%-- <li><a id="notifications_sms_templates_add_hl" href="<%=request.getContextPath()%>/notification.do?method=showAddSMSTemplate" title="Add" alt="Add"><i class="fa fa-plus menu-icon-blue"></i>Add</a></li> --%>
                <li><a id="notifications_sms_templates_manage_hl" href="<%=request.getContextPath()%>/notification.do?method=manageSmsTemplate" title="Manage" alt="Manage"><i class="fa fa-pencil menu-icon-blue"></i>Manage</a></li>
              </ul>
            </li>
          </ul>
        </li>
         <li role="presentation" class="divider"></li>
        <%} %>
        
      <%if(roleId == Constants.SYSTEM_ADMIN || roleId == Constants.HUB_MANAGER || roleId == Constants.TEAM_LEADER) { %>
      <li class="dropdown-submenu">
          <a id="upload_hl" tabindex="-1" href="#" title="Upload" alt="Upload"><i class="fa fa-upload menu-icon-blue distributors-icon-padding"></i>Upload</a>
          <ul class="dropdown-menu upload-dropdown-menu">
            <li><a id="upload_online_cafs_hl" href="<%=request.getContextPath()%>/upload.do?method=showUploadCAFsPage" title="Upload online CAFs" alt="Upload online CAFs"><i class="fa fa-upload menu-icon-blue"></i>Upload online CAFs</a></li>
            <li><a id="upload_online_cafs_view_hl" href="<%=request.getContextPath()%>/upload.do?method=showUploadedCAFs" title="View CAFs uploaded online" alt="View CAFs uploaded online"><i class="fa fa-eye menu-icon-blue"></i>View CAFs uploaded online</a></li>
          </ul>
        </li>
         <li role="presentation" class="divider"></li>
         <%} %>
        <li class="dropdown-submenu menu-icon-managefooter">&nbsp;
      </li>
      </ul>
    </li>
    <%} %>
    <li class="dropdown">
      <a id="profile_hl" id="drop6" role="button" data-toggle="dropdown" href="#" class="shortcut-link" title="Profile" alt="Profile">
      <i class="fa fa-users"></i>
      <b class="caret"></b>
      </a>
      <ul id="menu3" class="dropdown-menu pull-right" role="menu" aria-labelledby="drop6">
      <li class="dropdown-submenu menu-icon-profileheader">&nbsp;
      </li>
        <li role="presentation"><a id="profile_edit_hl" role="menuitem" tabindex="-1" href="<%=request.getContextPath()%>/user.do?method=showEditProfilePage" title="Edit Profile" alt="Edit Profile"><i class="fa fa-cog menu-icon-green"></i>Edit Profile</a></li>
        <li role="presentation" class="divider"></li>
        <li role="presentation"><a id="profile_change_password_hl" role="menuitem" tabindex="-1" href="<%=request.getContextPath()%>/user.do?method=showChangePasswordPage" title="Change Password" alt="Change Password"><i class="fa fa-wrench menu-icon-green"></i>Change Password</a></li>
        <li role="presentation" class="divider"></li>
        <li role="presentation"><a id="profile_sign_out_hl" role="menuitem" tabindex="-1" href="<%=request.getContextPath()%>/login.do?method=logoutUser" title="Logout" alt="Logout"><i class="fa fa-sign-out menu-icon-green"></i>Logout <span class="adminfont"> <%=request.getSession().getAttribute(Constants.USER_NAME)%></span></a></li>
        <li class="dropdown-submenu menu-icon-profilefooter">&nbsp;
      </li>
      </ul>
    </li>
  </ul>
  <!-- /tabs -->
</div></div>
</body>
</html>