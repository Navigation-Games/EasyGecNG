package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;

import core.EasyGec;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import to.Orienteer;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;

public class UiOrienteer extends JDialog
{

  /**
   * 
   */
  private static final long serialVersionUID = 6925815924430132616L;
  private EasyGec easyGec;
  private Orienteer orienteer;
  private Orienteer o;
  private final JPanel contentPanel = new JPanel();
  private JTextField textFieldName;
  private JTextField textFieldFirstName;
  private JTextField textFieldChip;
  private JList<String> list;


  /**
   * Create the dialog.
   */
  public UiOrienteer(final EasyGec easyGec, Orienteer orienteer)
  {
    setModal(true);
    setTitle(EasyGec.getLanguages().getText("60", EasyGec.getLang()));
    setIconImage(Toolkit.getDefaultToolkit().getImage(UiOrienteer.class.getResource("/icons/easy.png")));
    setBounds(100, 100, 281, 376);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    {
      JPanel panel = new JPanel();
      FlowLayout flowLayout = (FlowLayout) panel.getLayout();
      flowLayout.setAlignment(FlowLayout.RIGHT);
      panel.setPreferredSize(new Dimension(220, 30));
      contentPanel.add(panel);
      {
        JLabel lblName = new JLabel(EasyGec.getLanguages().getText("61", EasyGec.getLang()));
        panel.add(lblName);
      }
      {
        textFieldName = new JTextField();
        textFieldName.setPreferredSize(new Dimension(200, 20));
        panel.add(textFieldName);
        textFieldName.setColumns(17);
      }
    }
    {
      JPanel panel = new JPanel();
      panel.setPreferredSize(new Dimension(220, 30));
      FlowLayout flowLayout = (FlowLayout) panel.getLayout();
      flowLayout.setAlignment(FlowLayout.RIGHT);
      contentPanel.add(panel);
      {
        JLabel lblPrname = new JLabel(EasyGec.getLanguages().getText("62", EasyGec.getLang()));
        panel.add(lblPrname);
      }
      {
        textFieldFirstName = new JTextField();
        panel.add(textFieldFirstName);
        textFieldFirstName.setColumns(17);
      }
    }
    {
      JPanel panel = new JPanel();
      FlowLayout flowLayout = (FlowLayout) panel.getLayout();
      flowLayout.setAlignment(FlowLayout.RIGHT);
      panel.setPreferredSize(new Dimension(220, 30));
      contentPanel.add(panel);
      {
        JLabel lblChip = new JLabel(EasyGec.getLanguages().getText("63", EasyGec.getLang()));
        panel.add(lblChip);
      }
      {
        textFieldChip = new JTextField();
        textFieldChip.setMinimumSize(new Dimension(130, 20));
        textFieldChip.setPreferredSize(new Dimension(130, 20));
        textFieldChip.setSize(new Dimension(130, 20));
        panel.add(textFieldChip);
        textFieldChip.setColumns(17);
      }
    }
    {
      JPanel panel = new JPanel();
      panel.setBorder(new TitledBorder(null, EasyGec.getLanguages().getText("64", EasyGec.getLang()), TitledBorder.LEADING, TitledBorder.TOP, null, null));
      panel.setPreferredSize(new Dimension(230, 180));
      contentPanel.add(panel);
      {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(210, 100));
        panel.add(scrollPane);
        list = new JList<String>();
        scrollPane.setViewportView(list);
      }
      {
        JPanel panel_1 = new JPanel();
        panel.add(panel_1);
        {
          JButton btnplus = new JButton("");
          btnplus.setToolTipText(EasyGec.getLanguages().getText("65", EasyGec.getLang()));
          btnplus.addActionListener(new ActionListener() 
          {
            public void actionPerformed(ActionEvent e) 
            {
              String resultValue = JOptionPane.showInputDialog(UiOrienteer.this, EasyGec.getLanguages().getText("72", EasyGec.getLang()), EasyGec.getLanguages().getText("73", EasyGec.getLang()), JOptionPane.INFORMATION_MESSAGE);
              if(null != resultValue)
              {
                if(null != UiOrienteer.this.orienteer)
                {
                  UiOrienteer.this.orienteer.getDatas().add(resultValue);
                  list.setListData(UiOrienteer.this.orienteer.getDatas());
                }
                else
                {
                  o.getDatas().add(resultValue);
                  list.setListData(o.getDatas());
                }
              }
            }
          });
          btnplus.setPreferredSize(new Dimension(32, 32));
          btnplus.setIcon(new ImageIcon(UiOrienteer.class.getResource("/icons/plus.png")));
          panel_1.add(btnplus);
        }
        {
          JButton btnModif = new JButton("");
          btnModif.setToolTipText(EasyGec.getLanguages().getText("66", EasyGec.getLang()));
          btnModif.addActionListener(new ActionListener() 
          {
            public void actionPerformed(ActionEvent e) 
            {
              if(list.getSelectedIndex()>-1)
              {
                String resultValue = JOptionPane.showInputDialog(UiOrienteer.this, EasyGec.getLanguages().getText("74", EasyGec.getLang()),  list.getSelectedValue());
                if(null != resultValue)
                {
                  if(null != UiOrienteer.this.orienteer)
                  {
                    UiOrienteer.this.orienteer.getDatas().set(list.getSelectedIndex(), resultValue);
                    list.setListData(UiOrienteer.this.orienteer.getDatas());
                  }
                  else
                  {
                    o.getDatas().set(list.getSelectedIndex(), resultValue);
                    list.setListData(o.getDatas());
                  }
                }
              }
            }
          });
          btnModif.setPreferredSize(new Dimension(32, 32));
          btnModif.setIcon(new ImageIcon(UiOrienteer.class.getResource("/icons/search.png")));
          panel_1.add(btnModif);
        }
        {
          JButton btnSupp = new JButton("");
          btnSupp.setToolTipText(EasyGec.getLanguages().getText("67", EasyGec.getLang()));
          btnSupp.addActionListener(new ActionListener() 
          {
            public void actionPerformed(ActionEvent e) 
            {
              if(list.getSelectedIndex()>-1)
              {
                if(null != UiOrienteer.this.orienteer)
                {
                  UiOrienteer.this.orienteer.getDatas().remove(list.getSelectedIndex());
                  list.setListData(UiOrienteer.this.orienteer.getDatas());
                }
                else
                {
                  o.getDatas().remove(list.getSelectedIndex());
                  list.setListData(o.getDatas());
                }
              }
            }
          });
          btnSupp.setPreferredSize(new Dimension(32, 32));
          btnSupp.setIcon(new ImageIcon(UiOrienteer.class.getResource("/icons/delete.png")));
          panel_1.add(btnSupp);
        }
        {
          JButton btnUp = new JButton("");
          btnUp.setToolTipText(EasyGec.getLanguages().getText("68", EasyGec.getLang()));
          btnUp.addActionListener(new ActionListener() 
          {
            public void actionPerformed(ActionEvent e) 
            {
              if(list.getSelectedIndex()>-1)
              {
                int index = list.getSelectedIndex();
                if(null != UiOrienteer.this.orienteer)
                {
                  UiOrienteer.this.orienteer.upData(list.getSelectedValue(), list.getSelectedIndex());
                  list.setListData(UiOrienteer.this.orienteer.getDatas());
                }
                else
                {
                  o.upData(list.getSelectedValue(), list.getSelectedIndex());;
                  list.setListData(o.getDatas());
                }
                list.setSelectedIndex(index-1);
              }
            }
          });
          btnUp.setPreferredSize(new Dimension(32, 32));
          btnUp.setIcon(new ImageIcon(UiOrienteer.class.getResource("/icons/up.png")));
          panel_1.add(btnUp);
        }
        {
          JButton btnDonw = new JButton("");
          btnDonw.setToolTipText(EasyGec.getLanguages().getText("69", EasyGec.getLang()));
          btnDonw.addActionListener(new ActionListener() 
          {
            public void actionPerformed(ActionEvent e) 
            {
              if(list.getSelectedIndex()>-1)
              {
                int index = list.getSelectedIndex();
                if(null != UiOrienteer.this.orienteer)
                {
                  UiOrienteer.this.orienteer.downData(list.getSelectedValue(), list.getSelectedIndex());
                  list.setListData(UiOrienteer.this.orienteer.getDatas());
                }
                else
                {
                  o.downData(list.getSelectedValue(), list.getSelectedIndex());;
                  list.setListData(o.getDatas());
                }
                list.setSelectedIndex(index+1);
              }
            }
          });
          btnDonw.setIcon(new ImageIcon(UiOrienteer.class.getResource("/icons/down.png")));
          btnDonw.setPreferredSize(new Dimension(32, 32));
          panel_1.add(btnDonw);
        }
      }
    }
    {
      JPanel buttonPane = new JPanel();
      buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      {
        JButton okButton = new JButton(EasyGec.getLanguages().getText("70", EasyGec.getLang()));
        okButton.addActionListener(new ActionListener() 
        {
          public void actionPerformed(ActionEvent e) 
          {
            if(UiOrienteer.this.orienteer == null)
            {
              
              o.setName(textFieldName.getText().trim());
              o.setFirstName(textFieldFirstName.getText().trim());
              o.setIdChip(textFieldChip.getText().trim());
              o.setDatas(list.getModel());
              UiOrienteer.this.easyGec.getOrienteers().getOrienteers().add(o);
            }
            else
            {
              UiOrienteer.this.orienteer.setName(textFieldName.getText().trim());
              UiOrienteer.this.orienteer.setFirstName(textFieldFirstName.getText().trim());
              UiOrienteer.this.orienteer.setIdChip(textFieldChip.getText().trim());
              UiOrienteer.this.orienteer.setDatas(list.getModel());
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
    }
    
    this.easyGec = easyGec;
    this.orienteer = orienteer;
    initData();
  }

  private void initData()
  {
    if(orienteer == null)
    {
      o = new Orienteer();
      textFieldName.setText("NOM");
      textFieldFirstName.setText("PRENOM");
      textFieldChip.setText("123456");
      list.setListData(o.getDatas());
    }
    else
    {
      textFieldName.setText(orienteer.getName());
      textFieldFirstName.setText(orienteer.getFirstName());
      textFieldChip.setText(orienteer.getIdChip());
      list.setListData(orienteer.getDatas());
    }
  }
}
