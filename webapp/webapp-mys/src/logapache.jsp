<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
<%@ page errorPage="PbGeneral.jsp" %>
<html>
<head>
<title>logapache</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Refresh"      Content="60">
</head>

<body bgcolor="#FFFFFF" text="#000000">
<div align="center">
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
int nbpb=0;
 
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
 <!--  MENU  --> <%@ include file="menu.jsp"%> <!--  MENU  --> 
  <!-- table appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
  <!--  MENU  -->
   
  <!--  MENU  -->
  <!-- table liste appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
  <font face="Arial, Helvetica, sans-serif" size="6">Liste des services en exploitation, 
  <%if(request.getParameter("sort")==null) { %>
  par serveur Web </font><br>
  <i><font color="#000000" face="Arial, Helvetica, sans-serif" size="2">affichage 
  par <a href="Dispatcher?operation=logapache&sort=webapp">serveur applicatif</a></font></i> 
  <font color="#000000">
  <% } else { %>
  </font> <font face="Arial, Helvetica, sans-serif" font size="6">par serveur 
  applicatif </font> <br>
  <i><font face="Arial, Helvetica, sans-serif" size="2">affichage par <a href="Dispatcher?operation=logapache">serveur 
  web</a></font></i> <font face="Arial, Helvetica, sans-serif" size="2"> 
  <% } %>
  </font> </div>
<table border="0" cellspacing="0" cellpadding="1" width=746  bgcolor=#9999CC align="center">
  <tr><td>
      <table border="0" cellspacing="0" width=745
	   cellpadding="0" align="center">
        <% ArrayList liste       = (ArrayList)session.getValue("liste_log_apache") ;
String oldportail="";
for (int i=0; i<liste.size(); i++)   {  ItemApplication item = (ItemApplication) liste.get(i); 
if(request.getParameter("sort")==null) {
if (!(item.getLa_machine().equals(oldportail))) { %>
        <tr bgcolor="#FFFFFF"> 
          <td colspan=5>&nbsp; </td>
        </tr>
        <tr> 
          <td bgcolor="#9999CC" colspan=5> 
            <div align="left"><font color="#FFFFFF" size="4"> <i>Nom du serveur 
              web: <b><%=item.getLa_machine()%></b> </i> </font></div>
          </td>
        </tr>
        <tr bgcolor=#9999CC > 
          <td width="192"><i><font color="#FFFFFF" face="Arial, Helvetica, sans-serif" size="2">webapp 
            </font></i></td>
          <td width="304"><i><font face="Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">service 
            </font></i></td>
          <td width="62"> 
            <div align="right"><i><font face="Arial, Helvetica, sans-serif" size="2" color="#FFFFFF"> 
              socket </font></i></div>
          </td>
          <td width="54"> 
            <div align="center"><i><font face="Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">etat</font></i></div>
          </td>
          <td width="133"><i><font color="#FFFFFF" size="2" face="Arial, Helvetica, sans-serif">date</font> 
            </i></td>
        </tr>
        <% oldportail=item.getLa_machine();
}
}
else 
{
if ((item.getLa_webapp()!=null)&&!(item.getLa_webapp().equals(oldportail))) { %>
        <tr bgcolor="#FFFFFF"> 
          <td colspan=5>&nbsp; </td>
        </tr>
        <tr> 
          <td bgcolor="#9999CC" colspan=5> 
            <div align="left"><font color="#FFFFFF" size="4"> <i>Nom du serveur 
              applicatif: <b><%=item.getLa_webapp()%> </i> </font></div>
          </td>
        </tr>
        <tr bgcolor=#9999CC > 
          <td width="192"><i><font color="#FFFFFF" face="Arial, Helvetica, sans-serif" size="2">portail</font></i></td>
          <td width="304"><i><font face="Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">service 
            </font></i></td>
          <td width="62"> 
            <div align="right"><i><font face="Arial, Helvetica, sans-serif" size="2" color="#FFFFFF"> 
              socket </font></i></div>
          </td>
          <td width="54"> 
            <div align="center"><i><font face="Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">etat</font></i></div>
          </td>
          <td width="133"><i><font color="#FFFFFF" size="2" face="Arial, Helvetica, sans-serif">date</font> 
            </i></td>
        </tr>
        <% oldportail=item.getLa_webapp();
}

}
%>
        <tr bgcolor=white > 
          <td width="192"><font color="#000033"  > 
            <%if(request.getParameter("sort")==null) { %>
            <%=item.getLa_webapp()%> 
            <%} else {%>
            <%=item.getLa_machine()%> 
            <% } %>
            </font></td>
          <td width="304"> 
            <div align="left"><font color="#000033"  ><%=item.getLa_service()%></font></div>
          </td>
          <td width="62"> 
            <div align="right"><font color="#000033"  > <%=item.getLa_port()%></font> 
            </div>
          </td>
          <td width="54"> 
            <div align="center"> 
              <% if (item.getLa_etat()!=null && (item.getLa_etat().indexOf("1")!=-1) ) { %>
              <img src="images/boule_1.gif" width="20" height="20"> 
              <% } else { nbpb++; %>
              <img src="images/boule_2.gif" width="20" height="20"> 
              <% } %>
            </div>
          </td>
          <td width="133"><font color="#000033" size="1"> <%=item.getLa_etat_date()%></font> 
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
<div align="center"><br>
  <br>
   
<% if (nbpb>1000) { %>   Arret alarme:<!-- <embed src="alerte.wma" autostart="true" loop="true" volume=70% border="1"    > -->
<%  } %>  
</div>
<div align="center">
  <p><img src="images/logo_java.gif" width="103" height="53"> <img src="images/apache_pb.gif" width="259" height="32"></p>
  <p>&nbsp; </p>
</div>
</body>
</html>

