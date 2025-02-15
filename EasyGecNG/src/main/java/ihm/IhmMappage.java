package ihm;
import inOut.Config;
import inOut.CsvMappageCourant;

import java.awt.Toolkit;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTable;

import metier.EasyGec;
import outils.DesktopApi;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JComboBox;

import to.MappagesAbc;


public class IhmMappage extends javax.swing.JDialog
{
  /**
   * 
   */
  private static final long serialVersionUID = 6626751292697067849L;
  private EasyGec easyGec;
  private JTable table;
  private JComboBox<MappagesAbc> comboBoxMappages;
  
  
  public IhmMappage(EasyGec easygec) 
  {
    setTitle("ABC");
    setModal(true);
    setSize(new Dimension(174, 500));
    setPreferredSize(new Dimension(150, 500));
    setResizable(false);
    this.easyGec = easygec;
    setIconImage(Toolkit.getDefaultToolkit().getImage(IhmMappage.class.getResource("/icones/abc128.png")));

    table = new JTable(225, 2)
    {
      /**
       * 
       */
      private static final long serialVersionUID = -6034492198900885978L;

      @Override
      public boolean isCellEditable(int row, int column) 
      {
          return column == 0 || row <= 2 || row == 224 ? false : true;
      }
      
    };
    
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setViewportView(table);
    getContentPane().add(scrollPane, BorderLayout.CENTER);
    
    JPanel panel = new JPanel();
    getContentPane().add(panel, BorderLayout.SOUTH);
    
    JButton btnOk = new JButton("");
    btnOk.setToolTipText(EasyGec.getLangages().getText("70", EasyGec.getLang()));
    btnOk.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        if(easyGec.getMappages().size()>0)
        {
          table.editCellAt(0, 1);
        }
        for(int i=0; i<easyGec.getMappagesCourant().getMappages().size(); i++)
        {
          if(((String) table.getValueAt(i, 1)).trim().compareTo("") != 0)
          {
            easyGec.getMappagesCourant().getMappages().get(i).setMap(((String) table.getValueAt(i, 1)).trim().substring(0, 1).toLowerCase());
          }
          else
          {
            easyGec.getMappagesCourant().getMappages().get(i).setMap("");
          }
        }
        Config.enregistre(easyGec, "config.xml");
        dispose();
      }
    });
    panel.add(btnOk);
    btnOk.setPreferredSize(new Dimension(50, 50));
    btnOk.setIcon(new ImageIcon(IhmMappage.class.getResource("/icones/ok64.png")));
    
    JButton btnNewButton = new JButton("");
    btnNewButton.setToolTipText(EasyGec.getLangages().getText("71", EasyGec.getLang()));
    btnNewButton.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        dispose();
      }
    });
    btnNewButton.setIcon(new ImageIcon(IhmMappage.class.getResource("/icones/back.png")));
    btnNewButton.setPreferredSize(new Dimension(50, 50));
    panel.add(btnNewButton);
    
    JButton btnPrint = new JButton("");
    btnPrint.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        CsvMappageCourant.exporter(easyGec, easyGec.getMappagesCourant().getNom() + ".csv");
        
        try
        {
          // Lancement du fichier d'aide de l'application
          String adresse = new File(".").getCanonicalPath().toString();
          adresse = adresse + "/" + easyGec.getMappagesCourant().getNom() + ".csv";
          DesktopApi.edit(new File(adresse));
        }
        catch (IOException e1)
        {
          JOptionPane.showMessageDialog(null,"Erreur de lancement du fichier : "+e1.getClass().getName());
        }
      }
    });
    btnPrint.setPreferredSize(new Dimension(50, 50));
    btnPrint.setIcon(new ImageIcon(IhmMappage.class.getResource("/icones/print.png")));
    panel.add(btnPrint);
    
    JPanel panel_1 = new JPanel();
    panel_1.setPreferredSize(new Dimension(10, 70));
    getContentPane().add(panel_1, BorderLayout.NORTH);
    
    comboBoxMappages = new JComboBox<MappagesAbc>();
    comboBoxMappages.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        easyGec.setMappagesCourant((MappagesAbc) comboBoxMappages.getSelectedItem());
        remplirTable();
      }
    });
    comboBoxMappages.setPreferredSize(new Dimension(150, 20));
    panel_1.add(comboBoxMappages);
    
    JButton btnPlus = new JButton("");
    btnPlus.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        String retour = JOptionPane.showInputDialog(IhmMappage.this, EasyGec.getLangages().getText("61", EasyGec.getLang()) ,
            EasyGec.getLangages().getText("76", EasyGec.getLang()), JOptionPane.OK_CANCEL_OPTION);
        if(retour != null)
        {
          MappagesAbc mappages = new MappagesAbc();
          mappages.setNom(retour);
          mappages.setMappagesVide();
          easyGec.getMappages().add(mappages);
          easyGec.setMappagesCourant(mappages);
          comboBoxMappages.setModel(new DefaultComboBoxModel<>(easyGec.getMappages()));
          comboBoxMappages.setSelectedItem(easyGec.getMappagesCourant());
          remplirTable();
        }
      }
    });
    btnPlus.setPreferredSize(new Dimension(33, 33));
    btnPlus.setIcon(new ImageIcon(IhmMappage.class.getResource("/icones/plus.png")));
    panel_1.add(btnPlus);
    
    JButton btnModif = new JButton("");
    btnModif.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        String retour = JOptionPane.showInputDialog(IhmMappage.this, EasyGec.getLangages().getText("61", EasyGec.getLang()) ,
            ((MappagesAbc) (comboBoxMappages.getSelectedItem())).getNom());
        if(retour != null)
        {
          ((MappagesAbc) (comboBoxMappages.getSelectedItem())).setNom(retour);
          comboBoxMappages.setModel(new DefaultComboBoxModel<>(easyGec.getMappages()));
          comboBoxMappages.setSelectedItem(easyGec.getMappagesCourant());
          remplirTable();
        }
      }
    });
    btnModif.setPreferredSize(new Dimension(33, 33));
    btnModif.setIcon(new ImageIcon(IhmMappage.class.getResource("/icones/search.png")));
    panel_1.add(btnModif);
    
    JButton btnSupp = new JButton("");
    btnSupp.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        if(comboBoxMappages.getSelectedIndex()>-1)
        {
          int retour = JOptionPane.showConfirmDialog(IhmMappage.this, EasyGec.getLangages().getText("119", EasyGec.getLang()) + " : " + ((MappagesAbc) (comboBoxMappages.getSelectedItem())).getNom(),
              EasyGec.getLangages().getText("119", EasyGec.getLang()), JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
          if(retour == 0)
          {
            easyGec.getMappages().remove(((MappagesAbc) (comboBoxMappages.getSelectedItem())));
            comboBoxMappages.setModel(new DefaultComboBoxModel<>(easyGec.getMappages()));
            if(easyGec.getMappages().size()>0)
            {
              comboBoxMappages.setSelectedIndex(0);
            }
          }
        }
      }
    });
    btnSupp.setPreferredSize(new Dimension(33, 33));
    btnSupp.setIcon(new ImageIcon(IhmMappage.class.getResource("/icones/delete.png")));
    panel_1.add(btnSupp);
    

    comboBoxMappages.setModel(new DefaultComboBoxModel<>(easyGec.getMappages()));
    comboBoxMappages.setSelectedItem(easygec.getMappagesCourant());
    initialiser();
  }
  
  private void initialiser()
  {
    
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment( JLabel.CENTER );
    table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
    table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
    table.getTableHeader().getColumnModel().getColumn(0).setHeaderValue(EasyGec.getLangages().getText("6", EasyGec.getLang()));
    table.getTableHeader().getColumnModel().getColumn(1).setHeaderValue(EasyGec.getLangages().getText("64", EasyGec.getLang()));
    
    remplirTable();
  }
  
  private void remplirTable()
  {
    table.setValueAt("<html><b><font color='red'>31</font></b></html>", 0, 0);
    table.setValueAt("<html><b><font color='red'>\u00D0</font></b></html>", 0, 1);
    table.setValueAt("<html><b><font color='red'>32</font></b></html>", 1, 0);
    table.setValueAt("<html><b><font color='red'>\u00D1</font></b></html>", 1, 1);
    table.setValueAt("<html><b><font color='red'>33</font></b></html>", 2, 0);
    table.setValueAt("<html><b><font color='red'>_</font></b></html>", 2, 1);
    for(int i=3; i<easyGec.getMappagesCourant().getMappages().size(); i++)
    {
      table.setValueAt(31 + i, i, 0);
      table.setValueAt(easyGec.getMappagesCourant().getMappages().get(i).getMap(), i, 1);
    }
  }

}
