//package servlets;

import javax.servlet.*;

import netscape.ldap.*;

import javax.servlet.http.*;

import java.io.*;
import java.sql.*;

import javax.sql.*;

import java.util.Date;
import java.util.*;

import uti2.*;
import db.*;
import metier.*;

import java.lang.*;

import javabeans.*;

import javax.servlet.http.*;
import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.directory.InitialDirContext;

 
public class Dispatcher extends HttpServlet {
    static public PoolConnect pool;
    static public PoolConnect poolannu;
    private static final String jspPbGeneral = "/PbGeneral.jsp";
    private static ServletContext sc;
    private static String WEBMASTER_EMAIL="hhhhhhhhhhh";
    private boolean debugFlag;
    private static int nb_lignes;
    private static String cnx_annuaire;
    private static String cnx_ddt;
    private static String cnx;
    private static String liste1;
    private static String liste2;
    private static String ldap;
    private static String ldapmode;
    private static String ldapdm;
    private static String ldappw;
    private static String ldapbasedn;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("-----------git 111111111111111111111111  ------------------------------------");
        System.out.println("-----------webapp  juin 2016a 64bits  x  ------------------------------------");
        System.out.println("initserveur:"+InitServeur.dbgInitParameter(this));
        Const.dbg = InitServeur.valeurDebug(this);
        System.out.println("CneServlet.init Const.dbg="+Const.dbg);
        
        sc = this.getServletContext();
        
        System.out.println("-----------webapp  juin 2016a 64bits  x  ------------------------------------");
        // get the WEBMASTER_EMAIL Context Parameter and store it a global variable for the servlet to access
        //WEBMASTER_EMAIL = (String) sc.getInitParameter("webmaster");
        //WEBMASTER_EMAIL = sc.getContext("webmaster");
        // set the ServletDebug init-parameter, this determines if messages are sent to the
        //    console when actions are received by the servlet
        
        Context ctx = null;
        System.out.println("Recherche des valeurs du contexte");
        try {            ctx = new InitialContext();
        } catch (NamingException e) { System.out.println("Couldn't build an initial context : " + e);
        return;
        }
        
        try {
            Context envCtx = (Context) ctx.lookup("java:/comp/env/");
            System.out.println("list() on /comp/env Context : ");
              
            //Object nb_lignes1 = ctx.lookup("java:/comp/env/nb_lignes");
            nb_lignes = Integer.parseInt(ctx.lookup("java:/comp/env/nb_lignes").toString()) ;
            cnx_annuaire = ctx.lookup("java:/comp/env/cnx_annuaire").toString() ;
            cnx_ddt      = ctx.lookup("java:/comp/env/cnx_ddt").toString() ;
            System.out.println("nb de lignes dans tableau: " +  nb_lignes);
            System.out.println("param de cnx annuaire    : " +  cnx_annuaire);
            String mode=ctx.lookup("java:/comp/env/ldapmode").toString();
            ldap=ctx.lookup("java:/comp/env/ldapsrv"+mode).toString();
            System.out.println("serveur ldap="+ldap+".");
            ldapmode=ctx.lookup("java:/comp/env/ldapmode").toString();
            ldapdm=ctx.lookup("java:/comp/env/ldapdm"+mode).toString();
            ldappw=ctx.lookup("java:/comp/env/ldappw"+mode).toString();
            ldapbasedn=ctx.lookup("java:/comp/env/ldapbasedn"+mode).toString();
            System.out.println("serveur basedn="+ldapbasedn+".");
            liste1=ctx.lookup("java:/comp/env/liste1").toString();
            liste2=ctx.lookup("java:/comp/env/liste2").toString();
            ServletContext servletContext = config.getServletContext();
            String str_conn="jdbc:mysql://localhost/webapp";
            str_conn="jdbc:mysql://bd1.in.ac-reunion.fr/webapp";
            System.out.print("driver   _________ : "+str_conn);
            Class.forName("com.mysql.jdbc.Driver") ;
              
            
            Connection  conn = DriverManager.getConnection(str_conn,"alain","trek250");
           
            System.out.print("driver ok ______________________ : " );
            String racineServeurWeb = servletContext.getRealPath("/");
            System.out.println("racineServeurWeb "+racineServeurWeb);
            Log.logInit(racineServeurWeb + "WEB-INF/");
            
            pool = InitServeur.litconfigProperties(this, racineServeurWeb, "config");
            TacheExtraction.dbUrlLocale = InitServeur.getDbURL();
            OkConn okConn = pool.wgetConnection();
            conn = okConn.conn;
            Statement st = conn.createStatement();
            String cnx,qry;
            qry=  "SELECT * FROM param where code='DEBAT'";
            ResultSet rs = st.executeQuery(qry);
            while(rs.next())
            {   	 String titre=rs.getString("titre");
                         while (titre.indexOf("`",0) != -1)
                             titre=titre.substring(0,titre.indexOf("`",0))+ "'"+titre.substring(titre.indexOf("`",0)+1);
                         servletContext.setAttribute("titre",titre);
                         
                         titre=rs.getString("message1");
                         while (titre.indexOf("`",0) != -1)
                             titre=titre.substring(0,titre.indexOf("`",0))+ "'"+titre.substring(titre.indexOf("`",0)+1);
                         servletContext.setAttribute("message1",titre);
                         
                         titre=rs.getString("message2");
                         while (titre.indexOf("`",0) != -1)
                             titre=titre.substring(0,titre.indexOf("`",0))+ "'"+titre.substring(titre.indexOf("`",0)+1);
                         servletContext.setAttribute("message2",titre);
                         
                         titre=rs.getString("message3");
                         while (titre.indexOf("`",0) != -1)
                             titre=titre.substring(0,titre.indexOf("`",0))+ "'"+titre.substring(titre.indexOf("`",0)+1);
                         servletContext.setAttribute("message3",titre);
            }
            pool.wfreeConnection(okConn);   // libération de la connexion
        }
        catch(Exception e){
            e.printStackTrace();
            Log.logErr(" l'application n'est pas correctement initialisée");
            System.err.println("Erreur fatale pendant l'init de la servlet Dispatcher");
        }
    }
    
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");
        String next = jspPbGeneral;
        String msgErr = "";
        String operation = request.getParameter("operation");
        // retrieve the parameter indicating the type of operation
        System.out.println("---------------------------- service dispatcher WEBAPP ----------------------------- " +ldap);
        System.out.println("Operation:"+operation);
        HttpSession session = request.getSession(true);
        //if(request.getSession(true) == null)
        //  System.out.println("session true null");
        //RequestDispatcher rd = sc.getRequestDispatcher("/home.jsp?message=plusdecnx");
        //rd.forward(request, response);
        //else System.out.println("session true not null");
        Date created = new Date(session.getCreationTime());
        Date accessed = new Date(session.getLastAccessedTime());
        System.out.println("ID " + session.getId());
        System.out.println("Created: " + created);
        System.out.println("Last Accessed: " + accessed);
        // print session contents
         
        OkConn okConn = pool.wgetConnection();
        if (okConn == null || okConn.conn == null) 	// erreur grave
        {       msgErr ="pas de connexion disponible.";
                System.out.println(msgErr);
                pool.getStats(msgErr);
                Log.logErr(msgErr);
                request.setAttribute("erreur", msgErr);
                RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                rd.forward(request, response);
                return;
        }
        // cette connexion va servir pendant toute la duree de la page
        // Attention ! elle change à chaque page !
        // ne l'utiliser QUE dans le bloc try catch finally
        System.out.println("---> session          :" + request.getSession(false));
        UserInfo userinfo = (UserInfo) session.getValue("currentUser");
        if (userinfo == null) {
        	  System.out.println("--->userinfo est null");       	  
        	  userinfo = new UserInfo();
        }
        Connection conn = okConn.conn;
        System.out.println("--->userinfo est ok");    
        //if (request.getParameter("debug")!=null)    userinfo.setDebug(request.getParameter("debug"));
        Client  client = null;	// sera soit cree (login) soit recupere dans le contexte
        try {
            // if null, set the operation to the empty String
        	ListeParam(request);
            if (operation == null)
            {           operation = "application";      }
            System.out.println("op="+operation);
            if (operation.equalsIgnoreCase("login"))           {   this.doLogin(request, response, conn );      return;       }
            // applications autorisées sans login
            if (operation.equalsIgnoreCase("surveillance"))    {   this.doSurveillance(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("application"))     {   this.doApplication(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("voirapplication")) {   this.doVoirApplication(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("cp"))              {   this.doCheckPoint(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("dns"))             {   this.doDns(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("eon"))             {   this.doEon(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("onedns"))          {   this.doOneDns(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("log"))             {   this.doLog(request, response, conn );    return;       }
              if (operation.equalsIgnoreCase("logapache"))     {   this.doLogApache(request, response, conn );    return;       }
            
            // -------------------------------------------------------------------------------
            
            userinfo=(UserInfo)session.getValue("currentUser");
            System.out.println("  userinfo:"+userinfo);
            
            if ( userinfo.getProfil()==0)     {   this.doLogin(request, response, conn );    return;       }
            System.out.println("dispatch operation: user:"+userinfo.getNom()+" profil:"+userinfo.getProfil());
            
            if (operation.equalsIgnoreCase("machine"))     {   this.doMachine(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("addmachine"))  {   this.doAddMachine(request, response, conn ); return;       }
            if (operation.equalsIgnoreCase("supmachine"))  {   this.doSupMachine(request, response, conn ); return;       }
            if (operation.equalsIgnoreCase("suplog"))      {   this.doSupLog(request, response, conn ); return;       }
            if (operation.equalsIgnoreCase("modmachine"))  {   this.doModMachine(request, response, conn ); return;       }
            if (operation.equalsIgnoreCase("bdd"))         {   this.doBdd(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("addbdd"))      {   this.doAddBdd(request, response, conn ); return;       }
            if (operation.equalsIgnoreCase("modbdd"))      {   this.doModBdd(request, response, conn ); return;       }
            if (operation.equalsIgnoreCase("supbdd"))      {   this.doSupBdd(request, response, conn ); return;       }
            if (operation.equalsIgnoreCase("portail"))     {   this.doPortail(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("addportail"))     {   this.doAddPortail(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("supportail"))     {   this.doSupPortail(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("modportail"))     {   this.doModPortail(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("addapplication"))     {   this.doAddApplication(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("supapplication"))     {   this.doSupApplication(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("modapplication"))     {   this.doModApplication(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("modvapplication"))    {   this.doModVApplication(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("modCapplication"))    {   this.doModCApplication(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("modOapplication"))    {   this.doModOApplication(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("addastreinte"))       {   this.doAddAstreinte(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("supastreinte"))       {   this.doSupAstreinte(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("Webapp"))        {   this.doWebapp(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("addWebapp"))     {   this.doAddWebapp(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("supWebapp"))     {   this.doSupWebapp(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("modWebapp"))     {   this.doModWebapp(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("DomaineWA"))        {   this.doDomaineWA(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("addDomaineWA"))     {   this.doAddDomaineWA(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("supDomaineWA"))     {   this.doSupDomaineWA(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("modDomaineWA"))     {   this.doModDomaineWA(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("InstanceWA"))        {   this.doInstanceWA(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("addInstanceWA"))     {   this.doAddInstanceWA(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("supInstanceWA"))     {   this.doSupInstanceWA(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("modInstanceWA"))     {   this.doModInstanceWA(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("PortailIW"))        {   this.doPortailIW(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("addPortailIW"))     {   this.doAddPortailIW(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("supPortailIW"))     {   this.doSupPortailIW(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("modPortailIW"))     {   this.doModPortailIW(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("Theme"))            {   this.doTheme(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("addTheme"))         {   this.doAddTheme(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("modTheme"))         {   this.doModTheme(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("supTheme"))         {   this.doSupTheme(request, response, conn );    return;       }
           
            if (operation.equalsIgnoreCase("Zone"))        {   this.doZone(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("addZone"))     {   this.doAddZone(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("supZone"))     {   this.doSupZone(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("modZone"))     {   this.doModZone(request, response, conn );    return;       }
            if (operation.equalsIgnoreCase("voirZone"))     {   this.doVoirZone(request, response, conn );    return;       }
            
            
            System.out.println("Operation inconnue:"+operation);
            return;
        }
        
        catch(SQLException e)
        {  	System.out.println("<H2>"+"Erreur: " + e.getMessage() + "<BR>");
                while((e = e.getNextException()) != null)
                    System.out.println(e.getMessage() + "<BR>");
                msgErr ="Erreur service : "+ e.getMessage();
                System.out.println(msgErr+"-"+jspPbGeneral+":"+e.getMessage());
                e.printStackTrace();
                request.setAttribute("erreur", msgErr);
                RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                rd.forward(request, response);
                return;
                
        }
        
        catch(Exception e) {
            msgErr ="Erreur service : "+ e.getMessage();
            System.out.println(msgErr+"-"+jspPbGeneral+":"+e.getMessage());
            e.printStackTrace();
            request.setAttribute("erreur", msgErr);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
            rd.forward(request, response);
            return;
            //System.out.println("Exception service: "    );
        }
        finally {
            //System.out.println("service-finally");
            pool.wfreeConnection(okConn);   // libération de la connexion
        }
    }
    
    private void doLog(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException,IOException, SQLException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              ArrayList liste  = (ArrayList)catalog.getAllLog(conn,userinfo);
              session.putValue("liste_log",liste);
              
              //System.out.println("-------> DOMACHINE" + userinfo.getError_message());
              if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
                  
              {
                  String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
                  
              } else {
                  RequestDispatcher rd = sc.getRequestDispatcher("/log.jsp" );
                  rd.forward(request, response);
              }
              
    }    // _______________________________________________________________________________________________
    private void doLogApache(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException,IOException, SQLException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              ArrayList liste  = (ArrayList)catalog.getAllLogApache(conn,userinfo,request);
              session.putValue("liste_log_apache",liste);
              
              //System.out.println("-------> DOMACHINE" + userinfo.getError_message());
              if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
                  
              {
                  String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
                  
              } else {
                  RequestDispatcher rd = sc.getRequestDispatcher("/logapache.jsp" );
                  rd.forward(request, response);
              }
              
    }    // _______________________________________________________________________________________________
    
    
    private void doMachine(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException,IOException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              ArrayList liste_machine = (ArrayList)catalog.getAllMachine(conn,userinfo,0);
              session.putValue("liste_machine",liste_machine);
              ArrayList liste_portail = (ArrayList)catalog.getAllPortail(conn,userinfo,0);
              session.putValue("liste_portail",liste_portail);
              System.out.println("-------> DOMACHINE: erreur");
              //System.out.println("-------> DOMACHINE" + userinfo.getError_message());
              if (userinfo.getError_message().indexOf("Erreur:")!=-1) {
                  String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
                  
              } else {
                  RequestDispatcher rd = sc.getRequestDispatcher("/machine.jsp" );
                  rd.forward(request, response);
              }
              
    }    // _______________________________________________________________________________________________
    
    private void doAddMachine(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException ,SQLException {
        System.out.println(" doAddMachine: " );
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        catalog.AddMachine(conn,userinfo,request);
        ArrayList liste_machine = (ArrayList)catalog.getAllMachine(conn,userinfo,0);
        session.putValue("liste_machine",liste_machine);
        RequestDispatcher rd = sc.getRequestDispatcher("/machine.jsp" );
        rd.forward(request, response);
    }    // _______________________________________________________________________________________________
    
    private void doSupMachine(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException {
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        catalog.SupMachine(conn,userinfo,request);
        ArrayList liste_machine = (ArrayList)catalog.getAllMachine(conn,userinfo,0);
        session.putValue("liste_machine",liste_machine);
        RequestDispatcher rd = sc.getRequestDispatcher("/machine.jsp" );
        rd.forward(request, response);
    }    // ______________________________________________________________________________________________
    private void doModMachine(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              ArrayList liste = (ArrayList)catalog.getAllMachine(conn,userinfo,Integer.parseInt(request.getParameter("code")));
              session.putValue("liste_one_machine",liste);
              RequestDispatcher rd = sc.getRequestDispatcher("/machine.jsp" );
              rd.forward(request, response);
    }    // _______________________________________________________________________________________________
    
    
    
    private void doBdd(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException,IOException ,SQLException, ClassNotFoundException ,SQLException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              ArrayList liste_bdd = (ArrayList)catalog.getAllBdd(conn,userinfo,0);
              session.putValue("liste_bdd",liste_bdd);
              session.putValue("liste_one_bdd",(ArrayList)catalog.getAllBdd(conn,userinfo,-1));
              
              ArrayList liste_machine = (ArrayList)catalog.getAllMachine(conn,userinfo,0);
              session.putValue("liste_machine",liste_machine);
              System.out.println("-------> DOBdd: erreur");
              //System.out.println("-------> DOMACHINE" + userinfo.getError_message());
              if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1) {
                  String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
                  
              } else {
                  RequestDispatcher rd = sc.getRequestDispatcher("/bdd.jsp" );
                  rd.forward(request, response);
              }
              
    }    // _______________________________________________________________________________________________
    
    private void doModBdd(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException ,SQLException    {
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        session.putValue("liste_bdd",(ArrayList)catalog.getAllBdd(conn,userinfo,0));
        session.putValue("liste_one_bdd",(ArrayList)catalog.getAllBdd(conn,userinfo,Integer.parseInt(request.getParameter("code"))));
        RequestDispatcher rd = sc.getRequestDispatcher("/bdd.jsp" );
        rd.forward(request, response);
    }    // _______________________________________________________________________________________________
    private void doAddBdd(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException ,SQLException {
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        catalog.AddBdd(conn,userinfo,request);
        session.putValue("liste_bdd",(ArrayList)catalog.getAllBdd(conn,userinfo,0));
        session.putValue("liste_one_bdd",(ArrayList)catalog.getAllBdd(conn,userinfo,-1));
        RequestDispatcher rd = sc.getRequestDispatcher("/bdd.jsp" );
        rd.forward(request, response);
    }    // _______________________________________________________________________________________________
    
    private void doSupBdd(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException ,SQLException {
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        catalog.SupBdd(conn,userinfo,request);
        session.putValue("liste_bdd",(ArrayList)catalog.getAllBdd(conn,userinfo,0));
        session.putValue("liste_one_bdd",(ArrayList)catalog.getAllBdd(conn,userinfo,-1));
        if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1) {
            String msgErr = userinfo.getError_message();
            System.out.println(msgErr+"-"+jspPbGeneral);
            request.setAttribute("erreur", msgErr);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
            rd.forward(request, response);
            
        } else {
            RequestDispatcher rd = sc.getRequestDispatcher("/bdd.jsp" );
            rd.forward(request, response);
        }
    }    // _______________________________________________________________________________________________
    
    private void doPortail(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException,IOException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              session.putValue("liste_machine",(ArrayList)catalog.getAllMachine(conn,userinfo,0));
              session.putValue("liste_portail",(ArrayList)catalog.getAllPortail(conn,userinfo,0));
              session.putValue("liste_one_portail",(ArrayList)catalog.getAllPortail(conn,userinfo,-1));
              if (userinfo.getError_message().indexOf("Erreur:")!=-1) {
                  String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
              } else {
                  RequestDispatcher rd = sc.getRequestDispatcher("/portail.jsp" );
                  rd.forward(request, response);
              }
              
    }    // _______________________________________________________________________________________________
    
    private void doAddPortail(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException,IOException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              catalog.AddPortail(conn,userinfo,request);
              session.putValue("liste_portail",(ArrayList)catalog.getAllPortail(conn,userinfo,0));
              session.putValue("liste_one_portail",(ArrayList)catalog.getAllPortail(conn,userinfo,-1));
              if (userinfo.getError_message().indexOf("Erreur:")!=-1) {
                  String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
                  
              } else {
                  RequestDispatcher rd = sc.getRequestDispatcher("/portail.jsp" );
                  rd.forward(request, response);
              }
              
    }    // _______________________________________________________________________________________________
    
    private void doModPortail(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException ,SQLException {
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        session.putValue("liste_portail",(ArrayList)catalog.getAllPortail(conn,userinfo,0));
        session.putValue("liste_one_portail",(ArrayList)catalog.getAllPortail(conn,userinfo,Integer.parseInt(request.getParameter("code"))));
        if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1) {
            String msgErr = userinfo.getError_message();
            System.out.println(msgErr+"-"+jspPbGeneral);
            request.setAttribute("erreur", msgErr);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
            rd.forward(request, response);
            
        } else {
            RequestDispatcher rd = sc.getRequestDispatcher("/portail.jsp" );
            rd.forward(request, response);
        }
    }    // _______________________________________________________________________________________________
    
    
    private void doSupPortail(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException ,SQLException {
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        catalog.SupPortail(conn,userinfo,request);
        session.putValue("liste_portail",(ArrayList)catalog.getAllPortail(conn,userinfo,0));
        session.putValue("liste_one_portail",(ArrayList)catalog.getAllPortail(conn,userinfo,-1));
        if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1) {
            String msgErr = userinfo.getError_message();
            System.out.println(msgErr+"-"+jspPbGeneral);
            request.setAttribute("erreur", msgErr);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
            rd.forward(request, response);
            
        } else {
            RequestDispatcher rd = sc.getRequestDispatcher("/portail.jsp" );
            rd.forward(request, response);
        }
    }    // _______________________________________________________________________________________________
    
    private void doSupZone(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException ,SQLException {
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        catalog.SupZone(conn,userinfo,request);
        session.putValue("liste_Zone",(ArrayList)catalog.getAllZone(conn,userinfo,request,0));
        if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1) {
            String msgErr = userinfo.getError_message();
            System.out.println(msgErr+"-"+jspPbGeneral);
            request.setAttribute("erreur", msgErr);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
            rd.forward(request, response);
            
        } else {
            RequestDispatcher rd = sc.getRequestDispatcher("/zone.jsp" );
            rd.forward(request, response);
        }
    }    // _______________________________________________________________________________________________
    
      private void doSupTheme(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException ,SQLException {
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        catalog.SupTheme(conn,userinfo,request);
        session.putValue("liste_Theme",(ArrayList)catalog.getAllTheme(conn,userinfo,0));
        if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1) {
            String msgErr = userinfo.getError_message();
            System.out.println(msgErr+"-"+jspPbGeneral);
            request.setAttribute("erreur", msgErr);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
            rd.forward(request, response);
            
        } else {
            RequestDispatcher rd = sc.getRequestDispatcher("/theme.jsp" );
            rd.forward(request, response);
        }
    }    // _______________________________________________________________________________________________
    
    
    private void doSupAstreinte(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        catalog.SupAstreinte(conn,userinfo,request);
        ArrayList liste = (ArrayList)catalog.getAllApplication(conn,userinfo,request,0);
        session.putValue("liste_application",liste);
        session.putValue("liste_one_application",(ArrayList)catalog.getAllApplication(conn,userinfo,request,Integer.parseInt(request.getParameter("code"))));
        session.putValue("liste_journal",(ArrayList)catalog.getAllJournal(conn,userinfo,request,Integer.parseInt(request.getParameter("code"))));
        session.putValue("liste_astreinte",(ArrayList)catalog.getAllAstreinte(conn,userinfo,request,Integer.parseInt(request.getParameter("code"))));
        if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1) {
            String msgErr = userinfo.getError_message();
            System.out.println(msgErr+"-"+jspPbGeneral);
            request.setAttribute("erreur", msgErr);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
            rd.forward(request, response);
            
        } else {
            RequestDispatcher rd = sc.getRequestDispatcher("/application.jsp" );
            rd.forward(request, response);
        }
    }
    private void doSurveillance(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException,IOException ,SQLException, ClassNotFoundException ,SQLException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              //System.out.println("doSurveillance  : user:"+userinfo.getNom()+" profil:"+userinfo.getProfil());
              
              Catalog catalog = new Catalog();
              session.putValue("liste_application",(ArrayList)catalog.getAllApplication(conn,userinfo,request,0));
              session.putValue("liste_one_application",(ArrayList)catalog.getAllApplication(conn,userinfo,request,-1));
              session.putValue("liste_PortailIW",(ArrayList)catalog.getAllPortailIW(conn,userinfo,0));
              session.putValue("liste_portail",(ArrayList)catalog.getAllPortail(conn,userinfo,0));
              session.putValue("liste_theme",(ArrayList)catalog.getAllTheme(conn,userinfo,0));
              
              
              RequestDispatcher rd = sc.getRequestDispatcher("/application.jsp" );
              rd.forward(request, response);
              
              
    }    // _______________________________________________________________________________________________
    
    private void doApplication(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException,IOException ,SQLException, ClassNotFoundException ,SQLException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              System.out.println("doSurveillance  : user:"+userinfo.getNom()+" profil:"+userinfo.getProfil());
              
              Catalog catalog = new Catalog();
              session.putValue("liste_application",(ArrayList)catalog.getAllApplication(conn,userinfo,request,0));
              ArrayList liste_application       = (ArrayList)session.getValue("liste_application") ;
              System.out.println("list alla"+liste_application.size());
              session.putValue("liste_one_application",(ArrayList)catalog.getAllApplication(conn,userinfo,request,-1));
              ArrayList liste_one_application       = (ArrayList)session.getValue("liste_one_application") ;
              System.out.println("list onea"+liste_one_application.size());
              session.putValue("liste_PortailIW",(ArrayList)catalog.getAllPortailIW(conn,userinfo,0));
              session.putValue("liste_portail",(ArrayList)catalog.getAllPortail(conn,userinfo,0));
              session.putValue("liste_theme",(ArrayList)catalog.getAllTheme(conn,userinfo,0));
              
              if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1) {
                  String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
                  
              } else {
                  RequestDispatcher rd = sc.getRequestDispatcher("/application.jsp" );
                  rd.forward(request, response);
              }
              
    }    // _______________________________________________________________________________________________
    
    private void doAddApplication(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException {
        System.out.println(" doAddBdd: " );
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        catalog.AddApplication(conn,userinfo,request);
        session.putValue("liste_application",(ArrayList)catalog.getAllApplication(conn,userinfo,request,0));
        session.putValue("liste_one_application",(ArrayList)catalog.getAllApplication(conn,userinfo,request,-1));
        RequestDispatcher rd = sc.getRequestDispatcher("/application.jsp" );
        rd.forward(request, response);
    }    // _______________________________________________________________________________________________
    private void doCheckPoint(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    	    throws ServletException, IOException  , ClassNotFoundException {
    	        System.out.println(" doDns: " );
    	        HttpSession session = request.getSession(true);
    	        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
    	        Catalog catalog = new Catalog();
    	        session.putValue("liste_cp",(ArrayList)catalog.getAllCheckPoint(conn,userinfo,request,0));
    	        session.putValue("liste_dns",(ArrayList)catalog.getAllDns(conn,userinfo,request,0));
    	        RequestDispatcher rd = sc.getRequestDispatcher("/listecp.jsp" );
    	        rd.forward(request, response);
    	    }    // _______________________________________________________________________________________________

    private void doDns(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    	    throws ServletException, IOException  , ClassNotFoundException {
    	        System.out.println(" doDns: " );
    	        HttpSession session = request.getSession(true);
    	        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
    	        Catalog catalog = new Catalog();
    	        session.putValue("liste_cp",(ArrayList)catalog.getAllCheckPoint(conn,userinfo,request,0));
    	        session.putValue("liste_dns",(ArrayList)catalog.getAllDns(conn,userinfo,request,0));
    	        RequestDispatcher rd = sc.getRequestDispatcher("/listedns.jsp" );
    	        rd.forward(request, response);
    	    }    // _______________________________________________________________________________________________

    private void doOneDns(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    	    throws ServletException, IOException  , ClassNotFoundException {
    	        System.out.println(" doDns: " );
    	        HttpSession session = request.getSession(true);
    	        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
    	        Catalog catalog = new Catalog();
    	        session.putValue("liste_dns",(ArrayList)catalog.getOneDns(conn,userinfo,request));
    	        session.putValue("liste_cp",(ArrayList)catalog.getOneCheckPoint(conn,userinfo,request));
    	        RequestDispatcher rd = sc.getRequestDispatcher("/onedns.jsp" );
    	        rd.forward(request, response);
    	    }    // _______________________________________________________________________________________________
    private void doEon(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    	    throws ServletException, IOException  , ClassNotFoundException {
    	        System.out.println(" doEon: " );
    	        HttpSession session = request.getSession(true);
    	        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
    	        Catalog catalog = new Catalog();
    	        session.putValue("liste_eon",(ArrayList)catalog.getAllEon(conn,userinfo,request,0));
    	        //session.putValue("liste_cp",(ArrayList)catalog.getAllCheckPoint(conn,userinfo,request,0));
    	        //session.putValue("liste_dns",(ArrayList)catalog.getAllDns(conn,userinfo,request,0));
    	        RequestDispatcher rd = sc.getRequestDispatcher("/listeallip.jsp" );
    	        rd.forward(request, response);
    	    }    // _________________________________________________________________________________________
    private void doModApplication(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException {
        System.out.println("modif appli: " );
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        session.putValue("liste_one_application",(ArrayList)catalog.getAllApplication(conn,userinfo,request,Integer.parseInt(request.getParameter("code"))));
        session.putValue("liste_journal",(ArrayList)catalog.getAllJournal(conn,userinfo,request,Integer.parseInt(request.getParameter("code"))));
        session.putValue("liste_astreinte",(ArrayList)catalog.getAllAstreinte(conn,userinfo,request,Integer.parseInt(request.getParameter("code"))));
        RequestDispatcher rd = sc.getRequestDispatcher("/application.jsp" );
        rd.forward(request, response);
    }    // _______________________________________________________________________________________________
    private void doVoirApplication(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException {
        System.out.println(" doAddBdd: " );
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        session.putValue("liste_one_application",(ArrayList)catalog.getAllApplication(conn,userinfo,request,Integer.parseInt(request.getParameter("code"))));
        RequestDispatcher rd = sc.getRequestDispatcher("/maquette.jsp" );
        rd.forward(request, response);
    }    // _______________________________________________________________________________________________
    
    private void doSupApplication(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        catalog.SupApplication(conn,userinfo,request);
        ArrayList liste = (ArrayList)catalog.getAllApplication(conn,userinfo,request,0);
        session.putValue("liste_application",liste);
        if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1) {
            String msgErr = userinfo.getError_message();
            System.out.println(msgErr+"-"+jspPbGeneral);
            request.setAttribute("erreur", msgErr);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
            rd.forward(request, response);
            
        } else {
            RequestDispatcher rd = sc.getRequestDispatcher("/application.jsp" );
            rd.forward(request, response);
        }
    }    // _______________________________________________________________________________________________
    
    private void doSupLog(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        catalog.SupLog(conn,userinfo,request);
        ArrayList liste  = (ArrayList)catalog.getAllLog(conn,userinfo);
        session.putValue("liste_log",liste);
        
        //System.out.println("-------> DOMACHINE" + userinfo.getError_message());
        if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
            
        {
            String msgErr = userinfo.getError_message();
            System.out.println(msgErr+"-"+jspPbGeneral);
            request.setAttribute("erreur", msgErr);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
            rd.forward(request, response);
            
        } else {
            RequestDispatcher rd = sc.getRequestDispatcher("/log.jsp" );
            rd.forward(request, response);
        }
        
    }    // _______________________________________________________________________________________________
    
    private void doModVApplication(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        catalog.ModVApplication(conn,userinfo,request);
        ArrayList liste = (ArrayList)catalog.getAllApplication(conn,userinfo,request,0);
        session.putValue("liste_application",liste);
        if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1) {
            String msgErr = userinfo.getError_message();
            System.out.println(msgErr+"-"+jspPbGeneral);
            request.setAttribute("erreur", msgErr);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
            rd.forward(request, response);
            
        } else {
            RequestDispatcher rd = sc.getRequestDispatcher("/application.jsp" );
            rd.forward(request, response);
        }
    }    // _______________________________________________________________________________________________
    private void doModCApplication(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        catalog.ModCApplication(conn,userinfo,request);
        ArrayList liste = (ArrayList)catalog.getAllApplication(conn,userinfo,request,0);
        session.putValue("liste_application",liste);
        if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1) {
            String msgErr = userinfo.getError_message();
            System.out.println(msgErr+"-"+jspPbGeneral);
            request.setAttribute("erreur", msgErr);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
            rd.forward(request, response);
            
        } else {
            RequestDispatcher rd = sc.getRequestDispatcher("/application.jsp" );
            rd.forward(request, response);
        }
    }    // _______________________________________________________________________________________________
    
    private void doModOApplication(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        catalog.ModOApplication(conn,userinfo,request);
        ArrayList liste = (ArrayList)catalog.getAllApplication(conn,userinfo,request,0);
        session.putValue("liste_application",liste);
        session.putValue("liste_one_application",(ArrayList)catalog.getAllApplication(conn,userinfo,request,Integer.parseInt(request.getParameter("code"))));
        session.putValue("liste_journal",(ArrayList)catalog.getAllJournal(conn,userinfo,request,Integer.parseInt(request.getParameter("code"))));
        session.putValue("liste_astreinte",(ArrayList)catalog.getAllAstreinte(conn,userinfo,request,Integer.parseInt(request.getParameter("code"))));
        if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1) {
            String msgErr = userinfo.getError_message();
            System.out.println(msgErr+"-"+jspPbGeneral);
            request.setAttribute("erreur", msgErr);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
            rd.forward(request, response);
            
        } else {
            RequestDispatcher rd = sc.getRequestDispatcher("/application.jsp" );
            rd.forward(request, response);
        }
    }
    private void doAddAstreinte(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        catalog.AddAstreinte(conn,userinfo,request);
        ArrayList liste = (ArrayList)catalog.getAllApplication(conn,userinfo,request,0);
        session.putValue("liste_application",liste);
        session.putValue("liste_one_application",(ArrayList)catalog.getAllApplication(conn,userinfo,request,Integer.parseInt(request.getParameter("code"))));
        session.putValue("liste_journal",(ArrayList)catalog.getAllJournal(conn,userinfo,request,Integer.parseInt(request.getParameter("code"))));
        session.putValue("liste_astreinte",(ArrayList)catalog.getAllAstreinte(conn,userinfo,request,Integer.parseInt(request.getParameter("code"))));
        if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1) {
            String msgErr = userinfo.getError_message();
            System.out.println(msgErr+"-"+jspPbGeneral);
            request.setAttribute("erreur", msgErr);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
            rd.forward(request, response);
            
        } else {
            RequestDispatcher rd = sc.getRequestDispatcher("/application.jsp" );
            rd.forward(request, response);
        }
    }
    private void doWebapp(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException,IOException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              session.putValue("liste_Webapp",(ArrayList)catalog.getAllWebapp(conn,userinfo,0));
              session.putValue("liste_machine",(ArrayList)catalog.getAllMachine(conn,userinfo,0));
              session.putValue("liste_one_Webapp",(ArrayList)catalog.getAllWebapp(conn,userinfo,-1));
              if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
              {   String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
              } else {
                  RequestDispatcher rd = sc.getRequestDispatcher("/webapp.jsp" );
                  rd.forward(request, response);
              }
    }
    private void doAddWebapp(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException
    {        HttpSession session = request.getSession(true);
             UserInfo userinfo = (UserInfo)session.getValue("currentUser");
             Catalog catalog = new Catalog();
             catalog.AddWebapp(conn,userinfo,request);
             session.putValue("liste_Webapp",(ArrayList)catalog.getAllWebapp(conn,userinfo,0));
             session.putValue("liste_one_Webapp",(ArrayList)catalog.getAllWebapp(conn,userinfo,-1));
             RequestDispatcher rd = sc.getRequestDispatcher("/webapp.jsp" );
             rd.forward(request, response);
    }
    private void doModWebapp(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              session.putValue("liste_one_Webapp",(ArrayList)catalog.getAllWebapp(conn,userinfo,Integer.parseInt(request.getParameter("code"))));
              RequestDispatcher rd = sc.getRequestDispatcher("/webapp.jsp" );
              rd.forward(request, response);
    }
    private void doSupWebapp(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        catalog.SupWebapp(conn,userinfo,request);
        session.putValue("liste_Webapp",(ArrayList)catalog.getAllWebapp(conn,userinfo,0));
        session.putValue("liste_one_Webapp",(ArrayList)catalog.getAllWebapp(conn,userinfo,-1));
        if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
        {   String msgErr = userinfo.getError_message();
            System.out.println(msgErr+"-"+jspPbGeneral);
            request.setAttribute("erreur", msgErr);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = sc.getRequestDispatcher("/webapp.jsp" );
            rd.forward(request, response);
        }
    }
    private void doDomaineWA(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException,IOException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              ArrayList liste = (ArrayList)catalog.getAllDomaineWA(conn,userinfo,0);
              
              if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
              {   String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
              }
              session.putValue("liste_DomaineWA",liste);
              ArrayList liste_webapp = (ArrayList)catalog.getAllWebapp(conn,userinfo,0);
              session.putValue("liste_webapp",liste_webapp);
              if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
              {   String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
              } else {
                  RequestDispatcher rd = sc.getRequestDispatcher("/domaineWA.jsp" );
                  rd.forward(request, response);
              }
    }
    private void doAddDomaineWA(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException
    {        HttpSession session = request.getSession(true);
             UserInfo userinfo = (UserInfo)session.getValue("currentUser");
             Catalog catalog = new Catalog();
             catalog.AddDomaineWA(conn,userinfo,request);
             ArrayList liste = (ArrayList)catalog.getAllDomaineWA(conn,userinfo,0);
             
             session.putValue("liste_DomaineWA",liste);
             RequestDispatcher rd = sc.getRequestDispatcher("/domaineWA.jsp" );
             rd.forward(request, response);
    }
    private void doModDomaineWA(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              ArrayList liste = (ArrayList)catalog.getAllDomaineWA(conn,userinfo,Integer.parseInt(request.getParameter("code")));
              
              session.putValue("liste_one_DomaineWA",liste);
              RequestDispatcher rd = sc.getRequestDispatcher("/domaineWA.jsp" );
              rd.forward(request, response);
    }
    private void doSupDomaineWA(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        catalog.SupDomaineWA(conn,userinfo,request);
        ArrayList liste = (ArrayList)catalog.getAllDomaineWA(conn,userinfo,0);
        
        session.putValue("liste_DomaineWA",liste);
        if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
        {   String msgErr = userinfo.getError_message();
            System.out.println(msgErr+"-"+jspPbGeneral);
            request.setAttribute("erreur", msgErr);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = sc.getRequestDispatcher("/domaineWA.jsp" );
            rd.forward(request, response);
        }
    }
    private void doInstanceWA(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException,IOException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              session.putValue("liste_InstanceWA",(ArrayList)catalog.getAllInstanceWA(conn,userinfo,0));
              session.putValue("liste_DomaineWA",(ArrayList)catalog.getAllDomaineWA(conn,userinfo,0));
              session.putValue("liste_Bdd",(ArrayList)catalog.getAllBdd(conn,userinfo,0));
              session.putValue("liste_Theme",(ArrayList)catalog.getAllTheme(conn,userinfo,0));
              System.out.println(" appel getallIW: " );
              session.putValue("liste_one_InstanceWA",(ArrayList)catalog.getAllInstanceWA(conn,userinfo,-1));
              
              if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
              {   String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
              }
              
              if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
              {   String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
              } else {
                  RequestDispatcher rd = sc.getRequestDispatcher("/InstanceWA.jsp" );
                  rd.forward(request, response);
              }
    }
    private void doAddInstanceWA(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException
    {        HttpSession session = request.getSession(true);
             UserInfo userinfo = (UserInfo)session.getValue("currentUser");
             Catalog catalog = new Catalog();
             catalog.AddInstanceWA(conn,userinfo,request);
             session.putValue("liste_InstanceWA",(ArrayList)catalog.getAllInstanceWA(conn,userinfo,0));
             session.putValue("liste_one_InstanceWA",(ArrayList)catalog.getAllInstanceWA(conn,userinfo,-1));
             
             if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
             {   String msgErr = userinfo.getError_message();
                 System.out.println(msgErr+"-"+jspPbGeneral);
                 request.setAttribute("erreur", msgErr);
                 RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                 rd.forward(request, response);
             } else {
                 RequestDispatcher rd = sc.getRequestDispatcher("/InstanceWA.jsp" );
                 rd.forward(request, response);
             }
             
             
    }
    private void doModInstanceWA(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              session.putValue("liste_one_InstanceWA",(ArrayList)catalog.getAllInstanceWA(conn,userinfo,Integer.parseInt(request.getParameter("code"))));
              RequestDispatcher rd = sc.getRequestDispatcher("/InstanceWA.jsp" );
              rd.forward(request, response);
    }
    private void doSupInstanceWA(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              catalog.SupInstanceWA(conn,userinfo,request);
              session.putValue("liste_InstanceWA",(ArrayList)catalog.getAllInstanceWA(conn,userinfo,0));
              session.putValue("liste_one_InstanceWA",(ArrayList)catalog.getAllInstanceWA(conn,userinfo,-1));
              if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
              {   String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
              }
              
              if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
              {   String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
              } else {
                  RequestDispatcher rd = sc.getRequestDispatcher("/InstanceWA.jsp" );
                  rd.forward(request, response);
              }
              
    }
    private void doPortailIW(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException,IOException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              session.putValue("liste_PortailIW",(ArrayList)catalog.getAllPortailIW(conn,userinfo,0));
              session.putValue("liste_InstanceWA",(ArrayList)catalog.getAllInstanceWA(conn,userinfo,0));
              session.putValue("liste_Portail",(ArrayList)catalog.getAllPortail(conn,userinfo,0));
              System.out.println("one_portail");
              session.putValue("liste_one_PortailIW",(ArrayList)catalog.getAllPortailIW(conn,userinfo,-1));
              
              if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
              {   String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
              }
              
              if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
              {   String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
              } else {
                  RequestDispatcher rd = sc.getRequestDispatcher("/PortailIW.jsp" );
                  rd.forward(request, response);
              }
    }
    private void doAddPortailIW(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException
    {        HttpSession session = request.getSession(true);
             UserInfo userinfo = (UserInfo)session.getValue("currentUser");
             Catalog catalog = new Catalog();
             catalog.AddPortailIW(conn,userinfo,request);
             session.putValue("liste_PortailIW",(ArrayList)catalog.getAllPortailIW(conn,userinfo,0));
             session.putValue("liste_one_PortailIW",(ArrayList)catalog.getAllPortailIW(conn,userinfo,-1));
             if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
             {   String msgErr = userinfo.getError_message();
                 System.out.println(msgErr+"-"+jspPbGeneral);
                 request.setAttribute("erreur", msgErr);
                 RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                 rd.forward(request, response);
             } else {
                 RequestDispatcher rd = sc.getRequestDispatcher("/PortailIW.jsp" );
                 rd.forward(request, response);
             }
    }
    private void doModPortailIW(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              session.putValue("liste_one_PortailIW",(ArrayList)catalog.getAllPortailIW(conn,userinfo,Integer.parseInt(request.getParameter("code"))));
              RequestDispatcher rd = sc.getRequestDispatcher("/PortailIW.jsp" );
              rd.forward(request, response);
    }
    private void doSupPortailIW(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        UserInfo userinfo = (UserInfo)session.getValue("currentUser");
        Catalog catalog = new Catalog();
        catalog.SupPortailIW(conn,userinfo,request);
        ArrayList liste = (ArrayList)catalog.getAllPortailIW(conn,userinfo,0);
        if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
        {   String msgErr = userinfo.getError_message();
            System.out.println(msgErr+"-"+jspPbGeneral);
            request.setAttribute("erreur", msgErr);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
            rd.forward(request, response);
        }
        session.putValue("liste_PortailIW",liste);
        
        if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
        {   String msgErr = userinfo.getError_message();
            System.out.println(msgErr+"-"+jspPbGeneral);
            request.setAttribute("erreur", msgErr);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = sc.getRequestDispatcher("/PortailIW.jsp" );
            rd.forward(request, response);
        }
        
    }
    
    private void doTheme(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException,IOException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              session.putValue("liste_Theme",(ArrayList)catalog.getAllTheme(conn,userinfo,0));
                  if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
              {   String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
              }
              if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
              {   String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
              } else {
                  RequestDispatcher rd = sc.getRequestDispatcher("/theme.jsp" );
                  rd.forward(request, response);
              }
    }
    
    private void doZone(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException,IOException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              session.putValue("liste_Zone",(ArrayList)catalog.getAllZone(conn,userinfo,request,0));
              session.putValue("liste_machine",(ArrayList)catalog.getAllMachine(conn,userinfo,0));
              if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
              {   String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
              }
              if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
              {   String msgErr = userinfo.getError_message();
                  System.out.println(msgErr+"-"+jspPbGeneral);
                  request.setAttribute("erreur", msgErr);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                  rd.forward(request, response);
              } else {
                  RequestDispatcher rd = sc.getRequestDispatcher("/zone.jsp" );
                  rd.forward(request, response);
              }
    }
    private void doAddZone(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException
    {        HttpSession session = request.getSession(true);
             UserInfo userinfo = (UserInfo)session.getValue("currentUser");
             Catalog catalog = new Catalog();
             catalog.AddZone(conn,userinfo,request);
             ArrayList liste = (ArrayList)catalog.getAllZone(conn,userinfo,request,0);
             session.putValue("liste_Zone",liste);
             liste = (ArrayList)catalog.getAllApplication(conn,userinfo,request,0);
             session.putValue("liste_application",liste);
             if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
             {   String msgErr = userinfo.getError_message();
                 System.out.println(msgErr+"-"+jspPbGeneral);
                 request.setAttribute("erreur", msgErr);
                 RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                 rd.forward(request, response);
             } else {
                 RequestDispatcher rd = sc.getRequestDispatcher("/zone.jsp" );
                 rd.forward(request, response);
             }
             
             
    }
    
     private void doAddTheme(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException
    {        HttpSession session = request.getSession(true);
             UserInfo userinfo = (UserInfo)session.getValue("currentUser");
             Catalog catalog = new Catalog();
             catalog.AddTheme(conn,userinfo,request);
             session.putValue("liste_Theme",(ArrayList)catalog.getAllTheme(conn,userinfo,0));
             //liste = (ArrayList)catalog.getAllApplication(conn,userinfo,request,0);
             //session.putValue("liste_application",liste);
             if (userinfo.getError_message()!=null && userinfo.getError_message().indexOf("Erreur:")!=-1)
             {   String msgErr = userinfo.getError_message();
                 System.out.println(msgErr+"-"+jspPbGeneral);
                 request.setAttribute("erreur", msgErr);
                 RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPbGeneral);
                 rd.forward(request, response);
             } else {
                 RequestDispatcher rd = sc.getRequestDispatcher("/theme.jsp" );
                 rd.forward(request, response);
             }
                    
    }
    private void doModZone(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              ArrayList liste = (ArrayList)catalog.getAllZone(conn,userinfo,request,Integer.parseInt(request.getParameter("code")));
              session.putValue("liste_one_Zone",liste);
              
              RequestDispatcher rd = sc.getRequestDispatcher("/zone.jsp" );
              rd.forward(request, response);
    }    // _______________________________________________________________________________________________
  
    private void doModTheme(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              ArrayList liste = (ArrayList)catalog.getAllTheme(conn,userinfo,Integer.parseInt(request.getParameter("code")));
              session.putValue("liste_one_Theme",liste);
              
              RequestDispatcher rd = sc.getRequestDispatcher("/theme.jsp" );
              rd.forward(request, response);
    }    // _______________________________________________________________________________________________
  
    private void doVoirZone(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException, IOException  , ClassNotFoundException
    {         HttpSession session = request.getSession(true);
              UserInfo userinfo = (UserInfo)session.getValue("currentUser");
              Catalog catalog = new Catalog();
              ArrayList liste = (ArrayList)catalog.getAllApplication(conn,userinfo,request,0);
              session.putValue("liste_theme",(ArrayList)catalog.getAllTheme(conn,userinfo,0));
              session.putValue("liste_application",liste);
              System.out.println("voirzone");
              RequestDispatcher rd = sc.getRequestDispatcher("/maquette_ieple.jsp" );
              rd.forward(request, response);
    }    // _______________________________________________________________________________________________
    private void doLogin(HttpServletRequest request, HttpServletResponse response, java.sql.Connection conn)
    throws ServletException,IOException ,SQLException, ClassNotFoundException
    {      System.out.println("------------------------ recherche ldap dispatcher ------------------------------");
           System.out.println("session ::"+request.getSession(false));
           HttpSession session = request.getSession(true);
           
           UserInfo userinfo = new UserInfo();
           userinfo.setProfil(1);
           if (ldapmode.equals("tst")) {
               userinfo.setProfil(1);
               session.putValue("currentUser",userinfo);
               this.doApplication(request, response, conn);
           }
           else {
               
               
               LDAPConnection ld = null;
               LDAPEntry findEntry=null;
               int status = -1;
               String xdn="";
               
               try {
                   if (request.getParameter("code")==null) {
                       request.setAttribute("erreur", "veuillez vous identifier");
                       RequestDispatcher rd = sc.getRequestDispatcher("/login.jsp");
                       rd.forward(request,response);
                   }
                   ld = new LDAPConnection();
                   ld.connect(ldap,389);
                   ld.authenticate(3,ldapdm,ldappw);
                   String MY_FILTER = "uid="+request.getParameter("code");
                   LDAPSearchResults res = ld.search( ldapbasedn,2,MY_FILTER, null, false );
                   int n=0;
                   String xcn="";
                   String xmail="";
                   String xuid="";
                   String xmobile="";
                   String xdate="";
                   while ( res.hasMoreElements() ) {
                       findEntry = null;
                       findEntry = res.next();
                       xdn=findEntry.getDN();
                       // bind de utilisateur
                       //-------------------
                       try {
                           ld.authenticate(3,  xdn, request.getParameter("password") );
                           status=0;
                           System.out.println("bind ok for "+request.getParameter("code"));
                       }
                       catch( LDAPException e ) { System.out.println( "Error bind user ldap: " + e.toString() );     }
                   }
                   if (status==0) {
                       String[] attrNames = { "datenaissance" , "mail"  ,"mobile", "uid", "cn" };
                       res = ld.search( ldapbasedn,2,MY_FILTER, attrNames, false );
                       n=1;
                       /* Get the attributes of the entry */
                       LDAPAttributeSet findAttrs=findEntry.getAttributeSet();
                       Enumeration enumAttrs = findAttrs.getAttributes();
                       /* Loop on attributes */
                       while ( enumAttrs.hasMoreElements() ) {
                           LDAPAttribute anAttr =
                           (LDAPAttribute)enumAttrs.nextElement();
                           String attrName = anAttr.getName();
                           //System.out.println("attrName:"+attrName);
                           /* Loop on values for this attribute */
                           Enumeration enumVals = anAttr.getStringValues();
                           while ( enumVals.hasMoreElements() ) {
                               String aVal = ( String )enumVals.nextElement();
                               //System.out.println("aVal:"+aVal);
                               if ( attrName.equals( "cn" ) )   xcn  =  aVal;
                               if ( attrName.equals( "uid" ) )  xuid =  aVal;
                               if ( attrName.equals( "mail" ) ) xmail=  aVal;
                               if ( attrName.equals( "mobile" ) ) xmobile=  aVal;
                           }
                       }
                       userinfo.setNom(xcn);
                       userinfo.setPrenom(xuid);
                       userinfo.setMail(xmail);
                       userinfo.setTelephone(xmobile);
                       System.out.println("mail:"+userinfo.getMail());
                       // search de liste1
                       //-------------------
                       String[] attrLNames = { "uniquemember" };
                       MY_FILTER =   "(|(cn=webapp)(cn=liste.dsi.test))";
                       res = ld.search( ldapbasedn,2,MY_FILTER, attrLNames, false );
                       System.out.println("search ...for.. "+xdn);
                       System.out.println("dans "+MY_FILTER);
                       while ( res.hasMoreElements() ) {
                           System.out.println("res hasmore...");
                           findEntry = null;
                           findEntry = res.next();
                           /* Get the attributes of the entry */
                           findAttrs=findEntry.getAttributeSet();
                           enumAttrs = findAttrs.getAttributes();
                           while ( enumAttrs.hasMoreElements() ) {
                               System.out.println("enum hasmore...");
                               LDAPAttribute anAttr = (LDAPAttribute)enumAttrs.nextElement();
                               String attrName = anAttr.getName();
                               System.out.println("attrN:"+attrName);
                               Enumeration enumVals = anAttr.getStringValues();
                               while ( enumVals.hasMoreElements() ) {
                                   String aVal = ( String )enumVals.nextElement();
                                   System.out.println("val:"+aVal);
                                   if ( aVal.substring(0,aVal.indexOf("gouv")).equals(xdn.substring(0,xdn.indexOf("gouv")))) {
                                       System.out.println("ok");
                                       n=2;
                                       break; }
                               }
                           }
                       }
                       // ---------------------test si moderateur ---------------
                       
                   }
                   /* Done, so disconnect */
                   if ( (ld != null) && ld.isConnected() ) {         ld.disconnect();           }
                   // ---------------------------------------------------------------------------
                   if (n==0) {
                       System.out.println("compte ldap inconnu !");
                       request.setAttribute("erreur", "Compte ldap inconnu !");
                       RequestDispatcher rd = sc.getRequestDispatcher("/login.jsp");
                       rd.forward(request,response);
                   }
                   else if (n==1){
                       System.out.println("user non autorise"+request.getSession(false));
                       //HttpSession session = request.getSession(true);
                       request.setAttribute("erreur", "cette application est limitée à un groupe d'utilisateur !");
                       RequestDispatcher rd = sc.getRequestDispatcher("/login.jsp");
                       rd.forward(request,response);
                   }
                   else if (n==2){
                       System.out.println("cnx autorise");
                       //HttpSession session = request.getSession(true);
                       session.putValue("currentUser",userinfo);
                       this.doApplication(request, response, conn);
                   }
               }
               
               catch( LDAPException e ) {
                   System.out.println( "Error ldap: " + e.toString() );
                   //Exception e = new Exception("The operation is unknown.");
                   request.setAttribute("sourcePage","servlet Dispatcher action:doLogin");
                   request.setAttribute("javax.servlet.jsp.jspException", e);
                   RequestDispatcher rd = sc.getRequestDispatcher("/PbGeneral.jsp");
                   rd.forward(request,response);
                   
               }
           }
    }

	private void ListeParam(HttpServletRequest request) {
		System.out
				.println("=============================liste param=========================================");
		Enumeration enTetes = request.getHeaderNames();
		while (enTetes.hasMoreElements()) {
			String enTete = (String) enTetes.nextElement();
			System.out.println(enTete + " : " + request.getHeader(enTete));
		}
		enTetes = request.getParameterNames();
		while (enTetes.hasMoreElements()) {
			String s = (String) enTetes.nextElement();
			System.out.println(s + " : " + request.getParameter(s));

		}
		;
		System.out
				.println("================================================================================");

	}
    
}



