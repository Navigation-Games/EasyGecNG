/**
 * 
 */
package inOut;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;

import to.Orienteur;

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
public class CsvOrienteurs {
  
  public static void importer(EasyGec esg, String fichier) {
    
    File chemin = new File ( fichier ) ;
    String chaine ;
    String [ ] tampon ;
    Vector<Integer> lignes = new Vector<Integer>(); // vector of CSV lines that could not be processed
    int ligne = 1;

    try {
      // Why do this? should probably just return instead
      if (!chemin.exists()) {
        chemin.createNewFile();
      }
      BufferedReader monFichier = new BufferedReader( new FileReader( chemin )) ;
      
      // Discard the header line
      monFichier.readLine();

      while ((chaine= monFichier.readLine()) != null) {
        ligne++;

        // Delimit on semi-colons or commas to support both french and english style .csv files
        tampon = chaine.trim().split(";|,");
        
        // Discard too-short lines
        if (tampon.length <= 1 )
        {
          lignes.add(ligne);
          continue;
        }

        // Else, try to process line
        try {
          // Ensure first element is integer
          Integer.parseInt(tampon[0]);

          // Set Orienteer ID and names
          Orienteur r = new Orienteur();
          r.setIdPuce(tampon[0]);
          r.setNom(tampon[1]);
          if (tampon.length>2)
          {
            r.setPrenom(tampon [ 2 ]);
          }

          // Add remaining elements to orienteer datas
          for (int i=3; i<tampon.length; i++)
          {
              r.getDatas().add(tampon [ i ]);
          }

          // Register Orienteer
          esg.getOrienteurs().addOrienteur(r);
        } catch (NumberFormatException e) {
          lignes.add(ligne);
        }
      }

      monFichier.close();
      if (lignes.size() > 0) {
        StringBuffer message = new StringBuffer("Certains r�sultats n'ont pu �tre import�s :\nLignes ");
        for (int i=0; i<lignes.size(); i++) {
          message.append(lignes.get(i)+",");
        }
        message.append("\nV�rifiez que ces r�sultats ont une puce valide.");
        JOptionPane.showMessageDialog(esg.getIhm(), message.toString(), "Import des r�sultats", JOptionPane.OK_OPTION);
      }
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null,"Erreur d'import : "+e.getClass().getName()+", "+e.getMessage());
      return;
    }
  }
  
  public static void importerSi(EasyGec esg, String fichier)
  {
    
    File chemin = new File ( fichier ) ;
    String chaine ;
    String [ ] tampon ;
    Vector<Integer> lignes = new Vector<Integer>();
    int ligne = 1;

    try
    {
    if (!chemin.exists())
    {
      chemin.createNewFile();
    }
    BufferedReader monFichier = new BufferedReader ( new FileReader ( chemin )) ;
    monFichier . readLine ( );
    while (( chaine = monFichier . readLine ( )) != null )
    {
        ligne++;
        tampon = chaine.trim().split(";|,");
        if(tampon.length > 1 )
        {
          try
          {
            Integer.parseInt(tampon [ 0 ]);
            Orienteur r = new Orienteur();
            r.setIdPuce(tampon [ 0 ]);
            r.setNom(tampon [ 1 ]);
            r.setPrenom(tampon [ 2 ]);
            for(int i=0; i<(tampon.length - 3); i++)
            {
                r.getDatas().add(tampon [ 3+i ]);
            }
            esg.getOrienteursSi().addOrienteur(r);
          }
          catch (NumberFormatException e)
          {
            lignes.add(ligne);
          }
        }
        else
        {
          lignes.add(ligne);
        }
    }
      monFichier . close ( ) ;
      if(lignes.size()>0)
      {
        StringBuffer message = new StringBuffer("Certains r�sultats n'ont pu �tre import�s :\nLignes ");
        for(int i=0; i<lignes.size(); i++)
        {
          message.append(lignes.get(i)+",");
        }
        message.append("\nV�rifiez que ces r�sultats ont une puce valide.");
        JOptionPane.showMessageDialog(esg.getIhm(), message.toString(), "Import des r�sultats", JOptionPane.OK_OPTION);
      }
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(null,"Erreur d'import : "+e.getClass().getName()+", "+e.getMessage());
      return;
    }
  }
}
