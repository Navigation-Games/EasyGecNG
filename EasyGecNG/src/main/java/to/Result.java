/**
 * 
 */
package to;

import java.util.Vector;

/**
 * <P>
 * Titre : Result.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author Thierry
 *
 */
public class Result implements Comparable<Result>
{
  private String Identifier ="";
  private Vector<String> dataEntries = new Vector<String>();
  private int ranking = -1;
  private long time = 0;
  private int controlCount = 0;
  

  public int compareTo(Result o)
  {
    if(controlCount < o.getControlCount())
    {
      return 1;
    }
    if(controlCount == o.getControlCount())
    {
      if(time > o.getTime())
      {
        return 1;
      }
      if(time == o.getTime())
      {
        return 0;
      }
    }
    return -1;
  }
  
  /**
   * @return the identifier
   */
  public String getIdentifier()
  {
    return Identifier;
  }
  /**
   * @param identifier the identifier to set
   */
  public void setIdentifier(String identifier)
  {
    Identifier = identifier;
  }
  /**
   * @return the ranking
   */
  public int getRanking()
  {
    return ranking;
  }
  /**
   * @param ranking the ranking to set
   */
  public void setRanking(int ranking)
  {
    this.ranking = ranking;
  }
  /**
   * @return the time
   */
  public long getTime()
  {
    return time;
  }
  /**
   * @param time the time to set
   */
  public void setTime(long time)
  {
    this.time = time;
  }
  /**
   * @return the controlCount
   */
  public int getControlCount()
  {
    return controlCount;
  }
  /**
   * @param controlCount the controlCount to set
   */
  public void setControlCount(int controlCount)
  {
    this.controlCount = controlCount;
  }

  /**
   * @return the data
   */
  public Vector<String> getDatas()
  {
    return dataEntries;
  }

  /**
   * @param data the data to set
   */
  public void setDatas(Vector<String> dataEntries)
  {
    this.dataEntries = dataEntries;
  }

  public String getData0()
  {
    if(dataEntries.size()>0)
    {
      return dataEntries.get(0);
    }
    else
    {
      return "";
    }
  }

  public String getDatasToCSV()
  {
    StringBuffer resultValue = new StringBuffer();
    if(dataEntries.size()>2)
    {
      for(int i=2; i<dataEntries.size(); i++)
      {
        resultValue.append(dataEntries.get(i) + ";");
      }
    }
    
    return resultValue.toString();
  }

  public String getData1()
  {
    if(dataEntries.size()>1)
    {
      return dataEntries.get(1);
    }
    else
    {
      return "";
    }
  }

  public String getDatasToHtml()
  {
    StringBuffer resultValue = new StringBuffer();
    if(dataEntries.size()>2)
    {
      for(int i=2; i<dataEntries.size(); i++)
      {
        resultValue.append("<td>  " + dataEntries.get(i) + "  </td>");
      }
    }
    
    return resultValue.toString();
  }
  
  
}
