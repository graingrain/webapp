package javabeans;
import  java.io.*;
public class ItemChef implements Serializable {
    private int ch_code;
    private int ch_action;
    private String ch_libelle;
      
    /**
     * Getter for property ch_libelle.
     * @return Value of property ch_libelle.
     */
    public java.lang.String getCh_libelle() {
        return ch_libelle;
    }
    
    /**
     * Setter for property ch_libelle.
     * @param ch_libelle New value of property ch_libelle.
     */
    public void setCh_libelle(java.lang.String ch_libelle) {
        this.ch_libelle = ch_libelle;
    }
    
    /**
     * Getter for property ch_code.
     * @return Value of property ch_code.
     */
    public int getCh_code() {
        return ch_code;
    }
    
    /**
     * Setter for property ch_code.
     * @param ch_code New value of property ch_code.
     */
    public void setCh_code(int ch_code) {
        this.ch_code = ch_code;
    }
    
    /**
     * Getter for property ch_action.
     * @return Value of property ch_action.
     */
    public int getCh_action() {
        return ch_action;
    }
    
    /**
     * Setter for property ch_action.
     * @param ch_action New value of property ch_action.
     */
    public void setCh_action(int ch_action) {
        this.ch_action = ch_action;
    }
    
}