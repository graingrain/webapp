<HTML><META HTTP-EQUIV="Refresh"  Content="150";URL="Dispatcher?operation=browse">
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="javabeans.*" %>
 
<!---------- dropmenu -------><%@  include file="include_haut_de_page"%>   
  
<%//_________________________________table parametre___________________________________
if (userinfo.getAgprofil()==0)
{ %>
<center><table width=800><tr bgcolor=blue>
    <td>division</td><td>nom</td> <td>prenom</td><td>initiales</td><td>password</td><td>profilddt</td>
    </tr>
<% 
Annuaire annuaire = new Annuaire();
ArrayList liste_agent_profil  = (ArrayList) annuaire.getAllItemsProfil(userinfo ,request.getParameter("profil"));
for (int i=0; i<liste_agent_profil.size(); i++)
        {  ItemAnnuaire item = (ItemAnnuaire) liste_agent_profil.get(i);
            {
%>
<tr>
<!--FORM name='form' ACTION=Dispatcher METHOD=POST><input type=hidden name=operation size=8 maxlength=8 value=addparam>
-->  
<td><%=item.getDivision()%></td>

 
<td><a href='Dispatcher?operation=adminuser&codeagt=<%=item.getCodeagt()%>'><%=item.getNom()%></a></td>
<td><%=item.getPrenom()%></td> 
<td><%=item.getInitiales()%></td> 
<td><%=item.getProfilddt()%></td> 
<td><%=item.getAgpass()%></td> 
<td><%=item.getCodeagtwp()%></td> 
 
</tr> 
<%        }   }  //fin de la boucle    %>
<center> 
 </table> 
<%     }       // fin MENU %>
 
<!--------------------------------------->  
<%@  include file="include_bas_de_page" %>
<!---------------------------------------> 
</BODY>
</HTML>

