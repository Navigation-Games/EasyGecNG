/**
 * 
 */
package to;

import java.util.Date;
import java.util.List;

import core.EasyGec;

import utils.TimeManager;

/**
 * <P>
 * Titre : Course.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author Thierry
 *
 */
public class Course implements Comparable<Course>
{
  private String name = "";
  private boolean enLigne = true;
  private boolean startBoitier = true;
  private Date heureStart = new Date(32400000);//9:00
  private Codes codes = new Codes();
  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }
  /**
   * @param name the name to set
   */
  public void setName(String name)
  {
    this.name = name;
  }
  /**
   * @return the enLigne
   */
  public boolean isClassicCourseEvaluator()
  {
    return enLigne;
  }
  /**
   * @param enLigne the enLigne to set
   */
  public void setClassicCourseEvaluator(boolean enLigne)
  {
    this.enLigne = enLigne;
  }
  
  public String getClassicCourseEvaluator()
  {
    return (isClassicCourseEvaluator())? "1" : "0";
  }
  /**
   * @return the codes
   */
  public Codes getCodes()
  {
    return codes;
  }
  /**
   * @param codes the codes to set
   */
  public void setCodes(Codes codes)
  {
    this.codes = codes;
  }
  /**
   * @return the startBoitier
   */
  public boolean isStartBoitier()
  {
    return startBoitier;
  }
  /**
   * @param startBoitier the startBoitier to set
   */
  public void setStartBoitier(boolean startBoitier)
  {
    this.startBoitier = startBoitier;
  }
  
  public String getStartBoitier()
  {
    return (isStartBoitier())? "1" : "0";
  }
  /**
   * @return the heureStart
   */
  public Date getHeureStart()
  {
    return heureStart;
  }
  /**
   * @param heureStart the heureStart to set
   */
  public void setHeureStart(Date heureStart)
  {
    this.heureStart = heureStart;
  }
  
  public String toString()
  {
    StringBuffer resultValue = new StringBuffer(name);
    resultValue .append(" (" + codes.getCodes().size() + " " + EasyGec.getLanguages().getText("106", EasyGec.getLang()) + ", ");
    if(isClassicCourseEvaluator())
    {
      resultValue.append(EasyGec.getLanguages().getText("18", EasyGec.getLang()) + ", ");
    }
    else
    {
      resultValue.append(EasyGec.getLanguages().getText("17", EasyGec.getLang()) + ", ");
    }
    if(isStartBoitier())
    {
      resultValue.append(EasyGec.getLanguages().getText("15", EasyGec.getLang()) + ")");
    }
    else
    {
      resultValue.append(EasyGec.getLanguages().getText("16", EasyGec.getLang()) + " " + TimeManager.fullTime(heureStart) +")");
    }
    return resultValue.toString() ;
  }

  public int[] getCodesToArray()
  {
    int[] resultValue = new int[codes.getCodes().size()];
    for(int i=0; i<codes.getCodes().size(); i++)
    {
      resultValue[i] = codes.getCodes().get(i);
    }
    return resultValue;
  }
  
  public String toHtml()
  {
    StringBuffer resultValue = new StringBuffer();
    resultValue.append("<b>" + getName() + "</b> (" + getCodes().getCodes().size() + ")");
    resultValue.append("<table CELLPADDING=5><tr align=center style='font-weight:bold;'><td></td><td></td><td></td><td></td><td>" + EasyGec.getLanguages().getText("101", EasyGec.getLang()) + "</td><td>" + EasyGec.getLanguages().getText("108", EasyGec.getLang()) + "</td>");
    if(isClassicCourseEvaluator())
    {
      for(int i=0; i<codes.getCodes().size(); i++)
      {
         resultValue.append("<td align=center>" + (i+1) + " (" + codes.getCodes().get(i) + ")</td>");
      }
    }
    else
    {
      resultValue.append("<td align=center>" + EasyGec.getLanguages().getText("106", EasyGec.getLang()) + "</td>");
    }
    resultValue.append("<td align=center>" + EasyGec.getLanguages().getText("109", EasyGec.getLang()) + "</td></tr>");
    return resultValue.toString();
  }
  
  public void addCodes(List<Integer> codes)
  {
    for(int i=0; i<codes.size(); i++)
    {
      this.codes.getCodes().add(codes.get(i));
    }
  }
  @Override
  public int compareTo(Course o)
  {
    if(codes.getCodes().size()>o.getCodes().getCodes().size())
    {
      return -1;
    }
    return 1;
  }
}
