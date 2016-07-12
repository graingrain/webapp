<HTML><META HTTP-EQUIV="Refresh"  Content="150";URL="Dispatcher?operation=browse">
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="javabeans.*" %>
<center>
<!---------------------------------><%@  include file="include_init_form.jsp" %>
<!-------------------------------><%@  include file="titreddt.jsp" %>  
<table>
<%//_________________________________table menu___________________________________%>
<FORM name='form' ACTION=Dispatcher METHOD=POST><input type=hidden name=operation size=8 maxlength=8 value=addmenu>
<center><table><tr bgcolor=c0c0c0>
    <td>mcode</td>
    <td>mnom</td>
    <td>mlibelle</td>
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
<tr><td><%=item.getMcode()%></td>
    <td><%=item.getMnom()%></td>
    <td><%=item.getMlibelle()%></td>
    <td><%=item.getMimage()%></td>
    <td><%=item.getMlien()%></td>
    <td><%=item.getMdroit()%></td>
    <td><%=item.getMordre()%></td>
    <td><a href=Dispatcher?operation=supmenu&code=<%=item.getMcode()%>  >
    <img border="0" src="/ddt/images/poubelle.gif"  alt="Suppression de ce menu" width="25" height="25"></a></td>
</tr>
<%        }         %>
<td></td>    
<td><input type=text name=nom size=15 maxlength=80></td>
<td><input type=text name=libelle size=15 maxlength=80></td>
<td><input type=text name=image size=15 maxlength=80></td>
<td><input type=text name=lien size=15 maxlength=80></td>
<td><input type=text name=droit size=3 maxlength=3></td>
<td><input type=text name=ordre size=3 maxlength=3></td>
<td><input type='submit' value='Ajout menu' name='submit'></td>
</FORM>
</table><i>menus</i>

<%//_________________________________table action___________________________________%>

<center><table><tr bgcolor=c0c0c0>
    <td width=10>mcode</td>
    <td>mnom</td>
    <td width=10>code</td>
    <td>anom</td>
    <td>alibelle</td>
    <td>alien</td>
    <td>aimage</td>
    <td width=10>adroit</td>
     
</tr>
<% 
ArrayList liste_action = (ArrayList)session.getValue("liste_action");
for (int i=0; i<liste_action.size(); i++)
        {  ItemAction item = (ItemAction) liste_action.get(i);
%>
<tr><FORM name='form' ACTION=Dispatcher METHOD=POST><input type=hidden name=operation   value=modaction>
    <td><input type=text name=mcode size=3 maxlength=80 value=<%=item.getMcode()%>></td>
    <td><input type=text name=mnom size=15 maxlength=80 value=<%=item.getMnom()%>></td>
    <td><input type=hiden name=acode size=3 maxlength=10 value=<%=item.getAcode()%>></td>
    <td><input type=text name=anom size=15 maxlength=80 value='<%=item.getAnom()%>'></td>
    <td><input type=text name=alibelle size=15 maxlength=80 value='<%=item.getAlibelle()%>'></td>
    <td><input type=text name=alien size=35 maxlength=80 value=<%=item.getAlien()%>></td>
    <td><input type=text name=aimage size=5 maxlength=80 value=<%=item.getAimage()%>></td>
    <td><input type=text name=adroit size=3 maxlength=80 value=<%=item.getAdroit()%>></td>
   
    <td><input type='submit' value='mod' name='submit'>
        <input type='submit' value='copy' name='submit'>
</FORM>
    <a href=Dispatcher?operation=supaction&code=<%=item.getAcode()%>  >
    <img border="0" src="/ddt/images/poubelle.gif"  alt="Suppression de ce menu" width="25" height="25"></a></td>
</tr><%     }     %>
<FORM name='form' ACTION=Dispatcher METHOD=POST><input type=hidden name=operation   value=addaction>
<td><input type=text name=mcode size=1 maxlength=3></td>
<td></td> <td></td>   
<td><input type=text name=nom size=15 maxlength=80></td>
<td><input type=text name=libelle size=15 maxlength=80></td>
<td><input type=text name=lien size=15 maxlength=80></td>
<td><input type=text name=image size=15 maxlength=80></td>
<td><input type=text name=droit size=3 maxlength=3></td>
<td><input type='submit' value='Ajout action' name='submit'></td>
</FORM>
</table><i>actions</i>

</BODY>
</HTML>

