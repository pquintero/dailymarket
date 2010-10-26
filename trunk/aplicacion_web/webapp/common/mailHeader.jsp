<%@ taglib uri="/tags/struts-bean" prefix="bean" %>

<%@ page contentType="text/html" language="java" %>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<head>
	<link href="site.css" rel="stylesheet" type="text/css">
	<title>DailyMarket </title>
</head>
<body onload="popup()">
<table  align="center" bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" border="0" width="100%">
  	<tr>
    	<td align="left" valign="top" ><img src="images/header/tituloDailyMarket.jpg"></td>
		<td></td>
		<td align="right"><img src="images/header/logoDailyMarket.jpg"></td>
  	</tr>
  	<tr>
    	<td class="logout" colspan="3" bgcolor="#FFFFFF"  align="right">
			<table cellpadding="0" class="bullets" cellspacing="0" border="0" align="right">
    			<tr>
       				<td>
						<a><img src="images/header/home.jpg"   name="home"  border="0" alt="Ir a Inicio"></a></td>
					<td>&nbsp;</td>
	   				<td><a href="logout.do?VirtualDispatchName=doLogout" ><img src="images/header/exit.jpg" name="boton3" border="0" alt="Salir"></a></td>
    			</tr>
    		</table>
		</td>
	</tr>
	<tr>
		<td align="left" valign="top" >
			<h1 class="formtitle">Mail de Productos pendientes</h1>
		</TD> 
	</TR>		
	<tr>
    	<td class="logout2" colspan="3" bgcolor="#FFFFFF"  align="right"></td>
	</tr>
</table>
</body>				
				
