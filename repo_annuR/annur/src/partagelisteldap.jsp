<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
 
<HTML>
<% 
String table="partagelisteldap";
ArrayList allappels         = (ArrayList)session.getValue("allappels");
ArrayList divtree           = (ArrayList)session.getValue("divtree");
ArrayList alltree           = (ArrayList)session.getValue("alltree");
ArrayList onepole           = (ArrayList)session.getValue("onepole");
ArrayList allpole           = (ArrayList)session.getValue("allpole");
ArrayList onebureau         = (ArrayList)session.getValue("onebureau");
ArrayList alldivision       = (ArrayList)session.getValue("alldivision");
ArrayList allservice        = (ArrayList)session.getValue("allservice");
ArrayList allagent          = (ArrayList)session.getValue("allagentsms");
ArrayList allasa            = (ArrayList)session.getValue("allasa");
ArrayList allldap           = (ArrayList)session.getValue("allldap");
//ArrayList allldap           = (ArrayList)session.getValue("allldap");
ServletContext sc = getServletContext();
UserInfo userinfo = (UserInfo)session.getValue("currentUser");
 
System.out.println("debut :" + table +".jsp" );
System.out.println("--------------------------------------" );
%>
<head><title><%=table%></title>
<script language="JavaScript1.2" src="resources/control.js"></script>
<link rel="shortcut icon" type="images/x-icon" href="favicon-annuaire.png" />
<script language="JavaScript1.2" src="resources/control.js"></script>
<link href="boiler.plate.css" rel="stylesheet" type="text/css">
<link href="organigramme.ac-reunion.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="menu.organigramme.css" type="text/css" media="screen" />
</head>
<body>   
 
<%@  include file="include_menu_partage.jsp" %>
<%@  include file="include_liste_ldap_addpartage.htm"%>
</BODY>
