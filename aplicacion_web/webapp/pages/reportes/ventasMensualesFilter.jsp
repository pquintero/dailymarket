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
			<h1 class="formtitle"><bean:message key="reportes.ventasMensuales"/></h1>
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
				<html:option value="0">2009</html:option>
			</html:select>
		</td>
		<th><bean:message key="commons.yearHasta"/></th>
		<td>&nbsp;</td>
		<td>
			<html:select property="yearTo">
				<html:option value="1">2010</html:option>
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
				<html:option value="0">Septiembre</html:option>
			</html:select>
		</td>
		<th><bean:message key="commons.mesHasta"/></th>
		<td>&nbsp;</td>
		<td>
			<html:select property="monthTo">
				<html:option value="1">Febrero</html:option>
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
		<TD colspan="4">
			<html:select property="groupProductId">						
				<OPTION VALUE="">Seleccione</OPTION>
				<html:options collection="groupsProduct" property="id" labelProperty="name" />				
			</html:select>
		</TD>
	</TR>
	<tr>
		<td colspan="7">&nbsp;</td>
	</tr>
	<TR>
		<td>&nbsp;</td>
		<TH><bean:message key="reportes.ventasAnuales.producto"/></TH>
		<td>&nbsp;</td>
		<TD colspan="4">
			<html:select property="productId">						
				<OPTION VALUE="">Seleccione</OPTION>
				<html:options collection="products" property="id" labelProperty="name" />				
			</html:select>
		</TD>
	</TR>	
</TABLE>