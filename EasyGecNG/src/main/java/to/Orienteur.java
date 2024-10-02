package to;

import java.util.Vector;

import javax.swing.ListModel;

public class Orienteur
{
  private String idPuce = "";
  private String nom = "";
  private String prenom = "";
  private boolean notUsed = true;
  private Vector<String> datas = new Vector<String>();
  private boolean debut = false;
  private boolean fin = false;
  
  public Orienteur()
  {
    
  }
  
  public String[][] getDatasTableau()
  {
    String[][] retour = new String[datas.size()+1][1];
    for(int i=0; i<datas.size(); i++)
    {
      retour[i][0] = datas.get(i);
    }
    return retour;
  }
  
  public String toString()
  {
    StringBuffer retour = new StringBuffer(idPuce + " - " + nom + " " + prenom);
    if(datas.size()>0)
    {
      retour.append(" - " + datas.get(0));
    }
    return  retour.toString();
  }
  
  public String getData(int index)
  {
    if(datas.size()>0)
    {
      return datas.get(index);
    }
    else
    {
      return "";
    }
  }
  
  /**
   * @return the idPuce
   */
  public String getIdPuce()
  {
    return idPuce;
  }
  /**
   * @param idPuce the idPuce to set
   */
  public void setIdPuce(String idPuce)
  {
    this.idPuce = idPuce;
  }
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
   * @return the prenom
   */
  public String getPrenom()
  {
    return prenom;
  }
  /**
   * @param prenom the prenom to set
   */
  public void setPrenom(String prenom)
  {
    this.prenom = prenom;
  }
  /**
   * @return the datas
   */
  public Vector<String> getDatas()
  {
    return datas;
  }
  /**
   * @param datas the datas to set
   */
  public void setDatas(Vector<String> datas)
  {
    this.datas = datas;
    if(this.datas.lastElement().trim().compareTo("")==0)
    {
      this.datas.removeElementAt(datas.size()-1);
    }
  }
  /**
   * @return the notUsed
   */
  public boolean isNotUsed()
  {
    return notUsed;
  }
  /**
   * @param notUsed the notUsed to set
   */
  public void setNotUsed(boolean notUsed)
  {
    this.notUsed = notUsed;
  }

  /**
   * @return the debut
   */
  public boolean isDebut()
  {
    return debut;
  }

  /**
   * @param debut the debut to set
   */
  public void setDebut(boolean debut)
  {
    this.debut = debut;
  }

  /**
   * @return the fin
   */
  public boolean isFin()
  {
    return fin;
  }

  /**
   * @param fin the fin to set
   */
  public void setFin(boolean fin)
  {
    this.fin = fin;
  }

  public void setDatas(ListModel<String> model)
  {
    datas = new Vector<String>();
    for(int i=0; i<model.getSize(); i++)
    {
      datas.add(model.getElementAt(i));
    }
  }
  
  public void upData(String data, int index)
  {
    if(index>0)
    {
      datas.insertElementAt(data, index-1);
      datas.removeElementAt(index+1);
    }
  }
  
  public void downData(String data, int index)
  {
    if(index<datas.size()-1)
    {
      datas.insertElementAt(data, index+2);
      datas.removeElementAt(index);
    }
  }
}
