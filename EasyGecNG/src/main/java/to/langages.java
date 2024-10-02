package to;

import java.util.Vector;

public class langages
{
  private Vector<langage> langages = new Vector<langage>();

  public String getText(String id, int lang)
  {
    String retour = "";
    for(int i=0; i<langages.size(); i++)
    {
      if(id.compareTo(langages.get(i).getId())==0)
      {
        return langages.get(i).getTexts().get(lang);
      }
    }
    return retour;
  }
  
  /**
   * @return the langages
   */
  public Vector<langage> getLangages()
  {
    return langages;
  }

  /**
   * @param langages the langages to set
   */
  public void setLangages(Vector<langage> langages)
  {
    this.langages = langages;
  }
}
