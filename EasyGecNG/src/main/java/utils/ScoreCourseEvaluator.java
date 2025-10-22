/**
 * 
 */
package utils;

import java.util.Vector;

/**
 * <P>
 * Titre : ClassicCourseEvaluator.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author Thierry
 *
 */
public class ScoreCourseEvaluator
{
  private int codesATrouver [];
  private Vector<Integer> codesChip = new Vector<Integer>();
  private Vector<String> timeChip = new Vector<String>();
  private boolean matrice[];
  private boolean okpm[];
  private String time[];
  private int index = 0;
  
  public ScoreCourseEvaluator(int codesATrouver [], Vector<Integer> codesChip, Vector<String> timeChip)
  {
    this.codesATrouver = codesATrouver;
    this.codesChip = codesChip;
    this.timeChip = timeChip;
    initMatrice();
    okPm();
    //lireOkPm();
  }
  
  private void initMatrice()
  {
    matrice = new boolean[codesChip.size()];
    for(int i=0; i < codesChip.size(); i++) 
    {
      matrice[i]=false;
    }
  }
  
  private void okPm()
  {
    okpm = new boolean[codesATrouver.length];
    time = new String[codesATrouver.length];
    for(int i=0; i<codesATrouver.length; i++)
    {
      okpm[i] = isCode(codesATrouver[i]);
      if(index>=0)
      {
        time[i] = timeChip.get(index);
      }
      else
      {
        time[i] = "PM";
      }
    }
  }
  
  private boolean isCode(int code)
  {
    index = -1;
    boolean resultValue = false;
    for(int i = 0; i<codesChip.size(); i++)
    {
      if(code==codesChip.get(i) && !matrice[i])
      {
        matrice[i] = true;
        index = i;
        return true;
      }
    }
    return resultValue;
  }
  
  public boolean[] getOkPm()
  {
    return okpm;
  }
  
  @SuppressWarnings("unused")
  private void lireOkPm()
  {
    for(int i=0; i <codesATrouver.length; i++) 
    {
      System.out.println(codesATrouver[i] + " : " + okpm[i]);
    }
  }
  
  public static void main(String[] args)
  {
    @SuppressWarnings("unused")
    int codes[] = {31, 32, 33, 34, 35, 32, 36, 37, 40, 32, 38, 39, 31};
    Vector<Integer> trace = new Vector<Integer>();
    trace.add(31);
    trace.add(32);
    trace.add(33);
    trace.add(34);
    trace.add(35);
    trace.add(32);
    trace.add(45);
    trace.add(36);
    trace.add(37);
    trace.add(40);
    trace.add(32);
    trace.add(38);
    trace.add(39);
    //new ScoreCourseEvaluator(codes, trace);    
  }

  /**
   * @return the time
   */
  public String[] getTime()
  {
    return time;
  }
}
