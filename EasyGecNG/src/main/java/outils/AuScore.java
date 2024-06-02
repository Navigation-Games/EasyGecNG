/**
 * 
 */
package outils;

import java.util.Vector;

/**
 * <P>
 * Titre : EnLigne.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author Thierry
 *
 */
public class AuScore
{
  private int codesATrouver [];
  private Vector<Integer> codesPuce = new Vector<Integer>();
  private Vector<String> tempsPuce = new Vector<String>();
  private boolean matrice[];
  private boolean okpm[];
  private String temps[];
  private int index = 0;
  
  public AuScore(int codesATrouver [], Vector<Integer> codesPuce, Vector<String> tempsPuce)
  {
    this.codesATrouver = codesATrouver;
    this.codesPuce = codesPuce;
    this.tempsPuce = tempsPuce;
    initMatrice();
    okPm();
    //lireOkPm();
  }
  
  private void initMatrice()
  {
    matrice = new boolean[codesPuce.size()];
    for(int i=0; i < codesPuce.size(); i++) 
    {
      matrice[i]=false;
    }
  }
  
  private void okPm()
  {
    okpm = new boolean[codesATrouver.length];
    temps = new String[codesATrouver.length];
    for(int i=0; i<codesATrouver.length; i++)
    {
      okpm[i] = isCode(codesATrouver[i]);
      if(index>=0)
      {
        temps[i] = tempsPuce.get(index);
      }
      else
      {
        temps[i] = "PM";
      }
    }
  }
  
  private boolean isCode(int code)
  {
    index = -1;
    boolean retour = false;
    for(int i = 0; i<codesPuce.size(); i++)
    {
      if(code==codesPuce.get(i) && !matrice[i])
      {
        matrice[i] = true;
        index = i;
        return true;
      }
    }
    return retour;
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
    //new AuScore(codes, trace);    
  }

  /**
   * @return the temps
   */
  public String[] getTemps()
  {
    return temps;
  }
}
