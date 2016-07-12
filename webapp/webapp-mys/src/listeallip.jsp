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

<table border="1" cellspacing="0" cellpadding="1" width=801  bgcolor=#9999CC align="center">
  <tr>
    <td bordercolor="#FFFFFF"> 
      <table border="1" cellspacing="0" width=800 cellpadding="0" align="center">
        <tr><td bgcolor="#9999CC" colspan=7><div align="center"><font color="#FFFFFF" size="4"><i> Déclaration Dns - Eon </i></font></div></td></tr>
       <tr bgcolor=white >      
          <td width="30">  <div align="center"> <b><i><font color="#9999CC"><a href="Dispatcher?operation=eon&tri=ip"><img src="arrow.gif" width="9" height="13"></a>Ip</font></i></b></div>    </td>
          <td width="80">  <div align="center"> <b><i><font color="#9999CC"><a href="Dispatcher?operation=eon&tri=node"><img src="arrow.gif" width="9" height="13"></a>Name</font></i></b></div>    </td>
          <td width="15">  <div align="center">  <b><i><font color="#9999CC">Comores</font></i></b></div> </td>
          <td width="15">  <div align="center">  <b><i><font color="#9999CC">Mayotte</font></i></b></div> </td>
          <td width="15">  <div align="center">  <b><i><font color="#9999CC">Etab</font></i></b></div> </td>
          <td width="15">  <div align="center">  <b><i><font color="#9999CC">Ibis</font></i></b></div> </td>
	      <td width="15">  <div align="center">  <b><i><font color="#9999CC">Eon</font></i></b></div> </td>
       </tr>
	   
   <% 
    ArrayList liste       = (ArrayList)session.getValue("liste_eon") ;
    System.out.println("nb ip:"+liste.size());
        for (int i=0; i<liste.size(); i++)   {  ItemDns item = (ItemDns) liste.get(i); 
        %>
		<tr bgcolor=white >      
          <td width="30">   <%=item.getAi_ip()%>   </font></td>
         <td width="70">   <%=item.getAi_name()%> </td>
		 <td >  <div align="center"> <% if (item.getAi_mayotte()!=null) { %> <font size="1"><%=item.getAi_mayotte()%> <% } %> </td>
		 <td >  <div align="center"> <% if (item.getAi_comores()!=null) { %> <font size="1"><%=item.getAi_comores()%> <% } %> </td>
		 <td >  <div align="center"> <% if (item.getAi_etab()!=null) { %> <font size="1"><%=item.getAi_etab()%> <% } %> </td>
		 <td >  <div align="center"> <% if (item.getAi_ibis()!=null) { %> <font size="1"><%=item.getAi_ibis()%> <% } %> </td>
		 <td >  <div align="center"> <% if (item.getAi_eon()!=null) { %> <font size="1"><%=item.getAi_eon()%> <% } %> </td>
		 
       <tr>
		  
<%  } }
catch(IOException e)
 	{	System.out.println("<H2>"+"ClassNotFoundException: " + e.getMessage() + "<BR>");  	}
%> 
</table>
</td></tr></table>
<!-- table liste appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
 
<br>
 
</body>
</html>

