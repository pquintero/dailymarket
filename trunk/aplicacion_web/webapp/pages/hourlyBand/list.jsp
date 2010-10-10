<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>

<%@ page import="ar.com.dailyMarket.model.HourlyBand" %>

<ds:table name="items" sort="list"  prop="formDisplaytag" export="false" id="row" pagesize="40" class="list"  cellspacing="0" cellpadding="3">

        <ds:column titleKey="HourlyBandForm.name" headerClass="listTitle" sortable="true" property="name"/>
        <ds:column titleKey="HourlyBandForm.description" headerClass="listTitle" sortable="true" property="description"/>
        <ds:column titleKey="HourlyBandForm.initHour" headerClass="listTitle" sortable="true" property="initBand"/>
        <ds:column titleKey="HourlyBandForm.endHour" headerClass="listTitle" sortable="true" property="endBand"/>
        <ds:column headerClass="listTitle"  title="&nbsp;">
			<img 
				src="images/common/editar.gif" 
				onclick="document.location='hourlyBand.do?VirtualDispatchName=findByPK&id=<%=((HourlyBand)row).getId()%>'" 
				alt='<bean:message key="common.edit"/>'
				style="cursor: pointer;"/>
    	</ds:column>
    	<ds:column headerClass="listTitle"  title="&nbsp;">
			<img 
				src="images/common/eliminar.gif" 
				onclick="document.location='filterHourlyBand.do?VirtualDispatchName=delete&id=<%=((HourlyBand)row).getId()%>'" 
				alt='<bean:message key="common.edit"/>'
				style="cursor: pointer;"/>
    	</ds:column>        
</ds:table>
