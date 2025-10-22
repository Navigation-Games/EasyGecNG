package ui;

import java.awt.BorderLayout;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.border.TitledBorder;
import javax.swing.text.html.HTMLDocument;
import javax.swing.JButton;
import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;

import core.EasyGec;

import utils.ScoreCourseEvaluator;
import utils.ClassicCourseEvaluator;
import utils.TimeManager;

import to.Course;
import to.ChipResult;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class UiChipResult extends JDialog
{

  /**
   * 
   */
  private static final long serialVersionUID = 8959944820685395136L;
  private UiEasyGec ihm;
  private ChipResult rp;
  private JPanel contentPane;
  private JTextField textFieldIdentifier;
  private JComboBox<Course> comboBoxCourses;
  private Vector<Integer> resultsChip = new Vector<Integer>();
  private JEditorPane editorPane;
  private JButton btnOk;
  private JButton btnReload;


  /**
   * Create the frame.
   * @param ihm an instance of the application
   * @param rp  an instance of ChipResult from /src/to
   *
   * UiChipResult only called from SIReaderHandler
   */
  public UiChipResult(final UiEasyGec ihm, final ChipResult rp)
  {

    /*
      creates 'New Result' tab
     */
    setModal(true);
    setResizable(false);
    setTitle(EasyGec.getLanguages().getText("90", EasyGec.getLang()));
    setIconImage(Toolkit.getDefaultToolkit().getImage(UiChipResult.class.getResource("/icons/easy.png")));
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBounds(100, 100, 450, 542);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);

    /*
      creates 'actions' panel
     */
    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(60, 10));
    panel.setBorder(new TitledBorder(null, EasyGec.getLanguages().getText("104", EasyGec.getLang()), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    contentPane.add(panel, BorderLayout.EAST);

    /*
      creates 'Validate the result' button
     */
    btnOk = new JButton("");
    btnOk.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(comboBoxCourses.getSelectedIndex()>-1)
        {
          addChipResult();
          ihm.btnEnregistrer.doClick();
          dispose();
        }
      }
    });
    btnOk.setIcon(new ImageIcon(UiChipResult.class.getResource("/icons/ok64.png")));
    btnOk.setPreferredSize(new Dimension(48, 48));
    btnOk.setToolTipText(EasyGec.getLanguages().getText("91", EasyGec.getLang()));
    panel.add(btnOk);

    /*
      creates 'validate and print result' button
     */
    JButton btnOkPrint = new JButton("");
    btnOkPrint.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(comboBoxCourses.getSelectedIndex()>-1)
        {
          addChipResult();
          ihm.btnEnregistrer.doClick();
          printResult();
          dispose();
        }
      }
    });
    btnOkPrint.setPreferredSize(new Dimension(48, 48));
    btnOkPrint.setToolTipText(EasyGec.getLanguages().getText("92", EasyGec.getLang()));
    btnOkPrint.setIcon(new ImageIcon(UiChipResult.class.getResource("/icons/okPrint.png")));
    panel.add(btnOkPrint);

    /*
      creates 'print the result' button
     */
    JButton btnPrint = new JButton("");
    btnPrint.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(comboBoxCourses.getSelectedIndex()>-1)
        {
          printResult();
        }
      }
    });
    btnPrint.setIcon(new ImageIcon(UiChipResult.class.getResource("/icons/print.png")));
    btnPrint.setToolTipText(EasyGec.getLanguages().getText("93", EasyGec.getLang()));
    btnPrint.setPreferredSize(new Dimension(48, 48));
    panel.add(btnPrint);

    /*
      creates 'cancel the result' button
     */
    JButton btnAnnuler = new JButton("");
    btnAnnuler.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        dispose();
      }
    });
    btnAnnuler.setToolTipText(EasyGec.getLanguages().getText("94", EasyGec.getLang()));
    btnAnnuler.setPreferredSize(new Dimension(48, 48));
    btnAnnuler.setIcon(new ImageIcon(UiChipResult.class.getResource("/icons/back.png")));
    panel.add(btnAnnuler);

    /*
      creates 'result' panel
     */
    JPanel panel_1 = new JPanel();
    FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
    flowLayout.setAlignment(FlowLayout.LEFT);
    panel_1.setBorder(new TitledBorder(null, EasyGec.getLanguages().getText("95", EasyGec.getLang()), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    contentPane.add(panel_1, BorderLayout.CENTER);

    /*
      add 'ID' field to result panel
     */
    JPanel panel_2 = new JPanel();
    panel_1.add(panel_2);
    
    JLabel lblIdentifier = new JLabel(EasyGec.getLanguages().getText("96", EasyGec.getLang()));
    panel_2.add(lblIdentifier);
    
    textFieldIdentifier = new JTextField();
    textFieldIdentifier.setMinimumSize(new Dimension(273, 20));
    textFieldIdentifier.setPreferredSize(new Dimension(273, 20));
    panel_2.add(textFieldIdentifier);
    textFieldIdentifier.setColumns(18);

    /*
      add 'Course' field to result panel
     */
    JPanel panel_3 = new JPanel();
    panel_1.add(panel_3);
    
    JLabel lblCourse = new JLabel(EasyGec.getLanguages().getText("97", EasyGec.getLang()));
    panel_3.add(lblCourse);
    
    comboBoxCourses = new JComboBox<Course>();
    comboBoxCourses.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(comboBoxCourses.getSelectedIndex()>-1)
        {
          rp.setCourse((Course) comboBoxCourses.getSelectedItem());
          rp.setIdentifier(textFieldIdentifier.getText());
          rp.setDatas(ihm.easyGec.getOrienteers().getDatas(textFieldIdentifier.getText()));
          rp.saveHtml();
          reLoadPage();
        }
      }
    });
    comboBoxCourses.setPreferredSize(new Dimension(248, 20));
    panel_3.add(comboBoxCourses);
    
    JPanel panel_4 = new JPanel();
    FlowLayout flowLayout_1 = (FlowLayout) panel_4.getLayout();
    flowLayout_1.setAlignment(FlowLayout.LEFT);
    panel_4.setPreferredSize(new Dimension(353, 400));
    //panel_4.setSize(220, 350);
    panel_1.add(panel_4);
    
    btnReload = new JButton("");
    btnReload.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(comboBoxCourses.getSelectedIndex()>-1)
        {
          reLoadPage();
        }
      }
    });
    panel_4.add(btnReload);
    btnReload.setToolTipText(EasyGec.getLanguages().getText("98", EasyGec.getLang()));
    btnReload.setPreferredSize(new Dimension(32, 32));
    btnReload.setIcon(new ImageIcon(UiChipResult.class.getResource("/icons/reload.png")));
    
    editorPane = new JEditorPane();
    editorPane.setEditable(false);
    editorPane.setPreferredSize(new Dimension(343, 350));
    //editorPane.setSize(220, 350);
    
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setViewportView(editorPane);
    panel_4.add(scrollPane);
    
    this.ihm = ihm;
    this.rp = rp;
    textFieldIdentifier.setText(this.ihm.easyGec.getOrienteers().getOrienteer(rp.getChip().getIdChip()));
    initCourses();
    textFieldIdentifier.selectAll();
    //reLoadPage();
    if(ihm.autoResult)
    {
      UiSmiley is;
      int min = getMinNbPM(); //min holds the number of missed checkpoints of the most accurate course (minimum missed checkpoints)

      //construct String 'missedIndex' of missed checkpoint indexes to pass to UiSmiley
      Vector<Integer> missedCheckpoints = rp.getMissed();
      int size = missedCheckpoints.size();
      String missedIndex = "";
      if (size>0) {
        for (int i = 0; i < size-1; i++) {
          missedIndex += missedCheckpoints.get(i);
          missedIndex += "\n";
        }
        missedIndex += missedCheckpoints.get(size-1);
      }
      String elapsedTime = TimeManager.fullTime(this.rp.finish-this.rp.start);
      String courseName = rp.getCourse().getName();

      long start = rp.getStart();
      long finish = rp.getFinish();
      // //dev print
      // System.out.println("start: " + start);
      // System.out.println("finish: " + finish);
      //make a local variable to pass to UiSmiley
      int okCourse = 0;
      if(start==-1) { //missed start control
        if(finish==-1) { //missed start and finish controls
          okCourse = 3;
        } else {
          okCourse = 1;
        }
      } else if(finish==-1) { //missed finish control
        okCourse = 2;
      }

      if(min == 0)
      {
        is = new UiSmiley(min, missedIndex, elapsedTime, courseName, ihm.easyGec, okCourse);
      }
      else
      {
        if(this.ihm.easyGec.isAbc())
        {
          is = new UiSmiley(min, missedIndex, rp.getTexteFormate(), courseName, ihm.easyGec, okCourse);
        }
        else
        {
          is = new UiSmiley(min, missedIndex, elapsedTime, courseName, ihm.easyGec, okCourse);
        }
      }
      is.setLocationRelativeTo(UiChipResult.this);
      //is.setVisible(true);
      /*
      try
      {
        Thread.sleep(ihm.easyGec.getTempo()*1000);
        //Thread.sleep(4000);
      }
      catch (InterruptedException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      is.dispose();*/
      doClickOk();
    }
  }

  /**
   * calculResultsChip():
   * calculates information about run comparing to every course in comboBoxCourses
   * determines trad or score-o course
   * adds result to resultsChip collection
   */
  private void calculResultsChip()
  {
    for(int i=0; i<comboBoxCourses.getItemCount(); i++)
    {
      int result = 0;
      if(comboBoxCourses.getItemAt(i).isClassicCourseEvaluator())
      {
        ClassicCourseEvaluator el = new ClassicCourseEvaluator(comboBoxCourses.getItemAt(i).getCodesToArray(), rp.getCodes(), rp.getTime());
        rp.okPm = el.getOkPm();
      }
      else
      {
        ScoreCourseEvaluator as = new ScoreCourseEvaluator(comboBoxCourses.getItemAt(i).getCodesToArray(), rp.getCodes(), rp.getTime());
        rp.okPm =as.getOkPm();
      }
      result = rp.getNbPM();
      resultsChip.add(result);
    }
  }

  /**
   * @return index of minimum value in resultsChip
   */
  private int getMinPM()
  {
    int index = 0;
    int resultValue = resultsChip.get(0);
    for(int i=1; i<resultsChip.size(); i++)
    {
      if(resultValue>resultsChip.get(i))
      {
        resultValue = resultsChip.get(i);
        index = i;
      }
    }
    return index;
  }

  /**
   * @return minimum value in resultsChip
   */
  private int getMinNbPM()
  {
    int resultValue = resultsChip.get(0);
    for(int i=1; i<resultsChip.size(); i++)
    {
      if(resultValue>resultsChip.get(i))
      {
        resultValue = resultsChip.get(i);
      }
    }
    return resultValue;
  }

  /**
   * sets up the comboBox collection
   * sets selected index to course with minimum # missed checkpoints
   */
  private void initCourses()
  {
    comboBoxCourses.setModel(new DefaultComboBoxModel<Course>(ihm.easyGec.getCourse().getCourses()));
    comboBoxCourses.repaint();
    comboBoxCourses.setSelectedIndex(-1);
    calculResultsChip();
    if(comboBoxCourses.getItemCount()>0)
    {
      comboBoxCourses.setSelectedIndex(getMinPM());
    }
  }
  
  private void addChipResult()
  {
    rp.setCourse((Course) comboBoxCourses.getSelectedItem());
    rp.setIdentifier(textFieldIdentifier.getText());
    rp.setDatas(ihm.easyGec.getOrienteers().getDatas(textFieldIdentifier.getText()));
    ihm.easyGec.getResults().getResults().add(rp);
    //ihm.enregistreResults();
  }
  
  private void printResult()
  {
    //MessageFormat header = new MessageFormat(owner.geRaid.nameRaid);
    //MessageFormat footer = new MessageFormat("Cette �preuve est g�r�e par le logiciel GeRaid.");
    PrinterJob job = PrinterJob.getPrinterJob();
    //System.out.println(job.);
    PageFormat format = job.validatePage(new PageFormat());
    int largeur = (int) (format.getWidth() - 10);
    PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

    pras.add(new MediaPrintableArea(5, 5, largeur, editorPane.getHeight(), MediaPrintableArea.MM));
    try
    {
      editorPane.print(null, null, false, null, pras, false);
    }
    catch (PrinterException e1)
    {
      System.out.println(e1.getMessage());
    }
  }
  
  private void reLoadPage()
  {
    try
    {
      editorPane.setDocument(new HTMLDocument());
      String adresse = new File(".").getCanonicalPath().toString();
      editorPane.setPage("file:///" + adresse + "/temp.html");
    }
    catch (IOException et)
    {
      System.err.format("Impossible de charger la page", et.getMessage());
    }
  }
  
  public void doClickOk()
  {
    btnOk.doClick();
  }
}
