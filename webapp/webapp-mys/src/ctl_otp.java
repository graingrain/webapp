import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.Date;
import java.util.StringTokenizer;

public class ctl_otp {
    
    public ctl_otp() {     }
    
    public static void main(String argv[])
    throws IOException, SQLException {
        String repapache   = argv[0] ;
        
        String nomfichlire = repapache + "/conf/httpd.conf";
        
        zone = Integer.parseInt(argv[1]);
        port = Integer.parseInt(argv[2]);
        //if (port==443) nomfichlire = repapache + "/conf/ssl.conf";
        Date DateJour = new Date();
        int month = DateJour.getMonth() + 1;
        String vdatedeb = DateJour.getDate() + "/" + month + " \340 " + DateJour.getHours() + ":" + DateJour.getMinutes();
        try {            FileOutputStream fos = new FileOutputStream("test_ecrire");   int j = 0;   fos.write(j);      }
        catch(IOException e) {     e.printStackTrace();      }
        System.out.println("-------------------------------------------");
        System.out.println("ctl apache SI1D httpd.conf le " + vdatedeb);
        System.out.println(nomfichlire);
        System.out.println("------------------------------------v3------");
        if(argv[3].equals("Y"))            verbose1 = true;
        if(argv[4].equals("Y"))            verbose2 = true;
        if(argv[5].equals("Y"))            verbose3 = true;
        if(argv[5].equals("Y"))            verbose4 = true;
        else {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(new String[] {"/bin/sh", "-c", "uname -n  "});
            DataInputStream data_in = new DataInputStream(process.getInputStream());
            for(String line_in = null; (line_in = data_in.readLine()) != null;)
                machine = line_in;
            process = runtime.exec(new String[] {"/bin/sh", "-c", ""+repapache+"/bin/httpd -v | grep version " });
            data_in = new DataInputStream(process.getInputStream());
            for(String line_in = null; (line_in = data_in.readLine()) != null;)
                xapache_version=line_in;
            StringTokenizer tokenizer = new StringTokenizer(xapache_version, " , \t");
            for(int t = 0; tokenizer.hasMoreTokens(); t++) {
                String token = tokenizer.nextToken();
                
                System.out.println("apache version: t "+t+":"+token);
                if(t == 2)     apache_version=token;
            }
            System.out.println("apache version:"+apache_version);
        }
        try {
        	try {
                InetAddress addr = InetAddress.getByName(machine);
                String hostname = addr.getHostName();
                machine = addr.getCanonicalHostName();
                host_ip= addr.getHostAddress();
            }
            catch(UnknownHostException e) { }
            //String select = "delete from logapache where machine ='" + machine + "';";
            //stmt_maj.executeUpdate(select);
            //code_machine = bddaddmachine(stmt_maj, machine, zone);
            //if(code_machine != 0)
            //  code_portail = bddaddportail(stmt_maj, code_machine, "apache", "version", nomfichlire, port);
            //if(verbose3)
            //  System.out.println("ajout du portail" + code_portail);
            String nomfichecrire = "sortie.conf";
            String type = "";
            String initiales = "";
            String datedeb = "";
            String datefin = "";
            String location = "";
            String host = "";
            int port = 0;
            String etat = "";
            String thost[] = new String[50];
            int tport[] = new int[50];
            String tloca[] = new String[50];
            String tname[] = new String[50];
            String twork[] = new String[50];
            for(int i = 0; i < 50; i++) {
                thost[i] = "                                                                                       ";
                tname[i] = "                                                                                       ";
                twork[i] = "                                                                                       ";
            }
            
            int tmax = 0;
            int n = 0;
            int i = 0;
            BufferedReader entree = new BufferedReader(new FileReader(nomfichlire));
            PrintWriter sortie = new PrintWriter(new FileWriter(nomfichecrire));
            String lignesortie = "";
            sortie.println(lignesortie);
            String ligne;
            while((ligne = entree.readLine()) != null) {
                i++;
                int hit = 0;
                if(verbose1)
                    System.out.println("-----ligne____:"  + ligne);
                if(ligne.length() > 15 && ligne.indexOf("Listen")!=-1) {
                    System.out.println(red + "            ---------------------------------------"  + normal);
                    System.out.println(red + "                   " + ligne + normal);
                    System.out.println(red + "            ---------------------------------------"  + normal);
                    
                }
            }
            if(ligne.length() > 15 && ligne.substring(0, 13).compareToIgnoreCase("JkWorkersFile") == 0) {
                if(verbose1)
                    System.out.println(blue + "JkWorkersFile:" + ligne.substring(ligne.indexOf(" ") + 1, ligne.length()) + normal);
                if(verbose1)
                    System.out.println("----------------------------------------------------------------------");
                String nom_include = "";
                StringTokenizer tokenizer = new StringTokenizer(ligne, " , \t");
                for(int t = 0; tokenizer.hasMoreTokens(); t++) {
                    String token = tokenizer.nextToken();
                    if(verbose3)
                        System.out.println("t:" + t + " " + token);
                    if(t == 1)
                        nom_include = token;
                }
                
                if(verbose3)   System.out.println("file:" + nom_include + "<---");
                BufferedReader entree_include = new BufferedReader(new FileReader(nom_include));
                host = "";
                port = 0;
                location = "";
                do {
                    String ligne2;
                    if((ligne2 = entree_include.readLine()) == null)
                        break;
                    if(verbose2)
                        System.out.println(gray + ligne2.substring(ligne2.indexOf(" ") + 1, ligne2.length()) + normal);
                    if(!ligne.substring(0, 1).equals("#") && ligne2.indexOf("host=") != -1) {
                        host = ligne2.substring(ligne2.indexOf("=") + 1, ligne2.length());
                        tokenizer = new StringTokenizer(ligne2, ".");
                        String name = "----";
                        int t = 0;
                        int trouve = 0;
                        while(tokenizer.hasMoreTokens()) {
                            String token = tokenizer.nextToken();
                            if(verbose3)
                                System.out.println("t:" + t + " " + token);
                            if(t == 1)
                                name = token;
                            t++;
                        }
                        for(i = 0; i <= tmax; i++)
                            if(twork[i].compareToIgnoreCase(name) == 0)
                                trouve = i;
                        
                        thost[trouve] = host;
                    }
                    if(!ligne.substring(0, 1).equals("#") && ligne2.indexOf("port=") != -1) {
                        port = Integer.parseInt(ligne2.substring(ligne2.indexOf("=") + 1, ligne2.length()));
                        tokenizer = new StringTokenizer(ligne2, ".");
                        String name = "----";
                        int t = 0;
                        int trouve = 0;
                        while(tokenizer.hasMoreTokens()) {
                            String token = tokenizer.nextToken();
                            if(verbose3)
                                System.out.println("t:" + t + " " + token);
                            if(t == 1)
                                name = token;
                            t++;
                        }
                        for(i = 0; i <= tmax; i++)
                            if(twork[i].compareToIgnoreCase(name) == 0)
                                trouve = i;
                        
                        tport[trouve] = port;
                    }
                    if(!ligne.substring(0, 1).equals("#") && ligne2.indexOf("balance_workers=") != -1) {
                        tmax++;
                        String work = ligne2.substring(ligne2.indexOf("=") + 1, ligne2.length());
                        twork[tmax] = work;
                        tokenizer = new StringTokenizer(ligne2, ".");
                        int t = 0;
                        while(tokenizer.hasMoreTokens()) {
                            String token = tokenizer.nextToken();
                            if(verbose3)
                                System.out.println("t:" + t + " " + token);
                            if(t == 1)
                                tname[tmax] = token;
                            t++;
                        }
                    }
                } while(true);
            }
            if(ligne.length() > 15 && ligne.substring(0, 7).equals("Include") && ((ligne.indexOf("weblogic") != -1 ) ||(ligne.indexOf("ct-httpd") != -1 ) ) ) {
                if(verbose1)
                    System.out.println(blue + "weblogic: " + ligne.substring(ligne.indexOf(" ") + 1, ligne.length()) + "<----" + normal);
                if(verbose1)
                    System.out.println("----------------------------------------------------------------------");
                String nom_include   = "";
                String include="";
                String type_host     = "";
                StringTokenizer tokenizer = new StringTokenizer(ligne, " , \t");
                for(int t = 0; tokenizer.hasMoreTokens(); t++) {
                    String token = tokenizer.nextToken();
                    if(verbose3)   System.out.println("t:" + t + " = " + token);
                    if(t == 1)     include = token;
                }
                nom_include=repapache + "/" + include;
                if (include.substring(0,1).equals("/")) nom_include=include;
                System.out.println(yellow + nom_include);
                if(verbose3)       System.out.println("jai trouve include=" + repapache + "/" +nom_include);
                //BufferedReader entree_include = new BufferedReader(new FileReader(repapache + "/"+nom_include));
                BufferedReader entree_include = new BufferedReader(new FileReader(nom_include));
                
                host = "";
                port = 0;
                location = "";
                do {
                    String ligne2;
                    if((ligne2 = entree_include.readLine()) == null)
                        break;
                    if(verbose2)
                        System.out.println(green + ligne2 + normal);
                    if(ligne2.length() > 0 && ligne2.indexOf("<Location") != -1) {
                        tokenizer = new StringTokenizer(ligne2, " , \t");
                        for(int t = 0; tokenizer.hasMoreTokens(); t++) {
                            String token = tokenizer.nextToken();
                            if(verbose3)  System.out.println("---->t:" + t + " " + token);
                            if(t == 1)    location = token;
                        }
                        if(verbose3)      System.out.println(gray + "Location=" + location + normal);
                    }
                    if(ligne2.length() > 0 && !ligne2.substring(0, 1).equals("#") && ligne2.indexOf("WebLogicHost") != -1) {
                        type_host="host   ";
                        tokenizer = new StringTokenizer(ligne2, " , \t");
                        for(int t = 0; tokenizer.hasMoreTokens(); t++) {
                            String token = tokenizer.nextToken();
                            if(verbose3)
                                System.out.println("---->t:" + t + " " + token);
                            if(t == 1)
                                host = token;
                        }
                        
                    }
                    if(ligne2.length() > 0 && !ligne2.substring(0, 1).equals("#") && ligne2.indexOf("WebLogicPort") != -1) {
                        tokenizer = new StringTokenizer(ligne2, " , \t");
                        for(int t = 0; tokenizer.hasMoreTokens(); t++) {
                            String token = tokenizer.nextToken();
                            if(verbose3)
                                System.out.println("---->t:" + t + " " + token);
                            if(t == 1)
                                port = Integer.parseInt(token);
                        }
                        etat = socket2( type_host, host, port, "Weblogic   :" + location + "                ");
                    }
                    if(ligne2.length() > 0 && !(ligne2.indexOf("#")!=-1)  && ligne2.indexOf("WebLogicCluster") != -1) {
                        type_host="cluster";
                        tokenizer = new StringTokenizer(ligne2, " , \t");
                        for(int t = 0; tokenizer.hasMoreTokens(); t++) {
                            String token = tokenizer.nextToken();
                            if(verbose3)   System.out.println("---->t:" + t + " " + token);
                            if(t > 0) {
                                StringTokenizer tokcluster = new StringTokenizer(token, ":");
                                for(int s = 0; tokcluster.hasMoreTokens(); s++) {
                                    String tokencluster = tokcluster.nextToken();
                                    if(s == 0) host = tokencluster;
                                    if(s == 1) port = Integer.parseInt(tokencluster);
                                }
                                etat = socket2( type_host, host, port, "Weblogic   :" + location + "                ");
                                type_host="       ";
                            }
                        }
                        
                    }
                    if(ligne2.length() > 0 && !(ligne2.indexOf("#")!=-1)  && ligne2.indexOf("ProxyPass ") != -1) {
                        type_host="proxyP ";
                        tokenizer = new StringTokenizer(ligne2, " , \t");
                        for(int t = 0; tokenizer.hasMoreTokens(); t++) {
                            String token = tokenizer.nextToken();
                            if(verbose3)   System.out.println("---->t:" + t + " " + token);
                            if(t == 1) {
                                StringTokenizer tokcluster = new StringTokenizer(token, ":");
                                for(int s = 0; tokcluster.hasMoreTokens(); s++) {
                                    String tokencluster = tokcluster.nextToken();
                                    if(s == 1) host = tokencluster.substring(2);
                                    if(s == 2) port = Integer.parseInt(tokencluster.substring(0,tokencluster.indexOf("/")));
                                }
                                etat = socket2( type_host, host, port, "ProxyPass   :" + location + "                ");
                            }
                        }
                        if(host.length() > 0 && port > 0) {
                            //etat = socket2(stmt_maj, type_host, host, port, "ProxyPass   :" + location + "                ");
                            host = "";
                            port = 0;
                            location = "";
                        }
                    }
                    
                    if(host.length() > 0 && port > 0) {
                        //etat = socket2(stmt_maj,type_host, host, port, "Weblogic   :" + location + "                ");
                        host = "";
                        port = 0;
                        location = "";
                    }
                }
                while(true);
            }
            if(ligne.length() > 15 && ligne.substring(0, 7).equals("Include") && ligne.indexOf("tomcat") != -1) {
                if(verbose2)
                    System.out.println( blue + "v2 "+"tomcat: " + ligne.substring(ligne.indexOf(" ") + 1, ligne.length()) + normal);
                if(verbose2)
                    System.out.println(gray + "v2 " + "--------------------------------------------------------------------------" + normal);
                String nom_include = ligne.substring(ligne.indexOf(" ") + 1, ligne.length());
                BufferedReader entree_include = new BufferedReader(new FileReader(nom_include));
                host = "";
                port = 0;
                location = "";
                String ligne2;
                while((ligne2 = entree_include.readLine()) != null) {
                    if(verbose2)
                        System.out.println(gray + "v2 " + ligne2 + normal);
                    if(ligne2.length() > 13 && ligne2.substring(0, 12).equals("WebAppDeploy")) {
                        StringTokenizer tokenizer = new StringTokenizer(ligne2, " , \t");
                        for(int t = 0; tokenizer.hasMoreTokens(); t++) {
                            String token = tokenizer.nextToken();
                            if(verbose3)
                                System.out.println( "v3 " + "---->t:" + t + " " + token);
                            if(t == 3)
                                location = token + "                                                            ";
                        }
                        
                        if(verbose3)
                            System.out.println("v3 " +"---->location:" + location);
                        if(host.length() > 0 && port > 0 && location.length() > 0) {
                            etat = socket2(  "webappD", host, port, "tomcat warp:" + location);
                            location = "";
                        }
                    }
                    if(ligne2.length() > 16 && ligne2.substring(0, 16).equals("WebAppConnection")) {
                        host = ligne2.substring(ligne2.indexOf("warp") + 5, ligne2.length());
                        String whost = host;
                        if(verbose3)
                            System.out.println("v3 " + "---->whost:" + whost);
                        host = whost.substring(0, whost.indexOf(":"));
                        port = Integer.parseInt(whost.substring(whost.indexOf(":") + 1, whost.length()));
                    }
                    if(ligne2.length() > 6 && ligne2.indexOf("Mount") != -1) {
                        StringTokenizer tokenizer = new StringTokenizer(ligne2, " , \t");
                        int t = 0;
                        int trouve = 0;
                        String name = "----";
                        if(verbose3)
                            System.out.println("v3 " +"---->jkmount:" + ligne2);
                        while(tokenizer.hasMoreTokens()) {
                            String token = tokenizer.nextToken();
                            if(verbose3)
                                System.out.println("v3 " +"---->t:" + t + " " + token);
                            if(t == 2)
                                name = token;
                            if(t == 1)
                                location = token + "                                                            ";
                            t++;
                        }
                        for(i = 0; i <= tmax; i++)
                            if(tname[i].compareToIgnoreCase(name) == 0)
                                trouve = i;
                        
                        tloca[trouve] = location;
                        etat = socket2( "tomcat" , thost[trouve], tport[trouve], "tomcat(modjk)   :" + tloca[trouve]);
                    }
                }
            }
       
        if(verbose3)
            for(i = 1; i < tmax + 1; i++)
                System.out.println("v3 " +"" + i + tname[i] + "\t p=" + tport[i] + "\t h=" + thost[i] + " w=" + twork[i] + "\t l=" + tloca[i]);
        
        System.out.println(lignesortie);
        sortie.close();
    }
    catch(Exception e) {
        String msgErr = "Erreur  : " + e.getMessage();
        System.out.println(msgErr);
        e.printStackTrace();
        return;
    }
}

public static String socket2(  String type_host, String host, int port, String type)
throws IOException {
    String reponse = "1";
    if(port == 0)
        return reponse;
    try {
        if(verbose3)    System.out.println("v3 " +"test socket vers " + type_host + " " + host + ":" + port + " type:" + type);
        InetAddress addr = InetAddress.getByName(host);
        java.net.SocketAddress sockaddr = new InetSocketAddress(addr, port);
        String hostname = addr.getHostName();
        host   = addr.getCanonicalHostName();
        host_ip= addr.getHostAddress();
        //System.out.println("ip:"+host_ip);
        Socket sock = new Socket();
        int timeoutMs = 1000;
        String mess = type_host+ " " + host + "                                                                     ";
        if(verbose3)     System.out.println("---->mess" + mess);
        String mess1 = mess.substring(0, 35);
        String vport = "" + port + "                                                    ";
        if(verbose3)     System.out.println("---->vport" + port);
        String vport1 = vport.substring(0, 10);
        if(verbose3)     System.out.println("---->type" + type + "<---");
        String vtype = "(" + type.substring(0, type.indexOf("         ")) + ")" + "                                                                                                                               ";
        if(verbose3)     System.out.println("---->vtype" + vtype);
        sock.connect(sockaddr, timeoutMs);
        sock.close();
        String affiche = blue + mess1 + red + vport1 + blue + vtype.substring(0, 50) + "                                                                                                                                           ";
        System.out.println(affiche.substring(0, pos) + normal + normal + " [" + green + "  OK  " + normal + "]");
        //log(stmt_maj, vport, vtype, host, "1");
    }
    catch(IOException ioe) {
        String mess = type_host+ " " + host + "                                                                     ";;
        String mess1 = mess.substring(0, 35);
        String vport = "" + port + "                                                    ";
        String vport1 = vport.substring(0, 10);
        String vtype = "(" + type.substring(0, type.indexOf("         ")) + ")" + "                                                                                                                               ";
        String affiche = blue + mess1 + red + vport1 + blue + vtype.substring(0, 50) + "                                                                                      ";
        System.out.println(affiche.substring(0, pos) + normal + normal + " [" + red + "  KO  " + normal + "]");
        reponse = "2";
        //log(stmt_maj, vport, vtype, host, "2");
    }
    finally {
        
    }
    return reponse;
}

public static String log(Statement stmt_maj, String port, String service, String host, String etat) {
    String reponse = "1";
    
    Date DateJour = new Date();
    int month = DateJour.getMonth() + 1;
    String vdatedeb = DateJour.getDate() + "/" + month + "-" + DateJour.getHours() + ":" + DateJour.getMinutes() + ":" + DateJour.getSeconds();
    String select = "insert into logapache (machine,port,date,service,webapp,etat) values ('" + machine + "'," + port + ",'" + vdatedeb + "','" + service + "','" + host + "','" + etat + "')";
    if(verbose3)
        System.out.println("sql:" + select);
    //stmt_maj.executeUpdate(select);
    code_machine = bddaddmachine(stmt_maj, host, zone);
    if(code_machine != 0)
        code_webapp = bddaddwebapp(stmt_maj, code_machine, service);
    if(code_webapp != 0)
        code_domaine = bddadddomaine(stmt_maj, code_webapp, service);
    if(code_domaine != 0)
        code_instancewa = bddaddIW(stmt_maj, code_domaine, port);
    if(verbose3)
        System.out.println("ajout de PIW" + code_portail + " " + code_instancewa);
    if(code_instancewa != 0)
        code_portailIw = bddaddPiWa(stmt_maj, code_portail, code_instancewa, port, etat);
    if(code_portailIw != 0)
        code_appli = bddaddApplication(stmt_maj, code_portail, code_portailIw, service);
    
    
    return reponse;
}

public static int bddaddmachine(Statement stmt_maj, String machine, int zone) {
    int reponse = 0;
    try {
        Date DateJour = new Date();
        int month = DateJour.getMonth() + 1;
        String vdatedeb = DateJour.getDate() + "/" + month + "-" + DateJour.getHours() + ":" + DateJour.getMinutes() + ":" + DateJour.getSeconds();
        String qry = "select * from  machine where ma_libelle = '" + machine + "'";
        ResultSet rs = stmt_maj.executeQuery(qry);
        int nb = 0;
        do {
            if(!rs.next())
                break;
            nb++;
            reponse = rs.getInt("ma_code");
            if(verbose3)
                System.out.println("sqladdbddmachine:" + rs.getString("ma_libelle"));
        } while(true);
        if(nb == 0) {
            String sigle = "";
            StringTokenizer tokenizer = new StringTokenizer(machine, " , \t, .");
            for(int t = 0; tokenizer.hasMoreTokens(); t++) {
                String token = tokenizer.nextToken();
                if(t == 0)
                    sigle = token;
            }
            select = "insert into machine (ma_libelle,ma_zn_code,ma_sigle,ma_etat_date,ma_adresse) values ('" + machine + "', " + zone + ",'" + sigle + "','" + vdatedeb + "','" + host_ip + "')";
        } else {
            select = "update machine set ma_etat='1', ma_zn_code=" + zone + ",  ma_etat_date='" + vdatedeb + "',  ma_adresse='" + host_ip + "' where ma_libelle = '" + machine + "';";
        }
        if(verbose3)      System.out.println("sqladdbddmachine:" + select);
        //stmt_maj.executeUpdate(select);
    }
    catch(SQLException ioe) {
        System.out.println("erreur sql:" + ioe);
    }
    finally {
        
    }
    return reponse;
}

public static int bddaddportail(Statement stmt_maj, int machine, String type, String version, String repertoire, int port) {
    int reponse = 0;
    try {
        Date DateJour = new Date();
        int month = DateJour.getMonth() + 1;
        String vdatedeb = DateJour.getDate() + "/" + month + "-" + DateJour.getHours() + ":" + DateJour.getMinutes() + ":" + DateJour.getSeconds();
        String qry = "select * from  portail where pt_ma_code = " + machine + " and  pt_port =" + port;
        ResultSet rs = stmt_maj.executeQuery(qry);
        int nb = 0;
        do {
            if(!rs.next())
                break;
            nb++;
            reponse = rs.getInt("pt_code");
            if(verbose3)
                System.out.println("sqladdbddportail:" + rs.getString("pt_type"));
        } while(true);
        if(nb == 0)
            select = "insert into portail (pt_ma_code,pt_type,pt_version,pt_repertoire,pt_port,pt_port_etat,pt_port_etat_date) values (" + machine + ",'" + type + "','" + apache_version + "','" + repertoire + "'," + port + ",'1','" + vdatedeb + "')";
        else
            select = "update portail set pt_port_etat='1',   pt_port_etat_date='" + vdatedeb + "', pt_version ='" + apache_version + "' where pt_ma_code=" + machine + " and pt_port=" + port;
        if(verbose3)
            System.out.println("sqladdbddmachine:" + select);
        //stmt_maj.executeUpdate(select);
    }
    catch(SQLException ioe) {
        System.out.println("erreur sql:" + ioe);
    }
    finally {
        
    }
    return reponse;
}

public static int bddaddwebapp(Statement stmt_maj, int machine, String type) {
    int reponse = 0;
    try {
        Date DateJour = new Date();
        int month = DateJour.getMonth() + 1;
        String vdatedeb = DateJour.getDate() + "/" + month + "-" + DateJour.getHours() + ":" + DateJour.getMinutes() + ":" + DateJour.getSeconds();
        String sigle = "";
        StringTokenizer tokenizer = new StringTokenizer(type, " , \t, ), :, ( ");
        for(int t = 0; tokenizer.hasMoreTokens(); t++) {
            String token = tokenizer.nextToken();
            if(t == 0)
                sigle = token;
        }
        
        String qry = "select * from  webapp where wa_ma_code = " + machine + " and  wa_type ='" + sigle + "'";
        ResultSet rs = stmt_maj.executeQuery(qry);
        int nb = 0;
        do {
            if(!rs.next())
                break;
            nb++;
            reponse = rs.getInt("wa_code");
            if(verbose3)
                System.out.println("sqladdbddwebapp:" + rs.getString("wa_type"));
        } while(true);
        if(nb == 0)
            select = "insert into webapp (wa_ma_code,wa_type) values (" + machine + ",'" + sigle + "')";
        else
            select = "update webapp set wa_ma_code=" + machine + ",wa_type='" + sigle + "' where wa_ma_code=" + machine + " and wa_type='" + sigle + "'";
        if(verbose3)
            System.out.println("sqladdbddwebapp:" + select);
        //stmt_maj.executeUpdate(select);
    }
    catch(SQLException ioe) {
        System.out.println("erreur sql:" + ioe);
    }
    finally {
        
    }
    return reponse;
}

public static int bddadddomaine(Statement stmt_maj, int machine, String type) {
    int reponse = 0;
    try {
        Date DateJour = new Date();
        int month = DateJour.getMonth() + 1;
        String vdatedeb = DateJour.getDate() + "/" + month + "-" + DateJour.getHours() + ":" + DateJour.getMinutes() + ":" + DateJour.getSeconds();
        String sigle = "";
        StringTokenizer tokenizer = new StringTokenizer(type, " , \t, ), :, ( ");
        for(int t = 0; tokenizer.hasMoreTokens(); t++) {
            String token = tokenizer.nextToken();
            if(t == 0)
                sigle = token;
        }
        
        String qry = "select * from  domainewa where do_wa_code = " + machine + " and  do_nom ='" + sigle + "'";
        ResultSet rs = stmt_maj.executeQuery(qry);
        int nb = 0;
        do {
            if(!rs.next())
                break;
            nb++;
            reponse = rs.getInt("do_code");
            if(verbose3)
                System.out.println("sqladdbddwebapp:" + rs.getString("do_nom"));
        } while(true);
        if(nb == 0)
            select = "insert into domainewa (do_wa_code,do_nom) values (" + machine + ",'" + sigle + "')";
        else
            select = "update domainewa set do_wa_code=" + machine + ",do_nom='" + sigle + "' where do_wa_code=" + machine + " and do_nom='" + sigle + "'";
        if(verbose3)
            System.out.println("sqladdbddwebapp:" + select);
        //stmt_maj.executeUpdate(select);
    }
    catch(SQLException ioe) {
        System.out.println("erreur sql:" + ioe);
    }
    finally {
        
    }
    return reponse;
}

public static int bddaddIW(Statement stmt_maj, int machine, String port) {
    int reponse = 0;
    try {
        Date DateJour = new Date();
        int month = DateJour.getMonth() + 1;
        String vdatedeb = DateJour.getDate() + "/" + month + "-" + DateJour.getHours() + ":" + DateJour.getMinutes() + ":" + DateJour.getSeconds();
        String qry = "select * from  instancewa where iw_do_code = " + machine + " and  iw_port1 =" + port;
        ResultSet rs = stmt_maj.executeQuery(qry);
        int nb = 0;
        while(rs.next()) {
            nb++;
            reponse = rs.getInt("iw_code");
        }
        if(nb == 0)
            select = "insert into instancewa (iw_do_code,iw_port1) values (" + machine + ",'" + port + "')";
        else
            select = "update instancewa set iw_do_code=" + machine + ",iw_port1=" + port + " where iw_do_code=" + machine + " and iw_port1=" + port;
        if(verbose3)
            System.out.println("sqladdbddIW:" + select);
        //stmt_maj.executeUpdate(select);
    }
    catch(SQLException ioe) {
        System.out.println("erreur sql:" + ioe);
    }
    finally {
        
    }
    return reponse;
}

public static int bddaddPiWa(Statement stmt_maj, int portail, int instance, String port, String etat) {
    int reponse = 0;
    try {
        Date DateJour = new Date();
        int month = DateJour.getMonth() + 1;
        String vdatedeb = DateJour.getDate() + "/" + month + "-" + DateJour.getHours() + ":" + DateJour.getMinutes() + ":" + DateJour.getSeconds();
        String qry = "select * from  portail_instancewa where pi_pt_code =" + portail + " and  pi_iw_code = " + instance + " and  pi_port =" + port;
        ResultSet rs = stmt_maj.executeQuery(qry);
        int nb = 0;
        while(rs.next()) {
            nb++;
            reponse = rs.getInt("pi_code");
        }
        if(nb == 0)
            select = "insert into portail_instancewa (pi_pt_code,pi_iw_code,pi_port,pi_port_etat,pi_port_etat_date) values (" + portail + "," + instance + "," + port + ",'" + etat + "','" + vdatedeb + "')";
        else
            select = "update portail_instancewa set pi_pt_code=" + portail + " ,pi_iw_code=" + instance + ",pi_port=" + port + ",pi_port_etat='" + etat + "',pi_port_etat_date='" + vdatedeb + "' where pi_pt_code=" + portail + " and pi_iw_code = " + instance + " and pi_port=" + port;
        if(verbose3)
            System.out.println("sqladdbddPIWA:" + select);
        //stmt_maj.executeUpdate(select);
    }
    catch(SQLException ioe) {
        System.out.println("erreur sql:" + ioe);
    }
    finally {
        
    }
    return reponse;
}

public static int bddaddApplication(Statement stmt_maj, int portail, int portailIW, String type) {
    int reponse = 0;
    try {
        Date DateJour = new Date();
        int month = DateJour.getMonth() + 1;
        String vdatedeb = DateJour.getDate() + "/" + month + "-" + DateJour.getHours() + ":" + DateJour.getMinutes() + ":" + DateJour.getSeconds();
        String sigle = "";
        StringTokenizer tokenizer = new StringTokenizer(type, " , \t, ), :, ( ");
        for(int t = 0; tokenizer.hasMoreTokens(); t++) {
            String token = tokenizer.nextToken();
            if(t == 1)
                sigle = token;
        }
        
        String qry = "select * from  application where ap_pi_code = " + portailIW + " and  ap_pt_code =" + portail;
        ResultSet rs = stmt_maj.executeQuery(qry);
        int nb = 0;
        while(rs.next()) {
            nb++;
            reponse = rs.getInt("ap_code");
        }
        if(nb == 0)
            select = "insert into application (ap_pt_code,ap_pi_code,ap_libelle,ap_visible,ap_controle) values (" + portail + "," + portailIW + ",'" + type + "',1,1)";
        else
            select = "update application set ap_pt_code=" + portail + ",ap_pi_code=" + portailIW + ",ap_libelle='" + sigle + "' where ap_pt_code=" + portail + " and ap_pi_code=" + portailIW;
        if(verbose3)
            System.out.println("sqladdbddappli:" + select);
        //stmt_maj.executeUpdate(select);
    }
    catch(SQLException ioe) {
        System.out.println("erreur sql:" + ioe);
    }
    finally {
        
    }
    return reponse;
}
public static String machine = "";
public static String apache_version = "";
public static String xapache_version = "";
public static String host_ip = "";

public static String green = "\033[1;32m";
public static String normal = "\033[0;39m";
public static String clear = "\033[2J";
public static String red = "\033[1;31m";
public static String yellow = "\033[1;33m";
public static String white = "\033[1;37m";
public static String gray = "\033[1;30m";
public static String blue = "\033[1;34m";
public static String select = "";
public static int pos = 120;
public static int zone = 0;
public static int port = 0;
public static boolean verbose1 = false;
public static boolean verbose2 = false;
public static boolean verbose3 = false;
public static boolean verbose4 = false;
public static int code_machine = 0;
public static int code_portail = 0;
public static int code_webapp = 0;
public static int code_domaine = 0;
public static int code_instancewa = 0;
public static int code_portailIw = 0;
public static int code_appli = 0;

}
