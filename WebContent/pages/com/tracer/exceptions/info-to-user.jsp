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
      <div class="navtitle1">Info / Error</div>
      <br />
      <div id="titleheadingbar">
        <div class="navtitle">Info / Error</div>
      </div>
      <div class="invalidTokenBlock">
        <div class="divheading3">Info / Error</div>
        <div class="divcontent3" id="panel3">
          <div class="center">
            <form class="form-signin" role="form">
              <logic:messagesPresent message="true">
                <ul>
                  <html:messages id="msg" message="true">
                   <li><bean:write name="msg" filter="false"/> </li>
                  </html:messages>
                </ul>
              </logic:messagesPresent>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>