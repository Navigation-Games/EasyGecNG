package to;

import java.util.Collections;
import java.util.Vector;

public class DetailedResultsByIdentifier
{
  private Vector<DetailedResultsWithIdentifier> results = new Vector<>();
  
  public void trier()
  {
    for(int i=0; i<results.size(); i++)
    {
      Collections.sort(results.get(i).getResults());
    }
  }

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

  public DetailedResultsWithIdentifier getIdentifiedResults(String identifier)
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
  public Vector<DetailedResultsWithIdentifier> getResults()
  {
    return results;
  }

  /**
   * @param results the results to set
   */
  public void setResults(Vector<DetailedResultsWithIdentifier> results)
  {
    this.results = results;
  }
  
  
}
