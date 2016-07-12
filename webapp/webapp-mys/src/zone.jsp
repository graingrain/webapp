<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
 
<html>
<head>
<title>zone</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF" text="#000000">
<script language="JavaScript1.1">
function control() {
 
if (testnum0(document.form.port) == false) { alert("info numerique et non nulle"); document.form.port.focus() ;return false; }
if (testnum(document.form.nb22) == false) { alert("info numerique"); document.form.nb22.focus() ;return false; }
if (testnum(document.form.nb6) == false) { alert("info numerique"); document.form.nb6.focus() ;return false; }
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
</script>
<% 
System.out.println("debut jsp:zone"); 
try
{
UserInfo userinfo = (UserInfo)session.getValue("currentUser"); 
 
String vnom=userinfo.getNom();
int profil=userinfo.getProfil();
String voper="";
String nom="";
String prenom="";
String vlibelle="";
String vzn_pt_rep="";
int vpoul=120000; 
int vcode=0;
String  vcouleur="ffffff";
int nb22=0;
int nbrep=0;
 
 
int codepostal=974;
String vcodenom="null";
if (request.getParameter("operation")!=null) voper=request.getParameter("operation");
if (request.getParameter("operation")!=null&&(request.getParameter("operation").equals("modzone")))
{ 
ArrayList liste      = (ArrayList)session.getValue("liste_one_Zone") ;
for (int i=0; i<liste.size(); i++)   
{  
ItemZone item = (ItemZone) liste.get(i);  
vcode=item.getZn_code();
vlibelle=item.getZn_libelle();
vzn_pt_rep=item.getZn_pt_rep();
vpoul=item.getZn_poul();
vcouleur=item.getZn_couleur();
}
} %>  
 <!--  MENU  --> <%@ include file="menu.jsp"%> <!--  MENU  --> 
 <table border="0" cellspacing="0" cellpadding="1" width=501  bgcolor=#9999CC align="center">   <tr><td>
 <table border="0" cellspacing="0" width=798 cellpadding="0" align="center">
              <tr> 
                <td bgcolor="#9999CC" colspan=2> 
                  
            <div align="center"><i><font color="#FFFFFF" size="5">Zone</font></i></div>
                </td>
              </tr>
<form action="Dispatcher" method="post" name="form"   onsubmit='return control()'>
<tr bgcolor=white><td colspan=2>&nbsp;</td></tr> 
<tr bgcolor=white>
            <td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">libelle</font></td>
                  <td><input type="hidden" name="operation" size="8" value='addzone'>
				  
				  <input type="hidden" name="code" size="8"    value="<%=vcode%>">
				   <input type="text" name="libelle" size="60" value="<%=vlibelle%>">
               
            </td></tr>
<tr bgcolor=white>
             <td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">poul</font></td>
                  <td><input type="text" name="poul" size="60" value="<%=vpoul%>"></td></tr>
<tr bgcolor=white>
             
            <td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">r&eacute;pertoire 
              portail </font></td>
                  <td><input type="text" name="zn_pt_rep" size="60" value="<%=vzn_pt_rep%>"></td></tr>
<tr bgcolor=white>
            <td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">couleur:</font></td>
                  <td><select name="ma_code" size="1"> 
<% ArrayList liste_machine       = (ArrayList)session.getValue("liste_machine") ;
for (int i=0; i<liste_machine.size(); i++)   {  ItemApplication item = (ItemApplication) liste_machine.get(i); %>
<option value=<%=item.getMa_code()%>><%=item.getMa_libelle()%></option>
<% } %>                                  
</select>
</td></tr>
<tr bgcolor=white><td colspan=2 >
              <div align="center"> 
                <p>
                  <input type="reset"  name="Submit"   value="R&eacute;tablir">
                  <input type="submit" value="Ajouter" width="72"  name="submit">
				  <input type="submit" value="Modif pouls toutes les zones" width="72"  name="submit">
                   <% if(vcode!=0) { %>
                  <input type="submit" value="Modifier" width="72" name="submit">
                 <% } %>
                </p>
              </div></td></tr></form></table>
</td></tr></table>
<br>
<!-- table liste appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
<table border="0" cellspacing="0" cellpadding="1" width=701  bgcolor=#9999CC align="center">   <tr><td>
      <table border="0" cellspacing="0" width=713 cellpadding="0" align="center">
        <tr> 
          <td bgcolor="#9999CC" colspan=8 height="20"> 
            <div align="center"><font color="#FFFFFF" size="4"><i>Liste des zones</i></font> 
            </div>
          </td>
        </tr>
        <tr bgcolor=white > 
          <td width="107"> 
            <div align="center"><b><i><font color="#9999CC">Code</font></i></b></div>
          </td>
          <td width="107"> 
            <div align="center"><b><i><font color="#9999CC">Libelle</font></i></b></div>
          </td>
          <td width="80"> 
            <div align="center"><b><i><font color="#9999CC"> </font></i></b></div>
          </td>
          <td width="180"> 
            <div align="center"><b><i><font color="#9999CC">Machine</font></i></b></div>
          </td>
          <td width="80"> 
            <div align="center"><b><i><font color="#9999CC">Poul</font></i></b></div>
          </td>
          <td width="80"> 
            <div align="center"><b><i><font color="#9999CC"> </font></i></b></div>
          </td>
          <td width="107"> 
            <div align="center"><b><i><font color="#9999CC">Date </font></i></b></div>
          </td>
          <td width=72> 
            <div align="center"><b><i><font color="#9999CC">action</font></i></b></div>
          </td>
        </tr>
        <% ArrayList liste       = (ArrayList)session.getValue("liste_Zone") ;
for (int i=0; i<liste.size(); i++)   {  ItemZone item = (ItemZone) liste.get(i); %>
        <tr bgcolor=white > 
          <td width="107"> 
            <div align="center"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
              <%=item.getZn_code()%></font></div>
          </td>
          <td width="107"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
            <%=item.getZn_libelle()%></font></td>
          <td width="80"> 
            <div align="left"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif">   </font></div>
          </td>
          <td width="80" bgcolor="#FFFFFF"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
            <%=item.getZn_machine()%></font></td>
          <td width="80"> 
            <div align="center"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
              <%=item.getZn_poul()%></font></div>
          </td>
          <td width="80"> </td>
          <td width="107"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
            <%=item.getZn_date()%></font></td>
          <td width="72"><font face="Arial, Helvetica, sans-serif" size="2"> <a href="Dispatcher?operation=supzone&code=<%=item.getZn_code()%>"><img src="images/poubelle.gif" width="14" height="14" border="0"></a> 
            <a href="Dispatcher?operation=modzone&code=<%=item.getZn_code()%>"><img src="avis4.gif" width="19" height="15" border="0"></a> 
            <a href="Dispatcher?operation=voirzone&code=<%=item.getZn_code()%>"><img src="images/menu.gif" width="23" height="20" border="0"></a> 
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

