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
			<table width="100%" align="left" cellpadding="0"  cellspacing="0" border="0">			
				<tr align="center">
					<td>&nbsp;</td>
					<td>
						<a href="document.location='estadisticas.do?VirtualDispatchName=doVentasMensuales'">Ventas Mensuales</a>
					</td>
					<td>&nbsp;</td>
					<td>
						<a href="document.location='estadisticas.do?VirtualDispatchName=doVentasAnuales'">Ventas Anuales</a>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="5">&nbsp;</td>
				</tr>
				<tr align="center">
					<td>&nbsp;</td>
					<td>
						<a href="document.location='estadisticas.do?VirtualDispatchName=doVentasMensaualesPorProducto'">Ventas Mensauales<br/>Por Producto</a>
					</td>
					<td>&nbsp;</td>
					<td>
						<a href="document.location='estadisticas.do?VirtualDispatchName=doVentasAnualesPorProducto'">Ventas Anuales<br/>Por Producto</a>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="5">&nbsp;</td>
				</tr>
				<tr align="center">
					<td>&nbsp;</td>
					<td>
						<a href="document.location='estadisticas.do?VirtualDispatchName=doVentasMensualesPorGrupoDeProducto'">Ventas Mensuales<br/>Por Grupo de Producto</a>
					</td>
					<td>&nbsp;</td>
					<td>
						<a href="document.location='estadisticas.do?VirtualDispatchName=doVentasMensualesPorGrupoDeProducto'">Ventas Anuales<br/>Por Grupo de Producto</a>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="5">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
</TABLE>
