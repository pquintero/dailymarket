<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>

<tiles:useAttribute name="action" classname="java.lang.String"/>
<tiles:useAttribute name="title" classname="java.lang.String"/>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
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

<TABLE class="body" border="0" cellpadding="0" cellspacing="0" >
<TR> 
    <TD  align="left" valign="top"> 
        <html:form action="<%=action%>">
            <html:hidden property="VirtualDispatchName"/>
            <html:hidden property="collectionName"/>
            <html:hidden property="id"/>
            <html:hidden property="saveToken"/>
            <tiles:insert attribute="body"/>
            <tiles:insert attribute="buttons"/>
        </html:form>    
    </TD> 
</TR>

</TABLE>
