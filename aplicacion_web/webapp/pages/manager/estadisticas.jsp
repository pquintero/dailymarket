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
						<a href="estadisticas.do?VirtualDispatchName=doEstadisticasVentasMensuales">Ventas Mensuales</a>
					</td>
					<td>&nbsp;</td>
					<td>
						<a href="estadisticas.do?VirtualDispatchName=doEstadisticasVentasAnuales">Ventas Anuales</a>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="5">&nbsp;</td>
				</tr>
				<tr align="center">
					<td>&nbsp;</td>
					<td>
						<a href="estadisticas.do?VirtualDispatchName=doEstadisticasVentasMensualesPorProducto">Ventas Mensuales<br/>Por Producto</a>
					</td>
					<td>&nbsp;</td>
					<td>
						<a href="estadisticas.do?VirtualDispatchName=doEstadisticasVentasAnualesPorProducto">Ventas Anuales<br/>Por Producto</a>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="5">&nbsp;</td>
				</tr>
				<tr align="center">
					<td>&nbsp;</td>
					<td>
						<a href="estadisticas.do?VirtualDispatchName=doEstadisticasVentasMensualesPorGrupoDeProducto">Ventas Mensuales<br/>Por Grupo de Producto</a>
					</td>
					<td>&nbsp;</td>
					<td>
						<a href="estadisticas.do?VirtualDispatchName=doEstadisticasVentasAnualesPorGrupoDeProducto">Ventas Anuales<br/>Por Grupo de Producto</a>
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
