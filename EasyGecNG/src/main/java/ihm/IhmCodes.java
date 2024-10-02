package ihm;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JList;

import to.Circuit;
import javax.swing.JScrollPane;

import metier.EasyGec;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IhmCodes extends JDialog
{

  /**
   * 
   */
  private static final long serialVersionUID = 6925815924430132616L;
  private Integer[] codes = new Integer[225];
  private final JPanel contentPanel = new JPanel();
  public JList<Integer> listCodes;


  /**
   * Create the dialog.
   */
  public IhmCodes(final Circuit circuit, EasyGec easyGec)
  {
    setModal(true);
    setTitle( EasyGec.getLangages().getText("75", EasyGec.getLang()));
    setIconImage(Toolkit.getDefaultToolkit().getImage(IhmCodes.class.getResource("/icones/easy.png")));
    setBounds(100, 100, 191, 404);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    {
      listCodes = new JList<Integer>();
    }
    contentPanel.setLayout(new BorderLayout(0, 0));
    {
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(listCodes);
      contentPanel.add(scrollPane);
    }
    {
      JPanel buttonPane = new JPanel();
      buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      {
        JButton okButton = new JButton(EasyGec.getLangages().getText("76", EasyGec.getLang()));
        okButton.addActionListener(new ActionListener() 
        {
          public void actionPerformed(ActionEvent e) 
          {
            if(listCodes.getSelectedIndices().length>0)
            {
              circuit.addCodes(listCodes.getSelectedValuesList());
            }
            dispose();
          }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
      }
      {
        JButton cancelButton = new JButton(EasyGec.getLangages().getText("71", EasyGec.getLang()));
        cancelButton.addActionListener(new ActionListener() 
        {
          public void actionPerformed(ActionEvent arg0) 
          {
            dispose();
          }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
      }
    }
    {
      JPanel panel = new JPanel();
      FlowLayout flowLayout = (FlowLayout) panel.getLayout();
      flowLayout.setAlignment(FlowLayout.LEFT);
      getContentPane().add(panel, BorderLayout.NORTH);
      {
        JLabel lblChoisissezLesCodes = new JLabel(EasyGec.getLangages().getText("77", EasyGec.getLang()));
        panel.add(lblChoisissezLesCodes);
      }
    }
    
    initCodes();
    listCodes.setListData(codes);
  }

  private void initCodes()
  {
    for(int i=0; i<225; i++)
    {
      codes[i] = i + 31;
    }
  }
}
