package inOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import metier.EasyGec;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import to.MappageAbc;
import to.MappagesAbc;



/**
 * <P>
 * Titre : JDomParse.java
 * </P>
 * <P>
 * Description : Classe métier permettant de lire un fichier XML contenant les personnes
 * </P>
 * @author Thierry PORRET
 *
 */
public class Config
{
  // Déclaration des variables Document et de la racine
  private static Document document;
  private static Element racine;
  private static String CONFIG = "config";
  private static String REPERTOIRE = "rpd";
  private static String LANG = "lang";
  private static String TEMPO = "tempo";
  private static String ABC = "abc";
  private static String MAPPAGE = "mappage";
  private static String NOM = "nom";
  private static String MAP = "map";
  
  /**
   * Méthode lisant le fichier XML passé en paramètre et construisant 
   * Le vecteur contenant toutes les personnes
   * @param String fichier
   */
  // Méthode de lecture du fichier XML
  public  static void lectureFichier(EasyGec easygec, String fichier)
  {
    SAXBuilder sxb = new SAXBuilder();
    try
    {
      document = sxb.build(new File(fichier));
    }
    catch (JDOMException e)
    {
      JOptionPane.showMessageDialog(null,"Le fichier de configuration est introuvable. Un nouveau fichier a été généré.");
      easygec.getMappagesCourant().setMappagesAbcInitial();
      easygec.getMappages().add(easygec.getMappagesCourant());
      enregistre(easygec, "config.xml");
      return;
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(null,"Le fichier de configuration est introuvable. Un nouveau fichier a été généré.");
      easygec.getMappagesCourant().setMappagesAbcInitial();
      easygec.getMappages().add(easygec.getMappagesCourant());
      enregistre(easygec, "config.xml");
      return;
    }
    racine = document.getRootElement();
    
    Element rpd = racine.getChild(REPERTOIRE);
    easygec.setRepertoire(rpd.getText());
    
    if(racine.getChild(LANG) != null)
    {
      Element lang = racine.getChild(LANG);
      easygec.setLangUsed(lang.getText());
    }
    
    if(racine.getChild(TEMPO) != null)
    {
      Element tempo = racine.getChild(TEMPO);
      easygec.setTempo(Integer.parseInt(tempo.getText()));
    }

    if(racine.getChild(ABC) != null)
    {
      Element result = racine.getChild(ABC);

      List<?> mappages = result.getChildren(MAPPAGE);
      Iterator<?> j = mappages.iterator();

      while (j.hasNext())
      {
        Element mappa = (Element) j.next();
        MappagesAbc maps = new MappagesAbc();
        maps.setNom(mappa.getChildText(NOM));
        
        List<?> results = mappa.getChildren(MAP);
        Iterator<?> i = results.iterator();

        while (i.hasNext())
        {
            Element v = (Element) i.next();
            
            MappageAbc mappage = new MappageAbc(v.getText().toLowerCase());
            maps.getMappages().add(mappage);
        }
        easygec.getMappages().add(maps);
      }
      
     }
     else
     {
      easygec.getMappagesCourant().setMappagesAbcInitial();
      easygec.getMappages().add(easygec.getMappagesCourant());
      enregistre(easygec, fichier);
     }

    easygec.setMappagesCourant(easygec.getMappages().get(0));
  }
  
  /**
  /**
   * Méthode créant le fichier XML passé en paramètre 
   * et enregistrant les personnes
   * @param String fichier
   */
  //Méthode qui enregistre au format XML les personnes
  public static void enregistre(EasyGec easygec, String fichier)
  {
    //Déclaration des variables Document et de la racine
    Element racine = new Element(CONFIG);
    Document document = new Document(racine);

    Element ft = new Element(REPERTOIRE);
    ft.setText(easygec.getRepertoire());
    racine.addContent(ft);

    Element lang = new Element(LANG);
    lang.setText(easygec.getLangUsed());
    racine.addContent(lang);

    Element tempo = new Element(TEMPO);
    tempo.setText(easygec.getTempo() + "");
    racine.addContent(tempo);
    
    Element abc = new Element(ABC);
    for(int j=0; j<easygec.getMappages().size(); j++)
    {
      Element mappage = new Element(MAPPAGE);
      Element nom = new Element(NOM);
      nom.setText(easygec.getMappages().get(j).getNom());
      mappage.addContent(nom);
      for(int i=0; i<easygec.getMappages().get(j).getMappages().size(); i++)
      {
        Element map = new Element(MAP);
        map.setText(easygec.getMappages().get(j).getMappages().get(i).getMap());
        mappage.addContent(map);
      }
      abc.addContent(mappage);
    }
    racine.addContent(abc);

      
    // Ecriture du fichier XML
    XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
    FileOutputStream fo;
    try
     {
      fo = new FileOutputStream(fichier);
      sortie.output(document, fo);
      fo.close();
     }
    catch (FileNotFoundException e)
    {
      JOptionPane.showMessageDialog(null,"Erreur d'écriture: "+e.getClass().getName()+", "+e.getMessage());
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(null,"Erreur d'écriture: "+e.getClass().getName()+", "+e.getMessage());
    }
  }
  
}
