package javabeans;

import java.io.*;

public class ItemParam implements Serializable {
     
    private String element;
    private String libelle;
    private int code;
    private int type;
    private int action;        // 1 = action permettant de clore la demande 2 = non)
// action = code fournisseur pour envoi en réparation (smib=31)   
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

    /**
     * Getter for property action.
     * @return Value of property action.
     */
    public int getAction() {
        return action;
    }
    
    /**
     * Setter for property action.
     * @param action New value of property action.
     */
    public void setAction(int action) {
        this.action = action;
    }
    
}
