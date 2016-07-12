
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ldifsql2 {
public static void main(String[] args) throws IOException  {
    String nomfichlire   = args[0]  ;
    String nomfichecrire = args[1]  ;
    int nbmax=20;
    System.out.println(" nb alternate sup " + nbmax + " ldif to sql de "+nomfichlire+" vers "+nomfichecrire);
    String ligne;
    String lignesortie;
    String t[] = new String[28]; 
    int nb = 0;
    for (int j = 0 ; j < t.length ; j++) t[j] = "";
	t[20]=String.valueOf(nb);

    int i = 0;
//    System.out.println("debut traitement");
    BufferedReader entree = new BufferedReader (new FileReader (nomfichlire));
    PrintWriter sortie = new PrintWriter (new FileWriter (nomfichecrire));
    while ( (ligne = entree.readLine()) != null)
    {
    i++;
// System.out.println("ligne :"+i+" "+ligne );
    if (ligne.length()>8&&(ligne.substring (0,8).equals("dn: uid=")||ligne.substring (0,7).equals("dn: cn=")))
     {
     	nb=0;
int nbt=0;
nbt=Integer.parseInt(t[20]);
if (nbt>4) {
          lignesortie="";
          for (int j = 1 ; j < t.length ; j++) lignesortie += t[j] + "!" ;
          sortie.println(lignesortie);
          for (int j = 0 ; j < t.length ; j++) t[j] = "";
	t[20]=String.valueOf(nb);
     }
     t[0]=ligne.substring(8);
     }
     if (ligne.length()>5&&ligne.substring (0,4).equals("uid:")) 
{        t[1]=ligne.substring(ligne.indexOf(":")+2);
          t[12]=t[1].toLowerCase();
}
     if (ligne.length()>5&&ligne.substring (0,5).equals("mail:"))     
{
          t[2]=ligne.substring(ligne.indexOf(":")+2);
          t[13]=t[2].toLowerCase();
}

//System.out.println(ligne);
     if (ligne.length()>9&&ligne.substring (0,9).equalsIgnoreCase("mailhost:")) t[3]=ligne.substring(ligne.indexOf(":")+2);
     if (ligne.length()>19&&ligne.substring (0,15).equals("employeeNumber:")) t[4]=ligne.substring(ligne.indexOf(":")+2);
     if (ligne.length()>12&&ligne.substring (0,12).equalsIgnoreCase("finfonction:")) t[21]=ligne.substring(ligne.indexOf(":")+2);
     if (ligne.length()>4&&ligne.substring (0,4).equalsIgnoreCase("rne:"))      t[6]=ligne.substring(ligne.indexOf(":")+2);
     if (ligne.length()>9&&ligne.substring (0,9).equalsIgnoreCase("rneextrac")) t[7]=ligne.substring(ligne.indexOf(":")+2);
     if (ligne.length()>18&&ligne.substring (0,13).equalsIgnoreCase("datenaissance")) t[8]=ligne.substring(ligne.indexOf(":")+2);
     if (ligne.length()>6&&ligne.substring (0,6).equalsIgnoreCase("fonctm")) t[16]=ligne.substring(ligne.indexOf(":")+2);
     if (ligne.length()>7&&ligne.substring (0,7).equalsIgnoreCase("typensi")) t[17]=ligne.substring(ligne.indexOf(":")+2);
     if (ligne.length()>21&&ligne.substring (0,21).equalsIgnoreCase("mailEquivalentAddress"))
{
	nb++;
	t[20]=String.valueOf(nb);
}
     if (ligne.length()>21&&ligne.substring (0,20).equalsIgnoreCase("mailAlternateAddress"))
{
	nb++;
	t[20]=String.valueOf(nb);
	t[5]=ligne.substring(ligne.indexOf(":")+2);
}
     if (ligne.length()>3&&ligne.substring (0,3).equals("sn:"))  
{
          t[9]=ligne.substring(ligne.indexOf(":")+2);
          t[14]=t[9].toLowerCase();
}
     if (ligne.length()>9&&ligne.substring (0,9).equals("givenname"))
{ 
          t[10]=ligne.substring(ligne.indexOf(":")+2);
          t[15]=t[10].toLowerCase();
}
     if (ligne.length()>13&&ligne.substring (0,13).equals("modifiersname")) t[11]=ligne.substring(ligne.indexOf(":")+2);
    }
    // ecriture donnees dans tableau
int nbt=0;
nbt=Integer.parseInt(t[20]);     
if (nbt>nbmax) {
System.out.println("ok:"+nbt);
          lignesortie="";
          for (int j = 1 ; j < t.length ; j++) lignesortie += t[j] + "!" ;
          sortie.println(lignesortie);
          }
    entree.close();
    sortie.close();
System.out.println("ligne :"+i+" "+ligne );
  }
}
