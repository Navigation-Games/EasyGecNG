/**
 * Copyright (c) 2009 Simon Denier
 * Released under the MIT License (see LICENSE file)
 */
package core;

import ui.UiEasyGec;
import ui.UiChipResult;

import java.io.IOException;
import java.util.Date;
import java.util.TooManyListenersException;

import net.gecosi.CommStatus;
import net.gecosi.SiHandler;
import net.gecosi.SiListener;
import net.gecosi.dataframe.SiDataFrame;


import to.Split;
import to.ChipResult;


/**
 * @author Simon Denier
 * @since Oct 8, 2009
 *
 */
public class SIReaderHandler implements SiListener
{
	
	private SiHandler portHandler;

	private String portName = "";
	
	private long zeroTime = 32400000; // 9:00
  //private long zeroTime = 0; // 9:00
	
	private int nbTry;
	
	private boolean starting;
	
	private UiEasyGec ihm = null;
	
	/**
	 * @param factory
	 * @param stage
	 * @param announcer
	 */
	public SIReaderHandler(UiEasyGec ihm) 
	{
	  this.ihm = ihm;
	}

  private void configure() {
    portHandler = null;
    portHandler = new SiHandler(this);
    portHandler.setZeroHour(getZeroTime());
    try
    {
      portHandler.connect(getPortName());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (TooManyListenersException e)
    {
      e.printStackTrace();
    }
  }

  public void start() {
    configure();
    //setNbTry(0);
    //setStarting(true);
    //if (!portHandler.isAlive())
      //portHandler.start();
  }
  
  public void stop() {
    if( portHandler==null )
      return;
    portHandler.stop();
  }
  public String getPortName() {
    return portName;
  }

  public void setPortName(String portName) {
    this.portName = portName;
  }

  public long getZeroTime() {
    return zeroTime;
  }

  public void setZeroTime(long zeroTime) {
    this.zeroTime = zeroTime;
  }
  
  private void remplirChipResult(SiDataFrame card, ChipResult rp)
  {
    rp.getChip().setIdChip(card.getSiNumber());
    rp.getChip().setStarttime(new Date(card.getStartTime()));
    rp.getChip().setFinishtime(new Date(card.getFinishTime()));
    Split[] splits = new Split[card.getPunches().length];
    for(int i = 0; i < card.getPunches().length; i++)
    {
      Split split = new Split();
      split.setCode(card.getPunches()[i].code());
      split.setTime(new Date(card.getPunches()[i].timestamp()));
      splits[i] = split;
    }
    rp.getChip().setSplits(splits);
  }

  public void setNbTry(int nbTry)
  {
    this.nbTry = nbTry;
  }

  public int getNbTry()
  {
    return nbTry;
  }

  public void setStarting(boolean starting)
  {
    this.starting = starting;
  }

  public boolean isStarting()
  {
    return starting;
  }

  @Override
  public void handleEcard(SiDataFrame card)
  {
    if(ihm.easyGec.getCourse().getCourses().size()>0)
    {
      ChipResult rp = new ChipResult();
      remplirChipResult(card, rp);

      UiChipResult ihmResult =  new UiChipResult(ihm, rp);
      ihmResult.setLocationRelativeTo(ihm);
      if(ihm.autoResult)
      {
        ihmResult.dispose();
      }
      else
      {
        ihmResult.setVisible(true);
      }
    }
  }

  @Override
  public void notify(CommStatus status)
  {
    switch (status) {
      case ON:
        ihm.stationStatus("Ready");
        starting = false;
        break;
      default:
        ihm.stationStatus("Failed");
        break;
      }
  }

  @Override
  public void notify(CommStatus errorStatus, String errorMessage)
  {
    // TODO Auto-generated method stub
    
  }
	
}
