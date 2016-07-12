package javabeans;

import java.io.*;

public class ItemParam implements Serializable {
     
    private String element;
    private String libelle;
    private int code;
    private int type;
    
// methodes get ----------------------------------------------------------------------
    
    public String getElement()    			 {      return element;    }
    public String getLibelle()    			 {      return libelle;    }
    public int    getCode()                     	 {      return code; 	   }
    public int    getType()                     	 {      return type; 	   }
     
// methodes set ---------------------------------------------------------------------------
    public void setElement(String var)                   {      element  = var;    }
    public void setLibelle(String var)                   {      libelle  = var;    }
    public void setCode(int var)                         {      code     = var;    }
    public void setType(int var)                         {      type     = var;    }

}
