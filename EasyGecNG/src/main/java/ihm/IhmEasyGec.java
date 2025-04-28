package ihm;

import inOut.Config;
import inOut.CsvCircuits;
import inOut.CsvOrienteurs;
import inOut.CsvResultats;
import inOut.CsvResultatsDetailles;
import inOut.HtmlResultatDetaille;
import inOut.HtmlResultatGlobal;
import inOut.XmlLangage;
import inOut.XmlOcadCircuit;
import inOut.XmlOrganisation;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;

import to.Circuit;
import to.Orienteur;
import to.ResultatPuceComparator;

import javax.swing.ListSelectionModel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import metier.EasyGec;

import outils.CommunicationRail;
import outils.FiltreFichier;
import outils.Outils;
import outils.TaskBoutonOkNok;
import outils.TimeManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Color;


public class IhmEasyGec extends JFrame
{

  /**
   * 
   */
  private static final long serialVersionUID = -4824746389076523212L;
  public EasyGec easyGec = null;
  public boolean autoResult = false;
  private Integer[] codes = new Integer[225];
  private Integer[] tempos = {2, 3, 4, 5, 10, 15, 20};
  private JPanel contentPane;
  private JList<Circuit> listCircuits;
  public JList<Integer> listCodes;
  private CommunicationRail cr = new CommunicationRail();
  private boolean siReaderEnCours = false;
  
  private JRadioButton rdbtnCourseEnLigne;
  private JRadioButton rdbtnCourseAuScore;
  private JRadioButton rdbtnDpartAuBoitier;
  private JRadioButton rdbtnDpartGroup;
  private JSpinner spinnerHeures;
  private JSpinner spinnerMinutes;
  private JSpinner spinnerSecondes;
  public JButton btnEnregistrer;
  private JButton btnLancerLecture;
  private JButton btnExportHtml;
  private JButton btnExportPartiels;
  private JSpinner spinner;
  private JSpinner spinner_1;
  private JButton btnModifier;
  private JButton buttonOrienteurModif;
  private JPanel panel_1;
  public JPanel panel_2;
  private JPanel panel_3;
  private JPanel panel_4;
  private JPanel panel_5;
  private JPanel panel_6;
  private JPanel panel_7;
  private JPanel panel_8;
  private JPanel panel_9;
  public JPanel panelType;
  private JPanel panelOrienteurs;
  private JPanel panelFichier;
  private JPanel panel;
  private JPanel panelLecturePuce;
  private JPanel panelCentre;
  private JPanel panelCodes;
  private JPanel panelDepart;
  private JLabel lblPortCom;
  private JLabel label;
  private JButton btnNouveau;
  private JButton btnOuvrir;
  public JButton btnImportOcad;
  public JButton btnSI;
  private JButton btnAide;
  private JButton btnInfo;
  private final JButton btnMode;
  private JButton btnLang;
  private JButton btnInitCom;
  private JButton buttonChargerOrienteurs;
  private JButton buttonOrienteurPlus;
  private JButton buttonOrienteurMoins;
  private JButton btnCircuitPlus;
  private final JButton btnCircuitModifier;
  private JButton btnCircuitSupprimer;
  private JButton btnCodePlus;
  private JButton btnCodeSupprimer;
  private JButton btnCodeMonter;
  private JButton btnNewButton;
  private JButton btnConfig;
  public JButton btnAbc;
  public JButton btnImportCircuitAbc;
  private JButton btnResultatsGlobaux;
  private JButton btnResultatsDetailled;
  private JButton btnEffacerResultats;
  private JButton buttonUnss;

  /**
   * Launch the application.
   */
  public static void main(String[] args)
  {

    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        // On r�cup�re le Look courant et on l'applique � notre application
        try{ UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() ); }
        catch( Exception e ) {}
        
        try
        {
          IhmEasyGec frame = new IhmEasyGec();
          frame.setVisible(true);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame.
   */
  public IhmEasyGec()
  {
    setPreferredSize(new Dimension(560, 570));
    setMinimumSize(new Dimension(650, 570));
    setMaximumSize(new Dimension(570, 1000));
    addWindowListener(new WindowAdapter() 
    {
      @Override
      public void windowClosing(WindowEvent arg0) 
      {
        Enregistrer();
        System.exit(0);
      }
    });
    setTitle("EasyGec");
    setIconImage(Toolkit.getDefaultToolkit().getImage(IhmEasyGec.class.getResource("/icones/easy.png")));
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    setBounds(100, 100, 738, 591);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);
    
    panelCentre = new JPanel();
    panelCentre.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Param\u00E8tres de l'organisation", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    contentPane.add(panelCentre, BorderLayout.CENTER);
    panelCentre.setLayout(new BorderLayout(0, 0));
    
    panel_1 = new JPanel();
    panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Circuits", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    panelCentre.add(panel_1, BorderLayout.CENTER);
    panel_1.setLayout(new BorderLayout(0, 0));
    
    panel_4 = new JPanel();
    panel_4.setPreferredSize(new Dimension(50, 120));
    panel_1.add(panel_4, BorderLayout.EAST);
    
    btnCircuitPlus = new JButton("");
    btnCircuitPlus.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        String retour = JOptionPane.showInputDialog(IhmEasyGec.this, EasyGec.getLangages().getText("79", EasyGec.getLang()) ,
            EasyGec.getLangages().getText("78", EasyGec.getLang()), JOptionPane.OK_CANCEL_OPTION);
        if(retour != null)
        {
          Circuit circuit = new Circuit();
          circuit.setNom(retour);
          if(easyGec.isAbc())
          {
            easyGec.addAbc(circuit);
          }
          else
          {
            easyGec.getCircuit().getCircuits().add(circuit);
          }
          Collections.sort(easyGec.getCircuit().getCircuits());
          listCircuits.setListData(easyGec.getCircuit().getCircuits());
          if(easyGec.getCircuit().getCircuits().size()>0)
          {
            listCircuits.setSelectedIndex(easyGec.getCircuit().getCircuits().size()-1);
          }
        }
      }
    });
    panel_4.add(btnCircuitPlus);
    btnCircuitPlus.setPreferredSize(new Dimension(32, 32));
    btnCircuitPlus.setToolTipText("Ajouter un circuit.");
    btnCircuitPlus.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/plus.png")));
    
    btnCircuitModifier = new JButton("");
    btnCircuitModifier.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(listCircuits.getSelectedIndex()>-1)
        {
          String retour = (String) JOptionPane.showInputDialog(IhmEasyGec.this, EasyGec.getLangages().getText("81", EasyGec.getLang()) ,
              EasyGec.getLangages().getText("80", EasyGec.getLang()), JOptionPane.OK_CANCEL_OPTION, null, null, listCircuits.getSelectedValue().getNom());
          if(retour != null)
          {
            if(easyGec.isAbc())
            {
              easyGec.setNomCircuitAbc(listCircuits.getSelectedValue(), retour);
            }
            else
            {
              listCircuits.getSelectedValue().setNom(retour);
            }
            int index = listCircuits.getSelectedIndex();
            listCircuits.setListData(easyGec.getCircuit().getCircuits());
            if(easyGec.getCircuit().getCircuits().size()>0)
            {
              listCircuits.setSelectedIndex(index);
            }
          }
        }
      }
    });
    panel_4.add(btnCircuitModifier);
    btnCircuitModifier.setToolTipText("Modifier le circuit.");
    btnCircuitModifier.setPreferredSize(new Dimension(32, 32));
    btnCircuitModifier.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/search.png")));
    
    btnCircuitSupprimer = new JButton("");
    btnCircuitSupprimer.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(listCircuits.getSelectedIndex()>-1)
        {
          int retour = JOptionPane.showConfirmDialog(IhmEasyGec.this, EasyGec.getLangages().getText("83", EasyGec.getLang()) + listCircuits.getSelectedValue().getNom(),
              EasyGec.getLangages().getText("82", EasyGec.getLang()), JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
          if(retour == 0)
          {
            easyGec.getCircuit().getCircuits().remove(listCircuits.getSelectedValue());
            listCircuits.setListData(easyGec.getCircuit().getCircuits());
            if(easyGec.getCircuit().getCircuits().size()>0)
            {
              listCircuits.setSelectedIndex(0);
            }
          }
        }
      }
    });
    panel_4.add(btnCircuitSupprimer);
    btnCircuitSupprimer.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/delete.png")));
    btnCircuitSupprimer.setPreferredSize(new Dimension(32, 32));
    btnCircuitSupprimer.setToolTipText("Supprimer le circuit.");
    
    buttonUnss = new JButton("");
    buttonUnss.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        if(easyGec.isUnss())
        {
          easyGec.setUnss(false);
          buttonUnss.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/not_equal.png")));
        }
        else
        {
          easyGec.setUnss(true);
          buttonUnss.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/equal.png")));
        }
      }
    });
    buttonUnss.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/not_equal.png")));
    buttonUnss.setToolTipText(EasyGec.getLangages().getText("123", EasyGec.getLang()));
    buttonUnss.setPreferredSize(new Dimension(32, 32));
    panel_4.add(buttonUnss);
    
    listCircuits = new JList<Circuit>();
    listCircuits.addMouseListener(new MouseAdapter() 
    {
      @Override
      public void mouseClicked(MouseEvent arg0) 
      {
        if (arg0.getClickCount() == 2) 
        {
          btnCircuitModifier.doClick();
        } 
      }
    });
    listCircuits.addListSelectionListener(new ListSelectionListener() 
    {
      public void valueChanged(ListSelectionEvent e) 
      {
        if(listCircuits.getSelectedIndex()>-1)
        {
          listCodes.setListData(listCircuits.getSelectedValue().getCodes().getCodes());
          if(listCircuits.getSelectedValue().isEnLigne())
          {            
            rdbtnCourseEnLigne.setSelected(true);
          }
          else
          {        
            rdbtnCourseAuScore.setSelected(true);
          }
          if(listCircuits.getSelectedValue().isDepartBoitier())
          {            
            rdbtnDpartAuBoitier.setSelected(true);
          }
          else
          {        
            rdbtnDpartGroup.setSelected(true);
          }
          miseAjourDepart();
        }
      }
    });
    listCircuits.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    JScrollPane scrollPaneCircuits = new JScrollPane();
    panel_1.add(scrollPaneCircuits, BorderLayout.CENTER);
    scrollPaneCircuits.setPreferredSize(new Dimension(150, 270));
    scrollPaneCircuits.setViewportView(listCircuits);
    
    panelCodes = new JPanel();
    panelCodes.setBorder(new TitledBorder(null, "Codes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelCodes.setLayout(new BorderLayout(0, 0));
    panelCentre.add(panelCodes, BorderLayout.EAST);
    
    listCodes = new JList<Integer>();
    listCodes.setFont(new Font("Tahoma", Font.PLAIN, 12));
    listCodes.addMouseListener(new MouseAdapter() 
    {
      @Override
      public void mouseClicked(MouseEvent e) 
      {
        if (e.getClickCount() == 2) 
        {
          btnModifier.doClick();
        } 
      }
    });
    listCodes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setPreferredSize(new Dimension(50, 100));
    scrollPane.setViewportView(listCodes);
    panelCodes.add(scrollPane, BorderLayout.CENTER);
    
    panel_2 = new JPanel();
    panel_2.setPreferredSize(new Dimension(50, 10));
    panelCodes.add(panel_2, BorderLayout.EAST);
    
    btnCodePlus = new JButton("");
    btnCodePlus.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(listCircuits.getSelectedIndex()>-1)
        {
          Circuit cir = listCircuits.getSelectedValue();
          IhmCodes ihm = new IhmCodes(listCircuits.getSelectedValue(), easyGec);
          ihm.setLocationRelativeTo(IhmEasyGec.this);
          ihm.setVisible(true);
          listCodes.setListData(listCircuits.getSelectedValue().getCodes().getCodes());
          if(listCircuits.getSelectedValue().getCodes().getCodes().size()>0)
          {
            listCodes.setSelectedIndex(listCircuits.getSelectedValue().getCodes().getCodes().size()-1);
          }
          Collections.sort(easyGec.getCircuit().getCircuits());
          listCircuits.repaint();
          listCircuits.setSelectedValue(cir, true);
        }
      }
    });
    btnCodePlus.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/plus.png")));
    btnCodePlus.setPreferredSize(new Dimension(32, 32));
    btnCodePlus.setToolTipText("Ajouter un code.");
    panel_2.add(btnCodePlus);
    
    btnModifier = new JButton("");
    btnModifier.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(listCodes.getSelectedIndex()>-1)
        {
          int retour = -1;
          try
          {
            retour = (int) JOptionPane.showInputDialog(IhmEasyGec.this, EasyGec.getLangages().getText("85", EasyGec.getLang()) ,
                EasyGec.getLangages().getText("84", EasyGec.getLang()), JOptionPane.OK_CANCEL_OPTION, null, codes, codes[0]);
          }
          catch (NullPointerException e)
          {
            // TODO: handle exception
          }
          if(retour > 0)
          {
            int index = listCodes.getSelectedIndex();
            listCircuits.getSelectedValue().getCodes().getCodes().set(index, retour);
            listCodes.setListData(listCircuits.getSelectedValue().getCodes().getCodes());
            if(listCircuits.getSelectedValue().getCodes().getCodes().size()>0)
            {
              listCodes.setSelectedIndex(index);
            }
          }
        }
      }
    });
    btnModifier.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/search.png")));
    btnModifier.setPreferredSize(new Dimension(32, 32));
    btnModifier.setToolTipText("Modifier le code.");
    panel_2.add(btnModifier);
    
    btnCodeSupprimer = new JButton("");
    btnCodeSupprimer.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(listCodes.getSelectedIndex()>-1)
        {
          int retour = JOptionPane.showConfirmDialog(IhmEasyGec.this, EasyGec.getLangages().getText("87", EasyGec.getLang()) + listCodes.getSelectedValue().toString(),
              EasyGec.getLangages().getText("86", EasyGec.getLang()), JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
          if(retour == 0)
          {
            listCircuits.getSelectedValue().getCodes().getCodes().remove(listCodes.getSelectedIndex());
            listCodes.setListData(listCircuits.getSelectedValue().getCodes().getCodes());
            if(listCircuits.getSelectedValue().getCodes().getCodes().size()>0)
            {
              listCodes.setSelectedIndex(0);
            }
            Collections.sort(easyGec.getCircuit().getCircuits());
            listCircuits.repaint();
          }
        }
      }
    });
    btnCodeSupprimer.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/delete.png")));
    btnCodeSupprimer.setPreferredSize(new Dimension(32, 32));
    btnCodeSupprimer.setToolTipText("Supprimer le code.");
    panel_2.add(btnCodeSupprimer);
    
    btnCodeMonter = new JButton("");
    btnCodeMonter.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(listCodes.getSelectedIndex()>0)
        {
          int index = listCodes.getSelectedIndex();
          listCircuits.getSelectedValue().getCodes().upCode(listCodes.getSelectedValue(), index);
          listCodes.setListData(listCircuits.getSelectedValue().getCodes().getCodes());
          if(listCircuits.getSelectedValue().getCodes().getCodes().size()>0)
          {
            listCodes.setSelectedIndex(index-1);
          }
        }
      }
    });
    btnCodeMonter.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/up.png")));
    btnCodeMonter.setPreferredSize(new Dimension(32, 32));
    btnCodeMonter.setToolTipText("Monter d'un niveau.");
    panel_2.add(btnCodeMonter);
    
    btnNewButton = new JButton("");
    btnNewButton.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(listCodes.getSelectedIndex()>=0 && listCodes.getSelectedIndex()<listCircuits.getSelectedValue().getCodes().getCodes().size()-1)
        {
          int index = listCodes.getSelectedIndex();
          listCircuits.getSelectedValue().getCodes().downCode(listCodes.getSelectedValue(), index);
          listCodes.setListData(listCircuits.getSelectedValue().getCodes().getCodes());
          if(listCircuits.getSelectedValue().getCodes().getCodes().size()>0)
          {
            listCodes.setSelectedIndex(index+1);
          }
        }
      }
    });
    btnNewButton.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/down.png")));
    btnNewButton.setPreferredSize(new Dimension(32, 32));
    btnNewButton.setToolTipText("Descendre d'un niveau.");
    panel_2.add(btnNewButton);
    
    panel_3 = new JPanel();
    FlowLayout flowLayout_3 = (FlowLayout) panel_3.getLayout();
    flowLayout_3.setAlignment(FlowLayout.LEFT);
    panel_3.setPreferredSize(new Dimension(10, 130));
    panelCentre.add(panel_3, BorderLayout.SOUTH);
    
    panelDepart = new JPanel();
    //panelDepart.setPreferredSize(new Dimension(250, 10));
    panel_3.add(panelDepart);
    panelDepart.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Type de d\u00E9part", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    
    rdbtnDpartAuBoitier = new JRadioButton("D\u00E9part au boitier");
    rdbtnDpartAuBoitier.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        miseAjourDepart();
      }
    });
    rdbtnDpartAuBoitier.setSelected(true);
    panelDepart.add(rdbtnDpartAuBoitier);
    
    rdbtnDpartGroup = new JRadioButton("D\u00E9part group\u00E9");
    rdbtnDpartGroup.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        miseAjourDepart();
      }
    });
    panelDepart.add(rdbtnDpartGroup);
    
    panel_5 = new JPanel();
    panelDepart.add(panel_5);
    
    spinnerHeures = new JSpinner();
    spinnerHeures.addChangeListener(new ChangeListener() 
    {
      public void stateChanged(ChangeEvent arg0) 
      {
        miseAjourHeureDepart();
      }
    });
    spinnerHeures.setEnabled(false);
    spinnerHeures.setModel(new SpinnerNumberModel(0, 0, 23, 1));
    panel_5.add(spinnerHeures);
    
    JLabel lblH = new JLabel("H");
    panel_5.add(lblH);
    
    spinnerMinutes = new JSpinner();
    spinnerMinutes.addChangeListener(new ChangeListener() 
    {
      public void stateChanged(ChangeEvent arg0) 
      {
        miseAjourHeureDepart();
      }
    });
    spinnerMinutes.setEnabled(false);
    spinnerMinutes.setModel(new SpinnerNumberModel(0, 0, 59, 1));
    panel_5.add(spinnerMinutes);
    
    JLabel lblMn = new JLabel("MN");
    panel_5.add(lblMn);
    
    spinnerSecondes = new JSpinner();
    spinnerSecondes.addChangeListener(new ChangeListener() 
    {
      public void stateChanged(ChangeEvent arg0) 
      {
        miseAjourHeureDepart();
      }
    });
    spinnerSecondes.setEnabled(false);
    spinnerSecondes.setModel(new SpinnerNumberModel(0, 0, 59, 1));
    panel_5.add(spinnerSecondes);
    
    JLabel lblS = new JLabel("S");
    panel_5.add(lblS);
    
    panelType = new JPanel();
    FlowLayout flowLayout_5 = (FlowLayout) panelType.getLayout();
    flowLayout_5.setAlignment(FlowLayout.LEFT);
    //panelType.setPreferredSize(new Dimension(180, 90));
    panel_3.add(panelType);
    panelType.setBorder(new TitledBorder(null, "Type de course", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    
    rdbtnCourseEnLigne = new JRadioButton("Course en ligne");
    rdbtnCourseEnLigne.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        miseAjourEnLigne();
      }
    });
    rdbtnCourseEnLigne.setSelected(true);
    panelType.add(rdbtnCourseEnLigne);
    
    rdbtnCourseAuScore = new JRadioButton("Course au score");
    rdbtnCourseAuScore.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        miseAjourEnLigne();
      }
    });
    panelType.add(rdbtnCourseAuScore);
    
    ButtonGroup btgp = new ButtonGroup();
    btgp.add(rdbtnCourseEnLigne);
    btgp.add(rdbtnCourseAuScore);
    
    ButtonGroup btgph = new ButtonGroup();
    btgph.add(rdbtnDpartAuBoitier);
    btgph.add(rdbtnDpartGroup);
    
    panelOrienteurs = new JPanel();
    panelOrienteurs.setBorder(new TitledBorder(null, "Orienteurs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelCentre.add(panelOrienteurs, BorderLayout.WEST);
    panelOrienteurs.setLayout(new BorderLayout(0, 0));
    
    final JList<Orienteur> listOrienteurs = new JList<Orienteur>();
    listOrienteurs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listOrienteurs.addMouseListener(new MouseAdapter() 
    {
      @Override
      public void mouseClicked(MouseEvent arg0) 
      {
        if (arg0.getClickCount() == 2) 
        {
          buttonOrienteurModif.doClick();
        } 
      }
    });
    
    JScrollPane scrollPane_1 = new JScrollPane();
    scrollPane_1.setPreferredSize(new Dimension(200, 100));
    scrollPane_1.setViewportView(listOrienteurs);
    panelOrienteurs.add(scrollPane_1, BorderLayout.CENTER);
    
    panel_8 = new JPanel();
    panel_8.setPreferredSize(new Dimension(50, 10));
    panelOrienteurs.add(panel_8, BorderLayout.EAST);
    
    buttonChargerOrienteurs = new JButton("");
    buttonChargerOrienteurs.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        // On ouvre le navigateur
        JFileChooser chooser = new JFileChooser();
        FiltreFichier filter = new FiltreFichier();
        filter.addExtension("csv");
        filter.addExtension("ssv");
        filter.setDescription("CSV Files");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(easyGec.getRepertoire()));
        
        int returnVal = chooser.showOpenDialog(IhmEasyGec.this);
        // Si un fichier a �t� choisi
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
          easyGec.getOrienteurs().getOrienteurs().clear();
          CsvOrienteurs.importer(easyGec, chooser.getSelectedFile().getAbsolutePath());
          listOrienteurs.setListData(easyGec.getOrienteurs().getOrienteurs());
        }
      }
    });
    buttonChargerOrienteurs.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/csv.png")));
    buttonChargerOrienteurs.setToolTipText("Importer des orienteurs.");
    buttonChargerOrienteurs.setPreferredSize(new Dimension(32, 32));
    panel_8.add(buttonChargerOrienteurs);
    
    buttonOrienteurPlus = new JButton("");
    buttonOrienteurPlus.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        IhmOrienteur ihm = new IhmOrienteur(easyGec, null);
        ihm.setLocationRelativeTo(IhmEasyGec.this);
        //ihm.pack();
        ihm.setVisible(true);
        listOrienteurs.setListData(easyGec.getOrienteurs().getOrienteurs());
      }
    });
    buttonOrienteurPlus.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/plus.png")));
    buttonOrienteurPlus.setToolTipText("Ajouter un orienteur.");
    buttonOrienteurPlus.setPreferredSize(new Dimension(32, 32));
    panel_8.add(buttonOrienteurPlus);
    
    buttonOrienteurModif = new JButton("");
    buttonOrienteurModif.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        if(listOrienteurs.getSelectedIndex()>-1)
        {
          IhmOrienteur ihm = new IhmOrienteur(easyGec, listOrienteurs.getSelectedValue());
          ihm.setLocationRelativeTo(IhmEasyGec.this);
          ihm.setVisible(true);
          listOrienteurs.setListData(easyGec.getOrienteurs().getOrienteurs());
        }
      }
    });
    buttonOrienteurModif.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/search.png")));
    buttonOrienteurModif.setToolTipText("Modifier l'orienteur.");
    buttonOrienteurModif.setPreferredSize(new Dimension(32, 32));
    panel_8.add(buttonOrienteurModif);
    
    buttonOrienteurMoins = new JButton("");
    buttonOrienteurMoins.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        if(listOrienteurs.getSelectedIndex()>-1)
        {
          int retour = JOptionPane.showConfirmDialog(IhmEasyGec.this, EasyGec.getLangages().getText("89", EasyGec.getLang()) + listOrienteurs.getSelectedValue().getNom(),
              EasyGec.getLangages().getText("88", EasyGec.getLang()), JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
          if(retour == 0)
          {
            easyGec.getOrienteurs().getOrienteurs().remove(listOrienteurs.getSelectedValue());
            listOrienteurs.setListData(easyGec.getOrienteurs().getOrienteurs());
            if(easyGec.getOrienteurs().getOrienteurs().size()>0)
            {
              listOrienteurs.setSelectedIndex(0);
            }
          }
        }
      }
    });
    buttonOrienteurMoins.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/delete.png")));
    buttonOrienteurMoins.setToolTipText("Supprimer l'orienteur.");
    buttonOrienteurMoins.setPreferredSize(new Dimension(32, 32));
    panel_8.add(buttonOrienteurMoins);
    
    initCodes();

    easyGec = new EasyGec(IhmEasyGec.this);

    XmlLangage.lecture(easyGec, IhmEasyGec.class.getResource("/lang.xml"));
    Config.lectureFichier(easyGec, "config.xml");
    
    panel_7 = new JPanel();
    panel_7.setBorder(new TitledBorder(null, "Menu", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    FlowLayout flowLayout_4 = (FlowLayout) panel_7.getLayout();
    flowLayout_4.setAlignment(FlowLayout.LEFT);
    contentPane.add(panel_7, BorderLayout.NORTH);
    
    JPanel panelNord = new JPanel();
    panel_7.add(panelNord);
    panelNord.setPreferredSize(new Dimension(650, 140));
    FlowLayout flowLayout = (FlowLayout) panelNord.getLayout();
    flowLayout.setAlignment(FlowLayout.LEFT);
    
    panelFichier = new JPanel();
    FlowLayout flowLayout_2 = (FlowLayout) panelFichier.getLayout();
    flowLayout_2.setAlignment(FlowLayout.LEFT);
    panelFichier.setBorder(new TitledBorder(null, "Fichier", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelNord.add(panelFichier);
    
    btnNouveau = new JButton("");
    btnNouveau.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        Enregistrer();
        easyGec.clear();
        listCircuits.repaint();
        listOrienteurs.repaint();
        listCodes.setListData(new Vector<Integer>());
        listCodes.repaint();
      }
    });
    btnNouveau.setToolTipText("Nouvelle organisation.");
    btnNouveau.setPreferredSize(new Dimension(32, 32));
    btnNouveau.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/new.png")));
    panelFichier.add(btnNouveau);
    
    btnOuvrir = new JButton("");
    btnOuvrir.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        Enregistrer();
        // On ouvre le navigateur
        JFileChooser chooser = new JFileChooser();
        FiltreFichier filter = new FiltreFichier();
        filter.addExtension("egc");
        filter.setDescription("Fichiers EasyGec");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(easyGec.getRepertoire()));
        
        int returnVal = chooser.showOpenDialog(IhmEasyGec.this);
        // Si un fichier a �t� choisi
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
          easyGec.clear();
          easyGec.setFichier(chooser.getSelectedFile().getAbsolutePath());
          easyGec.setRepertoire(chooser.getSelectedFile().getAbsolutePath());
          Config.enregistre(easyGec, "config.xml");
          XmlOrganisation.lecture(easyGec, easyGec.getFichier());
          setTitre();
          Collections.sort(easyGec.getCircuit().getCircuits());
          listCircuits.setListData(easyGec.getCircuit().getCircuits());
          if(easyGec.getCircuit().getCircuits().size()>0)
          {
            listCircuits.setSelectedIndex(0);
          }
          listOrienteurs.setListData(easyGec.getOrienteurs().getOrienteurs());
        }
      }
    });
    btnOuvrir.setToolTipText("Ouvrir une organisation.");
    btnOuvrir.setPreferredSize(new Dimension(32, 32));
    btnOuvrir.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/dossier.png")));
    panelFichier.add(btnOuvrir);
    
    btnEnregistrer = new JButton("");
    btnEnregistrer.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(easyGec.getFichier().compareTo("") == 0)
        {
          // On ouvre le navigateur sur le r�pertoire par d�faut
          JFileChooser chooser = new JFileChooser();
          FiltreFichier filter = new FiltreFichier();
          filter.addExtension("egc");
          filter.setDescription("Fichiers EasyGec");
          chooser.setFileFilter(filter);
          chooser.setCurrentDirectory(new File(easyGec.getRepertoire()));
          
          int returnVal = chooser.showSaveDialog(IhmEasyGec.this);
          // Si un fichier a �t� choisi
          if(returnVal == JFileChooser.APPROVE_OPTION) 
          {
            easyGec.setFichier((Outils.verifExtension(chooser.getSelectedFile().getAbsolutePath(), ".egc")));
          }
          enregistreOrganisation();
        }
        else
        {
          enregistreOrganisation();
        }
      }
    });
    btnEnregistrer.setToolTipText("Enregistrer l'organisation.");
    btnEnregistrer.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/save32.png")));
    btnEnregistrer.setPreferredSize(new Dimension(32, 32));
    panelFichier.add(btnEnregistrer);
    
    panel = new JPanel();
    panel.setBorder(new TitledBorder(null, "Import/Export", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelNord.add(panel);
    
    btnImportOcad = new JButton("");
    btnImportOcad.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        // On ouvre le navigateur
        JFileChooser chooser = new JFileChooser();
        FiltreFichier filter = new FiltreFichier();
        filter.addExtension("xml");
        filter.setDescription("Fichiers xml");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(easyGec.getRepertoire()));
        
        int returnVal = chooser.showOpenDialog(IhmEasyGec.this);
        // Si un fichier a �t� choisi
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
          String fichier = chooser.getSelectedFile().getAbsolutePath();
          XmlOcadCircuit.importer(easyGec,fichier);
          Collections.sort(easyGec.getCircuit().getCircuits());
          listCircuits.setListData(easyGec.getCircuit().getCircuits());
          listCircuits.repaint();
          if(listCircuits.getModel().getSize()>0)
          {
            listCircuits.setSelectedIndex(0);
          }
          else
          {
            listCircuits.setSelectedIndex(-1);
          }
        }
      }
    });
    panel.add(btnImportOcad);
    btnImportOcad.setToolTipText("Importer les circuits \u00E0 partir d'OCAD.");
    btnImportOcad.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/ocad.png")));
    btnImportOcad.setPreferredSize(new Dimension(32, 32));
    
    btnExportHtml = new JButton("");
    btnExportHtml.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        //enregistreResultats();
        // Lancement du fichier
        Runtime r = Runtime.getRuntime();
        
        try
        {
          // Lancement du fichier d'aide de l'application
          String adresse = new File(".").getCanonicalPath().toString();
          adresse = "cmd /c start \"\" \""  + easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLangages().getText("114", EasyGec.getLang()) + ".html" +"\"";
          r.exec(adresse);
        }
        catch (IOException e1)
        {
          JOptionPane.showMessageDialog(null,"Erreur de lancement du fichier : "+e1.getClass().getName());
        }
      }
    });
    
    btnImportCircuitAbc = new JButton();

    // Why is this button disabled?
    btnImportCircuitAbc.setVisible(false);
    btnImportCircuitAbc.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        // On ouvre le navigateur
        JFileChooser chooser = new JFileChooser();
        FiltreFichier filter = new FiltreFichier();
        filter.addExtension("csv");
        filter.setDescription("Fichiers cvs");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(easyGec.getRepertoire()));
        
        int returnVal = chooser.showOpenDialog(IhmEasyGec.this);
        // Si un fichier a �t� choisi
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
          String fichier = chooser.getSelectedFile().getAbsolutePath();
          CsvCircuits.importer(easyGec,fichier);
          Collections.sort(easyGec.getCircuit().getCircuits());
          listCircuits.setListData(easyGec.getCircuit().getCircuits());
          listCircuits.repaint();
          if(listCircuits.getModel().getSize()>0)
          {
            listCircuits.setSelectedIndex(0);
          }
          else
          {
            listCircuits.setSelectedIndex(-1);
          }
        }
      }
    });
    btnImportCircuitAbc.setPreferredSize(new Dimension(32, 32));
    btnImportCircuitAbc.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/csv.png")));
    panel.add(btnImportCircuitAbc);
    
    btnResultatsGlobaux = new JButton("");
    btnResultatsGlobaux.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        // Lancement du fichier
        Runtime r = Runtime.getRuntime();
        
        try
        {
          // Lancement du fichier d'aide de l'application
          String adresse = new File(".").getCanonicalPath().toString();
          adresse = "cmd /c start \"\" \""  + easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLangages().getText("113", EasyGec.getLang()) + ".html" +"\"";
          r.exec(adresse);
        }
        catch (IOException e1)
        {
          JOptionPane.showMessageDialog(null,"Erreur de lancement du fichier : "+e1.getClass().getName());
        }
      }
    });
    btnResultatsGlobaux.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/podium.png")));
    btnResultatsGlobaux.setPreferredSize(new Dimension(32, 32));
    panel.add(btnResultatsGlobaux);
    
    btnResultatsDetailled = new JButton("");
    btnResultatsDetailled.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        // Lancement du fichier
        Runtime r = Runtime.getRuntime();
        
        try
        {
          // Lancement du fichier d'aide de l'application
          String adresse = new File(".").getCanonicalPath().toString();
          adresse = "cmd /c start \"\" \""  + easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLangages().getText("115", EasyGec.getLang()) + ".html" +"\"";
          r.exec(adresse);
        }
        catch (IOException e1)
        {
          JOptionPane.showMessageDialog(null,"Erreur de lancement du fichier : "+e1.getClass().getName());
        }
      }
    });
    btnResultatsDetailled.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/chrono.png")));
    btnResultatsDetailled.setPreferredSize(new Dimension(32, 32));
    panel.add(btnResultatsDetailled);
    panel.add(btnExportHtml);
    btnExportHtml.setToolTipText("Ouvrir les r\u00E9sultats globaux en HTML.");
    btnExportHtml.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/global.png")));
    btnExportHtml.setPreferredSize(new Dimension(32, 32));
    
    btnExportPartiels = new JButton("");
    btnExportPartiels.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        //enregistreResultatsDetailles();

        // Lancement du fichier
        Runtime r = Runtime.getRuntime();
        
        try
        {
          // Lancement du fichier d'aide de l'application
          String adresse = new File(".").getCanonicalPath().toString();
          adresse = "cmd /c start \"\" \""  + easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLangages().getText("117", EasyGec.getLang()) + ".html" +"\"";
          r.exec(adresse);
        }
        catch (IOException e1)
        {
          JOptionPane.showMessageDialog(null,"Erreur de lancement du fichier : "+e1.getClass().getName());
        }
      }
    });
    panel.add(btnExportPartiels);
    btnExportPartiels.setToolTipText("Ouvrir les r\u00E9sultats d\u00E9taill\u00E9s en HTML.");
    btnExportPartiels.setPreferredSize(new Dimension(32, 32));
    btnExportPartiels.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/detailed.png")));
    
    btnSI = new JButton("");
    btnSI.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(easyGec.getCircuit().getCircuits().size()>0)
        {
          easyGec.getResultatsSi().getResultatsSi().clear();
          easyGec.getOrienteursSi().getOrienteurs().clear();
          IhmImportSI ihm = new IhmImportSI(IhmEasyGec.this);
          ihm.setLocationRelativeTo(IhmEasyGec.this);
          ihm.setVisible(true);
        }
      }
    });
    
    btnEffacerResultats = new JButton("");
    btnEffacerResultats.setToolTipText(EasyGec.getLangages().getText("120", EasyGec.getLang()));
    btnEffacerResultats.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        if(easyGec.getResultats().getResultats().size()>0)
        {
          int retour = JOptionPane.showConfirmDialog(IhmEasyGec.this, EasyGec.getLangages().getText("122", EasyGec.getLang()),
              EasyGec.getLangages().getText("121", EasyGec.getLang()), JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
          if(retour == 0)
          {
            easyGec.getResultats().getResultats().removeAllElements();
          }
        }
      }
    });
    btnEffacerResultats.setPreferredSize(new Dimension(32, 32));
    btnEffacerResultats.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/delete.png")));
    panel.add(btnEffacerResultats);
    btnSI.setToolTipText("Associer un Log SI et une liste d'orienteurs.");
    btnSI.setPreferredSize(new Dimension(32, 32));
    btnSI.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/si.png")));
    panel.add(btnSI);
    
    panel_6 = new JPanel();
    panel_6.setBorder(new TitledBorder(null, "Aide", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelNord.add(panel_6);
    
    btnAide = new JButton("");
    btnAide.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        Runtime r = Runtime.getRuntime();
        
        try
        {
          // Lancement du fichier d'aide de l'application
          String adresse = new File(".").getCanonicalPath().toString();
          adresse = "cmd /c start \"\" \"" + adresse + "/" + EasyGec.getLangages().getText("0", EasyGec.getLang()) +"\"";
          r.exec(adresse);
        }
        catch (IOException e1)
        {
          JOptionPane.showMessageDialog(IhmEasyGec.this,"Erreur de lancement de l'aide : "+e1.getClass().getName());
        }
      }
    });
    btnAide.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/help.png")));
    btnAide.setPreferredSize(new Dimension(32, 32));
    btnAide.setToolTipText("Afficher le fichier d'aide.");
    panel_6.add(btnAide);
    
    btnInfo = new JButton("");
    btnInfo.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        JOptionPane.showMessageDialog(IhmEasyGec.this, EasyGec.getLangages().getText("57", EasyGec.getLang()) + "\nD�veloppeur : Thierry PORRET.\nTraducteurs : Robert MARIQUE, Michael HOCK et Luc CLOOSTERMANS.");
      }
    });
    btnInfo.setToolTipText("Afficher la boite d'information.");
    btnInfo.setPreferredSize(new Dimension(32, 32));
    btnInfo.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/info.png")));
    panel_6.add(btnInfo);
    
    panel_9 = new JPanel();
    panel_9.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Param\u00E8tres", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    panelNord.add(panel_9);
    
    btnMode = new JButton("");
    btnMode.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        if(autoResult)
        {
          btnMode.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/ihm.png")));
          autoResult = false;
        }
        else
        {
          btnMode.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/smiley.png")));
          autoResult = true;
        }
      }
    });
    btnMode.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/ihm.png")));
    btnMode.setToolTipText("Basculer de mode de lecture des puces.");
    btnMode.setPreferredSize(new Dimension(32, 32));
    panel_9.add(btnMode);
    
    btnLang = new JButton("");
    btnLang.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        String retour = "";
        try
        {
          retour = (String) JOptionPane.showInputDialog(IhmEasyGec.this, EasyGec.getLangages().getText("59", EasyGec.getLang()) ,
              EasyGec.getLangages().getText("58", EasyGec.getLang()), JOptionPane.OK_CANCEL_OPTION, null, easyGec.getLangs().toArray(), easyGec.getLangUsed());
        }
        catch (NullPointerException ee)
        {
          // TODO: handle exception
        }
        if(retour != null)
        {
          easyGec.setLangUsed(retour);
          Config.enregistre(easyGec, "config.xml");
          changeLanguage();
        }
      }
    });
    
    btnConfig = new JButton("");
    btnConfig.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        try
        {
          int retour = easyGec.getTempo();
          retour = (int) JOptionPane.showInputDialog(IhmEasyGec.this, "" ,
                  EasyGec.getLangages().getText("110", EasyGec.getLang()), JOptionPane.OK_CANCEL_OPTION, null, tempos, easyGec.getTempo());
          easyGec.setTempo(retour);
        }
        catch (NullPointerException ee)
        {
          // TODO: handle exception
        }
        Config.enregistre(easyGec, "config.xml");
      }
    });
    
    btnAbc = new JButton("");
    btnAbc.setVisible(false);
    btnAbc.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        IhmMappage ihm = new IhmMappage(easyGec);
        ihm.setLocationRelativeTo(IhmEasyGec.this);
        ihm.setVisible(true);
      }
    });
    btnAbc.setPreferredSize(new Dimension(32, 32));
    btnAbc.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/abc32.png")));
    panel_9.add(btnAbc);
    
    btnConfig.setToolTipText("Temporisation");
    btnConfig.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/configure.png")));
    btnConfig.setPreferredSize(new Dimension(32, 32));
    panel_9.add(btnConfig);
    btnLang.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/lang.png")));
    btnLang.setPreferredSize(new Dimension(32, 32));
    btnLang.setToolTipText("Choisissez votre langage");
    panel_9.add(btnLang);
    
    panelLecturePuce = new JPanel();
    FlowLayout flowLayout_1 = (FlowLayout) panelLecturePuce.getLayout();
    flowLayout_1.setAlignment(FlowLayout.LEFT);
    panelLecturePuce.setBorder(new TitledBorder(null, "Lecture des puces", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelNord.add(panelLecturePuce);
    
    lblPortCom = new JLabel("Port COM :");
    panelLecturePuce.add(lblPortCom);
    
    final JComboBox<String> comboBoxPortCom = new JComboBox<String>();
    comboBoxPortCom.setPreferredSize(new Dimension(100, 20));
    panelLecturePuce.add(comboBoxPortCom);
    
    btnInitCom = new JButton("");
    btnInitCom.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        cr = new CommunicationRail();
        comboBoxPortCom.setModel(new DefaultComboBoxModel<>(cr.getListePortRail()));
      }
    });
    btnInitCom.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/reload.png")));
    btnInitCom.setPreferredSize(new Dimension(32, 32));
    btnInitCom.setToolTipText("R\u00E9initialiser la liste des ports.");
    panelLecturePuce.add(btnInitCom);
    
    btnLancerLecture = new JButton("");
    btnLancerLecture.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(!siReaderEnCours)
        {
          easyGec.getSiHandler().setZeroTime(((Integer)spinner.getValue()*3600 + (Integer)spinner_1.getValue()*60)*1000);
          easyGec.getSiHandler().setPortName((String) comboBoxPortCom.getSelectedItem());
          easyGec.getSiHandler().start();
          btnLancerLecture.setIcon(new ImageIcon(getClass().getResource("/icones/attente.png")));
          siReaderEnCours = true;
          spinner.setEnabled(false);
          spinner_1.setEnabled(false);
        }
        else
        {
          easyGec.getSiHandler().stop();
          btnLancerLecture.setIcon(new ImageIcon(getClass().getResource("/icones/play.png")));
          btnLancerLecture.setToolTipText("Connecter la station maitre.");
          siReaderEnCours = false;
          spinner.setEnabled(true);
          spinner_1.setEnabled(true);
        }
      }
    });
    
    label = new JLabel("Heure z\u00E9ro : ");
    panelLecturePuce.add(label);
    
    spinner = new JSpinner();
    spinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
    panelLecturePuce.add(spinner);
    
    JLabel label_1 = new JLabel("H");
    panelLecturePuce.add(label_1);
    
    spinner_1 = new JSpinner();
    spinner_1.setModel(new SpinnerNumberModel(0, 0, 59, 1));
    panelLecturePuce.add(spinner_1);
    
    JLabel label_2 = new JLabel("MN   ");
    panelLecturePuce.add(label_2);
    btnLancerLecture.setToolTipText(EasyGec.getLangages().getText("31", EasyGec.getLang()));
    btnLancerLecture.setIcon(new ImageIcon(IhmEasyGec.class.getResource("/icones/play.png")));
    btnLancerLecture.setPreferredSize(new Dimension(32, 32));
    panelLecturePuce.add(btnLancerLecture);
    comboBoxPortCom.setModel(new DefaultComboBoxModel<>(cr.getListePortRail()));
    
    setTitle("EasyGec " + easyGec.getVersion());
    changeLanguage();
  }

  private void initCodes()
  {
    for(int i=0; i<225; i++)
    {
      codes[i] = i + 31;
    }
  }
  
  private void setTitre()
  {
    String nom = "";
    if(easyGec.isAbc())
    {
      nom = "AbcGec ";
    }
    else
    {
      nom = "EasyGec ";
    }
    setTitle(nom + easyGec.getVersion() + " - " + Outils.getNom(easyGec.getFichier()));
  }
  
  private void miseAjourDepart()
  {
    if(listCircuits.getSelectedIndex()>-1)
    {
      if(rdbtnDpartAuBoitier.isSelected())
      {
        spinnerHeures.setEnabled(false);
        spinnerMinutes.setEnabled(false);
        spinnerSecondes.setEnabled(false);
        listCircuits.getSelectedValue().setDepartBoitier(true);
      }
      else
      {
        int heure = TimeManager.getHeures(listCircuits.getSelectedValue().getHeureDepart());
        int minute = TimeManager.getMinutes(listCircuits.getSelectedValue().getHeureDepart());
        int seconde = TimeManager.getSecondes(listCircuits.getSelectedValue().getHeureDepart());
        spinnerHeures.setValue(heure);
        spinnerMinutes.setValue(minute);
        spinnerSecondes.setValue(seconde);
        listCircuits.getSelectedValue().setDepartBoitier(false);
        spinnerHeures.setEnabled(true);
        spinnerMinutes.setEnabled(true);
        spinnerSecondes.setEnabled(true);
      }
      listCircuits.repaint();
    }
  }
  
  private void miseAjourEnLigne()
  {
    if(listCircuits.getSelectedIndex()>-1)
    {
      if(rdbtnCourseEnLigne.isSelected())
      {
        listCircuits.getSelectedValue().setEnLigne(true);
      }
      else
      {
        listCircuits.getSelectedValue().setEnLigne(false);
      }
      listCircuits.repaint();
    }
  }
  
  private void miseAjourHeureDepart()
  {
    if(listCircuits.getSelectedIndex()>-1)
    {
      listCircuits.getSelectedValue().setHeureDepart(new Date((Integer)spinnerHeures.getValue()*3600000 +
          (Integer)spinnerMinutes.getValue()*60000 + (Integer)spinnerSecondes.getValue()*1000));
      listCircuits.repaint();
    }
  }
  
  private boolean existeRepertoire()
  {
    if(!easyGec.getFichier().equals(""))
    {
      File dossier = new File(Outils.getRepertoire(easyGec.getFichier()));
      return dossier.exists();
    }
    else
    {
      return false;
    }
  }
  
  public void setBoutonIcone(JButton bouton, ImageIcon icone)
  {
    bouton.setIcon(icone);
  }
  
  private void enregistreOrganisation()
  {
    if(existeRepertoire())
    {
      TaskBoutonOkNok task = new TaskBoutonOkNok(IhmEasyGec.this, btnEnregistrer, true);
      task.execute();
      XmlOrganisation.enregistre(easyGec, easyGec.getFichier());
      enregistreResultats();
    }
    else
    {
      TaskBoutonOkNok task = new TaskBoutonOkNok(IhmEasyGec.this, btnEnregistrer, false);
      task.execute();
    }
  }
  
  public void enregistreResultats()
  {
    easyGec.CalculResultats();
    if(existeRepertoire())
    {
      TaskBoutonOkNok task = new TaskBoutonOkNok(IhmEasyGec.this, btnExportHtml, true);
      task.execute();
      TaskBoutonOkNok task2 = new TaskBoutonOkNok(IhmEasyGec.this, btnResultatsGlobaux, true);
      task2.execute();
      HtmlResultatGlobal.exporterHtml(easyGec, easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLangages().getText("113", EasyGec.getLang()) + ".html");
      easyGec.getResultatsParIdentifiants().trier();
      Collections.sort(easyGec.getResultatsParIdentifiants().getResultats());
      HtmlResultatGlobal.exporterHtmlParIdentifiant(easyGec, easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLangages().getText("114", EasyGec.getLang()) + ".html");
      CsvResultats.exporter(easyGec, easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLangages().getText("113", EasyGec.getLang()) + ".csv");
    }
    else
    {
      TaskBoutonOkNok task = new TaskBoutonOkNok(IhmEasyGec.this, btnExportHtml, false);
      task.execute();
      TaskBoutonOkNok task2 = new TaskBoutonOkNok(IhmEasyGec.this, btnResultatsGlobaux, false);
      task2.execute();
    }
    enregistreResultatsDetailles();
  }
  
  public void enregistreResultatsDetailles()
  {
    easyGec.CalculResultatsDetaille();
    Collections.sort(easyGec.getResultats().getResultats(), new ResultatPuceComparator(1));
    if(existeRepertoire())
    {
      TaskBoutonOkNok task = new TaskBoutonOkNok(IhmEasyGec.this, btnExportPartiels, true);
      task.execute();
      TaskBoutonOkNok task2 = new TaskBoutonOkNok(IhmEasyGec.this, btnResultatsDetailled, true);
      task2.execute();
      HtmlResultatDetaille.exporterHtml(easyGec, easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLangages().getText("115", EasyGec.getLang()) + ".html");
      easyGec.getResultatsDetailleParIdentifiants().trier();
      Collections.sort(easyGec.getResultatsDetailleParIdentifiants().getResultats());
      HtmlResultatDetaille.exporterHtmlParIdentifiant(easyGec, easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLangages().getText("117", EasyGec.getLang()) + ".html");
      CsvResultatsDetailles.exporter(easyGec, easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLangages().getText("115", EasyGec.getLang()) + ".csv");
      CsvResultatsDetailles.exporterSimple(easyGec, easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLangages().getText("116", EasyGec.getLang()) + ".csv");
    }
    else
    {
      TaskBoutonOkNok task = new TaskBoutonOkNok(IhmEasyGec.this, btnExportPartiels, false);
      task.execute();
      TaskBoutonOkNok task2 = new TaskBoutonOkNok(IhmEasyGec.this, btnResultatsDetailled, false);
      task2.execute();
    }
  }

  public void stationStatus(String status) 
  {
    if(siReaderEnCours)
    {
      if( status.equals("Ready") ) 
      { //$NON-NLS-1$
        btnLancerLecture.setIcon(new ImageIcon(getClass().getResource("/icones/stop.png")));
        btnLancerLecture.setToolTipText(EasyGec.getLangages().getText("32", EasyGec.getLang()));
        return;
      }
    }
  }
  
  private void Enregistrer()
  {
    // Do not save empty event
    if (existeRepertoire()) {
      int retour = JOptionPane.showConfirmDialog(IhmEasyGec.this, EasyGec.getLangages().getText("45", EasyGec.getLang()),
      EasyGec.getLangages().getText("46", EasyGec.getLang()), JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION);
      if(retour == 0) {
        btnEnregistrer.doClick();
      }
    }

    // // prev
    // int retour = JOptionPane.showConfirmDialog(IhmEasyGec.this, EasyGec.getLangages().getText("45", EasyGec.getLang()),
    //   EasyGec.getLangages().getText("46", EasyGec.getLang()), JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION);
    // if(retour == 0)
    // {
    //   btnEnregistrer.doClick();
    // }
  }
  
  private void changeLanguage()
  {
    int lang = EasyGec.getLang();
    // BorderTitle
    panel_7.setBorder(new TitledBorder(null, EasyGec.getLangages().getText("1", lang), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelFichier.setBorder(new TitledBorder(null, EasyGec.getLangages().getText("2", lang), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelCentre.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), EasyGec.getLangages().getText("4", lang), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), EasyGec.getLangages().getText("5", lang), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    panelCodes.setBorder(new TitledBorder(null, EasyGec.getLangages().getText("6", lang), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelDepart.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), EasyGec.getLangages().getText("7", lang), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelType.setBorder(new TitledBorder(null, EasyGec.getLangages().getText("8", lang), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelOrienteurs.setBorder(new TitledBorder(null, EasyGec.getLangages().getText("9", lang), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panel.setBorder(new TitledBorder(null, EasyGec.getLangages().getText("10", lang), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panel_6.setBorder(new TitledBorder(null, EasyGec.getLangages().getText("3", lang), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panel_9.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), EasyGec.getLangages().getText("11", lang), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    panelLecturePuce.setBorder(new TitledBorder(null, EasyGec.getLangages().getText("12", lang), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    
    // Label
    lblPortCom.setText(EasyGec.getLangages().getText("13", lang));
    label.setText(EasyGec.getLangages().getText("14", lang));
    
    // radioBouton
    rdbtnDpartAuBoitier.setText(EasyGec.getLangages().getText("15", lang));
    rdbtnDpartGroup.setText(EasyGec.getLangages().getText("16", lang));
    rdbtnCourseAuScore.setText(EasyGec.getLangages().getText("17", lang));
    rdbtnCourseEnLigne.setText(EasyGec.getLangages().getText("18", lang));
    
    // ToolTip
    btnNouveau.setToolTipText(EasyGec.getLangages().getText("19", lang));
    btnOuvrir.setToolTipText(EasyGec.getLangages().getText("20", lang));
    btnEnregistrer.setToolTipText(EasyGec.getLangages().getText("21", lang));
    btnImportOcad.setToolTipText(EasyGec.getLangages().getText("22", lang));
    btnExportHtml.setToolTipText(EasyGec.getLangages().getText("23", lang));
    btnResultatsGlobaux.setToolTipText(EasyGec.getLangages().getText("23", lang));
    btnResultatsDetailled.setToolTipText(EasyGec.getLangages().getText("24", lang));
    btnExportPartiels.setToolTipText(EasyGec.getLangages().getText("24", lang));
    btnSI.setToolTipText(EasyGec.getLangages().getText("25", lang));
    btnAide.setToolTipText(EasyGec.getLangages().getText("26", lang));
    btnInfo.setToolTipText(EasyGec.getLangages().getText("27", lang));
    btnMode.setToolTipText(EasyGec.getLangages().getText("28", lang));
    btnLang.setToolTipText(EasyGec.getLangages().getText("29", lang));
    btnInitCom.setToolTipText(EasyGec.getLangages().getText("30", lang));
    btnLancerLecture.setToolTipText(EasyGec.getLangages().getText("31", lang));
    buttonChargerOrienteurs.setToolTipText(EasyGec.getLangages().getText("33", lang));
    buttonOrienteurPlus.setToolTipText(EasyGec.getLangages().getText("34", lang));
    buttonOrienteurModif.setToolTipText(EasyGec.getLangages().getText("35", lang));
    buttonOrienteurMoins.setToolTipText(EasyGec.getLangages().getText("36", lang));
    btnCircuitPlus.setToolTipText(EasyGec.getLangages().getText("37", lang));
    btnCircuitModifier.setToolTipText(EasyGec.getLangages().getText("38", lang));
    btnCircuitSupprimer.setToolTipText(EasyGec.getLangages().getText("39", lang));
    btnCodePlus.setToolTipText(EasyGec.getLangages().getText("40", lang));
    btnModifier.setToolTipText(EasyGec.getLangages().getText("41", lang));
    btnCodeSupprimer.setToolTipText(EasyGec.getLangages().getText("42", lang));
    btnCodeMonter.setToolTipText(EasyGec.getLangages().getText("43", lang));
    btnNewButton.setToolTipText(EasyGec.getLangages().getText("44", lang));
    btnConfig.setToolTipText(EasyGec.getLangages().getText("110", lang));
    btnImportCircuitAbc.setToolTipText(EasyGec.getLangages().getText("111", lang));
    btnAbc.setToolTipText(EasyGec.getLangages().getText("112", lang));
    btnEffacerResultats.setToolTipText(EasyGec.getLangages().getText("120", lang));
    buttonUnss.setToolTipText(EasyGec.getLangages().getText("123", lang));
  }
}
