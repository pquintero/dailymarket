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
			<h1 class="formtitle"><bean:message key="reportes.facturacionAnual"/></h1>
		</TD>		
	</TR>	
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr>
		<th style="width:100px;padding-left:40px;"><bean:message key="commons.yearDesde"/></th>		
		<td width="100px;">
			<html:select property="yearFrom">
				<html:option value="2009">2009</html:option>				
			</html:select>
		</td>
		<th style="width:100px;padding-left:40px;"><bean:message key="commons.yearHasta"/></th>
		<td>
			<html:select property="yearTo">
				<html:option value="2010">2010</html:option>
			</html:select>
		</td>
	</tr>
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>	
	<TR>
		<TH style="width:100px;padding-left:40px;"><bean:message key="ProductForm.groupProduct"/></TH>		
		<TD width="100px;">
			<html:select property="groupProductId">						
				<OPTION VALUE="-1">Seleccione</OPTION>
				<html:options collection="groupsProduct" property="id" labelProperty="name" />				
			</html:select>
		</TD>
		<TH style="width:100px;padding-left:40px;"><bean:message key="reportes.ventasAnuales.producto"/></TH>
		<TD>
			<html:select property="productId">						
				<OPTION VALUE="-1">Seleccione</OPTION>
				<html:options collection="products" property="id" labelProperty="name" />				
			</html:select>
		</TD>
	</TR>
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>
	<TR>
		<TH style="width:100px;padding-left:40px;"><bean:message key="commons.bandaHoraria"/></TH>		
		<TD width="100px;" colspan="3">
			<html:select property="hourlyBandId">						
				<OPTION VALUE="-1">Seleccione</OPTION>
				<html:options collection="hourlyBands" property="id" labelProperty="detail" />				
			</html:select>
		</TD>		
	</TR>
</TABLE>