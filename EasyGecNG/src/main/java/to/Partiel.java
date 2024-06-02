/**
 * 
 */
package to;

import java.util.Date;

import outils.TimeManager;


/**
 * <P>
 * Titre : Partiel.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author thierry
 *
 */
public class Partiel implements Cloneable
{
  private int code = 31;
  private Date time = new Date(32400000);// 9:00:00
  
  public Partiel()
  {
    
  }
  
  /**
   * @return the code
   */
  public int getCode()
  {
    return code;
  }
  /**
   * @param code the code to set
   */
  public void setCode(int code)
  {
    this.code = code;
  }
  /**
   * @return the time
   */
  public Date getTime()
  {
    return time;
  }
  /**
   * @param time the time to set
   */
  public void setTime(Date time)
  {
    this.time = time;
  }
  
  public String toString()
  {
    StringBuffer retour = new StringBuffer();
    
    retour.append("Poste : "+ code + " - " + TimeManager.fullTime(time));
    
    return retour.toString();
  }
}
