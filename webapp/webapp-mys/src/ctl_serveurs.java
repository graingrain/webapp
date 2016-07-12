
import java.io.*;
import java.util.Date;
import java.sql.*;
import java.awt.*;
import java.net.*;


public class ctl_serveurs {
    // caracteres affichages
    public static String green =(char)27+"[1;32m";
    public static String normal=(char)27+"[0;39m";
    public static String clear =(char)27+"[2J";
    public static String red   =(char)27+"[1;31m";
    public static String yellow=(char)27+"[1;33m";
    public static String white =(char)27+"[1;37m";
    public static String gray  =(char)27+"[1;30m";
    public static String blue  =(char)27+"[1;34m";
    public static int cyclem        = -1;
    public static int cyclem_nb     = -12;
    public static int cyclep        = -1;
    public static int cyclep_nb     = -10;
    public static int cycleib       = -1;
    public static int cycleib_nb    = -15;
    public static int cyclei        = -1;
    public static int cyclei_nb     = -15;
    public static int pos      = 120;
    
    // ctlzonen
    // modif du 23 sept 2005 : gestion de cycles pour test machine
    public static  void main(String[] argv) throws IOException,SQLException  {
        String cnx_annuaire;
        if (argv[2] != null && argv[2].equals("TEST"))
            cnx_annuaire="jdbc:informix-sqli://grainville:1517/webapp:informixserver=se724;user=alain;password=trek250";
        else
            cnx_annuaire="jdbc:informix-sqli://mac5.in.ac-reunion.fr:1519/webapp:informixserver=bov724;user=alain;password=tiga";
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
        
        System.out.println( red+"surveillance   machine :"+argv[0]+normal);
        System.out.println(gray+"-----------------------"+normal);
        boolean verbose=false;
        if (argv[1].equals("V"))  {   verbose=true;   }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String cnx,qry;
            cnx = cnx_annuaire;
             
            Connection  conn_annu = DriverManager.getConnection(cnx_annuaire,"alain","trek250");
            //------------------------------------------------
            Statement stmt_annu = conn_annu.createStatement();
            Statement stmt_maj  = conn_annu.createStatement();
            Statement stmt_maj2  = conn_annu.createStatement();
            //------------------------------------------------
            qry="select * from application";
            ResultSet rs = stmt_annu.executeQuery(qry);
            
            while (boucle>0) {
                stmt_annu = conn_annu.createStatement();
                stmt_maj  = conn_annu.createStatement();
                boucle++;
                // liste des applications ---------------------------------------------------------------------------------------------------------
                Date DateJ = new Date();
                int month=DateJ.getMonth()+1;
                String vdate=DateJ.getDate()+"/"+month+"-"+DateJ.getHours()+":"+DateJ.getMinutes()+":"+DateJ.getSeconds();
                // recherche du poul  ----------------------------------------------------------------------------------------------------
                
                // maj table zone pour verif activite ctlzone
                 
                //
                int j=0;
                int k=0;
                int lg=0;
                
                // liste des machines ---------------------------------------------------------------------------------------------------------
                
                {
                    cyclem=cyclem_nb;
                    if (verbose) System.out.println(gray+"--------------------"+normal);
                    if (verbose) System.out.println(gray+"----->      Machines     "+normal);
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
                                String affiche = gray + rs.getString("ma_libelle")  +   ":"+
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
                                  if (verbose) System.out.println(affiche.substring(0,pos)+  " ["+green+"  OK  "+normal+"]" );
                                }
                                majetat(verbose,stmt_maj,"ma",rs.getInt("ma_code"),etat);
                                if (!(rs.getString("ma_etat").equals(etat)))
                                    log(verbose,stmt_maj,"ma",rs.getInt("ma_code"),etat,rs.getString("ma_libelle"),rs.getString("ma_adresse"));
                    }
                }
                 // -------------------------------------------------fin des ctrls-----sleep--------------------------------------------------
                
                System.out.println(gray+"-------FIN D'UNE BOUCLE DE CONTROLE <----->passe en état dormant ----------"+normal);
                System.out.println(red+DateJ+normal);
                stmt_annu.close();
                stmt_maj.close();
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
            String vtype="("+type.substring(0,type.indexOf(" "))+")"+"                           ";
            
            sock.connect(sockaddr, timeoutMs);
            sock.close();
            String affiche=gray+mess1+normal +vport1+blue+vtype.substring(0,30)+"                                            ";
            if (verbose) System.out.println(affiche.substring(0,pos)+normal+normal+" ["+green+"  OK  "+normal+"]"   );
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
        finally       {    }
        return reponse;
    }
    public static String log(boolean verbose,Statement stmt_maj, String type, int code, String etat, String libelle, String port)
    {	String reponse = "1";
        String mess_err=type+" "+libelle.substring(0,libelle.indexOf(" "))+","+port+" etat="+etat;
        
        try {
            Date DateJour=new Date();
            int month=DateJour.getMonth()+1;
            String vdatedeb=DateJour.getDate()+"/"+month+"-"+DateJour.getHours()+":"+DateJour.getMinutes()+":"+DateJour.getSeconds();
            String select="insert into log (lg_type,lg_type_code,lg_etat,lg_etat_date,lg_libelle) values ('" +
            type + "'," +  code + ",'" +
            etat  + "','" + vdatedeb + "','" + mess_err + "')" ;
            stmt_maj.executeUpdate(select);
            //stmt_annu.close();
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
            //stmt_maj.close();
        }
        catch (SQLException ioe)           {	    //System.out.println(" majetat :ko " + type);
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
            //stmt_maj.close();
        }
        catch (SQLException ioe)         {  System.out.println(""+insert+ioe.getErrorCode()+"mess"+ioe.getMessage());         }
        finally                          {        }
        return reponse;
    }
}




