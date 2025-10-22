package to;

import java.util.Vector;

public class DetailedResultsWithIdentifier implements Comparable<DetailedResultsWithIdentifier>
{
  private String identifier = "";
  private Vector<DetailedResultSummary> results = new Vector<>();

  public String toHtml(boolean abc)
  {
    StringBuffer resultValue = new StringBuffer("<tr style='background-color: #ebffeb;'><td>");
    resultValue.append(identifier + "</td>");
    for(int i=0; i<results.size(); i++)
    {
      resultValue.append(results.get(i).toHtml(abc));
    }
    resultValue.append("</tr>\n");
    return resultValue.toString();
  }
  
  /**
   * @return the results
   */
  public Vector<DetailedResultSummary> getResults()
  {
    return results;
  }

  /**
   * @param results the results to set
   */
  public void setResults(Vector<DetailedResultSummary> results)
  {
    this.results = results;
  }

  /**
   * @return the identifier
   */
  public String getIdentifier()
  {
    return identifier;
  }

  /**
   * @param identifier the identifier to set
   */
  public void setIdentifier(String identifier)
  {
    this.identifier = identifier;
  }

  @Override
  public int compareTo(DetailedResultsWithIdentifier o)
  {
    return identifier.compareTo(o.getIdentifier());
  }
  
}
