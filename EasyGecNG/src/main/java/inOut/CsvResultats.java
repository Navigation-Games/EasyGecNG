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
public class CsvResultats
{
  public static void exporter(EasyGec es, String fichier)
  {
    File chemin = new File ( fichier ) ;
    BufferedWriter monFichier;
    try
    {
      monFichier = new BufferedWriter ( new FileWriter ( chemin )) ;

      StringBuffer buf = new StringBuffer("Clt;Nom;;;Circuit;Temps;Nb de postes;");
      String tampon = buf.toString();
      monFichier . write ( tampon , 0 , tampon . length ());
      monFichier . newLine ( ) ;
      
      for ( int i = 0 ; i < es.getResultatscircuit().size() ; i++ )
      {
        tampon = es.getResultatscircuit().get(i).toCSV() ;
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
}
