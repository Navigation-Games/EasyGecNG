/**
 * 
 */
package to;

import java.util.Collections;
import java.util.Vector;

import outils.TimeManager;


/**
 * <P>
 * Titre : ResultatCircuit.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author Thierry
 *
 */
public class ResultatDetaille
{
  private Circuit circuit;
  private Vector<ResultatPuce> resultats = new Vector<ResultatPuce>();
  
  public void FaireClassement()
  {
    Collections.sort(resultats);
    for(int i=0; i<resultats.size(); i++)
    {
      resultats.get(i).setClassement(i+1);
    }
  }
  
  public String toHtml()
  {
    StringBuffer retour = new StringBuffer(circuit.toHtml());
    for(int i=0; i<resultats.size(); i++)
    {
      retour.append("<tr><td>" + resultats.get(i).getClassement() + "</td>");
      retour.append("<td align=center>" + resultats.get(i).getIdentifiant() + "</td>");
      retour.append("<td align=center>" + resultats.get(i).getData0() + "</td>");
      retour.append("<td align=center>" + resultats.get(i).getData1() + "</td>");
      retour.append("<td align=center>" + TimeManager.fullTime(resultats.get(i).getTempsDeCourse()) + "</td>");
      retour.append("<td align=center>" + TimeManager.fullTime(resultats.get(i).depart) + "</td>");
      
      if(circuit.isEnLigne())
      {
        for(int j=0; j<resultats.get(i).temps.length; j++)
        {
           retour.append("<td align=center>" + resultats.get(i).temps[j] + "</td>");
        }
        retour.append("<td align=center>" + TimeManager.fullTime(resultats.get(i).arrivee) + "</td>");
        retour.append("</tr>");
        retour.append("<tr><td></td><td></td><td></td><td></td><td></td><td></td>");

        for(int j=0; j<resultats.get(i).temps.length+1; j++)
        {
           retour.append("<td align=center>" + resultats.get(i).getPartiel(j) + "</td>");
        }
        retour.append("</tr>");
      }
      else
      {
        retour.append("<td align=center>" + resultats.get(i).getNbPostes() + "</td>");
        retour.append("<td align=center>" + TimeManager.fullTime(resultats.get(i).arrivee) + "</td>");
        for(int j=0; j<resultats.get(i).getPuce().getPartiels().length; j++)
        {
          if(resultats.get(i).existeCode(resultats.get(i).getPuce().getPartiels()[j].getCode()))
          {
            retour.append("<td align=center>" + "(" + resultats.get(i).getPuce().getPartiels()[j].getCode() + ") " + TimeManager.fullTime(resultats.get(i).getPuce().getPartiels()[j].getTime()) + "</td>");
          }
          else
          {
            retour.append("<td align=center><font color=red>" + "(" + resultats.get(i).getPuce().getPartiels()[j].getCode() + ") " + TimeManager.fullTime(resultats.get(i).getPuce().getPartiels()[j].getTime()) + "</font></td>");
          }
        }
        retour.append("</tr>");
      }
    }
    retour.append("</table><br><br>");
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

  public String toCSV(int index)
  {
    StringBuffer tampon = new StringBuffer ("") ;
    for(int i=0; i<resultats.size(); i++)
    {
      tampon.append(";"+resultats.get(i).getPuce().getIdPuce()+";;");
      tampon.append(resultats.get(i).getIdentifiant()+";;90;H;;0;");
      tampon.append(TimeManager.fullTime(resultats.get(i).getPuce().getStarttime())+";"+TimeManager.fullTime(resultats.get(i).getPuce().getFinishtime())+";"+
          TimeManager.fullTime(resultats.get(i).getTempsDeCourse())+";0;9999;LI;Pass O;FR;" +index +";"+ circuit.getNom() + ";" + circuit.getNom() + ";;;;;;;;;;;;;;;;0;0;0;");
      tampon.append(index +";"+ circuit.getNom() + ";10.0;100;" + circuit.getCodes().getCodes().size()+ ";" + (i+1) + ";");
      tampon.append(TimeManager.fullTime(resultats.get(i).getPuce().getStarttime())+";"+TimeManager.fullTime(resultats.get(i).getPuce().getFinishtime()));
      tampon . append( resultats.get(i).toCSV()) ;
      tampon . append( "\n" ) ;
    }
    
    return tampon . toString ( ) ;
  }

}
