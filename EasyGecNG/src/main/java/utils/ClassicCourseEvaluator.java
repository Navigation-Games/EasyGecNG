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
public class ClassicCourseEvaluator
{
  private int codesATrouver [];
  private Vector<Integer> codesChip = new Vector<Integer>();
  private Vector<String> timeChip = new Vector<String>();
  private int matrice[][];
  private int coutLevenshtein = 0;
  private boolean okpm[];
  private String time[];
  
  public ClassicCourseEvaluator(int codesATrouver [], Vector<Integer> codesChip, Vector<String> timeChip)
  {
    this.codesATrouver = codesATrouver;
    this.codesChip = codesChip;
    this.timeChip = timeChip;
    suppCodesInutiles();
    initMatrice();
    levenshtein();
    okPm();
    //verifMatrice();
    //lireOkPm();
  }
  
  private void initMatrice()
  {
    matrice = new int[codesATrouver.length + 1][codesChip.size() + 1];
    for(int i=0; i < codesATrouver.length + 1; i++) 
    {
      matrice[i][0]=i;
    }
    for(int j=0; j < codesChip.size() + 1; j++) {
      matrice[0][j]=j;
    }
  }
  
  private void levenshtein ()
  {
    int cout = 0;
    for(int i=0; i <codesATrouver.length; i++) 
    {
      for(int j=0; j < codesChip.size(); j++) 
      {
              cout = (codesATrouver[i]) == codesChip.get(j)? 0 : 1;
              matrice[i+1][j+1] = minimum(1+matrice[i+1][j], 1+matrice[i][j+1], cout+matrice[i][j]);
      }
    }
    coutLevenshtein = matrice[codesATrouver.length][codesChip.size()];
  }
  
  private int minimum(int a, int b, int c)
  {
    int resultValue = 0;
    resultValue = Math.min(a, Math.min(b, c));
    return resultValue;
  }
  
  private void okPm()
  {
    okpm = new boolean[codesATrouver.length];
    time = new String[codesATrouver.length];
    int i = 0;
    int j = 0;

    while(i<codesATrouver.length && j<codesChip.size())
    {
      if(j == codesChip.size())
      {
        okpm[i]=false;
        time[i] = "PM";
        i++;
      }
      else if(i == codesATrouver.length)
      {
        j++;
      }
      else if(matrice[i+1][j+1]==matrice[i][j])
      {
        okpm[i]=true;
        time[i] = timeChip.get(j);
        i++;
        j++;
      }
      else if(!existeCode(codesATrouver[i], j+1))
      {
        okpm[i]=false;
        time[i] = "PM";
        i++;
      }
      else if(existeCode(codesATrouver[i], j+1))
      {
        if(matrice[i][j+1]>coutLevenshtein)
        {
          okpm[i]=false;
          time[i] = "PM";
          i++;
          j--;
        }
        else
        {
          j++;
        }
      }
    }
  }
  
  private void suppCodesInutiles()
  {
    int i = codesChip.size()-1;
    while(i>=0)
    {
      if(!isCode(codesChip.get(i)))
      {
        codesChip.remove(i);
        timeChip.remove(i);
      }
      i--;
    }
  }
  
  private boolean isCode(int code)
  {
    boolean resultValue = false;
    for(int i=0; i<codesATrouver.length; i++)
    {
      if(code==codesATrouver[i])
      {
        resultValue = true;
      }
    }
    return resultValue;
  }
  
  public boolean[] getOkPm()
  {
    return okpm;
  }
  
  @SuppressWarnings("unused")
  private void verifMatrice()
  {
    for(int i=0; i <codesATrouver.length + 1; i++) 
    {
      for(int j=0; j <codesChip.size() + 1; j++) 
      {
        if(j==codesChip.size())
        {
          System.out.println(matrice[i][j]);
        }
        else
        {
          System.out.print(matrice[i][j] + "  ");
        }
      }
    }
    System.out.println(coutLevenshtein + "");
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
    int codes[] = {31, 32, 33, 34, 32, 35, 36, 32, 37, 38, 39};
    Vector<Integer> trace = new Vector<Integer>();
    Vector<String> time = new Vector<String>();
    trace.add(31);
    time.add("10:00:00");
    trace.add(33);
    time.add("10:00:00");
    trace.add(34);
    time.add("10:00:00");
    trace.add(32);
    time.add("10:00:00");
    trace.add(35);
    time.add("10:00:00");
    trace.add(36);
    time.add("10:00:00");
    trace.add(32);
    time.add("10:00:00");
    trace.add(37);
    time.add("10:00:00");
    trace.add(38);
    time.add("10:00:00");
    trace.add(39);
    time.add("10:00:00");
    new ClassicCourseEvaluator(codes, trace, time);    
  }

  /**
   * @return the time
   */
  public String[] getTime()
  {
    return time;
  }

  public Vector<Integer> getCardCodes() { return codesChip;}
  public Vector<String> getCardTimes() { return timeChip;}
  
  private boolean existeCode(int code, int index)
  { 
    for(int j=index; j < codesChip.size(); j++) 
  {
      if(codesChip.get(j)==code)
    {
    return true;
    }
    }
  
  return false;
  }
}
