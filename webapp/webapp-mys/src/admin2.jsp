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
 
} %>
 
<table border="0" cellspacing="0" width=700 cellpadding="0" align="center">
<tr><td bgcolor="#9999CC" colspan=8>
<div align="center"><i><font color="#FFFFFF" size="4">Liste des machines</font></i></div>
</td></tr><div><font color="#9999CC" size="2">
<tr bgcolor=white  ><td>Code</td><td>Nom</td><td>Sigle</td>
<td>Adresse</td><td>Zone</td><td>Etat</td><td>Date Etat</td><td width=20>action</td></tr></div>
<% ArrayList liste       = (ArrayList)session.getValue("liste_machine") ;
for (int i=0; i<liste.size(); i++)   {  ItemMachine item = (ItemMachine) liste.get(i); %>
<tr bgcolor=white >  
    <td width="100"><font color="#000033" size="1">    <%=item.getMa_code()%>         </font></td>
    <td width="100"><font color="#000033" size="1">    <%=item.getMa_libelle()%>      </font></td>
    <td width="75"><font color="#000033" size="1">     <%=item.getMa_sigle()%>        </font></td>
    <td width="75"><font color="#000033" size="1">     <%=item.getMa_adresse()%>      </font></td>
    <td width="75"><font color="#000033" size="1">     <%=item.getMa_zn_code()%>      </font></td>
    <td width="75"><font color="#000033" size="1">     <%=item.getMa_etat()%>         </font></td>
    <td width="100"><font color="#000033" size="1">    <%=item.getMa_etat_date()%>    </font></td>
    <td><a href="Dispatcher?operation=supmachine&code=<%=item.getMa_code()%>">
        <img src="avis3.gif" width="19" height="15" border="0"></a> 
       <a href="Dispatcher?operation=modmachine&code=<%=item.getMa_code()%>">
       <img src="avis4.gif" width="19" height="15" border="0"></a>
 </td>
</tr><% } %></table>
     </td>
  </tr>
</table> 
<br>

<table border="0" cellspacing="0" width=600 cellpadding="0" align="center">
<tr><td bgcolor="#9999CC" colspan=2>
     <div align="center"><i><font color="#FFFFFF" size="5">Ajouter une machine</font></i></div>
     </td>
</tr>
<form action="Dispatcher"     method="post" name="form"   onsubmit='return control()'>
<tr bgcolor=white><td colspan=2>&nbsp</td></tr> 
<tr bgcolor=white> 
<td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">libelle</font></td>
<td><input type="hidden" name="operation" size="8" value='addmachine'>
    <input type="text"   name="libelle" size="50"  >
</td></tr>
<tr bgcolor=white>  
<td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">sigle</font></td>
<td><input type="text" name="sigle" size="4"  >
</td></tr>
<tr bgcolor=white>  
<td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">adresse</font></td>
<td><input type="text" name="adresse" size="20"  >
</td></tr>
<tr bgcolor=white>  
<td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">zone</font></td>
<td><input type="text" name="zone" size="2"  >
</td></tr>
 
<tr bgcolor=white> <td colspan=2>&nbsp</td></tr> 
<tr bgcolor=white>  
<td colspan=2> 
<div align="center">
                                  <input type="submit" value="Ajouter" width="72">
                                </div>
                              </td>
                            </tr>
</form>
        </table>
     </td>
  </tr>
</table> 
 </body>
</html>

