package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import core.EasyGec;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import to.AbcMapping;
import to.AbcMappings;



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
  private static String MAPPAGE = "mapping";
  private static String NOM = "name";
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
      easygec.getMappingsCourant().setAbcMappingsInitial();
      easygec.getMappings().add(easygec.getMappingsCourant());
      enregistre(easygec, "config.xml");
      return;
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(null,"Le fichier de configuration est introuvable. Un nouveau fichier a été généré.");
      easygec.getMappingsCourant().setAbcMappingsInitial();
      easygec.getMappings().add(easygec.getMappingsCourant());
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

      List<?> mappings = result.getChildren(MAPPAGE);
      Iterator<?> j = mappings.iterator();

      while (j.hasNext())
      {
        Element mappa = (Element) j.next();
        AbcMappings maps = new AbcMappings();
        maps.setName(mappa.getChildText(NOM));
        
        List<?> results = mappa.getChildren(MAP);
        Iterator<?> i = results.iterator();

        while (i.hasNext())
        {
            Element v = (Element) i.next();
            
            AbcMapping mapping = new AbcMapping(v.getText().toLowerCase());
            maps.getMappings().add(mapping);
        }
        easygec.getMappings().add(maps);
      }
      
     }
     else
     {
      easygec.getMappingsCourant().setAbcMappingsInitial();
      easygec.getMappings().add(easygec.getMappingsCourant());
      enregistre(easygec, fichier);
     }

    easygec.setMappingsCourant(easygec.getMappings().get(0));
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
    for(int j=0; j<easygec.getMappings().size(); j++)
    {
      Element mapping = new Element(MAPPAGE);
      Element name = new Element(NOM);
      name.setText(easygec.getMappings().get(j).getName());
      mapping.addContent(name);
      for(int i=0; i<easygec.getMappings().get(j).getMappings().size(); i++)
      {
        Element map = new Element(MAP);
        map.setText(easygec.getMappings().get(j).getMappings().get(i).getMap());
        mapping.addContent(map);
      }
      abc.addContent(mapping);
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
