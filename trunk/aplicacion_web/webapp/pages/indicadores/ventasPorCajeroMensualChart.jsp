<!--VENTAS POR CAJERO MENSUAL CHART-->
<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ page import="java.net.URLEncoder" %>

<bean:define id="month" property="month" name="IndicadoresForm" type="java.lang.String"/>
<bean:define id="year" property="year" name="IndicadoresForm" type="java.lang.String"/>
<bean:define id="cajeroId" property="cajeroId" name="IndicadoresForm" type="java.lang.Long"/>
<bean:define id="bandaHorariaId" property="bandaHorariaId" name="IndicadoresForm" type="java.lang.Long"/>


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
								"VirtualDispatchName=getVPCMChart" + 
								"&year="+ URLEncoder.encode(year) +
								"&month="+ URLEncoder.encode(month) +
								"&cajeroId=" + cajeroId + 
								"&bandaHorariaId=" + bandaHorariaId;
						%>
						<embed src="charts/Line.swf"
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