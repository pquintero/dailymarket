<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<TABLE class="form" cellSpacing="0" cellPadding="0" border="0">
<TR> 
    <TD colspan="7" align="left" valign="top"> 
		<h1 class="formtitle"><bean:message key="EditLayout.main"/></h1>
    </TD> 
</TR>
<TR> 
	<TD colspan="7"> 
		<html:errors/>
	</TD>
</TR> 

<TR> 
	<TH class="required"><bean:message key="GroupUserForm.name"/></TH>
	<TD><html:text property="name" size="30"  maxlength="20"/></TD>	
</TR>

<TR> 
	<TH class="required"><bean:message key="GroupUserForm.description"/></TH>
	<TD><html:text property="description" size="30"  maxlength="20"/></TD>	
</TR>
</TABLE>
