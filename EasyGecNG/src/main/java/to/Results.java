/**
 * 
 */
package to;

import java.util.Vector;

import utils.TimeManager;

/**
 * <P>
 * Titre : Results.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author Thierry
 *
 */
public class Results
{
  private Vector<ChipResult> results = new Vector<ChipResult>();

  /**
   * @return the results
   */
  public Vector<ChipResult> getResults()
  {
    return results;
  }

  /**
   * @param results the results to set
   */
  public void setResults(Vector<ChipResult> results)
  {
    this.results = results;
  }

  public String toCSV()
  {
    StringBuffer tampon = new StringBuffer ("") ;
    for(int i=0; i<results.size(); i++)
    {
      tampon.append(results.get(i).getIdentifier()+";");
      tampon.append( results.get(i).getCourse().getName() + ";" );
      tampon.append( results.get(i).getCourse().getCodes().getCodes().size()+ ";" + results.get(i).getRanking() + ";");
      tampon.append(TimeManager.fullTime(results.get(i).getChip().getStarttime())+";"+TimeManager.fullTime(results.get(i).getChip().getFinishtime())+ ";"+ TimeManager.fullTime(results.get(i).getCourseDuration()) + ";");
      tampon . append( results.get(i).toCSV()) ;
      tampon . append( "\n" ) ;
    }
    
    return tampon . toString ( ) ;
  }
}
