package to;

import java.util.Vector;

public class ResultatsDetailleIdentifiant implements Comparable<ResultatsDetailleIdentifiant>
{
  private String identifiant = "";
  private Vector<ResultatDetailleIdentifiant> resultats = new Vector<>();

  public String toHtml(boolean abc)
  {
    StringBuffer retour = new StringBuffer("<tr style='background-color: #ebffeb;'><td>");
    retour.append(identifiant + "</td>");
    for(int i=0; i<resultats.size(); i++)
    {
      retour.append(resultats.get(i).toHtml(abc));
    }
    retour.append("</tr>\n");
    return retour.toString();
  }
  
  /**
   * @return the resultats
   */
  public Vector<ResultatDetailleIdentifiant> getResultats()
  {
    return resultats;
  }

  /**
   * @param resultats the resultats to set
   */
  public void setResultats(Vector<ResultatDetailleIdentifiant> resultats)
  {
    this.resultats = resultats;
  }

  /**
   * @return the identifiant
   */
  public String getIdentifiant()
  {
    return identifiant;
  }

  /**
   * @param identifiant the identifiant to set
   */
  public void setIdentifiant(String identifiant)
  {
    this.identifiant = identifiant;
  }

  @Override
  public int compareTo(ResultatsDetailleIdentifiant o)
  {
    return identifiant.compareTo(o.getIdentifiant());
  }
  
}
