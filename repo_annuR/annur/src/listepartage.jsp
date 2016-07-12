<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
<HTML>
<% String table="partage";
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
<link rel="shortcut icon" type="images/x-icon" href="favicon-annuaire.png" />
<link href="boiler.plate.css" rel="stylesheet" type="text/css">
<link href="organigramme.ac-reunion.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="menu.organigramme.css" type="text/css" media="screen" />
</head>
<body>
<%@  include file="include_menu-karine.jsp" %>
<SCRIPT language="javascript">
   function Valider(formulaire,type) {
    
      formulaire.bsubmit.value=type;
       
formulaire.submit()
}
</SCRIPT>
<div id="content-row1" class="detail-agent"> 
<%@ include file="include_liste_partage.htm"%>
</div>
 
<div align="center">
<a href="Dispatcher?operation=userpartage&table=userpartage" class="Style1">Tableau croisé </a>
</div>
</BODY>
    
