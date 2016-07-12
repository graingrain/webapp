package javabeans;
import javax.sql.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
//import common.DataBean;
import javabeans.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class Journal extends HttpServlet
{   public ArrayList getAllItems(UserInfo userinfo) throws SQLException,ClassNotFoundException
    {	ArrayList alist = new ArrayList();
	        String cnx;
		String select="select * from journal where codeddt="+ userinfo.getDdtcle() +" order by code";
                System.out.println("jNL:"+select);
                Class.forName("com.informix.jdbc.IfxDriver");
                cnx = userinfo.getCnx_ddt();
                Connection conn_ddt = null;
         	conn_ddt = DriverManager.getConnection(cnx);
         	Statement st = conn_ddt.createStatement();
         	ResultSet rs = st.executeQuery(select) ;
                while(rs.next())
        	{       ItemJournal item = new ItemJournal();
			System.out.println("jNL:"+rs.getString("date"));
                        item.setDate(rs.getString("date"));
			item.setNom(rs.getString("nom"));
			item.setAction(rs.getString("action"));
                        item.setP1(rs.getString("p1"));
                        item.setP2(rs.getString("p2"));
                        item.setP3(rs.getString("p3"));
                        item.setP4(rs.getString("p4"));
                        item.setP5(rs.getString("p5"));
			alist.add(item);
		}
		rs.close();
		st.close();
		conn_ddt.close();
        	return alist;
    }// -------------------------------------------------fin control
        
public int AddJrnl(UserInfo userinfo,String action,String p1,String p2,String p3,String p4,String p5)
//__________________________________________________________________________________________________________
{	int i=0; 
        try
	 {      System.out.println("Catalog Addjrnl :" );
                Class.forName("com.informix.jdbc.IfxDriver");
		String cnx = userinfo.getCnx_ddt();
                Connection conn_ddt = null;
         	conn_ddt = DriverManager.getConnection(cnx);
         	Statement st = conn_ddt.createStatement();
	        Date DateJour;
		DateJour=new Date();
                int month=DateJour.getMonth()+1;
                String vdatedeb;
                String vdatefin="";
                vdatedeb=DateJour.getDate()+"/"+month+"-"+DateJour.getHours()+":"+DateJour.getMinutes();
                String select="insert into journal (codeddt,date,action,nom,p1,p2,p3,p4,p5) values ('" + 
                userinfo.getDdtcle() + "','" +  vdatedeb + "','" +  
                action  + "','" + userinfo.getAgnom() + "','" + p1 + "','" + p2 + "','" + p3 + "','" + p4 + "','" + p5 + "')" ; 
                System.out.println("addjrnl:qry="+select);
		i = st.executeUpdate(select);
         	st.close();
		conn_ddt.close();
 	}
 	catch(SQLException e)
 	{  	System.out.println("<H2>"+"Erreur: " + e.getMessage() + "<BR>");
         	while((e = e.getNextException()) != null)
            	System.out.println(e.getMessage() + "<BR>");
 	}
  	catch(ClassNotFoundException e)
 	{	System.out.println("<H2>"+"ClassNotFoundException: " + e.getMessage() + "<BR>"); }
        finally	{ 	}
        return i;
  }  

        
  
  
 
  
}
