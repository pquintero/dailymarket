<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>


<TABLE class="form"  border="0" cellpadding="0" cellspacing="0">	
	<TR> 
		<TD colspan="3"> 
			<ul class="errors" type="square">
				<html:messages id="mensaje" message="true" >
					<li><bean:write name="mensaje" /></li>
				</html:messages>
			</ul>
		</TD>
	</TR>
	<tr>
		<td colspan="3">&nbsp;</td>
	</tr>
	<TR> 
		<TD colspan="3">
			<h1 class="formtitle"><bean:message key="indicadores.ventasPorCajero"/></h1>
		</TD>		
	</TR>
	<tr>				
		<td>&nbsp;</td>
		<td>
			<a href="indicadores.do?VirtualDispatchName=doIndicadoresVentasPorCajeroMensual">Mensuales</a>
		</td>		
		<td>
			<a href="indicadores.do?VirtualDispatchName=doIndicadoresVentasPorCajeroAnual">Anuales</a>
		</td>		
	</tr>
	<tr>				
		<td>&nbsp;</td>
		<TD class="title">Refleja las ventas por cajero de forma mensual</TD>		
		<TD class="title">Refleja las ventas por cajero de forma anual</TD>		
	</tr>
	<tr>
		<td colspan="3">&nbsp;</td>
	</tr>
	<TR> 
		<TD colspan="3">
			<h1 class="formtitle"><bean:message key="indicadores.comparativaPorCajero"/></h1>
		</TD>		
	</TR>
	<tr>
		<td>&nbsp;</td>
		<td>
			<a href="indicadores.do?VirtualDispatchName=doIndicadoresComparativaDeVentasPorCajeroMensual">Mensual</a>
		</td>
		<td>
			<a href="indicadores.do?VirtualDispatchName=doIndicadoresComparativaDeVentasPorCajeroAnual">Anual</a>
		</td>
	</tr>
	<tr>				
		<td>&nbsp;</td>
		<TD class="title">Refleja una comparativa de ventas mensuales entre cajeros de forma mensual</TD>		
		<TD class="title">Refleja una comparativa de ventas mensuales entre cajeros de forma anual</TD>		
	</tr>	
</TABLE>