/**
 * 
 */
package to;

import java.util.Vector;

import outils.TimeManager;

/**
 * <P>
 * Titre : Resultats.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author Thierry
 *
 */
public class Resultats
{
  private Vector<ResultatPuce> resultats = new Vector<ResultatPuce>();

  /**
   * @return the resultats
   */
  public Vector<ResultatPuce> getResultats()
  {
    return resultats;
  }

  /**
   * @param resultats the resultats to set
   */
  public void setResultats(Vector<ResultatPuce> resultats)
  {
    this.resultats = resultats;
  }

  public String toCSV()
  {
    StringBuffer tampon = new StringBuffer ("") ;
    for(int i=0; i<resultats.size(); i++)
    {
      tampon.append(resultats.get(i).getIdentifiant()+";");
      tampon.append( resultats.get(i).getCircuit().getNom() + ";" );
      tampon.append( resultats.get(i).getCircuit().getCodes().getCodes().size()+ ";" + resultats.get(i).getClassement() + ";");
      tampon.append(TimeManager.fullTime(resultats.get(i).getPuce().getStarttime())+";"+TimeManager.fullTime(resultats.get(i).getPuce().getFinishtime())+ ";"+ TimeManager.fullTime(resultats.get(i).getTempsDeCourse()) + ";");
      tampon . append( resultats.get(i).toCSV()) ;
      tampon . append( "\n" ) ;
    }
    
    return tampon . toString ( ) ;
  }
}
