<HTML><META HTTP-EQUIV="Refresh"  Content="150";URL="Dispatcher?operation=browse">
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="javabeans.*" %>
<!--------- dropmenu ------------->
<head>
<script language="JavaScript" src="resources/sniffer.js"></script>
<script language="JavaScript1.2">
<%@  include file="include_dropmenu_custom"%>    
<%=session.getValue("dropmenu")%>
</script>
<script language="JavaScript1.2" src="resources/style.js"></script>
</head>
<body bgcolor=white><br>
<!--------------- <table width=805 align=center bgcolor=white border=1><tr><td> --------->
<!--------------------------><%@  include file="include_init_form.jsp" %>
<div align=center>
<table width=800 cellpadding=0 cellspacing=0 border=0 >
<tr><td><font color=blue size="4"><b><i>Ddt:</b>
      <% out.println(" "+userinfo.getAgnom()+", "+    userinfo.getSvnom());
      if (userinfo.getInitiales()!=null) out.println("("+userinfo.getInitiales()+")"); %> </i></font></td>
<td align=center><font color=red size="4"><i>
<!------------------------->
<%@  include file="titreddt.jsp"          %> 
</td></tr>
<tr>
<td valign=top>
    <table cellpadding=0 cellspacing=0 border=1 width=11 height=10>
    <tr><td align=left valign=middle>
        <div><img src="/ddt/images/point.gif" name="anchor" alt="" border=0 width=1 height=1></div>
        </td>
    </tr>
    </table>
</td>
<td></td>
    </tr></table>
</div>
<br>
<!------------------------ fin entete dropmenu ---- , ne pas oublier en fin: menu.js  ------->  
<!--------------- <script language="javascript1.2" src="resources/menu.js"></script> ---------->
<table cellpadding="0"width=820 cellspacing="0" border="0" marginheight="0" marginwidth="0" align="center"  >
<!--trait -->    <tr width=820>      
    <td width="1" bgcolor="#0000FF" ><img src="point.gif" width="1" height="1"></tD>
    <td  bgcolor="#0000FF" height=1  width=""> </td>
    <td width="1" bgcolor="#0000FF" ><img src="point.gif" width="1" height="1"></tD></tr> <!--fin trait-->
<!--corps -->    <tr width=800>         <!--liste demandes recues -->
    <td width="1" bgcolor="#0000FF" height="46" > <img src='point.gif' width='1' height='1'></td>
    <td width=798  > 
<%//_________________________________table menu___________________________________
if (userinfo.getAgprofil()==0)
{ %>
<center><table bgcolor=c0c0c0><tr bgcolor=c0c0c0>
    <td>mcode</td>
    <td>mnom</td>
    <td>mimage</td>
    <td>mlien</td>
    <td>mdroit</td>
    <td>mordre</td>
    <td> </td>
</tr>
<% 
ArrayList liste_menu = (ArrayList)session.getValue("liste_menu");
for (int i=0; i<liste_menu.size(); i++)
        {  ItemMenu item = (ItemMenu) liste_menu.get(i);
%>
<tr><FORM name='form' ACTION=Dispatcher METHOD=POST><input type=hidden name=operation size=8 maxlength=8 value=addmenu>
<td><input type=text name=mcode size=3  maxlength=80 value=<%=item.getMcode()%>></td>
<td><input type=text name=mnom  size=30 maxlength=80 value='<%=item.getMnom()%>'></td> 
<td><input type=text name=mimage    size=5 maxlength=80 value=<%=item.getMimage()%>></td> 
<td><input type=text name=mlien     size=30 maxlength=80 value=<%=item.getMlien()%>></td> 
<td><input type=text name=mdroit    size=1 maxlength=80 value=<%=item.getMdroit()%>></td> 
<td><input type=text name=mordre    size=1 maxlength=80 value=<%=item.getMordre()%>></td> 
<td><input type='submit' value='mod'     name='submit'>
    <input type='submit' value='copy'   name='submit'>
    <a href=Dispatcher?operation=supmenu&code=<%=item.getMcode()%>  >
    <img border="0" src="/ddt/images/poub.gif"  alt="Suppression de ce menu" width="25" height="25"></a>
</td>
</tr></form> 
<%        }     //fin de la boucle    %>
<center> 
<tr>
<td></td> <FORM name='form' ACTION=Dispatcher METHOD=POST>
<input type=hidden name=operation size=8 maxlength=8 value=addmenu>   
<td><input type=text name=mnom size=30 maxlength=80></td>
<td><input type=text name=mimage size=5 maxlength=80></td>
<td><input type=text name=mlien size=30 maxlength=80></td>
<td><input type=text name=mdroit size=1 value=0 maxlength=3></td>
<td><input type=text name=mordre size=1 value=99 maxlength=3></td>
<td><input type='submit' value='Ajout menu' name='submit'></td>
</FORM>
</tr></table><i>menus</i> 
<%     }       // fin MENU
//_________________________________table action___________________________________%>
<center><table border=0><tr bgcolor=c0c0c0>
    <td>mcode</td> 
    <td>mnom</td>
    <td>nom</td>
    <td>lien</td>
     
    <td>droit</td>
    <td>droitu</td>
 <td>action</td>
</tr>
<% 
ArrayList liste_action = (ArrayList)session.getValue("liste_action");
for (int i=0; i<liste_action.size(); i++)
        {  ItemAction item = (ItemAction) liste_action.get(i);
           if ((item.getAdroitu()==userinfo.getCodeagt())||userinfo.getAgprofil()==0)
           {
%>
<tr><FORM name='form' ACTION=Dispatcher METHOD=get><input type=hidden name=operation   value=modaction>
    <td><input type=text name=mcode size=2 maxlength=80 value=<%=item.getMcode()%>></td>
    <td><input type=text name=mnom size=4  maxlength=80 value=<%=item.getMnom()%>>
        <input type=hidden name=acode size=3 maxlength=10 value=<%=item.getAcode()%>> </td>
    <td><input type=text name=anom size=20 maxlength=80 value='<%=item.getAnom()%>'></td>
    <td><input type=text name=alien size=40 maxlength=255 value=<%=item.getAlien()%>> 
     <input type=hidden name=aimage size=5 maxlength=80 value=<%=item.getAimage()%>></td>
    <td><input type=text name=adroit size=3 maxlength=80 value=<%=item.getAdroit()%>></td>
    <td><input type=text name=adroitu size=3 maxlength=80 value=<%=item.getAdroitu()%>></td>
   
    <td><input type='submit' value='mod'   name='submit'>
        <input type='submit' value='copy'   name='submit'>

    <a href=Dispatcher?operation=supaction&code=<%=item.getAcode()%>  >
    <img border="0" src="/ddt/images/poub.gif"  alt="Suppression de ce menu" width="25" height="25"></a></FORM>
</td>
</tr><%     }   
} 
if (userinfo.getAgprofil()==0)
           { 
%>
<FORM name='form' ACTION=Dispatcher METHOD=POST><input type=hidden name=operation   value=addaction>
<td><input type=text name=mcode size=1 maxlength=3></td>
<td></td>    
<td><input type=text name=nom   maxlength=80></td>
<td><input type=text name=lien  size=40 maxlength=80></td>
 
<td><input type=text name=droit size=2  maxlength=3></td>
<td><input type=text name=droitu size=2  maxlength=3></td>
<td><input type='submit' value='Ajout' name='submit'></td>
</FORM>
</table><i>actions</i><% } %>
<%//_________________________________table custom___________________________________%>
<center><table border=0>
<FORM name='form' ACTION=Dispatcher METHOD=POST><input type=hidden name=operation   value=custom>
<tr bgcolor=c0c0c0><td>nb lignes liste</td><td><input type=text name=nblignes size=3 value=<%=userinfo.getNb_lignes()%>></td></tr>
<tr bgcolor=c0c0c0><td>nb lignes avoir</td><td><input type=text name=nbvoir   size=3 value=<%=userinfo.getNb_voir()%>></td></tr>
<tr><td colspan=2><input type='submit' value='custom' name='submit'></td></tr></table>
<!--------------------------------------->  
<%@  include file="include_bas_de_page" %>
<!---------------------------------------> 
</BODY>
</HTML>
