<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>

<%@ page import="ar.com.dailyMarket.model.Product" %>

<ds:table name="items" sort="list"  prop="formDisplaytag" export="false" id="row" pagesize="40" class="list"  cellspacing="0" cellpadding="3">

        <ds:column titleKey="ProductForm.code" headerClass="listTitle" property="code"/>
        <ds:column titleKey="ProductForm.name" headerClass="listTitle" property="name"/>
        <ds:column titleKey="ProductForm.description" headerClass="listTitle" property="description"/>
        <ds:column titleKey="ProductForm.price" headerClass="listTitle" property="price"/>
        <ds:column titleKey="ProductForm.actualStock" headerClass="listTitle" property="actualStock"/>
        <ds:column titleKey="ProductForm.repositionStock" headerClass="listTitle" property="repositionStock"/>
        <ds:column titleKey="ProductForm.groupProduct" headerClass="listTitle" property="groupProduct.name"/>
        <ds:column headerClass="listTitle"  title="&nbsp;">
			<img 
				src="images/common/editar.gif" 
				onclick="document.location='product.do?VirtualDispatchName=findByPK&id=<%=((Product)row).getId()%>'" 
				alt='<bean:message key="common.edit"/>'
				style="cursor: pointer;"/>
    	</ds:column>
    	<ds:column headerClass="listTitle"  title="&nbsp;">
			<img 
				src="images/common/eliminar.gif" 
				onclick="document.location='filterProduct.do?VirtualDispatchName=delete&id=<%=((Product)row).getId()%>'" 
				alt='<bean:message key="common.delete"/>'
				style="cursor: pointer;"/>
    	</ds:column>
</ds:table>
