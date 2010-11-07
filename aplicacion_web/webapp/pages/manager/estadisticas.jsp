<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>


<TABLE class="form"  border="0" cellpadding="0" cellspacing="0">	
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
		<td>&nbsp;</td>
	</tr>
	<TR> 
		<TD colspan="4">
			<h1 class="formtitle"><bean:message key="estadisticas.ventas"/></h1>
		</TD>		
	</TR>
	<tr align="center">
		<td width="60px;">&nbsp;</td>
		<td>
			<a class="reportesboton" href="estadisticas.do?VirtualDispatchName=doEstadisticasVentasMensuales">Mensuales</a>
		</td>
		<td>&nbsp;</td>
		<td>
			<a class="reportesboton" href="estadisticas.do?VirtualDispatchName=doEstadisticasVentasAnuales">Anuales</a>
		</td>
	</tr>
	<tr>				
		<td>&nbsp;</td>
		<TD class="explicacion">Refleja las ventas totales efectuadas en un periodo mensual</TD>	
		<td>&nbsp;</td>			
		<TD class="explicacion">Refleja las ventas totales efectuadas en un periodo anual</TD>
	</tr>
	<tr align="center">
		<td colspan="4">&nbsp;</td>
	</tr>
	<TR> 
		<TD colspan="4">
			<h1 class="formtitle"><bean:message key="estadisticas.ventasPorProducto"/></h1>
		</TD>		
	</TR>
	<tr align="center">
		<td>&nbsp;</td>
		<td>
			<a class="reportesboton" href="estadisticas.do?VirtualDispatchName=doEstadisticasVentasMensualesPorProducto">Mensuales</a>
		</td>
		<td>&nbsp;</td>
		<td>
			<a class="reportesboton" href="estadisticas.do?VirtualDispatchName=doEstadisticasVentasAnualesPorProducto">Anuales</a>
		</td>		
	</tr>
	<tr>				
		<td>&nbsp;</td>
		<TD class="explicacion">Refleja las ventas por producto efectuadas en un periodo mensual</TD>
		<td>&nbsp;</td>				
		<TD class="explicacion">Refleja las ventas por producto efectuadas en un periodo anual</TD>
	</tr>
	<tr align="center">
		<td colspan="4">&nbsp;</td>
	</tr>
	<TR> 
		<TD colspan="4">
			<h1 class="formtitle"><bean:message key="estadisticas.ventasPorGrupoDeProducto"/></h1>
		</TD>		
	</TR>
	<tr align="center">
		<td>&nbsp;</td>
		<td>
			<a class="reportesboton" href="estadisticas.do?VirtualDispatchName=doEstadisticasVentasMensualesPorGrupoDeProducto">Mensuales</a>
		</td>
		<td>&nbsp;</td>
		<td>
			<a class="reportesboton" href="estadisticas.do?VirtualDispatchName=doEstadisticasVentasAnualesPorGrupoDeProducto">Anuales</a>
		</td>	
	</tr>
	<tr>				
		<td>&nbsp;</td>
		<TD class="explicacion">Refleja las ventas por grupo de producto efectuadas en un periodo mensual</TD>
		<td>&nbsp;</td>				
		<TD class="explicacion">Refleja las ventas por grupo de producto efectuadas en un periodo anual</TD>		
	</tr>	
</TABLE>
