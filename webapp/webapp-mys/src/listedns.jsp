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
<table width=700 border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#9999CC">
        <tr><td bgcolor="#9999CC" colspan=6><div align="center"><font color="#FFFFFF" size="4"><i> Dns </i></font></div></td></tr>
  <tr bgcolor=white >      
          <td width="61">  <div align="center"> <b><i><font color="#9999CC">Name</font></i></b></div>    </td>
          <td width="61">  <div align="center">  <b><i><font color="#9999CC">Mayotte</font></i></b></div>    </td>
          <td width="93">  <div align="center">  <b><i><font color="#9999CC">Comores</font></i></b></div> </td>
          <td width="93">  <div align="center">  <b><i><font color="#9999CC">Etab</font></i></b></div> </td>
		  <td width="93">  <div align="center">  <b><i><font color="#9999CC">Ibis</font></i></b></div> </td>
          <td width="93" bordercolor="#CC99FF" bgcolor="#CCCCFF"> <div align="center"> <b><i><font color="#9999CC">Nb réf CP</font></i></b></div> </td>
  </tr>
   <% 
   String old="deb";
   String oldnode="deb";
   String oldip="deb";
   int oldnb=0;
   
   String[] T;
   String[] TIP;
   String[] TNODE;
   
   T = new String[4];
   for(int t=0; t < 4 ; t++) {  T[t]=" "; }
   TIP = new String[4];
   for(int t=0; t < 4 ; t++) {  TIP[t]=" "; }
   TNODE = new String[4];
   for(int t=0; t < 4 ; t++) {  TNODE[t]=" "; }
   
   ArrayList liste       = (ArrayList)session.getValue("liste_dns") ;
        for (int i=0; i<liste.size(); i++)   {  ItemDns item = (ItemDns) liste.get(i); 
        String dns=item.getDn_name() ; 
		String node=item.getDn_node() ;
		String ip=item.getDn_ip() ;
		int nb=item.getDn_node_nb() ;
		if (old.equals("deb")) 
		{
			old=dns;
 			oldnode=node;
			oldip=ip;
			oldnb=nb;
		}
        System.out.println("---:old:"+old+ " -- en cours:"+item.getDn_name()+" "+item.getDn_dns()+ " ---  nb="+oldnb);
        if(!(old.equals(dns)))
		{ %>
		
		<tr  bgcolor=white  onmouseover="setPointer(this, '#FFFFCC')" onmouseout="setPointer(this, '#ffffff')" > 
          
		  <td width="353"><a href="http://<%=old%>"><font color="#000033" size="3">   <%=old%></font> </a></td>
          
		  <td width="104"> <div align="center">
          <% if ( T[0].equals("A")) { %><font color="#F00033" size="2"> <% } else { %> <font color="#000033" size="2"> <% } %>
          <a href="Dispatcher?operation=onedns&node=<%=TNODE[0]%>&ip1=<%=TIP[0]%>&ip2=<%=TIP[1]%>&ip3=<%=TIP[2]%>"><font color="#000033" size="3"> <%=T[0]%></font> </a>
		  <% if ( T[0].equals("A")) { %> </style><img src="arrow.gif" onMouseOver="FoToolTip.show(this,'<%=TIP[0]%>')"><% } %> </font></div>
		  </td>
         
		  <td width="104"> <div align="center">
 			<% if ( T[1].equals("A")) { %><font color="#F00033" size="2"> <% } else { %> <font color="#000033" size="2"> <% } %>
			<a href="Dispatcher?operation=onedns&node=<%=TNODE[1]%>&ip1=<%=TIP[0]%>&ip2=<%=TIP[1]%>&ip3=<%=TIP[2]%>"><font color="#000033" size="3"> <%=T[1]%></font> </a>
		    <% if ( T[1].equals("A")) { %> </style><img src="arrow.gif" onMouseOver="FoToolTip.show(this,'<%=TIP[1]%>')"><% } %> </font></div>
		  </td>
          
		  <td width="104"> <div align="center">
            <% if ( T[2].equals("A")) { %><font color="#F00033" size="2"> <% } else { %> <font color="#000033" size="2"> <% } %>
            <a href="Dispatcher?operation=onedns&node=<%=TNODE[2]%>&ip1=<%=TIP[0]%>&ip2=<%=TIP[1]%>&ip3=<%=TIP[2]%>"><font color="#000033" size="3"> <%=T[1]%></font> </a>
			<% if ( T[2].equals("A")) { %> </style><img src="arrow.gif" onMouseOver="FoToolTip.show(this,'<%=TIP[2]%>')"><% } %> </font></div>
		  </td>
		  
		  <td width="104"> <div align="center">
            <% if ( T[3].equals("A")) { %><font color="#F00033" size="2"> <% } else { %> <font color="#000033" size="2"> <% } %>
            <a href="Dispatcher?operation=onedns&node=<%=TNODE[3]%>&ip1=<%=TIP[0]%>&ip2=<%=TIP[1]%>&ip3=<%=TIP[3]%>"><font color="#000033" size="3"> <%=T[3]%></font> </a>
			<% if ( T[3].equals("A")) { %> </style><img src="arrow.gif" onMouseOver="FoToolTip.show(this,'<%=TIP[3]%>')"><% } %> </font></div>
		  </td>
		  
		   <td width="104"> <div align="center"><% if ( oldnb >1 )   {  %>   <%=oldnb%>  <% } %> </div></td>
       
	    </tr>
		
		  
        <% 
		old=dns;
		oldnode=node;
		oldnb=nb;
		 for(int t=0; t < 4 ; t++) {  T[t]=" "; }
		 for(int t=0; t < 4 ; t++) {  TIP[t]="0"; }
		 for(int t=0; t < 4 ; t++) {  TNODE[t]=" "; }
		}      
       		
 	    if (item.getDn_dns().equals("mayotte")) {  T[0]=item.getDn_type(); TIP[0]=item.getDn_ip(); TNODE[0]=item.getDn_node() ;} 
 	    if (item.getDn_dns().equals("comores")) {  T[1]=item.getDn_type(); TIP[1]=item.getDn_ip(); TNODE[1]=item.getDn_node() ;} 
 	    if (item.getDn_dns().equals("etab"))    {  T[2]=item.getDn_type(); TIP[2]=item.getDn_ip(); TNODE[2]=item.getDn_node() ;} 
		if (item.getDn_dns().equals("ibis"))    {  T[3]=item.getDn_type(); TIP[3]=item.getDn_ip(); TNODE[3]=item.getDn_node() ;} 
        
        %>
   
<% } %>
<tr  bgcolor=white  onmouseover="setPointer(this, '#FFFFCC')" onmouseout="setPointer(this, '#ffffff')" > 
          <td width="353"><a href="http://<%=old%>"><font color="#000033" size="3">   <%=old%></font> </a></td>
          <td width="104"> <div align="center">
          <% if ( T[0].equals("A")) { %><font color="#F00033" size="2"> <% } else { %> <font color="#000033" size="2"> <% } %>
          <a href="Dispatcher?operation=onedns&node=<%=TNODE[0]%>&ip1=<%=TIP[0]%>&ip2=<%=TIP[1]%>&ip3=<%=TIP[2]%>"><font color="#000033" size="3"> <%=T[0]%></font> </a>
		  <% if ( T[0].equals("A")) { %> </style><img src="arrow.gif" onMouseOver="FoToolTip.show(this,'<%=TIP[0]%>')"><% } %> </font></div></td>
         
		  <td width="104"> <div align="center">
 			<% if ( T[1].equals("A")) { %><font color="#F00033" size="2"> <% } else { %> <font color="#000033" size="2"> <% } %>
			<a href="Dispatcher?operation=onedns&node=<%=TNODE[1]%>&ip1=<%=TIP[0]%>&ip2=<%=TIP[1]%>&ip3=<%=TIP[2]%>"><font color="#000033" size="3"> <%=T[1]%></font> </a>
		    <% if ( T[1].equals("A")) { %> </style><img src="arrow.gif" onMouseOver="FoToolTip.show(this,'<%=TIP[1]%>')"><% } %> </font></div></td>
          
		  <td width="104"> <div align="center">
            <% if ( T[2].equals("A")) { %><font color="#F00033" size="2"> <% } else { %> <font color="#000033" size="2"> <% } %>
            <a href="Dispatcher?operation=onedns&node=<%=TNODE[2]%>&ip1=<%=TIP[0]%>&ip2=<%=TIP[1]%>&ip3=<%=TIP[2]%>"><font color="#000033" size="3"> <%=T[1]%></font> </a>
			<% if ( T[2].equals("A")) { %> </style><img src="arrow.gif" onMouseOver="FoToolTip.show(this,'<%=TIP[2]%>')"><% } %> </font></div></td>
		  
		  <td width="104"> <div align="center">
            <% if ( T[3].equals("A")) { %><font color="#F00033" size="2"> <% } else { %> <font color="#000033" size="2"> <% } %>
            <a href="Dispatcher?operation=onedns&node=<%=TNODE[3]%>&ip1=<%=TIP[0]%>&ip2=<%=TIP[1]%>&ip3=<%=TIP[3]%>"><font color="#000033" size="3"> <%=T[3]%></font> </a>
			<% if ( T[3].equals("A")) { %> </style><img src="arrow.gif" onMouseOver="FoToolTip.show(this,'<%=TIP[3]%>')"><% } %> </font></div>
		  </td>
		  
		  <td width="104"> <div align="center"><% if ( oldnb >1 )   {  %>   <%=oldnb%>  <% } %> </div></td>
        </tr>
<%  }
catch(IOException e)
 	{	System.out.println("<H2>"+"ClassNotFoundException: " + e.getMessage() + "<BR>");  	}
%> 
</table>
</td></tr></table>
<!-- table liste appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
 
<br>
 
</body>
</html>

