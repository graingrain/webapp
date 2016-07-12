package javabeans;
import uti2.*;
import javax.sql.*;
import java.lang.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
//import common.DataBean;
import javabeans.ItemDdt;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.NamingException;
public class Winpark extends HttpServlet
{    
    private static String cnx_ddt;
    private String lib_liste;  
    private String one_tache;
    private String cnx;
    public      void setLib_liste(String arg)   {  this.lib_liste = arg ;   }
    public      String getLib_liste()           {  return lib_liste;        }
    
    public ArrayList getAllItems (java.sql.Connection conn, UserInfo userinfo,String codeagtwp)  
  //_______________________________________________________lecture toutes ddt_____avec pool____________  
    {	ArrayList alist = new ArrayList();
        
        String select;
        try
        {
                int type_liste = 0;
                select="select * FROM winpark where (codeagtwp = " +  codeagtwp + ")   ";   
                Statement st = conn.createStatement();
         	ResultSet rs = st.executeQuery(select) ;
                if (userinfo.getDebug())System.out.println("WINPARK: select="+select);
                if (userinfo.getDebug())System.out.println("--------------------------");
		while(rs.next())
        	{       ItemWinpark item = new ItemWinpark();
			 
			item.setEtat(rs.getString("etat"));
			item.setGarantie(rs.getString("garantie"));
			item.setGenre(rs.getString("genre"));
			item.setModele(rs.getString("modele"));
                        item.setType(rs.getString("type"));
			item.setCodewp(rs.getInt("codewp"));
			alist.add(item);
                        System.out.println("type="+ rs.getString("type"));
		}
                rs.close();
		st.close();
        	return alist;
  }
 	catch(SQLException e)
 	{  	System.out.println("Erreur Info getAllItem: " + e.getMessage() + "<BR>");
         	while((e = e.getNextException()) != null)
            	System.out.println(e.getMessage() + "<BR>");
 	}
  	 
 	finally
	{
	return alist;
 	}
// -------------------------------------------------fin control
  }
  
 
  
}
