package javabeans;
import java.io.*;
public class ItemPortail implements Serializable {
     
    private String Pt_type;
    private String Pt_version;
    private String Pt_repertoire;
    private String Pt_port_etat;
    private String Pt_port_etat_date;
    private String Ma_sigle;
    private String Ma_libelle;
    private int Pt_code;
    private int Pt_ma_code;
    private int Pt_port;
//  -------------   intregrite relationnelle --------------------------------------------
    private int    Nb_joint;
    public  int    getNb_joint()                          {      return Nb_joint;        }
    public  void   setNb_joint(int var)                   {      Nb_joint=var;           }


// methodes get ----------------------------------------------------------------------
    public String getPt_type()    		         	 {      return Pt_type;           }
    public String getPt_version()    		         	 {      return Pt_version;           }
    public String getPt_repertoire()  		       {      return Pt_repertoire;     }
    public String getPt_port_etat()  		       {      return Pt_port_etat;      }
    public String getPt_port_etat_date()  		 {      return Pt_port_etat_date; }
    public String getMa_sigle()  		 {      return Ma_sigle; }
    public String getMa_libelle()  		 {      return Ma_libelle; }
    public int    getPt_code()                     	 {      return Pt_code; 	    }
    public int    getPt_ma_code()                      {      return Pt_ma_code;        }
    public int    getPt_port()                      {      return Pt_port;        }
// methodes set ---------------------------------------------------------------------------
    public void setPt_type(String var)    		 {      Pt_type=var;              }
    public void setPt_version(String var)    		 {      Pt_version=var;              }
    public void setPt_repertoire(String var)  		 {      Pt_repertoire=var;        }
    public void setPt_port_etat(String var) 	       {      Pt_port_etat=var;         }
    public void setPt_port_etat_date(String var) 	 {      Pt_port_etat_date=var;    }
    public void setMa_sigle(String var) 	 {      Ma_sigle=var;    }
    public void setMa_libelle(String var) 	 {      Ma_libelle=var;    }
    public void setPt_code(int var)                    {      Pt_code=var;              }
    public void setPt_ma_code(int var)                 {      Pt_ma_code=var;           }
    public void setPt_port(int var)                 {      Pt_port=var;           }
}
