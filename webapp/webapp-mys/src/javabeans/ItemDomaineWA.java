package javabeans;
import java.io.*;
public class ItemDomaineWA implements Serializable {
     
   
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
//  -------------   intregrite relationnelle --------------------------------------------
    private int    Nb_joint;
    public  int    getNb_joint()                          {      return Nb_joint;        }
    public  void   setNb_joint(int var)                   {      Nb_joint=var;           }

   
// methodes get ----------------------------------------------------------------------
    public String getDo_nom()  		               {      return Do_nom;           }
    public String getDo_libelle()  		       {      return Do_libelle;           }
    public String getDo_portc_etat()    	       {      return Do_portc_etat;     }
    public String getDo_portc_etat_date()  	       {      return Do_portc_etat_date;}
    public String getDo_wa_type()  		       {      return Do_wa_type;          }
    public String getDo_wa_libelle()  		       {      return Do_wa_libelle;        }
    public String getDo_user()  		       {      return Do_user;           }
    public String getDo_passwd()  		       {      return Do_passwd;         }
    public int    getDo_code()                         {      return Do_code; 	        }
    public int    getDo_ma_code()                      {      return Do_ma_code;        }
    public int    getDo_wa_code()                      {      return Do_wa_code;        }
    public int    getDo_portc()                        {      return Do_portc;          }
 // methodes set ---------------------------------------------------------------------------
    public void setDo_nom(String var) 	             {      Do_nom=var;              }
    public void setDo_libelle(String var) 	       {      Do_libelle=var;              }
    public void setDo_portc_etat(String var)           {      Do_portc_etat=var;        }
    public void setDo_portc_etat_date(String var)      {      Do_portc_etat_date=var;   }
    public void setDo_wa_type(String var) 	       {      Do_wa_type=var;             }
    public void setDo_wa_libelle(String var) 	       {      Do_wa_libelle=var;           }
    public void setDo_user(String var) 	             {      Do_user=var;              }
    public void setDo_passwd(String var) 	             {      Do_passwd=var;            }
    public void setDo_code(int var)                    {      Do_code=var;              }
    public void setDo_ma_code(int var)                 {      Do_ma_code=var;           }
    public void setDo_wa_code(int var)                 {      Do_wa_code=var;           }
    public void setDo_portc(int var)                   {      Do_portc=var;             }
 }
