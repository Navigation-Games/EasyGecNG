package to;

import utils.TimeManager;

public class SiResult
{
  private Chip chip;
  private boolean debut = false;
  private boolean fin = false;
  
  public SiResult()
  {
    chip = new Chip();
  }

  /**
   * @return the chip
   */
  public Chip getChip()
  {
    return chip;
  }

  /**
   * @param chip the chip to set
   */
  public void setChip(Chip chip)
  {
    this.chip = chip;
  }
  
  public String toString()
  {
    return chip.getIdChip() + " - " + TimeManager.fullTime(chip.getStarttime()) + " - " + TimeManager.fullTime(chip.getFinishtime());
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
