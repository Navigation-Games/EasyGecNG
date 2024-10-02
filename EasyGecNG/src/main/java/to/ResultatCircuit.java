/**
 * 
 */
package to;

import java.util.Collections;
import java.util.Vector;

import metier.EasyGec;

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
public class ResultatCircuit
{
  private Circuit circuit;
  private Vector<Resultat> resultats = new Vector<Resultat>();
  
  public void FaireClassement()
  {
    Collections.sort(resultats);
    for(int i=0; i<resultats.size(); i++)
    {
      resultats.get(i).setClassement(i+1);
    }
    simplifierClassement();
    for(int i=0; i<resultats.size(); i++)
    {
      resultats.get(i).setClassement(i+1);
    }
  }
  
  private void simplifierClassement()
  {
    for(int i=0; i<resultats.size(); i++)
    {

      for(int j=i+1; j<resultats.size(); j++)
      {
        if(resultats.get(i).getIdentifiant().compareTo(resultats.get(j).getIdentifiant())==0)
        {
          resultats.remove(j);
          j--;
        }
      }
    }
  }
  
  public String toHtml()
  {
    StringBuffer retour = new StringBuffer("<b>" + circuit.getNom() + " ("+ circuit.getCodes().getCodes().size() + ")" + "</b><br>");
    retour.append("<table><tr style='background-color: #ebffeb;'><td>" + EasyGec.getLangages().getText("107", EasyGec.getLang()) + "</td><td>" + EasyGec.getLangages().getText("105", EasyGec.getLang()) + "</td><td></td><td></td><td>" + EasyGec.getLangages().getText("101", EasyGec.getLang()) + "</td><td>" + EasyGec.getLangages().getText("106", EasyGec.getLang()) + "</td></tr>");
    for(int i=0; i<resultats.size(); i++)
    {
      retour.append("<tr style='");
      if(i%2==0)
      {
        retour.append("background-color: #FFFFE0;'");
      }
      else
      {
        retour.append("background-color: #E0FFFF;'");
      }
      retour.append("align=center>");
      retour.append("<td>  " + resultats.get(i).getClassement() + "  </td>");
      retour.append("<td>  " + resultats.get(i).getIdentifiant() + "  </td>");
      retour.append("<td>  " + resultats.get(i).getData0() + "  </td>");
      retour.append("<td>  " + resultats.get(i).getData1() + "  </td>");
      retour.append("<td>  " + TimeManager.fullTime(resultats.get(i).getTemps()) + "  </td>");
      retour.append("<td>  " + resultats.get(i).getNbPostes() + "  </td>");
      retour.append(resultats.get(i).getDatasToHtml() + "</tr>");
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
  public Vector<Resultat> getResultats()
  {
    return resultats;
  }
  /**
   * @param resultats the resultats to set
   */
  public void setResultats(Vector<Resultat> resultats)
  {
    this.resultats = resultats;
  }

  public String toCSV()
  {
    StringBuffer tampon = new StringBuffer ("") ;
    for(int i=0; i<resultats.size(); i++)
    {
      tampon.append(resultats.get(i).getClassement()+";");
      tampon.append(resultats.get(i).getIdentifiant()+";");
      tampon.append(resultats.get(i).getData0() + ";");
      tampon.append(resultats.get(i).getData1() + ";");
      tampon.append(circuit.getNom() + ";");
      tampon.append(TimeManager.fullTime(resultats.get(i).getTemps())+";");
      tampon.append(resultats.get(i).getNbPostes()+";");
      tampon.append("sur "+ circuit.getCodes().getCodes().size() + ";");
      tampon.append(resultats.get(i).getDatasToCSV()+";");
      tampon . append( "\n" ) ;
    }
    
    return tampon . toString ( ) ;
  }

}
