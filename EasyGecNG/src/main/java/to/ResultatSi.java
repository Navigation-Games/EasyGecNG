package to;

import outils.TimeManager;

public class ResultatSi
{
  private Puce puce;
  private boolean debut = false;
  private boolean fin = false;
  
  public ResultatSi()
  {
    puce = new Puce();
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
  
  public String toString()
  {
    return puce.getIdPuce() + " - " + TimeManager.fullTime(puce.getStarttime()) + " - " + TimeManager.fullTime(puce.getFinishtime());
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
}
