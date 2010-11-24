<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@page import="ar.com.dailyMarket.model.Image;"%>


<script type="text/javascript">
	hs.graphicsDir = 'highslide/graphics/';
	hs.align = 'center';
	hs.transitions = ['expand', 'crossfade'];
	hs.outlineType = 'rounded-white';
	hs.fadeInOut = true;
	//hs.dimmingOpacity = 0.75;

	// Add the controlbar
	if (hs.addSlideshow) hs.addSlideshow({
		//slideshowGroup: 'group1',
		interval: 5000,
		repeat: false,
		useControls: true,
		fixedControls: true,
		overlayOptions: {
			opacity: .75,
			position: 'top center',
			hideOnMouseOut: true
		}
	});
</script>

<bean:define id="id" property="id" name="ProductForm" type="java.lang.Long"/>
<bean:define id="attachId" property="attachId" name="ProductForm" type="java.lang.Long"/>


<TABLE class="form" cellSpacing="0" cellPadding="6" border="0">
<TR> 
    <TD colspan="5" align="left" valign="top"> 
		<h1 class="formtitle"><bean:message key="EditLayout.main"/></h1>
    </TD> 
</TR>
<TR> 
	<TD colspan="5"> 
		<html:errors/>
	</TD>
</TR> 

<logic:notEqual name="attachId" value="-1">
	<TR>
		<TH style="width:100px;padding-left:40px;" rowspan="5">
			&nbsp;
			<div>					
					<%
						Image image = (Image)request.getSession().getAttribute("image");
					%>						
					<a href="image.do?VirtualDispatchName=getImage&isImage=1&imageId=<%=image.getId().toString()%>" class="highslide" onclick="return hs.expand(this)">								
					<img src="image.do?VirtualDispatchName=getImage&isImage=0&imageId=<%=image.getThumbnail().getId().toString()%>" alt="<%=image.getDescription() %>"
						title="<%=((Image)image).getDescription() %>" /></a>
						<div class="highslide-caption">																																				
					</div>
			</div>
		</TH>	
	</TR>
</logic:notEqual>

<TR> 
	<TH style="width:100px;padding-left:40px;"><bean:message key="ProductForm.name"/></TH>
	<TD width="100px;"><html:text property="name" size="30"  maxlength="50"/></TD>	

	<TH style="width:100px;padding-left:40px;"><bean:message key="ProductForm.description"/></TH>
	<TD><html:text property="description" size="30"  maxlength="50"/></TD>	
</TR>
<TR> 
	<TH style="width:100px;padding-left:40px;"><bean:message key="ProductForm.code"/></TH>
	<TD width="100px;"><html:text property="code" size="30"  maxlength="20"/></TD>
	
	<TH style="width:100px;padding-left:40px;"><bean:message key="ProductForm.price"/></TH>
	<TD><html:text property="price" size="30"  maxlength="20"/></TD>			
</TR>
<TR> 
	<TH style="width:100px;padding-left:40px;"><bean:message key="ProductForm.actualStock"/></TH>
	<TD width="100px;"><html:text property="actualStock" size="30"  maxlength="20"/></TD>
	
	<TH style="width:100px;padding-left:40px;"><bean:message key="ProductForm.repositionStock"/></TH>
	<TD><html:text property="repositionStock" size="30"  maxlength="20"/></TD>	 
		
</TR>
<TR>
	<TH style="width:100px;padding-left:40px;"><bean:message key="ProductForm.sizePurchase"/></TH>
	<TD width="100px;"><html:text property="sizeOfPurchase" size="30"  maxlength="20"/></TD>
	<TH style="width:100px;padding-left:40px;"><bean:message key="ProductForm.groupProduct"/></TH>
	<TD>
		<html:select property="groupProductId">						
			<html:options collection="groupsProduct" property="id" labelProperty="name" />
		</html:select>
	</TD>
</TR>



<logic:notEqual name="id" value="-1">
	<tr>
		<TH colspan="2" style="width:100px;padding-left:40px;"></TH>
			<TABLE align="left" class="buttons" border="0" cellspacing="0" cellpadding="3">
				<TR>	
					<td width="100%">&nbsp;</td>
			        <TD align="right"  width="130px" >        				
			     	   <input width="130px" class="btn" value="Adjuntar Imagen" readonly="readonly"
							 onclick="forms[0].VirtualDispatchName.value='initImage';forms[0].submit();">
			        </TD>	
				</TR>
			</TABLE>
		</td>
	</tr>
</logic:notEqual>
</TABLE>
