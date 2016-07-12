<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
<%@ page errorPage="PbGeneral.jsp" %>
<html>
<head>
<title>instanceWA</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head> 

<body bgcolor="#FFFFFF" text="#000000">
<script language="JavaScript1.1">
function setPointer(theRow, thePointerColor)
{   if (thePointerColor == '' || typeof(theRow.style) == 'undefined') {
        return false;  }
    if (typeof(document.getElementsByTagName) != 'undefined') {
        var theCells = theRow.getElementsByTagName('td');
    }
    else if (typeof(theRow.cells) != 'undefined') {
        var theCells = theRow.cells;
    }
    else {         return false;    }

    var rowCellsCnt  = theCells.length;
    for (var c = 1; c < rowCellsCnt; c++) {
        theCells[c].style.backgroundColor = thePointerColor;
    }

    return true;
} // end of the 'setPointer()' function

function control() {
if (teststring(document.form.nom) == false) { alert("a saisir !"); document.form.nom.focus() ;return false; }
if (testnum0(document.form.port1)   == false) { alert("num !"); document.form.port1.focus() ;return false; }

if (document.form.do_code.value.length == 0) {
   alert("Le choix du domaine est obligatoire \nCorrigez le ...")
   document.form.do_code.focus()
   return false
}
if (document.form.bd_port.value.length == 0) {
   alert("Le choix de la Bdd  est obligatoire \nCorrigez le ...")
   document.form.bd_port.focus()
   return false
}
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
System.out.println("debut  de jsp:InstanceWA"); 
try
{
UserInfo userinfo = (UserInfo)session.getValue("currentUser"); 
String vnom=userinfo.getNom();
int profil=userinfo.getProfil();
String zone1="bgcolor='#FFFF00'";
String zone2="bgcolor='#FF9900'";
String zone3="bgcolor='#99CCFF'"; 
String voper="";
 
String vlibelle="";
String vshell="";
int vport1=0;
int vcode=0; 
if (request.getParameter("operation")!=null) voper=request.getParameter("operation");
if (request.getParameter("operation")!=null&&(request.getParameter("operation").equals("modInstanceWA")))
{ 
ArrayList listeI     = (ArrayList)session.getValue("liste_one_InstanceWA") ;
for (int i=0; i<listeI.size(); i++)   
{  
ItemApplication item = (ItemApplication) listeI.get(i);  
vcode=   item.getIw_code();
vnom=    item.getIw_nom();
vlibelle=item.getIw_libelle();
vport1=  item.getIw_port1();
vshell=  item.getIw_shell();
}  
} 
ArrayList listeI     = (ArrayList)session.getValue("liste_one_InstanceWA") ;
ItemApplication item1 = (ItemApplication) listeI.get(0);  
%>
<!--  MENU  --> <%@ include file="menu.jsp"%> <!--  MENU  -->  
<!-- table appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
<table border="0" cellspacing="0" cellpadding="1" width=501  bgcolor=#9999CC align="center">   <tr><td>
      <table border="0" cellspacing="0" width=500 cellpadding="0" align="center">
        <tr> 
          <td bgcolor="#9999CC" colspan=2> 
            <div align="center"><i><font color="#FFFFFF" size="5">Ajouter une 
              Instance Webapp</font></i></div>
          </td>
        </tr>
        <form action="Dispatcher" method="post" name="form"   onsubmit='return control()'>
          <tr bgcolor=white> 
            <td colspan=2>&nbsp;</td>
          </tr>
          <tr bgcolor=white> 
            <td align="right" width="203" height="29"><font size="-1" face="PrimaSans BT,Verdana,sans-serif"> 
              <input type="hidden" name="operation" size="8" value='addInstanceWA'>
			  <input type="hidden" name="code"      size="8" value="<%=vcode%>">
              Nom de l' instance</font></td>
            <td width="297" height="29"> 
              <input type="text" name="nom" size="50" value="<%=vnom%>">
            </td>
          </tr>
          <tr bgcolor=white> 
            <td align="right" width="203"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Libelle</font></td>
            <td width="297"> 
              <div align="left">
                <textarea name="libelle" cols="40" rows="3"><%=vlibelle%></textarea>
              </div>
            </td>  
          </tr>
          <tr bgcolor=white> 
            <td align="right" width="203" height="21"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Port 
              liaison vers portail</font></td>
            <td width="297" height="21"> 
              <input type="text" name="port1" size="6" value="<%=vport1%>">
            </td>
          </tr>
		  <tr bgcolor=white> 
            <td align="right" width="203"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Domaine</font></td>
            <td width="297"> 
              <select name="do_code" size="1"> 
     		   <% if(item1.getDo_code()!=0) { %>
			   <option value=<%=item1.getDo_code()%>,<%=item1.getDo_ma_code()%>><%=item1.getDo_nom()%>,<%=item1.getDo_libelle()%>  </option>
                 <% } else { %>
				<option value=""></option>
				<% } %> 
				<% ArrayList listeD        = (ArrayList)session.getValue("liste_DomaineWA") ;
                for (int i=0; i<listeD.size(); i++)   {   ItemApplication item = (ItemApplication) listeD.get(i); %>
                <option value=<%=item.getDo_code()%>,<%=item.getDo_ma_code()%>><%=item.getDo_nom()%>,<%=item.getDo_libelle()%>  </option>
                <% } %>
              </select>
            </td>
          </tr>
		     <tr bgcolor=white> 
            <td align="right" width="203" height="31"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Port 
              Bdd</font></td>
            <td width="297" height="31"> 
              <select name="bd_port" size="1"> 
               <% if(item1.getBd_port()!=0) { %>
			   <option value=<%=item1.getBd_port()%>,<%=item1.getBd_code()%>,<%=item1.getBd_ma_code()%>><%=item1.getBd_type()%>,<%=item1.getBd_repertoire()%>:<%=item1.getBd_port()%> 
                sur <%=item1.getMa_libelle()%></option>  
				<% } else { %>
				<option value=""></option>
				<% } %> 
				<% ArrayList listeB        = (ArrayList)session.getValue("liste_Bdd") ;
                for (int i=0; i<listeB.size(); i++)   {  ItemApplication item = (ItemApplication) listeB.get(i); %>
                <option value=<%=item.getBd_port()%>,<%=item.getBd_code()%>,<%=item.getBd_ma_code()%>><%=item.getBd_type()%>,<%=item.getBd_repertoire()%>:<%=item.getBd_port()%> 
                sur <%=item.getMa_libelle()%></option>
                <% } %>
              </select>
            </td>
          </tr>
		  <tr bgcolor=white> 
            <td align="right" width="203" height="18"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Shell 
              lancement </font></td>
            <td width="297" height="18"> 
              <input type="text" name="shell" size="50" value="<%=vshell%>">
            </td>
          </tr>
          <tr bgcolor=white>
            <td colspan=2 height="40" > 
              <div align="center"> 
                <p>
                  <input type="reset"  name="Submit"   value="R&eacute;tablir">
                  <input type="submit" value="Ajouter" width="72"  name="submit">
                   <% if(vcode!=0) { %>
                  <input type="submit" value="Modifier" width="72" name="submit">
                 <% } %>
                </p>
              </div></td></tr>
        </form>
      </table>
</td></tr></table>
<br>
<!-- table liste appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
<table border="0" cellspacing="0" cellpadding="1" width=801  bgcolor=#9999CC align="center">   <tr><td>
      <table border="0" cellspacing="0" width=800 cellpadding="0" align="center">
        <tr> 
          <td bgcolor="#9999CC" colspan=8> 
            <div align="center"><font color="#FFFFFF" size="4"><i>Liste des Instances 
              Webapps </i></font></div>
          </td>
        </tr>
        <tr bgcolor=white > 
          <td> 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">Zone</font></i></b></font></div>
          </td>
          <td><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">Machine</font></i></b></font></td>
          <td><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">Webapp</font></i></b></font></td>
          <td width="44"> 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">Java </font></i></b></font></div>
          </td>
          <td width="39"> 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">Bdd </font></i></b></font></div>
          </td>
           
           
          <td width="88"> 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">Etat</font></i></b></font></div>
          </td>
          <td width="104"  > 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">Date 
              Etat</font></i></b></font></div>
          </td>
          <td width=100> 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">action</font></i></b></font></div>
          </td>
        </tr>
        <% ArrayList liste       = (ArrayList)session.getValue("liste_InstanceWA") ;
        for (int i=0; i<liste.size(); i++)   {  ItemApplication  item = (ItemApplication) liste.get(i); %>
<tr   bgcolor=white height="20"
			   onmouseover="setPointer(this, '#CCFFCC')" 
			   onmouseout="setPointer(this, '#ffffff')" align="left" valign="bottom" > 
          <td width="34" height="20" 
		  <%        if(item.getMa_zn_code()==1) out.println(zone1);
                    if(item.getMa_zn_code()==2) out.println(zone2);
                    if(item.getMa_zn_code()==3) out.println(zone3);  %>> 
            <div align="center"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
              <%=item.getMa_zn_code()%></font></div>
          </td>
          <td width="80"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"><%=item.getMa_sigle()%></font></td>
          <td width="297"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"><em><strong><%=item.getIw_nom()%></strong></em></font>(<%=item.getIw_libelle()%>)</td>
          <td width="44"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
            <b><i><%=item.getIw_port1()%></i></b></font></td>
          <td width="39"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
            <%=item.getIw_port2()%></font></td>
 
                    
          <td width="88"><font face="Arial, Helvetica, sans-serif" size="2"> 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"> 
              
			  <% String num=item.getIw_port1_etat()  ;  %><img src="images/boule_<%=num%>.gif" width="20" height="20"  >
			  <%        num=item.getIw_port2_etat()  ;  %>
			  <font face="Arial, Helvetica, sans-serif" size="2"><img src="images/boule_<%=num%>.gif" width="20" height="20"  ></font>
              </font></div></font> 
          </td>
            
          <td width="104"  ><font color="#000033" size="-2" face="Arial, Helvetica, sans-serif"> 
            <%=item.getIw_port1_etat_date()%></font></td> 
           
          <td width="100">
            <div align="right"><font face="Arial, Helvetica, sans-serif" size="2"> 
              <% if (item.getNb_joint()== 0) { %>
              <a href="Dispatcher?operation=supinstancewa&code=<%=item.getIw_code()%>"> 
              <img src="images/poubelle.gif" width="14" height="14" border="0"></a> 
              <% } else { %>
              <img src="images/poubelle0.gif" width="14" height="14" border="0"> 
              <% } %>
              <a href="Dispatcher?operation=modInstanceWA&code=<%=item.getIw_code()%>"><img src="avis4.gif" width="19" height="15" border="0"></a> 
              </font></div>
          </td>
        </tr>
        <% } 
		
}
catch(IOException e)
 	{	System.out.println("<H2>"+"ClassNotFoundException: " + e.getMessage() + "<BR>");
 	}
%>
      </table>
</td></tr></table>
<br>

<div align="center"><img src="images/logo_java.gif" width="103" height="53"> <img src="images/apache_pb.gif" width="259" height="32"> 
</div>
</body>
</html>

