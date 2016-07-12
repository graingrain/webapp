package javabeans;
import java.io.*;
public class ItemLog implements Serializable {
     
    private String Lg_type;
    private String Lg_type_code;
    private String Lg_etat;
    private String Lg_etat_date;
    private String Lg_libelle;
    private int Lg_code;
// methodes get ----------------------------------------------------------------------
    public String getLg_type()    		         	 {      return Lg_type;           }
    public String getLg_type_code()  		       {      return Lg_type_code;     }
    public String getLg_etat()  		       {      return Lg_etat;      }
    public String getLg_etat_date()  		 {      return Lg_etat_date; }
    public String getLg_libelle()  		 {      return Lg_libelle; }
    public int    getLg_code()                     	 {      return Lg_code; 	    }
// methodes set ---------------------------------------------------------------------------
    public void setLg_type(String var)    		 {      Lg_type=var;              }
    public void setLg_type_code(String var)  		 {      Lg_type_code=var;        }
    public void setLg_etat(String var) 	       {      Lg_etat=var;         }
    public void setLg_etat_date(String var) 	 {      Lg_etat_date=var;    }
    public void setLg_libelle(String var) 	 {      Lg_libelle=var;    }
    public void setLg_code(int var)                    {      Lg_code=var;              }
    
}
