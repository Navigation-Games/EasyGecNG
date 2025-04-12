/**
 * 
 */
package inOut;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import outils.TimeManager;

import to.Circuit;

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
public class CsvCircuits
{
  
  public static void importer(EasyGec esg, String fichier)
  {
    
    File chemin = new File ( fichier ) ;
    String chaine ;
    String [ ] tampon ;
    //Vector<Integer> lignes = new Vector<Integer>();
    //int ligne = 1;

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
        //ligne++;
        tampon = chaine . trim ( ) . split ( ";" ) ;
        
          Circuit r = new Circuit();
          r.setNom(tampon [ 0 ]);
          esg.addAbc(r);
            if(tampon.length>1)
            {
              r.setDepartBoitier(false);
              r.setHeureDepart(TimeManager.safeParse(tampon [ 1 ]));
            }
        else
        {
          //lignes.add(ligne);
        }
    }
      monFichier . close ( ) ;
      /*if(lignes.size()>0)
      {
        StringBuffer message = new StringBuffer("Certains résultats n'ont pu être importés :\nLignes ");
        for(int i=0; i<lignes.size(); i++)
        {
          message.append(lignes.get(i)+",");
        }
        message.append("\nVérifiez que ces résultats ont une puce valide.");
        JOptionPane.showMessageDialog(esg.getIhm(), message.toString(), "Import des résultats", JOptionPane.OK_OPTION);
      }*/
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(null,"Erreur d'import : "+e.getClass().getName()+", "+e.getMessage());
      return;
    }
  }
}
