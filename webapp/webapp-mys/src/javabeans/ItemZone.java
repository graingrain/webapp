package javabeans;
import java.io.*;
public class ItemZone implements Serializable {
     
    private String Zn_libelle;
    private String Zn_machine;
    private String Zn_pt_rep;
    private String Zn_couleur;
    private String Zn_date;
    private int Zn_code;
    private int Zn_poul;
     

// methodes get ----------------------------------------------------------------------
    public String getZn_libelle()    		       {      return Zn_libelle;        }
    public String getZn_machine()  		       {      return Zn_machine;        }
    public String getZn_couleur()  		       {      return Zn_couleur;        }
    public String getZn_date()  		       {      return Zn_date;           }
    public int    getZn_code()                         {      return Zn_code; 	        }
    public int    getZn_poul()                         {      return Zn_poul;           }
  
// methodes set ---------------------------------------------------------------------------
    public void setZn_libelle(String var)    	       {      Zn_libelle=var;           }
    public void setZn_machine(String var)              {      Zn_machine=var;           }
    public void setZn_couleur(String var)              {      Zn_couleur=var;           }
    public void setZn_date(String var) 	               {      Zn_date=var;              }
    public void setZn_code(int var)                    {      Zn_code=var;              }
    public void setZn_poul(int var)                    {      Zn_poul=var;              }
     
    /** Getter for property Zn_pt_rep.
     * @return Value of property Zn_pt_rep.
     */
    public String getZn_pt_rep() {
        return Zn_pt_rep;
    }
    
    /** Setter for property Zn_pt_rep.
     * @param Zn_pt_rep New value of property Zn_pt_rep.
     */
    public void setZn_pt_rep(String Zn_pt_rep) {
        this.Zn_pt_rep = Zn_pt_rep;
    }
    
}
