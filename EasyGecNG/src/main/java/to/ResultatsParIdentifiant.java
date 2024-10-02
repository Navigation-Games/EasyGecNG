package to;

import java.util.Collections;
import java.util.Vector;

public class ResultatsParIdentifiant
{
  private Vector<ResultatsIdentifiant> resultats = new Vector<>();

  public boolean existe(String identifiant)
  {
    for(int i=0; i<resultats.size(); i++)
    {
      if(resultats.get(i).getIdentifiant().compareTo(identifiant)==0)
      {
        return true;
      }
    }
    return false;
  }

  public ResultatsIdentifiant getResultatsIdentifiant(String identifiant)
  {
    for(int i=0; i<resultats.size(); i++)
    {
      if(resultats.get(i).getIdentifiant().compareTo(identifiant)==0)
      {
        return resultats.get(i);
      }
    }
    return null;
  }
  
  /**
   * @return the resultats
   */
  public Vector<ResultatsIdentifiant> getResultats()
  {
    return resultats;
  }

  /**
   * @param resultats the resultats to set
   */
  public void setResultats(Vector<ResultatsIdentifiant> resultats)
  {
    this.resultats = resultats;
  }

  public void trier()
  {
    for(int i=0; i<resultats.size(); i++)
    {
      Collections.sort(resultats.get(i).getResultats());
    }
  }
  
  
}
