<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>

<script type="text/javascript" src="yui/yahoo-min.js"></script>
<script type="text/javascript" src="yui/event-min.js"></script>

<script type="text/javascript">
var combo = eval(<bean:write property="comboProductos" name="ReportesForm" filter="false"/>);
var productId = <bean:write property='productId' name='ReportesForm' filter='false'/>;

function init() {
	changeCombo(document.getElementById("groupProducts"));
    if(productId != -1) {
		var products = document.getElementById("products");
		for(var i=0; i < products.options.length; i++){
			if(products.options[i].value == productId){
				products.selectedIndex = i;
				break;
			}
		}
    }
}

YAHOO.util.Event.onDOMReady(init);

function changeCombo(grupoProd) {
	var products = document.getElementById("products");
   	products.options.length = 0;
   	
   	if (grupoProd[grupoProd.selectedIndex].value == -1) {
		products.options[0]=new Option("TODOS", -1, true);
		var j = 1;
		for(var i = 0; i < combo.length; i++){
			var jsonPr = eval(combo[i]);
			products.options[j] = new Option(jsonPr.product, jsonPr.productId, false);
			j++;
		}
		products.selectedIndex = 0;
	} else {
		products.options[0] = new Option("TODOS", -1, true);
		var j = 1;
		for(var i = 0; i  < combo.length; i++) {
			var jsonPr = eval(combo[i]);
			if(jsonPr.groupId == grupoProd[grupoProd.selectedIndex].value) {
				products.options[j] = new Option(jsonPr.product, jsonPr.productId, false);
				j++;
			}
		}
		products.selectedIndex = 0;
	}
}

</script>

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
			<h1 class="formtitle"><bean:message key="reportes.listadoDeCodigos"/></h1>
		</TD>		
	</TR>	
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>
	<TR>
		<TH style="width:100px;padding-left:40px;"><bean:message key="ProductForm.groupProduct"/></TH>
		<TD width="100px;">
			<html:select property="groupProductId" styleId="groupProducts" onchange="changeCombo(this)">						
				<OPTION VALUE="-1">TODOS</OPTION>
				<html:options collection="groupsProduct" property="id" labelProperty="name" />				
			</html:select>
		</TD>
		<TH style="width:100px;padding-left:40px;"><bean:message key="reportes.ventasAnuales.producto"/></TH>
		<TD>
			<html:select property="productId" styleId="products">						
			</html:select>
		</TD>
	</TR>
</TABLE>