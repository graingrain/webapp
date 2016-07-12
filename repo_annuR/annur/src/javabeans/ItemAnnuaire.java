package javabeans;
import java.io.*;
import java.util.*;
public class ItemAnnuaire implements Comparable {
    // agent
    private int ag_code=0;
    private int ag_ldap_annu_code=0;     /* uid  */
    private String ag_ldap_uid="";     /* uid  */
    private String ag_ldap_nom="";     /* nompatro  */
    private String ag_ldap_prenom="";  /* givenname */
    private String ag_ldap_title="";   /* title     */
    private String ag_ldap_fredufonctadm="";   /* fredufonctadm     */
    private String ag_ldap_grade="";   /* title     */
    private String ag_ldap_dn="";      /* dn     */
    private String ag_ldap_pw="";      /* pw     */
    private String ag_gsm="";
    private String ag_ldap_mail="";        /* mail      */
    private String ag_ldap_numen="";       /* employeenumber*/
    private String ag_ldap_datenaiss="";       /* date naissance*/
    private String ag_ldap_annu_division=""; 
    private String ag_photo="";
    private int    ag_ldap_synchro=2;      /* 1=synchro, 2=non synchro */
    private int    ag_ldap_profil_ddt=0;   /* 5 : le chef ... :-)
                                              4 : les exploitants
                                              3 : ????
                                              2 : les demandeurs
                                              1 : les lecteurs
                                              0 : rien                 */
    private int    ag_ldap_profil_annu=0;  /* 0 : rien
                                              2 : secretaire
                                              3 : 
                                              5 : sys
                                              6 : admin,               */
    
    private String ag_ldap_profil_asa="";
    private String ag_ldap_otpdatesignature="";
    private String ag_ldap_otpserie="";
    private String ag_gemalto_serie="";
    private int    ag_gemalto_num=0;
    private String ag_gemalto_charte_signee="";
    private String ag_gemalto_date_restitution="";
    private String ag_gemalto_date_expiration="";
    private String ag_gemalto_commentaire="";
    private int     ag_integrite=0;
    // bureau
    private int bu_code=0;
    private int bu_tri=0;
    private String bu_nom="";
    private String bu_nomc="";
    private String bu_mission="";
    private String bu_mail="";
    private String bu_telephone="";
    private String bu_fax="";
    private String bu_adresse="";
    private int bu_integrite=0;
    // service
    private int sv_code=0;
    private int sv_tri=0;
    private String sv_nom="";
    private String sv_nomc="";
    private String sv_mission="";
    private String sv_mail="";
    private String sv_telephone="";
    private String sv_fax="";
    private String sv_adresse="";
    private int sv_integrite=0;
    // division
    private int dv_code=0;
    private int dv_tri=0;
    private String dv_nom="";
    private String dv_nomc="";
    private String dv_mission="";
    private String dv_mail="";
    private String dv_telephone="";
    private String dv_fax="";
    private String dv_adresse="";
    
    private int dv_integrite=0;
     
    // partage
    private int pa_code=0;
    private int pa_dv_code=0;
    private String pa_nom="";
    private String pa_nomc="";
    private String pa_description="";
    public String getPa_description() {
		return pa_description;
	}

	public void setPa_description(String pa_description) {
		this.pa_description = pa_description;
	}

	private String pa_date_creation="";
    private int pa_ag_code_proprio=0;
    private int pa_integrite=0;
    // pole
    private int pl_code=0;
    private String pl_nom="";
    private String pl_nomc="";
    private String pl_mission="";
    private int pl_integrite=0;
    // affectation partage
    private int ap_code=0;
    public int getAp_type() {
		return ap_type;
	}

	public void setAp_type(int ap_type) {
		this.ap_type = ap_type;
	}

	public String getAp_droits() {
		return ap_droits;
	}

	public void setAp_droits(String ap_droits) {
		this.ap_droits = ap_droits;
	}

	private int ap_ag_code=0;
    private int ap_pa_code=0;
    private int ap_type=0;
    private String ap_droits="";
    // affectation
    private int af_code=0;
    private int af_ag_code=0;
    private int af_bu_code=0;
    private int af_sv_code=0;
    private int af_dv_code=0;
    private int af_pl_code=0;
    private int af_type=0;
    private String af_libelle_type="";
    private String af_mission="";
    private String af_telephone="";
    private String af_telephone_dir="";
    private String af_bureau="";
    private int af_integrite=0;
    //grade
    private int gr_code=0;
    private String gr_libc="";   /* grade libelle court    */
    private String gr_libl="";   /* grade libelle long     */
    private String gr_cat="";
    
    /**
     * Getter for property af_ag_code.
     * @return Value of property af_ag_code.
     */
    public int getAf_ag_code() {
        return af_ag_code;
    }
    
    /**
     * Setter for property af_ag_code.
     * @param af_ag_code New value of property af_ag_code.
     */
    public void setAf_ag_code(int af_ag_code) {
        this.af_ag_code = af_ag_code;
    }
    
    /**
     * Getter for property af_bu_code.
     * @return Value of property af_bu_code.
     */
    public int getAf_bu_code() {
        return af_bu_code;
    }
    
    /**
     * Setter for property af_bu_code.
     * @param af_bu_code New value of property af_bu_code.
     */
    public void setAf_bu_code(int af_bu_code) {
        this.af_bu_code = af_bu_code;
    }
    
    /**
     * Getter for property af_bureau.
     * @return Value of property af_bureau.
     */
    public java.lang.String getAf_bureau() {
        return af_bureau;
    }
    
    /**
     * Setter for property af_bureau.
     * @param af_bureau New value of property af_bureau.
     */
    public void setAf_bureau(java.lang.String af_bureau) {
        this.af_bureau = af_bureau;
    }
    
    /**
     * Getter for property af_code.
     * @return Value of property af_code.
     */
    public int getAf_code() {
        return af_code;
    }
    
    /**
     * Setter for property af_code.
     * @param af_code New value of property af_code.
     */
    public void setAf_code(int af_code) {
        this.af_code = af_code;
    }
    
    /**
     * Getter for property af_dv_code.
     * @return Value of property af_dv_code.
     */
    public int getAf_dv_code() {
        return af_dv_code;
    }
    
    /**
     * Setter for property af_dv_code.
     * @param af_dv_code New value of property af_dv_code.
     */
    public void setAf_dv_code(int af_dv_code) {
        this.af_dv_code = af_dv_code;
    }
    
    /**
     * Getter for property af_integrite.
     * @return Value of property af_integrite.
     */
    public int getAf_integrite() {
        return af_integrite;
    }
    
    /**
     * Setter for property af_integrite.
     * @param af_integrite New value of property af_integrite.
     */
    public void setAf_integrite(int af_integrite) {
        this.af_integrite = af_integrite;
    }
    
    /**
     * Getter for property af_libelle_type.
     * @return Value of property af_libelle_type.
     */
    public java.lang.String getAf_libelle_type() {
        return af_libelle_type;
    }
    
    /**
     * Setter for property af_libelle_type.
     * @param af_libelle_type New value of property af_libelle_type.
     */
    public void setAf_libelle_type(java.lang.String af_libelle_type) {
        this.af_libelle_type = af_libelle_type;
    }
    
    /**
     * Getter for property af_mission.
     * @return Value of property af_mission.
     */
    public java.lang.String getAf_mission() {
        return af_mission;
    }
    
    /**
     * Setter for property af_mission.
     * @param af_mission New value of property af_mission.
     */
    public void setAf_mission(java.lang.String af_mission) {
        this.af_mission = af_mission;
    }
    
    /**
     * Getter for property af_pl_code.
     * @return Value of property af_pl_code.
     */
    public int getAf_pl_code() {
        return af_pl_code;
    }
    
    /**
     * Setter for property af_pl_code.
     * @param af_pl_code New value of property af_pl_code.
     */
    public void setAf_pl_code(int af_pl_code) {
        this.af_pl_code = af_pl_code;
    }
    
    /**
     * Getter for property af_sv_code.
     * @return Value of property af_sv_code.
     */
    public int getAf_sv_code() {
        return af_sv_code;
    }
    
    /**
     * Setter for property af_sv_code.
     * @param af_sv_code New value of property af_sv_code.
     */
    public void setAf_sv_code(int af_sv_code) {
        this.af_sv_code = af_sv_code;
    }
    
    /**
     * Getter for property af_telephone.
     * @return Value of property af_telephone.
     */
    public java.lang.String getAf_telephone() {
        return af_telephone;
    }
    
    /**
     * Setter for property af_telephone.
     * @param af_telephone New value of property af_telephone.
     */
    public void setAf_telephone(java.lang.String af_telephone) {
        this.af_telephone = af_telephone;
    }
    
    /**
     * Getter for property af_type.
     * @return Value of property af_type.
     */
    public int getAf_type() {
        return af_type;
    }
    
    /**
     * Setter for property af_type.
     * @param af_type New value of property af_type.
     */
    public void setAf_type(int af_type) {
        this.af_type = af_type;
    }
    
    /**
     * Getter for property ag_code.
     * @return Value of property ag_code.
     */
    public int getAg_code() {
        return ag_code;
    }
    
    /**
     * Setter for property ag_code.
     * @param ag_code New value of property ag_code.
     */
    public void setAg_code(int ag_code) {
        this.ag_code = ag_code;
    }
    
    /**
     * Getter for property ag_gsm.
     * @return Value of property ag_gsm.
     */
    public java.lang.String getAg_gsm() {
        return ag_gsm;
    }
    
    /**
     * Setter for property ag_gsm.
     * @param ag_gsm New value of property ag_gsm.
     */
    public void setAg_gsm(java.lang.String ag_gsm) {
        this.ag_gsm = ag_gsm;
    }
    
    /**
     * Getter for property ag_ldap_dn.
     * @return Value of property ag_ldap_dn.
     */
    public java.lang.String getAg_ldap_dn() {
        return ag_ldap_dn;
    }
    
    /**
     * Setter for property ag_ldap_dn.
     * @param ag_ldap_dn New value of property ag_ldap_dn.
     */
    public void setAg_ldap_dn(java.lang.String ag_ldap_dn) {
        this.ag_ldap_dn = ag_ldap_dn;
    }
    
    /**
     * Getter for property ag_ldap_grade.
     * @return Value of property ag_ldap_grade.
     */
    public java.lang.String getAg_ldap_grade() {
        return ag_ldap_grade;
    }
    
    /**
     * Setter for property ag_ldap_grade.
     * @param ag_ldap_grade New value of property ag_ldap_grade.
     */
    public void setAg_ldap_grade(java.lang.String ag_ldap_grade) {
        this.ag_ldap_grade = ag_ldap_grade;
    }
    
    /**
     * Getter for property ag_ldap_mail.
     * @return Value of property ag_ldap_mail.
     */
    public java.lang.String getAg_ldap_mail() {
        return ag_ldap_mail;
    }
    
    /**
     * Setter for property ag_ldap_mail.
     * @param ag_ldap_mail New value of property ag_ldap_mail.
     */
    public void setAg_ldap_mail(java.lang.String ag_ldap_mail) {
        this.ag_ldap_mail = ag_ldap_mail;
    }
    
    /**
     * Getter for property ag_ldap_nom.
     * @return Value of property ag_ldap_nom.
     */
    public java.lang.String getAg_ldap_nom() {
        return ag_ldap_nom;
    }
    
    /**
     * Setter for property ag_ldap_nom.
     * @param ag_ldap_nom New value of property ag_ldap_nom.
     */
    public void setAg_ldap_nom(java.lang.String ag_ldap_nom) {
        this.ag_ldap_nom = ag_ldap_nom;
    }
    
    /**
     * Getter for property ag_ldap_numen.
     * @return Value of property ag_ldap_numen.
     */
    public java.lang.String getAg_ldap_numen() {
        return ag_ldap_numen;
    }
    
    /**
     * Setter for property ag_ldap_numen.
     * @param ag_ldap_numen New value of property ag_ldap_numen.
     */
    public void setAg_ldap_numen(java.lang.String ag_ldap_numen) {
        this.ag_ldap_numen = ag_ldap_numen;
    }
    
    /**
     * Getter for property ag_ldap_prenom.
     * @return Value of property ag_ldap_prenom.
     */
    public java.lang.String getAg_ldap_prenom() {
        return ag_ldap_prenom;
    }
    
    /**
     * Setter for property ag_ldap_prenom.
     * @param ag_ldap_prenom New value of property ag_ldap_prenom.
     */
    public void setAg_ldap_prenom(java.lang.String ag_ldap_prenom) {
        this.ag_ldap_prenom = ag_ldap_prenom;
    }
    
    /**
     * Getter for property ag_ldap_profil_annu.
     * @return Value of property ag_ldap_profil_annu.
     */
    public int getAg_ldap_profil_annu() {
        return ag_ldap_profil_annu;
    }
    
    /**
     * Setter for property ag_ldap_profil_annu.
     * @param ag_ldap_profil_annu New value of property ag_ldap_profil_annu.
     */
    public void setAg_ldap_profil_annu(int ag_ldap_profil_annu) {
        this.ag_ldap_profil_annu = ag_ldap_profil_annu;
    }
    
    /**
     * Getter for property ag_ldap_profil_ddt.
     * @return Value of property ag_ldap_profil_ddt.
     */
    public int getAg_ldap_profil_ddt() {
        return ag_ldap_profil_ddt;
    }
    
    /**
     * Setter for property ag_ldap_profil_ddt.
     * @param ag_ldap_profil_ddt New value of property ag_ldap_profil_ddt.
     */
    public void setAg_ldap_profil_ddt(int ag_ldap_profil_ddt) {
        this.ag_ldap_profil_ddt = ag_ldap_profil_ddt;
    }
    
    /**
     * Getter for property ag_ldap_synchro.
     * @return Value of property ag_ldap_synchro.
     */
    public int getAg_ldap_synchro() {
        return ag_ldap_synchro;
    }
    
    /**
     * Setter for property ag_ldap_synchro.
     * @param ag_ldap_synchro New value of property ag_ldap_synchro.
     */
    public void setAg_ldap_synchro(int ag_ldap_synchro) {
        this.ag_ldap_synchro = ag_ldap_synchro;
    }
    
    /**
     * Getter for property ag_ldap_title.
     * @return Value of property ag_ldap_title.
     */
    public java.lang.String getAg_ldap_title() {
        return ag_ldap_title;
    }
    
    /**
     * Setter for property ag_ldap_title.
     * @param ag_ldap_title New value of property ag_ldap_title.
     */
    public void setAg_ldap_title(java.lang.String ag_ldap_title) {
        this.ag_ldap_title = ag_ldap_title;
    }
    
    /**
     * Getter for property ag_ldap_uid.
     * @return Value of property ag_ldap_uid.
     */
    public java.lang.String getAg_ldap_uid() {
        return ag_ldap_uid;
    }
    
    /**
     * Setter for property ag_ldap_uid.
     * @param ag_ldap_uid New value of property ag_ldap_uid.
     */
    public void setAg_ldap_uid(java.lang.String ag_ldap_uid) {
        this.ag_ldap_uid = ag_ldap_uid;
    }
    
    /**
     * Getter for property ag_photo.
     * @return Value of property ag_photo.
     */
    public java.lang.String getAg_photo() {
        return ag_photo;
    }
    
    /**
     * Setter for property ag_photo.
     * @param ag_photo New value of property ag_photo.
     */
    public void setAg_photo(java.lang.String ag_photo) {
        this.ag_photo = ag_photo;
    }
    
    /**
     * Getter for property bu_adresse.
     * @return Value of property bu_adresse.
     */
    public java.lang.String getBu_adresse() {
        return bu_adresse;
    }
    
    /**
     * Setter for property bu_adresse.
     * @param bu_adresse New value of property bu_adresse.
     */
    public void setBu_adresse(java.lang.String bu_adresse) {
        this.bu_adresse = bu_adresse;
    }
    
    /**
     * Getter for property bu_code.
     * @return Value of property bu_code.
     */
    public int getBu_code() {
        return bu_code;
    }
    
    /**
     * Setter for property bu_code.
     * @param bu_code New value of property bu_code.
     */
    public void setBu_code(int bu_code) {
        this.bu_code = bu_code;
    }
    
    /**
     * Getter for property bu_fax.
     * @return Value of property bu_fax.
     */
    public java.lang.String getBu_fax() {
        return bu_fax;
    }
    
    /**
     * Setter for property bu_fax.
     * @param bu_fax New value of property bu_fax.
     */
    public void setBu_fax(java.lang.String bu_fax) {
        this.bu_fax = bu_fax;
    }
    
    /**
     * Getter for property bu_integrite.
     * @return Value of property bu_integrite.
     */
    public int getBu_integrite() {
        return bu_integrite;
    }
    
    /**
     * Setter for property bu_integrite.
     * @param bu_integrite New value of property bu_integrite.
     */
    public void setBu_integrite(int bu_integrite) {
        this.bu_integrite = bu_integrite;
    }
    
    /**
     * Getter for property bu_mail.
     * @return Value of property bu_mail.
     */
    public java.lang.String getBu_mail() {
        return bu_mail;
    }
    
    /**
     * Setter for property bu_mail.
     * @param bu_mail New value of property bu_mail.
     */
    public void setBu_mail(java.lang.String bu_mail) {
        this.bu_mail = bu_mail;
    }
    
    /**
     * Getter for property bu_mission.
     * @return Value of property bu_mission.
     */
    public java.lang.String getBu_mission() {
        return bu_mission;
    }
    
    /**
     * Setter for property bu_mission.
     * @param bu_mission New value of property bu_mission.
     */
    public void setBu_mission(java.lang.String bu_mission) {
        this.bu_mission = bu_mission;
    }
    
    /**
     * Getter for property bu_nom.
     * @return Value of property bu_nom.
     */
    public java.lang.String getBu_nom() {
        return bu_nom;
    }
    
    /**
     * Setter for property bu_nom.
     * @param bu_nom New value of property bu_nom.
     */
    public void setBu_nom(java.lang.String bu_nom) {
        this.bu_nom = bu_nom;
    }
    
    /**
     * Getter for property bu_nomc.
     * @return Value of property bu_nomc.
     */
    public java.lang.String getBu_nomc() {
        return bu_nomc;
    }
    
    /**
     * Setter for property bu_nomc.
     * @param bu_nomc New value of property bu_nomc.
     */
    public void setBu_nomc(java.lang.String bu_nomc) {
        this.bu_nomc = bu_nomc;
    }
    
    /**
     * Getter for property bu_telephone.
     * @return Value of property bu_telephone.
     */
    public java.lang.String getBu_telephone() {
        return bu_telephone;
    }
    
    /**
     * Setter for property bu_telephone.
     * @param bu_telephone New value of property bu_telephone.
     */
    public void setBu_telephone(java.lang.String bu_telephone) {
        this.bu_telephone = bu_telephone;
    }
    
    /**
     * Getter for property dv_adresse.
     * @return Value of property dv_adresse.
     */
    public java.lang.String getDv_adresse() {
        return dv_adresse;
    }
    
    /**
     * Setter for property dv_adresse.
     * @param dv_adresse New value of property dv_adresse.
     */
    public void setDv_adresse(java.lang.String dv_adresse) {
        this.dv_adresse = dv_adresse;
    }
    
    /**
     * Getter for property dv_code.
     * @return Value of property dv_code.
     */
    public int getDv_code() {
        return dv_code;
    }
    
    /**
     * Setter for property dv_code.
     * @param dv_code New value of property dv_code.
     */
    public void setDv_code(int dv_code) {
        this.dv_code = dv_code;
    }
    
    /**
     * Getter for property dv_fax.
     * @return Value of property dv_fax.
     */
    public java.lang.String getDv_fax() {
        return dv_fax;
    }
    
    /**
     * Setter for property dv_fax.
     * @param dv_fax New value of property dv_fax.
     */
    public void setDv_fax(java.lang.String dv_fax) {
        this.dv_fax = dv_fax;
    }
    
    /**
     * Getter for property dv_integrite.
     * @return Value of property dv_integrite.
     */
    public int getDv_integrite() {
        return dv_integrite;
    }
    
    /**
     * Setter for property dv_integrite.
     * @param dv_integrite New value of property dv_integrite.
     */
    public void setDv_integrite(int dv_integrite) {
        this.dv_integrite = dv_integrite;
    }
    
    /**
     * Getter for property dv_mail.
     * @return Value of property dv_mail.
     */
    public java.lang.String getDv_mail() {
        return dv_mail;
    }
    
    /**
     * Setter for property dv_mail.
     * @param dv_mail New value of property dv_mail.
     */
    public void setDv_mail(java.lang.String dv_mail) {
        this.dv_mail = dv_mail;
    }
    
    /**
     * Getter for property dv_mission.
     * @return Value of property dv_mission.
     */
    public java.lang.String getDv_mission() {
        return dv_mission;
    }
    
    /**
     * Setter for property dv_mission.
     * @param dv_mission New value of property dv_mission.
     */
    public void setDv_mission(java.lang.String dv_mission) {
        this.dv_mission = dv_mission;
    }
    
    /**
     * Getter for property dv_nom.
     * @return Value of property dv_nom.
     */
    public java.lang.String getDv_nom() {
        return dv_nom;
    }
    
    /**
     * Setter for property dv_nom.
     * @param dv_nom New value of property dv_nom.
     */
    public void setDv_nom(java.lang.String dv_nom) {
        this.dv_nom = dv_nom;
    }
    
    /**
     * Getter for property dv_nomc.
     * @return Value of property dv_nomc.
     */
    public java.lang.String getDv_nomc() {
        return dv_nomc;
    }
    
    /**
     * Setter for property dv_nomc.
     * @param dv_nomc New value of property dv_nomc.
     */
    public void setDv_nomc(java.lang.String dv_nomc) {
        this.dv_nomc = dv_nomc;
    }
    
    /**
     * Getter for property dv_telephone.
     * @return Value of property dv_telephone.
     */
    public java.lang.String getDv_telephone() {
        return dv_telephone;
    }
    
    /**
     * Setter for property dv_telephone.
     * @param dv_telephone New value of property dv_telephone.
     */
    public void setDv_telephone(java.lang.String dv_telephone) {
        this.dv_telephone = dv_telephone;
    }
    
    /**
     * Getter for property gr_cat.
     * @return Value of property gr_cat.
     */
    public java.lang.String getGr_cat() {
        return gr_cat;
    }
    
    /**
     * Setter for property gr_cat.
     * @param gr_cat New value of property gr_cat.
     */
    public void setGr_cat(java.lang.String gr_cat) {
        this.gr_cat = gr_cat;
    }
    
    /**
     * Getter for property gr_code.
     * @return Value of property gr_code.
     */
    public int getGr_code() {
        return gr_code;
    }
    
    /**
     * Setter for property gr_code.
     * @param gr_code New value of property gr_code.
     */
    public void setGr_code(int gr_code) {
        this.gr_code = gr_code;
    }
    
    /**
     * Getter for property gr_libc.
     * @return Value of property gr_libc.
     */
    public java.lang.String getGr_libc() {
        return gr_libc;
    }
    
    /**
     * Setter for property gr_libc.
     * @param gr_libc New value of property gr_libc.
     */
    public void setGr_libc(java.lang.String gr_libc) {
        this.gr_libc = gr_libc;
    }
    
    /**
     * Getter for property gr_libl.
     * @return Value of property gr_libl.
     */
    public java.lang.String getGr_libl() {
        return gr_libl;
    }
    
    /**
     * Setter for property gr_libl.
     * @param gr_libl New value of property gr_libl.
     */
    public void setGr_libl(java.lang.String gr_libl) {
        this.gr_libl = gr_libl;
    }
    
    /**
     * Getter for property pl_code.
     * @return Value of property pl_code.
     */
    public int getPl_code() {
        return pl_code;
    }
    
    /**
     * Setter for property pl_code.
     * @param pl_code New value of property pl_code.
     */
    public void setPl_code(int pl_code) {
        this.pl_code = pl_code;
    }
    
    /**
     * Getter for property pl_integrite.
     * @return Value of property pl_integrite.
     */
    public int getPl_integrite() {
        return pl_integrite;
    }
    
    /**
     * Setter for property pl_integrite.
     * @param pl_integrite New value of property pl_integrite.
     */
    public void setPl_integrite(int pl_integrite) {
        this.pl_integrite = pl_integrite;
    }
    
    /**
     * Getter for property pl_mission.
     * @return Value of property pl_mission.
     */
    public java.lang.String getPl_mission() {
        return pl_mission;
    }
    
    /**
     * Setter for property pl_mission.
     * @param pl_mission New value of property pl_mission.
     */
    public void setPl_mission(java.lang.String pl_mission) {
        this.pl_mission = pl_mission;
    }
    
    /**
     * Getter for property pl_nom.
     * @return Value of property pl_nom.
     */
    public java.lang.String getPl_nom() {
        return pl_nom;
    }
    
    /**
     * Setter for property pl_nom.
     * @param pl_nom New value of property pl_nom.
     */
    public void setPl_nom(java.lang.String pl_nom) {
        this.pl_nom = pl_nom;
    }
    
    /**
     * Getter for property pl_nomc.
     * @return Value of property pl_nomc.
     */
    public java.lang.String getPl_nomc() {
        return pl_nomc;
    }
    
    /**
     * Setter for property pl_nomc.
     * @param pl_nomc New value of property pl_nomc.
     */
    public void setPl_nomc(java.lang.String pl_nomc) {
        this.pl_nomc = pl_nomc;
    }
    
    /**
     * Getter for property sv_adresse.
     * @return Value of property sv_adresse.
     */
    public java.lang.String getSv_adresse() {
        return sv_adresse;
    }
    
    /**
     * Setter for property sv_adresse.
     * @param sv_adresse New value of property sv_adresse.
     */
    public void setSv_adresse(java.lang.String sv_adresse) {
        this.sv_adresse = sv_adresse;
    }
    
    /**
     * Getter for property sv_code.
     * @return Value of property sv_code.
     */
    public int getSv_code() {
        return sv_code;
    }
    
    /**
     * Setter for property sv_code.
     * @param sv_code New value of property sv_code.
     */
    public void setSv_code(int sv_code) {
        this.sv_code = sv_code;
    }
    
    /**
     * Getter for property sv_fax.
     * @return Value of property sv_fax.
     */
    public java.lang.String getSv_fax() {
        return sv_fax;
    }
    
    /**
     * Setter for property sv_fax.
     * @param sv_fax New value of property sv_fax.
     */
    public void setSv_fax(java.lang.String sv_fax) {
        this.sv_fax = sv_fax;
    }
    
    /**
     * Getter for property sv_integrite.
     * @return Value of property sv_integrite.
     */
    public int getSv_integrite() {
        return sv_integrite;
    }
    
    /**
     * Setter for property sv_integrite.
     * @param sv_integrite New value of property sv_integrite.
     */
    public void setSv_integrite(int sv_integrite) {
        this.sv_integrite = sv_integrite;
    }
    
    /**
     * Getter for property sv_mail.
     * @return Value of property sv_mail.
     */
    public java.lang.String getSv_mail() {
        return sv_mail;
    }
    
    /**
     * Setter for property sv_mail.
     * @param sv_mail New value of property sv_mail.
     */
    public void setSv_mail(java.lang.String sv_mail) {
        this.sv_mail = sv_mail;
    }
    
    /**
     * Getter for property sv_mission.
     * @return Value of property sv_mission.
     */
    public java.lang.String getSv_mission() {
        return sv_mission;
    }
    
    /**
     * Setter for property sv_mission.
     * @param sv_mission New value of property sv_mission.
     */
    public void setSv_mission(java.lang.String sv_mission) {
        this.sv_mission = sv_mission;
    }
    
    /**
     * Getter for property sv_nom.
     * @return Value of property sv_nom.
     */
    public java.lang.String getSv_nom() {
        return sv_nom;
    }
    
    /**
     * Setter for property sv_nom.
     * @param sv_nom New value of property sv_nom.
     */
    public void setSv_nom(java.lang.String sv_nom) {
        this.sv_nom = sv_nom;
    }
    
    /**
     * Getter for property sv_nomc.
     * @return Value of property sv_nomc.
     */
    public java.lang.String getSv_nomc() {
        return sv_nomc;
    }
    
    /**
     * Setter for property sv_nomc.
     * @param sv_nomc New value of property sv_nomc.
     */
    public void setSv_nomc(java.lang.String sv_nomc) {
        this.sv_nomc = sv_nomc;
    }
    
    /**
     * Getter for property sv_telephone.
     * @return Value of property sv_telephone.
     */
    public java.lang.String getSv_telephone() {
        return sv_telephone;
    }
    
    /**
     * Setter for property sv_telephone.
     * @param sv_telephone New value of property sv_telephone.
     */
    public void setSv_telephone(java.lang.String sv_telephone) {
        this.sv_telephone = sv_telephone;
    }
    
    /**
     * Getter for property ag_integrite.
     * @return Value of property ag_integrite.
     */
    public int getAg_integrite() {
        return ag_integrite;
    }
    
    /**
     * Setter for property ag_integrite.
     * @param ag_integrite New value of property ag_integrite.
     */
    public void setAg_integrite(int ag_integrite) {
        this.ag_integrite = ag_integrite;
    }
    
    /**
     * Getter for property bu_tri.
     * @return Value of property bu_tri.
     */
    public int getBu_tri() {
        return bu_tri;
    }
    
    /**
     * Setter for property bu_tri.
     * @param bu_tri New value of property bu_tri.
     */
    public void setBu_tri(int bu_tri) {
        this.bu_tri = bu_tri;
    }
    
    /**
     * Getter for property sv_tri.
     * @return Value of property sv_tri.
     */
    public int getSv_tri() {
        return sv_tri;
    }
    
    /**
     * Setter for property sv_tri.
     * @param sv_tri New value of property sv_tri.
     */
    public void setSv_tri(int sv_tri) {
        this.sv_tri = sv_tri;
    }
    public int compareTo(Object anotherAnnuaire) throws ClassCastException {
        if (!(anotherAnnuaire instanceof ItemAnnuaire))
            throw new ClassCastException("A Person object expected.");
        String anotherNom    = ((ItemAnnuaire) anotherAnnuaire).getAg_ldap_nom();
        String anotherPrenom = ((ItemAnnuaire) anotherAnnuaire).getAg_ldap_prenom();
        String anotherAsa    = ((ItemAnnuaire) anotherAnnuaire).getAg_ldap_profil_asa();
        
        String anothernompre = anotherAsa + anotherNom + anotherPrenom;
        String nompre = this.ag_ldap_profil_asa + this.ag_ldap_nom + this.ag_ldap_prenom;
        return(nompre.compareToIgnoreCase(anothernompre));
    }
    
    /**
     * Getter for property dv_tri.
     * @return Value of property dv_tri.
     */
    public int getDv_tri() {
        return dv_tri;
    }
    
    /**
     * Setter for property dv_tri.
     * @param dv_tri New value of property dv_tri.
     */
    public void setDv_tri(int dv_tri) {
        this.dv_tri = dv_tri;
    }
    
    /**
     * Getter for property af_telephone_dir.
     * @return Value of property af_telephone_dir.
     */
    public java.lang.String getAf_telephone_dir() {
        return af_telephone_dir;
    }
    
    /**
     * Setter for property af_telephone_dir.
     * @param af_telephone_dir New value of property af_telephone_dir.
     */
    public void setAf_telephone_dir(java.lang.String af_telephone_dir) {
        this.af_telephone_dir = af_telephone_dir;
    }
    
    /**
     * Getter for property ag_ldap_pw.
     * @return Value of property ag_ldap_pw.
     */
    public java.lang.String getAg_ldap_pw() {
        return ag_ldap_pw;
    }
    
    /**
     * Setter for property ag_ldap_pw.
     * @param ag_ldap_pw New value of property ag_ldap_pw.
     */
    public void setAg_ldap_pw(java.lang.String ag_ldap_pw) {
        this.ag_ldap_pw = ag_ldap_pw;
    }
    
    /**
     * Getter for property ag_ldap_profil_asa.
     * @return Value of property ag_ldap_profil_asa.
     */
    public java.lang.String getAg_ldap_profil_asa() {
        return ag_ldap_profil_asa;
    }
    
    /**
     * Setter for property ag_ldap_profil_asa.
     * @param ag_ldap_profil_asa New value of property ag_ldap_profil_asa.
     */
    public void setAg_ldap_profil_asa(java.lang.String ag_ldap_profil_asa) {
        this.ag_ldap_profil_asa = ag_ldap_profil_asa;
    }
    
    /**
     * Getter for property ag_gemalto_charte_signee.
     * @return Value of property ag_gemalto_charte_signee.
     */
    public java.lang.String getAg_gemalto_charte_signee() {
        return ag_gemalto_charte_signee;
    }
    
    /**
     * Setter for property ag_gemalto_charte_signee.
     * @param ag_gemalto_charte_signee New value of property ag_gemalto_charte_signee.
     */
    public void setAg_gemalto_charte_signee(java.lang.String ag_gemalto_charte_signee) {
        this.ag_gemalto_charte_signee = ag_gemalto_charte_signee;
    }
    
    /**
     * Getter for property ag_gemalto_num.
     * @return Value of property ag_gemalto_num.
     */
    
    
    /**
     * Getter for property ag_gemalto_serie.
     * @return Value of property ag_gemalto_serie.
     */
    public java.lang.String getAg_gemalto_serie() {
        return ag_gemalto_serie;
    }
    
    /**
     * Setter for property ag_gemalto_serie.
     * @param ag_gemalto_serie New value of property ag_gemalto_serie.
     */
    public void setAg_gemalto_serie(java.lang.String ag_gemalto_serie) {
        this.ag_gemalto_serie = ag_gemalto_serie;
    }
    
    /**
     * Getter for property ag_gemalto_num.
     * @return Value of property ag_gemalto_num.
     */
    public int getAg_gemalto_num() {
        return ag_gemalto_num;
    }    
    
    /**
     * Setter for property ag_gemalto_num.
     * @param ag_gemalto_num New value of property ag_gemalto_num.
     */
    public void setAg_gemalto_num(int ag_gemalto_num) {
        this.ag_gemalto_num = ag_gemalto_num;
    }    
    
    /**
     * Getter for property ag_gemalto_commentaire.
     * @return Value of property ag_gemalto_commentaire.
     */
    public java.lang.String getAg_gemalto_commentaire() {
        return ag_gemalto_commentaire;
    }
    
    /**
     * Setter for property ag_gemalto_commentaire.
     * @param ag_gemalto_commentaire New value of property ag_gemalto_commentaire.
     */
    public void setAg_gemalto_commentaire(java.lang.String ag_gemalto_commentaire) {
        this.ag_gemalto_commentaire = ag_gemalto_commentaire;
    }
    
    /**
     * Getter for property ag_ldap_otpdatesignature.
     * @return Value of property ag_ldap_otpdatesignature.
     */
    public java.lang.String getAg_ldap_otpdatesignature() {
        return ag_ldap_otpdatesignature;
    }
    
    /**
     * Setter for property ag_ldap_otpdatesignature.
     * @param ag_ldap_otpdatesignature New value of property ag_ldap_otpdatesignature.
     */
    public void setAg_ldap_otpdatesignature(java.lang.String ag_ldap_otpdatesignature) {
        this.ag_ldap_otpdatesignature = ag_ldap_otpdatesignature;
    }
    
    /**
     * Getter for property ag_ldap_annu_code.
     * @return Value of property ag_ldap_annu_code.
     */
    public int getAg_ldap_annu_code() {
        return ag_ldap_annu_code;
    }
    
    /**
     * Setter for property ag_ldap_annu_code.
     * @param ag_ldap_annu_code New value of property ag_ldap_annu_code.
     */
    public void setAg_ldap_annu_code(int ag_ldap_annu_code) {
        this.ag_ldap_annu_code = ag_ldap_annu_code;
    }
    
    /**
     * Getter for property ag_ldap_fredufonctadm.
     * @return Value of property ag_ldap_fredufonctadm.
     */
    public java.lang.String getAg_ldap_fredufonctadm() {
        return ag_ldap_fredufonctadm;
    }
    
    /**
     * Setter for property ag_ldap_fredufonctadm.
     * @param ag_ldap_fredufonctadm New value of property ag_ldap_fredufonctadm.
     */
    public void setAg_ldap_fredufonctadm(java.lang.String ag_ldap_fredufonctadm) {
        this.ag_ldap_fredufonctadm = ag_ldap_fredufonctadm;
    }
    
    /**
     * Getter for property ag_ldap_datenaiss.
     * @return Value of property ag_ldap_datenaiss.
     */
    public java.lang.String getAg_ldap_datenaiss() {
        return ag_ldap_datenaiss;
    }
    
    /**
     * Setter for property ag_ldap_datenaiss.
     * @param ag_ldap_datenaiss New value of property ag_ldap_datenaiss.
     */
    public void setAg_ldap_datenaiss(java.lang.String ag_ldap_datenaiss) {
        this.ag_ldap_datenaiss = ag_ldap_datenaiss;
    }
    
    /**
     * Getter for property ag_ldap_otpserie.
     * @return Value of property ag_ldap_otpserie.
     */
    public java.lang.String getAg_ldap_otpserie() {
        return ag_ldap_otpserie;
    }
    
    /**
     * Setter for property ag_ldap_otpserie.
     * @param ag_ldap_otpserie New value of property ag_ldap_otpserie.
     */
    public void setAg_ldap_otpserie(java.lang.String ag_ldap_otpserie) {
        this.ag_ldap_otpserie = ag_ldap_otpserie;
    }
    
    /**
     * Getter for property ag_ldap_annu_division.
     * @return Value of property ag_ldap_annu_division.
     */
    public java.lang.String getAg_ldap_annu_division() {
        return ag_ldap_annu_division;
    }
    
    /**
     * Setter for property ag_ldap_annu_division.
     * @param ag_ldap_annu_division New value of property ag_ldap_annu_division.
     */
    public void setAg_ldap_annu_division(java.lang.String ag_ldap_annu_division) {
        this.ag_ldap_annu_division = ag_ldap_annu_division;
    }
    
    /**
     * Getter for property pa_ag_code_proprio.
     * @return Value of property pa_ag_code_proprio.
     */
    public int getPa_ag_code_proprio() {
        return pa_ag_code_proprio;
    }
    
    /**
     * Setter for property pa_ag_code_proprio.
     * @param pa_ag_code_proprio New value of property pa_ag_code_proprio.
     */
    public void setPa_ag_code_proprio(int pa_ag_code_proprio) {
        this.pa_ag_code_proprio = pa_ag_code_proprio;
    }
    
    /**
     * Getter for property pa_code.
     * @return Value of property pa_code.
     */
    public int getPa_code() {
        return pa_code;
    }
    
    /**
     * Setter for property pa_code.
     * @param pa_code New value of property pa_code.
     */
    public void setPa_code(int pa_code) {
        this.pa_code = pa_code;
    }
    
    /**
     * Getter for property pa_date_creation.
     * @return Value of property pa_date_creation.
     */
    public java.lang.String getPa_date_creation() {
        return pa_date_creation;
    }
    
    /**
     * Setter for property pa_date_creation.
     * @param pa_date_creation New value of property pa_date_creation.
     */
    public void setPa_date_creation(java.lang.String pa_date_creation) {
        this.pa_date_creation = pa_date_creation;
    }
    
    /**
     * Getter for property pa_dv_code.
     * @return Value of property pa_dv_code.
     */
    public int getPa_dv_code() {
        return pa_dv_code;
    }
    
    /**
     * Setter for property pa_dv_code.
     * @param pa_dv_code New value of property pa_dv_code.
     */
    public void setPa_dv_code(int pa_dv_code) {
        this.pa_dv_code = pa_dv_code;
    }
    
    /**
     * Getter for property pa_nom.
     * @return Value of property pa_nom.
     */
    public java.lang.String getPa_nom() {
        return pa_nom;
    }
    
    /**
     * Setter for property pa_nom.
     * @param pa_nom New value of property pa_nom.
     */
    public void setPa_nom(java.lang.String pa_nom) {
        this.pa_nom = pa_nom;
    }
    
    /**
     * Getter for property pa_nomc.
     * @return Value of property pa_nomc.
     */
    public java.lang.String getPa_nomc() {
        return pa_nomc;
    }
    
    /**
     * Setter for property pa_nomc.
     * @param pa_nomc New value of property pa_nomc.
     */
    public void setPa_nomc(java.lang.String pa_nomc) {
        this.pa_nomc = pa_nomc;
    }
    
    /**
     * Getter for property pa_integrite.
     * @return Value of property pa_integrite.
     */
    public int getPa_integrite() {
        return pa_integrite;
    }
    
    /**
     * Setter for property pa_integrite.
     * @param pa_integrite New value of property pa_integrite.
     */
    public void setPa_integrite(int pa_integrite) {
        this.pa_integrite = pa_integrite;
    }
    
    /**
     * Getter for property ap_ag_code.
     * @return Value of property ap_ag_code.
     */
    public int getAp_ag_code() {
        return ap_ag_code;
    }
    
    /**
     * Setter for property ap_ag_code.
     * @param ap_ag_code New value of property ap_ag_code.
     */
    public void setAp_ag_code(int ap_ag_code) {
        this.ap_ag_code = ap_ag_code;
    }
    
    /**
     * Getter for property ap_code.
     * @return Value of property ap_code.
     */
    public int getAp_code() {
        return ap_code;
    }
    
    /**
     * Setter for property ap_code.
     * @param ap_code New value of property ap_code.
     */
    public void setAp_code(int ap_code) {
        this.ap_code = ap_code;
    }
    
    /**
     * Getter for property ap_pa_code.
     * @return Value of property ap_pa_code.
     */
    public int getAp_pa_code() {
        return ap_pa_code;
    }
    
    /**
     * Setter for property ap_pa_code.
     * @param ap_pa_code New value of property ap_pa_code.
     */
    public void setAp_pa_code(int ap_pa_code) {
        this.ap_pa_code = ap_pa_code;
    }

	public String getAg_gemalto_date_restitution() {
		return ag_gemalto_date_restitution;
	}

	public void setAg_gemalto_date_restitution(String ag_gemalto_date_restitution) {
		this.ag_gemalto_date_restitution = ag_gemalto_date_restitution;
	}

	public String getAg_gemalto_date_expiration() {
		return ag_gemalto_date_expiration;
	}

	public void setAg_gemalto_date_expiration(String ag_gemalto_date_expiration) {
		this.ag_gemalto_date_expiration = ag_gemalto_date_expiration;
	}
    
}

