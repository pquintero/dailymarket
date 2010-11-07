<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>

<TABLE class="searchform" cellSpacing="0" cellPadding="6" border="0">
	<TR> 
		<TD colspan="2"> 
			<html:errors/>
		</TD>
	</TR> 
	<tr>
		<TH style="width:100px;padding-left:40px;"><bean:message key="GroupUserForm.name"/></TH>
	    <TD><html:text property="name"/></TD>
	</tr>
	<tr>
		<TH style="width:100px;padding-left:40px;"><bean:message key="GroupUserForm.description"/></TH>
	    <TD><html:text property="description"/></TD>
	</tr>	
</TABLE>

