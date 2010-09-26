<%@ page language="java" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>

<tiles:useAttribute name="action" classname="java.lang.String"/>
<tiles:useAttribute name="title" classname="java.lang.String"/>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>

<html:form action="<%=action%>">
	<%
	ResourceBundle resources = ResourceBundle.getBundle("ApplicationResources", (Locale)request.getSession().getAttribute("org.apache.struts.action.LOCALE"));	
	String retVal = null;
        	try {
	        	String s = title;				  		
				retVal = resources.getString(s);	
			} catch (Exception e) {
				retVal = title;
			}
	%>
	
	<html:hidden property="VirtualDispatchName"/>
	<html:hidden property="collectionName"/>
	<html:hidden property="dsTable"/>

	<tiles:insert attribute="body"/>
	<tiles:insert attribute="buttons"/>
	
</html:form>  
