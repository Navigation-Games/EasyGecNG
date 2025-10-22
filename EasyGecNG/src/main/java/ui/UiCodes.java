package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JList;

import to.Course;
import javax.swing.JScrollPane;

import core.EasyGec;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UiCodes extends JDialog
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
  public UiCodes(final Course course, EasyGec easyGec)
  {
    setModal(true);
    setTitle( EasyGec.getLanguages().getText("75", EasyGec.getLang()));
    setIconImage(Toolkit.getDefaultToolkit().getImage(UiCodes.class.getResource("/icons/easy.png")));
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
        JButton okButton = new JButton(EasyGec.getLanguages().getText("76", EasyGec.getLang()));
        okButton.addActionListener(new ActionListener() 
        {
          public void actionPerformed(ActionEvent e) 
          {
            if(listCodes.getSelectedIndices().length>0)
            {
              course.addCodes(listCodes.getSelectedValuesList());
            }
            dispose();
          }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
      }
      {
        JButton cancelButton = new JButton(EasyGec.getLanguages().getText("71", EasyGec.getLang()));
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
        JLabel lblChoisissezLesCodes = new JLabel(EasyGec.getLanguages().getText("77", EasyGec.getLang()));
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
