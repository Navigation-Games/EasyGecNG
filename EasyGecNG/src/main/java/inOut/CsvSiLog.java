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

import outils.TimeManager;

import to.Partiel;
import to.ResultatSi;

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
public class CsvSiLog
{
  
  public static void importer(EasyGec esg, String fichier)
  {
    
    File chemin = new File ( fichier ) ;
    String chaine ;
    boolean siConfigPlus = false;
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
    chaine = monFichier . readLine ( );
    tampon = chaine.trim().split(";|,");
    if(tampon[0].compareTo("No")==0)
    {
      siConfigPlus = true;
    }
    if(siConfigPlus)
    {
      while (( chaine = monFichier . readLine ( )) != null )
      {
          ligne++;
          tampon = chaine.trim().split(";|,");
          if(tampon.length > 1 )
          {
            try
            {
              Integer.parseInt(tampon [ 2 ]);
              ResultatSi r = new ResultatSi();
              r.getPuce().setIdPuce(tampon [ 2 ]);
              r.getPuce().setStarttime(TimeManager.safeParse(tampon [ 15 ]));
              r.getPuce().setFinishtime(TimeManager.safeParse(tampon [ 21 ]));
              r.getPuce().setPartiels(new Partiel[(tampon.length - 45)/3]);
              for(int i=0; i<(tampon.length - 45)/3; i++)
              {
                if(tampon [ 45+i*3 ].compareTo("")!=0 && tampon [ 47+i*3 ].compareTo("") != 0)
                {
                  Partiel p = new Partiel();
                  p.setCode(Integer.parseInt(tampon [ 45+i*3 ]));
                  p.setTime(TimeManager.safeParse(tampon [ 47+i*3 ]));
                  r.getPuce().getPartiels()[i] = p;
                }
              }
              esg.getResultatsSi().addResultatSi(r);
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
    }
    else
    {
      while (( chaine = monFichier . readLine ( )) != null )
      {
          ligne++;
          tampon = chaine.trim().split(";|,");
          if(tampon.length > 1 )
          {
            try
            {
              Integer.parseInt(tampon [ 2 ]);
              ResultatSi r = new ResultatSi();
              r.getPuce().setIdPuce(tampon [ 2 ]);
              r.getPuce().setStarttime(TimeManager.safeParse(tampon [ 24 ]));
              r.getPuce().setFinishtime(TimeManager.safeParse(tampon [ 27 ]));
              r.getPuce().setPartiels(new Partiel[(tampon.length - 29)/3]);
              for(int i=0; i<(tampon.length - 29)/3; i++)
              {
                if(tampon [ 29+i*3 ].compareTo("")!=0 && tampon [ 31+i*3 ].compareTo("") != 0)
                {
                  Partiel p = new Partiel();
                  p.setCode(Integer.parseInt(tampon [ 29+i*3 ]));
                  p.setTime(TimeManager.safeParse(tampon [ 31+i*3 ]));
                  r.getPuce().getPartiels()[i] = p;
                }
              }
              esg.getResultatsSi().addResultatSi(r);
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
