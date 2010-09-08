<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>


<table  align="left" bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td align="left" valign="top" >
			<h1 class="formtitle">ADMINISTRACIÓN</h1>
		</TD> 
	</TR>		
	<tr>
		<td align="left" >
			<table class="bullets" width="100" align="left" cellpadding="0"  cellspacing="0" border="0">			
				<TR>
					<td width="25"></td>
					<TD class="inciarOrdenLeft"> 
						<a href="filterGroupUser.do?VirtualDispatchName=showFilter">Grupo de Usuario</a>
					</TD>
				</TR>
				<tr> 
					<td width="25"></td>
					<TD class="inciarOrdenLeft"> 
						<a href="filterUser.do?VirtualDispatchName=showFilter">Usuario</a>
					</TD>
				</TR>
				<tr>
					<td width="25"></td>
					<TD class="inciarOrdenLeft"> 
						<a href="filterGroupProduct.do?VirtualDispatchName=showFilter">Grupo de Producto</a>
				</TR>
				<tr>
					<td width="25"></td>
					<TD class="inciarOrdenLeft"> 
						<a href="filterProduct.do?VirtualDispatchName=showFilter">Producto</a>
					</TD> 	
				</tr>
				<tr>
					<td width="25"></td>
					<TD class="inciarOrdenLeft"> 
						<a href="filterHourlyBand.do?VirtualDispatchName=showFilter">Banda Horaria</a>
					</TD>
				</tr>
				<tr>
					<td width="25"></td>
					<td class="inciarOrdenLeft"> 
						<a href="simulator.do?VirtualDispatchName=initAction">Simulador</a>
					</td>	
					
				</tr>				
			</table>
		</td>
	</tr>
</TABLE>