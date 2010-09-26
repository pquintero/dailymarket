<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>


<TABLE class="body"  border="0" cellpadding="0" cellspacing="0">	
	<TR> 
		<TD> 
			<ul class="errors" type="square">
				<html:messages id="mensaje" message="true" >
					<li><bean:write name="mensaje" /></li>
				</html:messages>
			</ul>
		</TD>
	</TR>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td >
			<table width="70%" align="left" cellpadding="0"  cellspacing="0" border="0">			
				<tr align="center">
					<td><bean:message key="indicadores.ventasPorCajeroMensualFilter"/></td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
					<td><bean:message key="commons.mesDesde"/></td>
					<td>&nbsp;</td>
					<td>
						<html:select property="monthFrom">
							<html:option value="0">Enero</html:option>
							<html:option value="1">Febrero</html:option>
						</html:select>
					</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
					<td><bean:message key="commons.mesHasta"/></td>
					<td>&nbsp;</td>
					<td>
						<html:select property="monthTo">
							<html:option value="0">Enero</html:option>
							<html:option value="1">Febrero</html:option>
						</html:select>
					</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
					<td><bean:message key="commons.cajero"/></td>
					<td>&nbsp;</td>
					<td>
						<html:select property="cajero">
							<html:option value="cajero">cajero</html:option>
						</html:select>
					</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
					<td><bean:message key="commons.bandaHoraria"/></td>
					<td>&nbsp;</td>
					<td>
						<html:select property="bandaHoraria">
							<html:option value="bandaHoraria">bandaHoraria</html:option>
						</html:select>
					</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
				</tr>
				<tr align="left">
					<td colspan="3">&nbsp;</td>
					<td>
						<input type="button" value="Ver Indicador" 
						onclick="forms[0].VirtualDispatchName.value='executeVentasPorCajeroMensual'; forms[0].submit();">
					</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
</TABLE>