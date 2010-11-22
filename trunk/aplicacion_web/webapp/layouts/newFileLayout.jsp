<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<tiles:useAttribute name="title" classname="java.lang.String"/>
<%
	ResourceBundle resources = ResourceBundle.getBundle("ApplicationResources", (Locale)request.getSession().getAttribute("org.apache.struts.action.LOCALE"));	
	String retVal = null;
        	try {
	        	String s = title;				  		
				retVal = resources.getString(s);	
			} catch (Exception e) {
				retVal = title;
			}%>

<tiles:useAttribute name="action" classname="java.lang.String"/>

<TABLE class="body" border="0" cellpadding="0" cellspacing="0" valign="top">
<TR> 
    <TD align="left" valign="top"> 
		<h1 class="formtitle"><%=retVal%></h1>
    </TD> 
</TR> 
<TR> 
    <TD align="left" valign="top"> 
        <html:form action="<%=action%>" enctype="multipart/form-data">
            <html:hidden property="VirtualDispatchName"/>
            <html:hidden property="collectionName"/>
            <html:hidden property="id"/>

            <tiles:insert attribute="body"/>
            <tiles:insert attribute="buttons"/>
        </html:form>    
    </TD> 
</TR> 
</TABLE>
