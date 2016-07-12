package javabeans;

import java.io.*;

public class ItemAction implements Serializable {
     
    private String mnom;
    private String mlibelle;
    private String mimage;
    private int mcode;
    private int mdroit;
    private int acode;
    private int adroit;
    private int adroitu;
    private String anom;
    private String alibelle;
    private String aimage;
    private String alien;
    
// methodes get ----------------------------------------------------------------------
    
    public String getMnom()    			         {      return mnom;       }
    public String getMlibelle()    			 {      return mlibelle;   }
    public String getMimage()    			 {      return mimage;     }
    public int    getMcode()                     	 {      return mcode; 	   }
    public int    getMdroit()                     	 {      return mdroit; 	   }
    
    public String getAnom()    			         {      return anom;       }
    public String getAlibelle()    			 {      return alibelle;   }
    public String getAimage()    			 {      return aimage;     }
    public String getAlien()    			 {      return alien;     }
    public int    getAcode()                     	 {      return acode; 	   }
    public int    getAdroit()                     	 {      return adroit; 	   }
    public int    getAdroitu()                     	 {      return adroitu;    }
   
// methodes set ---------------------------------------------------------------------------
    public void setMnom(String var)                       {      mnom      = var;    }
    public void setMlibelle(String var)                   {      mlibelle  = var;    }
    public void setMimage(String var)                     {      mimage    = var;    }
    public void setMcode(int var)                         {      mcode     = var;    }
    public void setMdroit(int var)                        {      mdroit    = var;    }

    public void setAnom(String var)                       {      anom      = var;    }
    public void setAlibelle(String var)                   {      alibelle  = var;    }
    public void setAimage(String var)                     {      aimage    = var;    }
    public void setAlien(String var)                      {      alien     = var;    }
    public void setAcode(int var)                         {      acode     = var;    }
    public void setAdroit(int var)                        {      adroit    = var;    }
    public void setAdroitu(int var)                       {      adroitu   = var;    }

}
