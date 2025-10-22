/**
 * 
 */
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

import utils.TimeManager;
import to.Course;
import to.Codes;
import to.Orienteer;
import to.Split;
import to.Chip;
import to.ChipResult;

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
public class XmlOrganization
{
  // Déclaration des variables Document et de la racine
  private static Document document;
  private static Element racine;
  private static String EASYGEC = "easygec";
  private static String VERSION = "version";
  private static String CIRCUITS = "courses";
  private static String CIRCUIT = "course";
  private static String HEURE = "heure";
  private static String LIGNE = "ligne";
  private static String BOITIER = "boitier";
  private static String RESULTATS = "results";
  private static String RESULTAT = "result";
  private static String PUCE = "chip";
  private static String START = "start";
  private static String FINISH = "finish";
  private static String PARTIELS = "splits";
  private static String PARTIEL = "split";
  private static String BALISES = "balises";
  private static String BALISE = "balise";
  private static String CODE = "code";
  private static String TIME = "time";
  private static String NOM = "name";
  private static String PRENOM = "firstName";
  private static String DATAS = "dataEntries";
  private static String DATA = "data";
  private static String ORIENTEURS = "orienteers";
  private static String ORIENTEUR = "orienteer";


  /**
   * Méthode lisant le fichier XML passé en paramètre et construisant 
   * Le vecteur contenant tous les parcours
   * @param fichier
   */
  // Méthode de lecture du fichier XML
  public  static void lecture(EasyGec easyGec, String fichier)
  {
    SAXBuilder sxb = new SAXBuilder();
    try
    {
      document = sxb.build(new File(fichier));
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
    recupereAllCourses(easyGec);
    if(racine.getChild(ORIENTEURS)!=null)
    {
      recupereAllOrienteers(easyGec);
    }
    recupereAllResults(easyGec);
  }
  
  // Méthode récupérant toutes les results
  private static void recupereAllResults(EasyGec easyGec)
  {
    Element result = racine.getChild(RESULTATS);
    List<?> results = result.getChildren(RESULTAT);
    Iterator<?> i = results.iterator();
    
    while (i.hasNext())
    {
        Element v = (Element) i.next();
        
        ChipResult r = new ChipResult();
        r.setCourse(easyGec.getCourse().getCitcuit(v.getChildText(CIRCUIT)));
        r.setIdentifier(v.getChildText(NOM));
        Element dataEntries = v.getChild(DATAS);
        List<?> data = dataEntries.getChildren(DATA);
        Iterator<?> w = data.iterator();
        while (w.hasNext())
        {
          Element d = (Element) w.next();
          r.getDatas().add(d.getText());
        }
        
        Element p = v.getChild(PUCE);
        Chip chip = r.getChip();
        chip.setStarttime(TimeManager.newDate(p.getChildText(START)));
        chip.setFinishtime(TimeManager.newDate(p.getChildText(FINISH)));

        Element parts = p.getChild(PARTIELS);
        List<?> splits = parts.getChildren(PARTIEL);
        Iterator<?> j = splits.iterator();
        int k = 0;
        Split[] ps = new Split[splits.size()];
        while (j.hasNext())
        {
          Element part = (Element) j.next();
          Split split = new Split();
          split.setCode(Integer.parseInt(part.getChildText(CODE)));
          split.setTime(TimeManager.newDate(part.getChildText(TIME)));
          ps[k] = split;
          k++;
        }
        chip.setSplits(ps);
      
      // Ajout du parcours dans le vecteur
        easyGec.getResults().getResults().add(r);
    }
  }
  
  // Méthode récupérant toutes les orienteers
  private static void recupereAllOrienteers(EasyGec easyGec)
  {
    Element result = racine.getChild(ORIENTEURS);
    List<?> results = result.getChildren(ORIENTEUR);
    Iterator<?> i = results.iterator();
    
    while (i.hasNext())
    {
        Element v = (Element) i.next();
        
        Orienteer r = new Orienteer();
        r.setIdChip(v.getChildText(PUCE));
        r.setName(v.getChildText(NOM));
        r.setFirstName(v.getChildText(PRENOM));
        Element dataEntries = v.getChild(DATAS);
        List<?> data = dataEntries.getChildren(DATA);
        Iterator<?> j = data.iterator();
        while (j.hasNext())
        {
          Element d = (Element) j.next();
          r.getDatas().add(d.getText());
        }
      
      // Ajout de l'orienteer
        easyGec.getOrienteers().getOrienteers().add(r);
    }
  }
  
  // Méthode récupérant toutes les parcours
  private static void recupereAllCourses(EasyGec easyGec)
  {
    Element pars = racine.getChild(CIRCUITS);
    List<?> parcours = pars.getChildren(CIRCUIT);
    Iterator<?> i = parcours.iterator();
    
    while (i.hasNext())
    {
        Element v = (Element) i.next();
        
        Course p = new Course();
        p.setName(v.getChild(NOM).getText().trim());
        p.setCodes(recupereCodes(easyGec, v));
        int ligne = Integer.parseInt(v.getChildText(LIGNE));
        p.setClassicCourseEvaluator((ligne)==1?true:false);
        int boitier = Integer.parseInt(v.getChildText(BOITIER));
        p.setStartBoitier((boitier)==1?true:false);
        if(!p.isStartBoitier())
        {
          p.setHeureStart(TimeManager.newDate(v.getChildText(HEURE)));
        }
      
      // Ajout du parcours dans le vecteur
        easyGec.getCourse().getCourses().add(p);
    }
  }
    
  private static Codes recupereCodes(EasyGec easyGec, Element p)
  {
    Codes resultValue = new Codes();
    Element ets = p.getChild(BALISES);
    List<?> etapes = ets.getChildren(BALISE);
    Iterator<?> i = etapes.iterator();
    
    while (i.hasNext())
    {
      Element et = (Element) i.next();
      
      int code = Integer.parseInt(et.getChildText(CODE));
      
      resultValue.getCodes().add(code);
    }
    return resultValue;
  }
  
  /**
   * Méthode créant le fichier XML passé en paramètre 
   * et enregistrant le Raid
   * @param fichier
   */
  
  
  //Méthode qui enregistre au format XML le Raid
  public static void enregistre(EasyGec easyGec, String fichier)
  {
    //Déclaration des variables Document et de la racine
    Element racine = new Element(EASYGEC);
    Document document = new Document(racine);
    Element version = new Element(VERSION);
    version.setText(easyGec.getVersion());
    racine.addContent(version);
    //Lecture du vecteur de Categorie et construction de la structure XML
    Element cats = new Element(CIRCUITS);
    for(int i =0;i<easyGec.getCourse().getCourses().size();i++)
    {
      Element e = new Element(CIRCUIT);
      
      Element nameL = new Element(NOM);
      nameL.setText(easyGec.getCourse().getCourses().get(i).getName());
      e.addContent(nameL);
      Element li = new Element(LIGNE);
      li.setText(easyGec.getCourse().getCourses().get(i).getClassicCourseEvaluator());
      e.addContent(li);
      Element dp = new Element(BOITIER);
      dp.setText(easyGec.getCourse().getCourses().get(i).getStartBoitier());
      e.addContent(dp);
      Element eheure = new Element(HEURE);
      if(!easyGec.getCourse().getCourses().get(i).isStartBoitier())
      {
        eheure.setText(easyGec.getCourse().getCourses().get(i).getHeureStart().getTime()+"");
      }
      e.addContent(eheure);
      
      Element pars = new Element(BALISES);
      for(int j = 0;j<easyGec.getCourse().getCourses().get(i).getCodes().getCodes().size();j++)
      {
        Element balise = new Element(BALISE);
        Element code = new Element(CODE);
        code.setText(easyGec.getCourse().getCourses().get(i).getCodes().getCodes().get(j) + "");
        balise.addContent(code);
        pars.addContent(balise);
      }
      e.addContent(pars);

      cats.addContent(e);
    }
    racine.addContent(cats);

    Element orients = new Element(ORIENTEURS);
    for(int i =0;i<easyGec.getOrienteers().getOrienteers().size();i++)
    {
      Element orient = new Element(ORIENTEUR);
      
      Element chip = new Element(PUCE);
      chip.setText(easyGec.getOrienteers().getOrienteers().get(i).getIdChip());
      orient.addContent(chip);
      
      Element name = new Element(NOM);
      name.setText(easyGec.getOrienteers().getOrienteers().get(i).getName());
      orient.addContent(name);
      
      Element firstName = new Element(PRENOM);
      firstName.setText(easyGec.getOrienteers().getOrienteers().get(i).getFirstName());
      orient.addContent(firstName);
      
      Element dataEntries = new Element(DATAS);
      for(int j=0; j<easyGec.getOrienteers().getOrienteers().get(i).getDatas().size(); j++)
      {
        Element data = new Element(DATA);
        data.setText(easyGec.getOrienteers().getOrienteers().get(i).getDatas().get(j));
        dataEntries.addContent(data);
      }
      orient.addContent(dataEntries);

      orients.addContent(orient);
    }
    racine.addContent(orients);
    
    //Lecture du vecteur de résultats et construction de la structure XML
    Element rs = new Element(RESULTATS);
    for(int i =0;i<easyGec.getResults().getResults().size();i++)
    {
      Element r = new Element(RESULTAT);
      
      Element pc = new Element(CIRCUIT);
      pc.setText(easyGec.getResults().getResults().get(i).getCourse().getName());
      r.addContent(pc);
      Element et = new Element(NOM);
      et.setText(easyGec.getResults().getResults().get(i).getIdentifier());
      r.addContent(et);
      
      Element dataEntries = new Element(DATAS);
      for(int j=0; j<easyGec.getResults().getResults().get(i).getDatas().size(); j++)
      {
        Element data = new Element(DATA);
        data.setText(easyGec.getResults().getResults().get(i).getDatas().get(j));
        dataEntries.addContent(data);
      }
      r.addContent(dataEntries);
      
      Element pu = new Element(PUCE);
      
      Element str = new Element(START);
      str.setText(easyGec.getResults().getResults().get(i).getChip().getStarttime().getTime()+"");
      pu.addContent(str);
      Element fns = new Element(FINISH);
      fns.setText(easyGec.getResults().getResults().get(i).getChip().getFinishtime().getTime()+"");
      pu.addContent(fns);
      
      Element parts = new Element(PARTIELS);
      
      for(int j=0; j<easyGec.getResults().getResults().get(i).getChip().getSplits().length; j++)
      {
        Element part = new Element(PARTIEL);
        
        Element cd = new Element(CODE);
        cd.setText(easyGec.getResults().getResults().get(i).getChip().getSplits()[j].getCode()+"");
        part.addContent(cd);
        Element tm = new Element(TIME);
        tm.setText(easyGec.getResults().getResults().get(i).getChip().getSplits()[j].getTime().getTime()+"");
        part.addContent(tm);
        
        parts.addContent(part);
      }
      
      pu.addContent(parts);
      
      r.addContent(pu);

      rs.addContent(r);
    }
    racine.addContent(rs);
      
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
