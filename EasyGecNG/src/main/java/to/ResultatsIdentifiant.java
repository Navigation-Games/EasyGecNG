package to;

import java.util.Vector;

public class ResultatsIdentifiant implements Comparable<ResultatsIdentifiant>
{
  private String identifiant = "";
  private Vector<ResultatIdentifiant> resultats = new Vector<>();

  public String toHtml()
  {
    StringBuffer retour = new StringBuffer("<tr style='background-color: #ebffeb;'><td>");
    retour.append(identifiant + "</td>");
    for(int i=0; i<resultats.size(); i++)
    {
      retour.append(resultats.get(i).toHtml());
    }
    retour.append("</tr>\n");
    return retour.toString();
  }
  
  /**
   * @return the resultats
   */
  public Vector<ResultatIdentifiant> getResultats()
  {
    return resultats;
  }

  /**
   * @param resultats the resultats to set
   */
  public void setResultats(Vector<ResultatIdentifiant> resultats)
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
  public int compareTo(ResultatsIdentifiant ri)
  {
    return identifiant.compareTo(ri.getIdentifiant());
  }
  
}
