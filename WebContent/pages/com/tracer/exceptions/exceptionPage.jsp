<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@page import="com.tracer.common.Constants"%>
<%@page import="org.apache.struts.Globals"%>
<!DOCTYPE html>
<html>
<head>
<link rel='shortcut icon' type='image/x-icon' href='<%=request.getContextPath()%>/images/tracer_favicon.ico' />
</head>
<body>
	<div class="clear"></div>
	<div id="form-content">
		<div id="titleheading">
			<div class="navtitle1">Exception Page</div>
			<br />
			<div id="titleheadingbar">
				<div class="navtitle">Exception</div>
			</div>
			<div class="invalidTokenBlock">
				<div class="divheading3">Exception Type</div>
				<div class="divcontent3" id="panel3">
					<div class="center">
						This is global exception handling page
						<div style="color: red;">
							<html:errors />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>