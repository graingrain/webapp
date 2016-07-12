<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
<%@ page errorPage="PbGeneral.jsp" %>
<html>
<head>
<title>domaine</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF" text="#000000">
<script language="JavaScript1.1">
function control() {
 
if (teststring(document.form.nom) == false) { alert("a saisir !"); document.form.nom.focus() ;return false; }
if (teststring(document.form.user) == false) { alert("a saisir !"); document.form.user.focus() ;return false; }
if (testnum(document.form.portc)   == false) { alert("num !"); document.form.portc.focus() ;return false; }
 
if (document.form.wa_code.value.length == 0) {
   alert("Le choix du webapp est obligatoire \nCorrigez le ...")
   document.form.wa_code.focus()
   return false
}
}
function testnum(champ)
{   this.champ=champ
    if (isNaN(this.champ.value)||(this.champ.value.indexOf (' ',0) != -1)||(this.champ.value.length == 0) )
    {   return false   }
    return true
} 
function testnum0(champ)
{   this.champ=champ
    if (isNaN(this.champ.value)||(this.champ.value.indexOf (' ',0) != -1)||(this.champ.value.length == 0)||(this.champ.value == 0))
    {   return false   }
    return true
} 
function teststring(champ)
{   this.champ=champ
    if ( this.champ.value.length == 0)
    {   return false   }
    return true
} 
</script>
<% 
System.out.println("debut jsp:DomaineWA"); 
try
{
UserInfo userinfo = (UserInfo)session.getValue("currentUser"); 
String vnom=userinfo.getNom();
int profil=userinfo.getProfil();
String zone1="bgcolor='#FFFF00'";
String zone2="bgcolor='#FF9900'";
String zone3="bgcolor='#99CCFF'"; 
 
String voper="";
 
String vlibelle="";
String vuser="";
String vpasswd="";
int vportc=0;
int vcode=0;
if (request.getParameter("operation")!=null) voper=request.getParameter("operation");
if (request.getParameter("operation")!=null&&(request.getParameter("operation").equals("moddomaineWA")))
{ 
ArrayList listeD      = (ArrayList)session.getValue("liste_one_DomaineWA") ;
for (int i=0; i<listeD.size(); i++)   
{  
ItemApplication item = (ItemApplication) listeD.get(i);  
vcode=item.getDo_code();
vnom=item.getDo_nom();
vlibelle=item.getDo_libelle();
vuser=item.getDo_user();
vpasswd=item.getDo_passwd();
vportc=item.getDo_portc();
 
}  
} %>
 
<!--  MENU  --> <%@ include file="menu.jsp"%> <!--  MENU  --> 
<!-- table appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
<table border="0" cellspacing="0" cellpadding="1" width=501  bgcolor=#9999CC align="center">   <tr><td>
<table border="0" cellspacing="0" width=500 cellpadding="0" align="center">
<tr><td bgcolor="#9999CC" colspan=2>
            <div align="center"><i><font color="#FFFFFF" size="5">Ajouter un DomaineWebapp</font></i></div>
          </td></tr>
<form action="Dispatcher" method="post" name="form"   onsubmit='return control()'>
<tr bgcolor=white><td colspan=2>&nbsp;</td></tr> 
<tr bgcolor=white>
            <td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">nom 
              domaine </font></td>
                  <td>
              <input type="text" name="nom" size="60" value="<%=vnom%>">
            </td></tr>
				  <tr bgcolor=white>
            <td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">libelle</font></td>
                  <td>
              <textarea name="libelle" cols="60" rows="3"><%=vlibelle%></textarea>
            </td></tr>
<tr bgcolor=white><td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">user</font></td>
                  <td><input type="hidden" name="operation" size="8" value='adddomaineWA'>
<input type="text"   name="user" size="20" value="<%=vuser%>"></td></tr>
<tr bgcolor=white><td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">passwd</font></td>
                  <td><input type="text" name="passwd" size="60" value="<%=vpasswd%>"></td></tr>
<tr bgcolor=white><td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">port console</font></td>
                  <td><input type="text" name="portc" size="5" value="<%=vportc%>">
				  
				    <input type="hidden" name="code"      size="8" value="<%=vcode%>"></td></tr>
<tr bgcolor=white><td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">webapp:</font></td>
                  <td><select name="wa_code" size="1"><option value=""> 
<% ArrayList listeW        = (ArrayList)session.getValue("liste_webapp") ;
for (int i=0; i<listeW.size(); i++)   {  ItemApplication item = (ItemApplication) listeW.get(i); %>
<option value=<%=item.getWa_code()%>,<%=item.getWa_ma_code()%>><%=item.getMa_libelle()%>,<%=item.getWa_type()%> sur <%=item.getWa_repertoire()%></option>
<% } %>                                  
</select>
</td></tr>
<tr bgcolor=white><td colspan=2 >
              <div align="center"> 
                <p>
                  <input type="reset"  name="Submit"   value="R&eacute;tablir">
                  <input type="submit" value="Ajouter" width="72"  name="submit">
                   <% if(vcode!=0) { %>
                  <input type="submit" value="Modifier" width="72" name="submit">
                 <% } %>
                </p>
              </div></td></tr>
</form></table>
</td></tr></table>
<br>
<!-- table liste appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
<table border="0" cellspacing="0" cellpadding="1" width=801  bgcolor=#9999CC align="center">   <tr><td height="154">
      <table border="0" cellspacing="0" width=800 cellpadding="0" align="center">
        <tr> 
          <td bgcolor="#9999CC" colspan=8> 
            <div align="center"><font color="#FFFFFF" size="4"><i>Liste des Domaines-webapps</i></font> 
            </div>
          </td>
        </tr>
        <tr bgcolor=white > 
          <td> 
            <div align="center"><font color="#9999CC" size="2" face="Arial, Helvetica, sans-serif"><b><i>Zone</i></b></font></div>
          </td>
          <td><div align="center"><font color="#9999CC" size="2" face="Arial, Helvetica, sans-serif"><b><i>Machine</i></b></font></div></td>
          <td><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC"> Webapp</font></i></b></font></td>
          <td width="91"> 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">Domaine</font></i></b></font></div>
          </td>
          <td width="77"> 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">User</font></i></b></font></div>
          </td>
          <td width="89"> 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">Password</font></i></b></font></div>
          </td>
          <td width="93"> 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">Port 
              console</font></i></b></font></div>
          </td>
           
           
          <td width=83> 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">action</font></i></b></font></div>
          </td>
        </tr>
        <% ArrayList liste       = (ArrayList)session.getValue("liste_DomaineWA") ;
for (int i=0; i<liste.size(); i++)   {  ItemApplication item = (ItemApplication) liste.get(i); %>
        <tr bgcolor=white height=20> 
          <td width="41"  
		  <%        if(item.getMa_zn_code()==1) out.println(zone1);
                    if(item.getMa_zn_code()==2) out.println(zone2);
                    if(item.getMa_zn_code()==3) out.println(zone3);  %>> 
            <div align="center"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
              <%=item.getMa_zn_code()%></font></div>
          </td>
          <td width="73"><div align="left"><font color="#000033" size="-1" face="Arial, Helvetica, sans-serif"> 
          <%=item.getMa_sigle()%> </font></div></td>
          <td width="255"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif">
              <%=item.getWa_type()%><%=item.getWa_version()%></font></td>
          <td width="91"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
            <b><i><%=item.getDo_nom()%></i></b></font></td>
          <td width="77"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
            <%=item.getDo_user()%></font></td>
          <td width="89"> 
            <div align="center"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"><%=item.getDo_passwd()%></font></div>
          </td>
          <td width="93" bgcolor="#FFFFFF"> 
            <div align="center"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
              <%=item.getDo_portc()%></font></div>
          </td>
           
          <td width="83"><font face="Arial, Helvetica, sans-serif" size="2"> 
            <% if (item.getNb_joint()== 0) { %>
            <a href="Dispatcher?operation=supdomainewa&code=<%=item.getDo_code()%>"> 
            <img src="images/poubelle.gif" width="14" height="14" border="0"></a> 
            <% } else { %>
            <img src="images/poubelle0.gif" width="14" height="14" border="0"> 
            <% } %>
            <a href="Dispatcher?operation=moddomaineWA&code=<%=item.getDo_code()%>"><img src="avis4.gif" width="19" height="15" border="0"></a> 
            </font></td>
        </tr>
        <% } 
}
catch(IOException e)
 	{	System.out.println("<H2>"+"ClassNotFoundException: " + e.getMessage() + "<BR>");
 	}
%>
      </table>
</td></tr></table>
<br>

<div align="center"><img src="images/logo_java.gif" width="103" height="53"> <img src="images/apache_pb.gif" width="259" height="32"> 
</div>
</body>
</html>

