package to;

import java.util.Vector;

public class MappagesAbc
{
  private String nom = "";
  private Vector<MappageAbc> mappages = new Vector<>();
  
  public void addMap(String map)
  {
    mappages.add(new MappageAbc(map.toLowerCase()));
  }
  
  public String getMap(int code)
  {
    for(int i=0; i<mappages.size(); i++)
    {
      if(code==(i + 31) )
      {
        return mappages.get(i).getMap();
      }
    }
    return "?";
  }
  
  public int getCode(String map)
  {
    if(map.compareTo(" ")==0)
    {
      return 33;
    }
    for(int i=0; i<mappages.size(); i++)
    {
      if(mappages.get(i).getMap().compareTo(map)==0)
      {
        return i+31;
      }
    }
    return 255;
  }
  
  public String toString()
  {
    return getNom();
  }
  
  public String toCsv()
  {
    StringBuffer retour = new StringBuffer();
    retour.append(31 + ";\u00D0;\n");
    retour.append(32 + ";\u00D1;\n");
    retour.append(33 + ";_;\n");
    for(int i=2; i<225; i++)
    {
      retour.append(31 + i + ";" + mappages.get(i).getMap() + ";\n");
    }
    
    return retour.toString();
  }
  
  public void setMappagesVide()
  {
    mappages.add(new MappageAbc(""));
    mappages.add(new MappageAbc(""));
    for(int i=33; i<256; i++)
    {
      mappages.add(new MappageAbc(""));
    }
  }
  
  public void setMappagesAbcInitial()
  {
    setNom("Default");
    String[] charArray ={ "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
    mappages.add(new MappageAbc(""));
    mappages.add(new MappageAbc(""));
    mappages.add(new MappageAbc(""));
    for(int i=0; i<charArray.length; i++)
    {
      mappages.add(new MappageAbc(charArray[i]));
    }
    for(int i=0; i<10; i++)
    {
      mappages.add(new MappageAbc(i + ""));
    }
    mappages.add(new MappageAbc("+"));
    mappages.add(new MappageAbc("-"));
    mappages.add(new MappageAbc("*"));
    mappages.add(new MappageAbc("/"));
    mappages.add(new MappageAbc("="));
    for(int i=44; i<225; i++)
    {
      mappages.add(new MappageAbc(""));
    }
  }

  /**
   * @return the mappages
   */
  public Vector<MappageAbc> getMappages()
  {
    return mappages;
  }

  /**
   * @param mappages the mappages to set
   */
  public void setMappages(Vector<MappageAbc> mappages)
  {
    this.mappages = mappages;
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
}
