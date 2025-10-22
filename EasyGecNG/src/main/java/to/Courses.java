/**
 * 
 */
package to;

import java.util.Vector;

/**
 * <P>
 * Titre : Courses.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author Thierry
 *
 */
public class Courses
{
  private Vector<Course> courses = new Vector<Course>();

  /**
   * @return the courses
   */
  public Vector<Course> getCourses()
  {
    return courses;
  }

  /**
   * @param courses the courses to set
   */
  public void setCourses(Vector<Course> courses)
  {
    this.courses = courses;
  }
  
  public Course getCitcuit(String course)
  {
    Course resultValue = null;
    for(int i=0; i<courses.size(); i++)
    {
      if(courses.get(i).getName().compareTo(course)==0)
      {
        return courses.get(i);
      }
    }
    return resultValue;
  }
  
  public int getNumeroCourse(String course)
  {
    int resultValue = -1;
    for(int i=0; i<courses.size(); i++)
    {
      if(courses.get(i).getName().compareTo(course)==0)
      {
        return i+1;
      }
    }
    return resultValue;
  }
}
