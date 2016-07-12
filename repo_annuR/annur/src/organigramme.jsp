<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
<% 
ServletContext sc = getServletContext();
UserInfo userinfo = (UserInfo)session.getValue("currentUser");
String table="annuaire";
System.out.println("debut jsp :" + table +".jsp"  );
System.out.println("debut jsp :" + table +".jsp" +" profil="+userinfo.getProfil());
System.out.println("--------------------------------------" );
ArrayList alldivision       = (ArrayList)session.getValue("alldivision");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title><%=table%></title>
<style type="text/css">
<!--
.Style15 {
	font-size: 12
}
.Style16 {font-size: 14px}
.Style18 {font-size: 13px}
.Style19 {font-size: 12px}
-->
</style>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="stylefoad.css" type="text/css" media="screen" />
<link rel="shortcut icon" href="images/favicon.ico" />
<link rel="stylesheet" href="menu-organigramme.css" type="text/css" media="screen" />
<body>
<%@  include file="include_menu.jsp" %>
 
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p><br>
</p> 
<div id="global"> 
  <div align="center">L'organigramme est en cours de modification, vous pouvez 
    consulter sa version à jour en pdf. </div>
</div>
</body>
</html>
