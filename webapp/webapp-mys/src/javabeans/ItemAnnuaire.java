package javabeans;
import java.io.*;
// description d'un element de l annuaire
//_______________________________________________
public class ItemAnnuaire implements Serializable {
    private String nom;
    private String prenom;
    private String identifiant;
    private String insee;
    private String qt; 
    private String tp; 
    private String datesaisie;
    private int    nbjmaxi;
        
    private int nbj;
    private int nbjr;
     
      
// methodes get ----------------------------------------------------------------------
    public String getNom()     		 {      return nom;                }
    public String getPrenom()            {      return prenom;             }
    public String getIdentifiant()    	 {      return identifiant;        }
    public String getInsee()    	 {      return insee;              }
    public String getQt()    	         {      return qt;  	           }
    public String getTp()    	         {      return tp;  	           }
    public String getDatesaisie()        {      return datesaisie;         }
    public int    getNbj()               {      return nbj;  	           }
    public int    getNbjr()              {      return nbjr;  	           }
    public int    getNbjmaxi()           {      return nbjmaxi;            }
    
// methodes set -------------------------------------------------------------------
    public void setNom(String var)          {        nom            = var;    }
    public void setPrenom(String var)       {        prenom         = var;    }
    public void setIdentifiant(String var)  {        identifiant       = var; }
    public void setInsee(String var)        {        insee          = var;    }
    public void setQt(String var)           {        qt      = var;           }
    public void setTp(String var)           {        tp      = var;           }
    public void setDatesaisie(String var)   {        datesaisie      = var;   }
    public void setNbj(int var)             {        nbj      = var;          }
    public void setNbjr(int var)            {        nbjr     = var;          }
    public void setNbjmaxi(int var)         {        nbjmaxi  = var;          }
     
      
}
