package javabeans;
import javax.sql.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
//import common.DataBean;
import javabeans.ItemDdt;
import javax.servlet.*;
import javax.servlet.http.*;
public class Annuaire_retenue extends HttpServlet
{   private static String cnx_ddt;
    private String lib_liste;  
    private String one_tache;
    private String cnx,type,qry,select,sel2;

    public ArrayList getAllItems(java.sql.Connection conn, UserInfo userinfo,String adm,String etab)
  //______________________________lecture des agents de la div de type adm     ____  
    {	ArrayList alist = new ArrayList();
	try
	{       
            if (!(adm.equals("")))
            select="select   * from agent2,adm  where adm  = '"+adm+"'  and adm=codeadm and nbj = 0  order by nom";
            else
            select="select distinct  * from agent2,adm  where etab = '"+etab+"' and adm in (select codeadm from adm where codediv='"  + userinfo.getAgnom() +"') and adm=codeadm and nbj = 0  order by nom";
            System.out.println("AllItem:"+select);    
            
            Statement st = conn.createStatement();
         	ResultSet rs = st.executeQuery(select) ;   
		while(rs.next())
        	{       ItemAnnuaire item = new ItemAnnuaire();
                        item.setNom(rs.getString("nom"));
                        //System.out.println(rs.getString("nom"));
			item.setPrenom(rs.getString("prenom"));
                        item.setIdentifiant(rs.getString("identifiant"));
                        item.setInsee(rs.getString("insee"));
                        item.setNbjmaxi(rs.getInt("nbjmaxi"));
                         //item.setQt(rs.getString("qt"));
                        //item.setTp(rs.getString("tp"));
			 alist.add(item); 
                }
                rs.close();
		st.close();
 	}
 	catch(SQLException e)
 	{  	System.out.println("Erreur annuaire getAllItem: " + e.getMessage() );
                System.out.println(select);
         	while((e = e.getNextException()) != null)
            	System.out.println(e.getMessage() + "<BR>");
 	}
 	finally
	{
	return alist;
 	}
  }
// ------------------------------------------------- fin methode   ------------------------------

   public ArrayList getOneItem(java.sql.Connection conn, UserInfo userinfo,String insee,String identifiant)
  //______________________________lecture des agents de la div de type adm     ____  
    {	ArrayList alist = new ArrayList();
	try
	{       select="select distinct * from agentengreve  where identifiant = '"+identifiant+"' and insee ='" + insee + "'  order by moisdegreve";
                Statement st = conn.createStatement();
         	ResultSet rs = st.executeQuery(select) ;   
                 System.out.println("OneItem:"+select);    
		while(rs.next())
        	{       ItemMoisEnGreve item = new ItemMoisEnGreve();
                        item.setMois(rs.getString("moisdegreve"));
                        item.setLigne(rs.getString("ligne"));
                        alist.add(item); 
                }
                rs.close();
		st.close();
 	}
 	catch(SQLException e)
 	{  	System.out.println("Erreur annuaire getOneItem: " + e.getMessage() );
                System.out.println(select);
         	while((e = e.getNextException()) != null)
            	System.out.println(e.getMessage() + "<BR>");
 	}
 	finally  	{	return alist; 	}
  }
// ------------------------------------------------- fin methode   ------------------------------
  
  
   public ArrayList getAllEtab(java.sql.Connection conn, UserInfo userinfo,String codediv)
  //______________________________lecture des agents de la div de type adm     ____  
    {	ArrayList alist = new ArrayList();
	try
	{       select="select distinct etab from agent2 where adm in (select codeadm from adm where codediv='"  + codediv +"')";
                
                Statement st = conn.createStatement();
         	ResultSet rs = st.executeQuery(select) ;   
                 System.out.println("AllEtab:"+select);    
		while(rs.next())
        	{       ItemEtab item = new ItemEtab();
                        item.setEtab(rs.getString("etab"));
                        alist.add(item); 
                }
                rs.close();
		st.close();
 	}
 	catch(SQLException e)
 	{  	System.out.println("Erreur annuaire getOneItem: " + e.getMessage() );
                System.out.println(select);
         	while((e = e.getNextException()) != null)
            	System.out.println(e.getMessage() + "<BR>");
 	}
 	finally  	{	return alist; 	}
  }
// ------------------------------------------------- fin methode   ------------------------------
  
  
 
  
  
        public ArrayList getAllRetenue(java.sql.Connection conn, UserInfo userinfo,HttpServletRequest request,String adm,String etab)
  //______________________________lecture des agents de la div de type adm     ____  
    {	ArrayList alist = new ArrayList();
	try
	{   if (!(adm.equals("")))
            select="select distinct * from agent2  where adm   = '"+adm+"' and nbj != 0  order by nom";
            else
            select="select distinct * from agent2  where etab = '"+
            etab+"' and adm in (select codeadm from adm where codediv='"  + userinfo.getAgnom() +"') and nbj != 0  order by nom";
             System.out.println("AllRetenue:"+select);        
                Statement st = conn.createStatement();
                 
         	ResultSet rs = st.executeQuery(select) ;
                 
		while(rs.next())
        	{       ItemAnnuaire item = new ItemAnnuaire();
                        item.setNom(rs.getString("nom"));
			item.setPrenom(rs.getString("prenom"));
                        item.setIdentifiant(rs.getString("identifiant"));
                        item.setInsee(rs.getString("insee"));
                        
                        if((request.getParameter("admin")!=null)&&(request.getParameter("admin").equals("yes")))
                        {
                            sel2="select sum(nbj) sum from retenue where insee='" + rs.getString("insee") +"'";
                            Statement st2 = conn.createStatement();
                            ResultSet rs2 = st2.executeQuery(sel2) ;
                            while(rs2.next())
                            {       item.setNbjr(rs2.getInt("sum"));
                            }
                            rs2.close();
                            st2.close();
                        } 
                        item.setDatesaisie(rs.getString("datesaisie"));
                         
                        item.setNbj(rs.getInt("nbj"));
			alist.add(item); 
                }
		 
                rs.close();
		st.close();
 	}
 	catch(SQLException e)
 	{  	System.out.println("Erreur annuaire getAllRETENUE: " + e.getMessage() );
                System.out.println(select);
                System.out.println(sel2);
         	while((e = e.getNextException()) != null)
            	System.out.println(e.getMessage() + "<BR>");
 	}
 	finally
	{
	return alist;
 	}
  }
// ------------------------------------------------- fin methode   ------------------------------

       public int SupRetenue(java.sql.Connection conn, UserInfo userinfo,String insee)
  //______________________________lecture des agents de la div de type adm     ____  
    {	int i=0;
	try
	{        
                Statement st = conn.createStatement();
         	
		String select = "update agent2 set (nbj) = (0) where insee ='"+ insee + "'";
                   
                System.out.println("rq del:"+select); 
                i = st.executeUpdate(select);
		select = "delete from retenue where insee ='"+ insee + "'";
                   
                System.out.println("rq del:"+select); 
                i = st.executeUpdate(select); 
                 
		st.close();
 	}
 	catch(SQLException e)
 	{  	System.out.println("Erreur annuaire getAllItem: " + e.getMessage() );
                System.out.println(select);
         	while((e = e.getNextException()) != null)
            	System.out.println(e.getMessage() + "<BR>");
 	}
 	finally
	{
	return i;
 	}
  }
// ------------------------------------------------- fin methode   ------------------------------
 public ArrayList getStat(java.sql.Connection conn, UserInfo userinfo,HttpServletRequest request)
  //______________________________lecture des agents de la div de type adm     ____  
    {	int nb=0;
        ArrayList alist = new ArrayList();
	try
	{        
		select="select sum(nbj) sum,div   from retenue group by div ;";
                String sel2="";
                Statement st = conn.createStatement();
         	ResultSet rs = st.executeQuery(select) ;   
                System.out.println("sel"+select); 
                while(rs.next())
                {   ItemStat item = new ItemStat();
                    item.setNbjretenue(rs.getInt("sum"));
                    item.setDivision(rs.getString("div"));
                    sel2="select sum(nbj) sum from agent2 where division = '"+ rs.getString("div") + "'";
                    Statement st2 = conn.createStatement();
                    ResultSet rs2 = st2.executeQuery(sel2) ;  
                    
                    while(rs2.next())
                    {
                        item.setNbjuser(rs2.getInt("sum"));
                    }
                    
                    sel2="select count (distinct insee) sum  from agent2 where nbj != 0 and division = '"+ rs.getString("div") + "'";
                    rs2 = st2.executeQuery(sel2) ; 
                    while(rs2.next())
                    {
                        item.setNbuser(rs2.getInt("sum"));
                    }
                    rs2.close();
                    st2.close();
                    
                    
                    alist.add(item);
                }
                
                rs.close();
		st.close();
 	}
 	catch(SQLException e)
 	{  	System.out.println("Erreur annuaire getAllItem: " + e.getMessage() );
                System.out.println(select);
         	while((e = e.getNextException()) != null)
            	System.out.println(e.getMessage() + "<BR>");
 	}
 	finally
	{
	return alist;
 	}
  
 }
 
 
  public ArrayList getStatEtab(java.sql.Connection conn, UserInfo userinfo,HttpServletRequest request)
  //______________________________lecture des agents de la div de type adm     ____  
    {	int nb=0;
        ArrayList alist = new ArrayList();
	try
	{        
		select="select sum(nbj) sum  from agent2  where etab = '"+
                request.getParameter("etab")+"' and adm in (select codeadm from adm where codediv='"  + 
                userinfo.getAgnom() +"')  "   ;
      
                String sel2="";
                Statement st = conn.createStatement();
         	ResultSet rs = st.executeQuery(select) ;   
                ResultSet rs2 = st.executeQuery(select) ;   
                 
                while(rs.next())
                {   ItemStat item = new ItemStat();
                    //item.setNbjretenue(rs.getInt("sum"));
                    //item.setDivision(rs.getString("div"));
                    sel2="select sum(nbj) sum from retenue where division = '"+ rs.getString("div") + "'";
                    //rs2 = st.executeQuery(sel2) ;
                    //while(rs2.next())
                    //{                        item.setNbjuser(rs2.getInt("sum"));
                    //   System.out.println("nbj:"+rs2.getInt("sum"));
                    //}
                    
                    alist.add(item);
                }
                rs.close();
		st.close();
 	}
 	catch(SQLException e)
 	{  	System.out.println("Erreur annuaire getStatETAB : " + e.getMessage() );
                System.out.println(select);
         	while((e = e.getNextException()) != null)
            	System.out.println(e.getMessage() + "<BR>");
 	}
 	finally
	{
	return alist;
 	}
  
 }
 
   
        public String getNbjmaxi(java.sql.Connection conn, UserInfo userinfo,String adm)
  //______________________________lecture des agents de la div de type adm     ____  
    {	String nbjmaxi="";
	try
	{        
		select="select * from adm where codeadm = "+adm;
                Statement st = conn.createStatement();
         	ResultSet rs = st.executeQuery(select) ;   
                 
                 
                while(rs.next())
                {   
                    nbjmaxi=rs.getString("nbjmaxi");
                }
                rs.close();
		st.close();
 	}
 	catch(SQLException e)
 	{  	System.out.println("Erreur annuaire getAllItem: " + e.getMessage() );
                System.out.println(select);
         	while((e = e.getNextException()) != null)
            	System.out.println(e.getMessage() + "<BR>");
 	}
 	finally
	{
	return nbjmaxi;
 	}
  }
// ------------------------------------------------- fin methode   ------------------------------
    
  public int AddRetenue(HttpServletRequest request,java.sql.Connection conn, UserInfo userinfo, String insee,int maxi) throws SQLException,ClassNotFoundException
//__________________________________________________________________________________________________________________________________________________________________________
{	int i=0;
        int j=0;
        HttpSession session = request.getSession(true)        ;
        int tmois[] = new int[50];
        int trmois[] = new int[50];
        int tan[]   = new int[50];
        for(int l=0;i<50;i++) trmois[l]=0;
        String codemois[] = new String[10];
        codemois[0]="2";
        codemois[1]="4";
        codemois[2]="5";
        codemois[3]="6";
        String select="";
        tmois = (int [])session.getValue("tmois");
        tan = (int [])session.getValue("tan");
        Class.forName("com.informix.jdbc.IfxDriver");
	try
	{ 	
        String cnx = userinfo.getCnx_ddt();
                Statement st = conn.createStatement();
	        Date DateJour;
                DateJour=new Date();
                String select2="select distinct(mois),sum(nbj) sum,insee from retenue where insee ='" + insee +"' group by mois,insee";
                ResultSet rs0 = st.executeQuery(select2) ;   
                 
                 
                while(rs0.next())
                {   
                    trmois[rs0.getInt("mois")]=rs0.getInt("sum");
                }
                rs0.close();
                int month=DateJour.getMonth()+1;
                int moisdegreve=1;
                int val=0;
                int restemois=0;
                int nbmoisencours=0;
                 
                int k=0;
                String vdatedeb=DateJour.getDate()+"/"+month+"-"+DateJour.getHours()+":"+DateJour.getMinutes();
                 
                for (k=0;k<4;k++)
                {
                   if((request.getParameter("nbj"+codemois[k])!=null)&&(Integer.parseInt(request.getParameter("nbj"+codemois[k]))!=0))
                   {
                    System.out.println(k+" nbj:"+ request.getParameter("nbj"+codemois[k]) + " reste="+nbmoisencours); 
                    //if(nbmoisencours==0) moisdegreve++;
                    restemois=Integer.parseInt(request.getParameter("nbj"+codemois[k]));
                    
                    //int nbmois=nbj/maxi;
                    while (restemois > 0)
                    {   System.out.println(" while restemois:"+restemois);
                        System.out.println("mois:"+moisdegreve+" histo="+trmois[moisdegreve]);
                        while(trmois[moisdegreve]>=maxi) moisdegreve++;
                        System.out.println("mois:"+moisdegreve+" histo="+trmois[moisdegreve]);
                        
                        val=maxi - nbmoisencours - trmois[moisdegreve];
                        System.out.println("av val:"+val+" nbmoisencours: "+nbmoisencours +" trmois="+trmois[moisdegreve]);
                        
                        if (val>restemois) val=restemois ;
                        System.out.println("ap val:"+val+" nbmoisencours: "+nbmoisencours +" trmois="+trmois[moisdegreve]);
                    //String xadm=request.getParameter("identifiant").substring(3,6);    
                    select = "insert into retenue (mois,an,insee,nbj,moisdegreve,ligne,div)"+
                    " values ("+moisdegreve+","+tan[j]+",'" + insee +"',"+ val + ",'"+ codemois[k] + "','"+ 
                    request.getParameter("lig"+codemois[k]) +"','"+userinfo.getAgnom()+
                   // "','"+request.getParameter("etab")+"','"+xadm+
                    "')";
                    System.out.println("rq ajout1:"+select); 
                    restemois= restemois-val;
                    System.out.println("restemois:"+restemois);
                    i = st.executeUpdate(select);
                    int tot=val+nbmoisencours+trmois[moisdegreve];
                    System.out.println("tot:"+tot);
                    if(tot == maxi) {
                        moisdegreve++;
                        System.out.println("change mois");
                        }
                        nbmoisencours=0;
                    }
                    //reste = nbj - (j * maxi);
                    if(val<maxi) nbmoisencours=val;
                    System.out.println("mois en cours :"+nbmoisencours);
                     
                   }
                
                }
                int nbj4=0;
                int nbj5=0;
                int nbj6=0;
                if (request.getParameter("nbj4")!=null) nbj4=Integer.parseInt(request.getParameter("nbj4"));
                if (request.getParameter("nbj5")!=null) nbj5=Integer.parseInt(request.getParameter("nbj5"));
                if (request.getParameter("nbj6")!=null) nbj6=Integer.parseInt(request.getParameter("nbj6"));
                String where="";
                if (request.getParameter("adm")!=null&&request.getParameter("adm").length()>1)
                   where=" and adm='"+request.getParameter("adm") + "'"; 
                if (request.getParameter("etab")!=null&&request.getParameter("etab").length()>1)
                   where=" and etab='"+request.getParameter("etab") + "'";
                select = "update agent2 set (datesaisie,nbj,nbj4,nbj5,nbj6,division) = ('"+vdatedeb+"',"+
                request.getParameter("nbjtot") + "," +request.getParameter("nbj4") + ","+ request.getParameter("nbj5") + ","+
                request.getParameter("nbj6") + ",'"+ userinfo.getAgnom() +   "') where identifiant = '" + request.getParameter("identifiant") +"' and insee = '" + insee +"'" + where;
                System.out.println("rq update:"+select); 
                i = st.executeUpdate(select);
         	st.close();
 	}
 	catch(SQLException e)
 	{  	System.out.println("Erreur annuaire getAllItem: " + e.getMessage() );
                while((e = e.getNextException()) != null) System.out.println(e.getMessage());
 	}
 	finally
	{
	return i;
 	}
  }
  
}
