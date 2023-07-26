/**
 * 
 */
package to;

import inOut.HtmlResultatPuce;

import java.util.Vector;

import metier.EasyGec;

import outils.AuScore;
import outils.EnLigne;
import outils.TimeManager;



/**
 * <P>
 * Titre : ResultatPuce.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author thierry
 *
 */
public class ResultatPuce implements Cloneable, Comparable<ResultatPuce>
{
  private Circuit circuit = null;
  private String identifiant = null;
  private Vector<String> datas = new Vector<String>();
  private int classement = -1;
  public int [] codesATrouver;
  public boolean[] okPm;
  public String[] temps;
  public int [] codesATrouverClasser;
  public boolean[] okPmClasser;
  public String[] tempsClasser;
  private long tempsDeCourse = 0;
  private int nbPostes = 0;
  public long depart = -1;
  public long arrivee = -1;
  
  private Puce puce = new Puce();
  
  public ResultatPuce()
  {
    
  }
  
  public String getTexteFormate()
  {
    StringBuffer retour = new StringBuffer("<html><body>");
    /*int index = 0;
    for(int i=0; i<codesATrouver.length; i++)
    {
      if(codesATrouver[i]!=31)
      {
        if(okPm[i])
        {
          retour.append("<font color='green'>" + circuit.getNom().substring(index, index+1) + "</font>");
        }
        else
        {
          //retour.append("<font color='red'>" + circuit.getNom().substring(index, index+1) + "</font>");
          if(i<puce.getPartiels().length)
          {
            retour.append("<font color='red'>" + EasyGec.mappagesCourant.getMap(puce.getPartiels()[i].getCode()) + "</font>");
          }
          else
          {
            retour.append("<font color='red'>" + "?" + "</font>");
          }
        }
        index++;
      }
    }*/
    retour.append("<font color='red'>" + EasyGec.getCircuitEnMappage(puce) + "</font>");
    retour.append("<font color='black'>  ---  " + circuit.getNom() + "</font>");
    retour.append("</body></html>");
    return retour.toString();
  }
 
  public void triResultatsScore() 
  {
    int longueur = temps.length;
    String tamponString = "";
    int tamponInt = 0;
    boolean tamponBool = true;
    boolean permut;
 
    do 
    {
      // hypoth�se : le tableau est tri�
      permut = false;
      for (int i = 0; i < longueur - 1; i++) 
      {
        // Teste si 2 �l�ments successifs sont dans le bon ordre ou non
        if (temps[i].compareTo( temps[i + 1])>0) 
        {
          // s'ils ne le sont pas, on �change leurs positions
          tamponString = temps[i];
          temps[i] = temps[i + 1];
          temps[i + 1] = tamponString;
          tamponInt = codesATrouver[i];
          codesATrouver[i] = codesATrouver[i + 1];
          codesATrouver[i + 1] = tamponInt;
          tamponBool = okPm[i];
          okPm[i] = okPm[i + 1];
          okPm[i + 1] = tamponBool;
          permut = true;
        }
      }
    } while (permut);
  }
  
  public String toCSV()
  {
    StringBuffer tampon = new StringBuffer ( ) ;
    for(int i=0; i<codesATrouver.length; i++)
    {
      tampon . append( ";" ) ;
      tampon . append( codesATrouver[i]) ;
      tampon . append( ";" ) ;
      tampon . append( temps[i]) ;
    }
    
    return tampon . toString ( ) ;
  }
  
  public String getPartiel(int index)
  {
    String retour = "----";
    if(index==0)
    {
      if(okPm.length>0 && okPm[0] && depart>-1)
      {
        retour = TimeManager.fullTime(TimeManager.toLong(TimeManager.safeParse(temps[0])) - depart);
      }
    }
    else if(index == temps.length)
    {
      if(arrivee>-1 && okPm[index-1])
      {
        retour = TimeManager.fullTime(arrivee - TimeManager.toLong(TimeManager.safeParse(temps[index - 1])));
      }
    }
    else
    {
      if(okPm[index] && okPm[index-1])
      {
        retour = TimeManager.fullTime(TimeManager.toLong(TimeManager.safeParse(temps[index])) - TimeManager.toLong(TimeManager.safeParse(temps[index - 1])));
      }
    }
    if(retour.substring(0, 1).compareTo("-")==0)
    {
      retour = "----";
    }
    return retour;
  }
  
  public void calculTempsPostes()
  {
    if(circuit.isDepartBoitier())
    {
      depart = getPuce().getStarttime().getTime();
    }
    else
    {
      depart = circuit.getHeureDepart().getTime();
    }
    arrivee = getPuce().getFinishtime().getTime();
    tempsDeCourse = arrivee-depart;
    calculOkPm();
    setNbPostes();
    verifPm();
  }
  
  private void verifPm()
  {
    for(int i=0; i<okPm.length; i++)
    {
      if(!okPm[i])
      {
        temps[i] = "PM";
      }
    }
  }
  
  private void setNbPostes()
  {
    nbPostes = 0;
    for(int i=0; i <okPm.length; i++)
    {
      if(okPm[i])
      {
        nbPostes++;
      }
    }
  }
  
  private void calculOkPm()
  {
    // r�cup�ration des codes � trouver
    codesATrouver = circuit.getCodesToArray();
    // r�cup�ration des codes de la puce
    Vector<Integer> codesPuce = getCodes();
    Vector<String> tempsPuce = getTemps();
    // calcul des OK et PM
    okPm = new boolean[codesATrouver.length];
    temps = new String[codesATrouver.length];
    if(circuit.isEnLigne())
    {
      EnLigne el = new EnLigne(codesATrouver, codesPuce, tempsPuce);
      okPm = el.getOkPm();
      temps = el.getTemps();
    }
    else
    {
      AuScore as = new AuScore(codesATrouver, codesPuce, tempsPuce);
      okPm =as.getOkPm();
      temps = as.getTemps();
    }
  }
  
  public int getNbPM()
  {
    int retour = 0;
    for(int i=0; i<okPm.length; i++)
    {
      if(!okPm[i])
      {
        retour++;
      }
    }
    return retour;
  }

  public String toHtml()
  {
    calculOkPm();
    depart = 0;
    StringBuffer retour = new StringBuffer();
    // Identification
    retour.append("<b>" + EasyGec.getLangages().getText("96", EasyGec.getLang()) + "</b> " + getIdentifiant() + "<br>");
    retour.append("<b>" + EasyGec.getLangages().getText("97", EasyGec.getLang()) + "</b> " + getCircuit().getNom() + "");   
    
    //  R�sultats globaux de l'�tape
    //retour.append("<br>");
    retour.append("<br>");
    if(circuit.isDepartBoitier())
    {
      depart = getPuce().getStarttime().getTime();
    }
    else
    {
      depart = circuit.getHeureDepart().getTime();
    }
    arrivee = getPuce().getFinishtime().getTime();
    retour.append("<b>" + EasyGec.getLangages().getText("99", EasyGec.getLang()) + "</b> ");
    retour.append(TimeManager.fullTime(arrivee - depart) + "<br>");
    retour.append("<table><tr align=center><th></th><th>" + EasyGec.getLangages().getText("100", EasyGec.getLang()) + "</th><th>" + EasyGec.getLangages().getText("101", EasyGec.getLang()) + "</th><th>" + EasyGec.getLangages().getText("102", EasyGec.getLang()) + "</th></tr>");
    retour.append("<tr align=center><td></td><td><b>D</b></td>");
    retour.append("<td>" + TimeManager.fullTime(depart) + "</td><td></td></tr>");
    if(!circuit.isEnLigne())
    {
      triResultatsScore();
    }
    for(int i=0; i<codesATrouver.length; i++)
    {
      if(okPm[i])
      {
        retour.append("<tr align=center><td>" + (i+1) + "</td><td><b>" + codesATrouver[i] + "</b></td>");
        retour.append("<td>" + temps[i] + "</td><td>" + getPartiel(i) + "</td></tr>");
      }
      else
      {
        retour.append("<font color=red>");
        retour.append("<tr align=center><td>" + (i+1) + "</td><td><b>" + codesATrouver[i] + "</b></td>");
        retour.append("<td>PM</td><td>----</td></tr>");
        retour.append("</font>");
      }
    }
    retour.append("<tr align=center><td></td><td><b>A</b></td>");
    retour.append("<td>" + TimeManager.fullTime(arrivee) + "</td><td>" + getPartiel(codesATrouver.length) + "</td></tr>");
    retour.append("</table>");
    
    Vector<Integer> codesEnPlus = getCodesEnPlus();
    if(codesEnPlus.size()>0)
    {
      retour.append("<font size=1 color=blue><b>" + EasyGec.getLangages().getText("103", EasyGec.getLang()) + "</b><br>");
      for(int i=0; i<codesEnPlus.size(); i++)
      {
        retour.append(codesEnPlus.get(i) + "<br>");
      }
      //retour.append("</font>");
    }
    
    return retour.toString();
  }

  public void saveHtml()
  {
    HtmlResultatPuce.save(this, "temp.html");
  }
  
  private Vector<Integer> getCodesEnPlus()
  {
    Vector<Integer> retour = new Vector<Integer>();
    
    for(int i=0; i<getPuce().getPartiels().length; i++)
    {
      if(!existeCode(getPuce().getPartiels()[i].getCode()))
      {
        retour.add(getPuce().getPartiels()[i].getCode());
      }
    }
    
    return retour;
  }
  
  public boolean existeCode(int code)
  {
    boolean retour = false;
    
    for(int i=0; i<circuit.getCodes().getCodes().size(); i++)
    {
        if(circuit.getCodes().getCodes().get(i)==code)
        {
          retour = true;
        }
    }
    
    return retour;
  }
  
  public int getPositionPoste(int code)
  {
    return puce.getPositionPoste(code);
  }
  
  public Vector<String> getTemps()
  {
    return puce.getTemps();
  }
  
  public Vector<Integer> getCodes()
  {
    return puce.getCodes();
  }

  /**
   * @return the puce
   */
  public Puce getPuce()
  {
    return puce;
  }

  /**
   * @param puce the puce to set
   */
  public void setPuce(Puce puce)
  {
    this.puce = puce;
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

  /**
   * @return the nbPostes
   */
  public int getNbPostes()
  {
    return nbPostes;
  }

  /**
   * @param nbPostes the nbPostes to set
   */
  public void setNbPostes(int nbPostes)
  {
    this.nbPostes = nbPostes;
  }

  /**
   * @return the tempsDeCourse
   */
  public long getTempsDeCourse()
  {
    return tempsDeCourse;
  }

  /**
   * @param tempsDeCourse the tempsDeCourse to set
   */
  public void setTempsDeCourse(long tempsDeCourse)
  {
    this.tempsDeCourse = tempsDeCourse;
  }

  /**
   * @return the classement
   */
  public int getClassement()
  {
    return classement;
  }

  /**
   * @param classement the classement to set
   */
  public void setClassement(int classement)
  {
    this.classement = classement;
  }

  @Override
  public int compareTo(ResultatPuce o)
  {
    if(nbPostes < o.getNbPostes())
    {
      return 1;
    }
    if(nbPostes == o.getNbPostes())
    {
      if(tempsDeCourse >= o.getTempsDeCourse())
      {
        return 1;
      }
    }
    return -1;
  }

  /**
   * @return the data
   */
  public Vector<String> getDatas()
  {
    return datas;
  }

  /**
   * @param data the data to set
   */
  public void setDatas(Vector<String> datas)
  {
    this.datas = datas;
  }

  public String getData0()
  {
    if(datas.size()>0)
    {
      return datas.get(0);
    }
    else
    {
      return "";
    }
  }

  public String getDatasToCSV()
  {
    StringBuffer retour = new StringBuffer();
    for(int i=0; i<datas.size(); i++)
    {
      retour.append(datas.get(i) + ";");
    }
    
    return retour.toString();
  }

  public String getData1()
  {
    if(datas.size()>1)
    {
      return datas.get(1);
    }
    else
    {
      return "";
    }
  }
}
