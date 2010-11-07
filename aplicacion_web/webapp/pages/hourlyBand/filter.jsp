<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>

<TABLE class="searchform" cellSpacing="0" cellPadding="6" border="0">
	<TR> 
		<TD colspan="2"> 
			<html:errors/>
		</TD>
	</TR> 
	<TR> 
	    <TH style="width:100px;padding-left:40px;"><bean:message key="GroupProductForm.name"/></TH>
		<TD>
			<html:select property="id">	
				<OPTION VALUE="-1">Seleccione</OPTION>					
				<html:options collection="hourlyBands" property="id" labelProperty="detail" />
			</html:select>
		</TD>
	</TR>	
</TABLE>

