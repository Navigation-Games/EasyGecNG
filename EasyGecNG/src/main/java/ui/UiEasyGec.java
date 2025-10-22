package ui;

import io.Config;
import io.CsvCourses;
import io.CsvOrienteers;
import io.CsvResults;
import io.CsvDetailedResults;
import io.HtmlDetailedResult;
import io.HtmlGlobalResult;
import io.XmlLanguage;
import io.XmlOcadCourse;
import io.XmlOrganization;

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
import java.util.HashMap;
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

import to.Course;
import to.Orienteer;
import to.ChipResultComparator;

import javax.swing.ListSelectionModel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import core.EasyGec;

import utils.RailCommunication;
import utils.FileExtensionFilter;
import utils.Utility;
import utils.OkNokButtonTask;
import utils.TimeManager;
import utils.DesktopApi;

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


public class UiEasyGec extends JFrame
{

  /**
   * 
   */
  private static final long serialVersionUID = -4824746389076523212L;
  public EasyGec easyGec = null;
  public boolean autoResult = true;
  private Integer[] codes = new Integer[225];
  private Integer[] tempos = {2, 3, 4, 5, 10, 15, 20};
  private JPanel contentPane;
  private JList<Course> listCourses;
  public JList<Integer> listCodes;
  private RailCommunication cr = new RailCommunication();
  private boolean siReaderEnCours = false;
  
  private JRadioButton rdbtnCourseClassicCourseEvaluator;
  private JRadioButton rdbtnCourseScoreCourseEvaluator;
  private JRadioButton rdbtnDpartAuBoitier;
  private JRadioButton rdbtnDpartGroup;
  private JSpinner spinnerHeures;
  private JSpinner spinnerMinutes;
  private JSpinner spinnerSecondes;
  public JButton btnEnregistrer;
  private JButton btnLancerLecture;
  private JButton btnExportHtml;
  private JButton btnExportSplits;
  private JSpinner spinner;
  private JSpinner spinner_1;
  private JButton btnModifier;
  private JButton buttonOrienteerModif;
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
  private JPanel panelOrienteers;
  private JPanel panelFichier;
  private JPanel panel;
  private JPanel panelLectureChip;
  private JPanel panelCentre;
  private JPanel panelCodes;
  private JPanel panelStart;
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
  private JButton buttonChargerOrienteers;
  private JButton buttonOrienteerPlus;
  private JButton buttonOrienteerMoins;
  private JButton btnCoursePlus;
  private final JButton btnCourseModifier;
  private JButton btnCourseSupprimer;
  private JButton btnCodePlus;
  private JButton btnCodeSupprimer;
  private JButton btnCodeMonter;
  private JButton btnNewButton;
  private JButton btnConfig;
  public JButton btnAbc;
  public JButton btnImportCourseAbc;
  private JButton btnResultsGlobaux;
  private JButton btnResultsDetailled;
  private JButton btnEffacerResults;
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
          UiEasyGec frame = new UiEasyGec();
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
  public UiEasyGec()
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
    setIconImage(Toolkit.getDefaultToolkit().getImage(UiEasyGec.class.getResource("/icons/easy.png")));
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
    panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Courses", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    panelCentre.add(panel_1, BorderLayout.CENTER);
    panel_1.setLayout(new BorderLayout(0, 0));
    
    panel_4 = new JPanel();
    panel_4.setPreferredSize(new Dimension(50, 120));
    panel_1.add(panel_4, BorderLayout.EAST);
    
    btnCoursePlus = new JButton("");
    btnCoursePlus.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        String resultValue = JOptionPane.showInputDialog(UiEasyGec.this, EasyGec.getLanguages().getText("79", EasyGec.getLang()) ,
            EasyGec.getLanguages().getText("78", EasyGec.getLang()), JOptionPane.OK_CANCEL_OPTION);
        if(resultValue != null)
        {
          Course course = new Course();
          course.setName(resultValue);
          if(easyGec.isAbc())
          {
            easyGec.addAbc(course);
          }
          else
          {
            easyGec.getCourse().getCourses().add(course);
          }
          Collections.sort(easyGec.getCourse().getCourses());
          listCourses.setListData(easyGec.getCourse().getCourses());
          if(easyGec.getCourse().getCourses().size()>0)
          {
            listCourses.setSelectedIndex(easyGec.getCourse().getCourses().size()-1);
          }
        }
      }
    });
    panel_4.add(btnCoursePlus);
    btnCoursePlus.setPreferredSize(new Dimension(32, 32));
    btnCoursePlus.setToolTipText("Ajouter un course.");
    btnCoursePlus.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/plus.png")));
    
    btnCourseModifier = new JButton("");
    btnCourseModifier.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(listCourses.getSelectedIndex()>-1)
        {
          String resultValue = (String) JOptionPane.showInputDialog(UiEasyGec.this, EasyGec.getLanguages().getText("81", EasyGec.getLang()) ,
              EasyGec.getLanguages().getText("80", EasyGec.getLang()), JOptionPane.OK_CANCEL_OPTION, null, null, listCourses.getSelectedValue().getName());
          if(resultValue != null)
          {
            if(easyGec.isAbc())
            {
              easyGec.setNameCourseAbc(listCourses.getSelectedValue(), resultValue);
            }
            else
            {
              listCourses.getSelectedValue().setName(resultValue);
            }
            int index = listCourses.getSelectedIndex();
            listCourses.setListData(easyGec.getCourse().getCourses());
            if(easyGec.getCourse().getCourses().size()>0)
            {
              listCourses.setSelectedIndex(index);
            }
          }
        }
      }
    });
    panel_4.add(btnCourseModifier);
    btnCourseModifier.setToolTipText("Modifier le course.");
    btnCourseModifier.setPreferredSize(new Dimension(32, 32));
    btnCourseModifier.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/search.png")));
    
    btnCourseSupprimer = new JButton("");
    btnCourseSupprimer.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(listCourses.getSelectedIndex()>-1)
        {
          int resultValue = JOptionPane.showConfirmDialog(UiEasyGec.this, EasyGec.getLanguages().getText("83", EasyGec.getLang()) + listCourses.getSelectedValue().getName(),
              EasyGec.getLanguages().getText("82", EasyGec.getLang()), JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
          if(resultValue == 0)
          {
            easyGec.getCourse().getCourses().remove(listCourses.getSelectedValue());
            listCourses.setListData(easyGec.getCourse().getCourses());
            if(easyGec.getCourse().getCourses().size()>0)
            {
              listCourses.setSelectedIndex(0);
            }
          }
        }
      }
    });
    panel_4.add(btnCourseSupprimer);
    btnCourseSupprimer.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/delete.png")));
    btnCourseSupprimer.setPreferredSize(new Dimension(32, 32));
    btnCourseSupprimer.setToolTipText("Supprimer le course.");
    
    buttonUnss = new JButton("");
    buttonUnss.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        if(easyGec.isUnss())
        {
          easyGec.setUnss(false);
          buttonUnss.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/not_equal.png")));
        }
        else
        {
          easyGec.setUnss(true);
          buttonUnss.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/equal.png")));
        }
      }
    });
    buttonUnss.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/not_equal.png")));
    buttonUnss.setToolTipText(EasyGec.getLanguages().getText("123", EasyGec.getLang()));
    buttonUnss.setPreferredSize(new Dimension(32, 32));
    panel_4.add(buttonUnss);
    
    listCourses = new JList<Course>();
    listCourses.addMouseListener(new MouseAdapter() 
    {
      @Override
      public void mouseClicked(MouseEvent arg0) 
      {
        if (arg0.getClickCount() == 2) 
        {
          btnCourseModifier.doClick();
        } 
      }
    });
    listCourses.addListSelectionListener(new ListSelectionListener() 
    {
      public void valueChanged(ListSelectionEvent e) 
      {
        if(listCourses.getSelectedIndex()>-1)
        {
          listCodes.setListData(listCourses.getSelectedValue().getCodes().getCodes());
          if(listCourses.getSelectedValue().isClassicCourseEvaluator())
          {            
            rdbtnCourseClassicCourseEvaluator.setSelected(true);
          }
          else
          {        
            rdbtnCourseScoreCourseEvaluator.setSelected(true);
          }
          if(listCourses.getSelectedValue().isStartBoitier())
          {            
            rdbtnDpartAuBoitier.setSelected(true);
          }
          else
          {        
            rdbtnDpartGroup.setSelected(true);
          }
          miseAjourStart();
        }
      }
    });
    listCourses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    JScrollPane scrollPaneCourses = new JScrollPane();
    panel_1.add(scrollPaneCourses, BorderLayout.CENTER);
    scrollPaneCourses.setPreferredSize(new Dimension(150, 270));
    scrollPaneCourses.setViewportView(listCourses);
    
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
        if(listCourses.getSelectedIndex()>-1)
        {
          Course cir = listCourses.getSelectedValue();
          UiCodes ihm = new UiCodes(listCourses.getSelectedValue(), easyGec);
          ihm.setLocationRelativeTo(UiEasyGec.this);
          ihm.setVisible(true);
          listCodes.setListData(listCourses.getSelectedValue().getCodes().getCodes());
          if(listCourses.getSelectedValue().getCodes().getCodes().size()>0)
          {
            listCodes.setSelectedIndex(listCourses.getSelectedValue().getCodes().getCodes().size()-1);
          }
          Collections.sort(easyGec.getCourse().getCourses());
          listCourses.repaint();
          listCourses.setSelectedValue(cir, true);
        }
      }
    });
    btnCodePlus.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/plus.png")));
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
          int resultValue = -1;
          try
          {
            resultValue = (int) JOptionPane.showInputDialog(UiEasyGec.this, EasyGec.getLanguages().getText("85", EasyGec.getLang()) ,
                EasyGec.getLanguages().getText("84", EasyGec.getLang()), JOptionPane.OK_CANCEL_OPTION, null, codes, codes[0]);
          }
          catch (NullPointerException e)
          {
            // TODO: handle exception
          }
          if(resultValue > 0)
          {
            int index = listCodes.getSelectedIndex();
            listCourses.getSelectedValue().getCodes().getCodes().set(index, resultValue);
            listCodes.setListData(listCourses.getSelectedValue().getCodes().getCodes());
            if(listCourses.getSelectedValue().getCodes().getCodes().size()>0)
            {
              listCodes.setSelectedIndex(index);
            }
          }
        }
      }
    });
    btnModifier.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/search.png")));
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
          int resultValue = JOptionPane.showConfirmDialog(UiEasyGec.this, EasyGec.getLanguages().getText("87", EasyGec.getLang()) + listCodes.getSelectedValue().toString(),
              EasyGec.getLanguages().getText("86", EasyGec.getLang()), JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
          if(resultValue == 0)
          {
            listCourses.getSelectedValue().getCodes().getCodes().remove(listCodes.getSelectedIndex());
            listCodes.setListData(listCourses.getSelectedValue().getCodes().getCodes());
            if(listCourses.getSelectedValue().getCodes().getCodes().size()>0)
            {
              listCodes.setSelectedIndex(0);
            }
            Collections.sort(easyGec.getCourse().getCourses());
            listCourses.repaint();
          }
        }
      }
    });
    btnCodeSupprimer.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/delete.png")));
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
          listCourses.getSelectedValue().getCodes().upCode(listCodes.getSelectedValue(), index);
          listCodes.setListData(listCourses.getSelectedValue().getCodes().getCodes());
          if(listCourses.getSelectedValue().getCodes().getCodes().size()>0)
          {
            listCodes.setSelectedIndex(index-1);
          }
        }
      }
    });
    btnCodeMonter.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/up.png")));
    btnCodeMonter.setPreferredSize(new Dimension(32, 32));
    btnCodeMonter.setToolTipText("Monter d'un niveau.");
    panel_2.add(btnCodeMonter);
    
    btnNewButton = new JButton("");
    btnNewButton.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(listCodes.getSelectedIndex()>=0 && listCodes.getSelectedIndex()<listCourses.getSelectedValue().getCodes().getCodes().size()-1)
        {
          int index = listCodes.getSelectedIndex();
          listCourses.getSelectedValue().getCodes().downCode(listCodes.getSelectedValue(), index);
          listCodes.setListData(listCourses.getSelectedValue().getCodes().getCodes());
          if(listCourses.getSelectedValue().getCodes().getCodes().size()>0)
          {
            listCodes.setSelectedIndex(index+1);
          }
        }
      }
    });
    btnNewButton.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/down.png")));
    btnNewButton.setPreferredSize(new Dimension(32, 32));
    btnNewButton.setToolTipText("Descendre d'un niveau.");
    panel_2.add(btnNewButton);
    
    panel_3 = new JPanel();
    FlowLayout flowLayout_3 = (FlowLayout) panel_3.getLayout();
    flowLayout_3.setAlignment(FlowLayout.LEFT);
    panel_3.setPreferredSize(new Dimension(10, 130));
    panelCentre.add(panel_3, BorderLayout.SOUTH);
    
    panelStart = new JPanel();
    //panelStart.setPreferredSize(new Dimension(250, 10));
    panel_3.add(panelStart);
    panelStart.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Type de d\u00E9part", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    
    rdbtnDpartAuBoitier = new JRadioButton("D\u00E9part au boitier");
    rdbtnDpartAuBoitier.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        miseAjourStart();
      }
    });
    rdbtnDpartAuBoitier.setSelected(true);
    panelStart.add(rdbtnDpartAuBoitier);
    
    rdbtnDpartGroup = new JRadioButton("D\u00E9part group\u00E9");
    rdbtnDpartGroup.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        miseAjourStart();
      }
    });
    panelStart.add(rdbtnDpartGroup);
    
    panel_5 = new JPanel();
    panelStart.add(panel_5);
    
    spinnerHeures = new JSpinner();
    spinnerHeures.addChangeListener(new ChangeListener() 
    {
      public void stateChanged(ChangeEvent arg0) 
      {
        miseAjourHeureStart();
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
        miseAjourHeureStart();
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
        miseAjourHeureStart();
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
    
    rdbtnCourseClassicCourseEvaluator = new JRadioButton("Course en ligne");
    rdbtnCourseClassicCourseEvaluator.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        miseAjourClassicCourseEvaluator();
      }
    });
    rdbtnCourseClassicCourseEvaluator.setSelected(true);
    panelType.add(rdbtnCourseClassicCourseEvaluator);
    
    rdbtnCourseScoreCourseEvaluator = new JRadioButton("Course au score");
    rdbtnCourseScoreCourseEvaluator.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        miseAjourClassicCourseEvaluator();
      }
    });
    panelType.add(rdbtnCourseScoreCourseEvaluator);
    
    ButtonGroup btgp = new ButtonGroup();
    btgp.add(rdbtnCourseClassicCourseEvaluator);
    btgp.add(rdbtnCourseScoreCourseEvaluator);
    
    ButtonGroup btgph = new ButtonGroup();
    btgph.add(rdbtnDpartAuBoitier);
    btgph.add(rdbtnDpartGroup);
    
    panelOrienteers = new JPanel();
    panelOrienteers.setBorder(new TitledBorder(null, "Orienteers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelCentre.add(panelOrienteers, BorderLayout.WEST);
    panelOrienteers.setLayout(new BorderLayout(0, 0));
    
    final JList<Orienteer> listOrienteers = new JList<Orienteer>();
    listOrienteers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listOrienteers.addMouseListener(new MouseAdapter() 
    {
      @Override
      public void mouseClicked(MouseEvent arg0) 
      {
        if (arg0.getClickCount() == 2) 
        {
          buttonOrienteerModif.doClick();
        } 
      }
    });
    
    JScrollPane scrollPane_1 = new JScrollPane();
    scrollPane_1.setPreferredSize(new Dimension(200, 100));
    scrollPane_1.setViewportView(listOrienteers);
    panelOrienteers.add(scrollPane_1, BorderLayout.CENTER);
    
    panel_8 = new JPanel();
    panel_8.setPreferredSize(new Dimension(50, 10));
    panelOrienteers.add(panel_8, BorderLayout.EAST);
    
    buttonChargerOrienteers = new JButton("");
    buttonChargerOrienteers.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        // On ouvre le navigateur
        JFileChooser chooser = new JFileChooser();
        FileExtensionFilter filter = new FileExtensionFilter();
        filter.addExtension("csv");
        filter.addExtension("ssv");
        filter.setDescription("CSV Files");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(easyGec.getRepertoire()));
        
        int returnVal = chooser.showOpenDialog(UiEasyGec.this);
        // Si un fichier a �t� choisi
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
          easyGec.getOrienteers().getOrienteers().clear();
          CsvOrienteers.importer(easyGec, chooser.getSelectedFile().getAbsolutePath());
          listOrienteers.setListData(easyGec.getOrienteers().getOrienteers());
        }
      }
    });
    buttonChargerOrienteers.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/csv.png")));
    buttonChargerOrienteers.setToolTipText("Importer des orienteers.");
    buttonChargerOrienteers.setPreferredSize(new Dimension(32, 32));
    panel_8.add(buttonChargerOrienteers);
    
    buttonOrienteerPlus = new JButton("");
    buttonOrienteerPlus.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        UiOrienteer ihm = new UiOrienteer(easyGec, null);
        ihm.setLocationRelativeTo(UiEasyGec.this);
        //ihm.pack();
        ihm.setVisible(true);
        listOrienteers.setListData(easyGec.getOrienteers().getOrienteers());
      }
    });
    buttonOrienteerPlus.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/plus.png")));
    buttonOrienteerPlus.setToolTipText("Ajouter un orienteer.");
    buttonOrienteerPlus.setPreferredSize(new Dimension(32, 32));
    panel_8.add(buttonOrienteerPlus);
    
    buttonOrienteerModif = new JButton("");
    buttonOrienteerModif.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        if(listOrienteers.getSelectedIndex()>-1)
        {
          UiOrienteer ihm = new UiOrienteer(easyGec, listOrienteers.getSelectedValue());
          ihm.setLocationRelativeTo(UiEasyGec.this);
          ihm.setVisible(true);
          listOrienteers.setListData(easyGec.getOrienteers().getOrienteers());
        }
      }
    });
    buttonOrienteerModif.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/search.png")));
    buttonOrienteerModif.setToolTipText("Modifier l'orienteer.");
    buttonOrienteerModif.setPreferredSize(new Dimension(32, 32));
    panel_8.add(buttonOrienteerModif);
    
    buttonOrienteerMoins = new JButton("");
    buttonOrienteerMoins.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        if(listOrienteers.getSelectedIndex()>-1)
        {
          int resultValue = JOptionPane.showConfirmDialog(UiEasyGec.this, EasyGec.getLanguages().getText("89", EasyGec.getLang()) + listOrienteers.getSelectedValue().getName(),
              EasyGec.getLanguages().getText("88", EasyGec.getLang()), JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
          if(resultValue == 0)
          {
            easyGec.getOrienteers().getOrienteers().remove(listOrienteers.getSelectedValue());
            listOrienteers.setListData(easyGec.getOrienteers().getOrienteers());
            if(easyGec.getOrienteers().getOrienteers().size()>0)
            {
              listOrienteers.setSelectedIndex(0);
            }
          }
        }
      }
    });
    buttonOrienteerMoins.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/delete.png")));
    buttonOrienteerMoins.setToolTipText("Supprimer l'orienteer.");
    buttonOrienteerMoins.setPreferredSize(new Dimension(32, 32));
    panel_8.add(buttonOrienteerMoins);
    
    initCodes();

    easyGec = new EasyGec(UiEasyGec.this);

    XmlLanguage.lecture(easyGec, UiEasyGec.class.getResource("/lang.xml"));
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
        listCourses.repaint();
        listOrienteers.repaint();
        listCodes.setListData(new Vector<Integer>());
        listCodes.repaint();
      }
    });
    btnNouveau.setToolTipText("Nouvelle organisation.");
    btnNouveau.setPreferredSize(new Dimension(32, 32));
    btnNouveau.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/new.png")));
    panelFichier.add(btnNouveau);
    
    btnOuvrir = new JButton("");
    btnOuvrir.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        Enregistrer();
        // On ouvre le navigateur
        JFileChooser chooser = new JFileChooser();
        FileExtensionFilter filter = new FileExtensionFilter();
        filter.addExtension("egc");
        filter.setDescription("Fichiers EasyGec");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(easyGec.getRepertoire()));
        
        int returnVal = chooser.showOpenDialog(UiEasyGec.this);
        // Si un fichier a �t� choisi
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
          easyGec.clear();
          easyGec.setFichier(chooser.getSelectedFile().getAbsolutePath());
          easyGec.setRepertoire(chooser.getSelectedFile().getAbsolutePath());
          Config.enregistre(easyGec, "config.xml");
          XmlOrganization.lecture(easyGec, easyGec.getFichier());
          setTitre();
          Collections.sort(easyGec.getCourse().getCourses());
          listCourses.setListData(easyGec.getCourse().getCourses());
          if(easyGec.getCourse().getCourses().size()>0)
          {
            listCourses.setSelectedIndex(0);
          }
          listOrienteers.setListData(easyGec.getOrienteers().getOrienteers());
        }
      }
    });
    btnOuvrir.setToolTipText("Ouvrir une organisation.");
    btnOuvrir.setPreferredSize(new Dimension(32, 32));
    btnOuvrir.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/folder.png")));
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
          FileExtensionFilter filter = new FileExtensionFilter();
          filter.addExtension("egc");
          filter.setDescription("Fichiers EasyGec");
          chooser.setFileFilter(filter);
          chooser.setCurrentDirectory(new File(easyGec.getRepertoire()));
          
          int returnVal = chooser.showSaveDialog(UiEasyGec.this);
          // Si un fichier a �t� choisi
          if(returnVal == JFileChooser.APPROVE_OPTION) 
          {
            easyGec.setFichier((Utility.verifExtension(chooser.getSelectedFile().getAbsolutePath(), ".egc")));
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
    btnEnregistrer.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/save32.png")));
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
        FileExtensionFilter filter = new FileExtensionFilter();
        filter.addExtension("xml");
        filter.setDescription("Fichiers xml");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(easyGec.getRepertoire()));
        
        int returnVal = chooser.showOpenDialog(UiEasyGec.this);
        // Si un fichier a �t� choisi
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
          String fichier = chooser.getSelectedFile().getAbsolutePath();
          XmlOcadCourse.importer(easyGec,fichier);
          Collections.sort(easyGec.getCourse().getCourses());
          listCourses.setListData(easyGec.getCourse().getCourses());
          listCourses.repaint();
          if(listCourses.getModel().getSize()>0)
          {
            listCourses.setSelectedIndex(0);
          }
          else
          {
            listCourses.setSelectedIndex(-1);
          }
        }
      }
    });
    panel.add(btnImportOcad);
    btnImportOcad.setToolTipText("Importer les courses \u00E0 partir d'OCAD.");
    btnImportOcad.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/ocad.png")));
    btnImportOcad.setPreferredSize(new Dimension(32, 32));
    
    btnExportHtml = new JButton("");
    btnExportHtml.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        // Lancement du fichier d'aide de l'application
        String adresse = easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLanguages().getText("114", EasyGec.getLang()) + ".html";
        DesktopApi.edit(new File(adresse));
      }
    });
    
    btnImportCourseAbc = new JButton();

    // Why is this button disabled?
    btnImportCourseAbc.setVisible(false);
    btnImportCourseAbc.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        // On ouvre le navigateur
        JFileChooser chooser = new JFileChooser();
        FileExtensionFilter filter = new FileExtensionFilter();
        filter.addExtension("csv");
        filter.setDescription("Fichiers cvs");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(easyGec.getRepertoire()));
        
        int returnVal = chooser.showOpenDialog(UiEasyGec.this);
        // Si un fichier a �t� choisi
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
          String fichier = chooser.getSelectedFile().getAbsolutePath();
          CsvCourses.importer(easyGec,fichier);
          Collections.sort(easyGec.getCourse().getCourses());
          listCourses.setListData(easyGec.getCourse().getCourses());
          listCourses.repaint();
          if(listCourses.getModel().getSize()>0)
          {
            listCourses.setSelectedIndex(0);
          }
          else
          {
            listCourses.setSelectedIndex(-1);
          }
        }
      }
    });
    btnImportCourseAbc.setPreferredSize(new Dimension(32, 32));
    btnImportCourseAbc.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/csv.png")));
    panel.add(btnImportCourseAbc);
    
    btnResultsGlobaux = new JButton("");
    btnResultsGlobaux.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        // Lancement du fichier
        String adresse = easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLanguages().getText("113", EasyGec.getLang()) + ".html";
        DesktopApi.edit(new File(adresse));
      }
    });
    btnResultsGlobaux.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/podium.png")));
    btnResultsGlobaux.setPreferredSize(new Dimension(32, 32));
    panel.add(btnResultsGlobaux);
    
    btnResultsDetailled = new JButton("");
    btnResultsDetailled.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        // Lancement du fichier d'aide de l'application
        String adresse = easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLanguages().getText("115", EasyGec.getLang()) + ".html";
        DesktopApi.edit(new File(adresse));
      }
    });
    btnResultsDetailled.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/timer.png")));
    btnResultsDetailled.setPreferredSize(new Dimension(32, 32));
    panel.add(btnResultsDetailled);
    panel.add(btnExportHtml);
    btnExportHtml.setToolTipText("Ouvrir les r\u00E9sultats globaux en HTML.");
    btnExportHtml.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/global.png")));
    btnExportHtml.setPreferredSize(new Dimension(32, 32));
    
    btnExportSplits = new JButton("");
    btnExportSplits.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        // Lancement du fichier d'aide de l'application
        String adresse = easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLanguages().getText("117", EasyGec.getLang()) + ".html";
        DesktopApi.edit(new File(adresse));
      }
    });
    panel.add(btnExportSplits);
    btnExportSplits.setToolTipText("Ouvrir les r\u00E9sultats d\u00E9taill\u00E9s en HTML.");
    btnExportSplits.setPreferredSize(new Dimension(32, 32));
    btnExportSplits.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/detailed.png")));
    
    btnSI = new JButton("");
    btnSI.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(easyGec.getCourse().getCourses().size()>0)
        {
          easyGec.getSiResults().getSiResults().clear();
          easyGec.getOrienteersSi().getOrienteers().clear();
          UiImportSi ihm = new UiImportSi(UiEasyGec.this);
          ihm.setLocationRelativeTo(UiEasyGec.this);
          ihm.setVisible(true);
        }
      }
    });
    
    btnEffacerResults = new JButton("");
    btnEffacerResults.setToolTipText(EasyGec.getLanguages().getText("120", EasyGec.getLang()));
    btnEffacerResults.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        if(easyGec.getResults().getResults().size()>0)
        {
          int resultValue = JOptionPane.showConfirmDialog(UiEasyGec.this, EasyGec.getLanguages().getText("122", EasyGec.getLang()),
              EasyGec.getLanguages().getText("121", EasyGec.getLang()), JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
          if(resultValue == 0)
          {
            easyGec.getResults().getResults().removeAllElements();
          }
        }
      }
    });
    btnEffacerResults.setPreferredSize(new Dimension(32, 32));
    btnEffacerResults.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/delete.png")));
    panel.add(btnEffacerResults);
    btnSI.setToolTipText("Associer un Log SI et une liste d'orienteers.");
    btnSI.setPreferredSize(new Dimension(32, 32));
    btnSI.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/si.png")));
    panel.add(btnSI);
    
    panel_6 = new JPanel();
    panel_6.setBorder(new TitledBorder(null, "Aide", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelNord.add(panel_6);
    
    btnAide = new JButton("");
    btnAide.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        try
        {
          // Lancement du fichier d'aide de l'application
          String adresse = new File(".").getCanonicalPath().toString();
          adresse = adresse + "/" + EasyGec.getLanguages().getText("0", EasyGec.getLang());
          DesktopApi.edit(new File(adresse));
        }
        catch (IOException e1)
        {
          JOptionPane.showMessageDialog(UiEasyGec.this,"Erreur de lancement de l'aide : "+e1.getClass().getName());
        }
      }
    });
    btnAide.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/help.png")));
    btnAide.setPreferredSize(new Dimension(32, 32));
    btnAide.setToolTipText("Afficher le fichier d'aide.");
    panel_6.add(btnAide);
    
    btnInfo = new JButton("");
    btnInfo.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        JOptionPane.showMessageDialog(UiEasyGec.this, EasyGec.getLanguages().getText("57", EasyGec.getLang()) + "\nD�veloppeur : Thierry PORRET.\nTraducteurs : Robert MARIQUE, Michael HOCK et Luc CLOOSTERMANS.");
      }
    });
    btnInfo.setToolTipText("Afficher la boite d'information.");
    btnInfo.setPreferredSize(new Dimension(32, 32));
    btnInfo.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/info.png")));
    panel_6.add(btnInfo);
    
    panel_9 = new JPanel();
    panel_9.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Param\u00E8tres", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    panelNord.add(panel_9);

    HashMap<Boolean, ImageIcon>autoResultIcon = new HashMap<Boolean, ImageIcon>() {{
      put(false, new ImageIcon(UiEasyGec.class.getResource("/icons/ui.png")));
      put(true, new ImageIcon(UiEasyGec.class.getResource("/icons/smiley.png")));
    }};
    
    btnMode = new JButton("");
    btnMode.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        autoResult = !autoResult;
        btnMode.setIcon(autoResultIcon.get(autoResult));
      }
    });
    btnMode.setIcon(autoResultIcon.get(autoResult));
    btnMode.setToolTipText("Basculer de mode de lecture des chips.");
    btnMode.setPreferredSize(new Dimension(32, 32));
    panel_9.add(btnMode);
    
    btnLang = new JButton("");
    btnLang.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent e) 
      {
        String resultValue = "";
        try
        {
          resultValue = (String) JOptionPane.showInputDialog(UiEasyGec.this, EasyGec.getLanguages().getText("59", EasyGec.getLang()) ,
              EasyGec.getLanguages().getText("58", EasyGec.getLang()), JOptionPane.OK_CANCEL_OPTION, null, easyGec.getLangs().toArray(), easyGec.getLangUsed());
        }
        catch (NullPointerException ee)
        {
          // TODO: handle exception
        }
        if(resultValue != null)
        {
          easyGec.setLangUsed(resultValue);
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
          int resultValue = easyGec.getTempo();
          resultValue = (int) JOptionPane.showInputDialog(UiEasyGec.this, "" ,
                  EasyGec.getLanguages().getText("110", EasyGec.getLang()), JOptionPane.OK_CANCEL_OPTION, null, tempos, easyGec.getTempo());
          easyGec.setTempo(resultValue);
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
        UiMapping ihm = new UiMapping(easyGec);
        ihm.setLocationRelativeTo(UiEasyGec.this);
        ihm.setVisible(true);
      }
    });
    btnAbc.setPreferredSize(new Dimension(32, 32));
    btnAbc.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/abc32.png")));
    panel_9.add(btnAbc);
    
    btnConfig.setToolTipText("Temporisation");
    btnConfig.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/configure.png")));
    btnConfig.setPreferredSize(new Dimension(32, 32));
    panel_9.add(btnConfig);
    btnLang.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/lang.png")));
    btnLang.setPreferredSize(new Dimension(32, 32));
    btnLang.setToolTipText("Choisissez votre Language");
    panel_9.add(btnLang);
    
    panelLectureChip = new JPanel();
    FlowLayout flowLayout_1 = (FlowLayout) panelLectureChip.getLayout();
    flowLayout_1.setAlignment(FlowLayout.LEFT);
    panelLectureChip.setBorder(new TitledBorder(null, "Lecture des chips", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelNord.add(panelLectureChip);
    
    lblPortCom = new JLabel("Port COM :");
    panelLectureChip.add(lblPortCom);
    
    final JComboBox<String> comboBoxPortCom = new JComboBox<String>();
    comboBoxPortCom.setPreferredSize(new Dimension(100, 20));
    panelLectureChip.add(comboBoxPortCom);
    
    btnInitCom = new JButton("");
    btnInitCom.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        cr = new RailCommunication();
        comboBoxPortCom.setModel(new DefaultComboBoxModel<>(cr.getListePortRail()));
      }
    });
    btnInitCom.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/reload.png")));
    btnInitCom.setPreferredSize(new Dimension(32, 32));
    btnInitCom.setToolTipText("R\u00E9initialiser la liste des ports.");
    panelLectureChip.add(btnInitCom);
    
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
          btnLancerLecture.setIcon(new ImageIcon(getClass().getResource("/icons/waiting.png")));
          siReaderEnCours = true;
          spinner.setEnabled(false);
          spinner_1.setEnabled(false);
        }
        else
        {
          easyGec.getSiHandler().stop();
          btnLancerLecture.setIcon(new ImageIcon(getClass().getResource("/icons/play.png")));
          btnLancerLecture.setToolTipText("Connecter la station maitre.");
          siReaderEnCours = false;
          spinner.setEnabled(true);
          spinner_1.setEnabled(true);
        }
      }
    });
    
    label = new JLabel("Heure z\u00E9ro : ");
    panelLectureChip.add(label);
    
    spinner = new JSpinner();
    spinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
    panelLectureChip.add(spinner);
    
    JLabel label_1 = new JLabel("H");
    panelLectureChip.add(label_1);
    
    spinner_1 = new JSpinner();
    spinner_1.setModel(new SpinnerNumberModel(0, 0, 59, 1));
    panelLectureChip.add(spinner_1);
    
    JLabel label_2 = new JLabel("MN   ");
    panelLectureChip.add(label_2);
    btnLancerLecture.setToolTipText(EasyGec.getLanguages().getText("31", EasyGec.getLang()));
    btnLancerLecture.setIcon(new ImageIcon(UiEasyGec.class.getResource("/icons/play.png")));
    btnLancerLecture.setPreferredSize(new Dimension(32, 32));
    panelLectureChip.add(btnLancerLecture);
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
    String name = "";
    if(easyGec.isAbc())
    {
      name = "AbcGec ";
    }
    else
    {
      name = "EasyGec ";
    }
    setTitle(name + easyGec.getVersion() + " - " + Utility.getName(easyGec.getFichier()));
  }
  
  private void miseAjourStart()
  {
    if(listCourses.getSelectedIndex()>-1)
    {
      if(rdbtnDpartAuBoitier.isSelected())
      {
        spinnerHeures.setEnabled(false);
        spinnerMinutes.setEnabled(false);
        spinnerSecondes.setEnabled(false);
        listCourses.getSelectedValue().setStartBoitier(true);
      }
      else
      {
        int heure = TimeManager.getHeures(listCourses.getSelectedValue().getHeureStart());
        int minute = TimeManager.getMinutes(listCourses.getSelectedValue().getHeureStart());
        int seconde = TimeManager.getSecondes(listCourses.getSelectedValue().getHeureStart());
        spinnerHeures.setValue(heure);
        spinnerMinutes.setValue(minute);
        spinnerSecondes.setValue(seconde);
        listCourses.getSelectedValue().setStartBoitier(false);
        spinnerHeures.setEnabled(true);
        spinnerMinutes.setEnabled(true);
        spinnerSecondes.setEnabled(true);
      }
      listCourses.repaint();
    }
  }
  
  private void miseAjourClassicCourseEvaluator()
  {
    if(listCourses.getSelectedIndex()>-1)
    {
      if(rdbtnCourseClassicCourseEvaluator.isSelected())
      {
        listCourses.getSelectedValue().setClassicCourseEvaluator(true);
      }
      else
      {
        listCourses.getSelectedValue().setClassicCourseEvaluator(false);
      }
      listCourses.repaint();
    }
  }
  
  private void miseAjourHeureStart()
  {
    if(listCourses.getSelectedIndex()>-1)
    {
      listCourses.getSelectedValue().setHeureStart(new Date((Integer)spinnerHeures.getValue()*3600000 +
          (Integer)spinnerMinutes.getValue()*60000 + (Integer)spinnerSecondes.getValue()*1000));
      listCourses.repaint();
    }
  }
  
  private boolean existeRepertoire()
  {
    if(!easyGec.getFichier().equals(""))
    {
      File dossier = new File(Utility.getRepertoire(easyGec.getFichier()));
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
      OkNokButtonTask task = new OkNokButtonTask(UiEasyGec.this, btnEnregistrer, true);
      task.execute();
      XmlOrganization.enregistre(easyGec, easyGec.getFichier());
      enregistreResults();
    }
    else
    {
      OkNokButtonTask task = new OkNokButtonTask(UiEasyGec.this, btnEnregistrer, false);
      task.execute();
    }
  }
  
  public void enregistreResults()
  {
    easyGec.CalculResults();
    if(existeRepertoire())
    {
      OkNokButtonTask task = new OkNokButtonTask(UiEasyGec.this, btnExportHtml, true);
      task.execute();
      OkNokButtonTask task2 = new OkNokButtonTask(UiEasyGec.this, btnResultsGlobaux, true);
      task2.execute();
      HtmlGlobalResult.exporterHtml(easyGec, easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLanguages().getText("113", EasyGec.getLang()) + ".html");
      easyGec.getResultsByIdentifiers().trier();
      Collections.sort(easyGec.getResultsByIdentifiers().getResults());
      HtmlGlobalResult.exporterHtmlParIdentifier(easyGec, easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLanguages().getText("114", EasyGec.getLang()) + ".html");
      CsvResults.exporter(easyGec, easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLanguages().getText("113", EasyGec.getLang()) + ".csv");
    }
    else
    {
      OkNokButtonTask task = new OkNokButtonTask(UiEasyGec.this, btnExportHtml, false);
      task.execute();
      OkNokButtonTask task2 = new OkNokButtonTask(UiEasyGec.this, btnResultsGlobaux, false);
      task2.execute();
    }
    enregistreResultsDetailles();
  }
  
  public void enregistreResultsDetailles()
  {
    easyGec.CalculResultsDetaille();
    Collections.sort(easyGec.getResults().getResults(), new ChipResultComparator(1));
    if(existeRepertoire())
    {
      OkNokButtonTask task = new OkNokButtonTask(UiEasyGec.this, btnExportSplits, true);
      task.execute();
      OkNokButtonTask task2 = new OkNokButtonTask(UiEasyGec.this, btnResultsDetailled, true);
      task2.execute();
      HtmlDetailedResult.exporterHtml(easyGec, easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLanguages().getText("115", EasyGec.getLang()) + ".html");
      easyGec.getDetailedResultsByIdentifiers().trier();
      Collections.sort(easyGec.getDetailedResultsByIdentifiers().getResults());
      HtmlDetailedResult.exporterHtmlParIdentifier(easyGec, easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLanguages().getText("117", EasyGec.getLang()) + ".html");
      CsvDetailedResults.exporter(easyGec, easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLanguages().getText("115", EasyGec.getLang()) + ".csv");
      CsvDetailedResults.exporterSimple(easyGec, easyGec.getFichier().substring(0, easyGec.getFichier().length()-4) + EasyGec.getLanguages().getText("116", EasyGec.getLang()) + ".csv");
    }
    else
    {
      OkNokButtonTask task = new OkNokButtonTask(UiEasyGec.this, btnExportSplits, false);
      task.execute();
      OkNokButtonTask task2 = new OkNokButtonTask(UiEasyGec.this, btnResultsDetailled, false);
      task2.execute();
    }
  }

  public void stationStatus(String status) 
  {
    if(siReaderEnCours)
    {
      if( status.equals("Ready") ) 
      { //$NON-NLS-1$
        btnLancerLecture.setIcon(new ImageIcon(getClass().getResource("/icons/stop.png")));
        btnLancerLecture.setToolTipText(EasyGec.getLanguages().getText("32", EasyGec.getLang()));
        return;
      }
    }
  }
  
  private void Enregistrer()
  {
    // Do not save empty event
    if (existeRepertoire()) {
      int resultValue = JOptionPane.showConfirmDialog(UiEasyGec.this, EasyGec.getLanguages().getText("45", EasyGec.getLang()),
      EasyGec.getLanguages().getText("46", EasyGec.getLang()), JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION);
      if(resultValue == 0) {
        btnEnregistrer.doClick();
      }
    }

    // // prev
    // int resultValue = JOptionPane.showConfirmDialog(UiEasyGec.this, EasyGec.getLanguages().getText("45", EasyGec.getLang()),
    //   EasyGec.getLanguages().getText("46", EasyGec.getLang()), JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION);
    // if(resultValue == 0)
    // {
    //   btnEnregistrer.doClick();
    // }
  }
  
  private void changeLanguage()
  {
    int lang = EasyGec.getLang();
    // BorderTitle
    panel_7.setBorder(new TitledBorder(null, EasyGec.getLanguages().getText("1", lang), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelFichier.setBorder(new TitledBorder(null, EasyGec.getLanguages().getText("2", lang), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelCentre.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), EasyGec.getLanguages().getText("4", lang), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), EasyGec.getLanguages().getText("5", lang), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    panelCodes.setBorder(new TitledBorder(null, EasyGec.getLanguages().getText("6", lang), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelStart.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), EasyGec.getLanguages().getText("7", lang), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelType.setBorder(new TitledBorder(null, EasyGec.getLanguages().getText("8", lang), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panelOrienteers.setBorder(new TitledBorder(null, EasyGec.getLanguages().getText("9", lang), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panel.setBorder(new TitledBorder(null, EasyGec.getLanguages().getText("10", lang), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panel_6.setBorder(new TitledBorder(null, EasyGec.getLanguages().getText("3", lang), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panel_9.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), EasyGec.getLanguages().getText("11", lang), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    panelLectureChip.setBorder(new TitledBorder(null, EasyGec.getLanguages().getText("12", lang), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    
    // Label
    lblPortCom.setText(EasyGec.getLanguages().getText("13", lang));
    label.setText(EasyGec.getLanguages().getText("14", lang));
    
    // radioBouton
    rdbtnDpartAuBoitier.setText(EasyGec.getLanguages().getText("15", lang));
    rdbtnDpartGroup.setText(EasyGec.getLanguages().getText("16", lang));
    rdbtnCourseScoreCourseEvaluator.setText(EasyGec.getLanguages().getText("17", lang));
    rdbtnCourseClassicCourseEvaluator.setText(EasyGec.getLanguages().getText("18", lang));
    
    // ToolTip
    btnNouveau.setToolTipText(EasyGec.getLanguages().getText("19", lang));
    btnOuvrir.setToolTipText(EasyGec.getLanguages().getText("20", lang));
    btnEnregistrer.setToolTipText(EasyGec.getLanguages().getText("21", lang));
    btnImportOcad.setToolTipText(EasyGec.getLanguages().getText("22", lang));
    btnExportHtml.setToolTipText(EasyGec.getLanguages().getText("23", lang));
    btnResultsGlobaux.setToolTipText(EasyGec.getLanguages().getText("23", lang));
    btnResultsDetailled.setToolTipText(EasyGec.getLanguages().getText("24", lang));
    btnExportSplits.setToolTipText(EasyGec.getLanguages().getText("24", lang));
    btnSI.setToolTipText(EasyGec.getLanguages().getText("25", lang));
    btnAide.setToolTipText(EasyGec.getLanguages().getText("26", lang));
    btnInfo.setToolTipText(EasyGec.getLanguages().getText("27", lang));
    btnMode.setToolTipText(EasyGec.getLanguages().getText("28", lang));
    btnLang.setToolTipText(EasyGec.getLanguages().getText("29", lang));
    btnInitCom.setToolTipText(EasyGec.getLanguages().getText("30", lang));
    btnLancerLecture.setToolTipText(EasyGec.getLanguages().getText("31", lang));
    buttonChargerOrienteers.setToolTipText(EasyGec.getLanguages().getText("33", lang));
    buttonOrienteerPlus.setToolTipText(EasyGec.getLanguages().getText("34", lang));
    buttonOrienteerModif.setToolTipText(EasyGec.getLanguages().getText("35", lang));
    buttonOrienteerMoins.setToolTipText(EasyGec.getLanguages().getText("36", lang));
    btnCoursePlus.setToolTipText(EasyGec.getLanguages().getText("37", lang));
    btnCourseModifier.setToolTipText(EasyGec.getLanguages().getText("38", lang));
    btnCourseSupprimer.setToolTipText(EasyGec.getLanguages().getText("39", lang));
    btnCodePlus.setToolTipText(EasyGec.getLanguages().getText("40", lang));
    btnModifier.setToolTipText(EasyGec.getLanguages().getText("41", lang));
    btnCodeSupprimer.setToolTipText(EasyGec.getLanguages().getText("42", lang));
    btnCodeMonter.setToolTipText(EasyGec.getLanguages().getText("43", lang));
    btnNewButton.setToolTipText(EasyGec.getLanguages().getText("44", lang));
    btnConfig.setToolTipText(EasyGec.getLanguages().getText("110", lang));
    btnImportCourseAbc.setToolTipText(EasyGec.getLanguages().getText("111", lang));
    btnAbc.setToolTipText(EasyGec.getLanguages().getText("112", lang));
    btnEffacerResults.setToolTipText(EasyGec.getLanguages().getText("120", lang));
    buttonUnss.setToolTipText(EasyGec.getLanguages().getText("123", lang));
  }
}
