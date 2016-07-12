
import java.io.*;
import java.util.Date;
import java.sql.*;
import java.awt.*;
import java.net.*;


public class ctl_zonen {
    // caracteres affichages
    public static String green =(char)27+"[1;32m";
    public static String normal=(char)27+"[0;39m";
    public static String clear =(char)27+"[2J";
    public static String red   =(char)27+"[1;31m";
    public static String yellow=(char)27+"[1;33m";
    public static String white =(char)27+"[1;37m";
    //public static String gray  =(char)27+"[1;33m";
    public static String gray  =(char)27+"[1;37m";
    public static String blue  =(char)27+"[1;34m";
    public static int cyclem        = -1;
    public static int cyclem_nb     = -2;
    public static int cyclep        = -1;
    public static int cyclep_nb     = -1;
    public static int cycleib       = -1;
    public static int cycleib_nb    = -1;
    public static int cyclei        = -1;
    public static int cyclei_nb     = -1;
    public static int pos      = 120;
    
    // ctlzonen
    // modif du 23 sept 2005 : gestion de cycles pour test machine
    public static  void main(String[] argv) throws IOException,SQLException  {
        String cnx_annuaire;
        cnx_annuaire = "jdbc:mysql://bd1.in.ac-reunion.fr/webapp";
        
        // args 0 : numero de zone
        // args 1 : V   pour verbose
        // args 2 : TEST
        int time_to_sleep=120000;
        String etat="";
        int netat=2;
        String machine="";
        int boucle=1;
        Runtime runtime = Runtime.getRuntime();
        Process process =   runtime.exec(new String[] {"/bin/sh","-c", "uname -n  "  });
        DataInputStream data_in = new DataInputStream(process.getInputStream());
        String line_in = null;
        while ((line_in = data_in.readLine()) != null) {   machine = line_in;      }
        
        System.out.println( red+"surveillance zonen Luna :"+argv[0]+normal);
        
        boolean verbose=false;
        boolean verbose2=false;
        if (argv[1].equals("V"))  {   verbose=true;   }
        if (argv[2].equals("V"))  {   verbose2=true;   }
        
        try {
        	 Class.forName("com.mysql.jdbc.Driver");
             String cnx,qry;
             cnx = cnx_annuaire;
             
             Connection  conn_annu = DriverManager.getConnection(cnx,"alain","trek250");
            //------------------------------------------------
            Statement stmt_annu = conn_annu.createStatement();
            System.out.println( green+"conn close :"+ conn_annu.isClosed() + normal  );
            Statement stmt_maj  = conn_annu.createStatement();
            System.out.println( green+"conn close :"+ conn_annu.isClosed() + normal  );
            //Statement stmt_maj2  = conn_annu.createStatement();
            //------------------------------------------------
            qry="select * from application";
            ResultSet rs = stmt_annu.executeQuery(qry);
            
            while (boucle>0) {
            	if (verbose) System.out.println("boucle:"+green+boucle+normal);
                //stmt_annu = conn_annu.createStatement();
                System.out.println(  "conn close :"+ blue + conn_annu.isClosed() + normal  );
                //stmt_maj  = conn_annu.createStatement();
                boucle++;
                // liste des applications ---------------------------------------------------------------------------------------------------------
                Date DateJ = new Date();
                int month=DateJ.getMonth()+1;
                String vdate=DateJ.getDate()+"/"+month+"-"+DateJ.getHours()+":"+DateJ.getMinutes()+":"+DateJ.getSeconds();
                // recherche du poul  ----------------------------------------------------------------------------------------------------
                
                // maj table zone pour verif activite ctlzone
                qry=  "SELECT * FROM  zone where zn_code="+argv[0];
                if (verbose)  System.out.println("qry:"+green+qry+normal);
                rs = stmt_annu.executeQuery(qry);
                etat="1";
                while(rs.next())         {   time_to_sleep=rs.getInt("zn_poul");            }
                
                if (verbose)  System.out.println("pool:"+green+time_to_sleep+normal);
               
                String select="update zone  set  zn_machine ='" +machine+ "',zn_date='" +  vdate + "' where zn_code=" + argv[0];
                
                if (verbose2) System.out.println("select :"+green+ select+normal);
                stmt_maj.executeUpdate(select);
                //
                if (verbose) System.out.println(red+"--------------------"+normal);
                if (verbose) System.out.println(red+"----->  applications     "+normal);
                if (verbose) System.out.println(red+"--------------------"+normal);
                int j=0;
                int k=0;
                int lg=0;
                qry=  "SELECT * FROM  application,portail,machine where ap_pt_code=pt_code and pt_ma_code=ma_code and ap_controle = 1 and ma_zn_code="+argv[0] ;
                if (verbose2) System.out.println("qry:"+qry);
                rs = stmt_annu.executeQuery(qry);
                runtime = Runtime.getRuntime();
                etat="1";
                j=0;
                lg=0;
                while(rs.next())
                {   	j++;
                            lg=0;
                            if (verbose2) System.out.println(gray+" ap_url:" + rs.getString("ap_url")+normal);
                             process =
                            runtime.exec(new String[] {"/bin/sh","-c", " curl "+ rs.getString("ap_url") +" --connect-timeout 5| grep  " + rs.getString("ap_curl_texte")  });
                            //Process process = runtime.exec(new String[] {"/bin/sh","-c", " ping grainville -c 2 "  });
                              data_in = new DataInputStream(process.getInputStream());
                             line_in = null;
                            while ((line_in = data_in.readLine()) != null) {        
                            	if (verbose2) System.out.println(line_in);
                                lg++;
                            }
                            String ap_url=rs.getString("ap_url")+"                                          ";
                            String zone=gray + rs.getString("ap_libelle")  + normal +      ":"+ap_url.indexOf(" ")+"                                                                                                                                                                  ";
                            
                            String affiche=zone.substring(0,pos-20) + gray + rs.getString("ap_curl_texte")  +
                            "                                                                                  ";
                            
                            //if (verbose) System.out.println("lg:"+lg);
                            if (lg==0) { etat="2";
                            if (verbose) System.out.println(affiche.substring(0,pos)+normal+" ["+red+"  KO  "+normal+"]"  );
                            
                            }
                            else       { etat="1";
                            if (verbose) System.out.println(affiche.substring(0,pos)+normal+" ["+green+"OK"+normal+"]"   );
                            }
                            majetat(verbose2,stmt_maj,"ap",rs.getInt("ap_code"),etat);
                            if (!(rs.getString("ap_etat").equals(etat)))
                                log(verbose2,stmt_maj,"ap",rs.getInt("ap_code"),etat,rs.getString("ap_libelle"),rs.getString("ap_curl_texte"));
                            
                }
                // liste des machines ---------------------------------------------------------------------------------------------------------
                cyclem++;
                if (cyclem==0) {
                    cyclem=cyclem_nb;
                    if (verbose) System.out.println("");
                    if (verbose) System.out.println(gray+"--------------------"+normal);
                    if (verbose) System.out.println(gray+"----->      Machines     "+normal);
                    if (verbose) System.out.println(gray+"--------------------"+normal);
                    
                    qry=  "SELECT * FROM  machine where ma_zn_code="+argv[0] ;
                    //qry=  "SELECT * FROM  machine where ma_zn_code=5" ;
                    
                    rs = stmt_annu.executeQuery(qry);
                    runtime = Runtime.getRuntime();
                    etat="1";
                    j=0;
                    lg=0;
                    while(rs.next())
                    {   	j++;
                                lg=0;
                                if (verbose2) System.out.println(gray+"--------------------"+normal);
                                if (verbose2) System.out.println(gray+"machine:"+rs.getString("ma_libelle")+normal);
                                if (verbose2) System.out.println(gray+"machine:"+rs.getString("ma_libelle").length() +normal);
                                if (verbose2) System.out.println(gray+"--------------------"+normal);
                                process =
                                //runtime.exec(new String[] {"/bin/sh","-c", " curl "+ rs.getString("ap_url") +" --connect-timeout 5| grep  " + rs.getString("ap_curl_texte")  });
                                runtime.exec(new String[] {"/bin/sh","-c", " ping " + rs.getString("ma_libelle") +" -c 2 -w 5"  });
                                data_in = new DataInputStream(process.getInputStream());
                                DataInputStream data_er = new DataInputStream(process.getErrorStream());
                                
                                line_in = null;
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
                                String ma_libelle=rs.getString("ma_libelle")+"                                              ";
                                String affiche = gray + ma_libelle.substring(0,30) +   " "+
                                "                                "+
                                gray +"-"+ pourcent +  normal+
                                "                                                                                  ";
                                
                                //if (verbose) System.out.println("lg:"+lg);
                                if (pourcent.equals("100%") || pourcent.equals("unknown") )
                                { etat="2";
                                  if (verbose) System.out.println(affiche.substring(0,pos)+ " ["+red+"  KO  "+normal+"]" );
                                }
                                else
                                { etat="1";
                                  if (verbose) System.out.println(affiche.substring(0,pos)+  " ["+green+"  OK  "+normal+"]" );
                                }
                                majetat(verbose2,stmt_maj,"ma",rs.getInt("ma_code"),etat);
                                if (verbose2) System.out.println("test:"+red+rs.getString("ma_etat")+normal);
                                if (!(rs.getString("ma_etat").equals(etat)))
                                    log(verbose2,stmt_maj,"ma",rs.getInt("ma_code"),etat,rs.getString("ma_libelle"),rs.getString("ma_adresse"));
                    }
                }
                // liste des portails de la zone   ----------------------------------------------------------------------------------------------------
                cyclep++;
                if (cyclep==0) {
                    cyclep=cyclep_nb;
                    if (verbose) System.out.println("");
                    if (verbose) System.out.println(gray+"--------------------"+normal);
                    if (verbose) System.out.println(gray+"----->      Portails     "+normal);
                    System.out.println(gray+"--------------------"+normal);
                    qry=  "SELECT * FROM  portail,machine where  pt_ma_code=ma_code and ma_zn_code="+argv[0];
                    if (verbose2) System.out.println("qry:"+qry);
                    rs = stmt_annu.executeQuery(qry);
                    etat="1";
                    while(rs.next()) {   	
                    	if (verbose2) System.out.println("portail:"+rs.getString("pt_type"));
                        //etat=socket2(verbose,
                        //rs.getString("ma_libelle").substring(0,rs.getString("ma_libelle").indexOf(" ")),
                        //rs.getInt("pt_port"),
                        //rs.getString("pt_type").substring(0,rs.getString("pt_type").indexOf(" "))+","+rs.getString("pt_version"));
                        etat=socket2(verbose,
                                rs.getString("ma_libelle"),
                                rs.getInt("pt_port"),
                                rs.getString("pt_type")+","+rs.getString("pt_version"));
                        majetat(verbose2, stmt_maj,"pt", rs.getInt("pt_code"), etat);
                        if (!(rs.getString("pt_port_etat").equals(etat)))
                            log(verbose,stmt_maj,"pt",rs.getInt("pt_code"),etat,rs.getString("ma_libelle"),""+rs.getInt("pt_port"));
                    }
                    // liste des bdd de la zone   ----------------------------------------------------------------------------------------------------
                    if (verbose) System.out.println("");
                    if (verbose) System.out.println(gray+"--------------------"+normal);
                    if (verbose) System.out.println(gray+"----->           Bdd     "+normal);
                    System.out.println(gray+"--------------------"+normal);
                    qry=  "SELECT * FROM  bdd,machine where  bd_ma_code=ma_code and ma_zn_code="+argv[0];
                    rs = stmt_annu.executeQuery(qry);
                    etat="1";
                    while(rs.next()) {   	//if (verbose) System.out.println("bdd:"+rs.getString("bd_type"));
                        //    etat=socket(verbose,  rs.getString("ma_libelle").substring(0,rs.getString("ma_libelle").indexOf(" ")),rs.getInt("bd_port"));
                        
                    	etat=socket2(verbose,
                        rs.getString("ma_libelle"),
                        rs.getInt("bd_port"),
                        rs.getString("bd_type")+","+rs.getString("bd_repertoire"));
                        
                        majetat(verbose2,stmt_maj,"bd", rs.getInt("bd_code"), etat);
                        if ((rs.getString("bd_port_etat")==null)||!(rs.getString("bd_port_etat").equals(etat)))
                            log(verbose,stmt_maj,"bd",rs.getInt("bd_code"),etat,rs.getString("ma_libelle"),""+rs.getInt("bd_port"));
                    }
                }
                // liste des instances(webapp) de la zone   ----------------------------------------------------------------------------------------------------
                cyclei++;
                if (cyclei==0) {
                    cyclei=cyclei_nb;
                    if (verbose) System.out.println("");
                    if (verbose) System.out.println(gray+"--------------------"+normal);
                    if (verbose) System.out.println(gray+"----->    InstanceWA     "+normal);
                    System.out.println(gray+"--------------------"+normal);
                    qry=  "SELECT * FROM  instancewa,machine where  iw_ma_code=ma_code and ma_zn_code="+argv[0];
                    if (verbose2) System.out.println("----->  qry   " + gray + qry + normal);
                    rs = stmt_annu.executeQuery(qry);
                    etat="1";
                    while(rs.next()) {   	//if (verbose) System.out.println("pi:"+rs.getString("pi_port"));
                        etat=socket2(verbose,
                        rs.getString("ma_libelle"),
                        rs.getInt("iw_port1"),
                        rs.getString("iw_nom"));
                        majetat(verbose2, stmt_maj,"iw1", rs.getInt("iw_code"), etat);
                        if ((rs.getString("iw_port1_etat")==null)||!(rs.getString("iw_port1_etat").equals(etat)))
                            log(verbose,stmt_maj,"iw1",rs.getInt("iw_code"),etat,rs.getString("ma_libelle"),""+rs.getInt("iw_port1"));
                    }
                }
                // liste des instanceWA-bdd de la zone   ----------------------------------------------------------------------------------------------------
                cycleib++;
                if (cycleib==0) {
                    cycleib=cycleib_nb;
                    if (verbose) System.out.println("");
                    if (verbose) System.out.println(gray+"--------------------"+normal);
                    if (verbose) System.out.println(gray+"----->   InstanceWA-bdd     "+normal);
                    System.out.println(gray+"--------------------"+normal);
                    qry=  "SELECT instancewa.*, machine1.ma_sigle iw_machine,bdd.*,machine2.ma_libelle bd_machine "+
                    "FROM  instancewa,  machine machine1, bdd, machine machine2 where "+
                    "iw_ma_code = machine1.ma_code and iw_bd_code = bd_code and "+
                    "bd_ma_code = machine2.ma_code and machine1.ma_zn_code="+argv[0];
                    if (verbose2) System.out.println("----->  qry   " + gray + qry + normal);
                    
                    rs = stmt_annu.executeQuery(qry);
                    etat="1";
                    while(rs.next()) {   	//if (verbose) System.out.println("pi:"+rs.getString("pi_port"));
                        String libelle= rs.getString("iw_machine")+"->"+ rs.getString("bd_machine");
                        etat=socket2(verbose,
                        rs.getString("bd_machine"),
                        rs.getInt("iw_port2"),
                        rs.getString("iw_nom"));
                        majetat(verbose2, stmt_maj,"iw2", rs.getInt("iw_code"), etat);
                        if ((rs.getString("iw_port2_etat")==null)||!(rs.getString("iw_port2_etat").equals(etat)))
                            log(verbose,stmt_maj,"iw2",rs.getInt("iw_code"),etat,libelle,""+rs.getInt("iw_port2"));
                    }
                    // liste des portail_instanceWA de la zone   ----------------------------------------------------------------------------------------------------
                    if (verbose) System.out.println("");
                    if (verbose) System.out.println(gray+"--------------------"+normal);
                    if (verbose) System.out.println(gray+"----->Portail_InstWA"+normal);
                    System.out.println(gray+"--------------------"+normal);
                    qry=  "SELECT portail_instancewa.*, portail.*,machine1.ma_sigle pt_machine,instancewa.*, machine2.ma_libelle iw_machine "+
                    "FROM  portail_instancewa,portail,machine machine1,instancewa,machine machine2 where " +
                    "pi_pt_code = pt_code and pt_ma_code = machine1.ma_code and " +
                    "pi_iw_code = iw_code and iw_ma_code = machine2.ma_code and machine1.ma_zn_code="+argv[0];
                    if (verbose2) System.out.println("----->  qry   " + gray + qry + normal);
                    rs = stmt_annu.executeQuery(qry);
                    etat="1";
                    while(rs.next()) {   	//if (verbose) System.out.println("pi:"+rs.getString("pi_port"));
                        
                        String libelle= rs.getString("pt_machine")+"->"+ rs.getString("iw_machine");
                        etat=socket2(verbose,
                        rs.getString("iw_machine"),
                        rs.getInt("iw_port1"),
                        rs.getString("iw_nom"));
                        majetat(verbose2, stmt_maj,"pi", rs.getInt("pi_code"), etat);
                        if ((rs.getString("pi_port_etat")==null)||!(rs.getString("pi_port_etat").equals(etat)))
                            log(verbose,stmt_maj,"pi",rs.getInt("pi_code"),etat,libelle,""+rs.getInt("pi_port"));
                    }
                }
                
                // -------------------------------------------------fin des ctrls-----sleep--------------------------------------------------
                
                System.out.println(gray+"-------FIN D'UNE BOUCLE DE CONTROLE <----->passe en état dormant ----------"+normal);
                System.out.println(red+DateJ+normal+"stmt:");
               
                //stmt_annu.close();
                //stmt_maj.close();
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
            int timeoutMs = 3000;
            String mess=host+"                                                                     ";
            String mess1=mess.substring(0,30);
            String vport=""+port+"                                                    ";
            String vport1=vport.substring(0,30);
            String xtype=type+" ";
            String vtype="("+xtype.substring(0,xtype.indexOf(" "))+")"+"                           ";
            
            sock.connect(sockaddr, timeoutMs);
            sock.close();
            String affiche=gray+mess1+normal +vport1+gray+vtype.substring(0,30)+"                                            ";
            if (verbose) System.out.println(affiche.substring(0,pos)+normal+normal+" ["+green+"  OK  "+normal+"]"   );
        }
        catch (IOException ioe) {
            String mess=host+"                                                                     ";
            String mess1=mess.substring(0,30);
            String vport=""+port+"                                                    ";
            String vport1=vport.substring(0,30);
            String vtype1=type+"                            ";
            String vtype="("+vtype1.substring(0,vtype1.indexOf(" "))+")"+"                           ";
            String affiche=gray+mess1+normal +vport1+gray+vtype.substring(0,30)+"                                            ";
            
            if (verbose) System.out.println(affiche.substring(0,pos)+normal+normal+" ["+red+"  KO  "+normal+"]"  );
            
            //System.out.println(red+"pb: socket :" + host+ " port:" + port + normal );
            reponse = "2";
        }
        finally       {    }
        return reponse;
    }
    public static String log(boolean verbose,Statement stmt_maj, String type, int code, String etat, String libelle, String port)
    {	String reponse = "1";
        String xlibelle=libelle+ " ";
        if (verbose) System.out.println(red+"log:"+xlibelle+normal);
        String mess_err=type+" "+libelle.substring(0,xlibelle.indexOf(" "))+","+port+" etat="+etat;
        
        try {
            Date DateJour=new Date();
            int month=DateJour.getMonth()+1;
            String vdatedeb=DateJour.getDate()+"/"+month+"-"+DateJour.getHours()+":"+DateJour.getMinutes()+":"+DateJour.getSeconds();
            String select="insert into log (lg_type,lg_type_code,lg_etat,lg_etat_date,lg_libelle) values ('" +
            type + "'," +  code + ",'" +
            etat  + "','" + vdatedeb + "','" + mess_err + "')" ;
            stmt_maj.executeUpdate(select);
            //stmt_maj.close();
        }
        catch (SQLException ioe)         {  System.out.println(" log: ko");         }
        finally                          {    }
        return reponse;
    }
    
    
    
    public static  String majetat(boolean verbose, Statement stmt_maj, String type, int code, String etat)
    {	String reponse = "1";
        String select="";
        try {    //Statement stmt_maj = conn_annu.createStatement();
            Date DateJour=new Date();
            int month=DateJour.getMonth()+1;
            String vdate=DateJour.getDate()+"/"+month+"-"+DateJour.getHours()+":"+DateJour.getMinutes()+":"+DateJour.getSeconds();
            if (type.equals("ap")) select="update application        set   ap_etat ='" + etat + "' ,  ap_etat_date = '" +  vdate +           "' where ap_code=" + code +";" ;
            if (type.equals("pt")) select="update portail            set   pt_port_etat ='" + etat + "' ,  pt_port_etat_date = '" +  vdate + "' where pt_code=" + code +";" ;
            if (type.equals("ma")) select="update machine            set   ma_etat ='" + etat + "' ,  ma_etat_date = '" +  vdate +           "' where ma_code=" + code +";" ;
            if (type.equals("pi")) select="update portail_instancewa set   pi_port_etat ='" + etat + "' ,  pi_port_etat_date = '" +  vdate + "' where pi_code=" + code +";" ;
            if (type.equals("wa")) select="update webapp             set   wa_etat ='" + etat + "' ,  wa_etat_date = '" +  vdate +           "' where wa_code=" + code +";" ;
            if (type.equals("bd")) select="update bdd                set   bd_port_etat ='" + etat + "' ,  bd_port_etat_date = '" +  vdate + "' where bd_code=" + code +";" ;
            if (type.equals("iw1")) select="update instancewa        set   iw_port1_etat ='" + etat + "' ,  iw_port1_etat_date = '" +  vdate +"' where iw_code=" + code +";" ;
            if (type.equals("iw2")) select="update instancewa        set   iw_port2_etat ='" + etat + "' ,  iw_port2_etat_date = '" +  vdate +"' where iw_code=" + code +";" ;
            if (type.equals("ib")) select="update instancewa_bdd     set   ib_port_etat ='" + etat + "' ,  ib_port_etat_date = '" +  vdate + "' where ib_code=" + code +";" ;
             
            if (verbose) System.out.println(" majetat : "+select);
           // if (verbose) System.out.println(" stmt: " + stmt_maj.isCloseOnCompletion());
            stmt_maj.executeUpdate(select);
            if (verbose) System.out.println("finmaj");
            //stmt_maj.close();
        }
        catch (SQLException ioe)           {	 if (verbose)   System.out.println(red+" majetat :ko " + type+normal);
            reponse = "2" ;
        }
        finally                            {      }
        return reponse;
    }
    public static String alerte(boolean verbose,Statement stmt_maj, Statement stmt_maj2,String type, int code, int etat, String libelle, String port)
    {	String reponse = "1";
        String mess_err=type+" "+libelle.substring(0,libelle.indexOf(" "))+","+port+" etat="+etat;
        System.out.println("alerte:"+code);
        String insert="";
        try {
            Date DateJour=new Date();
            int month=DateJour.getMonth()+1;
            String vdatedeb="le " + DateJour.getDate()+"/"+month+" a " + DateJour.getHours()+":"+DateJour.getMinutes()+":"+DateJour.getSeconds();
            String qry=  "SELECT * from astreinte,application where as_ap_code=ap_code and  as_ap_code = " + code  ;
            System.out.println("alerte:"+qry+"etat:"+etat);
            ResultSet rs = stmt_maj.executeQuery(qry);
            while(rs.next()) {   	//if (verbose) System.out.println("pi:"+rs.getString("pi_port"));
                System.out.println("alerte:"+rs.getString("as_nom")+rs.getString("as_mail")+rs.getString("as_gsm"));
                String message="";
                
                if (etat==2)
                    message=" Arret de " + rs.getString("ap_libelle").substring(0,rs.getString("ap_libelle").indexOf(" ")) +   vdatedeb;
                else
                    message=" Démarrage de " + rs.getString("ap_libelle").substring(0,rs.getString("ap_libelle").indexOf(" "))   + vdatedeb; ;
                    if (rs.getString("as_mail").indexOf("@")!=-1)
                        insert="insert into alerte (al_type,al_adresse,al_message) values ('" +
                        rs.getString("as_mail") + "','" + rs.getString("as_nom") + "','" +
                        message + "');" ;
                    else
                        insert="insert into alerte (al_type,al_adresse,al_message) values ('" +
                        rs.getString("as_gsm") + "','" + rs.getString("as_nom") + "','" +
                        message + "');" ;
                    System.out.println("alerte insert :"+insert);
                    stmt_maj2.executeUpdate(insert);
            }
            stmt_maj.close();
        }
        catch (SQLException ioe)         {  System.out.println(""+insert+ioe.getErrorCode()+"mess"+ioe.getMessage());         }
        finally                          {        }
        return reponse;
    }
}




