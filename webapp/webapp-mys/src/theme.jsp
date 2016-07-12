<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
 
<html>
<head>
<title>theme</title>
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
System.out.println("debut jsp: theme"); 
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
System.out.println("user:"+userinfo.getNom()); 
 
int codepostal=974;
String vcodenom="null";
if (request.getParameter("operation")!=null) voper=request.getParameter("operation");
if (request.getParameter("operation")!=null&&(request.getParameter("operation").equals("modtheme")))
{ 
ArrayList liste      = (ArrayList)session.getValue("liste_one_Theme") ;
for (int i=0; i<liste.size(); i++)   
{  
ItemApplication item = (ItemApplication) liste.get(i);  
vcode=item.getTh_code();
vlibelle=item.getTh_libelle();
vcouleur=item.getTh_couleur();
}
} %>  
 <!--  MENU  --> <%@ include file="menu.jsp"%> <!--  MENU  --> 
 <table border="0" cellspacing="0" cellpadding="1" width=402 bgcolor=#9999CC align="center">   
   <tr><td width="798">
 <table border="0" cellspacing="0" width=400 cellpadding="0" align="center">
              <tr> 
                <td bgcolor="#9999CC" colspan=2> 
                  
            <div align="center"><i><font color="#FFFFFF" size="5">Theme</font></i></div>
                </td>
              </tr>
<form action="Dispatcher" method="post" name="form"   onsubmit='return control()'>
<tr bgcolor=white><td colspan=2>&nbsp;</td></tr> 
<tr bgcolor=white>
            <td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">libelle</font></td>
                  <td><input type="hidden" name="operation" size="8" value='addtheme'>
				  
				  <input type="hidden" name="code" size="8"    value="<%=vcode%>">
				   <input type="text" name="libelle" size="60" value="<%=vlibelle%>">
               
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
              </div></td></tr></form></table>
</td></tr></table>
<br>
<!-- table liste appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
<table border="0" cellspacing="0" cellpadding="1" width=701  bgcolor=#9999CC align="center">   <tr><td>
      <table border="0" cellspacing="0" width=713 cellpadding="0" align="center">
        <tr> 
          <td height="20" colspan="3" bgcolor="#9999CC"> 
            <div align="center"><font color="#FFFFFF" size="4"><i>Liste des theme </i></font>            </div>          </td>
        </tr>
        <tr bgcolor=white > 
          <td width="280"> 
          <div align="center"><b><i><font color="#9999CC">Code</font></i></b></div>          </td>
          <td width="318"> 
          <div align="center"><b><i><font color="#9999CC">Libelle</font></i></b></div>          </td>
          
          
          <td width="115">&nbsp;</td>
        </tr>
        <% ArrayList liste       = (ArrayList)session.getValue("liste_Theme") ;
for (int i=0; i<liste.size(); i++)   {  ItemApplication item = (ItemApplication) liste.get(i); %>
        <tr bgcolor=white > 
          <td width="280"> 
            <div align="center"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
              <%=item.getTh_code()%></font></div>          </td>
          <td  ><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
            <%=item.getTh_libelle()%></font><font face="Arial, Helvetica, sans-serif" size="2"> 
            </td>
          <td><a href="Dispatcher?operation=suptheme&code=<%=item.getTh_code()%>"><img src="images/poubelle.gif" width="14" height="14" border="0"></a> 
            <a href="Dispatcher?operation=modtheme&code=<%=item.getTh_code()%>"><img src="avis4.gif" width="19" height="15" border="0"></a> 
              </td>
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

