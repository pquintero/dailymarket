<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>

<%@ page import="ar.com.dailyMarket.model.GroupProduct" %>

<ds:table name="items" sort="list"  prop="formDisplaytag" export="false" id="row" pagesize="40" class="list"  cellspacing="0" cellpadding="3">

        <ds:column titleKey="GroupProductForm.name" headerClass="listTitle" sortable="true" property="name"/>
        <ds:column titleKey="GroupProductForm.description" headerClass="listTitle" sortable="true" property="description"/>
        <ds:column headerClass="listTitle">
	    	<table class="buttons">
				<tr>
					<td><a class="btn" href="#" onclick="document.location='groupProduct.do?VirtualDispatchName=findByPK&id=<%=((GroupProduct)row).getId()%>'" 
	            		style="cursor: pointer;">Ver</a></td>
				</tr>
			</table>
		</ds:column>
</ds:table>
