package javabeans;
import java.io.*;
public class ItemWebapp implements Serializable {
     
    private String Wa_type;
    private String Wa_repertoire;
    private String Wa_version;
 
    private String Ma_sigle;
    private String Ma_libelle;
     
    private int    Wa_code;
    private int    Wa_ma_code;
//  -------------   intregrite relationnelle --------------------------------------------
    private int    Nb_joint;
    public  int    getNb_joint()                          {      return Nb_joint;        }
    public  void   setNb_joint(int var)                   {      Nb_joint=var;           }


   
// methodes get ----------------------------------------------------------------------
    public String getWa_type()    		         	 {      return Wa_type;           }
    public String getWa_repertoire()  		       {      return Wa_repertoire;     }
    public String getWa_version()  		             {      return Wa_version;        }
    public String getMa_sigle()  		             {      return Ma_sigle;          }
    public String getMa_libelle()  		             {      return Ma_libelle;        }
    public int    getWa_code()                     	 {      return Wa_code; 	    }
    public int    getWa_ma_code()                      {      return Wa_ma_code;        }
 // methodes set ---------------------------------------------------------------------------
    public void setWa_type(String var)    		 {      Wa_type=var;              }
    public void setWa_repertoire(String var)  		 {      Wa_repertoire=var;        }
    public void setWa_version(String var)  		 {      Wa_version=var;           }
    public void setMa_sigle(String var) 	             {      Ma_sigle=var;             }
    public void setMa_libelle(String var) 	       {      Ma_libelle=var;           }
    public void setWa_code(int var)                    {      Wa_code=var;              }
    public void setWa_ma_code(int var)                 {      Wa_ma_code=var;           }
 }
