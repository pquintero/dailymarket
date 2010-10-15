<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>

<link href="../../site.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
	function isAllUnselected() {
		var ids = document.getElementsByName("transportersIds");
		var i = 0;
		while (i < ids.length) {
			if (ids[i].checked) {
				document.getElementById("allUnselected").value = "false";
				break;
			}
			i++;
		}
		if (i == ids.length) {
			document.getElementById("allUnselected").value = "true";
		}
	}
	
	function sendOrder() {
		alert('sendOrder');
		window.open('pages/manager/sendOrders.jsp','my_new_window','toolbar=no, location=no, directories=no, status=yes, menubar=no, scrollbars=yes, resizable=no,copyhistory=no, width=815, height=640');
	}
</script>

<bean:define id="productsIds" name="ManagerForm" property="productsIds" type="java.lang.Long[]"/>

<TABLE class="form" cellSpacing="0" cellPadding="0" border="0">
	<tr>
		<td>
			<h1 class="formtitle">PRODUCTOS PENDIENTES DE PEDIDO</h1>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="3">
		<TR>	
			<td width="100%">
				<ds:table name="products" sort="list"  prop="formDisplaytag" export="false" id="row" pagesize="40" class="list"  cellspacing="0" cellpadding="3" decorator="ar.com.dailyMarket.ui.decorators.ProductDecorator">
				
				        <ds:column titleKey="ProductForm.code" headerClass="listTitle" sortable="true" property="code"/>
				        <ds:column titleKey="ProductForm.name" headerClass="listTitle" sortable="true" property="name"/>
				        <ds:column titleKey="ProductForm.description" headerClass="listTitle" sortable="true" property="description"/>       
				        <ds:column titleKey="ProductForm.actualStock" headerClass="listTitle" sortable="true" property="actualStock"/>
				        <ds:column titleKey="ProductForm.repositionStock" headerClass="listTitle" sortable="true" property="repositionStock"/>
				        <ds:column titleKey="ProductForm.state" headerClass="listTitle" sortable="true" property="state"/>
				        <ds:column property="check" title="&nbsp;"/>        
				</ds:table>
			</td>
		</TR>
	</TABLE>
	<TABLE align="right" class="buttons" border="0" cellspacing="0" cellpadding="3">
		<TR>	
			<td width="100%">&nbsp;</td>
		        <TD align="right"  width="130px" >        				
		     	   <input width="130px" class="btn" value="Enviar Pedido"
						onclick="sendOrder();">
		        </TD>	
		</TR>
	</TABLE>
</TABLE>
