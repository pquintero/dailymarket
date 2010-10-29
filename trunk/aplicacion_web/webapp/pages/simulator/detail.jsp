<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>
<%@ page import="ar.com.dailyMarket.model.Product" %>

<bean:define id="ssop" property="simulatedSizeOfPurchaseArray" name="SimulatorForm" type="java.lang.String[]"></bean:define>
<bean:define id="srs" property="simulatedRepositionStockArray" name="SimulatorForm" type="java.lang.String[]"></bean:define>


<TABLE class="form" cellSpacing="0" cellPadding="0" border="0">
	<TR> 
	    <TD colspan="7" align="left" valign="top"> 
			<h1 class="formtitle"><bean:message key="simulator.home"/></h1>
	    </TD> 
	</TR>
	<tr>
		<td colspan="7">&nbsp;</td>	
	</tr>
	<TR>
		<td>&nbsp;</td>
		<TH><bean:message key="ProductForm.groupProduct"/></TH>
		<td>&nbsp;</td>
		<TD>
			<html:select property="groupProductId">						
				<OPTION VALUE="-1">Seleccione</OPTION>
				<html:options collection="groupsProduct" property="id" labelProperty="name" />				
			</html:select>
		</TD>
		<TH><bean:message key="reportes.ventasAnuales.producto"/></TH>
		<td>&nbsp;</td>
		<TD>
			<html:select property="productId">						
				<OPTION VALUE="-1">Seleccione</OPTION>
				<html:options collection="products" property="id" labelProperty="name" />				
			</html:select>
		</TD>
	</TR>
	<tr>
		<td colspan="7">&nbsp;</td>	
	</tr>
	<tr>
		<td colspan="7">
			<TABLE align="right" class="buttons" border="0" cellspacing="0" cellpadding="3">
				<TR>	
					<td width="100%">&nbsp;</td>
					<TD align="right"  width="130px" >        				
					   <input width="130px" class="btn" value="Filtrar"
							 onclick="forms[0].VirtualDispatchName.value='executeFilter'; forms[0].submit();">
					</TD>	
				</TR>
			</TABLE>
		</td>	
	</tr>
	
<!--	SI SE APLICO EL FILTRO SE MUESTRA LO SIGUIENTE	-->
	<logic:notEmpty name="productsList">
		<TR>
			<td>&nbsp;</td>
			<TH><bean:message key="SimulatorForm.daysSimulator"/></TH>
			<td>&nbsp;</td>
			<TD><html:text property="days" size="10"  maxlength="5"/></TD>
			<TH><bean:message key="SimulatorForm.margen"/></TH>
			<td>&nbsp;</td>
			<TD><html:text property="margen" size="10"  maxlength="5"/></TD>
		</TR>
		<tr>
			<td colspan="7">&nbsp;</td>	
		</tr>
		<TR>
			<td>&nbsp;</td>
			<TH><bean:message key="SimulatorForm.yearFrom"/></TH>
			<td>&nbsp;</td>
			<TD>
				<html:select property="yearFrom">
					<html:option value="2009">2009</html:option>
					<html:option value="2008">2008</html:option>
				</html:select>
			</TD>
			<TH><bean:message key="SimulatorForm.simulatedDate"/></TH>
			<td>&nbsp;</td>
			<TD><html:text property="simulatedDay" size="10"  maxlength="10"/></TD>
		</TR>
		<tr>
			<td colspan="7">&nbsp;</td>	
		</tr>
		
		<tr>
			<td colspan="7">
				<TABLE align="right" class="buttons" border="0" cellspacing="0" cellpadding="3">
					<TR>	
						<td width="100%">&nbsp;</td>
						<TD align="right"  width="130px" >        				
						   <input width="130px" class="btn" value="Ejecutar Simulación"
								 onclick="forms[0].VirtualDispatchName.value='executeSimulator'; forms[0].submit();">
						</TD>	
						<TD align="right"  width="130px" >        				
						   <input width="130px" class="btn" value="Aplicar Cambios"
								 onclick="forms[0].VirtualDispatchName.value='aplicarCambios'; forms[0].submit();">
						</TD>	
					</TR>
				</TABLE>
			</td>	
		</tr>
		<tr>
			<td colspan="7">&nbsp;</td>	
		</tr>
		<TR> 
		    <TD colspan="7" align="left" valign="top"> 
				<h1 class="formtitle"><bean:message key="simulator.result"/></h1>
		    </TD> 
		</TR>
		<% Integer i = 0; %>
		<ds:table name="productsList" sort="list"  prop="formDisplaytag" export="false" id="row" pagesize="40" class="list"  cellspacing="0" cellpadding="3" decorator="ar.com.dailyMarket.ui.decorators.ProductDecorator">
		        <ds:column titleKey="ProductForm.code" headerClass="listTitle" property="code"/>
		        <ds:column titleKey="ProductForm.name" headerClass="listTitle" property="name"/>
		        <ds:column titleKey="ProductForm.description" headerClass="listTitle" property="description"/>       
		        
		        <ds:column titleKey="simulator.ActualSizeOfPurchase" headerClass="listTitle">
		        	<%= ((Product)row).getSizeOfPurchase() %>
		        </ds:column>		
		        <ds:column titleKey="simulator.ActualRepositionStock" headerClass="listTitle">
		        	<%= ((Product)row).getRepositionStock() %>
		        </ds:column>
		        <ds:column titleKey="simulator.SimulatedSizeOfPurchase" headerClass="listTitle">
		        	<html:text property="simulatedSizeOfPurchaseArray" value="<%= ssop[i] %>"/>
		        </ds:column>
		        <ds:column titleKey="simulator.SimulatedRepositionStock" headerClass="listTitle">
		        	<html:text property="simulatedRepositionStockArray" value="<%= srs[i] %>"/>
		        </ds:column>
		        
		        <ds:column headerClass="listTitle" titleKey="empty">
		        	<html:hidden property="productsArray" value="<%= ((Product)row).getId().toString() %>"/>
		        	<html:multibox property="simuladorArray" value="<%= ((Product)row).getId().toString() %>"/>
		        </ds:column>
		        
		        <% i++; %>
		</ds:table>
	</logic:notEmpty>
</TABLE>