<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>
<%@ page import="java.util.*" %>
<%@ page import="ar.com.dailyMarket.model.*" %>

<bean:define id="cajerosArray" property="cajerosArray" name="IndicadoresForm" type="java.lang.String[]"/>
<bean:define id="cajerosList" property="cajerosList" name="IndicadoresForm" type="java.util.List"/>

<% ArrayList<String> listaAnios = (ArrayList<String>) request.getAttribute("aniosList"); %>
<TABLE class="form"  border="0" cellpadding="0" cellspacing="0">	
	<TR> 
		<TD colspan="2"> 
			<ul class="errors" type="square">
				<html:messages id="mensaje" message="true" >
					<li><bean:write name="mensaje" /></li>
				</html:messages>
			</ul>
		</TD>
	</TR>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<TR> 
		<TD colspan="2">
			<h1 class="formtitle"><bean:message key="indicadores.comparativaDeVentasPorCajeroAnualFilter"/></h1>
		</TD>		
	</TR>	
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<th style="width:100px;padding-left:40px;"><bean:message key="commons.year"/></th>
		<td width="100px;">
			<html:select property="year">
				<% for(int i = 0; i < listaAnios.size(); i++) { %>
					<html:option  value="<%= listaAnios.get(i) %>"><%= listaAnios.get(i) %></html:option>
				<% } %>
			</html:select>
		</td>
	</tr>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<th style="width:100px;padding-left:40px;"><bean:message key="commons.bandaHoraria"/></th>
		<td width="100px;">
			<html:select property="bandaHorariaId">
				<html:option value="-1">&nbsp;</html:option>
				<html:options collection="bandaList" property="id" labelProperty="detail"/>
			</html:select>
		</td>
	</tr>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="2">
			<table  width="100%">
				<TR>
					<th style="width:250px;padding-left:40px;" width="200px;" colspan="2"><bean:message key="indicadores.seleccionarCajeros"/></th>
				</TR>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<logic:iterate id="cajero" property="cajerosList" name="IndicadoresForm">
					<% User us = (User) cajero; %>
					<tr>
						<th style="width:250px;padding-left:40px;">
							<bean:write name="cajero" property="name"/>
						</th>
						<td>
							<html:multibox name="IndicadoresForm" property="cajerosArray" value='<%= us.getId().toString() %>' /> 
						</td>
					</tr>
				</logic:iterate>
			</table>			
		</td>
	</tr>
</TABLE>