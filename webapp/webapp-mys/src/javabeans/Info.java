package javabeans;
import javax.sql.*;
import java.sql.*;
import java.util.ArrayList;
//import common.DataBean;
import javabeans.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class Info extends HttpServlet
{    
     
    public ArrayList getAllItems(UserInfo userinfo)
    {	ArrayList alist = new ArrayList();
	try
	{       String cnx;

		String select="select * from ddtinfo where ddtcle="+ userinfo.getDdtcle() +" order by cle";
            
                Class.forName("com.informix.jdbc.IfxDriver");
		//cnx="jdbc:informix-sqli://mac5:10535/ddt:informixserver=online_730;user=alain;password=tiga";
		//cnx="jdbc:informix-sqli://grainville:1517/ddt:informixserver=se724;user=alain;password=trek250";
		cnx = userinfo.getCnx_ddt();
                Connection conn_ddt = null;
         	conn_ddt = DriverManager.getConnection(cnx);
         	Statement st = conn_ddt.createStatement();
         	ResultSet rs = st.executeQuery(select) ;

                while(rs.next())
        	{       ItemInfo item = new ItemInfo();

			item.setDate(rs.getString("date"));

			item.setNom(rs.getString("nom"));

			item.setInfo(rs.getString("info"));

			alist.add(item);
		}
               
		rs.close();
		st.close();
		conn_ddt.close();
 	}
 	catch(SQLException e)
 	{  	System.out.println("Erreur Info getAllItem: " + e.getMessage() + "<BR>");
         	while((e = e.getNextException()) != null)
            	System.out.println(e.getMessage() + "<BR>");
 	}
  	catch(ClassNotFoundException e)
 	{	System.out.println("<H2>"+"ClassNotFoundException: " + e.getMessage() + "<BR>");
 	}
 	finally
	{
	return alist;
 	}
// -------------------------------------------------fin control
  }
  
 
  
}
