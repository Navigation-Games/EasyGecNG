package to;

import java.util.Vector;

public class Orienteurs
{
  private Vector<Orienteur> orienteurs = new Vector<Orienteur>();
  
  public Vector<String> getDatas(String identification)
  {
    for(int i=0; i<orienteurs.size(); i++)
    {
      if((orienteurs.get(i).getPrenom() + " " + orienteurs.get(i).getNom()).compareTo(identification)==0)
      {
        return orienteurs.get(i).getDatas();
      }
    }
    return new Vector<String>();
  }

  public void addOrienteur(Orienteur r)
  {
    orienteurs.add(r);
    r.setFin(true);
    if(orienteurs.size() == 1)
    {
      r.setDebut(true);
    }
    if(orienteurs.size() > 1)
    {
      orienteurs.get(orienteurs.size()-2).setFin(false);
    }
  }
  
  public void setDebut(Orienteur r)
  {
    if(orienteurs.indexOf(r)<getIndexFin())
    {
      for(int i=0; i<orienteurs.size(); i++)
      {
        orienteurs.get(i).setDebut(false);
      }
      r.setDebut(true);
    }
  }
  
  public void setFin(Orienteur r)
  {
    if(orienteurs.indexOf(r)>getIndexDebut())
    {
      for(int i=0; i<orienteurs.size(); i++)
      {
        orienteurs.get(i).setFin(false);
      }
      r.setFin(true);
    }
  }
  
  public void simplifier()
  {
    int debut = getIndexDebut();
    int fin = getIndexFin();
    int size = orienteurs.size()-1;
    for(int i=size; i>fin; i--)
    {
      orienteurs.remove(orienteurs.size()-1);
    }
    for(int i=0; i<debut; i++)
    {
      orienteurs.remove(0);
    }
  }
  
  private int getIndexDebut()
  {
    for(int i=0; i < orienteurs.size(); i++)
    {
      if(orienteurs.get(i).isDebut())
      {
        return i;
      }
    }
    return 0;
  }
  
  private int getIndexFin()
  {
    for(int i=orienteurs.size()-1; i >= 0 ; i--)
    {
      if(orienteurs.get(i).isFin())
      {
        return i;
      }
    }
    return 0;
  }
  
  public String getOrienteur(String idPuce)
  {
    for(int i=0; i < orienteurs.size(); i++)
    {
      if(orienteurs.get(i).getIdPuce().compareTo(idPuce)==0)
      {
        return orienteurs.get(i).getPrenom() + " " + orienteurs.get(i).getNom();
      }
    }
    return idPuce;
  }

  /**
   * @return the orienteurs
   */
  public Vector<Orienteur> getOrienteurs()
  {
    return orienteurs;
  }

  /**
   * @param orienteurs the orienteurs to set
   */
  public void setOrienteurs(Vector<Orienteur> orienteurs)
  {
    this.orienteurs = orienteurs;
  }
}
