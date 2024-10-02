/**
 * 
 */
package to;

import java.util.Vector;

/**
 * <P>
 * Titre : Codes.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author Thierry
 *
 */
public class Codes
{
  private Vector<Integer> codes = new Vector<Integer>();

  /**
   * @return the codes
   */
  public Vector<Integer> getCodes()
  {
    return codes;
  }

  /**
   * @param codes the codes to set
   */
  public void setCodes(Vector<Integer> codes)
  {
    this.codes = codes;
  }

  
  public void upCode(int code, int index)
  {
    if(index>0)
    {
      codes.insertElementAt(code, index-1);
      codes.removeElementAt(index+1);
    }
  }
  
  public void downCode(int code, int index)
  {
    if(index<codes.size()-1)
    {
      codes.insertElementAt(code, index+2);
      codes.removeElementAt(index);
    }
  }
}
