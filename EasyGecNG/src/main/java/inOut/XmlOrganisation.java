/**
 * 
 */
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

import outils.TimeManager;
import to.Circuit;
import to.Codes;
import to.Orienteur;
import to.Partiel;
import to.Puce;
import to.ResultatPuce;

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
public class XmlOrganisation
{
  // Déclaration des variables Document et de la racine
  private static Document document;
  private static Element racine;
  private static String EASYGEC = "easygec";
  private static String VERSION = "version";
  private static String CIRCUITS = "circuits";
  private static String CIRCUIT = "circuit";
  private static String HEURE = "heure";
  private static String LIGNE = "ligne";
  private static String BOITIER = "boitier";
  private static String RESULTATS = "resultats";
  private static String RESULTAT = "resultat";
  private static String PUCE = "puce";
  private static String START = "start";
  private static String FINISH = "finish";
  private static String PARTIELS = "partiels";
  private static String PARTIEL = "partiel";
  private static String BALISES = "balises";
  private static String BALISE = "balise";
  private static String CODE = "code";
  private static String TIME = "time";
  private static String NOM = "nom";
  private static String PRENOM = "prenom";
  private static String DATAS = "datas";
  private static String DATA = "data";
  private static String ORIENTEURS = "orienteurs";
  private static String ORIENTEUR = "orienteur";


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
    recupereAllCircuits(easyGec);
    if(racine.getChild(ORIENTEURS)!=null)
    {
      recupereAllOrienteurs(easyGec);
    }
    recupereAllResultats(easyGec);
  }
  
  // Méthode récupérant toutes les resultats
  private static void recupereAllResultats(EasyGec easyGec)
  {
    Element result = racine.getChild(RESULTATS);
    List<?> results = result.getChildren(RESULTAT);
    Iterator<?> i = results.iterator();
    
    while (i.hasNext())
    {
        Element v = (Element) i.next();
        
        ResultatPuce r = new ResultatPuce();
        r.setCircuit(easyGec.getCircuit().getCitcuit(v.getChildText(CIRCUIT)));
        r.setIdentifiant(v.getChildText(NOM));
        Element datas = v.getChild(DATAS);
        List<?> data = datas.getChildren(DATA);
        Iterator<?> w = data.iterator();
        while (w.hasNext())
        {
          Element d = (Element) w.next();
          r.getDatas().add(d.getText());
        }
        
        Element p = v.getChild(PUCE);
        Puce puce = r.getPuce();
        puce.setStarttime(TimeManager.newDate(p.getChildText(START)));
        puce.setFinishtime(TimeManager.newDate(p.getChildText(FINISH)));

        Element parts = p.getChild(PARTIELS);
        List<?> partiels = parts.getChildren(PARTIEL);
        Iterator<?> j = partiels.iterator();
        int k = 0;
        Partiel[] ps = new Partiel[partiels.size()];
        while (j.hasNext())
        {
          Element part = (Element) j.next();
          Partiel partiel = new Partiel();
          partiel.setCode(Integer.parseInt(part.getChildText(CODE)));
          partiel.setTime(TimeManager.newDate(part.getChildText(TIME)));
          ps[k] = partiel;
          k++;
        }
        puce.setPartiels(ps);
      
      // Ajout du parcours dans le vecteur
        easyGec.getResultats().getResultats().add(r);
    }
  }
  
  // Méthode récupérant toutes les orienteurs
  private static void recupereAllOrienteurs(EasyGec easyGec)
  {
    Element result = racine.getChild(ORIENTEURS);
    List<?> results = result.getChildren(ORIENTEUR);
    Iterator<?> i = results.iterator();
    
    while (i.hasNext())
    {
        Element v = (Element) i.next();
        
        Orienteur r = new Orienteur();
        r.setIdPuce(v.getChildText(PUCE));
        r.setNom(v.getChildText(NOM));
        r.setPrenom(v.getChildText(PRENOM));
        Element datas = v.getChild(DATAS);
        List<?> data = datas.getChildren(DATA);
        Iterator<?> j = data.iterator();
        while (j.hasNext())
        {
          Element d = (Element) j.next();
          r.getDatas().add(d.getText());
        }
      
      // Ajout de l'orienteur
        easyGec.getOrienteurs().getOrienteurs().add(r);
    }
  }
  
  // Méthode récupérant toutes les parcours
  private static void recupereAllCircuits(EasyGec easyGec)
  {
    Element pars = racine.getChild(CIRCUITS);
    List<?> parcours = pars.getChildren(CIRCUIT);
    Iterator<?> i = parcours.iterator();
    
    while (i.hasNext())
    {
        Element v = (Element) i.next();
        
        Circuit p = new Circuit();
        p.setNom(v.getChild(NOM).getText().trim());
        p.setCodes(recupereCodes(easyGec, v));
        int ligne = Integer.parseInt(v.getChildText(LIGNE));
        p.setEnLigne((ligne)==1?true:false);
        int boitier = Integer.parseInt(v.getChildText(BOITIER));
        p.setDepartBoitier((boitier)==1?true:false);
        if(!p.isDepartBoitier())
        {
          p.setHeureDepart(TimeManager.newDate(v.getChildText(HEURE)));
        }
      
      // Ajout du parcours dans le vecteur
        easyGec.getCircuit().getCircuits().add(p);
    }
  }
    
  private static Codes recupereCodes(EasyGec easyGec, Element p)
  {
    Codes retour = new Codes();
    Element ets = p.getChild(BALISES);
    List<?> etapes = ets.getChildren(BALISE);
    Iterator<?> i = etapes.iterator();
    
    while (i.hasNext())
    {
      Element et = (Element) i.next();
      
      int code = Integer.parseInt(et.getChildText(CODE));
      
      retour.getCodes().add(code);
    }
    return retour;
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
    for(int i =0;i<easyGec.getCircuit().getCircuits().size();i++)
    {
      Element e = new Element(CIRCUIT);
      
      Element nomL = new Element(NOM);
      nomL.setText(easyGec.getCircuit().getCircuits().get(i).getNom());
      e.addContent(nomL);
      Element li = new Element(LIGNE);
      li.setText(easyGec.getCircuit().getCircuits().get(i).getEnLigne());
      e.addContent(li);
      Element dp = new Element(BOITIER);
      dp.setText(easyGec.getCircuit().getCircuits().get(i).getDepartBoitier());
      e.addContent(dp);
      Element eheure = new Element(HEURE);
      if(!easyGec.getCircuit().getCircuits().get(i).isDepartBoitier())
      {
        eheure.setText(easyGec.getCircuit().getCircuits().get(i).getHeureDepart().getTime()+"");
      }
      e.addContent(eheure);
      
      Element pars = new Element(BALISES);
      for(int j = 0;j<easyGec.getCircuit().getCircuits().get(i).getCodes().getCodes().size();j++)
      {
        Element balise = new Element(BALISE);
        Element code = new Element(CODE);
        code.setText(easyGec.getCircuit().getCircuits().get(i).getCodes().getCodes().get(j) + "");
        balise.addContent(code);
        pars.addContent(balise);
      }
      e.addContent(pars);

      cats.addContent(e);
    }
    racine.addContent(cats);

    Element orients = new Element(ORIENTEURS);
    for(int i =0;i<easyGec.getOrienteurs().getOrienteurs().size();i++)
    {
      Element orient = new Element(ORIENTEUR);
      
      Element puce = new Element(PUCE);
      puce.setText(easyGec.getOrienteurs().getOrienteurs().get(i).getIdPuce());
      orient.addContent(puce);
      
      Element nom = new Element(NOM);
      nom.setText(easyGec.getOrienteurs().getOrienteurs().get(i).getNom());
      orient.addContent(nom);
      
      Element prenom = new Element(PRENOM);
      prenom.setText(easyGec.getOrienteurs().getOrienteurs().get(i).getPrenom());
      orient.addContent(prenom);
      
      Element datas = new Element(DATAS);
      for(int j=0; j<easyGec.getOrienteurs().getOrienteurs().get(i).getDatas().size(); j++)
      {
        Element data = new Element(DATA);
        data.setText(easyGec.getOrienteurs().getOrienteurs().get(i).getDatas().get(j));
        datas.addContent(data);
      }
      orient.addContent(datas);

      orients.addContent(orient);
    }
    racine.addContent(orients);
    
    //Lecture du vecteur de résultats et construction de la structure XML
    Element rs = new Element(RESULTATS);
    for(int i =0;i<easyGec.getResultats().getResultats().size();i++)
    {
      Element r = new Element(RESULTAT);
      
      Element pc = new Element(CIRCUIT);
      pc.setText(easyGec.getResultats().getResultats().get(i).getCircuit().getNom());
      r.addContent(pc);
      Element et = new Element(NOM);
      et.setText(easyGec.getResultats().getResultats().get(i).getIdentifiant());
      r.addContent(et);
      
      Element datas = new Element(DATAS);
      for(int j=0; j<easyGec.getResultats().getResultats().get(i).getDatas().size(); j++)
      {
        Element data = new Element(DATA);
        data.setText(easyGec.getResultats().getResultats().get(i).getDatas().get(j));
        datas.addContent(data);
      }
      r.addContent(datas);
      
      Element pu = new Element(PUCE);
      
      Element str = new Element(START);
      str.setText(easyGec.getResultats().getResultats().get(i).getPuce().getStarttime().getTime()+"");
      pu.addContent(str);
      Element fns = new Element(FINISH);
      fns.setText(easyGec.getResultats().getResultats().get(i).getPuce().getFinishtime().getTime()+"");
      pu.addContent(fns);
      
      Element parts = new Element(PARTIELS);
      
      for(int j=0; j<easyGec.getResultats().getResultats().get(i).getPuce().getPartiels().length; j++)
      {
        Element part = new Element(PARTIEL);
        
        Element cd = new Element(CODE);
        cd.setText(easyGec.getResultats().getResultats().get(i).getPuce().getPartiels()[j].getCode()+"");
        part.addContent(cd);
        Element tm = new Element(TIME);
        tm.setText(easyGec.getResultats().getResultats().get(i).getPuce().getPartiels()[j].getTime().getTime()+"");
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
