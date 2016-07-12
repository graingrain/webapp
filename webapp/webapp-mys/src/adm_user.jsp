<HTML><META HTTP-EQUIV="Refresh"  Content="150";URL="Dispatcher?operation=browse">
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="javabeans.*" %>
 
<!---------- dropmenu -------><%@  include file="include_haut_de_page"%>   
  
<%//_________________________________table parametre___________________________________
if (userinfo.getAgprofil()==0)
{ %>
<center><table width=800><tr >
    <td>nom</td>
    <td>prenom</td>
    <td>initiales</td>
    <td>division</td>
    <td>codeagtwp</td>
    <td>password</td>
    <td>profilddt</td>
    </tr>
<% 
ArrayList liste_agent_profil       = (ArrayList)session.getValue("one_user_profil"); 
for (int i=0; i<liste_agent_profil.size(); i++)
        {  ItemAnnuaire item = (ItemAnnuaire) liste_agent_profil.get(i);
            {
%>
<tr>
<FORM name='form' ACTION=Dispatcher METHOD=POST>
<input type=hidden name=operation size=8 maxlength=8 value=moduser>
<td><input type=text name=nom        size=20 maxlength=80 value='<%=item.getNom()%>'>
    <input type=hidden name=codeagt  size=20 maxlength=80 value='<%=item.getCodeagt()%>'></td>
<td><input type=text name=prenom     size=10 maxlength=80 value='<%=item.getPrenom()%>'></td> 
<td><input type=text name=initiales  size=4  maxlength=80 value='<%=item.getInitiales()%>'></td> 
<td><input type=text name=division   size=10 maxlength=80 value='<%=item.getDivision()%>'></td> 
<td><input type=text name=codeagtwp  size=5  maxlength=80 value='<%=item.getCodeagtwp()%>'></td> 
<td><input type=text name=agpass     size=4  maxlength=80 value='<%=item.getAgpass()%>'></td> 
<td><input type=text name=profilddt  size=1  maxlength=80 value='<%=item.getProfilddt()%>'></td> 
<td><input type='submit' value='mod'     name='submit'></td></tr></form> 
<%        }   }  //fin de la boucle    %>
<center> 
 </table> 
<%     }       // fin MENU %>
 
<!--------------------------------------->  
<%@  include file="include_bas_de_page" %>
<!---------------------------------------> 
</BODY>
</HTML>

