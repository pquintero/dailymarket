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
		<TH style="width:100px;padding-left:40px;"><bean:message key="HourlyBandForm.name"/></TH>
		<TD width="100px;"><html:text property="name" size="30"  maxlength="20"/></TD>	
	
		<TH style="width:100px;padding-left:40px;"><bean:message key="HourlyBandForm.description"/></TH>
		<TD><html:text property="description" size="30"  maxlength="20"/></TD>	
	</TR>
	<TR> 
		<TH style="width:100px;padding-left:40px;"><bean:message key="HourlyBandForm.initHour"/></TH>
		<TD width="100px;"><html:text property="initBand" size="30"  maxlength="20"/></TD>	
	 
		<TH style="width:100px;padding-left:40px;"><bean:message key="HourlyBandForm.endHour"/></TH>
		<TD><html:text property="endBand" size="30"  maxlength="20"/></TD>	
	</TR>
</TABLE>
