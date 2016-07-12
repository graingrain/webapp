package javabeans;

import java.io.*;

public class ItemMenu implements Serializable {
     
    private String mnom;
    private String mlibelle;
    private String mimage;
    private String mlien;
    private int mcode;
    private int mdroit;
    private int mordre;
    
// methodes get ----------------------------------------------------------------------
    
    public String getMnom()    			         {      return mnom;       }
    public String getMlibelle()    			 {      return mlibelle;   }
    public String getMimage()    			 {      return mimage;     }
    public String getMlien()    			 {      return mlien;     }
    public int    getMcode()                     	 {      return mcode; 	   }
    public int    getMdroit()                     	 {      return mdroit; 	   }
    public int    getMordre()                     	 {      return mordre; 	   }
     
// methodes set ---------------------------------------------------------------------------
    public void setMnom(String var)                       {      mnom      = var;    }
    public void setMlibelle(String var)                   {      mlibelle  = var;    }
    public void setMimage(String var)                     {      mimage    = var;    }
    public void setMlien(String var)                      {      mlien     = var;    }
    public void setMcode(int var)                         {      mcode     = var;    }
    public void setMdroit(int var)                        {      mdroit    = var;    }
    public void setMordre(int var)                        {      mordre    = var;    }

}
