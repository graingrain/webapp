<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
  
<HTML>
<% 
String table="partage";
 
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
System.out.println("-------------------------------------- partage : "+ userinfo.getPa_nom() );
%>
<head>
<title><%=table%></title>
<script language="JavaScript1.2" src="resources/control.js"></script>
<link rel="shortcut icon" type="images/x-icon" href="favicon-annuaire.png" />
<script language="JavaScript1.2" src="resources/control.js"></script>
<link href="boiler.plate.css" rel="stylesheet" type="text/css">
<link href="organigramme.ac-reunion.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="menu.organigramme.css" type="text/css" media="screen" />
</head>
<body>  
<%@  include file="include_menu_partage.jsp" %>
<SCRIPT language="javascript">
   function Valider(formulaire,type) {
    
      formulaire.bsubmit.value=type;
       
formulaire.submit()
}
</SCRIPT>
  
<% 
System.out.println("---------- nb partage : "+onepartage.size() );
if(onepartage!=null) { 
for(int i=0; i < onepartage.size(); i++) {  
ItemAnnuaire E  = (ItemAnnuaire) onepartage.get(i); 
if (E.getPa_code()>0) { %>
  
<div id="content-row1" class="detail-agent">     
<h3><a href="Dispatcher?operation=division&codedv=<%=E.getDv_code()%>" class="lien_noir14_none"><%=E.getDv_nom()%></a></h3> 
     <table width="100%" border="0">
        <tr>
     <td width="81%" rowspan="3" align="left" valign="top" style="background-color:#effceb;" > <pre class="menua Style2"><em><%=E.getPa_nomc()%></em></pre>
            <br>
       	  </td>
    <td width="19%" valign="top">
	<span class="lienoutils"><strong><%=E.getAf_libelle_type()%>  </strong></span><br>
	crée le <%=E.getPa_date_creation()%> </td>
   </tr>
 </table>
<br> 
      <%@ include file="include_liste_agent_partage_ng.htm"%>
	  <% } %> 
      <%  if((userinfo.getProfil()>=2)||(userinfo.getProfil()>=2&&E.getPa_code()<=0)||(userinfo.getProfil()==2&&E.getDv_code()==userinfo.getDv_code())||(userinfo.getProfil()==1&&E.getDv_code()==userinfo.getDv_code()))
	  
           { %>	
 <br> 
 <br> 
 <table width="95%"  >
        <tr >
          
    <td height="306" bgcolor="#FFFFFF"> 
      <div align="center"> 
			
        <table width="900" border="0"    cellpadding="0" cellspacing="0"  >
          <tr style="border-top: 1px solid #3b8211;" bgcolor="#3b8211"> 
            <td colspan=4> <div align="center"><i><font color="#FFFFFF" size="5"><%=table%></font></i></div></td>
          </tr>
          <form action="Dispatcher" method="post" name="form"    >
            <tr bgcolor=#effceb> 
              <td width="129"  ><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">nom 
                court</font></em></td>
              <td width="235"  bgcolor=#effceb> &nbsp; <input type="text"   name="nom" size="50" value="<%=E.getPa_nom()%>"> 
                <input type="hidden" name="table" size="8" value=<%=table%>> 
                <input type="hidden" name="codepa" size="8" value=<%=E.getPa_code()%>> 
                <input type="hidden" name="partage" size="8" value=<%=E.getPa_nom()%>> 
                <input type="hidden" name="operation" size="8" value=<%=table%>> 
                <input type="hidden" name="bsubmit"    size="30"  > </td>
              <td width="117" rowspan="4" align="center" valign="middle"  bgcolor=#effceb> 
                
                <p align="center">&nbsp; </p></td>
              <td width="118" rowspan="4" align="center" valign="middle"  bgcolor=#effceb> 
			   <p align="center"> 
                  <%  if((userinfo.getProfil()>=2)&&E.getPa_code()==0) { %>
                  <input type="reset"  name="Submit2"   value="R&eacute;tablir">
                  <input type="button" name="submit1" value="Ajouter"  onClick="Valider(this.form,'Ajouter')">
                  <% } %>
                  <% if(E.getPa_code()>0)     { %>
                  <input type="button" name="submit2" value="Modifier"  onClick="Valider(this.form,'Modifier')">
                  <% if(E.getPa_integrite()==0)     { %>
                  <input name="submit3" type="button" class="menu"  onClick="Valider(this.form,'Supprimer')" value="Supprimer le partage">
                  <% } } %>
                </p></td>
            </tr>
            <tr bgcolor=#effceb> 
              <td   ><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">nom 
                long</font></em></td>
              <td>&nbsp; <input type="text" name="nomc" size="60" value="<%=E.getPa_nomc()%>"> 
              </td>
            </tr>
            <tr bgcolor=#effceb> 
              <td   ><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">description</font></em></td>
              <td>&nbsp; <textarea name="description" cols="50" rows="4"><%=E.getPa_description()%></textarea> 
              </td>
            </tr>
            <tr bgcolor=#effceb> 
              <td  > <em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Division</font></em> 
              </td>
              <td> &nbsp; <% if((userinfo.getProfil()>2)&&(E.getPa_integrite()==0)) { %> <select name="pldv_code" size="1">
                  <option value=<%=E.getDv_code()%>><%=E.getDv_nom()%></option>
                  <% ArrayList liste        = (ArrayList)session.getValue("alldivision") ;
                   for (int p=0; p<liste.size(); p++)   {   ItemAnnuaire division = (ItemAnnuaire) liste.get(p); %>
                  <option value=<%=division.getDv_code()%>><%=division.getDv_nom()%></option>
                  <% } %>
                </select> <% } else { 
					int dvcode=E.getDv_code();
					String dvnom=E.getDv_nom();
					if (userinfo.getProfil()==2) 
					{ 
					dvnom="la votre";
					dvcode=userinfo.getDv_code();
					}
					%> <input type="hidden" name="pldv_code" size="8" value=<%=dvcode%>>
                Division non modifiable: <%=dvnom%> <% } %></td>
            </tr>
            <tr bgcolor=#effceb> 
              <td  > </td>
              <td colspan="3"   ><div align="center"> </td>
            </tr>
            <% if (E.getPa_code()!=0) { %>
            <tr bgcolor=#CCCC66> 
              <td   ><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Ajout 
                membre</font></em></td>
              <td> <p>(ecriture) </p></td>
              <td colspan="2"> <select name="membre_code" size="1" onchange="Valider(this.form,'AjouterMembre')">
                  <option value=0;0>faire un choix</option>
                  <% ArrayList liste        = (ArrayList)session.getValue("allagentdiv") ;
                      for (int p=0; p<liste.size(); p++)   {   ItemAnnuaire item = (ItemAnnuaire) liste.get(p);         %>
                  <option value=<%=item.getDv_code()%>;<%=item.getSv_code()%>;<%=item.getAg_code()%>;<%=item.getAg_ldap_uid()%>;<%=item.getAg_ldap_datenaiss()%>;<%=item.getAg_ldap_mail()%>;<%=item.getAg_ldap_nom()%>;<%=item.getAg_ldap_prenom()%>><%=item.getAg_ldap_nom()%> <%=item.getAg_ldap_prenom()%> - <%=item.getDv_nom()%></option>
                  <%  }  %>
                </select></td>
            </tr>
            <tr bgcolor=#CCCC66> 
              <td   ><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Ajout 
                groupe</font></em></td>
              <td><div align="left">&nbsp; &nbsp;&nbsp; lecture 
                  <input type="radio" name="droit" value="r-x"  checked >
                  &nbsp; ecriture 
                  <input type="radio" name="droit" value="rwx"   >
                </div></td>
              <td colspan="2"> <select name="groupe_code" size="1"  onchange="Valider(this.form,'AjouterGroupe')">
                  <option value=0;0>faire un choix</option>
                  <% ArrayList liste1        = (ArrayList)session.getValue("allbureaux") ;
                      for (int p=0; p<liste1.size(); p++)   {  
                      ItemAnnuaire item = (ItemAnnuaire) liste1.get(p);   
                      if ((userinfo.getProfil()>=3)||(userinfo.getProfil()==2&&userinfo.getDv_code()==item.getDv_code())||(userinfo.getProfil()==1&&userinfo.getSv_code()==item.getSv_code())) { %>
                  <option value=<%=item.getDv_code()%>;<%=item.getSv_code()%>;<%=item.getDv_nom()%>;<%=item.getSv_nom()%>;<%=item.getBu_nom()%>><%=item.getDv_nom()%>- <%=item.getSv_nom()%>- 
                  <% if (item.getBu_nom()!=null) %>
                  <%=item.getBu_nom()%></option>
                  <% } }  %>
                </select></td>
            </tr>
            <tr bgcolor=#CCCC66> 
              <td   ><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Ajout 
                usager </font></em></td>
              <td> <p>(lecture) <td colspan="2"> <select name="usager_code" size="1" onchange="Valider(this.form,'AjouterUsager')">
                  <option value=0;0>faire un choix</option>
                  <% ArrayList liste2        = (ArrayList)session.getValue("allagentdiv") ;
                      for (int p=0; p<liste2.size(); p++)   {   ItemAnnuaire item = (ItemAnnuaire) liste2.get(p);         %>
                  <option value=<%=item.getDv_code()%>;<%=item.getSv_code()%>;<%=item.getAg_code()%>;<%=item.getAg_ldap_uid()%>;<%=item.getAg_ldap_datenaiss()%>;<%=item.getAg_ldap_mail()%>;<%=item.getAg_ldap_nom()%>;<%=item.getAg_ldap_prenom()%>><%=item.getAg_ldap_nom()%> <%=item.getAg_ldap_prenom()%> - <%=item.getDv_nom()%></option>
                  <%  }  %>
                </select></tr>
            <% } %>
            <tr bgcolor=#effceb> 
              <td   >&nbsp; </td>
              <td colspan="3"   > </td>
            </tr>
          </form>
        </table>
     
      </div>
            <% } } } %>
          </td>
        </tr>
      </table> 
</BODY>
 
 