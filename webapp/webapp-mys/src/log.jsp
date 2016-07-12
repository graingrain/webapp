<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
<%@ page errorPage="PbGeneral.jsp" %>
<html>
<head>
<title>log</title>
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
System.out.println("debut jsp:log "); 
try
{
UserInfo userinfo = (UserInfo)session.getValue("currentUser"); 
String vnom=userinfo.getNom();
int profil=userinfo.getProfil();
String voper="";
String nom="";
String prenom="";
String norue="";
String rue="";
String ville="";
String qualite="";
String tel="0262";
String fax="0262";
String gsm="0692";
String observation=""; 
int nb=0;
int nb6=0;
int nb22=0;
int nbrep=0;
 
 
int codepostal=974;
String vcodenom="null";
if (request.getParameter("operation")!=null) voper=request.getParameter("operation");
if (request.getParameter("operation")!=null&&(request.getParameter("operation").equals("mod"))||(request.getParameter("operation").equals("ajo")))
{ 
 
} %>
<!-- table appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
<!--  MENU  --> <%@ include file="menu.jsp"%> <!--  MENU  -->  
<!-- table liste appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
<table border="0" cellspacing="0" cellpadding="1" width=701  bgcolor=#9999CC align="center">   <tr><td>
<table border="0" cellspacing="0" width=700 cellpadding="0" align="center">
  <tr> 
    <td bgcolor="#9999CC" colspan=6> 
            <div align="center"><font color="#FFFFFF" size="4"><i>Journal des 
              &eacute;venements </i></font></div>
    </td>
  </tr>
  <tr bgcolor=white > 
           
          <td width="290"> 
            <div align="center"><b><i><font color="#9999CC">Libelle</font></i></b></div>
    </td>
          <td width="61"> 
            <div align="center"><b><i><font color="#9999CC">type</font></i></b></div>
    </td>
          <td width="93"> 
            <div align="center"><b><i><font color="#9999CC">type_code</font></i></b></div>
    </td>
          
          <td width="73"> 
            <div align="center"><b><i><font color="#9999CC">Etat</font></i></b></div>
    </td>
          <td width="101"> 
            <div align="center"><b><i><font color="#9999CC">Date Etat</font></i></b></div>
    </td>
          <td width=82> 
            <div align="center"><b><i><font color="#9999CC">action</font></i></b></div>
    </td>
  </tr>
  <% ArrayList liste       = (ArrayList)session.getValue("liste_log") ;
for (int i=0; i<liste.size(); i++)   {  ItemLog item = (ItemLog) liste.get(i); %>
  <tr bgcolor=white > 
          
          <td width="290"><font color="#000033" size="1"> <%=item.getLg_libelle()%></font></td>
          <td width="61"> 
            <div align="center"><font color="#000033" size="1"><%=item.getLg_type()%></font></div></td>
    
          <td width="93"> 
            <div align="center"><font color="#000033" size="1"> <%=item.getLg_type_code()%></font></div></td>
    
  
          <td width="73"> 
            <div align="center"><% if (item.getLg_etat()!=null && item.getLg_etat().equals("1") ) { %>
         <img src="images/boule_bleue.jpg" width="15" height="15"> 
                                           <% } else { %><img src="images/boule_rouge.jpg" width="15" height="15"><% } %></div></td>
          <td width="101"><font color="#000033" size="1"> <%=item.getLg_etat_date()%></font></td>
          <td width="82">
		  <% if (profil>0) { %>
		  <a href="Dispatcher?operation=suplog&code=<%=item.getLg_code()%>"><img src="images/poubelle.gif" width="14" height="14" border="0"></a> <% } %>
            <a href="Dispatcher?operation=voirlog&code=<%=item.getLg_code()%>"><img src="avis4.gif" width="19" height="15" border="0"></a> 
			
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

