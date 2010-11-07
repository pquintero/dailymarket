<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<TABLE class="form" cellSpacing="0" cellPadding="0" border="0">
<TR> 
    <TD colspan="2" align="left" valign="top"> 
		<h1 class="formtitle"><bean:message key="EditLayout.main"/></h1>
    </TD> 
</TR>
<tr>
	<td colspan="2">&nbsp;</td>
</tr>
<TR> 
	<TD colspan="2"> 
		<html:errors/>
	</TD>
</TR> 

<TR> 
	<TH style="width:100px;padding-left:40px;"><bean:message key="GroupUserForm.name"/></TH>
	<TD><html:text property="name" size="30"  maxlength="20"/></TD>	
</TR>
<tr>
	<td colspan="2">&nbsp;</td>
</tr>
	
<TR> 
	<TH style="width:100px;padding-left:40px;"><bean:message key="GroupUserForm.description"/></TH>
	<TD><html:text property="description" size="30"  maxlength="20"/></TD>	
</TR>
</TABLE>
