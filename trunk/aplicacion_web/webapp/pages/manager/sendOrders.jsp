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
	var bol = confirm('¿Desea cerrar la ventana?');
	var validate = validateFields();
	if (validate && bol) {		
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

	<tr>
		<td>
			<%= request.getSession().getAttribute("mail").toString() %>
		</td>
	</tr>
	<tr>
		<td>
			&nbsp;
		</td>
	</tr>
	
	<tr>
		<td>
			<input width="130px" class="btn" value="Cerrar" onclick="validateWindows()">
		</td>
	</tr>
</TABLE>