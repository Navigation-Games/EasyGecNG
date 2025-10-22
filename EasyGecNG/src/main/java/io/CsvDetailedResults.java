/**
 * 
 */
package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import core.EasyGec;


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
public class CsvDetailedResults
{
  public static void exporter(EasyGec es, String fichier)
  {
    File chemin = new File ( fichier ) ;
    BufferedWriter monFichier;
    try
    {
      monFichier = new BufferedWriter ( new FileWriter ( chemin )) ;

      StringBuffer buf = new StringBuffer("N° dép.;Chip;Ident. base de données;Name;Préname;Né;S;Plage;nc;Départ;Arrivée;Time;Evaluation;N° club;Name;Ville;Nat;" +
      		"N° cat.;Court;Long;Num1;Num2;Num3;Text1;Text2;Text3;Adr. name;Rue;Ligne2;Code Post.;Ville;Tél.;Fax;E-mail;Id/Club;Louée;Engagement;Payé;Course N°;" +
      		"Course;km;m;Postes du course;Pl;Poinçon de départ;Arrivée (P)");
      String tampon = buf.toString();
      monFichier . write ( tampon , 0 , tampon . length ());
      monFichier . newLine ( ) ;
      
      for ( int i = 0 ; i < es.getResultsDetaille().size() ; i++ )
      {
        tampon = es.getResultsDetaille().get(i).toCSV(i+1) ;
          monFichier . write ( tampon , 0 , tampon . length ());
          //monFichier . newLine ( ) ;
      }
      monFichier . close ( ) ;
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(null,"Erreur d'écriture du fichier d'export : "+e.getClass().getName()+", "+e.getMessage());
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

      StringBuffer buf = new StringBuffer("Identifier;" +
          "Course;Nb de postes;Place;Départ;Arrivée;Time;");
      String tampon = buf.toString();
      monFichier . write ( tampon , 0 , tampon . length ());
      monFichier . newLine ( ) ;
      
        tampon = es.getResults().toCSV() ;
          monFichier . write ( tampon , 0 , tampon . length ());
          //monFichier . newLine ( ) ;
      monFichier . close ( ) ;
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(null,"Erreur d'écriture du fichier d'export : "+e.getClass().getName()+", "+e.getMessage());
      return;
    }
  }
}
