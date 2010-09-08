<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>

<tiles:useAttribute name="title" classname="java.lang.String"/>

<%-- Layout component 
  parameters : title, header, menu, body, footer 
--%>
<html:html locale="true">
<%
	ResourceBundle resources = ResourceBundle.getBundle("ApplicationResources", (Locale)request.getSession().getAttribute("org.apache.struts.action.LOCALE"));	
	String retVal = null;
        	try {
	        	String s = title;				  		
				retVal = resources.getString(s);	
			} catch (Exception e) {
				retVal = title;
			}%>
<HEAD>
    <TITLE>.:: DataGen :: <%=retVal%> ::.</TITLE>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=iso-8859-1"/>
    <LINK href="site2.css" type="text/css" rel="stylesheet">
    <LINK href="dcc_calendar.css" type="text/css" rel="stylesheet">

	<script type="text/javascript" src="functions.js"></script>
	<script type="text/javascript" src="ieupdateV2.js"></script>
	<link rel="stylesheet" type="text/css" href="yui/fonts/fonts-min.css" /> 
	<link rel="stylesheet" type="text/css" href="yui/autocomplete/assets/skins/sam/autocomplete.css"/> 
	<link rel="stylesheet" type="text/css" href="yui/button/assets/skins/sam/button.css" />
	<link rel="stylesheet" type="text/css" href="yui/container/assets/skins/sam/container.css" />
	<link rel="stylesheet" type="text/css" href="yui/calendar/assets/skins/sam/calendar.css" />
	<script type="text/javascript" src="yui/yahoo-dom-event/yahoo-dom-event.js"></script>

	<script type="text/javascript" src="yui/connection/connection-min.js"></script> 
	<script type="text/javascript" src="yui/animation/animation-min.js"></script>
	<script type="text/javascript" src="yui/datasource/datasource-min.js"></script>
	<script type="text/javascript" src="yui/autocomplete/autocomplete-min.js"></script>
	<script type="text/javascript" src="assistant.js"></script>
	<script type="text/javascript" src="yui/dragdrop/dragdrop-min.js"></script>
	<script type="text/javascript" src="yui/element/element-min.js"></script>
	<script type="text/javascript" src="yui/button/button-min.js"></script>
	<script type="text/javascript" src="yui/container/container-min.js"></script>
	<script type="text/javascript" src="yui/calendar/calendar.js"></script>
	<script type="text/javascript" src="dcc_calendar.js"></script>
	<script type="text/javascript" src="pages/remittanceCertification/certification.js"></script>
	<script type="text/javascript" src="pages/remittanceCertification/downloadCertification.js"></script>
		
	<script type="text/javascript">
		setApplicationContext('<%=request.getRequestURL().substring(0,request.getRequestURL().indexOf(":")+3)%>' + '<%= request.getServerName() %>' + ":" + '<%= request.getServerPort()%>' +'<%= request.getRequestURI().substring(request.getRequestURI().indexOf("/") , request.getRequestURI().indexOf("l") )%>');
	</script>
</HEAD>

<BODY class="yui-skin-sam" onload="execOnLoad()">
	<table class="body"  align="center" cellSpacing="0" cellPadding="0" border="0">
		<tr>
			<td></td>
			<td>
			<!-- Header -->
			<tiles:insert attribute="header"/>
			
			<!-- Menu y body -->
			<TABLE  cellSpacing="0" align="center" cellPadding="0"  border="0">
			    <TBODY>
				<TR>
			        <!-- Margen Izquierdo -->
					
			        <!-- Separación entre el menu y el body -->
			        <!-- Body -->
					<TD valign="top" align="center">
						<table border="0" >
							<tr>
								<td><tiles:insert attribute="body"/></td>
							</tr>
						</table>
						
					</TD>
				</TR>
				<tr>
					<td height="20px"></td>
				</tr>
				</TBODY>
			</TABLE>
			
			<!-- Footer -->
			<tiles:insert attribute="footer"/>
			</td>
		</tr>
	</table>
</BODY>


<HEAD>
    <META HTTP-EQUIV="Expires" CONTENT="-1"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-store, no-cache, must-revalidate, post-check=0, pre-check=0"/>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache"/>  
</HEAD>
</html:html>
