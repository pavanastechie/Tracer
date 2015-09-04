<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link rel='shortcut icon' type='image/x-icon' href='<%=request.getContextPath()%>/images/tracer_favicon.ico'/>
    <title><tiles:getAsString name="title" ignore="true" /></title>

    <link href="<%= request.getContextPath() %>/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="<%= request.getContextPath() %>/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
    <link href="<%= request.getContextPath() %>/css/jquery-ui.css" rel="stylesheet" type="text/css">
    <link href="<%= request.getContextPath() %>/css/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css">
    <link href="<%= request.getContextPath() %>/css/font-awesome/css/endless.css" rel="stylesheet" type="text/css">
    <link href="<%= request.getContextPath() %>/css/tracer.css" rel="stylesheet" type="text/css">
    <link href="<%= request.getContextPath() %>/js/jChartFX/styles/jchartfx.css" rel="stylesheet" type="text/css">
    <link href="<%= request.getContextPath() %>/css/dataTables/TableTools_JUI.css" rel="stylesheet" type="text/css">
    <link href="<%= request.getContextPath() %>/css/dataTables/jquery.dataTables.css" rel="stylesheet" type="text/css">
    <link href="<%= request.getContextPath() %>/css/dataTables/table_jui.css" rel="stylesheet" type="text/css">
    <link href="<%= request.getContextPath() %>/css/dataTables/table.css" rel="stylesheet" type="text/css">
    <link href="<%= request.getContextPath() %>/css/dataTables/page.css" rel="stylesheet" type="text/css">
    <link href="<%= request.getContextPath() %>/css/notifIt.css" rel="stylesheet" type="text/css">
    <link href="<%= request.getContextPath() %>/css/main.css" rel="stylesheet" type="text/css">
    <link href="<%= request.getContextPath() %>/css/jquery.ui.timepicker.css" rel="stylesheet" type="text/css">

    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/notifIt.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/underscore-min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/dataTables/jquery.dataTables.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/dataTables/TableTools.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/dataTables/TableTools.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/dataTables/ZeroClipboard.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/app-util.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/dashboard-util.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jChartFX/js/jchartfx.system.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jChartFX/js/jchartfx.coreVector.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jChartFX/js/jchartfx.advanced.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jChartFX/js/jchartfx.animation.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jChartFX/js/jchartfx.funnel.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jChartFX/js/jchartfx.pyramid.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jChartFX/js/jchartfx.coreVector3d.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jChartFX/js/jchartfx.data.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jChartFX/js/jchartfx.treemap.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jChartFX/js/jchartfx.overlaybubble.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jChartFX/js/jchartfx.rose.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.ui.timepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/google-org-chart/jsapi"></script>
  </head>
  <body>
    <!-- header comes here -->
    <tiles:insert attribute="header"/>
    
    <!-- main body comes here-->
    <tiles:insert attribute="body" flush="true"  /> 
    
    <!-- footer comes here-->
    <tiles:insert attribute="footer" flush="true"  />
  </body>
</html>