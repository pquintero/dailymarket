<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>

<tiles:useAttribute name="action" classname="java.lang.String"/>
<tiles:useAttribute name="title" classname="java.lang.String"/>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>

<TABLE  class="body" align="center" border="0" cellpadding="0" cellspacing="0">
<TR> 
	<TD align="center" valign="top"> 
        <tiles:insert attribute="tabs"/>
    </TD> 
</TR> 
<TR> 
    <TD align="left" valign="top"> 
        <html:form action="<%=action%>">
            <html:hidden property="VirtualDispatchName"/>
            <html:hidden property="collectionName"/>
            <html:hidden property="id"/>
			<html:hidden property="saveToken"/>
			<html:hidden property="dsTable" />
            <tiles:insert attribute="body"/>
            <tiles:insert attribute="buttons"/>
        </html:form>    
    </TD> 
</TR> 
</TABLE>
