package to;

import java.util.Comparator;

public class ChipResultComparator implements Comparator<ChipResult>
{
  public static final int COMPARE_CLASSIQUE = 0;
  public static final int COMPARE_BY_IDENTIFIANT = 1;
  
  private int compare_mode = COMPARE_CLASSIQUE;
  
  public ChipResultComparator(int compare_mode)
  {
    this.compare_mode = compare_mode;
  }
  
  @Override
  public int compare(ChipResult o1, ChipResult o2)
  {
    switch (compare_mode)
    {
      case COMPARE_CLASSIQUE:
        if(o1.getControlCount() < o2.getControlCount())
        {
          return 1;
        }
        if(o1.getControlCount() == o2.getControlCount())
        {
          if(o1.getCourseDuration() > o2.getCourseDuration())
          {
            return 1;
          }
        }
        return -1;
        
        case COMPARE_BY_IDENTIFIANT:
          if(o1.getIdentifier().compareTo(o2.getIdentifier())>0)
          {
            return 1;
          }
          if(o1.getIdentifier().compareTo(o2.getIdentifier())==0)
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
