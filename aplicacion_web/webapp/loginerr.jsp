<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>


<html:form action="login">
<head>
<title> Login .:: TECNO-SOLUTIONS ::. </title>
<meta http-equiv="Content-Type" content="text/html;">
<!--Fireworks MX 2004 Dreamweaver MX 2004 target.  Created Wed Jan 03 14:25:43 GMT-0300 2007-->
<link href="site.css" rel="stylesheet" type="text/css">
</head>
<body marginheight="0" bottommargin="0" leftmargin="0" topmargin="0" rightmargin="0">
<table align="center" bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" border="0" width="98%">
  <tr>
    <!-- header -->
    <td colspan="3" align="center" background="images/login/pixelHead.jpg"><img src="images/login/logoDailyMarket.jpg" name="undefined_2"></td>
  </tr>
  <tr>
    <!-- title -->
    <td colspan="3" background="images/login/titlepx.jpg"><img src="images/login/title.jpg" name="undefined_2"></td>
  </tr>
  <tr>
    <!-- header -->
    <td   valign="top" colspan="3" ><table border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td class="user" height="25px" colspan="5"></td>
      </tr>
      <tr>
        <td width="60"  height="20" class="user" >Usuario </td>
        <td ><input type="text" name="j_username" value="" class="login"></td>
        <td width="60"  height="20"  class="user" >Password </td>
        <td ><input type="password" name="j_password" value="" class="login"></td>
        <td bgcolor="#FFFFFF"  height="20" align="left" ><input name="image" type="image"   onClick="document.location='prueba.html'" src="images/login/flechitalogin.jpg" alt="Login"  align="left"></td>
      </tr>
      <tr>
        <td bgcolor="#FFFFFF" valign="top" colspan="5" height="30"><p class="error">* El nombre de usuario y/o password es incorrecto.<p></td>
      </tr>
    </table>
        <table cellpadding="0" width="100%" cellspacing="0" border="0">
          <tr>
            <td width="950px"  height="15"><img src="images/login/linea.jpg" name="undefined_2" width="950px"></td>
            <td background="images/login/lineapx.jpg"></td>
          </tr>
        </table>
      <table  width="50%" align="left" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td colspan="2" height="350"></td>
          </tr>
      </table></td>
  </tr>
</table>
</body>
</html:form>
