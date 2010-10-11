<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>


<TABLE class="form"  border="0" cellpadding="0" cellspacing="0">		
	<TR> 
		<TD colspan="7"> 
			<ul class="errors" type="square">
				<html:messages id="mensaje" message="true" >
					<li><bean:write name="mensaje" /></li>
				</html:messages>
			</ul>
		</TD>
	</TR>
	<tr>
		<td colspan="7">&nbsp;</td>
	</tr>
	<TR> 
		<TD colspan="7">
			<h1 class="formtitle"><bean:message key="reportes.facturacionMensual"/></h1>
		</TD>		
	</TR>	
	<tr>
		<td colspan="7">&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<th><bean:message key="commons.yearDesde"/></th>
		<td>&nbsp;</td>
		<td>
			<html:select property="yearFrom">
				<html:option value="2009">2009</html:option>				
			</html:select>
		</td>
		<th><bean:message key="commons.yearHasta"/></th>
		<td>&nbsp;</td>
		<td>
			<html:select property="yearTo">
				<html:option value="2010">2010</html:option>
			</html:select>
		</td>
	</tr>
	<tr>
		<td colspan="7">&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<th><bean:message key="commons.mesDesde"/></th>
		<td>&nbsp;</td>
		<td>
			<html:select property="monthFrom">
				<html:option value="01">Enero</html:option>
				<html:option value="02">Febrero</html:option>
				<html:option value="03">Marzo</html:option>
				<html:option value="04">Abril</html:option>
				<html:option value="05">Mayo</html:option>
				<html:option value="06">Junio</html:option>
				<html:option value="07">Julio</html:option>
				<html:option value="08">Agosto</html:option>
				<html:option value="09">Septiembre</html:option>
				<html:option value="10">Octubre</html:option>
				<html:option value="11">Noviembre</html:option>
				<html:option value="12">Diciembre</html:option>
			</html:select>
		</td>
		<th><bean:message key="commons.mesHasta"/></th>
		<td>&nbsp;</td>
		<td>
			<html:select property="monthTo">
				<html:option value="01">Enero</html:option>
				<html:option value="02">Febrero</html:option>
				<html:option value="03">Marzo</html:option>
				<html:option value="04">Abril</html:option>
				<html:option value="05">Mayo</html:option>
				<html:option value="06">Junio</html:option>
				<html:option value="07">Julio</html:option>
				<html:option value="08">Agosto</html:option>
				<html:option value="09">Septiembre</html:option>
				<html:option value="10">Octubre</html:option>
				<html:option value="11">Noviembre</html:option>
				<html:option value="12">Diciembre</html:option>
			</html:select>
		</td>
	</tr>
	<tr>
		<td colspan="7">&nbsp;</td>
	</tr>
	<TR>
		<td>&nbsp;</td>
		<TH><bean:message key="ProductForm.groupProduct"/></TH>
		<td>&nbsp;</td>
		<TD>
			<html:select property="groupProductId">						
				<OPTION VALUE="-1">Seleccione</OPTION>
				<html:options collection="groupsProduct" property="id" labelProperty="name" />				
			</html:select>
		</TD>
		<TH><bean:message key="reportes.ventasAnuales.producto"/></TH>
		<td>&nbsp;</td>
		<TD>
			<html:select property="productId">						
				<OPTION VALUE="-1">Seleccione</OPTION>
				<html:options collection="products" property="id" labelProperty="name" />				
			</html:select>
		</TD>
	</TR>
	<tr>
		<td colspan="7">&nbsp;</td>
	</tr>
	<TR>
		<td>&nbsp;</td>
		<TH><bean:message key="commons.bandaHoraria"/></TH>
		<td>&nbsp;</td>
		<TD colspan="5">
			<html:select property="hourlyBandId">						
				<OPTION VALUE="-1">Seleccione</OPTION>
				<html:options collection="hourlyBands" property="id" labelProperty="detail" />				
			</html:select>
		</TD>		
	</TR>
</TABLE>