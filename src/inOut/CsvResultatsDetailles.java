/**
 * 
 */
package inOut;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import metier.EasyGec;


/**
 * <P>
 * Titre : CsvEquipes.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author thierry
 *
 */
public class CsvResultatsDetailles
{
  public static void exporter(EasyGec es, String fichier)
  {
    File chemin = new File ( fichier ) ;
    BufferedWriter monFichier;
    try
    {
      monFichier = new BufferedWriter ( new FileWriter ( chemin )) ;

      StringBuffer buf = new StringBuffer("N� d�p.;Puce;Ident. base de donn�es;Nom;Pr�nom;N�;S;Plage;nc;D�part;Arriv�e;Temps;Evaluation;N� club;Nom;Ville;Nat;" +
      		"N� cat.;Court;Long;Num1;Num2;Num3;Text1;Text2;Text3;Adr. nom;Rue;Ligne2;Code Post.;Ville;T�l.;Fax;E-mail;Id/Club;Lou�e;Engagement;Pay�;Circuit N�;" +
      		"Circuit;km;m;Postes du circuit;Pl;Poin�on de d�part;Arriv�e (P)");
      String tampon = buf.toString();
      monFichier . write ( tampon , 0 , tampon . length ());
      monFichier . newLine ( ) ;
      
      for ( int i = 0 ; i < es.getResultatsDetaille().size() ; i++ )
      {
        tampon = es.getResultatsDetaille().get(i).toCSV(i+1) ;
          monFichier . write ( tampon , 0 , tampon . length ());
          //monFichier . newLine ( ) ;
      }
      monFichier . close ( ) ;
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(null,"Erreur d'�criture du fichier d'export : "+e.getClass().getName()+", "+e.getMessage());
      return;
    }
  }
  public static void exporterSimple(EasyGec es, String fichier)
  {
    File chemin = new File ( fichier ) ;
    BufferedWriter monFichier;
    try
    {
      monFichier = new BufferedWriter ( new FileWriter ( chemin )) ;

      StringBuffer buf = new StringBuffer("Identifiant;" +
          "Circuit;Nb de postes;Place;D�part;Arriv�e;Temps;");
      String tampon = buf.toString();
      monFichier . write ( tampon , 0 , tampon . length ());
      monFichier . newLine ( ) ;
      
        tampon = es.getResultats().toCSV() ;
          monFichier . write ( tampon , 0 , tampon . length ());
          //monFichier . newLine ( ) ;
      monFichier . close ( ) ;
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(null,"Erreur d'�criture du fichier d'export : "+e.getClass().getName()+", "+e.getMessage());
      return;
    }
  }
}
