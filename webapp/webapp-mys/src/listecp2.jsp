<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
 
<html>
<head>
<title>dns</title>
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
 
%>
 <!--  MENU  --> <%@ include file="menu.jsp"%> <!--  MENU  --> 
<!-- table appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
 
<br>

<table border="0" cellspacing="0" cellpadding="1" width=701  bgcolor=#9999CC align="center">   <tr><td>
<table border="0" cellspacing="0" width=700 cellpadding="0" align="center">
  <tr><td bgcolor="#9999CC" colspan=6><div align="center"><font color="#FFFFFF" size="4"><i>Nodes Checkpoint </i></font></div></td></tr>
  <tr bgcolor=white >      
          <td width="290"> <div align="center"><a href="Dispatcher?operation=cp&tri=cp_node"> <img src="arrow.gif" width="9" height="13"> </a><b><i><font color="#9999CC">Node</font></i></b></div> </td>
          <td width="61">  <div align="center"><a href="Dispatcher?operation=cp&tri=cp_nat">  <img src="arrow.gif" width="9" height="13"> </a><b><i><font color="#9999CC">Ip publique</font></i></b></div>    </td>
          <td width="93">  <div align="center"><a href="Dispatcher?operation=cp&tri=cp_ip">   <img src="arrow.gif" width="9" height="13"> </a> <b><i><font color="#9999CC">Ip interne</font></i></b></div> </td>
        
  </tr>
   <% ArrayList liste       = (ArrayList)session.getValue("liste_dns") ;
        for (int i=0; i<liste.size(); i++)   {  ItemDns item = (ItemDns) liste.get(i); 
        String dns=item.getDn_name() ; 
        String old="";
        String[] T;
        T = new String[3];
        for(int t=0; t < 3 ; t++) {  T[t]=" "; }
        if(!(old.equals(dns)))  
		{ %>
		<tr bgcolor=white > 
          <td width="353"><a href="Dispatcher?operation=onedns&node=<%=item.getCp_node()%>"><font color="#000033" size="2"> <%=item.getDn_name()%></font>   </a></td>
          <td width="104"> <font color="#000033" size="2"><%=T[0]%></font></div></td>
          <td width="121"> <font color="#000033" size="2"><%=T[1]%></font></div></td>
		  <td width="122"> <font color="#000033" size="2"><%=T[2]%></font></div></td>
 	   </tr>
<% }      
        old=dns;
 	    for(int t=0; t < 3 ; t++) {  T[t]=" "; }		
 	    if (item.getDn_dns().equals("mayotte")) {  T[0]="X"; } 
 	    if (item.getDn_dns().equals("comores")) {  T[1]="X"; } 
 	    if (item.getDn_dns().equals("etab"))    {  T[2]="X"; }
        
        %>
  <tr bgcolor=white > 
          <td width="290"><a href="Dispatcher?operation=onedns&node=<%=item.getCp_node()%>"><font color="#000033" size="2"> <%=item.getCp_node()%></font>   </a></td>
          <td width="61"> <font color="#000033" size="2"><%=item.getCp_nat()%></font></div></td>
          <td width="93"> <font color="#000033" size="2"><%=item.getCp_ip()%></font></div></td>
  </tr>
<% } 
}
catch(IOException e)
 	{	System.out.println("<H2>"+"ClassNotFoundException: " + e.getMessage() + "<BR>");  	}
%> 
</table>
</td></tr></table>
<!-- table liste appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
 
<br>
 
</body>
</html>

