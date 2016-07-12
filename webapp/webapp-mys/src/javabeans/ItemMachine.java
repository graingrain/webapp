package javabeans;
import java.io.*;
public class ItemMachine implements Serializable {
     
    private String ma_libelle;
    private String ma_sigle;
    private String ma_adresse;
    private String ma_etat;
    private String ma_etat_date;
    
    private int ma_nb_bdd;
    private int ma_code;
    private int ma_zn_code;
// methodes get ----------------------------------------------------------------------
    public String getMa_libelle()    			         {      return ma_libelle;       }
    public String getMa_sigle()    		         	 {      return ma_sigle;         }
    public String getMa_adresse()  			         {      return ma_adresse;       }
    public String getMa_etat()  			         {      return ma_etat;          }
    public String getMa_etat_date()  			         {      return ma_etat_date;     }
    public int    getMa_nb_bdd()                     	         {      return ma_nb_bdd; 	 }
    public int    getMa_code()                     	         {      return ma_code; 	 }
    public int    getMa_zn_code()                     	         {      return ma_zn_code; 	 }
// methodes set ---------------------------------------------------------------------------
    public void setMa_libelle(String var)     			                 {      ma_libelle=var;     }
    public void setMa_sigle(String var)    		         	         {      ma_sigle=var;       }
    public void setMa_adresse(String var)  			                 {      ma_adresse=var;     }
    public void setMa_etat(String var) 		                                 {      ma_etat=var;        }
    public void setMa_etat_date(String var) 			                 {      ma_etat_date=var;   }
    public void setMa_nb_bdd(int var)                    	                 {      ma_nb_bdd=var;      }
    public void setMa_code(int var)                    	                         {      ma_code=var;        }
    public void setMa_zn_code(int var)                    	                 {      ma_zn_code=var;     }
}
