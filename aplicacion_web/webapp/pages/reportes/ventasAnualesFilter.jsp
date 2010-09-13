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
					<td><bean:message key="reportes.ventasAnuales.title"/></td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
					<td><bean:message key="reportes.ventasAnuales.desde"/></td>
					<td>&nbsp;</td>
					<td>
						<html:select property="yearFrom">
							<html:option value="0">0</html:option>
						</html:select>
					</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
					<td><bean:message key="reportes.ventasAnuales.hasta"/></td>
					<td>&nbsp;</td>
					<td>
						<html:select property="yearTo">
							<html:option value="1">1</html:option>
						</html:select>
					</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
					<td><bean:message key="reportes.ventasAnuales.grupoProducto"/></td>
					<td>&nbsp;</td>
					<td>
						<html:select property="productGroup">
							<html:option value="productGroup">productGroup</html:option>
						</html:select>
					</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
					<td><bean:message key="reportes.ventasAnuales.producto"/></td>
					<td>&nbsp;</td>
					<td>
						<html:select property="product">
							<html:option value="product">product</html:option>
						</html:select>
					</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
					<td><bean:message key="reportes.ventasAnuales.bandaHoraria"/></td>
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
						<input type="button" value="Ejecutar" readonly="readonly" onclick="alert('VentasAnuales.pdf')">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</TABLE>