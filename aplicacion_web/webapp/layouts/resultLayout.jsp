<%@ page language="java" %>

<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<tiles:useAttribute name="title" classname="java.lang.String"/>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<tiles:useAttribute name="action" classname="java.lang.String"/>

<TABLE   border="0" cellpadding="0" cellspacing="0" valign="top">
<%
	ResourceBundle resources = ResourceBundle.getBundle("ApplicationResources", (Locale)request.getSession().getAttribute("org.apache.struts.action.LOCALE"));	
	String retVal = null;
        	try {
	        	String s = title;				  		
				retVal = resources.getString(s);	
			} catch (Exception e) {
				retVal = title;
			}%>

<TR> 
    <TD  align="left" valign="top"> 
		<h1 class="formtitle"><%=retVal%></h1>
    </TD> 
</TR>
<TR> 
    <TD align="left" valign="top"> 
        <html:form action="task">
          <html:hidden property="VirtualDispatchName"/>
          <html:hidden property="collectionName"/>
			<html:hidden property="dsTable" />
			<tiles:insert attribute="list"/>
			<tiles:insert attribute="buttons"/>
        </html:form>    
	</TD> 
</TR>

</TABLE>
