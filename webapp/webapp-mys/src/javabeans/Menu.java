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

public class Menu extends HttpServlet
{    
    private static final String jspPbGeneral = "/PbGeneral.jsp";
    public ArrayList getAllItems (java.sql.Connection conn) throws Exception
  //_________________________________lecture MENU_____avec pool____________  
    {	ArrayList alist = new ArrayList();
        String qry;
        qry = " select * from menu";  
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(qry) ;
        //System.out.println("getMenuAllItem-------------->");
	while(rs.next())
        	{       ItemMenu item = new ItemMenu();
			item.setMcode(rs.getInt("mcode"));
			item.setMdroit(rs.getInt("mdroit") );
			
			item.setMnom(rs.getString("mnom"));
			item.setMlibelle(rs.getString("mlibelle"));
			item.setMimage(rs.getString("mimage"));
                        item.setMlien(rs.getString("mlien"));
                        item.setMordre(rs.getInt("mordre"));
			 
        		alist.add(item);
		}
                //System.out.println("getMenuAllItem----fin---------->");
		rs.close();
		st.close();
        	return alist;
  }
  
   public String getMenu (java.sql.Connection conn ,int agprofil) throws Exception
  //_________________________________lecture MENU_____avec pool____________  
    {	String menu;
        menu="menu = new Menu();";
        String qry;
        qry = " select * from menu order by mordre"; 
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(qry) ;
        //System.out.println("getMenu-------------->");
	while(rs.next())
        	{   //if (rs.getInt("mdroit")>=agprofil)
                    {
                    //System.out.println("getMenu-------------->"+rs.getString("mnom"));
                        menu =menu+"\nmenu.addItem('n"+
			rs.getInt("mcode") +"',\n'" +
			rs.getString("mnom") +"',\n'" +
                        rs.getString("mlibelle") +"',\n" +
			rs.getString("mlien") +",null);";
		 }   }
                //System.out.println("getMenu---fin----------->");
		rs.close();
		st.close();
                //System.out.println("getMenu:"+menu);
        	return menu;
  }
// ------------------------------- fin methode getAllItems ------------------------------
public String getDropMenu (java.sql.Connection conn ,int agprofil,int agcode) throws Exception
  //_________________________________lecture MENU_____avec pool____________  
    {	String menu;
        menu="";
        String qry,qry2,argum;
        qry = " select * from menu order by mordre"; 
        Statement st  = conn.createStatement();
        Statement st2 = conn.createStatement();
        ResultSet rs  = st.executeQuery(qry) ;
        ResultSet rs2  = st2.executeQuery(qry) ;
        System.out.println("getMenu-------------->");
	while(rs.next())
        	{   //if (rs.getInt("mdroit")>=agprofil)
                    {
                    //System.out.println("getMenu-------------->"+rs.getString("mnom"));
                        int lg_menu = rs.getString("mnom").indexOf("  ") * 10;
                        menu =menu+"\naddMainItem('"+
			rs.getString("mlien").substring(0,rs.getString("mlien").indexOf("  ")) +"',\n'" +
			rs.getString("mnom") +"',\n" + lg_menu + ",'center','','',0,0,'u');";
                        menu=menu+"\ndefineSubmenuProperties(180,'left','left',0,0,'');";
                        
                        qry2="select * from action where acodem =" + rs.getInt("mcode") +" order by anom";
                        rs2 = st2.executeQuery(qry2) ;
                        while(rs2.next())
                        {
                            if (rs2.getInt("adroit")>=agprofil||(rs2.getInt("adroitu")==agcode))
                            {
                            if (  rs2.getString("alien").indexOf("&")==-1 ) 
                                argum =   "?anom="+rs2.getString("alibelle").substring(0,rs2.getString("alibelle").indexOf("  "))+"&avis=1";
                            else argum =   "&anom="+rs2.getString("alibelle").substring(0,rs2.getString("alibelle").indexOf("  "))+"&avis=1";

                            menu=menu+"\naddSubmenuItem('"+
                            rs2.getString("alien").substring(0,rs2.getString("alien").indexOf(" "))+argum+ "',\n'" +
                            rs2.getString("anom")+"','','');";
                            }
                        }
                    }
                }
        menu=menu+"}//********fin menu ddt***************";

        //System.out.println("getMenu---fin----------->");
		rs.close();
		st.close();
                rs2.close();
		st2.close();
                //System.out.println("getMenu:"+menu);
        	return menu;
  }
// ------------------------------- fin methode getAllItems ------------------------------

  
  
  public int AddMenu(java.sql.Connection conn,String nom,String libelle, String image, String lien,int droit, String ordre ) throws SQLException
//__________________________________________________________________________________________________
{	int i=0; 
                Statement st = conn.createStatement();
	        String select="insert into menu (mnom,mlibelle,mimage,mlien,mdroit,mordre) values ('" + 	
                nom +"','" + libelle +"','" + image + "','" + lien + "','" + droit + "'," + ordre + ")";
                System.out.println("addmenu:"+select);
                i = st.executeUpdate(select);
                st.close();
        return i;
}   

  public int ModMenu(java.sql.Connection conn,String nom,String libelle, String image,int droit,int ordre,String lien,int code ) throws Exception
//__________________________________________________________________________________________________
{	int i=0; 
                Statement st = conn.createStatement();
	        String select="update menu set (mnom,mlibelle,mimage,mdroit,mordre,mlien) = ('" + 	
                nom +"','" + libelle +"','" + image + "','" + droit + "','"+ ordre + "','" + lien + "')   where mcode = " + code;
                System.out.println("modmenu:"+select);
                i = st.executeUpdate(select);
         	st.close();
        return i;
  }   

  public int SupMenu(java.sql.Connection conn,int code )  throws Exception
//__________________________________________________________________________________________________
{	int i=0; 
                Statement st = conn.createStatement();
	        String select="delete from menu where mcode = " +   code ;
                i = st.executeUpdate(select);
           	st.close();
        return i;
  }   
  
}
