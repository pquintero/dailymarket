<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>

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
        <ds:column titleKey="ProductForm.sizePurchase" headerClass="listTitle" sortable="true" property="sizeOfPurchase"/>		
        <ds:column titleKey="ProductForm.repositionStock" headerClass="listTitle" sortable="true" property="repositionStock"/>              
</ds:table>
