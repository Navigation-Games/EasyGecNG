package to;

public class ResultatIdentifiant implements Comparable<ResultatIdentifiant>
{
  private Circuit circuit;
  private Resultat resultat;

  public String toHtml()
  {
    StringBuffer retour = new StringBuffer();
    if(resultat.getNbPostes()<circuit.getCodes().getCodes().size())
    {
      retour.append("<td style='background-color:red'>" + circuit.getNom() + " : " + resultat.getNbPostes() + "/" + circuit.getCodes().getCodes().size() + "</td>");
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
  public Resultat getResultat()
  {
    return resultat;
  }
  /**
   * @param resultat the resultat to set
   */
  public void setResultat(Resultat resultat)
  {
    this.resultat = resultat;
  }

  @Override
  public int compareTo(ResultatIdentifiant o)
  {
    return circuit.getNom().compareTo(o.getCircuit().getNom());
  }
}
