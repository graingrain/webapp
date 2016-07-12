package javabeans;
import javax.sql.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
//import common.DataBean;
import javabeans.ItemDdt;
import javax.servlet.*;
import javax.servlet.http.*;
public class Annuaire extends HttpServlet
{   private static String cnx_ddt;
    private String lib_liste;  
    private String one_tache;
    private String cnx,type,qry,select,sel2;

  
   
  public ArrayList getAllAstreinteAdm(java.sql.Connection conn, UserInfo userinfo,HttpServletRequest request) throws SQLException,ClassNotFoundException
  //______________________________lecture des agents de la div de type adm     ____  
    {	ArrayList alist = new ArrayList();
	   select="select  code, nb,nb6,nb22,nom,commune,ville,pass from etab  where nb != 0"            ;
             
            System.out.println("select:"+select); 
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(select) ;
		while(rs.next())
        	{       ItemAstreinte item = new ItemAstreinte();
                        item.setNom(rs.getString("nom")+" "+
                        rs.getString("commune")+"-"+rs.getString("ville"));
 			item.setPrenom(rs.getString("pass"));
                        item.setObservation(rs.getString("code"));
                        item.setNb(rs.getInt("nb")); 
                        item.setNb6(rs.getInt("nb6")); 
                        item.setNb22(rs.getInt("nb22")); 
                       
			alist.add(item); 
                }
        rs.close();
        st.close();
 	 
	return alist;
 	}
  
  
  
   
// ------------------------------------------------- fin methode   ------------------------------
    
 public int ModParam(HttpServletRequest request,java.sql.Connection conn, UserInfo userinfo) throws SQLException,ClassNotFoundException
//__________________________________________________________________________________________________________________________________________________________________________
{	int i=0;
        int j=0;
        HttpSession session = request.getSession(true)        ;
        String select="";
        Class.forName("com.informix.jdbc.IfxDriver");
	 	
        String cnx = userinfo.getCnx_ddt();
                Statement st = conn.createStatement();
	        Date DateJour;
                DateJour=new Date();
                int codenom=0;
                    String titre=request.getParameter("titre");
                    while (titre.indexOf ("'",0) != -1)
                      titre=titre.substring(0,titre.indexOf ("'",0))+ "`"+titre.substring(titre.indexOf ("'",0)+1);
                    while (titre.indexOf ('"',0) != -1)
                      titre=titre.substring(0,titre.indexOf ('"',0))+ "`"+titre.substring(titre.indexOf ('"',0)+1); 
                    String ptitre=titre;
                    
                    titre=request.getParameter("message1");
                    while (titre.indexOf ("'",0) != -1)
                      titre=titre.substring(0,titre.indexOf ("'",0))+ "`"+titre.substring(titre.indexOf ("'",0)+1);
                    while (titre.indexOf ('"',0) != -1)
                      titre=titre.substring(0,titre.indexOf ('"',0))+ "`"+titre.substring(titre.indexOf ('"',0)+1); 
                    String pmessage1=titre;
                    
                    titre=request.getParameter("message2");
                    while (titre.indexOf ("'",0) != -1)
                      titre=titre.substring(0,titre.indexOf ("'",0))+ "`"+titre.substring(titre.indexOf ("'",0)+1);
                    while (titre.indexOf ('"',0) != -1)
                      titre=titre.substring(0,titre.indexOf ('"',0))+ "`"+titre.substring(titre.indexOf ('"',0)+1); 
                    String pmessage2=titre;
                    
                    titre=request.getParameter("message3");
                    while (titre.indexOf ("'",0) != -1)
                      titre=titre.substring(0,titre.indexOf ("'",0))+ "`"+titre.substring(titre.indexOf ("'",0)+1);
                    while (titre.indexOf ('"',0) != -1)
                      titre=titre.substring(0,titre.indexOf ('"',0))+ "`"+titre.substring(titre.indexOf ('"',0)+1); 
                    String pmessage3=titre;
                    
                    select="update param  set (titre,message1,message2,message3) = ('"  +
                    ptitre      +  "','"  +
                    pmessage1   +  "','"  +
                    pmessage2   +  "','"  +
                    pmessage3   +  "') where code = 'DEBAT'"          ;
                    
                    System.out.println("update:"+select);
                    i = st.executeUpdate(select);
                 
         	st.close();
 	 
	return i;
 	 
  }
  
    public int ModEtab(HttpServletRequest request,java.sql.Connection conn, UserInfo userinfo) throws SQLException,ClassNotFoundException
//__________________________________________________________________________________________________________________________________________________________________________
{	int i=0;
        int j=0;
        HttpSession session = request.getSession(true)        ;
        String select="";
        Class.forName("com.informix.jdbc.IfxDriver");
	 	
        String cnx = userinfo.getCnx_ddt();
                Statement st = conn.createStatement();
	        Date DateJour;
                DateJour=new Date();
                int codenom=0;
                
                 
                    select="update etab  set (nb,nb6,nb22) = ("  +
                     
                    request.getParameter("nb")   +  ","  +
                    request.getParameter("nb6")   +  ","  +
                    request.getParameter("nb22")     +  ") where code = '" + request.getParameter("codeetab")      +"'"           ;
                    
                    System.out.println("update:"+select);
                    i = st.executeUpdate(select);
                 
         	st.close();
 	 
	return i;
 	 
  }
  
  
  
   
  
  
  
}
