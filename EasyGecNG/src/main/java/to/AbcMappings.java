package to;

import java.util.Vector;

public class AbcMappings
{
  private String name = "";
  private Vector<AbcMapping> mappings = new Vector<>();
  
  public void addMap(String map)
  {
    mappings.add(new AbcMapping(map.toLowerCase()));
  }
  
  public String getMap(int code)
  {
    for(int i=0; i<mappings.size(); i++)
    {
      if(code==(i + 31) )
      {
        return mappings.get(i).getMap();
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
    for(int i=0; i<mappings.size(); i++)
    {
      if(mappings.get(i).getMap().compareTo(map)==0)
      {
        return i+31;
      }
    }
    return 255;
  }
  
  public String toString()
  {
    return getName();
  }
  
  public String toCsv()
  {
    StringBuffer resultValue = new StringBuffer();
    resultValue.append(31 + ";\u00D0;\n");
    resultValue.append(32 + ";\u00D1;\n");
    resultValue.append(33 + ";_;\n");
    for(int i=2; i<225; i++)
    {
      resultValue.append(31 + i + ";" + mappings.get(i).getMap() + ";\n");
    }
    
    return resultValue.toString();
  }
  
  public void setMappingsVide()
  {
    mappings.add(new AbcMapping(""));
    mappings.add(new AbcMapping(""));
    for(int i=33; i<256; i++)
    {
      mappings.add(new AbcMapping(""));
    }
  }
  
  public void setAbcMappingsInitial()
  {
    setName("Default");
    String[] charArray ={ "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
    mappings.add(new AbcMapping(""));
    mappings.add(new AbcMapping(""));
    mappings.add(new AbcMapping(""));
    for(int i=0; i<charArray.length; i++)
    {
      mappings.add(new AbcMapping(charArray[i]));
    }
    for(int i=0; i<10; i++)
    {
      mappings.add(new AbcMapping(i + ""));
    }
    mappings.add(new AbcMapping("+"));
    mappings.add(new AbcMapping("-"));
    mappings.add(new AbcMapping("*"));
    mappings.add(new AbcMapping("/"));
    mappings.add(new AbcMapping("="));
    for(int i=44; i<225; i++)
    {
      mappings.add(new AbcMapping(""));
    }
  }

  /**
   * @return the mappings
   */
  public Vector<AbcMapping> getMappings()
  {
    return mappings;
  }

  /**
   * @param mappings the mappings to set
   */
  public void setMappings(Vector<AbcMapping> mappings)
  {
    this.mappings = mappings;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name)
  {
    this.name = name;
  }
}
