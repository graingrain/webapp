<HTML><META HTTP-EQUIV="Refresh"  Content="150";URL="Dispatcher?operation=browse">
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="javabeans.*" %>
<!--------- dropmenu --------------------><%@  include file="include_haut_de_page"%>   
<table width=800>
<tr bgcolor=c0c0c0><td>date</td>
    <td>Action</td>
    <td>Nom</td>
    <td>P1</td>
    <td>P2</td>
    <td>P3</td>
    <td>P4</td>
    <td>P5</td>
</tr>
<% 
ArrayList liste_journal = (ArrayList)session.getValue("journal");
for (int i=0; i<liste_journal.size(); i++)
        {  ItemJournal item = (ItemJournal) liste_journal.get(i);
%>
<tr><td><%=item.getDate()%></td>
    <td><%=item.getAction()%></td>
    <td><%=item.getNom()%></td>
    <td><%=item.getP1()%></td>
    <td><%=item.getP2()%></td>
    <td><%=item.getP3()%></td>
    <td><%=item.getP4()%></td>
    <td><%=item.getP5()%></td>
</tr>
<%        }
%>
</table>
<!--------------------------------------->  
<%@  include file="include_bas_de_page" %>
<!---------------------------------------> 
<center><i>journal des evenements de la demande</i> 
</BODY>
</HTML>

