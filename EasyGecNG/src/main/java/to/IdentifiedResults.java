package to;

import java.util.Vector;

public class IdentifiedResults implements Comparable<IdentifiedResults>
{
  private String identifier = "";
  private Vector<IdentifiedResult> results = new Vector<>();

  public String toHtml()
  {
    StringBuffer resultValue = new StringBuffer("<tr style='background-color: #ebffeb;'><td>");
    resultValue.append(identifier + "</td>");
    for(int i=0; i<results.size(); i++)
    {
      resultValue.append(results.get(i).toHtml());
    }
    resultValue.append("</tr>\n");
    return resultValue.toString();
  }
  
  /**
   * @return the results
   */
  public Vector<IdentifiedResult> getResults()
  {
    return results;
  }

  /**
   * @param results the results to set
   */
  public void setResults(Vector<IdentifiedResult> results)
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
  public int compareTo(IdentifiedResults ri)
  {
    return identifier.compareTo(ri.getIdentifier());
  }
  
}
