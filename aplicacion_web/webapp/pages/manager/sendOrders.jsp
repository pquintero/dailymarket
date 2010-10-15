<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>

<script language="JavaScript">
function print () {
	alert ('print');
}


function validateFields() {	
	return true;
}


function validateWindows () {
	alert('validate');
	var validate = validateFields();
	if (validate) {		
		window.close();
	}				
}

</script>

<TABLE class="form" cellSpacing="0" cellPadding="0" border="0">
	<tr>
		<td>
			<h1 class="formtitle">PRODUCTOS PENDIENTES DE PEDIDO</h1>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr align="center">
		<td>&nbsp;</td>
		<td>
			<a href="estadisticas.do?VirtualDispatchName=doEstadisticasVentasMensuales">Mensuales</a>
		</td>
		<td>&nbsp;</td>
		<td>
			<a href="estadisticas.do?VirtualDispatchName=doEstadisticasVentasAnuales">Anuales</a>
		</td>
	</tr>		
	<TABLE align="right" class="buttons" border="0" cellspacing="0" cellpadding="3">
		<TR>	
			<td width="100%">&nbsp;</td>
		        <TD align="right"  width="130px" >        				
		     	   <input width="130px" class="btn" value="Cerrar"
						onclick="validateWindows()">
		        </TD>	
		</TR>
	</TABLE>	
</TABLE>