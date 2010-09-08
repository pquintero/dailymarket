<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
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

<html:form action="<%=action%>">			
<TABLE class="body" border="0" align="center" width="800" cellpadding="0" cellspacing="0">		
		<tr>
			 <TD align="left"> 
					<table class="body" cellpadding="0" align="left" width="800" cellspacing="0" style="border:1px solid #cccccc; border-top:none;" >
						<TR> 
							<TD align="left" valign="top">
								<h1 class="formtitle"><bean:message key="SearchLayout.criteria"/></h1>
							</TD> 
						</TR> 
						<TR> 
							<TD align="left"> 
							   
								<html:hidden property="VirtualDispatchName"/>
								<html:hidden property="collectionName"/>
								<html:hidden property="dsTable" />
									
								<tiles:insert attribute="criteria"/>
								<tiles:insert attribute="buttons"/>
							</TD> 
						</TR> 
						<TR> 
							<TD align="left" valign="top">
								<h1 class="formtitle"><bean:message key="SearchLayout.result"/></h1>
							</TD> 
						</TR> 
						<TR> 	
							<Th align="center" valign="top"> 
								<tiles:insert attribute="result"/>    
							</Th> 
						</TR>
						<TR> 
							<TD align="center" valign="top"></TD> 
						</TR> 
					</table>
				 </td>
 			 </tr>
</TABLE>
</html:form> 