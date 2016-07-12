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
public class Action extends HttpServlet
{    
    public ArrayList getAllItems (java.sql.Connection conn) throws SQLException
  //_________________________________lecture MENU_____avec pool____________  
    {	ArrayList alist = new ArrayList();
        String qry;
        qry = " select * from menu, action where mcode = acodem order by mcode,anom";  
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(qry) ;
	while(rs.next())
        	{       ItemAction item = new ItemAction();
			item.setMcode(rs.getInt("mcode"));
			item.setMdroit(rs.getInt("mdroit"));
			item.setMnom(rs.getString("mnom"));
			item.setMlibelle(rs.getString("mlibelle"));
			item.setMimage(rs.getString("mimage"));
			
                        item.setAcode(rs.getInt("acode"));
			item.setAdroitu(rs.getInt("adroitu"));
                        item.setAdroit(rs.getInt("adroit"));
			item.setAnom(rs.getString("anom"));
			item.setAlibelle(rs.getString("alibelle"));
			item.setAimage(rs.getString("aimage"));
                        item.setAlien(rs.getString("alien"));
                        
        		alist.add(item);
		}
		rs.close();
		st.close();
        	return alist;
  }
// ------------------------------- fin methode getAllItems ------------------------------

 public String getAction (java.sql.Connection conn,int agprofil,int agcode) throws Exception
  //_________________________________lecture MENU_____avec pool____________  
    {	String action;
        action="";
        String qry;
        String argum;
        qry = " select * from menu, action where mcode = acodem order by mcode,anom";  
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(qry) ;
         
	while(rs.next())
        	{   if (rs.getInt("adroit")>=agprofil||(rs.getInt("adroitu")==agcode))
                    {
                        if (  rs.getString("alien").indexOf("&")==-1 ) argum =   "?anom="+rs.getString("alibelle").substring(0,rs.getString("alibelle").indexOf("  "))+"&avis=1";
                        else argum =   "&anom="+rs.getString("alibelle").substring(0,rs.getString("alibelle").indexOf("  "))+"&avis=1";
                        action=action+"\nmenu.addSubItem('n"+
			rs.getInt("mcode") +"',\n'" +
			rs.getString("anom").substring(0,rs.getString("anom").indexOf("  ")) +"',\n'" +
			rs.getString("alibelle").substring(0,rs.getString("alibelle").indexOf("  ")) +"',\n'" +
                        rs.getString("alien").substring(0,rs.getString("alien").indexOf(" "))+argum+"','');";
                        //System.out.println("alien"+rs.getString("alien").substring(0,rs.getString("alien").indexOf(" "))+"arg:"+rs.getString("alien").indexOf("&"));	
                    }
                }
		rs.close();
		st.close();
                // ajout &avis=1 pour l avoir aussi sur les nouvelles demandes
                // modif : mettre le nom d action en argument  , a modifier avec une hashtable               
        	return action;
  }
  
  
  public int AddAction(java.sql.Connection conn,String nom,String libelle, String image,int droit,String lien,int acodem ) throws Exception, SQLException
//__________________________________________________________________________________________________
{	int i=0; 
                Statement st = conn.createStatement();
                System.out.println("Addaction av select:" ); 
	        String select="insert into action (anom,alibelle,aimage,adroit,alien,acodem) values ('" + 	
                nom +"','" + libelle +"','" + image + "','" + droit + "','" + lien + "','" + acodem + "')";
                System.out.println("Addaction select:"+select); 
                i = st.executeUpdate(select);
         	st.close();
 	 
        return i;
  }   
  
   public int AddActionPerso(java.sql.Connection conn, HttpServletRequest request ,UserInfo userinfo, String query) throws Exception, SQLException
//__________________________________________________________________________________________________
{	int i=0; 
        int acodem=26;
        String lien="Dispatcher?operation=browse&action=0&avis=98&query=";
                Statement st = conn.createStatement();
                    
                String newquery=query;
                     while (query.indexOf(" ")!=-1) 
                     {
                           newquery=query.substring(0,query.indexOf(" "))+"%20"+query.substring(query.indexOf(" ")+1);
                           query=newquery;
                      }
                String select="insert into action (anom,alibelle,adroit,adroitu,alien,acodem) values ('" + 	
                request.getParameter("nom") +"','" + request.getParameter("nom") +"',-1,'" + userinfo.getCodeagt() + "','" + lien + query + "','" + acodem + "')";
                 
                i = st.executeUpdate(select);
         	st.close();
 	 
        return i;
  }   

    public int ModAction(java.sql.Connection conn,String nom,String libelle, String image,int droit,int droitu,String lien,int mcode,int acode ) throws Exception
//__________________________________________________________________________________________________
{	int i=0; 
        System.out.println("Modaction av select:" ); 
               Statement st = conn.createStatement();
	        String select="update action set (anom,alibelle,aimage,adroit,adroitu,alien,acodem) = ('" + 	
                nom +"','" + nom +"','" + image + "','" + droit + "','"+ droitu + "','" + lien + "','" + mcode + 
                "')   where acode = " + acode;
                System.out.println("Modaction select:"+select); 
                i = st.executeUpdate(select);
         	st.close();
 	 
        return i;
  }   
  
   public int CopieAction(java.sql.Connection conn,String nom,String libelle, String image,int droit,int droitu,String lien,int mcode,int acode ) throws Exception
//__________________________________________________________________________________________________
{	int i=0; 
                Statement st = conn.createStatement();
	        String select="insert into action  (anom,alibelle,aimage,adroit,adroitu,alien,acodem) values ('" + 	
                nom +"','" + libelle +"','" + image + "','" + droit + "','" + droitu + "','" + lien + "','" + mcode + 
                "')  " ;
                i = st.executeUpdate(select);
         	st.close();
 	 
        return i;
  }   
  
  public int SupAction(java.sql.Connection conn,int code )
//__________________________________________________________________________________________________
{	int i=0; 
        try
	 {      Statement st = conn.createStatement();
	        String select="delete from action where acode = " +   code ;
                i = st.executeUpdate(select);
         	st.close();
 	}
 	catch(SQLException e)
 	{  	System.out.println("<H2>"+"Erreur: " + e.getMessage() + "<BR>");
         	while((e = e.getNextException()) != null)
            	System.out.println(e.getMessage() + "<BR>");
 	}
        finally	{ 	}
        return i;
  }   
  
}
