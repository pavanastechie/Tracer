<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
</head>
<body>
  <div class="clear"></div>
  <div id="form-content">
    <div id="titleheading">
      <div class="navtitle1">Inactive Session</div>
      <br />
      <div id="titleheadingbar">
        <div class="navtitle">Inactive Session</div>
      </div>
      <div class="invalidTokenBlock">
        <div class="divheading3">Invalid Request</div>
        <div class="divcontent3" id="panel3">
          <div class="center">
            <form class="form-signin" role="form">
              Your session expired <br /> 
              <a href="<%=request.getContextPath()%>/login.do?method=showLogin" title="Login">
              <span style="color: blue;">Click here</span> </a> to login again.
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
  </body>
  </html>