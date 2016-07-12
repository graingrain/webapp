package javabeans;
import java.io.*;
// description d'un element de l annuaire
//_______________________________________________
public class ItemMoisEnGreve implements Serializable {
    private String mois;
    private String ligne;
     
     
     
      
// methodes get ----------------------------------------------------------------------
    public String getMois()     		 {      return mois;                }
    public String getLigne()     		 {      return ligne;               }
    
    
// methodes set -------------------------------------------------------------------
    public void setMois(String var)              {        mois            = var;    }
    public void setLigne(String var)             {        ligne           = var;    }
   
}
