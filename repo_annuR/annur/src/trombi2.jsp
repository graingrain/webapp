<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
 
<HTML>
<% 
String table="liste";
System.out.println("debut jsp :" + table +".jsp" );
System.out.println("--------------------------------------" );

ArrayList divtree           = (ArrayList)session.getValue("divtree");
ArrayList alltree           = (ArrayList)session.getValue("alltree");
ArrayList onepole           = (ArrayList)session.getValue("onepole");
ArrayList allpole           = (ArrayList)session.getValue("allpole");
//ArrayList onedivision       = (ArrayList)session.getValue("onedivision");
ArrayList alldivision       = (ArrayList)session.getValue("alldivision");
//ArrayList allservice        = (ArrayList)session.getValue("allservice");
ArrayList allagent          = (ArrayList)session.getValue("allagent");
System.out.println("--------------------------------------" );
//Collections.sort(allagent);
//ArrayList allldap           = (ArrayList)session.getValue("allldap");
ServletContext sc = getServletContext();
UserInfo userinfo = (UserInfo)session.getValue("currentUser");
 

%>
<head><title><%=table%></title>
 
<script language="JavaScript1.2" src="include.control.js"></script>

<link href="boilerplate.css" rel="stylesheet" type="text/css">
<link href="organigramme-ac-reunion.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="menu-organigramme.css" type="text/css" media="screen" />	
</head>
<body> NEW
<%@ include file="include_menu-karine.jsp" %> 
<div id="content-row1" class="detail-agent">  
<%@ include file="include_trombi.htm"%>
    
</BODY>


  
 