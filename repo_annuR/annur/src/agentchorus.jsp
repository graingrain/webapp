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
System.out.println("debut jsp :" + table +".jsp"  );
System.out.println("debut jsp :" + table +".jsp" +" profil="+userinfo.getProfil());
System.out.println("--------------------------------------" );
ArrayList oneagent       = (ArrayList)session.getValue("allasa");
System.out.println("nb de cle : " + oneagent.size());
String nom="";
String prenom="";
int num=0;
String serie="";
String commentaire="";
String date="";
String dateexpiration="";
String daterestitution="";
int ccode=0;
if (oneagent.size() > 0 )
{
ItemAnnuaire E  = (ItemAnnuaire) oneagent.get(0);
ccode=E.getAg_code();
nom=E.getAg_ldap_nom();
prenom=E.getAg_ldap_prenom();
num=E.getAg_gemalto_num();
serie=E.getAg_gemalto_serie();
date=E.getAg_gemalto_charte_signee();
daterestitution=E.getAg_gemalto_date_restitution();
dateexpiration=E.getAg_gemalto_date_expiration();
commentaire=E.getAg_gemalto_commentaire();
}
%>
<HTML>
<head>
<title>Agent Chorus</title>
<script language="JavaScript1.2" src="resources/control.js"></script>
<link rel="stylesheet" href="stylefoad.css" type="text/css" media="screen" />
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
<SCRIPT language="javascript">
   function Valider(formulaire,type) {
      formulaire.bsubmit.value=type;
      formulaire.submit();
}
</SCRIPT>
<BODY  >
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
<%@  include file="include_menu.jsp" %>
<br>
<br>
<table border="0" cellspacing="0" cellpadding="1" width=637
 bgcolor="#000000" align="left">
  <tr>
    <td><table border="0" cellspacing="0" width=635 cellpadding="0" align="center">
        <tr>
          <td bgcolor="#FF9933" colspan=2><div align="center"><i><font color="#FFFFFF" size="5">Agent Chorus <br>
              </font></i></div></td>
        </tr>
        <form action="Dispatcher"     method="post" name="form"  target=_top   onsubmit='return control()'     >
          <tr bgcolor=white>
            <td colspan=2>&nbsp;</td>
          </tr>
          <tr bgcolor=white>
            <td width="155" align="right"><div align="left"><font size="-1" face="PrimaSans BT,Verdana,sans-serif"><font size="-1">Nom </font> </font></div></td>
            <td width="480"><input type="hidden" name="operation" size="8" value='modagentchorus'>
              <input type="hidden" name="code" size="8" value='<%=ccode%>'>
              <input type="hidden" name="table" size="8" value='chorus'>
              <input type="text"   name="nom"      size="20" value='<%=nom%>'>
            </td>
          </tr>
          <tr bgcolor=white>
            <td align="right"><div align="left"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Pr&eacute;n</font><font size="-1" face="PrimaSans BT,Verdana,sans-serif"><font size="-1">om </font> </font></div></td>
            <td><input type="text"   name="prenom"      size="20" value='<%=prenom%>'>
            </td>
          </tr>
          <tr bgcolor=white>
            <td align="right"><div align="left"><font size="-1" face="PrimaSans BT,Verdana,sans-serif"><font size="-1">commentaire</font> </font></div></td>
            <td><input type="text"   name="commentaire"      size="80" value='<%=commentaire%>'>
            </td>
          </tr>
          <tr bgcolor=white>
            <td align="right"><div align="left"><font size="-1" face="PrimaSans BT,Verdana,sans-serif"><font size="-1">numero de s&eacute;rie </font> </font> </div></td>
            <td><input type="text"   name="serie"      size="20" value='<%=serie%>'>
            </td>
          </tr>
          <tr bgcolor=white>
            <td align="right"><div align="left"><font size="-1" face="PrimaSans BT,Verdana,sans-serif"><font size="-1">numero de cl&eacute; </font> </font></div></td>
            <td><input type="text"   name="num"      size="3" value='<%=num%>'>
            </td>
          </tr>
		   <tr bgcolor=white>
            <td align="right"><div align="left"><font size="-1" face="PrimaSans BT,Verdana,sans-serif"><font size="-1">date 
                expiration  du certificat</font> </font></div></td>
            <td><input type="text"   name="dateexpiration"      size="9" value='<%=dateexpiration%>'>
            </td>
          </tr>
          <tr bgcolor=white>
            <td align="right"><div align="left"><font size="-1" face="PrimaSans BT,Verdana,sans-serif"><font size="-1">date signature </font> </font></div></td>
            <td><input type="text"   name="date"      size="9" value='<%=date%>'>
              Attention cette date doit etre saisie avant impression de la charte. 
            </td>
          </tr>
		   <tr bgcolor=white>
            <td align="right"><div align="left"><font size="-1" face="PrimaSans BT,Verdana,sans-serif"><font size="-1">date restitution </font> </font></div></td>
            <td><input type="text"   name="daterestitution"      size="9" value='<%=daterestitution%>'>
            </td>
          </tr>
          <tr bgcolor=white>
            <td colspan=2><p>&nbsp;</p>
              <div align="center">
                <p>
                  <% if (oneagent.size()>0)
				{
				%>
                  <input name="bsubmit" type="submit" value="Modification" width="72" onClick="Valider(this.form,'Modification')">
                  <% } else { %>
                  <input name="bsubmit" type="submit" value="Ajouter" width="72" onClick="Valider(this.form,'Ajouter')">
                  
                  <% } %>
                </p>
                <p>&nbsp; </p>
            </div></td>
          </tr>
        </form>
      </table></td>
  </tr>
</table>
</body>
</html>
