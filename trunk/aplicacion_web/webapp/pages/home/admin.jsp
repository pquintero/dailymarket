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
			<table width="600" align="center" cellpadding="0"  cellspacing="0" border="0">			
				<TR>
					<td width="25">&nbsp;</td>
					<TD class="inciarOrdenLeft"> 
						<a href="filterGroupUser.do?VirtualDispatchName=showFilter">Grupo de Usuario</a>
					</TD> 
					<td width="25">&nbsp;</td>
					<td width="25">&nbsp;</td>
					<TD class="inciarOrdenLeft"> 
						<a href="filterUser.do?VirtualDispatchName=showFilter">Usuario</a>
					</TD>
				</TR>
				<tr><td>&nbsp;</td></tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td width="25">&nbsp;</td>
					<TD class="inciarOrdenLeft"> 
						<a href="filterGroupProduct.do?VirtualDispatchName=showFilter">Grupo de Producto</a>
					</TD>
					<td width="25">&nbsp;</td>
					<td width="25">&nbsp;</td>
					<TD class="inciarOrdenLeft"> 
						<a href="filterProduct.do?VirtualDispatchName=showFilter">Producto</a>
					</TD> 	
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td width="25">&nbsp;</td>
					<TD class="inciarOrdenLeft"> 
						<a href="filterHourlyBand.do?VirtualDispatchName=showFilter">Banda Horaria</a>
					</TD>
					<td width="25">&nbsp;</td>
					<td width="25">&nbsp;</td>
					<td class="inciarOrdenLeft"> 
						<a href="configuration.do?VirtualDispatchName=initAction">Configuraciones</a>
					</td>	
					
				</tr>				
			</table>
		</td>
	</tr>
</TABLE>