/**
 * 
 */
package to;

import java.util.Vector;

/**
 * <P>
 * Titre : Circuits.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author Thierry
 *
 */
public class Circuits
{
  private Vector<Circuit> circuits = new Vector<Circuit>();

  /**
   * @return the circuits
   */
  public Vector<Circuit> getCircuits()
  {
    return circuits;
  }

  /**
   * @param circuits the circuits to set
   */
  public void setCircuits(Vector<Circuit> circuits)
  {
    this.circuits = circuits;
  }
  
  public Circuit getCitcuit(String circuit)
  {
    Circuit retour = null;
    for(int i=0; i<circuits.size(); i++)
    {
      if(circuits.get(i).getNom().compareTo(circuit)==0)
      {
        return circuits.get(i);
      }
    }
    return retour;
  }
  
  public int getNumeroCircuit(String circuit)
  {
    int retour = -1;
    for(int i=0; i<circuits.size(); i++)
    {
      if(circuits.get(i).getNom().compareTo(circuit)==0)
      {
        return i+1;
      }
    }
    return retour;
  }
}
