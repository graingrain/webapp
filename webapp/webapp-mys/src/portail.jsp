<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
<%@ page errorPage="error.jsp" %>
<html>
<head>
<title>portail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF" text="#000000">
<script language="JavaScript1.1">
function control() {  
if (document.form.type.value.length == 0) {
   alert("Le type est obligatoire \nCorrigez le ...")
   document.form.type.focus()
   return false
}
document.form.version.value=document.form.version.value.substr(0,20)
if (teststring(document.form.version) == false) { alert("a saisir !"); document.form.version.focus() ;return false; }
document.form.repertoire.value=document.form.repertoire.value.substr(0,80)
 
 
if (teststring(document.form.repertoire) == false) { alert("a saisir !"); document.form.repertoire.focus() ;return false; }


if (document.form.ma_code.value.length == 0) {
   alert("Le choix de la machine est obligatoire \nCorrigez le ...")
   document.form.ma_code.focus()
   return false
}
if (teststring(document.form.type) == false) { alert("a saisir !"); document.form.type.focus() ;return false; }
 
if (testnum0(document.form.port) == false) { alert("info numerique et non nulle"); document.form.port.focus() ;return false; }
 
 
 
 
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
System.out.println("debut jsp:Poratil"); 
try
{
UserInfo userinfo = (UserInfo)session.getValue("currentUser"); 
String vnom=userinfo.getNom();
int profil=userinfo.getProfil();
String voper="";
String vrepertoire="";
String vsigle="";
String vtype="";
String vlibelle="";
String vversion="";
String zone1="bgcolor='#FFFF00'";
String zone2="bgcolor='#FF9900'";
String zone3="bgcolor='#99CCFF'";
int vcode=0;
int nb6=0;
int vport=0;
if (request.getParameter("operation")!=null) voper=request.getParameter("operation");
ArrayList one        = (ArrayList)session.getValue("liste_one_portail") ;
ItemApplication item1 = (ItemApplication) one.get(0);
vcode=item1.getPt_code(); 
%>
<!--  MENU  --> <%@ include file="menu.jsp"%> <!--  MENU  --> 
<!-- table appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
<table border="0" cellspacing="0" cellpadding="1" width=501  bgcolor=#9999CC align="center">   <tr><td>
<table border="0" cellspacing="0" width=500 cellpadding="0" align="center">
<tr><td bgcolor="#9999CC" colspan=2><div align="center"><i><font color="#FFFFFF" size="5">Portail</font></i></div></td></tr>
<form action="Dispatcher" method="post" name="form"   onsubmit='return control()'>
<tr bgcolor=white><td colspan=2>&nbsp;</td></tr> 
<tr bgcolor=white><td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">type</font></td>
                  <td><input type="hidden" name="operation" size="8" value='addportail'>
              <select name="type"> 
                <option value=<%=item1.getPt_type()%>><%=item1.getPt_type()%></option>
              	<option value="apache">apache</option>
                <option value="tomcat">tomcat</option>
                <option value="netscape">netscape</option>
              </select>
            </td>
          </tr>
<tr bgcolor=white>
            <td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">version</font></td>
                  <td>
              <input type="hidden" name="operation" size="8" value='addportail'>
              <input type="hidden" name="code" size="8"     value="<%=vcode%>">
              <input type="text"   name="version" size="20"  value="<%=item1.getPt_version()%>">
            </td></tr>
<tr bgcolor=white>
            <td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">repertoire</font></td>
                  <td>
              <input type="text" name="repertoire" size="60" value="<%=item1.getPt_repertoire()%>">
            </td></tr>
<tr bgcolor=white><td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">machine:</font></td>
                  <td><select name="ma_code" size="1">
				  <option value=<%=item1.getMa_code()%>><%=item1.getMa_sigle()%></option>
              <% ArrayList liste_machine       = (ArrayList)session.getValue("liste_machine") ;
for (int i=0; i<liste_machine.size(); i++)   {  ItemApplication item = (ItemApplication) liste_machine.get(i); %>
<option value=<%=item.getMa_code()%>><%=item.getMa_libelle()%></option>
<% } %>                                  
</select>
</td></tr>
<tr bgcolor=white><td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">port</font></td>
                <td><input type="text" name="port" size="6" value="<%=item1.getPt_port()%>">
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
<table border="0" cellspacing="0" cellpadding="1" width=701  bgcolor=#9999CC align="center">   <tr><td>
      <table border="0" cellspacing="0" width=800 cellpadding="0" align="center">
        <tr> 
          <td bgcolor="#9999CC" colspan=9> 
            <div align="center"><font color="#FFFFFF" size="4"><i>Liste des Portails</i></font> 
            </div>
          </td>
        </tr>
        <tr bgcolor=white > 
          <td width="38"> 
            <div align="center"><b><i><font color="#9999CC">Zone</font></i></b></div>
          </td>
          <td width="113"> 
            <div align="center"><b><i><font color="#9999CC">Type</font></i></b></div>
          </td>
          <td width="89"> 
            <div align="center"><b><i><font color="#9999CC">Version</font></i></b></div>
          </td>
          <td width="99"> 
            <div align="center"><b><i><font color="#9999CC">Repertoire</font></i></b></div>
          </td>
          <td width="91"> 
            <div align="center"><b><i><font color="#9999CC">Machine</font></i></b></div>
          </td>
          <td width="55"> 
            <div align="center"><b><i><font color="#9999CC">Port</font></i></b></div>
          </td>
          <td width="72"> 
            <div align="center"><b><i><font color="#9999CC">Etat</font></i></b></div>
          </td>
          <td width="96"> 
            <div align="center"><b><i><font color="#9999CC">Date Etat</font></i></b></div>
          </td>
          <td width=47> 
            <div align="center"><b><i><font color="#9999CC">action</font></i></b></div>
          </td>
        </tr>
        <% ArrayList liste       = (ArrayList)session.getValue("liste_portail") ;
for (int i=0; i<liste.size(); i++)   {  ItemApplication item = (ItemApplication) liste.get(i); %>
         <tr bgcolor=white > 
          <td width="38" height="20" 
		  <%        if(item.getMa_zn_code()==1) out.println(zone1);
                    if(item.getMa_zn_code()==2) out.println(zone2);
                    if(item.getMa_zn_code()==3) out.println(zone3);  %>> 
            <div align="center"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
              <%=item.getMa_zn_code()%></font></div>
          </td>
          <td width="113"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
            <%=item.getPt_type()%></font></td>
          <td width="89"> 
            <div align="left"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"><%=item.getPt_version()%></font></div>
          </td>
          <td width="199"> 
            <div align="left"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"><%=item.getPt_repertoire()%></font></div>
          </td>
          <td width="91" bgcolor="#FFFFFF"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
          <%=item.getMa_sigle()%></font></td>
          <td width="55"> 
            <div align="center"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
              <%=item.getPt_port()%></font></div>
          </td>
          <td width="72" valign="middle" align="center"> 
            <%String        num=item.getPt_port_etat()  ;  %>
            <img src="images/boule_<%=num%>.gif" width="20" height="20"  > </td>
          <td width="96"><font color="#000033" size="-2" face="Arial, Helvetica, sans-serif"> 
            <%=item.getPt_port_etat_date()%></font></td>
          <td width="47"> <font face="Arial, Helvetica, sans-serif" size="2"> 
            <% if (item.getNb_joint()== 0) { %>
            <a href="Dispatcher?operation=supportail&code=<%=item.getPt_code()%>"> 
            <img src="images/poubelle.gif" width="14" height="14" border="0"></a> 
            <% } else { %>
            <img src="images/poubelle0.gif" width="14" height="14" border="0"> 
            <% } %>
            <a href="Dispatcher?operation=modportail&code=<%=item.getPt_code()%>"><img src="avis4.gif" width="19" height="15" border="0"></a> 
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
<div align="center"><img src="images/logo_java.gif" width="103" height="53"> <img src="images/apache_pb.gif" width="259" height="32"> 
</div>
</body>
</html>

