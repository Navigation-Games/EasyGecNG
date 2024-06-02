/**
 * Copyright (c) 2009 Simon Denier
 */
package outils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Vector;

/**
 * @author Simon Denier
 * @since Jan 24, 2009
 *
 */
public class TimeManager 
{
	
	public static final Date NO_TIME = new Date(90000000000L);
	public static final long NO_TIME_l = NO_TIME.getTime();
	public static final String NO_TIME_STRING = "--:--";

	public static SimpleDateFormat FORMATTER;
	private static SimpleDateFormat FORMATTER60;
  private static SimpleDateFormat FORMATTERDD;

	static 
	{ // have to set GMT time zone to avoid TZ offset in race time computation
		FORMATTER = new SimpleDateFormat("H:mm:ss");
		FORMATTER.setTimeZone(TimeZone.getTimeZone("GMT"));
		FORMATTER60 = new SimpleDateFormat("m:ss");
		FORMATTER60.setTimeZone(TimeZone.getTimeZone("GMT"));
		FORMATTERDD = new SimpleDateFormat("dd:HH:mm:ss");
		FORMATTERDD.setTimeZone(TimeZone.getTimeZone("GMT"));
	}
	
	public static String getDateHeureMinuteSeconte()
	{
	  StringBuffer retour = new StringBuffer();
	  GregorianCalendar date = new GregorianCalendar();
	  retour.append(date.get(Calendar.YEAR) +"-");
    retour.append(date.get(Calendar.MONTH) +"-");
    retour.append(date.get(Calendar.DAY_OF_MONTH) +" ");
    retour.append(date.get(Calendar.HOUR_OF_DAY) +"h");
    retour.append(date.get(Calendar.MINUTE) +"mn");
    retour.append(date.get(Calendar.SECOND) +"s");
	  return retour.toString();
	}

	public static Date parse(String time, SimpleDateFormat formatter) throws ParseException 
	{
		return formatter.parse(time);
	}

	public static Date safeParse(String time) 
	{
		try 
		{
			return parse(time, FORMATTER);
		} 
		catch (ParseException e) {}
		catch (NullPointerException np) {}
		return NO_TIME;
	}
	
	public static Date userParse(String time) throws ParseException 
	{
		try 
		{
			return parse(time, FORMATTER);
		} catch (ParseException e) 
		{
			return parse(time, FORMATTER60);
		}
	}

	public static String fullTime(Date date) 
	{
		if( date.equals(NO_TIME) )
			return NO_TIME_STRING;
		if(date.compareTo(new Date(86400000))>0)
		{
		  date = new Date(date.getTime()-86400000);
	    return FORMATTERDD.format(date);
		}
		return FORMATTER.format(date);
	}

	public static String fullTime(long timestamp) 
	{
	  if(timestamp<0)
	  {
	    return "-" + fullTime(new Date(-timestamp));
	  }
		return fullTime(new Date(timestamp));
	}
	
	public static String time(Date date) 
	{
		if( date.equals(NO_TIME) )
			return NO_TIME_STRING;
		if( date.getTime()<3600000 ) 
		{
			return FORMATTER60.format(date);
		} else 
		{
			return FORMATTER.format(date);
		}
	}
	
	public static String time(long timestamp) 
	{
		return time(new Date(timestamp));
	}
	
	public static Date newDate(String lo)
	{
	  Date retour = null;

	  if(lo.compareTo("0")==0)
    {
      //retour = NO_TIME;
      retour = new Date(0);
    }
	  else
	  {
	    retour = new Date(Long.parseLong(lo));
	  }
	  return retour;
	}
	
	public static long toLong(Date d)
	{
	  if(d.equals(NO_TIME))
	  {
	    return 0;
	  }
	  return d.getTime();
	}
	
	public static int getHeures(Date date)
	{
	  long l = date.getTime();
	  return (int) Math.floor(l/3600000);
	}
  
  public static int getMinutes(Date date)
  {
    long l = date.getTime()- getHeures(date)*3600000;
    return (int) Math.floor(l/60000);
  }
  
  public static int getSecondes(Date date)
  {
    long l = date.getTime()- getHeures(date)*3600000 - getMinutes(date)*60000;
    return (int) Math.floor(l/1000);
  }
	
  public static Date setHeure(Date date, int heure)
  {
    Date retour = new Date((heure*3600 + getMinutes(date)*60 + getSecondes(date))*1000);
    return retour;
  }
  
  public static Date setMinute(Date date, int minute)
  {
    Date retour = new Date((getHeures(date)*3600 + minute*60 + getSecondes(date))*1000);
    return retour;
  }
  
  public static Date setSeconde(Date date, int seconde)
  {
    Date retour = new Date((getHeures(date)*3600 + getMinutes(date)*60 + seconde)*1000);
    return retour;
  }
  
  public static boolean isTempsProche(String t1, String t2, int ecart)
  {
    boolean retour = false;
    
    long l1 = toLong(t1);
    long l2 = toLong(t2);
    if(Math.abs((l2-l1)/1000)<ecart)
    {
      retour = true;
    }
    
    return retour;
  }
  
  public static long toLong(String jhms)
  {
    //System.out.println(jhms);
    long retour = 0;
    int jour = 0;
    Vector<Integer> count = compteurChar(jhms, ":");
    if(count.size() == 3)
    {
      String j = jhms.substring(0, count.get(1));
      retour = retour + Long.parseLong(j) * 3600 * 24;
      jour++;
    }

    int debut = 0;
    if(jour == 1)
    {
      debut = count.get(1);
    }
    String h = jhms.substring(debut , count.get(1+jour));
    retour = retour + Long.parseLong(h) * 3600;
    String m = jhms.substring(count.get(1+jour)+1, count.get(2+jour));
    retour = retour + Long.parseLong(m) * 60;
    String s = jhms.substring(count.get(2+jour)+1, jhms.length()-1);
    retour = retour + Long.parseLong(s);
    return retour * 1000;
  }
  
  @SuppressWarnings("static-access")
  public static Vector<Integer> compteurChar(String str, String st) 
  {
    Vector<Integer> compteur = new Vector<Integer>();  
    for (int i = 0; i < str.length(); i++) 
    if (str.valueOf(i).compareTo(st) == 00)  
    {
       compteur.add(i);
    }                         
    return compteur;   
  }
}
