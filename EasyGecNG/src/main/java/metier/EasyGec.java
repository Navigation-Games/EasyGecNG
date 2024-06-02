/**
 * 
 */
package metier;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import outils.AuScore;
import outils.EnLigne;

import ihm.IhmEasyGec;
import to.Circuit;
import to.Circuits;
import to.MappagesAbc;
import to.Orienteurs;
import to.Puce;
import to.Resultat;
import to.ResultatCircuit;
import to.ResultatDetaille;
import to.ResultatDetailleIdentifiant;
import to.ResultatIdentifiant;
import to.ResultatPuce;
import to.Resultats;
import to.ResultatsDetailleIdentifiant;
import to.ResultatsDetailleParIdentifiant;
import to.ResultatsIdentifiant;
import to.ResultatsParIdentifiant;
import to.ResultatsSi;
import to.langages;

/**
 * <P>
 * Titre : EasyGec.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author Thierry
 *
 */
public class EasyGec
{
  private String version = "1.4.2";
  private boolean abc = false;
  private boolean unss = false;
  private String fichier = "";
  private String repertoire = "";
  private int tempo = 10;
  private Circuits circuit = new Circuits();
  private Resultats resultats = new Resultats();
  private ResultatsSi resultatsSi = new ResultatsSi();
  private Orienteurs orienteurs = new Orienteurs();
  private Orienteurs orienteursSi = new Orienteurs();
  private Vector<MappagesAbc> mappages = new Vector<>();
  public static MappagesAbc mappagesCourant = new MappagesAbc();
  private Vector<ResultatCircuit> resultatscircuit;
  private Vector<ResultatDetaille> resultatsDetaille;
  private ResultatsParIdentifiant resultatsParIdentifiants;
  /**
   * @return the resultatsParIdentifiants
   */
  public ResultatsParIdentifiant getResultatsParIdentifiants()
  {
    return resultatsParIdentifiants;
  }

  private ResultatsDetailleParIdentifiant resultatsDetailleParIdentifiants;
  private SIReaderHandler siHandler;
  private IhmEasyGec ihm = null;
  private Vector<Integer> resultatsPuce = new Vector<Integer>();
  private static langages langages = new langages();
  private static Vector<String> langs = new Vector<String>();
  private static String langUsed = "English";
  
  public EasyGec(IhmEasyGec ihm)
  {
    this.setIhm(ihm);
    siHandler = new SIReaderHandler(ihm);
  }
  
  public static int getLang()
  {
    for(int i=0; i<langs.size(); i++)
    {
      if(langUsed.compareTo(langs.get(i))==0)
      {
        return i;
      }
    }
    return 0;
  }
  
  public static String getCircuitEnMappage(Puce puce)
  {
    StringBuffer retour = new StringBuffer();
    for(int i=0; i<puce.getPartiels().length; i++)
    {
      if(puce.getPartiels()[i].getCode()!=31)
      {
        retour.append(mappagesCourant.getMap(puce.getPartiels()[i].getCode()));
      }
    }
    
    return retour.toString();
  }
  
  public void affecterResultatsSiOrienteurs()
  {
    int count = 0;
    for(int i=0; i<resultatsSi.getResultatsSi().size(); i++)
    {
      for(int j=0; j<orienteursSi.getOrienteurs().size(); j++)
      {
        if(orienteursSi.getOrienteurs().get(j).isNotUsed() && resultatsSi.getResultatsSi().get(i).getPuce().getIdPuce().compareTo(orienteursSi.getOrienteurs().get(j).getIdPuce())==0)
        {
          ResultatPuce rp = new ResultatPuce();
          rp.setPuce(resultatsSi.getResultatsSi().get(i).getPuce());
          calculResultatsPuce(rp);
          rp.setCircuit(circuit.getCircuits().get(getMinPM()));
          rp.setIdentifiant(orienteursSi.getOrienteurs().get(j).getNom() + " " + orienteursSi.getOrienteurs().get(j).getPrenom());
          orienteursSi.getOrienteurs().get(j).setNotUsed(false);
          resultats.getResultats().add(rp);
          count++;
          break;
        }
      }
    }
    ihm.enregistreResultats();
    JOptionPane.showMessageDialog(ihm, count + " r�sultats ont �t� associ�s.", "Association termin�e", JOptionPane.INFORMATION_MESSAGE);
  }
  
  private void calculResultatsPuce(ResultatPuce rp)
  {
    resultatsPuce.clear();
    for(int i=0; i<circuit.getCircuits().size(); i++)
    {
      int resultat = 0;
      if(circuit.getCircuits().get(i).isEnLigne())
      {
        EnLigne el = new EnLigne(circuit.getCircuits().get(i).getCodesToArray(), rp.getCodes(), rp.getTemps());
        rp.okPm = el.getOkPm();
        resultat = rp.getNbPM();
      }
      else
      {
        AuScore as = new AuScore(circuit.getCircuits().get(i).getCodesToArray(), rp.getCodes(), rp.getTemps());
        rp.okPm =as.getOkPm();
        resultat = rp.getNbPM();
      }
      resultatsPuce.add(resultat);
    }
  }
  
  private int getMinPM()
  {
    int index = 0;
    int retour = resultatsPuce.get(0);
    for(int i=1; i<resultatsPuce.size(); i++)
    {
      if(retour>resultatsPuce.get(i))
      {
        retour = resultatsPuce.get(i);
        index = i;
      }
    }
    return index;
  }

  // Pour Martin --> regroupement des r�sultats de tous les circuits
 public void CalculResultatsUNSS()
  {
    setResultatscircuit(new Vector<ResultatCircuit>());
    ResultatCircuit rc = new ResultatCircuit();
    if(circuit.getCircuits().size()>0)
    {
      rc.setCircuit(circuit.getCircuits().get(0));
    }
    for(int i=0; i<circuit.getCircuits().size(); i++)
    {
      for(int j=0; j<resultats.getResultats().size(); j++)
      {
        if(true)
        {
          Resultat r = new Resultat();
          resultats.getResultats().get(j).calculTempsPostes();
          r.setIdentifiant(resultats.getResultats().get(j).getIdentifiant());
          r.setDatas(resultats.getResultats().get(j).getDatas());
          r.setNbPostes(resultats.getResultats().get(j).getNbPostes());
          r.setTemps(resultats.getResultats().get(j).getTempsDeCourse());
          rc.getResultats().add(r);
        }
      }
    }
    resultatscircuit.add(rc);
    faireclassement();
    trierResultatsParIdentifiant();
  }

  public void CalculResultats()
  {
    if(unss)
    {
      CalculResultatsUNSS();
      return;
    }
    setResultatscircuit(new Vector<ResultatCircuit>());
    for(int i=0; i<circuit.getCircuits().size(); i++)
    {
      ResultatCircuit rc = new ResultatCircuit();
      rc.setCircuit(circuit.getCircuits().get(i));
      for(int j=0; j<resultats.getResultats().size(); j++)
      {
        if(resultats.getResultats().get(j).getCircuit().equals(circuit.getCircuits().get(i)))
        {
          Resultat r = new Resultat();
          resultats.getResultats().get(j).calculTempsPostes();
          r.setIdentifiant(resultats.getResultats().get(j).getIdentifiant());
          r.setDatas(resultats.getResultats().get(j).getDatas());
          r.setNbPostes(resultats.getResultats().get(j).getNbPostes());
          r.setTemps(resultats.getResultats().get(j).getTempsDeCourse());
          rc.getResultats().add(r);
        }
      }
      resultatscircuit.add(rc);
    }
    faireclassement();
    trierResultatsParIdentifiant();
  }
  
  private void trierResultatsParIdentifiant()
  {
    ResultatsIdentifiant resultatsIdentifiant;
    resultatsParIdentifiants = new ResultatsParIdentifiant();
    for(int i=0; i<resultatscircuit.size(); i++)
    {
      for(int j=0; j<resultatscircuit.get(i).getResultats().size(); j++)
      {
        if(resultatsParIdentifiants.existe(resultatscircuit.get(i).getResultats().get(j).getIdentifiant()))
        {
          resultatsIdentifiant = resultatsParIdentifiants.getResultatsIdentifiant(resultatscircuit.get(i).getResultats().get(j).getIdentifiant());
        }
        else
        {
          resultatsIdentifiant = new ResultatsIdentifiant();
          resultatsParIdentifiants.getResultats().add(resultatsIdentifiant);
          resultatsIdentifiant.setIdentifiant(resultatscircuit.get(i).getResultats().get(j).getIdentifiant());
        }
        ResultatIdentifiant resultatIdentifiant = new ResultatIdentifiant();
        resultatIdentifiant.setCircuit(resultatscircuit.get(i).getCircuit());
        resultatIdentifiant.setResultat(resultatscircuit.get(i).getResultats().get(j));
        resultatsIdentifiant.getResultats().add(resultatIdentifiant);
      }
    }
  }
  
  private void trierResultatsDetailleParIdentifiant()
  {
    ResultatsDetailleIdentifiant resultatsIdentifiant;
    resultatsDetailleParIdentifiants = new ResultatsDetailleParIdentifiant();
    for(int i=0; i<resultats.getResultats().size(); i++)
    {
        if(resultatsDetailleParIdentifiants.existe(resultats.getResultats().get(i).getIdentifiant()))
        {
          resultatsIdentifiant = resultatsDetailleParIdentifiants.getResultatsIdentifiant(resultats.getResultats().get(i).getIdentifiant());
        }
        else
        {
          resultatsIdentifiant = new ResultatsDetailleIdentifiant();
          resultatsDetailleParIdentifiants.getResultats().add(resultatsIdentifiant);
          resultatsIdentifiant.setIdentifiant(resultats.getResultats().get(i).getIdentifiant());
        }
        ResultatDetailleIdentifiant resultatIdentifiant = new ResultatDetailleIdentifiant();
        resultatIdentifiant.setCircuit(resultats.getResultats().get(i).getCircuit());
        resultatIdentifiant.setResultat(resultats.getResultats().get(i));
        resultatsIdentifiant.getResultats().add(resultatIdentifiant);
    }
  }
  
  public String getResultatsCircuitToHtml()
  {
    StringBuffer retour = new StringBuffer();

    for(int i=0; i<resultatscircuit.size(); i++)
    {
      retour.append(resultatscircuit.get(i).toHtml());
    }
    return retour.toString();
  }
  
  public void CalculResultatsDetaille()
  {
    setResultatsDetaille(new Vector<ResultatDetaille>());
    for(int i=0; i<circuit.getCircuits().size(); i++)
    {
      ResultatDetaille rc = new ResultatDetaille();
      rc.setCircuit(circuit.getCircuits().get(i));
      for(int j=0; j<resultats.getResultats().size(); j++)
      {
        if(resultats.getResultats().get(j).getCircuit().equals(circuit.getCircuits().get(i)))
        {
          resultats.getResultats().get(j).calculTempsPostes();
          rc.getResultats().add(resultats.getResultats().get(j));
        }
      }
      resultatsDetaille.add(rc);
    }
    faireclassementDetaille();
    trierResultatsDetailleParIdentifiant();
  }
  
  public String getResultatsCircuitDetailleToHtml()
  {
    StringBuffer retour = new StringBuffer();

    for(int i=0; i<resultatsDetaille.size(); i++)
    {
      retour.append(resultatsDetaille.get(i).toHtml());
    }
    return retour.toString();
  }
  
  private void faireclassement()
  {
    for(int i=0; i<resultatscircuit.size(); i++)
    {
      resultatscircuit.get(i).FaireClassement();
    }
  }
  
  private void faireclassementDetaille()
  {
    for(int i=0; i<resultatsDetaille.size(); i++)
    {
      resultatsDetaille.get(i).FaireClassement();
    }
  }
  
  public void clear()
  {
    fichier = "";
    circuit.getCircuits().clear();
    resultats.getResultats().clear();
    orienteurs.getOrienteurs().clear();
  }
  
  /**
   * @return the fichier
   */
  public String getFichier()
  {
    return fichier;
  }
  /**
   * @param fichier the fichier to set
   */
  public void setFichier(String fichier)
  {
    this.fichier = fichier;
  }
  /**
   * @return the circuit
   */
  public Circuits getCircuit()
  {
    return circuit;
  }
  /**
   * @param circuit the circuit to set
   */
  public void setCircuit(Circuits circuit)
  {
    this.circuit = circuit;
  }
  /**
   * @return the resultats
   */
  public Resultats getResultats()
  {
    return resultats;
  }
  /**
   * @param resultats the resultats to set
   */
  public void setResultats(Resultats resultats)
  {
    this.resultats = resultats;
  }
  /**
   * @return the version
   */
  public String getVersion()
  {
    return version;
  }
  /**
   * @param version the version to set
   */
  public void setVersion(String version)
  {
    this.version = version;
  }
  /**
   * @return the siHandler
   */
  public SIReaderHandler getSiHandler()
  {
    return siHandler;
  }
  /**
   * @param siHandler the siHandler to set
   */
  public void setSiHandler(SIReaderHandler siHandler)
  {
    this.siHandler = siHandler;
  }
  /**
   * @return the ihm
   */
  public IhmEasyGec getIhm()
  {
    return ihm;
  }
  /**
   * @param ihm the ihm to set
   */
  public void setIhm(IhmEasyGec ihm)
  {
    this.ihm = ihm;
  }

  /**
   * @return the resultatscircuit
   */
  public Vector<ResultatCircuit> getResultatscircuit()
  {
    return resultatscircuit;
  }

  /**
   * @param resultatscircuit the resultatscircuit to set
   */
  public void setResultatscircuit(Vector<ResultatCircuit> resultatscircuit)
  {
    this.resultatscircuit = resultatscircuit;
  }

  /**
   * @return the resultatsDetaille
   */
  public Vector<ResultatDetaille> getResultatsDetaille()
  {
    return resultatsDetaille;
  }

  /**
   * @param resultatsDetaille the resultatsDetaille to set
   */
  public void setResultatsDetaille(Vector<ResultatDetaille> resultatsDetaille)
  {
    this.resultatsDetaille = resultatsDetaille;
  }

  /**
   * @return the resultatsSi
   */
  public ResultatsSi getResultatsSi()
  {
    return resultatsSi;
  }

  /**
   * @param resultatsSi the resultatsSi to set
   */
  public void setResultatsSi(ResultatsSi resultatsSi)
  {
    this.resultatsSi = resultatsSi;
  }

  /**
   * @return the orienteurs
   */
  public Orienteurs getOrienteurs()
  {
    return orienteurs;
  }

  /**
   * @return the orienteurs
   */
  public Orienteurs getOrienteursSi()
  {
    return orienteursSi;
  }

  /**
   * @param orienteurs the orienteurs to set
   */
  public void setOrienteurs(Orienteurs orienteurs)
  {
    this.orienteurs = orienteurs;
  }

  /**
   * @return the repertoire
   */
  public String getRepertoire()
  {
    return repertoire;
  }

  /**
   * @param repertoire the repertoire to set
   */
  public void setRepertoire(String repertoire)
  {
    this.repertoire = repertoire;
    //Config.enregistre(this, "config.xml");
  }

  /**
   * @return the langages
   */
  public static langages getLangages()
  {
    return langages;
  }

  /**
   * @param langages the langages to set
   */
  public void setLangages(langages langages)
  {
    EasyGec.langages = langages;
  }

  /**
   * @return the langs
   */
  public Vector<String> getLangs()
  {
    return langs;
  }

  /**
   * @param langs the langs to set
   */
  public void setLangs(Vector<String> langs)
  {
    EasyGec.langs = langs;
  }

  /**
   * @return the langUsed
   */
  public String getLangUsed()
  {
    return langUsed;
  }

  /**
   * @param langUsed the langUsed to set
   */
  public void setLangUsed(String langUsed)
  {
    EasyGec.langUsed = langUsed;
    //Config.enregistre(this, "config.xml");
  }

  /**
   * @return the tempo
   */
  public int getTempo()
  {
    return tempo;
  }

  /**
   * @param tempo the tempo to set
   */
  public void setTempo(int tempo)
  {
    this.tempo = tempo;
  }

  /**
   * @return the resultatsDetailleParIdentifiants
   */
  public ResultatsDetailleParIdentifiant getResultatsDetailleParIdentifiants()
  {
    return resultatsDetailleParIdentifiants;
  }

  /**
   * @param resultatsDetailleParIdentifiants the resultatsDetailleParIdentifiants to set
   */
  public void setResultatsDetailleParIdentifiants(
      ResultatsDetailleParIdentifiant resultatsDetailleParIdentifiants)
  {
    this.resultatsDetailleParIdentifiants = resultatsDetailleParIdentifiants;
  }

  public String getResultatsIdentifiantToHtml()
  {
    StringBuffer retour  = new StringBuffer("<table>");
    for(int i=0; i<resultatsParIdentifiants.getResultats().size(); i++)
    {
      retour.append(resultatsParIdentifiants.getResultats().get(i).toHtml());
    }
    retour.append("</table>");
    return retour.toString();
  }

  public String getResultatsDetailleIdentifiantToHtml()
  {
    StringBuffer retour  = new StringBuffer("<table>");
    for(int i=0; i<resultatsDetailleParIdentifiants.getResultats().size(); i++)
    {
      retour.append(resultatsDetailleParIdentifiants.getResultats().get(i).toHtml(abc));
    }
    retour.append("</table>");
    return retour.toString();
  }

  /**
   * @return the mappages
   */
  public Vector<MappagesAbc> getMappages()
  {
    return mappages;
  }

  /**
   * @param mappages the mappages to set
   */
  public void setMappages(Vector<MappagesAbc> mappages)
  {
    this.mappages = mappages;
  }

  /**
   * @return the abc
   */
  public boolean isAbc()
  {
    return abc;
  }

  /**
   * @param abc the abc to set
   */
  public void setAbc(boolean abc)
  {
    this.abc = abc;
  }

  public void addAbc(Circuit circuit)
  {
    setcodesAbc(circuit);
    getCircuit().getCircuits().add(circuit);
  }

  public void setNomCircuitAbc(Circuit circuit, String nom)
  {
    circuit.setNom(nom);
    setcodesAbc(circuit);
  }
  
  private void setcodesAbc(Circuit circuit)
  {
    circuit.getCodes().getCodes().removeAllElements();
    Pattern pattern = Pattern.compile("[a-zA-Z]");
    for(int i=0; i<circuit.getNom().length(); i++)
    {
      Matcher matcher = pattern.matcher(circuit.getNom().substring(i, i+1));
      boolean isAbc = false;
      while(matcher.find()) 
      {
        isAbc = true;
      }
      
      if(i>0 && circuit.getNom().toUpperCase().substring(i-1, i).compareTo(circuit.getNom().toUpperCase().substring(i, i+1))==0)
      {
        circuit.getCodes().getCodes().add(31);
      }
      if(isAbc && circuit.getNom().substring(i, i+1).toUpperCase().compareTo(circuit.getNom().substring(i, i+1))==0)
      {
        circuit.getCodes().getCodes().add(32);
        circuit.getCodes().getCodes().add(mappagesCourant.getCode(circuit.getNom().substring(i, i+1).toLowerCase()));
      }
      else
      {
        circuit.getCodes().getCodes().add(mappagesCourant.getCode(circuit.getNom().substring(i, i+1)));
      }
    }
  }

  /**
   * @return the mappagesCourant
   */
  public MappagesAbc getMappagesCourant()
  {
    return mappagesCourant;
  }

  /**
   * @param mappagesCourant the mappagesCourant to set
   */
  public void setMappagesCourant(MappagesAbc mappagesCourant)
  {
    this.mappagesCourant = mappagesCourant;
  }

  /**
   * @return the unss
   */
  public boolean isUnss()
  {
    return unss;
  }

  /**
   * @param unss the unss to set
   */
  public void setUnss(boolean unss)
  {
    this.unss = unss;
  }
}
