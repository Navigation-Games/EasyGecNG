package ihm;

import inOut.CsvOrienteurs;
import inOut.CsvSiLog;

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

import metier.EasyGec;

import outils.FiltreFichier;

import to.Orienteur;
import to.ResultatSi;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class IhmImportSI extends JDialog
{

  /**
   * 
   */
  private static final long serialVersionUID = 6671715455058829726L;
  private IhmEasyGec ihm;
  private JLabel lblFichierOrienteurs;
  private JLabel lblFichierLogSi;
  private JList<ResultatSi> listResultatsSi;
  private JList<Orienteur> listOrienteurs;

  /**
   * Create the dialog.
   */
  public IhmImportSI(final IhmEasyGec ihm)
  {
    setMaximumSize(new Dimension(400, 2147483647));
    setMinimumSize(new Dimension(400, 300));
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    setModal(true);
    setTitle(EasyGec.getLangages().getText("47", EasyGec.getLang()));
    setIconImage(Toolkit.getDefaultToolkit().getImage(IhmImportSI.class.getResource("/icones/easy.png")));
    setBounds(100, 100, 400, 492);
    getContentPane().setLayout(new BorderLayout(0, 0));
    
    JPanel panelOrienteurs = new JPanel();
    panelOrienteurs.setPreferredSize(new Dimension(10, 200));
    panelOrienteurs.setBorder(new TitledBorder(null, EasyGec.getLangages().getText("48", EasyGec.getLang()), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    getContentPane().add(panelOrienteurs, BorderLayout.NORTH);
    panelOrienteurs.setLayout(new BorderLayout(0, 0));
    
    JPanel panel_1 = new JPanel();
    FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
    flowLayout.setAlignment(FlowLayout.LEFT);
    panelOrienteurs.add(panel_1, BorderLayout.NORTH);
    
    final JButton btnImportOrienteurs = new JButton("");
    panel_1.add(btnImportOrienteurs);
    btnImportOrienteurs.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        // On ouvre le navigateur
        JFileChooser chooser = new JFileChooser();
        FiltreFichier filter = new FiltreFichier();
        filter.addExtension("csv");
        filter.setDescription("Fichiers csv");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(ihm.easyGec.getRepertoire()));
        
        int returnVal = chooser.showOpenDialog(IhmImportSI.this);
        // Si un fichier a été choisi
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
          IhmImportSI.this.ihm.easyGec.getOrienteursSi().getOrienteurs().clear();
          CsvOrienteurs.importerSi(IhmImportSI.this.ihm.easyGec, chooser.getSelectedFile().getAbsolutePath());
          listOrienteurs.setListData(IhmImportSI.this.ihm.easyGec.getOrienteursSi().getOrienteurs());
          lblFichierOrienteurs.setText(chooser.getSelectedFile().getAbsolutePath());
        }
      }
    });
    btnImportOrienteurs.setIcon(new ImageIcon(IhmImportSI.class.getResource("/icones/runner.png")));
    btnImportOrienteurs.setToolTipText(EasyGec.getLangages().getText("51", EasyGec.getLang()));
    btnImportOrienteurs.setPreferredSize(new Dimension(32, 32));
    
    lblFichierOrienteurs = new JLabel("");
    panel_1.add(lblFichierOrienteurs);
    lblFichierOrienteurs.setPreferredSize(new Dimension(300, 30));
    
    listOrienteurs = new JList<Orienteur>();
    listOrienteurs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listOrienteurs.setCellRenderer(new OrienteurListCellRenderer());
    
    JScrollPane scrollPane_1 = new JScrollPane();
    scrollPane_1.setViewportView(listOrienteurs);
    panelOrienteurs.add(scrollPane_1, BorderLayout.CENTER);
    
    JPanel panel_2 = new JPanel();
    panel_2.setPreferredSize(new Dimension(36, 10));
    panelOrienteurs.add(panel_2, BorderLayout.EAST);
    
    JButton button = new JButton("");
    button.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        IhmImportSI.this.ihm.easyGec.getOrienteursSi().setDebut(listOrienteurs.getSelectedValue());
        listOrienteurs.setListData(IhmImportSI.this.ihm.easyGec.getOrienteursSi().getOrienteurs());
      }
    });
    button.setIcon(new ImageIcon(IhmImportSI.class.getResource("/icones/debut.png")));
    button.setToolTipText(EasyGec.getLangages().getText("53", EasyGec.getLang()));
    button.setPreferredSize(new Dimension(32, 32));
    panel_2.add(button);
    
    JButton button_1 = new JButton("");
    button_1.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        IhmImportSI.this.ihm.easyGec.getOrienteursSi().setFin(listOrienteurs.getSelectedValue());
        listOrienteurs.setListData(IhmImportSI.this.ihm.easyGec.getOrienteursSi().getOrienteurs());
      }
    });
    button_1.setIcon(new ImageIcon(IhmImportSI.class.getResource("/icones/end.png")));
    button_1.setToolTipText(EasyGec.getLangages().getText("54", EasyGec.getLang()));
    button_1.setPreferredSize(new Dimension(32, 32));
    panel_2.add(button_1);
    
    JPanel panelLogSi = new JPanel();
    panelLogSi.setBorder(new TitledBorder(null, EasyGec.getLangages().getText("49", EasyGec.getLang()), TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
        FiltreFichier filter = new FiltreFichier();
        filter.addExtension("csv");
        filter.setDescription("Fichiers csv");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(ihm.easyGec.getRepertoire()));
        
        int returnVal = chooser.showOpenDialog(IhmImportSI.this);
        // Si un fichier a été choisi
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
          lblFichierLogSi.setText(chooser.getSelectedFile().getAbsolutePath());
          IhmImportSI.this.ihm.easyGec.getResultatsSi().getResultatsSi().clear();
          CsvSiLog.importer(IhmImportSI.this.ihm.easyGec, chooser.getSelectedFile().getAbsolutePath());
          listResultatsSi.setListData(IhmImportSI.this.ihm.easyGec.getResultatsSi().getResultatsSi());
        }
      }
    });
    btnImporterSi.setToolTipText(EasyGec.getLangages().getText("52", EasyGec.getLang()));
    btnImporterSi.setPreferredSize(new Dimension(32, 32));
    btnImporterSi.setIcon(new ImageIcon(IhmImportSI.class.getResource("/icones/si.png")));
    panelFichierLogSi.add(btnImporterSi);
    
    lblFichierLogSi = new JLabel("");
    lblFichierLogSi.setPreferredSize(new Dimension(300, 30));
    panelFichierLogSi.add(lblFichierLogSi);
    
    listResultatsSi = new JList<ResultatSi>();
    listResultatsSi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listResultatsSi.setCellRenderer(new ResultatSiListCellRenderer());
    
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setViewportView(listResultatsSi);
    panelLogSi.add(scrollPane, BorderLayout.CENTER);
    
    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(36, 10));
    panelLogSi.add(panel, BorderLayout.EAST);
    
    JButton btnDebut = new JButton("");
    btnDebut.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(listResultatsSi.getSelectedIndex()>-1)
        {
          IhmImportSI.this.ihm.easyGec.getResultatsSi().setDebut(listResultatsSi.getSelectedValue());
          listResultatsSi.setListData(IhmImportSI.this.ihm.easyGec.getResultatsSi().getResultatsSi());
        }
      }
    });
    btnDebut.setIcon(new ImageIcon(IhmImportSI.class.getResource("/icones/debut.png")));
    btnDebut.setPreferredSize(new Dimension(32, 32));
    btnDebut.setToolTipText(EasyGec.getLangages().getText("53", EasyGec.getLang()));
    panel.add(btnDebut);
    
    JButton btnFin = new JButton("");
    btnFin.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(listResultatsSi.getSelectedIndex()>-1)
        {
          IhmImportSI.this.ihm.easyGec.getResultatsSi().setFin(listResultatsSi.getSelectedValue());
          listResultatsSi.setListData(IhmImportSI.this.ihm.easyGec.getResultatsSi().getResultatsSi());
        }
      }
    });
    btnFin.setToolTipText(EasyGec.getLangages().getText("54", EasyGec.getLang()));
    btnFin.setPreferredSize(new Dimension(32, 32));
    btnFin.setIcon(new ImageIcon(IhmImportSI.class.getResource("/icones/end.png")));
    panel.add(btnFin);
    
    JPanel panelBouton = new JPanel();
    getContentPane().add(panelBouton, BorderLayout.SOUTH);
    
    JButton btnOK = new JButton(EasyGec.getLangages().getText("50", EasyGec.getLang()));
    btnOK.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(IhmImportSI.this.ihm.easyGec.getResultatsSi().getResultatsSi().size()>0 && IhmImportSI.this.ihm.easyGec.getOrienteursSi().getOrienteurs().size()>0)
        {
          IhmImportSI.this.ihm.easyGec.getResultatsSi().simplifier();
          IhmImportSI.this.ihm.easyGec.getOrienteursSi().simplifier();
          IhmImportSI.this.ihm.easyGec.affecterResultatsSiOrienteurs();
          dispose();
        }
        else
        {
          JOptionPane.showMessageDialog(IhmImportSI.this, EasyGec.getLangages().getText("55", EasyGec.getLang()), EasyGec.getLangages().getText("56", EasyGec.getLang()), JOptionPane.INFORMATION_MESSAGE);
        }
      }
    });
    panelBouton.add(btnOK);

    this.ihm = ihm;
  }

}
