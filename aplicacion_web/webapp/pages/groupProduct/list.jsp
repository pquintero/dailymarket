<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>

<%@ page import="ar.com.dailyMarket.model.GroupProduct" %>

<ds:table name="items" sort="list"  prop="formDisplaytag" export="false" id="row" pagesize="40" class="list"  cellspacing="0" cellpadding="3">

        <ds:column titleKey="GroupProductForm.name" headerClass="listTitle" sortable="true" property="name"/>
        <ds:column titleKey="GroupProductForm.description" headerClass="listTitle" sortable="true" property="description"/>
		<ds:column headerClass="listTitle"  title="&nbsp;">
			<img 
				src="images/common/editar.gif" 
				onclick="document.location='groupProduct.do?VirtualDispatchName=findByPK&id=<%=((GroupProduct)row).getId()%>'" 
				alt='<bean:message key="common.edit"/>'
				style="cursor: pointer;"/>
    	</ds:column>
    	<ds:column headerClass="listTitle"  title="&nbsp;">
			<img 
				src="images/common/eliminar.gif" 
				onclick="document.location='filterGroupProduct.do?VirtualDispatchName=delete&id=<%=((GroupProduct)row).getId()%>'" 
				alt='<bean:message key="common.delete"/>'
				style="cursor: pointer;"/>
    	</ds:column>
</ds:table>
