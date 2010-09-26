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
	<TH><bean:message key="SimulatorForm.lastExecute"/></TH>
	<TD><html:text property="lastExecute" size="30"  maxlength="20"/></TD>	
</TR>
<TR>
	<TH><bean:message key="SimulatorForm.groupProduct"/></TH>
	<TD>
		<html:select property="groupProductId">						
			<html:options collection="groupsProduct" property="id" labelProperty="name" />
		</html:select>
	</TD>
</TR>
</TABLE>
