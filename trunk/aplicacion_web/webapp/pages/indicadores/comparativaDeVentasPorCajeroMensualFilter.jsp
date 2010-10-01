<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>


<TABLE class="form" cellSpacing="0" cellPadding="0" border="0">
	<TR> 
		<TD colspan="4"> 
			<ul class="errors" type="square">
				<html:messages id="mensaje" message="true" >
					<li><bean:write name="mensaje" /></li>
				</html:messages>
			</ul>
		</TD>
	</TR>
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>				
	<TR> 
		<TD colspan="4">
			<h1 class="formtitle"><bean:message key="indicadores.comparativaDeVentasPorCajeroMensualFilter"/></h1>
		</TD>		
	</TR>
	<tr align="left">
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr align="left">
		<td>&nbsp;</td>
		<th><bean:message key="commons.mesDesde"/></th>
		<td>&nbsp;</td>
		<td>
			<html:select property="monthFrom">
				<html:option value="0">0</html:option>
			</html:select>
		</td>
	</tr>
	<tr align="left">
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr align="left">
		<td>&nbsp;</td>
		<th><bean:message key="commons.mesHasta"/></th>
		<td>&nbsp;</td>
		<td>
			<html:select property="monthTo">
				<html:option value="1">1</html:option>
			</html:select>
		</td>
	</tr>
	<tr align="left">
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr align="left">
		<td>&nbsp;</td>
		<th><bean:message key="commons.bandaHoraria"/></th>
		<td>&nbsp;</td>
		<td>
			<html:select property="bandaHoraria">
				<html:option value="bandaHoraria">bandaHoraria</html:option>
			</html:select>
		</td>
	</tr>
	<tr align="left">
		<td colspan="4">&nbsp;</td>
	</tr>		
</TABLE>