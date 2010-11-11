<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ page import="ar.com.dailyMarket.model.Product" %>

<script type="text/javascript">
	function sendOrder() {
		document.forms[0].VirtualDispatchName.value='redirectToConfirmSendOrder';
		document.forms[0].submit();
	}
</script>

<TABLE class="form" cellSpacing="0" cellPadding="0" border="0">
	<tr>
		<td>
			<h1 class="formtitle">PRODUCTOS PENDIENTES DE PEDIDO</h1>
		</td>
	</tr>		
</TABLE>
<table width="97%" align="left" style="margin-left: 15px; clear:right;">
	<tr>
		<td>
			<ds:table name="products" sort="list"  prop="formDisplaytag" export="false" id="row" pagesize="40000" class="list"  cellspacing="0"
			cellpadding="3" decorator="ar.com.dailyMarket.ui.decorators.ProductDecorator">
		        <ds:column titleKey="ProductForm.code" headerClass="listTitle" property="code"/>
		        <ds:column titleKey="ProductForm.name" headerClass="listTitle" property="name"/>
		        <ds:column titleKey="ProductForm.description" headerClass="listTitle" property="description"/>       
		        <ds:column titleKey="ProductForm.actualStock" headerClass="listTitle" property="actualStock"/>
		        <ds:column titleKey="ProductForm.repositionStock" headerClass="listTitle" property="repositionStock"/>
		        <ds:column titleKey="ProductForm.state" headerClass="listTitle" property="state"/>
		        <ds:column title="&nbsp;">
		        	<% if(Product.PRODUCT_STATE_PENDING.equals(((Product)row).getState())) { %>
		        		<html:multibox property="productsIds" value="<%= ((Product)row).getId().toString() %>"/>
		        		<html:hidden property="productsIds" value="-1"/>
		        	<% } else {%>
		        		&nbsp;
		        	<% } %>
		        </ds:column>
			</ds:table>
		</td>
	</tr>
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
</table>
