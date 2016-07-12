<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
<%@ page errorPage="PbGeneral.jsp" %>
<% request.setAttribute("sourcePage",request.getRequestURI()); %>
<% 
ServletContext sc = getServletContext();
UserInfo userinfo = (UserInfo)session.getValue("currentUser");
String table="login";
System.out.println("debut jsp ------:" + table +".jsp"  );
System.out.println("debut jsp ------:" + table +".jsp" +" profil="+userinfo.getProfil());
System.out.println("debut jsp ------:" + table +".jsp" +" erreur="+userinfo.getError_message());
System.out.println("----------------------------------------------------------------------" );
ArrayList alldivision       = (ArrayList)session.getValue("alldivision");
%>
<HTML>
<head>
<title><%=table%></title>
<link rel="shortcut icon" type="images/x-icon" href="favicon-annuaire.png" />
<script language="JavaScript1.2" src="resources/control.js"></script>
<link href="boiler.plate.css" rel="stylesheet" type="text/css">
<link href="organigramme.ac-reunion.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="menu.organigramme.css" type="text/css" media="screen" />
</head>
<script language="JavaScript">
function control() {
if (document.form.code.value.length == 0) {
   alert("Le Rne est obligatoire \nCorrigez le ...")
   document.form.code.focus()
   return false
}
if (document.form.password.value.length == 0) {
   alert("Le Mot de passe est obligatoire \nCorrigez le ...")
   document.form.password.focus()
   return false
}
if (document.form.password.value.length < 3) {
   alert("Le Mot de passe doit contenir 3 caractères minimum \nCorrigez le ...")
   document.form.password.focus()
   return false
}
}
</script>
<body>
<%
 
String code="";
String libelle="";
if (request.getAttribute("erreur")!=null) libelle=(String) request.getAttribute("erreur");    
else
if (request.getParameter("code")!=null)  
{
  code=request.getParameter("code");
  libelle=userinfo.getError_message(); 
}
%>
<%@  include file="include_menu-karine.jsp" %>
 <br>
<div align="center">
  <table border="0" cellspacing="0" cellpadding="1" width=301
 bgcolor="#3b8211"  >
    <tr>
    <td><table border="0" cellspacing="0" width=300 cellpadding="0" align="center">
          <tr bgcolor="#3b8211"> 
            <td colspan=2> 
              <div align="center"><i><font color="#FFFFFF" size="5">Ldap Identification<br>
              </font></i></div></td>
        </tr>
        <form action="Dispatcher"     method="post" name="form"  target=_top   onsubmit='return control()'     >
          <tr bgcolor=white>
            <td colspan=2>&nbsp;</td>
          </tr>
          <tr bgcolor=white>
            <td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif"> Compte utilisateur </font></td>
            <td><input type="hidden" name="operation" size="8" value='login'>
              <input type="text"   name="code"      size="20" value='<%=code%>'>
            </td>
          </tr>
          <tr bgcolor=white>
            <td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif"> Mot de passe </font></td>
            <td><input type="password" name="password" size="6" onFocus="this.select()">
            </td>
          </tr>
          <tr bgcolor=white>
            <td colspan=2> </td>
          </tr>
          <tr bgcolor=white>
            <td colspan=2><div align="center">
                <input type="submit" value="Connexion" width="72">
              </div></td>
          </tr><tr bgcolor=white>
            <td height="50" colspan=2 align="center" valign="middle"><div align="center"><%=userinfo.getError_message()%>
                 
            </div></td>
          </tr>
        </form>
      </table></td>
  </tr>
</table>
 
<p>&nbsp;</p></div>

</body>
</html>
