package ui;

import io.CsvOrienteers;
import io.CsvSiLog;

import javax.swing.JDialog;
import java.awt.Toolkit;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import core.EasyGec;

import utils.FileExtensionFilter;

import to.Orienteer;
import to.SiResult;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class UiImportSi extends JDialog
{

  /**
   * 
   */
  private static final long serialVersionUID = 6671715455058829726L;
  private UiEasyGec ihm;
  private JLabel lblFichierOrienteers;
  private JLabel lblFichierLogSi;
  private JList<SiResult> listSiResults;
  private JList<Orienteer> listOrienteers;

  /**
   * Create the dialog.
   */
  public UiImportSi(final UiEasyGec ihm)
  {
    setMaximumSize(new Dimension(400, 2147483647));
    setMinimumSize(new Dimension(400, 300));
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    setModal(true);
    setTitle(EasyGec.getLanguages().getText("47", EasyGec.getLang()));
    setIconImage(Toolkit.getDefaultToolkit().getImage(UiImportSi.class.getResource("/icons/easy.png")));
    setBounds(100, 100, 400, 492);
    getContentPane().setLayout(new BorderLayout(0, 0));
    
    JPanel panelOrienteers = new JPanel();
    panelOrienteers.setPreferredSize(new Dimension(10, 200));
    panelOrienteers.setBorder(new TitledBorder(null, EasyGec.getLanguages().getText("48", EasyGec.getLang()), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    getContentPane().add(panelOrienteers, BorderLayout.NORTH);
    panelOrienteers.setLayout(new BorderLayout(0, 0));
    
    JPanel panel_1 = new JPanel();
    FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
    flowLayout.setAlignment(FlowLayout.LEFT);
    panelOrienteers.add(panel_1, BorderLayout.NORTH);
    
    final JButton btnImportOrienteers = new JButton("");
    panel_1.add(btnImportOrienteers);
    btnImportOrienteers.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        // On ouvre le navigateur
        JFileChooser chooser = new JFileChooser();
        FileExtensionFilter filter = new FileExtensionFilter();
        filter.addExtension("csv");
        filter.setDescription("Fichiers csv");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(ihm.easyGec.getRepertoire()));
        
        int returnVal = chooser.showOpenDialog(UiImportSi.this);
        // Si un fichier a été choisi
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
          UiImportSi.this.ihm.easyGec.getOrienteersSi().getOrienteers().clear();
          CsvOrienteers.importerSi(UiImportSi.this.ihm.easyGec, chooser.getSelectedFile().getAbsolutePath());
          listOrienteers.setListData(UiImportSi.this.ihm.easyGec.getOrienteersSi().getOrienteers());
          lblFichierOrienteers.setText(chooser.getSelectedFile().getAbsolutePath());
        }
      }
    });
    btnImportOrienteers.setIcon(new ImageIcon(UiImportSi.class.getResource("/icons/runner.png")));
    btnImportOrienteers.setToolTipText(EasyGec.getLanguages().getText("51", EasyGec.getLang()));
    btnImportOrienteers.setPreferredSize(new Dimension(32, 32));
    
    lblFichierOrienteers = new JLabel("");
    panel_1.add(lblFichierOrienteers);
    lblFichierOrienteers.setPreferredSize(new Dimension(300, 30));
    
    listOrienteers = new JList<Orienteer>();
    listOrienteers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listOrienteers.setCellRenderer(new OrienteerListCellRenderer());
    
    JScrollPane scrollPane_1 = new JScrollPane();
    scrollPane_1.setViewportView(listOrienteers);
    panelOrienteers.add(scrollPane_1, BorderLayout.CENTER);
    
    JPanel panel_2 = new JPanel();
    panel_2.setPreferredSize(new Dimension(36, 10));
    panelOrienteers.add(panel_2, BorderLayout.EAST);
    
    JButton button = new JButton("");
    button.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        UiImportSi.this.ihm.easyGec.getOrienteersSi().setDebut(listOrienteers.getSelectedValue());
        listOrienteers.setListData(UiImportSi.this.ihm.easyGec.getOrienteersSi().getOrienteers());
      }
    });
    button.setIcon(new ImageIcon(UiImportSi.class.getResource("/icons/start.png")));
    button.setToolTipText(EasyGec.getLanguages().getText("53", EasyGec.getLang()));
    button.setPreferredSize(new Dimension(32, 32));
    panel_2.add(button);
    
    JButton button_1 = new JButton("");
    button_1.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        UiImportSi.this.ihm.easyGec.getOrienteersSi().setFin(listOrienteers.getSelectedValue());
        listOrienteers.setListData(UiImportSi.this.ihm.easyGec.getOrienteersSi().getOrienteers());
      }
    });
    button_1.setIcon(new ImageIcon(UiImportSi.class.getResource("/icons/end.png")));
    button_1.setToolTipText(EasyGec.getLanguages().getText("54", EasyGec.getLang()));
    button_1.setPreferredSize(new Dimension(32, 32));
    panel_2.add(button_1);
    
    JPanel panelLogSi = new JPanel();
    panelLogSi.setBorder(new TitledBorder(null, EasyGec.getLanguages().getText("49", EasyGec.getLang()), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    getContentPane().add(panelLogSi, BorderLayout.CENTER);
    panelLogSi.setLayout(new BorderLayout(0, 0));
    
    JPanel panelFichierLogSi = new JPanel();
    FlowLayout flowLayout_1 = (FlowLayout) panelFichierLogSi.getLayout();
    flowLayout_1.setAlignment(FlowLayout.LEFT);
    panelLogSi.add(panelFichierLogSi, BorderLayout.NORTH);
    
    JButton btnImporterSi = new JButton("");
    btnImporterSi.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        // On ouvre le navigateur
        JFileChooser chooser = new JFileChooser();
        FileExtensionFilter filter = new FileExtensionFilter();
        filter.addExtension("csv");
        filter.setDescription("Fichiers csv");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(ihm.easyGec.getRepertoire()));
        
        int returnVal = chooser.showOpenDialog(UiImportSi.this);
        // Si un fichier a été choisi
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
          lblFichierLogSi.setText(chooser.getSelectedFile().getAbsolutePath());
          UiImportSi.this.ihm.easyGec.getSiResults().getSiResults().clear();
          CsvSiLog.importer(UiImportSi.this.ihm.easyGec, chooser.getSelectedFile().getAbsolutePath());
          listSiResults.setListData(UiImportSi.this.ihm.easyGec.getSiResults().getSiResults());
        }
      }
    });
    btnImporterSi.setToolTipText(EasyGec.getLanguages().getText("52", EasyGec.getLang()));
    btnImporterSi.setPreferredSize(new Dimension(32, 32));
    btnImporterSi.setIcon(new ImageIcon(UiImportSi.class.getResource("/icons/si.png")));
    panelFichierLogSi.add(btnImporterSi);
    
    lblFichierLogSi = new JLabel("");
    lblFichierLogSi.setPreferredSize(new Dimension(300, 30));
    panelFichierLogSi.add(lblFichierLogSi);
    
    listSiResults = new JList<SiResult>();
    listSiResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listSiResults.setCellRenderer(new SiResultListCellRenderer());
    
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setViewportView(listSiResults);
    panelLogSi.add(scrollPane, BorderLayout.CENTER);
    
    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(36, 10));
    panelLogSi.add(panel, BorderLayout.EAST);
    
    JButton btnDebut = new JButton("");
    btnDebut.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(listSiResults.getSelectedIndex()>-1)
        {
          UiImportSi.this.ihm.easyGec.getSiResults().setDebut(listSiResults.getSelectedValue());
          listSiResults.setListData(UiImportSi.this.ihm.easyGec.getSiResults().getSiResults());
        }
      }
    });
    btnDebut.setIcon(new ImageIcon(UiImportSi.class.getResource("/icons/start.png")));
    btnDebut.setPreferredSize(new Dimension(32, 32));
    btnDebut.setToolTipText(EasyGec.getLanguages().getText("53", EasyGec.getLang()));
    panel.add(btnDebut);
    
    JButton btnFin = new JButton("");
    btnFin.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(listSiResults.getSelectedIndex()>-1)
        {
          UiImportSi.this.ihm.easyGec.getSiResults().setFin(listSiResults.getSelectedValue());
          listSiResults.setListData(UiImportSi.this.ihm.easyGec.getSiResults().getSiResults());
        }
      }
    });
    btnFin.setToolTipText(EasyGec.getLanguages().getText("54", EasyGec.getLang()));
    btnFin.setPreferredSize(new Dimension(32, 32));
    btnFin.setIcon(new ImageIcon(UiImportSi.class.getResource("/icons/end.png")));
    panel.add(btnFin);
    
    JPanel panelBouton = new JPanel();
    getContentPane().add(panelBouton, BorderLayout.SOUTH);
    
    JButton btnOK = new JButton(EasyGec.getLanguages().getText("50", EasyGec.getLang()));
    btnOK.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(UiImportSi.this.ihm.easyGec.getSiResults().getSiResults().size()>0 && UiImportSi.this.ihm.easyGec.getOrienteersSi().getOrienteers().size()>0)
        {
          UiImportSi.this.ihm.easyGec.getSiResults().simplifier();
          UiImportSi.this.ihm.easyGec.getOrienteersSi().simplifier();
          UiImportSi.this.ihm.easyGec.affecterSiResultsOrienteers();
          dispose();
        }
        else
        {
          JOptionPane.showMessageDialog(UiImportSi.this, EasyGec.getLanguages().getText("55", EasyGec.getLang()), EasyGec.getLanguages().getText("56", EasyGec.getLang()), JOptionPane.INFORMATION_MESSAGE);
        }
      }
    });
    panelBouton.add(btnOK);

    this.ihm = ihm;
  }

}
