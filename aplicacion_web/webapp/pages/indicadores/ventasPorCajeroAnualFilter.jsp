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
			<h1 class="formtitle"><bean:message key="indicadores.ventasPorCajeroAnualFilter"/></h1>
		</TD>		
	</TR>					
	<tr align="left">
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr align="left">
		<td>&nbsp;</td>
		<th><bean:message key="commons.yearDesde"/></th>
		<td>&nbsp;</td>
		<td>
			<html:select property="yearFrom">
				<html:option value="2010">2010</html:option>
				<html:option value="2009">2009</html:option>
					<html:option value="2008">2008</html:option>
					</html:select>
		</td>
	</tr>
	<tr align="left">
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr align="left">
		<td>&nbsp;</td>
		<th><bean:message key="commons.yearHasta"/></th>
		<td>&nbsp;</td>
		<td>
			<html:select property="yearTo">
				<html:option value="2010">2010</html:option>
				<html:option value="2009">2009</html:option>
				<html:option value="2008">2008</html:option>
			</html:select>
		</td>
	</tr>
	<tr align="left">
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr align="left">
		<td>&nbsp;</td>
		<th><bean:message key="commons.cajero"/></th>
		<td>&nbsp;</td>
		<td>
			<html:select property="cajero">
				<html:option value="cajero">cajero</html:option>
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
			<html:select property="bandaHoraria">
				<html:option value="bandaHoraria">bandaHoraria</html:option>
			</html:select>
		</td>
	</tr>
</TABLE>
