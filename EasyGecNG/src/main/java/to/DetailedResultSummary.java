package to;

import core.EasyGec;

public class DetailedResultSummary implements Comparable<DetailedResultSummary>
{
  private Course course;
  private ChipResult result;

  public String toHtml(boolean abc)
  {
    StringBuffer resultValue = new StringBuffer();
    if(result.getControlCount()<course.getCodes().getCodes().size())
    {
      resultValue.append("<td style='background-color:red'>");
      if(abc)
      {
        resultValue.append(EasyGec.getCourseEnMapping(result.getChip()));
      }
      else
      {
        resultValue.append(course.getName());
      }
      resultValue.append(" : " + result.getControlCount() + "/" + course.getCodes().getCodes().size() + "</td>");
    }
    else if(result.getRanking()==1)
    {
      resultValue.append("<td style='background-color:yellow'>" + course.getName() + " : " + result.getControlCount() + "/" + course.getCodes().getCodes().size() + "</td>");
    }
    else
    {
      resultValue.append("<td style='background-color:green'>" + course.getName() + " : " + result.getControlCount() + "/" + course.getCodes().getCodes().size() + "</td>");
    }
    
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
   * @return the result
   */
  public ChipResult getResult()
  {
    return result;
  }
  /**
   * @param result the result to set
   */
  public void setResult(ChipResult result)
  {
    this.result = result;
  }

  @Override
  public int compareTo(DetailedResultSummary o)
  {
    return course.getName().compareTo(o.getCourse().getName());
  }
}
