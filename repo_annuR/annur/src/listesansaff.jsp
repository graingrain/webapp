<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
<%@ page errorPage="PbGeneral.jsp" %>
<% request.setAttribute("sourcePage",request.getRequestURI()); %>
<% 
ServletContext sc = getServletContext();
UserInfo userinfo = (UserInfo)session.getValue("currentUser");
ArrayList allagent          = (ArrayList)session.getValue("allagent");
String table="liste";
System.out.println("debut jsp :" + table +".jsp"  );
System.out.println("debut jsp :" + table +".jsp" +" profil="+userinfo.getProfil());
System.out.println("----------sans affectation----------------------------" );
%>
<HTML>
<head>
<title><%=table%></title>
<link rel="shortcut icon" type="images/x-icon" href="favicon-annuaire.png" />
<link href="boiler.plate.css" rel="stylesheet" type="text/css">
<link href="organigramme.ac-reunion.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="menu.organigramme.css" type="text/css" media="screen" />
</head>
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
    if (thePointerColor == '' || typeof(theRow.style) == 'undefined') {        return false;    }
    if (typeof(document.getElementsByTagName) != 'undefined') {
        var theCells = theRow.getElementsByTagName('td');
    }
    else if (typeof(theRow.cells) != 'undefined') {        var theCells = theRow.cells;    }
    else {        return false;    }
    var rowCellsCnt  = theCells.length;
    for (var c = 0; c < rowCellsCnt; c++) {
        theCells[c].style.backgroundColor = thePointerColor;
    }
   return true;
} // end of the 'setPointer()' function
</script>

<script language="JavaScript">
function control() {
if (document.form.code.value.length == 0) {
   alert("Le Rne est obligatoire \nCorrigez le ...")
   document.form.code.focus()
   return false
}
if (document.form.password.value.length == 0) {
   alert("Le Mot de passe est obligatoire \nCorrigez le ...")
   document.form.password.focus()
   return false
}
if (document.form.password.value.length < 3) {
   alert("Le Mot de passe doit contenir 3 caractères minimum \nCorrigez le ...")
   document.form.password.focus()
   return false
}
}
</script>
<body>
<%
String code="";
String libelle="";
if (request.getAttribute("erreur")!=null) libelle=(String) request.getAttribute("erreur");    
else
if (request.getParameter("code")!=null)  
{
  code=request.getParameter("code");
  libelle=userinfo.getError_message(); 
}
%>
<%@ include file="include_menu-karine.jsp" %>
<div id="content-row1" class="detail-agent"> 
<% System.out.println("---------- deb jsp    sans affectation----------------------------" );%>
<%@ include file="include_liste_agent_sans_aff.htm" %>
<% System.out.println("---------- fin  jsp    sans affectation----------------------------" ); %>
</div>
</body>
</html>
