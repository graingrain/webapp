package javabeans;
import javax.sql.*;
import java.sql.*;
import java.util.ArrayList;
//import common.DataBean;
import javax.servlet.*;
import javax.servlet.http.*;
public class Parametre extends HttpServlet {
    public ArrayList getAllItems(java.sql.Connection conn,UserInfo userinfo)
    {	ArrayList alist = new ArrayList();
        try {
            String cnx;
            String select="select * FROM parametre order by type,libelle,element;";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(select) ;
            while(rs.next())
            {       ItemParam item = new ItemParam();
                    item.setCode(rs.getInt("code"));
                    item.setType(rs.getInt("type"));
                    item.setAction(rs.getInt("action"));
                    item.setElement(rs.getString("element"));
                    item.setLibelle(rs.getString("libelle"));
                    alist.add(item);
            }
            // type= 1 : imprimante
            // type= 2 : materiel (sujet)
            // type= 3 : materiel (verbe)
            // type= 4
            // type= 5 : resolution
            // type= 6 : logiciel (verbe)
            // type= 7 : logiciel (complement)
            // type= 8 : machine
            // type= 9 :
            // type= 10: fournisseurs
            
            // chargement formatoce dans tableau type = 20
            //----------------------------------------------
            select="select * FROM formatsoce order by code;";
            rs = st.executeQuery(select) ;
            while(rs.next())
            {       ItemParam item = new ItemParam();
                    item.setCode(20);
                    item.setType(20);
                    item.setElement(rs.getString("code"));
                    item.setLibelle(rs.getString("code")+" du "+rs.getString("date"));
                    alist.add(item);
            }
            rs.close();
            st.close();
            
        }
        catch(SQLException e)
        {  	System.out.println("Erreur catalog getAllItem: " + e.getMessage() + "<BR>");
                while((e = e.getNextException()) != null)
                    System.out.println(e.getMessage() + "<BR>");
        }
        
        finally 	{ System.out.println("fin parametre"); 	 	}
        return alist;
        
        // -------------------------------------------------fin control
    }
    public int AddParametre(java.sql.Connection conn,HttpServletRequest request) throws Exception
    //__________________________________________________________________________________________________
    {	int i=0;
        Statement st = conn.createStatement();
        String select="insert into parametre (element,libelle,type,action) values ('" +
        request.getParameter("element") +"','" +
        request.getParameter("libelle") +"'," +
        Integer.parseInt(request.getParameter("type")) + "," +
        Integer.parseInt(request.getParameter("action"))+   ")";
        i = st.executeUpdate(select);
        st.close();
        return i;
    }
    public int ModParametre(java.sql.Connection conn,HttpServletRequest request) throws Exception
    //__________________________________________________________________________________________________
    {	int i=0;
        Statement st = conn.createStatement();
        String select="update parametre set  element ='"    +
        request.getParameter("element") + "' , libelle = '" +
        request.getParameter("libelle") + "' , action  = "  +
        Integer.parseInt(request.getParameter("action"))    +  "  , type = "   +
        Integer.parseInt(request.getParameter("type"))      +  "    where code = " +
        Integer.parseInt(request.getParameter("code"));
        i = st.executeUpdate(select);
        st.close();
        return i;
    }
    public int SupParametre(java.sql.Connection conn,int code )  throws Exception
    //__________________________________________________________________________________________________
    {	int i=0;
        Statement st = conn.createStatement();
        String select="delete from parametre where code = " +   code ;
        i = st.executeUpdate(select);
        st.close();
        return i;
    }
    
    
}
