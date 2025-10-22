/**
 * 
 */
package utils;

import java.io.File;

/**
 * <P>
 * Titre : ExtensionFichier.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author thierry
 *
 */
public class Utility
{
  public static String verifExtension(String fichier, String extension)
  {
    StringBuffer resultValue = new StringBuffer();
    if(!fichier.contains(extension))
    {
      resultValue.append(fichier);
      resultValue.append(extension);
      /*
      int index = fichier.lastIndexOf(".");
      if(index == -1)
      {
        resultValue.append(fichier);
        resultValue.append(extension);
      }
      else
      {
        resultValue.append(fichier.substring(0, index));
        resultValue.append(extension);
      }
      */
    }
    else
    {
      resultValue.append(fichier);
    }
    return resultValue.toString();
  }

  public static String getName(String fichier)
  {
    String resultValue = "";
    int indexDebut = fichier.lastIndexOf(File.separatorChar);
    int indexFin = fichier.lastIndexOf(".");
    resultValue = fichier.substring(indexDebut + 1, indexFin);
    return resultValue;
  }
  
  public static String getRepertoire(String fichier)
  {
    String resultValue = "";
    int indexDebut = fichier.lastIndexOf(File.separatorChar);
    resultValue = fichier.substring(0, indexDebut);
    return resultValue;
  }
  
  public static int parseInt(String time)
  {
    if(time.substring(0, 1).compareTo("-") == 0)
    {
      time = time.substring(1);
      return (getHeure(time)*3600 + getMinute(time)*60 + getSecond(time)) * (-1);
    }
    else
    {
      return getHeure(time)*3600 + getMinute(time)*60 + getSecond(time);
    }
  }
  
  private static int getSecond(String time)
  {
    return Integer.parseInt(time.substring(time.lastIndexOf(":") + 1));
  }
  
  private static int getMinute(String time)
  {
    return Integer.parseInt(time.substring(time.indexOf(":") + 1, time.lastIndexOf(":")));
  }
  
  private static int getHeure(String time)
  {
    return Integer.parseInt(time.substring(0, time.indexOf(":")));
  }
  
  public static boolean estUnEntier(String chaine) 
  {
    try 
    {
      Integer.parseInt(chaine);
    } 
    catch (NumberFormatException e)
    {
      return false;
    }
 
    return true;
  }
}
