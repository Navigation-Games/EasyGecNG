package to;

import java.util.Collections;
import java.util.Vector;

public class ResultsByIdentifier
{
  private Vector<IdentifiedResults> results = new Vector<>();

  public boolean existe(String identifier)
  {
    for(int i=0; i<results.size(); i++)
    {
      if(results.get(i).getIdentifier().compareTo(identifier)==0)
      {
        return true;
      }
    }
    return false;
  }

  public IdentifiedResults getIdentifiedResults(String identifier)
  {
    for(int i=0; i<results.size(); i++)
    {
      if(results.get(i).getIdentifier().compareTo(identifier)==0)
      {
        return results.get(i);
      }
    }
    return null;
  }
  
  /**
   * @return the results
   */
  public Vector<IdentifiedResults> getResults()
  {
    return results;
  }

  /**
   * @param results the results to set
   */
  public void setResults(Vector<IdentifiedResults> results)
  {
    this.results = results;
  }

  public void trier()
  {
    for(int i=0; i<results.size(); i++)
    {
      Collections.sort(results.get(i).getResults());
    }
  }
  
  
}
