package ihm;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import to.Orienteur;

public class OrienteurListCellRenderer extends JLabel implements ListCellRenderer<Orienteur>
{

  /**
   * 
   */
  private static final long serialVersionUID = -6294326879574699200L;

  @Override
  public Component getListCellRendererComponent(JList<? extends Orienteur> list,
      Orienteur value, int index, boolean isSelected, boolean cellHasFocus)
  {
    setText(value.toString());
    
    if (isSelected) 
    {
      setBackground(list.getSelectionBackground());
      setForeground(Color.WHITE);
    }
    else 
    {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }
    
    if (value.isDebut()) 
    {
      setBackground(Color.GREEN);
    }
    if (value.isFin())
    {
      setBackground(Color.RED);
    }
    
    setEnabled(list.isEnabled());
    setFont(list.getFont());
    setOpaque(true);
    return this;
  }

}
