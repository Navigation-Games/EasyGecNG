/**
 * 
 */
package inOut;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import metier.EasyGec;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import to.langage;

/**
 * <P>
 * Titre : XmlRaid.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author thierry
 *
 */
public class XmlLangage
{
  // D�claration des variables Document et de la racine
  private static Document document;
  private static Element racine;
  private static String LANGS = "langs";
  private static String STRINGS = "strings";
  private static String STRING = "string";
  private static String ID = "id";
  private static String TEXT = "text";
  private static String NAME = "name";


  /**
   * M�thode lisant le fichier XML pass� en param�tre et construisant 
   * Le vecteur contenant tous les parcours
   * @param fichier
   */
  // M�thode de lecture du fichier XML
  public  static void lecture(EasyGec easyGec, URL fichier)
  {
    // System.out.println("In XML:");
    // System.out.println(fichier);

    SAXBuilder sxb = new SAXBuilder();
    try
    {
      document = sxb.build(fichier);
    }
    catch (JDOMException e)
    {
      JOptionPane.showMessageDialog(null,"Erreur de lecture du fichier : "+e.getClass().getName()+", "+e.getMessage());
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(null,"Erreur de lecture du fichier : "+e.getClass().getName()+", "+e.getMessage());
    }
    racine = document.getRootElement();
    recupereAllLangs(easyGec);
    recupereAllStrings(easyGec);
  }
  
  // M�thode r�cup�rant toutes les orienteurs
  private static void recupereAllStrings(EasyGec easyGec)
  {
    Element result = racine.getChild(STRINGS);
    List<?> results = result.getChildren(STRING);
    Iterator<?> i = results.iterator();
    
    while (i.hasNext())
    {
        Element v = (Element) i.next();
        
        langage r = new langage();
        r.setId(v.getAttributeValue(ID));

        List<?> data = v.getChildren(TEXT);
        Iterator<?> j = data.iterator();
        while (j.hasNext())
        {
          Element d = (Element) j.next();
          r.getTexts().add(d.getText());
        }
      
      // Ajout de l'orienteur
        EasyGec.getLangages().getLangages().add(r);
    }
  }
  
  // M�thode r�cup�rant toutes les parcours
  private static void recupereAllLangs(EasyGec easyGec)
  {
    Element pars = racine.getChild(LANGS);
    List<?> parcours = pars.getChildren(NAME);
    Iterator<?> i = parcours.iterator();
    
    while (i.hasNext())
    {
        Element v = (Element) i.next();

        easyGec.getLangs().add(v.getText());
    }
  }
  
}
