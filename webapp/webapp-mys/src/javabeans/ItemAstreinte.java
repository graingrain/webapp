package javabeans;
import java.io.*;
public class ItemAstreinte implements Serializable {
    private String nom;
    private String prenom;
    private String adresse;
    private String observation;
    private String qualite; 
    private int duj; 
    private int dum; 
    private int dua; 
    private int auj;
    private int aum;
    private int aua;
    private int    code;
    private int    codenom;
    private int    nb;
    private int    nb6;
    private int    nb22;
// methodes get ----------------------------------------------------------------------
    public String getNom()     		 {      return nom;                }
    public String getPrenom()            {      return prenom;             }
    public String getAdresse()    	 {      return adresse;            }
    public String getObservation()       {      return observation;        }
    public String getQualite()           {      return qualite;            }
    public int getDuj()                  {      return duj;  	           }
    public int getDum()                  {      return dum;  	           }
    public int getDua()                  {      return dua;  	           }
    public int getAuj()                  {      return auj;  	           }
    public int getAum()                  {      return aum;  	           }
    public int getAua()                  {      return aua;  	           }
    public int    getCode()              {      return code ;              }
    public int    getCodenom()           {      return codenom;            }
    public int    getNb()    	         {      return nb;                }
    public int    getNb6()    	         {      return nb6;  	           }
    public int    getNb22()    	         {      return nb22;  	           }
// methodes set -------------------------------------------------------------------
    public void setNom(String var)          {        nom            = var;    }
    public void setPrenom(String var)       {        prenom         = var;    }
    public void setAdresse(String var)      {        adresse        = var;    }
    public void setObservation(String var)  {        observation    = var;    }
    public void setQualite(String var)      {        qualite        = var;    }
    public void setDuj(int var)             {        duj            = var;    }
    public void setDum(int var)             {        dum            = var;    }
    public void setDua(int var)             {        dua            = var;    }
    public void setAuj(int var)             {        auj            = var;    }
    public void setAum(int var)             {        aum            = var;    }
    public void setAua(int var)             {        aua            = var;    }
    public void setCode(int var)            {        code           = var;    }
    public void setCodenom(int var)         {        codenom        = var;    }
    public void setNb(int var)              {        nb             = var;    }
    public void setNb6(int var)             {        nb6            = var;    }
    public void setNb22(int var)            {        nb22           = var;    }
}
