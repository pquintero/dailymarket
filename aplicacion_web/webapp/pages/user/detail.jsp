<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<TABLE class="form" cellSpacing="0" cellPadding="6" border="0">
	<TR> 
	    <TD colspan="4" align="left" valign="top"> 
			<h1 class="formtitle"><bean:message key="EditLayout.main"/></h1>
	    </TD> 
	</TR>
	<TR> 
		<TD colspan="4"> 
			<html:errors/>
		</TD>
	</TR> 
	
	<TR> 
		<TH style="width:100px;padding-left:40px;"><bean:message key="UserForm.lastName"/></TH>
		<TD width="100px;"><html:text property="lastName" size="30"  maxlength="20"/></TD>			
	
		<TH style="width:100px;padding-left:40px;"><bean:message key="UserForm.name"/></TH>
		<TD><html:text property="name" size="30"  maxlength="20"/></TD>	
	</TR>
	
	<TR> 
		<TH style="width:100px;padding-left:40px;"><bean:message key="UserForm.user"/></TH>
		<TD width="100px;"><html:text property="user" size="30"  maxlength="20"/></TD>
	
		<TH style="width:100px;padding-left:40px;"><bean:message key="UserForm.password"/></TH>
		<TD><html:text property="password" size="30"  maxlength="20"/></TD>			
	</TR>
	
	<TR> 
		<TH style="width:100px;padding-left:40px;"><bean:message key="UserForm.dni"/></TH>
		<TD><html:text property="dni" size="30"  maxlength="20"/></TD>	
	
		<TH style="width:100px;padding-left:40px;"><bean:message key="UserForm.groupUser"/></TH>
		<TD>
			<html:select property="groupUserId">						
				<html:options collection="groupsUsers" property="id" labelProperty="name" />
			</html:select>
		</TD>
	</TR>
</TABLE>
