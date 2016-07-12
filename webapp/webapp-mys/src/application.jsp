<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<HTML>
  <HEAD>
    <TITLE> application</TITLE>
    <!-- Created by: D.K.S., 18-Nov-1997 -->
  </HEAD>

<body bgcolor="#FFFFFF" text="#000000">
<%@ page errorPage="error.jsp" %>
    <script language="JavaScript1.1">
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
    for (var c = 1; c < rowCellsCnt; c++) {
    theCells[c].style.backgroundColor = thePointerColor;
    }

    return true;
    } // end of the 'setPointer()' function

    function control() {
 
    chaine=document.form.libelle.value.substr(0,255);document.form.libelle.value=chaine;
    if (document.form.th_code.value  == 0) {
    alert("Le choix du theme (menu)  est obligatoire \nCorrigez le ...")
    document.form.th_code.focus()
    return false
    }
    chaine=document.form.message.value.substr(0,255);document.form.message.value=chaine;
 
    chaine=document.form.murl.value.substr(0,255)      ;document.form.murl.value=chaine;
    chaine=document.form.url.value.substr(0,255)       ;document.form.url.value=chaine;
    chaine=document.form.curl_chaine.value.substr(0,20);document.form.curl_chaine.value=chaine;
    if (teststring(document.form.libelle) == false)          {return false;}
 
    if (testlength(document.form.description,3000) == false) {return false;}
 
    if (teststring(document.form.murl) == false)             {return false;}
    if (teststring(document.form.url) == false)              {return false;}
    if (teststring(document.form.curl_chaine) == false)      {return false;}
 
    if (document.form.pt_code.value.length == 0) {
    alert("Le choix du portail-instanceWA est obligatoire \nCorrigez le ...")
    document.form.pt_code.focus()
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
    {   
    alert("a saisir !");
    this.champ.focus();
    return false   }
    return true
    }  
    function testlength(champ,lg)
    {   this.champ=champ
    this.lg=lg;
    if ( this.champ.value.length > lg)
    {  alert("saisie trop longue: "+this.champ.value.length); 
    chaine=this.champ.value.substr(0,lg)     ;
    this.champ.value=chaine;
    this.champ.focus();
    return false   }
    return true;
    }  
    </script>
	
<% 
System.out.println("debut jsp:appli"); 
System.out.println("---------------"); 
try
{
UserInfo userinfo = (UserInfo)session.getValue("currentUser"); 
String vnom=userinfo.getNom();
int profil=userinfo.getProfil();
String voper="";
String zone1   ="bgcolor='#FFFF00'";
String couleur1="#FFFF00";
String zone2="bgcolor='#FF9900'";
String couleur2="#FF9900";
String zone3="bgcolor='#99CCFF'";
String couleur3="#99CCFF";
String couleur="#ffffff"; 
if (request.getParameter("operation")!=null) voper=request.getParameter("operation");
ArrayList liste_one_application       = (ArrayList)session.getValue("liste_one_application") ;
ItemApplication item1 = (ItemApplication) liste_one_application.get(0);
System.out.println("suite jsp:appli listeoneappli"); 
System.out.println("..... jsp:appli listeone appli code "+item1.getAp_code()); 

%>
    <!--  MENU  -->
    <%@ include file="menu.jsp"%>
    <!--  MENU  -->
    <!-- table appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
<% if (userinfo.getProfil()>0) { 

System.out.println("..... jsp:appli"); 

%>
    <table width="100%" border="0">
        <tr> 
        <td height="595"> 
        <div align="right">&nbsp;</div>
        <table border="0" cellspacing="0" cellpadding="1" width=800  bgcolor=#9999CC align="center">
            <tr> 
            <td> 
                <table border="0" cellspacing="0" width=798 cellpadding="0" align="center">
                    <tr> 
                        <td bgcolor="#9999CC" colspan=3> 
                            <div align="center"><i><font color="#FFFFFF" size="5">Application</font></i></div>
                        </td>
                    </tr>
                    <form action="Dispatcher" method="post" name="form"   onSubmit='return control()'>
                    <tr bgcolor=white> 
                        <td colspan=3>&nbsp;</td>
                    </tr>
                    <tr bgcolor=white> 
                    <td align="right" width="113" height="22" valign="baseline"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">libelle</font></td>
                    <td width="408" height="22"> 
					<%System.out.println("debut sssss:appli"); %>
                    <%System.out.println("code appli "+item1.getAp_code()); %>

                    <input type="hidden" name="operation" size="8" value='addapplication'>
                    <input type="hidden" name="code" size="8" value=<%=item1.getAp_code()%>>
					<input type="hidden" name="etat" size="8" value=<%=item1.getAp_ouvert()%>>
                    <input type="hidden" name="etat_message" size="8" value=<%=item1.getAp_ouvert_lib()%>>
                    <input type="text"   name="libelle" size="20" value="<%=item1.getAp_libelle()%>">
                    <font face="Arial, Helvetica, sans-serif" size="2"> visible: 
                        <input type="checkbox" name="visible" value="1"  <% if(item1.getAp_visible()==1) out.println("checked"); %> >
                        controle: 
                        <input type="checkbox" name="controle" value="1" <% if(item1.getAp_controle()==1) out.println("checked"); %> >
                    </font> </td>
                    <td rowspan="4" valign="top"> 
                        <div align="center"> 
                        <textarea name="description" cols="40" rows="3" wrap="Hard"><%=item1.getAp_description()%></textarea>
                        <br>
                        <font size="-1" face="PrimaSans BT,Verdana,sans-serif">description</font></div>
                    </td>
                    </tr>
                    <tr bgcolor=white> 
                        <td align="right" width="113" valign="baseline"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">menu 
                        theme</font></td>
                        <td width="408"> 
                            <div align="left">
							<%System.out.println("debut gggsssss:appli "); %>
 
                            <select name="th_code" size="1">
                                <option value=<%=item1.getTh_code()%> ><%=item1.getTh_libelle()%></option>
                        <% ArrayList listeTH       = (ArrayList)session.getValue("liste_theme") ;
                        for (int i=0; i<listeTH.size(); i++)   {  ItemApplication item = (ItemApplication) listeTH.get(i); %>
                                <option value=<%=item.getTh_code()%> ><%=item.getTh_libelle()%></option>
                        <% } %>
                            </select>
                            &nbsp; </div>
                        </td>
                    </tr>
                    <tr bgcolor=white> 
                        <td align="right" width="113" height="23" valign="baseline"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">date 
                        ouverture</font></td>
                        <td width="408" height="23"> 
                            <input type="text" name="date_ouverture" size="10" value="<%=item1.getAp_date_ouverture()%>">
                        </td>
                    </tr>
                    <tr bgcolor=white> 
                        <td align="right" width="113" height="24" valign="baseline"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">date 
                        fermeture</font></td>
                        <td height="24"> 
                            <input type="text" name="date_fermeture" size="10" value="<%=item1.getAp_date_fermeture()%>">
                        </td>
                    </tr>
                
                    <tr bgcolor=white> 
                        <td align="right" width="113" valign="baseline"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">menu 
                        url</font></td>
                        <td colspan="2"> 
                            <input type="text" name="murl" size="80" value="<%=item1.getAp_murl()%>">
                        </td>
                    </tr>
                    <tr bgcolor=white> 
                        <td align="right" width="113" valign="baseline"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">curl 
                        url</font></td>
                        <td colspan="2"> 
                            <input type="text" name="url" size="80" value="<%=item1.getAp_url()%>">
                        </td>
                    </tr>
                    <tr bgcolor=white> 
                        <td align="right" width="113" valign="baseline"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">curl 
                        chaine </font></td>
                        <td colspan="2"> 
                            <input type="text" name="curl_chaine" size="60" value="<%=item1.getAp_curl_chaine()%>">
                        </td>
                    </tr>
                    <tr bgcolor=white> 
                        <td align="right" width="113" valign="baseline"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">portail-instanceWA</font></td>
                        <td colspan="2"> 
                            <select name="pt_code" size="1">
							<%System.out.println("debut tttttt :appli "); %>

                      <% if(item1.getAp_code()!=0) { %>
                                <option value=<%=item1.getPi_pt_code()%>,<%=item1.getPi_code()%>> 
                      <%=item1.getPt_type()%>(<%=item1.getPt_version()%>) sur 
                      <%=item1.getPt_sigle()%>:<%=item1.getPt_port()%> - <%=item1.getIw_nom()%>port 
                                java=<%=item1.getIw_sigle()%>:<%=item1.getIw_port1()%>,port 
                                sql=<%=item1.getBd_sigle()%>:<%=item1.getIw_port2()%></option>
								<%System.out.println("debut uuuuu :appli "); %>

                      <% } else { %>
                                <option value=""></option>
                      <% } %>
                      <%System.out.println("debut    appli 5 "); %>
                      <% ArrayList listePI       = (ArrayList)session.getValue("liste_PortailIW") ;
                      System.out.println("debut    appli 6 : "+listePI.size());
                       
                      for (int i=0; i<listePI.size(); i++)  
                       {  ItemApplication item = (ItemApplication) listePI.get(i); %>
                                <option value=<%=item.getPi_pt_code()%>,<%=item.getPi_code()%>> 
                      			<%=item.getPt_type()%>(<%=item.getPt_version()%>) sur <%=item.getPt_sigle()%>:<%=item.getPt_port()%> 
                                - <%=item.getIw_nom()%>port java=<%=item.getIw_sigle()%>:<%=item.getIw_port1()%>,port 
                                sql=<%=item.getBd_sigle()%>:<%=item.getIw_port2()%></option>
                      <% } %>
                       <%System.out.println("debut    appli 7 "); %>
                            </select>
                        </td>
                    </tr>
                    <tr bgcolor=white> 
                        <td align="right" width="113" valign="baseline"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">message</font></td>
                        <td colspan="2" valign="baseline"> 
                        <input type="text" name="message" size="60" value="<%=item1.getAp_message()%>">
                        <font size="-1" face="PrimaSans BT,Verdana,sans-serif"> (ce 
                        message s'affichera sur le portail.)</font></td>
                    </tr>
               
                    <tr bgcolor=white> 
                    <td colspan=3 height="122" > 
                        <div align="center"> 
                        <%System.out.println("debut    appli 8 "); %>
                            <p> <% 
				           String ouvert="0";
				           if(item1.getAp_ouvert() != null && item1.getAp_ouvert().length() > 0 ) ouvert= item1.getAp_ouvert().substring(0,1); 
				           %>
				           <%System.out.println("debut    appli 9 "+item1.getAp_code()); %>
                            <b><i><font size="7" color="#9999CC"><% if(ouvert.equals("0")) %> Ouvert   </font></i></b>
                            <b><i><font size="7" color="red">    <% if(ouvert.equals("1")) %> Application fermée </font></i></b>
                            <b><i><font size="7" color="red">    <% if(ouvert.equals("2")) %> Application en maintenance </font></i></b>
                            <br>
                             <% if(item1.getAp_code()==0) { %>
							<input type="reset"  name="Submit"   value="R&eacute;tablir">
                            <input type="submit" value="Ajouter" width="72"  name="submit">
							<% } %>
							 <%System.out.println("debut    appli 10 "); %>
                        <% if(item1.getAp_code()!=0) { %>
                            <input type="submit" value="Modifier" width="72" name="submit">
							<input type="submit" value="Ajouter" width="72"  name="submit">
							 <%System.out.println("debut    appli 10 "); %>
                       <% } %>
                        <%System.out.println("debut    appli 11  "+item1.getAp_ouvert()); %>
                            </p>
                        </div>
                    </td>
                    </tr>
                    </form>
                    <form action="Dispatcher" method="post" name="form2" >
                    <input type="hidden" name="operation" size="8" value='modOapplication'>
                    <input type="hidden" name="code" size="8" value=<%=item1.getAp_code()%>>
                    <tr bgcolor=white> 
                    <td align="right" width="113" height="31" bgcolor="#CCCCFF" valign="baseline"><font color="#FFFFFF" size="3">Changement 
                    état</font></td>
                    <td height="31" align="center" bgcolor="#CCCCFF" colspan="2"> 
                    <% String chk0="";String chk1="";String chk2="";
					if(item1.getAp_ouvert().equals("0")) chk0="checked";
					if(item1.getAp_ouvert().equals("1")) chk1="checked";
					if(item1.getAp_ouvert().equals("2")) chk2="checked";
					%>
                        <div align="right">&nbsp;&nbsp;ouvert 
                            <input type="radio" name="etat" value="0" <%=chk0%> >
                            &nbsp;&nbsp;fermé 
                            <input type="radio" name="etat" value="1" <%=chk1%> >
                            &nbsp;&nbsp;maintenance 
                            <input type="radio" name="etat" value="2" <%=chk2%> >
                            <b>&nbsp;&nbsp;message </b>(raison, dur&eacute;e) 
                            <input type="text" name="etat_message" value="<%=item1.getAp_ouvert_lib()%>">
                            <input type="submit" name="Submit" value="Changer etat">
                        </div>
                    </td>
                    </tr>
                    </form>
                    <form action="Dispatcher" method="post" name="form3" >
                    <input type="hidden" name="operation" size="8" value='addastreinte'>
                    <input type="hidden" name="code" size="8" value=<%=item1.getAp_code()%>> 
                    <tr bgcolor=white> 
                        <td align="right" width="113" valign="baseline"><font size="-1" face="PrimaSans BT,Verdana,sans-serif">Ajouter 
                        alerte</font></td>
                        <td colspan="2"> 
                            <div align="right">&nbsp;&nbsp;<font face="PrimaSans BT,Verdana,sans-serif" color="#000000" size="-1">mail 
                                <input type="checkbox" name="mail" value="mail">
                                &nbsp;&nbsp;sms 
                                <input type="checkbox" name="gsm" value="gsm"> <%=userinfo.getTelephone()%>  
                                <input type="submit" name="Submit" value="Ajouter alerte">
                            </font> </div>
                        </td>
                    </tr>
                    </form>
			  <% if(item1.getAp_code()!=0) { %>
                    <tr bgcolor=#FFFFFF> 
                    
                <td colspan=3 height="54" > 
                  <div align="left"><i><b>Liste des alertes:</b></i> </div>
                  <table width="90%" border="0" cellspacing="0" cellpadding="0" align="center">
                    <tr bordercolor="#000066"> 
                      <td><i><font size="3" color="#9933FF">nom</font></i></td>
                      <td><i><font size="3" color="#9933FF">mail</font></i></td>
                      <td><i><font size="3" color="#9933FF">gsm</font></i></td>
                      <td>&nbsp;</td>
                    </tr>
                    <% ArrayList liste_alerte      = (ArrayList)session.getValue("liste_astreinte") ;
                     for (int i=0; i<liste_alerte.size(); i++)   {  ItemApplication item = (ItemApplication) liste_alerte.get(i); %>
                    <tr> 
                      <td> <%=item.getAs_nom()%> </td>
                      <td> <%=item.getAs_mail()%> </td>
                      <td> <%=item.getAs_gsm()%> </td>
                      <td><a href="Dispatcher?operation=supastreinte&as_code=<%=item.getAs_code()%>&code=<%=item1.getAp_code()%>"> 
                        <img src="images/poubelle.gif" width="14" height="14" border="0"></a> </td>
                    </tr>
                    <% } %>
                  </table>
                  <i><b>Journal:</b></i>
                  <table width="90%" border="0" cellspacing="0" cellpadding="0" align="center">
                    <tr> 
                      <td><i><font color="#9933FF">date</font></i></td>
                      <td><i><font color="#9933FF">etat</font></i></td>
                      <td><i><font color="#9933FF">message</font></i></td>
                      <td><i><font color="#9933FF">auteur</font></i></td>
                    </tr>
                    <% ArrayList liste_journal       = (ArrayList)session.getValue("liste_journal") ;
                     for (int i=0; i<liste_journal.size(); i++)   {  ItemApplication item = (ItemApplication) liste_journal.get(i); %>
                    <tr> 
                      <td><i><%=item.getAl_date()%></i></td>
                      <td><i><%=item.getAl_etat()%></i></td>
                      <td><i><%=item.getAl_libetat()%></i></td>
                      <td><i><%=item.getAl_nom()%></i></td>
                    </tr>
                    <% } %>
                  </table>
                        </td>
                    </tr>
			  <% } %>
                </table>
            </td>
            </tr>
		
        </table>
        <br>
	  <% } %>
        <table border="0" cellspacing="0" cellpadding="1" width=900  bgcolor=#9999CC align="center">
            <tr> 
            <td height="91" > 
            <table border="0" cellspacing="0" width=900 cellpadding="0" align="center">
                <tr> 
                    <td bgcolor="#9999CC" colspan=14> 
                        <div align="center"><font color="#FFFFFF" size="4"><i>Applications 
                        (toutes les zones)</i></font> </div>
                    </td>
                </tr>
                <tr bgcolor=white > 
                    <td width="34" rowspan="2"> 
                        <div align="center"><b><i><font color="#9999CC">Zone</font></i></b></div>
                    </td>
                    <td width="25"   rowspan="2"> 
                        <div align="center"><b><i><font color="#9999CC">Libelle</font></i></b></div>
                    </td>
                    <td width="17" rowspan="2"> 
                        <div align="center"><b><i><font color="#9999CC">V</font></i></b></div>
                    </td>
                    <td width="17" rowspan="2"> 
                        <div align="center"><font color="#9999CC"><b><i>C</i></b></font></div>
                    </td>
                    <td    rowspan="2" colspan="2"> 
                        <div align="center"><b><i><font color="#9999CC">Url</font></i></b></div>
                    </td>
                    <td colspan="6"  > 
                        <div align="center"><b><i><font color="#9999CC">Etats</font></i></b></div>
                    </td>
                    <td width="47" rowspan="2"> 
                        <div align="center"><b><i><font color="#9999CC"> </font></i></b></div>
                        <div align="center"><b><i><font color="#9999CC"> &nbsp;Date</font></i></b></div>
                    </td>
                    <td width=74 rowspan="2"> 
                        <div align="center"><b><i><font color="#9999CC">Action</font></i></b></div>
                    </td>
                </tr>
                <tr bgcolor=white > 
                    <td width="20"    > 
                        <div align="center"><b><i><font face="Arial, Helvetica, sans-serif" size="1" color="#9999CC">A</font></i></b></div>
                    </td>
                    <td width="20"    > 
                        <div align="center"><b><i><font face="Arial, Helvetica, sans-serif" size="1" color="#9999CC">P</font></i></b></div>
                    </td>
                    <td width="20"     > 
                        <div align="center"><b><i><font face="Arial, Helvetica, sans-serif" size="1" color="#9999CC">P->I</font></i></b></div>
                    </td>
                    <td width="20"      > 
                        <div align="center"><b><i><font face="Arial, Helvetica, sans-serif" size="1" color="#9999CC">I</font></i></b></div>
                    </td>
                    <td width="20"    > 
                        <div align="center"><b><i><font face="Arial, Helvetica, sans-serif" size="1" color="#9999CC">I->B</font></i></b></div>
                    </td>
                    <td width="20"     > 
                        <div align="center"><b><i><font face="Arial, Helvetica, sans-serif" size="1" color="#9999CC">B</font></i></b></div>
                    </td>
                </tr>
              <% ArrayList liste_application       = (ArrayList)session.getValue("liste_application") ;
for (int i=0; i<liste_application.size(); i++)   {  ItemApplication item = (ItemApplication) liste_application.get(i); %>
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
                <td   height="21" width="253"><font color="#0000FF" size="2" face="Arial, Helvetica, sans-serif"><a href="Dispatcher?operation=voirapplication&code=<%=item.getAp_code()%>"><%=item.getAp_libelle()%></a></font></td>
                <td width="17" height="21"> <font size="2" color="#0000FF"> <a href="Dispatcher?operation=modVapplication&code=<%=item.getAp_code()%>&val=<%=item.getAp_visible()%>"> 
                  <%=item.getAp_visible() %> </a> </font></td>
                <td width="17" height="21"> <font size="2" color="#0000FF"> <a href="Dispatcher?operation=modCapplication&code=<%=item.getAp_code()%>&val=<%=item.getAp_controle()%>"> 
                  <%=item.getAp_controle() %> </a> </font></td>
                <td   height="21" bgcolor="#FFFFFF" width="359"><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"> 
                    <a href="<%=item.getAp_url()%>">&nbsp;<%=item.getAp_url()%></a> 
                </font></td>
                <td   height="21" bgcolor="#FFFFFF" width="22">
				<% 
				String ouvert="0";
				if(item.getAp_ouvert() != null ) ouvert= item.getAp_ouvert().substring(0,1); 
				%>
				
                    <img src="images/flag<%=ouvert.substring(0,1)%>.gif" width="19" height="16"> 
                </td>
                <td width="20" height="21"> 
                  <% String num=item.getAp_etat()       ;  %>
                <img src="images/boule_<%=num%>.gif" height="20"  > </td>
                <td width="20" height="21"> 
                  <%        num=item.getPt_port_etat()  ;  %>
                <img src="images/boule_<%=num%>.gif" height="20"  > </td>
                <td width="20" height="21"> 
                  <%        num=item.getPi_port_etat()  ;  %>
                <img src="images/boule_<%=num%>.gif" height="20"  > </td>
                <td width="20" height="21"> 
                  <%        num=item.getIw_port1_etat() ;  %>
                <img src="images/boule_<%=num%>.gif" height="20"  > </td>
                <td width="20" height="21"> 
                  <%        num=item.getIw_port2_etat() ;  %>
                <img src="images/boule_<%=num%>.gif" height="20"  > </td>
                <td width="20" height="21"> 
                  <%        num=item.getBd_port_etat()  ;  %>
                <img src="images/boule_<%=num%>.gif" height="20"  > </td>
                <td width="47" height="21"  ><font color="#000033" size="-2" face="Arial, Helvetica, sans-serif">&nbsp; 
                </font> 
                    <div align="center"><font color="#000033" size="-2" face="Arial, Helvetica, sans-serif"><%=item.getAp_etat_date()%></font><font color="#000033" size="2" face="Arial, Helvetica, sans-serif"></font> 
                    </div>
                </td>
                <td width="74"> 
                  <% if(profil>0) { %>
                    <div align="right"><font face="Arial, Helvetica, sans-serif" size="2"> 
                        <a href="Dispatcher?operation=supapplication&code=<%=item.getAp_code()%>"> 
                        <img src="images/poubelle.gif" width="14" height="14" border="0"></a> 
                        <a href="Dispatcher?operation=modapplication&code=<%=item.getAp_code()%>" target="_top"> 
                        <img src="avis4.gif" width="19" height="15" border="0"></a> 
                    </font></div>
                  <% } %>
                </td>
                </tr>
              <% } 
}
catch(IOException e)
 	{	System.out.println("<H2>"+"ClassNotFoundException: " + e.getMessage() + "<BR>");
 	}
%>
            </table>
            </tr>
        </table>
        </td>
        </tr>
    </table>
 
    <table width="33%" border="0" align="center">
        <tr valign="baseline"> 
            <td> 
            <table width="107" border="0"  height="82" align="center" bgcolor="white" cellpadding="0" cellspacing="0">
            <tr> 
                <td width="28"     bgcolor="#FFFF00" height="18">&nbsp;</td>
                <td width="79" height="18"   > 
                    <div align="right" class="style17"> 
                        <div align="left"> <font face="Arial, Helvetica, sans-serif" size="1">&nbsp;gestion</font></div>
                    </div>
                </td>
            </tr>
            <tr> 
                <td height="19" bgcolor="#FF9900" width="28">&nbsp;</td>
                <td width="79"> 
                    <div align="right" class="style17"> 
                        <div align="left"> <font face="Arial, Helvetica, sans-serif" size="1">&nbsp;agriates</font></div>
                    </div>
                </td>
            </tr>
            <tr> 
            <td height="19" bgcolor="#99CCFF" width="28">&nbsp;</td>
            <td width="79"> 
                <div align="right" class="style17"> 
                    <div align="left"> <font face="Arial, Helvetica, sans-serif" size="1">&nbsp;extranet</font></div>
                </div>
            </td>
        </tr>
                <tr> 
                <td height="19" bgcolor="#FFFFFF" width="28"> 
                    <div align="center"><img src="images/boule_ .gif"  ></div>
                </td>
                <td width="79"> 
                    <div align="right" class="style17"> 
                        <div align="left"> <font face="Arial, Helvetica, sans-serif" size="1">&nbsp;attente 
                        controle</font></div>
                    </div>
                </td>
                </tr>
                <tr> 
                    <td height="19" bgcolor="#FFFFFF" width="28"> 
                        <div align="center"><img src="images/boule_1.gif"  ></div>
                    </td>
                    <td width="79"> 
                        <div align="right" class="style17"> 
                            <div align="left"> <font face="Arial, Helvetica, sans-serif" size="1">&nbsp;ok</font></div>
                        </div>
                    </td>
                </tr>
                <tr> 
                    <td height="19" bgcolor="#FFFFFF" width="28"> 
                        <div align="center"><img src="images/boule_2.gif"  ></div>
                    </td>
                    <td width="79"> 
                        <div align="right" class="style17"> 
                            <div align="left"> <font face="Arial, Helvetica, sans-serif" size="1" color="#FF0000">&nbsp;ko</font></div>
                        </div>
                    </td>
                </tr>
                <tr> 
                    <td height="19" bgcolor="#FFFFFF" width="28"> 
                        <div align="center"><img src="images/flag1.gif" width="17" height="16"  ></div>
                    </td>
                    <td width="79"> 
                        <div align="right" class="style17"> 
                            <div align="left"> <font face="Arial, Helvetica, sans-serif" size="1" color="#FF0000">&nbsp;ferm&eacute; 
                            ou maintenance</font></div>
                        </div>
                    </td>
                </tr>
                <tr bgcolor="#666666"> 
                <td height="19" colspan="2"> 
                    <div align="right" class="style17"> 
                        <div align="left" class="style18"> 
                            <div align="center" class="style10"><strong><font color="#FFFFFF">Légende</font></strong></div>
                        </div>
                    </div>
                </td>
                </tr>
            </table>
        </td>
        <td> 
            <table width="194" border="0"  height="82"   bgcolor="white" cellpadding="0" cellspacing="0">
                <tr bgcolor="#9999CC"> 
                    <td colspan="2" height="18"> 
                        <div align="center" class="style17"> <font face="Arial, Helvetica, sans-serif" size="1">Etats</font></div>
                    </td>
                </tr>
                <tr> 
                    <td height="19" bgcolor="#FFFFFF" width="34"><b><font color="#9999CC">A</font></b></td>
                    <td width="160"> 
                        <div align="right" class="style17"> 
                            <div align="left"> <font face="Arial, Helvetica, sans-serif" size="1">&nbsp;test 
                            application(curl) </font></div>
                        </div>
                    </td>
                </tr>
                <tr> 
                    <td height="19" bgcolor="#FFFFFF" width="34"><b><font color="#9999CC">P</font></b></td>
                    <td width="160"> 
                        <div align="right" class="style17"> 
                            <div align="left"> <font face="Arial, Helvetica, sans-serif" size="1">&nbsp;portail 
                            web </font></div>
                        </div>
                    </td>
                </tr>
                <tr> 
                    <td height="19" bgcolor="#FFFFFF" width="34"><b><font color="#9999CC"> 
                    P->I </font></b></td>
                    <td width="160"> 
                        <div align="right" class="style17"> 
                            <div align="left"> <font face="Arial, Helvetica, sans-serif" size="1">&nbsp;portail 
                            vers webapp</font></div>
                        </div>
                    </td>
                </tr>
                <tr> 
                    <td height="19" bgcolor="#FFFFFF" width="34"><b><font color="#9999CC"> 
                    I </font></b></td>
                    <td width="160"> 
                        <div align="right" class="style17"> 
                            <div align="left"> <font face="Arial, Helvetica, sans-serif" size="1">&nbsp;webapp</font></div>
                        </div>
                    </td>
                </tr>
                <tr> 
                    <td height="19" bgcolor="#FFFFFF" width="34"><b><font color="#9999CC"> 
                    I->B </font></b></td>
                    <td width="160"> 
                        <div align="right" class="style17"> 
                            <div align="left"> <font face="Arial, Helvetica, sans-serif" size="1">webapp 
                            vers bdd</font></div>
                        </div>
                    </td>
                </tr>
                <tr> 
                    <td height="19" bgcolor="#FFFFFF" width="34"><b><font color="#9999CC"> 
                    B </font></b></td>
                    <td width="160"> 
                        <div align="right" class="style17"> 
                            <div align="left"> <font face="Arial, Helvetica, sans-serif" size="1">bdd</font></div>
                        </div>
                    </td>
                </tr>
                <tr bgcolor="#666666"> 
                    <td height="2" colspan="2"> 
             
                    </td>
                </tr>
            </table>
        </td>
        </tr>
        <tr valign="baseline"> 
            <td colspan="2" height="20">&nbsp;</td>
        </tr>
    </table>
     
</body>
</html>

