/**
 * 
 */
package to;

import java.util.Collections;
import java.util.Vector;

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
public class DetailedResult
{
  private Course course;
  private Vector<ChipResult> results = new Vector<ChipResult>();
  
  public void FaireRanking()
  {
    Collections.sort(results);
    for(int i=0; i<results.size(); i++)
    {
      results.get(i).setRanking(i+1);
    }
  }
  
  public String toHtml()
  {
    StringBuffer resultValue = new StringBuffer(course.toHtml());
    for(int i=0; i<results.size(); i++)
    {
      resultValue.append("<tr><td>" + results.get(i).getRanking() + "</td>");
      resultValue.append("<td align=center>" + results.get(i).getIdentifier() + "</td>");
      resultValue.append("<td align=center>" + results.get(i).getData0() + "</td>");
      resultValue.append("<td align=center>" + results.get(i).getData1() + "</td>");
      resultValue.append("<td align=center>" + TimeManager.fullTime(results.get(i).getCourseDuration()) + "</td>");
      resultValue.append("<td align=center>" + TimeManager.fullTime(results.get(i).start) + "</td>");
      
      if(course.isClassicCourseEvaluator())
      {
        for(int j=0; j<results.get(i).time.length; j++)
        {
           resultValue.append("<td align=center>" + results.get(i).time[j] + "</td>");
        }
        resultValue.append("<td align=center>" + TimeManager.fullTime(results.get(i).finish) + "</td>");
        resultValue.append("</tr>");
        resultValue.append("<tr><td></td><td></td><td></td><td></td><td></td><td></td>");

        for(int j=0; j<results.get(i).time.length+1; j++)
        {
           resultValue.append("<td align=center>" + results.get(i).getSplit(j) + "</td>");
        }
        resultValue.append("</tr>");
      }
      else
      {
        resultValue.append("<td align=center>" + results.get(i).getControlCount() + "</td>");
        resultValue.append("<td align=center>" + TimeManager.fullTime(results.get(i).finish) + "</td>");
        for(int j=0; j<results.get(i).getChip().getSplits().length; j++)
        {
          if(results.get(i).existeCode(results.get(i).getChip().getSplits()[j].getCode()))
          {
            resultValue.append("<td align=center>" + "(" + results.get(i).getChip().getSplits()[j].getCode() + ") " + TimeManager.fullTime(results.get(i).getChip().getSplits()[j].getTime()) + "</td>");
          }
          else
          {
            resultValue.append("<td align=center><font color=red>" + "(" + results.get(i).getChip().getSplits()[j].getCode() + ") " + TimeManager.fullTime(results.get(i).getChip().getSplits()[j].getTime()) + "</font></td>");
          }
        }
        resultValue.append("</tr>");
      }
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
  public Vector<ChipResult> getResults()
  {
    return results;
  }
  /**
   * @param results the results to set
   */
  public void setResults(Vector<ChipResult> results)
  {
    this.results = results;
  }

  public String toCSV(int index)
  {
    StringBuffer tampon = new StringBuffer ("") ;
    for(int i=0; i<results.size(); i++)
    {
      tampon.append(";"+results.get(i).getChip().getIdChip()+";;");
      tampon.append(results.get(i).getIdentifier()+";;90;H;;0;");
      tampon.append(TimeManager.fullTime(results.get(i).getChip().getStarttime())+";"+TimeManager.fullTime(results.get(i).getChip().getFinishtime())+";"+
          TimeManager.fullTime(results.get(i).getCourseDuration())+";0;9999;LI;Pass O;FR;" +index +";"+ course.getName() + ";" + course.getName() + ";;;;;;;;;;;;;;;;0;0;0;");
      tampon.append(index +";"+ course.getName() + ";10.0;100;" + course.getCodes().getCodes().size()+ ";" + (i+1) + ";");
      tampon.append(TimeManager.fullTime(results.get(i).getChip().getStarttime())+";"+TimeManager.fullTime(results.get(i).getChip().getFinishtime()));
      tampon . append( results.get(i).toCSV()) ;
      tampon . append( "\n" ) ;
    }
    
    return tampon . toString ( ) ;
  }

}
