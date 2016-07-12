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

<script type="text/javascript" language="javascript">
function FoToolTip() {
    var tt_id="fotooltip";
    var tt_bgcolor="#FFFFCC";
    var tt_bordercolor="#FFE2A6";
/** créer l'objet ToolTip   assigner les evt au 'parent'  */
    this.init=function (obj,text) {
        tt=getInstance();
        tt.style.display='block';
        obj.onmousemove=mouseMouveEx;
        obj.onmouseout=hide;
        loadcontent(text);
    }
/** private: Charger le contenu du ToolTip */
    function loadcontent(text) {
        tt=getInstance();
        if (text.substr(0,4)!='http') {
            tt.innerHTML=text;
        } else {
            tt.innerHTML='Chargement en cours ...';
           /* new ajax().request(text,
            {
                request:'GET',
                onresponse:function(response, success) {
                    tt=getInstance();
                    tt.innerHTML=response;
                }
            });*/
        }
    }
/** private: cacher le ToolTip */
    function hide() {
        tt=getInstance();
        tt.style.display='none';
    }
/** private: deplacer le ToolTip**/
    function mouseMouveEx(e) {
        var pos=getPos(e);
        tt=getInstance();
        tt.style.left=(pos.x+20)+"px";
        tt.style.top=pos.y+"px";
    }
/** private: retourne un nouveau ToolTip ou instance en cours**/
    function getInstance() {
        var tt=document.getElementById(tt_id);
        if (!tt) {
            tt=document.createElement("div");
            tt.style.position="absolute";
            tt.style.backgroundColor=tt_bgcolor;
            tt.style.border='1px solid '+tt_bordercolor;
            tt.style.padding="5px";
            tt.id=tt_id;
            document.body.appendChild(tt);
            console.debug("create new foToolTip");
            return tt;
        } else {
            return tt;
        }
    }
/** private: position du curseur */
    function getPos(e) {
        e = e || window.event;
        var cursor = {
            x:0,

            y:0
        };
        if (e.pageX || e.pageY) {
            cursor.x = e.pageX;
            cursor.y = e.pageY;
        }
        else {
            cursor.x = e.clientX
            (document.documentElement.scrollLeft ||
                document.body.scrollLeft) -
            document.documentElement.clientLeft;
            cursor.y = e.clientY
            (document.documentElement.scrollTop ||
                document.body.scrollTop) -
            document.documentElement.clientTop;
        }
        return cursor;
    }
}
 
/**
 * Static Call de ToolTip, faciliter l'utilisaton du TooTip
 */
FoToolTip.show=function(obj,text) {
    var foToolTip=new FoToolTip();
    foToolTip.init(obj, text);
};
</script>
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
 
<p><br>
  <% ArrayList onecp      = (ArrayList)session.getValue("liste_cp") ;
   ItemDns cp = (ItemDns) onecp.get(0);  %>
<table border="1" cellspacing="0" width=300 cellpadding="0" align="center">
  <tr bordercolor="#FFFFFF"> 
    <td colspan="2" bgcolor="#CC9999"> 
      <div align="center"><font color="#000000" size="+3">Stonesoft</font> </div></td>
  </tr>
  <tr bordercolor="#FFFFFF"> 
    <td> Nom du noeud </td>
    <td> <div align="right"><font size="+1"> <%=cp.getCp_node()%> </font> </div></td>
  </tr>
  <tr bordercolor="#FFFFFF"> 
    <td> Adresse de nat</td>
    <td><div align="right"><font size="+1"> <%=cp.getCp_nat()%> </font> </div></td>
  </tr>
  <tr bordercolor="#FFFFFF"> 
    <td>Adresse interne</td>
    <td><div align="right"><font size="+1"> <%=cp.getCp_ip()%> </font> </div></td>
  </tr>
</table>
<br>
<table border="0" cellspacing="0" cellpadding="1" width=701  bgcolor=#9999CC align="center">   <tr><td>
<table border="0" cellspacing="0" width=700 cellpadding="0" align="center">
  <tr><td bgcolor="#9999CC" colspan=6><div align="center"><font color="#FFFFFF" size="4"><i> Dns </i></font></div></td></tr>
        </tr>
        <tr bgcolor=white > 
          <td width="61"> <div align="center"><font size="3"><b><i><font color="#9999CC">Name</font></i></b></font></div></td>
          <td width="61"> <div align="center"><font size="3"><b><i><font color="#9999CC">Mayotte</font></i></b></font></div></td>
          <td width="93"> <div align="center"><font size="3"> <b><i><font color="#9999CC">Comores</font></i></b></font></div></td>
          <td width="93"> <div align="center"><font size="3"> <b><i><font color="#9999CC">Etab</font></i></b></font></div></td>
        </tr>
        <% 
   String old="deb";
   String oldnode="deb";
   String oldip="deb";
   String[] T;
   String[] TIP;
   T = new String[3];
   for(int t=0; t < 3 ; t++) {  T[t]=" "; }
   TIP = new String[3];
   for(int t=0; t < 3 ; t++) {  TIP[t]=" "; }
   ArrayList liste       = (ArrayList)session.getValue("liste_dns") ;
        for (int i=0; i<liste.size(); i++)   {  ItemDns item = (ItemDns) liste.get(i); 
        String dns=item.getDn_name() ; 
		String node=item.getDn_node() ;
		String ip=item.getDn_ip() ;
		if (old.equals("deb")) 
        {
			old=dns;
 			oldnode=node;
			oldip=ip;
		}
        
        System.out.println("---:old:"+old+ " en cours:"+item.getDn_name()+" "+item.getDn_dns());
		if(!(old.equals(dns)))
		{ 
        	 System.out.println("--- diff : old:" + old + "  en cours: " + item.getDn_name()+" "+item.getDn_dns());
		%> 
       <tr  bgcolor=white  onmouseover="setPointer(this, '#FFFFCC')" onmouseout="setPointer(this, '#ffffff')" > 
          <td width="353"><a href="http://<%=old%>"><font color="#000033" size="3"> 
           <%=old%></font> </a></td>
           <td width="104"> <div align="center">
          <% if ( T[0].equals("A")) { %><font color="#F00033" size="2"> <% } else { %> <font color="#000033" size="2"> <% } %>
          <%=T[0]%><% if ( T[0].equals("A")) { %> <br> <%=TIP[0]%> <% } %> </font></div></td>
          <td width="104"> <div align="center">
 			<% if ( T[1].equals("A")) { %><font color="#F00033" size="2"> <% } else { %> <font color="#000033" size="2"> <% } %>
			<%=T[1]%><% if ( T[1].equals("A")) { %> <br> <%=TIP[1]%> <% } %> </font></div></td>
          <td width="104"> <div align="center">
            <% if ( T[2].equals("A")) { %><font color="#F00033" size="2"> <% } else { %> <font color="#000033" size="2"> <% } %>
            <%=T[2]%><% if ( T[2].equals("A")) { %> <br> <%=TIP[2]%> <% } %> </font></div></td>
        </tr>
        <% 
		old=dns;
		 for(int t=0; t < 3 ; t++) {  T[t]=" "; }
		}      
        		
 	   if (item.getDn_dns().equals("mayotte")) {  T[0]=item.getDn_type(); TIP[0]=item.getDn_ip(); } 
 	    if (item.getDn_dns().equals("comores")) {  T[1]=item.getDn_type(); TIP[1]=item.getDn_ip(); } 
 	    if (item.getDn_dns().equals("etab"))    {  T[2]=item.getDn_type(); TIP[2]=item.getDn_ip(); } 
        
        %>
        <% } 
        %>
         <tr  bgcolor=white  onmouseover="setPointer(this, '#FFFFCC')" onmouseout="setPointer(this, '#ffffff')" > 
          <td width="353"><a href="http://<%=old%>"><font color="#000033" size="3"> <%=old%></font> </a></td>
          <td width="104"> <div align="center">
          <% if ( T[0].equals("A")) { %><font color="#F00033" size="2"> <% } else { %> <font color="#000033" size="2"> <% } %>
          <%=T[0]%><% if ( T[0].equals("A")) { %><br> <%=TIP[0]%> <% } %> </font></div></td>
		  
          <td width="104"> <div align="center">
 			<% if ( T[1].equals("A")) { %><font color="#F00033" size="2"> <% } else { %> <font color="#000033" size="2"> <% } %>
			<%=T[1]%><% if ( T[1].equals("A")) { %><br>  <%=TIP[1]%> <% } %> </font></div></td>
			
          <td width="104"> <div align="center">
            <% if ( T[2].equals("A")) { %><font color="#F00033" size="2"> <% } else { %> <font color="#000033" size="2"> <% } %>
            <%=T[2]%><% if ( T[2].equals("A")) { %><br> <%=TIP[2]%> <% } %> </font></div></td>
			
        </tr>
        <%         
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

