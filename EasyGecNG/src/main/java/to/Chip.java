/**
 * 
 */
package to;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import utils.TimeManager;

/**
 * <P>
 * Titre : Chip.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author thierry
 *
 */
public class Chip implements Cloneable
{
  private String idChip = "";

  private Date starttime = TimeManager.NO_TIME;
  
  private Date finishtime = TimeManager.NO_TIME;
  
  private Split[] splits = new Split[0];
  
  public Chip()
  {
    
  }
  
  public int getPositionPoste(int code)
  {
    int resultValue = -1;
    for(int i=0; i<splits.length; i++)
    {
      if(splits[i].getCode()==code)
      {
        return i;
      }
    }
    return resultValue;
  }
  
  public Vector<String> getTime()
  {
    Vector<String> time = new Vector<String>();
    for(int i=0; i<splits.length; i++)
    {
      time.add(TimeManager.fullTime(splits[i].getTime()));
    }
    return time;
  }
  
  public Vector<Integer> getCodes()
  {
    Vector<Integer> codes = new Vector<Integer>();
    for(int i=0; i<splits.length; i++)
    {
      codes.add(splits[i].getCode());
    }
    return codes;
  }
  
  /**
   * @return the idChip
   */
  public String getIdChip()
  {
    return idChip;
  }

  /**
   * @param idChip the idChip to set
   */
  public void setIdChip(String idChip)
  {
    this.idChip = idChip;
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
   * @return the splits
   */
  public Split[] getSplits()
  {
    return splits;
  }

  /**
   * @param splits the splits to set
   */
  public void setSplits(Split[] splits)
  {
    this.splits = splits;
  }
  
  public String toStringEnTete()
  {
    StringBuffer resultValue = new StringBuffer();
    
    resultValue.append("D�part : "+TimeManager.fullTime(starttime)+", ");
    resultValue.append("Arriv�e : "+TimeManager.fullTime(finishtime)+", ");
    
    return resultValue.toString();
  }
  
  public String toStringSplits()
  {
    StringBuffer resultValue = new StringBuffer();

    resultValue.append("D�part : "+TimeManager.fullTime(starttime)+"\n");
    for(int i = 0; i<splits.length; i++)
    {
      resultValue.append(splits[i].toString());
    }
    resultValue.append("Arriv�e : "+TimeManager.fullTime(finishtime));

    return resultValue.toString();
  }
  
  public void removeSplit(Split p)
  {
    Split[] ps = new Split[splits.length-1];
    int index = indexOfSplit(p);
    System.arraycopy(splits, 0, ps, 0, index);
    if(splits.length != index)
    {
      System.arraycopy(splits, index+1, ps, index, splits.length-index-1);
    }
    setSplits(ps);
  }
  
  private int indexOfSplit(Split p)
  {
    for(int i=0; i<splits.length; i++)
    {
      if(splits[i].equals(p))
      {
        return i;
      }
    }
    return -1;
  }
  
  public void upBalise(Split p)
  {
    List<Split> l = Arrays.asList(splits);
    Vector<Split> v = new Vector<Split>(l);
    int index = l.indexOf(p);
    if(index>0)
    {
      v.insertElementAt(p, index-1);
      v.removeElementAt(index+1);
    }
    v.toArray(splits);
  }
  
  public void downBalise(Split p)
  {
    List<Split> l = Arrays.asList(splits);
    Vector<Split> v = new Vector<Split>(l);
    int index = l.indexOf(p);
    if(index<splits.length-1)
    {
      v.insertElementAt(p, index+2);
      v.removeElementAt(index);
    }
    v.toArray(splits);
  }
  
  public Date getTimeSplitFirstCode(int code)
  {
    Date resultValue = TimeManager.NO_TIME;
    for(int i=0; i<splits.length; i++)
    {
      if(code == splits[i].getCode())
      {
        return splits[i].getTime();
      }
    }
    return resultValue;
  }
  
  // on r�cup�re le split du deuxi�me code trouv�
  public Date getTimeSplitLastCode(int code)
  {
    Date resultValue = TimeManager.NO_TIME;
    for(int i=splits.length-1; i>-1; i--)
    {
      if(code == splits[i].getCode())
      {
		return splits[i].getTime();
      }
    }
    return resultValue;
  }
}
