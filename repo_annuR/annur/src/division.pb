<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
<%@ page errorPage="PbGeneral.jsp" %> 
 
<HTML>
<% 
String table="division";
ArrayList divtree           = (ArrayList)session.getValue("divtree");
ArrayList alltree           = (ArrayList)session.getValue("alltree");
ArrayList onepole           = (ArrayList)session.getValue("onepole");
ArrayList allpole           = (ArrayList)session.getValue("allpole");
ArrayList onedivision       = (ArrayList)session.getValue("onedivision");
ArrayList alldivision       = (ArrayList)session.getValue("alldivision");
ArrayList allservice        = (ArrayList)session.getValue("allservice");
ArrayList allpartage        = (ArrayList)session.getValue("allpartage");
ArrayList allagent          = (ArrayList)session.getValue("allagent");
//ArrayList allldap           = (ArrayList)session.getValue("allldap");
ServletContext sc = getServletContext();
UserInfo userinfo = (UserInfo)session.getValue("currentUser");
System.out.println("-------------------------------------------------------" );
System.out.println("debut :" + table +".jsp"+" login:"+userinfo.getAg_code() );
System.out.println("-------------------------------------------------------" );
System.out.println("--userinfo profil:" + userinfo.getProfil() );

%>
<head><title><%=table%></title> 
    <script language="JavaScript1.2" src="include.control.js"></script> 
    <link rel="stylesheet" href="stylefoad.css" type="text/css" media="screen" />
<!-- if you want black backgound, remove this style block -->
 
</head>
<body> 
<%@  include file="include_menu.jsp" %>
<table width=800 border="0">
  <tr align="left" valign="top"> 
<% if(onedivision!=null) { 
//for(int i=0; i < onedivision.size(); i++) 
if (onedivision.size()==1)
{  
ItemAnnuaire E  = (ItemAnnuaire) onedivision.get(0); 
if (E.getDv_code()>0) { 
System.out.println("-- Dv_code:" +  (E.getDv_code() );
%>
    <td width="87%">
	 <span class="titre1"><%=E.getDv_nom()%> </span>&nbsp;<%=E.getDv_nomc()%><br>
        <img src="images-foad/orange2.gif" width="800" height="2"> <br>
        
	  <table width="100%" border="0">
  <tr>
    <td width="7%" rowspan="5" align="left" valign="top">       </td>
    <td width="35%" rowspan="5" align="left" valign="top" <% if(E.getDv_mission().length()>0) {%> class=fond <% } %>> 
	<pre class="titre1"><em><%=E.getDv_mission()%></em></pre>
	<pre class="menua"><em><%=E.getDv_adresse()%></em></pre>	</td>
    <td width="3%" valign="top"> <img src="images/mib.gif" width="24" height="24"></td>
    
	<td width="55%" valign="top">
	 
	 
	<span class="lienoutils"><strong><%=E.getAf_libelle_type()%>  </strong></span><br>
	<a href="Dispatcher?operation=agent&table=agent&codepl=<%=E.getPl_code()%>&codedv=<%=E.getDv_code()%>&codesv=<%=E.getSv_code()%>&codebu=<%=E.getBu_code()%>&codeag=<%=E.getAg_code()%>"><strong><%=E.getAg_ldap_nom()%></strong></a>
	 &nbsp;<%=E.getAg_ldap_prenom()%></td>
  </tr>
  	<%
	     String teld=E.getDv_telephone().replaceAll(" ", "");
         if (teld.indexOf("026248")!=-1)        {        	teld=teld.substring(6);          }
         else                                   {        	teld="0"+teld;                   }
	%> 
	 
  <tr>
    <td width="3%" valign="top">&nbsp;</td>
          <td valign="top">
		  <span class="lienoutils"><strong>Tél.&nbsp;&nbsp;&nbsp;&nbsp;</strong></span> <a href="callto://<%=teld%>"><%=E.getDv_telephone()%></a> </td>
  </tr>
  <tr>
    <td width="3%" valign="top">&nbsp; </td>
    <td valign="top"><% if (E.getDv_fax().length() > 1) { %>
            <span class="lienoutils"><strong>Fax.&nbsp;</strong></span>&nbsp;<%=E.getDv_fax()%><% } %></td>
  </tr>
  <tr>
    <td width="3%" height="8" valign="top">&nbsp;</td>
    <td valign="top"><span class="lienoutils"><strong>Mél.&nbsp;</strong></span>&nbsp;<a href="mailto:<%=E.getDv_mail()%>"><%=E.getDv_mail()%></a></td>
  </tr>
 
</table>
 <br>
<%	   if(allservice!=null) 
{ %>
		<table width="100%" border="0">
<% for(int d=0; d < allservice.size(); d++) 
{	  
ItemAnnuaire D  = (ItemAnnuaire) allservice.get(d);
         String tels=D.getAf_telephone().replaceAll(" ", "");
         if (tels.indexOf("026248")!=-1)        {        	tels=tels.substring(6);          }
         else                                   {        	tels="0"+tels;                   }
	 
 %>
        <tr> 
          <td width="7%">&nbsp;</td>
          <td width="231" nowrap  > <p><span class="titre1"> <a href="Dispatcher?operation=service&codedv=<%=D.getDv_code()%>&codesv=<%=D.getSv_code()%>" class="lien_noir14_none" title="<%=D.getSv_mission()%>" ><%=D.getSv_nom()%></a></span> <br>
              <img src="4.GIF">&nbsp;<%=D.getSv_nomc()%> </td>
          <td width="167"     valign="bottom" nowrap> <span class="lienoutils"><strong><%=D.getAf_libelle_type()%><br>
            </strong></span> 
			<a href="Dispatcher?operation=agent&table=agent&codepl=<%=D.getPl_code()%>&codedv=<%=D.getDv_code()%>&codesv=<%=D.getSv_code()%>&codebu=<%=D.getBu_code()%>&codeag=<%=D.getAg_code()%>"><strong><%=D.getAg_ldap_nom()%></strong></a>
			 &nbsp;<%=D.getAg_ldap_prenom()%> </td>
          <td width="373" valign="bottom" nowrap class="lienoutils"><em> <a href="callto://<%=tels%>"><%=D.getAf_telephone()%></a></em></td>
        </tr>
<% if((userinfo.getVoir_mission()==1)) 
{ %>    
		<tr> 
          <td width="11"     >&nbsp;</td>
          <td colspan="3" nowrap  > 
            <pre><%=D.getSv_mission()%></pre>  </td>
        </tr>
<% } } %>
      </table>
<% 
} %>
<br>
<%	     if((userinfo.getProfil()>=3)||(userinfo.getProfil()==2&&E.getDv_code()==userinfo.getDv_code()))         { %>
<table width="100%" border="0" bgcolor="#FFFFFF">
		<tr  class="titre1"> 
    <td height="1" colspan=13 bgcolor="#FFFFFF">Liste des partages Scribe</td>
  </tr>   
  <tr bgcolor="#FFCC99"> 
    <td height="1" colspan=13><img src="images-foad/orange2.gif" width="1" height="1"></td>
  </tr> 
<% for(int d=0; d < allpartage.size(); d++) 
{  
ItemAnnuaire D  = (ItemAnnuaire) allpartage.get(d); %> 
        <tr> 
          <td width="7%" height="28">&nbsp;</td>
          <td width="231" nowrap  ><img src="4.GIF" width="5" height="5">&nbsp; <span class="titre1"> <a href="Dispatcher?operation=partage&codepa=<%=D.getPa_code()%>&codedv=<%=D.getPa_dv_code()%>&table=partage" class="lien_noir14_none" title="<%=D.getPa_nomc()%>" ><%=D.getPa_nom()%></a></span>
              <span class="Style1">(<%=D.getPa_integrite()%>)	</span>		   
               &nbsp;&nbsp;<%=D.getPa_nomc()%> </td>
          <td width="167"     valign="top" nowrap> <span class="lienoutils"> 
		 </td>
          <td width="373" valign="top" nowrap class="lienoutils"><em> </em></td>
        </tr>
  
<%  } %>
      </table>
<% } %>
		
<br>
      <%@ include file="include_liste_agent.htm"%>
      <% } %>
 

 <%  if((userinfo.getMenu_admin()==1)&&(userinfo.getProfil()>=3)||(userinfo.getProfil()==2&&E.getDv_code()==userinfo.getDv_code()))         { %>	

<table width="90%"  align=left>
        <tr> 
          <td> <table border="0" cellspacing="0" width=700  cellpadding="0" align="left">
              <tr   id=titre_tr> 
                <td colspan=2> <div align="center"><i><font color="#FFFFFF" size="5"><%=table%></font></i></div></td>
              </tr>
              <form action="Dispatcher" method="post" name="form"   onsubmit='return control()'>
                <tr bgcolor=white> 
                  <td width="196" align="right"><div align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">nom 
                    court</font></em></div></td>
                  <td width="761">&nbsp; <input type="text"   name="nom" size="60" value="<%=E.getDv_nom()%>"> 
                    <input type="hidden" name="table" size="8" value=<%=table%>> 
                    <input type="hidden" name="codedv" size="8" value=<%=E.getDv_code()%>> 
                    <input type="hidden" name="codepl" size="8" value=<%=E.getPl_code()%>> 
                    <input type="hidden" name="operation" size="8" value=<%=table%>>   
					<input type="hidden" name="bsubmit"    size="30"  >                       </td>
                </tr>
                <tr bgcolor=white> 
                  <td align="right"><div align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">nom 
                    long</font></em></div></td>
                  <td>&nbsp; <input type="text" name="nomc" size="60" value="<%=E.getDv_nomc()%>">                  </td>
                </tr>
                <tr bgcolor=white> 
                  <td align="right"><div align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Mission</font></em></div></td>
                  <td> <div align="left">&nbsp; 
                      <textarea name="mission" cols="50" rows="4"><%=E.getDv_mission()%></textarea>
                  </div></td>
                </tr>
                <tr bgcolor=white> 
                  <td align="left"><div align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Telephone</font></em></div></td>
                  <td> &nbsp;
                     <input type="text"   name="telephone" size="15" value="<%=E.getDv_telephone()%>"></td>
                </tr>
				<tr bgcolor=white> 
                  <td align="left"><div align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Fax</font></em></div></td>
                  <td> &nbsp;
                     <input type="text"   name="fax" size="15" value="<%=E.getDv_fax()%>"></td>
                </tr>
				<tr bgcolor=white> 
                  <td align="left"><div align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Mail</font></em></div></td>
                  <td> &nbsp;
				     <input type="text"   name="mail" size="15" value="<%=E.getDv_mail()%>"></td>
                </tr>
				
				
				 <tr bgcolor=white> 
                  <td align="right"><div align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Adresse</font></em></div></td>
                  <td> <div align="left">&nbsp; 
                      <textarea name="adresse" cols="50" rows="4"><%=E.getDv_adresse()%></textarea>
                    </div></td>
                </tr>
				
				
				
				<tr bgcolor=white> 
                  <td align="left"><div align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Niveau hierarchique </font></em></div></td>
                 <td>&nbsp;
                  <input type="text" name="tri" size="3" value="<%=E.getDv_tri()%>"></td>
                </tr>
				<tr bgcolor=white> 
                  <td align="left"><div align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Pole</font></em></div></td> 
                  <td> &nbsp;
                    <select name="pldv_code" size="1">
                      <option value=<%=E.getPl_code()%>><%=E.getPl_nom()%></option>
                      <% ArrayList liste_pole       = (ArrayList)session.getValue("allpole") ;
                for (int p=0; p<liste_pole.size(); p++)   {  
                ItemAnnuaire pole = (ItemAnnuaire) liste_pole.get(p); %>
                      <option value=<%=pole.getPl_code()%>><%=pole.getPl_nom()%></option>
                      <% } %>
                    </select> </td>
                </tr>
                <tr bgcolor=white> 
                  <td colspan=2 > <div align="center"> 
                      <p> 
                        <input type="reset"  name="Submit"   value="R&eacute;tablir">
                         <%  if((userinfo.getProfil()>=3)) { %>
						<input type="button" value="Ajouter"  onClick="control('Ajouter')"> 
						 
                        <% }  if(E.getDv_code()>0)     { %>
                        <input type="button" value="Modifier"  onClick="control('Modifier')"> 
						 <% if(E.getDv_integrite()==0)     { %>
                        <input type="button" value="Supprimer"  onClick="control('Supprimer')"> 
						 	 <% } } %>
                      </p>
                    </div></td>
                </tr>
              </form>
            </table>
            <div align="left">
              <% } } } %> 
            </div></td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td>  </td>
  </tr>
</table>
</BODY>


  

 