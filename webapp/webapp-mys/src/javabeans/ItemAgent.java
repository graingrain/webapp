package javabeans;
import java.io.*;
// description d'un element de l annuaire
//_______________________________________________
public class ItemAgent implements Serializable {
    private String nom;
    private String prenom;
    private String norue;
    private String qualite; 
    private String rue; 
    private String ville;
    private int    codepostal;
    private int    code;
    private int    tel;
    private int    fax;
    private int    gsm;
     
      
// methodes get ----------------------------------------------------------------------
    public String getNom()     		 {      return nom;                }
    public String getPrenom()            {      return prenom;             }
    public String getNorue()    	 {      return norue;              }
    public String getRue()       	 {      return rue;                }
    public String getVille()    	 {      return ville;              }
    public String getQualite()           {      return qualite;            }
    public int    getCodepostal()        {      return codepostal;         }
    public int    getCode()              {      return code;               }
    public int    getTel()    	         {      return tel;                }
    public int    getFax()    	         {      return fax;  	           }
    public int    getGsm()    	         {      return gsm;  	           }
  
// methodes set -------------------------------------------------------------------
    public void setNom(String var)          {        nom            = var;    }
    public void setPrenom(String var)       {        prenom         = var;    }
    public void setNorue(String var)        {        norue        = var;      }
    public void setRue(String var)          {        rue        = var;        }
    public void setVille(String var)        {        ville        = var;      }
    public void setQualite(String var)      {        qualite        = var;    }
    public void setCodepostal(int var)      {        codepostal     = var;    }
    public void setCode(int var)            {        code           = var;    }
    public void setTel(int var)             {        tel            = var;    }
    public void setFax(int var)             {        fax            = var;    }
    public void setGsm(int var)             {        gsm            = var;    }
     
      
}
