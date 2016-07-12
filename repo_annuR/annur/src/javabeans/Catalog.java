package javabeans;

import netscape.ldap.*;

import javax.naming.NamingEnumeration;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.*;

import java.util.Date;
import java.util.*;
import java.io.*;

import uti2.*;

import java.lang.*;

import javabeans.*;

import javax.servlet.http.*;
import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.directory.InitialDirContext;

import java.util.*;

// PDF
import javax.servlet.*;
import javax.servlet.http.*;

import java.awt.Color;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

// 
public class Catalog extends HttpServlet {
	private String cnx;
	// PDF ---------------------------------------------------------
	static int taille = 0;
	private static Document document;
	private static Phrase texte;
	private static Paragraph titre;
	private static Cell cellule;
	private int coordY;
	// nombre de retour à la ligne pour les experiences professionnelles
	private int cptl = 0;
	// nombre de retour à la ligne général
	private int rl = 0;

	/** Couleur des traits */
	private static Color black = new Color(0x00, 0x00, 0x00);
	private static Color red = new Color(0xff, 0x33, 0x00);
	private static Color grey = new Color(0xc0, 0xc0, 0xc0);

	/** Content Byte */
	private static PdfContentByte cb;

	/** Polices graphiques : Helvetica (normal) */
	private static BaseFont bfhn;
	/** Polices graphiques : Times-Roman (normal) */
	private static BaseFont bftn;
	/** Polices graphiques : Times-Roman (gras) */
	private static BaseFont bftb;
	/** Polices graphiques : Times-Roman (gras et italique) */
	private static BaseFont bftbi;
	/** Polices graphiques : Courier (normal) */
	private static BaseFont bfcn;

	public int getMaxDivision(java.sql.Connection conn) { // System.out.println("Catalog: getAllQr:"+faqinfo.getTitre1());
		ArrayList alist = new ArrayList();
		String qry = "";
		qry = "select max(dv_code) max  from  division   ";
		int max = 0;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				max = rs.getInt("max");
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return max;
	}

	public int getMaxService(java.sql.Connection conn) { // System.out.println("Catalog: getAllQr:"+faqinfo.getTitre1());
		ArrayList alist = new ArrayList();
		String qry = "";
		qry = "select max(sv_code) max  from  service   ";
		int max = 0;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				max = rs.getInt("max");
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return max;
	}

	public int getMaxPartage(java.sql.Connection conn) { // System.out.println("Catalog: getAllQr:"+faqinfo.getTitre1());
		ArrayList alist = new ArrayList();
		String qry = "";
		qry = "select max(pa_code) max  from  partage   ";
		int max = 0;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				max = rs.getInt("max");
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return max;
	}

	public int getMaxBureau(java.sql.Connection conn) { // System.out.println("Catalog: getAllQr:"+faqinfo.getTitre1());
		ArrayList alist = new ArrayList();
		String qry = "";
		qry = "select max(bu_code) max  from  bureau   ";
		int max = 0;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				max = rs.getInt("max");
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return max;
	}

	public int getMaxAffectation(java.sql.Connection conn) { // System.out.println("Catalog: getAllQr:"+faqinfo.getTitre1());
		ArrayList alist = new ArrayList();
		String qry = "";
		qry = "select max(af_code) max  from  affectation";
		int max = 0;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				max = rs.getInt("max");
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return max;
	}

	public int getMaxAgent(java.sql.Connection conn) { // System.out.println("Catalog: getAllQr:"+faqinfo.getTitre1());
		ArrayList alist = new ArrayList();
		String qry = "";
		qry = "select max(ag_code) max  from  agent   ";
		int max = 0;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				max = rs.getInt("max");
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return max;
	}

	public ArrayList getAllPole(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request, int code) {
		// System.out.println("Catalog: getAllQr:"+faqinfo.getTitre1());
		ArrayList alist = new ArrayList();
		String qry = "";
		if (code != 0)
			qry = "select * from  pole where  pl_code = '" + code + " '   ";
		else
			qry = "select * from  pole order  by pl_code   ";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOnePole(rs, item);
				// calcul integrite si admin
				// -------------------------------
				int nb = 1;
				if (userinfo.getType().equals("admin")) {
					nb = 0;
					String qry2 = "select count(*)nb from affectation,division where af_dv_code = dv_code and dv_pl_code = "
							+ code;
					Statement st2 = conn.createStatement();
					ResultSet rs2 = st2.executeQuery(qry2);
					while (rs2.next()) {
						nb = nb + rs2.getInt("nb");
					}
					rs2.close();
					st2.close();
				}
				item.setPl_integrite(nb);

				// ------------------
				alist.add(item);
			}
			if (code == -1) {
				ItemAnnuaire item = new ItemAnnuaire();
				alist.add(item);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	public ArrayList getAllPartage(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request, int codediv, int codeser) {
		// codeser=codepar
		System.out.println(" ????????? getallpartage codediv = " + codediv
				+ "  ?????????????");
		System.out
				.println(" ?????????????????????????????????????????????????????? ");
		ArrayList alist = new ArrayList();
		String qry = "";
		String where_codeser = "";
		String where_codediv = "";
		if (codediv != 0)
			where_codediv = " and  pa_dv_code=" + codediv;
		if (codeser != 0)
			where_codeser = "  and pa_code=" + codeser;
	//	qry = "select * from  partage,division,agent  where dv_code=pa_dv_code   and pa_ag_code_proprio = ag_code    "
	//			+ where_codediv + where_codeser + " order by  pa_nom";
		qry = "select * from  partage,division   where dv_code=pa_dv_code        " 	+ where_codediv + where_codeser + " order by  pa_nom";
		System.out.println(" ????????? liste partage:" + qry);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOneDivision(rs, item);
				item = fgetOnePartage(rs, item);
				//item = fgetOneAgent(rs, item);
				codeser = item.getPa_code();
				// calcul integrite si admin
				// -------------------------------
				int nb = 0;
	// System.out.println("userinfo svcode:"+userinfo.getSv_code());

				 if (userinfo.getType().equals("admin")
						|| (userinfo.getProfil() == 2 && userinfo.getDv_code() == item
								.getDv_code())
						|| (userinfo.getSv_code() == codeser)) { } ;
						{
					String qry2 = "select count(*)nb from af_partage where ap_pa_code = "
							+ codeser;
					System.out.println("-------- integrite ------- : " + qry2);
					Statement st2 = conn.createStatement();
					ResultSet rs2 = st2.executeQuery(qry2);
					while (rs2.next()) {
						nb = nb + rs2.getInt("nb");
					} 
				}  
				if (userinfo.getType().equals("admin")
						|| (userinfo.getProfil() == 2 && userinfo.getDv_code() == item
								.getDv_code())
						|| (userinfo.getSv_code() == codeser)) { };
						{
					String qry2 = "select count(*)nb from af_partage_ldap where af_pa_code = "
							+ codeser;
					System.out.println("-------- integrite: --------" + qry2);
					Statement st2 = conn.createStatement();
					ResultSet rs2 = st2.executeQuery(qry2);
					while (rs2.next()) {
						nb = nb + rs2.getInt("nb");
					}
				}
						
				// System.out.println("---------  integrite:" + nb);
				item.setPa_integrite(nb);
				alist.add(item);
			}
			if (codeser == -1) {
				ItemAnnuaire item = new ItemAnnuaire();
				alist.add(item);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	public ArrayList getAllMission(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request, int code) {
		// System.out.println("Catalog: getAllQr:"+faqinfo.getTitre1());
		ArrayList alist = new ArrayList();
		String qry = "";
		if (code != 0)
			qry = "select * from  parametre where type=60 and  code = '" + code
					+ " '   ";
		else
			qry = "select count(*) as nb ,dv_nom,dv_nomc,dv_mission,sv_nom,sv_nomc,sv_mission from division,affectation,service where dv_code=af_dv_code and af_sv_code=sv_code GROUP BY dv_nom,sv_nom";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemMission item = new ItemMission();
				item = fgetOneMission(rs, item);
				// ------------------
				alist.add(item);
			}
			if (code == -1) {
				ItemMission item = new ItemMission();
				alist.add(item);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	public ArrayList getAllChorus(java.sql.Connection conn,
			HttpServletRequest request, int code) {
		// System.out.println("Catalog: getAllQr:"+faqinfo.getTitre1());
		ArrayList alist = new ArrayList();
		String qry = "";
		if (code != 0)
			qry = "select * from  agent where ag_code = '" + code + " '   ";
		else
			qry = "select * from agent where ag_gemalto_num !=0 order by ag_gemalto_num ;";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOneAgent(rs, item);
				// ------------------
				alist.add(item);
			}

			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			// userinfo.setError_message("Erreur: Catalog "+e.getMessage()+" "+qry);
			e.printStackTrace();
		}
		return alist;
	}

	public ArrayList getAllChef(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request, int code) {
		// System.out.println("Catalog: getAllQr:"+faqinfo.getTitre1());
		ArrayList alist = new ArrayList();
		String qry = "";
		if (code != 0)
			qry = "select * from  parametre where type=60 and  code = '" + code
					+ " '   ";
		else
			qry = "select * from  parametre where type=60 order  by  element   ";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemChef item = new ItemChef();
				item = fgetOneChef(rs, item);
				// ------------------
				alist.add(item);
			}
			if (code == -1) {
				ItemChef item = new ItemChef();
				alist.add(item);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	public ArrayList getAllTree(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request, int code) { // System.out.println("Catalog: getAllQr:"+faqinfo.getTitre1());
		ArrayList alist = new ArrayList();
		String qry = "";
		if (code != 0)
			qry = "select * from  pole where  pl_code = '" + code + " '   ";
		else
			qry = "select * from  pole order  by pl_code   ";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			String ligne = "";
			// String ligne=
			// "aux1 = insFld(foldersTree, gFld('Annuaire du rectorat', 'Dispatcher?operation=annuaire'))"
			// ;
			// alist.add(ligne);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOnePole(rs, item);
				int codepl = item.getPl_code();
				ligne = "aux1 = insFld(foldersTree, gFld('" + item.getPl_nom()
						+ "', 'Dispatcher?operation=pole&code="
						+ item.getPl_code() + "'))";
				alist.add(ligne);
				qry = "select * from  division where  dv_pl_code = '" + codepl
						+ " ' order by dv_nom  ";
				Statement st2 = conn.createStatement();
				ResultSet rs2 = st2.executeQuery(qry);
				while (rs2.next()) {
					item = fgetOneDivision(rs2, item);
					int codedv = item.getDv_code();
					ligne = "aux2 = insFld(aux1, gFld('" + item.getDv_nom()
							+ "', 'Dispatcher?operation=division&codedv="
							+ item.getDv_code() + "'))";
					alist.add(ligne);
					qry = "select * from  service where  sv_dv_code = '"
							+ codedv + " ' order by sv_tri, sv_nom ";
					Statement st3 = conn.createStatement();
					ResultSet rs3 = st3.executeQuery(qry);
					while (rs3.next()) {
						item = fgetOneService(rs3, item);
						ligne = "aux3 = insFld(aux2, gFld('" + item.getSv_nom()
								+ "', 'Dispatcher?operation=service&codedv="
								+ item.getDv_code() + "&codesv="
								+ item.getSv_code() + "'))";
						alist.add(ligne);
					}
					// ajout d'un service
					if ((userinfo.getType() != null)
							&& (userinfo.getType().equals("admin"))) {
						ligne = "aux3 = insFld(aux2, gFld('"
								+ "<b>+</b>"
								+ "', 'Dispatcher?operation=service&codesv=-1&codedv="
								+ item.getDv_code() + "&codesv=0" + "'))";
						// alist.add(ligne);
					}
				}
				// ajout d'une division
				if ((userinfo.getType() != null)
						&& (userinfo.getType().equals("admin"))) {
					ligne = "aux2 = insFld(aux1, gFld('"
							+ "<b>+</b>"
							+ "', 'Dispatcher?operation=division&codedv=-1&codepl="
							+ item.getPl_code() + "'))";
					// alist.add(ligne);
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	public ArrayList getAllDivSerBur(java.sql.Connection conn, UserInfo userinfo) { // System.out.println("Catalog: getAllQr:"+faqinfo.getTitre1());
		ArrayList alist = new ArrayList();
		String qry = "select * from division,pole  where dv_pl_code=pl_code order by dv_nom ";

		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);

			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOnePole(rs, item);
				item = fgetOneDivision(rs, item);
				int codedv = item.getDv_code();
				alist.add(item);
				qry = "select * from  service,division,pole  where dv_pl_code=pl_code and sv_dv_code=dv_code and  sv_dv_code = '"
						+ codedv + " '  order by sv_nom ";
				Statement st1 = conn.createStatement();
				ResultSet rs1 = st1.executeQuery(qry);
				while (rs1.next()) {
					ItemAnnuaire item1 = new ItemAnnuaire();
					item1 = fgetOnePole(rs1, item1);
					item1 = fgetOneDivision(rs1, item1);
					item1 = fgetOneService(rs1, item1);
					int codesv = item1.getSv_code();
					alist.add(item1);
					qry = "select * from  bureau,service,division,pole  where dv_pl_code=pl_code and sv_dv_code=dv_code and bu_sv_code=sv_code and  bu_sv_code = '"
							+ codesv + " '  order by bu_nom ";
					Statement st2 = conn.createStatement();
					ResultSet rs2 = st2.executeQuery(qry);
					while (rs2.next()) {
						ItemAnnuaire item2 = new ItemAnnuaire();
						item2 = fgetOnePole(rs2, item2);
						item2 = fgetOneDivision(rs2, item2);
						item2 = fgetOneService(rs2, item2);
						item2 = fgetOneBureau(rs2, item2);
						alist.add(item2);
					}
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	public ArrayList getDivTree(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request, int code) { // System.out.println("Catalog: getAllQr:"+faqinfo.getTitre1());
		ArrayList alist = new ArrayList();
		String qry = "";
		if (code != 0)
			qry = "select * from  division,service where  dv_code=sv_dv_code and sv_dv_code = '"
					+ code + " '   ";
		else
			qry = "select * from  service order  by sv_tri, sv_code   ";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			int n = 0;
			String ligne = " ";
			alist.add(ligne);
			while (rs.next()) {

				ItemAnnuaire item = new ItemAnnuaire();
				n++;
				if (n == 1) {
					item = fgetOneDivision(rs, item);
					ligne = "aux1 = insFld(foldersTree, gFld('"
							+ item.getDv_nom()
							+ "', 'demoFramesetRightFrame.html'))";
					alist.add(ligne);
				}
				;
				item = fgetOneService(rs, item);
				int codesv = item.getSv_code();
				ligne = "aux2 = insFld(aux1, gFld('" + item.getSv_nom()
						+ "', 'Dispatcher?operation=service&codesv="
						+ item.getSv_code() + "'))";
				alist.add(ligne);
				qry = "select * from  bureau where  bu_sv_code = '" + codesv
						+ " '   ";
				Statement st2 = conn.createStatement();
				ResultSet rs2 = st2.executeQuery(qry);
				while (rs2.next()) {
					item = fgetOneBureau(rs2, item);
					int codebu = item.getBu_code();
					ligne = "aux3 = insFld(aux2, gFld('" + item.getBu_nom()
							+ "', 'Dispatcher?operation=bureau&codebu="
							+ item.getBu_code() + "'))";
					alist.add(ligne);
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	public ArrayList getAllDivision(java.sql.Connection conn,
			UserInfo userinfo, HttpServletRequest request, int codepole,
			int codediv) {
		ArrayList alist = new ArrayList();
		System.out.println(" getAll Divisionn");
		String qry = "";
		String where_codediv = "";
		String where_codepole = "";
		if (codepole != 0)
			where_codepole = " and pl_code=" + codepole;
		if (codediv != 0)
			where_codediv = " and dv_code=" + codediv;
		qry = "select * from  division,pole  where pl_code=dv_pl_code "
				+ where_codediv + where_codepole + " order by dv_tri,dv_nom ";
		System.out.println("qry:" + qry);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOnePole(rs, item);
				item = fgetOneDivision(rs, item);
				// calcul integrite si admin
				// -------------------------------
				// System.out.println("userinfo dvcode:"+userinfo.getDv_code());
				int nb = 1;
				if (userinfo.getType().equals("admin")
						|| (userinfo.getDv_code() == codediv)) {
					nb = 0;
					String qry2 = "select count(*)nb from affectation where af_dv_code = "
							+ codediv;
					Statement st2 = conn.createStatement();
					ResultSet rs2 = st2.executeQuery(qry2);
					while (rs2.next()) {
						nb = rs2.getInt("nb");
					}
					// System.out.println("inetegrite:"+qry2);
					qry2 = "select count(*)nb from division, service where dv_code=sv_dv_code and dv_code = "
							+ codediv;
					st2 = conn.createStatement();
					rs2 = st2.executeQuery(qry2);
					while (rs2.next()) {
						nb = nb + rs2.getInt("nb");
					}
					// System.out.println("integreit="+nb);
					rs2.close();
					st2.close();
				}
				item.setDv_integrite(nb);
				// --------------------------------
				String qry3 = "select * from affectation,division,agent where af_dv_code = "
						+ codediv
						+ " and af_type = 1 and af_ag_code = ag_code ";
				// System.out.println("chef div:"+qry3);
				Statement st3 = conn.createStatement();
				ResultSet rs3 = st3.executeQuery(qry3);
				while (rs3.next()) {
					item = fgetOneAffectation(rs3, item);
					item = fgetOneAgent(rs3, item);
				}
				alist.add(item);
			}
			if (codediv == -1) {
				ItemAnnuaire item = new ItemAnnuaire();
				alist.add(item);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	public ArrayList getFindDivision(java.sql.Connection conn,
			UserInfo userinfo, HttpServletRequest request, int codepole,
			int codediv) {
		ArrayList alist = new ArrayList();
		String qry = "";
		String where_codediv = "";
		String where_codepole = "";
		if (codepole != 0)
			where_codepole = " and pl_code=" + codepole;
		if (codediv != 0)
			where_codediv = " and dv_code=" + codediv;
		qry = "select distinct pl_code,pl_nom,pl_nomc,pl_mission,dv_nom,dv_nomc,dv_mission,dv_code,dv_fax,dv_telephone,dv_tri,dv_mail,dv_adresse from   division,affectation,pole left join service on sv_dv_code=dv_code  where  pl_code=dv_pl_code and af_dv_code=dv_code "
				+ where_codediv
				+ where_codepole
				+ " and  ( dv_nom REGEXP '"
				+ request.getParameter("recherche")
				+ "' "
				+ " or  dv_nomc REGEXP '"
				+ request.getParameter("recherche")
				+ "' "
				+ " or  LOWER(dv_mission) REGEXP '"
				+ request.getParameter("recherche").toLowerCase()
				+ "'  "
				+ " or  LOWER(sv_mission) REGEXP '"
				+ request.getParameter("recherche").toLowerCase()
				+ "'  "
				+ " or  sv_nom     REGEXP '"
				+ request.getParameter("recherche")
				+ "'  "
				+ " or  sv_nomc    REGEXP '"
				+ request.getParameter("recherche")
				+ "'  "
				+ " or  LOWER(af_mission)    REGEXP '"
				+ request.getParameter("recherche").toLowerCase()
				+ "') "
				+ "    order by dv_tri,dv_nom ";
		System.out.println("qry:" + qry);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOnePole(rs, item);
				item = fgetOneDivision(rs, item);
				// calcul integrite si admin
				// -------------------------------
				System.out.println("userinfo dvcode:" + userinfo.getDv_code());
				int nb = 1;
				if (userinfo.getType().equals("admin")
						|| (userinfo.getDv_code() == codediv)) {
					nb = 0;
					String qry2 = "select count(*)nb from affectation where af_dv_code = "
							+ codediv;
					Statement st2 = conn.createStatement();
					ResultSet rs2 = st2.executeQuery(qry2);
					while (rs2.next()) {
						nb = rs2.getInt("nb");
					}
					// System.out.println("inetegrite:"+qry2);
					qry2 = "select count(*)nb from division, service where dv_code=sv_dv_code and dv_code = "
							+ codediv;
					st2 = conn.createStatement();
					rs2 = st2.executeQuery(qry2);
					while (rs2.next()) {
						nb = nb + rs2.getInt("nb");
					}
					// System.out.println("integreit="+nb);
					rs2.close();
					st2.close();
				}
				item.setDv_integrite(nb);
				// --------------------------------
				String qry3 = "select * from affectation,division,agent where af_dv_code = "
						+ codediv
						+ " and af_type = 1 and af_ag_code = ag_code ";
				// System.out.println("chef div:"+qry3);
				Statement st3 = conn.createStatement();
				ResultSet rs3 = st3.executeQuery(qry3);
				while (rs3.next()) {
					item = fgetOneAffectation(rs3, item);
					item = fgetOneAgent(rs3, item);
				}
				alist.add(item);
			}
			if (codediv == -1) {
				ItemAnnuaire item = new ItemAnnuaire();
				alist.add(item);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	public ArrayList getAllService(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request, int codediv, int codeser) {
		ArrayList alist = new ArrayList();
		String qry = "";
		String where_codeser = "";
		String where_codediv = "";

		if (codeser != 0)
			where_codeser = " and sv_code=" + codeser;
		if (codediv != 0)
			where_codediv = " and dv_code=" + codediv;

		qry = "select * from  service,division,pole where dv_code= sv_dv_code and pl_code=dv_pl_code "
				+ where_codeser
				+ where_codediv
				+ " order by dv_nom, sv_tri, sv_nom";
		System.out.println("liste service:" + qry);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOnePole(rs, item);
				item = fgetOneDivision(rs, item);
				item = fgetOneService(rs, item);
				codeser = item.getSv_code();
				// calcul integrite si admin
				// -------------------------------
				int nb = 1;
				// System.out.println("userinfo svcode:"+userinfo.getSv_code());

				if ((userinfo.getProfil() > 2)
						|| (userinfo.getProfil() == 2 && userinfo.getDv_code() == item
								.getDv_code())
						|| (userinfo.getSv_code() == codeser)) {
					nb = 0;
					String qry2 = "select count(*)nb from affectation where af_sv_code = "
							+ codeser;
					// System.out.println("inetegrite:"+qry2);
					Statement st2 = conn.createStatement();
					ResultSet rs2 = st2.executeQuery(qry2);
					while (rs2.next()) {
						nb = nb + rs2.getInt("nb");
					}
					qry2 = "select count(*)nb from service,bureau where sv_code = bu_sv_code and bu_code != 0 and sv_code = "
							+ codeser;
					// System.out.println("inetegrite:"+qry2);
					st2 = conn.createStatement();
					rs2 = st2.executeQuery(qry2);
					// System.out.println("inetegrite:"+qry2);
					while (rs2.next()) {
						nb = nb + rs2.getInt("nb");
					}
					rs2.close();
					st2.close();
				}
				item.setSv_integrite(nb);
				// ----------------------
				String qry3 = "select * from affectation,service,agent where af_sv_code = "
						+ codeser
						+ " and af_type = 2 and af_ag_code = ag_code and sv_code = af_sv_code ";
				// System.out.println("chef ser:"+qry3);
				Statement st3 = conn.createStatement();
				ResultSet rs3 = st3.executeQuery(qry3);
				while (rs3.next()) {

					item = fgetOneAffectation(rs3, item);
					item = fgetOneAgent(rs3, item);
					// System.out.println("trouve un pti chef: "+item.getAg_ldap_nom());
				}
				alist.add(item);
			}
			if (codeser == -1) {
				ItemAnnuaire item = new ItemAnnuaire();
				alist.add(item);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	public ArrayList getAllBureau(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request, int codebur, int codeser, int codediv,
			int codepol) {

		ArrayList alist = new ArrayList();
		String where_codebur = "";
		String where_codeser = "";
		String where_codediv = "";
		String where_codepol = "";

		if (codebur != 0)
			where_codebur = " and bu_code=" + codebur;
		if (codeser != 0)
			where_codeser = " and sv_code=" + codeser;
		if (codediv != 0)
			where_codediv = " and dv_code=" + codediv;
		if (codepol != 0)
			where_codepol = " and pl_code=" + codepol;

		String qry = "select * from   pole,division   left join service on sv_dv_code=dv_code  left join bureau on bu_sv_code=sv_code where   dv_pl_code=pl_code "
				+ where_codebur
				+ where_codeser
				+ where_codediv
				+ where_codepol
				+ " order by bu_tri ";
		// System.out.println("allbureaux:"+qry);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOnePole(rs, item);
				item = fgetOneDivision(rs, item);
				item = fgetOneService(rs, item);
				item = fgetOneBureau(rs, item);
				alist.add(item);
			}
			if (codebur == -1) {
				ItemAnnuaire item = new ItemAnnuaire();
				alist.add(item);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	public ArrayList zzzzzzzzzzzzgetAllBureaux(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request, int codebur, int codeser, int codediv,
			int codepol) {

		ArrayList alist = new ArrayList();
		String where_codebur = "";
		String where_codeser = "";
		String where_codediv = "";
		String where_codepol = "";

		if (codebur != 0)
			where_codebur = " and bu_code=" + codebur;
		if (codeser != 0)
			where_codeser = " and sv_code=" + codeser;
		if (codediv != 0)
			where_codediv = " and dv_code=" + codediv;
		if (codepol != 0)
			where_codepol = " and pl_code=" + codepol;

		String qry = "select * from   pole,division   left join service on sv_dv_code=dv_code  left join bureau on bu_sv_code=sv_code where   dv_pl_code=pl_code "
				+ where_codebur + where_codeser + where_codediv + where_codepol;
		System.out.println("allbureaux:" + qry);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOnePole(rs, item);
				item = fgetOneDivision(rs, item);
				item = fgetOneService(rs, item);
				item = fgetOneBureau(rs, item);
				alist.add(item);
			}
			qry = "select * from   pole,division   left join service on sv_dv_code=dv_code    where   dv_pl_code=pl_code "
					+ where_codeser + where_codediv + where_codepol;
			System.out.println("allbureaux:" + qry);
			rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOnePole(rs, item);
				item = fgetOneDivision(rs, item);
				item = fgetOneService(rs, item);
				alist.add(item);
			}
			qry = "select * from    division   left join service on sv_dv_code=dv_code    where   dv_pl_code=pl_code "
					+ where_codeser + where_codediv + where_codepol;
			System.out.println("allbureaux:" + qry);
			rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOnePole(rs, item);
				item = fgetOneDivision(rs, item);
				item = fgetOneService(rs, item);
				alist.add(item);
			}
			if (codebur == -1) {
				ItemAnnuaire item = new ItemAnnuaire();
				alist.add(item);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	public ArrayList getAllAgentOld(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request, int codeagt, int codebur, int codeser,
			int codediv, int codepol, int codepa) {
		System.out.println("------getallAgent-----------");
		ArrayList alist = new ArrayList();
		String order = "";
		if (request.getParameter("order") != null)
			order = request.getParameter("order") + ",";
		String where_codeagt = "";
		String where_codebur = "";
		String where_codeser = "";
		String where_codediv = "";
		String where_codepol = "";
		String where_partage = "";
		if (codeagt != 0)
			where_codeagt = " and ag_code=" + codeagt;
		if (codebur != 0)
			where_codebur = " and bu_code=" + codebur;
		if (codeser != 0)
			where_codeser = " and sv_code=" + codeser;
		if (codediv != 0)
			where_codediv = " and dv_code=" + codediv;
		if (codepa != 0)
			where_partage = " and ag_code NOT IN (select ap_ag_code from af_partage where ap_code ="
					+ codepa + ")";
		// if (codepol != 0) where_codepol = " and pl_code=" + codepol ;
		String qry = "select * from  agent,affectation,division,bureau,service,pole LEFT JOIN grade ON gr_code=ag_ldap_grade   where "
				+ "   af_ag_code=ag_code and af_dv_code=dv_code  and af_bu_code=bu_code and af_pl_code=pl_code and af_sv_code=sv_code"
				+ where_codeagt
				+ where_codebur
				+ where_codeser
				+ where_codediv
				+ where_codepol
				+ where_partage
				+ " order by "
				+ order
				+ "ag_ldap_nom,ag_ldap_prenom ";
		System.out.println("get allagent qry:" + qry
				+ "\n*******************************************************");
		try {
			System.out.println("recherche agent");
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOnePole(rs, item);
				item = fgetOneGrade(rs, item);
				item = fgetOneDivision(rs, item);
				item = fgetOneService(rs, item);
				item = fgetOneBureau(rs, item);
				item = fgetOneAgent(rs, item);
				item = fgetOneAffectation(rs, item);
				alist.add(item);
			}
			if (codeagt == -1) {
				System.out.println("init vide de agent");
				ItemAnnuaire item = new ItemAnnuaire();
				alist.add(item);
			}
			System.out.println("nb agent trouves : " + alist.size());
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	public ArrayList getAllAgent(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request, int codeagt, int codebur, int codeser,
			int codediv, int codepol, int codepa) {
		System.out.println("------getallAgent-----------");
		ArrayList alist = new ArrayList();
		String order = "";
		if (request.getParameter("order") != null)
			order = request.getParameter("order") + ",";
		String where_codeagt = "";
		String where_codebur = "";
		String where_codeser = "";
		String where_codediv = "";
		String where_codepol = "";
		String where_partage = "";
		if (codeagt != 0)
			where_codeagt = " and ag_code=" + codeagt;
		if (codebur != 0)
			where_codebur = " and bu_code=" + codebur;
		if (codeser != 0)
			where_codeser = " and sv_code=" + codeser;
		if (codediv != 0)
			where_codediv = " and dv_code=" + codediv;
		if (codepa != 0)
			where_partage = " and ag_code NOT IN (select ap_ag_code from af_partage where ap_code ="
					+ codepa + ")";
		// if (codepol != 0) where_codepol = " and pl_code=" + codepol ;
		String qry = "select * from  agent,affectation,division,bureau,pole LEFT JOIN grade ON gr_code=ag_ldap_grade LEFT JOIN   service ON  af_sv_code=sv_code  where "
				+ "   af_ag_code=ag_code and af_dv_code=dv_code  and af_bu_code=bu_code and af_pl_code=pl_code "
				+ where_codeagt
				+ where_codebur
				+ where_codeser
				+ where_codediv
				+ where_codepol
				+ where_partage
				+ " order by "
				+ order
				+ "ag_ldap_nom,ag_ldap_prenom ";
		System.out.println("get allagent qry:" + qry
				+ "\n*******************************************************");
		try {
			System.out.println("recherche agent");
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOnePole(rs, item);
				item = fgetOneGrade(rs, item);
				item = fgetOneDivision(rs, item);
				item = fgetOneService(rs, item);
				item = fgetOneBureau(rs, item);
				item = fgetOneAgent(rs, item);
				item = fgetOneAffectation(rs, item);
				
				if (codeagt > 0) {
					
					int nb = 1;
						nb = 0;
						String qry2 = "select count(*)nb ,ag_code  from affectation,agent WHERE af_ag_code = ag_code  and ag_code =   "
								+ codeagt + " GROUP BY ag_code ";
						Statement st2 = conn.createStatement();
						ResultSet rs2 = st2.executeQuery(qry2);
						while (rs2.next()) {
							nb = nb + rs2.getInt("nb");
						}
						rs2.close();
						st2.close();
					
					item.setAg_integrite(nb); 
					System.out.println(" -----------------calcul integrite ag ----------------------------:" );
					
				}
				
				
				alist.add(item);
			}
			if (codeagt == -1) {
				System.out.println("init vide de agent");
				ItemAnnuaire item = new ItemAnnuaire();
				alist.add(item);
			}
						
				
			System.out.println("nb agent trouves : " + alist.size());
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	
	public ArrayList getAllAgentSansAff(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request, int codeagt, int codebur, int codeser,
			int codediv, int codepol, int codepa) {
		System.out.println("------getallAgentSansAff-----------");
		ArrayList alist = new ArrayList();
		String order = "";
		if (request.getParameter("order") != null)
			order = request.getParameter("order") + ",";
		String where_codeagt = "";
		String where_codebur = "";
		String where_codeser = "";
		String where_codediv = "";
		String where_codepol = "";
		String where_partage = "";
		if (codeagt != 0)
			where_codeagt = "   ag_code=" + codeagt;
		if (codebur != 0)
			where_codebur = " and bu_code=" + codebur;
		if (codeser != 0)
			where_codeser = " and sv_code=" + codeser;
		if (codediv != 0)
			where_codediv = " and dv_code=" + codediv;
		if (codepa != 0)
			where_partage = " and ag_code NOT IN (select ap_ag_code from af_partage where ap_code ="
					+ codepa + ")";
		// if (codepol != 0) where_codepol = " and pl_code=" + codepol ;
		String qry = "select * from  agent where  "
				+ where_codeagt
				+ where_codebur
				+ where_codeser
				+ where_codediv
				+ where_codepol
				+ where_partage
				+ " order by "
				+ order
				+ "ag_ldap_nom,ag_ldap_prenom ";
		//qry="SELECT * FROM agent   LEFT JOIN affectation ON ag_code=af_ag_code WHERE af_ag_code IS null";
		System.out.println("get allagent sans aff qry:" + qry
				+ "\n*******************************************************");
		try {
			System.out.println("recherche agent");
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				 
				item = fgetOneAgent(rs, item);
				 
				alist.add(item);
			}
			if (codeagt == -1) {
				System.out.println("init vide de agent");
				ItemAnnuaire item = new ItemAnnuaire();
				alist.add(item);
			}
			System.out.println("nb agent trouves : " + alist.size());
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	
	
	public ArrayList getAllAgentPartage(java.sql.Connection conn,
			UserInfo userinfo, HttpServletRequest request, int codeagt,
			int codepar) {
		System.out.println("------getallAgentPartage-----------");
		ArrayList alist = new ArrayList(); 
		String order = "";
		if (request.getParameter("order") != null)
			order = request.getParameter("order") + ",";
		String where_codeagt = "";
		String where_codepar = "";

		if (codeagt != 0)
			where_codeagt = " and ag_code=" + codeagt;
		if (codepar != 0)
			where_codepar = " and pa_code=" + codepar;

		// if (codepol != 0) where_codepol = " and pl_code=" + codepol ;
		
		// select distinct * from  agent,af_partage ,partage   where
		//   ap_ag_code=ag_code and ap_pa_code=pa_code    and pa_code=15 order by ag_ldap_nom,ag_ldap_prenom
		String qry = "select * from   af_partage, partage  left join agent on ag_code=ap_ag_code left join division on dv_code=ap_dv_code left join service on sv_code=ap_sv_code  where "
				+ "   ap_pa_code=pa_code   "
				+ where_codeagt
				+ where_codepar
				+ " order by "
				+ order
				+ "ag_ldap_nom,ag_ldap_prenom ";
		String qryx = "select * from   af_partage, partage left join agent on ag_code=ap_ag_code   where "
			    + where_codeagt
				+ where_codepar
				+ " order by "
				+ order
				+ "ag_ldap_nom,ag_ldap_prenom "; 
		System.out.println("get allagent qry:" + qry
				+ "\n*******************************************************");
		try {
			System.out.println("recherche agent  partage");
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOneAgent(rs, item);
				item = fgetOneDivision(rs, item);
				item = fgetOneService(rs, item);
				//item = fgetOneAffectation(rs, item);
				item = fgetOnePartage(rs, item);
				item = fgetOneAffectationPartage(rs, item);
				alist.add(item);
			}
			System.out.println("recherche agent  partage");
			qry = "select * from   af_partage_ldap ,partage   where   af_pa_code=pa_code  "
					+ where_codepar;
			System.out
					.println("get allagent qry:"
							+ qry
							+ "\n*******************************************************");

			st = conn.createStatement();
			rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				System.out.println("ajout ldap:"+ rs.getString("af_uid_ldap"));

				item.setAg_ldap_uid(rs.getString("af_uid_ldap"));
				item.setAg_ldap_nom(rs.getString("af_nom_ldap"));
				item.setAg_ldap_prenom(rs.getString("af_prenom_ldap"));
				item.setAg_ldap_mail(rs.getString("af_mail_ldap"));
				item.setAg_ldap_datenaiss(rs.getString("af_dtn_ldap"));
				item.setAf_bureau("ldap");
				item = fgetOnePartage(rs, item);
				alist.add(item);
			}
			if (codepar == -1) {
				System.out.println("init vide de agent partage");
				ItemAnnuaire item = new ItemAnnuaire();

				alist.add(item);
			}
			System.out.println("nb agent trouves : " + alist.size());
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	
	
	public ArrayList getAllAgentPartageNg(java.sql.Connection conn,
			UserInfo userinfo, HttpServletRequest request, int codeagt,
			int codepar) {
		System.out.println("------getallAgentPartage-----------");
		ArrayList alist = new ArrayList(); 
		String order = "";
		if (request.getParameter("order") != null)
			order = request.getParameter("order") + ",";
		String where_codeagt = "";
		String where_codepar = "";

		if (codeagt != 0)
			where_codeagt = " and ag_code=" + codeagt;
		if (codepar != 0)
			where_codepar = " and pa_code=" + codepar;

		// if (codepol != 0) where_codepol = " and pl_code=" + codepol ;
		
		// select distinct * from  agent,af_partage ,partage   where
		//   ap_ag_code=ag_code and ap_pa_code=pa_code    and pa_code=15 order by ag_ldap_nom,ag_ldap_prenom
		String qry = "select * from   af_partage_ng, partage  left join agent on ag_code=ap_ag_code   where "
				+ "   ap_pa_code=pa_code  "
				+ where_codeagt
				+ where_codepar
				+ " order by "
				+ order
				+ "ag_ldap_nom,ag_ldap_prenom ";
		String qryx = "select * from   af_partage_ng, partage left join agent on ag_code=ap_ag_code   where "
			    + where_codeagt
				+ where_codepar
				+ " order by "
				+ order
				+ "ag_ldap_nom,ag_ldap_prenom "; 
		System.out.println("get allagent qry:" + qry
				+ "\n*******************************************************");
		try {
			System.out.println("recherche agent  partage");
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOneAgent(rs, item);
				item = fgetOneDivision(rs, item);
				//item = fgetOneAffectation(rs, item);
				item = fgetOnePartage(rs, item);
				item = fgetOneAffectationPartage(rs, item);
				alist.add(item);
			}
			System.out.println("recherche agent  partage");
			qry = "select * from   af_partage_ldap ,partage   where   af_pa_code=pa_code  "
					+ where_codepar;
			System.out
					.println("get allagent qry:"
							+ qry
							+ "\n*******************************************************");

			st = conn.createStatement();
			rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				System.out.println("ajout ldap:"+ rs.getString("af_uid_ldap"));

				item.setAg_ldap_uid(rs.getString("af_uid_ldap"));
				item.setAg_ldap_nom(rs.getString("af_nom_ldap"));
				item.setAg_ldap_prenom(rs.getString("af_prenom_ldap"));
				item.setAg_ldap_mail(rs.getString("af_mail_ldap"));
				item.setAg_ldap_datenaiss(rs.getString("af_dtn_ldap"));
				item.setAf_bureau("ldap");
				item = fgetOnePartage(rs, item);
				alist.add(item);
			}
			if (codepar == -1) {
				System.out.println("init vide de agent partage");
				ItemAnnuaire item = new ItemAnnuaire();

				alist.add(item);
			}
			System.out.println("nb agent trouves : " + alist.size());
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	public ArrayList getAllAgentSms(java.sql.Connection conn,
			UserInfo userinfo, HttpServletRequest request, int codeagt,
			int codebur, int codeser, int codediv, int codepol) {
		System.out.println("getallAgentSms");
		ArrayList alist = new ArrayList();
		String where_sms = " and ag_gsm != '' ";
		String where_codeagt = "";
		String where_codebur = "";
		String where_codeser = "";
		String where_codediv = "";
		String where_codepol = "";
		if (codeagt != 0)
			where_codeagt = " and ag_code=" + codeagt;
		if (codebur != 0)
			where_codebur = " and bu_code=" + codebur;
		if (codeser != 0)
			where_codeser = " and sv_code=" + codeser;
		if (codediv != 0)
			where_codediv = " and dv_code=" + codediv;
		// if (codepol != 0) where_codepol = " and pl_code=" + codepol ;
		String qry = "select * from  agent,affectation,division,service,bureau,pole LEFT JOIN grade ON gr_code=ag_ldap_grade where "
				+ "   af_ag_code=ag_code and af_dv_code=dv_code and af_sv_code=sv_code and af_bu_code=bu_code and af_pl_code=pl_code"
				+ where_codeagt
				+ where_codebur
				+ where_codeser
				+ where_codediv
				+ where_codepol
				+ where_sms
				+ " order by ag_ldap_nom,ag_ldap_prenom ";
		System.out.println("get allagent qry:" + qry
				+ "\n*******************************************************");
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOnePole(rs, item);
				item = fgetOneGrade(rs, item);
				item = fgetOneDivision(rs, item);
				item = fgetOneService(rs, item);
				item = fgetOneBureau(rs, item);
				item = fgetOneAgent(rs, item);
				item = fgetOneAffectation(rs, item);
				alist.add(item);
			}
			if (codeagt == -1) {
				System.out.println("init vide de agent");
				ItemAnnuaire item = new ItemAnnuaire();

				alist.add(item);
			}
			System.out
					.println("get allagent qry:"
							+ alist.size()
							+ "\n*******************************************************");

			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	public ArrayList SynchroLdapAllAgent(String ldap, String ldapdm,
			String ldappw, String ldapbasedn, java.sql.Connection conn,
			UserInfo userinfo, HttpServletRequest request, int codeagt,
			int codebur, int codeser, int codediv, int codepol) {
		System.out.println("SynchroLdapAllAgent");
		LDAPConnection ld = null;
		LDAPEntry findEntry = null;
		int status = -1;
		String xdn = "";
		String qry = "select * from  agent,affectation,division,service,bureau,pole LEFT JOIN grade ON gr_code=ag_ldap_grade where "
				+ "   af_ag_code=ag_code and af_dv_code=dv_code  and af_sv_code=sv_code and af_bu_code=bu_code and af_pl_code=pl_code ";
		ArrayList alist = new ArrayList();
		try {
			ld = new LDAPConnection();
			ld.connect(ldap, 389);
			System.out.println("authenticate");
			ld.authenticate(3, ldapdm, ldappw);

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOneAgent(rs, item);
				Catalog catalog = new Catalog();
				catalog.getLdapByDn(ld, ldap, ldapdm, ldappw, ldapbasedn,
						request, item.getAg_ldap_uid(), item);
				String xdate = item.getAg_ldap_datenaiss();
				if (xdate.length() == 10)
					qry = "update agent   set " + " ag_ldap_mail   = '"
							+ item.getAg_ldap_mail() + "' ,"
							+ " ag_ldap_pw     = '" + item.getAg_ldap_pw()
							+ "' ," + " ag_ldap_grade  = '"
							+ item.getAg_ldap_grade() + "', "
							+ " ag_ldap_datenaiss  = '"
							+ item.getAg_ldap_datenaiss().substring(6, 10)
							+ item.getAg_ldap_datenaiss().substring(3, 5)
							+ item.getAg_ldap_datenaiss().substring(0, 2)
							+ "' " + " where ag_code=" + item.getAg_code();
				else
					qry = "update agent   set " + " ag_ldap_mail   = '"
							+ item.getAg_ldap_mail() + "' ,"
							+ " ag_ldap_pw     = '" + item.getAg_ldap_pw()
							+ "' ," + " ag_ldap_grade  = '"
							+ item.getAg_ldap_grade() + "', "
							+ " ag_ldap_datenaiss  = '" + "19000101" + "' "
							+ " where ag_code=" + item.getAg_code();
				System.out.println("qry update:" + qry);
				st.executeUpdate(qry);
				alist.add(item);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		} catch (LDAPException e) {
			System.out.println("Error ldap: " + e.toString());
			// Exception e = new Exception("The operation is unknown.");
		}
		return alist;
	}

	public ArrayList getAllAgentAffecte(java.sql.Connection conn,
			UserInfo userinfo, HttpServletRequest request, int code) {
		System.out.println("getallAgent");
		ArrayList alist = new ArrayList();
		String where_codeagtaff = "";

		if (code != 0)
			where_codeagtaff = " and af_code=" + code;

		String qry = "select * from  agent,affectation,division,service,bureau,pole LEFT JOIN grade ON (grade.gr_code=agent.ag_ldap_grade)  where "
				+ "   af_ag_code=ag_code and af_dv_code=dv_code and af_sv_code=sv_code and af_bu_code=bu_code and af_pl_code=pl_code"
				+ where_codeagtaff;
		System.out.println("qry:" + qry);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				System.out.println("qry:ok j en ai 1");

				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOnePole(rs, item);
				item = fgetOneGrade(rs, item);
				item = fgetOneDivision(rs, item);
				item = fgetOneService(rs, item);
				item = fgetOneBureau(rs, item);
				item = fgetOneAgent(rs, item);
				item = fgetOneAffectation(rs, item);
				alist.add(item);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	public ArrayList getFindAgent(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request, int codeagt, int codebur, int codeser,
			int codediv, int codepol) {
		ArrayList alist = new ArrayList();

		String where_codeagt = "";
		String where_codebur = "";
		String where_codeser = "";
		String where_codediv = "";
		String where_codepol = "";

		if (codeagt != 0)
			where_codeagt = " and ag_code=" + codeagt;
		if (codebur != 0)
			where_codebur = " and bu_code=" + codebur;
		if (codeser != 0)
			where_codeser = " and sv_code=" + codeser;
		if (codediv != 0)
			where_codediv = " and dv_code=" + codediv;
		if (codepol != 0)
			where_codepol = " and pl_code=" + codepol;

		String qry = "select * from  agent,affectation,division,bureau,pole LEFT JOIN service ON  af_sv_code=sv_code LEFT JOIN grade ON gr_code=ag_ldap_grade where "
				+ "   af_ag_code=ag_code and af_dv_code=dv_code  and af_bu_code=bu_code and af_pl_code=pl_code"
				+ where_codeagt
				+ where_codebur
				+ where_codeser
				+ where_codediv
				+ where_codepol
				+ " and (ag_ldap_nom REGEXP '"
				+ request.getParameter("recherche")
				+ "'  or  LOWER(af_mission)    REGEXP '"
				+ request.getParameter("recherche").toLowerCase() 
				+ "'  or LOWER(ag_ldap_uid) = '" + request.getParameter("recherche").toLowerCase()  + "') " +

				"  order by ag_ldap_nom";
		;
		System.out.println(" getFindAgent qry:" + qry);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {

				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOnePole(rs, item);
				item = fgetOneGrade(rs, item);
				item = fgetOneDivision(rs, item);
				item = fgetOneService(rs, item);
				item = fgetOneBureau(rs, item);
				item = fgetOneAgent(rs, item);
				item = fgetOneAffectation(rs, item);
				alist.add(item);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	 

	public ArrayList getAgentSansAffectation(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request, int codeagt, int codebur, int codeser,
			int codediv, int codepol) {
		ArrayList alist = new ArrayList();

		String where_codeagt = "";
		String where_codebur = "";
		String where_codeser = "";
		String where_codediv = "";
		String where_codepol = "";

		if (codeagt != 0)
			where_codeagt = " and ag_code=" + codeagt;
		if (codebur != 0)
			where_codebur = " and bu_code=" + codebur;
		if (codeser != 0)
			where_codeser = " and sv_code=" + codeser;
		if (codediv != 0)
			where_codediv = " and dv_code=" + codediv;
		if (codepol != 0)
			where_codepol = " and pl_code=" + codepol;

		String qry = "SELECT * FROM agent   LEFT JOIN affectation ON ag_code=af_ag_code WHERE af_ag_code IS null order by ag_code"		;
		System.out.println(" getAgentSansAffectation qry:" + qry);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {

				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOneAgent(rs, item);
				item = fgetOneAffectation(rs, item);
				alist.add(item);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		}
		return alist;
	}

	
	
	public ItemChef fgetOneChef(ResultSet rs, ItemChef item1) {
		ItemChef item = item1;
		try {
			item.setCh_code(rs.getInt("code"));
			item.setCh_code(rs.getInt("code"));
			item.setCh_action(rs.getInt("action"));
			item.setCh_libelle(rs.getString("element"));
		} catch (Exception e) {
		}
		return item;
	}

	



	public ItemAnnuaire fgetOnePartage(ResultSet rs, ItemAnnuaire item1) {
		ItemAnnuaire item = item1;
		try {
			item.setPa_code(rs.getInt("pa_code"));
			item.setPa_dv_code(rs.getInt("pa_dv_code"));
			item.setPa_ag_code_proprio(rs.getInt("pa_ag_code_proprio"));
			item.setPa_nom(rs.getString("pa_nom"));
			item.setPa_nomc(rs.getString("pa_nomc"));
			item.setPa_description(rs.getString("pa_description"));
			item.setPa_date_creation(rs.getString("pa_date_creation"));
		} catch (Exception e) {
			System.out.println("Erreur: Catalog " + e.getMessage());
		}
		return item;
	}

	public ItemAnnuaire fgetOnePole(ResultSet rs, ItemAnnuaire item1) {
		ItemAnnuaire item = item1;
		try {
			item.setPl_code(rs.getInt("pl_code"));
			item.setPl_nom(rs.getString("pl_nom"));
			item.setPl_nomc(rs.getString("pl_nomc"));
			item.setPl_mission(rs.getString("pl_mission"));
		} catch (Exception e) {
			System.out.println("Erreur: Catalog " + e.getMessage());
		}
		return item;
	}

	public ItemAnnuaire fgetOneDivision(ResultSet rs, ItemAnnuaire item1) {
		ItemAnnuaire item = item1;
		try {
			item.setDv_code(rs.getInt("dv_code"));
			item.setDv_tri(rs.getInt("dv_tri"));
			item.setDv_nom(rs.getString("dv_nom"));
			item.setDv_nomc(rs.getString("dv_nomc"));
			item.setDv_mission(rs.getString("dv_mission"));
			item.setDv_telephone(rs.getString("dv_telephone"));
			item.setDv_fax(rs.getString("dv_fax"));
			item.setDv_adresse(rs.getString("dv_adresse"));
			item.setDv_mail(rs.getString("dv_mail"));

		} catch (SQLException e) {
			System.out.println("Erreur: Catalog " + e.getMessage());
		}
		return item;
	}

	public ItemAnnuaire fgetOneService(ResultSet rs, ItemAnnuaire item1) {
		ItemAnnuaire item = item1;
		try {
			if (rs.getInt("sv_code") != 0 ) {
			item.setSv_code(rs.getInt("sv_code"));
			item.setSv_tri(rs.getInt("sv_tri"));
			item.setSv_nom(rs.getString("sv_nom"));
			item.setSv_nomc(rs.getString("sv_nomc"));
			item.setSv_mission(rs.getString("sv_mission"));
			item.setSv_telephone(rs.getString("sv_telephone"));
			item.setSv_fax(rs.getString("sv_fax"));
			item.setSv_adresse(rs.getString("sv_adresse"));
			item.setSv_mail(rs.getString("sv_mail"));
			}
			// System.out.println("service:"+item.getSv_nomc());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	public ItemAnnuaire fgetOneBureau(ResultSet rs, ItemAnnuaire item1) {
		ItemAnnuaire item = item1;
		try {
			item.setBu_code(rs.getInt("bu_code"));
			item.setBu_tri(rs.getInt("bu_tri"));
			item.setBu_nom(rs.getString("bu_nom"));
			item.setBu_nomc(rs.getString("bu_nomc"));
			item.setBu_mission(rs.getString("bu_mission"));
			item.setBu_telephone(rs.getString("bu_telephone"));
			item.setBu_fax(rs.getString("bu_fax"));
			item.setBu_adresse(rs.getString("bu_adresse"));
			item.setBu_mail(rs.getString("bu_mail"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	public ItemAnnuaire fgetOneAgent(ResultSet rs, ItemAnnuaire item1) {
		ItemAnnuaire item = item1;
		try {
			// System.out.println("trouve:"+rs.getString("ag_ldap_grade"));
			item.setAg_code(rs.getInt("ag_code"));
			item.setAg_ldap_uid(rs.getString("ag_ldap_uid"));
			item.setAg_ldap_nom(rs.getString("ag_ldap_nom"));
			item.setAg_ldap_prenom(rs.getString("ag_ldap_prenom"));
			item.setAg_ldap_title(rs.getString("ag_ldap_title"));
			item.setAg_ldap_grade(rs.getString("ag_ldap_grade"));
			item.setAg_gsm(rs.getString("ag_gsm"));
			item.setAg_ldap_mail(rs.getString("ag_ldap_mail"));
			item.setAg_ldap_numen(rs.getString("ag_ldap_numen"));
			item.setAg_ldap_datenaiss(rs.getString("ag_ldap_datenaiss"));
			item.setAg_photo(rs.getString("ag_photo"));
			item.setAg_ldap_synchro(rs.getInt("ag_ldap_synchro"));
			item.setAg_ldap_profil_ddt(rs.getInt("ag_ldap_profil_ddt"));
			item.setAg_ldap_profil_annu(rs.getInt("ag_ldap_profil_annu"));
			item.setAg_ldap_dn(rs.getString("ag_ldap_dn"));
			item.setAg_gemalto_serie(rs.getString("ag_gemalto_serie"));
			item.setAg_gemalto_charte_signee(rs
					.getString("ag_gemalto_charte_signee"));
			item.setAg_gemalto_commentaire(rs
					.getString("ag_gemalto_commentaire"));
			item.setAg_gemalto_num(rs.getInt("ag_gemalto_num"));
			item.setAg_gemalto_date_restitution(rs.getString("ag_gemalto_date_restitution"));
			item.setAg_gemalto_date_expiration(rs.getString("ag_gemalto_date_expiration"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	public ItemAnnuaire fgetOneGrade(ResultSet rs, ItemAnnuaire item1) {
		ItemAnnuaire item = item1;
		try {
			// System.out.println("affect");

			item.setGr_code(rs.getInt("gr_code"));
			item.setGr_libc(rs.getString("gr_libc"));
			item.setGr_libl(rs.getString("gr_libl"));
			item.setGr_cat(rs.getString("gr_cat"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	public ItemMission fgetOneMission(ResultSet rs, ItemMission item1) {
		ItemMission item = item1;
		try {
			// System.out.println("affect");

			item.setNb(rs.getInt("nb"));
			item.setDv_nom(rs.getString("dv_nom"));
			item.setDv_nomc(rs.getString("dv_nomc"));
			item.setDv_mission(rs.getString("dv_mission"));
			item.setSv_nom(rs.getString("sv_nom"));
			item.setSv_nomc(rs.getString("sv_nomc"));
			item.setSv_mission(rs.getString("sv_mission"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	public ItemAnnuaire fgetOneAffectation(ResultSet rs, ItemAnnuaire item1) {
		ItemAnnuaire item = item1;
		try {
			item.setAf_code(rs.getInt("af_code"));
			item.setAf_ag_code(rs.getInt("af_ag_code"));
			item.setAf_bu_code(rs.getInt("af_bu_code"));
			item.setAf_sv_code(rs.getInt("af_sv_code"));
			item.setAf_dv_code(rs.getInt("af_dv_code"));
			item.setAf_pl_code(rs.getInt("af_pl_code"));
			item.setAf_type(rs.getInt("af_type"));
			item.setAf_libelle_type(rs.getString("af_libelle_type"));
			item.setAf_mission(rs.getString("af_mission"));
			item.setAf_telephone(rs.getString("af_telephone"));
			item.setAf_telephone_dir(rs.getString("af_telephone_dir"));
			item.setAf_bureau(rs.getString("af_bureau"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	public ItemAnnuaire fgetOneAffectationPartage(ResultSet rs,
			ItemAnnuaire item1) {
		ItemAnnuaire item = item1;
		try {
			item.setAp_code(rs.getInt("ap_code"));
			item.setAp_ag_code(rs.getInt("ap_ag_code"));
			item.setAp_pa_code(rs.getInt("ap_pa_code"));
			item.setAp_type(rs.getInt("ap_type"));
			item.setAp_droits(rs.getString("ap_droits"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	public void AddPole(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request) {
		String champ = " (pl_nom,pl_nomc) ";
		String valeur = "('" + request.getParameter("nom") + "','"
				+ request.getParameter("nomc") + "') ";
		String qry = "";

		try {
			System.out.println(request.getParameter("table") + " "
					+ request.getParameter("submit"));
			Statement st = conn.createStatement();
			if (request.getParameter("bsubmit").equals("Ajouter"))
				qry = "insert into " + request.getParameter("table")
						+ " (pl_nom,pl_nomc,pl_mission)" + " values " + "('"
						+ request.getParameter("nom") + "','"
						+ request.getParameter("nomc") + "','"
						+ request.getParameter("mission") + "') ";
			if (request.getParameter("bsubmit").equals("Modifier"))
				qry = "update " + request.getParameter("table") + " set "
						+ " pl_nom  = '" + request.getParameter("nom") + "' ,"
						+ " pl_nomc = '" + request.getParameter("nomc") + "' ,"
						+ " pl_mission = '" + request.getParameter("mission")
						+ "'" + "  where pl_code="
						+ request.getParameter("code");
			if (request.getParameter("bsubmit").equals("Supprimer"))
				qry = "delete from " + request.getParameter("table")
						+ "  where pl_code=" + request.getParameter("code");
			System.out.println("qry:" + qry);
			st.executeUpdate(qry);
			st.close();
		} catch (SQLException e) {
			userinfo.setError_message("Erreur: Catalog " + e.getMessage());
		} finally {
		}
	}

	public void ModAgentCharte(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request) {
		String qry = "";
		ListeParam(request);
		try {
			System.out.println(request.getParameter("table") + " : "
					+ request.getParameter("bsubmit"));
			Statement st = conn.createStatement();
			if (request.getParameter("bsubmit").equals("Ajouter"))
				qry = "insert into agent (ag_gemalto_commentaire,ag_gemalto_num,ag_gemalto_serie) "
						+ " values "
						+ "('"
						+ request.getParameter("commentaire")
						+ "','"
						+ request.getParameter("num")
						+ "','"
						+ request.getParameter("serie") + "') ";

			if (request.getParameter("bsubmit").equals("Modification"))
				qry = "update agent  set " + " ag_gemalto_commentaire  = '"
						+ request.getParameter("commentaire") + "',"
						+ " ag_gemalto_charte_signee  = '"
						+ request.getParameter("date") + "',"
						+ " ag_gemalto_date_restitution  = '"
						+ request.getParameter("daterestitution") + "',"
						+ " ag_gemalto_date_expiration  = '"
						+ request.getParameter("dateexpiration") + "',"
						+ " ag_gemalto_num   = '" + request.getParameter("num")
						+ "'," + " ag_gemalto_serie  = '"
						+ request.getParameter("serie") + "'"
						+ "  where ag_code=" + request.getParameter("code");
			
			System.out.println("qry:" + qry);
			st.executeUpdate(qry);
			st.close();
		} catch (SQLException e) {
			userinfo.setError_message("Erreur: Catalog " + e.getMessage());
		} finally {
		}
	}

	public void CreChorus(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request) {
		String qry = "";
		ListeParam(request);
		try {
			Statement st = conn.createStatement();
			qry = "update agent  set "
					+ " ag_gemalto_num  = '99', ag_gemalto_commentaire='------- numéro de clé à modifier ----------'  where ag_code="
					+ request.getParameter("code");
			System.out.println("qry:" + qry);
			st.executeUpdate(qry);
			st.close();
		} catch (SQLException e) {
			userinfo.setError_message("Erreur: Catalog " + e.getMessage());
		} finally {
		}
	}

	public void ModContact(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request) {
		String qry = "";
		ListeParam(request);
		try {
			System.out.println(request.getParameter("table") + " : "
					+ request.getParameter("bsubmit"));
			Statement st = conn.createStatement();
			String portee = "0000";
			if (request.getParameter("cportee").equals("privee"))
				portee = userinfo.getPoste();
			if (request.getParameter("bsubmit").equals("Modification"))
				qry = "update contact  set " + " ct_nom  = '"
						+ request.getParameter("nom") + "',"
						+ " ct_prenom  = '" + request.getParameter("prenom")
						+ "'," + " ct_portee  = '" + portee + "',"
						+ " ct_creat   = '" + userinfo.getPoste() + "',"
						+ " ct_adresse  = '" + request.getParameter("adresse")
						+ "'" + "  where ct_numero="
						+ request.getParameter("numero");
			if (request.getParameter("bsubmit").equals("Ajouter"))
				qry = "insert into contact "
						+ " (ct_numero,ct_nom,ct_prenom,ct_adresse,ct_creat,ct_portee)"
						+ " values " + "('" + request.getParameter("numero")
						+ "','" + request.getParameter("nom") + "','"
						+ request.getParameter("prenom") + "','"
						+ request.getParameter("adresse") + "','"
						+ userinfo.getPoste() + "','" + portee + "')";
			st.executeUpdate(qry);
			System.out.println("qry contact: " + qry);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			userinfo.setError_message("Erreur: Catalog " + e.getMessage());
		} finally {
		}
	}

	public void AddChef(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request) {
		String champ = " (pl_nom,pl_nomc) ";
		String valeur = "('" + request.getParameter("nom") + "','"
				+ request.getParameter("nomc") + "') ";
		String qry = "";

		try {
			System.out.println(request.getParameter("table") + " "
					+ request.getParameter("submit"));
			Statement st = conn.createStatement();
			if (request.getParameter("submit").equals("Ajouter"))
				qry = "insert into parametre " + " (element,type,action)"
						+ " values " + "('" + request.getParameter("element")
						+ "',60," + request.getParameter("action") + ")";

			if (request.getParameter("submit").equals("Modifier"))
				qry = "update parametre " + " set " + " element  = '"
						+ request.getParameter("element") + "' , "
						+ " action   = " + request.getParameter("action")
						+ "  where  code=" + request.getParameter("code");
			if (request.getParameter("submit").equals("Supprimer"))
				qry = "delete from parametre " + "  where  code="
						+ request.getParameter("code");
			System.out.println("qry:" + qry);
			st.executeUpdate(qry);
			st.close();
		} catch (SQLException e) {
			userinfo.setError_message("Erreur: Catalog " + e.getMessage());
		} finally {
		}
	}

	public int AddDivision(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request) {
		userinfo.setError_message("");

		int maxi = this.getMaxDivision(conn);
		maxi++;
		String champ = " (dv_nom,dv_nomc) ";
		String valeur = "('" + request.getParameter("nom") + "','"
				+ request.getParameter("nomc") + "') ";
		String qry = "";
		try {
			System.out.println(request.getParameter("table") + " "
					+ request.getParameter("submit"));
			Statement st = conn.createStatement();
			if (request.getParameter("bsubmit").equals("Ajouter"))
				qry = "insert into "
						+ request.getParameter("table")
						+ " (dv_code,dv_nom,dv_nomc,dv_pl_code,dv_telephone,dv_fax,dv_adresse,dv_mail,dv_mission)"
						+ " values " + "(" + maxi + ",'"
						+ request.getParameter("nom").replace('\'', '`')
						+ "','"
						+ request.getParameter("nomc").replace('\'', '`')
						+ "'," + request.getParameter("pldv_code") + ",'"
						+ request.getParameter("telephone") + "','"
						+ request.getParameter("fax") + "','"
						+ request.getParameter("adresse").replace('\'', '`')
						+ "','" + request.getParameter("mail") + "','"
						+ request.getParameter("mission").replace('\'', '`')
						+ "') ";
			if (request.getParameter("bsubmit").equals("Modifier"))
				qry = "update " + request.getParameter("table") + " set "
						+ " dv_nom  = '"
						+ request.getParameter("nom").replace('\'', '`')
						+ "' ," + " dv_nomc = '"
						+ request.getParameter("nomc").replace('\'', '`')
						+ "' ," + " dv_mission = '"
						+ request.getParameter("mission").replace('\'', '`')
						+ "' ," + " dv_telephone = '"
						+ request.getParameter("telephone") + "' ,"
						+ " dv_fax = '" + request.getParameter("fax") + "' ,"
						+ " dv_adresse = '"
						+ request.getParameter("adresse").replace('\'', '`')
						+ "' ," + " dv_mail = '" + request.getParameter("mail")
						+ "' ," + " dv_tri =   " + request.getParameter("tri")
						+ " ," + " dv_pl_code = "
						+ request.getParameter("pldv_code")
						+ "  where dv_code=" + request.getParameter("codedv");
			if (request.getParameter("bsubmit").equals("Supprimer"))
				qry = "delete from " + request.getParameter("table")
						+ "  where dv_code=" + request.getParameter("codedv");
			System.out.println("qry:" + qry);
			st.executeUpdate(qry);
			st.close();
		} catch (SQLException e) {
			userinfo.setError_message("Erreur: Catalog " + e.getMessage()
					+ "<br><b>" + qry + "</b>");
		} finally {
		}
		return maxi;
	}

	public int AddService(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request) {
		int maxi = this.getMaxService(conn);
		maxi++;
		String champ = " (dv_nom,dv_nomc) ";
		String valeur = "('" + request.getParameter("nom") + "','"
				+ request.getParameter("nomc") + "') ";
		String qry = "";
		try {
			System.out.println(request.getParameter("table") + " "
					+ request.getParameter("submit"));
			Statement st = conn.createStatement();
			if (request.getParameter("bsubmit").equals("Ajouter"))
				qry = "insert into "
						+ request.getParameter("table")
						+ " (sv_code,sv_nom,sv_nomc,sv_mission,sv_telephone,sv_fax,sv_adresse,sv_mail,sv_dv_code)"
						+ " values " + "(" + maxi + ",'"
						+ request.getParameter("nom").replace('\'', '`')
						+ "','"
						+ request.getParameter("nomc").replace('\'', '`')
						+ "','"
						+ request.getParameter("mission").replace('\'', '`')
						+ "','" + request.getParameter("telephone") + "','"
						+ request.getParameter("fax") + "','"
						+ request.getParameter("adresse").replace('\'', '`')
						+ "','" + request.getParameter("mail") + "', "
						+ request.getParameter("pldv_code") + ") ";
			if (request.getParameter("bsubmit").equals("Modifier"))
				qry = "update " + request.getParameter("table") + " set "
						+ " sv_nom  = '"
						+ request.getParameter("nom").replace('\'', '`')
						+ "' ," + " sv_nomc = '"
						+ request.getParameter("nomc").replace('\'', '`')
						+ "' ," + " sv_mission = '"
						+ request.getParameter("mission").replace('\'', '`')
						+ "' ," + " sv_telephone = '"
						+ request.getParameter("telephone") + "' ,"
						+ " sv_fax = '" + request.getParameter("fax") + "' ,"
						+ " sv_adresse = '"
						+ request.getParameter("adresse").replace('\'', '`')
						+ "' ," + " sv_mail = '" + request.getParameter("mail")
						+ "' ," + " sv_dv_code = "
						+ request.getParameter("pldv_code") + " , "
						+ " sv_tri = " + request.getParameter("tri")
						+ "  where sv_code=" + request.getParameter("codesv");
			if (request.getParameter("bsubmit").equals("Supprimer"))
				qry = "delete from " + request.getParameter("table")
						+ "  where sv_code=" + request.getParameter("codesv");
			System.out.println("qry:" + qry);
			st.executeUpdate(qry);
			st.close();
		} catch (SQLException e) {
			userinfo.setError_message("Erreur: Catalog " + e.getErrorCode()
					+ "<br>" + qry);
		} finally {
		}
		return maxi;
	}

	public int AddBureau(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request) {
		int maxi = this.getMaxBureau(conn);

		maxi++;
		System.out.println("AddBureau :" + maxi + " dans service :"
				+ request.getParameter("pldv_code"));
		String champ = " (dv_nom,dv_nomc) ";
		String valeur = "('" + request.getParameter("nom") + "','"
				+ request.getParameter("nomc") + "') ";
		String qry = "";
		try {
			System.out.println(request.getParameter("table") + " "
					+ request.getParameter("bsubmit"));
			Statement st = conn.createStatement();
			if (request.getParameter("bsubmit").equals("Ajouter"))
				qry = "insert into "
						+ request.getParameter("table")
						+ " (bu_code,bu_nom,bu_nomc,bu_mission,bu_telephone,bu_fax,bu_adresse,bu_mail,bu_sv_code)"
						+ " values " + "(" + maxi + ",'"
						+ request.getParameter("nom") + "','"
						+ request.getParameter("nomc").replace('\'', '`')
						+ "','"
						+ request.getParameter("mission").replace('\'', '`')
						+ "','" + request.getParameter("telephone") + "','"
						+ request.getParameter("fax") + "','"
						+ request.getParameter("adresse").replace('\'', '`')
						+ "','" + request.getParameter("mail") + "', "
						+ request.getParameter("pldv_code") + ") ";
			if (request.getParameter("bsubmit").equals("Modifier"))
				qry = "update " + request.getParameter("table") + " set "
						+ " bu_nom  = '" + request.getParameter("nom") + "' ,"
						+ " bu_nomc = '" + request.getParameter("nomc") + "' ,"
						+ " bu_mission = '" + request.getParameter("mission")
						+ "' ," + " bu_telephone = '"
						+ request.getParameter("telephone") + "' ,"
						+ " bu_fax = '" + request.getParameter("fax") + "' ,"
						+ " bu_adresse = '" + request.getParameter("adresse")
						+ "' ," + " bu_mail = '" + request.getParameter("mail")
						+ "' ," + " bu_sv_code = "
						+ request.getParameter("pldv_code") + "  , "
						+ " bu_tri = " + request.getParameter("tri")
						+ "  where bu_code=" + request.getParameter("codebu");
			if (request.getParameter("bsubmit").equals("Supprimer"))
				qry = "delete from " + request.getParameter("table")
						+ "  where bu_code=" + request.getParameter("codebu");
			System.out.println("qry:" + qry);
			st.executeUpdate(qry);
			st.close();
		} catch (SQLException e) {
			userinfo.setError_message("Erreur: Catalog " + e.getErrorCode()
					+ "<br>" + qry);
		} finally {
		}
		return maxi;
	}
 
	public int AddPartage(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request, String ldappwS, String samba_sid)
			throws IOException {
		System.out.println("---- catalog addpartageng ----"+userinfo.getUser());
		String user=userinfo.getMail();
		int codepa = 0;
		if (request.getParameter("codepa") != null)
			codepa = Integer.parseInt(request.getParameter("codepa"));
		int maxi = this.getMaxPartage(conn);
		Calendar c = Calendar.getInstance();
		String vdate = c.get(Calendar.DAY_OF_MONTH) + " / "
				+ (c.get(Calendar.MONTH) + 1) + " / " + c.get(Calendar.YEAR);
		maxi++;
		String partage = "";
		String nom = "";
		if (request.getParameter("partage") != null)
			partage = request.getParameter("partage").toLowerCase();
		if (request.getParameter("nom") != null)
			nom = request.getParameter("nom").toLowerCase();
		String qry = "";
		try {
			System.out.println(request.getParameter("table") + ": "
					+ request.getParameter("bsubmit"));
			Statement st = conn.createStatement();
			if (request.getParameter("bsubmit").equals("Ajouter")) {
				{
					qry = "insert into partage "
							+ " (pa_code,pa_dv_code,pa_ag_code_proprio,pa_nom,pa_nomc,pa_description,pa_date_creation)"
							+ " values " + "(" + maxi + ","
							+ request.getParameter("pldv_code") + ","
							+ userinfo.getAg_code() + ",'"
							+ nom.replace('\'', '`') + "','"
							+ request.getParameter("nomc").replace('\'', '`')
							+ "','" + request.getParameter("description")
							+ "','" + vdate + "') ";
					codepa = maxi;
					// ------------------------------------------- add groupe
					// AddGroupePartageScribe(userinfo,ldappwS,request,request.getParameter("nom"),samba_sid);
					// AddPartageScribe(userinfo,ldappwS,request,request.getParameter("nom"));
					// ------------------------------------------- add
					// repertoire

					// String uid=request.getParameter("uid");
					String ssh = "ssh root@scribe.in.ac-reunion.fr \"mkdir /home/workgroups/"
							+ partage
							+ "; chmod 770 /home/workgroups/"
							+ partage
							+ "; setfacl -b -R  /home/workgroups/"
							+ partage
							+ "; setfacl -m u::rwx,g::rwx,o::---,m::rwx /home/workgroups/"
							+ partage
							+ "; setfacl -dm u::rwx,g::rwx,o::---,m::rwx /home/workgroups/"
							+ partage + "\";";
					String ssh2 = "ssh root@scribe.in.ac-reunion.fr \"setfacl -R -m g:"
							+ partage
							+ ":rwx /home/workgroups/"
							+ partage
							+ "; setfacl -R -dm g:"
							+ partage
							+ ":rwx /home/workgroups/" + partage + "\";";
					String ssh3 = "ssh root@scribe.in.ac-reunion.fr \" /usr/share/eole/backend/creation-groupe.py -g "
							+ nom + "  -t Groupe -p  \";";
					// ------------------------------------------------------------------------------------------------------------------------------------
					// System.out.println("ssh2:"+ssh2);
					System.out.println("ssh3:" + ssh3);
					Runtime runtime = Runtime.getRuntime();
					Process process = runtime.exec(new String[] { "/bin/sh",
							"-c", ssh3 });
					// Process process2 = runtime.exec(new String[]
					// {"/bin/sh","-c", ssh2 });
					DataInputStream data_in = new DataInputStream(
							process.getInputStream());
					// DataInputStream data_in2 = new
					// DataInputStream(process2.getInputStream());
					String line_in = null;
					// while ((line_in = data_in.readLine()) != null) {
					// System.out.println("ssh:"+line_in); }
					System.out.println("qry2:" + qry);
					st.executeUpdate(qry);
				}
			}
			if (request.getParameter("bsubmit").equals("Modifier")) {
				qry = "update partage  set " + 
			          " pa_nom  = '" + request.getParameter("nom") + "' ," + 
						" pa_nomc = '"  + request.getParameter("nomc") + "'  ," + 
						" pa_description = '"  + request.getParameter("description") + "'  " + 
						"  where pa_code=" + request.getParameter("codepa");
				System.out.println("qry2:" + qry);
				st.executeUpdate(qry);
			}
			if (request.getParameter("bsubmit").equals("AjouterUsager")) {
				int p = 0;
				int ag_code = 0;
				int dv_code = 0;
				int sv_code = 0;
				String uid = "_";
				nom = "_";
				String prenom = "_";
				String mail = "_";
				String dtn = "";
				StringTokenizer tokenizer = new StringTokenizer(
						request.getParameter("usager_code"), ";");
				while (tokenizer.hasMoreTokens()) {
					p++;
					String token = tokenizer.nextToken();
					System.out.println("" + p + ":" + token);
					if (p == 1)
						dv_code = Integer.parseInt(token);
					if (p == 2)
						sv_code = Integer.parseInt(token);
					if (p == 3)
						ag_code = Integer.parseInt(token);
					if (p == 4)
						uid = token;
					if (p == 5)
						dtn = token.substring(6, 8) + "/"
								+ token.substring(4, 6) + "/"
								+ token.substring(0, 4);
					if (p == 6)
						mail = token;
					if (p == 7)
						nom = token;
					if (p == 8)
						prenom = token;
				}
				qry = "insert into  af_partage (ap_pa_code,ap_ag_code,ap_sv_code,ap_dv_code,ap_type,ap_droits ) values ( "
						+ request.getParameter("codepa")
						+ ","
						+ ag_code
						+ ","
						+ sv_code
						+ ","
						+ dv_code
						+ ","
						+ "1,'r-x'"
						+ ")  ";
				 
				System.out.println("qry:" + qry);
				st.executeUpdate(qry);
				// ------------------------------------------- add lien .ftp
				// partage=request.getParameter("partage").toLowerCase();
				String ssh1 = "ssh root@scribe.in.ac-reunion.fr \" /usr/share/eole/x_ajout_usager_partage  "
						+ user
						+ " " 
						+ uid
						+ " "
						+ partage + "  \"   ;";
				System.out
						.println("------------------ssh x_ajout_usager_partage : "
								+ ssh1);
				Runtime runtime = Runtime.getRuntime();
				Process process = runtime.exec(new String[] { "/bin/sh", "-c",
						ssh1 });
				DataInputStream data_in = new DataInputStream(
						process.getInputStream());
				String line_in = null;
				System.out.println("------------------fin x_add_user_admin:"
						+ ssh1);
				// while ((line_in = data_in.readLine()) != null) {
				// System.out.println("ssh:"+line_in); }
				
				
			}
			if (request.getParameter("bsubmit").equals("AjouterGroupe")) {
				int p = 0;
				int ag_code = 0;
				int dv_code = 0;
				int sv_code = 0;
				String dvnom = "" ;
				String svnom = "" ;	
				String uid = "_";
				nom = "_";
				String prenom = "_";
				String mail = "_";
				String dtn = "";
				String droit=request.getParameter("droit");
				StringTokenizer tokenizer = new StringTokenizer(
						request.getParameter("groupe_code"), ";");
				while (tokenizer.hasMoreTokens()) {
					p++;
					String token = tokenizer.nextToken();
					System.out.println("" + p + ":" + token);
					if (p == 1)
						dv_code = Integer.parseInt(token);
					if (p == 2)
						sv_code = Integer.parseInt(token);
					if (p == 3)
						dvnom = token ;
					if (p == 4)
						svnom = token ;

				}
				qry = "insert into  af_partage (ap_pa_code,ap_ag_code,ap_sv_code,ap_dv_code,ap_type,ap_droits ) values ( "
						+ request.getParameter("codepa")
						+ ","
						+ "0"
						+ ","
						+ sv_code
						+ ","
						+ dv_code
						+ ","
						+ "2"
						+ ",'"
						+ droit
						+ "'"
						+ ")  ";
				// ------------------------------------------- add lien .ftp 
				// partage=request.getParameter("partage").toLowerCase();
			
				System.out.println("qry:" + qry);
				st.executeUpdate(qry);
				// ------------------------------------------- add lien .ftp
				// partage=request.getParameter("partage").toLowerCase();
				String ssh1 = "ssh root@scribe.in.ac-reunion.fr \" /usr/share/eole/x_ajout_groupe_partage  "
						+ user
						+ " " 
						+ dvnom + "-" + svnom
						+ " " 
						+ droit
						+ " " + partage + "  \"   ;";
				System.out
						.println("------------------ssh x_ajout_groupe_partage : "
								+ ssh1);
				
				Runtime runtime3 = Runtime.getRuntime();
				Process process = runtime3.exec(new String[] { "/bin/sh", "-c",
						ssh1 });
				DataInputStream data_in = new DataInputStream(
						process.getInputStream());
				String line_in = null;
				System.out.println("------------------fin x_add_user_admin:"
						+ ssh1);
				 
	 
			}
			if (request.getParameter("bsubmit").equals("AjouterMembre")) {
				int p = 0;
				int ag_code = 0;
				int dv_code = 0;
				int sv_code = 0;
				String uid = "_";
				nom = "_";
				String prenom = "_";
				String mail = "_";
				String dtn = "";
				StringTokenizer tokenizer = new StringTokenizer(
						request.getParameter("membre_code"), ";");
				while (tokenizer.hasMoreTokens()) {
					p++;
					String token = tokenizer.nextToken();
					System.out.println("" + p + ":" + token);
					if (p == 1)
						dv_code = Integer.parseInt(token);
					if (p == 2)
						sv_code = Integer.parseInt(token);
					if (p == 3)
						ag_code = Integer.parseInt(token);
					if (p == 4)
						uid = token;
					if (p == 5)
						dtn = token.substring(6, 8) + "/"
								+ token.substring(4, 6) + "/"
								+ token.substring(0, 4);
					if (p == 6)
						mail = token;
					if (p == 7)
						nom = token;
					if (p == 8)
						prenom = token;
				}
				qry = "insert into  af_partage  (ap_pa_code,ap_ag_code,ap_sv_code,ap_dv_code,ap_type,ap_droits ) values ( "
						+ request.getParameter("codepa")
						+ ","
						+ ag_code
						+ ","
						+ sv_code
						+ ","
						+ dv_code
						+ ","
						+ "3,'rwx'"
						+ ")  ";
				// ------------------------------------------- add lien .ftp
				// partage=request.getParameter("partage").toLowerCase();
				String ssh1 = "ssh root@scribe.in.ac-reunion.fr \" /usr/share/eole/x_add_user_partage  "
						+ user
						+ " " 
						+ uid
						+ " "
						+ nom
						+ " "
						+ prenom
						+ " "
						+ mail
						+ " "
						+ dtn + " " + partage + "  \"   ;";
				System.out
						.println("------------------ssh x_add_user_partage : "
								+ ssh1);
				Runtime runtime = Runtime.getRuntime();
				Process process = runtime.exec(new String[] { "/bin/sh", "-c",
						ssh1 });
				DataInputStream data_in = new DataInputStream(
						process.getInputStream());
				String line_in = null;
				System.out.println("------------------fin x_add_user_admin:"
						+ ssh1);
				// while ((line_in = data_in.readLine()) != null) {
				// System.out.println("ssh:"+line_in); }

				// AddMemberScribe(userinfo,ldappwS,request,partage,uid);
				System.out.println("qry:" + qry);
				st.executeUpdate(qry);
			}
			if (request.getParameter("bsubmit").equals("Supprimer")) {
				qry = "delete from partage where pa_code="
						+ request.getParameter("codepa");
				System.out.println("qry:" + qry);
				st.executeUpdate(qry);
			}
			if (request.getParameter("bsubmit").equals("SupprimerAffectation")) {
				if ( Integer.parseInt(request.getParameter("codeag")) > 0)
				{
					String qry1 = "delete from af_partage  where ap_ag_code="
						+ request.getParameter("codeag") + " and  ap_pa_code=" + request.getParameter("codepa") +   " ; ";
					System.out.println("qry1:" + qry1);
					st.executeUpdate(qry1);
				}
				String qry2 = "delete from af_partage_ldap where af_uid_ldap='"
						+ request.getParameter("uid") + "' and af_pa_code="+ request.getParameter("codepa") + ";";
				
				
				System.out.println("qry2:" + qry2);
				
				st.executeUpdate(qry2);

				// ring partage=request.getParameter("partage").toLowerCase();
				String uid = request.getParameter("uid");
				// String
				// ssh="ssh root@scribe.in.ac-reunion.fr \"rm -rf    /home/" +
				// uid.substring(0,1) + "/" + uid + "/.ftp/"+ partage +
				// " \"   ;" ;
				String ssh = "ssh root@scribe.in.ac-reunion.fr \" /usr/share/eole/x_del_user_partage "
						+ user + " " + uid + " " + partage + " \"   ;";
				Runtime runtime = Runtime.getRuntime();
				Process process = runtime.exec(new String[] { "/bin/sh", "-c",
						ssh });
				DataInputStream data_in = new DataInputStream(
						process.getInputStream());
				String line_in = null;
				while ((line_in = data_in.readLine()) != null) {
					System.out.println("ssh:" + line_in);
				}
				DelMemberScribe(userinfo, ldappwS, request, partage, uid);
			}
			if (request.getParameter("bsubmit").equals("SupprimerAffectationGroupe")) {
				String qry1 = "delete from af_partage  where ap_dv_code="
						+ request.getParameter("codedv") + 
						" and  ap_pa_code=" + request.getParameter("codepa") +
						" and  ap_sv_code=" + request.getParameter("codesv") +
						" ; ";
				//String qry2 = "delete from af_partage_ldap where af_uid_ldap='"
				//		+ request.getParameter("uid") + "' and af_pa_code="+ request.getParameter("codepa") + ";";
				System.out.println("qry1:" + qry1);
				//System.out.println("qry2:" + qry2);
				st.executeUpdate(qry1);
				// ring partage=request.getParameter("partage").toLowerCase();
				String uid = request.getParameter("uid");
				// String
				// ssh="ssh root@scribe.in.ac-reunion.fr \"rm -rf    /home/" +
				// uid.substring(0,1) + "/" + uid + "/.ftp/"+ partage +
				// " \"   ;" ;
				 
				String ssh = "ssh root@scribe.in.ac-reunion.fr \" /usr/share/eole/x_supprime_groupe_partage "
						+ user
						+ " " 
						+ request.getParameter("nomdv")
						  + "-" 
						  + request.getParameter("nomsv") 
						  + " " 
						  + partage + " \"   ;";
				System.out.println("ssh: "+ ssh);
				Runtime runtime = Runtime.getRuntime();
				Process process = runtime.exec(new String[] { "/bin/sh", "-c",
						ssh });
				DataInputStream data_in = new DataInputStream(
						process.getInputStream());
				//String line_in = null;
				//while ((line_in = data_in.readLine()) != null) {
				//	System.out.println("ssh:" + line_in);
				//}
			}
			if (request.getParameter("bsubmit").equals("SupprimerAffectationUsager")) {
				String qry1 = "delete from af_partage  where ap_ag_code="
						+ request.getParameter("codeag") + " and  ap_pa_code=" + request.getParameter("codepa") +   " ; ";
				String qry2 = "delete from af_partage_ldap where af_uid_ldap='"
						+ request.getParameter("uid") + "' and af_pa_code="+ request.getParameter("codepa") + ";";
				System.out.println("qry1:" + qry1);
				System.out.println("qry2:" + qry2);
				st.executeUpdate(qry1);
				st.executeUpdate(qry2);
				// ring partage=request.getParameter("partage").toLowerCase();
				String uid = request.getParameter("uid");
				// String
				// ssh="ssh root@scribe.in.ac-reunion.fr \"rm -rf    /home/" +
				// uid.substring(0,1) + "/" + uid + "/.ftp/"+ partage +
				// " \"   ;" ;
				String ssh = "ssh root@scribe.in.ac-reunion.fr \" /usr/share/eole/x_supprime_usager_partage "
						+ user
						+ " " 
						+ request.getParameter("uid")
						  + " " 
						  + partage + " \"   ;";
				Runtime runtime = Runtime.getRuntime();
				Process process = runtime.exec(new String[] { "/bin/sh", "-c",
						ssh });
				DataInputStream data_in = new DataInputStream(
						process.getInputStream());
				String line_in = null;
				while ((line_in = data_in.readLine()) != null) {
					System.out.println("ssh:" + line_in);
				}
			}
			st.close();
		} catch (SQLException e) {
			userinfo.setError_message("Erreur: Catalog " + e.getErrorCode()
					+ "<br>" + qry);
		} finally {
		}
		return codepa;
	}


	
	public int AddAgent(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request) {
		System.out.println("catalog.AddAgent par  " + userinfo.getUser()
				+ " p:" + userinfo.getProfil());
		System.out
				.println("--------------------catalog: AddAgent----------------------------------------");
		int maxi = this.getMaxAgent(conn);
		int dvcode = 0;
		maxi++;
		int maxiaff = this.getMaxAffectation(conn);
		maxiaff++;
		String qry = "";
		String qry_profil = "";
		if (userinfo.getProfil() == 4)
			qry_profil = ", ag_ldap_profil_annu = '"
					+ request.getParameter("ldap_profil_annu") + "' ,"
					+ " ag_ldap_profil_ddt  = "
					+ request.getParameter("ldap_profil_ddt");

		try {
			System.out.println(request.getParameter("table") + " "
					+ request.getParameter("bsubmit"));
			Statement st = conn.createStatement();
			if (request.getParameter("bsubmit").equals("Ajouter")) {
				qry = "insert into "
						+ request.getParameter("table")
						+ " (ag_code,ag_ldap_nom,ag_ldap_prenom,ag_ldap_mail,ag_gsm)"
						+ " values " + "(" + maxi + ",'"
						+ request.getParameter("nom").toUpperCase() + "','"
						+ request.getParameter("prenom") + "','"
						+ request.getParameter("mail") + "','"
						+ request.getParameter("gsm") + "') ";
				System.out.println("qry:" + qry);
				st.executeUpdate(qry);
			}

			StringTokenizer tokenizer = new StringTokenizer(
					request.getParameter("libchef"), ";");
			int k = 0;
			int type_code = 0;
			String type_libelle = "";
			while (tokenizer.hasMoreTokens()) {
				k++;
				String token = tokenizer.nextToken();
				if (k == 1)
					type_code = Integer.parseInt(token);
				if (k == 2)
					type_libelle = token;
			}
			type_code = Integer.parseInt(request.getParameter("typechef"));
			// type_libelle=request.getParameter("libchef");
			
			if (request.getParameter("bsubmit").equals("Modifier")) {
				qry = "update " + request.getParameter("table") + " set "
						+ " ag_gsm         = '" + request.getParameter("gsm")
						+ "' ," + " ag_ldap_nom    = '"
						+ request.getParameter("nom").replace('\'', '`')
						+ "' ," + " ag_ldap_prenom = '"
						+ request.getParameter("prenom").replace('\'', '`')
						+ "' ," + " ag_ldap_mail   = '"
						+ request.getParameter("mail") + "' ,"
						+ " ag_ldap_grade  = '" + request.getParameter("grade")
						+ "' " + qry_profil + " where ag_code="
						+ request.getParameter("code");
				System.out.println("qry update:" + qry);
				st.executeUpdate(qry);

				qry = "update affectation   set " + " af_mission    = '"
						+ request.getParameter("mission").replace('\'', '`')
						+ "' ," + " af_type       =  " + type_code + " ,"
						+ " af_libelle_type  = '" + type_libelle + "' , "
						+ " af_telephone  = '"
						+ request.getParameter("telephone") + "' ,"
						+ " af_telephone_dir  = '"
						+ request.getParameter("telephone_dir") + "' ,"
						+ " af_bureau     = '" + request.getParameter("bureau")
						+ "' " + " where af_code="
						+ request.getParameter("af_code");
				System.out.println("qry:" + qry);
				st.executeUpdate(qry);
			}
			
			if (request.getParameter("bsubmit").equals("Supprimer")) {
				qry = "delete from " + request.getParameter("table")
						+ "  where ag_code=" + request.getParameter("code");
				System.out.println("qry:" + qry);
				st.executeUpdate(qry);
				qry = "delete from affectation  where af_ag_code= "
						+ request.getParameter("code");
				System.out.println("qry:" + qry);
				st.executeUpdate(qry);
			}

			if (request.getParameter("bsubmit").equals("Deplacer")) {
				int p = 0;
				int p1 = 0;
				int p2 = 0;
				int p3 = 0;
				int p4 = 0;
				tokenizer = new StringTokenizer(
						request.getParameter("pldv_code"), ";");
				while (tokenizer.hasMoreTokens()) {
					p++;
					String token = tokenizer.nextToken();
					if (p == 1)
						p1 = Integer.parseInt(token);
					if (p == 2)
						p2 = Integer.parseInt(token);
					if (p == 3)
						p3 = Integer.parseInt(token);
					if (p == 4)
						p4 = Integer.parseInt(token);
				}
				qry = "update affectation   set " + " af_pl_code     = " + p1
						+ " ," + " af_dv_code     = " + p2 + " ,"
						+ " af_sv_code     = " + p3 + " ,"
						+ " af_bu_code     = " + p4 + " ,"
						+ " af_mission    = '"
						+ request.getParameter("mission").replace('\'', '`')
						+ "' ," + " af_type       =  " + type_code + " , "
						+ " af_libelle_type  = '" + type_libelle + "' , "
						+ " af_telephone  = '"
						+ request.getParameter("telephone") + "' , "
						+ " af_bureau     = '" + request.getParameter("bureau")
						+ "' " + " where af_code="
						+ request.getParameter("af_code");
				System.out.println("qry:" + qry);
				st.executeUpdate(qry);
				dvcode = p2;
			}
			if (request.getParameter("bsubmit").equals("Supprimer affectation")) {
				qry = "delete from  affectation  where af_code="
						+ request.getParameter("af_code");
				System.out.println("qry:" + qry);
				st.executeUpdate(qry);
			}
			if (request.getParameter("bsubmit").equals("Ajouter Affectation")) {
				int p = 0;
				int p1 = 0;
				int p2 = 0;
				int p3 = 0;
				int p4 = 0;
				tokenizer = new StringTokenizer(
						request.getParameter("pldv_code"), ";");
				while (tokenizer.hasMoreTokens()) {
					p++;
					String token = tokenizer.nextToken();
					if (p == 1)
						p1 = Integer.parseInt(token);
					if (p == 2)
						p2 = Integer.parseInt(token);
					if (p == 3)
						p3 = Integer.parseInt(token);
					if (p == 4)
						p4 = Integer.parseInt(token);
				}
				qry = "insert into affectation "
						+ " (af_code,af_ag_code,af_bu_code,af_sv_code,af_dv_code,af_pl_code,af_mission,af_telephone,af_telephone_dir,af_type,af_libelle_type,af_bureau)"
						+ " values " + "("
						+ maxiaff
						+ ","
						+ request.getParameter("codeag")
						+ " ,  "
						+ p4
						+ " , "
						+ p3
						+ " , "
						+ p2
						+ " , "
						+ p1
						+ " , '"
						+ request.getParameter("mission")
						+ "' , '"
						+ request.getParameter("telephone")
						+ "' , '"
						+ request.getParameter("telephone_dir")
						+ "' , "
						+ type_code
						+ " , '"
						+ type_libelle
						+ "' , '"
						+ request.getParameter("bureau") + "') ";
				System.out.println("qry-----:" + qry);
				st.executeUpdate(qry);
				dvcode = p2;
			}
			if (request.getParameter("bsubmit").equals("Ajouter")) {
				int p = 0;
				int p1 = 0;
				int p2 = 0;
				int p3 = 0;
				int p4 = 0;
				tokenizer = new StringTokenizer(
						request.getParameter("pldv_code"), ";");
				while (tokenizer.hasMoreTokens()) {
					p++;
					String token = tokenizer.nextToken();
					if (p == 1)
						p1 = Integer.parseInt(token);
					if (p == 2)
						p2 = Integer.parseInt(token);
					if (p == 3)
						p3 = Integer.parseInt(token);
					if (p == 4)
						p4 = Integer.parseInt(token);
				}
				qry = "insert into affectation "
						+ " (af_code,af_ag_code,af_bu_code,af_sv_code,af_dv_code,af_pl_code,af_mission,af_telephone,af_telephone_dir,af_type,af_libelle_type,af_bureau)"
						+ " values " + "("
						+ maxiaff
						+ ","
						+ maxi
						+ " , "
						+ p4
						+ " , "
						+ p3
						+ " , "
						+ p2
						+ " , "
						+ p1
						+ " , '"
						+ request.getParameter("mission")
						+ "' , '"
						+ request.getParameter("telephone")
						+ "' , '"
						+ request.getParameter("telephone_dir")
						+ "' , "
						+ type_code
						+ " , '"
						+ type_libelle
						+ "' , '"
						+ request.getParameter("bureau") + "') ";
				System.out.println("qry:" + qry);
				st.executeUpdate(qry);
				dvcode = p2;
			}
			st.close();
		} catch (SQLException e) {
			userinfo.setError_message("Erreur: Catalog " + e.getMessage()
					+ "<br>" + qry);
			System.out.println("Error addagent: " + e.toString());
		} finally {
		}
		return dvcode;
	}

	public void Synchro(java.sql.Connection conn, ArrayList ldap,
			HttpServletRequest request) {
		System.out.println("synchro dn:");
		String qry = "";
		try {
			Statement st = conn.createStatement();
			ItemAnnuaire itemldap = (ItemAnnuaire) ldap.get(Integer
					.parseInt(request.getParameter("ldapnumber")));
			String datenaiss = itemldap.getAg_ldap_datenaiss();
			if (datenaiss.length() < 10)
				datenaiss = "0000000000";
			qry = "update " + request.getParameter("table") + " set "
					+ " ag_ldap_synchro    = 1 ," + " ag_ldap_nom    = '"
					+ itemldap.getAg_ldap_nom().toUpperCase() + "' ,"
					+ " ag_ldap_prenom = '" + itemldap.getAg_ldap_prenom()
					+ "' ," + " ag_ldap_dn = '" + itemldap.getAg_ldap_dn()
					+ "' ," + " ag_ldap_mail   = '"
					+ itemldap.getAg_ldap_mail() + "' ,"
					+ " ag_ldap_uid    = '" + itemldap.getAg_ldap_uid() + "' ,"
					+ " ag_ldap_numen   = '" + itemldap.getAg_ldap_numen()
					+ "' ," + " ag_ldap_datenaiss  = '"
					+ datenaiss.substring(6, 10) + datenaiss.substring(3, 5)
					+ datenaiss.substring(0, 2) + "' ,"
					+ " ag_ldap_grade   = '" + itemldap.getAg_ldap_grade()
					+ "'  where ag_code=" + request.getParameter("code");
			request.setAttribute("ag_ldap_dn", itemldap.getAg_ldap_dn());
			System.out.println("synchro dn: " + itemldap.getAg_ldap_dn());
			System.out.println("synchro qy: " + qry);
			st.executeUpdate(qry);

			st.close();
		} catch (SQLException e) {
			System.out.println("Error synchro: " + e.toString());
			System.out.println("qry : " + qry);
		} finally {
		}
	}

	public ArrayList getLdap(UserInfo userinfo, String ldap, String ldapdm,
			String ldappw, String ldapbasedn, HttpServletRequest request) {
		ArrayList alist = new ArrayList();
		System.out
				.println("------------------------ recherche ldap GETLDAP   ------------------------------");
		System.out.println("" + ldap + ldapdm + ldappw + ldapbasedn);
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
			String xfredufonctadm = "*";
			if (request.getParameter("fonct") != null)
				xfredufonctadm = request.getParameter("fonct");
			String MY_FILTER = "cn=*" + request.getParameter("nom") + "*";
			// MY_FILTER =
			// "(&(|("+"uid=*"+request.getParameter("nom")+"*)("+"cn=*"+request.getParameter("nom")+"*))(fredufonctadm="+
			// xfredufonctadm +"))";
			MY_FILTER = "(&(|(" + "uid=*" + request.getParameter("nom") + "*)("
					+ "cn=*" + request.getParameter("nom") + "*)))";
			if (request.getParameter("nom") == null)
				MY_FILTER = "cn=*" + userinfo.getLast_req() + "*";
			if (request.getParameter("submit") != null
					&& request.getParameter("submit").equals("uid"))
				MY_FILTER = "uid=" + request.getParameter("uid") + "";
			// String MY_FILTER = "(|(uid=alain.grainville)(uid=kgrainville))";
			System.out.println("search " + MY_FILTER);
			// String[] attrNames = { "annu-code","annu-profil-ddt"
			// ,"annu-profil-annu" ,"annu-synchro" ,"datenaissance" , "mail"
			// ,"uid", "cn", "sn", "givenName",
			// "telephonenumber","grade","employeenumber","AnnuProfilAsa","AnnuOtpDateSignature"
			// };
			String[] attrNames = {};
			LDAPSearchResults res = ld.search(ldapbasedn, 2, MY_FILTER, null,
					false);
			int n = 0;
			String xcn = "";
			String xsn = "";
			String xgivenName = "";
			String xmail = "";
			String xtel = "";
			String xtitle = "";
			String xuid = "";
			String xgrade = "";
			String xemployeenumber = "";
			String xannu_code = "";
			String xannu_synchro = "";
			String xannu_profil_ddt = "";
			String xannu_profil_annu = "";
			String xdate = "";
			while (res.hasMoreElements()) {
				// System.out.println("search more");
				findEntry = null;
				findEntry = res.next();
				xdn = findEntry.getDN();
				// System.out.println("dn:"+xdn);
				LDAPAttributeSet findAttrs = findEntry.getAttributeSet();
				Enumeration enumAttrs = findAttrs.getAttributes();
				/* Loop on attributes */
				xcn = "";
				xsn = "";
				xgivenName = "";
				xmail = "";
				xtel = "";
				xtitle = "";
				xuid = "";
				xgrade = "";
				xemployeenumber = "";
				xannu_code = "";
				xannu_synchro = "";
				xannu_profil_ddt = "";
				xannu_profil_annu = "";

				xdate = "";
				ItemAnnuaire item = new ItemAnnuaire();
				while (enumAttrs.hasMoreElements()) {
					LDAPAttribute anAttr = (LDAPAttribute) enumAttrs
							.nextElement();
					String attrName = anAttr.getName();
					// System.out.println("attrname:"+attrName);
					/* Loop on values for this attribute */
					Enumeration enumVals = anAttr.getStringValues();

					while (enumVals.hasMoreElements()) {
						String aVal = (String) enumVals.nextElement();
						// System.out.println("attrname:"+attrName+" "+aVal);
						if (attrName.equals("cn"))
							xcn = aVal;
						else if (attrName.equalsIgnoreCase("fredufonctadm"))
							item.setAg_ldap_fredufonctadm(aVal);
						else if (attrName.equalsIgnoreCase("sn"))
							xsn = aVal;
						else if (attrName.equalsIgnoreCase("givenName"))
							xgivenName = aVal;
						else if (attrName.equalsIgnoreCase("uid"))
							xuid = aVal;
						else if (attrName.equalsIgnoreCase("title"))
							xtitle = aVal;
						else if (attrName.equalsIgnoreCase("mail"))
							xmail = aVal;
						else if (attrName.equalsIgnoreCase("telephonenumber"))
							xtel = aVal;
						else if (attrName.equalsIgnoreCase("grade"))
							xgrade = aVal;
						else if (attrName.equalsIgnoreCase("employeenumber"))
							xemployeenumber = aVal;
						else if (attrName.equalsIgnoreCase("annu-code"))
							xannu_code = aVal;
						else if (attrName.equalsIgnoreCase("annu-synchro"))
							xannu_synchro = aVal;
						else if (attrName.equalsIgnoreCase("annu-profil-ddt"))
							xannu_profil_ddt = aVal;
						else if (attrName.equalsIgnoreCase("annu-profil-annu"))
							xannu_profil_annu = aVal;
						else if (attrName.equalsIgnoreCase("datenaissance"))
							item.setAg_ldap_datenaiss(aVal);
						else if (attrName.equalsIgnoreCase("annuprofilasa"))
							item.setAg_ldap_profil_asa(aVal);
						else if (attrName.equalsIgnoreCase("annuotpserie"))
							item.setAg_ldap_otpserie(aVal);
						else if (attrName
								.equalsIgnoreCase("annuotpdatesignature"))
							item.setAg_ldap_otpdatesignature(aVal);
					}
				}

				item.setAg_ldap_mail(xmail);
				item.setAg_ldap_uid(xuid);
				item.setAg_ldap_nom(xsn);
				item.setAg_ldap_prenom(xgivenName);
				item.setAg_ldap_title(xtitle);
				item.setAg_ldap_grade(xgrade);
				item.setAg_ldap_numen(xemployeenumber);
				if (verifNumber(xannu_synchro))
					item.setAg_ldap_synchro(Integer.parseInt(xannu_synchro));

				if (verifNumber(xannu_code)) {
					item.setAg_code(Integer.parseInt(xannu_code));
					item.setAg_ldap_annu_code(Integer.parseInt(xannu_code));
					item.setAg_ldap_synchro(1);
				}
				if (verifNumber(xannu_profil_ddt))
					item.setAg_ldap_profil_ddt(Integer
							.parseInt(xannu_profil_ddt));

				if (verifNumber(xannu_profil_annu))
					item.setAg_ldap_profil_annu(Integer
							.parseInt(xannu_profil_annu));
				// si annucode estexite dans ldap on considere que la personnes
				// est synchro ldap

				item.setAg_ldap_dn(xdn);
				alist.add(item);
				status = 0;
			}

			/* Done, so disconnect */
			if ((ld != null) && ld.isConnected()) {
				ld.disconnect();
			}
			// ---------------------------------------------------------------------------
		} catch (LDAPException e) {
			System.out.println("Error ldap: " + e.toString());
			// Exception e = new Exception("The operation is unknown.");
		}
		// sort resultat ldap
		// _____________________________________________________________
		int nb = alist.size();
		System.out.println("Sort ldap: " + nb);

		ItemAnnuaire[] items = new ItemAnnuaire[nb];
		for (int i = 0; i < nb; i++) {
			ItemAnnuaire E = (ItemAnnuaire) alist.get(i);
			items[i] = E;
		}
		Arrays.sort(items);
		ArrayList slist = new ArrayList();
		for (int i = 0; i < nb; i++) {
			ItemAnnuaire E = (ItemAnnuaire) items[i];
			slist.add(E);
		}
		return slist;
	}

	public ArrayList getLdapSun(UserInfo userinfo, String ldap, String ldapdm,
			String ldappw, String ldapbasedn, HttpServletRequest request) {
		ArrayList alist = new ArrayList();
		System.out
				.println("------------------------ recherche ldap GETLDAP sans1telephone  ------------------------------");
		System.out.println("" + ldap + ldapdm + ldappw + ldapbasedn);
		HttpSession session = request.getSession(true);
		LDAPConnection ld = null;
		LDAPEntry findEntry = null;
		int status = -1;
		String xdn = "";
		try {
			ld = new LDAPConnection();
			ld.connect(ldap, 389);
			System.out.println("authenticate sun");
			ld.authenticate(3, ldapdm, ldappw);
			String xfredufonctadm = "*";
			if (request.getParameter("fonct") != null)
				xfredufonctadm = request.getParameter("fonct");
			//String MY_FILTER = "&(telephoneNumber=0262 48*)(annu-division=*)";
			String MY_FILTER = "(annu-division=*)";

			System.out.println("search " + MY_FILTER);
			// String[] attrNames = { "annu-code","annu-profil-ddt"
			// ,"annu-profil-annu" ,"annu-synchro" ,"datenaissance" , "mail"
			// ,"uid", "cn", "sn", "givenName",
			// "telephonenumber","grade","employeenumber","AnnuProfilAsa","AnnuOtpDateSignature"
			// };
			String[] attrNames = {};
			LDAPSearchResults res = ld.search(ldapbasedn, 2, MY_FILTER, null,
					false);
			int n = 0;
			String xcn = "";
			String xsn = "";
			String xgivenName = "";
			String xmail = "";
			String xtel = "";
			String xtitle = "";
			String xuid = "";
			String xgrade = "";
			String xemployeenumber = "";
			String xannu_code = "";
			String xannu_synchro = "";
			String xannu_profil_ddt = "";
			String xannu_profil_annu = "";
			String xdate = "";
			while (res.hasMoreElements()) {
				// System.out.println("search more");
				findEntry = null;
				findEntry = res.next();
				xdn = findEntry.getDN();
				// System.out.println("dn:"+xdn);
				LDAPAttributeSet findAttrs = findEntry.getAttributeSet();
				Enumeration enumAttrs = findAttrs.getAttributes();
				/* Loop on attributes */
				xcn = "";
				xsn = "";
				xgivenName = "";
				xmail = "";
				xtel = "";
				xtitle = "";
				xuid = "";
				xgrade = "";
				xemployeenumber = "";
				xannu_code = "";
				xannu_synchro = "";
				xannu_profil_ddt = "";
				xannu_profil_annu = "";

				xdate = "";
				ItemSun item = new ItemSun();
				while (enumAttrs.hasMoreElements()) {
					LDAPAttribute anAttr = (LDAPAttribute) enumAttrs
							.nextElement();
					String attrName = anAttr.getName();
					// System.out.println("attrname:"+attrName);
					/* Loop on values for this attribute */
					Enumeration enumVals = anAttr.getStringValues();

					while (enumVals.hasMoreElements()) {
						String aVal = (String) enumVals.nextElement();
						// System.out.println("attrname:"+attrName+" "+aVal);
						if (attrName.equals("cn"))
							xcn = aVal;
						else if (attrName.equalsIgnoreCase("fredufonctadm"))
							item.setAg_ldap_fredufonctadm(aVal);
						else if (attrName.equalsIgnoreCase("sn"))
							xsn = aVal;
						else if (attrName.equalsIgnoreCase("givenName"))
							xgivenName = aVal;
						else if (attrName.equalsIgnoreCase("uid"))
							xuid = aVal;
						else if (attrName.equalsIgnoreCase("title"))
							xtitle = aVal;
						else if (attrName.equalsIgnoreCase("mail"))
							xmail = aVal;
						else if (attrName.equalsIgnoreCase("telephonenumber"))
							xtel = aVal;
						else if (attrName.equalsIgnoreCase("grade"))
							xgrade = aVal;
						else if (attrName.equalsIgnoreCase("employeenumber"))
							xemployeenumber = aVal;
						else if (attrName.equalsIgnoreCase("annu-code"))
							xannu_code = aVal;
						else if (attrName.equalsIgnoreCase("annu-synchro"))
							xannu_synchro = aVal;
						else if (attrName.equalsIgnoreCase("annu-profil-ddt"))
							xannu_profil_ddt = aVal;
						else if (attrName.equalsIgnoreCase("annu-profil-annu"))
							xannu_profil_annu = aVal;
						else if (attrName.equalsIgnoreCase("annu-division"))
							item.setAg_ldap_annu_division(aVal);
						else if (attrName.equalsIgnoreCase("annuprofilasa"))
							item.setAg_ldap_profil_asa(aVal);
						else if (attrName.equalsIgnoreCase("annuotpserie"))
							item.setAg_ldap_otpserie(aVal);
						else if (attrName
								.equalsIgnoreCase("annuotpdatesignature"))
							item.setAg_ldap_otpdatesignature(aVal);
					}
				}

				item.setAg_ldap_mail(xmail);
				item.setAg_ldap_uid(xuid);
				item.setAg_ldap_nom(xsn);
				item.setAg_ldap_prenom(xgivenName);
				item.setAg_ldap_title(xtitle);
				item.setAg_ldap_grade(xgrade);
				item.setAg_ldap_numen(xemployeenumber);
				if (verifNumber(xannu_synchro))
					item.setAg_ldap_synchro(Integer.parseInt(xannu_synchro));

				if (verifNumber(xannu_code)) {
					item.setAg_code(Integer.parseInt(xannu_code));
					item.setAg_ldap_annu_code(Integer.parseInt(xannu_code));
					item.setAg_ldap_synchro(1);
				}
				if (verifNumber(xannu_profil_ddt))
					item.setAg_ldap_profil_ddt(Integer
							.parseInt(xannu_profil_ddt));

				if (verifNumber(xannu_profil_annu))
					item.setAg_ldap_profil_annu(Integer
							.parseInt(xannu_profil_annu));
				// si annucode estexite dans ldap on considere que la personnes
				// est synchro ldap

				item.setAg_ldap_dn(xdn);
				alist.add(item);
				status = 0;
			}
			ItemSun item = new ItemSun();
			item.setAg_ldap_nom("Delimiteur fin de fichier");
			item.setAg_ldap_uid("ZZZZZZ");
			alist.add(item);
			/* Done, so disconnect */
			if ((ld != null) && ld.isConnected()) {
				ld.disconnect();
			}
			// ---------------------------------------------------------------------------
		} catch (LDAPException e) {
			System.out.println("Error ldap: " + e.toString());
			// Exception e = new Exception("The operation is unknown.");
		}
		// sort resultat ldap
		// _____________________________________________________________
		int nb = alist.size();
		System.out.println("  ldap: " + nb);

		ItemSun[] items = new ItemSun[nb];
		for (int i = 0; i < nb; i++) {
			ItemSun E = (ItemSun) alist.get(i);
			items[i] = E;
		}
		System.out.println("Sort ...... ");

		Arrays.sort(items, new UidComparator());
		ArrayList slist = new ArrayList();
		for (int i = 0; i < nb; i++) {
			ItemSun E = (ItemSun) items[i];
			System.out.println(" sort ldap: " + E.getAg_ldap_uid());
			slist.add(E);
		}
		return slist;
	}

	public ArrayList getMysql(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request, int codeagt, int codebur, int codeser,
			int codediv, int codepol) {
		ArrayList alist = new ArrayList();
		System.out.println(" getMysql3");
		try {
			Statement st = conn.createStatement();
			Statement st1 = conn.createStatement();
			String qryannu = "   delete from tagent  ";
			st.executeUpdate(qryannu);
			// recherche division
			//qryannu = "  insert into tagent select dv_nom,dv_sigle,sv_nom,ag_ldap_pw, ag_ldap_uid,  ag_ldap_nom,ag_ldap_prenom,ag_ldap_datenaiss,af_telephone,af_telephone_dir,ag_ldap_mail,af_bureau,ag_code,'1955','06','07'  from agent,division,service,bureau,affectation"
			//		+ " where af_ag_code=ag_code and af_dv_code = dv_code    and af_sv_code=sv_code and af_bu_code=bu_code  order by dv_nom, ag_ldap_nom , ag_ldap_prenom ";
			qryannu = "  insert into tagent select dv_nom,dv_sigle,sv_nom,ag_ldap_pw, ag_ldap_uid,  ag_ldap_nom,ag_ldap_prenom,ag_ldap_datenaiss,af_telephone,af_telephone_dir,ag_ldap_mail,af_bureau,ag_code,'1955','06','07'  from agent,division, bureau,affectation   LEFT JOIN service  ON (af_sv_code=sv_code) "
					+ " where af_ag_code=ag_code and af_dv_code = dv_code    and af_bu_code=bu_code  order by dv_nom, ag_ldap_nom , ag_ldap_prenom ";
			st.executeUpdate(qryannu);
			//String qryannu1 = " select distinct ag_code,sv_nom,ag_ldap_pw, ag_ldap_uid,MID(ag_ldap_uid,1,1) as ag_ldap_lettre,  ag_ldap_nom,ag_ldap_prenom,ag_ldap_datenaiss,ag_ldap_mail   from tagent where af_telephone LIKE '0262 48%' ";
			String qryannu1 = " select distinct ag_code,sv_nom,ag_ldap_pw, ag_ldap_uid,MID(ag_ldap_uid,1,1) as ag_ldap_lettre,  ag_ldap_nom,ag_ldap_prenom,ag_ldap_datenaiss,ag_ldap_mail   from tagent order by ag_ldap_uid";
			ResultSet rs = st.executeQuery(qryannu1);
			while (rs.next()) {
				ItemSun item = new ItemSun();
				item.setAg_ldap_uid(rs.getString("ag_ldap_uid"));
				item.setAg_ldap_nom(rs.getString("ag_ldap_nom"));
				item.setAg_ldap_prenom(rs.getString("ag_ldap_prenom"));
				item.setAg_ldap_mail(rs.getString("ag_ldap_mail"));
				item.setAg_ldap_datenaiss(rs.getString("ag_ldap_datenaiss"));
				String agcode = rs.getString("ag_code");
				String qry2 = "select from affectation,division,agent where af_dv_code=dv_code and af_ag_code="
						+ agcode;
				// item.setAg_ldap_annu_division(rs.getString("dv_nom"));
				// System.out.println("  mysql: " + item.getAg_ldap_uid());
				alist.add(item);
			}
			ItemSun item = new ItemSun();
			item.setAg_ldap_nom("Delimiteur fin de fichier");
			item.setAg_ldap_uid("ZZZZZZ");
			alist.add(item);
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			// userinfo.setError_message("Erreur: Catalog "+e.getMessage()+" "+qryannu1);
			e.printStackTrace();
		}
		int nb = alist.size();
		System.out.println("  mysql: " + nb);
		String olduid = "--------------";
		ItemSun[] items = new ItemSun[nb];
		for (int i = 0; i < nb; i++) {
			ItemSun E = (ItemSun) alist.get(i);
			items[i] = E;
		}
		System.out.println("Sort ...... ");
		int j = 0;
		Arrays.sort(items, new UidComparator());
		ArrayList slist = new ArrayList();
		ItemSun O = new ItemSun();
		for (int i = 0; i < nb; i++) {
			// System.out.println("i= "+ i+" "+" j= "+j+ " "+
			// items[i].getAg_ldap_uid());
			ItemSun E = (ItemSun) items[i];
			ItemSun N = (ItemSun) items[i];
			String newuid = E.getAg_ldap_uid();
			if (newuid.compareTo(olduid) == 0) {
				String newdv = N.getAg_ldap_annu_division();
				String olddv = O.getAg_ldap_annu_division();
				// System.out.println(" egalite old :i= "+ i+" "+" j= "+j+ " "+
				// O.getAg_ldap_uid() +" nom:" + E.getAg_ldap_nom()+
				// " div:"+newdv);
				// System.out.println(" mod slist :i= "+ i+" "+" j= "+j+ " "+
				// items[i].getAg_ldap_uid() + olddv + "-"+ newdv);
				O.setAg_ldap_annu_division(olddv + " , " + newdv);
				// slist.add(O);
				olduid = newuid;
			} else {
				// System.out.println(" add slist :i= "+ i+" "+" j= "+j+ " "+
				// O.getAg_ldap_uid()+ " "+ O.getAg_ldap_nom()+ " " +
				// O.getAg_ldap_annu_division() );
				slist.add(O);
				// System.out.println(" --- slist ");
				O = (ItemSun) items[i];
				olduid = newuid;
			}
		}
		int z = slist.size();
		for (int i = 0; i < z; i++) {
			ItemSun E = (ItemSun) slist.get(i);
			// System.out.println(" en reel   :i= "+ i+ E.getAg_ldap_uid()+ " "+
			// E.getAg_ldap_nom()+ " " + E.getAg_ldap_annu_division() );
		}
		slist.add(O);
		return slist;
	}

	public ArrayList getMysqlold(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request, int codeagt, int codebur, int codeser,
			int codediv, int codepol) {
		ArrayList alist = new ArrayList();

		try {
			Statement st = conn.createStatement();
			String qryannu = "   delete from tagent  ";
			st.executeUpdate(qryannu);
			// recherche division
			qryannu = "  insert into tagent select dv_nom,dv_sigle,sv_nom,ag_ldap_pw, ag_ldap_uid,  ag_ldap_nom,ag_ldap_prenom,ag_ldap_datenaiss,af_telephone,af_telephone_dir,ag_ldap_mail,af_bureau  from agent,division,service,bureau,affectation"
					+ " where af_ag_code=ag_code and af_dv_code = dv_code    and af_sv_code=sv_code and af_bu_code=bu_code  order by dv_nom, ag_ldap_nom , ag_ldap_prenom ";
			st.executeUpdate(qryannu);
			String qryannu1 = " select dv_nom,dv_sigle,sv_nom,ag_ldap_pw, ag_ldap_uid,MID(ag_ldap_uid,1,1) as ag_ldap_lettre,  ag_ldap_nom,ag_ldap_prenom,ag_ldap_datenaiss,af_telephone,af_telephone_dir,ag_ldap_mail,af_bureau   from tagent where af_telephone LIKE '0262 48%' ";
			ResultSet rs = st.executeQuery(qryannu1);
			while (rs.next()) {
				ItemSun item = new ItemSun();
				item.setAg_ldap_uid(rs.getString("ag_ldap_uid"));
				item.setAg_ldap_nom(rs.getString("ag_ldap_nom"));
				item.setAg_ldap_prenom(rs.getString("ag_ldap_prenom"));
				item.setAg_ldap_mail(rs.getString("ag_ldap_mail"));
				item.setAg_ldap_datenaiss(rs.getString("ag_ldap_datenaiss"));
				item.setAg_ldap_annu_division(rs.getString("dv_nom"));
				alist.add(item);
			}

			ItemSun item = new ItemSun();
			item.setAg_ldap_nom("Delimiteur fin de fichier");
			item.setAg_ldap_uid("ZZZZZZ");
			alist.add(item);
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			// userinfo.setError_message("Erreur: Catalog "+e.getMessage()+" "+qryannu1);
			e.printStackTrace();
		}
		int nb = alist.size();
		System.out.println("  mysql: " + nb);
		String olduid = "--------------";
		ItemSun[] items = new ItemSun[nb];
		for (int i = 0; i < nb; i++) {
			ItemSun E = (ItemSun) alist.get(i);
			items[i] = E;
		}
		System.out.println("Sort ...... ");
		int j = 0;
		Arrays.sort(items, new UidComparator());
		ArrayList slist = new ArrayList();
		ItemSun O = new ItemSun();
		for (int i = 0; i < nb; i++) {
			// System.out.println("i= "+ i+" "+" j= "+j+ " "+
			// items[i].getAg_ldap_uid());
			ItemSun E = (ItemSun) items[i];
			ItemSun N = (ItemSun) items[i];
			String newuid = E.getAg_ldap_uid();
			if (newuid.compareTo(olduid) == 0) {
				String newdv = N.getAg_ldap_annu_division();
				String olddv = O.getAg_ldap_annu_division();
				// System.out.println(" egalite old :i= "+ i+" "+" j= "+j+ " "+
				// O.getAg_ldap_uid() +" nom:" + E.getAg_ldap_nom()+
				// " div:"+newdv);
				// System.out.println(" mod slist :i= "+ i+" "+" j= "+j+ " "+
				// items[i].getAg_ldap_uid() + olddv + "-"+ newdv);
				O.setAg_ldap_annu_division(olddv + " , " + newdv);
				// slist.add(O);
				olduid = newuid;
			} else {
				// System.out.println(" add slist :i= "+ i+" "+" j= "+j+ " "+
				// O.getAg_ldap_uid()+ " "+ O.getAg_ldap_nom()+ " " +
				// O.getAg_ldap_annu_division() );
				slist.add(O);
				// System.out.println(" --- slist ");
				O = (ItemSun) items[i];
				olduid = newuid;
			}
		}
		int z = slist.size();
		for (int i = 0; i < z; i++) {
			ItemSun E = (ItemSun) slist.get(i);
			// System.out.println(" en reel   :i= "+ i+ E.getAg_ldap_uid()+ " "+
			// E.getAg_ldap_nom()+ " " + E.getAg_ldap_annu_division() );
		}
		slist.add(O);
		return slist;
	}

	public ArrayList getLdapScribe(UserInfo userinfo, String ldap,
			String ldapdm, String ldappwS, String ldapbasedn,
			HttpServletRequest request) {
		ArrayList alist = new ArrayList();
		System.out
				.println("------------------------ recherche ldap GETLDAP scribe  ------------------------------");
		ldapbasedn = "ou=local,ou=personnels,ou=Utilisateurs,ou=9740049K,ou=ac-reunion,ou=education,o=gouv,c=fr";
		String ldapbasednG = "ou=local,ou=groupes,ou=9740049K,ou=ac-reunion,ou=education,o=gouv,c=fr";
		HttpSession session = request.getSession(true);
		LDAPConnection ld = null;
		LDAPEntry findEntry = null;
		LDAPConnection ldG = null;
		LDAPEntry findEntryG = null;
		int status = -1;
		String xdn = "";
		try {
			ld = new LDAPConnection();
			ld.connect("scribe.in.ac-reunion.fr", 389);
			ld.authenticate(3, "cn=admin,o=gouv,c=fr", ldappwS);
			ldG = new LDAPConnection();
			ldG.connect("scribe.in.ac-reunion.fr", 389);
			ldG.authenticate(3, "cn=admin,o=gouv,c=fr", ldappwS);
			String MY_FILTER = "uid=*";
			String[] attrNames = {};
			LDAPSearchResults res = ld.search(ldapbasedn, 2, MY_FILTER, null,
					false);
			int n = 0;
			String xcn = "";
			String xsn = "";
			String xgivenName = "";
			String xmail = "";
			String xtel = "";
			String xtitle = "";
			String xuid = "";
			String xgrade = "";
			String xemployeenumber = "";
			String xannu_code = "";
			String xannu_synchro = "";
			String xannu_profil_ddt = "";
			String xannu_profil_annu = "";
			String xdate = "";
			while (res.hasMoreElements()) {
				// System.out.println("search more");
				findEntry = null;
				findEntry = res.next();
				xdn = findEntry.getDN();
				// System.out.println("dn:"+xdn);
				LDAPAttributeSet findAttrs = findEntry.getAttributeSet();
				Enumeration enumAttrs = findAttrs.getAttributes();
				/* Loop on attributes */
				xcn = "";
				xsn = "";
				xgivenName = "";
				xmail = "";
				xtel = "";
				xtitle = "";
				xuid = "";
				xgrade = "";
				xemployeenumber = "";
				xannu_code = "";
				xannu_synchro = "";
				xannu_profil_ddt = "";
				xannu_profil_annu = "";

				ItemSun item = new ItemSun();
				while (enumAttrs.hasMoreElements()) {
					LDAPAttribute anAttr = (LDAPAttribute) enumAttrs
							.nextElement();
					String attrName = anAttr.getName();
					Enumeration enumVals = anAttr.getStringValues();

					while (enumVals.hasMoreElements()) {
						String aVal = (String) enumVals.nextElement();
						// System.out.println("attrname:"+attrName+" "+aVal);
						if (attrName.equals("cn"))
							xcn = aVal;
						else if (attrName.equalsIgnoreCase("fredufonctadm"))
							item.setAg_ldap_fredufonctadm(aVal);
						else if (attrName.equalsIgnoreCase("sn"))
							item.setAg_ldap_nom(aVal);
						else if (attrName.equalsIgnoreCase("givenName"))
							item.setAg_ldap_prenom(aVal);
						else if (attrName.equalsIgnoreCase("uid"))
							item.setAg_ldap_uid(aVal);
						else if (attrName.equalsIgnoreCase("title"))
							item.setAg_ldap_title(aVal);
						else if (attrName.equalsIgnoreCase("mail"))
							item.setAg_ldap_mail(aVal);
						else if (attrName.equalsIgnoreCase("telephonenumber"))
							xtel = aVal;
						else if (attrName.equalsIgnoreCase("grade"))
							item.setAg_ldap_grade(aVal);
						else if (attrName.equalsIgnoreCase("employeenumber"))
							item.setAg_ldap_numen(aVal);
						else if (attrName.equalsIgnoreCase("annu-code"))
							xannu_code = aVal;
						else if (attrName.equalsIgnoreCase("annu-synchro"))
							xannu_synchro = aVal;
						else if (attrName.equalsIgnoreCase("annu-profil-ddt"))
							xannu_profil_ddt = aVal;
						else if (attrName.equalsIgnoreCase("annu-profil-annu"))
							xannu_profil_annu = aVal;
						else if (attrName.equalsIgnoreCase("annu-division"))
							item.setAg_ldap_annu_division(aVal);
					}
				}
				// -------------------------recherche
				// groupes-------------------------------i
				String filterG = "memberUid=" + item.getAg_ldap_uid();
				;
				String xcnG = item.getAg_ldap_annu_division();
				LDAPSearchResults resG = ldG.search(ldapbasednG, 2, filterG,
						null, false);
				while (resG.hasMoreElements()) {
					// System.out.println("search more");
					findEntryG = null;
					findEntryG = resG.next();
					xdn = findEntryG.getDN();
					// System.out.println("dn:"+xdn);
					LDAPAttributeSet findAttrsG = findEntryG.getAttributeSet();
					Enumeration enumAttrsG = findAttrsG.getAttributes();
					while (enumAttrsG.hasMoreElements()) {
						LDAPAttribute anAttrG = (LDAPAttribute) enumAttrsG
								.nextElement();
						String attrNameG = anAttrG.getName();
						Enumeration enumValsG = anAttrG.getStringValues();

						while (enumValsG.hasMoreElements()) {
							String aValG = (String) enumValsG.nextElement();
							// System.out.println("attrname:"+attrName+" "+aVal);
							if (attrNameG.equals("cn"))
								if (!(aValG.equalsIgnoreCase("DomainUsers"))
										&& (!(aValG
												.equalsIgnoreCase("administratifs"))))
									xcnG = xcnG + " , " + aValG;
						}
					}
				}
				item.setAg_ldap_annu_division(xcnG);
				// ------------------------------------------------------------
				if (verifNumber(xannu_synchro))
					item.setAg_ldap_synchro(Integer.parseInt(xannu_synchro));

				if (verifNumber(xannu_code)) {
					item.setAg_code(Integer.parseInt(xannu_code));
					item.setAg_ldap_annu_code(Integer.parseInt(xannu_code));
					item.setAg_ldap_synchro(1);
				}
				if (verifNumber(xannu_profil_ddt))
					item.setAg_ldap_profil_ddt(Integer
							.parseInt(xannu_profil_ddt));

				if (verifNumber(xannu_profil_annu))
					item.setAg_ldap_profil_annu(Integer
							.parseInt(xannu_profil_annu));
				// si annucode estexite dans ldap on considere que la personnes
				// est synchro ldap

				item.setAg_ldap_dn(xdn);
				alist.add(item);
				status = 0;
			}
			ItemSun item = new ItemSun();
			item.setAg_ldap_nom("Delimiteur fin de fichier");
			item.setAg_ldap_uid("ZZZZZZ");
			alist.add(item);

			/* Done, so disconnect */
			if ((ld != null) && ld.isConnected()) {
				ld.disconnect();
			}
			// ---------------------------------------------------------------------------
		} catch (LDAPException e) {
			System.out.println("Error ldap: " + e.toString());
			// Exception e = new Exception("The operation is unknown.");
		}
		// sort resultat ldap
		// _____________________________________________________________
		int nb = alist.size();
		System.out.println("  ldap: " + nb);

		ItemSun[] items = new ItemSun[nb];
		for (int i = 0; i < nb; i++) {
			ItemSun E = (ItemSun) alist.get(i);
			items[i] = E;
		}
		System.out.println("Sort ...... ");

		Arrays.sort(items, new UidComparator());
		ArrayList slist = new ArrayList();
		for (int i = 0; i < nb; i++) {
			ItemSun E = (ItemSun) items[i];
			slist.add(E);
		}
		return slist;
	}

	public ArrayList getpartageScribe(UserInfo userinfo, String ldap,
			String ldapdm, String ldappwS, String ldapbasedn,
			HttpServletRequest request) {
		ArrayList alist = new ArrayList();
		System.out
				.println("------------------------ recherche ldap GETLDAP scribe  ------------------------------");
		ldapbasedn = "ou=local,ou=personnels,ou=Utilisateurs,ou=9740049K,ou=ac-reunion,ou=education,o=gouv,c=fr";
		String ldapbasednG = "ou=local,ou=groupes,ou=9740049K,ou=ac-reunion,ou=education,o=gouv,c=fr";
		HttpSession session = request.getSession(true);
		LDAPConnection ld = null;
		LDAPEntry findEntry = null;
		LDAPConnection ldG = null;
		LDAPEntry findEntryG = null;
		int status = -1;
		String xdn = "";
		try {
			ld = new LDAPConnection();
			ld.connect("scribe.in.ac-reunion.fr", 389);
			ld.authenticate(3, "cn=admin,o=gouv,c=fr", ldappwS);
			ldG = new LDAPConnection();
			ldG.connect("scribe.in.ac-reunion.fr", 389);
			ldG.authenticate(3, "cn=admin,o=gouv,c=fr", ldappwS);
			String MY_FILTER = "uid=*";
			String[] attrNames = {};
			LDAPSearchResults res = ld.search(ldapbasedn, 2, MY_FILTER, null,
					false);
			int n = 0;
			String xcn = "";
			String xsn = "";
			String xgivenName = "";
			String xmail = "";
			String xtel = "";
			String xtitle = "";
			String xuid = "";
			String xgrade = "";
			String xemployeenumber = "";
			String xannu_code = "";
			String xannu_synchro = "";
			String xannu_profil_ddt = "";
			String xannu_profil_annu = "";
			String xdate = "";
			while (res.hasMoreElements()) {
				// System.out.println("search more");
				findEntry = null;
				findEntry = res.next();
				xdn = findEntry.getDN();
				// System.out.println("dn:"+xdn);
				LDAPAttributeSet findAttrs = findEntry.getAttributeSet();
				Enumeration enumAttrs = findAttrs.getAttributes();
				/* Loop on attributes */
				xcn = "";
				xsn = "";
				xgivenName = "";
				xmail = "";
				xtel = "";
				xtitle = "";
				xuid = "";
				xgrade = "";
				xemployeenumber = "";
				xannu_code = "";
				xannu_synchro = "";
				xannu_profil_ddt = "";
				xannu_profil_annu = "";

				ItemSun item = new ItemSun();
				while (enumAttrs.hasMoreElements()) {
					LDAPAttribute anAttr = (LDAPAttribute) enumAttrs
							.nextElement();
					String attrName = anAttr.getName();
					Enumeration enumVals = anAttr.getStringValues();

					while (enumVals.hasMoreElements()) {
						String aVal = (String) enumVals.nextElement();
						// System.out.println("attrname:"+attrName+" "+aVal);
						if (attrName.equals("cn"))
							xcn = aVal;
						else if (attrName.equalsIgnoreCase("fredufonctadm"))
							item.setAg_ldap_fredufonctadm(aVal);
						else if (attrName.equalsIgnoreCase("sn"))
							item.setAg_ldap_nom(aVal);
						else if (attrName.equalsIgnoreCase("givenName"))
							item.setAg_ldap_prenom(aVal);
						else if (attrName.equalsIgnoreCase("uid"))
							item.setAg_ldap_uid(aVal);
						else if (attrName.equalsIgnoreCase("title"))
							item.setAg_ldap_title(aVal);
						else if (attrName.equalsIgnoreCase("mail"))
							item.setAg_ldap_mail(aVal);
						else if (attrName.equalsIgnoreCase("telephonenumber"))
							xtel = aVal;
						else if (attrName.equalsIgnoreCase("grade"))
							item.setAg_ldap_grade(aVal);
						else if (attrName.equalsIgnoreCase("employeenumber"))
							item.setAg_ldap_numen(aVal);
						else if (attrName.equalsIgnoreCase("annu-code"))
							xannu_code = aVal;
						else if (attrName.equalsIgnoreCase("annu-synchro"))
							xannu_synchro = aVal;
						else if (attrName.equalsIgnoreCase("annu-profil-ddt"))
							xannu_profil_ddt = aVal;
						else if (attrName.equalsIgnoreCase("annu-profil-annu"))
							xannu_profil_annu = aVal;
						else if (attrName.equalsIgnoreCase("annu-division"))
							item.setAg_ldap_annu_division(aVal);
					}
				}
				// -------------------------recherche
				// groupes-------------------------------i
				String filterG = "memberUid=" + item.getAg_ldap_uid();
				;
				String xcnG = item.getAg_ldap_annu_division();
				LDAPSearchResults resG = ldG.search(ldapbasednG, 2, filterG,
						null, false);
				while (resG.hasMoreElements()) {
					// System.out.println("search more");
					findEntryG = null;
					findEntryG = resG.next();
					xdn = findEntryG.getDN();
					// System.out.println("dn:"+xdn);
					LDAPAttributeSet findAttrsG = findEntryG.getAttributeSet();
					Enumeration enumAttrsG = findAttrsG.getAttributes();
					while (enumAttrsG.hasMoreElements()) {
						LDAPAttribute anAttrG = (LDAPAttribute) enumAttrsG
								.nextElement();
						String attrNameG = anAttrG.getName();
						Enumeration enumValsG = anAttrG.getStringValues();

						while (enumValsG.hasMoreElements()) {
							String aValG = (String) enumValsG.nextElement();
							// System.out.println("attrname:"+attrName+" "+aVal);
							if (attrNameG.equals("cn"))
								if (!(aValG.equalsIgnoreCase("DomainUsers"))
										&& (!(aValG
												.equalsIgnoreCase("administratifs"))))
									xcnG = xcnG + " , " + aValG;
						}
					}
				}
				item.setAg_ldap_annu_division(xcnG);
				// ------------------------------------------------------------
				if (verifNumber(xannu_synchro))
					item.setAg_ldap_synchro(Integer.parseInt(xannu_synchro));

				if (verifNumber(xannu_code)) {
					item.setAg_code(Integer.parseInt(xannu_code));
					item.setAg_ldap_annu_code(Integer.parseInt(xannu_code));
					item.setAg_ldap_synchro(1);
				}
				if (verifNumber(xannu_profil_ddt))
					item.setAg_ldap_profil_ddt(Integer
							.parseInt(xannu_profil_ddt));

				if (verifNumber(xannu_profil_annu))
					item.setAg_ldap_profil_annu(Integer
							.parseInt(xannu_profil_annu));
				// si annucode estexite dans ldap on considere que la personnes
				// est synchro ldap

				item.setAg_ldap_dn(xdn);
				alist.add(item);
				status = 0;
			}
			ItemSun item = new ItemSun();
			item.setAg_ldap_nom("Delimiteur fin de fichier");
			item.setAg_ldap_uid("ZZZZZZ");
			alist.add(item);

			/* Done, so disconnect */
			if ((ld != null) && ld.isConnected()) {
				ld.disconnect();
			}
			// ---------------------------------------------------------------------------
		} catch (LDAPException e) {
			System.out.println("Error ldap: " + e.toString());
			// Exception e = new Exception("The operation is unknown.");
		}
		// sort resultat ldap
		// _____________________________________________________________
		int nb = alist.size();
		System.out.println("  ldap: " + nb);

		ItemSun[] items = new ItemSun[nb];
		for (int i = 0; i < nb; i++) {
			ItemSun E = (ItemSun) alist.get(i);
			items[i] = E;
		}
		System.out.println("Sort ...... ");

		Arrays.sort(items, new UidComparator());
		ArrayList slist = new ArrayList();
		for (int i = 0; i < nb; i++) {
			ItemSun E = (ItemSun) items[i];
			slist.add(E);
		}
		return slist;
	}

	public ArrayList getValidLdap(java.sql.Connection conn, UserInfo userinfo,
			String ldap, String ldapdm, String ldappw, String ldapbasedn,
			HttpServletRequest request) {
		ArrayList alist = new ArrayList();
		System.out
				.println("------------------------ recherche ldap dispatcher ------------------------------");
		System.out.println("" + ldap + ldapdm + ldappw + ldapbasedn);
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

			String MY_FILTER = "annu-code=*";

			// String MY_FILTER = "(|(uid=alain.grainville)(uid=kgrainville))";
			System.out.println("search" + MY_FILTER);
			// String[] attrNames = { "annu-code","annu-profil-ddt"
			// ,"annu-profil-annu" ,"annu-synchro" ,"datenaissance" , "mail"
			// ,"uid", "cn", "sn", "givenName",
			// "telephonenumber","grade","employeenumber","AnnuProfilAsa","AnnuOtpDateSignature"
			// };
			String[] attrNames = {};
			LDAPSearchResults res = ld.search(ldapbasedn, 2, MY_FILTER,
					attrNames, false);
			int n = 0;
			String xcn = "";
			String xsn = "";
			String xgivenName = "";
			String xmail = "";
			String xtel = "";
			String xtitle = "";
			String xuid = "";
			String xgrade = "";
			String xemployeenumber = "";
			String xannu_code = "";
			String xannu_synchro = "";
			String xannu_profil_ddt = "";
			String xannu_profil_annu = "";
			String xdate = "";
			while (res.hasMoreElements()) {
				// System.out.println("search more");
				findEntry = null;
				findEntry = res.next();
				xdn = findEntry.getDN();
				// System.out.println("dn:"+xdn);
				LDAPAttributeSet findAttrs = findEntry.getAttributeSet();
				Enumeration enumAttrs = findAttrs.getAttributes();
				/* Loop on attributes */
				xcn = "";
				xsn = "";
				xgivenName = "";
				xmail = "";
				xtel = "";
				xtitle = "";
				xuid = "";
				xgrade = "";
				xemployeenumber = "";
				xannu_code = "";
				xannu_synchro = "";
				xannu_profil_ddt = "";
				xannu_profil_annu = "";

				xdate = "";
				ItemAnnuaire item = new ItemAnnuaire();
				while (enumAttrs.hasMoreElements()) {
					LDAPAttribute anAttr = (LDAPAttribute) enumAttrs
							.nextElement();
					String attrName = anAttr.getName();
					// System.out.println("attrname:"+attrName);
					/* Loop on values for this attribute */
					Enumeration enumVals = anAttr.getStringValues();

					while (enumVals.hasMoreElements()) {
						String aVal = (String) enumVals.nextElement();
						// System.out.println("attrname:"+attrName+" "+aVal);
						if (attrName.equals("cn"))
							xcn = aVal;
						else if (attrName.equalsIgnoreCase("sn"))
							xsn = aVal;
						else if (attrName.equalsIgnoreCase("givenName"))
							xgivenName = aVal;
						else if (attrName.equalsIgnoreCase("uid"))
							xuid = aVal;
						else if (attrName.equalsIgnoreCase("title"))
							xtitle = aVal;
						else if (attrName.equalsIgnoreCase("mail"))
							xmail = aVal;
						else if (attrName.equalsIgnoreCase("telephonenumber"))
							xtel = aVal;
						else if (attrName.equalsIgnoreCase("grade"))
							xgrade = aVal;
						else if (attrName.equalsIgnoreCase("employeenumber"))
							xemployeenumber = aVal;
						else if (attrName.equalsIgnoreCase("annu-code"))
							xannu_code = aVal;
						else if (attrName.equalsIgnoreCase("annu-synchro"))
							xannu_synchro = aVal;
						else if (attrName.equalsIgnoreCase("annu-profil-ddt"))
							xannu_profil_ddt = aVal;
						else if (attrName.equalsIgnoreCase("annu-profil-annu"))
							xannu_profil_annu = aVal;
						else if (attrName.equalsIgnoreCase("annuprofilasa"))
							item.setAg_ldap_profil_asa(aVal);

						else if (attrName
								.equalsIgnoreCase("annuotpdatesignature"))
							item.setAg_ldap_otpdatesignature(aVal);
					}
				}

				item.setAg_ldap_mail(xmail);
				item.setAg_ldap_uid(xuid);
				item.setAg_ldap_nom(xsn);
				item.setAg_ldap_prenom(xgivenName);
				item.setAg_ldap_title(xtitle);
				item.setAg_ldap_grade(xgrade);
				item.setAg_ldap_numen(xemployeenumber);
				if (verifNumber(xannu_synchro))
					item.setAg_ldap_synchro(Integer.parseInt(xannu_synchro));

				if (verifNumber(xannu_code)) {
					item.setAg_code(Integer.parseInt(xannu_code));
					item.setAg_ldap_annu_code(Integer.parseInt(xannu_code));
					item.setAg_ldap_synchro(1);
					String qry = "select * from  agent    where        ag_code= "
							+ xannu_code;
					System.out
							.println("get allagent qry:"
									+ qry
									+ "\n*******************************************************");
					try {
						Statement st = conn.createStatement();
						ResultSet rs = st.executeQuery(qry);
						while (rs.next()) {

							item = fgetOneAgent(rs, item);
						}
					} catch (SQLException e) {
						System.out.println(e.getMessage());
						userinfo.setError_message("Erreur: Catalog "
								+ e.getMessage() + " " + qry);
						e.printStackTrace();
					}
				}

				if (verifNumber(xannu_profil_ddt))
					item.setAg_ldap_profil_ddt(Integer
							.parseInt(xannu_profil_ddt));

				if (verifNumber(xannu_profil_annu))
					item.setAg_ldap_profil_annu(Integer
							.parseInt(xannu_profil_annu));
				// si annucode estexite dans ldap on considere que la personnes
				// est synchro ldap

				item.setAg_ldap_dn(xdn);
				alist.add(item);
				status = 0;
			}

			/* Done, so disconnect */
			if ((ld != null) && ld.isConnected()) {
				ld.disconnect();
			}
			// ---------------------------------------------------------------------------
		} catch (LDAPException e) {
			System.out.println("Error ldap: " + e.toString());
			// Exception e = new Exception("The operation is unknown.");
		}
		// sort resultat ldap
		// _____________________________________________________________
		int nb = alist.size();
		System.out.println("Sort ldap: " + nb);

		ItemAnnuaire[] items = new ItemAnnuaire[nb];
		for (int i = 0; i < nb; i++) {
			ItemAnnuaire E = (ItemAnnuaire) alist.get(i);
			items[i] = E;
		}
		Arrays.sort(items);
		ArrayList slist = new ArrayList();
		for (int i = 0; i < nb; i++) {
			ItemAnnuaire E = (ItemAnnuaire) items[i];
			slist.add(E);
		}
		return slist;
	}

	public ArrayList getLdapAsa(String ldap, String ldapdm, String ldappw,
			String ldapbasedn, HttpServletRequest request) {
		ArrayList alist = new ArrayList();
		System.out
				.println("------------------------ recherche ldap dispatcher ------------------------------");
		System.out.println("" + ldap + ldapdm + ldappw + ldapbasedn);
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
			String MY_FILTER = "AnnuProfilAsa=*";
			// String MY_FILTER = "(|(uid=alain.grainville)(uid=kgrainville))";
			System.out.println("search" + MY_FILTER);
			String[] attrNames = { "annu-code", "annu-profil-ddt",
					"annu-profil-annu", "annu-synchro", "datenaissance",
					"mail", "uid", "cn", "sn", "givenName", "telephonenumber",
					"grade", "employeenumber", "AnnuProfilAsa",
					"AnnuOtpDateSignature" };
			LDAPSearchResults res = ld.search(ldapbasedn, 2, MY_FILTER, null,
					false);
			int n = 0;
			String xcn = "";
			String xsn = "";
			String xgivenName = "";
			String xmail = "";
			String xtel = "";
			String xtitle = "";
			String xuid = "";
			String xgrade = "";
			String xemployeenumber = "";
			String xannu_code = "";
			String xannu_synchro = "";
			String xannu_profil_ddt = "";
			String xannu_profil_annu = "";
			String xannu_profil_asa = "";
			String xdate = "";
			while (res.hasMoreElements()) {
				// System.out.println("search more");
				findEntry = null;
				findEntry = res.next();
				xdn = findEntry.getDN();
				// System.out.println("dn:"+xdn);
				LDAPAttributeSet findAttrs = findEntry.getAttributeSet();
				Enumeration enumAttrs = findAttrs.getAttributes();
				/* Loop on attributes */
				xcn = "";
				xsn = "";
				xgivenName = "";
				xmail = "";
				xtel = "";
				xtitle = "";
				xuid = "";
				xgrade = "";
				xemployeenumber = "";
				xannu_code = "";
				xannu_synchro = "";
				xannu_profil_ddt = "";
				xannu_profil_annu = "";
				xannu_profil_asa = "";
				xdate = "";
				ItemAnnuaire item = new ItemAnnuaire();
				while (enumAttrs.hasMoreElements()) {
					LDAPAttribute anAttr = (LDAPAttribute) enumAttrs
							.nextElement();
					String attrName = anAttr.getName();
					// System.out.println("attrname:"+attrName);
					/* Loop on values for this attribute */
					Enumeration enumVals = anAttr.getStringValues();

					while (enumVals.hasMoreElements()) {
						String aVal = (String) enumVals.nextElement();
						// System.out.println("attrname:"+attrName+" "+aVal);
						if (attrName.equals("cn"))
							xcn = aVal;
						else if (attrName.equalsIgnoreCase("sn"))
							item.setAg_ldap_nom(aVal);
						else if (attrName.equalsIgnoreCase("givenName"))
							item.setAg_ldap_prenom(aVal);
						else if (attrName.equalsIgnoreCase("uid"))
							item.setAg_ldap_uid(aVal);
						else if (attrName.equalsIgnoreCase("title"))
							item.setAg_ldap_title(aVal);
						else if (attrName.equalsIgnoreCase("mail"))
							item.setAg_ldap_mail(aVal);
						else if (attrName.equalsIgnoreCase("telephonenumber"))
							xtel = aVal;
						else if (attrName.equalsIgnoreCase("grade"))
							item.setAg_ldap_grade(aVal);
						else if (attrName.equalsIgnoreCase("employeenumber"))
							item.setAg_ldap_numen(aVal);
						else if (attrName.equalsIgnoreCase("annu-code"))
							xannu_code = aVal;
						else if (attrName.equalsIgnoreCase("annu-synchro"))
							xannu_synchro = aVal;
						else if (attrName.equalsIgnoreCase("annu-profil-ddt"))
							xannu_profil_ddt = aVal;
						else if (attrName.equalsIgnoreCase("annu-profil-annu"))
							xannu_profil_annu = aVal;
						else if (attrName.equalsIgnoreCase("annuprofilasa"))
							item.setAg_ldap_profil_asa(aVal);
						else if (attrName
								.equalsIgnoreCase("annuotpdatesignature"))
							item.setAg_ldap_otpdatesignature(aVal);
						else if (attrName.equalsIgnoreCase("annuotpserie"))
							item.setAg_ldap_otpserie(aVal);
					}
				}

				if (verifNumber(xannu_synchro))
					item.setAg_ldap_synchro(Integer.parseInt(xannu_synchro));

				if (verifNumber(xannu_code)) {
					item.setAg_code(Integer.parseInt(xannu_code));
					item.setAg_ldap_synchro(1);
				}
				if (verifNumber(xannu_profil_ddt))
					item.setAg_ldap_profil_ddt(Integer
							.parseInt(xannu_profil_ddt));

				if (verifNumber(xannu_profil_annu))
					item.setAg_ldap_profil_annu(Integer
							.parseInt(xannu_profil_annu));
				// si annucode estexite dans ldap on considere que la personnes
				// est synchro ldap

				item.setAg_ldap_dn(xdn);
				alist.add(item);
				status = 0;
			}

			/* Done, so disconnect */
			if ((ld != null) && ld.isConnected()) {
				ld.disconnect();
			}
			// ---------------------------------------------------------------------------
		} catch (LDAPException e) {
			System.out.println("Error ldap: " + e.toString());
			// Exception e = new Exception("The operation is unknown.");
		}
		// sort resultat ldap
		// _____________________________________________________________
		int nb = alist.size();
		System.out.println("Sort ldap: " + nb);

		ItemAnnuaire[] items = new ItemAnnuaire[nb];
		for (int i = 0; i < nb; i++) {
			ItemAnnuaire E = (ItemAnnuaire) alist.get(i);
			items[i] = E;
		}

		Arrays.sort(items);
		ArrayList slist = new ArrayList();
		for (int i = 0; i < nb; i++) {
			ItemAnnuaire E = (ItemAnnuaire) items[i];
			slist.add(E);
		}
		return slist;
	}

	public ArrayList getLdapUid(String ldap, String ldapdm, String ldappw,
			String ldapbasedn, HttpServletRequest request) {
		ArrayList alist = new ArrayList();
		System.out
				.println("------------------------ recherche ldap dispatcher ------------------------------");
		System.out.println("" + ldap + ldapdm + ldappw + ldapbasedn);
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
			String MY_FILTER = "uid=" + request.getParameter("uid");
			// String MY_FILTER = "(|(uid=alain.grainville)(uid=kgrainville))";
			System.out.println("search" + MY_FILTER);
			String[] attrNames = { "annu-code", "annu-profil-ddt",
					"annu-profil-annu", "annu-synchro", "datenaissance",
					"mail", "uid", "cn", "sn", "givenName", "telephonenumber",
					"grade", "employeenumber", "AnnuProfilAsa" };
			LDAPSearchResults res = ld.search(ldapbasedn, 2, MY_FILTER, null,
					false);
			int n = 0;
			String xcn = "";
			String xsn = "";
			String xgivenName = "";
			String xmail = "";
			String xtel = "";
			String xtitle = "";
			String xuid = "";
			String xgrade = "";
			String xemployeenumber = "";
			String xannu_code = "";
			String xannu_synchro = "";
			String xannu_profil_ddt = "";
			String xannu_profil_annu = "";
			String xannu_profil_asa = "";
			String xdate = "";
			while (res.hasMoreElements()) {
				// System.out.println("search more");
				findEntry = null;
				findEntry = res.next();
				xdn = findEntry.getDN();
				// System.out.println("dn:"+xdn);
				LDAPAttributeSet findAttrs = findEntry.getAttributeSet();
				Enumeration enumAttrs = findAttrs.getAttributes();
				/* Loop on attributes */
				xcn = "";
				xsn = "";
				xgivenName = "";
				xmail = "";
				xtel = "";
				xtitle = "";
				xuid = "";
				xgrade = "";
				xemployeenumber = "";
				xannu_code = "";
				xannu_synchro = "";
				xannu_profil_ddt = "";
				xannu_profil_annu = "";
				xannu_profil_asa = "";
				xdate = "";
				ItemAnnuaire item = new ItemAnnuaire();
				while (enumAttrs.hasMoreElements()) {
					LDAPAttribute anAttr = (LDAPAttribute) enumAttrs
							.nextElement();
					String attrName = anAttr.getName();
					// System.out.println("attrname:"+attrName);
					/* Loop on values for this attribute */
					Enumeration enumVals = anAttr.getStringValues();

					while (enumVals.hasMoreElements()) {
						String aVal = (String) enumVals.nextElement();
						// System.out.println("attrname:"+attrName+" "+aVal);
						if (attrName.equals("cn"))
							xcn = aVal;
						else if (attrName.equalsIgnoreCase("sn"))
							xsn = aVal;
						else if (attrName.equalsIgnoreCase("givenName"))
							xgivenName = aVal;
						else if (attrName.equalsIgnoreCase("uid"))
							xuid = aVal;
						else if (attrName.equalsIgnoreCase("title"))
							xtitle = aVal;
						else if (attrName.equalsIgnoreCase("mail"))
							xmail = aVal;
						else if (attrName.equalsIgnoreCase("telephonenumber"))
							xtel = aVal;
						else if (attrName.equalsIgnoreCase("grade"))
							xgrade = aVal;
						else if (attrName.equalsIgnoreCase("employeenumber"))
							xemployeenumber = aVal;
						else if (attrName.equalsIgnoreCase("annu-code"))
							xannu_code = aVal;
						else if (attrName.equalsIgnoreCase("annu-synchro"))
							xannu_synchro = aVal;
						else if (attrName.equalsIgnoreCase("annu-profil-ddt"))
							xannu_profil_ddt = aVal;
						else if (attrName.equalsIgnoreCase("annuotpserie"))
							item.setAg_ldap_otpserie(aVal);
						else if (attrName.equalsIgnoreCase("annu-profil-annu"))
							xannu_profil_annu = aVal;
						else if (attrName.equalsIgnoreCase("annuprofilasa"))
							xannu_profil_asa = aVal;
					}
				}

				item.setAg_ldap_profil_asa(xannu_profil_asa);
				item.setAg_ldap_mail(xmail);
				item.setAg_ldap_uid(xuid);
				item.setAg_ldap_nom(xsn);
				item.setAg_ldap_prenom(xgivenName);
				item.setAg_ldap_title(xtitle);
				item.setAg_ldap_grade(xgrade);
				item.setAg_ldap_numen(xemployeenumber);
				if (verifNumber(xannu_synchro))
					item.setAg_ldap_synchro(Integer.parseInt(xannu_synchro));

				if (verifNumber(xannu_code)) {
					item.setAg_code(Integer.parseInt(xannu_code));
					item.setAg_ldap_synchro(1);
				}
				if (verifNumber(xannu_profil_ddt))
					item.setAg_ldap_profil_ddt(Integer
							.parseInt(xannu_profil_ddt));

				if (verifNumber(xannu_profil_annu))
					item.setAg_ldap_profil_annu(Integer
							.parseInt(xannu_profil_annu));
				// si annucode estexite dans ldap on considere que la personnes
				// est synchro ldap

				item.setAg_ldap_dn(xdn);
				alist.add(item);
				status = 0;
			}

			/* Done, so disconnect */
			if ((ld != null) && ld.isConnected()) {
				ld.disconnect();
			}
			// ---------------------------------------------------------------------------
		} catch (LDAPException e) {
			System.out.println("Error ldap: " + e.toString());
			// Exception e = new Exception("The operation is unknown.");
		}
		// sort resultat ldap
		// _____________________________________________________________
		int nb = alist.size();
		System.out.println("Sort ldap: " + nb);

		ItemAnnuaire[] items = new ItemAnnuaire[nb];
		for (int i = 0; i < nb; i++) {
			ItemAnnuaire E = (ItemAnnuaire) alist.get(i);
			items[i] = E;
		}
		Arrays.sort(items);
		ArrayList slist = new ArrayList();
		for (int i = 0; i < nb; i++) {
			ItemAnnuaire E = (ItemAnnuaire) items[i];
			slist.add(E);
		}
		return slist;
	}

	public void getLdapByDn(LDAPConnection ld, String ldap, String ldapdm,
			String ldappw, String ldapbasedn, HttpServletRequest request,
			String uid, ItemAnnuaire item1) {
		ItemAnnuaire item = item1;
		ArrayList alist = new ArrayList();
		System.out
				.println("------------------------                 recherche ldap by dn           ------------------------------");
		System.out.println("" + ldap + ldapdm + ldappw + ldapbasedn);
		HttpSession session = request.getSession(true);

		LDAPEntry findEntry = null;
		int status = -1;
		String xdn = "";
		try {

			String MY_FILTER = "uid=" + uid;
			// String MY_FILTER = "(|(uid=alain.grainville)(uid=kgrainville))";
			System.out.println("search" + MY_FILTER);
			String[] attrNames = { "annu-code", "annu-profil-ddt",
					"userPassword", "annu-profil-annu", "annu-synchro",
					"datenaissance", "mail", "uid", "cn", "sn", "givenName",
					"telephonenumber", "grade", "employeenumber" };
			LDAPSearchResults res = ld.search(ldapbasedn, 2, MY_FILTER,
					attrNames, false);
			int n = 0;
			String xcn = "";
			String xsn = "";
			String xgivenName = "";
			String xmail = "";
			String xtel = "";
			String xtitle = "";
			String xuid = "";
			String xgrade = "";
			String xdatenaiss = "";
			String xemployeenumber = "";
			String xannu_code = "";
			String xannu_synchro = "";
			String xannu_profil_ddt = "";
			String xuserpassword = "";
			String xannu_profil_annu = "";
			String xdate = "";
			while (res.hasMoreElements()) {
				// System.out.println("search more");
				findEntry = null;
				findEntry = res.next();
				xdn = findEntry.getDN();
				// System.out.println("dn:"+xdn);
				LDAPAttributeSet findAttrs = findEntry.getAttributeSet();
				Enumeration enumAttrs = findAttrs.getAttributes();
				/* Loop on attributes */
				xcn = "";
				xsn = "";
				xgivenName = "";
				xmail = "";
				xtel = "";
				xtitle = "";
				xuid = "";
				xgrade = "";
				xdatenaiss = "";
				xemployeenumber = "";
				xuserpassword = "";
				xannu_code = "";
				xannu_synchro = "";
				xannu_profil_ddt = "";
				xannu_profil_annu = "";
				xdate = "";
				while (enumAttrs.hasMoreElements()) {
					LDAPAttribute anAttr = (LDAPAttribute) enumAttrs
							.nextElement();
					String attrName = anAttr.getName();
					// System.out.println("attrname:"+attrName);
					/* Loop on values for this attribute */
					Enumeration enumVals = anAttr.getStringValues();
					while (enumVals.hasMoreElements()) {
						String aVal = (String) enumVals.nextElement();
						// System.out.println("attrname:"+attrName+" "+aVal);
						if (attrName.equals("cn"))
							xcn = aVal;
						else if (attrName.equalsIgnoreCase("sn"))
							xsn = aVal;
						else if (attrName.equalsIgnoreCase("givenName"))
							xgivenName = aVal;
						else if (attrName.equalsIgnoreCase("uid"))
							xuid = aVal;
						else if (attrName.equalsIgnoreCase("title"))
							xtitle = aVal;
						else if (attrName.equalsIgnoreCase("mail"))
							xmail = aVal;
						else if (attrName.equalsIgnoreCase("telephonenumber"))
							xtel = aVal;
						else if (attrName.equalsIgnoreCase("userpassword"))
							xuserpassword = aVal;
						else if (attrName.equalsIgnoreCase("grade"))
							xgrade = aVal;
						else if (attrName.equalsIgnoreCase("datenaissance"))
							xdatenaiss = aVal;
						else if (attrName.equalsIgnoreCase("employeenumber"))
							xemployeenumber = aVal;
						else if (attrName.equalsIgnoreCase("annu-code"))
							xannu_code = aVal;
						else if (attrName.equalsIgnoreCase("annu-synchro"))
							xannu_synchro = aVal;
						else if (attrName.equalsIgnoreCase("annu-profil-ddt"))
							xannu_profil_ddt = aVal;
						else if (attrName.equalsIgnoreCase("annu-profil-annu"))
							xannu_profil_annu = aVal;
					}
				}
				// ItemAnnuaire item = new ItemAnnuaire();
				item.setAg_ldap_mail(xmail);
				item.setAg_ldap_uid(xuid);
				item.setAg_ldap_nom(xsn);
				item.setAg_ldap_prenom(xgivenName);
				item.setAg_ldap_title(xtitle);
				item.setAg_ldap_grade(xgrade);
				item.setAg_ldap_numen(xemployeenumber);
				item.setAg_ldap_datenaiss(xdatenaiss);
				item.setAg_ldap_pw(xuserpassword);
				if (verifNumber(xannu_synchro))
					item.setAg_ldap_synchro(Integer.parseInt(xannu_synchro));

				if (verifNumber(xannu_code)) {
					item.setAg_code(Integer.parseInt(xannu_code));
					item.setAg_ldap_synchro(1);
				}
				if (verifNumber(xannu_profil_ddt))
					item.setAg_ldap_profil_ddt(Integer
							.parseInt(xannu_profil_ddt));

				if (verifNumber(xannu_profil_annu))
					item.setAg_ldap_profil_annu(Integer
							.parseInt(xannu_profil_annu));
				// si annucode estexite dans ldap on considere que la personnes
				// est synchro ldap

				item.setAg_ldap_dn(xdn);
				alist.add(item);
				status = 0;
			}

			/* Done, so disconnect */
			// if ( (ld != null) && ld.isConnected() ) { ld.disconnect(); }
			// ---------------------------------------------------------------------------
		} catch (LDAPException e) {
			System.out.println("Error ldap: " + e.toString());
			// Exception e = new Exception("The operation is unknown.");
		}
		// sort resultat ldap
		// _____________________________________________________________
		int nb = alist.size();
		System.out.println("Sort ldap: " + nb);

		ItemAnnuaire[] items = new ItemAnnuaire[nb];
		for (int i = 0; i < nb; i++) {
			ItemAnnuaire E = (ItemAnnuaire) alist.get(i);
			items[i] = E;
		}
		Arrays.sort(items);
		ArrayList slist = new ArrayList();
		for (int i = 0; i < nb; i++) {
			ItemAnnuaire E = (ItemAnnuaire) items[i];
			slist.add(E);
		}
		// return slist;
	}

	public void setLdap(java.sql.Connection conn, String ldap, String ldapdm,
		String ldappw, String ldapbasedn, UserInfo userinfo, HttpServletRequest request) {
		System.out.println("***************");
		System.out.println("Catalog.Setldap");
		System.out.println("***************");
		/* Specify the entry to be modified. */
		String ENTRYDN = request.getParameter("ag_ldap_dn");
		String uid = request.getParameter("ag_ldap_uid");
		String MY_FILTER = "(|(uid="+request.getParameter("ag_ldap_uid")+ ")";
		MY_FILTER=MY_FILTER+"(mail="+request.getParameter("mail")+")"; 
		MY_FILTER=MY_FILTER+"(mailalternateaddress="+request.getParameter("mail")+")"; 
		MY_FILTER=MY_FILTER+"(mailequivalentaddress="+request.getParameter("mail")+"))"; 
		//
        
		/* Create a new set of modifications. */
		LDAPModificationSet mods = new LDAPModificationSet();

		/* Add each change to an attribute to the set of modifications. */
		if (request.getParameter("mail") != null) {
			LDAPAttribute attrEmail = new LDAPAttribute("mail",
					request.getParameter("mail"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		if (request.getParameter("telephone") != null) {
			LDAPAttribute attrEmail = new LDAPAttribute("telephoneNumber",
					request.getParameter("telephone"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		if (request.getParameter("grade") != null
				&& request.getParameter("grade").length() > 0) {
			System.out.println("grade lg:"
					+ request.getParameter("grade").length());
			LDAPAttribute attrEmail = new LDAPAttribute("grade",
					request.getParameter("grade"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		
		if (request.getParameter("numen") != null
				&& request.getParameter("numen").length() > 0) {
			System.out.println("Catalog.Setldap: attention modif du numen --------------------------");
			LDAPAttribute attrEmail = new LDAPAttribute("employeenumber",
					request.getParameter("numen"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		
		/*if (request.getParameter("nomdiv") != null
				&& request.getParameter("nomdiv").length() > 0) {
			LDAPAttribute attrEmail = new LDAPAttribute("annu-division",
					request.getParameter("nomdiv"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}*/
		/*if (request.getParameter("nomser") != null
				&& request.getParameter("nomser").length() > 0) {
			LDAPAttribute attrEmail = new LDAPAttribute("annu-nompabx",
					request.getParameter("nomser"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}*/
		if (request.getParameter("passwd") != null
				&& request.getParameter("passwd").length() > 0) {
			LDAPAttribute attrEmail = new LDAPAttribute("userpassword",
					request.getParameter("passwd"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		
		if (request.getParameter("ldap_profil_ddt") != null
				&& Integer.parseInt(request.getParameter("ldap_profil_ddt"))  > 0) {
			System.out.println("maj ldap: " +  request.getParameter("ldap_profil_ddt"));
			LDAPAttribute attrEmail = new LDAPAttribute("annu-profil-ddt",
					request.getParameter("ldap_profil_ddt"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		if (request.getParameter("ldap_profil_annu") != null
				&& Integer.parseInt(request.getParameter("ldap_profil_annu")) > 0) {
			LDAPAttribute attrEmail = new LDAPAttribute("annu-profil-annu",
					request.getParameter("ldap_profil_annu"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		if (request.getParameter("code") != null
				&& Integer.parseInt(request.getParameter("code")) > 0) {
			LDAPAttribute attrEmail = new LDAPAttribute("annu-code",
					request.getParameter("code"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		
	 
		if (request.getParameter("profilasa") != null
				&& request.getParameter("profilasa").length() > 0) {
			LDAPAttribute attrEmail = new LDAPAttribute("AnnuProfilAsa",
					request.getParameter("profilasa"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		if (request.getParameter("otpserie") != null
				&& request.getParameter("otpserie").length() > 0) {
			LDAPAttribute attrEmail = new LDAPAttribute("AnnuOtpSerie",
					request.getParameter("otpserie"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		// LDAPAttribute attrDesc = new LDAPAttribute( "description",
		// "This entry was modified with the modattrs program" );
		// mods.add( LDAPModification.ADD, attrDesc );
		// LDAPAttribute attrPhone = new
		// LDAPAttribute("telephoneNumber");
		// mods.add( LDAPModification.DELETE, attrPhone );
		/* Connect to the server and modify the entry. */
		LDAPConnection ld = null;
		int status = -1;

		try {
			/* Connect to the server. */
			ld = new LDAPConnection();
			ld.connect(ldap, 389);
			// System.out.println( "auth" );
			/* Authenticate to the server as directory manager */
			ld.authenticate(3, ldapdm, ldappw);
			// test doublon de mail
			// --------------------
			LDAPEntry findEntry=null;
			System.out.println("search :"+MY_FILTER);
	        String[] attrNames = {  "mail"  ,"uid"  };
	        LDAPSearchResults res = ld.search( ldapbasedn,2,MY_FILTER, attrNames, false );
	        String xmessage=  "Modif impossible: doublon sur uid, mail, alternate ou equiv:<br>";
	        xmessage=xmessage+"----------------------------------------------------------------------------------------------<br>";
	        String xdn="";
	        userinfo.setError_message("Ajout impossible,doublon sur uid ou mail:" );
	        int n=0;
	        while ( res.hasMoreElements() ) {
	            n++;
	            findEntry = res.next();
	            xdn=findEntry.getDN();
	            xmessage=xmessage+xdn+"<br>";
	            System.out.println("xdn :"+xdn);
	        }
	        if (n==0) {
	        	System.out.println("pas de maj ldap");
	        	userinfo.setError_message("modif mysql seulement : compte non synchronisé avec annuaire ldap");
	            
	        }
	        if (n>1) {
	        	 
	            System.out.println("Ajout impossible: doublons");
	            userinfo.setError_message(xmessage);
	            userinfo.setError_message("pas de maj possibe, doublons sur adresse mail");
	        }
	        if (n==1) {
	        	System.out.println("Modif mail possible zero doublons");
	        	userinfo.setError_message("ok");
			System.out.println("setldap modify:" + ENTRYDN);
			/* Now modify the entry in the directory */
			ld.modify(ENTRYDN, mods);
			System.out.println("Successfully modified the entry.");
			/* on modifie aussui la table agent */
			Statement st = conn.createStatement();
			String qry = "update " + request.getParameter("table") + " set "
					+ " ag_ldap_nom    = '" + request.getParameter("nom")
					+ "' ," + " ag_ldap_prenom = '"
					+ request.getParameter("prenom") + "' ,"
					+ " ag_ldap_grade  = '" + request.getParameter("grade")
					+ "' ," + " ag_ldap_mail   = '"
					+ request.getParameter("mail") + "' ,"
					+ " ag_ldap_numen   = '" + request.getParameter("numen")
					+ "' ," + " ag_ldap_profil_ddt    = '"
					+ request.getParameter("ldap_profil_ddt") + "' ,"
					+ " ag_ldap_profil_annu   = '"
					+ request.getParameter("ldap_profil_annu")
					+ "'  where ag_code=" + request.getParameter("code");
			System.out.println("qry:" + qry);
			st.executeUpdate(qry);
		}
		}

		catch (SQLException e) {
			System.out.println("Error: pb sql");
		}

		catch (LDAPException e) {

			if (e.getLDAPResultCode() == LDAPException.NO_SUCH_OBJECT)
				System.out.println("Error: No such entry");

			else if (e.getLDAPResultCode() == LDAPException.INSUFFICIENT_ACCESS_RIGHTS)
				System.out.println("Error: Insufficient rights");

			else if (e.getLDAPResultCode() == LDAPException.ATTRIBUTE_OR_VALUE_EXISTS)
				System.out.println("Error: Attribute or value exists");

			else
				System.out.println("Error: " + e.toString());
		}

		/* Disconnect when done. */
		if ((ld != null) && ld.isConnected()) {
			try {
				ld.disconnect();
			} catch (LDAPException e) {
				System.out.println("Error: " + e.toString());
			}
		}

	}

	public void setLdapotp(java.sql.Connection conn, String ldap,
			String ldapdm, String ldappw, String ldapbasedn,
			HttpServletRequest request) {
		/* Specify the entry to be modified. */
		String ENTRYDN = request.getParameter("ag_ldap_dn");

		/* Create a new set of modifications. */
		LDAPModificationSet mods = new LDAPModificationSet();

		/* Add each change to an attribute to the set of modifications. */
		if (request.getParameter("mail") != null) {
			LDAPAttribute attrEmail = new LDAPAttribute("mail",
					request.getParameter("mail"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		if (request.getParameter("tel") != null) {
			LDAPAttribute attrEmail = new LDAPAttribute("telephoneNumber",
					request.getParameter("tel"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		if (request.getParameter("grade") != null
				&& request.getParameter("grade").length() > 0) {
			System.out.println("grade lg:"
					+ request.getParameter("grade").length());
			LDAPAttribute attrEmail = new LDAPAttribute("grade",
					request.getParameter("grade"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		if (request.getParameter("numen") != null
				&& request.getParameter("numen").length() > 0) {
			LDAPAttribute attrEmail = new LDAPAttribute("employeenumber",
					request.getParameter("numen"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		if (request.getParameter("passwd") != null
				&& request.getParameter("passwd").length() > 0) {
			LDAPAttribute attrEmail = new LDAPAttribute("userpassword",
					request.getParameter("passwd"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		if (request.getParameter("ldap_profil_ddt") != null
				&& Integer.parseInt(request.getParameter("ldap_profil_ddt"))  > 0) {
			System.out.println("maj ldap: " +  request.getParameter("ldap_profil_ddt"));
			LDAPAttribute attrEmail = new LDAPAttribute("annu-profil-ddt",
					request.getParameter("ldap_profil_ddt"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		if (request.getParameter("ldap_profil_annu") != null
				&& Integer.parseInt(request.getParameter("ldap_profil_annu")) > 0) {
			LDAPAttribute attrEmail = new LDAPAttribute("annu-profil-annu",
					request.getParameter("ldap_profil_annu"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		if (request.getParameter("code") != null
				&& Integer.parseInt(request.getParameter("code")) > 0) {
			LDAPAttribute attrEmail = new LDAPAttribute("annu-code",
					request.getParameter("code"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		if (request.getParameter("profilasa") != null
				&& request.getParameter("profilasa").length() > 0) {
			LDAPAttribute attrEmail = new LDAPAttribute("AnnuProfilAsa",
					request.getParameter("profilasa"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		if (request.getParameter("otpserie") != null
				&& request.getParameter("otpserie").length() > 0) {
			LDAPAttribute attrEmail = new LDAPAttribute("AnnuOtpSerie",
					request.getParameter("otpserie"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		// LDAPAttribute attrDesc = new LDAPAttribute( "description",
		// "This entry was modified with the modattrs program" );
		// mods.add( LDAPModification.ADD, attrDesc );
		// LDAPAttribute attrPhone = new
		// LDAPAttribute("telephoneNumber");
		// mods.add( LDAPModification.DELETE, attrPhone );
		/* Connect to the server and modify the entry. */
		LDAPConnection ld = null;
		int status = -1;

		try {
			/* Connect to the server. */
			ld = new LDAPConnection();
			ld.connect(ldap, 389);
			// System.out.println( "auth" );
			/* Authenticate to the server as directory manager */
			ld.authenticate(3, ldapdm, ldappw);
			System.out.println("setldap modify:" + ENTRYDN);
			/* Now modify the entry in the directory */
			ld.modify(ENTRYDN, mods);
			System.out.println("Successfully modified the entry.");
			/* on modifie aussui la table agent */

		}

		catch (LDAPException e) {

			if (e.getLDAPResultCode() == LDAPException.NO_SUCH_OBJECT)
				System.out.println("Error: No such entry");

			else if (e.getLDAPResultCode() == LDAPException.INSUFFICIENT_ACCESS_RIGHTS)
				System.out.println("Error: Insufficient rights");

			else if (e.getLDAPResultCode() == LDAPException.ATTRIBUTE_OR_VALUE_EXISTS)
				System.out.println("Error: Attribute or value exists");

			else
				System.out.println("Error: " + e.toString());
		}

		/* Disconnect when done. */
		if ((ld != null) && ld.isConnected()) {
			try {
				ld.disconnect();
			} catch (LDAPException e) {
				System.out.println("Error: " + e.toString());
			}
		}

	}

	public void SupLdap(String ldap, String ldapdm, String ldappw,
			String ldapbasedn, HttpServletRequest request) {
		/* Specify the entry to be modified. */
		String ENTRYDN = request.getParameter("dn");
		LDAPConnection ld = null;
		int status = -1;
		try {
			ld = new LDAPConnection();
			ld.connect(ldap, 389);
			ld.authenticate(3, ldapdm, ldappw);
			System.out.println("authenticate .... ok");
			ld.delete(ENTRYDN);
			System.out.println("Delete :" + ENTRYDN);
		} catch (LDAPException e) {
			if (e.getLDAPResultCode() == LDAPException.NO_SUCH_OBJECT)
				System.out.println("Error: No such entry");

			else if (e.getLDAPResultCode() == LDAPException.INSUFFICIENT_ACCESS_RIGHTS)
				System.out.println("Error: Insufficient rights");

			else if (e.getLDAPResultCode() == LDAPException.ATTRIBUTE_OR_VALUE_EXISTS)
				System.out.println("Error: Attribute or value exists");

			else
				System.out.println("Error: " + e.toString());
		}

		/* Disconnect when done. */
		if ((ld != null) && ld.isConnected()) {
			try {
				ld.disconnect();
			} catch (LDAPException e) {
				System.out.println("Error: " + e.toString());
			}
		}

	}

	public void AddLdapPartage(java.sql.Connection conn, UserInfo userinfo,
			HttpServletRequest request, String ldappwS) throws IOException {
		/* Specify the entry to be modified. */
		String user=userinfo.getMail();
		String uid = request.getParameter("uid");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String mail = request.getParameter("mail");
		String dtn = request.getParameter("dtn");
		int pa_code = userinfo.getPa_code();
		String qry = "select * from partage where pa_code = " + pa_code;
		String partage = "";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				partage = rs.getString("pa_nom");
			}
			System.out
					.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx:"
							+ partage);
			qry = "insert into af_partage_ldap (af_pa_code,af_uid_ldap,af_nom_ldap,af_prenom_ldap,af_mail_ldap,af_dtn_ldap) values ("
					+ pa_code
					+ ",'"
					+ uid
					+ "','"
					+ nom
					+ "','"
					+ prenom
					+ "','" + mail + "','" + dtn + "');";
			System.out
					.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx:"
							+ qry);
			st.executeUpdate(qry);
			rs.close();
			st.close();
			// ------------------------------------------- add lien .ftp
			// ssh1="ssh root@scribe.in.ac-reunion.fr \" /usr/share/eole/x_add_user_partage "
			// + uid + " " + nom + " " + prenom + " " + mail + " " + dtn + " " +
			// partage + "  \"   ;" ;
			String ssh1 = "ssh root@scribe.in.ac-reunion.fr \" /usr/share/eole/x_add_user_partage  "
			+ user
			+ " " 
			+ uid
			+ " "
			+ nom
			+ " "
			+ prenom
			+ " "
			+ mail
			+ " "
			+ dtn + " " + partage + "  \"   ;";
			
			
			
			
			System.out.println("------------------ssh x_add_user_partage : "
					+ ssh1);
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime
					.exec(new String[] { "/bin/sh", "-c", ssh1 });
			DataInputStream data_in = new DataInputStream(
					process.getInputStream());
			String line_in = null;
			System.out
					.println("------------------fin x_add_user_admin:" + ssh1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			userinfo.setError_message("Erreur: Catalog " + e.getMessage() + " "
					+ qry);
			e.printStackTrace();
		} finally {
		}

	}

	public void setLdapOtp(java.sql.Connection conn, String ldap,
			String ldapdm, String ldappw, String ldapbasedn,
			HttpServletRequest request) {
		/* Specify the entry to be modified. */
		String ENTRYDN = request.getParameter("dn");
		ListeParam(request);
		/* Create a new set of modifications. */
		LDAPModificationSet mods = new LDAPModificationSet();

		/* Add each change to an attribute to the set of modifications. */
		if (request.getParameter("profilasa") != null) {
			LDAPAttribute attrEmail = new LDAPAttribute("annuprofilasa",
					request.getParameter("profilasa"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		if (request.getParameter("otpserie") != null) {
			LDAPAttribute attrEmail = new LDAPAttribute("annuotpserie",
					request.getParameter("otpserie"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		if (request.getParameter("date") != null) {
			LDAPAttribute attrEmail = new LDAPAttribute("annuotpdatesignature",
					request.getParameter("date"));
			System.out.println("setldap modify annuotpdatesignature:"
					+ request.getParameter("date"));
			// mods.add( LDAPModification.ADD, attrEmail );
			mods.add(LDAPModification.REPLACE, attrEmail);

		}

		LDAPConnection ld = null;
		int status = -1;

		try {
			/* Connect to the server. */
			ld = new LDAPConnection();
			ld.connect(ldap, 389);
			// System.out.println( "auth" );
			/* Authenticate to the server as directory manager */
			ld.authenticate(3, ldapdm, ldappw);
			System.out.println("setldap modify:" + ENTRYDN);
			/* Now modify the entry in the directory */
			ld.modify(ENTRYDN, mods);
			System.out.println("Successfully modified the entry.");
			/* on modifie aussui la table agent */

		}

		catch (LDAPException e) {

			if (e.getLDAPResultCode() == LDAPException.NO_SUCH_OBJECT)
				System.out.println("Error: No such entry");

			else if (e.getLDAPResultCode() == LDAPException.INSUFFICIENT_ACCESS_RIGHTS)
				System.out.println("Error: Insufficient rights");

			else if (e.getLDAPResultCode() == LDAPException.ATTRIBUTE_OR_VALUE_EXISTS)
				System.out.println("Error: Attribute or value exists");

			else
				System.out.println("Error: " + e.toString());
		}

		/* Disconnect when done. */
		if ((ld != null) && ld.isConnected()) {
			try {
				ld.disconnect();
			} catch (LDAPException e) {
				System.out.println("Error: " + e.toString());
			}
		}

	}

	public void setLdapDiv(java.sql.Connection conn, String ldap,
			String ldapdm, String ldappw, String ldapbasedn,
			HttpServletRequest request) throws LDAPException {
		/* modification annu-division et annu-nompabx pour un agent */
		ArrayList alist = new ArrayList();
		String where_codeagtaff = " and ag_code="+ request.getParameter("code");
		String qry = "select * from  agent,affectation,division,service,bureau,pole LEFT JOIN grade ON (grade.gr_code=agent.ag_ldap_grade)  where "
				+ "   af_ag_code=ag_code   and af_dv_code=dv_code and af_sv_code=sv_code and af_bu_code=bu_code and af_pl_code=pl_code"
				+ where_codeagtaff;
	    qry = "select * from  agent,affectation,division ,bureau,pole  LEFT JOIN service ON ( af_sv_code=sv_code) LEFT JOIN grade ON (grade.gr_code=agent.ag_ldap_grade)  where "
				+ "   af_ag_code=ag_code   and af_dv_code=dv_code and af_bu_code=bu_code and af_pl_code=pl_code"
				+ where_codeagtaff;

		System.out.println("Modiif division annupabx qry:" + qry);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			while (rs.next()) {
				ItemAnnuaire item = new ItemAnnuaire();
				item = fgetOnePole(rs, item);
				item = fgetOneGrade(rs, item);
				item = fgetOneDivision(rs, item);
				item = fgetOneService(rs, item);
				item = fgetOneBureau(rs, item);
				item = fgetOneAgent(rs, item);
				item = fgetOneAffectation(rs, item);
				alist.add(item);
				// System.out.println("setallldap:"+item.getAg_ldap_nom()+" -"
				// +item.getAg_ldap_dn());
			}
			System.out.println("modif ldap:" + alist.size() + " entrees");
			LDAPConnection ld = null;
			ld = new LDAPConnection();
			ld.connect(ldap, 389);
			ld.authenticate(3, ldapdm, ldappw);

			for (int a = 0; a < alist.size(); a++) {
				if (a==0) majldapdel(request, ldap, ldapdm, ldappw, alist, a, ld);
				//majldap(request, ldap, ldapdm, ldappw, alist, a, ld);
				majldapadddiv(request, ldap, ldapdm, ldappw, alist, a, ld);
				majldapaddser(request, ldap, ldapdm, ldappw, alist, a, ld);
			}
			ld.disconnect();
		} catch (SQLException e) {
			System.out.println("Error: pb sql");
		}
		/* Disconnect when done. */
	}

	public void setAllLdap(java.sql.Connection conn, String ldap,
			String ldapdm, String ldappw, String ldapbasedn,
			HttpServletRequest request) throws LDAPException {
		/* Specify the entry to be modified. */
		System.out.println("SETALLldap :");
		String where_codeagtaff = "   ";
		String qry0 = "select distinct ag_code from  agent,affectation,division, " +
				"bureau,pole  LEFT JOIN service ON (af_sv_code=sv_code)  LEFT JOIN grade ON (grade.gr_code=agent.ag_ldap_grade)  where "
				+ "   af_ag_code=ag_code   and af_dv_code=dv_code and af_bu_code=bu_code and af_pl_code=pl_code" + where_codeagtaff ;
		System.out.println("qry0:" + qry0);
		try {
			Statement st0 = conn.createStatement();
			ResultSet rs0 = st0.executeQuery(qry0);
			while (rs0.next()) {

				ArrayList alist = new ArrayList();
				
				String qry = "select * from  agent,affectation,division,bureau,pole " +
						" LEFT JOIN service ON (af_sv_code=sv_code)" +
						" LEFT JOIN grade ON (grade.gr_code=agent.ag_ldap_grade)  where "
						+ "   af_ag_code=ag_code   and af_dv_code=dv_code  and af_bu_code=bu_code and af_pl_code=pl_code and ag_code="
						+ rs0.getString("ag_code");

				System.out.println("qry:" + qry);
				try {
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(qry);
					while (rs.next()) {
						ItemAnnuaire item = new ItemAnnuaire();
						item = fgetOnePole(rs, item);
						item = fgetOneGrade(rs, item);
						item = fgetOneDivision(rs, item);
						item = fgetOneService(rs, item);
						item = fgetOneBureau(rs, item);
						item = fgetOneAgent(rs, item);
						item = fgetOneAffectation(rs, item);
						alist.add(item);
						// System.out.println("setallldap:"+item.getAg_ldap_nom()+" -"
						// +item.getAg_ldap_dn());
					}
					System.out.println("modif ldap:" + alist.size()
							+ " entrees");
					LDAPConnection ld = null;
					ld = new LDAPConnection();
					ld.connect(ldap, 389);
					ld.authenticate(3, ldapdm, ldappw);

					for (int a = 0; a < alist.size(); a++) {
						if (a==0) 
						majldapdel(request, ldap, ldapdm, ldappw, alist, a, ld); // suppression attribut annu-division et annu-nompabx
						majldap(request, ldap, ldapdm, ldappw, alist, a, ld);
						majldapadddiv(request, ldap, ldapdm, ldappw, alist, a,
								ld);
						majldapaddser(request, ldap, ldapdm, ldappw, alist, a,
								ld);
					}
					ld.disconnect();
				}

				catch (SQLException e) {
					System.out.println("Error: pb sql");
				}

			}
		} catch (SQLException e) {
			System.out.println("Error: pb sql");
		}
		/* Disconnect when done. */
	}

	 
	public void setLdapSynchro(java.sql.Connection conn, String ldap,
			String ldapdm, String ldappw, String ldapbasedn,
			HttpServletRequest request) {
		/* Specify the entry to be modified. */
		String ENTRYDN = (String) request.getAttribute("ag_ldap_dn");
		LDAPModificationSet mods = new LDAPModificationSet();
		
		if (request.getParameter("ldap_profil_ddt") != null
				&& Integer.parseInt(request.getParameter("ldap_profil_ddt"))  > 0) {
			System.out.println("maj ldap: " +  request.getParameter("ldap_profil_ddt"));
			LDAPAttribute attrEmail = new LDAPAttribute("annu-profil-ddt",
					request.getParameter("ldap_profil_ddt"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		if (request.getParameter("ldap_profil_annu") != null
				&& Integer.parseInt(request.getParameter("ldap_profil_annu")) > 0) {
			LDAPAttribute attrEmail = new LDAPAttribute("annu-profil-annu",
					request.getParameter("ldap_profil_annu"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		if (request.getParameter("code") != null
				&& Integer.parseInt(request.getParameter("code")) > 0) {
			LDAPAttribute attrEmail = new LDAPAttribute("annu-code",
					request.getParameter("code"));
			mods.add(LDAPModification.REPLACE, attrEmail);
		}
		
		LDAPConnection ld = null;
		int status = -1;

		try {
			ld = new LDAPConnection();
			ld.connect(ldap, 389);
			// System.out.println( "auth" );
			/* Authenticate to the server as directory manager */
			ld.authenticate(3, ldapdm, ldappw);
			System.out.println("set ldapsynchro modify:" + ENTRYDN);
			/* Now modify the entry in the directory */
			ld.modify(ENTRYDN, mods);
			System.out.println("Successfully modified the entry.");
			/* on modifie aussui la table agent */

		}

		catch (LDAPException e) {

			if (e.getLDAPResultCode() == LDAPException.NO_SUCH_OBJECT)
				System.out.println("Error: No such entry");

			else if (e.getLDAPResultCode() == LDAPException.INSUFFICIENT_ACCESS_RIGHTS)
				System.out.println("Error: Insufficient rights");

			else if (e.getLDAPResultCode() == LDAPException.ATTRIBUTE_OR_VALUE_EXISTS)
				System.out.println("Error: Attribute or value exists");

			else
				System.out.println("Error: " + e.toString());
		}

		/* Disconnect when done. */
		if ((ld != null) && ld.isConnected()) {
			try {
				ld.disconnect();
			} catch (LDAPException e) {
				System.out.println("Error: " + e.toString());
			}
		}
	}

	private boolean verifNumber(String number) {
		for (int i = 0; i < number.length(); i++) {
			char nb = number.charAt(i);
			if ((nb != '0') && (nb != '1') && (nb != '2') && (nb != '3')
					&& (nb != '4') && (nb != '5') && (nb != '6') && (nb != '7')
					&& (nb != '8') && (nb != '9'))
				return (false);
		}
		if (number.length() == 0)
			return (false);
		else
			return (true);
	}

	public static String[] readFile(String f) {
		String[] data = null;
		File ft = new File("d:/data/mail/", f);
		if (ft.exists()) {
			System.out.println("Ouverture du fichier");
		} else {
			System.out.println("fichier n'existe pas");
		}
		taille = linesInFile(ft);
		System.out.println(taille);
		data = new String[taille];//
		try {
			BufferedReader in = new BufferedReader(new FileReader(ft));
			String str;
			int i = 0;
			while ((str = in.readLine()) != null) {
				data[i] = str;
				// System.out.println(data[i]);
				i++;
			}
			in.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			data[0] = "erreur";
			return data;
		}
		return data;
	}

	public boolean CharteOtp(String ldap, String ldapdm, String ldappw,
			String ldapbasedn, java.sql.Connection conn,
			HttpServletRequest request, String rep_pdf) {
		String qry = "";
		try {
			String[] champ = null;
			champ = new String[20];//

			ArrayList OneUid = getLdapUid(ldap, ldapdm, ldappw, ldapbasedn,
					request);
			String xbdd = "Tarbes;Gilles;0692281580;gillestarbes@msn.com;Crous";
			ItemAnnuaire A = (ItemAnnuaire) OneUid.get(0);
			xbdd = A.getAg_ldap_nom() + ";" + A.getAg_ldap_prenom() + ";"
					+ A.getAg_ldap_profil_asa() + ";" + A.getAg_ldap_mail()
					+ ";" + A.getAg_ldap_uid() + ";" + A.getAg_ldap_otpserie();
			{
				StringTokenizer tokenizer = new StringTokenizer(xbdd, ";");
				int t = 0;
				int trouve = 0;
				while (tokenizer.hasMoreTokens()) {
					String token = tokenizer.nextToken();
					champ[t] = token;
					t++;
				}
				// PdfReader reader = new
				// PdfReader("d:/data/Mes documents/asa/CHARTEOtp.pdf");
				// PdfReader reader = new
				// PdfReader("/appli/annur/webapps/annur/CHARTEOtpV2.pdf");
				PdfReader reader = new PdfReader(rep_pdf + "CHARTEOtpv3.pdf");
				// PdfReader reader = new PdfReader(rep_pdf+"CHARTEChorus.pdf");

				int n = reader.getNumberOfPages();
				// we create a stamper that will copy the document to a new file
				PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(
						rep_pdf + champ[0] + "_" + champ[1] + ".pdf"));
				// PdfStamper stamp = new PdfStamper(reader, new
				// FileOutputStream("/tmp/"+champ[0]+"_"+champ[1]+".pdf"));
				// PdfStamper stamp = new PdfStamper(reader, new
				// FileOutputStream( champ[0]+"_"+champ[1]+".pdf"));
				System.out.println("nb pages CHARTEOtpv3.pdf :" + n + " "
						+ champ[0] + "_" + champ[1] + ".pdf");
				// adding some metadata
				HashMap moreInfo = new HashMap();
				moreInfo.put("Author", "Alain Grainville");
				stamp.setMoreInfo(moreInfo);
				// adding content to each page
				int i = 0;
				PdfContentByte under;
				PdfContentByte over;
				BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
						BaseFont.WINANSI, BaseFont.EMBEDDED);
				bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD,
						BaseFont.WINANSI, BaseFont.EMBEDDED);
				System.out.println("nb pages :" + n);
				while (i < n) {
					i++;
					// watermark under the existing page
					under = stamp.getUnderContent(i);
					over = stamp.getOverContent(i);
					over.setFontAndSize(bf, 12);
					if (i == 1 || i == 7) {
						// identite
						over.beginText();
						over.setTextMatrix(260, 442);
						over.showText(champ[0] + " " + champ[1]);
						over.endText();
						// telephone

						// adresse electronique
						over.beginText();
						over.setTextMatrix(260, 415);
						over.showText(champ[3]);
						over.endText();
						// login
						over.beginText();
						over.setTextMatrix(260, 375);
						over.showText(champ[4]);
						over.endText();
						// profil
						over.beginText();
						over.setTextMatrix(260, 360);
						over.showText(champ[2]);
						over.endText();
						// nume serie
						over.beginText();
						over.setTextMatrix(260, 345);
						over.showText(champ[5]);
						over.endText();

					}
					if (i == 2 || i == 8) {
						// identite
						over.beginText();
						over.setTextMatrix(140, 200);
						over.showText(champ[0]);
						over.endText();
						// identite prenom
						over.beginText();
						over.setTextMatrix(140, 190);
						over.showText(champ[1]);
						over.endText();

						// Date
						Calendar c = Calendar.getInstance();
						over.beginText();
						over.setFontAndSize(bf, 10);
						over.setTextMatrix(140, 140);
						over.showText(" " + c.get(Calendar.DAY_OF_MONTH)
								+ " / " + (c.get(Calendar.MONTH) + 1) + " / "
								+ c.get(Calendar.YEAR));
						over.endText();
						over.beginText();
						over.setFontAndSize(bf, 10);
						over.setTextMatrix(340, 140);
						over.showText(" " + c.get(Calendar.DAY_OF_MONTH)
								+ " / " + (c.get(Calendar.MONTH) + 1) + " / "
								+ c.get(Calendar.YEAR));
						over.endText();

					}
				}
				stamp.close();
			}
		} catch (Exception de) {
			de.printStackTrace();
		}
		return true;
	}

	public boolean CharteChorus(java.sql.Connection conn,
			HttpServletRequest request, int code, String rep_pdf) {
		try {
			String[] champ = null;
			champ = new String[20];//
			ArrayList OneUid = getAllChorus(conn, request, code);
			String xbdd = "Tarbes;Gilles;0692281580;gillestarbes@msn.com;Crous";
			ItemAnnuaire A = (ItemAnnuaire) OneUid.get(0);
			xbdd = A.getAg_ldap_nom() + ";" + A.getAg_ldap_prenom() + ";"
					+ A.getAg_ldap_profil_asa() + ";" + A.getAg_ldap_mail()
					+ ";" + A.getAg_ldap_uid() + ";" + A.getAg_ldap_uid()
					+ ";aaaa";
			{
				StringTokenizer tokenizer = new StringTokenizer(xbdd, ";");
				int t = 0;
				int trouve = 0;
				while (tokenizer.hasMoreTokens()) {
					String token = tokenizer.nextToken();
					champ[t] = token;
					t++;
				}
				champ[0] = A.getAg_ldap_nom();
				champ[1] = A.getAg_ldap_prenom();
				champ[2] = A.getAg_ldap_mail();
				t = A.getAg_gemalto_num();
				champ[3] = A.getAg_gemalto_serie();
				champ[4] = A.getAg_gemalto_charte_signee();
				//PdfReader reader = new PdfReader(rep_pdf + "CHARTEChorus.pdf");
				PdfReader reader = new PdfReader(rep_pdf + "chartechorusv6-signe.pdf");
				// PdfReader reader = new
				// PdfReader("d:/data/Mes documents/api/CHARTEChorus.pdf");
				// PdfReader reader = new
				// PdfReader("/appli/annur/webapps/annur/CHARTEChorus.pdf");
				int n = reader.getNumberOfPages();
				// we create a stamper that will copy the document to a new file
				PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(
						rep_pdf + champ[0] + "_" + champ[1] + ".pdf"));
				// PdfStamper stamp = new PdfStamper(reader, new
				// FileOutputStream("/tmp/"+champ[0]+"_"+champ[1]+".pdf"));
				// PdfStamper stamp = new PdfStamper(reader, new
				// FileOutputStream( champ[0]+"_"+champ[1]+".pdf"));
				System.out.println("nb pages :" + n + " " + champ[0] + "_"
						+ champ[1] + ".pdf");
				// adding some metadata
				HashMap moreInfo = new HashMap();
				moreInfo.put("Author", "Alain Grainville");
				stamp.setMoreInfo(moreInfo);
				// adding content to each page
				int i = 0;
				PdfContentByte under;
				PdfContentByte over;
				// BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
				// BaseFont.WINANSI, BaseFont.EMBEDDED);
				BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
						BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
				// BaseFont bf1 = BaseFont.createFont(BaseFont.HELVETICA,
				// BaseFont.WINANSI, BaseFont.BBOXLLX);
				bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD,
						BaseFont.WINANSI, BaseFont.EMBEDDED);
				System.out.println("nb pages :" + n);
				while (i < n) {
					i++;
					// watermark under the existing page
					under = stamp.getUnderContent(i);
					over = stamp.getOverContent(i);
					over.setFontAndSize(bf, 10);

					if (i == 1 || i == 3) {
						
						over.beginText();
						over.setTextMatrix(225, 465);
						over.showText(champ[3]);
						over.endText();
						// identite fonction
						// identite ISR
						 
						over.beginText();
						over.setTextMatrix(215, 590);
						over.showText("Grainville Alain");
						over.endText();
						
						over.beginText();
						over.setTextMatrix(215, 575);
						over.showText("0262 481083");
						over.endText();
						 
						over.beginText();
						over.setTextMatrix(215, 560);
						over.showText("alain.grainville@ac-reunion.fr");
						over.endText();
						
						over.beginText();
						over.setTextMatrix(215, 450);
						over.showText(champ[0] + " " + champ[1]);
						over.endText();
						// telephone
						// over.beginText();
						// over.setTextMatrix(220 , 428);
						// over.showText(champ[2]);
						// over.endText();
						// adresse electronique
						over.beginText();
						over.setTextMatrix(215, 420);
						over.showText(champ[2]);
						over.endText();
 
						// telephone
						// over.beginText();
						// over.setTextMatrix(220 , 428);
						// over.showText(champ[2]);
						// over.endText();
						// adresse electronique
						 

						// ou
						// over.beginText();
						// over.setTextMatrix(220 , 370);
						// over.showText("ac-reunion");
						// over.endText();

					}
					if (i == 2 || i == 4) {
						// identite
						over.beginText();
						over.setTextMatrix(120, 472);
						over.showText(champ[0]);
						over.endText();
						 
						// identite prenom
						over.beginText();
						over.setTextMatrix(120, 445);
						over.showText(champ[1]);
						over.endText();
						
						// identite ISR
						over.beginText();
						over.setTextMatrix(340, 472);
						over.showText("Grainville");
						over.endText();
						 
						// identite prenom
						over.beginText();
						over.setTextMatrix(340, 445);
						over.showText("Alain");
						over.endText();
						
						// Profil_Asa
						//over.beginText();
						//over.setTextMatrix(190, 190);
						//over.showText(String.valueOf(t));
						//over.endText();
						// ou
						//over.beginText();
						//over.setTextMatrix(190, 180);
						//over.showText(champ[3]);
						//over.endText();// identite fonction
						// over.beginText();
						// over.setTextMatrix(140 , 215);
						// over.showText(champ[4]);
						// over.endText();
						// Date
						Calendar c = Calendar.getInstance();
						over.beginText();
						over.setFontAndSize(bf, 10);
						over.setTextMatrix(120, 385);
						over.showText(champ[4]);
						//over.showText(" " + c.get(Calendar.DAY_OF_MONTH)
							//	+ " / " + (c.get(Calendar.MONTH) + 1) + " / "
								//+ c.get(Calendar.YEAR));
						over.endText();
						over.beginText();
						over.setFontAndSize(bf, 10);
						over.setTextMatrix(340, 385);
						over.showText(champ[4]);
						//over.showText(" " + c.get(Calendar.DAY_OF_MONTH)
							//	+ " / " + (c.get(Calendar.MONTH) + 1) + " / "
								//+ c.get(Calendar.YEAR));
						over.endText();
					}
				}
				stamp.close();
			}
		} catch (Exception de) {
			de.printStackTrace();
		}
		return true;
	}

	private static int linesInFile(File file) {
		int cpt = 0;
		try {
			String fic = file.getName().toLowerCase();
			if (file.isFile()) {
				BufferedReader bfr = new BufferedReader(new FileReader(file));
				while (bfr.readLine() != null) {
					cpt++;
				}
			}
		} catch (Exception e) {
			System.out.println("linesInFile eror :" + e);
		}
		return cpt;
	}

	private void ListeParam(HttpServletRequest request) {
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
	}

	public static String genFtp(PrintWriter sortieA, String uid) {
		String rep = "";
		sortieA.println("rm -rf  /home/" + uid.substring(0, 1) + "/" + uid
				+ " ;");
		sortieA.println("mkdir /home/" + uid.substring(0, 1) + " ;");
		sortieA.println("mkdir /home/" + uid.substring(0, 1) + "/" + uid + " ;");
		sortieA.println("mkdir /home/" + uid.substring(0, 1) + "/" + uid
				+ "/MailDir  ;");
		sortieA.println("mkdir /home/" + uid.substring(0, 1) + "/" + uid
				+ "/perso  ;");
		sortieA.println("chown mail:mail /home/" + uid.substring(0, 1) + "/"
				+ uid + "/MailDir/   ;");
		sortieA.println("chown " + uid + " /home/" + uid.substring(0, 1) + "/"
				+ uid + "/perso/   ;");
		sortieA.println("chmod -R 770 /home/" + uid.substring(0, 1) + "/" + uid
				+ "  ;");
		sortieA.println("mkdir /home/" + uid.substring(0, 1) + "/" + uid
				+ "/.ftp ;");
		sortieA.println("chmod 500 /home/" + uid.substring(0, 1) + "/" + uid
				+ "/.ftp ;");
		sortieA.println("chown " + uid + " /home/" + uid.substring(0, 1) + "/"
				+ uid + "/.ftp ;");
		sortieA.println("cd      /home/" + uid.substring(0, 1) + "/" + uid
				+ "/.ftp ;");
		sortieA.println("rm -rf    /home/" + uid.substring(0, 1) + "/" + uid
				+ "/.ftp/commun ;");
		sortieA.println("rm -rf    /home/" + uid.substring(0, 1) + "/" + uid
				+ "/.ftp/perso ;");
		sortieA.println("ln -s   /home/workgroups/commun commun ;");
		sortieA.println("ln -s   /home/" + uid.substring(0, 1) + "/" + uid
				+ "/perso perso ;");
		sortieA.println("setfacl -m u:" + uid
				+ ":rwx,u:mail:rwx,g::---,o::---,g:mail:rwx,m::rwx /home/"
				+ uid.substring(0, 1) + "/" + uid + "/MailDir  ;");
		sortieA.println("setfacl -dm u:" + uid
				+ ":rwx,u:mail:rwx,g::---,o::---,g:mail:rwx,m::rwx /home/"
				+ uid.substring(0, 1) + "/" + uid + "/MailDir  ;");
		sortieA.println("setfacl -m u:" + uid
				+ ":rwx,g::---,o::---,m::rwx /home/" + uid.substring(0, 1)
				+ "/" + uid + "/perso  ;");
		sortieA.println("setfacl -dm u:" + uid
				+ ":rwx,g::---,o::---,m::rwx /home/" + uid.substring(0, 1)
				+ "/" + uid + "/perso  ;");
		sortieA.println("setfacl -m u:" + uid + ":rwx /home/"
				+ uid.substring(0, 1) + "/" + uid + " ;");
		return rep;
	}

	public static String genFtpPartage(PrintWriter sortieA, String uid,
			String partage) {
		String rep = "";
		sortieA.println("rm -rf   /home/workgroups/'" + partage + "' ;");
		sortieA.println("mkdir  /home/workgroups/'" + partage + "' ;");
		sortieA.println("chmod 770  /home/workgroups/'" + partage + "' ;");
		sortieA.println("setfacl -b -R  /home/workgroups/'" + partage + "' ;");
		sortieA.println("setfacl -m u::rwx,g::---,o::---,m::rwx /home/workgroups/'"
				+ partage + "' ;");
		sortieA.println("setfacl -dm u::rwx,g::---,o::---,m::rwx /home/workgroups/'"
				+ partage + "' ;");
		sortieA.println("setfacl -m u::rwx /home/workgroups/'" + partage
				+ "' ;");
		sortieA.println("setfacl -R -m g:'" + partage
				+ "':rwx /home/workgroups/'" + partage + "' ;");
		sortieA.println("setfacl -R -dm g:'" + partage
				+ "':rwx /home/workgroups/'" + partage + "' ;");
		return rep;
	}

	public static String genFtpPartageUser(PrintWriter sortieA, String uid,
			String partage) throws ServletException, IOException {

		String SSH = "ssh root@scribe 'ln -s   /home/workgroups/'" + partage
				+ "'  /home/" + uid.substring(0, 1) + "/" + uid + "/.ftp/"
				+ partage + "   ;";
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(new String[] { "/bin/sh", "-c", SSH });
		DataInputStream data_in = new DataInputStream(process.getInputStream());
		String line_in = null;
		while ((line_in = data_in.readLine()) != null) {
			System.out.println("ssh:" + line_in);
		}
		String rep = "";
		// sortieA.println("rm -rf  /home/" + uid.substring(0,1) + "/" + uid +
		// "/.ftp/'"+ partage + "'   ;");
		// sortieA.println("ln -s   /home/workgroups/'" + partage + "'  /home/"
		// + uid.substring(0,1) + "/" + uid + "/.ftp/'"+ partage + "'   ;");
		return rep;
	}

	private void majldap(HttpServletRequest request, String ldap,
			String ldapdm, String ldappw, ArrayList alist, int a,
			LDAPConnection ld) {
		ItemAnnuaire A = (ItemAnnuaire) alist.get(a);
		try {
			if (A.getAg_ldap_dn().length() > 2) {

				String ENTRYDN = A.getAg_ldap_dn();

				// System.out.println("   modif ldap:"+A.getAg_ldap_dn()
				// +" "+A.getDv_nom() + " af_ag_code :" + A.getAf_ag_code()+ " "
				// + A.getAf_telephone() );
				System.out.println("modif :" + A.getDv_nom() + " - "
						+ A.getSv_nom() + " - " + A.getAg_ldap_nom() + " - "
						+ A.getAf_ag_code() + " - " + A.getAf_telephone());

				LDAPModificationSet mods = new LDAPModificationSet();

				/* Add each change to an attribute to the set of modifications. */
				if (A.getAf_ag_code() > 0) {
					LDAPAttribute attrEmail = new LDAPAttribute("annu-code", ""
							+ A.getAf_ag_code());
					mods.add(LDAPModification.REPLACE, attrEmail);
				}
				if (A.getAf_ag_code() > 0) {
					LDAPAttribute attrEmail = new LDAPAttribute(
							"annu-profil-ddt", "" + A.getAg_ldap_profil_ddt());
					mods.add(LDAPModification.REPLACE, attrEmail);
				}
				if (A.getAf_ag_code() > 0) {
					LDAPAttribute attrEmail = new LDAPAttribute(
							"annu-profil-annu", "" + A.getAg_ldap_profil_annu());
					mods.add(LDAPModification.REPLACE, attrEmail);
				}

				if (A.getAf_ag_code() > 0) {
					LDAPAttribute attrEmail = new LDAPAttribute(
							"telephoneNumber", "" + A.getAf_telephone());
					mods.add(LDAPModification.REPLACE, attrEmail);
				}
				if (A.getAf_ag_code() > 0) {
					LDAPAttribute attrEmail = new LDAPAttribute("roomNumber",
							"" + A.getAf_bureau());
					mods.add(LDAPModification.REPLACE, attrEmail);
				}

				int status = -1;
				/* Connect to the server. */

				ld.modify(ENTRYDN, mods);

			}

		}

		catch (LDAPException e) {
			if (e.getLDAPResultCode() == LDAPException.NO_SUCH_OBJECT)
				System.out.println("Error no entry : " + A.getDv_nom() + " - "
						+ A.getAg_ldap_nom() + " - code :" + A.getAf_ag_code()
						+ " - uid : " + A.getAg_ldap_uid());
			else if (e.getLDAPResultCode() == LDAPException.INSUFFICIENT_ACCESS_RIGHTS)
				System.out.println("Error: Insufficient rights");
			else if (e.getLDAPResultCode() == LDAPException.ATTRIBUTE_OR_VALUE_EXISTS)
				System.out.println("Error: Attribute or value exists");
			else
				System.out.println("Error: " + e.toString());
		}

	}

	private void majldapdel(HttpServletRequest request, String ldap,
			String ldapdm, String ldappw, ArrayList alist, int a,
			LDAPConnection ld) {
		ItemAnnuaire A = (ItemAnnuaire) alist.get(a);
		try {
			if (A.getAg_ldap_dn().length() > 2) {

				String ENTRYDN = A.getAg_ldap_dn();

				System.out.println("   modif ldap:"+A.getAg_ldap_dn());
				// +" "+A.getDv_nom() + " af_ag_code :" + A.getAf_ag_code()+ " "
				// + A.getAf_telephone() );
				System.out.println("suppression annu-division et nompabx  :"
						+ A.getAg_ldap_nom() + " - " + A.getAf_ag_code()
						+ " - " + A.getAf_telephone());

				LDAPModificationSet mods = new LDAPModificationSet();

				/* Add each change to an attribute to the set of modifications. */

				if (A.getAf_ag_code() > 0) {
					LDAPAttribute attrEmail = new LDAPAttribute("annu-division");
					mods.add(LDAPModification.DELETE, attrEmail);
				}
				if (A.getAf_ag_code() > 0) {
					LDAPAttribute attrEmail = new LDAPAttribute("annu-nompabx");
					mods.add(LDAPModification.DELETE, attrEmail);
				}

				int status = -1;
				/* Connect to the server. */

				ld.modify(ENTRYDN, mods);

			}

		}

		catch (LDAPException e) {
			if (e.getLDAPResultCode() == LDAPException.NO_SUCH_OBJECT)
				System.out.println("Error no entry : " + A.getDv_nom() + " - "
						+ A.getAg_ldap_nom() + " - code :" + A.getAf_ag_code()
						+ " - uid : " + A.getAg_ldap_uid());
			else if (e.getLDAPResultCode() == LDAPException.INSUFFICIENT_ACCESS_RIGHTS)
				System.out.println("Error: Insufficient rights");
			else if (e.getLDAPResultCode() == LDAPException.ATTRIBUTE_OR_VALUE_EXISTS)
				System.out.println("Error: Attribute or value exists");
			else
				System.out.println("Error: " + e.toString());
		}

	}

	private void majldapadddiv(HttpServletRequest request, String ldap,
			String ldapdm, String ldappw, ArrayList alist, int a,
			LDAPConnection ld) {
		ItemAnnuaire A = (ItemAnnuaire) alist.get(a);
		try {
			if (A.getAg_ldap_dn().length() > 2) {

				String ENTRYDN = A.getAg_ldap_dn();
				System.out.println("ajout division :" + A.getDv_nom() + " - "
						+ " - " + A.getAg_ldap_nom() + " - "
						+ A.getAf_ag_code() + " - " + A.getAf_telephone());

				LDAPModificationSet mods = new LDAPModificationSet();
				if (A.getAf_ag_code() > 0) {
					LDAPAttribute attrEmail = new LDAPAttribute(
							"annu-division", "" + A.getDv_nom());
					mods.add(LDAPModification.ADD, attrEmail);
				}
				int status = -1;
				ld.modify(ENTRYDN, mods);
			}
		} catch (LDAPException e) {
			if (e.getLDAPResultCode() == LDAPException.NO_SUCH_OBJECT)
				System.out.println("Error no entry : " + A.getDv_nom() + " - "
						+ A.getAg_ldap_nom() + " - code :" + A.getAf_ag_code()
						+ " - uid : " + A.getAg_ldap_uid());
			else if (e.getLDAPResultCode() == LDAPException.INSUFFICIENT_ACCESS_RIGHTS)
				System.out.println("Error: Insufficient rights");
			else if (e.getLDAPResultCode() == LDAPException.ATTRIBUTE_OR_VALUE_EXISTS)
				System.out.println("Error: Attribute or value exists");
			else
				System.out.println("Error: " + e.toString());
		}

	}

	private void majldapaddser(HttpServletRequest request, String ldap,
			String ldapdm, String ldappw, ArrayList alist, int a,
			LDAPConnection ld) {
		ItemAnnuaire A = (ItemAnnuaire) alist.get(a);
		try {
			if (A.getAg_ldap_dn().length() > 2) {

				String ENTRYDN = A.getAg_ldap_dn();
				System.out.println("ajout service :" + A.getSv_nom() + " - "
						+ A.getAg_ldap_nom() + " - " + A.getAf_ag_code()
						+ " - " + A.getAf_telephone());

				LDAPModificationSet mods = new LDAPModificationSet();
				if (A.getAf_ag_code() > 0) {
					LDAPAttribute attrEmail = new LDAPAttribute("annu-nompabx",
							"" + A.getSv_nom());
					mods.add(LDAPModification.ADD, attrEmail);
				}
				int status = -1;
				/* Connect to the server. */

				ld.modify(ENTRYDN, mods);
			}
		} catch (LDAPException e) {
			if (e.getLDAPResultCode() == LDAPException.NO_SUCH_OBJECT)
				System.out.println("Error no entry : " + A.getDv_nom() + " - "
						+ A.getAg_ldap_nom() + " - code :" + A.getAf_ag_code()
						+ " - uid : " + A.getAg_ldap_uid());
			else if (e.getLDAPResultCode() == LDAPException.INSUFFICIENT_ACCESS_RIGHTS)
				System.out.println("Error: Insufficient rights");
			else if (e.getLDAPResultCode() == LDAPException.ATTRIBUTE_OR_VALUE_EXISTS)
				System.out.println("Error: Attribute or value exists");
			else
				System.out.println("Error: " + e.toString());
		}

	}

	public void AddMemberScribe(UserInfo userinfo, String ldappwS,
			HttpServletRequest request, String partage, String uid) {
		ArrayList alist = new ArrayList();
		System.out
				.println("------------------------ recherche ldap partage scribe  ------------------------------");
		String ldapbasednG = "ou=local,ou=groupes,ou=9740049K,ou=ac-reunion,ou=education,o=gouv,c=fr";
		HttpSession session = request.getSession(true);
		LDAPConnection ldG = null;
		LDAPEntry findEntryG = null;
		int status = -1;
		String xdn = "";
		try {
			ldG = new LDAPConnection();
			ldG.connect("scribe.in.ac-reunion.fr", 389);
			ldG.authenticate(3, "cn=admin,o=gouv,c=fr", ldappwS);
			String MY_FILTER = "cn=" + partage;
			String[] attrNames = {};
			LDAPSearchResults res = ldG.search(ldapbasednG, 2, MY_FILTER, null,
					false);
			int n = 0;
			String xcn = "";
			while (res.hasMoreElements()) {
				findEntryG = null;
				findEntryG = res.next();
				xdn = findEntryG.getDN();
				System.out.println("dn:" + xdn);
				LDAPModificationSet mods = new LDAPModificationSet();
				LDAPAttribute attrEmail = new LDAPAttribute("memberUid", uid);
				mods.add(LDAPModification.ADD, attrEmail);
				ldG.modify(xdn, mods);
			}
			/* Done, so disconnect */
			if ((ldG != null) && ldG.isConnected()) {
				ldG.disconnect();
			}
			// ---------------------------------------------------------------------------
		} catch (LDAPException e) {
			System.out.println("Error ldap: " + e.toString());
		}

	}

	public void DelMemberScribe(UserInfo userinfo, String ldappwS,
			HttpServletRequest request, String partage, String uid) {
		ArrayList alist = new ArrayList();
		System.out
				.println("------------------------ recherche ldap partage scribe  ------------------------------");
		String ldapbasednG = "ou=local,ou=groupes,ou=9740049K,ou=ac-reunion,ou=education,o=gouv,c=fr";
		HttpSession session = request.getSession(true);
		LDAPConnection ldG = null;
		LDAPEntry findEntryG = null;
		int status = -1;
		String xdn = "";
		try {
			ldG = new LDAPConnection();
			ldG.connect("scribe.in.ac-reunion.fr", 389);
			ldG.authenticate(3, "cn=admin,o=gouv,c=fr", ldappwS);
			String MY_FILTER = "cn=" + partage;
			String[] attrNames = {};
			LDAPSearchResults res = ldG.search(ldapbasednG, 2, MY_FILTER, null,
					false);
			int n = 0;
			String xcn = "";
			while (res.hasMoreElements()) {
				findEntryG = null;
				findEntryG = res.next();
				xdn = findEntryG.getDN();
				System.out.println("dn:" + xdn);
				System.out.println("Ajout MemberUid: " + uid);
				LDAPModificationSet mods = new LDAPModificationSet();
				LDAPAttribute attrEmail = new LDAPAttribute("memberUid", uid);
				mods.add(LDAPModification.DELETE, attrEmail);
				ldG.modify(xdn, mods);
			}
			/* Done, so disconnect */
			if ((ldG != null) && ldG.isConnected()) {
				ldG.disconnect();
			}
			// ---------------------------------------------------------------------------
		} catch (LDAPException e) {
			System.out.println("Error ldap: " + e.toString());
		}
	}

	public void AddPartageScribe(UserInfo userinfo, String ldappwS,
			HttpServletRequest request, String partage) {
		ArrayList alist = new ArrayList();
		System.out
				.println("------------------------ AddPartageScribe  ------------------------------");
		String ldapbasednG = "ou=local,ou=groupes,ou=9740049K,ou=ac-reunion,ou=education,o=gouv,c=fr";
		HttpSession session = request.getSession(true);
		LDAPConnection ldG = null;
		LDAPEntry findEntryG = null;
		int status = -1;
		/*
		 * Specify the DN to add dn:
		 * cn=smb://scribe/$%dv_nom%$,ou=local,ou=partages
		 * ,ou=9740049K,ou=ac-reunion,ou=education,o=gouv,c=fr
		 */
		String dn = "cn=smb://scribe/"
				+ partage
				+ ",ou=local,ou=partages,ou=9740049K,ou=ac-reunion,ou=education,o=gouv,c=fr";
		System.out.println("add:" + dn);

		/* Specify the attributes of the entry */
		LDAPAttributeSet attrs = new LDAPAttributeSet();
		attrs.add(new LDAPAttribute("objectclass", "sambaFileShare"));
		attrs.add(new LDAPAttribute("cn", "smb://scribe/" + partage));
		attrs.add(new LDAPAttribute("sambaShareName", partage));
		attrs.add(new LDAPAttribute("description", partage));
		attrs.add(new LDAPAttribute("sambaShareGroup", partage));
		attrs.add(new LDAPAttribute("sambaShareModel", "standard"));
		attrs.add(new LDAPAttribute("sambaFilePath", "/home/workgroups/"
				+ partage));
		attrs.add(new LDAPAttribute("sambaShareURI", "\\\\scribe\\" + partage));
		/* Create an entry with this DN and these attributes */
		LDAPEntry myEntry = new LDAPEntry(dn, attrs);
		try {
			ldG = new LDAPConnection();
			ldG.connect("scribe.in.ac-reunion.fr", 389);
			ldG.authenticate(3, "cn=admin,o=gouv,c=fr", ldappwS);
			/* Now add the entry to the directory */
			ldG.add(myEntry);
			System.out.println("Entry added");
			/* Done, so disconnect */
			if ((ldG != null) && ldG.isConnected()) {
				ldG.disconnect();
			}
			// ---------------------------------------------------------------------------
		} catch (LDAPException e) {
			System.out.println("Error ldap: " + e.toString());
		}

	}

	public void AddGroupePartageScribe(UserInfo userinfo, String ldappwS,
			HttpServletRequest request, String partage, String samba_sid) {
		ArrayList alist = new ArrayList();
		System.out
				.println("------------------------ AddGroupePartageScribe  ------------------------------");
		String ldapbasednG = "ou=local,ou=groupes,ou=9740049K,ou=ac-reunion,ou=education,o=gouv,c=fr";
		HttpSession session = request.getSession(true);
		LDAPConnection ldG = null;
		LDAPEntry findEntryG = null;

		String Message = partage.toLowerCase();
		// --------------------------------------------------------
		// --- Calcul uidnumber
		// --------------------
		int xgid = 20000;
		for (int i = 0; i < Message.length(); i++)
			xgid += Message.charAt(i) * Math.pow(i + 1, 4);
		int status = -1;
		/*
		 * Specify the DN to add dn:
		 * cn=smb://scribe/$%dv_nom%$,ou=local,ou=partages
		 * ,ou=9740049K,ou=ac-reunion,ou=education,o=gouv,c=fr
		 */
		String dn = "cn="
				+ partage
				+ ",ou=local,ou=Groupes,ou=9740049K,ou=ac-reunion,ou=education,o=gouv,c=fr";
		/* Specify the attributes of the entry */
		System.out.println("add:" + dn);

		LDAPAttributeSet attrs = new LDAPAttributeSet();
		String objectclass_values[] = { "top", "posixGroup",
				"sambaGroupMapping", "eolegroupe", "ENTGroupe" };
		LDAPAttribute attr = new LDAPAttribute("objectclass");
		for (int i = 0; i < objectclass_values.length; i++) {
			attr.addValue(objectclass_values[i]);
		}
		attrs.add(attr);
		attrs.add(new LDAPAttribute("type", "Groupe"));
		attrs.add(new LDAPAttribute("description", "Partage: " + partage));
		attrs.add(new LDAPAttribute("sambaGroupType", "2"));
		attrs.add(new LDAPAttribute("cn", partage));
		attrs.add(new LDAPAttribute("LastUpdate", "20110316")); // necessaire
																// pour
																// eolegroup
		attrs.add(new LDAPAttribute("displayName", partage));
		attrs.add(new LDAPAttribute("gidNumber", "" + xgid));
		attrs.add(new LDAPAttribute("sambaSID", samba_sid + xgid));
		/* Create an entry with this DN and these attributes */
		LDAPEntry myEntry = new LDAPEntry(dn, attrs);
		try {
			ldG = new LDAPConnection();
			ldG.connect("scribe.in.ac-reunion.fr", 389);
			ldG.authenticate(3, "cn=admin,o=gouv,c=fr", ldappwS);
			/* Now add the entry to the directory */
			ldG.add(myEntry);
			System.out.println("Entry added");
			/* Done, so disconnect */
			if ((ldG != null) && ldG.isConnected()) {
				ldG.disconnect();
			}
			// ---------------------------------------------------------------------------
		} catch (LDAPException e) {
			System.out.println("Error ldap: " + e.toString());
		}

	}

}
