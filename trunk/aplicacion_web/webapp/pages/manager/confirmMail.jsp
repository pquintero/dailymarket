<!--MAIL-->
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<TABLE class="form" cellSpacing="0" cellPadding="0" border="0">
	<TR> 
		<TD colspan="2"> 
			<html:errors/>
		</TD>
	</TR>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td colspan="2">
			<h1 class="formtitle">PRODUCTOS PENDIENTES DE PEDIDO</h1>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td width="10px" style="font-size:12px;color:#24A2C9;">
			DE:
		</td>
		<td>
			<html:text property="mailFrom" maxlength="50" size="75" readonly="true"/>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td style="font-size:12px;color:#24A2C9;">
			PARA: 
		</td>
		<td>
			<html:textarea property="mailTo"  cols="150" rows="2"/>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td style="font-size:12px;color:#24A2C9;">
			ASUNTO: 
		</td>
		<td>
			 <html:text property="mailSubject"  maxlength="250" size="75" />
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td colspan="2">
			<html:textarea property="mailBody" cols="150" rows="15"/>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
</TABLE>
