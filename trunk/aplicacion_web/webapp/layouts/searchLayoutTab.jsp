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
<TABLE class="searchform" border="0" cellpadding="0" cellspacing="0" valign="top">
<TR> 
    <TR> 
		<TD align="left" valign="top"><tiles:insert attribute="tabs"/></TD>
		<td  class="borderTab">&nbsp;</td>
	</TR>
	<TR> 
    	<TD colspan="2" align="left" valign="top"> 
       		<table cellpadding="0" cellspacing="0" style="border:1px solid #cccccc; border-top:none;" >
				<tr>	
					<td colspan="2"  class="tabAct"><%=retVal%></td>
				</tr>
				<TR> 
					<TD  colspan="2" align="left" valign="top"><h1 class="formtitle"><bean:message key="SearchLayout.criteria"/></h1></TD> 
				</TR> 
				<tr>
					<td colspan="2">
						<table width="100%"  cellpadding="0" border="0" cellspacing="0">
							<tr>
								<td>&nbsp;</td>
								<td>
									<html:hidden property="VirtualDispatchName"/>
									<html:hidden property="collectionName"/>
									<html:hidden property="dsTable" />
									<tiles:insert attribute="criteria"/>
								</td>
							</tr>
							<tr>
								<td height="20"></td>
							</tr>
							<tr>
								<td colspan="2"><tiles:insert attribute="buttons"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<TR> 
					<TD colspan="2"  align="left" valign="top">
						<h1 class="formtitle"><bean:message key="SearchLayout.result"/></h1>
					</TD> 
				</TR> 
				
				<TR> 	
					<td width="24px">&nbsp;</td>
					<Th align="left" valign="top"> 
						<tiles:insert attribute="result"/>    
					</Th> 
				</TR> 
			</table>			
    	</TD> 
	</TR> 
	<TR> 
		<TD align="left" valign="top"></TD> 
	</TR> 
</TABLE>
</html:form> 