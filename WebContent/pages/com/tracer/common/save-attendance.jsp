<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<div class="clear"></div>
<div id="form-content">
  <div id="titleheading">
    <div class="navtitle1">Attendance</div>
    <br />
    <div id="titleheadingbar">
      <div class="navtitle"><i class="fa fa-dashboard"></i>  Save Attendance</div>
    </div>
    <div>
    
    </div>
    <div class="attendencebtn">
        <a href="<%=request.getContextPath()%>/login.do?method=saveAttendance" title="Save Attendance" class="btn btn-primary">
        <i class="fa fa-check-square-o"></i> <bean:message key="label.save.attendance"/></a>
      </div>
  </div>
</div>