package to;

import java.util.Vector;

public class langage
{
  private String id="";
  private Vector<String> texts = new Vector<String>();
  
  
  /**
   * @return the id
   */
  public String getId()
  {
    return id;
  }
  /**
   * @param id the id to set
   */
  public void setId(String id)
  {
    this.id = id;
  }
  /**
   * @return the texts
   */
  public Vector<String> getTexts()
  {
    return texts;
  }
  /**
   * @param texts the texts to set
   */
  public void setTexts(Vector<String> texts)
  {
    this.texts = texts;
  }
}
