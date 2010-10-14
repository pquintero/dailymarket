<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>
<%@ page import="java.util.*" %>
<%@ page import="ar.com.dailyMarket.model.*" %>

<bean:define id="cajerosArray" property="cajerosArray" name="IndicadoresForm" type="java.lang.String[]"/>
<bean:define id="cajerosList" property="cajerosList" name="IndicadoresForm" type="java.util.List"/>
<%
ArrayList<String> listaAnios = (ArrayList<String>) request.getAttribute("aniosList");
ArrayList<String> listaMeses = (ArrayList<String>)request.getAttribute("mesesList");
%>

<TABLE class="form" cellSpacing="0" cellPadding="0" border="0">
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
			<h1 class="formtitle"><bean:message key="indicadores.comparativaDeVentasPorCajeroMensualFilter"/></h1>
		</TD>		
	</TR>
	<tr align="left">
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr align="left">
		<td>&nbsp;</td>
		<th><bean:message key="commons.year"/></th>
		<td>&nbsp;</td>
		<td>
			<html:select property="year">
				<% for(int i = 0; i < listaAnios.size(); i++) { %>
					<html:option  value="<%= listaAnios.get(i) %>"><%= listaAnios.get(i) %></html:option>
				<% } %>
			</html:select>
		</td>
	</tr>
	<tr align="left">
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr align="left">
		<td>&nbsp;</td>
		<th><bean:message key="commons.mes"/></th>
		<td>&nbsp;</td>
		<td>
			<html:select property="month">
				<% for(Integer i = 0; i < listaMeses.size(); i++) { %>
					<html:option  value="<%= i.toString() %>"><%= listaMeses.get(i) %></html:option>
				<% } %>
			</html:select>
		</td>
	</tr>
	<tr align="left">
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr align="left">
		<td>&nbsp;</td>
		<th><bean:message key="commons.bandaHoraria"/></th>
		<td>&nbsp;</td>
		<td>
			<html:select property="bandaHorariaId">
				<html:option value="-1">&nbsp;</html:option>
				<html:options collection="bandaList" property="id" labelProperty="detail"/>
			</html:select>
		</td>
	</tr>
	<tr align="left">
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="4">
			<table  width="40%">
				<TR>
					<td><bean:message key="indicadores.seleccionarCajeros"/></td>
				</TR>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<logic:iterate id="cajero" property="cajerosList" name="IndicadoresForm">
					<% User us = (User) cajero; %>
					<tr>
						<td>
							<bean:write name="cajero" property="name"/>
						</td>
						<td>
							<html:multibox name="IndicadoresForm" property="cajerosArray" value='<%= us.getId().toString() %>' /> 
						</td>
					</tr>
				</logic:iterate>
			</table>
			
		</td>
	</tr>
	<tr align="left">
		<td colspan="4">&nbsp;</td>
	</tr>		
</TABLE>