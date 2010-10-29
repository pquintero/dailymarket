<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>
<%@ page import="ar.com.dailyMarket.model.Product" %>

<TABLE class="form" cellSpacing="0" cellPadding="0" border="0">
<TR> 
    <TD colspan="7" align="left" valign="top"> 
		<h1 class="formtitle"><bean:message key="simulator.home"/></h1>
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
<tr>
	<td colspan="7">&nbsp;</td>	
</tr>
<tr>
	<td colspan="7">
	<tr><td>
	<TABLE align="right" class="buttons" border="0" cellspacing="0" cellpadding="3">
		<TR>	
			<td width="100%">&nbsp;</td>
			<TD align="right"  width="130px" >        				
			   <input width="130px" class="btn" value="Enviar Pedido"
					 onclick="sendOrder();">
			</TD>	
		</TR>
	</TABLE>
</td></tr>
	</td>	
</tr>

<tr>
	<td colspan="7">SI HAY PRODUCTOS SE MUESTRA ESTO</td>	
</tr>
<TR>
	<td>&nbsp;</td>
	<TH><bean:message key="SimulatorForm.daysSimulator"/></TH>
	<td>&nbsp;</td>
	<TD><html:text property="margen" size="10"  maxlength="5"/></TD>
	<TH><bean:message key="SimulatorForm.margen"/></TH>
	<td>&nbsp;</td>
	<TD colspan="4"><html:text property="days" size="10"  maxlength="5"/></TD>
</TR>
<tr>
	<td colspan="7">&nbsp;</td>	
</tr>


<TR> 
    <TD colspan="7" align="left" valign="top"> 
		<h1 class="formtitle"><bean:message key="simulator.result"/></h1>
    </TD> 
</TR>
</TABLE>
<ds:table name="productsList" sort="list"  prop="formDisplaytag" export="false" id="row" pagesize="40" class="list"  cellspacing="0" cellpadding="3" decorator="ar.com.dailyMarket.ui.decorators.ProductDecorator">
        <ds:column titleKey="ProductForm.code" headerClass="listTitle" sortable="true" property="code"/>
        <ds:column titleKey="ProductForm.name" headerClass="listTitle" sortable="true" property="name"/>
        <ds:column titleKey="ProductForm.description" headerClass="listTitle" sortable="true" property="description"/>       
        <ds:column titleKey="ProductForm.sizePurchase" headerClass="listTitle">
        	<html:text property="sizeOfPurchaseArray"/>
        </ds:column>		
        <ds:column titleKey="ProductForm.repositionStock" headerClass="listTitle">
        	<html:text property="repositionStockArray"/>
        </ds:column>
        <ds:column headerClass="listTitle">
        	<html:multibox property="simuladorArray" value="<%= ((Product)row).getId().toString() %>"/>
        </ds:column>
</ds:table>

<tr>
	<td colspan="7">Boton Simular</td>	
</tr>

<tr>
	<td colspan="7">Boton Aplicar Cambios</td>	
</tr>