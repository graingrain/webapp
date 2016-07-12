package javabeans;
import java.io.*;
public class ItemApplication implements Serializable {
    // Domaine
    private String Do_nom;
    private String Do_libelle;
    private String Do_user;
    private String Do_passwd;
    private String Do_portc_etat;
    private String Do_portc_etat_date;
    private String Do_wa_type;
    private String Do_wa_libelle;
    private int Do_code;
    private int Do_ma_code;
    private int Do_wa_code;
    private int Do_portc;
    // 
    // Application
    
    private String Ap_type            = "";
    private String Ap_libelle         = "";
    private String Ap_description     = "";
    private String Ap_date_ouverture  = "";
    private String Ap_message         = "";
    private String Ap_date_fermeture  = "";
    private String Ap_murl            = "";
    private String Ap_url             = "";
    private String Ap_curl_chaine     = "";
    private String Ap_etat            = "";
    private String Ap_etat_date = "";
    private String Ap_ouvert          = "1"; // 0:ouvert  1:fermé   2:maintenance
    private String Ap_ouvert_lib      = "";
    private int    Ap_code = 0;
    private int    Ap_pt_code =0;
    private int    Ap_port= 0;
    private int    Ap_visible         = 0;  // 0,1 : oui      2 : non
    private int    Ap_controle        = 0;  // 0,1 : oui      2 : non
    //
    //  data du pi
    //  ----------
    private int Th_code         = 0 ;
    private String Th_libelle  = "";
    private String Th_couleur  = "";
    
    //  data du theme
    //  ----------
    private String Pi_nom     = "";
    private String Pi_libelle = "";
    private String Pi_portail = "";
    private String Pi_port_etat = "";
    private String Pi_port_etat_date = "";
    private int Pi_code    =0;
    private int Pi_ma_code =0;
    private int Pi_pt_code =0;
    private int Pi_iw_code =0;
    private int Pi_port    =0;
    //
    //  data du portail
    //  ---------------
    private String Pt_sigle        ="";
    private int    Pt_zone         =0;
    private int    Pt_code         =0;
    private int    Pt_ma_code      =0;
    private String Pt_type         ="";
    private String Pt_version      ="";
    private String Pt_repertoire   ="";
    private int    Pt_port         =0;
    private String Pt_port_etat    ="";;
    private String Pt_port_etat_date="";;
    //
    //  data de InstanceWA
    //  ------------------
    private String Iw_sigle        ="";
    private int    Iw_zone         =0;
    private int    Iw_code         =0;
    private int    Iw_ma_code      =0;;
    private int    Iw_do_code      =0;
    private String Iw_nom          ="";;
    private String Iw_libelle      ="";
    private String Iw_shell        ="";
    private int    Iw_port1        =0;
    private String Iw_port1_etat   ="";
    private String Iw_port1_etat_date="";
    private int    Iw_port2=0;
    private String Iw_port2_etat="";
    private String Iw_port2_etat_date="";
    //
    //  data de webapp
    //  ------------------
    private int    Wa_code;
    private int    Wa_ma_code;
    private String Wa_type="";
    private String Wa_repertoire="";
    private String Wa_version="";
    //
    //  data de applilog
    //  ------------------
    private int    Al_code = 0;
    private int    Al_ap_code = 0;
    private String Al_date="";
    private String Al_nom="";
    private String Al_etat="";
    private String Al_libetat="";
    //
    //  data de astreinte
    //  ------------------
    private int    As_code;
    private int    As_ap_code;
    private String As_nom;
    private String As_mail;
    private String As_gsm;
    //
    //  data de Bdd
    //  ------------------
    private String Bd_sigle         =""; // nom de la machine
    private int    Bd_zone          =0;
    private String Bd_type          ="";
    private String Bd_repertoire    ="";
    private String Bd_port_etat     ="";
    private String Bd_port_etat_date="";
    private int Bd_code             =0;
    private int Bd_ma_code          =0;
    private int Bd_port             =0;
    //
    // MACHINE
    private String ma_libelle;
    private String ma_sigle;
    private String ma_adresse;
    private String ma_etat;
    private String ma_etat_date;
    private int ma_nb_bdd;
    private int ma_code;
    private int ma_zn_code;
    // integrite
    //
    private int Nb_joint;
    
    // logapache
    private int    la_code=0;
    private String la_machine  ="";
    private String la_webapp   ="";
    private String la_port     ="";
    private String la_service  ="";
    private String la_etat_date="";
    private String la_etat     ="";
    
    /** Getter for property Ap_code.
     * @return Value of property Ap_code.
     *
     */
    public int getAp_code() {
        return Ap_code;
    }
    
    /** Setter for property Ap_code.
     * @param Ap_code New value of property Ap_code.
     *
     */
    public void setAp_code(int Ap_code) {
        this.Ap_code = Ap_code;
    }
    
    /** Getter for property Ap_controle.
     * @return Value of property Ap_controle.
     *
     */
    public int getAp_controle() {
        return Ap_controle;
    }
    
    /** Setter for property Ap_controle.
     * @param Ap_controle New value of property Ap_controle.
     *
     */
    public void setAp_controle(int Ap_controle) {
        this.Ap_controle = Ap_controle;
    }
    
    /** Getter for property Ap_curl_chaine.
     * @return Value of property Ap_curl_chaine.
     *
     */
    public java.lang.String getAp_curl_chaine() {
        return Ap_curl_chaine;
    }
    
    /** Setter for property Ap_curl_chaine.
     * @param Ap_curl_chaine New value of property Ap_curl_chaine.
     *
     */
    public void setAp_curl_chaine(java.lang.String Ap_curl_chaine) {
        this.Ap_curl_chaine = Ap_curl_chaine;
    }
    
    /** Getter for property Ap_date_fermeture.
     * @return Value of property Ap_date_fermeture.
     *
     */
    public java.lang.String getAp_date_fermeture() {
        return Ap_date_fermeture;
    }
    
    /** Setter for property Ap_date_fermeture.
     * @param Ap_date_fermeture New value of property Ap_date_fermeture.
     *
     */
    public void setAp_date_fermeture(java.lang.String Ap_date_fermeture) {
        this.Ap_date_fermeture = Ap_date_fermeture;
    }
    
    /** Getter for property Ap_date_ouverture.
     * @return Value of property Ap_date_ouverture.
     *
     */
    public java.lang.String getAp_date_ouverture() {
        return Ap_date_ouverture;
    }
    
    /** Setter for property Ap_date_ouverture.
     * @param Ap_date_ouverture New value of property Ap_date_ouverture.
     *
     */
    public void setAp_date_ouverture(java.lang.String Ap_date_ouverture) {
        this.Ap_date_ouverture = Ap_date_ouverture;
    }
    
    /** Getter for property Ap_description.
     * @return Value of property Ap_description.
     *
     */
    public java.lang.String getAp_description() {
        return Ap_description;
    }
    
    /** Setter for property Ap_description.
     * @param Ap_description New value of property Ap_description.
     *
     */
    public void setAp_description(java.lang.String Ap_description) {
        this.Ap_description = Ap_description;
    }
    
    /** Getter for property Ap_etat.
     * @return Value of property Ap_etat.
     *
     */
    public java.lang.String getAp_etat() {
        return Ap_etat;
    }
    
    /** Setter for property Ap_etat.
     * @param Ap_etat New value of property Ap_etat.
     *
     */
    public void setAp_etat(java.lang.String Ap_etat) {
        this.Ap_etat = Ap_etat;
    }
    
    /** Getter for property Ap_etat_date.
     * @return Value of property Ap_etat_date.
     *
     */
    public java.lang.String getAp_etat_date() {
        return Ap_etat_date;
    }
    
    /** Setter for property Ap_etat_date.
     * @param Ap_etat_date New value of property Ap_etat_date.
     *
     */
    public void setAp_etat_date(java.lang.String Ap_etat_date) {
        this.Ap_etat_date = Ap_etat_date;
    }
    
    /** Getter for property Ap_libelle.
     * @return Value of property Ap_libelle.
     *
     */
    public java.lang.String getAp_libelle() {
        return Ap_libelle;
    }
    
    /** Setter for property Ap_libelle.
     * @param Ap_libelle New value of property Ap_libelle.
     *
     */
    public void setAp_libelle(java.lang.String Ap_libelle) {
        this.Ap_libelle = Ap_libelle;
    }
    
    /** Getter for property Ap_message.
     * @return Value of property Ap_message.
     *
     */
    public java.lang.String getAp_message() {
        return Ap_message;
    }
    
    /** Setter for property Ap_message.
     * @param Ap_message New value of property Ap_message.
     *
     */
    public void setAp_message(java.lang.String Ap_message) {
        this.Ap_message = Ap_message;
    }
    
    /** Getter for property Ap_murl.
     * @return Value of property Ap_murl.
     *
     */
    public java.lang.String getAp_murl() {
        return Ap_murl;
    }
    
    /** Setter for property Ap_murl.
     * @param Ap_murl New value of property Ap_murl.
     *
     */
    public void setAp_murl(java.lang.String Ap_murl) {
        this.Ap_murl = Ap_murl;
    }
    
    /** Getter for property Ap_port.
     * @return Value of property Ap_port.
     *
     */
    public int getAp_port() {
        return Ap_port;
    }
    
    /** Setter for property Ap_port.
     * @param Ap_port New value of property Ap_port.
     *
     */
    public void setAp_port(int Ap_port) {
        this.Ap_port = Ap_port;
    }
    
    /** Getter for property Ap_pt_code.
     * @return Value of property Ap_pt_code.
     *
     */
    public int getAp_pt_code() {
        return Ap_pt_code;
    }
    
    /** Setter for property Ap_pt_code.
     * @param Ap_pt_code New value of property Ap_pt_code.
     *
     */
    public void setAp_pt_code(int Ap_pt_code) {
        this.Ap_pt_code = Ap_pt_code;
    }
    
    /** Getter for property Ap_type.
     * @return Value of property Ap_type.
     *
     */
    public java.lang.String getAp_type() {
        return Ap_type;
    }
    
    /** Setter for property Ap_type.
     * @param Ap_type New value of property Ap_type.
     *
     */
    public void setAp_type(java.lang.String Ap_type) {
        this.Ap_type = Ap_type;
    }
    
    /** Getter for property Ap_url.
     * @return Value of property Ap_url.
     *
     */
    public java.lang.String getAp_url() {
        return Ap_url;
    }
    
    /** Setter for property Ap_url.
     * @param Ap_url New value of property Ap_url.
     *
     */
    public void setAp_url(java.lang.String Ap_url) {
        this.Ap_url = Ap_url;
    }
    
    /** Getter for property Ap_visible.
     * @return Value of property Ap_visible.
     *
     */
    public int getAp_visible() {
        return Ap_visible;
    }
    
    /** Setter for property Ap_visible.
     * @param Ap_visible New value of property Ap_visible.
     *
     */
    public void setAp_visible(int Ap_visible) {
        this.Ap_visible = Ap_visible;
    }
    
    /** Getter for property Bd_code.
     * @return Value of property Bd_code.
     *
     */
    public int getBd_code() {
        return Bd_code;
    }
    
    /** Setter for property Bd_code.
     * @param Bd_code New value of property Bd_code.
     *
     */
    public void setBd_code(int Bd_code) {
        this.Bd_code = Bd_code;
    }
    
    /** Getter for property Bd_ma_code.
     * @return Value of property Bd_ma_code.
     *
     */
    public int getBd_ma_code() {
        return Bd_ma_code;
    }
    
    /** Setter for property Bd_ma_code.
     * @param Bd_ma_code New value of property Bd_ma_code.
     *
     */
    public void setBd_ma_code(int Bd_ma_code) {
        this.Bd_ma_code = Bd_ma_code;
    }
    
    /** Getter for property Bd_port.
     * @return Value of property Bd_port.
     *
     */
    public int getBd_port() {
        return Bd_port;
    }
    
    /** Setter for property Bd_port.
     * @param Bd_port New value of property Bd_port.
     *
     */
    public void setBd_port(int Bd_port) {
        this.Bd_port = Bd_port;
    }
    
    /** Getter for property Bd_port_etat.
     * @return Value of property Bd_port_etat.
     *
     */
    public java.lang.String getBd_port_etat() {
        return Bd_port_etat;
    }
    
    /** Setter for property Bd_port_etat.
     * @param Bd_port_etat New value of property Bd_port_etat.
     *
     */
    public void setBd_port_etat(java.lang.String Bd_port_etat) {
        this.Bd_port_etat = Bd_port_etat;
    }
    
    /** Getter for property Bd_port_etat_date.
     * @return Value of property Bd_port_etat_date.
     *
     */
    public java.lang.String getBd_port_etat_date() {
        return Bd_port_etat_date;
    }
    
    /** Setter for property Bd_port_etat_date.
     * @param Bd_port_etat_date New value of property Bd_port_etat_date.
     *
     */
    public void setBd_port_etat_date(java.lang.String Bd_port_etat_date) {
        this.Bd_port_etat_date = Bd_port_etat_date;
    }
    
    /** Getter for property Bd_repertoire.
     * @return Value of property Bd_repertoire.
     *
     */
    public java.lang.String getBd_repertoire() {
        return Bd_repertoire;
    }
    
    /** Setter for property Bd_repertoire.
     * @param Bd_repertoire New value of property Bd_repertoire.
     *
     */
    public void setBd_repertoire(java.lang.String Bd_repertoire) {
        this.Bd_repertoire = Bd_repertoire;
    }
    
    /** Getter for property Bd_sigle.
     * @return Value of property Bd_sigle.
     *
     */
    public java.lang.String getBd_sigle() {
        return Bd_sigle;
    }
    
    /** Setter for property Bd_sigle.
     * @param Bd_sigle New value of property Bd_sigle.
     *
     */
    public void setBd_sigle(java.lang.String Bd_sigle) {
        this.Bd_sigle = Bd_sigle;
    }
    
    /** Getter for property Bd_type.
     * @return Value of property Bd_type.
     *
     */
    public java.lang.String getBd_type() {
        return Bd_type;
    }
    
    /** Setter for property Bd_type.
     * @param Bd_type New value of property Bd_type.
     *
     */
    public void setBd_type(java.lang.String Bd_type) {
        this.Bd_type = Bd_type;
    }
    
    /** Getter for property Bd_zone.
     * @return Value of property Bd_zone.
     *
     */
    public int getBd_zone() {
        return Bd_zone;
    }
    
    /** Setter for property Bd_zone.
     * @param Bd_zone New value of property Bd_zone.
     *
     */
    public void setBd_zone(int Bd_zone) {
        this.Bd_zone = Bd_zone;
    }
    
    /** Getter for property Do_code.
     * @return Value of property Do_code.
     *
     */
    public int getDo_code() {
        return Do_code;
    }
    
    /** Setter for property Do_code.
     * @param Do_code New value of property Do_code.
     *
     */
    public void setDo_code(int Do_code) {
        this.Do_code = Do_code;
    }
    
    /** Getter for property Do_libelle.
     * @return Value of property Do_libelle.
     *
     */
    public java.lang.String getDo_libelle() {
        return Do_libelle;
    }
    
    /** Setter for property Do_libelle.
     * @param Do_libelle New value of property Do_libelle.
     *
     */
    public void setDo_libelle(java.lang.String Do_libelle) {
        this.Do_libelle = Do_libelle;
    }
    
    /** Getter for property Do_ma_code.
     * @return Value of property Do_ma_code.
     *
     */
    public int getDo_ma_code() {
        return Do_ma_code;
    }
    
    /** Setter for property Do_ma_code.
     * @param Do_ma_code New value of property Do_ma_code.
     *
     */
    public void setDo_ma_code(int Do_ma_code) {
        this.Do_ma_code = Do_ma_code;
    }
    
    /** Getter for property Do_nom.
     * @return Value of property Do_nom.
     *
     */
    public java.lang.String getDo_nom() {
        return Do_nom;
    }
    
    /** Setter for property Do_nom.
     * @param Do_nom New value of property Do_nom.
     *
     */
    public void setDo_nom(java.lang.String Do_nom) {
        this.Do_nom = Do_nom;
    }
    
    /** Getter for property Do_passwd.
     * @return Value of property Do_passwd.
     *
     */
    public java.lang.String getDo_passwd() {
        return Do_passwd;
    }
    
    /** Setter for property Do_passwd.
     * @param Do_passwd New value of property Do_passwd.
     *
     */
    public void setDo_passwd(java.lang.String Do_passwd) {
        this.Do_passwd = Do_passwd;
    }
    
    /** Getter for property Do_portc.
     * @return Value of property Do_portc.
     *
     */
    public int getDo_portc() {
        return Do_portc;
    }
    
    /** Setter for property Do_portc.
     * @param Do_portc New value of property Do_portc.
     *
     */
    public void setDo_portc(int Do_portc) {
        this.Do_portc = Do_portc;
    }
    
    /** Getter for property Do_portc_etat.
     * @return Value of property Do_portc_etat.
     *
     */
    public java.lang.String getDo_portc_etat() {
        return Do_portc_etat;
    }
    
    /** Setter for property Do_portc_etat.
     * @param Do_portc_etat New value of property Do_portc_etat.
     *
     */
    public void setDo_portc_etat(java.lang.String Do_portc_etat) {
        this.Do_portc_etat = Do_portc_etat;
    }
    
    /** Getter for property Do_portc_etat_date.
     * @return Value of property Do_portc_etat_date.
     *
     */
    public java.lang.String getDo_portc_etat_date() {
        return Do_portc_etat_date;
    }
    
    /** Setter for property Do_portc_etat_date.
     * @param Do_portc_etat_date New value of property Do_portc_etat_date.
     *
     */
    public void setDo_portc_etat_date(java.lang.String Do_portc_etat_date) {
        this.Do_portc_etat_date = Do_portc_etat_date;
    }
    
    /** Getter for property Do_user.
     * @return Value of property Do_user.
     *
     */
    public java.lang.String getDo_user() {
        return Do_user;
    }
    
    /** Setter for property Do_user.
     * @param Do_user New value of property Do_user.
     *
     */
    public void setDo_user(java.lang.String Do_user) {
        this.Do_user = Do_user;
    }
    
    /** Getter for property Do_wa_code.
     * @return Value of property Do_wa_code.
     *
     */
    public int getDo_wa_code() {
        return Do_wa_code;
    }
    
    /** Setter for property Do_wa_code.
     * @param Do_wa_code New value of property Do_wa_code.
     *
     */
    public void setDo_wa_code(int Do_wa_code) {
        this.Do_wa_code = Do_wa_code;
    }
    
    /** Getter for property Do_wa_libelle.
     * @return Value of property Do_wa_libelle.
     *
     */
    public java.lang.String getDo_wa_libelle() {
        return Do_wa_libelle;
    }
    
    /** Setter for property Do_wa_libelle.
     * @param Do_wa_libelle New value of property Do_wa_libelle.
     *
     */
    public void setDo_wa_libelle(java.lang.String Do_wa_libelle) {
        this.Do_wa_libelle = Do_wa_libelle;
    }
    
    /** Getter for property Do_wa_type.
     * @return Value of property Do_wa_type.
     *
     */
    public java.lang.String getDo_wa_type() {
        return Do_wa_type;
    }
    
    /** Setter for property Do_wa_type.
     * @param Do_wa_type New value of property Do_wa_type.
     *
     */
    public void setDo_wa_type(java.lang.String Do_wa_type) {
        this.Do_wa_type = Do_wa_type;
    }
    
    /** Getter for property Iw_code.
     * @return Value of property Iw_code.
     *
     */
    public int getIw_code() {
        return Iw_code;
    }
    
    /** Setter for property Iw_code.
     * @param Iw_code New value of property Iw_code.
     *
     */
    public void setIw_code(int Iw_code) {
        this.Iw_code = Iw_code;
    }
    
    /** Getter for property Iw_do_code.
     * @return Value of property Iw_do_code.
     *
     */
    public int getIw_do_code() {
        return Iw_do_code;
    }
    
    /** Setter for property Iw_do_code.
     * @param Iw_do_code New value of property Iw_do_code.
     *
     */
    public void setIw_do_code(int Iw_do_code) {
        this.Iw_do_code = Iw_do_code;
    }
    
    /** Getter for property Iw_libelle.
     * @return Value of property Iw_libelle.
     *
     */
    public java.lang.String getIw_libelle() {
        return Iw_libelle;
    }
    
    /** Setter for property Iw_libelle.
     * @param Iw_libelle New value of property Iw_libelle.
     *
     */
    public void setIw_libelle(java.lang.String Iw_libelle) {
        this.Iw_libelle = Iw_libelle;
    }
    
    /** Getter for property Iw_ma_code.
     * @return Value of property Iw_ma_code.
     *
     */
    public int getIw_ma_code() {
        return Iw_ma_code;
    }
    
    /** Setter for property Iw_ma_code.
     * @param Iw_ma_code New value of property Iw_ma_code.
     *
     */
    public void setIw_ma_code(int Iw_ma_code) {
        this.Iw_ma_code = Iw_ma_code;
    }
    
    /** Getter for property Iw_nom.
     * @return Value of property Iw_nom.
     *
     */
    public java.lang.String getIw_nom() {
        return Iw_nom;
    }
    
    /** Setter for property Iw_nom.
     * @param Iw_nom New value of property Iw_nom.
     *
     */
    public void setIw_nom(java.lang.String Iw_nom) {
        this.Iw_nom = Iw_nom;
    }
    
    /** Getter for property Iw_port1.
     * @return Value of property Iw_port1.
     *
     */
    public int getIw_port1() {
        return Iw_port1;
    }
    
    /** Setter for property Iw_port1.
     * @param Iw_port1 New value of property Iw_port1.
     *
     */
    public void setIw_port1(int Iw_port1) {
        this.Iw_port1 = Iw_port1;
    }
    
    /** Getter for property Iw_port1_etat.
     * @return Value of property Iw_port1_etat.
     *
     */
    public java.lang.String getIw_port1_etat() {
        return Iw_port1_etat;
    }
    
    /** Setter for property Iw_port1_etat.
     * @param Iw_port1_etat New value of property Iw_port1_etat.
     *
     */
    public void setIw_port1_etat(java.lang.String Iw_port1_etat) {
        this.Iw_port1_etat = Iw_port1_etat;
    }
    
    /** Getter for property Iw_port1_etat_date.
     * @return Value of property Iw_port1_etat_date.
     *
     */
    public java.lang.String getIw_port1_etat_date() {
        return Iw_port1_etat_date;
    }
    
    /** Setter for property Iw_port1_etat_date.
     * @param Iw_port1_etat_date New value of property Iw_port1_etat_date.
     *
     */
    public void setIw_port1_etat_date(java.lang.String Iw_port1_etat_date) {
        this.Iw_port1_etat_date = Iw_port1_etat_date;
    }
    
    /** Getter for property Iw_port2.
     * @return Value of property Iw_port2.
     *
     */
    public int getIw_port2() {
        return Iw_port2;
    }
    
    /** Setter for property Iw_port2.
     * @param Iw_port2 New value of property Iw_port2.
     *
     */
    public void setIw_port2(int Iw_port2) {
        this.Iw_port2 = Iw_port2;
    }
    
    /** Getter for property Iw_port2_etat.
     * @return Value of property Iw_port2_etat.
     *
     */
    public java.lang.String getIw_port2_etat() {
        return Iw_port2_etat;
    }
    
    /** Setter for property Iw_port2_etat.
     * @param Iw_port2_etat New value of property Iw_port2_etat.
     *
     */
    public void setIw_port2_etat(java.lang.String Iw_port2_etat) {
        this.Iw_port2_etat = Iw_port2_etat;
    }
    
    /** Getter for property Iw_port2_etat_date.
     * @return Value of property Iw_port2_etat_date.
     *
     */
    public java.lang.String getIw_port2_etat_date() {
        return Iw_port2_etat_date;
    }
    
    /** Setter for property Iw_port2_etat_date.
     * @param Iw_port2_etat_date New value of property Iw_port2_etat_date.
     *
     */
    public void setIw_port2_etat_date(java.lang.String Iw_port2_etat_date) {
        this.Iw_port2_etat_date = Iw_port2_etat_date;
    }
    
    /** Getter for property Iw_shell.
     * @return Value of property Iw_shell.
     *
     */
    public java.lang.String getIw_shell() {
        return Iw_shell;
    }
    
    /** Setter for property Iw_shell.
     * @param Iw_shell New value of property Iw_shell.
     *
     */
    public void setIw_shell(java.lang.String Iw_shell) {
        this.Iw_shell = Iw_shell;
    }
    
    /** Getter for property Iw_sigle.
     * @return Value of property Iw_sigle.
     *
     */
    public java.lang.String getIw_sigle() {
        return Iw_sigle;
    }
    
    /** Setter for property Iw_sigle.
     * @param Iw_sigle New value of property Iw_sigle.
     *
     */
    public void setIw_sigle(java.lang.String Iw_sigle) {
        this.Iw_sigle = Iw_sigle;
    }
    
    /** Getter for property Iw_zone.
     * @return Value of property Iw_zone.
     *
     */
    public int getIw_zone() {
        return Iw_zone;
    }
    
    /** Setter for property Iw_zone.
     * @param Iw_zone New value of property Iw_zone.
     *
     */
    public void setIw_zone(int Iw_zone) {
        this.Iw_zone = Iw_zone;
    }
    
    /** Getter for property ma_adresse.
     * @return Value of property ma_adresse.
     *
     */
    public java.lang.String getMa_adresse() {
        return ma_adresse;
    }
    
    /** Setter for property ma_adresse.
     * @param ma_adresse New value of property ma_adresse.
     *
     */
    public void setMa_adresse(java.lang.String ma_adresse) {
        this.ma_adresse = ma_adresse;
    }
    
    /** Getter for property ma_code.
     * @return Value of property ma_code.
     *
     */
    public int getMa_code() {
        return ma_code;
    }
    
    /** Setter for property ma_code.
     * @param ma_code New value of property ma_code.
     *
     */
    public void setMa_code(int ma_code) {
        this.ma_code = ma_code;
    }
    
    /** Getter for property ma_etat.
     * @return Value of property ma_etat.
     *
     */
    public java.lang.String getMa_etat() {
        return ma_etat;
    }
    
    /** Setter for property ma_etat.
     * @param ma_etat New value of property ma_etat.
     *
     */
    public void setMa_etat(java.lang.String ma_etat) {
        this.ma_etat = ma_etat;
    }
    
    /** Getter for property ma_etat_date.
     * @return Value of property ma_etat_date.
     *
     */
    public java.lang.String getMa_etat_date() {
        return ma_etat_date;
    }
    
    /** Setter for property ma_etat_date.
     * @param ma_etat_date New value of property ma_etat_date.
     *
     */
    public void setMa_etat_date(java.lang.String ma_etat_date) {
        this.ma_etat_date = ma_etat_date;
    }
    
    /** Getter for property ma_libelle.
     * @return Value of property ma_libelle.
     *
     */
    public java.lang.String getMa_libelle() {
        return ma_libelle;
    }
    
    /** Setter for property ma_libelle.
     * @param ma_libelle New value of property ma_libelle.
     *
     */
    public void setMa_libelle(java.lang.String ma_libelle) {
        this.ma_libelle = ma_libelle;
    }
    
    /** Getter for property ma_nb_bdd.
     * @return Value of property ma_nb_bdd.
     *
     */
    public int getMa_nb_bdd() {
        return ma_nb_bdd;
    }
    
    /** Setter for property ma_nb_bdd.
     * @param ma_nb_bdd New value of property ma_nb_bdd.
     *
     */
    public void setMa_nb_bdd(int ma_nb_bdd) {
        this.ma_nb_bdd = ma_nb_bdd;
    }
    
    /** Getter for property ma_sigle.
     * @return Value of property ma_sigle.
     *
     */
    public java.lang.String getMa_sigle() {
        return ma_sigle;
    }
    
    /** Setter for property ma_sigle.
     * @param ma_sigle New value of property ma_sigle.
     *
     */
    public void setMa_sigle(java.lang.String ma_sigle) {
        this.ma_sigle = ma_sigle;
    }
    
    /** Getter for property ma_zn_code.
     * @return Value of property ma_zn_code.
     *
     */
    public int getMa_zn_code() {
        return ma_zn_code;
    }
    
    /** Setter for property ma_zn_code.
     * @param ma_zn_code New value of property ma_zn_code.
     *
     */
    public void setMa_zn_code(int ma_zn_code) {
        this.ma_zn_code = ma_zn_code;
    }
    
    /** Getter for property Nb_joint.
     * @return Value of property Nb_joint.
     *
     */
    public int getNb_joint() {
        return Nb_joint;
    }
    
    /** Setter for property Nb_joint.
     * @param Nb_joint New value of property Nb_joint.
     *
     */
    public void setNb_joint(int Nb_joint) {
        this.Nb_joint = Nb_joint;
    }
    
    /** Getter for property Pi_code.
     * @return Value of property Pi_code.
     *
     */
    public int getPi_code() {
        return Pi_code;
    }
    
    /** Setter for property Pi_code.
     * @param Pi_code New value of property Pi_code.
     *
     */
    public void setPi_code(int Pi_code) {
        this.Pi_code = Pi_code;
    }
    
    /** Getter for property Pi_iw_code.
     * @return Value of property Pi_iw_code.
     *
     */
    public int getPi_iw_code() {
        return Pi_iw_code;
    }
    
    /** Setter for property Pi_iw_code.
     * @param Pi_iw_code New value of property Pi_iw_code.
     *
     */
    public void setPi_iw_code(int Pi_iw_code) {
        this.Pi_iw_code = Pi_iw_code;
    }
    
    /** Getter for property Pi_libelle.
     * @return Value of property Pi_libelle.
     *
     */
    public java.lang.String getPi_libelle() {
        return Pi_libelle;
    }
    
    /** Setter for property Pi_libelle.
     * @param Pi_libelle New value of property Pi_libelle.
     *
     */
    public void setPi_libelle(java.lang.String Pi_libelle) {
        this.Pi_libelle = Pi_libelle;
    }
    
    /** Getter for property Pi_ma_code.
     * @return Value of property Pi_ma_code.
     *
     */
    public int getPi_ma_code() {
        return Pi_ma_code;
    }
    
    /** Setter for property Pi_ma_code.
     * @param Pi_ma_code New value of property Pi_ma_code.
     *
     */
    public void setPi_ma_code(int Pi_ma_code) {
        this.Pi_ma_code = Pi_ma_code;
    }
    
    /** Getter for property Pi_nom.
     * @return Value of property Pi_nom.
     *
     */
    public java.lang.String getPi_nom() {
        return Pi_nom;
    }
    
    /** Setter for property Pi_nom.
     * @param Pi_nom New value of property Pi_nom.
     *
     */
    public void setPi_nom(java.lang.String Pi_nom) {
        this.Pi_nom = Pi_nom;
    }
    
    /** Getter for property Pi_port.
     * @return Value of property Pi_port.
     *
     */
    public int getPi_port() {
        return Pi_port;
    }
    
    /** Setter for property Pi_port.
     * @param Pi_port New value of property Pi_port.
     *
     */
    public void setPi_port(int Pi_port) {
        this.Pi_port = Pi_port;
    }
    
    /** Getter for property Pi_port_etat.
     * @return Value of property Pi_port_etat.
     *
     */
    public java.lang.String getPi_port_etat() {
        return Pi_port_etat;
    }
    
    /** Setter for property Pi_port_etat.
     * @param Pi_port_etat New value of property Pi_port_etat.
     *
     */
    public void setPi_port_etat(java.lang.String Pi_port_etat) {
        this.Pi_port_etat = Pi_port_etat;
    }
    
    /** Getter for property Pi_port_etat_date.
     * @return Value of property Pi_port_etat_date.
     *
     */
    public java.lang.String getPi_port_etat_date() {
        return Pi_port_etat_date;
    }
    
    /** Setter for property Pi_port_etat_date.
     * @param Pi_port_etat_date New value of property Pi_port_etat_date.
     *
     */
    public void setPi_port_etat_date(java.lang.String Pi_port_etat_date) {
        this.Pi_port_etat_date = Pi_port_etat_date;
    }
    
    /** Getter for property Pi_portail.
     * @return Value of property Pi_portail.
     *
     */
    public java.lang.String getPi_portail() {
        return Pi_portail;
    }
    
    /** Setter for property Pi_portail.
     * @param Pi_portail New value of property Pi_portail.
     *
     */
    public void setPi_portail(java.lang.String Pi_portail) {
        this.Pi_portail = Pi_portail;
    }
    
    /** Getter for property Pi_pt_code.
     * @return Value of property Pi_pt_code.
     *
     */
    public int getPi_pt_code() {
        return Pi_pt_code;
    }
    
    /** Setter for property Pi_pt_code.
     * @param Pi_pt_code New value of property Pi_pt_code.
     *
     */
    public void setPi_pt_code(int Pi_pt_code) {
        this.Pi_pt_code = Pi_pt_code;
    }
    
    /** Getter for property Pt_code.
     * @return Value of property Pt_code.
     *
     */
    public int getPt_code() {
        return Pt_code;
    }
    
    /** Setter for property Pt_code.
     * @param Pt_code New value of property Pt_code.
     *
     */
    public void setPt_code(int Pt_code) {
        this.Pt_code = Pt_code;
    }
    
    /** Getter for property Pt_ma_code.
     * @return Value of property Pt_ma_code.
     *
     */
    public int getPt_ma_code() {
        return Pt_ma_code;
    }
    
    /** Setter for property Pt_ma_code.
     * @param Pt_ma_code New value of property Pt_ma_code.
     *
     */
    public void setPt_ma_code(int Pt_ma_code) {
        this.Pt_ma_code = Pt_ma_code;
    }
    
    /** Getter for property Pt_port.
     * @return Value of property Pt_port.
     *
     */
    public int getPt_port() {
        return Pt_port;
    }
    
    /** Setter for property Pt_port.
     * @param Pt_port New value of property Pt_port.
     *
     */
    public void setPt_port(int Pt_port) {
        this.Pt_port = Pt_port;
    }
    
    /** Getter for property Pt_port_etat.
     * @return Value of property Pt_port_etat.
     *
     */
    public java.lang.String getPt_port_etat() {
        return Pt_port_etat;
    }
    
    /** Setter for property Pt_port_etat.
     * @param Pt_port_etat New value of property Pt_port_etat.
     *
     */
    public void setPt_port_etat(java.lang.String Pt_port_etat) {
        this.Pt_port_etat = Pt_port_etat;
    }
    
    /** Getter for property Pt_port_etat_date.
     * @return Value of property Pt_port_etat_date.
     *
     */
    public java.lang.String getPt_port_etat_date() {
        return Pt_port_etat_date;
    }
    
    /** Setter for property Pt_port_etat_date.
     * @param Pt_port_etat_date New value of property Pt_port_etat_date.
     *
     */
    public void setPt_port_etat_date(java.lang.String Pt_port_etat_date) {
        this.Pt_port_etat_date = Pt_port_etat_date;
    }
    
    /** Getter for property Pt_repertoire.
     * @return Value of property Pt_repertoire.
     *
     */
    public java.lang.String getPt_repertoire() {
        return Pt_repertoire;
    }
    
    /** Setter for property Pt_repertoire.
     * @param Pt_repertoire New value of property Pt_repertoire.
     *
     */
    public void setPt_repertoire(java.lang.String Pt_repertoire) {
        this.Pt_repertoire = Pt_repertoire;
    }
    
    /** Getter for property Pt_sigle.
     * @return Value of property Pt_sigle.
     *
     */
    public java.lang.String getPt_sigle() {
        return Pt_sigle;
    }
    
    /** Setter for property Pt_sigle.
     * @param Pt_sigle New value of property Pt_sigle.
     *
     */
    public void setPt_sigle(java.lang.String Pt_sigle) {
        this.Pt_sigle = Pt_sigle;
    }
    
    /** Getter for property Pt_type.
     * @return Value of property Pt_type.
     *
     */
    public java.lang.String getPt_type() {
        return Pt_type;
    }
    
    /** Setter for property Pt_type.
     * @param Pt_type New value of property Pt_type.
     *
     */
    public void setPt_type(java.lang.String Pt_type) {
        this.Pt_type = Pt_type;
    }
    
    /** Getter for property Pt_version.
     * @return Value of property Pt_version.
     *
     */
    public java.lang.String getPt_version() {
        return Pt_version;
    }
    
    /** Setter for property Pt_version.
     * @param Pt_version New value of property Pt_version.
     *
     */
    public void setPt_version(java.lang.String Pt_version) {
        this.Pt_version = Pt_version;
    }
    
    /** Getter for property Pt_zone.
     * @return Value of property Pt_zone.
     *
     */
    public int getPt_zone() {
        return Pt_zone;
    }
    
    /** Setter for property Pt_zone.
     * @param Pt_zone New value of property Pt_zone.
     *
     */
    public void setPt_zone(int Pt_zone) {
        this.Pt_zone = Pt_zone;
    }
    
    /** Getter for property Th_code.
     * @return Value of property Th_code.
     *
     */
    public int getTh_code() {
        return Th_code;
    }
    
    /** Setter for property Th_code.
     * @param Th_code New value of property Th_code.
     *
     */
    public void setTh_code(int Th_code) {
        this.Th_code = Th_code;
    }
    
    /** Getter for property Th_couleur.
     * @return Value of property Th_couleur.
     *
     */
    public java.lang.String getTh_couleur() {
        return Th_couleur;
    }
    
    /** Setter for property Th_couleur.
     * @param Th_couleur New value of property Th_couleur.
     *
     */
    public void setTh_couleur(java.lang.String Th_couleur) {
        this.Th_couleur = Th_couleur;
    }
    
    /** Getter for property Th_libelle.
     * @return Value of property Th_libelle.
     *
     */
    public java.lang.String getTh_libelle() {
        return Th_libelle;
    }
    
    /** Setter for property Th_libelle.
     * @param Th_libelle New value of property Th_libelle.
     *
     */
    public void setTh_libelle(java.lang.String Th_libelle) {
        this.Th_libelle = Th_libelle;
    }
    
    /** Getter for property Wa_code.
     * @return Value of property Wa_code.
     *
     */
    public int getWa_code() {
        return Wa_code;
    }
    
    /** Setter for property Wa_code.
     * @param Wa_code New value of property Wa_code.
     *
     */
    public void setWa_code(int Wa_code) {
        this.Wa_code = Wa_code;
    }
    
    /** Getter for property Wa_ma_code.
     * @return Value of property Wa_ma_code.
     *
     */
    public int getWa_ma_code() {
        return Wa_ma_code;
    }
    
    /** Setter for property Wa_ma_code.
     * @param Wa_ma_code New value of property Wa_ma_code.
     *
     */
    public void setWa_ma_code(int Wa_ma_code) {
        this.Wa_ma_code = Wa_ma_code;
    }
    
    /** Getter for property Wa_repertoire.
     * @return Value of property Wa_repertoire.
     *
     */
    public java.lang.String getWa_repertoire() {
        return Wa_repertoire;
    }
    
    /** Setter for property Wa_repertoire.
     * @param Wa_repertoire New value of property Wa_repertoire.
     *
     */
    public void setWa_repertoire(java.lang.String Wa_repertoire) {
        this.Wa_repertoire = Wa_repertoire;
    }
    
    /** Getter for property Wa_type.
     * @return Value of property Wa_type.
     *
     */
    public java.lang.String getWa_type() {
        return Wa_type;
    }
    
    /** Setter for property Wa_type.
     * @param Wa_type New value of property Wa_type.
     *
     */
    public void setWa_type(java.lang.String Wa_type) {
        this.Wa_type = Wa_type;
    }
    
    /** Getter for property Wa_version.
     * @return Value of property Wa_version.
     *
     */
    public java.lang.String getWa_version() {
        return Wa_version;
    }
    
    /** Setter for property Wa_version.
     * @param Wa_version New value of property Wa_version.
     *
     */
    public void setWa_version(java.lang.String Wa_version) {
        this.Wa_version = Wa_version;
    }
    
    /**
     * Getter for property Ap_ouvert.
     * @return Value of property Ap_ouvert.
     */
    public java.lang.String getAp_ouvert() {
        return Ap_ouvert;
    }
    
    /**
     * Setter for property Ap_ouvert.
     * @param Ap_ouvert New value of property Ap_ouvert.
     */
    public void setAp_ouvert(java.lang.String Ap_ouvert) {
        this.Ap_ouvert = Ap_ouvert;
    }
    
    /**
     * Getter for property Ap_ouvert_lib.
     * @return Value of property Ap_ouvert_lib.
     */
    public java.lang.String getAp_ouvert_lib() {
        return Ap_ouvert_lib;
    }
    
    /**
     * Setter for property Ap_ouvert_lib.
     * @param Ap_ouvert_lib New value of property Ap_ouvert_lib.
     */
    public void setAp_ouvert_lib(java.lang.String Ap_ouvert_lib) {
        this.Ap_ouvert_lib = Ap_ouvert_lib;
    }
    
    /**
     * Getter for property As_ap_code.
     * @return Value of property As_ap_code.
     */
    public int getAs_ap_code() {
        return As_ap_code;
    }
    
    /**
     * Setter for property As_ap_code.
     * @param As_ap_code New value of property As_ap_code.
     */
    public void setAs_ap_code(int As_ap_code) {
        this.As_ap_code = As_ap_code;
    }
    
    /**
     * Getter for property As_code.
     * @return Value of property As_code.
     */
    public int getAs_code() {
        return As_code;
    }
    
    /**
     * Setter for property As_code.
     * @param As_code New value of property As_code.
     */
    public void setAs_code(int As_code) {
        this.As_code = As_code;
    }
    
    /**
     * Getter for property As_gsm.
     * @return Value of property As_gsm.
     */
    public java.lang.String getAs_gsm() {
        return As_gsm;
    }
    
    /**
     * Setter for property As_gsm.
     * @param As_gsm New value of property As_gsm.
     */
    public void setAs_gsm(java.lang.String As_gsm) {
        this.As_gsm = As_gsm;
    }
    
    /**
     * Getter for property As_mail.
     * @return Value of property As_mail.
     */
    public java.lang.String getAs_mail() {
        return As_mail;
    }
    
    /**
     * Setter for property As_mail.
     * @param As_mail New value of property As_mail.
     */
    public void setAs_mail(java.lang.String As_mail) {
        this.As_mail = As_mail;
    }
    
    /**
     * Getter for property As_nom.
     * @return Value of property As_nom.
     */
    public java.lang.String getAs_nom() {
        return As_nom;
    }
    
    /**
     * Setter for property As_nom.
     * @param As_nom New value of property As_nom.
     */
    public void setAs_nom(java.lang.String As_nom) {
        this.As_nom = As_nom;
    }
    
    /**
     * Getter for property Al_ap_code.
     * @return Value of property Al_ap_code.
     */
    public int getAl_ap_code() {
        return Al_ap_code;
    }
    
    /**
     * Setter for property Al_ap_code.
     * @param Al_ap_code New value of property Al_ap_code.
     */
    public void setAl_ap_code(int Al_ap_code) {
        this.Al_ap_code = Al_ap_code;
    }
    
    /**
     * Getter for property Al_code.
     * @return Value of property Al_code.
     */
    public int getAl_code() {
        return Al_code;
    }
    
    /**
     * Setter for property Al_code.
     * @param Al_code New value of property Al_code.
     */
    public void setAl_code(int Al_code) {
        this.Al_code = Al_code;
    }
    
    /**
     * Getter for property Al_date.
     * @return Value of property Al_date.
     */
    public java.lang.String getAl_date() {
        return Al_date;
    }
    
    /**
     * Setter for property Al_date.
     * @param Al_date New value of property Al_date.
     */
    public void setAl_date(java.lang.String Al_date) {
        this.Al_date = Al_date;
    }
    
    /**
     * Getter for property Al_etat.
     * @return Value of property Al_etat.
     */
    public java.lang.String getAl_etat() {
        return Al_etat;
    }
    
    /**
     * Setter for property Al_etat.
     * @param Al_etat New value of property Al_etat.
     */
    public void setAl_etat(java.lang.String Al_etat) {
        this.Al_etat = Al_etat;
    }
    
    /**
     * Getter for property Al_libetat.
     * @return Value of property Al_libetat.
     */
    public java.lang.String getAl_libetat() {
        return Al_libetat;
    }
    
    /**
     * Setter for property Al_libetat.
     * @param Al_libetat New value of property Al_libetat.
     */
    public void setAl_libetat(java.lang.String Al_libetat) {
        this.Al_libetat = Al_libetat;
    }
    
    /**
     * Getter for property Al_nom.
     * @return Value of property Al_nom.
     */
    public java.lang.String getAl_nom() {
        return Al_nom;
    }
    
    /**
     * Setter for property Al_nom.
     * @param Al_nom New value of property Al_nom.
     */
    public void setAl_nom(java.lang.String Al_nom) {
        this.Al_nom = Al_nom;
    }
    
    /**
     * Getter for property la_code.
     * @return Value of property la_code.
     */
    public int getLa_code() {
        return la_code;
    }
    
    /**
     * Setter for property la_code.
     * @param la_code New value of property la_code.
     */
    public void setLa_code(int la_code) {
        this.la_code = la_code;
    }
    
    /**
     * Getter for property la_etat.
     * @return Value of property la_etat.
     */
    public java.lang.String getLa_etat() {
        return la_etat;
    }
    
    /**
     * Setter for property la_etat.
     * @param la_etat New value of property la_etat.
     */
    public void setLa_etat(java.lang.String la_etat) {
        this.la_etat = la_etat;
    }
    
    /**
     * Getter for property la_etat_date.
     * @return Value of property la_etat_date.
     */
    public java.lang.String getLa_etat_date() {
        return la_etat_date;
    }
    
    /**
     * Setter for property la_etat_date.
     * @param la_etat_date New value of property la_etat_date.
     */
    public void setLa_etat_date(java.lang.String la_etat_date) {
        this.la_etat_date = la_etat_date;
    }
    
    /**
     * Getter for property la_machine.
     * @return Value of property la_machine.
     */
    public java.lang.String getLa_machine() {
        return la_machine;
    }
    
    /**
     * Setter for property la_machine.
     * @param la_machine New value of property la_machine.
     */
    public void setLa_machine(java.lang.String la_machine) {
        this.la_machine = la_machine;
    }
    
    /**
     * Getter for property la_port.
     * @return Value of property la_port.
     */
    public java.lang.String getLa_port() {
        return la_port;
    }
    
    /**
     * Setter for property la_port.
     * @param la_port New value of property la_port.
     */
    public void setLa_port(java.lang.String la_port) {
        this.la_port = la_port;
    }
    
    /**
     * Getter for property la_service.
     * @return Value of property la_service.
     */
    public java.lang.String getLa_service() {
        return la_service;
    }
    
    /**
     * Setter for property la_service.
     * @param la_service New value of property la_service.
     */
    public void setLa_service(java.lang.String la_service) {
        this.la_service = la_service;
    }
    
    /** Getter for property la_webapp.
     * @return Value of property la_webapp.
     *
     */
    public java.lang.String getLa_webapp() {
        return la_webapp;
    }
    
    /** Setter for property la_webapp.
     * @param la_webapp New value of property la_webapp.
     *
     */
    public void setLa_webapp(java.lang.String la_webapp) {
        this.la_webapp = la_webapp;
    }
    
}
