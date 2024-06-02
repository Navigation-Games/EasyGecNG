package to;

import java.util.Collections;
import java.util.Vector;

public class ResultatsDetailleParIdentifiant
{
  private Vector<ResultatsDetailleIdentifiant> resultats = new Vector<>();
  
  public void trier()
  {
    for(int i=0; i<resultats.size(); i++)
    {
      Collections.sort(resultats.get(i).getResultats());
    }
  }

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

  public ResultatsDetailleIdentifiant getResultatsIdentifiant(String identifiant)
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
  public Vector<ResultatsDetailleIdentifiant> getResultats()
  {
    return resultats;
  }

  /**
   * @param resultats the resultats to set
   */
  public void setResultats(Vector<ResultatsDetailleIdentifiant> resultats)
  {
    this.resultats = resultats;
  }
  
  
}
