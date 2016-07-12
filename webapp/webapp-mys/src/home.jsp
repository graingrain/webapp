<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>

<HTML> 
<HEAD>
<TITLE>Login enquete debat</TITLE> 
</HEAD>
<script language="JavaScript">

function control() {
if (document.form.code.value.length == 0) {
   alert("Le code rne est obligatoire \nCorrigez le ...")
   document.form.code.focus()
   return false
}
if (document.form.password.value.length == 0) {
   alert("Le password est obligatoire \nCorrigez le ...")
   document.form.password.focus()
   return false
}
}
</script>  

<BODY  >
<%  
ServletContext sc = getServletContext();
System.out.println("titre:"+ sc.getAttribute("titre"));
if (request.getParameter("message")!=null)  System.out.println("Plus  de connexion active");%>
<%
String code="";
String libelle="";
if (request.getParameter("code")!=null)  
{
code=request.getParameter("code");
libelle="votre mot de passe est enregistré, re-saisir votre mot de passe";
}
%>
 

<p align="center"><font size="6"><i><font color="#000099"><img src='images/logodebat.gif' border=0> 
<%=sc.getAttribute("titre")%>

<br></font></i></font></p>
<hr width="90" noshade size="1">
<p align="center"><font color="#000099"><i><font size="6">

</font></i></font></p>
 <table border="0" cellspacing="0" cellpadding="1" width=301
 bgcolor="#000000" align="center">
  <tr>
    <td>
 
<table border="0" cellspacing="0" width=300 cellpadding="0" align="center">
<tr><td bgcolor="#9999CC" colspan=2>
     <div align="center"><i><font color="#FFFFFF" size="5">Identification</font></i></div>
     </td>
</tr>
<form action="Dispatcher"     method="post" name="form"   onsubmit='return control()'>
<tr bgcolor=white><td colspan=2>&nbsp</td></tr> 
<tr bgcolor=white> 
<td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">RNE Etablissement&nbsp</font></td>
<td><input type="hidden" name="operation" size="8" value='login'>
    <input type="text"   name="code" size="9" value='<%=code%>'>
</td>
</tr>
<tr bgcolor=white>  
<td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Mot de passe&nbsp</font></td>
<td><input type="password" name="password" size="6" onfocus="this.select()">
</td>
</tr>
<tr bgcolor=white> <td colspan=2>&nbsp</td></tr> 
<tr bgcolor=white>  
<td colspan=2> 
<div align="center">
                                  <input type="submit" value="Connexion" width="72">
                                </div>
                              </td>
                            </tr>
</form>
        </table>
     </td>
  </tr>
</table> 
<center>
<font color=red><i><%=libelle%></i></font><br><br>
<center><font color=blue>
<% if (sc.getAttribute("message1") != null) %><%=sc.getAttribute("message1")%><br>
<% if (sc.getAttribute("message2") != null) %><%=sc.getAttribute("message2")%><br>
<% if (sc.getAttribute("message3") != null) %><%=sc.getAttribute("message3")%></font>

<center><br><a href="javascript:close()" Quitter l application</a>Quitter l' application


 </body>
</html>
