package to;

import java.util.Vector;

public class Orienteers
{
  private Vector<Orienteer> orienteers = new Vector<Orienteer>();
  
  public Vector<String> getDatas(String identification)
  {
    for(int i=0; i<orienteers.size(); i++)
    {
      if((orienteers.get(i).getFirstName() + " " + orienteers.get(i).getName()).compareTo(identification)==0)
      {
        return orienteers.get(i).getDatas();
      }
    }
    return new Vector<String>();
  }

  public void addOrienteer(Orienteer r)
  {
    orienteers.add(r);
    r.setFin(true);
    if(orienteers.size() == 1)
    {
      r.setDebut(true);
    }
    if(orienteers.size() > 1)
    {
      orienteers.get(orienteers.size()-2).setFin(false);
    }
  }
  
  public void setDebut(Orienteer r)
  {
    if(orienteers.indexOf(r)<getIndexFin())
    {
      for(int i=0; i<orienteers.size(); i++)
      {
        orienteers.get(i).setDebut(false);
      }
      r.setDebut(true);
    }
  }
  
  public void setFin(Orienteer r)
  {
    if(orienteers.indexOf(r)>getIndexDebut())
    {
      for(int i=0; i<orienteers.size(); i++)
      {
        orienteers.get(i).setFin(false);
      }
      r.setFin(true);
    }
  }
  
  public void simplifier()
  {
    int debut = getIndexDebut();
    int fin = getIndexFin();
    int size = orienteers.size()-1;
    for(int i=size; i>fin; i--)
    {
      orienteers.remove(orienteers.size()-1);
    }
    for(int i=0; i<debut; i++)
    {
      orienteers.remove(0);
    }
  }
  
  private int getIndexDebut()
  {
    for(int i=0; i < orienteers.size(); i++)
    {
      if(orienteers.get(i).isDebut())
      {
        return i;
      }
    }
    return 0;
  }
  
  private int getIndexFin()
  {
    for(int i=orienteers.size()-1; i >= 0 ; i--)
    {
      if(orienteers.get(i).isFin())
      {
        return i;
      }
    }
    return 0;
  }
  
  public String getOrienteer(String idChip)
  {
    for(int i=0; i < orienteers.size(); i++)
    {
      if(orienteers.get(i).getIdChip().compareTo(idChip)==0)
      {
        return orienteers.get(i).getFirstName() + " " + orienteers.get(i).getName();
      }
    }
    return idChip;
  }

  /**
   * @return the orienteers
   */
  public Vector<Orienteer> getOrienteers()
  {
    return orienteers;
  }

  /**
   * @param orienteers the orienteers to set
   */
  public void setOrienteers(Vector<Orienteer> orienteers)
  {
    this.orienteers = orienteers;
  }
}
