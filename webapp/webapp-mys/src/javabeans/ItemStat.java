package javabeans;
import java.io.*;
// description d'un element de l annuaire
//_______________________________________________
public class ItemStat implements Serializable {
    private String division;
    private int nbjuser;
    private int nbjretenue;
    private int nbuser;
     
     
     
      
// methodes get ----------------------------------------------------------------------
    public String getDivision()     		 {      return division;              }
    public int    getNbjuser()     		 {      return nbjuser;               }
    public int    getNbjretenue()     		 {      return nbjretenue;            }
    public int    getNbuser()     		 {      return nbuser;                }
    
    
// methodes set -------------------------------------------------------------------
    public void setDivision(String var)          {        division          = var;    }
    public void setNbjuser(int var)           {        nbjuser           = var;    }
    public void setNbjretenue(int var)        {        nbjretenue        = var;    }
    public void setNbuser(int var)            {        nbuser            = var;    }
     
    
}