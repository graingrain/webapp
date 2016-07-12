<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
<%@ page errorPage="error.jsp" %>
<% 
String table="agent";
ArrayList divtree           = (ArrayList)session.getValue("divtree");
ArrayList alltree           = (ArrayList)session.getValue("alltree");
ArrayList onepole           = (ArrayList)session.getValue("onepole");
ArrayList allpole           = (ArrayList)session.getValue("allpole");
ArrayList onedivision       = (ArrayList)session.getValue("onedivision");
ArrayList alldivision       = (ArrayList)session.getValue("alldivision");   
ArrayList allservice        = (ArrayList)session.getValue("allservice");
ArrayList allservices       = (ArrayList)session.getValue("allservices");
ArrayList oneservice        = (ArrayList)session.getValue("oneservice");
ArrayList allagent          = (ArrayList)session.getValue("allagent");
ArrayList oneagent          = (ArrayList)session.getValue("oneagent");
ArrayList allldap           = (ArrayList)session.getValue("allldap");
ArrayList liste_chef        = (ArrayList)session.getValue("allchef");
ServletContext sc = getServletContext();
UserInfo userinfo = (UserInfo)session.getValue("currentUser");
System.out.println("debut :" + table +".jsp --- user:" + userinfo.getUser() + " --- profil:  " + userinfo.getProfil());
System.out.println("--------------------------------------------------------------------------------------" );
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><head>
<title><%=table%></title>
<script type="text/javascript" src="include.control.js"></script>
<SCRIPT language="javascript">
   function Valider(formulaire,type) {
      formulaire.bsubmit.value=type;
      zz=0;
for (var i = 0; i < formulaire.pldv_code.options.length; i++) 
 {  if (formulaire.pldv_code.options[i].selected) 		{     zz=i   }       }
 
 
 
if (zz == 0 && ((formulaire.bsubmit.value.indexOf("Ajouter") != -1) || (formulaire.bsubmit.value.indexOf("Deplacer") != -1) ) ) 
   {
   alert("La structure est obligatoire \nCorrigez la ...");
   formulaire.pldv_code.focus();
   return false 
   }
    
          formulaire.submit()
   
}
</SCRIPT>
 
 
</head>
<link rel="shortcut icon" type="images/x-icon" href="favicon-annuaire.png" />
<link href="boiler.plate.css" rel="stylesheet" type="text/css">
<link href="organigramme.ac-reunion.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="menu.organigramme.css" type="text/css" media="screen" />   
<body>
<%@  include file="include_menu-karine.jsp" %>
<% if(!(oneagent!=null&&oneagent.size()>0))  { %>
<% } else { 
//for(int i=0; i < oneagent.size(); i++)  on ne prend en compte que la premiere entree , if faut afficher les liens vers les autres affectations
{  
ItemAnnuaire E  = (ItemAnnuaire) oneagent.get(0);
if (E.getAg_code()>0) {
%>
<div id="content-row1" class="detail-agent"> 
<h3><a href="Dispatcher?operation=division&amp;codedv=<%=E.getDv_code()%>"><%=E.getDv_nom()%></a>,&nbsp;<%=E.getDv_nomc()%></h3><br> 
<% if(E.getSv_code()>0) { %>
<strong><span class="titre1"><a href="Dispatcher?operation=service&amp;codedv=<%=E.getDv_code()%>&amp;codesv=<%=E.getSv_code()%>"><%=E.getSv_nom()%></a>,&nbsp;<%=E.getSv_nomc()%></span></strong> <br>
<% } %>
<br>
<table width="100%" border="0">
        <tr>
          <td width="469"><p> <img src="images-foad/transp.gif" width="10" height="5" alt=""> <img src="4.GIF" width="10" height="5" alt=""><strong>&nbsp;<%=E.getAg_ldap_nom()%></strong> <strong><%=E.getAg_ldap_prenom()%></strong>&nbsp; <strong> </strong>
            <blockquote>
            <p> <br>
            <table width="75%"  >
              <tr>
                <td width="26%"><img src="4.GIF" width="5" height="5" alt=""> Mail</td>
                <td width="74%"><font color="#0000FF"><a href="mailto:<%=E.getAg_ldap_mail()%>"><%=E.getAg_ldap_mail()%></a></font> </td>
              </tr>
			  	<%
	            String teld=E.getAf_telephone().replaceAll(" ", "");
                  if (teld.indexOf("026248")!=-1)        {        	teld=teld.substring(6);          }
                else                                   {        	teld="0"+teld;                   }
	            %> 
              <tr>
                <td width="26%"><img src="4.GIF" width="5" height="5" alt=""> Téléphone </td>
                <td width="74%" align="left" valign="middle" bordercolor="#FFFFFF" bgcolor="#FFFFFF"><font color="#0000FF"><a href="callto://<%=teld%>"><%=E.getAf_telephone()%></a></font>
				</td>
              </tr>
              <tr>
                <td width="26%"><img src="4.GIF" width="5" height="5" alt=""> Bureau</td>
                <td width="74%"><strong><%=E.getAf_bureau()%></strong></td>
              </tr>
              <tr>
                <td width="26%" valign="top"><img src="4.GIF" width="5" height="5" alt=""> Mission</td>
                <td width="74%"><pre><%=E.getAf_mission()%></pre></td>
              </tr>
              <%  if (userinfo.getProfil()>0) { %>
              <tr>
                <td width="26%" valign="top"><img src="4.GIF" width="5" height="5" alt=""> Grade</td>
                <td width="74%"><%=E.getAg_ldap_grade()%> &nbsp;
                  <%if(E.getGr_libc()!=null) {%>
                  <%=E.getGr_libl()%> &nbsp; cat:<%=E.getGr_cat()%>
                  <% } %></td>
              </tr>
              <% } %>
            </table></td>
          <td width="164" bgcolor="#FFFFFF"><%  if (userinfo.getProfil()>=0) { 
               System.out.println("xxxxxxxxxxxxxxxx test ecriture fichier , pour verifier repertoire de recherche xxxxxxxxxxxx");
             // PrintWriter sortie = new PrintWriter(new FileWriter("testfichierimage2010"));
           System.out.println("fin: ecriture ");
           //sortie.println("result ");
           //sortie.close();
			//String fimg = "C:\Program Files\NetBeans3.6\jakarta-tomcat-5.0.19\webapps\ddt\photo\"+vp2.substring(0,vp2.indexOf(" 			",0))+vp8.substring(0,vp8.indexOf(" ",0))+".jpg";
			//String wimg = "/ddt/photo/"+vp2.substring(0,vp2.indexOf(" ",0))+vp8.substring(0,vp8.indexOf(" ",0))+".jpg";
			String fimg = "/appli/annur/webapps/annur/photo/"+E.getAg_ldap_uid()+".jpg";
			//String fimg = "/jakarta-tomcat-4.0.4/webapps/ddt/photo/"+vp2.substring(0,vp2.indexOf(" ",0))+vp8.substring(0,vp8.indexOf(" ",0))+".jpg";
			String wimg = "photo/"+E.getAg_ldap_uid()+".jpg";
			File f = new File(fimg);
			 
			if (f.length() != 0) out.println("<img src=" + wimg +  " width=176 heigth=144 alt=''>");  
 }  %>
          </td>
          <% } %>
        </tr>
      </table>
      <%  if((userinfo.getMenu_admin()==1)&&((userinfo.getProfil()>=3)||(userinfo.getProfil()>=1&&E.getAg_code()<=0)||(userinfo.getProfil()==2&&E.getDv_code()==userinfo.getDv_code())||(userinfo.getProfil()==1&&E.getSv_code()==userinfo.getSv_code()))) { %>
      
<div align="center">
  <% if(userinfo.getError_message().length() > 1) {%>
	   Erreur: <font color="#FF0000"> <%=userinfo.getError_message()%>  </font> 
	  <% } %>
	
	 <table border="0" cellspacing="0" width=700  cellpadding="0" >
     <tr style="border-top: 1px solid #3b8211;" bgcolor="#3b8211">  
     <td colspan=2> <div align="center"><i><font color="#FFFFFF" size="5"><%=table%></font></i></div></td>
              </tr>
              <form action="Dispatcher" method="post" name="form"   onsubmit='return control()'>
                <tr bgcolor=white>
                  <td width="119" align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Nom</font></em></td>
                  <td width="581"><input type="hidden" name="table" size="8" value=<%=table%>>
                    <input type="hidden" name="code" size="8" value=<%=E.getAg_code()%>>
                    <input type="hidden" name="operation"  size="8" value=<%=table%>>
                    <input type="hidden" name="af_bu_code" size="8" value=<%=E.getAf_bu_code()%>>
                    <input type="hidden" name="af_sv_code" size="8" value=<%=E.getAf_sv_code()%>>
                    <input type="hidden" name="af_dv_code" size="8" value=<%=E.getAf_dv_code()%>>
                    <input type="hidden" name="af_pl_code" size="8" value=<%=E.getAf_pl_code()%>>
                    <input type="hidden" name="codebu" size="8" value=<%=E.getAf_bu_code()%>>
                    <input type="hidden" name="codesv" size="8" value=<%=E.getAf_sv_code()%>>
                    <input type="hidden" name="codedv" size="8" value=<%=E.getAf_dv_code()%>>
                    <input type="hidden" name="codepl" size="8" value=<%=E.getAf_pl_code()%>>
                    <input type="hidden" name="af_code"    size="8" value=<%=E.getAf_code()%>>
                    <input type="hidden" name="ag_ldap_dn"    size="8" value='<%=E.getAg_ldap_dn()%>'>
                    <input type="hidden" name="ag_ldap_uid"    size="8" value='<%=E.getAg_ldap_uid()%>'>
                    <input type="text"   name="nom" size="60" value="<%=E.getAg_ldap_nom()%>">
                  </td>
                </tr>
                <tr bgcolor=white>
                  <td height="26" align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Prénom</font></em></td>
                  <td><input type="text" name="prenom" size="60" value="<%=E.getAg_ldap_prenom()%>">
                  </td>
                </tr>
                <tr bgcolor=white>
                  <td align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Mission</font></em></td>
                  <td><textarea name="mission" cols="40"><%=E.getAf_mission()%></textarea>
                  </td>
                </tr>
                <tr bgcolor=white>
                  <td align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Gsm</font></em></td>
                  <td><input type="text" name="gsm" size="60" value="<%=E.getAg_gsm()%>"></td>
                </tr>
                <tr bgcolor=white>
                  <td align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Téléphone</font></em></td>
                  <td><input type="text" name="telephone" size="60" value="<%=E.getAf_telephone()%>">
                  </td>
                </tr>
                <tr bgcolor=white>
                  <td align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Téléphone direct</font></em></td>
                  <td><input type="text" name="telephone_dir" size="60" value="<%=E.getAf_telephone_dir()%>"></td>
                </tr>
                <tr bgcolor=white>
                  <td align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Bureau</font></em></td>
                  <td><input type="text" name="bureau" size="60" value="<%=E.getAf_bureau()%>">
                  </td>
                </tr>
                <% if (E.getAg_code()>0) { %>
                <% if (userinfo.getProfil()>=5) { %>
                <tr bgcolor=white>
                  <td align="left" bordercolor="#FF0000" class="Style4"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Chorus num&eacute;ro cle: </font></td>
                  <td bordercolor="#FF0000">&nbsp;<%=E.getAg_gemalto_num()%> </td>
                </tr>
				<tr bgcolor=white>
                  <td align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Uid (ldap) </font></em></td>
                  <td><%=E.getAg_ldap_uid()%> </td>
                </tr> 
                <tr bgcolor=white>
                  <td align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Numen (ldap) </font></em></td>
                  <td> <%=E.getAg_ldap_numen()%> </td>
                </tr>
                <tr bgcolor=white>
                  <td align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Passwd (ldap ) </font></em></td>
                  <td><input type="text" name="passwd" size="2"  >
                  </td>
                </tr>
                <% if((request.getAttribute("ag_ldap_dn")!=null)) { %>
                <tr bgcolor=white>
                  <td align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">dn</font></em></td>
                  <td><%=request.getAttribute("ag_ldap_dn")%> </td>
                </tr>
                <% } %>
                <% if((E.getAg_ldap_dn()!=null)) { %>
                <tr bgcolor=white>
                  <td align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">dn</font></em></td>
                  <td><%=E.getAg_ldap_dn()%> </td>
                </tr>
                <tr bgcolor=white>
                  <td align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Ldap 
                    profil ddt </font></em></td>
                  <td><input type="text" name="ldap_profil_ddt" size="2" value="<%=E.getAg_ldap_profil_ddt()%>">
                    &nbsp;<em>(0=rien, 1=lecteur, 2=demandeur, 3=exploitation, 
                    4=admin)</em></td>
                <tr bgcolor=white>
                  <td align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Ldap 
                    profil annu </font></em></td>
                  <td><input type="text" name="ldap_profil_annu" size="2" value="<%=E.getAg_ldap_profil_annu()%>">
                    &nbsp;<em>(0=rien, 1=service, 2=division, 3=g&eacute;n&eacute;ral, 
                    4=appels,5=otp,6=admin) </em> </td>
                </tr>
                <% } %>
                <% } %>
                <% } %>
                <tr bgcolor=white>
                  <td align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Mel</font></em></td>
                  <td><input type="text" name="mail" size="60" value="<%=E.getAg_ldap_mail()%>">
                  </td>
                </tr>
                <% if (E.getAg_code()>0) { %>
                <tr bgcolor=white>
                  <td align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Grade 
                    code </font></em></td>
                  <td><input type="text" name="grade" size="6" value="<%=E.getAg_ldap_grade()%>">
                    &nbsp;<%=E.getGr_libc()%> &nbsp; cat:<%=E.getGr_cat()%> </td>
                </tr>
                <tr bgcolor=white>
                  <td align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Grade 
                    lib long</font></em></td>
                  <td><%=E.getGr_libl()%> </td>
                </tr>
                <tr bgcolor=white>
                  <td align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Ldap 
                    synchro </font></em></td>
                  <td><%=E.getAg_ldap_synchro()%>&nbsp;<em>(1=oui, 2=non) </em></td>
                </tr>
                <% } %>
                <tr bgcolor=white>
                  <td align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Division</font></em></td>
                  <td><select name="pldv_code" size="1">
                      <option value=<%=E.getPl_code()%>;<%=E.getPl_code()%>;<%=E.getDv_code()%>;<%=E.getSv_code()%>;<%=E.getBu_code()%>></option>
                      <%=E.getDv_nom()%>
                      </option>
                      <% ArrayList liste        = (ArrayList)session.getValue("allbureaux") ;
                      for (int p=0; p<liste.size(); p++)   {  
                      ItemAnnuaire item = (ItemAnnuaire) liste.get(p);   
                      if ((userinfo.getProfil()>=3)||(userinfo.getProfil()==2&&userinfo.getDv_code()==item.getDv_code())||(userinfo.getProfil()==1&&userinfo.getSv_code()==item.getSv_code())) { %>
                      <option value=<%=item.getPl_code()%>;<%=item.getDv_code()%>;<%=item.getSv_code()%>;<%=item.getBu_code()%>><%=item.getDv_nom()%>- <%=item.getSv_nom()%>-
                      <% if (item.getBu_nom()!=null) %>
                      <%=item.getBu_nom()%></option>
                      <% } }  %>
                    </select>
                    Libelle Chef:
                    <select name="libchef" size="1">
                      <option value='<%=E.getAf_type()%>;<%=E.getAf_libelle_type()%>' ><%=E.getAf_libelle_type()%> </option>
                      <option value='0; ' >rien</option>
                      <% for (int i=0; i<liste_chef.size(); i++)
                      {  ItemChef item = (ItemChef) liste_chef.get(i); %>
                      <option value='<%=item.getCh_action()%>;<%=item.getCh_libelle()%>' > <%=item.getCh_libelle()%></option>
                      <% } %>
                    </select>
                    Type Chef:
                    <select name="typechef" size="1">
                      <option value='<%=E.getAf_type()%>' ><%=E.getAf_type()%> </option>
                      <option value='0' >0 rien</option>
                      <option value='1' >1 division</option>
                      <option value='2' >2 service</option>
                      <option value='3' >3 autre</option>
                    </select>
                  </td>
                </tr>
                 <tr bgcolor="#3b8211"   align="center"> 
                  <td colspan=2 > 
                       
                        <input type="hidden" name="codeaf" size="8" value=<%=E.getAf_code()%>>
                        <input type="hidden" name="codeag" size="8" value=<%=E.getAf_ag_code()%>>
                        <input type="hidden" name="codedv" size="8" value=<%=E.getAf_dv_code()%>>
                        <input type="hidden" name="codesv" size="8" value=<%=E.getAf_sv_code()%>>
                        <input type="hidden" name="codebu" size="8" value=<%=E.getAf_bu_code()%>>
						<input type="hidden" name="nomdiv" size="8" value=<%=E.getDv_nom()%>>
						<input type="hidden" name="nomser" size="8" value=<%=E.getSv_nom()%>>
                        <input type="hidden" name="bsubmit"    size="30"  >
                        <input type="button" name="submit1"  value="Ajouter"              onClick="Valider(this.form,'Ajouter')">
                        <% if(E.getAg_code()!=0)     { %>
                        <input type="button" name="submit2" value="Ajouter Affectation"   onClick="Valider(this.form,'Ajouter Affectation')">
                        <input type="button" name="submit3" value="Modifier"              onClick="Valider(this.form,'Modifier')">
                        <input type="button" name="submit4" value="Deplacer"              onClick="Valider(this.form,'Deplacer')">
                        <br>
                        <input type="button" name="submit5" value="Supprimer"             onClick="Valider(this.form,'Supprimer')">
                        <input type="button" name="submit6" value="Supprimer affectation" onClick="Valider(this.form,'Supprimer affectation')">
                        <br>
                        <% if (userinfo.getProfil()>3)  { %>
                        <input type="submit"  value="Recherche_Ldap" width="72"  name="recldap">
                        <input type="submit"  value="Modif_Ldap"     width="72"  name="modldap">
                        <% } %>
						 <% if ((userinfo.getProfil()>4) &&  E.getAg_gemalto_num()==0  ) { %>
                        <input type="submit"  value="Creation cle chorus" width="72"  name="crechorus">
                        
                        <% } %>
                        <% } %>
                       
                    </td>
                </tr>
                <% if ((E.getAg_code()>0) & (userinfo.getProfil()>3)) { %>
                <% if (E.getAg_ldap_synchro()>=0) { %>
                <tr bgcolor=white>
                  <td colspan=2 ><%System.out.println("nb ldap:"+allldap.size());%>
                    <% if(allldap.size()>=0) { %>
                    <strong>Liste ldap: </strong> <br>
                    <table width=700  border="1"  >
                      <tr bgcolor="#FFCC99">
                        <td><div align="center"><strong>nom</strong></div></td>
                        <td><div align="center"><strong>prenom</strong></div></td>
						<td><div align="center"><strong>uid</strong></div></td>
                        <td><div align="center"><strong>mail</strong></div></td>
                        <td><div align="center"><strong>numen</strong></div></td>
                        <td><div align="center"><strong>tel</strong></div></td>
                        <td><div align="center"><strong>fax</strong></div></td>
                      </tr>
                      <tr>
                        <% for(int a=0; a < allldap.size(); a++) { ItemAnnuaire agent  = (ItemAnnuaire) allldap.get(a);  %>
                      <tr>
                        <td><a class=narub href="Dispatcher?ldapnumber=<%=a%>&table=agent&operation=synchroldap&code=<%=E.getAg_code()%>"> <%=agent.getAg_ldap_nom()%> </a></td>
                        <td><%=agent.getAg_ldap_prenom()%></td>
						<td><%=agent.getAg_ldap_uid()%>&nbsp;</td>
                        <td><%=agent.getAg_ldap_mail()%>&nbsp;</td>
                        <td><%=agent.getAg_ldap_numen()%>&nbsp;</td>
                        <td><%=agent.getAf_telephone()%>&nbsp;</td>
                        <td><%=agent.getAf_mission()%>&nbsp;</td>
                      </tr>
                      <% } } %>
                    </table>
                    <% } %>
                    <% } %>
                  </td>
                </tr>
              </form>
            </table>
            <% } } } %>
          </div>
    
</BODY>
