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
 
String code="";
String libelle="";
if (request.getAttribute("erreur")!=null)  
{
if (request.getParameter("code")!=null) code=request.getParameter("code");
libelle=(String)request.getAttribute("erreur");
}
%>
 
 
<p align="center"><font color="#000099"><i><font size="6">

</font></i></font></p>
 
<table border="0" cellspacing="0" cellpadding="1" width=402
 bgcolor="#000000" align="center">
  <tr>
    <td>
 
<table border="0" cellspacing="0" width=400 cellpadding="0" align="center">
        <tr bgcolor="#FF9933"> 
          <td colspan=2> 
            <div align="center"><i><font color="#FFFFFF" size="5">Administration 
              des applications <br>
              Identification</font></i></div>
     </td>
</tr>
<form action="Dispatcher"     method="post" name="form"   onsubmit='return control()'>
<tr bgcolor=white><td colspan=2>&nbsp;</td></tr> 
<tr bgcolor=white> 
            <td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">user&nbsp</font></td>
<td><input type="hidden" name="operation" size="8" value='login'>
              <input type="text"   name="code" size="30" value='<%=code%>'>
</td>
</tr>
<tr bgcolor=white>  
<td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Mot de passe&nbsp</font></td>
<td>
              <input type="password" name="password" size="15" onfocus="this.select()">
</td>
</tr>
<tr bgcolor=white> 
            <td colspan=2> 
              <div align="center"><i>identifiants de messagerie<br>
                <font color="#FF0000"><%=libelle%></font> </i></div>
            </td>
          </tr> 
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
 
<div align="center"><a href="Dispatcher">connexion 
  anonyme</a> </div>
</body>
</html>
