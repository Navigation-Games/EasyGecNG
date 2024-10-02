package to;

import metier.EasyGec;

public class ResultatDetailleIdentifiant implements Comparable<ResultatDetailleIdentifiant>
{
  private Circuit circuit;
  private ResultatPuce resultat;

  public String toHtml(boolean abc)
  {
    StringBuffer retour = new StringBuffer();
    if(resultat.getNbPostes()<circuit.getCodes().getCodes().size())
    {
      retour.append("<td style='background-color:red'>");
      if(abc)
      {
        retour.append(EasyGec.getCircuitEnMappage(resultat.getPuce()));
      }
      else
      {
        retour.append(circuit.getNom());
      }
      retour.append(" : " + resultat.getNbPostes() + "/" + circuit.getCodes().getCodes().size() + "</td>");
    }
    else if(resultat.getClassement()==1)
    {
      retour.append("<td style='background-color:yellow'>" + circuit.getNom() + " : " + resultat.getNbPostes() + "/" + circuit.getCodes().getCodes().size() + "</td>");
    }
    else
    {
      retour.append("<td style='background-color:green'>" + circuit.getNom() + " : " + resultat.getNbPostes() + "/" + circuit.getCodes().getCodes().size() + "</td>");
    }
    
    return retour.toString();
  }
  
  /**
   * @return the circuit
   */
  public Circuit getCircuit()
  {
    return circuit;
  }
  /**
   * @param circuit the circuit to set
   */
  public void setCircuit(Circuit circuit)
  {
    this.circuit = circuit;
  }
  /**
   * @return the resultat
   */
  public ResultatPuce getResultat()
  {
    return resultat;
  }
  /**
   * @param resultat the resultat to set
   */
  public void setResultat(ResultatPuce resultat)
  {
    this.resultat = resultat;
  }

  @Override
  public int compareTo(ResultatDetailleIdentifiant o)
  {
    return circuit.getNom().compareTo(o.getCircuit().getNom());
  }
}
