/**
 * 
 */
package outils;

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
public class Outils
{
  public static String verifExtension(String fichier, String extension)
  {
    StringBuffer retour = new StringBuffer();
    if(!fichier.contains(extension))
    {
      retour.append(fichier);
      retour.append(extension);
      /*
      int index = fichier.lastIndexOf(".");
      if(index == -1)
      {
        retour.append(fichier);
        retour.append(extension);
      }
      else
      {
        retour.append(fichier.substring(0, index));
        retour.append(extension);
      }
      */
    }
    else
    {
      retour.append(fichier);
    }
    return retour.toString();
  }

  public static String getNom(String fichier)
  {
    String retour = "";
    int indexDebut = fichier.lastIndexOf(File.separatorChar);
    int indexFin = fichier.lastIndexOf(".");
    retour = fichier.substring(indexDebut + 1, indexFin);
    return retour;
  }
  
  public static String getRepertoire(String fichier)
  {
    String retour = "";
    int indexDebut = fichier.lastIndexOf(File.separatorChar);
    retour = fichier.substring(0, indexDebut);
    return retour;
  }
  
  public static int parseInt(String temps)
  {
    if(temps.substring(0, 1).compareTo("-") == 0)
    {
      temps = temps.substring(1);
      return (getHeure(temps)*3600 + getMinute(temps)*60 + getSecond(temps)) * (-1);
    }
    else
    {
      return getHeure(temps)*3600 + getMinute(temps)*60 + getSecond(temps);
    }
  }
  
  private static int getSecond(String temps)
  {
    return Integer.parseInt(temps.substring(temps.lastIndexOf(":") + 1));
  }
  
  private static int getMinute(String temps)
  {
    return Integer.parseInt(temps.substring(temps.indexOf(":") + 1, temps.lastIndexOf(":")));
  }
  
  private static int getHeure(String temps)
  {
    return Integer.parseInt(temps.substring(0, temps.indexOf(":")));
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
