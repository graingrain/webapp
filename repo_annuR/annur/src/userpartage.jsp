<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
  
<HTML>
<% 
String table="partage";
 
ArrayList divtree           = (ArrayList)session.getValue("divtree");
ArrayList alltree           = (ArrayList)session.getValue("alltree");
ArrayList onepole           = (ArrayList)session.getValue("onepole");
ArrayList allpole           = (ArrayList)session.getValue("allpole");
ArrayList onepartage        = (ArrayList)session.getValue("onepartage");
ArrayList alldivision       = (ArrayList)session.getValue("alldivision");
ArrayList allpartage        = (ArrayList)session.getValue("allpartage");
ArrayList allbureau         = (ArrayList)session.getValue("allbureau");
ArrayList allagent          = (ArrayList)session.getValue("allagent");
ArrayList allagentdiv       = (ArrayList)session.getValue("allagentdiv");
//ArrayList allldap           = (ArrayList)session.getValue("allldap");
ServletContext sc = getServletContext();
UserInfo userinfo = (UserInfo)session.getValue("currentUser");
 
System.out.println("debut :" + table +".jsp" );
System.out.println("--------------------------------------" );
%>
<head>
<title><%=table%></title>
<script language="JavaScript1.2" src="resources/control.js"></script>
<script type="text/javascript" language="javascript">
<!--
// Updates the title of the frameset if possible (ns4 does not allow this)
if (typeof(parent.document.title) == 'string') {
    // parent.document.title = 'mbox2.table1 sur le serveur localhost - phpMyAdmin 2.2.3';
}
// js form validation stuff
var errorMsg0   = 'Formulaire incomplet !';
var errorMsg1   = 'Ce n\'est pas un nombre !';
var errorMsg2   = ' n\'est pas un nombre valide !';
var noDropDbMsg = 'La commande "DROP DATABASE" est désactivée.';
var confirmMsg  = 'Voulez-vous vraiment effectuer ';
//-->
function setPointer(theRow, thePointerColor)
{
    if (thePointerColor == '' || typeof(theRow.style) == 'undefined') {
        return false;
    }
    if (typeof(document.getElementsByTagName) != 'undefined') {
        var theCells = theRow.getElementsByTagName('td');
    }
    else if (typeof(theRow.cells) != 'undefined') {
        var theCells = theRow.cells;
    }
    else {
        return false;
    }
    var rowCellsCnt  = theCells.length;
    for (var c = 0; c < rowCellsCnt; c++) {
        theCells[c].style.backgroundColor = thePointerColor;
    }
    return true;
} // end of the 'setPointer()' function
</script>

<link rel="stylesheet" href="stylefoad.css" type="text/css" media="screen" />
</head>
<body>  
<%@  include file="include_menu_partage.jsp" %>
<strong><font size="+1">Tableau croisé des partages: </font></strong> 
<table width="90%" border="1"  >
  <tr align="left" valign="top"> 
    <td width=20 bordercolor="#FFFFFF">&nbsp;</td>
    <% 
System.out.println("---------- nb partage : "+allpartage.size() );
if(allpartage!=null) { 
for(int i=0; i < allpartage.size(); i++) {  
ItemAnnuaire E  = (ItemAnnuaire) allpartage.get(i); 
String partage=E.getPa_nom()+"______________________";
%>
    <td width=20 bordercolor="#CC99FF">
<div align="center"><font color="#0033FF" size="1"><a href="Dispatcher?operation=partage&codepa=<%=E.getPa_code()%>&table=partage&nompa=<%=E.getPa_nom()%>" class="Style1"><%=partage.substring(0,11)%></a>  </font></div></td>
    <% }  %>
  </tr>
  <% } 
if(allagent!=null) { 
String old="";
String[] T;
T = new String[allpartage.size()];
for(int t=0; t < allpartage.size(); t++) {  T[t]=" "; }
for(int a=0; a < allagent.size(); a++) { 
ItemAnnuaire A  = (ItemAnnuaire) allagent.get(a); 
String agent=A.getAg_ldap_nom()+" " +   A.getAg_ldap_prenom()     ; 
System.out.println("user:"+agent);
if(!(old.equals(agent)))  { %>
 <tr   onmouseover="setPointer(this, '#FFFFCC')" onmouseout="setPointer(this, '#ffffff')" > 
    <td> <div align="left"><font size="1"><%=old%>  </font></div></td>
    <%  for(int t=0; t < allpartage.size(); t++) {  %>
    <td> <div align="center"><%=T[t]%> </div></td>
    <% } %>
  </tr>
  <%
for(int t=0; t < allpartage.size(); t++) {  T[t]=" "; } 
} %>
  <%
old=agent;
for(int j=0; j < allpartage.size(); j++) {  
ItemAnnuaire E  = (ItemAnnuaire) allpartage.get(j); 
String partage=E.getPa_nom();
if (A.getPa_nom().equals(E.getPa_nom())) {  T[j]="X"; } 
 
} }   %></font>
 <tr   onmouseover="setPointer(this, '#FFFFCC')" onmouseout="setPointer(this, '#ffffff')" > 
    <td> <div align="left"><font size="1"><%=old%>  </font></div></td>
    <%  for(int t=0; t < allpartage.size(); t++) {  %>
    <td onMouseOver="AffBulle('Info bulle du premier lien')"> <div align="center"><%=T[t]%> </div></td>
    <% } %>
  </tr>
  <% } %>
</table> 
 aaaaaaaaaa 
 
</BODY>
 
 