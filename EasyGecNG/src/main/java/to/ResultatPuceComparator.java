package to;

import java.util.Comparator;

public class ResultatPuceComparator implements Comparator<ResultatPuce>
{
  public static final int COMPARE_CLASSIQUE = 0;
  public static final int COMPARE_BY_IDENTIFIANT = 1;
  
  private int compare_mode = COMPARE_CLASSIQUE;
  
  public ResultatPuceComparator(int compare_mode)
  {
    this.compare_mode = compare_mode;
  }
  
  @Override
  public int compare(ResultatPuce o1, ResultatPuce o2)
  {
    switch (compare_mode)
    {
      case COMPARE_CLASSIQUE:
        if(o1.getNbPostes() < o2.getNbPostes())
        {
          return 1;
        }
        if(o1.getNbPostes() == o2.getNbPostes())
        {
          if(o1.getTempsDeCourse() > o2.getTempsDeCourse())
          {
            return 1;
          }
        }
        return -1;
        
        case COMPARE_BY_IDENTIFIANT:
          if(o1.getIdentifiant().compareTo(o2.getIdentifiant())>0)
          {
            return 1;
          }
          if(o1.getIdentifiant().compareTo(o2.getIdentifiant())==0)
          {
            return 0;
          }
          else
          {
            return -1;
          }
          
      default:
        return 0;
    }
  }

}
