package to;

import java.util.Vector;

public class SiResults
{
  private Vector<SiResult> resultsSi = new Vector<SiResult>();

  public void addSiResult(SiResult r)
  {
    resultsSi.add(r);
    r.setFin(true);
    if(resultsSi.size() == 1)
    {
      r.setDebut(true);
    }
    if(resultsSi.size() > 1)
    {
      resultsSi.get(resultsSi.size()-2).setFin(false);
    }
  }
  
  public void setDebut(SiResult r)
  {
    if(resultsSi.indexOf(r)<getIndexFin())
    {
      for(int i=0; i<resultsSi.size(); i++)
      {
        resultsSi.get(i).setDebut(false);
      }
      r.setDebut(true);
    }
  }
  
  public void setFin(SiResult r)
  {
    if(resultsSi.indexOf(r)>getIndexDebut())
    {
      for(int i=0; i<resultsSi.size(); i++)
      {
        resultsSi.get(i).setFin(false);
      }
      r.setFin(true);
    }
  }
  
  public void simplifier()
  {
    int debut = getIndexDebut();
    int fin = getIndexFin();
    int size = resultsSi.size()-1;
    for(int i=size; i>fin; i--)
    {
      resultsSi.remove(resultsSi.size()-1);
    }
    for(int i=0; i<debut; i++)
    {
      resultsSi.remove(0);
    }
  }
  
  private int getIndexDebut()
  {
    for(int i=0; i < resultsSi.size(); i++)
    {
      if(resultsSi.get(i).isDebut())
      {
        return i;
      }
    }
    return 0;
  }
  
  private int getIndexFin()
  {
    for(int i=resultsSi.size()-1; i >= 0 ; i--)
    {
      if(resultsSi.get(i).isFin())
      {
        return i;
      }
    }
    return 0;
  }
  
  /**
   * @return the resultsSi
   */
  public Vector<SiResult> getSiResults()
  {
    return resultsSi;
  }

  /**
   * @param resultsSi the resultsSi to set
   */
  public void setSiResults(Vector<SiResult> resultsSi)
  {
    this.resultsSi = resultsSi;
  }
}
