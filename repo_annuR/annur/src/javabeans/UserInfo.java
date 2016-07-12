package javabeans;

import java.io.*;

public class UserInfo implements Serializable {
	int ag_code = 0;
	int bu_code = 0;
	private String code;
	private String ct_remote_user = "";
	int dv_code = 0;
	private String dv_nom = "";

	int dv1_code = 0;

	int dv_nb = 0; // 1 ou 2, mettre 1 apres choix admin_menu
	private String dv1_nom = "";
	private String error_message;
	private String faq;
	private String grade;
	private String last_req = "";
	private String libgrade;
	private String mail;
	int menu_admin   = 2; // 1=oui 2=non
	int pa_code = 0;

	private String pa_nom = "";
	private String passwd;
	int pl_code = 0;
	private String poste;

	int profil = -1;

	private String rne;

	int sv_code = 0;
	private String type = "";
	private String user;

	int voir_mission = 2; // 1=oui 2=non
	/**
	 * Getter for property ag_code.
	 * 
	 * @return Value of property ag_code.
	 */
	public int getAg_code() {
		return ag_code;
	}
	/**
	 * Getter for property bu_code.
	 * 
	 * @return Value of property bu_code.
	 */
	public int getBu_code() {
		return bu_code;
	}
	/**
	 * Getter for property code.
	 * 
	 * @return Value of property code.
	 */
	public java.lang.String getCode() {
		return code;
	}
	public String getCt_remote_user() {
		return ct_remote_user;
	}
	/**
	 * Getter for property dv_code.
	 * 
	 * @return Value of property dv_code.
	 */
	public int getDv_code() {
		return dv_code;
	}
	public String getDv_nom() {
		return dv_nom;
	}
	public int getDv1_code() {
		return dv1_code;
	}
	public String getDv1_nom() {
		return dv1_nom;
	}

	/**
	 * Getter for property error_message.
	 * 
	 * @return Value of property error_message.
	 */
	public java.lang.String getError_message() {
		return error_message;
	}

	/**
	 * Getter for property faq.
	 * 
	 * @return Value of property faq.
	 */
	public java.lang.String getFaq() {
		return faq;
	}

	/**
	 * Getter for property grade.
	 * 
	 * @return Value of property grade.
	 */
	public java.lang.String getGrade() {
		return grade;
	}

	/**
	 * Getter for property last_req.
	 * 
	 * @return Value of property last_req.
	 */
	public java.lang.String getLast_req() {
		return last_req;
	}

	/**
	 * Getter for property libgrade.
	 * 
	 * @return Value of property libgrade.
	 */
	public java.lang.String getLibgrade() {
		return libgrade;
	}

	/**
	 * Getter for property mail.
	 * 
	 * @return Value of property mail.
	 */
	public java.lang.String getMail() {
		return mail;
	}

	public int getMenu_admin() {
		return menu_admin;
	}

	/**
	 * Getter for property pa_code.
	 * 
	 * @return Value of property pa_code.
	 */
	public int getPa_code() {
		return pa_code;
	}

	/**
	 * Getter for property pa_nom.
	 * 
	 * @return Value of property pa_nom.
	 */
	public java.lang.String getPa_nom() {
		return pa_nom;
	}

	/**
	 * Getter for property passwd.
	 * 
	 * @return Value of property passwd.
	 */
	public java.lang.String getPasswd() {
		return passwd;
	}

	/**
	 * Getter for property pl_code.
	 * 
	 * @return Value of property pl_code.
	 */
	public int getPl_code() {
		return pl_code;
	}

	/**
	 * Getter for property poste.
	 * 
	 * @return Value of property poste.
	 */
	public java.lang.String getPoste() {
		return poste;
	}

	/**
	 * Getter for property profil.
	 * 
	 * @return Value of property profil.
	 */
	public int getProfil() {
		return profil;
	}

	/**
	 * Getter for property rne.
	 * 
	 * @return Value of property rne.
	 */
	public java.lang.String getRne() {
		return rne;
	}

	/**
	 * Getter for property sv_code.
	 * 
	 * @return Value of property sv_code.
	 */
	public int getSv_code() {
		return sv_code;
	}

	/**
	 * Getter for property type.
	 * 
	 * @return Value of property type.
	 */
	public java.lang.String getType() {
		return type;
	}

	/**
	 * Getter for property user.
	 * 
	 * @return Value of property user.
	 */
	public java.lang.String getUser() {
		return user;
	}

	/**
	 * Getter for property voir_mission.
	 * 
	 * @return Value of property voir_mission.
	 */
	public int getVoir_mission() {
		return voir_mission;
	}

	/**
	 * Setter for property ag_code.
	 * 
	 * @param ag_code
	 *            New value of property ag_code.
	 */
	public void setAg_code(int ag_code) {
		this.ag_code = ag_code;
	}

	/**
	 * Setter for property bu_code.
	 * 
	 * @param bu_code
	 *            New value of property bu_code.
	 */
	public void setBu_code(int bu_code) {
		this.bu_code = bu_code;
	}

	/**
	 * Setter for property code.
	 * 
	 * @param code
	 *            New value of property code.
	 */
	public void setCode(java.lang.String code) {
		this.code = code;
	}

	public void setCt_remote_user(String ct_remote_user) {
		this.ct_remote_user = ct_remote_user;
	}

	/**
	 * Setter for property dv_code.
	 * 
	 * @param dv_code
	 *            New value of property dv_code.
	 */
	public void setDv_code(int dv_code) {
		this.dv_code = dv_code;
	}

	public void setDv_nom(String dv_nom) {
		this.dv_nom = dv_nom;
	}

	public void setDv1_code(int dv1_code) {
		this.dv1_code = dv1_code;
	}

	public void setDv1_nom(String dv1_nom) {
		this.dv1_nom = dv1_nom;
	}

	/**
	 * Setter for property error_message.
	 * 
	 * @param error_message
	 *            New value of property error_message.
	 */
	public void setError_message(java.lang.String error_message) {
		this.error_message = error_message;
	}

	/**
	 * Setter for property faq.
	 * 
	 * @param faq
	 *            New value of property faq.
	 */
	public void setFaq(java.lang.String faq) {
		this.faq = faq;
	}

	/**
	 * Setter for property grade.
	 * 
	 * @param grade
	 *            New value of property grade.
	 */
	public void setGrade(java.lang.String grade) {
		this.grade = grade;
	}

	/**
	 * Setter for property last_req.
	 * 
	 * @param last_req
	 *            New value of property last_req.
	 */
	public void setLast_req(java.lang.String last_req) {
		this.last_req = last_req;
	}

	/**
	 * Setter for property libgrade.
	 * 
	 * @param libgrade
	 *            New value of property libgrade.
	 */
	public void setLibgrade(java.lang.String libgrade) {
		this.libgrade = libgrade;
	}

	/**
	 * Setter for property mail.
	 * 
	 * @param mail
	 *            New value of property mail.
	 */
	public void setMail(java.lang.String mail) {
		this.mail = mail;
	}

	public void setMenu_admin(int menu_admin) {
		this.menu_admin = menu_admin;
	}

	/**
	 * Setter for property pa_code.
	 * 
	 * @param pa_code
	 *            New value of property pa_code.
	 */
	public void setPa_code(int pa_code) {
		this.pa_code = pa_code;
	}

	/**
	 * Setter for property pa_nom.
	 * 
	 * @param pa_nom
	 *            New value of property pa_nom.
	 */
	public void setPa_nom(java.lang.String pa_nom) {
		this.pa_nom = pa_nom;
	}

	/**
	 * Setter for property passwd.
	 * 
	 * @param passwd
	 *            New value of property passwd.
	 */
	public void setPasswd(java.lang.String passwd) {
		this.passwd = passwd;
	}

	/**
	 * Setter for property pl_code.
	 * 
	 * @param pl_code
	 *            New value of property pl_code.
	 */
	public void setPl_code(int pl_code) {
		this.pl_code = pl_code;
	}

	/**
	 * Setter for property poste.
	 * 
	 * @param poste
	 *            New value of property poste.
	 */
	public void setPoste(java.lang.String poste) {
		this.poste = poste;
	}

	/**
	 * Setter for property profil.
	 * 
	 * @param profil
	 *            New value of property profil.
	 */
	public void setProfil(int profil) {
		this.profil = profil;
	}

	/**
	 * Setter for property rne.
	 * 
	 * @param rne
	 *            New value of property rne.
	 */
	public void setRne(java.lang.String rne) {
		this.rne = rne;
	}

	/**
	 * Setter for property sv_code.
	 * 
	 * @param sv_code
	 *            New value of property sv_code.
	 */
	public void setSv_code(int sv_code) {
		this.sv_code = sv_code;
	}

	/**
	 * Setter for property type.
	 * 
	 * @param type
	 *            New value of property type.
	 */
	public void setType(java.lang.String type) {
		this.type = type;
	}

	/**
	 * Setter for property user.
	 * 
	 * @param user
	 *            New value of property user.
	 */
	public void setUser(java.lang.String user) {
		this.user = user;
	}

	/**
	 * Setter for property voir_mission.
	 * 
	 * @param voir_mission
	 *            New value of property voir_mission.
	 */
	public void setVoir_mission(int voir_mission) {
		this.voir_mission = voir_mission;
	}
	public int getDv_nb() {
		return dv_nb;
	}
	public void setDv_nb(int dv_nb) {
		this.dv_nb = dv_nb;
	}

}
