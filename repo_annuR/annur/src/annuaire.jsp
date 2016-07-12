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
String table="annuaire";
System.out.println("debut jsp ---- annuaire ------:" + table +".jsp"  );
System.out.println("debut jsp ----- :" + table +".jsp" +" profil="+userinfo.getProfil());
System.out.println("--------------------------------------" );
ArrayList alldivision       = (ArrayList)session.getValue("alldivision");
%>
<HTML>
<head>
<title><%=table%></title>
<link rel="shortcut icon" type="images/x-icon" href="favicon-annuaire.png" />
<script language="JavaScript1.2" src="resources/control.js"></script>
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
{  code=request.getParameter("code");
   libelle=userinfo.getError_message(); 
}
%>
 
<%@ include file="include_menu-karine.jsp" %>
<div id="content-row1" class="detail-agent"> 
<table width="100%" border="0">
<% System.out.println("debut "+ alldivision.size()); %>
<%	   if(alldivision!=null) { 
for(int d=0; d < alldivision.size(); d++) {  
ItemAnnuaire D  = (ItemAnnuaire) alldivision.get(d); %>
<tr   onmouseover="setPointer(this, '#effceb')" onmouseout="setPointer(this, '#ffffff')" >
    <td width="auto"> <p><span class="titre1"> <a href="Dispatcher?operation=division&codedv=<%=D.getDv_code()%>" class="lien_noir14_none"> <%=D.getDv_nom()%> </a></span></p> </td>  
    <td ><p><span class="titre1"><%=D.getDv_nomc()%></span></p></td>
    <td ><p><span class="titre1"><% if (D.getDv_telephone().length() > 1) {%>
       T&eacute;l.&nbsp;<em><%=D.getDv_telephone()%></em>
	   <%}%>
	   </span></p></td>
    
       <td ><p><span class="titre1"><% if (D.getDv_fax().length() > 1) {%> 
	   Fax.&nbsp;<em><%=D.getDv_fax()%></em>
    <%}%>
	</span></p></td>
  </tr>
  <% if(D.getDv_mission().length() >1) { %>
  <% if((userinfo.getVoir_mission()==1)) { %>   
  <tr  > 
    <td height="36">&nbsp;</td>
    <td colspan="3" bgcolor="#CCCCCC">
	 <span class="td_mission"><pre><%=D.getDv_mission()%></pre></span>	</td>
  </tr>
  <% } } } } %>
</table> 
</div>
 </body>
</html>
