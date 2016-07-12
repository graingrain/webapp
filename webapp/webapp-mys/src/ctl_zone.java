
import java.io.*;
import java.util.Date;
import java.sql.*;
import java.awt.*;
import java.net.*;


public class ctl_zone {
    // caracteres affichages
    public static String green =(char)27+"[1;32m";
    public static String normal=(char)27+"[0;39m";
    public static String clear =(char)27+"[2J";
    public static String red   =(char)27+"[1;31m";
    public static String yellow=(char)27+"[1;33m";
    public static String white =(char)27+"[1;37m";
    public static String gray  =(char)27+"[1;30m";
    public static String blue  =(char)27+"[1;34m";
    public static int pos      = 120;
    //
    public static  void main(String[] argv) throws IOException,SQLException  {
        String cnx_annuaire;
        cnx_annuaire="jdbc:informix-sqli://grainville:1517/webapp:informixserver=se724;user=alain;password=trek250";
        //cnx_annuaire="jdbc:informix-sqli://mac5:1518/webapps:informixserver=bov724;user=alain;password=tiga";
        
        // args 0 : numero de zone
        // args 1 : V   pour verbose
        int time_to_sleep=1000;
        int boucle=1;
        System.out.println( red+"surveillance zone :"+argv[0]+normal);
        System.out.println(gray+"--------------------"+normal);
        boolean verbose=false;
        if (argv[1].equals("V"))  {   verbose=true;   }
        try	{       Class.forName("com.informix.jdbc.IfxDriver") ;
        String cnx,qry;
           cnx = cnx_annuaire;
        Connection conn_annu = null;
        conn_annu = DriverManager.getConnection(cnx);
        
        
        while (boucle>0) {
            boucle++;
            //------------------------------------------------
         
           Statement stmt_annu = conn_annu.createStatement();
           Statement stmt_maj  = conn_annu.createStatement();
           //------------------------------------------------
            // liste des applications ---------------------------------------------------------------------------------------------------------
            Date DateJ = new Date();
            System.out.println(red+"surveillance zone :"+argv[0]+normal+" "+ DateJ+" "+boucle);
            System.out.println(gray+"--------------------"+normal);
            System.out.println(gray+"----->  applications     "+normal);
            //System.out.println(gray+"--------------------"+normal);
            
            qry=  "SELECT * FROM  application,portail,machine where ap_pt_code=pt_code and pt_ma_code=ma_code and ma_zn_code="+argv[0] ;
            ResultSet rs  = stmt_annu.executeQuery(qry);
            ResultSet rs2 = stmt_annu.executeQuery(qry);
            Runtime runtime = Runtime.getRuntime();
            String etat="1";
            int j=0;
            int lg=0;
            while(rs.next())
            {   	j++;
                        lg=0;
                        //if (verbose) System.out.println(gray+rs.getString("ap_libelle")+normal);
                        Process process =
                        runtime.exec(new String[] {"/bin/sh","-c", " curl "+ rs.getString("ap_url") +" --connect-timeout 5| grep  " + rs.getString("ap_curl_texte")  });
                        //Process process = runtime.exec(new String[] {"/bin/sh","-c", " ping grainville -c 2 "  });
                        DataInputStream data_in = new DataInputStream(process.getInputStream());
                        String line_in = null;
                        while ((line_in = data_in.readLine()) != null) {        //if (verbose) System.out.println(line_in);
                            lg++;
                        }
                        String zone=gray + rs.getString("ap_libelle").substring(0,20) + normal +
                        ":"+rs.getString("ap_url").substring(0,rs.getString("ap_url").indexOf(" "))+"                                                                       ";
                        
                        String affiche=zone.substring(0,pos-20) + blue + rs.getString("ap_curl_texte")  +
                        "                                                                                  ";
                        
                        //if (verbose) System.out.println("lg:"+lg);
                        if (lg==0) { etat="2";
                        if (verbose) System.out.println(affiche.substring(0,pos)+normal+" ["+red+"  KO  "+normal+"]"  );
                        
                        }
                        else       { etat="1";
                        if (verbose) System.out.println(affiche.substring(0,pos)+normal+" ["+green+"NORMAL"+normal+"]"   );
                        }
                        majetat(verbose,stmt_maj,"ap",rs.getInt("ap_code"),etat);
                        if (!(rs.getString("ap_etat").equals(etat)))
                            log(verbose,stmt_maj,"ap",rs.getInt("ap_code"),etat,rs.getString("ap_libelle"),rs.getString("ap_curl_texte"));
                        
            }
            // liste des machines ---------------------------------------------------------------------------------------------------------
            System.out.println(gray+"--------------------"+normal);
            System.out.println(gray+"----->      Machines     "+normal);
            //System.out.println(gray+"--------------------"+normal);
            qry=  "SELECT * FROM  machine where ma_zn_code="+argv[0] ;
            rs = stmt_annu.executeQuery(qry);
            runtime = Runtime.getRuntime();
            etat="1";
            j=0;
            lg=0;
            while(rs.next())
            {   	j++;
                        lg=0;
                        //if (verbose) System.out.println(gray+"machine:"+rs.getString("ma_libelle")+normal);
                        Process process =
                        //runtime.exec(new String[] {"/bin/sh","-c", " curl "+ rs.getString("ap_url") +" --connect-timeout 5| grep  " + rs.getString("ap_curl_texte")  });
                        runtime.exec(new String[] {"/bin/sh","-c", " ping " + rs.getString("ma_libelle") +" -c 2 -w 5"  });
                        DataInputStream data_in = new DataInputStream(process.getInputStream());
                        DataInputStream data_er = new DataInputStream(process.getErrorStream());
                        
                        String line_in = null;
                        String pourcent="";
                        while ((line_in = data_in.readLine()) != null) {       // if (verbose) System.out.println(line_in);
                            if (line_in.indexOf("100%")!=-1)
                            {  pourcent="100%";
                            }
                        }
                        while ((line_in = data_er.readLine()) != null) {        //if (verbose) System.out.println(line_in);
                            
                            if (line_in.indexOf("unknown")!=-1)
                            {  pourcent="unknown";
                            }
                            lg++;
                        }
                        String affiche = gray + rs.getString("ma_libelle").substring(0,30) +   ":"+
                        "                                "+
                        blue +"-"+ pourcent +  normal+
                        "                                                                                  ";
                        
                        //if (verbose) System.out.println("lg:"+lg);
                        if (pourcent.equals("100%") || pourcent.equals("unknown") )
                        { etat="2";
                          if (verbose) System.out.println(affiche.substring(0,pos)+ " ["+red+"  KO  "+normal+"]" );
                        }
                        else
                        { etat="1";
                          if (verbose) System.out.println(affiche.substring(0,pos)+  " ["+green+"NORMAL"+normal+"]" );
                        }
                        majetat(verbose,stmt_maj,"ma",rs.getInt("ma_code"),etat);
                        if (!(rs.getString("ma_etat").equals(etat)))
                            log(verbose,stmt_maj,"ma",rs.getInt("ma_code"),etat,rs.getString("ma_libelle"),rs.getString("ma_adresse"));
                        
            }
            // liste des portails de la zone   ----------------------------------------------------------------------------------------------------
            System.out.println(gray+"--------------------"+normal);
            System.out.println(gray+"----->      Portails     "+normal);
            //System.out.println(gray+"--------------------"+normal);
            qry=  "SELECT * FROM  portail,machine where  pt_ma_code=ma_code and ma_zn_code="+argv[0];
            rs = stmt_annu.executeQuery(qry);
            etat="1";
            while(rs.next())
            {   	//if (verbose) System.out.println("portail:"+rs.getString("pt_type"));
                        etat=socket2(verbose,
                        rs.getString("ma_libelle").
                        substring(0,rs.getString("ma_libelle").indexOf(" ")),
                        rs.getInt("pt_port"),
                        rs.getString("pt_type").substring(0,rs.getString("pt_type").indexOf(" "))+","+rs.getString("pt_version"));
                        
                        majetat(verbose, stmt_maj,"pt", rs.getInt("pt_code"), etat);
                        if (!(rs.getString("pt_port_etat").equals(etat)))
                            log(verbose,stmt_maj,"pt",rs.getInt("pt_code"),etat,rs.getString("ma_libelle"),""+rs.getInt("pt_port"));
            }
            // liste des bdd de la zone   ----------------------------------------------------------------------------------------------------
            System.out.println(gray+"--------------------"+normal);
            System.out.println(gray+"----->           Bdd     "+normal);
            //System.out.println(gray+"--------------------"+normal);
            qry=  "SELECT * FROM  bdd,machine where  bd_ma_code=ma_code and ma_zn_code="+argv[0];
            rs = stmt_annu.executeQuery(qry);
            etat="1";
            while(rs.next()) {   	//if (verbose) System.out.println("bdd:"+rs.getString("bd_type"));
                //    etat=socket(verbose,  rs.getString("ma_libelle").substring(0,rs.getString("ma_libelle").indexOf(" ")),rs.getInt("bd_port"));
                etat=socket2(verbose,
                rs.getString("ma_libelle").substring(0,rs.getString("ma_libelle").indexOf(" ")),
                rs.getInt("bd_port"),
                rs.getString("bd_type").substring(0,rs.getString("bd_type").indexOf(" "))+","+rs.getString("bd_repertoire"));
                
                majetat(verbose,stmt_maj,"bd", rs.getInt("bd_code"), etat);
                if ((rs.getString("bd_port_etat")==null)||!(rs.getString("bd_port_etat").equals(etat)))
                    log(verbose,stmt_maj,"bd",rs.getInt("bd_code"),etat,rs.getString("ma_libelle"),""+rs.getInt("bd_port"));
            }
            // liste des instances(webapp) de la zone   ----------------------------------------------------------------------------------------------------
            System.out.println(gray+"--------------------"+normal);
            System.out.println(gray+"----->    InstanceWA     "+normal);
            //System.out.println(gray+"--------------------"+normal);
            qry=  "SELECT * FROM  instancewa,machine where  iw_ma_code=ma_code and ma_zn_code="+argv[0];
            rs = stmt_annu.executeQuery(qry);
            etat="1";
            while(rs.next()) {   	//if (verbose) System.out.println("pi:"+rs.getString("pi_port"));
                etat=socket2(verbose,
                rs.getString("ma_libelle").substring(0,rs.getString("ma_libelle").indexOf(" ")),
                rs.getInt("iw_port1"),
                rs.getString("iw_nom"));
                majetat(verbose, stmt_maj,"iw1", rs.getInt("iw_code"), etat);
                if ((rs.getString("iw_port1_etat")==null)||!(rs.getString("iw_port1_etat").equals(etat)))
                    log(verbose,stmt_maj,"iw1",rs.getInt("iw_code"),etat,rs.getString("ma_libelle"),""+rs.getInt("iw_port1"));
            }
            // liste des instanceWA-bdd de la zone   ----------------------------------------------------------------------------------------------------
            System.out.println(gray+"--------------------"+normal);
            System.out.println(gray+"----->InstanceWA-Bdd     "+normal);
            //System.out.println(gray+"--------------------"+normal);
            qry=  "SELECT * FROM  instancewa,machine,bdd where iw_bd_code = bd_code and bd_ma_code=ma_code and ma_zn_code="+argv[0];
            rs = stmt_annu.executeQuery(qry);
            etat="1";
            while(rs.next()) {   	//if (verbose) System.out.println("pi:"+rs.getString("pi_port"));
                etat=socket2(verbose,
                rs.getString("ma_libelle").substring(0,rs.getString("ma_libelle").indexOf(" ")),
                rs.getInt("iw_port2"),
                rs.getString("iw_nom"));
                majetat(verbose, stmt_maj,"iw2", rs.getInt("iw_code"), etat);
                if ((rs.getString("iw_port2_etat")==null)||!(rs.getString("iw_port2_etat").equals(etat)))
                    log(verbose,stmt_maj,"iw2",rs.getInt("iw_code"),etat,rs.getString("ma_libelle"),""+rs.getInt("iw_port2"));
            }
      // liste des portail_instanceWA de la zone   ----------------------------------------------------------------------------------------------------
            System.out.println(gray+"--------------------"+normal);
            System.out.println(gray+"----->Portail_InstWA"+normal);
            //System.out.println(gray+"--------------------"+normal);
            
            qry=  "SELECT * FROM  portail_instancewa, portail, machine where pi_pt_code = pt_code " + 
            " and pt_ma_code=ma_code and ma_zn_code="+argv[0];
            rs = stmt_annu.executeQuery(qry);
            while(rs.next()) {   	
            etat="1"; 
            int wpi_code=rs.getInt("pi_code"); 
            int wiw_code=rs.getInt("pi_iw_code");
            String wpi_port_etat=rs.getString("pi_port_etat");
            int wiw_port=0;
            String wsigle_distant="";
            qry=  "SELECT * FROM  instancewa,machine where   iw_ma_code=ma_code and iw_code=" + wiw_code;
            rs2 = stmt_annu.executeQuery(qry);
            while(rs2.next()) {
                wsigle_distant=rs2.getString("ma_sigle");
                wiw_port=rs2.getInt("iw_port1");
                if (verbose) System.out.println("pi:"+rs2.getString("iw_port1")+" "+rs2.getString("ma_sigle"));
                etat=socket2(verbose,
                rs2.getString("ma_libelle").substring(0,rs2.getString("ma_libelle").indexOf(" ")),
                rs2.getInt("iw_port1"),
                rs2.getString("iw_nom"));
            }
            if (verbose) System.out.println("majetat");
               
            majetat(verbose, stmt_maj,"pi", wpi_code , etat);
            if (verbose) System.out.println("log");
                if ((wpi_port_etat==null)||!(wpi_port_etat.equals(etat)))
                    log(verbose,stmt_maj,"pi", wpi_code ,etat,wsigle_distant,""+wiw_port);
            }
            
            // -------------------------------------------------fin des ctrls-----sleep--------------------------------------------------
            
            rs.close();
            rs2.close();
            stmt_annu.close();
            stmt_maj.close();
            //conn_annu.close();
            Thread.sleep(time_to_sleep);
        }
        }
        catch(SQLException e)
        {  	String msgErr ="Erreur Sql : "+ e.getMessage();
                while((e = e.getNextException()) != null) msgErr=msgErr + "<br>"+ e.getMessage();
                System.out.println(msgErr);
                return;
        }
        catch(Exception e)
        {  	String msgErr ="Erreur login : "+ e.getMessage();
                System.out.println(msgErr);
                e.printStackTrace();
                return;
        }
    }
    
    public  static  String socket(boolean verbose, String host, int port) throws IOException
    {	String reponse = "1";
        // ----------------   1: ok
        //                    2: ko
        int portNr = port;
        //if (verbose) System.out.println ("Connecting..."+host+":"+port);
        try {
            //if (verbose) System.out.println ("av s ");
            InetAddress addr=InetAddress.getByName(host);
            SocketAddress sockaddr = new InetSocketAddress(addr, port);
            Socket sock = new Socket() ;
            int timeoutMs = 2000;
            String mess=host+"                                                                     ";
            String mess1=mess.substring(0,30);
            String vport=""+port+"                                                    ";
            String vport1=vport.substring(0,30);
            
            sock.connect(sockaddr, timeoutMs);
            if (verbose) System.out.println(gray+mess1+normal +vport1 +" ["+green+"NORMAL"+normal+"]" );
            
            //if (verbose) System.out.println (green+"OK"+normal);
        }
        catch (IOException ioe) {
            String mess=host+"                                                                     ";
            String mess1=mess.substring(0,30);
            String vport=""+port+"                                                    ";
            String vport1=vport.substring(0,30);
            if (verbose) System.out.println(gray+mess1+normal + vport1 +
            " ["+red+"  KO  "+normal+"]" );
            
            
            //System.out.println(red+"pb: socket :" + host+ " port:" + port + normal );
            reponse = "2";
        }
        finally       { //sock.close();
            return reponse; }
    }
    
    
    public  static  String socket2(boolean verbose, String host, int port, String type) throws IOException
    {	String reponse = "1";
        // ----------------   1: ok
        //                    2: ko
        // socket de 
        int portNr = port;
        //if (verbose) System.out.println ("Connecting..."+host+":"+port);
        try {
            //if (verbose) System.out.println("av s "+host+port);
            InetAddress addr=InetAddress.getByName(host);
            SocketAddress sockaddr = new InetSocketAddress(addr, port);
            Socket sock = new Socket() ;
            int timeoutMs = 2000;
            String mess=host+"                                                                     ";
            String mess1=mess.substring(0,30);
            String vport=""+port+"                                                    ";
            String vport1=vport.substring(0,30);
            String vtype="("+type.substring(0,type.indexOf(" "))+")"+"                           ";
            
            sock.connect(sockaddr, timeoutMs);
            String affiche=gray+mess1+normal +vport1+blue+vtype.substring(0,30)+"                                            ";
            if (verbose) System.out.println(affiche.substring(0,pos)+normal+normal+" ["+green+"NORMAL"+normal+"]"   );
           }
        catch (IOException ioe) {
            String mess=host+"                                                                     ";
            String mess1=mess.substring(0,30);
            String vport=""+port+"                                                    ";
            String vport1=vport.substring(0,30);
            String vtype="("+type.substring(0,type.indexOf(" "))+")"+"                           ";
            String affiche=gray+mess1+normal +vport1+blue+vtype.substring(0,30)+"                                            ";
            
            if (verbose) System.out.println(affiche.substring(0,pos)+normal+normal+" ["+red+"  KO  "+normal+"]"  );
            
            //System.out.println(red+"pb: socket :" + host+ " port:" + port + normal );
            reponse = "2";
        }
        finally       { //sock.close();+
            return reponse; }
    }
    public static String log(boolean verbose,Statement stmt_maj, String type, int code, String etat, String libelle, String port)
    {	String reponse = "1";
        String wlib=libelle+"                                                                           ";
        String mess_err=type+" "+wlib.substring(0,wlib.indexOf(" "))+","+port+" etat="+etat;
        
        //if (verbose) System.out.println ("log..."+type+":"+code+" "+libelle);
        //if (verbose) System.out.println ("________________________________________________________");
        try {   Date DateJour=new Date();
        int month=DateJour.getMonth()+1;
        String vdatedeb=DateJour.getDate()+"/"+month+"-"+DateJour.getHours()+":"+DateJour.getMinutes()+":"+DateJour.getSeconds();
        String select="insert into log (lg_type,lg_type_code,lg_etat,lg_etat_date,lg_libelle) values ('" +
        type + "'," +  code + ",'" +
        etat  + "','" + vdatedeb + "','" + mess_err + "')" ;
        stmt_maj.executeUpdate(select);
        stmt_maj.close();
        }
        catch (SQLException ioe)         {  System.out.println(" log: ko");         }
        finally                          {
            
            return reponse;                         }
    }
    public static  String majetat(boolean verbose, Statement stmt_maj, String type, int code, String etat)
    {	String reponse = "1";
        String select="";
        try {    //Statement stmt_maj = conn_annu.createStatement();
            Date DateJour=new Date();
            int month=DateJour.getMonth()+1;
            String vdate=DateJour.getDate()+"/"+month+"-"+DateJour.getHours()+":"+DateJour.getMinutes()+":"+DateJour.getSeconds();
            if (type.equals("ap")) select="update application        set (ap_etat,ap_etat_date)           = ('" + etat + "','" + vdate +
            "') where ap_code=" + code +";" ;
            if (type.equals("pt")) select="update portail            set (pt_port_etat,pt_port_etat_date) = ('" + etat + "','" + vdate +
            "') where pt_code=" + code +";" ;
            if (type.equals("ma")) select="update machine            set (ma_etat,ma_etat_date)           = ('" + etat + "','" + vdate +
            "') where ma_code=" + code +";" ;
            if (type.equals("pi")) select="update portail_instancewa set (pi_port_etat,pi_port_etat_date) = ('" + etat + "','" + vdate +
            "') where pi_code=" + code +";" ;
            if (type.equals("wa")) select="update webapp             set (wa_port_etat,wa_port_etat_date) = ('" + etat + "','" + vdate +
            "') where wa_code=" + code +";" ;
            if (type.equals("iw1")) select="update instancewa         set (iw_port1_etat,iw_port1_etat_date) = ('" + etat + "','" + vdate +
            "') where iw_code=" + code +";" ;
            if (type.equals("iw2")) select="update instancewa         set (iw_port2_etat,iw_port2_etat_date) = ('" + etat + "','" + vdate +
            "') where iw_code=" + code +";" ;
            if (type.equals("ib")) select="update instancewa_bdd     set (ib_etat,ib_etat_date)           = ('" + etat + "','" + vdate +
            "') where ib_code=" + code +";" ;
            if (type.equals("bd")) select="update bdd                set (bd_port_etat,bd_port_etat_date) = ('" + etat + "','" + vdate +
            "') where bd_code=" + code +";" ;
            //if (verbose) System.out.println(" majetat : "+select);
            stmt_maj.executeUpdate(select);
            //if (verbose) System.out.println("finmaj");
            stmt_maj.close();
        }
        catch (SQLException ioe)           {	    //System.out.println(" majetat :ko " + type);
            reponse = "2" ;
        }
        finally                            {
            
            return reponse; }
    }
    
}




