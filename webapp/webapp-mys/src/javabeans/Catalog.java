package javabeans;
import uti2.*;
import javax.sql.*;
import java.lang.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
//import common.DataBean;
//import javabeans.ItemDdt;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.NamingException;
public class Catalog extends HttpServlet {
    private static String cnx_ddt; 
    private String lib_liste;
    private String one_tache;
    private String cnx;
    public      void setLib_liste(String arg)   {  this.lib_liste = arg ;   }
    public      String getLib_liste()           {  return lib_liste;        }
    
    //------------------webapp-Dns-----------------------------------------------------------------------------------
    public ArrayList getAllCheckPoint(java.sql.Connection conn, UserInfo userinfo,HttpServletRequest request,int code)
    {	ArrayList alist = new ArrayList();
        String qry="";
        String orderby="order by cp_node";
        if (request.getParameter("tri")!= null)    	orderby="order by "+request.getParameter("tri");
        try
        { if (code == 0) qry="select * from checkpoint   " + orderby;
          else           qry="select * from checkpoint   "  + orderby;
        
        /*
         * SELECT 
CONVERT(SUBSTRING_INDEX(adresse_ip, '.', 1), UNSIGNED INTEGER) as nb1,
CONVERT(SUBSTRING_INDEX(SUBSTRING_INDEX(adresse_ip, '.', 2), '.', -1), UNSIGNED INTEGER) as nb2,
CONVERT(SUBSTRING_INDEX(SUBSTRING_INDEX(adresse_ip, '.', -2), '.', 1), UNSIGNED INTEGER) as nb3,
CONVERT(SUBSTRING_INDEX(SUBSTRING_INDEX(adresse_ip, '.', -2), '.', -1), UNSIGNED INTEGER) as nb4,
adresse_ip
from table1
ORDER BY nb1,nb2,nb3,nb4 
*/
        if (( request.getParameter("tri")!= null) &&  request.getParameter("tri").equals("cp_nat"))       
      	 qry = "select  CONVERT(SUBSTRING_INDEX(cp_nat, '.', 1), UNSIGNED INTEGER) as nb1,  "
               		+ "      CONVERT(SUBSTRING_INDEX(SUBSTRING_INDEX(cp_nat, '.', 2), '.', -1), UNSIGNED INTEGER) as nb2,     "
               		+ "      CONVERT(SUBSTRING_INDEX(SUBSTRING_INDEX(cp_nat, '.', -2), '.', 1), UNSIGNED INTEGER) as nb3,     "
               		+ "      CONVERT(SUBSTRING_INDEX(SUBSTRING_INDEX(cp_nat, '.', -2), '.', -1), UNSIGNED INTEGER) as nb4,     "
               		+ "      cp_node,cp_ip,cp_nat         from checkpoint        ORDER BY nb1,nb2,nb3,nb4 ; ";
        if (( request.getParameter("tri")!= null) &&  request.getParameter("tri").equals("cp_ip"))   
       	 qry = "select  CONVERT(SUBSTRING_INDEX(cp_ip, '.', 1), UNSIGNED INTEGER) as nb1,  "
              		+ "      CONVERT(SUBSTRING_INDEX(SUBSTRING_INDEX(cp_ip, '.', 2), '.', -1), UNSIGNED INTEGER) as nb2,     "
              		+ "      CONVERT(SUBSTRING_INDEX(SUBSTRING_INDEX(cp_ip, '.', -2), '.', 1), UNSIGNED INTEGER) as nb3,     "
              		+ "      CONVERT(SUBSTRING_INDEX(SUBSTRING_INDEX(cp_ip, '.', -2), '.', -1), UNSIGNED INTEGER) as nb4,     "
              		+ "      cp_node,cp_ip,cp_nat         from checkpoint        ORDER BY nb1,nb2,nb3,nb4 ; ";
              		
          Statement st = conn.createStatement();
          ResultSet rs = st.executeQuery(qry) ;
          userinfo.setError_message("");
          System.out.println("get alldns:" + qry); 
          
          while(rs.next())
          {     ItemDns item = new ItemDns();
                item=fgetOneCp(rs,item);
                String nat=item.getCp_nat();
                String  ip=item.getCp_ip();
                // calcul nb dns externe---------------------------------------------------------------------------------
                {int nb=0;
                 String qry2="SELECT COUNT(*) nb FROM dns WHERE ip='"+nat+"'";
                 Statement st2 = conn.createStatement() ;
                 ResultSet rs2 = st2.executeQuery(qry2) ;
                 while(rs2.next()){ nb = rs2.getInt("nb"); }
                 rs2.close();
                 st2.close();
                 item.setCp_nat_nb(nb);
                }
                // calcul nb dns interne comores---------------------------------------------------------------------------------
                {int nb=0;
                 String qry2="SELECT COUNT(*) nb FROM dns WHERE dns='comores' and ip='"+ip+"'";
                 Statement st2 = conn.createStatement() ;
                 ResultSet rs2 = st2.executeQuery(qry2) ;
                 while(rs2.next()){ nb = rs2.getInt("nb"); }
                 rs2.close();
                 st2.close();
                 item.setCp_ip_nb(nb);
                }
                // --------------------------------------------------------------------------------------------------
             // calcul nb dns interne etab---------------------------------------------------------------------------------
                {int nb=0;
                 String qry2="SELECT COUNT(*) nb FROM dns WHERE  dns='etab' and  ip='"+ip+"'";
                 Statement st2 = conn.createStatement() ;
                 ResultSet rs2 = st2.executeQuery(qry2) ;
                 while(rs2.next()){ nb = rs2.getInt("nb"); }
                 rs2.close();
                 st2.close();
                 item.setCp_ipe_nb(nb);
                }
                // --------------------------------------------------------------------------------------------------
             
                alist.add(item);
          }
          System.out.println("nb machine:"+alist.size());
          rs.close();
          st.close();
          //conn.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        finally                         {  	           }
        return alist;
    }
   
    
    
    public ArrayList getOneCheckPoint(java.sql.Connection conn, UserInfo userinfo,HttpServletRequest request)
    {	ArrayList alist = new ArrayList();
        String qry="";
        String orderby="order by cp_node";
       // if (request.getParameter("tri")!= null)    	orderby="order by "+request.getParameter("tri");
        try
        { qry="select * from checkpoint  where cp_node ='"   +request.getParameter("node") +"' OR cp_nat ='" + request.getParameter("ip3") + 
        		"' OR cp_nat ='" + request.getParameter("ip2") +"'; " ;
          Statement st = conn.createStatement();
          ResultSet rs = st.executeQuery(qry) ;
          userinfo.setError_message("");
          System.out.println("get alldns:" + qry);
          
          while(rs.next())
          {     ItemDns item = new ItemDns();
                item=fgetOneCp(rs,item);
                alist.add(item);
          }
          System.out.println("nb machine:"+alist.size());
          rs.close();
          st.close();
          //conn.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        finally                         {  	           }
        return alist;
    }
    public ArrayList getAllDns(java.sql.Connection conn, UserInfo userinfo,HttpServletRequest request,int code)
    {	ArrayList alist = new ArrayList();
        String qry="";
        String orderby="order by name";
        //if (request.getParameter("tri")!= null)    	orderby="order by "+request.getParameter("tri");
        try
        { if (code == 0) qry="select * from dns   " + orderby;
          else           qry="select * from dns   "  + orderby;
          Statement st = conn.createStatement();
          ResultSet rs = st.executeQuery(qry) ;
          userinfo.setError_message("");
          System.out.println("get alldns:" + qry);
          
          while(rs.next())
          {     ItemDns item = new ItemDns();
                item=fgetOneDns(rs,item);
                // calcul nb reference checkpoint
                //-------------------------------
                int nb=0;
                String qry2="select   count(distinct node)nb , name   from dns where name='" + 
                rs.getString("name") + "'  group by name";
                
                Statement st2 = conn.createStatement() ;
                ResultSet rs2 = st2.executeQuery(qry2) ;
                while(rs2.next()){ nb = rs2.getInt("nb"); 
                System.out.println("name:"+rs2.getString("name")+ " nb:"+nb);
                
                }
                item.setDn_node_nb(nb);
                //------------------
                rs2.close();
                st2.close();
                
                 
                alist.add(item);
          }
          System.out.println("nb dns:"+alist.size());
          rs.close();
          st.close();
          //conn.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        finally                         {  	           }
        return alist;
    }
    public ArrayList getAllEon(java.sql.Connection conn, UserInfo userinfo,HttpServletRequest request,int code)
    {	ArrayList alist = new ArrayList();
        String qry="";
        String orderby="order by name";
        /*
        SELECT 
        CONVERT(SUBSTRING_INDEX(adresse_ip, '.', 1), UNSIGNED INTEGER) as nb1,
        CONVERT(SUBSTRING_INDEX(SUBSTRING_INDEX(adresse_ip, '.', 2), '.', -1), UNSIGNED INTEGER) as nb2,
        CONVERT(SUBSTRING_INDEX(SUBSTRING_INDEX(adresse_ip, '.', -2), '.', 1), UNSIGNED INTEGER) as nb3,
        CONVERT(SUBSTRING_INDEX(SUBSTRING_INDEX(adresse_ip, '.', -2), '.', -1), UNSIGNED INTEGER) as nb4,
        adresse_ip
        from table1
        ORDER BY nb1,nb2,nb3,nb4 
        */
        //if (request.getParameter("tri")!= null)    	orderby="order by "+request.getParameter("tri");
        try
        { if (code == 0) qry="select * from allip   " + orderby;
          else           qry="select * from allip   " + orderby;
        if ((request.getParameter("tri")!= null) &&  (request.getParameter("tri").equals("ip")))
        	qry = "select  CONVERT(SUBSTRING_INDEX(ip, '.', 1), UNSIGNED INTEGER) as nb1,  "
          		+ "      CONVERT(SUBSTRING_INDEX(SUBSTRING_INDEX(ip, '.', 2), '.', -1), UNSIGNED INTEGER) as nb2,     "
          		+ "      CONVERT(SUBSTRING_INDEX(SUBSTRING_INDEX(ip, '.', -2), '.', 1), UNSIGNED INTEGER) as nb3,     "
          		+ "      CONVERT(SUBSTRING_INDEX(SUBSTRING_INDEX(ip, '.', -2), '.', -1), UNSIGNED INTEGER) as nb4,     "
          		+ "      ip,name,comores,mayotte,etab,ibis,eon         from allip        ORDER BY nb1,nb2,nb3,nb4 ; ";
        if ((request.getParameter("tri")!= null) &&  (request.getParameter("tri").equals("node"))) 	
        	qry="select * from allip  order by name";
        
          Statement st = conn.createStatement();
          ResultSet rs = st.executeQuery(qry) ;
          userinfo.setError_message("");
          System.out.println("-------- get allip:" + qry);
          
          while(rs.next())
          {     ItemDns item = new ItemDns();
                item=fgetOneAllip(rs,item);
                alist.add(item);
          }
          System.out.println("nb dns:"+alist.size());
          rs.close();
          st.close();
          //conn.close(); 
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        finally                         {  	           }
        return alist;
    }
  //------------------webapp-Dns-----------------------------------------------------------------------------------
    public ArrayList getOneDns(java.sql.Connection conn, UserInfo userinfo,HttpServletRequest request)
    {	ArrayList alist = new ArrayList();
        String qry="";
        String orderby="order by cp_node";
        if (request.getParameter("tri")!= null)    	orderby="order by "+request.getParameter("tri");
        try
        {  
        	 
        
        if (request.getParameter("node").equals("NOCP"))
        {	
        	qry= "SELECT * FROM          dns   WHERE   ip='" + request.getParameter("ip1")  +
        	"' OR ip= '" + request.getParameter("ip2")  +
        	"' OR ip= '" + request.getParameter("ip3")  +
        	"' order by name ";
         
        System.out.println("get onedns NOCP:" + qry);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(qry) ;
        
          userinfo.setError_message("");
          
          
          while(rs.next())
          {     ItemDns item = new ItemDns();
                //item=fgetOneCp(rs,item);
                item=fgetOneDns(rs,item);
                 
                alist.add(item);
          }
        
          System.out.println("nb machine:"+alist.size());
          rs.close();
          st.close();
        }
        else
        {
        	qry= "SELECT * FROM          dns ,checkpoint WHERE cp_node= '"  
        	        +request.getParameter("node") 
        	        +"' AND (ip=cp_ip    OR ip=cp_nat) order by name ";	
        	 System.out.println("get onedns CHECKPOINT:" + qry);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(qry) ;
             
               userinfo.setError_message("");
               
               
               while(rs.next())
               {     ItemDns item = new ItemDns();
                     item=fgetOneCp(rs,item);
                     item=fgetOneDns(rs,item);
                      
                     alist.add(item);
               }
             
               System.out.println("nb machine:"+alist.size());
               rs.close();
               st.close();
        	
        }
        
          //conn.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        finally                         {  	           }
        return alist;
    }
    public ArrayList getAllMachine(java.sql.Connection conn, UserInfo userinfo,int code)
    {	ArrayList alist = new ArrayList();
        String qry="";
        try
        { if (code == 0) qry="select * from machine order by ma_zn_code,ma_sigle  ";
          else           qry="select * from machine   where ma_code =  " + code ;
          Statement st = conn.createStatement();
          ResultSet rs = st.executeQuery(qry) ;
          userinfo.setError_message("");
          System.out.println("get allmachine:" + qry);
          
          while(rs.next())
          {     ItemApplication item = new ItemApplication();
                item=fgetOneMachine(rs,item);
                //System.out.println("item apres fget:"+item.getMa_code());
                // calcul integrite
                //------------------
                int nb=0;
                String qry2="select count(*)nb from machine,bdd where ma_code = bd_ma_code and ma_code = " +
                rs.getInt("ma_code");
                Statement st2 = conn.createStatement() ;
                ResultSet rs2 = st2.executeQuery(qry2) ;
                while(rs2.next()){ nb = rs2.getInt("nb"); }
                qry2="select count(*)nb from machine,portail where ma_code = pt_ma_code and ma_code = "+
                rs.getInt("ma_code");
                rs2 = st2.executeQuery(qry2) ;
                while(rs2.next()){   nb = nb + rs2.getInt("nb"); }
                
                item.setMa_nb_bdd(nb);
                //------------------
                rs2.close();
                st2.close();
                alist.add(item);
          }
          System.out.println("nb machine:"+alist.size());
          rs.close();
          st.close();
          //conn.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        finally                         {  	           }
        return alist;
    }
    public void AddMachine(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request)
    {	String qry="";
        try
        
        {   System.out.println(" AddMachine: " );
            Statement st = conn.createStatement();
            if (request.getParameter("submit").equals("Ajouter"))
                qry="insert into machine (ma_libelle,ma_sigle,ma_adresse,ma_zn_code,ma_etat,ma_etat_date) values ('" +
                request.getParameter("libelle") + "','" +
                request.getParameter("sigle") + "','" +
                request.getParameter("adresse") + "','" +
                Integer.parseInt(request.getParameter("zone")) + "','','attente ctrl')" ;
            else
                qry="update machine set " +
                "ma_libelle='" + request.getParameter("libelle") + "'" + "," +
                "ma_sigle  ='" + request.getParameter("sigle") + "'" + "," +
                "ma_adresse='" + request.getParameter("adresse") + "'" + "," +
                "ma_zn_code='" + Integer.parseInt(request.getParameter("zone")) + "'" + "," +
                "ma_etat   ='" + "" + "'" + "," +
                "ma_etat_date='" + "attente ctrl" + "' " +
                "where ma_code = " + request.getParameter("code")  ;
            System.out.println("qry: "+qry);
            st.executeUpdate(qry);
            st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        finally                         {  	}
        
    }
    public void SupMachine(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request)
    {	String qry="";
        try
        {   System.out.println(" SupMachine: " );
            Class.forName("com.informix.jdbc.IfxDriver");
            Statement st = conn.createStatement();
            qry="delete  from machine where ma_code = " + Integer.parseInt(request.getParameter("code")) + ";" ;
            st.executeUpdate(qry);
            st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        finally                         {  	}
        
    }
    public ArrayList getAllBdd(java.sql.Connection conn, UserInfo userinfo, int code)
    {	ArrayList alist = new ArrayList();
        String qry="";
        try {      if (code==0)
            qry="select * from bdd,machine where ma_code = bd_ma_code order by ma_zn_code,ma_sigle ";
        else
            qry="select * from bdd,machine where ma_code = bd_ma_code and bd_code = " + code;
        Class.forName("com.informix.jdbc.IfxDriver");
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(qry) ;
        System.out.println("ad bd:"+code);
        while(rs.next())
        {       ItemApplication item = new ItemApplication();
                item=fgetOneBdd(rs,item);
                item=fgetOneMachine(rs,item);
                // calcul integrite---------------------------------------------------------------------------------
                {int nb=0;
                 String qry2="select count(*)nb from bdd,instancewa where bd_code = iw_bd_code and bd_code = " +
                 rs.getInt("bd_code");
                 Statement st2 = conn.createStatement() ;
                 ResultSet rs2 = st2.executeQuery(qry2) ;
                 while(rs2.next()){ nb = rs2.getInt("nb"); }
                 rs2.close();
                 st2.close();
                 item.setNb_joint(nb);
                }
                // --------------------------------------------------------------------------------------------------
                System.out.println("ad bd:  ok");
                alist.add(item);
        }
        if (code==-1) {
            ItemApplication item = new ItemApplication();
            alist.add(item);
        }
        rs.close();
        st.close();
        
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        
        finally                         {  	           }
        return alist;
    }
    public void AddBdd(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request) {
        String qry="";
        try
        {   System.out.println(" AddBdd: " );
            Class.forName("com.informix.jdbc.IfxDriver");
            Statement st = conn.createStatement();
            if (request.getParameter("submit").equals("Ajouter"))
                qry="insert into bdd (bd_repertoire,bd_type,bd_port,bd_ma_code,bd_port_etat,bd_port_etat_date) values ('" +
                request.getParameter("repertoire") + "','" +
                request.getParameter("type") + "'," +
                Integer.parseInt(request.getParameter("port")) + "," +
                Integer.parseInt(request.getParameter("ma_code")) + ",'','attente ctrl')" ;
            else
                qry="update bdd set " +
                "bd_repertoire='" + request.getParameter("repertoire") + "'" + "," +
                "bd_type  ='" + request.getParameter("type") + "'" + "," +
                "bd_port='" + request.getParameter("port") + "'" + "," +
                "bd_ma_code='" + Integer.parseInt(request.getParameter("ma_code")) + "'" + "," +
                "bd_port_etat='" + "" + "'" + "," +
                "bd_port_etat_date='" + "attente ctrl" + "' " +
                "where bd_code = " + request.getParameter("code")  ;
            st.executeUpdate(qry);
            st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry); }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        finally                         {	  	}
    }
    public void SupBdd(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request)
    {   String qry="";
        try	 {      Class.forName("com.informix.jdbc.IfxDriver");
        Statement st = conn.createStatement();
        qry="delete  from bdd where bd_code = " + Integer.parseInt(request.getParameter("code")) + ";" ;
        int ret=st.executeUpdate(qry);
        if (ret==0) userinfo.setError_message("Erreur: impossible de supprimer l'enregistrement");
        st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry); }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        finally                         {	  	}
    }
    public void SupLog(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request)
    {   String qry="";
        try	 {
            Statement st = conn.createStatement();
            qry="delete  from log where lg_code = " + Integer.parseInt(request.getParameter("code")) + ";" ;
            int ret=st.executeUpdate(qry);
            if (ret==0) userinfo.setError_message("Erreur: impossible de supprimer l'enregistrement");
            st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry); }
        finally                         {	  	}
    }
    public void SupZone(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request)
    {   String qry="";
        try	 {
            Statement st = conn.createStatement();
            qry="delete  from zone where zn_code = " + Integer.parseInt(request.getParameter("code")) + ";" ;
            int ret=st.executeUpdate(qry);
            if (ret==0) userinfo.setError_message("Erreur: impossible de supprimer l'enregistrement");
            st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry); }
        finally                         {	  	}
    }
    public void SupTheme(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request)
    {   String qry="";
        try	 {
            Statement st = conn.createStatement();
            System.out.println("sup theme:"+request.getParameter("code"));
            qry="delete  from theme where th_code = " + Integer.parseInt(request.getParameter("code")) + ";" ;
            System.out.println("qry:"+qry);
            int ret=st.executeUpdate(qry);
            if (ret==0) userinfo.setError_message("Erreur: impossible de supprimer l'enregistrement");
            st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry); }
        finally                         {	  	}
    }
    
    public void SupAstreinte(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request)
    {   String qry="";
        try	 {      Class.forName("com.informix.jdbc.IfxDriver");
        Statement st = conn.createStatement();
        System.out.println("sup :");
        System.out.println("sup :"+request.getParameter("as_code"));
        qry="delete  from astreinte where as_code = " + Integer.parseInt(request.getParameter("as_code")) + ";" ;
        int ret=st.executeUpdate(qry);
        if (ret==0) userinfo.setError_message("Erreur: impossible de supprimer l'enregistrement");
        st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry); }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        finally                         {	  	}
    }
    public ArrayList getAllPortail(java.sql.Connection conn, UserInfo userinfo, int code)
    {     ArrayList alist = new ArrayList();
          String qry="";
          try     { if (code == 0)
              qry="select * from portail,machine where ma_code = pt_ma_code order by ma_zn_code ,ma_sigle  ";
          else
              qry="select * from portail,machine where ma_code = pt_ma_code and pt_code = " + code;
          Class.forName("com.informix.jdbc.IfxDriver");
          Statement st = conn.createStatement();
          ResultSet rs = st.executeQuery(qry) ;
          userinfo.setError_message("");
          while(rs.next()) {       //System.out.println("portail:"+    rs.getString("pt_repertoire"));
              ItemApplication item = new ItemApplication();
              item=fgetOnePortail(rs,item);
              item=fgetOneMachine(rs,item);
              // calcul integrite---------------------------------------------------------------------------------
              //------------------
              int nb=0;
              String qry2="select count(*)nb from portail,portail_instancewa where pi_pt_code = pt_code and pt_code = " +
              rs.getInt("pt_code");
              Statement st2 = conn.createStatement() ;
              ResultSet rs2 = st2.executeQuery(qry2) ;
              while(rs2.next()){ nb = rs2.getInt("nb"); }
              item.setNb_joint(nb);
              // --------------------------------------------------------------------------------------------------
              rs2.close();
              st2.close();
              alist.add(item);
          }
          if (code==-1) {
              ItemApplication item = new ItemApplication();
              alist.add(item);
          }
          
          rs.close();
          st.close();
          }
          catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
          e.printStackTrace();            }
          catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
          finally                         {  	           }
          return alist;
    }
    public void AddPortail(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request) {
        String qry="";
        try {   userinfo.setError_message("");
        Class.forName("com.informix.jdbc.IfxDriver");
        Statement st = conn.createStatement();
        if (request.getParameter("submit").equals("Ajouter"))
            qry="insert into portail (pt_repertoire,pt_version,pt_type,pt_port,pt_ma_code,pt_port_etat,pt_port_etat_date) values ('" +
            request.getParameter("repertoire") + "','" +
            request.getParameter("version") + "','" +
            request.getParameter("type") + "'," +
            Integer.parseInt(request.getParameter("port")) + "," +
            Integer.parseInt(request.getParameter("ma_code")) + ",'','attente ctrl')" ;
        else
            qry="update portail set " +
            "pt_repertoire='" + request.getParameter("repertoire") + "'" + "," +
            "pt_version   ='" + request.getParameter("version") + "'" + "," +
            "pt_type      ='" + request.getParameter("type") + "'" + "," +
            "pt_port      ='" + request.getParameter("port") + "'" + "," +
            "pt_ma_code   ='" + Integer.parseInt(request.getParameter("ma_code")) + "'" + "," +
            "pt_port_etat ='" + "" + "'" + "," +
            "pt_port_etat_date='" + "attente ctrl" + "' " +
            "where pt_code = " + request.getParameter("code")  ;
        st.executeUpdate(qry);
        st.close();
        }
        catch(SQLException e)	        {
            System.out.println("pg:"+e.getMessage());
            userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
            e.printStackTrace();            }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        finally                         {	  	}
    }
    public void SupPortail(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request)
    {      String qry="";
           try {    Class.forName("com.informix.jdbc.IfxDriver");
           Statement st = conn.createStatement();
           qry="delete  from portail where pt_code = " + Integer.parseInt(request.getParameter("code")) + ";" ;
           int ret=st.executeUpdate(qry);
           if (ret==0) userinfo.setError_message("Erreur: impossible de supprimer l'enregistrement");
           st.close();
           }
           catch(SQLException e)           { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry); e.printStackTrace();   }
           catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
           finally                         {	  	}
    }
    public ArrayList getAllLog(java.sql.Connection conn, UserInfo userinfo)
    {     ArrayList alist = new ArrayList();
          String qry="";
          try {   System.out.println(" getAllLog: " );
          qry="select * from log order by Lg_code";
          Class.forName("com.informix.jdbc.IfxDriver");
          Statement st = conn.createStatement();
          ResultSet rs = st.executeQuery(qry) ;
          while(rs.next())
          {     ItemLog item = new ItemLog();
                item.setLg_code(rs.getInt("lg_code"));
                item.setLg_type(rs.getString("lg_type"));
                item.setLg_type_code(rs.getString("lg_type_code"));
                item.setLg_etat(rs.getString("lg_etat"));
                item.setLg_etat_date(rs.getString("lg_etat_date"));
                item.setLg_libelle(rs.getString("lg_libelle"));
                alist.add(item);
          }
          rs.close();
          st.close();
          }
          catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
          e.printStackTrace();            }
          catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
          
          finally                         { 	  	}
          return alist;
    }
    public ArrayList getAllLogApache(java.sql.Connection conn, UserInfo userinfo,HttpServletRequest request)
    {     ArrayList alist = new ArrayList();
          String qry="";
          try {   System.out.println(" getAllLog: " );
          
          qry="select * from logapache order by  machine, webapp, port";
          if (request.getParameter("sort")!=null&&request.getParameter("sort").equals("webapp"))
              qry="select * from logapache order by   webapp, port, machine";
          if (request.getParameter("sort")!=null&&request.getParameter("sort").equals("port"))
              qry="select * from logapache order by     port,webapp, machine";
          
          Class.forName("com.informix.jdbc.IfxDriver");
          Statement st = conn.createStatement();
          ResultSet rs = st.executeQuery(qry) ;
          while(rs.next())
          {     ItemApplication item = new ItemApplication();
                item.setLa_code(rs.getInt("code"));
                item.setLa_port(rs.getString("port"));
                item.setLa_machine(rs.getString("machine"));
                item.setLa_webapp(rs.getString("webapp"));
                item.setLa_service(rs.getString("service"));
                item.setLa_etat(rs.getString("etat"));
                item.setLa_etat_date(rs.getString("date"));
                alist.add(item);
          }
          rs.close();
          st.close();
          }
          catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
          e.printStackTrace();            }
          catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
          
          finally                         {  	}
          return alist;
    }
    public ArrayList getAllApplication(java.sql.Connection conn, UserInfo userinfo , HttpServletRequest request, int code)
    {	ArrayList alist = new ArrayList();
        String qry="";
        String qry3="select * from zone";
        try
        {   Date DateJour=new Date();
            int month=DateJour.getMonth()+1;
            String vdatedeb=DateJour.getDate()+"/"+month+"-"+DateJour.getHours()+":"+DateJour.getMinutes()+":"+DateJour.getSeconds();
            String filtre="";
            if(request.getParameter("filtre")!=null)
                filtre=" and ap_controle = 1 and ap_ouvert = 0 ";
            System.out.println(" getAllApplication: "+vdatedeb );
            if (code==0)
                qry="select application.*, portail.*,  portail_instancewa.*, instancewa.* , domainewa.*, webapp.*, machine.*, theme.* " +
                " from portail_instancewa,portail,instancewa, domainewa,webapp,machine,application LEFT JOIN  theme ON th_code = ap_th_code " +
                "where pi_code = ap_pi_code and pt_code = ap_pt_code  and iw_code = pi_iw_code and ma_code = pt_ma_code " +
                "   and iw_do_code = do_code and do_wa_code = wa_code " +
                filtre + " order by ma_zn_code,ap_libelle   "
                ;
            else
                qry="select application.*, portail.*,  portail_instancewa.*, instancewa.* , domainewa.*, webapp.*, machine.*, theme.* " +
                " from portail_instancewa,portail,instancewa, domainewa,webapp,machine,application LEFT JOIN  theme ON th_code = ap_th_code " +
                "where pi_code = ap_pi_code and pt_code = ap_pt_code  and iw_code = pi_iw_code and ma_code = pt_ma_code " +
                "   and iw_do_code = do_code and do_wa_code = wa_code "  +
                "and ap_code = " +          code   + " order by ma_zn_code,ap_libelle   ";
            Class.forName("com.informix.jdbc.IfxDriver");
            System.out.println(" getAllApplication:st "+qry );
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(qry) ;
            Statement st3 = conn.createStatement() ;
            ResultSet rs3 = st3.executeQuery(qry3) ;
            while(rs.next())
            {      //System.out.println(" j en ai un"   );
                   
                   ItemApplication item = new ItemApplication();
                   item=fgetOneApplication(rs,item);
                   item=fgetOneTheme(rs,item);
                   item=fgetOnePortailIW(rs,item);
                   item=fgetOnePortail(rs,item);
                   item=fgetOneMachine(rs,item);
                   item=fgetOneDomaineWA(rs,item);
                   item=fgetOneInstanceWA(rs,item);
                   item=fgetOneWebapp(rs,item);
                   
                   qry3="select ma_sigle,ma_zn_code from machine where ma_code = " + rs.getInt("iw_ma_code");
                   rs3 = st3.executeQuery(qry3) ;
                   while(rs3.next())
                   {   item.setIw_sigle(rs3.getString("ma_sigle"));
                       item.setIw_zone(rs3.getInt("ma_zn_code")); }
                   qry3="select * from bdd,machine where bd_ma_code = ma_code and  bd_code = " + rs.getInt("iw_bd_code");
                   rs3 = st3.executeQuery(qry3) ;
                   while(rs3.next())  { item=fgetOneBdd(rs3,item);  }
                   alist.add(item);
            }
            if (code==-1) {
                // initialisation d un objet application vide
                // -------------------------------------------
                ItemApplication item = new ItemApplication();
                alist.add(item);
                System.out.println("init item vide");
            }
            rs.close();
            st.close();
            rs3.close();
            st3.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        
        
        finally                         {  	}
        return alist;
    }
    public void AddApplication(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request)
    {	 String qry="";
         try
         {   System.out.println(" AddApplication: " );
             Class.forName("com.informix.jdbc.IfxDriver");
             Statement st = conn.createStatement();
             String wpt=request.getParameter("pt_code").substring(0,request.getParameter("pt_code").indexOf(","));
             String wpi=request.getParameter("pt_code").substring(request.getParameter("pt_code").indexOf(",")+1,request.getParameter("pt_code").length());
             String insertStatement = "";
             
             if (request.getParameter("submit").equals("Ajouter")) {
                 insertStatement = "insert into application  "+
                 "(ap_description,ap_message,ap_visible,ap_controle,ap_libelle,ap_date_ouverture,ap_date_fermeture,ap_murl,ap_url,ap_curl_texte,ap_pt_code,ap_pi_code,ap_etat,ap_etat_date,ap_th_code,ap_ouvert,ap_ouvert_lib) "+
                 " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                 PreparedStatement ps = conn.prepareStatement(insertStatement);
                 ps.setString(1,request.getParameter("description"));
                 ps.setString(2,request.getParameter("message"));
                 ps.setString(3,request.getParameter("visible"));
                 ps.setString(4,request.getParameter("controle"));
                 ps.setString(5,request.getParameter("libelle"));
                 ps.setString(6,request.getParameter("date_ouverture"));
                 ps.setString(7,request.getParameter("date_fermeture"));
                 ps.setString(8,request.getParameter("murl"));
                 ps.setString(9,request.getParameter("url"));
                 ps.setString(10,request.getParameter("curl_chaine"));
                 ps.setString(11,wpt);
                 ps.setString(12,wpi);
                 ps.setString(13," ");
                 ps.setString(14,"attente ctrl");
                 ps.setString(15,request.getParameter("th_code"));
                 ps.setString(16,request.getParameter("etat"));
                 ps.setString(17,request.getParameter("etat_message"));
                 System.out.println("ajout appli ouvert:"+request.getParameter("etat"));
                 ps.executeUpdate();
                 ps.close();
                 st.close();
             }
             else {
            	 String qryupdate= "update  application  set "+
                 " ap_description ='" + request.getParameter("description") + 
                 "',ap_message ='"    + request.getParameter("message")     +
                 "',ap_visible ='"     + request.getParameter("visible")     +
                 "',ap_controle ='"    + request.getParameter("controle")    +
                 "',ap_libelle ='"    +  request.getParameter("libelle")    +
                 "',ap_date_ouverture ='" + request.getParameter("date_ouverture") +
                 "',ap_date_fermeture ='" + request.getParameter("date_fermeture") +
                 "',ap_murl ='" + request.getParameter("murl") +
                 "',ap_url ='" + request.getParameter("url") +
                 "',ap_curl_texte ='" + request.getParameter("curl_chaine") +
                 "',ap_pt_code =" + wpt +
                 ",ap_pi_code =" + wpi +
                 ",ap_etat = ' ' " +
                 ",ap_etat_date ='" +  "attente ctrl" +
                 "',ap_th_code =" + request.getParameter("th_code") +
                 ",ap_ouvert =" +  request.getParameter("etat") +
                 ",ap_ouvert_lib ='"+ request.getParameter("etat_message") +
                 "'   where ap_code = " + request.getParameter("code");
                 //PreparedStatement ps = conn.prepareStatement(insertStatement);
                 
                 System.out.println("modif appli ouvert qry U:"+request.getParameter("etat")+qryupdate);
                 
                 st.executeUpdate(qryupdate);
                // ps.close();
                 st.close();
             }
         }
         catch(SQLException e)	         { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
         e.printStackTrace();            }
         catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
         finally                         {	  	}
    }
    public void SupApplication(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request)
    {	 String qry="";
         try
         {      Class.forName("com.informix.jdbc.IfxDriver");
                Statement st = conn.createStatement();
                qry="delete  from application where ap_code = " + Integer.parseInt(request.getParameter("code")) + ";" ;
                int ret=st.executeUpdate(qry);
                if (ret==0) userinfo.setError_message("Erreur: impossible de supprimer l'enregistrement");
                st.close();
         }
         catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
         e.printStackTrace();            }
         catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
         
         finally                         {	  	}
    }
    public void ModVApplication(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request)
    {	 String qry="";
         try
         {      Class.forName("com.informix.jdbc.IfxDriver");
                Statement st = conn.createStatement();
                if (Integer.parseInt(request.getParameter("val"))==1)
                    qry="update   application set (ap_visible) = (0) where ap_code = " + Integer.parseInt(request.getParameter("code")) + ";" ;
                else
                    qry="update   application set (ap_visible) = (1) where ap_code = " + Integer.parseInt(request.getParameter("code")) + ";" ;
                
                int ret=st.executeUpdate(qry);
                if (ret==0) userinfo.setError_message("Erreur: impossible de supprimer l'enregistrement");
                st.close();
         }
         catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
         e.printStackTrace();            }
         catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
         
         finally                         {	  	}
    }
    public void ModCApplication(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request)
    {	 String qry="";
         try
         {      Class.forName("com.informix.jdbc.IfxDriver");
                Statement st = conn.createStatement();
                if (Integer.parseInt(request.getParameter("val"))==1)
                    qry="update   application set (ap_controle,ap_etat,ap_etat_date) = (0,' ','non controle') where ap_code = " + Integer.parseInt(request.getParameter("code")) + ";" ;
                else
                    qry="update   application set (ap_controle,ap_etat,ap_etat_date) = (1,' ','attente ctl') where ap_code = " + Integer.parseInt(request.getParameter("code")) + ";" ;
                
                int ret=st.executeUpdate(qry);
                if (ret==0) userinfo.setError_message("Erreur: impossible de supprimer l'enregistrement");
                st.close();
         }
         catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
         e.printStackTrace();            }
         catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
         
         finally                         {	  	}
    }
    public void ModOApplication(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request)
    {	 String qry="";
         try
         {      Class.forName("com.informix.jdbc.IfxDriver");
                Statement st = conn.createStatement();
                
                qry="update   application set (ap_ouvert,ap_ouvert_lib) = ('"+
                request.getParameter("etat") +"','" + request.getParameter("etat_message") +"') where ap_code = " + Integer.parseInt(request.getParameter("code")) + ";" ;
               
                
                qry="update   application set " +
                "ap_ouvert='"    + request.getParameter("etat") + "'" + "," +
                "ap_ouvert_lib='"+ request.getParameter("etat_message")  + "' " +
                "where ap_code = " +  Integer.parseInt(request.getParameter("code")) + ";" ;
                       
                 
                int ret=st.executeUpdate(qry);
                Date  datej = new Date();
                String ouvert="ouvert";
                if (request.getParameter("etat").equals("1")) ouvert="ferme";
                if (request.getParameter("etat").equals("2")) ouvert="maintenance";
                
                qry="insert into applilog (al_date,al_nom,al_etat,al_libetat,al_ap_code) values ('" +
                datej + "','" +
                userinfo.getNom() + "','" +
                ouvert + "','" +
                request.getParameter("etat_message") + "'," +
                Integer.parseInt(request.getParameter("code")) +")" ;
                
                System.out.println("qry :"+qry);
                
                
                st.executeUpdate(qry);
                st.close();
         }
         catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
         e.printStackTrace();            }
         catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
         
         finally                         {	  	}
    }
    public void AddAstreinte(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request)
    {	 String qry="";
         try
         {      Class.forName("com.informix.jdbc.IfxDriver");
                Statement st = conn.createStatement();
                
                if (request.getParameter("mail")!= null && request.getParameter("mail").equals("mail")) {
                    qry="insert into astreinte (as_nom,as_mail,as_gsm,as_ap_code) values ('" +
                    userinfo.getNom() + "','" +
                    userinfo.getMail() + "',''," +
                    Integer.parseInt(request.getParameter("code")) +")" ;
                    st.executeUpdate(qry);
                }
                if (request.getParameter("gsm")!=null && request.getParameter("gsm").equals("gsm")) {
                    qry="insert into astreinte (as_nom,as_mail,as_gsm,as_ap_code) values ('" +
                    userinfo.getNom() + "','','" +
                    userinfo.getTelephone() + "'," +
                    Integer.parseInt(request.getParameter("code")) +")" ;
                    st.executeUpdate(qry);
                }
                
                st.close();
         }
         catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
         e.printStackTrace();            }
         catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
         
         finally                         {	  	}
    }
    public ArrayList getAllWebapp(java.sql.Connection conn, UserInfo userinfo, int code)
    {	ArrayList alist = new ArrayList();
        String qry="";
        try
        {      if (code == 0) qry="select * from webapp,machine where ma_code = wa_ma_code order by ma_zn_code,ma_sigle ";
               else           qry="select * from webapp,machine where ma_code = wa_ma_code  and wa_code = " + code ;
               Class.forName("com.informix.jdbc.IfxDriver");
               System.out.println(" getAllWebapp:st "+qry );
               Statement st = conn.createStatement();
               ResultSet rs = st.executeQuery(qry) ;
               while(rs.next())
               {     ItemApplication  item = new ItemApplication();
                     item=fgetOneWebapp(rs,item);
                     item=fgetOneMachine(rs,item);
                     // calcul integrite---------------------------------------------------------------------------------
                     //------------------
                     int nb=0;
                     String qry2="select count(*)nb from webapp,domainewa where do_wa_code = wa_code and wa_code = " +
                     rs.getInt("wa_code");
                     Statement st2 = conn.createStatement() ;
                     ResultSet rs2 = st2.executeQuery(qry2) ;
                     while(rs2.next()){ nb = rs2.getInt("nb"); }
                     item.setNb_joint(nb);
                     // --------------------------------------------------------------------------------------------------
                     rs2.close();
                     st2.close();
                     alist.add(item);
               }
               if (code==-1) {
                   ItemApplication item = new ItemApplication();
                   alist.add(item);
               }
               rs.close();
               st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        
        finally                         { 	}
        return alist;
    }
    public void AddWebapp(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request) {
        String qry="";
        try
        {   System.out.println(" AddWebapp: " );
            Class.forName("com.informix.jdbc.IfxDriver");
            Statement st = conn.createStatement();
            if (request.getParameter("submit").equals("Ajouter"))
                qry="insert into webapp (wa_repertoire,wa_type,wa_version,wa_ma_code) values ('" +
                request.getParameter("repertoire") + "','" +
                request.getParameter("type") + "','" +
                request.getParameter("version") + "'," +
                Integer.parseInt(request.getParameter("ma_code")) +")" ;
            else
                qry="update webapp set " +
                "wa_repertoire='" + request.getParameter("repertoire") + "'" + "," +
                "wa_type      ='" + request.getParameter("type") + "'" + "," +
                "wa_version   ='" + request.getParameter("version") + "'" + "," +
                "wa_ma_code   ='" + Integer.parseInt(request.getParameter("ma_code")) + "'"   +
                " where wa_code = " + request.getParameter("code")  ;
            st.executeUpdate(qry);
            st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        
        
        finally                         {	  	}
    }
    public void SupWebapp(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request) {
        String qry="";
        try
        {      Class.forName("com.informix.jdbc.IfxDriver");
               Statement st = conn.createStatement();
               qry="delete  from webapp where wa_code = " + Integer.parseInt(request.getParameter("code")) + ";" ;
               int ret=st.executeUpdate(qry);
               if (ret==0) userinfo.setError_message("Erreur: impossible de supprimer l'enregistrement");
               st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        
        finally                         {	  	}
    }
    public ArrayList getAllDomaineWA(java.sql.Connection conn, UserInfo userinfo, int code)
    {	ArrayList alist = new ArrayList();
        String qry="";
        try
        {       if (code==0)
                    qry="select * from domainewa,webapp,machine where do_wa_code = wa_code and wa_ma_code = ma_code order by ma_zn_code";
                else
                    qry="select * from domainewa,webapp,machine where do_wa_code = wa_code and wa_ma_code = ma_code and do_code = " +
                    code;
                
                Class.forName("com.informix.jdbc.IfxDriver");
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(qry) ;
                
                while(rs.next())
                {     ItemApplication item = new ItemApplication();
                      item=fgetOneDomaineWA(rs,item);
                      item=fgetOneWebapp(rs,item);
                      item=fgetOneMachine(rs,item);
                      // calcul integrite---------------------------------------------------------------------------------
                      //------------------
                      int nb=0;
                      String qry2="select count(*)nb from domainewa,instancewa where iw_do_code = do_code and do_code = " +
                      rs.getInt("do_code");
                      Statement st2 = conn.createStatement() ;
                      ResultSet rs2 = st2.executeQuery(qry2) ;
                      while(rs2.next()){ nb = rs2.getInt("nb"); }
                      item.setNb_joint(nb);
                      // --------------------------------------------------------------------------------------------------
                      rs2.close();
                      st2.close();
                      alist.add(item);
                }
                rs.close();
                st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        
        finally                         {  	}
        return alist;
    }
    public void AddDomaineWA(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request) {
        String qry="";
        try
        {   System.out.println(" AddWebapp: " );
            Class.forName("com.informix.jdbc.IfxDriver");
            Statement st = conn.createStatement();
            if (request.getParameter("submit").equals("Ajouter"))
                qry="insert into domainewa (do_nom,do_libelle,do_user,do_passwd,do_portc,do_wa_code,do_ma_code) values ('" +
                request.getParameter("nom") + "','" +
                request.getParameter("libelle") + "','" +
                request.getParameter("user") + "','" +
                request.getParameter("passwd") + "'," +
                request.getParameter("portc") + "," +
                request.getParameter("wa_code") +")" ;
            else
            {
            int 	  p=0;
             
            int xdo_wa_code=0;
            int xdo_ma_code=0;
            StringTokenizer tokenizer = new StringTokenizer(request.getParameter("wa_code"), ",");
    		while (tokenizer.hasMoreTokens()) {
    			p++;
    			String token = tokenizer.nextToken();
    			System.out.println("" + p + ":" + token);
    			if (p == 1) 		xdo_wa_code = Integer.parseInt(token);
    			if (p == 2) 		xdo_ma_code = Integer.parseInt(token);
    			 
    		}
                 qry="update   domainewa set  " +
                		"do_nom ='" +   request.getParameter("nom") +
                		"' ,do_libelle ='" + request.getParameter("libelle") +
                		"',do_user ='" +  request.getParameter("user") +
                		"',do_passwd ='" +   request.getParameter("passwd") + 
                		"',do_portc =" +  request.getParameter("portc") + 
                		",do_wa_code =" +  xdo_wa_code +
                		",do_ma_code =" +  xdo_ma_code +                     
                		"  where do_code = " + request.getParameter("code")  ;
            
            }
            
            System.out.println("qry:"+qry);
            st.executeUpdate(qry);
            st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        
        
        finally                         {	  	}
    }
    public void SupDomaineWA(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request) {
        String qry="";
        try
        {      Class.forName("com.informix.jdbc.IfxDriver");
               Statement st = conn.createStatement();
               qry="delete  from domainewa where do_code = " + Integer.parseInt(request.getParameter("code")) + ";" ;
               int ret=st.executeUpdate(qry);
               if (ret==0) userinfo.setError_message("Erreur: impossible de supprimer l'enregistrement");
               st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        
        finally                         {	  	}
    }
    public ArrayList getAllInstanceWA(java.sql.Connection conn, UserInfo userinfo,int code)
    {	ArrayList alist = new ArrayList();
        String qry="";
        try
        {       if (code==0) qry="select * from instancewa , domainewa,webapp, machine   LEFT JOIN bdd ON iw_bd_code = bd_code where   wa_ma_code = ma_code and do_wa_code = wa_code and do_code = iw_do_code order by ma_zn_code,iw_port1  ";
                else         qry="select * from instancewa , domainewa,webapp, machine   LEFT JOIN bdd ON iw_bd_code = bd_code where   wa_ma_code = ma_code and do_wa_code = wa_code and do_code = iw_do_code and iw_code = " + code;
                Class.forName("com.informix.jdbc.IfxDriver");
                System.out.println(" getallIW: " );
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(qry) ;
                while(rs.next())
                {     ItemApplication item = new ItemApplication();
                      
                      item=fgetOneInstanceWA(rs,item);
                      item=fgetOneDomaineWA(rs,item);
                      item=fgetOneBdd(rs,item);
                      item=fgetOneMachine(rs,item);
                      // calcul integrite---------------------------------------------------------------------------------
                      //------------------
                      int nb=0;
                      String qry2="select count(*)nb from instancewa,portail_instancewa where pi_iw_code = iw_code and iw_code = " +
                      rs.getInt("iw_code");
                      
                      Statement st2 = conn.createStatement() ;
                      ResultSet rs2 = st2.executeQuery(qry2) ;
                      while(rs2.next()){ nb = rs2.getInt("nb"); }
                      item.setNb_joint(nb);
                      // --------------------------------------------------------------------------------------------------
                      rs2.close();
                      st2.close();
                      alist.add(item);
                }
                if (code==-1) {
                    ItemApplication item = new ItemApplication();
                    alist.add(item);
                }
                rs.close();
                st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        
        finally                         {  	}
        return alist;
    }
    public void AddInstanceWA(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request) {
        String qry="";
        int p=0;
        int xiw_port2=0;
        int xiw_bd_code=0;
        int xiw_bdma_code=0;
        int xiw_do_code=0;
        int xiw_ma_code=0;
        StringTokenizer tokenizer = new StringTokenizer(request.getParameter("bd_port"), ",");
		while (tokenizer.hasMoreTokens()) {
			p++;
			String token = tokenizer.nextToken();
			System.out.println("" + p + ":" + token);
			if (p == 1) 		xiw_port2 = Integer.parseInt(token);
			if (p == 2) 		xiw_bd_code = Integer.parseInt(token);
			if (p == 3) 		xiw_bdma_code = Integer.parseInt(token);
		}
        p=0;
		tokenizer = new StringTokenizer(request.getParameter("do_code"), ",");
		while (tokenizer.hasMoreTokens()) {
				p++;
				String token = tokenizer.nextToken();
				System.out.println("" + p + ":" + token);
				if (p == 1) 		xiw_do_code = Integer.parseInt(token);
				if (p == 2) 		xiw_ma_code = Integer.parseInt(token);
			}
        
        try
        {   Statement st = conn.createStatement();
            if (request.getParameter("submit").equals("Ajouter"))
                qry="insert into instancewa (iw_nom,iw_libelle,iw_shell,iw_port1,iw_port2,iw_bd_code,iw_bdma_code,iw_do_code,iw_ma_code) values ('" +
                request.getParameter("nom") + "','" +
                request.getParameter("libelle") + "','" +
                request.getParameter("shell") + "'," +
                request.getParameter("port1") + "," +
                request.getParameter("bd_port") + "," +
                request.getParameter("do_code") +")" ;
            else
                qry="update instancewa set "  
                + " iw_nom ='" +   request.getParameter("nom")  
                + "', iw_libelle = '"    + request.getParameter("libelle")  
                + "', iw_shell ='" +  request.getParameter("shell")
                + "', iw_port1 = " +  request.getParameter("port1") 
                + ",  iw_port2 = " +  xiw_port2
                + ",  iw_bd_code = " +  xiw_bd_code
                + ",  iw_bdma_code = " +  xiw_bdma_code
                + ",  iw_do_code = " + xiw_do_code 
                + ",  iw_ma_code = " + xiw_ma_code 
                +"  where iw_code = " + request.getParameter("code") ;
            System.out.println("qry:"+qry);
            st.executeUpdate(qry);
            st.close();
        }
        
        // request: bd_port contient bd_port ET bd_code et bd_ma_code
        // request: do_code contient do_code ET ma_code
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        
        
        finally                         {	  	}
    }
    public void SupInstanceWA(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request) {
        String qry="";
        try
        {      Class.forName("com.informix.jdbc.IfxDriver");
               Statement st = conn.createStatement();
               qry="delete  from instancewa where iw_code = " + Integer.parseInt(request.getParameter("code")) + ";" ;
               int ret=st.executeUpdate(qry);
               if (ret==0) userinfo.setError_message("Erreur: impossible de supprimer l'enregistrement");
               st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        
        finally                         {	  	}
    }
    public ArrayList getAllPortailIW(java.sql.Connection conn, UserInfo userinfo, int code)
    {	ArrayList alist = new ArrayList();
        String qry="";
        try
        {   System.out.println("code:"+code);
            if (code == 0) qry="select * from portail_instancewa,portail,instancewa,domainewa, machine " +
            " where pi_iw_code = iw_code and pt_ma_code = ma_code and " +
            "do_code = iw_do_code and " +
            "pi_pt_code = pt_code order by ma_zn_code, pt_ma_code ";
            else
                qry="select * from portail_instancewa,portail,instancewa,domainewa, machine " +
                " where pi_iw_code = iw_code and pt_ma_code = ma_code and " +
                "do_code = iw_do_code and " +
                "pi_pt_code = pt_code   and pi_code = " + code;
            
            Class.forName("com.informix.jdbc.IfxDriver");
            
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(qry) ;
            while(rs.next())
            { ItemApplication item = new ItemApplication();
              item=fgetOnePortailIW(rs,item);
              item=fgetOnePortail(rs,item);
              item=fgetOneInstanceWA(rs,item);
              item=fgetOneMachine(rs,item);
              
              String qry3="select ma_sigle,ma_zn_code from machine where ma_code = " + rs.getInt("pt_ma_code");
              Statement st3 = conn.createStatement() ;
              ResultSet rs3 = st3.executeQuery(qry3) ;
              while(rs3.next()){
                  item.setPt_sigle(rs3.getString("ma_sigle"));
                  item.setPt_zone(rs3.getInt("ma_zn_code"));
              }
              qry3="select ma_sigle from machine where ma_code = " + rs.getInt("iw_ma_code");
              rs3 = st3.executeQuery(qry3) ;  while(rs3.next()){ item.setIw_sigle(rs3.getString("ma_sigle")); }
              //qry3="select ma_sigle from machine where ma_code = " + rs.getInt("iw_bdma_code");
              //rs3 = st3.executeQuery(qry3) ;  while(rs3.next()){ item.setBd_sigle(rs3.getString("ma_sigle")); }
              // bdd
              qry3="select * from bdd,machine where bd_ma_code = ma_code and  bd_code = " + rs.getInt("iw_bd_code");
              rs3 = st3.executeQuery(qry3) ;
              while(rs3.next()){
                  item.setBd_sigle(rs3.getString("ma_sigle"));
                  item.setBd_zone(rs3.getInt("ma_zn_code"));
                  item.setBd_type(rs3.getString("bd_type"));
                  item.setBd_repertoire(rs3.getString("bd_repertoire"));
                  item.setBd_port(rs3.getInt("bd_port"));
                  item.setBd_port_etat(rs3.getString("bd_port_etat"));
                  item.setBd_port_etat_date(rs3.getString("bd_port_etat_date"));
                  item.setBd_ma_code(rs3.getInt("bd_ma_code"));
                  item.setBd_code(rs3.getInt("bd_code"));
              }
              
              
              rs3.close();
              rs3.close();
              st3.close();
              // calcul integrite---------------------------------------------------------------------------------
              //------------------
              int nb=0;
              String qry2="select count(*)nb from  portail_instancewa,application where ap_pi_code = pi_code and pi_code = " +
              rs.getInt("pi_code");
              Statement st2 = conn.createStatement() ;
              ResultSet rs2 = st2.executeQuery(qry2) ;
              while(rs2.next()){ nb = rs2.getInt("nb"); }
              item.setNb_joint(nb);
              // --------------------------------------------------------------------------------------------------
              rs2.close();
              st2.close();
              alist.add(item);
            }
            if (code==-1) {
                ItemApplication item = new ItemApplication();
                alist.add(item);
            }
            rs.close();
            st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        
        finally                         {  	}
        return alist;
    }
    public void AddPortailIW(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request) {
        String qry="";
        try
        {   System.out.println(" AddWebapp: " );
            Class.forName("com.informix.jdbc.IfxDriver");
            Statement st = conn.createStatement();
            if (request.getParameter("submit").equals("Ajouter"))
                qry="insert into portail_instancewa (pi_pt_code,pi_iw_code,pi_port) values (" +
                request.getParameter("pt_code") + "," +
                //request.getParameter("iw_code") + "," +
                request.getParameter("port") +  ")" ;
            else
                qry="update portail_instancewa set (pi_pt_code,pi_iw_code,pi_port) = (" +
                request.getParameter("pt_code") + "," +
                //request.getParameter("iw_code") + "," +
                request.getParameter("port") +  ")  where pi_code = " + request.getParameter("code") ;
            System.out.println("qry PIW:"+qry);
            st.executeUpdate(qry);
            st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        
        
        finally                         {	  	}
    }
    public void SupPortailIW(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request) {
        String qry="";
        try
        {      Class.forName("com.informix.jdbc.IfxDriver");
               Statement st = conn.createStatement();
               qry="delete  from portail_instancewa where pi_code = " + Integer.parseInt(request.getParameter("code")) + ";" ;
               int ret=st.executeUpdate(qry);
               if (ret==0) userinfo.setError_message("Erreur: impossible de supprimer l'enregistrement");
               st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        
        finally                         {	  	}
    }
    public ArrayList getAllTheme(java.sql.Connection conn, UserInfo userinfo  , int code)
    {	ArrayList alist = new ArrayList();
        String qry="";
        try { if (code==0)    qry="select * from theme ;"        ;
        else                  qry="select * from theme " +     "where th_code = " +          code;
        //Class.forName("com.informix.jdbc.IfxDriver");
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(qry) ;
        System.out.println("theme qry :"+qry);
        while(rs.next())
        {   ItemApplication item = new ItemApplication();
            item=fgetOneTheme(rs,item);
            alist.add(item);
        }
        rs.close();
        st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        finally                         {   	}
        return alist;
    }
    public ArrayList getAllJournal(java.sql.Connection conn, UserInfo userinfo , HttpServletRequest request, int code)
    {	ArrayList alist = new ArrayList();
        String qry="";
        System.out.println("journal: " );
        try
        {   if (code==0)    qry="select * from applilog order by al_code ;"        ;
            else            qry="select * from applilog " +     " where al_ap_code = " +          code;
            Class.forName("com.informix.jdbc.IfxDriver");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(qry) ;
            while(rs.next())
            {   ItemApplication item = new ItemApplication();
                item=fgetOneJournal(rs,item);
                alist.add(item);
            }
            rs.close();
            st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        
        
        finally                         {  	}
        return alist;
    }
    public ArrayList getAllAstreinte(java.sql.Connection conn, UserInfo userinfo , HttpServletRequest request, int code)
    {	ArrayList alist = new ArrayList();
        String qry="";
        System.out.println("journal: " );
        try
        {   if (code==0)    qry="select * from astreinte order by as_code ;"        ;
            else            qry="select * from astreinte " +     " where as_ap_code = " +          code;
            Class.forName("com.informix.jdbc.IfxDriver");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(qry) ;
            while(rs.next())
            {   ItemApplication item = new ItemApplication();
                item=fgetOneAstreinte(rs,item);
                alist.add(item);
            }
            rs.close();
            st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        catch(ClassNotFoundException e) { userinfo.setError_message("ClassNotFoundException: " + e.getMessage() ); }
        
        
        finally                         { 	  	}
        return alist;
    }
    public ArrayList getAllZone(java.sql.Connection conn, UserInfo userinfo , HttpServletRequest request, int code)
    {	ArrayList alist = new ArrayList();
        String qry="";
        ItemZone item = new ItemZone();
        try
        {   if (code==0)    qry="select * from zone order by zn_code ;"        ;
            else            qry="select * from zone " +     "where zn_code = " +          code;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(qry) ;
            while(rs.next())
            {      item=fgetOneZone(rs);
                   alist.add(item);
            }
            rs.close();
            st.close();
        }
        catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
        e.printStackTrace();            }
        
        finally                         { 	}
        System.out.println("nb zone:"+alist.size());
        return alist;
    }
  public void AddZone(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request)
    {	 String qry="";
         try {
               Statement st = conn.createStatement();
             
             if (request.getParameter("submit").equals("Ajouter"))
                 qry="insert into zone (zn_libelle,zn_date ,zn_machine,zn_couleur,zn_poul) values ('" +
                 request.getParameter("libelle") + "','" +
                 "stop" + "','" +
                 "machine" + "','" +
                 "ffffff" + "',120000)" ;
              
             if (request.getParameter("submit").equals("Modifier"))
                 qry="update zone set "+
                 "zn_libelle='" + request.getParameter("libelle") + "'" + "," +
                 "zn_pt_rep='"  + request.getParameter("zn_pt_rep") + "'" + "," +
                 "zn_date='"    +  "stop" + "'" + "," +
                 "zn_machine='" +  "inconnue" + "'" + "," +
                 "zn_couleur='" +  request.getParameter("couleur") + "'" + "," +
                 "zn_poul=    " +  request.getParameter("poul") + " where zn_code= " + request.getParameter("code") ;
                      
             if (request.getParameter("submit").equals("Modif pouls toutes les zones"))
                 qry="update zone set (zn_date,zn_machine,zn_poul) = ('" +  "stop" +
                 "','" +  "inconnue" +
                 
                 "',"  +  request.getParameter("poul")  +   " ) ; " +
                 "update bdd set (bd_port_etat,bd_port_etat_date) = ('3','attente'); " +
                 "update portail set (pt_port_etat,pt_port_etat_date) = ('3','attente'); " +
                 "update machine set (ma_etat,ma_etat_date) = ('3','attente') ;" +
                 "update application set (ap_etat,ap_etat_date) = ('3','attente') ;" +
                 "update instancewa set (iw_port1_etat,iw_port2_etat,iw_port1_etat_date,iw_port2_etat_date) = ('3','3','attente','attente'); " +
                 "update portail_instancewa set (pi_port_etat,pi_port_etat_date) = ('3','attente'); " ;
             System.out.println("Zone:"+qry);
             st.executeUpdate(qry);
             st.close();
         }
         catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
         e.printStackTrace();            }
            
         finally                         {	  	}
    }  
    
    public void AddTheme(java.sql.Connection conn, UserInfo userinfo, HttpServletRequest request)
    {	 String qry="";
         try {
             Statement st = conn.createStatement();
             
             if (request.getParameter("submit").equals("Ajouter"))
                 qry="insert into theme (th_libelle) values ('" +
                 request.getParameter("libelle") +"')" ;
              
             if (request.getParameter("submit").equals("Modifier"))
                 qry="update theme set "+
                 "th_libelle='" + request.getParameter("libelle")  + "' where th_code= " + request.getParameter("code") ;
         	
             System.out.println("qry: "+qry)     ;
             
              
             st.executeUpdate(qry);
             st.close();
         }
         catch(SQLException e)	        { userinfo.setError_message("Erreur: Catalog "+e.getMessage()+"<br>"+qry);
         e.printStackTrace();            }
          
         finally                         {	  	}
    }
    public ItemZone fgetOneZone(ResultSet rs)
    { ItemZone item = new ItemZone();
      try {   		item.setZn_code(rs.getInt("zn_code"));
      item.setZn_poul(rs.getInt("zn_poul"));
      item.setZn_libelle(rs.getString("zn_libelle"));
      item.setZn_date(rs.getString("zn_date"));
      item.setZn_machine(rs.getString("zn_machine"));
      item.setZn_pt_rep(rs.getString("zn_pt_rep"));
      item.setZn_couleur(rs.getString("zn_couleur"));
      }
      catch(SQLException e)	        {  e.printStackTrace();            }
      finally                         {  	           }
      return item;
    }
    public ItemApplication fgetOneMachine(ResultSet rs, ItemApplication item1)
    {                 ItemApplication item = item1;
                      try {
                          item.setMa_code(rs.getInt("ma_code"));
                          item.setMa_zn_code(rs.getInt("ma_zn_code"));
                          item.setMa_libelle(rs.getString("ma_libelle"));
                          item.setMa_sigle(rs.getString("ma_sigle"));
                          item.setMa_adresse(rs.getString("ma_adresse"));
                          item.setMa_etat(rs.getString("ma_etat"));
                          item.setMa_etat_date(rs.getString("ma_etat_date"));
                      }
                      catch(SQLException e)	        {  e.printStackTrace();            }
                      finally                         {  	           }
                      return item;
    }
    public ItemApplication fgetOneBdd(ResultSet rs,ItemApplication item1)
    { ItemApplication item = item1;
      try {
          item.setBd_code(rs.getInt("bd_code"));
          item.setBd_port(rs.getInt("bd_port"));
          item.setBd_ma_code(rs.getInt("bd_ma_code"));
          item.setBd_sigle(rs.getString("ma_sigle"));
          item.setBd_zone(rs.getInt("ma_zn_code"));
          item.setBd_repertoire(rs.getString("bd_repertoire"));
          item.setBd_type(rs.getString("bd_type"));
          item.setBd_port_etat(rs.getString("bd_port_etat"));
          item.setBd_port_etat_date(rs.getString("bd_port_etat_date"));
      }
      catch(SQLException e)	        { e.printStackTrace();            }
      finally                         {  	           }
      return item;
    }
    public ItemDns fgetOneCp(ResultSet rs,ItemDns item1)
    { ItemDns item = item1;
      try {
          item.setCp_ip(rs.getString("cp_ip"));
          item.setCp_nat(rs.getString("cp_nat"));
          item.setCp_node(rs.getString("cp_node"));
     }
      catch(SQLException e)	        { e.printStackTrace();            }
      finally                         {  	           }
      return item;
    }
   
    public ItemDns fgetOneDns(ResultSet rs,ItemDns item1)
    { ItemDns item = item1;
      try {
    	  item.setDn_ip(rs.getString("ip"));
          item.setDn_name(rs.getString("name"));
          item.setDn_dns(rs.getString("dns"));
          item.setDn_node(rs.getString("node"));
          item.setDn_type(rs.getString("type"));
          System.out.println("ip: "+rs.getString("ip") + " name:" + rs.getString("name") + " type:" + rs.getString("type"))     ;
          
     }
      catch(SQLException e)	        { e.printStackTrace();            }
      finally                         {  	           }
      return item;
    }
    
    public ItemDns fgetOneAllip(ResultSet rs,ItemDns item1)
    { ItemDns item = item1;
      try {
    	  item.setAi_ip(rs.getString("ip"));
    	  item.setAi_name(rs.getString("name"));
    	  item.setAi_mayotte(rs.getString("mayotte"));
    	  item.setAi_comores(rs.getString("comores"));
    	  item.setAi_etab(rs.getString("etab"));
    	  item.setAi_ibis(rs.getString("ibis"));
    	  item.setAi_eon(rs.getString("eon"));
          }
      catch(SQLException e)	        { e.printStackTrace();            }
      finally                         {  	           }
      return item;
    }
    public ItemDns fgetOneEon(ResultSet rs,ItemDns item1)
    { ItemDns item = item1;
      try {
    	  item.setEo_ip(rs.getString("eo_ip"));
          item.setEo_dns(rs.getString("eo_dns"));
          }
      catch(SQLException e)	        { e.printStackTrace();            }
      finally                         {  	           }
      return item;
    }
   
    public ItemApplication fgetOneJournal(ResultSet rs,ItemApplication item1)
    { ItemApplication item = item1;
      try {
          item.setAl_code(rs.getInt("al_code"));
          item.setAl_nom(rs.getString("al_nom"));
          item.setAl_etat(rs.getString("al_etat"));
          item.setAl_libetat(rs.getString("al_libetat"));
          item.setAl_date(rs.getString("al_date"));
      }
      catch(SQLException e)	        { e.printStackTrace();            }
      finally                         {  	           }
      return item;
    }
    public ItemApplication fgetOneAstreinte(ResultSet rs,ItemApplication item1)
    { ItemApplication item = item1;
      try {
          item.setAs_code(rs.getInt("as_code"));
          item.setAs_nom(rs.getString("as_nom"));
          item.setAs_mail(rs.getString("as_mail"));
          item.setAs_gsm(rs.getString("as_gsm"));
          item.setAs_ap_code(rs.getInt("as_ap_code"));
      }
      catch(SQLException e)	        { e.printStackTrace();            }
      finally                         {  	           }
      return item;
    }
    public ItemApplication fgetOnePortail(ResultSet rs, ItemApplication item1)
    { ItemApplication item = item1;
      try {                 item.setPt_code(rs.getInt("pt_code"));
      item.setPt_port(rs.getInt("pt_port"));
      item.setPt_ma_code(rs.getInt("pt_ma_code"));
      //item.setMa_sigle(rs.getString("ma_sigle"));
      //item.setMa_libelle(rs.getString("ma_libelle"));
      item.setPt_sigle(rs.getString("ma_sigle"));
      item.setPt_zone(rs.getInt("ma_zn_code"));
      item.setPt_repertoire(rs.getString("pt_repertoire"));
      item.setPt_type(rs.getString("pt_type"));
      item.setPt_version(rs.getString("pt_version"));
      item.setPt_port_etat(rs.getString("pt_port_etat"));
      item.setPt_port_etat_date(rs.getString("pt_port_etat_date"));
      }
      catch(SQLException e)	        {  e.printStackTrace();            }
      finally                         {  	           }
      return item;
    }
    
    
    public ItemApplication fgetOneWebapp(ResultSet rs, ItemApplication item1)
    { ItemApplication item = item1;
      try {                 item.setWa_code(rs.getInt("wa_code"));
      item.setWa_ma_code(rs.getInt("wa_ma_code"));
      //item.setMa_sigle(rs.getString("ma_sigle"));
      //item.setMa_libelle(rs.getString("ma_libelle"));
      item.setWa_repertoire(rs.getString("wa_repertoire"));
      item.setWa_type(rs.getString("wa_type"));
      item.setWa_version(rs.getString("wa_version"));
      }
      catch(SQLException e)	        {  e.printStackTrace();            }
      finally                         {  	           }
      return item;
    }
    public ItemApplication fgetOneDomaineWA(ResultSet rs, ItemApplication item1)
    { ItemApplication item = item1;
      try {                 item.setDo_code(rs.getInt("do_code"));
      item.setDo_portc(rs.getInt("do_portc"));
      item.setDo_ma_code(rs.getInt("do_ma_code"));
      item.setDo_wa_code(rs.getInt("do_wa_code"));
      item.setDo_user(rs.getString("do_user"));
      item.setDo_nom(rs.getString("do_nom"));
      item.setDo_libelle(rs.getString("do_libelle"));
      item.setDo_passwd(rs.getString("do_passwd"));
      //item.setDo_wa_type(rs.getString("wa_type"));
      //item.setDo_wa_libelle(rs.getString("ma_libelle")+"/"+rs.getString("wa_repertoire"));
      }
      catch(SQLException e)	        {  e.printStackTrace();            }
      finally                         {  	           }
      return item;
    }
    public ItemApplication fgetOneInstanceWA(ResultSet rs, ItemApplication item1)
    { ItemApplication item = item1;
      try {           item.setIw_libelle(rs.getString("do_nom"));
      item.setIw_nom(rs.getString("iw_nom"));
      item.setIw_shell(rs.getString("iw_shell"));
      item.setIw_port1_etat(rs.getString("iw_port1_etat"));
      item.setIw_port2_etat(rs.getString("iw_port2_etat"));
      item.setIw_port1_etat_date(rs.getString("iw_port1_etat_date"));
      item.setIw_port2_etat_date(rs.getString("iw_port2_etat_date"));
      item.setIw_nom(rs.getString("iw_nom"));
      item.setIw_code(rs.getInt("iw_code"));
      item.setIw_do_code(rs.getInt("iw_do_code"));
      item.setIw_ma_code(rs.getInt("iw_ma_code"));
      item.setIw_port1(rs.getInt("iw_port1"));
      item.setIw_port2(rs.getInt("iw_port2"));
      }
      catch(SQLException e)	        {  e.printStackTrace();            }
      finally                         { 	           }
      return item;
    }
    public ItemApplication fgetOnePortailIW(ResultSet rs, ItemApplication item1)
    { ItemApplication item = item1;
      try {           item.setPi_libelle(rs.getString("iw_libelle"));
      item.setPi_nom(rs.getString("iw_nom"));
      item.setPi_portail(rs.getString("pt_type"));
      item.setPi_port_etat(rs.getString("pi_port_etat"));
      item.setPi_port_etat_date(rs.getString("pi_port_etat_date"));
      item.setPi_code(rs.getInt("pi_code"));
      item.setPi_pt_code(rs.getInt("pi_pt_code"));
      item.setPi_iw_code(rs.getInt("pi_iw_code"));
      }
      catch(SQLException e)	        {  e.printStackTrace();            }
      finally                         {	           }
      return item;
    }
    public ItemApplication fgetOneTheme(ResultSet rs, ItemApplication item1)
    { ItemApplication item = item1;
      try {           item.setTh_libelle(rs.getString("th_libelle"));
      item.setTh_couleur(rs.getString("th_couleur"));
      item.setTh_code(rs.getInt("th_code"));
      //System.out.println("theme: "+rs.getString("th_libelle")+" color:"+rs.getString("th_couleur"));
      }
      catch(SQLException e)	        {  e.printStackTrace();            }
      finally                         { 	           }
      return item;
    }
    public ItemApplication fgetOneApplication(ResultSet rs, ItemApplication item1)
    { ItemApplication item = item1;
      try {
          item.setAp_code(rs.getInt("ap_code"));
          //System.out.println("coe: "+rs.getInt("ap_code"));
          item.setAp_pt_code(rs.getInt("ap_pt_code"));
          item.setAp_message(rs.getString("ap_message"));
          item.setAp_libelle(rs.getString("ap_libelle"));
          item.setAp_description(rs.getString("ap_description"));
          item.setAp_date_ouverture(rs.getString("ap_date_ouverture"));
          item.setAp_date_fermeture(rs.getString("ap_date_fermeture"));
          item.setAp_murl(rs.getString("ap_murl"));
          item.setAp_url(rs.getString("ap_url"));
          item.setAp_curl_chaine(rs.getString("ap_curl_texte"));
          item.setAp_etat(rs.getString("ap_etat"));
          item.setAp_etat_date(rs.getString("ap_etat_date"));
          item.setAp_visible(rs.getInt("ap_visible"));
          item.setAp_controle(rs.getInt("ap_controle"));
          item.setAp_ouvert(rs.getString("ap_ouvert"));
          item.setAp_ouvert_lib(rs.getString("ap_ouvert_lib"));
      }
      catch(SQLException e)	        {  e.printStackTrace();            }
      finally                         { 	           }
      return item;
    }
}
