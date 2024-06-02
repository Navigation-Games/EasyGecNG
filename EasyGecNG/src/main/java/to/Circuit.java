/**
 * 
 */
package to;

import java.util.Date;
import java.util.List;

import metier.EasyGec;

import outils.TimeManager;

/**
 * <P>
 * Titre : Circuit.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author Thierry
 *
 */
public class Circuit implements Comparable<Circuit>
{
  private String nom = "";
  private boolean enLigne = true;
  private boolean departBoitier = true;
  private Date heureDepart = new Date(32400000);//9:00
  private Codes codes = new Codes();
  /**
   * @return the nom
   */
  public String getNom()
  {
    return nom;
  }
  /**
   * @param nom the nom to set
   */
  public void setNom(String nom)
  {
    this.nom = nom;
  }
  /**
   * @return the enLigne
   */
  public boolean isEnLigne()
  {
    return enLigne;
  }
  /**
   * @param enLigne the enLigne to set
   */
  public void setEnLigne(boolean enLigne)
  {
    this.enLigne = enLigne;
  }
  
  public String getEnLigne()
  {
    return (isEnLigne())? "1" : "0";
  }
  /**
   * @return the codes
   */
  public Codes getCodes()
  {
    return codes;
  }
  /**
   * @param codes the codes to set
   */
  public void setCodes(Codes codes)
  {
    this.codes = codes;
  }
  /**
   * @return the departBoitier
   */
  public boolean isDepartBoitier()
  {
    return departBoitier;
  }
  /**
   * @param departBoitier the departBoitier to set
   */
  public void setDepartBoitier(boolean departBoitier)
  {
    this.departBoitier = departBoitier;
  }
  
  public String getDepartBoitier()
  {
    return (isDepartBoitier())? "1" : "0";
  }
  /**
   * @return the heureDepart
   */
  public Date getHeureDepart()
  {
    return heureDepart;
  }
  /**
   * @param heureDepart the heureDepart to set
   */
  public void setHeureDepart(Date heureDepart)
  {
    this.heureDepart = heureDepart;
  }
  
  public String toString()
  {
    StringBuffer retour = new StringBuffer(nom);
    retour .append(" (" + codes.getCodes().size() + " " + EasyGec.getLangages().getText("106", EasyGec.getLang()) + ", ");
    if(isEnLigne())
    {
      retour.append(EasyGec.getLangages().getText("18", EasyGec.getLang()) + ", ");
    }
    else
    {
      retour.append(EasyGec.getLangages().getText("17", EasyGec.getLang()) + ", ");
    }
    if(isDepartBoitier())
    {
      retour.append(EasyGec.getLangages().getText("15", EasyGec.getLang()) + ")");
    }
    else
    {
      retour.append(EasyGec.getLangages().getText("16", EasyGec.getLang()) + " " + TimeManager.fullTime(heureDepart) +")");
    }
    return retour.toString() ;
  }

  public int[] getCodesToArray()
  {
    int[] retour = new int[codes.getCodes().size()];
    for(int i=0; i<codes.getCodes().size(); i++)
    {
      retour[i] = codes.getCodes().get(i);
    }
    return retour;
  }
  
  public String toHtml()
  {
    StringBuffer retour = new StringBuffer();
    retour.append("<b>" + getNom() + "</b> (" + getCodes().getCodes().size() + ")");
    retour.append("<table CELLPADDING=5><tr align=center style='font-weight:bold;'><td></td><td></td><td></td><td></td><td>" + EasyGec.getLangages().getText("101", EasyGec.getLang()) + "</td><td>" + EasyGec.getLangages().getText("108", EasyGec.getLang()) + "</td>");
    if(isEnLigne())
    {
      for(int i=0; i<codes.getCodes().size(); i++)
      {
         retour.append("<td align=center>" + (i+1) + " (" + codes.getCodes().get(i) + ")</td>");
      }
    }
    else
    {
      retour.append("<td align=center>" + EasyGec.getLangages().getText("106", EasyGec.getLang()) + "</td>");
    }
    retour.append("<td align=center>" + EasyGec.getLangages().getText("109", EasyGec.getLang()) + "</td></tr>");
    return retour.toString();
  }
  
  public void addCodes(List<Integer> codes)
  {
    for(int i=0; i<codes.size(); i++)
    {
      this.codes.getCodes().add(codes.get(i));
    }
  }
  @Override
  public int compareTo(Circuit o)
  {
    if(codes.getCodes().size()>o.getCodes().getCodes().size())
    {
      return -1;
    }
    return 1;
  }
}
