<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
 
<HTML>
<% 
String table="service";
ArrayList divtree           = (ArrayList)session.getValue("divtree");
ArrayList alltree           = (ArrayList)session.getValue("alltree");
ArrayList onepole           = (ArrayList)session.getValue("onepole");
ArrayList allpole           = (ArrayList)session.getValue("allpole");
ArrayList oneservice        = (ArrayList)session.getValue("oneservice");
ArrayList alldivision       = (ArrayList)session.getValue("alldivision");
ArrayList allservice        = (ArrayList)session.getValue("allservice");
ArrayList allbureau         = (ArrayList)session.getValue("allbureau");
ArrayList allagent          = (ArrayList)session.getValue("allagent");
//ArrayList allldap           = (ArrayList)session.getValue("allldap");
ServletContext sc = getServletContext();
UserInfo userinfo = (UserInfo)session.getValue("currentUser");
 
System.out.println("debut :" + table +".jsp" );
System.out.println("--------------------------------------" );
%>
<head><title><%=table%></title>
<link rel="shortcut icon" type="images/x-icon" href="favicon-annuaire.png" />
<link href="boiler.plate.css" rel="stylesheet" type="text/css">
<link href="organigramme.ac-reunion.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="menu.organigramme.css" type="text/css" media="screen" />   

<!-- if you want black backgound, remove this style block -->
</head>
<body> 
<%@  include file="include_menu-karine.jsp" %>
<SCRIPT language="javascript">
 function Valider(formulaire,type) {
 formulaire.bsubmit.value=type;
 formulaire.submit()
}
</SCRIPT>
 
<% 
if(oneservice!=null) { 
for(int i=0; i < oneservice.size(); i++) {  
ItemAnnuaire E  = (ItemAnnuaire) oneservice.get(i); 
         String tels=E.getSv_telephone().replaceAll(" ", "");
         if (tels.indexOf("026248")!=-1)        {        	tels=tels.substring(6);          }
         else                                   {        	tels="0"+tels;                   }
		 String tela=E.getAf_telephone().replaceAll(" ", "");
         if (tela.indexOf("026248")!=-1)        {        	tela=tela.substring(6);          }
         else                                   {        	tela="0"+tela;                   }
if (E.getSv_code()>0) { %>
<div id="content-row1" class="detail-agent">
 
<h3><a href="Dispatcher?operation=division&codedv=<%=E.getDv_code()%>" >  <%=E.getDv_nom()%></a> </h3>  
<H3><%=E.getSv_nom()%>&nbsp;<%=E.getSv_nomc()%></H3> 
<table width="100%" border="0">
  <tr style="border-top: 1px solid #3b8211;" >
  <td  style="padding-left: 1em; background-color:#effceb;" width="70%" rowspan="5" align="left" valign="top"> 
	<pre><em><%=E.getSv_mission()%> </em></pre>
	<pre><em><%=E.getSv_adresse()%> </em></pre>	</td>
    <td width="3%" valign="top"> <img src="images/mib.gif" width="24px" height="24px"></td>
 	<td width="30%" valign="top"><span class="lienoutils"><strong><%=E.getAf_libelle_type()%></strong></span><br>
	<a href="Dispatcher?operation=agent&table=agent&codepl=<%=E.getPl_code()%>&codedv=<%=E.getDv_code()%>&codesv=<%=E.getSv_code()%>&codebu=<%=E.getBu_code()%>&codeag=<%=E.getAg_code()%>"><strong><%=E.getAg_ldap_nom()%></strong></a>
	 &nbsp;<%=E.getAg_ldap_prenom()%></td>
  </tr>
 	<%
	     String teld=E.getSv_telephone().replaceAll(" ", "");
         if (teld.indexOf("026248")!=-1)        {        	teld=teld.substring(6);          }
         else                                   {        	teld="0"+teld;                   }
	%> 
  <tr>    <td width="3%" valign="top">&nbsp;</td>
          <td valign="top">  <span class="lienoutils"><strong>T&eacute;l.&nbsp;:</strong></span>&nbsp;<a href="callto://<%=teld%>"><%=E.getSv_telephone()%></a> </td>
  </tr>
 
  <tr>    <td width="3%" valign="top">&nbsp; </td>
          <td valign="top"><% if (E.getDv_fax().length() > 1) { %><span class="lienoutils"><strong>Fax.&nbsp;:</strong></span>&nbsp;<%=E.getSv_fax()%><% } %></td>
  </tr>
  
  <tr>    <td width="3%" height="8" valign="top">&nbsp;</td>
          <td valign="top"><span class="lienoutils"><strong>Mail.&nbsp;:</strong></span>&nbsp;<a href="mailto:<%=E.getDv_mail()%>"><%=E.getSv_mail()%></a></td>
  </tr>
</table>
<br>
   
   
 	  <%	   if(allbureau!=null) { 
for(int d=0; d < allbureau.size(); d++) {  
ItemAnnuaire D  = (ItemAnnuaire) allbureau.get(d); 
if (D.getBu_code() >0) { %>
      <p><span class="titre1"> 
	  <img src="images-foad/transp.gif" width="50" height="5">
	  <img src="4.GIF" width="10" height="5"> 
        <a href="Dispatcher?operation=bureau&codedv=<%=D.getDv_code()%>&codesv=<%=D.getSv_code()%>&codebu=<%=D.getBu_code()%>" class="lien_noir14_none"><%=D.getBu_nom()%></a></span><br>
        <img src="images-foad/transp.gif" width="50" height="5"><%=D.getBu_nomc()%><br>
        <% } } } %>   
      <%@ include file="include_liste_agent-karine.htm"%>
	  <% } %> 
      <%  if((userinfo.getMenu_admin()==1)&&(userinfo.getProfil()>=3)||(userinfo.getProfil()>=2&&E.getSv_code()<=0)||(userinfo.getProfil()==2&&E.getDv_code()==userinfo.getDv_code())||(userinfo.getProfil()==1&&E.getSv_code()==userinfo.getSv_code()))
      { %>	
 <br> 
 <div align="center">
 <table border="0" cellspacing="0" width=700  cellpadding="0" >
<tr style="border-top: 1px solid #3b8211;" bgcolor="#3b8211">  
 <td colspan=2> <div align="center"><i><font color="#FFFFFF" size="5"><%=table%></font></i></div></td>
 </tr>
              <form action="Dispatcher" method="post" name="form"    >
                <tr bgcolor=white> 
                  <td width="129"  ><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">nom 
                    court</font></em></td>
                  <td width="470">&nbsp; <input type="text"   name="nom" size="50" value="<%=E.getSv_nom()%>"> 
                    <input type="hidden" name="table" size="8" value=<%=table%>> 
                    <input type="hidden" name="codesv" size="8" value=<%=E.getSv_code()%>> 
                    <input type="hidden" name="operation" size="8" value=<%=table%>>
					<input type="hidden" name="bsubmit"    size="30"  >                  </td>
                </tr>
                <tr bgcolor=white> 
                  <td   ><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">nom 
                    long</font></em></td>
                  <td>&nbsp; <input type="text" name="nomc" size="60" value="<%=E.getSv_nomc()%>">                  </td>
                </tr>
				<tr bgcolor=white> 
                  <td   ><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">affichage</font></em></td>
                  <td>&nbsp; <input type="text" name="tri" size="60" value="<%=E.getSv_tri()%>">                  </td>
                </tr>
                <tr bgcolor=white> 
                  <td a ><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Mission</font></em></td>
                  <td> <div align="left">&nbsp; 
                      <textarea name="mission" cols="50" rows="4"><%=E.getSv_mission()%></textarea>
                    </div></td>
                </tr>
				 <tr bgcolor=white> 
                  <td align="left"><div align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Telephone</font></em></div></td>
                  <td> &nbsp;
                     <input type="text"   name="telephone" size="15" value="<%=E.getSv_telephone()%>"></td>
                </tr>
				<tr bgcolor=white> 
                  <td align="left"><div align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Fax</font></em></div></td>
                  <td> &nbsp;
                     <input type="text"   name="fax" size="15" value="<%=E.getSv_fax()%>"></td>
                </tr>
				<tr bgcolor=white> 
                  <td align="left"><div align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Mail</font></em></div></td>
                  <td> &nbsp;
				     <input type="text"   name="mail" size="15" value="<%=E.getSv_mail()%>"></td>
                </tr>
			 <tr bgcolor=white> 
                  <td align="right"><div align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Adresse</font></em></div></td>
                  <td> <div align="left">&nbsp; 
                      <textarea name="adresse" cols="50" rows="4"><%=E.getSv_adresse()%></textarea>
                    </div></td>
                </tr>
              <tr bgcolor=white> 
                  <td  > <em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Division</font></em> </td>
    			  <td> &nbsp;
				  <% if((userinfo.getProfil()>2)&&(E.getSv_integrite()==0)) { %>
                    <select name="pldv_code" size="1">
                      <option value=<%=E.getDv_code()%>><%=E.getDv_nom()%></option>
                      <% ArrayList liste        = (ArrayList)session.getValue("alldivision") ;
                   for (int p=0; p<liste.size(); p++)   {   ItemAnnuaire division = (ItemAnnuaire) liste.get(p); %>
                      <option value=<%=division.getDv_code()%>><%=division.getDv_nom()%></option>
                      <% } %>
                    </select>
					<% } else { 
					int dvcode=E.getDv_code();
					String dvnom=E.getDv_nom();
					if (userinfo.getProfil()==2) 
					{ 
					dvnom="la votre";
					dvcode=userinfo.getDv_code();
					}
					%> 
					<input type="hidden" name="pldv_code" size="8" value=<%=dvcode%>> 

					Division non modifiable: <%=dvnom%>
					<% } %></td>
                </tr>
                <tr bgcolor="#3b8211"   align="center"> 
                  <td colspan=2 > 
                          <%  if((userinfo.getProfil()>=1)||((E.getDv_code()==userinfo.getDv_code())&&(userinfo.getProfil()==2))||((E.getSv_code()==userinfo.getSv_code())&&(userinfo.getProfil()==4))) { %>
                        <input type="reset"  name="Submit"   value="R&eacute;tablir">
                        <input type="button" name="submit1" value="Ajouter"  onClick="Valider(this.form,'Ajouter')">
                        <% } %>
                        <% if(E.getSv_code()>0)     { %>
						<input type="button" name="submit2" value="Modifier"  onClick="Valider(this.form,'Modifier')">
                         
                        <% if(E.getSv_integrite()==0)     { %>
                        <input type="button" name="submit3" value="Supprimer"  onClick="Valider(this.form,'Supprimer')">
                        <% } } %>
                     </td>
                </tr>
              </form>
			   <tr style="border-top: 1px solid #3b8211;" > &nbsp;  <td colspan=2> </td>             </tr>
            </table>
			</div>
            <% } } } %> 
 </div>
 
</BODY>
 
 