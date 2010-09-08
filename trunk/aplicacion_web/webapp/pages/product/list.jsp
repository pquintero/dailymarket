<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>

<%@ page import="ar.com.dailyMarket.model.Product" %>

<ds:table name="items" sort="list"  prop="formDisplaytag" export="false" id="row" pagesize="40" class="list"  cellspacing="0" cellpadding="3">

        <ds:column titleKey="ProductForm.code" headerClass="listTitle" sortable="true" property="code"/>
        <ds:column titleKey="ProductForm.name" headerClass="listTitle" sortable="true" property="name"/>
        <ds:column titleKey="ProductForm.description" headerClass="listTitle" sortable="true" property="description"/>
        <ds:column titleKey="ProductForm.price" headerClass="listTitle" sortable="true" property="price"/>
        <ds:column titleKey="ProductForm.actualStock" headerClass="listTitle" sortable="true" property="actualStock"/>
        <ds:column titleKey="ProductForm.groupProduct" headerClass="listTitle" sortable="true" property="groupProduct.name"/>
        <ds:column headerClass="listTitle">
	    	<table class="buttons">
				<tr>
					<td><a class="btn" href="#" onclick="document.location='product.do?VirtualDispatchName=findByPK&id=<%=((Product)row).getId()%>'" 
	            		style="cursor: pointer;">Ver</a></td>
				</tr>
			</table>
		</ds:column>
</ds:table>
