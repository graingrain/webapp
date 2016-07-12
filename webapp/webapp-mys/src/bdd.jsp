<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
 
<html>
<head>
<title>bdd</title>
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
if (teststring(document.form.repertoire) == false) { alert("a saisir !"); document.form.repertoire.focus() ;return false; }


if (document.form.ma_code.value.length == 0) {
   alert("Le choix de la machine est obligatoire \nCorrigez le ...")
   document.form.ma_code.focus()
   return false
}

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
System.out.println("debut jsp:BDD"); 
try 
{
UserInfo userinfo = (UserInfo)session.getValue("currentUser"); 
String vnom=userinfo.getNom();
int profil=userinfo.getProfil();
 
String voper="";
int vcode=0; 
String zone1="bgcolor='#FFFF00'";
String zone2="bgcolor='#FF9900'";
String zone3="bgcolor='#99CCFF'"; 
int codepostal=974;
String vcodenom="null";
if (request.getParameter("operation")!=null) voper=request.getParameter("operation");
 
if (request.getParameter("operation")!=null) voper=request.getParameter("operation");
ArrayList liste_one_application       = (ArrayList)session.getValue("liste_one_bdd") ;
ItemApplication item1 = (ItemApplication) liste_one_application.get(0);
vcode=item1.getBd_code(); 
%>
 <!--  MENU  --> <%@ include file="menu.jsp"%> <!--  MENU  --> 
<!-- table appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
<table border="0" cellspacing="0" cellpadding="1" width=501  bgcolor=#9999CC align="center">   <tr><td>
<table border="0" cellspacing="0" width=500 cellpadding="0" align="center">
<tr><td bgcolor="#9999CC" colspan=2><div align="center"><i><font color="#FFFFFF" size="5">Bdd</font></i></div></td></tr>
<form action="Dispatcher" method="post" name="form"   onsubmit='return control()'>
  
<tr bgcolor=white><td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">type</font></td>
                  <td>
				  <input type="hidden" name="code" size="8" value=<%=vcode%>>
				  <input type="hidden" name="operation" size="8" value='addbdd'>
		       <select name="type"> 
			    <option value=<%=item1.getBd_type()%>><%=item1.getBd_type()%></option>
              
                <option value="informix SE">informix SE</option>
                <option value="informix Online">informix Online</option>
				<option value="ldap">ldap</option>
                <option value="Mysql">Mysql</option>
              </select>
            </td></tr>
<tr bgcolor=white><td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">repertoire</font></td>
                  <td><input type="text" name="repertoire" size="60" value="<%=item1.getBd_repertoire()%>">
				  </td></tr>
<tr bgcolor=white><td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">machine:</font></td>
                  
				  <td><select name="ma_code" size="1"> 
				   
<option value=<%=item1.getMa_code()%>><%=item1.getBd_sigle()%></option>				   
<% ArrayList liste_machine       = (ArrayList)session.getValue("liste_machine") ;
for (int i=0; i<liste_machine.size(); i++)   {  
ItemApplication item = (ItemApplication) liste_machine.get(i); %>
<option value=<%=item.getMa_code()%>><%=item.getMa_libelle()%></option>
<% } %>                                  
</select>
</td></tr>
<tr bgcolor=white><td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">port</font></td>
                  <td><input type="text" name="port" size="6"  value="<%=item1.getBd_port()%>"></td>
</tr>
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
      <table border="0" cellspacing="0" width=800 cellpadding="0" align="center">
        <tr> 
          <td bgcolor="#9999CC" colspan=8> 
            <div align="center"><font color="#FFFFFF" size="4"><i>Liste des bdds</i></font> 
            </div>
          </td>
        </tr>
        <tr bgcolor=white > 
          <td> 
            <div align="center"><b><i><font color="#9999CC">Zone</font></i></b></div>
          </td>
          <td> 
            <div align="center"><b><i><font color="#9999CC">Repertoire</font></i></b></div>
          </td>
          <td> 
            <div align="center"><b><i><font color="#9999CC">Type</font></i></b></div>
          </td>
          <td> 
            <div align="center"><b><i><font color="#9999CC">Machine</font></i></b></div>
          </td>
          <td> 
            <div align="center"><b><i><font color="#9999CC">Port</font></i></b></div>
          </td>
          <td> 
            <div align="center"><b><i><font color="#9999CC">Etat</font></i></b></div>
          </td>
          <td width="160"> 
            <div align="center"><b><i><font color="#9999CC">Date Etat</font></i></b></div>
          </td>
          <td width=42> 
            <div align="center"><b><i><font color="#9999CC">action</font></i></b></div>
          </td>
        </tr>
        <% ArrayList liste       = (ArrayList)session.getValue("liste_bdd") ;
for (int i=0; i<liste.size(); i++)   {  ItemApplication item = (ItemApplication) liste.get(i); %>
        <tr bgcolor=white > 
          <td width="41"  
		  <%        if(item.getMa_zn_code()==1) out.println(zone1);
                    if(item.getMa_zn_code()==2) out.println(zone2);
                    if(item.getMa_zn_code()==3) out.println(zone3);  %>> 
            <div align="center"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
              <%=item.getMa_zn_code()%></font></div>
          </td>
          <td width="230"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
            <%=item.getBd_repertoire()%></font></td>
          <td width="138"> 
            <div align="left"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"><%=item.getBd_type()%></font></div>
          </td>
          <td width="58" bgcolor="#FFFFFF"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
            <%=item.getMa_sigle()%></font></td>
          <td width="70"> 
            <div align="center"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
              <%=item.getBd_port()%></font></div>
          </td>
          <td width="61" valign="middle" align="center"> 
            <% String         num=item.getBd_port_etat()  ;  %>
            <div align="center"><img src="images/boule_<%=num%>.gif"  > 
            </div>
          </td>
          <td width="160"><font color="#000033" size="-2" face="Arial, Helvetica, sans-serif"> 
            <%=item.getBd_port_etat_date()%></font></td>
          <td><font face="Arial, Helvetica, sans-serif" size="2">
		  <% if (item.getNb_joint()== 0) { %>
		  <a href="Dispatcher?operation=supbdd&code=<%=item.getBd_code()%>">
		  <img src="images/poubelle.gif" width="14" height="14" border="0"></a> 
		  <% } else { %>  <img src="images/poubelle0.gif" width="14" height="14" border="0"> <% } %> 
            <a href="Dispatcher?operation=modbdd&code=<%=item.getBd_code()%>"><img src="avis4.gif" width="19" height="15" border="0"></a> 
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

