/**
 * 
 */
package to;

import java.util.Vector;

/**
 * <P>
 * Titre : Resultat.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author Thierry
 *
 */
public class Resultat implements Comparable<Resultat>
{
  private String Identifiant ="";
  private Vector<String> datas = new Vector<String>();
  private int classement = -1;
  private long temps = 0;
  private int nbPostes = 0;
  

  public int compareTo(Resultat o)
  {
    if(nbPostes < o.getNbPostes())
    {
      return 1;
    }
    if(nbPostes == o.getNbPostes())
    {
      if(temps > o.getTemps())
      {
        return 1;
      }
      if(temps == o.getTemps())
      {
        return 0;
      }
    }
    return -1;
  }
  
  /**
   * @return the identifiant
   */
  public String getIdentifiant()
  {
    return Identifiant;
  }
  /**
   * @param identifiant the identifiant to set
   */
  public void setIdentifiant(String identifiant)
  {
    Identifiant = identifiant;
  }
  /**
   * @return the classement
   */
  public int getClassement()
  {
    return classement;
  }
  /**
   * @param classement the classement to set
   */
  public void setClassement(int classement)
  {
    this.classement = classement;
  }
  /**
   * @return the temps
   */
  public long getTemps()
  {
    return temps;
  }
  /**
   * @param temps the temps to set
   */
  public void setTemps(long temps)
  {
    this.temps = temps;
  }
  /**
   * @return the nbPostes
   */
  public int getNbPostes()
  {
    return nbPostes;
  }
  /**
   * @param nbPostes the nbPostes to set
   */
  public void setNbPostes(int nbPostes)
  {
    this.nbPostes = nbPostes;
  }

  /**
   * @return the data
   */
  public Vector<String> getDatas()
  {
    return datas;
  }

  /**
   * @param data the data to set
   */
  public void setDatas(Vector<String> datas)
  {
    this.datas = datas;
  }

  public String getData0()
  {
    if(datas.size()>0)
    {
      return datas.get(0);
    }
    else
    {
      return "";
    }
  }

  public String getDatasToCSV()
  {
    StringBuffer retour = new StringBuffer();
    if(datas.size()>2)
    {
      for(int i=2; i<datas.size(); i++)
      {
        retour.append(datas.get(i) + ";");
      }
    }
    
    return retour.toString();
  }

  public String getData1()
  {
    if(datas.size()>1)
    {
      return datas.get(1);
    }
    else
    {
      return "";
    }
  }

  public String getDatasToHtml()
  {
    StringBuffer retour = new StringBuffer();
    if(datas.size()>2)
    {
      for(int i=2; i<datas.size(); i++)
      {
        retour.append("<td>  " + datas.get(i) + "  </td>");
      }
    }
    
    return retour.toString();
  }
  
  
}
