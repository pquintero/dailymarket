<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<TABLE class="form" cellSpacing="0" cellPadding="6" border="0">
	<TR> 
	    <TD colspan="2" align="left" valign="top"> 
			<h1 class="formtitle"><bean:message key="EditLayout.main"/></h1>
	    </TD> 
	</TR>
	<TR> 
		<TD colspan="2"> 
			<html:errors/>
		</TD>
	</TR> 
	
	<TR> 
		<TH style="width:100px;padding-left:40px;"><bean:message key="GroupProductForm.name"/></TH>
		<TD><html:text property="name" size="30"  maxlength="20"/></TD>	
	</TR>
	
	<TR> 
		<TH style="width:100px;padding-left:40px;"><bean:message key="GroupProductForm.description"/></TH>
		<TD><html:text property="description" size="30"  maxlength="20"/></TD>	
	</TR>
</TABLE>
