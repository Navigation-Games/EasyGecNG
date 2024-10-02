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
public class CsvOrienteurs
{
  
  public static void importer(EasyGec esg, String fichier)
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

        // Delimit on semi-colons or commas to support both french and english style .csv files
        tampon = chaine.trim().split(";|,");
        if(tampon.length > 1 )
        {
          try
          {
            Integer.parseInt(tampon [ 0 ]);
            Orienteur r = new Orienteur();
            r.setIdPuce(tampon [ 0 ]);
            r.setNom(tampon [ 1 ]);
            if(tampon.length>2)
            {
              r.setPrenom(tampon [ 2 ]);
            }
            for(int i=0; i<(tampon.length - 3); i++)
            {
                r.getDatas().add(tampon [ 3+i ]);
            }
            esg.getOrienteurs().addOrienteur(r);
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
        tampon = chaine . trim ( ) . split ( ";" ) ;
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
