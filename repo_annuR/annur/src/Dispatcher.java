//package javabeans;
//package servlets;
import netscape.ldap.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.lowagie.text.Document;
import java.sql.*;

import javax.sql.*;
import java.util.Date;
import java.util.*;
import uti2.*;
import java.net.*;
//import db.*;
//import metier.*;
import java.lang.*;
import javabeans.Catalog;
import javabeans.UserInfo;
import javabeans.ItemAnnuaire;
import javax.servlet.http.*;
import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.directory.InitialDirContext;

public class Dispatcher extends HttpServlet {
	private static final String jspPbGeneral = "/PbGeneral.jsp";
	static public PoolConnect pool;
	static public PoolConnect poolannu;
	private static ServletContext sc;
	private static String WEBMASTER_EMAIL = "hhhhhhhhhhh";
	private boolean debugFlag;
	private static String assistance_tel;
	private static String assistance_nom;
	private static String assistance_mail;
	private static String cnx;
	private static String mode;
	private static String ldap;
	private static String ldapdm;
	private static String ldappw;
	private static String ldappwS;
	private static String ldapbasedn;
	private static String liste1; // liste autorisation num 1 cn=liste1
	private static String liste2; // liste autorisation num 2
	private static String admin; // administrateur du forum
	private static String rep_pdf; // repertoire des documents pdf
	private static int intranet = 2; // intranet = 1, extranet = 2
	private static String samba_sid; // samb_sid pour creation des partages
	private static int maxi;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		System.out.println("initserveur:" + InitServeur.dbgInitParameter(this));
		// Const.dbg = InitServeur.valeurDebug(this);
		// System.out.println("CneServlet.init Const.dbg="+Const.dbg);
		sc = this.getServletContext();
		System.out
				.println("--------------annuaire Eclipse   LUNA  : 30 juillet 2015  ------  ménage FF   ------------------------------------");
		// get the WEBMASTER_EMAIL Context Parameter and store it a global
		// variable for the servlet to access
		// WEBMASTER_EMAIL = (String) sc.getInitParameter("webmaster");
		// WEBMASTER_EMAIL = sc.getContext("webmaster");
		// set the ServletDebug init-parameter, this determines if messages are
		// sent to the
		// console when actions are received by the servlet

		// System.out.println("debut traitement");

		Context ctx = null;
		System.out.println("Recherche des valeurs du contexte");

		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			System.out.println("Couldn't build an initial context : " + e);
			return;
		}
		try {
			System.out.println("Recherche des valeurs du contexte");
			Context envCtx = (Context) ctx.lookup("java:/comp/env/");
			System.out.println("list() on /comp/env Context : ");
			samba_sid = ctx.lookup("java:/comp/env/samba_sid").toString();
			rep_pdf = ctx.lookup("java:/comp/env/rep_pdf").toString();
			assistance_tel = ctx.lookup("java:/comp/env/assistance_tel")
					.toString();
			assistance_nom = ctx.lookup("java:/comp/env/assistance_nom")
					.toString();
			assistance_mail = ctx.lookup("java:/comp/env/assistance_mail")
					.toString();
			// ldapmode dans web.xml: tst :ldap local, exp :ann2
			mode = ctx.lookup("java:/comp/env/ldapmode").toString();
			cnx = ctx.lookup("java:/comp/env/cnx" + mode).toString();
			ldap = ctx.lookup("java:/comp/env/ldapsrv" + mode).toString();
			System.out.println("serveur ldap=" + ldap + ".");
			ldapdm = ctx.lookup("java:/comp/env/ldapdm" + mode).toString();
			ldappw = ctx.lookup("java:/comp/env/ldappw" + mode).toString();
			ldappwS = ctx.lookup("java:/comp/env/ldappwS").toString();
			ldapbasedn = ctx.lookup("java:/comp/env/ldapbasedn" + mode)
					.toString();
			System.out.println("serveur basedn=" + ldapbasedn + "."+"  sur "+ldap);
			liste1 = ctx.lookup("java:/comp/env/liste1").toString();
			liste2 = ctx.lookup("java:/comp/env/liste2").toString();
			admin = ctx.lookup("java:/comp/env/admin").toString();
			// ServletContext servletContext = config.getServletContext();
			Catalog catalog = new Catalog();
			//Class.forName("com.mysql.jdbc.Driver");
			System.out.println("cnx : " + cnx); 
			System.out.println("-----------------------------------");
			// String nomfichecrire = "annudispatcher.txt";
			// String lignesortie;PrintWriter sortie = new PrintWriter(new
			// FileWriter(rep_pdf+nomfichecrire));
			// lignesortie="debut Dispatcher";
			// sortie.println(lignesortie);
			// System.out.println(lignesortie);
			// sortie.close();
			//Connection conn = DriverManager.getConnection(cnx, "alain",
				//	"trek250");
			ServletContext servletContext = config.getServletContext();
			String racineServeurWeb = servletContext.getRealPath("/");
			System.out.println("racineServeurWeb " + racineServeurWeb);
			Log.logInit(racineServeurWeb + "WEB-INF/");
			System.out.println("loginit fait");
			pool = InitServeur.litconfigProperties(this, racineServeurWeb,
					"config");
			// TacheExtraction.dbUrlLocale = InitServeur.getDbURL();
			//OkConn okConn = pool.wgetConnection();
			//System.out.println("init: max agt:" + catalog.getMaxAgent(conn));
			System.out
					.println("---------------------------------pw ldap------------------------------------");
			Runtime runtime = Runtime.getRuntime();
			Process process = null;
			String cmd = "ssh root@scribe 'cat /usr/share/eole/pwld; ' ";
			process = runtime.exec(new String[] { "/bin/sh", "-c", cmd });
			System.out.println("cmd: " + cmd); 
			DataInputStream in2 = new DataInputStream(process.getInputStream());
			String line2 = null;
			while (((line2 = in2.readLine()) != null)) {
				ldappwS = line2.substring(1, line2.length() - 1);
			}
			System.out
					.println("----------------------------------------------------------------------------");
			//maxi = catalog.getMaxAgent(conn);
			// test reveil
			// int periodeReveil = 1;
			// System.out.println("reveil deb");
			// Reveil reveil = new Reveil(pool, periodeReveil); // reveil toutes
			// les minutes
			// new Thread(reveil).start();
			// System.out.println("reveil fin");
			// -----------
			//pool.wfreeConnection(okConn); // libération de la connexion
		} catch (Exception e) {
			e.printStackTrace();
			System.err
					.println("Erreur fatale pendant l'init de la servlet Dispatcher");
			// System.exit(0);
		}
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		System.out
				.println("----------debut service annuaire 06 Sept 2010 -------------");
		ListeParam(request);
		OkConn okConn = pool.wgetConnection();
		try {
			System.out
					.println("operation=" + request.getParameter("operation"));

			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = okConn.conn;
			Catalog catalog = new Catalog();
			System.out.println("max agt:" + catalog.getMaxAgent(conn));
			String operation = "login";
			HttpSession session = request.getSession(true);
			session.setMaxInactiveInterval(5000); // par defaut 30 mn;
			System.out.println("session id  :" + session.getId());
			System.out.println("session time:"
					+ session.getMaxInactiveInterval());
			System.out.println("host:" + request.getHeader("host"));
			System.out.println("host:"
					+ request.getHeader("host").indexOf("in"));
			System.out.println("intranet=" + intranet);

			if ((request.getHeader("host").indexOf("in") != -1))
				intranet = 1;
			else
				intranet = 2;
			Date DateJour = new Date();
			int month = DateJour.getMonth() + 1;
			String vdate = DateJour.getDate() + "/" + month + "-"
					+ DateJour.getHours() + ":" + DateJour.getMinutes() + ":"
					+ DateJour.getSeconds();

			System.out.println("**********************************");
			System.out.println("**********************************");
			System.out.println("date=" +vdate);
			System.out.println("intranet=" + intranet);
			System.out.println("ct-remote-user  ="
					+ request.getHeader("ct-remote-user"));
			System.out.println("annu-profil-annu="
					+ request.getHeader("annu-profil-annu"));
			System.out.println("cn              =" + request.getHeader("cn"));
			System.out.println("**********************************");
			System.out.println("**********************************");
			if (request.getParameter("operation") != null)
				operation = request.getParameter("operation");
			else
				operation = "menu";

			System.out.println("---> Service Operation:" + operation);
			System.out.println("---> session          :" + request.getSession(false));
			
			// HttpSession session = request.getSession(true);
			UserInfo userinfo = (UserInfo) session.getValue("currentUser");
			
			if (userinfo == null) {
				System.out.println("userinfo est nulle");
				System.out.println("------------------");
				// operation="menuframe";
				UserInfo userinfo_anonyme = new UserInfo();
				userinfo_anonyme.setType("");
				if (intranet == 2)
					userinfo_anonyme.setProfil(-1);
				else
					userinfo_anonyme.setProfil(0);
				if (request.getHeader("ct-remote-user") != null) {
					userinfo_anonyme.setUser(request.getHeader("cn"));
					if((request.getHeader("annu-profil-annu")) != null)
					{
					userinfo_anonyme.setProfil(Integer.parseInt(request
							.getHeader("annu-profil-annu")));
					}
					else  userinfo_anonyme.setProfil(0);

				}

				if (request.getHeader("telephoneNumber") != null) {
					String telephone = request.getHeader("telephoneNumber");
					System.out.println("telee :" + telephone);
					String wposte = telephone.replaceAll(" ", "");
					System.out.println("wpos :" + wposte);
					if (telephone.length() > 2)
						userinfo_anonyme.setPoste(telephone.replaceAll(" ", "")
								.substring(6, 10));
					else if (wposte.length() > 9)
						userinfo_anonyme.setPoste(wposte.replaceAll(" ", "")
								.substring(6, 10));
					else
						userinfo_anonyme.setPoste("nd");
					System.out
							.println("wposte :" + userinfo_anonyme.getPoste());

				}
				session.putValue("currentUser", userinfo_anonyme);
				userinfo = (UserInfo) session.getValue("currentUser");
			}

			// userinfo.setError_message("");
			// UserInfo userinfo = new UserInfo();
			System.out.println("Operation :" + operation + " par "+userinfo.getUser() + " " + userinfo.getProfil()+ " " + vdate); 
			System.out.println("-------------------------------LUNA---------------------------------");
			ListeParam(request);
			
			if (operation.equalsIgnoreCase("partage"))     { this.doPartage(request, response, conn);	 		return;}
			if (operation.equalsIgnoreCase("choixdiv"))    { this.doChoixDiv(request, response, conn);			return;}
			if (operation.equalsIgnoreCase("menu_admin"))  { this.doMenuAdmin(request, response, conn);			return;}
			if (operation.equalsIgnoreCase("menu_normal")) { this.doMenuNormal(request, response, conn);		return;}
			if (operation.equalsIgnoreCase("addpartage"))  { this.doAddPartage(request, response, conn);		return;}
			if (operation.equalsIgnoreCase("userpartage")) { this.doUserPartage(request, response, conn);		return;}
			if (operation.equalsIgnoreCase("validldap"))   { this.doValidLdap(request, response, conn);  		return;}
			if (operation.equalsIgnoreCase("recldap"))     { this.doRechercheLdap(request, response, conn);		return;}
			if (operation.equalsIgnoreCase("sunscribe"))   { this.doSunScribe(request, response, conn);			return;}
			if (operation.equalsIgnoreCase("supldap"))     { this.doSupLdap(request, response, conn);			return;}
		    if (operation.equalsIgnoreCase("trombi"))      { this.doTrombi(request, response, conn);			return;}
			if (operation.equalsIgnoreCase("modagentchorus")) {	this.doModAgentChorus(request, response, conn);	return;}
			if (operation.equalsIgnoreCase("modagentotp"))    { this.doModAgentOtp(request, response, conn);	return;}
			if (operation.equalsIgnoreCase("ldapasa"))        { this.doLdapAsa(request, response, conn);		return;}
			if (operation.equalsIgnoreCase("chorus")) 		  { this.doChorus(request, response, conn);			return;}
			if (operation.equalsIgnoreCase("otp"))			  { this.doOtp(request, response, conn);			return;}
			if (operation.equalsIgnoreCase("charte"))         {	this.doCharteOtp(request, response, conn);		return;}
			if (operation.equalsIgnoreCase("chartechorus"))   {	this.doCharteChorus(request, response, conn);	return;}
			if (operation.equalsIgnoreCase("listediv"))		  { this.doListe(request, response, conn);			return;}
			if (operation.equalsIgnoreCase("listemission"))   { this.doListeMission(request, response, conn);	return;}
			if (operation.equalsIgnoreCase("sms"))  		  { this.doSms(request, response, conn);			return;}
			if (operation.equalsIgnoreCase("envoisms"))       { this.doEnvoiSms(request, response, conn);		return;}
			if (operation.equalsIgnoreCase("testip"))         { this.doTestIp(request, response, conn);			return;} /* show request headers : non utilisé */
			if (operation.equalsIgnoreCase("menu")) 		  { this.doMenu(request, response, conn);			return;}
			if (operation.equalsIgnoreCase("voirmission"))    { this.doVoirMission(request, response, conn);	return;}
			if (operation.equalsIgnoreCase("login")) 		  { this.doLogin(request, response, conn);			return;}
			if (operation.equalsIgnoreCase("filtrage")) 	  { this.doFiltrage(request, response, conn);		return;} /* filtrage Url 1er et 2nd degré : plus utilisé    */
			if (operation.equalsIgnoreCase("sendmail"))       { this.doSendMail(request, response, conn);		return;} /* filtrage Url 1er et 2nd degré : plus utilisé    */
			if (operation.equalsIgnoreCase("logout"))		  { this.doLogout(request, response, conn);			return;}
			if (operation.equalsIgnoreCase("menuframe"))      { this.doMenuFrame(request, response, conn);		return;} /* frame type repertoire  : plus utilisé */
			userinfo.setError_message("");
			if (operation.equalsIgnoreCase("annuaire"))       { this.doMenuFrame(request, response, conn);		return;}
			if (operation.equalsIgnoreCase("pole"))           {	this.doPole(request, response, conn);			return;}
			if (operation.equalsIgnoreCase("division"))       { this.doDivision(request, response, conn);		return;}
			if (operation.equalsIgnoreCase("service"))        { this.doService(request, response, conn);		return;}
			if (operation.equalsIgnoreCase("bureau"))         { this.doBureau(request, response, conn);			return;}
			if (operation.equalsIgnoreCase("agent"))          { this.doAgent(request, response, conn);			return;}
			if (operation.equalsIgnoreCase("agentsansaff"))   { this.doAgentSansAff(request, response, conn);	return;}
			if (operation.equalsIgnoreCase("recherche"))      { this.doRecherche(request, response, conn);		return;}
			if (operation.equalsIgnoreCase("recherchediv"))   { this.doRechercheDiv(request, response, conn);	return;}
			if (operation.equalsIgnoreCase("ldap"))           { this.doLdap(request, response, conn);			return;}
			if (operation.equalsIgnoreCase("synchroldap"))    { this.doSynchroLdap(request, response, conn);	return;}
			if (operation.equalsIgnoreCase("libelle"))        { this.doLibelle(request, response, conn);		return;}
			if (operation.equalsIgnoreCase("synchroallannu")) {	this.doSynchroLdapAllAgent(request, response, conn);return;}
			if (operation.equalsIgnoreCase("synchroallldap")) { this.doSynchroAllLdap(request, response, conn);	return;}
			if (operation.equalsIgnoreCase("allagents"))      { this.doAllAgents(request, response, conn);		return;}
			System.out.println("Operation inconnue:" + operation);												return;
		} catch (SQLException e) {
			System.out.println("SQLException:");
			System.out.println(e.getMessage());
			while ((e = e.getNextException()) != null)
				System.out.println(e.getMessage());
			RequestDispatcher rd = sc.getRequestDispatcher("/home.jsp");
			return;
		} catch (Exception e) {
			System.out.println("Exception service :");
			System.out.println(e.getMessage());
			e.printStackTrace();
			// userinfo.setError_message("erreur:"+e.getMessage());
			RequestDispatcher rd = sc.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		} finally {
			pool.wfreeConnection(okConn);
		}
	}

	private void doLogout(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, ClassNotFoundException, IOException,
			SQLException {
		System.out
				.println("------------------------ doLogout dispatcher ------------------------------");
		System.out.println("Logout ...");
		HttpSession session = request.getSession();
		System.out.println("session avant logout" + request.getSession(false));
		UserInfo userinfo = new UserInfo();
		if (intranet == 2) {
			userinfo.setProfil(-1);
			userinfo.setMenu_admin(2);
		} else {
			userinfo.setProfil(0);
			userinfo.setMenu_admin(2);
		}
		Catalog catalog = new Catalog();
		session.putValue("login", (ArrayList) catalog.getAllAgent(conn,
				userinfo, request, -1, 0, 0, 0, 0, 0));
		session.putValue("currentUser", userinfo);
		System.out.println("appel doMenu .....");
		this.doMenu(request, response, conn);
	}

	private void doChoixDiv(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, ClassNotFoundException, IOException,
			SQLException {
		System.out
				.println("------------------------ passage en menu admin ------------------------------");

		HttpSession session = request.getSession();
		System.out.println("session avant logout" + request.getSession(false));

		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		userinfo.setDv_nb(1);
		userinfo.setDv_code(Integer.parseInt(request.getParameter("choixdv")));
		int nb=userinfo.getDv_nb();
		if (nb > 1)
		{
			System.out.println("nb div > 1 , demande de choisir" );
			RequestDispatcher rd = sc.getRequestDispatcher("/choix.jsp");
			rd.forward(request, response);
			 
		}
		else
		{
		
		if (request.getHeader("ct-remote-user") != null) {
			userinfo.setProfil(Integer.parseInt(request
					.getHeader("annu-profil-annu")));
			userinfo.setMenu_admin(1);
		}
		userinfo.setMenu_admin(1);
		Catalog catalog = new Catalog();
		session.putValue("login", (ArrayList) catalog.getAllAgent(conn,
				userinfo, request, -1, 0, 0, 0, 0, 0));
		session.putValue("currentUser", userinfo);
		this.doMenu(request, response, conn);
	}
	}
	
	private void doMenuAdmin(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, ClassNotFoundException, IOException,
			SQLException {
		System.out
				.println("------------------------ passage en menu admin ------------------------------");

		HttpSession session = request.getSession();
		System.out.println("session avant logout" + request.getSession(false));

		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		int nb=userinfo.getDv_nb();
		if (nb > 1)
		{
			System.out.println("nb div > 1 , demande de choisir" );
			RequestDispatcher rd = sc.getRequestDispatcher("/choix.jsp");
			rd.forward(request, response);
			 
		}
		else
		{
		
		if (request.getHeader("ct-remote-user") != null) {
			userinfo.setProfil(Integer.parseInt(request
					.getHeader("annu-profil-annu")));
			userinfo.setMenu_admin(1);
		}
		userinfo.setMenu_admin(1);
		Catalog catalog = new Catalog();
		session.putValue("login", (ArrayList) catalog.getAllAgent(conn,
				userinfo, request, -1, 0, 0, 0, 0, 0));
		session.putValue("currentUser", userinfo);
		this.doMenu(request, response, conn);
	}
	}

	private void doMenuNormal(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, ClassNotFoundException, IOException,
			SQLException {
		System.out
				.println("------------------------ passage en menu normal ------------------------------");
		System.out.println("Logout ...");
		HttpSession session = request.getSession();
		System.out.println("session avant logout" + request.getSession(false));

		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		userinfo.setMenu_admin(2);

		Catalog catalog = new Catalog();
		session.putValue("login", (ArrayList) catalog.getAllAgent(conn,
				userinfo, request, -1, 0, 0, 0, 0, 0));
		session.putValue("currentUser", userinfo);
		this.doMenu(request, response, conn);
	}

	private void doFiltrage(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, ClassNotFoundException, IOException,
			SQLException {
		System.out
				.println("------------------------ doLogout SECU dispatcher ------------------------------");
		System.out.println("Filtrage ... secu");
		HttpSession session = request.getSession();
		UserInfo userinfo = new UserInfo();
		userinfo.setProfil(-1);
		Catalog catalog = new Catalog();
		session.putValue("currentUser", userinfo);
		RequestDispatcher rd = sc.getRequestDispatcher("/filtrage.jsp");
		rd.forward(request, response);
	}

	private void doAnnuaire(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		System.out.println("annuaire:" + userinfo.getUser() + ":"
				+ userinfo.getProfil());

		RequestDispatcher rd = sc.getRequestDispatcher("/pole.jsp");
		Catalog catalog = new Catalog();
		session.putValue("alltree",
				(ArrayList) catalog.getAllTree(conn, userinfo, request, 0));
		session.putValue("divtree",
				(ArrayList) catalog.getDivTree(conn, userinfo, request, 1));
		rd.forward(request, response);
	}

	private void doMenuFrame(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		RequestDispatcher rd = sc.getRequestDispatcher("/menuframe.jsp");
		Catalog catalog = new Catalog();
		session.putValue("alltree",
				(ArrayList) catalog.getAllTree(conn, userinfo, request, 0));
		session.putValue("divtree",
				(ArrayList) catalog.getDivTree(conn, userinfo, request, 1));
		session.putValue("alldivision", (ArrayList) catalog.getAllDivision(
				conn, userinfo, request, 0, 0));
		session.putValue("allservices", (ArrayList) catalog.getAllService(conn,
				userinfo, request, 0, 0));
		if (userinfo.getProfil() >= 1) // ceration liste division, services,
										// bureaux
			session.putValue("allbureaux",
					(ArrayList) catalog.getAllDivSerBur(conn, userinfo));
		else
			System.out.println("anonyme: menuframe.jsp");
		rd.forward(request, response);
	}

	private void doMenu(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		System.out.println("doMenu");
		System.out.println("------");
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		System.out.println("anonyme: annuaire.jsp " + userinfo.getProfil());
		RequestDispatcher rd = sc.getRequestDispatcher("/annuaire.jsp");
		Catalog catalog = new Catalog();
		session.putValue("allpole",
				(ArrayList) catalog.getAllPole(conn, userinfo, request, 0));
		session.putValue("alltree",
				(ArrayList) catalog.getAllTree(conn, userinfo, request, 0));
		session.putValue("divtree",
				(ArrayList) catalog.getDivTree(conn, userinfo, request, 1));
		session.putValue("alldivision", (ArrayList) catalog.getAllDivision(
				conn, userinfo, request, 0, 0));
		session.putValue("allservices", (ArrayList) catalog.getAllService(conn,
				userinfo, request, 0, 0));
		if (userinfo.getProfil() >= 1) // ceration liste division, services,
										// bureaux
			session.putValue("allbureaux",
					(ArrayList) catalog.getAllDivSerBur(conn, userinfo));
		else
			System.out.println("anonyme: annuaire.jsp");
		rd.forward(request, response);
	}

	private void doListe(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		System.out.println("anonyme: annuaire.jsp " + userinfo.getProfil());
		RequestDispatcher rd = sc.getRequestDispatcher("/annuaire.jsp");
		Catalog catalog = new Catalog();
		session.putValue("allpole",
				(ArrayList) catalog.getAllPole(conn, userinfo, request, 0));
		session.putValue("alltree",
				(ArrayList) catalog.getAllTree(conn, userinfo, request, 0));
		session.putValue("divtree",
				(ArrayList) catalog.getDivTree(conn, userinfo, request, 1));
		session.putValue("alldivision", (ArrayList) catalog.getAllDivision(
				conn, userinfo, request, 0, 0));
		session.putValue("allservices", (ArrayList) catalog.getAllService(conn,
				userinfo, request, 0, 0));
		if (userinfo.getProfil() >= 1) // ceration liste division, services,
										// bureaux
			session.putValue("allbureaux",
					(ArrayList) catalog.getAllDivSerBur(conn, userinfo));
		else
			System.out.println("anonyme: annuaire.jsp");
		rd.forward(request, response);
	}

	private void doListeMission(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		System.out.println("anonyme: annuaire.jsp " + userinfo.getProfil());
		RequestDispatcher rd = sc.getRequestDispatcher("/mission.jsp");
		Catalog catalog = new Catalog();
		session.putValue("allmission",
				(ArrayList) catalog.getAllMission(conn, userinfo, request, 0));

		rd.forward(request, response);
	}

	private void doPole(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		System.out.println("Pole " + request.getParameter("bsubmit"));
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		RequestDispatcher rd = sc.getRequestDispatcher("/pole.jsp");
		Catalog catalog = new Catalog();
		if (request.getParameter("bsubmit") != null)
			catalog.AddPole(conn, userinfo, request);
		if (request.getParameter("code") != null)
			session.putValue("onepole", (ArrayList) catalog.getAllPole(conn,
					userinfo, request,
					Integer.parseInt(request.getParameter("code"))));
		session.putValue("allpole",
				(ArrayList) catalog.getAllPole(conn, userinfo, request, 0));
		session.putValue("alldivision", (ArrayList) catalog.getAllDivision(
				conn, userinfo, request,
				Integer.parseInt(request.getParameter("code")), 0));
		session.putValue("allservices", (ArrayList) catalog.getAllService(conn,
				userinfo, request, 0, 0));
		session.putValue("alltree",
				(ArrayList) catalog.getAllTree(conn, userinfo, request, 0));
		session.putValue("allldap", (ArrayList) catalog.getLdap(userinfo, ldap,
				ldapdm, ldappw, ldapbasedn, request));
		// session.putValue("allagent",(ArrayList)catalog.getAllAgent(conn,userinfo,request,
		// codebur, codeser, codediv, codepol);
		// allagent: tous les agents affectes dans cette division, dans l ordre:
		// agt,bur,service,division, pole
		session.putValue("allagent", (ArrayList) catalog.getAllAgent(conn,
				userinfo, request, 0, 0, 0, 0,
				Integer.parseInt(request.getParameter("code")), 0));
		if (userinfo.getError_message().length() > 0)
			rd = sc.getRequestDispatcher("/error.jsp");
		rd.forward(request, response);
	}

	private void doDivision(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		ListeParam(request);
		System.out.println("division:" + userinfo.getUser() + ":"
				+ userinfo.getProfil());
		String table = "division";
		if (request.getParameter("table") != null) {
			table = request.getParameter("table");
		}
		RequestDispatcher rd = sc.getRequestDispatcher("/" + table + ".jsp");
		Catalog catalog = new Catalog();
		int codedv = Integer.parseInt(request.getParameter("codedv"));
		int codepl = 0;
		if (request.getParameter("bsubmit") != null) {
			codepl = Integer.parseInt(request.getParameter("pldv_code"));
			rd = sc.getRequestDispatcher("/pole.jsp");
			codedv = catalog.AddDivision(conn, userinfo, request);
		}
		System.out.println("pole pour jsp suivante:" + codepl);
		session.putValue("onepole",
				(ArrayList) catalog.getAllPole(conn, userinfo, request, codepl));
		session.putValue("onedivision", (ArrayList) catalog.getAllDivision(
				conn, userinfo, request, 0, codedv));
		session.putValue("alldivision", (ArrayList) catalog.getAllDivision(
				conn, userinfo, request, codepl, 0));
		session.putValue("allservice", (ArrayList) catalog.getAllService(conn,
				userinfo, request, codedv, 0));
		session.putValue("allpartage", (ArrayList) catalog.getAllPartage(conn,
				userinfo, request, codedv, 0));
		session.putValue("allagent", (ArrayList) catalog.getAllAgent(conn,
				userinfo, request, 0, 0, 0, codedv, 0, 0));
		// session.putValue("allldap",(ArrayList)catalog.getLdap(ldap,ldapdm,ldappw,ldapbasedn,request));
		session.putValue("divtree",
				(ArrayList) catalog.getDivTree(conn, userinfo, request, codedv));
		session.putValue("alltree",
				(ArrayList) catalog.getAllTree(conn, userinfo, request, 0));
		if (userinfo.getError_message().length() > 0)
			rd = sc.getRequestDispatcher("/error.jsp");
		rd.forward(request, response);
	}

	private void doService(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		System.out.println("service:" + userinfo.getUser() + ":"
				+ userinfo.getProfil());

		RequestDispatcher rd = sc.getRequestDispatcher("/service.jsp");
		Catalog catalog = new Catalog();
		int codesv = Integer.parseInt(request.getParameter("codesv"));
		int codedv = 0;
		if (request.getParameter("codedv") != null)
			codedv = Integer.parseInt(request.getParameter("codedv"));
		if ((request.getParameter("bsubmit") != null)
				&& (request.getParameter("pldv_code") != null))
			codedv = Integer.parseInt(request.getParameter("pldv_code"));
		if (request.getParameter("bsubmit") != null) {
			rd = sc.getRequestDispatcher("/division.jsp");
			codesv = catalog.AddService(conn, userinfo, request);
		}
		session.putValue("allpole",
				(ArrayList) catalog.getAllPole(conn, userinfo, request, 0));
		session.putValue("allservice", (ArrayList) catalog.getAllService(conn,
				userinfo, request, codedv, 0));
		session.putValue("oneservice", (ArrayList) catalog.getAllService(conn,
				userinfo, request, 0, codesv));
		session.putValue("allbureau", (ArrayList) catalog.getAllBureau(conn,
				userinfo, request, 0, codesv, 0, 0));
		session.putValue("allagent", (ArrayList) catalog.getAllAgent(conn,
				userinfo, request, 0, 0, codesv, 0, 0, 0));
		if (userinfo.getError_message().length() > 0)
			rd = sc.getRequestDispatcher("/error.jsp");
		rd.forward(request, response);
	}

	private void doBureau(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		RequestDispatcher rd = sc.getRequestDispatcher("/bureau.jsp");
		Catalog catalog = new Catalog();
		int codebu = Integer.parseInt(request.getParameter("codebu"));
		int codesv = 0;
		if (request.getParameter("bsubmit") != null) {
			codebu = catalog.AddBureau(conn, userinfo, request);
			codesv = Integer.parseInt(request.getParameter("pldv_code"));
			session.putValue("allbureau", (ArrayList) catalog.getAllBureau(
					conn, userinfo, request, 0, codesv, 0, 0));
			rd = sc.getRequestDispatcher("/service.jsp");
		}
		session.putValue("allpole",
				(ArrayList) catalog.getAllPole(conn, userinfo, request, 0));
		session.putValue("allservice", (ArrayList) catalog.getAllService(conn,
				userinfo, request, 0, 0));
		session.putValue("onebureau", (ArrayList) catalog.getAllBureau(conn,
				userinfo, request, codebu, 0, 0, 0));
		session.putValue("allagent", (ArrayList) catalog.getAllAgent(conn,
				userinfo, request, 0, codebu, 0, 0, 0, 0));
		if (userinfo.getError_message().length() > 0)
			rd = sc.getRequestDispatcher("/error.jsp");
		rd.forward(request, response);
	}

	private void doSynchroLdapAllAgent(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		System.out.println("synchroldapallagent: de Ldap vers mysql");
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		System.out.println("Agent :" + userinfo.getUser() + ":"
				+ userinfo.getProfil());
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		session.putValue("allagent", (ArrayList) catalog.SynchroLdapAllAgent(
				ldap, ldapdm, ldappw, ldapbasedn, conn, userinfo, request, 0,
				0, 0, 0, 0));
		rd = sc.getRequestDispatcher("/division.jsp");
		rd.forward(request, response);
	}

	private void doSynchroAllLdap(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, LDAPException, SQLException,
			ClassNotFoundException {
		System.out.println("synchroALLldapallagent: de Mysql vers Ldap");
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		System.out.println("Agent :" + userinfo.getUser() + ":"
				+ userinfo.getProfil());
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		catalog.setAllLdap(conn, ldap, ldapdm, ldappw, ldapbasedn, request);
		rd = sc.getRequestDispatcher("/division.jsp");
		rd.forward(request, response);
	}

	private void doPartage(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		System.out.println("Dispatcher partage:" + userinfo.getUser() + ":"
				+ userinfo.getProfil() + " codepa="
				+ request.getParameter("codepa") + " nompa="
				+ request.getParameter("nompa"));
		ListeParam(request);
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		int codepa = 0;
		if (request.getParameter("codepa") != null)
			codepa = Integer.parseInt(request.getParameter("codepa"));
		if (request.getParameter("nompa") != null)
			userinfo.setPa_nom(request.getParameter("nompa"));
		userinfo.setPa_code(codepa);
		int codedv = 0;
		if (request.getParameter("codedv") != null)
			codedv = Integer.parseInt(request.getParameter("codedv"));
		if (request.getParameter("bsubmit") != null) {
			codepa = catalog.AddPartage(conn, userinfo, request, ldappwS,
					samba_sid);
		}
		if (request.getParameter("bsubmit") != null
				&& request.getParameter("table").equals("partage_ng")
				&& request.getParameter("bsubmit")
						.equalsIgnoreCase("Supprimer")) {
			rd = sc.getRequestDispatcher("/listepartage.jsp");
		}
		if (request.getParameter("operation").equals("addpartage")) {
			System.out.println("addpartage :-------------- "
					+ request.getParameter("nompa"));
			rd = sc.getRequestDispatcher("/listepartage.jsp");
		}
		session.putValue("allpartage", (ArrayList) catalog.getAllPartage(conn,
				userinfo, request, codedv, 0));
		session.putValue("onepartage", (ArrayList) catalog.getAllPartage(conn,
				userinfo, request, 0, codepa));
		session.putValue("allagent", (ArrayList) catalog.getAllAgentPartage(
				conn, userinfo, request, 0, codepa));
		session.putValue("allagentdiv", (ArrayList) catalog.getAllAgent(conn,
				userinfo, request, 0, 0, 0, codedv, 0, 0));
		session.putValue("onedivision", (ArrayList) catalog.getAllDivision(
				conn, userinfo, request, 0, codedv));
		// modif du 18/02/2016 appel ne fonctionne plus table tagent supprimée
		//session.putValue("allmysql", (ArrayList) catalog.getMysql(conn,
			//	userinfo, request, 0, 0, 0, 0, 0));
		session.putValue("allscribe", (ArrayList) catalog.getLdapScribe(
				userinfo, ldap, ldapdm, ldappwS, ldapbasedn, request));

		rd.forward(request, response);
	}


	private void doPartageNg(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		System.out.println("Dispatcher partage:" + userinfo.getUser() + ":"
				+ userinfo.getProfil() + " codepa="
				+ request.getParameter("codepa") + " nompa="
				+ request.getParameter("nompa"));
		ListeParam(request);
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		int codepa = 0;
		if (request.getParameter("codepa") != null)
			codepa = Integer.parseInt(request.getParameter("codepa"));
		if (request.getParameter("nompa") != null)
			userinfo.setPa_nom(request.getParameter("nompa"));
		userinfo.setPa_code(codepa);
		int codedv = 0;
		if (request.getParameter("codedv") != null)
			codedv = Integer.parseInt(request.getParameter("codedv"));
		if (request.getParameter("bsubmit") != null) {
			codepa = catalog.AddPartage(conn, userinfo, request, ldappwS,
					samba_sid);
		}
		if (request.getParameter("bsubmit") != null
				&& request.getParameter("table").equals("partage_ng")
				&& request.getParameter("bsubmit")
						.equalsIgnoreCase("Supprimer")) {
			rd = sc.getRequestDispatcher("/listepartage.jsp");
		}
		if (request.getParameter("operation").equals("addpartage")) {
			System.out.println("addpartage :-------------- "
					+ request.getParameter("nompa"));
			rd = sc.getRequestDispatcher("/listepartage.jsp");
		}
		session.putValue("allpartage", (ArrayList) catalog.getAllPartage(conn,
				userinfo, request, codedv, 0));
		session.putValue("onepartage", (ArrayList) catalog.getAllPartage(conn,
				userinfo, request, 0, codepa));
		session.putValue("allagent", (ArrayList) catalog.getAllAgentPartage(
				conn, userinfo, request, 0, codepa));
		session.putValue("allagentdiv", (ArrayList) catalog.getAllAgent(conn,
				userinfo, request, 0, 0, 0, codedv, 0, 0));
		session.putValue("onedivision", (ArrayList) catalog.getAllDivision(
				conn, userinfo, request, 0, codedv));
		session.putValue("allmysql", (ArrayList) catalog.getMysql(conn,
				userinfo, request, 0, 0, 0, 0, 0));
		session.putValue("allscribe", (ArrayList) catalog.getLdapScribe(
				userinfo, ldap, ldapdm, ldappwS, ldapbasedn, request));

		rd.forward(request, response);
	}

	private void doAgent(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, LDAPException, SQLException,
			ClassNotFoundException {
		System.out.println("addagent:" + request.getParameter("bsubmit"));
		 
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		System.out.println("Agent :" + userinfo.getUser() + ":"
				+ userinfo.getProfil());
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		session.putValue("allchef",
				(ArrayList) catalog.getAllChef(conn, userinfo, request, 0));
		session.putValue("allldap", (ArrayList) catalog.getLdap(userinfo, ldap,
				ldapdm, ldappw, ldapbasedn, request));
		int codedv = 0;
//      ----------------------------------------------------------------- addagent
		 
		if (request.getParameter("crechorus") != null) {
			catalog.CreChorus(conn, userinfo, request);
		}
		if (request.getParameter("recldap") != null)
			session.putValue("allldap", (ArrayList) catalog.getLdap(userinfo,
					ldap, ldapdm, ldappw, ldapbasedn, request));

		if ((request.getParameter("modldap") != null)||(request.getParameter("bsubmit") != null)){
			codedv = catalog.AddAgent(conn, userinfo, request);
			catalog.setLdap(conn, ldap, ldapdm, ldappw, ldapbasedn,userinfo, request);
			catalog.setLdapDiv(conn, ldap, ldapdm, ldappw, ldapbasedn, request);
			System.out
			.println("--------------------------------- genere droit scribe------------------------------------");
			Runtime runtime = Runtime.getRuntime();
			Process process = null;
			String cmd0 = "ssh root@scribe '/usr/share/eole/x_genere_user "+ request.getParameter("ag_ldap_uid")+" ; ' ";
			String cmd = "ssh root@scribe 'cat /usr/share/eole/pwld ; ' ";
			process = runtime.exec(new String[] { "/bin/sh", "-c", cmd0 });
			System.out.println("cmd: " + cmd0);
			//DataInputStream in2 = new DataInputStream(process.getInputStream());
			//String line2 = null;
			//while (((line2 = in2.readLine()) != null)) {
			//	ldappwS = line2.substring(1, line2.length() - 1);
			//}
			System.out
					.println("----------------------------fin droit scribe-----------------------------------------");
		}
 	
		if (request.getParameter("modldapotp") != null)
			catalog.setLdapotp(conn, ldap, ldapdm, ldappw, ldapbasedn, request);
		
		if ((request.getParameter("bsubmit") != null)
				&& (request.getParameter("bsubmit").equals("Modifer"))) {
			codedv = Integer.parseInt(request.getParameter("codedv"));
			session.putValue("allagent", (ArrayList) catalog.getAllAgent(conn,
					userinfo, request, 0, 0, 0, codedv, 0, 0));
			rd = sc.getRequestDispatcher("/division.jsp");
		}

		if ((request.getParameter("bsubmit") != null)
				&& (request.getParameter("bsubmit").equals("Deplacer"))) {
			session.putValue("allagent", (ArrayList) catalog.getAllAgent(conn,
					userinfo, request, 0, 0, 0, codedv, 0, 0));
			rd = sc.getRequestDispatcher("/division.jsp");
		}

		if ((request.getParameter("bsubmit") != null)
				&& (request.getParameter("bsubmit")
						.equals("Ajouter Affectation"))) {
			session.putValue("allagent", (ArrayList) catalog.getAllAgent(conn,
					userinfo, request, 0, 0, 0, codedv, 0, 0));
			rd = sc.getRequestDispatcher("/division.jsp");
		}

		if ((request.getParameter("bsubmit") != null)
				&& (request.getParameter("bsubmit")
						.equals("Supprimer affectation"))) {
			codedv = Integer.parseInt(request.getParameter("codedv"));
			session.putValue("allagent", (ArrayList) catalog.getAllAgent(conn,
					userinfo, request, 0, 0, 0,
					Integer.parseInt(request.getParameter("codedv")), 0, 0));
			rd = sc.getRequestDispatcher("/division.jsp");
		}

		if ((request.getParameter("bsubmit") != null)
				&& (request.getParameter("bsubmit").equals("Supprimer"))) {
			codedv = Integer.parseInt(request.getParameter("codedv"));
			System.out.println("doAgent supprimer div=" + codedv);
			session.putValue("allagent", (ArrayList) catalog.getAllAgent(conn,
					userinfo, request, 0, 0, 0, codedv, 0, 0));
			System.out.println("redirect");
			rd = sc.getRequestDispatcher("/division.jsp");
		}
		if ((request.getParameter("bsubmit") != null)
				&& (request.getParameter("bsubmit").equals("Ajouter"))) {
			System.out.println("doAgent ajouter div=" + codedv);
			session.putValue("allagent", (ArrayList) catalog.getAllAgent(conn,
					userinfo, request, 0, 0, 0, codedv, 0, 0));
			System.out.println("redirect");
			rd = sc.getRequestDispatcher("/division.jsp");
		}

		if (request.getParameter("modldap") != null) {
			rd = sc.getRequestDispatcher("/" + request.getParameter("table")
					+ ".jsp");
		}

		if (request.getParameter("recldap") != null)
			rd = sc.getRequestDispatcher("/" + request.getParameter("table")
					+ ".jsp");

		System.out.println("------------> forward " + codedv + " "
				+ request.getParameter("codesv"));
        int int_ag=0;
        int int_bu=0;
        int int_sv=0;
        int int_dv=0;
        int int_pl=0;
        if (request.getParameter("codeag") != null ) int_ag=Integer.parseInt(request.getParameter("codeag"));
        if (request.getParameter("codebu") != null ) int_bu=Integer.parseInt(request.getParameter("codebu"));
        if (request.getParameter("codesv") != null ) int_sv=Integer.parseInt(request.getParameter("codesv"));
        if (request.getParameter("codedv") != null ) int_dv=Integer.parseInt(request.getParameter("codedv"));
        if (request.getParameter("codepl") != null ) int_pl=Integer.parseInt(request.getParameter("codepl"));
          
		session.putValue("onedivision", (ArrayList) catalog.getAllDivision(
				conn, userinfo, request, 0, codedv));
		session.putValue("alldivision", (ArrayList) catalog.getAllDivision(
				conn, userinfo, request, 0, 0));
		session.putValue("allservice", (ArrayList) catalog.getAllService(conn,
				userinfo, request, codedv, 0));
		session.putValue("oneservice", (ArrayList) catalog.getAllService(conn,
				userinfo, request, 0, int_sv));
		// session.putValue("allagent",(ArrayList)catalog.getAllAgent(conn,userinfo,request,
		// codeagt, codebur, codeser, codediv, codepol);
		// session.putValue("allagent",(ArrayList)catalog.getAllAgent(conn,userinfo,request,
		// 0,0,Integer.parseInt(request.getParameter("code")),0,0));
		System.out.println("------------> oneagent " + int_ag + " bu: " + int_bu + " sv: " + int_sv + " dv: " + int_dv );
		session.putValue("oneagent", (ArrayList) catalog.getAllAgent(conn,
				userinfo, request, int_ag, int_bu, int_sv, int_dv, int_pl, 0));
				 
		// session.putValue("oneagentaffecte",(ArrayList)catalog.getAllAgentAffecte(conn,userinfo,request,
		// Integer.parseInt(request.getParameter("codeag"))));
		session.putValue("alltree",
				(ArrayList) catalog.getAllTree(conn, userinfo, request, 0));
		rd.forward(request, response);
	}
	private void doAgentSansAff(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, LDAPException, SQLException,
			ClassNotFoundException {
		System.out.println("addagent sans aff:" + request.getParameter("bsubmit"));
		
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		System.out.println("Agent :" + userinfo.getUser() + ":"
				+ userinfo.getProfil());
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		session.putValue("allchef",
				(ArrayList) catalog.getAllChef(conn, userinfo, request, 0));
		session.putValue("allldap", (ArrayList) catalog.getLdap(userinfo, ldap,
				ldapdm, ldappw, ldapbasedn, request));
		int codedv = 0;
//      ----------------------------------------------------------------- addagent
		 
		if ((request.getParameter("bsubmit") != null)
				&& (request.getParameter("bsubmit").equals("Supprimer"))) {
			Statement st = conn.createStatement();
			String qry = "delete from   agent where ag_code=" + request.getParameter("codeag");
			System.out.println("qry del agent sans aff :" + qry);
			st.executeUpdate(qry);
			st.close();
		}
        int int_ag=0;
        int int_bu=0;
        int int_sv=0;
        int int_dv=0;
        int int_pl=0;
        if (request.getParameter("codeag") != null ) int_ag=Integer.parseInt(request.getParameter("codeag"));
        if (request.getParameter("codebu") != null ) int_bu=Integer.parseInt(request.getParameter("codebu"));
        if (request.getParameter("codesv") != null ) int_sv=Integer.parseInt(request.getParameter("codesv"));
        if (request.getParameter("codedv") != null ) int_dv=Integer.parseInt(request.getParameter("codedv"));
        if (request.getParameter("codepl") != null ) int_pl=Integer.parseInt(request.getParameter("codepl"));
          
		//session.putValue("onedivision", (ArrayList) catalog.getAllDivision(
		//		conn, userinfo, request, 0, codedv));
		//session.putValue("alldivision", (ArrayList) catalog.getAllDivision(
		//		conn, userinfo, request, 0, 0));
		//session.putValue("allservice", (ArrayList) catalog.getAllService(conn,
		//		userinfo, request, codedv, 0));
		//session.putValue("oneservice", (ArrayList) catalog.getAllService(conn,
		//		userinfo, request, 0, int_sv));
		// session.putValue("allagent",(ArrayList)catalog.getAllAgent(conn,userinfo,request,
		// codeagt, codebur, codeser, codediv, codepol);
		// session.putValue("allagent",(ArrayList)catalog.getAllAgent(conn,userinfo,request,
		// 0,0,Integer.parseInt(request.getParameter("code")),0,0));
		System.out.println("------------> oneagent " + int_ag + " bu: " + int_bu + " sv: " + int_sv + " dv: " + int_dv );
		session.putValue("oneagent", (ArrayList) catalog.getAllAgentSansAff(conn,
				userinfo, request, int_ag, int_bu, int_sv, int_dv, int_pl, 0));
		session.putValue("allagent", (ArrayList) catalog.getAgentSansAffectation(conn,
				userinfo, request, 0, 0, 0, 0, 0));		 
		// session.putValue("oneagentaffecte",(ArrayList)catalog.getAllAgentAffecte(conn,userinfo,request,
		// Integer.parseInt(request.getParameter("codeag"))));
		//session.putValue("alltree",
			//	(ArrayList) catalog.getAllTree(conn, userinfo, request, 0));
		rd.forward(request, response);
	}

	private void doRecherche(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		System.out.println("doRecherche" + request.getParameter("table"));
		System.out.println("-----------");
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		session.putValue("allagent", (ArrayList) catalog.getFindAgent(conn,
				userinfo, request, 0, 0, 0, 0, 0));
		rd.forward(request, response);
	}
	private void doAllAgents(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		System.out.println("doRecherche" + request.getParameter("table"));
		System.out.println("-----------");
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		session.putValue("allagent", (ArrayList) catalog.getAgentSansAffectation(conn,
				userinfo, request, 0, 0, 0, 0, 0));
		rd.forward(request, response);
	}
	
	
	private void doTrombi(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				  + "trombi.jsp");
		Catalog catalog = new Catalog();
		session.putValue("allagent", (ArrayList) catalog.getFindAgent(conn,
				userinfo, request, 0, 0, 0, 0, 0));
		rd.forward(request, response);
	}

	private void doRechercheLdap(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		ListeParam(request);
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		userinfo.setLast_req(request.getParameter("nom"));
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		session.putValue("allldap", (ArrayList) catalog.getLdap(userinfo, ldap,
				ldapdm, ldappw, ldapbasedn, request));
		rd.forward(request, response);
	}

	private void doSunScribe(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		ListeParam(request);
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		userinfo.setLast_req(request.getParameter("nom"));
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		session.putValue("allmysql", (ArrayList) catalog.getMysql(conn,
				userinfo, request, 0, 0, 0, 0, 0));
		session.putValue("allsun", (ArrayList) catalog.getLdapSun(userinfo,
				ldap, ldapdm, ldappw, ldapbasedn, request));
		session.putValue("allscribe", (ArrayList) catalog.getLdapScribe(
				userinfo, ldap, ldapdm, ldappwS, ldapbasedn, request));
		rd.forward(request, response);
	}
	private void doUserPartage(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		ListeParam(request);
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		userinfo.setLast_req(request.getParameter("nom"));
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		session.putValue("allpartage", (ArrayList) catalog.getAllPartage(conn,
				userinfo, request, 0, 0));
		session.putValue("allagent", (ArrayList) catalog.getAllAgentPartage(
				conn, userinfo, request, 0, 0));
		rd.forward(request, response);
	}

	private void doValidLdap(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		ListeParam(request);
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		userinfo.setLast_req(request.getParameter("nom"));
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		session.putValue("allldap", (ArrayList) catalog.getValidLdap(conn,
				userinfo, ldap, ldapdm, ldappw, ldapbasedn, request));
		rd.forward(request, response);
	}

	private void doSupLdap(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		ListeParam(request);
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		catalog.SupLdap(ldap, ldapdm, ldappw, ldapbasedn, request);
		session.putValue("allldap", (ArrayList) catalog.getLdap(userinfo, ldap,
				ldapdm, ldappw, ldapbasedn, request));
		rd.forward(request, response);
	}

	private void doAddPartage(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		ListeParam(request);
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		int codepa = userinfo.getPa_code();
		int codedv = userinfo.getDv_code();
		System.out.println("------------> codepa " + userinfo.getPa_code());
		RequestDispatcher rd = sc.getRequestDispatcher("/partage.jsp");
		Catalog catalog = new Catalog();
		catalog.AddLdapPartage(conn, userinfo, request, ldappwS);
		session.putValue("allpartage", (ArrayList) catalog.getAllPartage(conn,
				userinfo, request, codedv, 0));
		session.putValue("onepartage", (ArrayList) catalog.getAllPartage(conn,
				userinfo, request, 0, codepa));
		session.putValue("allagent", (ArrayList) catalog.getAllAgentPartage(
				conn, userinfo, request, 0, codepa));
		session.putValue("allagentdiv", (ArrayList) catalog.getAllAgent(conn,
				userinfo, request, 0, 0, 0, codedv, 0, 0));
		session.putValue("onedivision", (ArrayList) catalog.getAllDivision(
				conn, userinfo, request, 0, codedv));
		session.putValue("allmysql", (ArrayList) catalog.getMysql(conn,
				userinfo, request, 0, 0, 0, 0, 0));
		session.putValue("allscribe", (ArrayList) catalog.getLdapScribe(
				userinfo, ldap, ldapdm, ldappwS, ldapbasedn, request));
		rd.forward(request, response);
	}

	private void doRechercheDiv(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		RequestDispatcher rd = sc.getRequestDispatcher("/annuaire.jsp");
		Catalog catalog = new Catalog();
		session.putValue("alldivision", (ArrayList) catalog.getFindDivision(
				conn, userinfo, request, 0, 0));
		rd.forward(request, response);
	}

	private void doSynchroLdap(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		ArrayList allldap = (ArrayList) session.getValue("allldap");
		catalog.Synchro(conn, allldap, request);
		session.putValue("oneagent", (ArrayList) catalog.getAllAgent(conn,
				userinfo, request,
				Integer.parseInt(request.getParameter("code")), 0, 0, 0, 0, 0));
		catalog.setLdapSynchro(conn, ldap, ldapdm, ldappw, ldapbasedn, request);
		rd.forward(request, response);
	}

	private void doModifLdap(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, LDAPException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		ArrayList allldap = (ArrayList) session.getValue("allldap");
		System.out.println("------------> modif ldap");
		catalog.setLdap(conn, ldap, ldapdm, ldappw, ldapbasedn, userinfo , request);
		System.out.println("------------> modif ldap division");
		catalog.setLdapDiv(conn, ldap, ldapdm, ldappw, ldapbasedn, request);
		session.putValue("oneagent", (ArrayList) catalog.getAllAgent(conn,
				userinfo, request,
				Integer.parseInt(request.getParameter("code")), 0, 0, 0, 0, 0));
		rd.forward(request, response);
	}

	private void doLibelle(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		System.out.println("------------> dolibelle:"
				+ request.getParameter("table"));
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		Catalog catalog = new Catalog();
		if (request.getParameter("submit") != null)
			catalog.AddChef(conn, userinfo, request);
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		System.out.println("------------> dolibelle: avant");

		session.putValue("allchef",
				(ArrayList) catalog.getAllChef(conn, userinfo, request, 0));
		System.out.println("------------> dolibelle: apres");
		rd.forward(request, response);
	}

	private void doLogin(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException, LDAPException,
			ClassNotFoundException {
		System.out
				.println("------------------------ recherche ldap dispatcher ------------------------------ sur " + ldap);
		System.out.println("session ::" + request.getSession(false));
		UserInfo userinfo = new UserInfo();
		HttpSession session = request.getSession(true);
		LDAPConnection ld = null;
		LDAPEntry findEntry = null;
		int status = -1;
		String xdn = "";

		try {
			ld = new LDAPConnection();
			ld.connect(ldap, 389);
			System.out.println("authenticate");
			ld.authenticate(3, ldapdm, ldappw);
			System.out.println("authenticate .... ok");
			String MY_FILTER = "uid=" + request.getParameter("code");
			System.out.println("search : " + MY_FILTER);
			LDAPSearchResults res = ld.search(ldapbasedn, 2, MY_FILTER, null,
					false);
			int n = 0;
			String xcn = "";
			String xmail = "";
			String xuid = "";
			String xdate = "";
			int xag_code = 0;
			int xannu_profil_annu = 0;
			while (res.hasMoreElements()) {
				System.out.println("search more");
				findEntry = null;
				findEntry = res.next();
				xdn = findEntry.getDN();
				System.out.println("ok  trouve dn:" + xdn);
				status = 99;
				// bind de utilisateur
				// -------------------
				try {
					if (mode.equals("tst"))
						status = 0;
					else {
						ld.authenticate(3, xdn,
								request.getParameter("password"));
						status = 0;
						System.out.println("bind ok for "
								+ request.getParameter("code"));
					}
				} catch (LDAPException e) {
					System.out.println("Error  ldap      : " + ldapbasedn);
					userinfo.setError_message("login ou mot de passe ldap erroné!");

					System.out.println("erreur catch ");
				}
			}
			System.out.println("test status : ");
			if (status == 0) {
				String[] attrNames = { "datenaissance", "mail", "uid",
						"annu-profil-annu", "ag_code", "cn" };
				res = ld.search(ldapbasedn, 2, MY_FILTER, attrNames, false);
				n = 1;
				/* Get the attributes of the entry */
				LDAPAttributeSet findAttrs = findEntry.getAttributeSet();
				Enumeration enumAttrs = findAttrs.getAttributes();
				/* Loop on attributes */
				while (enumAttrs.hasMoreElements()) {
					LDAPAttribute anAttr = (LDAPAttribute) enumAttrs
							.nextElement();
					String attrName = anAttr.getName();
					//System.out.println("attrname:" + attrName);
					Enumeration enumVals = anAttr.getStringValues();
					while (enumVals.hasMoreElements()) {
						String aVal = (String) enumVals.nextElement();
						if (attrName.equals("cn"))
							xcn = aVal;
						else if (attrName.equals("uid"))
							xuid = aVal;
						else if (attrName.equals("annu-profil-annu"))
							xannu_profil_annu = Integer.parseInt(aVal);
						else if (attrName.equals("mail"))
							xmail = aVal;
						else if (attrName.equals("annu-code"))
							xag_code = Integer.parseInt(aVal);
					}
				}
				userinfo.setUser(xcn);
				userinfo.setCode(xuid);
				userinfo.setMail(xmail);
				System.out.println("mail:" + xmail + "  +profilannuldap "
						+ xannu_profil_annu + " prf userinfo:"
						+ userinfo.getProfil());
				userinfo.setType("user");

				if (request.getParameter("code").equals("alain.grainville")) {
					n = 3;
					userinfo.setType("admin");
					userinfo.setProfil(4);
				}

				System.out.println("test moderateur  : " + userinfo.getType());

				/* Done, so disconnect */
				if ((ld != null) && ld.isConnected()) {
					ld.disconnect();
				}
				// ---------------------------------------------------------------------------
				if (n == 0) {
					System.out.println("le couple numen:"
							+ request.getParameter("code")
							+ " , date de naissance:"
							+ request.getParameter("password")
							+ " est inconnu !");
					request.setAttribute("erreur", "Compte inconnu !");
					RequestDispatcher rd = sc
							.getRequestDispatcher("/login.jsp");
					rd.forward(request, response);
				} else
					System.out.println("valeur de n:" + n + " prfil:"
							+ xannu_profil_annu);
				userinfo.setProfil(xannu_profil_annu);
				Catalog catalog = new Catalog();
				// session.putValue("login",(ArrayList)catalog.getAllAgent(conn,userinfo,request,
				// xag_code ,0,0,0,0));
				ArrayList login = (ArrayList) catalog.getAllAgent(conn,
						userinfo, request, xag_code, 0, 0, 0, 0, 0);
				ItemAnnuaire L = new ItemAnnuaire();
				System.out.println("xag_code:" + xag_code
						+ " login nb entree mysql :" + login.size());

				if (login != null && login.size() > 0 && xag_code != 0) {
					L = (ItemAnnuaire) login.get(0);
					System.out.println("login nb entree :" + login.size()
							+ " tel:" + L.getAf_telephone() + " code:"
							+ L.getAg_code());

					userinfo.setAg_code(L.getAg_code());
					userinfo.setBu_code(L.getBu_code());
					userinfo.setSv_code(L.getSv_code());

					userinfo.setDv_code(L.getDv_code());
					userinfo.setDv_nom(L.getDv_nom());
					userinfo.setDv_nb(login.size());
					
					
					System.out.println("premiere affectation dv :" +  L.getDv_code()) ;
					
					 
					
					if ( login.size() > 1 )
					{
						ItemAnnuaire L1 = new ItemAnnuaire();
						L1 = (ItemAnnuaire) login.get(1);
						System.out.println("deuxieme affectation dv :" +  L1.getDv_code()) ;
						userinfo.setDv1_code(L1.getDv_code());
						userinfo.setDv1_nom(L1.getDv_nom());
					}
					
					
					
					
					String wposte = L.getAf_telephone().replaceAll(" ", "");

					if (L.getAf_telephone_dir().length() > 2)
						userinfo.setPoste(L.getAf_telephone_dir()
								.replaceAll(" ", "").substring(6, 10));
					else if (wposte.length() > 9)
						userinfo.setPoste(wposte.replaceAll(" ", "").substring(
								6, 10));
					else
						userinfo.setPoste("nd");
					System.out.println("wposte :" + userinfo.getPoste());
					// if (intranet==2)
					// userinfo.setProfil(-1);
					// else
					userinfo.setProfil(L.getAg_ldap_profil_annu());
				}
				if (L.getAg_ldap_profil_annu() == 0)
					userinfo.setType("rien");
				if (L.getAg_ldap_profil_annu() == 1)
					userinfo.setType("service");
				if (L.getAg_ldap_profil_annu() == 2)
					userinfo.setType("division");
				if (L.getAg_ldap_profil_annu() == 3)
					userinfo.setType("general");
				if (L.getAg_ldap_profil_annu() >= 6)
					userinfo.setType("admin");
				// ---------------------test si admin general ---------------
				System.out.println("request code "
						+ request.getParameter("code") + " profil:"
						+ userinfo.getType());
				// ---------------------------------------------------
			}
			if (mode.equals("tst")
					&& admin.equals(request.getParameter("code"))) {
				n = 4;
				status = 0;
				System.out.println("je ne sais pas prd mais je suis admin");
				userinfo.setProfil(4);
				userinfo.setType("admin");
			}
			System.out.println("status:" + status);
			userinfo.setError_message("");
			System.out.println("DISCONNECT LDAP");
			if ((ld != null) && ld.isConnected()) {
				ld.disconnect();
			}
			if (status == -1) {
				userinfo.setError_message(" identification avec compte de messagerie   ");
			}
			if (status == 99) {
				userinfo.setError_message("probleme login ...");
			}

			if (status == 99 || status == -1) {
				session.putValue("currentUser", userinfo);
				RequestDispatcher rd = sc.getRequestDispatcher("/login.jsp");
				rd.forward(request, response);
			} else {
				session.putValue("currentUser", userinfo);

				// ---------------------------------------------------
				if (userinfo.getProfil() >= 3)
					// this.doMenuFrame(request, response, conn);
					this.doMenu(request, response, conn);
				else
					this.doMenu(request, response, conn);
				// ----------------------------------------------------
			}
		} catch (LDAPException e) {
			System.out.println("Error ldap: " + e.toString());
			// Exception e = new Exception("The operation is unknown.");
			request.setAttribute("sourcePage",
					"servlet Dispatcher action:doLogin");
			request.setAttribute("javax.servlet.jsp.jspException", e);
			RequestDispatcher rd = sc.getRequestDispatcher("/PbGeneral.jsp");
			rd.forward(request, response);
		}
	}

	private void doLdap(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		session.putValue("allldap", (ArrayList) catalog.getLdap(userinfo, ldap,
				ldapdm, ldappw, ldapbasedn, request));
		session.putValue("allagent", (ArrayList) catalog.getLdap(userinfo,
				ldap, ldapdm, ldappw, ldapbasedn, request));

		rd.forward(request, response);
	}

	private void doLdapAsa(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		session.putValue("allasa", (ArrayList) catalog.getLdapAsa(ldap, ldapdm,
				ldappw, ldapbasedn, request));
		rd.forward(request, response);
	}

	private void doOtp(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		ListeParam(request);
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		request.setAttribute("dn", request.getParameter("dn"));
		request.setAttribute("nom", request.getParameter("nom"));
		request.setAttribute("prenom", request.getParameter("prenom"));
		request.setAttribute("profilasa", request.getParameter("profilasa"));
		request.setAttribute("otpserie", request.getParameter("otpserie"));
		request.setAttribute("date", request.getParameter("date"));
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		session.putValue("allasa", (ArrayList) catalog.getLdapAsa(ldap, ldapdm,
				ldappw, ldapbasedn, request));

		rd.forward(request, response);
	}
 
	private void doChorus(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		ListeParam(request);
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		session.putValue(
				"allasa",
				(ArrayList) catalog.getAllChorus(conn, request,
						Integer.parseInt(request.getParameter("code"))));
		rd.forward(request, response);
	}

	private void doModAgentChorus(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();
		catalog.ModAgentCharte(conn, userinfo, request);
		// session.putValue("allasa",(ArrayList)catalog.getAllChorus(conn,request,
		// Integer.parseInt(request.getParameter("code"))));
		session.putValue("allasa",
				(ArrayList) catalog.getAllChorus(conn, request, 0));
		rd.forward(request, response);
	}

	private void doModAgentOtp(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		Catalog catalog = new Catalog();

		catalog.setLdapOtp(conn, ldap, ldapdm, ldappw, ldapbasedn, request);
		// session.putValue("allasa",(ArrayList)catalog.getAllChorus(conn,request,
		// Integer.parseInt(request.getParameter("code"))));
		session.putValue("allasa", (ArrayList) catalog.getLdapAsa(ldap, ldapdm,
				ldappw, ldapbasedn, request));
		rd.forward(request, response);
	}

	private void doCharteOtp(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		// RequestDispatcher rd = sc.getRequestDispatcher("/redirect.jsp");
		Catalog catalog = new Catalog();
		catalog.CharteOtp(ldap, ldapdm, ldappw, ldapbasedn, conn, request,
				rep_pdf);
		session.putValue("vnom", (String) request.getParameter("nom"));
		session.putValue("vprenom", (String) request.getParameter("prenom"));
		session.putValue("allasa", (ArrayList) catalog.getLdapAsa(ldap, ldapdm,
				ldappw, ldapbasedn, request));
		// session.putValue("allagent",(ArrayList)catalog.getLdap(ldap,ldapdm,ldappw,ldapbasedn,request));
		System.out.println("appel openDocument");
		// ------------------------- test affichage pdf
		String file = request.getParameter("nom") + "_"
				+ request.getParameter("prenom") + ".pdf";
		openDocument(response, file);
	}

	private void doCharteChorus(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		System.out.println("doCharteChorus");
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		// RequestDispatcher rd = sc.getRequestDispatcher("/redirect.jsp");
		Catalog catalog = new Catalog();
		catalog.CharteChorus(conn, request,
				Integer.parseInt(request.getParameter("code")), rep_pdf);
		session.putValue("vnom", (String) request.getParameter("nom"));
		session.putValue("vprenom", (String) request.getParameter("prenom"));
		session.putValue(
				"allasa",
				(ArrayList) catalog.getAllChorus(conn, request,
						Integer.parseInt(request.getParameter("code"))));
		// session.putValue("allagent",(ArrayList)catalog.getLdap(ldap,ldapdm,ldappw,ldapbasedn,request));
		System.out.println("appel openDocument");
		// ------------------------- test affichage pdf
		String file = request.getParameter("nom") + "_"
				+ request.getParameter("prenom") + ".pdf";
		openDocument(response, file);
	}

	private void doVoirMission(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		if (userinfo.getVoir_mission() == 2)
			userinfo.setVoir_mission(1);
		else
			userinfo.setVoir_mission(2);

		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");

		rd.forward(request, response);
	}

	private void doSms(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		Catalog catalog = new Catalog();
		session.putValue("allagentsms", (ArrayList) catalog.getAllAgentSms(
				conn, userinfo, request, 0, 0, 0, 0, 0));
		RequestDispatcher rd = sc.getRequestDispatcher("/"
				+ request.getParameter("table") + ".jsp");
		rd.forward(request, response);
	}

	private void doEnvoiSms(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		// userinfo.setDdtcle(Integer.parseInt(request.getParameter("ddtcle")));
		Catalog catalog = new Catalog();
		System.out.println("message   :" + request.getParameter("message"));
		System.out.println("gsm   :" + request.getParameter("gsm"));
		StringTokenizer tokenizer = new StringTokenizer(
				request.getParameter("gsm"), ";,'\n'");
		String cmd = "";
		// String optiont=" -t "; // T si reunion

		try {
			response.setContentType("text/html");
			System.out
					.println("----------------------------s-m-s--------------------------------");
			int k = 0;
			while (tokenizer.hasMoreTokens()) {
				k++;
				String token = tokenizer.nextToken();
				System.out.println(k + "--------- gsm   :" + token + " ------ "
						+ token.length());
				if (token.length() > 9) {
					Runtime runtime = Runtime.getRuntime();
					Process process = null;
					{
						cmd = "echo -e '" + request.getParameter("message")
								+ "' > /tmp/messagesms ;  sendsms -d '" + token
								+ "'  -f /tmp/messagesms   mascarin";
					}

					PrintWriter pout = response.getWriter();

					process = runtime
							.exec(new String[] { "/bin/sh", "-c", cmd });
					System.out.println("cmd: " + cmd);
					DataInputStream in = new DataInputStream(
							process.getInputStream());
					pout = response.getWriter();
					pout.println("<br><b>resultat:</b>" + "");
					pout.println("<pre>");
					String line = null;
					while ((line = in.readLine()) != null) {
						pout.println(line);
					}
					process.getInputStream().close();
					DataInputStream err = new DataInputStream(
							process.getErrorStream());
					String line_err = null;
					while ((line_err = err.readLine()) != null) {
						pout.println("message envoyé à  " + token + " :"
								+ line_err);
					}
				}
			}
		} catch (Exception err) {
			System.out.println("message envoyé à  "
					+ request.getParameter("sel") + " :" + err.getMessage()
					+ "<BR>");
		} finally {
		}
		// this.doIntervention(request, response);
	}

	private void doTestIp(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Servlet Example: Showing Request Headers";
		out.println("<BODY BGCOLOR=\"#FDF5E6\">\n" + "<H1 ALIGN=CENTER>"
				+ title + "</H1>\n" + "<B>Request Method: </B>"
				+ request.getMethod() + "<BR>\n" + "<B>Request URI: </B>"
				+ request.getRequestURI() + "<BR>\n"
				+ "<B>Request Protocol: </B>" + request.getProtocol()
				+ "<BR><BR>\n" + "<TABLE BORDER=1 ALIGN=CENTER>\n"
				+ "<TR BGCOLOR=\"#FFAD00\">\n"
				+ "<TH>Header Name<TH>Header Value");
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			out.println("<TR><TD>" + headerName);
			out.println("    <TD>" + request.getHeader(headerName));
		}
		out.println("<TR><TD>" + "REMOTE_ADDR");
		out.println("    <TD>" + request.getRemoteAddr());
		out.println("<TR><TD>" + "pathinfo");
		out.println("    <TD>" + request.getPathInfo());
		out.println("<TR><TD>" + "RemoteHost");
		out.println("    <TD>" + request.getRemoteHost());
		out.println("<TR><TD>" + "pathtranslated");
		out.println("    <TD>" + request.getPathTranslated());
		out.println("<TR><TD>" + "forwarded");
		out.println("    <TD>" + request.getHeader("X_FORWARDED_FOR"));
		out.println("<TR><TD>" + "forwarded host");
		out.println("    <TD>" + request.getHeader("X_FORWARDED_HOST"));
		out.println("</TABLE>\n</BODY></HTML>");
	}

	private void doSendMail(HttpServletRequest request,
			HttpServletResponse response, java.sql.Connection conn)
			throws ServletException, IOException, SQLException,
			ClassNotFoundException {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getValue("currentUser");
		Catalog catalog = new Catalog();
		if (userinfo.getMail() == null)
			userinfo.setError_message("time out ...");
		else {
			String host = "smtp.ac-reunion.fr";
			int port = 25;
			Socket socket = new Socket(host, port);
			BufferedReader plec = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter pred = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())), true);
			Date DateJour = new Date();
			int month = DateJour.getMonth() + 1;
			String vdate = DateJour.getDate() + "/" + month + "-"
					+ DateJour.getHours() + ":" + DateJour.getMinutes() + ":"
					+ DateJour.getSeconds();

			// pour le test
			System.out.println("envoi mail de test: " + userinfo.getUser()
					+ " vers:" + request.getParameter("destinataire")
					+ "@ac-reunion.fr" + request.getParameter("url"));
			pred.println("HELO " + " securite ");
			pred.println("MAIL FROM:" + "sys@ac-reunion.fr");
			// pred.println("MAIL FROM:" +
			// request.getParameter("destinataire")+"@ac-reunion.fr");
			pred.println("RCPT TO:" + request.getParameter("destinataire")
					+ "@ac-reunion.fr");
			pred.println("DATA");
			pred.println("Subject:  Url a black-lister (copie)");
			pred.println("demande émise par :" + userinfo.getMail() + " le "
					+ vdate);
			pred.println("------------------------------------------------------------------");
			pred.println("Nom  : " + userinfo.getUser());
			pred.println("Grade: " + userinfo.getLibgrade());
			pred.println("Rne  : " + userinfo.getRne());
			pred.println("");
			pred.println("");
			pred.println("Urls proposées:");
			pred.println("---------------");
			pred.println(request.getParameter("url"));
			pred.println(".");
			pred.println("END");
			plec.close();
			pred.close();
			socket.close();
			userinfo.setError_message("votre demande a été enregistrée.");
		}
		RequestDispatcher rd = sc.getRequestDispatcher(request
				.getParameter("fpage"));

		rd.forward(request, response);
	}

	private void doUnknown(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Exception e = new Exception("The operation is unknown.");
		RequestDispatcher rd = sc.getRequestDispatcher("/login.jsp");
		request.setAttribute("javax.servlet.jsp.jspException", e);
		rd.forward(request, response);
	}

	public void openDocument(HttpServletResponse response, String sfile) {
		// File file = new File("/tmp/"+sfile);
		System.out.println("opendocument" + sfile);
		File file = new File(rep_pdf + sfile);
		// File file = new File("d:/data/Mes documents/api/"+sfile);
		byte[] bytes = new byte[(int) file.length()];
		try {
			InputStream is = new FileInputStream(file);
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0,pre-check=0");
			response.setHeader("Pragma", "public");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Cache-Control", "max-age=0");
			response.setHeader("Content-disposition",
					"inline; filename=test.pdf");
			// response.setHeader("Content-disposition",
			// "attachment; filename=test.pdf");
			response.setContentType("application/pdf;charset=UTF-8");
			response.setContentLength(bytes.length);
			OutputStream out = response.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();
			is.close();
			System.gc();
			file.delete();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
