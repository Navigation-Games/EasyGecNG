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
public class HtmlResultatGlobal
{
  public static void exporterHtml(EasyGec easyGec, String fichier)
  {
    File chemin = new File ( fichier ) ;
    BufferedWriter monFichier;
    try
    {
      monFichier = new BufferedWriter ( new FileWriter ( chemin )) ;
      
      StringBuffer tampon = new StringBuffer();
      tampon.append("<html><body>");
      //tampon.append("<style type='text/css'>tr.entete{font-weight: bold;}tr.jaune{background-color: #ffff96;}tr.bleu{background-color: #affdff;}</style>");
      tampon.append(easyGec.getResultatsCircuitToHtml());
      tampon.append("</body></html>");
      
      monFichier . write ( tampon.toString() , 0 , tampon.toString() . length ());
      monFichier . newLine ( ) ;
      
      monFichier . close ( ) ;
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(null,"Erreur d'écriture du fichier d'export : "+e.getClass().getName()+", "+e.getMessage());
      return;
    }
  }

  public static void exporterHtmlParIdentifiant(EasyGec easyGec, String fichier)
  {
    File chemin = new File ( fichier ) ;
    BufferedWriter monFichier;
    try
    {
      monFichier = new BufferedWriter ( new FileWriter ( chemin )) ;
      
      StringBuffer tampon = new StringBuffer();
      tampon.append("<html><style>td {padding: 10px;}</style><body><h1>Résultats globaux par identifiant</h1>");
      tampon.append(easyGec.getResultatsIdentifiantToHtml());
      tampon.append("</body></html>");
      
      monFichier . write ( tampon.toString() , 0 , tampon.toString() . length ());
      monFichier . newLine ( ) ;
      
      monFichier . close ( ) ;
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(null,"Erreur d'écriture du fichier d'export : "+e.getClass().getName()+", "+e.getMessage());
      return;
    }
  }
}
