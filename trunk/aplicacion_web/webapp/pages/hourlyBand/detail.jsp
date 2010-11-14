<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<TABLE class="form" cellSpacing="0" cellPadding="6" border="0">
	<TR> 
	    <TD colspan="4" align="left" valign="top"> 
			<h1 class="formtitle"><bean:message key="EditLayout.main"/></h1>
	    </TD> 
	</TR>
	<TR> 
		<TD colspan="4"> 
			<html:errors/>
		</TD>
	</TR> 
	
	<TR> 
		<TH style="width:100px;padding-left:40px;"><bean:message key="HourlyBandForm.name"/></TH>
		<TD width="100px;"><html:text property="name" size="30"  maxlength="20"/></TD>	
	
		<TH style="width:100px;padding-left:40px;"><bean:message key="HourlyBandForm.description"/></TH>
		<TD><html:text property="description" size="30"  maxlength="20"/></TD>	
	</TR>
	<TR>
		<TH style="width:100px;padding-left:40px;"><bean:message key="HourlyBandForm.initHour"/></TH>
		<TD>
			<html:select property="initBand">	
				<html:option value="00">0</html:option>	
				<html:option value="01">1</html:option>
				<html:option value="02">2</html:option>
				<html:option value="03">3</html:option>
				<html:option value="04">4</html:option>
				<html:option value="05">5</html:option>
				<html:option value="06">6</html:option>
				<html:option value="07">7</html:option>
				<html:option value="08">8</html:option>
				<html:option value="09">9</html:option>
				<html:option value="10">10</html:option>
				<html:option value="11">11</html:option>
				<html:option value="12">12</html:option>
				<html:option value="13">13</html:option>
				<html:option value="14">14</html:option>
				<html:option value="15">15</html:option>
				<html:option value="16">16</html:option>
				<html:option value="17">17</html:option>
				<html:option value="18">18</html:option>
				<html:option value="19">19</html:option>
				<html:option value="20">20</html:option>
				<html:option value="21">21</html:option>
				<html:option value="22">22</html:option>
				<html:option value="23">23</html:option>				
			</html:select>
		</TD>
		<TH style="width:100px;padding-left:40px;"><bean:message key="HourlyBandForm.endHour"/></TH>
		<TD>
			<html:select property="endBand">	
				<html:option value="00">0</html:option>	
				<html:option value="01">1</html:option>
				<html:option value="02">2</html:option>
				<html:option value="03">3</html:option>
				<html:option value="04">4</html:option>
				<html:option value="05">5</html:option>
				<html:option value="06">6</html:option>
				<html:option value="07">7</html:option>
				<html:option value="08">8</html:option>
				<html:option value="09">9</html:option>
				<html:option value="10">10</html:option>
				<html:option value="11">11</html:option>
				<html:option value="12">12</html:option>
				<html:option value="13">13</html:option>
				<html:option value="14">14</html:option>
				<html:option value="15">15</html:option>
				<html:option value="16">16</html:option>
				<html:option value="17">17</html:option>
				<html:option value="18">18</html:option>
				<html:option value="19">19</html:option>
				<html:option value="20">20</html:option>
				<html:option value="21">21</html:option>
				<html:option value="22">22</html:option>
				<html:option value="23">23</html:option>				
			</html:select>
		</TD>
	</TR>
</TABLE>
