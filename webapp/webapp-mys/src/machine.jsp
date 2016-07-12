<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
<%@ page errorPage="PbGeneral.jsp" %>
<html>
<head>
<title>machine</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<style type="text/css">
	<!--
	.style9 {
		color: #0000FF;
		font-style: italic;
		font-weight: bold;
	}
.style10 {color: #FFFFFF}
.style16 {
	color: #FFFFFF;
	font-weight: bold;
	font-style: italic;
}
.style17 {
	font-size: 10px;
	font-style: italic;
}
.style18 {font-size: 12}
	-->
	</style>
	</head>
	
</head>

<body bgcolor="#FFFFFF" text="#000000">
<script language="JavaScript1.1">
function control() {
if (teststring(document.form.libelle) == false) { alert("a saisir !"); document.form.libelle.focus() ;return false; }
if (teststring(document.form.sigle)   == false) { alert("a saisir !"); document.form.sigle.focus() ;return false; }
if (teststring(document.form.adresse) == false) { alert("a saisir !"); document.form.adresse.focus() ;return false; }
if (testnum0(document.form.zone)      == false) { alert("info numerique et non nulle"); document.form.zone.focus() ;return false; }
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
System.out.println("debut jsp:machine"); 
try
{
UserInfo userinfo = (UserInfo)session.getValue("currentUser"); 
String vnom=userinfo.getNom();
int profil=userinfo.getProfil();
String voper="";
int vcode=0;
 
String vlibelle="";
String vsigle="";
String vadresse="";
String zone1="bgcolor='#FFFF00'";
String zone2="bgcolor='#FF9900'";
String zone3="bgcolor='#99CCFF'";
 
if (request.getParameter("operation")!=null) voper=request.getParameter("operation");
if (request.getParameter("operation")!=null&&(request.getParameter("operation").equals("modmachine")))
{ 
ArrayList liste      = (ArrayList)session.getValue("liste_one_machine") ;
for (int i=0; i<liste.size(); i++)   
{  
ItemApplication item = (ItemApplication) liste.get(i);  
vcode=item.getMa_code();
vsigle=item.getMa_sigle();
vlibelle=item.getMa_libelle();
vadresse=item.getMa_adresse();
 
}  
} %>
<!--  MENU  --> <%@ include file="menu.jsp"%> <!--  MENU  --> 
<!-- table appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
<table width="100%" border="0">
  <tr> 
    <td width="16%">&nbsp; </td>
    <td width="68%"> 
      <table border="0" cellspacing="0" cellpadding="1" width=501  bgcolor=#9999CC align="center">
        <tr>
          <td> 
            <table border="0" cellspacing="0" width=500 cellpadding="0" align="center">
              <tr> 
                <td bgcolor="#9999CC" colspan=2> 
                  <div align="center"><i><font color="#FFFFFF" size="5">Machine</font></i></div>
                </td>
              </tr>
              <form action="Dispatcher"     method="post" name="form"   onsubmit='return control()'>
                <tr bgcolor=white>
                  <td colspan=2>&nbsp;</td>
                </tr>
                <tr bgcolor=white> 
                  <td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">libelle</font></td>
                  <td>
                    <input type="hidden" name="operation" size="8" value='addmachine'>
					<input type="hidden" name="code" size="8"      value="<%=vcode%>">
                    <input type="text"   name="libelle" size="50"  value="<%=vlibelle%>">
                  </td>
                </tr>
                <tr bgcolor=white> 
                  <td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">sigle</font></td>
                  <td>
                    <input type="text" name="sigle" size="4" value="<%=vsigle%>">
                  </td>
                </tr>
                <tr bgcolor=white> 
                  <td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">adresse</font></td>
                  <td>
                    <input type="text" name="adresse" size="20" value="<%=vadresse%>">
                  </td>
                </tr>
                <tr bgcolor=white> 
                  <td align="right"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">zone</font></td>
                  <td>
                    <input type="text" name="zone" size="2"  >
                  </td>
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
              </div></td></tr>
              </form>
            </table>
          </td>
        </tr>
      </table></td>
    <td width="16%"> 
      <table width="139" height="84" border="0" align="right" cellpadding="0" cellspacing="0" bgcolor=blue>
        <tr> 
          <td height="82"> 
            <table width="137" border="0"  height="82" align="center" bgcolor="white" cellpadding="0" cellspacing="0">
              <tr> 
                <td width="39"     bgcolor="#FFFF00" height="18">&nbsp;</td>
                <td width="98" height="18"   > 
                  <div align="right" class="style17"> 
                    <div align="left">zone 1: gestion: </div>
                  </div>
                </td>
              </tr>
              <tr> 
                <td height="19" bgcolor="#FF9900" width="39">&nbsp;</td>
                <td width="98"> 
                  <div align="right" class="style17"> 
                    <div align="left">zone 2: agriates</div>
                  </div>
                </td>
              </tr>
              <tr> 
                <td height="19" bgcolor="#99CCFF" width="39">&nbsp;</td>
                <td width="98"> 
                  <div align="right" class="style17"> 
                    <div align="left">zone 3: extranet</div>
                  </div>
                </td>
              </tr>
              <tr bgcolor="#666666"> 
                <td height="19" colspan="2"> 
                  <div align="right" class="style17"> 
                    <div align="left" class="style18"> 
                      <div align="center" class="style10"><strong><font color="#FFFFFF">Légende</font></strong></div>
                    </div>
                  </div>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<br>
<!-- table liste appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
<table border="0" cellspacing="0" cellpadding="1" width=701  bgcolor=#9999CC align="center">   <tr><td>
      <table border="0" cellspacing="0" width=700 cellpadding="0" align="center">
        <tr> 
          <td bgcolor="#9999CC" colspan=7> 
            <div align="center"><font color="#FFFFFF" size="4"><i>Liste des machines</i></font> 
            </div>
          </td>
        </tr>
        <tr bgcolor=white > 
          <td width="41"> 
            <div align="center"><b><i><font color="#9999CC">Zone</font></i></b></div>
          </td>
          <td width="173"> 
            <div align="center"><b><i><font color="#9999CC">Nom</font></i></b></div>
          </td>
          <td width="61"> 
            <div align="center"><b><i><font color="#9999CC">Sigle</font></i></b></div>
          </td>
          <td> 
            <div align="center"><b><i><font color="#9999CC">Adresse</font></i></b></div>
            <div align="center"><b><i></i></b></div>
          </td>
          <td width="75"> 
            <div align="center"><b><i><font color="#9999CC">Etat</font></i></b></div>
          </td>
          <td width="100"> 
            <div align="center"><b><i><font color="#9999CC">Date Etat</font></i></b></div>
          </td>
          <td width=100> 
            <div align="center"><b><i><font color="#9999CC">action</font></i></b></div>
          </td>
        </tr>
        <% ArrayList liste       = (ArrayList)session.getValue("liste_machine") ;
for (int i=0; i<liste.size(); i++)   {  ItemApplication item = (ItemApplication) liste.get(i); %>
        <tr bgcolor=white > 
          <td width="41" 
		  <%        if(item.getMa_zn_code()==1) out.println(zone1);
                    if(item.getMa_zn_code()==2) out.println(zone2);
                    if(item.getMa_zn_code()==3) out.println(zone3);  %>> 
            <div align="center"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
              <%=item.getMa_zn_code()%></font></div>
          </td>
          <td width="173"> 
            <div align="left"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
              <%=item.getMa_libelle()%> </font></div>
          </td>
          <td width="61"> 
            <div align="center"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"><%=item.getMa_sigle()%> 
              </font></div>
          </td>
          <td align="center" bgcolor="#FFFFFF" valign="middle"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
            <%=item.getMa_adresse()%> </font> 
           
          </td>
          <td width="75"> 
            <div align="center"> <font face="Arial, Helvetica, sans-serif" size="2"> 
              <% String num=item.getMa_etat()   ;  %>
              <img src="images/boule_<%=num%>.gif"  > </font></div>
          </td>
          <td width="100"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
            <%=item.getMa_etat_date()%> </font></td>
          <td width="100"><font face="Arial, Helvetica, sans-serif" size="2"> 
            <% if (item.getMa_nb_bdd()== 0) { %>
            <a href="Dispatcher?operation=supmachine&code=<%=item.getMa_code()%>"> 
            <img src="images/poubelle.gif" width="14" height="14" border="0"></a> 
            <% } else { %>
            <img src="images/poubelle0.gif" width="14" height="14" border="0"> 
            <% } %>
            <a href="Dispatcher?operation=modmachine&code=<%=item.getMa_code()%>"> 
            <img src="avis4.gif" width="19" height="15" border="0"></a> </font></td>
        </tr>
        <% } %>
      </table>
</td></tr></table>
<% 
}
catch(IOException e)
 	{	System.out.println("<H2>"+"ClassNotFoundException: " + e.getMessage() + "<BR>");
 	}
%> 

<div align="center"><img src="images/logo_java.gif" width="103" height="53"> <img src="images/apache_pb.gif" width="259" height="32"> 
  v2 </div>
</body>
</html>

