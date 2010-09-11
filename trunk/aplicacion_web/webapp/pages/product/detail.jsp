<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<TABLE class="form" cellSpacing="0" cellPadding="0" border="0">
<TR> 
    <TD colspan="7" align="left" valign="top"> 
		<h1 class="formtitle"><bean:message key="EditLayout.main"/></h1>
    </TD> 
</TR>
<TR> 
	<TD colspan="7"> 
		<html:errors/>
	</TD>
</TR> 

<TR> 
	<TH class="required"><bean:message key="ProductForm.name"/></TH>
	<TD><html:text property="name" size="30"  maxlength="50"/></TD>	
</TR>

<TR> 
	<TH class="required"><bean:message key="ProductForm.description"/></TH>
	<TD><html:text property="description" size="30"  maxlength="50"/></TD>	
</TR>
<TR> 
	<TH class="required"><bean:message key="ProductForm.price"/></TH>
	<TD><html:text property="price" size="30"  maxlength="20"/></TD>	
</TR>
<TR> 
	<TH class="required"><bean:message key="ProductForm.actualStock"/></TH>
	<TD><html:text property="actualStock" size="30"  maxlength="20"/></TD>	
</TR>
<TR> 
	<TH class="required"><bean:message key="ProductForm.repositionStock"/></TH>
	<TD><html:text property="repositionStock" size="30"  maxlength="20"/></TD>	
</TR>
<TR> 
	<TH class="required"><bean:message key="ProductForm.sizePurchase"/></TH>
	<TD><html:text property="sizeOfPurchase" size="30"  maxlength="20"/></TD>	
</TR>
<TR>
	<TH class="required"><bean:message key="ProductForm.groupProduct"/></TH>
	<TD>
		<html:select property="groupProductId">						
			<html:options collection="groupsProduct" property="id" labelProperty="name" />
		</html:select>
	</TD>
</TR>
</TABLE>
