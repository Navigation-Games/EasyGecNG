package to;

import java.util.Vector;

public class Languages
{
  private Vector<Language> Languages = new Vector<Language>();

  public String getText(String id, int lang)
  {
    String resultValue = "";
    for(int i=0; i<Languages.size(); i++)
    {
      if(id.compareTo(Languages.get(i).getId())==0)
      {
        return Languages.get(i).getTexts().get(lang);
      }
    }
    return resultValue;
  }
  
  /**
   * @return the Languages
   */
  public Vector<Language> getLanguages()
  {
    return Languages;
  }

  /**
   * @param Languages the Languages to set
   */
  public void setLanguages(Vector<Language> Languages)
  {
    this.Languages = Languages;
  }
}
