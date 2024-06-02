/**
 * 
 */
package inOut;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import to.ResultatPuce;


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
public class HtmlResultatPuce
{
  public static void save(ResultatPuce re, String fichier)
  {
    File chemin = new File ( fichier ) ;
    BufferedWriter monFichier;
    try
    {
      monFichier = new BufferedWriter ( new FileWriter ( chemin )) ;

      StringBuffer tampon = new StringBuffer("<html>\n<head>\n<STYLE TYPE='text/css'>\n<!--\nTH{font-family: Arial; font-size: 20pt;}\nTD{font-family: Arial; font-size: 20pt;}\n--->\n</STYLE>\n</head>\n<body>");
      //StringBuffer tampon = new StringBuffer("<html>\n<head>\n<STYLE TYPE='text/css'>\n<!--\nTH{font-family: Arial; font-size: 10pt;}\nTD{font-family: Arial; font-size: 10pt;}\n--->\n</STYLE>\n</head>\n<body>");
      //tampon.append("<font size=2>");
      tampon.append("<font size=12>");
      //tampon.append(geraid.entete + "<br>");
      //tampon.append("<b>" + re.getCircuit().getNom() + "</b><br><br>");
      tampon.append(re.toHtml());
      //tampon.append("<font size=2>" + geraid.piedPage + "</font><br>");
      tampon.append("<br><b>Chronométré avec EasyGec</b>");
      tampon.append("</font>");
      tampon.append("</body></html>");
      monFichier . write ( tampon.toString() , 0 , tampon . length ());
      monFichier . newLine ( ) ;
      
      monFichier . close ( ) ;
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(null,"Erreur d'écriture du fichier en html : "+e.getClass().getName()+", "+e.getMessage());
      return;
    }
  }
}
