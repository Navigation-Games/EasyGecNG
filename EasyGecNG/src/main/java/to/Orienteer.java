package to;

import java.util.Vector;

import javax.swing.ListModel;

public class Orienteer
{
  private String idChip = "";
  private String name = "";
  private String firstName = "";
  private boolean notUsed = true;
  private Vector<String> dataEntries = new Vector<String>();
  private boolean debut = false;
  private boolean fin = false;
  
  public Orienteer()
  {
    
  }
  
  public String[][] getDatasTableau()
  {
    String[][] resultValue = new String[dataEntries.size()+1][1];
    for(int i=0; i<dataEntries.size(); i++)
    {
      resultValue[i][0] = dataEntries.get(i);
    }
    return resultValue;
  }
  
  public String toString()
  {
    StringBuffer resultValue = new StringBuffer(idChip + " - " + name + " " + firstName);
    if(dataEntries.size()>0)
    {
      resultValue.append(" - " + dataEntries.get(0));
    }
    return  resultValue.toString();
  }
  
  public String getData(int index)
  {
    if(dataEntries.size()>0)
    {
      return dataEntries.get(index);
    }
    else
    {
      return "";
    }
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
   * @return the name
   */
  public String getName()
  {
    return name;
  }
  /**
   * @param name the name to set
   */
  public void setName(String name)
  {
    this.name = name;
  }
  /**
   * @return the firstName
   */
  public String getFirstName()
  {
    return firstName;
  }
  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }
  /**
   * @return the dataEntries
   */
  public Vector<String> getDatas()
  {
    return dataEntries;
  }
  /**
   * @param dataEntries the dataEntries to set
   */
  public void setDatas(Vector<String> dataEntries)
  {
    this.dataEntries = dataEntries;
    if(this.dataEntries.lastElement().trim().compareTo("")==0)
    {
      this.dataEntries.removeElementAt(dataEntries.size()-1);
    }
  }
  /**
   * @return the notUsed
   */
  public boolean isNotUsed()
  {
    return notUsed;
  }
  /**
   * @param notUsed the notUsed to set
   */
  public void setNotUsed(boolean notUsed)
  {
    this.notUsed = notUsed;
  }

  /**
   * @return the debut
   */
  public boolean isDebut()
  {
    return debut;
  }

  /**
   * @param debut the debut to set
   */
  public void setDebut(boolean debut)
  {
    this.debut = debut;
  }

  /**
   * @return the fin
   */
  public boolean isFin()
  {
    return fin;
  }

  /**
   * @param fin the fin to set
   */
  public void setFin(boolean fin)
  {
    this.fin = fin;
  }

  public void setDatas(ListModel<String> model)
  {
    dataEntries = new Vector<String>();
    for(int i=0; i<model.getSize(); i++)
    {
      dataEntries.add(model.getElementAt(i));
    }
  }
  
  public void upData(String data, int index)
  {
    if(index>0)
    {
      dataEntries.insertElementAt(data, index-1);
      dataEntries.removeElementAt(index+1);
    }
  }
  
  public void downData(String data, int index)
  {
    if(index<dataEntries.size()-1)
    {
      dataEntries.insertElementAt(data, index+2);
      dataEntries.removeElementAt(index);
    }
  }
}
