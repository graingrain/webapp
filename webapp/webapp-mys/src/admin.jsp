<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
<%@ page errorPage="PbGeneral.jsp" %>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF" text="#000000">
<script language="JavaScript1.1">
function control() {
 
if (testnum0(document.form.nb) == false) { alert("info numerique et non nulle"); document.form.nb.focus() ;return false; }
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
ServletContext sc = getServletContext();
UserInfo userinfo = (UserInfo)session.getValue("currentUser"); 
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
ItemAgent item      = (ItemAgent)session.getValue("item_agent");
nom=item.getNom();
prenom=item.getPrenom();
norue=item.getNorue();
rue=item.getRue();
codepostal=item.getCodepostal();
ville=item.getVille();
qualite=item.getQualite();
tel="0"+item.getTel();
fax="0"+item.getFax();
gsm="0"+item.getGsm();
} %>
 


<p align="center"><font size="6"><i><font color="#000099"><img src='images/logodebat.gif' border=0> 
<%=sc.getAttribute("titre")%>
</font></i></font></p>
<p align="center"><font color="#000099"><i><font size="4">     </font></i></font></p>
<p align="center"><font color="#000099"><i><font size="4"><%=userinfo.getPrenom()%>-<%=userinfo.getAgnom()%></font></i></font></p>
 
   <%if (!(userinfo.getPrenom().equals("9740049K")))
{
%> 
<form method="post" name=form action="Dispatcher" ONSUBMIT='return control();'>
  <input type="hidden" name="operation" value="ajout">
  <input type="hidden" name="codeetab"  value=<%=userinfo.getPrenom()%>>
  <input type="hidden" name="etab"      value=<%=userinfo.getPrenom()%>>
  <input type="hidden" name="code"      value=<%=request.getParameter("code")%>>
  
 <input type="hidden" name="codenom"   value=<%=vcodenom%>>
 <table border="0" cellspacing="0" cellpadding="1" width=502
 bgcolor="#000000" align="center">
  <tr>
    <td>
 
 <table border="0" cellspacing="0" width=500 cellpadding="0" align="center">
          <tr> 
            <td bgcolor="#9999CC" colspan="2"> 
              <div align="center"><i><font color="#FFFFFF" size="5">Fiche de participation</font></i></div>
            </td>
          </tr>
		  <tr> 
            <td bgcolor="FFFFFF" colspan=2
			> 
              <div align="center"><i><font color="#FFFFFF" size="5">&nbsp
			  </font></i></div>
            </td>
          </tr>
          <tr bgcolor="FFFFFF"> 
            <td width="57%"> 
              <div align="right"><b><i><font color="#9999CC" size="3">Nb de lieux 
                de d&eacute;bat &nbsp&nbsp</font></i></b></div>
            </td>
            <td width="43%"><b><i><font color="#9999CC"> 
              <input type="text" name="nb" size=10 value='<%=userinfo.getNb()%>'>
              </font></i></b></td>
          <tr bgcolor="FFFFFF"> 
            <td width="57%"> 
              <div align="right"><b><i><font color="#9999CC" size="3">Nb de participants 
                le 22 novembre&nbsp&nbsp</font></i></b></div>
            </td>
            <td width="43%"><b><i><font color="#9999CC"> 
              <input type="text" name="nb22" size=10 value='<%=userinfo.getNb22()%>'>
              </font></i></b></td>
          </tr bgcolor="FFFFFF">
          <tr bgcolor="FFFFFF"> 
            <td width="57%"> 
              <div align="right"><b><i><font color="#9999CC" size="3">Nb de participants 
                le 6 décembre&nbsp&nbsp </font></i></b></div>
            </td>
            <td width="43%"><b><i><font color="#9999CC"> 
              <input type="text" name="nb6" size=10 value='<%=userinfo.getNb6()%>'>
              </font></i></b></td>
          </tr bgcolor="FFFFFF">
          <tr bgcolor="FFFFFF"> 
            <td colspan="2" height="100"> 
              <div align="center">
                <p>&nbsp;</p>
                <p><b><i><font color="#9999CC" size="3"> 
                  <input type="submit" name="Submit" value="Envoyer">
                  </font></i></b></p>
                <p>&nbsp;</p>
              </div>
            </td>
          </tr bgcolor="FFFFFF">
        </table>
     </td>
  </tr>
</table>
</form>
<% } else { %>

<table border="0" cellspacing="0" cellpadding="1" bgcolor="#000000" align="center">
  <tr>
    <td>
      <table border="0" cellspacing="0" width=800 cellpadding="0" align="center">
        <tr bgcolor=red> 
          <td    align="center" valign="middle" width="75"> 
            <div align="center"><font color="#FFFFFF"><i>Code</i></font></div>
          </td>
          <td  valign="middle"> 
            <div align="center"><font color="#FFFFFF"><i>Nom</i></font></div>
          </td>
          <td   align="center" valign="middle" width="90"> 
            <div align="center"><font color="#FFFFFF"><i>nb lieux</i></font></div>
            <div align="center"></div>
          </td>
          <td   align="center" valign="middle" width="164"> 
            <div align="center"><font color="#FFFFFF"><i>22 nov</i></font></div>
            <div align="center"></div>
          </td>
          <td   align="center" valign="middle" width="55"> 
            <div align="center"><font color="#FFFFFF"><i>6 dec</i></font></div>
          </td>
        </tr>
         
<% ArrayList liste       = (ArrayList)session.getValue("liste_machine") ;
   for (int i=0; i<liste.size(); i++)
   {  ItemMachine item = (ItemMachine) liste.get(i); 
 
%>
        <tr bgcolor=white >  
          <td width="75"><font color="#000033" size="8">     <%=item.getMa_libelle()%>      </font></td>
          <td  >    <div align="left"><font color="#000033" size="1"> <%=item.getNom()%></font></div>  </td>
          <td  >    <div align="center"><font color="#000033" size="1"><%=item.getNb()%></font></div>    </td>
          <td  >    <div align="center"><font color="#000033" size="1"><%=item.getNb22()%>&nbsp</font></div> </td>
          <td  >    <div align="center"><font color="#000033" size="1"><%=item.getNb6()%>&nbsp</font></div> </td>
  </tr>
<% } %> 
<tr bgcolor=yellow >  
          <td width="75"><font color="#000033" size="1">   total     </font></td>
          <td  >    <div align="left"><font color="#000033" size="1"> <%=nbrep%></font></div>  </td>
          <td  >    <div align="center"><font color="#000033" size="1"><%=nb%></font></div>    </td>
          <td  >    <div align="center"><font color="#000033" size="1"><%=nb22%>&nbsp</font></div> </td>
          <td  >    <div align="center"><font color="#000033" size="1"><%=nb6%>&nbsp</font></div> </td>
  </tr>
</table>
      </td>
  </tr>
</table><center><i>Tableau des résultats</i>





<form method="post" name=form action="Dispatcher" ONSUBMIT='return control();'>
<br>
<br>
<table border="0" cellspacing="0" cellpadding="1" width=801 bgcolor="#000000" align="center">
  <tr>
    <td>
         <table border="0" cellspacing="0" width=800 cellpadding="0" align="center">
  
<tr><TD colspan=2 bgcolor=red align=center><font color=white>Modification  des éléments variables</font></td></tr>
<tr bgcolor=white><td>titre</td><td>

  <input type="hidden" name="operation" value="admin">
  <input type="hidden" name="codeetab"  value=<%=userinfo.getPrenom()%>>
  <input type="hidden" name="etab"      value=<%=userinfo.getPrenom()%>>
  <input type="hidden" name="code"      value=<%=request.getParameter("code")%>>
  <input type="text"   name="titre"      size=80  value="<%=sc.getAttribute("titre")%>"<br>
  </tD></tr>
<tr bgcolor=white><td align=center>message 1</td><td><input type="text"   name="message1"   size=80  value='<%=sc.getAttribute("message1")%>'<br></td></tr>  
<tr bgcolor=white><td align=center>message 2</td><td><input type="text"   name="message2"   size=80  value='<%=sc.getAttribute("message2")%>'<br></td></tr> 
<tr bgcolor=white><td align=center>message 3</td><td><input type="text"   name="message3"   size=80  value='<%=sc.getAttribute("message3")%>'<br></td></tr> 
<tr bgcolor=white><td colspan=2 align=center>  <input type="submit" name="Submit" value="Modifier les éléments variables"></td></tr bgcolor="FFFFFF">
        </table>
     </td>
  </tr>
</table>
</form>
<% } %>

<center><font color=blue>
<% if (sc.getAttribute("message1") != null) %><%=sc.getAttribute("message1")%><br>
<% if (sc.getAttribute("message2") != null) %><%=sc.getAttribute("message2")%><br>
<% if (sc.getAttribute("message3") != null) %><%=sc.getAttribute("message3")%></font>

<center><br><a href="javascript:close()" Quitter l application</a>Quitter l' application


 </body>
</html>

