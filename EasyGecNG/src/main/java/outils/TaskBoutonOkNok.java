/**
 * 
 */
package outils;

import ihm.IhmEasyGec;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingWorker;


/**
 * <P>
 * Titre : Task.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author thierry
 *
 */
public class TaskBoutonOkNok extends SwingWorker<Void, Void>
{    
  private volatile Thread currentThread = null;
  private IhmEasyGec ihm = null;
  private JButton bouton =null;
  private boolean ok = true;
  private ImageIcon icone = null;
  
  public TaskBoutonOkNok(IhmEasyGec ihm, JButton bouton, boolean ok)
  {
    this.ihm = ihm;
    this.bouton = bouton;
    this.ok = ok;
  }
  
  @Override
  protected Void doInBackground() throws Exception
  {        
    icone = (ImageIcon) bouton.getIcon();
    currentThread = Thread.currentThread();
    //Thread.sleep(3000);
    while(! currentThread.isInterrupted())
    {
      if(ok)
      {
        ihm.setBoutonIcone(bouton, new ImageIcon(IhmEasyGec.class.getResource("/icones/good.png")));
      }
      else
      {
        ihm.setBoutonIcone(bouton, new ImageIcon(IhmEasyGec.class.getResource("/icones/bad.png")));
      }
      Thread.sleep(1000);
      ihm.setBoutonIcone(bouton, icone);
      Thread.sleep(2000);
      stop();
    }
    return null;
  }

  /*
   * Executed in event dispatching thread
   */
  @Override
  public void done() 
  {
    setProgress(0);
  }

  public void stop()
  {
    if(currentThread != null)
    {
      currentThread.interrupt();
    }
  }
}
