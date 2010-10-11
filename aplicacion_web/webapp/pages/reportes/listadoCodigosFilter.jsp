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
			<h1 class="formtitle"><bean:message key="reportes.listadoDeCodigos"/></h1>
		</TD>		
	</TR>	
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
</TABLE>