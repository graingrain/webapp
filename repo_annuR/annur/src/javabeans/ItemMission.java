package javabeans;
import  java.io.*;
public class ItemMission implements Serializable {
    private int nb;
    private String dv_nom;
    private String dv_nomc;
    private String dv_mission;
    private String sv_nom;
    private String sv_nomc;
    private String sv_mission;
     
    /**
     * Getter for property dv_mission.
     * @return Value of property dv_mission.
     */
    public java.lang.String getDv_mission() {
        return dv_mission;
    }    
    
    /**
     * Setter for property dv_mission.
     * @param dv_mission New value of property dv_mission.
     */
    public void setDv_mission(java.lang.String dv_mission) {
        this.dv_mission = dv_mission;
    }    
   
    /**
     * Getter for property dv_nom.
     * @return Value of property dv_nom.
     */
    public java.lang.String getDv_nom() {
        return dv_nom;
    }
    
    /**
     * Setter for property dv_nom.
     * @param dv_nom New value of property dv_nom.
     */
    public void setDv_nom(java.lang.String dv_nom) {
        this.dv_nom = dv_nom;
    }
    
    /**
     * Getter for property dv_nomc.
     * @return Value of property dv_nomc.
     */
    public java.lang.String getDv_nomc() {
        return dv_nomc;
    }
    
    /**
     * Setter for property dv_nomc.
     * @param dv_nomc New value of property dv_nomc.
     */
    public void setDv_nomc(java.lang.String dv_nomc) {
        this.dv_nomc = dv_nomc;
    }
    
    /**
     * Getter for property nb.
     * @return Value of property nb.
     */
    public int getNb() {
        return nb;
    }
    
    /**
     * Setter for property nb.
     * @param nb New value of property nb.
     */
    public void setNb(int nb) {
        this.nb = nb;
    }
    
    /**
     * Getter for property sv_mission.
     * @return Value of property sv_mission.
     */
    public java.lang.String getSv_mission() {
        return sv_mission;
    }
    
    /**
     * Setter for property sv_mission.
     * @param sv_mission New value of property sv_mission.
     */
    public void setSv_mission(java.lang.String sv_mission) {
        this.sv_mission = sv_mission;
    }
    
    /**
     * Getter for property sv_nom.
     * @return Value of property sv_nom.
     */
    public java.lang.String getSv_nom() {
        return sv_nom;
    }
    
    /**
     * Setter for property sv_nom.
     * @param sv_nom New value of property sv_nom.
     */
    public void setSv_nom(java.lang.String sv_nom) {
        this.sv_nom = sv_nom;
    }
    
    /**
     * Getter for property sv_nomc.
     * @return Value of property sv_nomc.
     */
    public java.lang.String getSv_nomc() {
        return sv_nomc;
    }
    
    /**
     * Setter for property sv_nomc.
     * @param sv_nomc New value of property sv_nomc.
     */
    public void setSv_nomc(java.lang.String sv_nomc) {
        this.sv_nomc = sv_nomc;
    }
    
}