<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
 
<html>
<head>
<title>maquette</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 
	<style type="text/css">
	<!--

.style9 {
		color: #0000FF;
		font-style: italic;
		font-weight: bold;
	}
.style10 {color: #FFFFFF}
.style16 {
	color: #FFFFFF;
	font-weight: bold;
	font-style: italic;
}
.style17 {
	font-size: 10px;
	font-style: italic;
}
.style18 {font-size: 12}
#toutautour {
  border-width:1px;
  border-style:solid;
  border-color:orange;
  padding:1mm;
  text-align:justify; }

	-->
	</style>
	</head>
	
	<body>
	<% 
System.out.println("debut jsp:maquette"); 
try
{
UserInfo userinfo = (UserInfo)session.getValue("currentUser"); 
String vnom=userinfo.getNom();
int profil=userinfo.getProfil();
String voper="";
int vcode=0; 
String vlibelle="";
String vurl="";
String vcurl="";
String vdouv="";
String vdfer="";
int i=0;

String zone1="bgcolor='#FFFF00'";
String zone2="bgcolor='#FF9900'";
String zone3="bgcolor='#99CCFF'";
String mzone3="bgcolor='#0067CE'";
String mzone2="bgcolor='#C47500'";
String mzone1="bgcolor='#C1C100'"; 
String vcodenom="null";
System.out.println("operation:"+request.getParameter("operation")); 
if (request.getParameter("operation")!=null) voper=request.getParameter("operation");
if (request.getParameter("operation")!=null&&(request.getParameter("operation").equals("voirapplication")))
{ 
System.out.println("voirappli");
ArrayList liste_one_application       = (ArrayList)session.getValue("liste_one_application") ;
  
ItemApplication item = (ItemApplication) liste_one_application.get(0);  
System.out.println("oneappli"); 
System.out.println("oneappli:"+item.getAp_libelle());
  
  %>  
  <!--  MENU  --> <%@ include file="menu.jsp"%> <!--  MENU  -->   
<table    border="0"  align="center" cellpadding="0" cellspacing="0" bgcolor="white">
  <tr> 
    <td height="1" colspan="5"> </td>
  </tr>
  <!-- ligne appli    -->
  <tr> 
    <td   colspan="5"  > 
      <!-- table appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
      <p id="toutautour"> 
      <table width="100%"  border="0"   cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
        <tr bgcolor="#FF9966"> 
          <!-- nom de la machine -->
          <td height="21" colspan="5" align="center" ><em><strong><font face="Arial, Helvetica, sans-serif" size="6" color="#FFFFFF">Application: 
            &nbsp;<%=item.getAp_libelle()%></font></strong></em></td>
        </tr>
        <tr> 
          <td colspan="5" h  bgcolor="#FFFFFF" height="41"> 
           
              <% int lg=item.getAp_description().length();
   if (item.getAp_description().indexOf("                     ")!=-1) lg=item.getAp_description().indexOf("                     ");
%>
           
            <pre> <font color="#0000FF"><%=item.getAp_description().substring(0,lg)%></font></pre>
             </td>
        </tr>
        <tr> 
          <td height="19" bgcolor="#FFFFFF" width="265"> 
            <div align="right"><em>adresse menu:</em></div>
          </td>
          <td bgcolor="#FFFFFF" colspan="3"> 
            <div align="left"><a href="<%=item.getAp_url()%>"> <font color="#0000FF">&nbsp;<%=item.getAp_murl()%></font></a></div>
          </td>
        </tr>
        <tr> 
          <td height="19" bgcolor="#FFFFFF" width="265"> 
            <div align="right"><em>adresse curl:</em></div>
          </td>
          <td bgcolor="#FFFFFF" colspan="3"> 
            <div align="left"><a href="<%=item.getAp_url()%>"> <font color="#0000FF">&nbsp;<%=item.getAp_url()%></font></a></div>
          </td>
        </tr>
        <tr> 
          <td height="17" bgcolor="#FFFFFF" width="265"> 
            <div align="right"><em>date ouverture: </em></div>
          </td>
          <td height="17" bgcolor="#FFFFFF" colspan="3"> 
            <div align="left"><font color="#0000FF">&nbsp;<em><font color="#0000FF"><%=item.getAp_date_ouverture()%></font></em></font></div>
          </td>
        </tr>
        <tr> 
          <td height="17" bgcolor="#FFFFFF" width="265"> 
            <div align="right"><em>date fermeture: </em></div>
          </td>
          <td height="17" bgcolor="#FFFFFF" colspan="3"> 
            <div align="left"><font color="#0000FF">&nbsp;<%=item.getAp_date_fermeture()%></font></div>
          </td>
        </tr>
        <tr> 
          <td height="18" bgcolor="#FFFFFF" width="265"> 
            <div align="right"><em>message du jour:</em></div>
          </td>
          <td bgcolor="#FFFFFF" height="18" colspan="3"> 
            <div align="left"><font color="#0000FF">&nbsp;<%=item.getAp_message()%></font></div>
          </td>
        </tr>
        <tr> 
          <td height="15" bgcolor="#FFFFFF" width="265"> 
            <div align="right"><em>chaine de controle:</em></div>
          </td>
          <td bgcolor="#FFFFFF" height="15" colspan="3"> 
            <div align="left"> <font color="#0000FF">&nbsp;<%=item.getAp_curl_chaine()%></font> 
            </div>
          </td>
        </tr>
        <tr> 
          <td height="5" bgcolor="#FFFFFF" width="265"> 
            <div align="right"><em>date dernier controle:</em></div>
          </td>
          <td bgcolor="#FFFFFF" height="5" colspan="3"> 
            <div align="left"> <font color="#0000FF">&nbsp;<%=item.getAp_etat_date()%></font> 
            </div>
          </td>
        </tr>
        <tr bordercolor="#0066FF"> 
          <td height="10" bgcolor="#FFFFFF" colspan="2"> </td>
          <td height="10" bgcolor="#FFFFFF" width="72">
<div align="center"> 
            <%if (item.getAp_etat()!=null && item.getAp_etat().equals("1") )      { %>
            <img src="images/boule_bleue.jpg" width="15" height="15" align="absbottom"><br>
              <img src="images/ligne_bleue.jpg" width="3" height="10"   align="middle"><br>
              <% } else { %>
              <img src="images/boule_rouge.jpg" width="15" height="15" align="absbottom"><br>
              <img src="images/ligne_rouge.jpg" width="3" height="10"   align="middle"><br>
              <% } %>
              <%if (item.getPt_port_etat()!=null && item.getPt_port_etat().equals("1") )      { %>
              <img src="images/boule_bleue.jpg" width="15" height="15" align="absbottom"> 
              <% } else { %>
              <img src="images/boule_rouge.jpg" width="15" height="15" align="absbottom"> 
              <% } %>
             
            </div>
          </td>
          <td height="10" bgcolor="#FFFFFF" width="595"><span class="style9"><font size="6" color="#0000FF"><i><%=item.getPt_port()%></i></font></span></td>
        </tr>
        <tr> 
          <td height="10" bgcolor="#FFFFFF" colspan="4"> 
            <table width="582" height="127" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="black">
              <tr> 
                <td> 
                  <table width="580" height="125" border="0" align="center" cellpadding="0" cellspacing="0"   
                 <% if(item.getPt_zone()==1) out.println(zone1);
                    if(item.getPt_zone()==2) out.println(zone2);
                    if(item.getPt_zone()==3) out.println(zone3);  %>
>
                    <tr> 
                      <!-- nom de la machine -->
                      <td height="21" colspan="2" align="center"  
                 <% if(item.getPt_zone()==1) out.println(zone1);
                    if(item.getPt_zone()==2) out.println(zone2);
                    if(item.getPt_zone()==3) out.println(zone3);  %>
><em><strong><%=item.getPt_type()%></strong></em></td>
                      <td width="56" rowspan="6" bordercolor="#FFFFFF" 
                 <% if(item.getPt_zone()==1) out.println(mzone1);
                    if(item.getPt_zone()==2) out.println(mzone2);
                    if(item.getPt_zone()==3) out.println(mzone3);  %>
> 
                        <div align="center"><span class="style16"> 
                          <% i = 0; while(i < item.getPt_sigle().length())
					     {  out.println(item.getPt_sigle().substring(i,i+1)+"<br>");i++; } %>
                          </span></div>
                      </td>
                    </tr>
                    <tr> 
                      <td width="200" bgcolor="#FFFFFF" height="20"> 
                        <div align="right"><em>nom:</em></div>
                      </td>
                      <td width="306" bgcolor="#FFFFFF" height="20"><font color="#0000FF">&nbsp;<%=item.getPt_type()%></font></td>
                    </tr>
                    <tr> 
                      <td width="200" bgcolor="#FFFFFF" height="16"> 
                        <div align="right"><em>version:</em></div>
                      </td>
                      <td width="306" bgcolor="#FFFFFF" height="16"><font color="#0000FF">&nbsp;<%=item.getPt_version()%></font></td>
                    </tr>
                    <tr> 
                      <td height="19" bgcolor="#FFFFFF" width="200"> 
                        <div align="right"><em>repertoire:</em></div>
                      </td>
                      <td bgcolor="#FFFFFF" width="306"><font color="#0000FF">&nbsp;<%=item.getPt_repertoire()%></font></td>
                    </tr>
                    <tr> 
                      <td height="19" bgcolor="#FFFFFF" width="200"> 
                        <div align="right"><em>port: </em></div>
                      </td>
                      <td bgcolor="#FFFFFF" width="306"><font color="#0000FF">&nbsp;<%=item.getPt_port()%></font></td>
                    </tr>
                    <tr> 
                      <td height="19" bgcolor="#FFFFFF" width="200"> 
                        <div align="right"><em>date dernier controle:</em></div>
                      </td>
                      <td bgcolor="#FFFFFF" width="306"><font color="#0000FF">&nbsp;<%=item.getPt_port_etat_date()%></font></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr> 
          <td height="10" colspan="2"> </td>
          <td height="10" width="72" align=center> 
            <div align="center">
            <%if (item.getPi_port_etat()!=null && item.getPi_port_etat().equals("1") )      { %>
           <img src="images/boule_bleue.jpg" width="15" height="15" align="absbottom"><br>
              <img src="images/ligne_bleue.jpg" width="3" height="10"   align="middle"><br>
              <% } else { %>
              <img src="images/boule_rouge.jpg" width="15" height="15" align="absbottom"><br>
              <img src="images/ligne_rouge.jpg" width="3" height="10"   align="middle"><br>
              <% } %>
              <%if (item.getIw_port1_etat()!=null && item.getIw_port1_etat().equals("1") )      { %>
              <img src="images/boule_bleue.jpg" width="15" height="15" align="absbottom"> 
              <% } else { %>
              <img src="images/boule_rouge.jpg" width="15" height="15" align="absbottom"> 
              <% } %>
                         </div>
          </td>
          <td height="10" width="595"><span class="style9"><font size="6" color="#0000FF"><i><%=item.getIw_port1()%></i></font></span></td>
        </tr>
        <tr> 
          <td height="10" bgcolor="#FFFFFF" colspan="4"> 
            <table width="582" height="127" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="black">
              <tr> 
                <td> 
                  <table width="580" height="125" border="0" align="center" cellpadding="0" cellspacing="0"   
                 <% if(item.getIw_zone()==1) out.println(zone1);
                    if(item.getIw_zone()==2) out.println(zone2);
                    if(item.getIw_zone()==3) out.println(zone3);  %>
>
                    <tr> 
                      <!-- nom de la machine -->
                      <td height="21" colspan="2" align="center" ><em><strong>Webapp</strong></em></td>
                      <td width="57" rowspan="6" bordercolor="#FFFFFF" 
                 <% if(item.getIw_zone()==1) out.println(mzone1);
                    if(item.getIw_zone()==2) out.println(mzone2);
                    if(item.getIw_zone()==3) out.println(mzone3);  %>
> 
                        <div align="center"><span class="style16"> 
                          <% i = 0; while(i < item.getIw_sigle().length())
					     {  out.println(item.getIw_sigle().substring(i,i+1)+"<br>");i++; } %>
                          </span></div>
                      </td>
                    </tr>
                    <tr> 
                      <td width="200" bgcolor="#FFFFFF"> 
                        <div align="right"><em>nom:</em></div>
                      </td>
                      <td width="323" bgcolor="#FFFFFF"><font color="#0000FF">&nbsp;<%=item.getIw_nom()%></font></td>
                    </tr>
                    <tr> 
                      <td width="200" bgcolor="#FFFFFF"> 
                        <div align="right"><em>type:</em></div>
                      </td>
                      <td width="323" bgcolor="#FFFFFF"> <font color="#0000FF">&nbsp;<%=item.getWa_type()%></font></td>
                    </tr>
                    <tr> 
                      <td height="19" bgcolor="#FFFFFF" width="200"> 
                        <div align="right"><em>version:</em></div>
                      </td>
                      <td bgcolor="#FFFFFF" width="323"><font color="#0000FF">&nbsp;<%=item.getWa_version()%></font></td>
                    </tr>
                    <tr> 
                      <td height="19" bgcolor="#FFFFFF" width="200"> 
                        <div align="right"><em>repertoire: </em></div>
                      </td>
                      <td bgcolor="#FFFFFF" width="323"><font color="#0000FF">&nbsp;<%=item.getWa_repertoire()%></font></td>
                    </tr>
                     <tr> 
                      <td height="9" bgcolor="#FFFFFF" width="200"> 
                        <div align="right"><em>date dernier controle: </em></div>
                      </td>
                      <td bgcolor="#FFFFFF" width="323" height="9"><font color="#0000FF">&nbsp;<%=item.getIw_port1_etat_date()%></font></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr> 
          <td height="10" width="265"> </td>
          <td height="10" width="279"></td>
          <td height="10" width="72"> 
            <div align="center">
            <%if (item.getIw_port2_etat()!=null && item.getIw_port2_etat().equals("1") )      { %>
            <img src="images/boule_bleue.jpg" width="15" height="15" align="absbottom"><br>
              <img src="images/ligne_bleue.jpg" width="3" height="10"   align="middle"><br>
              <% } else { %>
              <img src="images/boule_rouge.jpg" width="15" height="15" align="absbottom"><br>
              <img src="images/ligne_rouge.jpg" width="3" height="10"   align="middle"><br>
              <% } %>
              <%if (item.getBd_port_etat()!=null && item.getBd_port_etat().equals("1") )      { %>
              <img src="images/boule_bleue.jpg" width="15" height="15" align="absbottom"> 
              <% } else { %>
              <img src="images/boule_rouge.jpg" width="15" height="15" align="absbottom"> 
              <% } %>
            </div>
          </td>
          <td height="10" width="595"><span class="style9"><font color="#0000FF" size="6"><i><%=item.getIw_port2()%></i></font></span></td>
        </tr>
        <tr> 
          <td height="10" bgcolor="#FFFFFF" colspan="4"> 
            <table width="582" height="140" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="black">
              <tr> 
                <td height="138"> 
                  <table width="580" height="138" border="0" align="center" cellpadding="0" cellspacing="0" 
                 <% if(item.getBd_zone()==1) out.println(zone1);
                    if(item.getBd_zone()==2) out.println(zone2);
                    if(item.getBd_zone()==3) out.println(zone3);  %>
>
                    <tr> 
                      <!-- nom de la machine -->
                      <td height="21" colspan="2" align="center" ><em><strong>Base 
                        de donn&eacute;es </strong></em></td>
                      <td width="56" rowspan="6" bordercolor="#FFFFFF" 
                  <% if(item.getBd_zone()==1) out.println(mzone1);
                    if(item.getBd_zone()==2) out.println(mzone2);
                    if(item.getBd_zone()==3) out.println(mzone3);  %>
> 
                        <div align="center"> 
                          <p><span class="style16"> 
                            <% i = 0; while(i < item.getBd_sigle().length())
					     {  out.println(item.getBd_sigle().substring(i,i+1)+"<br>");i++; } %>
                            </span> </p>
                        </div>
                      </td>
                    </tr>
                    <tr> 
                      <td width="200" bgcolor="#FFFFFF" height="21"> 
                        <div align="right"><em>nom:</em></div>
                      </td>
                      <td width="323" bgcolor="#FFFFFF" height="21"><font color="#0000FF">&nbsp;<%=item.getBd_sigle()%></font></td>
                    </tr>
                    <tr> 
                      <td width="200" bgcolor="#FFFFFF" height="17"> 
                        <div align="right"><em>type:</em></div>
                      </td>
                      <td width="323" bgcolor="#FFFFFF" height="17"><font color="#0000FF">&nbsp;<%=item.getBd_type()%></font></td>
                    </tr>
                    <tr> 
                      <td height="19" bgcolor="#FFFFFF" width="200"> 
                        <div align="right"><em>repertoire:</em></div>
                      </td>
                      <td bgcolor="#FFFFFF" width="323"><font color="#0000FF">&nbsp;<%=item.getBd_repertoire()%></font></td>
                    </tr>
                    <tr> 
                      <td height="10" bgcolor="#FFFFFF" width="200"> 
                        <div align="right"><em>port:</em></div>
                      </td>
                      <td bgcolor="#FFFFFF" width="323" height="10"><font color="#0000FF">&nbsp;<%=item.getBd_port()%></font></td>
                    </tr>
                    <tr> 
                      <td height="9" bgcolor="#FFFFFF" width="200"> 
                        <div align="right"><em>date dernier controle: </em></div>
                      </td>
                      <td bgcolor="#FFFFFF" width="323" height="9"><font color="#0000FF">&nbsp;<%=item.getBd_port_etat_date()%></font></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
<!-- fin ligne application -->
<!-- ligne liaison bas -->
<tr> 
    
     
    <td width="139" height="13"> 
      
    <div align="center"> </div>
    </td>
    <td width="96" height="13">&nbsp;</td>
  </tr>
  <!-- fin ligne liaison bas -->
  <!-- ligne appli    -->
  <tr> 
    
  <td colspan="5"  > 
    <div align="right"></div>
      
    </td>
  </tr>
  <!-- fin ligne application -->
  <!-- ligne liaison bas -->
  <tr> 
    <td width="141" height="15" valign="bottom"> 
      
    </td>
    
    <td height="15"> 
      <div align="center"> <div align="center"> 
       
      </div>
    </div>
    </td>
    <td width="96" height="15">&nbsp;</td>
  </tr>
  <!-- fin ligne liaison bas -->
  <!-- ligne appli    -->
  <tr> 
    <td colspan="5"> 
      
    <div align="center"> 
      <!-- table appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
      <!-- fin table appli (bordure) -->
    </div>
    </td>
  </tr>
  <!-- fin ligne application -->
  <!-- ligne liaison bas -->
  <tr> 
    <td height="23" valign="bottom"> 
      <div align="right"> </div>
    </td>
     
    <td height="23"> 
      <div align="center"> 
    
    </div>
    </td>
    <td height="23">&nbsp;</td>
    <td width="96" height="23">&nbsp;</td>
  </tr>
  <!-- fin ligne liaison bas -->
  <!-- ligne appli    -->
  <tr> 
    <td colspan="5" height="140"> 
      
    <div align="center"> 
      <!-- table appli (bordure) width=interieur + 2,  heigth=interieur + 2-->
      <!-- fin table appli (bordure) -->
      <table width="139" height="84" border="0" align="right" cellpadding="0" cellspacing="0" bgcolor=blue>
        <tr> 
          <td height="82"> 
            <table width="137" border="0"  height="82" align="center" bgcolor="white" cellpadding="0" cellspacing="0">
              <tr> 
                <td width="69"   <%=zone3%> >&nbsp;</td>
                <td width="106"   > 
                  <div align="right" class="style17"> 
                    <div align="left">zone 3: infrastructure </div>
                  </div>
                </td>
              </tr>
              <tr>  
                <td height="19" <%=zone2%> >&nbsp;</td>
                <td> 
                  <div align="right" class="style17"> 
                    <div align="left">zone 2: dmz </div>
                  </div>
                </td>
              </tr>
              <tr> 
                <td height="19" <%=zone1%> >&nbsp;</td>
                <td> 
                  <div align="right" class="style17"> 
                    <div align="left">zone 1: gestion </div>
                  </div>
                </td>
              </tr>
              <tr bgcolor="#666666"> 
                <td height="19" colspan="2"> 
                  <div align="right" class="style17"> 
                    <div align="left" class="style18"> 
                      <div align="center" class="style10"><strong>Légende</strong></div>
                    </div>
                  </div>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      </div>
    </td>
  </tr>
  <!-- fin ligne application -->
  <tr> 
    <td colspan="5"> 
      <div align="center"><br>
      </div>
      
  </td>
    <td width="173" valign="middle"> </td>
  </tr>
<%} }
catch(IOException e)
 	{	System.out.println("<H2>"+"ClassNotFoundException: " + e.getMessage() + "<BR>");
 	}
%>
</body>
</html>
