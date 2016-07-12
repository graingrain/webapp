package javabeans;
import java.io.*;
public class ItemBdd implements Serializable {
     
    private String Bd_type;
    private String Bd_repertoire;
    private String Bd_port_etat;
    private String Bd_port_etat_date;
    private String Ma_sigle;
    private String Ma_libelle;
    private int Bd_code;
    private int Bd_ma_code;
    private int Bd_port;

    private int Nb_joint;


// methodes get ----------------------------------------------------------------------
    public String getBd_type()    		         	 {      return Bd_type;           }
    public String getBd_repertoire()  		       {      return Bd_repertoire;     }
    public String getBd_port_etat()  		       {      return Bd_port_etat;      }
    public String getBd_port_etat_date()  		 {      return Bd_port_etat_date; }
    public String getMa_sigle()  		             {      return Ma_sigle; }
    public String getMa_libelle()  		             {      return Ma_libelle; }
    public int    getBd_code()                     	 {      return Bd_code; 	    }
    public int    getBd_ma_code()                      {      return Bd_ma_code;        }
    public int    getBd_port()                         {      return Bd_port;           }
    public int    getNb_joint()                        {      return Nb_joint;          }

// methodes set ---------------------------------------------------------------------------
    public void setBd_type(String var)    		 {      Bd_type=var;              }
    public void setBd_repertoire(String var)  		 {      Bd_repertoire=var;        }
    public void setBd_port_etat(String var) 	       {      Bd_port_etat=var;         }
    public void setBd_port_etat_date(String var) 	 {      Bd_port_etat_date=var;    }
    public void setMa_sigle(String var) 	             {      Ma_sigle=var;    }
    public void setMa_libelle(String var) 	       {      Ma_libelle=var;    }
    public void setBd_code(int var)                    {      Bd_code=var;              }
    public void setBd_ma_code(int var)                 {      Bd_ma_code=var;           }
    public void setBd_port(int var)                    {      Bd_port=var;           }
    public void setNb_joint(int var)                   {      Nb_joint=var;           }

}
