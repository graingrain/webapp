package javabeans;
import java.io.*;
// description d'un element de l annuaire
//_______________________________________________ 
public class ItemDns implements Serializable {
     
    private String ai_ip;
    private String ai_name;
    private String ai_mayotte;
    private String ai_comores;
    private String ai_etab;
    private String ai_eon;
    private String ai_ibis;
    public String getAi_ip() {
		return ai_ip;
	}
	public void setAi_ip(String ai_ip) {
		this.ai_ip = ai_ip;
	}
	public String getAi_name() {
		return ai_name;
	}
	public void setAi_name(String ai_name) {
		this.ai_name = ai_name;
	}
	public String getAi_mayotte() {
		return ai_mayotte;
	}
	public void setAi_mayotte(String ai_mayotte) {
		this.ai_mayotte = ai_mayotte;
	}
	public String getAi_comores() {
		return ai_comores;
	}
	public void setAi_comores(String ai_comores) {
		this.ai_comores = ai_comores;
	}
	public String getAi_etab() {
		return ai_etab;
	}
	public void setAi_etab(String ai_etab) {
		this.ai_etab = ai_etab;
	}
	public String getAi_eon() {
		return ai_eon;
	}
	public void setAi_eon(String ai_eon) {
		this.ai_eon = ai_eon;
	}
	public String getAi_ibis() {
		return ai_ibis;
	}
	public void setAi_ibis(String ai_ibis) {
		this.ai_ibis = ai_ibis;
	}
	private String eo_dns;
    private String eo_ip;
    private String cp_node;
    private String cp_ip;
    private int    cp_ip_nb=0;
    private int    cp_ipe_nb=0;
   	private String cp_nat;
    private int    cp_nat_nb=0;
    public String getEo_dns() {
		return eo_dns;
	}
	public void setEo_dns(String eo_dns) {
		this.eo_dns = eo_dns;
	}
	public String getEo_ip() {
		return eo_ip;
	}
	public void setEo_ip(String eo_ip) {
		this.eo_ip = eo_ip;
	}
	private String dn_ip;
    private String dn_name;
    private String dn_dns;
    private String dn_node;
    private int    dn_node_nb=0;
    private String dn_type;
    private String dm_ip;
    private String dm_name;
    public String getDn_node() {
		return dn_node;
	}
	public void setDn_node(String dn_node) {
		this.dn_node = dn_node;
	}
	public int getDn_node_nb() {
		return dn_node_nb;
	}
	public void setDn_node_nb(int dn_node_nb) {
		this.dn_node_nb = dn_node_nb;
	}
	private String dm_type;
    private String dc_ip;
    private String dc_name;
    private String dc_type;
    private String de_ip;
    private String de_name;
    private String de_type;
	public String getCp_node() {
		return cp_node;
	}
	public void setCp_node(String cp_node) {
		this.cp_node = cp_node;
	}
	public String getCp_ip() {
		return cp_ip;
	}
	public void setCp_ip(String cp_ip) {
		this.cp_ip = cp_ip;
	}
	public String getCp_nat() {
		return cp_nat;
	}
	public void setCp_nat(String cp_nat) {
		this.cp_nat = cp_nat;
	}
	public String getDm_ip() {
		return dm_ip;
	}
	public void setDm_ip(String dm_ip) {
		this.dm_ip = dm_ip;
	}
	public String getDm_name() {
		return dm_name;
	}
	public void setDm_name(String dm_name) {
		this.dm_name = dm_name;
	}
	public String getDm_type() {
		return dm_type;
	}
	public void setDm_type(String dm_type) {
		this.dm_type = dm_type;
	}
	public String getDc_ip() {
		return dc_ip;
	}
	public void setDc_ip(String dc_ip) {
		this.dc_ip = dc_ip;
	}
	public String getDc_name() {
		return dc_name;
	}
	public void setDc_name(String dc_name) {
		this.dc_name = dc_name;
	}
	public String getDc_type() {
		return dc_type;
	}
	public void setDc_type(String dc_type) {
		this.dc_type = dc_type;
	}
	public String getDe_ip() {
		return de_ip;
	}
	public void setDe_ip(String de_ip) {
		this.de_ip = de_ip;
	}
	public String getDe_name() {
		return de_name;
	}
	public void setDe_name(String de_name) {
		this.de_name = de_name;
	}
	public String getDe_type() {
		return de_type;
	}
	public void setDe_type(String de_type) {
		this.de_type = de_type;
	}
	public String getDn_ip() {
		return dn_ip;
	}
	public void setDn_ip(String dn_ip) {
		this.dn_ip = dn_ip;
	}
	public String getDn_name() {
		return dn_name;
	}
	public void setDn_name(String dn_name) {
		this.dn_name = dn_name;
	}
	public String getDn_dns() {
		return dn_dns;
	}
	public void setDn_dns(String dn_dns) {
		this.dn_dns = dn_dns;
	}
	public String getDn_type() {
		return dn_type;
	}
	public void setDn_type(String dn_type) {
		this.dn_type = dn_type;
	}
	public int getCp_ip_nb() {
		return cp_ip_nb;
	}
	public void setCp_ip_nb(int cp_ip_nb) {
		this.cp_ip_nb = cp_ip_nb;
	}
	public int getCp_nat_nb() {
		return cp_nat_nb;
	}
	public void setCp_nat_nb(int cp_nat_nb) {
		this.cp_nat_nb = cp_nat_nb;
	}
	public int getCp_ipe_nb() {
		return cp_ipe_nb;
	}
	public void setCp_ipe_nb(int cp_ipe_nb) {
		this.cp_ipe_nb = cp_ipe_nb;
	}
      
}
