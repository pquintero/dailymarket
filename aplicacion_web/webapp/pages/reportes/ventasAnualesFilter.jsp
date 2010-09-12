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
					<td><bean:message key="reportes.ventasAnuales.desde"/></td>
					<td>&nbsp;</td>
					<td>
						
					</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
					<td><bean:message key="reportes.ventasAnuales.hasta"/></td>
					<td>&nbsp;</td>
					<td>
						
					</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
					<td><bean:message key="reportes.ventasAnuales.grupoProducto"/></td>
					<td>&nbsp;</td>
					<td>
						
					</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
					<td><bean:message key="reportes.ventasAnuales.producto"/></td>
					<td>&nbsp;</td>
					<td>
						
					</td>
				</tr>
				<tr align="left">
					<td>&nbsp;</td>
					<td><bean:message key="reportes.ventasAnuales.bandaHoraria"/></td>
					<td>&nbsp;</td>
					<td>
						
					</td>
				</tr>
				<tr align="center">
					<td colspan="3">&nbsp;</td>
					<td>
						Ejecutar
					</td>
				</tr>
			</table>
		</td>
	</tr>
</TABLE>