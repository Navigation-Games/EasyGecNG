package ihm;

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

import metier.EasyGec;

import outils.AuScore;
import outils.EnLigne;
import outils.TimeManager;

import to.Circuit;
import to.ResultatPuce;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class IhmResultatPuce extends JDialog
{

  /**
   * 
   */
  private static final long serialVersionUID = 8959944820685395136L;
  private IhmEasyGec ihm;
  private ResultatPuce rp;
  private JPanel contentPane;
  private JTextField textFieldIdentifiant;
  private JComboBox<Circuit> comboBoxCircuits;
  private Vector<Integer> resultatsPuce = new Vector<Integer>();
  private JEditorPane editorPane;
  private JButton btnOk;
  private JButton btnReload;


  /**
   * Create the frame.
   * @param ihm an instance of the application
   * @param rp  an instance of ResultatPuce from /src/to
   *
   * IhmResultatPuce only called from SIReaderHandler
   */
  public IhmResultatPuce(final IhmEasyGec ihm, final ResultatPuce rp)
  {

    /*
      creates 'New Result' tab
     */
    setModal(true);
    setResizable(false);
    setTitle(EasyGec.getLangages().getText("90", EasyGec.getLang()));
    setIconImage(Toolkit.getDefaultToolkit().getImage(IhmResultatPuce.class.getResource("/icones/easy.png")));
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
    panel.setBorder(new TitledBorder(null, EasyGec.getLangages().getText("104", EasyGec.getLang()), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    contentPane.add(panel, BorderLayout.EAST);

    /*
      creates 'Validate the result' button
     */
    btnOk = new JButton("");
    btnOk.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(comboBoxCircuits.getSelectedIndex()>-1)
        {
          addResultatPuce();
          ihm.btnEnregistrer.doClick();
          dispose();
        }
      }
    });
    btnOk.setIcon(new ImageIcon(IhmResultatPuce.class.getResource("/icones/ok64.png")));
    btnOk.setPreferredSize(new Dimension(48, 48));
    btnOk.setToolTipText(EasyGec.getLangages().getText("91", EasyGec.getLang()));
    panel.add(btnOk);

    /*
      creates 'validate and print result' button
     */
    JButton btnOkPrint = new JButton("");
    btnOkPrint.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(comboBoxCircuits.getSelectedIndex()>-1)
        {
          addResultatPuce();
          ihm.btnEnregistrer.doClick();
          printResultat();
          dispose();
        }
      }
    });
    btnOkPrint.setPreferredSize(new Dimension(48, 48));
    btnOkPrint.setToolTipText(EasyGec.getLangages().getText("92", EasyGec.getLang()));
    btnOkPrint.setIcon(new ImageIcon(IhmResultatPuce.class.getResource("/icones/okPrint.png")));
    panel.add(btnOkPrint);

    /*
      creates 'print the result' button
     */
    JButton btnPrint = new JButton("");
    btnPrint.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(comboBoxCircuits.getSelectedIndex()>-1)
        {
          printResultat();
        }
      }
    });
    btnPrint.setIcon(new ImageIcon(IhmResultatPuce.class.getResource("/icones/print.png")));
    btnPrint.setToolTipText(EasyGec.getLangages().getText("93", EasyGec.getLang()));
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
    btnAnnuler.setToolTipText(EasyGec.getLangages().getText("94", EasyGec.getLang()));
    btnAnnuler.setPreferredSize(new Dimension(48, 48));
    btnAnnuler.setIcon(new ImageIcon(IhmResultatPuce.class.getResource("/icones/back.png")));
    panel.add(btnAnnuler);

    /*
      creates 'result' panel
     */
    JPanel panel_1 = new JPanel();
    FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
    flowLayout.setAlignment(FlowLayout.LEFT);
    panel_1.setBorder(new TitledBorder(null, EasyGec.getLangages().getText("95", EasyGec.getLang()), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    contentPane.add(panel_1, BorderLayout.CENTER);

    /*
      add 'ID' field to result panel
     */
    JPanel panel_2 = new JPanel();
    panel_1.add(panel_2);
    
    JLabel lblIdentifiant = new JLabel(EasyGec.getLangages().getText("96", EasyGec.getLang()));
    panel_2.add(lblIdentifiant);
    
    textFieldIdentifiant = new JTextField();
    textFieldIdentifiant.setMinimumSize(new Dimension(273, 20));
    textFieldIdentifiant.setPreferredSize(new Dimension(273, 20));
    panel_2.add(textFieldIdentifiant);
    textFieldIdentifiant.setColumns(18);

    /*
      add 'Course' field to result panel
     */
    JPanel panel_3 = new JPanel();
    panel_1.add(panel_3);
    
    JLabel lblCircuit = new JLabel(EasyGec.getLangages().getText("97", EasyGec.getLang()));
    panel_3.add(lblCircuit);
    
    comboBoxCircuits = new JComboBox<Circuit>();
    comboBoxCircuits.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent arg0) 
      {
        if(comboBoxCircuits.getSelectedIndex()>-1)
        {
          rp.setCircuit((Circuit) comboBoxCircuits.getSelectedItem());
          rp.setIdentifiant(textFieldIdentifiant.getText());
          rp.setDatas(ihm.easyGec.getOrienteurs().getDatas(textFieldIdentifiant.getText()));
          rp.saveHtml();
          reLoadPage();
        }
      }
    });
    comboBoxCircuits.setPreferredSize(new Dimension(248, 20));
    panel_3.add(comboBoxCircuits);
    
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
        if(comboBoxCircuits.getSelectedIndex()>-1)
        {
          reLoadPage();
        }
      }
    });
    panel_4.add(btnReload);
    btnReload.setToolTipText(EasyGec.getLangages().getText("98", EasyGec.getLang()));
    btnReload.setPreferredSize(new Dimension(32, 32));
    btnReload.setIcon(new ImageIcon(IhmResultatPuce.class.getResource("/icones/reload.png")));
    
    editorPane = new JEditorPane();
    editorPane.setEditable(false);
    editorPane.setPreferredSize(new Dimension(343, 350));
    //editorPane.setSize(220, 350);
    
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setViewportView(editorPane);
    panel_4.add(scrollPane);
    
    this.ihm = ihm;
    this.rp = rp;
    textFieldIdentifiant.setText(this.ihm.easyGec.getOrienteurs().getOrienteur(rp.getPuce().getIdPuce()));
    initCircuits();
    textFieldIdentifiant.selectAll();
    //reLoadPage();
    if(ihm.autoResult)
    {
      IhmSmiley is;
      int min = getMinNbPM(); //min holds the number of missed checkpoints of the most accurate course (minimum missed checkpoints)

      //construct String 'missedIndex' of missed checkpoint indexes to pass to IhmSmiley
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
      String elapsedTime = TimeManager.fullTime(this.rp.arrivee-this.rp.depart);
      String courseName = rp.getCircuit().getNom();

      long start = rp.getStart();
      long finish = rp.getFinish();
      // //dev print
      // System.out.println("start: " + start);
      // System.out.println("finish: " + finish);
      //make a local variable to pass to IhmSmiley
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
        is = new IhmSmiley(min, missedIndex, elapsedTime, courseName, ihm.easyGec, okCourse);
      }
      else
      {
        if(this.ihm.easyGec.isAbc())
        {
          is = new IhmSmiley(min, missedIndex, rp.getTexteFormate(), courseName, ihm.easyGec, okCourse);
        }
        else
        {
          is = new IhmSmiley(min, missedIndex, elapsedTime, courseName, ihm.easyGec, okCourse);
        }
      }
      is.setLocationRelativeTo(IhmResultatPuce.this);
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
   * calculResultatsPuce():
   * calculates information about run comparing to every course in comboBoxCircuits
   * determines trad or score-o course
   * adds result to resultatsPuce collection
   */
  private void calculResultatsPuce()
  {
    for(int i=0; i<comboBoxCircuits.getItemCount(); i++)
    {
      int resultat = 0;
      if(comboBoxCircuits.getItemAt(i).isEnLigne())
      {
        EnLigne el = new EnLigne(comboBoxCircuits.getItemAt(i).getCodesToArray(), rp.getCodes(), rp.getTemps());
        rp.okPm = el.getOkPm();
      }
      else
      {
        AuScore as = new AuScore(comboBoxCircuits.getItemAt(i).getCodesToArray(), rp.getCodes(), rp.getTemps());
        rp.okPm =as.getOkPm();
      }
      resultat = rp.getNbPM();
      resultatsPuce.add(resultat);
    }
  }

  /**
   * @return index of minimum value in resultatsPuce
   */
  private int getMinPM()
  {
    int index = 0;
    int retour = resultatsPuce.get(0);
    for(int i=1; i<resultatsPuce.size(); i++)
    {
      if(retour>resultatsPuce.get(i))
      {
        retour = resultatsPuce.get(i);
        index = i;
      }
    }
    return index;
  }

  /**
   * @return minimum value in resultatsPuce
   */
  private int getMinNbPM()
  {
    int retour = resultatsPuce.get(0);
    for(int i=1; i<resultatsPuce.size(); i++)
    {
      if(retour>resultatsPuce.get(i))
      {
        retour = resultatsPuce.get(i);
      }
    }
    return retour;
  }

  /**
   * sets up the comboBox collection
   * sets selected index to course with minimum # missed checkpoints
   */
  private void initCircuits()
  {
    comboBoxCircuits.setModel(new DefaultComboBoxModel<Circuit>(ihm.easyGec.getCircuit().getCircuits()));
    comboBoxCircuits.repaint();
    comboBoxCircuits.setSelectedIndex(-1);
    calculResultatsPuce();
    if(comboBoxCircuits.getItemCount()>0)
    {
      comboBoxCircuits.setSelectedIndex(getMinPM());
    }
  }
  
  private void addResultatPuce()
  {
    rp.setCircuit((Circuit) comboBoxCircuits.getSelectedItem());
    rp.setIdentifiant(textFieldIdentifiant.getText());
    rp.setDatas(ihm.easyGec.getOrienteurs().getDatas(textFieldIdentifiant.getText()));
    ihm.easyGec.getResultats().getResultats().add(rp);
    //ihm.enregistreResultats();
  }
  
  private void printResultat()
  {
    //MessageFormat header = new MessageFormat(owner.geRaid.nomRaid);
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
