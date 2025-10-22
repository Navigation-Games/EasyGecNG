/**
 * 
 */
package to;

import java.util.Collections;
import java.util.Vector;

import core.EasyGec;

import utils.TimeManager;

/**
 * <P>
 * Titre : CourseResult.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author Thierry
 *
 */
public class CourseResult
{
  private Course course;
  private Vector<Result> results = new Vector<Result>();
  
  public void FaireRanking()
  {
    Collections.sort(results);
    for(int i=0; i<results.size(); i++)
    {
      results.get(i).setRanking(i+1);
    }
    simplifierRanking();
    for(int i=0; i<results.size(); i++)
    {
      results.get(i).setRanking(i+1);
    }
  }
  
  private void simplifierRanking()
  {
    for(int i=0; i<results.size(); i++)
    {

      for(int j=i+1; j<results.size(); j++)
      {
        if(results.get(i).getIdentifier().compareTo(results.get(j).getIdentifier())==0)
        {
          results.remove(j);
          j--;
        }
      }
    }
  }
  
  public String toHtml()
  {
    StringBuffer resultValue = new StringBuffer("<b>" + course.getName() + " ("+ course.getCodes().getCodes().size() + ")" + "</b><br>");
    resultValue.append("<table><tr style='background-color: #ebffeb;'><td>" + EasyGec.getLanguages().getText("107", EasyGec.getLang()) + "</td><td>" + EasyGec.getLanguages().getText("105", EasyGec.getLang()) + "</td><td></td><td></td><td>" + EasyGec.getLanguages().getText("101", EasyGec.getLang()) + "</td><td>" + EasyGec.getLanguages().getText("106", EasyGec.getLang()) + "</td></tr>");
    for(int i=0; i<results.size(); i++)
    {
      resultValue.append("<tr style='");
      if(i%2==0)
      {
        resultValue.append("background-color: #FFFFE0;'");
      }
      else
      {
        resultValue.append("background-color: #E0FFFF;'");
      }
      resultValue.append("align=center>");
      resultValue.append("<td>  " + results.get(i).getRanking() + "  </td>");
      resultValue.append("<td>  " + results.get(i).getIdentifier() + "  </td>");
      resultValue.append("<td>  " + results.get(i).getData0() + "  </td>");
      resultValue.append("<td>  " + results.get(i).getData1() + "  </td>");
      resultValue.append("<td>  " + TimeManager.fullTime(results.get(i).getTime()) + "  </td>");
      resultValue.append("<td>  " + results.get(i).getControlCount() + "  </td>");
      resultValue.append(results.get(i).getDatasToHtml() + "</tr>");
    }
    resultValue.append("</table><br><br>");
    return resultValue.toString();
  }
  
  /**
   * @return the course
   */
  public Course getCourse()
  {
    return course;
  }
  /**
   * @param course the course to set
   */
  public void setCourse(Course course)
  {
    this.course = course;
  }
  /**
   * @return the results
   */
  public Vector<Result> getResults()
  {
    return results;
  }
  /**
   * @param results the results to set
   */
  public void setResults(Vector<Result> results)
  {
    this.results = results;
  }

  public String toCSV()
  {
    StringBuffer tampon = new StringBuffer ("") ;
    for(int i=0; i<results.size(); i++)
    {
      tampon.append(results.get(i).getRanking()+";");
      tampon.append(results.get(i).getIdentifier()+";");
      tampon.append(results.get(i).getData0() + ";");
      tampon.append(results.get(i).getData1() + ";");
      tampon.append(course.getName() + ";");
      tampon.append(TimeManager.fullTime(results.get(i).getTime())+";");
      tampon.append(results.get(i).getControlCount()+";");
      tampon.append("sur "+ course.getCodes().getCodes().size() + ";");
      tampon.append(results.get(i).getDatasToCSV()+";");
      tampon . append( "\n" ) ;
    }
    
    return tampon . toString ( ) ;
  }

}
