package javabeans;
import java.io.*;
public class ItemInstanceWA implements Serializable {
     
    private String Iw_nom;
    private String Iw_libelle;
    private String Iw_shell;
    private String Iw_port1_etat;
    private String Iw_port1_etat_date;
    private String Iw_port2_etat;
    private String Iw_port2_etat_date;
    private String Ma_sigle;
    private String Ma_libelle;
    private int Iw_code;
    private int Iw_ma_code;
    private int Iw_bd_code;
    private int Iw_do_code;
    private int Iw_port1;
    private int Iw_port2;
//  -------------   intregrite relationnelle ---------------------------
    private int    Nb_joint;
    public  int    getNb_joint()          {       return Nb_joint;     }
    public  void   setNb_joint(int var)   {       Nb_joint=var;        }


// methodes get -------------------------------------------------------- 
    public String getIw_nom()    	      {  return Iw_nom;            }
    public String getIw_libelle()         {  return Iw_libelle;        }
    public String getIw_shell()         {  return Iw_shell;        }
    public String getIw_port1_etat()      {  return Iw_port1_etat;     }
    public String getIw_port1_etat_date() {  return Iw_port1_etat_date;}
    public String getIw_port2_etat()  	{  return Iw_port2_etat;     }
    public String getIw_port2_etat_date() {  return Iw_port2_etat_date;}
    public String getMa_sigle()  	      {  return Ma_sigle;          }
    public String getMa_libelle()  	      {  return Ma_libelle;        }
    public int    getIw_code()       	{  return Iw_code; 	     }
    public int    getIw_ma_code()         {  return Iw_ma_code;        }
    public int    getIw_bd_code()         {  return Iw_bd_code;        }
    public int    getIw_do_code()         {  return Iw_do_code;        }
    public int    getIw_port1()           {  return Iw_port1;          }
    public int    getIw_port2()           {  return Iw_port2;          }
// methodes set ------------------------------------------------------------
    public void setIw_nom(String var)             { Iw_nom=var;            }
    public void setIw_libelle(String var)         { Iw_libelle=var;        }
    public void setIw_shell(String var)         { Iw_shell=var;        }
    public void setIw_port1_etat(String var)      { Iw_port1_etat=var;     }
    public void setIw_port1_etat_date(String var) { Iw_port1_etat_date=var;}
    public void setIw_port2_etat(String var) 	  { Iw_port2_etat=var;     }
    public void setIw_port2_etat_date(String var) { Iw_port2_etat_date=var;}
    public void setMa_sigle(String var) 	        { Ma_sigle=var;          }
    public void setMa_libelle(String var)         { Ma_libelle=var;        }
    public void setIw_code(int var)               { Iw_code=var;           }
    public void setIw_ma_code(int var)            { Iw_ma_code=var;        }
    public void setIw_bd_code(int var)            { Iw_bd_code=var;        }
    public void setIw_do_code(int var)            { Iw_do_code=var;        }
    public void setIw_port1(int var)              { Iw_port1=var;          }
    public void setIw_port2(int var)              { Iw_port2=var;          }
}
