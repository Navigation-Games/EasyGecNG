package to;

import java.util.Vector;

public class ResultatsSi
{
  private Vector<ResultatSi> resultatsSi = new Vector<ResultatSi>();

  public void addResultatSi(ResultatSi r)
  {
    resultatsSi.add(r);
    r.setFin(true);
    if(resultatsSi.size() == 1)
    {
      r.setDebut(true);
    }
    if(resultatsSi.size() > 1)
    {
      resultatsSi.get(resultatsSi.size()-2).setFin(false);
    }
  }
  
  public void setDebut(ResultatSi r)
  {
    if(resultatsSi.indexOf(r)<getIndexFin())
    {
      for(int i=0; i<resultatsSi.size(); i++)
      {
        resultatsSi.get(i).setDebut(false);
      }
      r.setDebut(true);
    }
  }
  
  public void setFin(ResultatSi r)
  {
    if(resultatsSi.indexOf(r)>getIndexDebut())
    {
      for(int i=0; i<resultatsSi.size(); i++)
      {
        resultatsSi.get(i).setFin(false);
      }
      r.setFin(true);
    }
  }
  
  public void simplifier()
  {
    int debut = getIndexDebut();
    int fin = getIndexFin();
    int size = resultatsSi.size()-1;
    for(int i=size; i>fin; i--)
    {
      resultatsSi.remove(resultatsSi.size()-1);
    }
    for(int i=0; i<debut; i++)
    {
      resultatsSi.remove(0);
    }
  }
  
  private int getIndexDebut()
  {
    for(int i=0; i < resultatsSi.size(); i++)
    {
      if(resultatsSi.get(i).isDebut())
      {
        return i;
      }
    }
    return 0;
  }
  
  private int getIndexFin()
  {
    for(int i=resultatsSi.size()-1; i >= 0 ; i--)
    {
      if(resultatsSi.get(i).isFin())
      {
        return i;
      }
    }
    return 0;
  }
  
  /**
   * @return the resultatsSi
   */
  public Vector<ResultatSi> getResultatsSi()
  {
    return resultatsSi;
  }

  /**
   * @param resultatsSi the resultatsSi to set
   */
  public void setResultatsSi(Vector<ResultatSi> resultatsSi)
  {
    this.resultatsSi = resultatsSi;
  }
}
