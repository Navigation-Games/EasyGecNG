/**
 * 
 */
package to;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import outils.TimeManager;

/**
 * <P>
 * Titre : Puce.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author thierry
 *
 */
public class Puce implements Cloneable
{
  private String idPuce = "";

  private Date starttime = TimeManager.NO_TIME;
  
  private Date finishtime = TimeManager.NO_TIME;
  
  private Partiel[] partiels = new Partiel[0];
  
  public Puce()
  {
    
  }
  
  public int getPositionPoste(int code)
  {
    int retour = -1;
    for(int i=0; i<partiels.length; i++)
    {
      if(partiels[i].getCode()==code)
      {
        return i;
      }
    }
    return retour;
  }
  
  public Vector<String> getTemps()
  {
    Vector<String> temps = new Vector<String>();
    for(int i=0; i<partiels.length; i++)
    {
      temps.add(TimeManager.fullTime(partiels[i].getTime()));
    }
    return temps;
  }
  
  public Vector<Integer> getCodes()
  {
    Vector<Integer> codes = new Vector<Integer>();
    for(int i=0; i<partiels.length; i++)
    {
      codes.add(partiels[i].getCode());
    }
    return codes;
  }
  
  /**
   * @return the idPuce
   */
  public String getIdPuce()
  {
    return idPuce;
  }

  /**
   * @param idPuce the idPuce to set
   */
  public void setIdPuce(String idPuce)
  {
    this.idPuce = idPuce;
  }

  /**
   * @return the starttime
   */
  public Date getStarttime()
  {
    return starttime;
  }

  /**
   * @param starttime the starttime to set
   */
  public void setStarttime(Date starttime)
  {
    this.starttime = starttime;
  }

  /**
   * @return the finishtime
   */
  public Date getFinishtime()
  {
    return finishtime;
  }

  /**
   * @param finishtime the finishtime to set
   */
  public void setFinishtime(Date finishtime)
  {
    this.finishtime = finishtime;
  }

  /**
   * @return the partiels
   */
  public Partiel[] getPartiels()
  {
    return partiels;
  }

  /**
   * @param partiels the partiels to set
   */
  public void setPartiels(Partiel[] partiels)
  {
    this.partiels = partiels;
  }
  
  public String toStringEnTete()
  {
    StringBuffer retour = new StringBuffer();
    
    retour.append("D�part : "+TimeManager.fullTime(starttime)+", ");
    retour.append("Arriv�e : "+TimeManager.fullTime(finishtime)+", ");
    
    return retour.toString();
  }
  
  public String toStringPartiels()
  {
    StringBuffer retour = new StringBuffer();

    retour.append("D�part : "+TimeManager.fullTime(starttime)+"\n");
    for(int i = 0; i<partiels.length; i++)
    {
      retour.append(partiels[i].toString());
    }
    retour.append("Arriv�e : "+TimeManager.fullTime(finishtime));

    return retour.toString();
  }
  
  public void removePartiel(Partiel p)
  {
    Partiel[] ps = new Partiel[partiels.length-1];
    int index = indexOfPartiel(p);
    System.arraycopy(partiels, 0, ps, 0, index);
    if(partiels.length != index)
    {
      System.arraycopy(partiels, index+1, ps, index, partiels.length-index-1);
    }
    setPartiels(ps);
  }
  
  private int indexOfPartiel(Partiel p)
  {
    for(int i=0; i<partiels.length; i++)
    {
      if(partiels[i].equals(p))
      {
        return i;
      }
    }
    return -1;
  }
  
  public void upBalise(Partiel p)
  {
    List<Partiel> l = Arrays.asList(partiels);
    Vector<Partiel> v = new Vector<Partiel>(l);
    int index = l.indexOf(p);
    if(index>0)
    {
      v.insertElementAt(p, index-1);
      v.removeElementAt(index+1);
    }
    v.toArray(partiels);
  }
  
  public void downBalise(Partiel p)
  {
    List<Partiel> l = Arrays.asList(partiels);
    Vector<Partiel> v = new Vector<Partiel>(l);
    int index = l.indexOf(p);
    if(index<partiels.length-1)
    {
      v.insertElementAt(p, index+2);
      v.removeElementAt(index);
    }
    v.toArray(partiels);
  }
  
  public Date getTimePartielFirstCode(int code)
  {
    Date retour = TimeManager.NO_TIME;
    for(int i=0; i<partiels.length; i++)
    {
      if(code == partiels[i].getCode())
      {
        return partiels[i].getTime();
      }
    }
    return retour;
  }
  
  // on r�cup�re le partiel du deuxi�me code trouv�
  public Date getTimePartielLastCode(int code)
  {
    Date retour = TimeManager.NO_TIME;
    for(int i=partiels.length-1; i>-1; i--)
    {
      if(code == partiels[i].getCode())
      {
		return partiels[i].getTime();
      }
    }
    return retour;
  }
}
