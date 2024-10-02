package ihm;

import java.awt.*;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.UIManager;

public class IhmAbcGec extends IhmEasyGec
{

  /**
   * 
   */
  private static final long serialVersionUID = 3674862829136393962L;

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
        try{ UIManager.getLookAndFeelDefaults().put("defaultFont", new java.awt.Font("Arial", Font.BOLD, 14)); }
        catch( Exception e ) {}
        // init fichier rxtxSerial.dll
        String version = System.getProperty("sun.arch.data.model");
        if(version.contains("32"))
        {
           Path source = Paths.get("rxtxSerial32.dll");
           Path target = Paths.get("rxtxSerial.dll");
           try
          {
             Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING );
          }
          catch (IOException e)
          {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        else
        {
          Path source = Paths.get("rxtxSerial64.dll");
          Path target = Paths.get("rxtxSerial.dll");
          try
         {
           Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING );
         }
         catch (IOException e)
         {
           // TODO Auto-generated catch block
           e.printStackTrace();
         }
        }
        
        try
        {
          IhmAbcGec frame = new IhmAbcGec();
          frame.setVisible(true);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    });
  }
  
  public IhmAbcGec()
  {
    super();
    easyGec.setAbc(true);
    setTitle("AbcGec " + easyGec.getVersion());
    setIconImage(Toolkit.getDefaultToolkit().getImage(IhmEasyGec.class.getResource("/icones/abc32.png")));
    panelType.setVisible(false);
    panel_2.setVisible(false);
    btnSI.setVisible(false);
    btnImportOcad.setVisible(false);
    btnAbc.setVisible(true);
    btnImportCircuitAbc.setVisible(true);
    for( MouseListener al : listCodes.getMouseListeners() ) 
    {
      listCodes.removeMouseListener( al );
    }
  }

}
