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
    <td>code</td>
    <td>type</td>
    <td>element</td>
    <td>libelle</td>
    <td></td>
    </tr>
<% 
 
for (int i=0; i<liste_param.size(); i++)
        {  ItemParam item = (ItemParam) liste_param.get(i);
            if (item.getType() == Integer.parseInt(request.getParameter("type")))
            {
%>
<tr><FORM name='form' ACTION=Dispatcher METHOD=POST><input type=hidden name=operation size=8 maxlength=8 value=addparam>
<td><input type=text name=code       size=3  maxlength=80 value=<%=item.getCode()%>></td>
<td><input type=text name=type       size=3 maxlength=80 value='<%=item.getType()%>'></td> 
<td><input type=text name=element    size=30 maxlength=80 value='<%=item.getElement()%>'></td> 
<td><input type=text name=libelle    size=30 maxlength=80 value='<%=item.getLibelle()%>'></td> 
 
<td><input type='submit' value='mod'     name='submit'>
    <input type='submit' value='copy'    name='submit'>
    <a href=Dispatcher?operation=supparam&code=<%=item.getCode()%>&type=<%=request.getParameter("type")%>  >
    <img border="0" src="/ddt/images/poub.gif"  alt="Suppression " width="25" height="25"></a>
</td>
</tr></form> 
<%        }   }  //fin de la boucle    %>
<center> 
<tr>
<td></td> <FORM name='form' 
ACTION=Dispatcher METHOD=POST><input type=hidden name=operation size=8 maxlength=8 value=addparam>   
 
<td><input type=hidden name=type size=10 maxlength=80 value=<%=request.getParameter("type")%>></td>
<td><input type=text name=element size=30 maxlength=80></td>
<td><input type=text name=libelle size=30 maxlength=80></td>
<td><input type='submit' value='Ajout menu' name='submit'></td>
</FORM>
</tr></table> 
<%     }       // fin MENU %>
 
<!--------------------------------------->  
<%@  include file="include_bas_de_page" %>
<!---------------------------------------> 
</BODY>
</HTML>

