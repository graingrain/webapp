<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
 
<html>
<head>
<title>Portail_InstanceWA</title>
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
if (document.form.pt_code.value.length == 0) {
   alert("Le choix du portail  est obligatoire \nCorrigez le ...")
   document.form.pt_code.focus()
   return false
}
if (document.form.port.value.length == 0) {
   alert("Le choix de instanceWA est obligatoire \nCorrigez le ...")
   document.form.port.focus()
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
System.out.println("debut jsp:PortailIW"); 
try
{
UserInfo userinfo = (UserInfo)session.getValue("currentUser"); 
String vnom=userinfo.getNom();
int profil=userinfo.getProfil();
String voper="";
 
String vlibelle="";
String vportail="";
int vport=0;
int vcode=0;
String zone1="bgcolor='#FFFF00'";
String zone2="bgcolor='#FF9900'";
String zone3="bgcolor='#99CCFF'";
if (request.getParameter("operation")!=null) voper=request.getParameter("operation");
if (request.getParameter("operation")!=null&&(request.getParameter("operation").equals("modPortailIW")))
{ 
ArrayList listeI     = (ArrayList)session.getValue("liste_one_PortailIW") ;
for (int i=0; i<listeI.size(); i++)   
{  
ItemApplication item = (ItemApplication) listeI.get(i);  
vcode   =    item.getPi_code();
vnom    =    item.getPi_nom();
vlibelle=    item.getPi_libelle();
vportail=    item.getPi_portail();
vport   =    item.getPi_port();
}  
} %>
<%
ArrayList liste_one_portailIW       = (ArrayList)session.getValue("liste_one_PortailIW") ;
System.out.println("nb Pi:"+liste_one_portailIW.size());
ItemApplication item1 = (ItemApplication) liste_one_portailIW.get(0);
System.out.println("nom"+item1.getPi_code());
%> 
 <!--  MENU  --> <%@ include file="menu.jsp"%> <!--  MENU  --> 
<!--  table appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
<table border="0" cellspacing="0" cellpadding="1" width=501  bgcolor=#9999CC align="center">   <tr><td>
      <table border="0" cellspacing="0" width=500 cellpadding="0" align="center">
        <tr> 
          <td bgcolor="#9999CC" colspan=2> 
            <div align="center"><i><font color="#FFFFFF" size="5">Ajouter un Portail - Instance Webapp</font></i></div>
          </td>
        </tr>
        <form action="Dispatcher" method="post" name="form"   onsubmit='return control()'>
          <tr bgcolor=white> 
            <td colspan=2>&nbsp;</td>
          </tr>
          <tr bgcolor=white> 
            <td align="right" width="203"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">nom 
              de l' instance</font></td>
            <td width="297"> 
              <input type="hidden" name="operation" size="8" value='addPortailIW'>
			  <input type="hidden" name="code"                value="<%=item1.getPi_code()%>">
              <input type="text"   name="nom"  size="50"      value="<%=item1.getPi_nom()%>">
			    </td>
          </tr>
          <tr bgcolor=white> 
            <td align="right" width="203"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">libelle</font></td>
            <td width="297"> 
              <div align="right">
                <textarea name="libelle" cols="40" rows="3"><%=item1.getPi_libelle()%></textarea>
              </div>
            </td>
          </tr>
           
		  <tr bgcolor=white> 
            <td align="right" width="203"><font face="PrimaSans BT,Verdana,sans-serif" size="-1">Portail</font></td>
            <td width="297"> 
              <select name="pt_code" size="1">
			   <% if(item1.getPt_code()!=0) { %>
			   <option value=<%=item1.getPt_code()%>><%=item1.getMa_sigle()%>:<%=item1.getPt_port()%> <%=item1.getPt_type()%>,<%=item1.getPt_version()%>  </option>
               <% } else { %>
				<option value=""></option>
				<% } %> 
                <% ArrayList listeD        = (ArrayList)session.getValue("liste_Portail") ;
                for (int i=0; i<listeD.size(); i++)   {  ItemApplication item = (ItemApplication) listeD.get(i); %>
                <option value=<%=item.getPt_code()%>><%=item.getMa_sigle()%>:<%=item.getPt_port()%> <%=item.getPt_type()%>,<%=item.getPt_version()%>  </option>
                <% } %>
              </select>
            </td>
          </tr>
		     <tr bgcolor=white> 
            <td align="right" width="203"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Instance 
              Webapp </font></td>
            <td width="297"> 
              <select name="port" size="1">
                <% if(item1.getIw_code()!=0) { %>
				<option value=<%=item1.getIw_code()%>,<%=item1.getIw_port1()%>><%=item1.getIw_nom()%>,<%=item1.getIw_libelle()%>:<%=item1.getIw_port1()%> 
                </option>   
				<% } else { %>
				<option value=""></option>
				<% } %>
                 <% ArrayList listeB        = (ArrayList)session.getValue("liste_InstanceWA") ;
                for (int i=0; i<listeB.size(); i++)   {  ItemApplication item = (ItemApplication) listeB.get(i); %>
                <option value=<%=item.getIw_code()%>,<%=item.getIw_port1()%>><%=item.getIw_nom()%>,<%=item.getIw_libelle()%>:<%=item.getIw_port1()%> 
                </option>                <% } %>
              </select>
            </td>
          </tr>
          <tr bgcolor=white> 
                  <td colspan=2 > 
                    <div align="center"> 
                      <p> 
                        <input type="reset"  name="Submit"   value="R&eacute;tablir">
                        <input type="submit" value="Ajouter" width="72"  name="submit">
                        <% if(vcode!=0) { %>
                        <input type="submit" value="Modifier" width="72" name="submit">
                        <% } %>
                      </p>
                    </div>
                  </td>
                </tr>
        </form>
      </table>
</td></tr></table>
<br>
<!-- table liste appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
<table border="0" cellspacing="0" cellpadding="1" width=801  bgcolor=#9999CC align="center">   <tr><td>
      <table border="0" cellspacing="0" width=800 cellpadding="0" align="center">
        <tr> 
          <td bgcolor="#9999CC" colspan=14> 
            <div align="center"><font color="#FFFFFF" size="4"><i>Liste des Portails 
              - Instances Webapps </i></font></div>
          </td>
        </tr>
        <tr bgcolor=white > 
          <td width="38" rowspan="2"> 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">Zone</font></i></b></font></i></b></font></div>
          </td>
          <td width="202" rowspan="2"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">NOM 
            INSTANCE</font></i></b></font></i></b></font></td>
          <td width="85" rowspan="2"> 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">Portail</font></i></b></font></div>
          </td>
          <td width="90" rowspan="2"> 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">Webapp</font></i></b></font></div>
          </td>
          <td width="81" rowspan="2"> 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">Bdd</font></i></b></font></div>
          </td>
          <td width="35" rowspan="2"> 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC"> 
              </font></i></b></font></div>
          </td>
          <td colspan="5"> 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">Etat</font></i></b></font></div>
          </td>
          <td width="35" rowspan="2"  > 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i></i></b></font></div>
          </td>
          <td width=94 rowspan="2"> 
            <div align="center"><font face="Arial, Helvetica, sans-serif" size="2"><b><i><font color="#9999CC">action</font></i></b></font></div>
          </td>
        </tr>
        <tr bgcolor=white > 
          <td width="28"> 
            <div align="center"><font size="1" color="#9999CC"><i>P</i></font></div>
          </td>
          <td width="28"> 
            <div align="center"><i><font size="1" color="#9999CC">P-I</font></i></div>
          </td>
          <td width="28"> 
            <div align="center"><i><font size="1" color="#9999CC">I</font></i></div>
          </td>
          <td width="28"> 
            <div align="center"><i><font size="1" color="#9999CC">I-B</font></i></div>
          </td>
          <td width="28"> 
            <div align="center"><i><font size="1" color="#9999CC">B</font></i></div>
          </td>
        </tr>
        <% ArrayList listePi       = (ArrayList)session.getValue("liste_PortailIW") ;
        
        for (int i=0; i<listePi.size(); i++)   {  ItemApplication  item = (ItemApplication) listePi.get(i); 
        
%>
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
          <td width="202" height="0"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif">&nbsp;<%=item.getPi_nom()%></font></td>
          <td width="85" height="0"><font face="Arial, Helvetica, sans-serif" size="2">&nbsp;<%=item.getPt_sigle()%>:<%=item.getPt_port()%></font> 
          </td>
          <td width="90" height="0"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
            <i>&nbsp;<%=item.getIw_sigle()%>:<%=item.getIw_port1()%></i></font></td>
          <td width="81" height="0"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
            &nbsp;<i><%=item.getBd_sigle()%>:<%=item.getIw_port2()%></i></font> 
          </td>
          <td width="35" height="0"> 
            <div align="center"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"><font size="2"><font size="2"><font face="Arial, Helvetica, sans-serif"></font></font></font> 
              </font></div>
          </td>
          <td width="28" height="0"> 
            <% String num=item.getPt_port_etat()  ;  %>
            <img src="images/boule_<%=num%>.gif" height="20"  > </td>
          <td width="28" height="0"> 
            <%        num=item.getPi_port_etat() ;  %>
            <img src="images/boule_<%=num%>.gif" height="20"  > </td>
          <td width="28" height="0"> 
            <%        num=item.getIw_port1_etat() ;  %>
            <img src="images/boule_<%=num%>.gif" height="20"  > </td>
          <td width="28" height="0"> 
            <%        num=item.getIw_port2_etat() ;  %>
            <img src="images/boule_<%=num%>.gif" height="20"  > </td>
          <td width="28" height="0"> 
            <%        num=item.getBd_port_etat() ;  %>
            <img src="images/boule_<%=num%>.gif" height="20"  > </td>
          <td width="35" height="0"><font face="Arial, Helvetica, sans-serif" size="2"></font> 
          </td>
          <td width="94" height="0"><font face="Arial, Helvetica, sans-serif" size="2"> 
            <% if (item.getNb_joint()== 0) { %>
            <a href="Dispatcher?operation=supportailiw&code=<%=item.getPi_code()%>"> 
            <img src="images/poubelle.gif" width="14" height="14" border="0"></a> 
            <% } else { %>
            <img src="images/poubelle0.gif" width="14" height="14" border="0"> 
            <% } %>
            <a href="Dispatcher?operation=modPortailIW&code=<%=item.getPi_code()%>"><img src="avis4.gif" width="19" height="15" border="0"></a> 
            </font></td>
        </tr>
         
        <% } 
}
catch(IOException e)
 	{	System.out.println("<H2>"+"ClassNotFoundException: " + e.getMessage() + "<BR>");
 	}
%>
        <% System.out.println("debut jsp:InstanceWA finfin"); %>
      </table>
</td></tr></table>
<br>

<div align="center"><img src="images/logo_java.gif" width="103" height="53"> <img src="images/apache_pb.gif" width="259" height="32"> 
</div>
</body>
</html>

