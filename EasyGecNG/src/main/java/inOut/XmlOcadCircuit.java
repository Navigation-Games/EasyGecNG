/**
 * 
 */
package inOut;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import metier.EasyGec;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import to.Circuit;

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
public class XmlOcadCircuit
{
  // Déclaration des variables Document et de la racine
  private static Document document;
  private static Element racine;
  private static String IOFVERSIONV2 = "IOFVersion";
  private static String IOFVERSIONV3 = "iofVersion";
  private static String VERSION = "version";
  private static String COURSE = "Course";
  private static String RACECOURSEDATA = "RaceCourseData";
  private static String COURSENAME = "CourseName";
  private static String NAME = "Name";
  private static String COURSEVARIATION = "CourseVariation";
  private static String COURSECONTROL = "CourseControl";
  private static String CONTROLCODE = "ControlCode";
  private static String CONTROL = "Control";
  private static String ID = "Id";
  //private static String COURSEVARIATIONID = "CourseVariationId";


  /**
   * Méthode lisant le fichier XML passé en paramètre et construisant 
   * Le vecteur contenant tous les parcours
   * @param fichier
   */
  // Méthode de lecture du fichier XML
  public  static void importer(EasyGec easyGec, String fichier)
  {
    SAXBuilder sxb = new SAXBuilder(false);
    sxb.setValidation(false);
    sxb.setFeature("http://xml.org/sax/features/validation", false);
    sxb.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
    sxb.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
    try
    {
      document = sxb.build(new File(fichier));
    }
    catch (JDOMException e)
    {
      JOptionPane.showMessageDialog(null,"Erreur de lecture du fichier de configuration : "+e.getClass().getName()+", "+e.getMessage());
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(null,"Erreur de lecture du fichier de configuration : "+e.getClass().getName()+", "+e.getMessage());
    }
    racine = document.getRootElement();
    
    if(racine.getAttribute(IOFVERSIONV3)!= null)
    {
      if(racine.getAttributeValue(IOFVERSIONV3).substring(0, 1).compareTo("3")==0)
      {
        recupereTousPostesV3(easyGec);
        recupereCourseV3(easyGec);
      }
    }
    else if(racine.getChild(IOFVERSIONV2)!=null)
    {
      if(racine.getChild(IOFVERSIONV2).getAttributeValue(VERSION).substring(0, 1).compareTo("2")==0)
      {
        recupereTousPostesV2(easyGec);
        recupereCourseV2(easyGec);
      }
    }
    else
    {
      JOptionPane.showMessageDialog(null,"Cette version d'export XML d'OCAD n'est pas valide");
    }
  }
  
  // Méthode récupérant toutes les categories
  public static void recupereCourseV2(EasyGec easyGec)
  {
    List<?> courses = racine.getChildren(COURSE);
    if(courses.size()>0)
    {
      Iterator<?> i = courses.iterator();
      while (i.hasNext())
      {
        Element course = (Element) i.next();
        List<?> variations = course.getChildren(COURSEVARIATION);
        Iterator<?> k = variations.iterator();
        while (k.hasNext())
        {
          Element cv = (Element) k.next();
          
          Circuit circuit = new Circuit();
          if(variations.size()>1)
          {
            //circuit.setNom(course.getChildText(COURSENAME)+"-"+ (Integer.parseInt(cv.getChildText(COURSEVARIATIONID).trim())+1));
            circuit.setNom(course.getChildText(COURSENAME)+"- "+ (cv.getChildText(NAME).trim()));
          }
          else
          {
            circuit.setNom(course.getChildText(COURSENAME));
          }
          List<?> controls = cv.getChildren(COURSECONTROL);
          Iterator<?> j = controls.iterator();
          while (j.hasNext())
          {
            Element code = (Element) j.next();
            circuit.getCodes().getCodes().add(Integer.parseInt(code.getChildTextTrim(CONTROLCODE)));
          }
          easyGec.getCircuit().getCircuits().add(circuit);
        }
      }
    }
    else
    {
      JOptionPane.showMessageDialog(null,"Il n'existe pas de circuit dans ce fichier.");
    }
  }
  
  // Méthode récupérant toutes les categories
  public static void recupereTousPostesV2(EasyGec easyGec)
  {
    Circuit circuit = new Circuit();
    circuit.setNom("Tous postes");
    List<?> postes = racine.getChildren(CONTROL);
    if(postes.size()>0)
    {
      Iterator<?> i = postes.iterator();
      while (i.hasNext())
      {
        Element poste = (Element) i.next();
        circuit.getCodes().getCodes().add(Integer.parseInt(poste.getChildTextTrim(CONTROLCODE)));
      }
    }
    easyGec.getCircuit().getCircuits().add(circuit);
  }
  
  // Méthode récupérant toutes les categories
  public static void recupereCourseV3(EasyGec easyGec)
  {
    Element race = racine.getChild(RACECOURSEDATA, racine.getNamespace());
    List<?> courses = race.getChildren(COURSE, racine.getNamespace());
    if(courses.size()>0)
    {
      Iterator<?> i = courses.iterator();
      while (i.hasNext())
      {
        Element course = (Element) i.next();
        Circuit circuit = new Circuit();
        circuit.setNom(course.getChildText(NAME, racine.getNamespace()));
  
        List<?> controls = course.getChildren(COURSECONTROL, racine.getNamespace());
        Iterator<?> j = controls.iterator();
        while (j.hasNext())
        {
          Element code = (Element) j.next();
          if(code.getAttributeValue("type").compareTo(CONTROL)==0)
          {
            circuit.getCodes().getCodes().add(Integer.parseInt(code.getChildTextTrim(CONTROL, racine.getNamespace())));
          }
        }
        easyGec.getCircuit().getCircuits().add(circuit);
      }
    }
    else
    {
      JOptionPane.showMessageDialog(null,"Il n'existe pas de circuit dans ce fichier.");
    }
  }
  
  // Méthode récupérant toutes les categories
  public static void recupereTousPostesV3(EasyGec easyGec)
  {
    Circuit circuit = new Circuit();
    circuit.setNom("Tous postes");
    List<?> postes = racine.getChild(RACECOURSEDATA, racine.getNamespace()).getChildren(CONTROL, racine.getNamespace());
    if(postes.size()>0)
    {
      Iterator<?> i = postes.iterator();
      while (i.hasNext())
      {
        Element poste = (Element) i.next();
        if (isInt(poste.getChildTextTrim(ID, racine.getNamespace()))) 
        {
          circuit.getCodes().getCodes().add(Integer.parseInt(poste.getChildTextTrim(ID, racine.getNamespace())));
        }
      }
    }
    easyGec.getCircuit().getCircuits().add(circuit);
  }

  private static boolean isInt(String str)
  {
      return (str.lastIndexOf("-") == 0 && !str.equals("-0")) ? str.replace("-", "").matches(
              "[0-9]+") : str.matches("[0-9]+");
  }
}
