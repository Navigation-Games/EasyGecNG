package ihm;

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

import metier.EasyGec;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import to.Orienteur;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;

public class IhmOrienteur extends JDialog
{

  /**
   * 
   */
  private static final long serialVersionUID = 6925815924430132616L;
  private EasyGec easyGec;
  private Orienteur orienteur;
  private Orienteur o;
  private final JPanel contentPanel = new JPanel();
  private JTextField textFieldNom;
  private JTextField textFieldPrenom;
  private JTextField textFieldPuce;
  private JList<String> list;


  /**
   * Create the dialog.
   */
  public IhmOrienteur(final EasyGec easyGec, Orienteur orienteur)
  {
    setModal(true);
    setTitle(EasyGec.getLangages().getText("60", EasyGec.getLang()));
    setIconImage(Toolkit.getDefaultToolkit().getImage(IhmOrienteur.class.getResource("/icones/easy.png")));
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
        JLabel lblNom = new JLabel(EasyGec.getLangages().getText("61", EasyGec.getLang()));
        panel.add(lblNom);
      }
      {
        textFieldNom = new JTextField();
        textFieldNom.setPreferredSize(new Dimension(200, 20));
        panel.add(textFieldNom);
        textFieldNom.setColumns(17);
      }
    }
    {
      JPanel panel = new JPanel();
      panel.setPreferredSize(new Dimension(220, 30));
      FlowLayout flowLayout = (FlowLayout) panel.getLayout();
      flowLayout.setAlignment(FlowLayout.RIGHT);
      contentPanel.add(panel);
      {
        JLabel lblPrnom = new JLabel(EasyGec.getLangages().getText("62", EasyGec.getLang()));
        panel.add(lblPrnom);
      }
      {
        textFieldPrenom = new JTextField();
        panel.add(textFieldPrenom);
        textFieldPrenom.setColumns(17);
      }
    }
    {
      JPanel panel = new JPanel();
      FlowLayout flowLayout = (FlowLayout) panel.getLayout();
      flowLayout.setAlignment(FlowLayout.RIGHT);
      panel.setPreferredSize(new Dimension(220, 30));
      contentPanel.add(panel);
      {
        JLabel lblPuce = new JLabel(EasyGec.getLangages().getText("63", EasyGec.getLang()));
        panel.add(lblPuce);
      }
      {
        textFieldPuce = new JTextField();
        textFieldPuce.setMinimumSize(new Dimension(130, 20));
        textFieldPuce.setPreferredSize(new Dimension(130, 20));
        textFieldPuce.setSize(new Dimension(130, 20));
        panel.add(textFieldPuce);
        textFieldPuce.setColumns(17);
      }
    }
    {
      JPanel panel = new JPanel();
      panel.setBorder(new TitledBorder(null, EasyGec.getLangages().getText("64", EasyGec.getLang()), TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
          btnplus.setToolTipText(EasyGec.getLangages().getText("65", EasyGec.getLang()));
          btnplus.addActionListener(new ActionListener() 
          {
            public void actionPerformed(ActionEvent e) 
            {
              String retour = JOptionPane.showInputDialog(IhmOrienteur.this, EasyGec.getLangages().getText("72", EasyGec.getLang()), EasyGec.getLangages().getText("73", EasyGec.getLang()), JOptionPane.INFORMATION_MESSAGE);
              if(null != retour)
              {
                if(null != IhmOrienteur.this.orienteur)
                {
                  IhmOrienteur.this.orienteur.getDatas().add(retour);
                  list.setListData(IhmOrienteur.this.orienteur.getDatas());
                }
                else
                {
                  o.getDatas().add(retour);
                  list.setListData(o.getDatas());
                }
              }
            }
          });
          btnplus.setPreferredSize(new Dimension(32, 32));
          btnplus.setIcon(new ImageIcon(IhmOrienteur.class.getResource("/icones/plus.png")));
          panel_1.add(btnplus);
        }
        {
          JButton btnModif = new JButton("");
          btnModif.setToolTipText(EasyGec.getLangages().getText("66", EasyGec.getLang()));
          btnModif.addActionListener(new ActionListener() 
          {
            public void actionPerformed(ActionEvent e) 
            {
              if(list.getSelectedIndex()>-1)
              {
                String retour = JOptionPane.showInputDialog(IhmOrienteur.this, EasyGec.getLangages().getText("74", EasyGec.getLang()),  list.getSelectedValue());
                if(null != retour)
                {
                  if(null != IhmOrienteur.this.orienteur)
                  {
                    IhmOrienteur.this.orienteur.getDatas().set(list.getSelectedIndex(), retour);
                    list.setListData(IhmOrienteur.this.orienteur.getDatas());
                  }
                  else
                  {
                    o.getDatas().set(list.getSelectedIndex(), retour);
                    list.setListData(o.getDatas());
                  }
                }
              }
            }
          });
          btnModif.setPreferredSize(new Dimension(32, 32));
          btnModif.setIcon(new ImageIcon(IhmOrienteur.class.getResource("/icones/search.png")));
          panel_1.add(btnModif);
        }
        {
          JButton btnSupp = new JButton("");
          btnSupp.setToolTipText(EasyGec.getLangages().getText("67", EasyGec.getLang()));
          btnSupp.addActionListener(new ActionListener() 
          {
            public void actionPerformed(ActionEvent e) 
            {
              if(list.getSelectedIndex()>-1)
              {
                if(null != IhmOrienteur.this.orienteur)
                {
                  IhmOrienteur.this.orienteur.getDatas().remove(list.getSelectedIndex());
                  list.setListData(IhmOrienteur.this.orienteur.getDatas());
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
          btnSupp.setIcon(new ImageIcon(IhmOrienteur.class.getResource("/icones/delete.png")));
          panel_1.add(btnSupp);
        }
        {
          JButton btnUp = new JButton("");
          btnUp.setToolTipText(EasyGec.getLangages().getText("68", EasyGec.getLang()));
          btnUp.addActionListener(new ActionListener() 
          {
            public void actionPerformed(ActionEvent e) 
            {
              if(list.getSelectedIndex()>-1)
              {
                int index = list.getSelectedIndex();
                if(null != IhmOrienteur.this.orienteur)
                {
                  IhmOrienteur.this.orienteur.upData(list.getSelectedValue(), list.getSelectedIndex());
                  list.setListData(IhmOrienteur.this.orienteur.getDatas());
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
          btnUp.setIcon(new ImageIcon(IhmOrienteur.class.getResource("/icones/up.png")));
          panel_1.add(btnUp);
        }
        {
          JButton btnDonw = new JButton("");
          btnDonw.setToolTipText(EasyGec.getLangages().getText("69", EasyGec.getLang()));
          btnDonw.addActionListener(new ActionListener() 
          {
            public void actionPerformed(ActionEvent e) 
            {
              if(list.getSelectedIndex()>-1)
              {
                int index = list.getSelectedIndex();
                if(null != IhmOrienteur.this.orienteur)
                {
                  IhmOrienteur.this.orienteur.downData(list.getSelectedValue(), list.getSelectedIndex());
                  list.setListData(IhmOrienteur.this.orienteur.getDatas());
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
          btnDonw.setIcon(new ImageIcon(IhmOrienteur.class.getResource("/icones/down.png")));
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
        JButton okButton = new JButton(EasyGec.getLangages().getText("70", EasyGec.getLang()));
        okButton.addActionListener(new ActionListener() 
        {
          public void actionPerformed(ActionEvent e) 
          {
            if(IhmOrienteur.this.orienteur == null)
            {
              
              o.setNom(textFieldNom.getText().trim());
              o.setPrenom(textFieldPrenom.getText().trim());
              o.setIdPuce(textFieldPuce.getText().trim());
              o.setDatas(list.getModel());
              IhmOrienteur.this.easyGec.getOrienteurs().getOrienteurs().add(o);
            }
            else
            {
              IhmOrienteur.this.orienteur.setNom(textFieldNom.getText().trim());
              IhmOrienteur.this.orienteur.setPrenom(textFieldPrenom.getText().trim());
              IhmOrienteur.this.orienteur.setIdPuce(textFieldPuce.getText().trim());
              IhmOrienteur.this.orienteur.setDatas(list.getModel());
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
    }
    
    this.easyGec = easyGec;
    this.orienteur = orienteur;
    initData();
  }

  private void initData()
  {
    if(orienteur == null)
    {
      o = new Orienteur();
      textFieldNom.setText("NOM");
      textFieldPrenom.setText("PRENOM");
      textFieldPuce.setText("123456");
      list.setListData(o.getDatas());
    }
    else
    {
      textFieldNom.setText(orienteur.getNom());
      textFieldPrenom.setText(orienteur.getPrenom());
      textFieldPuce.setText(orienteur.getIdPuce());
      list.setListData(orienteur.getDatas());
    }
  }
}
