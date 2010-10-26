<!--MAIL-->
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<TABLE class="form" cellSpacing="0" cellPadding="0" border="0">
	<tr>
		<td>
			<h1 class="formtitle">PRODUCTOS PENDIENTES DE PEDIDO</h1>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td>
			<html:text property="mailFrom" maxlength="50" size="75"/>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td>
			<html:textarea property="mailTo"  cols="150" rows="2"/>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td>
			 <html:text property="mailSubject"  maxlength="250" size="75" />
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td>
			<html:textarea property="mailBody" cols="150" rows="15"/>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
</TABLE>
