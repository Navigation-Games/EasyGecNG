package ui;
import io.Config;
import io.CsvCurrentMapping;

import java.awt.Toolkit;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTable;

import core.EasyGec;
import utils.DesktopApi;

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

import to.AbcMappings;


public class UiMapping extends javax.swing.JDialog
{
  /**
   * 
   */
  private static final long serialVersionUID = 6626751292697067849L;
  private EasyGec easyGec;
  private JTable table;
  private JComboBox<AbcMappings> comboBoxMappings;
  
  
  public UiMapping(EasyGec easygec) 
  {
    setTitle("ABC");
    setModal(true);
    setSize(new Dimension(174, 500));
    setPreferredSize(new Dimension(150, 500));
    setResizable(false);
    this.easyGec = easygec;
    setIconImage(Toolkit.getDefaultToolkit().getImage(UiMapping.class.getResource("/icons/abc128.png")));

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
    btnOk.setToolTipText(EasyGec.getLanguages().getText("70", EasyGec.getLang()));
    btnOk.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        if(easyGec.getMappings().size()>0)
        {
          table.editCellAt(0, 1);
        }
        for(int i=0; i<easyGec.getMappingsCourant().getMappings().size(); i++)
        {
          if(((String) table.getValueAt(i, 1)).trim().compareTo("") != 0)
          {
            easyGec.getMappingsCourant().getMappings().get(i).setMap(((String) table.getValueAt(i, 1)).trim().substring(0, 1).toLowerCase());
          }
          else
          {
            easyGec.getMappingsCourant().getMappings().get(i).setMap("");
          }
        }
        Config.enregistre(easyGec, "config.xml");
        dispose();
      }
    });
    panel.add(btnOk);
    btnOk.setPreferredSize(new Dimension(50, 50));
    btnOk.setIcon(new ImageIcon(UiMapping.class.getResource("/icons/ok64.png")));
    
    JButton btnNewButton = new JButton("");
    btnNewButton.setToolTipText(EasyGec.getLanguages().getText("71", EasyGec.getLang()));
    btnNewButton.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        dispose();
      }
    });
    btnNewButton.setIcon(new ImageIcon(UiMapping.class.getResource("/icons/back.png")));
    btnNewButton.setPreferredSize(new Dimension(50, 50));
    panel.add(btnNewButton);
    
    JButton btnPrint = new JButton("");
    btnPrint.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        CsvCurrentMapping.exporter(easyGec, easyGec.getMappingsCourant().getName() + ".csv");
        
        try
        {
          // Lancement du fichier d'aide de l'application
          String adresse = new File(".").getCanonicalPath().toString();
          adresse = adresse + "/" + easyGec.getMappingsCourant().getName() + ".csv";
          DesktopApi.edit(new File(adresse));
        }
        catch (IOException e1)
        {
          JOptionPane.showMessageDialog(null,"Erreur de lancement du fichier : "+e1.getClass().getName());
        }
      }
    });
    btnPrint.setPreferredSize(new Dimension(50, 50));
    btnPrint.setIcon(new ImageIcon(UiMapping.class.getResource("/icons/print.png")));
    panel.add(btnPrint);
    
    JPanel panel_1 = new JPanel();
    panel_1.setPreferredSize(new Dimension(10, 70));
    getContentPane().add(panel_1, BorderLayout.NORTH);
    
    comboBoxMappings = new JComboBox<AbcMappings>();
    comboBoxMappings.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        easyGec.setMappingsCourant((AbcMappings) comboBoxMappings.getSelectedItem());
        remplirTable();
      }
    });
    comboBoxMappings.setPreferredSize(new Dimension(150, 20));
    panel_1.add(comboBoxMappings);
    
    JButton btnPlus = new JButton("");
    btnPlus.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        String resultValue = JOptionPane.showInputDialog(UiMapping.this, EasyGec.getLanguages().getText("61", EasyGec.getLang()) ,
            EasyGec.getLanguages().getText("76", EasyGec.getLang()), JOptionPane.OK_CANCEL_OPTION);
        if(resultValue != null)
        {
          AbcMappings mappings = new AbcMappings();
          mappings.setName(resultValue);
          mappings.setMappingsVide();
          easyGec.getMappings().add(mappings);
          easyGec.setMappingsCourant(mappings);
          comboBoxMappings.setModel(new DefaultComboBoxModel<>(easyGec.getMappings()));
          comboBoxMappings.setSelectedItem(easyGec.getMappingsCourant());
          remplirTable();
        }
      }
    });
    btnPlus.setPreferredSize(new Dimension(33, 33));
    btnPlus.setIcon(new ImageIcon(UiMapping.class.getResource("/icons/plus.png")));
    panel_1.add(btnPlus);
    
    JButton btnModif = new JButton("");
    btnModif.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        String resultValue = JOptionPane.showInputDialog(UiMapping.this, EasyGec.getLanguages().getText("61", EasyGec.getLang()) ,
            ((AbcMappings) (comboBoxMappings.getSelectedItem())).getName());
        if(resultValue != null)
        {
          ((AbcMappings) (comboBoxMappings.getSelectedItem())).setName(resultValue);
          comboBoxMappings.setModel(new DefaultComboBoxModel<>(easyGec.getMappings()));
          comboBoxMappings.setSelectedItem(easyGec.getMappingsCourant());
          remplirTable();
        }
      }
    });
    btnModif.setPreferredSize(new Dimension(33, 33));
    btnModif.setIcon(new ImageIcon(UiMapping.class.getResource("/icons/search.png")));
    panel_1.add(btnModif);
    
    JButton btnSupp = new JButton("");
    btnSupp.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        if(comboBoxMappings.getSelectedIndex()>-1)
        {
          int resultValue = JOptionPane.showConfirmDialog(UiMapping.this, EasyGec.getLanguages().getText("119", EasyGec.getLang()) + " : " + ((AbcMappings) (comboBoxMappings.getSelectedItem())).getName(),
              EasyGec.getLanguages().getText("119", EasyGec.getLang()), JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
          if(resultValue == 0)
          {
            easyGec.getMappings().remove(((AbcMappings) (comboBoxMappings.getSelectedItem())));
            comboBoxMappings.setModel(new DefaultComboBoxModel<>(easyGec.getMappings()));
            if(easyGec.getMappings().size()>0)
            {
              comboBoxMappings.setSelectedIndex(0);
            }
          }
        }
      }
    });
    btnSupp.setPreferredSize(new Dimension(33, 33));
    btnSupp.setIcon(new ImageIcon(UiMapping.class.getResource("/icons/delete.png")));
    panel_1.add(btnSupp);
    

    comboBoxMappings.setModel(new DefaultComboBoxModel<>(easyGec.getMappings()));
    comboBoxMappings.setSelectedItem(easygec.getMappingsCourant());
    initialiser();
  }
  
  private void initialiser()
  {
    
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment( JLabel.CENTER );
    table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
    table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
    table.getTableHeader().getColumnModel().getColumn(0).setHeaderValue(EasyGec.getLanguages().getText("6", EasyGec.getLang()));
    table.getTableHeader().getColumnModel().getColumn(1).setHeaderValue(EasyGec.getLanguages().getText("64", EasyGec.getLang()));
    
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
    for(int i=3; i<easyGec.getMappingsCourant().getMappings().size(); i++)
    {
      table.setValueAt(31 + i, i, 0);
      table.setValueAt(easyGec.getMappingsCourant().getMappings().get(i).getMap(), i, 1);
    }
  }

}
