package javabeans;

import java.io.*;

public class ItemInfo implements Serializable {
     
    private String date;
    private String nom;
    private String info;
         
// methodes get ----------------------------------------------------------------------
    
    public String getDate()    			 {      return date;    }
    public String getNom()    			 {      return nom;     }
    public String getInfo()                      {      return info;    }
     
// methodes set ---------------------------------------------------------------------------
    public void setDate(String var)              {      date  = var;    }
    public void setNom(String var)               {      nom   = var;    }
    public void setInfo(String var)              {      info  = var;    }
   
}
