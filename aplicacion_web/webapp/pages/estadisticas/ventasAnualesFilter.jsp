<!--VENTAS ANUALES FILTER-->
<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>
<%@ page import="java.util.*" %>

<%
ArrayList<String> listaAnios = (ArrayList<String>) request.getAttribute("aniosList");
%>

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
			<h1 class="formtitle"><bean:message key="estadisticas.ventasAnualesFilter"/></h1>
		</TD>		
	</TR>		
	<tr align="left">
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr align="left">
		<th style="width:100px;padding-left:40px;"><bean:message key="commons.year"/></th>
		<td>
			<html:select property="year">
				<% for(int i = 0; i < listaAnios.size(); i++) { %>
					<html:option  value="<%= listaAnios.get(i) %>"><%= listaAnios.get(i) %></html:option>
				<% } %>
			</html:select>
		</td>
	</tr>
	<tr align="left">
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr align="left">
		<th style="width:100px;padding-left:40px;"><bean:message key="commons.bandaHoraria"/></th>
		<td>
			<html:select property="bandaHorariaId">
				<html:option value="-1">&nbsp;</html:option>
				<html:options collection="bandaList" property="id" labelProperty="detail"/>
			</html:select>
		</td>
	</tr>	
</TABLE>