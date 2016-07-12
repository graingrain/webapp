<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
<!doctype html>
<head>
<title>division</title>
<link rel="shortcut icon" type="images/x-icon" href="favicon-annuaire.png" />
<link href="boiler.plate.css" rel="stylesheet" type="text/css">
<link href="organigramme.ac-reunion.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="menu.organigramme.css" type="text/css" media="screen" />
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
System.out.println("-----------------------2015--------------------------------" );
System.out.println("-------------------------------------------------------" );
System.out.println("debut :" + table +".jsp"+" login:"+userinfo.getAg_code() );
System.out.println("-------------------------------------------------------" );
System.out.println("------------- profil: " + userinfo.getProfil() );
%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>


 
$(document).ready(function(){


$(".parents-toggle-partage > div").click(function () {
    $(".parents-toggle-partage > div.iteminfo-toggle-partage").not($(this).siblings()).slideUp();
    $(this).siblings(".iteminfo-toggle-partage").slideToggle();
    $(".parents-toggle-personnels > div.iteminfo-toggle-personnels").not($(this).siblings()).slideUp();
    $(this).siblings(".iteminfo-toggle-personnels").slideToggle();
    // toggle open/close symbol
    if($('.itemheading2_toggle span').text() == '-'){
        $('.itemheading2_toggle span').text('+');   
    } else {
        $('.itemheading2_toggle span').text('-');
		}
	 if($('.itemheading3_toggle span').text() == '-'){
        $('.itemheading3_toggle span').text('+');   
    } else {
        $('.itemheading3_toggle span').text('-');
		}	
    });
});
</script>
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
<body>
<%@ include file="include_menu-karine.jsp" %>
<% if(onedivision!=null) { 
//for(int i=0; i < onedivision.size(); i++) 
if (onedivision.size()==1)
{  
ItemAnnuaire E  = (ItemAnnuaire) onedivision.get(0); 
if (E.getDv_code()>0) { %>
<div id="content-row1" class="detail-agent">
<h3><span><%=E.getDv_nom()%> </span>&nbsp;<%=E.getDv_nomc()%> <br></h3> <br>
 <!--mission de la division--->       
<table width="100%" border="0">
  <tr style="border-top: 1px solid #3b8211;" >
  <td  style="padding-left: 1em; background-color:#effceb;" width="70%" rowspan="5" align="left" valign="top"> 
	<pre><em><%=E.getDv_mission()%> </em></pre>
	<pre><em><%=E.getDv_adresse()%> </em></pre>	</td>
    <td width="3%" valign="top"> <img src="images/mib.gif" width="24px" height="24px"></td>
 	<td width="30%" valign="top"><span class="lienoutils"><strong><%=E.getAf_libelle_type()%></strong></span><br>
	<a href="Dispatcher?operation=agent&table=agent&codepl=<%=E.getPl_code()%>&codedv=<%=E.getDv_code()%>&codesv=<%=E.getSv_code()%>&codebu=<%=E.getBu_code()%>&codeag=<%=E.getAg_code()%>"><strong><%=E.getAg_ldap_nom()%></strong></a>
	 &nbsp;<%=E.getAg_ldap_prenom()%></td>
  </tr>
 	<%
	     String teld=E.getDv_telephone().replaceAll(" ", "");
         if (teld.indexOf("026248")!=-1)        {        	teld=teld.substring(6);          }
         else                                   {        	teld="0"+teld;                   }
	%> 
  <tr>    <td width="3%" valign="top">&nbsp;</td>
          <td valign="top">  <span class="lienoutils"><strong>T&eacute;l.&nbsp;:</strong></span>&nbsp;<a href="callto://<%=teld%>"><%=E.getDv_telephone()%></a> </td>
  </tr>
 
  <tr>    <td width="3%" valign="top">&nbsp; </td>
          <td valign="top"><% if (E.getDv_fax().length() > 1) { %><span class="lienoutils"><strong>Fax.&nbsp;:</strong></span>&nbsp;<%=E.getDv_fax()%><% } %></td>
  </tr>
  
  <tr>    <td width="3%" height="8" valign="top">&nbsp;</td>
          <td valign="top"><span class="lienoutils"><strong>Mail.&nbsp;:</strong></span>&nbsp;<a href="mailto:<%=E.getDv_mail()%>"><%=E.getDv_mail()%></a></td>
  </tr>
</table>
<br>

<!--liste des service-----> 
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
<tr>      <td width="231"   > <p><span class="titre1"> <a href="Dispatcher?operation=service&codedv=<%=D.getDv_code()%>&codesv=<%=D.getSv_code()%>" class="lien_noir14_none" title="<%=D.getSv_mission()%>" ><%=D.getSv_nom()%></a></span> <br>
              <img src="4.GIF">&nbsp;<%=D.getSv_nomc()%> </td>
          <td width="167"     valign="bottom" > <span class="lienoutils"><strong><%=D.getAf_libelle_type()%><br>
            </strong></span> 
			<a href="Dispatcher?operation=agent&table=agent&codepl=<%=D.getPl_code()%>&codedv=<%=D.getDv_code()%>&codesv=<%=D.getSv_code()%>&codebu=<%=D.getBu_code()%>&codeag=<%=D.getAg_code()%>"><strong><%=D.getAg_ldap_nom()%></strong></a>
			 &nbsp;<%=D.getAg_ldap_prenom()%> </td>
          <td width="373" valign="bottom"  class="lienoutils"><em> <a href="callto://<%=tels%>"><%=D.getAf_telephone()%></a></em></td>
</tr>
<% if((userinfo.getVoir_mission()==1)) 
{ %>    
<tr>      
      <td width="11"     >&nbsp; </td>
      <td colspan="3"   >             <pre><%=D.getSv_mission()%></pre>  </td>
</tr>
<% } } %> 
</table>
<% } %>  
<!--fin de liste des services-----> 
<!-- liste des partages -----> 
<%	     if((userinfo.getProfil()>=3)||(userinfo.getProfil()==2&&E.getDv_code()==userinfo.getDv_code()))         { %>
<div class="parents-toggle-partage">
<table style="width:100%; margin-top: 1em;" border="0" cellspacing="0">
<tr  class="titre1">    
<td style="border-top: 1px solid #3b8211;" height="1" colspan=13> </td> 
</tr></table>
<div class="itemheading2_toggle"><p class="nom"><span class="symbol">-</span>Liste des partages Scribe</p></div>
<div  class="iteminfo-toggle-partage">
<table style="width:100%; margin-top: 1em;" border="0" cellspacing="0">
<% for(int d=0; d < allpartage.size(); d++) 
{  
ItemAnnuaire D  = (ItemAnnuaire) allpartage.get(d); %> 
  <tr> 
     <td width="97" height="28">&nbsp;</td>
     <td width="420"   ><img src="4.GIF" width="5" height="5">&nbsp; <span class="titre1"> <a href="Dispatcher?operation=partage&codepa=<%=D.getPa_code()%>&codedv=<%=D.getPa_dv_code()%>&table=partage" class="lien_noir14_none" title="<%=D.getPa_nomc()%>" ><%=D.getPa_nom()%></a></span>
              <span class="Style1">(<%=D.getPa_integrite()%>)</span> </td>
     <td width="922"     valign="top" >   <%=D.getPa_nomc()%>		 </td>
  </tr>
<% } %>
  </table>
</div>
	 
<% } %>   
<% } %>    
<br> 
<!--liste des personnels----->  <%@ include file="include_liste_agent-2015.htm"%>

<% 
System.out.println("-------------      Dv_code: " + E.getDv_code() );  
System.out.println("------------- user Dv_code: " + userinfo.getDv_code() ); 
if((userinfo.getMenu_admin()==1)&(userinfo.getProfil()>=3)|| (userinfo.getProfil()==2&&E.getDv_code()==userinfo.getDv_code())  	)       
{ 
  

System.out.println("------------- admin ok"); 
 %>	
<!-- admin division --------->
<div id="fade">fade  division</div>
<div id="flip">modif division</div>
<div align=center id=division >
<br>
<table border="0" cellspacing="0" width=700  cellpadding="0" >
<tr style="border-top: 1px solid #3b8211;" bgcolor="#3b8211">  
                <td colspan=2> <div align="center"><i><font color="#FFFFFF" size="5"><%=table%></font></i></div></td>
              </tr>
              <form action="Dispatcher" method="post" name="form"   onsubmit='return control()'>
                <tr bgcolor=white> 
                  <td width="196" align="right"><div align="left"><em><font size="-1" face="PrimaSans BT,Verdana,sans-serif">nom 
                    court</font></em></div></td>
                  <td width="761">&nbsp;
				   <%  if ((E.getDv_code()<1) && (userinfo.getProfil()== 6) || (userinfo.getProfil()== 7) )    { %> 				  
				   <input type="text"   name="nom" size="60" value="<%=E.getDv_nom()%>"> 
				   <% } else { %>
				   <input type="hidden" name="nom" size="60" value="<%=E.getDv_nom()%>"> 
				   <%=E.getDv_nom()%>
				   <% } %>
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
                <tr bgcolor="#3b8211"> 
                  <td colspan=2 > <div align="center"> 
                      
                        <input type="reset"  name="Submit"   value="R&eacute;tablir">
                         <%  if((userinfo.getProfil()>=3)) { %>
						<input type="button" value="Ajouter"  onClick="control('Ajouter')"> 
						 
                        <% }  if(E.getDv_code()>0)     { %>
                        <input type="button" value="Modifier"  onClick="control('Modifier')"> 
						 <% if(E.getDv_integrite()==0)     { %>
                        <input type="button" value="Supprimer"  onClick="control('Supprimer')"> 
						 	 <% } } %>
                     
                    </div></td>
                </tr>
       <tr style="border-top: 1px solid #3b8211;" > &nbsp;  <td colspan=2> </td>             </tr>
              </form>
            </table>
			<br>
<!-- fin admin division  ---->
</div>
 
</div>
<% }}} %>
</BODY>
