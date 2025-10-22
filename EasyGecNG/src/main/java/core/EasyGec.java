/**
 * 
 */
package core;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import utils.ScoreCourseEvaluator;
import utils.ClassicCourseEvaluator;

import ui.UiEasyGec;
import to.Course;
import to.Courses;
import to.AbcMappings;
import to.Orienteers;
import to.Chip;
import to.Result;
import to.CourseResult;
import to.DetailedResult;
import to.DetailedResultSummary;
import to.IdentifiedResult;
import to.ChipResult;
import to.Results;
import to.DetailedResultsWithIdentifier;
import to.DetailedResultsByIdentifier;
import to.IdentifiedResults;
import to.ResultsByIdentifier;
import to.SiResults;
import to.Languages;

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
  private String version = "DEVELOPMENT VERSION";
  private boolean abc = false;
  private boolean unss = false;
  private String fichier = "";
  private String repertoire = "";
  private int tempo = 10;
  private Courses course = new Courses();
  private Results results = new Results();
  private SiResults resultsSi = new SiResults();
  private Orienteers orienteers = new Orienteers();
  private Orienteers orienteersSi = new Orienteers();
  private Vector<AbcMappings> mappings = new Vector<>();
  public static AbcMappings mappingsCourant = new AbcMappings();
  private Vector<CourseResult> resultscourse;
  private Vector<DetailedResult> resultsDetaille;
  private ResultsByIdentifier resultsParIdentifiers;
  /**
   * @return the resultsParIdentifiers
   */
  public ResultsByIdentifier getResultsByIdentifiers()
  {
    return resultsParIdentifiers;
  }

  private DetailedResultsByIdentifier resultsDetailleParIdentifiers;
  private SIReaderHandler siHandler;
  private UiEasyGec ihm = null;
  private Vector<Integer> resultsChip = new Vector<Integer>();
  private static Languages Languages = new Languages();
  private static Vector<String> langs = new Vector<String>();
  private static String langUsed = "English";
  
  public EasyGec(UiEasyGec ihm)
  {
    this.setIhm(ihm);
    siHandler = new SIReaderHandler(ihm);

    if (getClass().getPackage().getImplementationVersion() != null) {
      this.version = getClass().getPackage().getImplementationVersion();
    }
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
  
  public static String getCourseEnMapping(Chip chip)
  {
    StringBuffer resultValue = new StringBuffer();
    for(int i=0; i<chip.getSplits().length; i++)
    {
      if(chip.getSplits()[i].getCode()!=31)
      {
        resultValue.append(mappingsCourant.getMap(chip.getSplits()[i].getCode()));
      }
    }
    
    return resultValue.toString();
  }
  
  public void affecterSiResultsOrienteers()
  {
    int count = 0;
    for(int i=0; i<resultsSi.getSiResults().size(); i++)
    {
      for(int j=0; j<orienteersSi.getOrienteers().size(); j++)
      {
        if(orienteersSi.getOrienteers().get(j).isNotUsed() && resultsSi.getSiResults().get(i).getChip().getIdChip().compareTo(orienteersSi.getOrienteers().get(j).getIdChip())==0)
        {
          ChipResult rp = new ChipResult();
          rp.setChip(resultsSi.getSiResults().get(i).getChip());
          calculResultsChip(rp);
          rp.setCourse(course.getCourses().get(getMinPM()));
          rp.setIdentifier(orienteersSi.getOrienteers().get(j).getName() + " " + orienteersSi.getOrienteers().get(j).getFirstName());
          orienteersSi.getOrienteers().get(j).setNotUsed(false);
          results.getResults().add(rp);
          count++;
          break;
        }
      }
    }
    ihm.enregistreResults();
    JOptionPane.showMessageDialog(ihm, count + " r�sultats ont �t� associ�s.", "Association termin�e", JOptionPane.INFORMATION_MESSAGE);
  }
  
  private void calculResultsChip(ChipResult rp)
  {
    resultsChip.clear();
    for(int i=0; i<course.getCourses().size(); i++)
    {
      int result = 0;
      if(course.getCourses().get(i).isClassicCourseEvaluator())
      {
        ClassicCourseEvaluator el = new ClassicCourseEvaluator(course.getCourses().get(i).getCodesToArray(), rp.getCodes(), rp.getTime());
        rp.okPm = el.getOkPm();
        result = rp.getNbPM();
      }
      else
      {
        ScoreCourseEvaluator as = new ScoreCourseEvaluator(course.getCourses().get(i).getCodesToArray(), rp.getCodes(), rp.getTime());
        rp.okPm =as.getOkPm();
        result = rp.getNbPM();
      }
      resultsChip.add(result);
    }
  }
  
  private int getMinPM()
  {
    int index = 0;
    int resultValue = resultsChip.get(0);
    for(int i=1; i<resultsChip.size(); i++)
    {
      if(resultValue>resultsChip.get(i))
      {
        resultValue = resultsChip.get(i);
        index = i;
      }
    }
    return index;
  }

  // Pour Martin --> regroupement des r�sultats de tous les courses
 public void CalculResultsUNSS()
  {
    setResultscourse(new Vector<CourseResult>());
    CourseResult rc = new CourseResult();
    if(course.getCourses().size()>0)
    {
      rc.setCourse(course.getCourses().get(0));
    }
    for(int i=0; i<course.getCourses().size(); i++)
    {
      for(int j=0; j<results.getResults().size(); j++)
      {
        if(true)
        {
          Result r = new Result();
          results.getResults().get(j).calculTimePostes();
          r.setIdentifier(results.getResults().get(j).getIdentifier());
          r.setDatas(results.getResults().get(j).getDatas());
          r.setNbPostes(results.getResults().get(j).getNbPostes());
          r.setTime(results.getResults().get(j).getTimeDeCourse());
          rc.getResults().add(r);
        }
      }
    }
    resultscourse.add(rc);
    faireranking();
    trierResultsByIdentifier();
  }

  public void CalculResults()
  {
    if(unss)
    {
      CalculResultsUNSS();
      return;
    }
    setResultscourse(new Vector<CourseResult>());
    for(int i=0; i<course.getCourses().size(); i++)
    {
      CourseResult rc = new CourseResult();
      rc.setCourse(course.getCourses().get(i));
      for(int j=0; j<results.getResults().size(); j++)
      {
        if(results.getResults().get(j).getCourse().equals(course.getCourses().get(i)))
        {
          Result r = new Result();
          results.getResults().get(j).calculTimePostes();
          r.setIdentifier(results.getResults().get(j).getIdentifier());
          r.setDatas(results.getResults().get(j).getDatas());
          r.setNbPostes(results.getResults().get(j).getNbPostes());
          r.setTime(results.getResults().get(j).getTimeDeCourse());
          rc.getResults().add(r);
        }
      }
      resultscourse.add(rc);
    }
    faireranking();
    trierResultsByIdentifier();
  }
  
  private void trierResultsByIdentifier()
  {
    IdentifiedResults resultsIdentifier;
    resultsParIdentifiers = new ResultsByIdentifier();
    for(int i=0; i<resultscourse.size(); i++)
    {
      for(int j=0; j<resultscourse.get(i).getResults().size(); j++)
      {
        if(resultsParIdentifiers.existe(resultscourse.get(i).getResults().get(j).getIdentifier()))
        {
          resultsIdentifier = resultsParIdentifiers.getIdentifiedResults(resultscourse.get(i).getResults().get(j).getIdentifier());
        }
        else
        {
          resultsIdentifier = new IdentifiedResults();
          resultsParIdentifiers.getResults().add(resultsIdentifier);
          resultsIdentifier.setIdentifier(resultscourse.get(i).getResults().get(j).getIdentifier());
        }
        IdentifiedResult resultIdentifier = new IdentifiedResult();
        resultIdentifier.setCourse(resultscourse.get(i).getCourse());
        resultIdentifier.setResult(resultscourse.get(i).getResults().get(j));
        resultsIdentifier.getResults().add(resultIdentifier);
      }
    }
  }
  
  private void trierDetailedResultsByIdentifier()
  {
    DetailedResultsWithIdentifier resultsIdentifier;
    resultsDetailleParIdentifiers = new DetailedResultsByIdentifier();
    for(int i=0; i<results.getResults().size(); i++)
    {
        if(resultsDetailleParIdentifiers.existe(results.getResults().get(i).getIdentifier()))
        {
          resultsIdentifier = resultsDetailleParIdentifiers.getIdentifiedResults(results.getResults().get(i).getIdentifier());
        }
        else
        {
          resultsIdentifier = new DetailedResultsWithIdentifier();
          resultsDetailleParIdentifiers.getResults().add(resultsIdentifier);
          resultsIdentifier.setIdentifier(results.getResults().get(i).getIdentifier());
        }
        DetailedResultSummary resultIdentifier = new DetailedResultSummary();
        resultIdentifier.setCourse(results.getResults().get(i).getCourse());
        resultIdentifier.setResult(results.getResults().get(i));
        resultsIdentifier.getResults().add(resultIdentifier);
    }
  }
  
  public String getResultsCourseToHtml()
  {
    StringBuffer resultValue = new StringBuffer();

    for(int i=0; i<resultscourse.size(); i++)
    {
      resultValue.append(resultscourse.get(i).toHtml());
    }
    return resultValue.toString();
  }
  
  public void CalculResultsDetaille()
  {
    setResultsDetaille(new Vector<DetailedResult>());
    for(int i=0; i<course.getCourses().size(); i++)
    {
      DetailedResult rc = new DetailedResult();
      rc.setCourse(course.getCourses().get(i));
      for(int j=0; j<results.getResults().size(); j++)
      {
        if(results.getResults().get(j).getCourse().equals(course.getCourses().get(i)))
        {
          results.getResults().get(j).calculTimePostes();
          rc.getResults().add(results.getResults().get(j));
        }
      }
      resultsDetaille.add(rc);
    }
    fairerankingDetaille();
    trierDetailedResultsByIdentifier();
  }
  
  public String getResultsCourseDetailleToHtml()
  {
    StringBuffer resultValue = new StringBuffer();

    for(int i=0; i<resultsDetaille.size(); i++)
    {
      resultValue.append(resultsDetaille.get(i).toHtml());
    }
    return resultValue.toString();
  }
  
  private void faireranking()
  {
    for(int i=0; i<resultscourse.size(); i++)
    {
      resultscourse.get(i).FaireRanking();
    }
  }
  
  private void fairerankingDetaille()
  {
    for(int i=0; i<resultsDetaille.size(); i++)
    {
      resultsDetaille.get(i).FaireRanking();
    }
  }
  
  public void clear()
  {
    fichier = "";
    course.getCourses().clear();
    results.getResults().clear();
    orienteers.getOrienteers().clear();
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
   * @return the course
   */
  public Courses getCourse()
  {
    return course;
  }
  /**
   * @param course the course to set
   */
  public void setCourse(Courses course)
  {
    this.course = course;
  }
  /**
   * @return the results
   */
  public Results getResults()
  {
    return results;
  }
  /**
   * @param results the results to set
   */
  public void setResults(Results results)
  {
    this.results = results;
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
  public UiEasyGec getIhm()
  {
    return ihm;
  }
  /**
   * @param ihm the ihm to set
   */
  public void setIhm(UiEasyGec ihm)
  {
    this.ihm = ihm;
  }

  /**
   * @return the resultscourse
   */
  public Vector<CourseResult> getResultscourse()
  {
    return resultscourse;
  }

  /**
   * @param resultscourse the resultscourse to set
   */
  public void setResultscourse(Vector<CourseResult> resultscourse)
  {
    this.resultscourse = resultscourse;
  }

  /**
   * @return the resultsDetaille
   */
  public Vector<DetailedResult> getResultsDetaille()
  {
    return resultsDetaille;
  }

  /**
   * @param resultsDetaille the resultsDetaille to set
   */
  public void setResultsDetaille(Vector<DetailedResult> resultsDetaille)
  {
    this.resultsDetaille = resultsDetaille;
  }

  /**
   * @return the resultsSi
   */
  public SiResults getSiResults()
  {
    return resultsSi;
  }

  /**
   * @param resultsSi the resultsSi to set
   */
  public void setSiResults(SiResults resultsSi)
  {
    this.resultsSi = resultsSi;
  }

  /**
   * @return the orienteers
   */
  public Orienteers getOrienteers()
  {
    return orienteers;
  }

  /**
   * @return the orienteers
   */
  public Orienteers getOrienteersSi()
  {
    return orienteersSi;
  }

  /**
   * @param orienteers the orienteers to set
   */
  public void setOrienteers(Orienteers orienteers)
  {
    this.orienteers = orienteers;
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
   * @return the Languages
   */
  public static Languages getLanguages()
  {
    return Languages;
  }

  /**
   * @param Languages the Languages to set
   */
  public void setLanguages(Languages Languages)
  {
    EasyGec.Languages = Languages;
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
   * @return the resultsDetailleParIdentifiers
   */
  public DetailedResultsByIdentifier getDetailedResultsByIdentifiers()
  {
    return resultsDetailleParIdentifiers;
  }

  /**
   * @param resultsDetailleParIdentifiers the resultsDetailleParIdentifiers to set
   */
  public void setDetailedResultsByIdentifiers(
      DetailedResultsByIdentifier resultsDetailleParIdentifiers)
  {
    this.resultsDetailleParIdentifiers = resultsDetailleParIdentifiers;
  }

  public String getIdentifiedResultsToHtml()
  {
    StringBuffer resultValue  = new StringBuffer("<table>");
    for(int i=0; i<resultsParIdentifiers.getResults().size(); i++)
    {
      resultValue.append(resultsParIdentifiers.getResults().get(i).toHtml());
    }
    resultValue.append("</table>");
    return resultValue.toString();
  }

  public String getDetailedResultsWithIdentifierToHtml()
  {
    StringBuffer resultValue  = new StringBuffer("<table>");
    for(int i=0; i<resultsDetailleParIdentifiers.getResults().size(); i++)
    {
      resultValue.append(resultsDetailleParIdentifiers.getResults().get(i).toHtml(abc));
    }
    resultValue.append("</table>");
    return resultValue.toString();
  }

  /**
   * @return the mappings
   */
  public Vector<AbcMappings> getMappings()
  {
    return mappings;
  }

  /**
   * @param mappings the mappings to set
   */
  public void setMappings(Vector<AbcMappings> mappings)
  {
    this.mappings = mappings;
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

  public void addAbc(Course course)
  {
    setcodesAbc(course);
    getCourse().getCourses().add(course);
  }

  public void setNameCourseAbc(Course course, String name)
  {
    course.setName(name);
    setcodesAbc(course);
  }
  
  private void setcodesAbc(Course course)
  {
    course.getCodes().getCodes().removeAllElements();
    Pattern pattern = Pattern.compile("[a-zA-Z]");
    for(int i=0; i<course.getName().length(); i++)
    {
      Matcher matcher = pattern.matcher(course.getName().substring(i, i+1));
      boolean isAbc = false;
      while(matcher.find()) 
      {
        isAbc = true;
      }
      
      if(i>0 && course.getName().toUpperCase().substring(i-1, i).compareTo(course.getName().toUpperCase().substring(i, i+1))==0)
      {
        course.getCodes().getCodes().add(31);
      }
      if(isAbc && course.getName().substring(i, i+1).toUpperCase().compareTo(course.getName().substring(i, i+1))==0)
      {
        course.getCodes().getCodes().add(32);
        course.getCodes().getCodes().add(mappingsCourant.getCode(course.getName().substring(i, i+1).toLowerCase()));
      }
      else
      {
        course.getCodes().getCodes().add(mappingsCourant.getCode(course.getName().substring(i, i+1)));
      }
    }
  }

  /**
   * @return the mappingsCourant
   */
  public AbcMappings getMappingsCourant()
  {
    return mappingsCourant;
  }

  /**
   * @param mappingsCourant the mappingsCourant to set
   */
  public void setMappingsCourant(AbcMappings mappingsCourant)
  {
    this.mappingsCourant = mappingsCourant;
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
