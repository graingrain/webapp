package javabeans;
import java.io.*;
public class ItemPortailIW implements Serializable {
    private String Pi_nom;
    private String Pi_libelle;
    private String Pi_portail;
    private String Pi_port_etat;
    private String Pi_port_etat_date;
    private int Pi_code;
    private int Pi_ma_code; 
    private int Pi_pt_code; 
    private int Pi_iw_code;
    private int Pi_port;
//  data du portail
//  ---------------  
    private String Pt_sigle;
    private int    Pt_zone;
    private String Pt_type;
    private String Pt_version;
    private String Pt_repertoire;
    private int    Pt_port;
    private String Pt_port_etat;
    private String Pt_port_etat_date;
    private String Ma_sigle;
    private String Ma_libelle;
//  data de InstanceWA
//  ------------------
    private String Iw_sigle;
    private String Iw_nom;
    private String Iw_libelle;
    private int    Iw_port1;
    private String Iw_port1_etat;
    private String Iw_port1_etat_date;
    private int    Iw_port2;
    private String Iw_port2_etat;
    private String Iw_port2_etat_date;
//  data de Bdd
//  ------------------
    private String Bd_sigle; // nom de la machine
    private int    Bd_zone;
    private String Bd_type;
    private String Bd_repertoire;
    private String Bd_port_etat;
    private String Bd_port_etat_date;
    private int Bd_code;
    private int Bd_ma_code;
    private int Bd_port;

// methodes get ---------------------------------------------------
//--------------
    public String getPi_nom()           { return Pi_nom;           }
    public String getPi_libelle()       { return Pi_libelle;       }
    public String getPi_portail()       { return Pi_portail;       }
    public String getPi_port_etat()     { return Pi_port_etat;     }
    public String getPi_port_etat_date(){ return Pi_port_etat_date;}
    public int    getPi_code()          { return Pi_code;          }
    public int    getPi_ma_code()       { return Pi_ma_code;       }
    public int    getPi_pt_code()       { return Pi_pt_code;       }
    public int    getPi_iw_code()       { return Pi_iw_code;       }
    public int    getPi_port()          { return Pi_port;          }
// portail
//--------
    public String getPt_sigle()         { return Pt_sigle;         }
    public int    getPt_zone()          { return Pt_zone;          }
    public String getPt_type()          { return Pt_type;          }
    public String getPt_version()       { return Pt_version;       }
    public String getPt_repertoire()    { return Pt_repertoire;    }
    public int    getPt_port()          { return Pt_port;          }
    public String getPt_port_etat()     { return Pt_port_etat;     }
    public String getPt_port_etat_date(){ return Pt_port_etat_date;}
    public String getMa_sigle()         { return Ma_sigle;         }
    public String getMa_libelle()       { return Ma_libelle;       }
// InstanceWA
//-----------
    public String getIw_sigle()          { return Iw_sigle;         }
    public String getIw_nom()            { return Iw_nom;            }
    public String getIw_libelle()  	     { return Iw_libelle;        }
    public int    getIw_port1()          { return Iw_port1;          }
    public String getIw_port1_etat()     { return Iw_port1_etat;     }
    public String getIw_port1_etat_date(){ return Iw_port1_etat_date;}
    public int    getIw_port2()          { return Iw_port2;          }
    public String getIw_port2_etat()     { return Iw_port2_etat;     }
    public String getIw_port2_etat_date(){ return Iw_port2_etat_date;}
// bdd
//-----------
    public String getBd_sigle()          { return Bd_sigle;         }
    public int    getBd_zone()           { return Bd_zone;          }
    public String getBd_type()           { return Bd_type;          }
    public String getBd_repertoire()     { return Bd_repertoire;    }
    public String getBd_port_etat()      { return Bd_port_etat;     }
    public String getBd_port_etat_date() { return Bd_port_etat_date;}
    public int    getBd_code()           { return Bd_code;          }
    public int    getBd_ma_code()        { return Bd_ma_code;       }
    public int    getBd_port()           { return Bd_port;          }
   
//  -------------   intregrite relationnelle ------------------------- 
    private int    Nb_joint;
    public  int    getNb_joint()         { return Nb_joint;          }
    public  void   setNb_joint(int var)  { Nb_joint=var;             }


// methodes set ----------------------------------------------------------- 
//--------------
    public void setPi_nom(String var)            { Pi_nom=var;            }
    public void setPi_libelle(String var)        { Pi_libelle=var;        }
    public void setPi_portail(String var)        { Pi_portail=var;        }
    public void setPi_port_etat(String var)      { Pi_port_etat=var;      }
    public void setPi_port_etat_date(String var) { Pi_port_etat_date=var; }
    public void setPi_code(int var)              { Pi_code=var;           }
    public void setPi_ma_code(int var)           { Pi_ma_code=var;        }
    public void setPi_pt_code(int var)           { Pi_pt_code=var;        }
    public void setPi_iw_code(int var)           { Pi_iw_code=var;        }
    public void setPi_port(int var)              { Pi_port=var;           }
// methodes set PORTAIL
    public void setPt_sigle(String var)          { Pt_sigle=var;          }
    public void setPt_zone(int var)      	       { Pt_zone=var;           }
    public void setPt_type(String var)           { Pt_type=var;           }
    public void setPt_version(String var)        { Pt_version=var;        }
    public void setPt_repertoire(String var)     { Pt_repertoire=var;     }
    public void setPt_port(int var)      	       { Pt_port=var;           }
    public void setPt_port_etat(String var)      { Pt_port_etat=var;      }
    public void setPt_port_etat_date(String var) { Pt_port_etat_date=var; }
    public void setMa_sigle(String var) 	       { Ma_sigle=var;          }
    public void setMa_libelle(String var) 	 { Ma_libelle=var;        }
// methodes set InstanceWA
    public void setIw_sigle(String var)          { Iw_sigle=var;          }
    public void setIw_nom(String var)    		 { Iw_nom=var;            }
    public void setIw_libelle(String var)  	 { Iw_libelle=var;        }
    public void setIw_port1(int var) 	       { Iw_port1=var;          }
    public void setIw_port2(int var) 	       { Iw_port2=var;          }
    public void setIw_port1_etat(String var) 	 { Iw_port1_etat=var;     }
    public void setIw_port1_etat_date(String var){ Iw_port1_etat_date=var;}
    public void setIw_port2_etat(String var) 	 { Iw_port2_etat=var;     }
    public void setIw_port2_etat_date(String var){ Iw_port2_etat_date=var;}
// methodes set Bdd
    public void setBd_sigle(String var)          { Bd_sigle=var;          }
    public void setBd_zone(int var)      	       { Bd_zone=var;           }
    public void setBd_type(String var)    	 { Bd_type=var;           }
    public void setBd_repertoire(String var)  	 { Bd_repertoire=var;     }
    public void setBd_port_etat(String var) 	 { Bd_port_etat=var;      }
    public void setBd_port_etat_date(String var) { Bd_port_etat_date=var; }
    public void setBd_code(int var)              { Bd_code=var;           }
    public void setBd_ma_code(int var)           { Bd_ma_code=var;        }
    public void setBd_port(int var)              { Bd_port=var;           }
}
