package javabeans;

import java.io.*;

public class ItemJournal implements Serializable {
    private String date;
    private String ouvert;
    private String nom;
    private String ouvert_lib;
    
    /**
     * Getter for property date.
     * @return Value of property date.
     */
    public java.lang.String getDate() {
        return date;
    }    
 
    /**
     * Setter for property date.
     * @param date New value of property date.
     */
    public void setDate(java.lang.String date) {
        this.date = date;
    }
    
    /**
     * Getter for property nom.
     * @return Value of property nom.
     */
    public java.lang.String getNom() {
        return nom;
    }
    
    /**
     * Setter for property nom.
     * @param nom New value of property nom.
     */
    public void setNom(java.lang.String nom) {
        this.nom = nom;
    }
    
    /**
     * Getter for property ouvert.
     * @return Value of property ouvert.
     */
    public java.lang.String getOuvert() {
        return ouvert;
    }
    
    /**
     * Setter for property ouvert.
     * @param ouvert New value of property ouvert.
     */
    public void setOuvert(java.lang.String ouvert) {
        this.ouvert = ouvert;
    }
    
    /**
     * Getter for property ouvert_lib.
     * @return Value of property ouvert_lib.
     */
    public java.lang.String getOuvert_lib() {
        return ouvert_lib;
    }
    
    /**
     * Setter for property ouvert_lib.
     * @param ouvert_lib New value of property ouvert_lib.
     */
    public void setOuvert_lib(java.lang.String ouvert_lib) {
        this.ouvert_lib = ouvert_lib;
    }
    
}
