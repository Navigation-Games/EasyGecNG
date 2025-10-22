package to;

public class IdentifiedResult implements Comparable<IdentifiedResult>
{
  private Course course;
  private Result result;

  public String toHtml()
  {
    StringBuffer resultValue = new StringBuffer();
    if(result.getControlCount()<course.getCodes().getCodes().size())
    {
      resultValue.append("<td style='background-color:red'>" + course.getName() + " : " + result.getControlCount() + "/" + course.getCodes().getCodes().size() + "</td>");
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
  public Result getResult()
  {
    return result;
  }
  /**
   * @param result the result to set
   */
  public void setResult(Result result)
  {
    this.result = result;
  }

  @Override
  public int compareTo(IdentifiedResult o)
  {
    return course.getName().compareTo(o.getCourse().getName());
  }
}
