<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>

<TABLE class="searchform" cellSpacing="0" cellPadding="6" border="0">
	<TR> 
		<TD colspan="4"> 
			<html:errors/>
		</TD>
	</TR> 
	<tr>
		<TH style="width:100px;padding-left:40px;" ><bean:message key="UserForm.id"/></TH>
	    <TD width="100px;"><html:text property="idStr"/></TD>
	       
		<TH style="width:100px;padding-left:40px;" ><bean:message key="UserForm.user"/></TH>
		<TD ><html:text property="user"/></TD>
	</tr>
	<tr> 
		<TH style="width:100px;padding-left:40px;" ><bean:message key="UserForm.dni"/></TH>
		<TD width="100px;"><html:text property="dni"/></TD>
	        
		<TH style="width:100px;padding-left:40px;" ><bean:message key="UserForm.lastName"/></TH>
		<TD ><html:text property="lastName"/></TD>
	</tr>
	<tr>
		<TH style="width:100px;padding-left:40px;" ><bean:message key="UserForm.name"/></TH>
		<TD width="100px;"><html:text property="name"/></TD>
	        
		<TH style="width:100px;padding-left:40px;"><bean:message key="UserForm.groupUser"/></TH>
		<TD >
			<html:select property="groupUserId">	
				<OPTION VALUE="-1">Seleccione</OPTION>					
				<html:options collection="groupsUsers" property="id" labelProperty="name" />
			</html:select>
		</TD>
	</TR>		
</TABLE>

