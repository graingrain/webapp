package javabeans;
import java.io.*;
public class UserInfo implements Serializable
{ private String message="";
  private String error_message="";
  private String initiales="";
  private String nom="";
  private String prenom="";
  private String mail="";
  private String telephone="";
  private String debug="0";
  private int profil = 0;
  
  /**
   * Getter for property error_message.
   * @return Value of property error_message.
   */
  public java.lang.String getError_message() {
      return error_message;
  }
  
  /**
   * Setter for property error_message.
   * @param error_message New value of property error_message.
   */
  public void setError_message(java.lang.String error_message) {
      this.error_message = error_message;
  }
  
  /**
   * Getter for property initiales.
   * @return Value of property initiales.
   */
  public java.lang.String getInitiales() {
      return initiales;
  }
  
  /**
   * Setter for property initiales.
   * @param initiales New value of property initiales.
   */
  public void setInitiales(java.lang.String initiales) {
      this.initiales = initiales;
  }
  
  /**
   * Getter for property mail.
   * @return Value of property mail.
   */
  public java.lang.String getMail() {
      return mail;
  }
  
  /**
   * Setter for property mail.
   * @param mail New value of property mail.
   */
  public void setMail(java.lang.String mail) {
      this.mail = mail;
  }
  
  /**
   * Getter for property message.
   * @return Value of property message.
   */
  public java.lang.String getMessage() {
      return message;
  }
  
  /**
   * Setter for property message.
   * @param message New value of property message.
   */
  public void setMessage(java.lang.String message) {
      this.message = message;
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
   * Getter for property prenom.
   * @return Value of property prenom.
   */
  public java.lang.String getPrenom() {
      return prenom;
  }
  
  /**
   * Setter for property prenom.
   * @param prenom New value of property prenom.
   */
  public void setPrenom(java.lang.String prenom) {
      this.prenom = prenom;
  }
  
  /**
   * Getter for property telephone.
   * @return Value of property telephone.
   */
  public java.lang.String getTelephone() {
      return telephone;
  }
  
  /**
   * Setter for property telephone.
   * @param telephone New value of property telephone.
   */
  public void setTelephone(java.lang.String telephone) {
      this.telephone = telephone;
  }
  
  /**
   * Getter for property debug.
   * @return Value of property debug.
   */
  public java.lang.String getDebug() {
      return debug;
  }
  
  /**
   * Setter for property debug.
   * @param debug New value of property debug.
   */
  public void setDebug(java.lang.String debug) {
      this.debug = debug;
  }
  
  /**
   * Getter for property profil.
   * @return Value of property profil.
   */
  public int getProfil() {
      return profil;
  }
  
  /**
   * Setter for property profil.
   * @param profil New value of property profil.
   */
  public void setProfil(int profil) {
      this.profil = profil;
  }
  
}