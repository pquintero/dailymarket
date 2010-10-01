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
		<td colspan="4">&nbsp;</td>
	</tr>
	<TR> 
		<TD colspan="4">
			<h1 class="formtitle"><bean:message key="estadisticas.ventas"/></h1>
		</TD>		
	</TR>
	<tr align=center>
		<td>&nbsp;</td>
		<td>
			<a href="reportes.do?VirtualDispatchName=doReporteVentasMensuales">Mensuales</a>
		</td>		
		<td>&nbsp;</td>
		<td>
			<a href="reportes.do?VirtualDispatchName=doReporteVentasAnuales">Anuales</a>
		</td>
	</tr>
	<tr>				
		<td>&nbsp;</td>
		<TD class="title">Refleja las ventas de productos en un período anual</TD>
		<td>&nbsp;</td>		
		<TD class="title">Refleja las ventas de productos en un período mensual</TD>		
	</tr>
	<tr><td colspan="4">&nbsp;</td></tr>
	<TR> 
		<TD colspan="4">
			<h1 class="formtitle"><bean:message key="reportes.facturacion"/></h1>
		</TD>		
	</TR>
	<tr align="center">
		<td>&nbsp;</td>
		<td>
			<a href="reportes.do?VirtualDispatchName=doReporteFacturacionMensual">Mensual</a>
		</td>		
		<td>&nbsp;</td>
		<td>
			<a href="reportes.do?VirtualDispatchName=doReporteFacturacionAnual">Anual</a>
		</td>
	</tr>
	<tr>				
		<td>&nbsp;</td>
		<TD class="title">Refleja la facturación en un período anual</TD>
		<td>&nbsp;</td>		
		<TD class="title">Refleja la facturación en un período mensual</TD>		
	</tr>
	<tr><td colspan="4">&nbsp;</td></tr>
	<TR> 
		<TD colspan="4">
			<h1 class="formtitle"><bean:message key="reportes.listado"/></h1>
		</TD>		
	</TR>
	<tr align="center">
		<td>&nbsp;</td>
		<td>
			<a href="reportes.do?VirtualDispatchName=doReporteListadoPrecios">De Precios</a>
		</td>
		<td>&nbsp;</td>
		<td>
			<a href="reportes.do?VirtualDispatchName=doReporteListadoCodigos">De Códigos de Barra</a>
		</td>
	</tr>
	<tr>				
		<td>&nbsp;</td>
		<TD class="title">Lista los precios de los productos involucrados</TD>		
		<td>&nbsp;</td>
		<TD class="title">Lista los códigos de barra de los productos involucrados</TD>			
	</tr>	
</TABLE>