package javabeans;

import java.io.*;

public class ItemWinpark implements Serializable {
    private String genre;
    private String type;
    private String modele;
    private String etat;
    private String garantie;
    private int codewp;
// methodes get ----------------------------------------------------------------------
    public String getGenre()    			 {      return genre;      }
    public String getType()    			         {      return type;       }
    public String getModele()    			 {      return modele;     }
    public String getEtat()    			         {      return etat;       }
    public String getGarantie()    			 {      return garantie;   }
    public int    getCodewp()                     	 {      return codewp; 	   }
// methodes set ---------------------------------------------------------------------------
    public void setGenre(String var)                     {      genre    = var;    }
    public void setType(String var)                      {      type     = var;    }
    public void setModele(String var)                    {      modele   = var;    }
    public void setEtat(String var)                      {      etat     = var;    }
    public void setGarantie(String var)                  {      garantie = var;    }
    public void setCodewp(int var)                       {      codewp   = var;    }
}
