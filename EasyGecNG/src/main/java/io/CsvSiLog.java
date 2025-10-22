/**
 * 
 */
package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;

import utils.TimeManager;

import to.Split;
import to.SiResult;

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
    tampon = chaine . trim ( ) . split ( ";" ) ;
    if(tampon[0].compareTo("No")==0)
    {
      siConfigPlus = true;
    }
    if(siConfigPlus)
    {
      while (( chaine = monFichier . readLine ( )) != null )
      {
          ligne++;
          tampon = chaine . trim ( ) . split ( ";" ) ;
          if(tampon.length > 1 )
          {
            try
            {
              Integer.parseInt(tampon [ 2 ]);
              SiResult r = new SiResult();
              r.getChip().setIdChip(tampon [ 2 ]);
              r.getChip().setStarttime(TimeManager.safeParse(tampon [ 15 ]));
              r.getChip().setFinishtime(TimeManager.safeParse(tampon [ 21 ]));
              r.getChip().setSplits(new Split[(tampon.length - 45)/3]);
              for(int i=0; i<(tampon.length - 45)/3; i++)
              {
                if(tampon [ 45+i*3 ].compareTo("")!=0 && tampon [ 47+i*3 ].compareTo("") != 0)
                {
                  Split p = new Split();
                  p.setCode(Integer.parseInt(tampon [ 45+i*3 ]));
                  p.setTime(TimeManager.safeParse(tampon [ 47+i*3 ]));
                  r.getChip().getSplits()[i] = p;
                }
              }
              esg.getSiResults().addSiResult(r);
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
          tampon = chaine . trim ( ) . split ( ";" ) ;
          if(tampon.length > 1 )
          {
            try
            {
              Integer.parseInt(tampon [ 2 ]);
              SiResult r = new SiResult();
              r.getChip().setIdChip(tampon [ 2 ]);
              r.getChip().setStarttime(TimeManager.safeParse(tampon [ 24 ]));
              r.getChip().setFinishtime(TimeManager.safeParse(tampon [ 27 ]));
              r.getChip().setSplits(new Split[(tampon.length - 29)/3]);
              for(int i=0; i<(tampon.length - 29)/3; i++)
              {
                if(tampon [ 29+i*3 ].compareTo("")!=0 && tampon [ 31+i*3 ].compareTo("") != 0)
                {
                  Split p = new Split();
                  p.setCode(Integer.parseInt(tampon [ 29+i*3 ]));
                  p.setTime(TimeManager.safeParse(tampon [ 31+i*3 ]));
                  r.getChip().getSplits()[i] = p;
                }
              }
              esg.getSiResults().addSiResult(r);
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
        StringBuffer message = new StringBuffer("Certains résultats n'ont pu être importés :\nLignes ");
        for(int i=0; i<lignes.size(); i++)
        {
          message.append(lignes.get(i)+",");
        }
        message.append("\nVérifiez que ces résultats ont une chip valide.");
        JOptionPane.showMessageDialog(esg.getIhm(), message.toString(), "Import des résultats", JOptionPane.OK_OPTION);
      }
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(null,"Erreur d'import : "+e.getClass().getName()+", "+e.getMessage());
      return;
    }
  }
}
