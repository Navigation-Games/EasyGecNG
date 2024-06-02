/**
 * 
 */
package outils;

import com.fazecast.jSerialComm.SerialPort;


/**
 * <P>
 * Titre : CommunicationRail.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author thierry
 *
 */
public class CommunicationRail
{
  boolean autorisationCommunique = true;
  boolean communication;
 
  /**constructeur initialise le driver de la com et repertorie toutes les ports present sur l'ordinateur.
  ATTENTION si ce constructeur est appeller une deuxieme fois, les ports détecté s'ajouteront à ceux détéctés lors du premier appel.  
  */
  public CommunicationRail()
  {
    
  }
 
  /**repertorie les ports serie utilisable par le rail.
  @return un tableau de string de tout les noms des ports que peut utiliser le rail.
  */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public String[] getListePortRail()
  {
    SerialPort[] serialPorts = SerialPort.getCommPorts();
  
  String []listePortRail = new String[serialPorts.length];
  
  for(int i = 0; i<listePortRail.length; i++)
  {
    listePortRail[i]=serialPorts[i].getSystemPortName();
  }
  return listePortRail;
  }
}
