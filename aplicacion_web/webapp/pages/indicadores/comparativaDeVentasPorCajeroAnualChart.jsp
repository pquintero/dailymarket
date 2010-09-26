<!--COMPARATIVA DE VENTAS POR CAJERO ANUAL CHART-->
<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ page import="java.net.URLEncoder" %>

<bean:define id="yearFrom" property="yearFrom" name="IndicadoresForm" type="java.lang.String"/>
<bean:define id="yearTo" property="yearTo" name="IndicadoresForm" type="java.lang.String"/>
<bean:define id="bandaHoraria" property="bandaHoraria" name="IndicadoresForm" type="java.lang.String"/>

<%= yearFrom + "_" + yearTo + "_" + bandaHoraria%>

<TABLE class="body"  border="0" cellpadding="0" cellspacing="0">	
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td >
			<table width="100%" align="left" cellpadding="0"  cellspacing="0" border="0">			
				<tr align="center">
					<TD>&nbsp;</TD>
					<TD align="center">
						<% String url = "indicadores.do?" +
								"VirtualDispatchName=getCVPCAChart" + 
								"&yearFrom="+ URLEncoder.encode(yearFrom) + 
								"&yearTo="+   URLEncoder.encode(yearTo) + 
								"&bandaHoraria=" + URLEncoder.encode(bandaHoraria);
						%>
						<embed src="charts/MSLine.swf"
		 					flashVars="dataURL=<%=URLEncoder.encode(url)%>"
		 					allowscriptaccess="always"
		 					quality="high"
		 					width="900"
		 					height="400"
							name="Line"
		 					type="application/x-shockwave-flash"
		 					pluginspage="http://www.macromedia.com/go/getflashplayer"
		 				/>
					</TD>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</TABLE>