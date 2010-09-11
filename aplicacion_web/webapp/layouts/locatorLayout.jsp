<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<tiles:useAttribute name="title" classname="java.lang.String"/>
<%-- Layout component 
  parameters : title, body
--%>
<html:html locale="true">


<HEAD>
	<%
	ResourceBundle resources = ResourceBundle.getBundle("ApplicationResources", (Locale)request.getSession().getAttribute("org.apache.struts.action.LOCALE"));	
	String retVal = null;
        	try {
	        	String s = title;				  		
				retVal = resources.getString(s);	
			} catch (Exception e) {
				retVal = title;
			}%>
    <TITLE>.:: DAILYMARKET :: <%=retVal%> ::.</TITLE>

    <META HTTP-EQUIV="Content-Type" content="text/html; charset=iso-8859-1"/>
    <META HTTP-EQUIV="Expires" CONTENT="-1">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-store, no-cache, must-revalidate">
    <META HTTP-EQUIV="Cache-Control" CONTENT="post-check=0, pre-check=0">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">

    <LINK href="site.css" type="text/css" rel="stylesheet">
</HEAD>

<BODY>
<div class="contenedor_locator">

<!-- body -->
<TABLE cellSpacing="0" align="center" cellPadding="0" class="locator_layout" border="0">
    <TBODY>
    <TR height="100%">
        <!-- Body -->
        <TD valign="top" align="center">
            <br>
			<tiles:insert attribute="body"/>
        </TD>
    </TR>
    </TBODY>
</TABLE>

</div>
</BODY>
</html:html>
