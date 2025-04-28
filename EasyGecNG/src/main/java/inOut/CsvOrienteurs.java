/**
 * 
 */
package inOut;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.List;

import javax.swing.JOptionPane;

import to.Orienteur;

import metier.EasyGec;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;


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
  
  public static void importer(EasyGec esg, String filepath) {
    
    File file = new File(filepath);
    Vector<Integer> linesNotProcessed = new Vector<Integer>(); // vector of CSV lines that could not be processed
    
    if (!file.exists()) {
      JOptionPane.showMessageDialog(null, "File does not exist : " + file);
      return;
    }

    
    // Create a CSV parser with autodetection enabled
    CsvParserSettings settings = new CsvParserSettings();
    settings.detectFormatAutomatically();
    CsvParser parser = new CsvParser(settings);

    // Parse file
    List<String[]> rows = parser.parseAll(file);

    System.out.println(parser.getDetectedFormat());

    // Iterate through rows, skipping the header
    for (int i = 1; i<rows.size(); i++) {
      String[] row = rows.get(i);

      // Entries must have at least two fields - ID and name
      if (row.length < 2 ) {
        linesNotProcessed.add(i);
        continue;
      }

      try {

        // Ensure the first element is an int
        Integer.parseInt(row[0]);

        // Set Orienteer ID and names
        Orienteur orienteer = new Orienteur();
        orienteer.setIdPuce(row[0]);
        orienteer.setNom(row[1]);
        if (row.length>2) {
          orienteer.setPrenom(row[2]);
        }
        
        // Add remaining elements to orienteer datas
        for (int j=3; j<row.length; j++) {
          orienteer.getDatas().add(row[j]);
        }
        
        // Register the orienteer
        esg.getOrienteurs().addOrienteur(orienteer);

      } catch (NumberFormatException e) {
        linesNotProcessed.add(i);
      }
    }
    
    // Show errored lines
    if (linesNotProcessed.size() > 0) {
      StringBuffer message = new StringBuffer("Certains résultats n'ont pu être importés :\nLignes ");
      
      for (int line : linesNotProcessed) {
        message.append(line + ", ");
      }
      
      message.append("\nVérifiez que ces résultats ont une puce valide.");
      JOptionPane.showMessageDialog(esg.getIhm(), message.toString(), "Import des résultats", JOptionPane.OK_OPTION);
    }
    
  }
  
  public static void importerSi(EasyGec esg, String fichier) {
    
    File chemin = new File ( fichier ) ;
    String chaine ;
    String [ ] tampon ;
    Vector<Integer> lignes = new Vector<Integer>();
    int ligne = 1;

    try {

      if (!chemin.exists()) {
        chemin.createNewFile();
      }

      BufferedReader monFichier = new BufferedReader( new FileReader( chemin ));
      monFichier.readLine();

      while (( chaine = monFichier . readLine ( )) != null ) {
        ligne++;
        tampon = chaine.trim().split(";");
        if (tampon.length > 1) {
          try {
            Integer.parseInt(tampon [ 0 ]);
            Orienteur r = new Orienteur();
            r.setIdPuce(tampon [ 0 ]);
            r.setNom(tampon [ 1 ]);
            r.setPrenom(tampon [ 2 ]);

            for(int i=0; i<(tampon.length - 3); i++) {
                r.getDatas().add(tampon [ 3+i ]);
            }
            
            esg.getOrienteursSi().addOrienteur(r);
          
          } catch (NumberFormatException e) {
            lignes.add(ligne);
          }
        } else {
          lignes.add(ligne);
        }
      }
      
      monFichier.close();

      if (lignes.size()>0) {
      
        StringBuffer message = new StringBuffer("Certains résultats n'ont pu être importés :\nLignes ");
        
        for (int i=0; i<lignes.size(); i++) {
          message.append(lignes.get(i)+",");
        }
        
        message.append("\nVérifiez que ces résultats ont une puce valide.");
        JOptionPane.showMessageDialog(esg.getIhm(), message.toString(), "Import des résultats", JOptionPane.OK_OPTION);
      }

    } catch (IOException e) {
      JOptionPane.showMessageDialog(null,"Erreur d'import : "+e.getClass().getName()+", "+e.getMessage());
      return;
    }
  }
}
