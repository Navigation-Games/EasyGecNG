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
public class EnLigne
{
  private int codesATrouver [];
  private Vector<Integer> codesPuce = new Vector<Integer>();
  private Vector<String> tempsPuce = new Vector<String>();
  private int matrice[][];
  private int coutLevenshtein = 0;
  private boolean okpm[];
  private String temps[];
  
  public EnLigne(int codesATrouver [], Vector<Integer> codesPuce, Vector<String> tempsPuce)
  {
    this.codesATrouver = codesATrouver;
    this.codesPuce = codesPuce;
    this.tempsPuce = tempsPuce;
    suppCodesInutiles();
    initMatrice();
    levenshtein();
    okPm();
    //verifMatrice();
    //lireOkPm();
  }
  
  private void initMatrice()
  {
    matrice = new int[codesATrouver.length + 1][codesPuce.size() + 1];
    for(int i=0; i < codesATrouver.length + 1; i++) 
    {
      matrice[i][0]=i;
    }
    for(int j=0; j < codesPuce.size() + 1; j++) {
      matrice[0][j]=j;
    }
  }
  
  private void levenshtein ()
  {
    int cout = 0;
    for(int i=0; i <codesATrouver.length; i++) 
    {
      for(int j=0; j < codesPuce.size(); j++) 
      {
              cout = (codesATrouver[i]) == codesPuce.get(j)? 0 : 1;
              matrice[i+1][j+1] = minimum(1+matrice[i+1][j], 1+matrice[i][j+1], cout+matrice[i][j]);
      }
    }
    coutLevenshtein = matrice[codesATrouver.length][codesPuce.size()];
  }
  
  private int minimum(int a, int b, int c)
  {
    int retour = 0;
    retour = Math.min(a, Math.min(b, c));
    return retour;
  }
  
  private void okPm()
  {
    okpm = new boolean[codesATrouver.length];
    temps = new String[codesATrouver.length];
    int i = 0;
    int j = 0;

    while(i<codesATrouver.length && j<codesPuce.size())
    {
      if(j == codesPuce.size())
      {
        okpm[i]=false;
        temps[i] = "PM";
        i++;
      }
      else if(i == codesATrouver.length)
      {
        j++;
      }
      else if(matrice[i+1][j+1]==matrice[i][j])
      {
        okpm[i]=true;
        temps[i] = tempsPuce.get(j);
        i++;
        j++;
      }
      else if(!existeCode(codesATrouver[i], j+1))
      {
        okpm[i]=false;
        temps[i] = "PM";
        i++;
      }
      else if(existeCode(codesATrouver[i], j+1))
      {
        if(matrice[i][j+1]>coutLevenshtein)
        {
          okpm[i]=false;
          temps[i] = "PM";
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
    int i = codesPuce.size()-1;
    while(i>=0)
    {
      if(!isCode(codesPuce.get(i)))
      {
        codesPuce.remove(i);
        tempsPuce.remove(i);
      }
      i--;
    }
  }
  
  private boolean isCode(int code)
  {
    boolean retour = false;
    for(int i=0; i<codesATrouver.length; i++)
    {
      if(code==codesATrouver[i])
      {
        retour = true;
      }
    }
    return retour;
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
      for(int j=0; j <codesPuce.size() + 1; j++) 
      {
        if(j==codesPuce.size())
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
    Vector<String> temps = new Vector<String>();
    trace.add(31);
    temps.add("10:00:00");
    trace.add(33);
    temps.add("10:00:00");
    trace.add(34);
    temps.add("10:00:00");
    trace.add(32);
    temps.add("10:00:00");
    trace.add(35);
    temps.add("10:00:00");
    trace.add(36);
    temps.add("10:00:00");
    trace.add(32);
    temps.add("10:00:00");
    trace.add(37);
    temps.add("10:00:00");
    trace.add(38);
    temps.add("10:00:00");
    trace.add(39);
    temps.add("10:00:00");
    new EnLigne(codes, trace, temps);    
  }

  /**
   * @return the temps
   */
  public String[] getTemps()
  {
    return temps;
  }

  public Vector<Integer> getCardCodes() { return codesPuce;}
  public Vector<String> getCardTimes() { return tempsPuce;}
  
  private boolean existeCode(int code, int index)
  { 
    for(int j=index; j < codesPuce.size(); j++) 
  {
      if(codesPuce.get(j)==code)
    {
    return true;
    }
    }
  
  return false;
  }
}
