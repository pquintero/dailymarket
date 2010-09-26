<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<TABLE class="form" cellSpacing="0" cellPadding="6" border="0">
<TR> 
	<TD colspan="7"> 
		<html:errors/>
	</TD>
</TR> 
<html:hidden property="groupName"/>
<html:hidden property="createdBy" />
<html:hidden property="dateCreatedStr"/>
<html:hidden property="removed" />
<html:hidden property="dateRemovedDisplay"/>
<tr>
	<td height="20"></td>
</tr>
<TR> 
    <TH class="required"><bean:message key="UserForm.user"/></TH>
   	<TD colspan="8"><html:text property="user" maxlength="20" disabled="true" /></TD>
</TR>
<TR> 
    <TH class="required"><bean:message key="UserForm.passwordOld"/>&nbsp;(*)</TH>
    <TD colspan="8"><html:password property="passwordOld" /></TD>
</TR>
<TR> 
    <TH class="required"><bean:message key="UserForm.password"/>&nbsp;(*)</TH>
    <TD><html:password property="password" /></TD> 
    <TH class="required" ><bean:message key="UserForm.password2"/>&nbsp;(*)</TH>
    <TD colspan="5"><html:password property="password2" /></TD>
</TR>
</TABLE>
