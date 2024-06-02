/**
 *
 */
package to;

import inOut.HtmlResultatPuce;

import java.util.Vector;

import metier.EasyGec;

import outils.AuScore;
import outils.EnLigne;
import outils.TimeManager;



/**
 * <P>
 * Titre : ResultatPuce.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author thierry
 *
 */
public class ResultatPuce implements Cloneable, Comparable<ResultatPuce>
{
    private Circuit circuit = null;
    private String identifiant = null;
    private Vector<String> datas = new Vector<String>();
    private int classement = -1;
    public int [] codesATrouver;
    public boolean[] okPm;
    public String[] temps;
    public int [] codesATrouverClasser;
    public boolean[] okPmClasser;
    public String[] tempsClasser;
    private long tempsDeCourse = 0;
    private int nbPostes = 0;
    public long depart = -1;
    public long arrivee = -1;
    private Puce puce = new Puce();
    public Vector<Integer> cardCodes;
    public Vector<String> cardTimes;
    public Vector<Integer> extraCodes = new Vector<Integer>();
    public Vector<String> extraTimes = new Vector<String>();

    public ResultatPuce()
    {

    }

    public String getTexteFormate()
    {
        StringBuffer retour = new StringBuffer("<html><body>");
    /*int index = 0;
    for(int i=0; i<codesATrouver.length; i++)
    {
      if(codesATrouver[i]!=31)
      {
        if(okPm[i])
        {
          retour.append("<font color='green'>" + circuit.getNom().substring(index, index+1) + "</font>");
        }
        else
        {
          //retour.append("<font color='red'>" + circuit.getNom().substring(index, index+1) + "</font>");
          if(i<puce.getPartiels().length)
          {
            retour.append("<font color='red'>" + EasyGec.mappagesCourant.getMap(puce.getPartiels()[i].getCode()) + "</font>");
          }
          else
          {
            retour.append("<font color='red'>" + "?" + "</font>");
          }
        }
        index++;
      }
    }*/
        retour.append("<font color='red'>" + EasyGec.getCircuitEnMappage(puce) + "</font>");
        retour.append("<font color='black'>  ---  " + circuit.getNom() + "</font>");
        retour.append("</body></html>");
        return retour.toString();
    }

    public void triResultatsScore()
    {
        int longueur = temps.length;
        String tamponString = "";
        int tamponInt = 0;
        boolean tamponBool = true;
        boolean permut;

        do
        {
            // hypoth�se : le tableau est tri�
            permut = false;
            for (int i = 0; i < longueur - 1; i++)
            {
                // Teste si 2 �l�ments successifs sont dans le bon ordre ou non
                if (temps[i].compareTo( temps[i + 1])>0)
                {
                    // s'ils ne le sont pas, on �change leurs positions
                    tamponString = temps[i];
                    temps[i] = temps[i + 1];
                    temps[i + 1] = tamponString;
                    tamponInt = codesATrouver[i];
                    codesATrouver[i] = codesATrouver[i + 1];
                    codesATrouver[i + 1] = tamponInt;
                    tamponBool = okPm[i];
                    okPm[i] = okPm[i + 1];
                    okPm[i + 1] = tamponBool;
                    permut = true;
                }
            }
        } while (permut);
    }

    public String toCSV()
    {
        StringBuffer tampon = new StringBuffer ( ) ;
        for(int i=0; i<codesATrouver.length; i++)
        {
            tampon . append( ";" ) ;
            tampon . append( codesATrouver[i]) ;
            tampon . append( ";" ) ;
            tampon . append( temps[i]) ;
        }

        return tampon . toString ( ) ;
    }

    public String getPartiel(int index)
    {
        String retour = "----";
        if(index==0) //gets split between start and first control
        {
            if(okPm.length>0 && okPm[0] && depart>-1)
            {
                retour = TimeManager.fullTime(TimeManager.toLong(TimeManager.safeParse(temps[0])) - depart);
            }
        }
        else if(index == temps.length)
        {
            if(arrivee>-1 && okPm[index-1])
            {
                retour = TimeManager.fullTime(arrivee - TimeManager.toLong(TimeManager.safeParse(temps[index - 1])));
            }
        }
        else
        {
            if(okPm[index] && okPm[index-1])
            {
                retour = TimeManager.fullTime(TimeManager.toLong(TimeManager.safeParse(temps[index])) - TimeManager.toLong(TimeManager.safeParse(temps[index-1])));
            }
        }
        if(retour.substring(0, 1).compareTo("-")==0)
        {
            retour = "----";
        }
        return retour;
    }

    public void calculTempsPostes()
    {
        if(circuit.isDepartBoitier())
        {
            depart = getPuce().getStarttime().getTime();
        }
        else
        {
            depart = circuit.getHeureDepart().getTime();
        }
        arrivee = getPuce().getFinishtime().getTime();
        tempsDeCourse = arrivee-depart;
        calculOkPm();
        setNbPostes();
        verifPm();
    }

    private void verifPm()
    {
        for(int i=0; i<okPm.length; i++)
        {
            if(!okPm[i])
            {
                temps[i] = "PM";
            }
        }
    }

    private void setNbPostes()
    {
        nbPostes = 0;
        for(int i=0; i <okPm.length; i++)
        {
            if(okPm[i])
            {
                nbPostes++;
            }
        }
    }

    private void calculOkPm()
    {
        // r�cup�ration des codes � trouver
        codesATrouver = circuit.getCodesToArray();
        // r�cup�ration des codes de la puce
        Vector<Integer> codesPuce = getCodes();
        cardCodes = new Vector<>(codesPuce); //independent copy
        Vector<String> tempsPuce = getTemps();
        cardTimes = new Vector<>(tempsPuce); //independent copy
        // calcul des OK et PM
        okPm = new boolean[codesATrouver.length];
        temps = new String[codesATrouver.length];
        if(circuit.isEnLigne())
        {
            EnLigne el = new EnLigne(codesATrouver, codesPuce, tempsPuce);
            okPm = el.getOkPm();
            temps = el.getTemps();
            nullToPM();
        }
        else
        {
            AuScore as = new AuScore(codesATrouver, codesPuce, tempsPuce);
            okPm =as.getOkPm();
            temps = as.getTemps();
        }
    }

    /**
     * noNull() corrects the temps list so that any indexes that are null become PM.
     */
    public void nullToPM() {
        for(int i = 0; i<temps.length; i++) {
            if(temps[i] == null) {
                temps[i] = "PM";
            }
        }
    }

    public int getNbPM()
    {
        int retour = 0;
        for(int i=0; i<okPm.length; i++)
        {
            if(!okPm[i])
            {
                retour++;
            }
        }
        return retour;
    }

    /**
     * accessible from other classes
     * @return vector of indexes of missed checkpoints
     */
    public Vector<Integer> getMissed() {
        Vector<Integer> missedCheckpoints = new Vector<>();
        for(int i=0; i<okPm.length; i++)
        {
            if(!okPm[i])
            {
                missedCheckpoints.add(i+1);
            }
        }
        return missedCheckpoints;
    }

    /**
     * @param array of integers intArray[]
     * @return array of strings strArray[]
     * Purpose: convert variable types to that
     */
    public String[] intToString(int[] intArray) {
        String[] strArray = new String[intArray.length];
        for(int i=0; i<intArray.length; i++) {
            strArray[i] = String.valueOf(intArray[i]);
        }
        return strArray;
    }

    /**
     * take the codes and times from the si card data and filter so that only extra checkpoints are left
     * will add to global vectors extraCodes and extraTimes by comparing to courseCodes
     */
    public void filterExtra() {
        for (int i=0; i<cardCodes.size(); i++) {
            int cardCode = cardCodes.get(i);
            //for each cardcode, iterate through courseCodes. if it didn't match any, add to extra codes
            boolean extra = true;
            for (int coursecode : codesATrouver) {
                if (cardCode == coursecode) { //extra control on si card
                    extra = false;
                    break;
                }
            }
            if (extra) {
                extraCodes.addElement(cardCode);
                extraTimes.add(cardTimes.get(i));
            }
        }
    }


    /**
     * @return 2D array of codes, times, designations
     * joins the course controls and SI card controls punched
     * creates and fills matrix for codes, times, and designation (correct, missed, extra)
     *
     * extraCodes: vector<int> of all codes on the SI card
     * extraTimes: vector<str> of all times on the SI card
     * codesATrouver: list of codes on the guessed course
     * temps: list of times on the guessed course
     */
    public String[][] orderedControlsList() {

        filterExtra();
        int size = codesATrouver.length + extraCodes.size();

        String[][] allControls = new String[size][3];
        //column 1: codes
        //column 2: times
        //column 3: designation (missed, correct, extra)

        int[] courseCodes = codesATrouver;
        String[] courseTimes = temps;
        int coursePointer = 0;
        int cardPointer = 0;

        String courseTime;
        int courseCode;
        String cardTime;

        for(int i=0; i<size; i++) {
            //iterate through course codes and card codes, adding to allControls
            if (coursePointer < courseTimes.length) {
                courseTime = courseTimes[coursePointer];
                courseCode = courseCodes[coursePointer];
            } else {
                courseTime = "";
                courseCode = -1;
            }
            if (cardPointer < extraTimes.size()) {
                cardTime = extraTimes.get(cardPointer);
            } else {
                cardTime = "";
            }

            //'PM's will always go first
            if(courseTime.equals("PM")) {
                allControls[i][0] = String.valueOf(courseCode);
                allControls[i][1] = courseTime;
                allControls[i][2] = courseTime;
                coursePointer++;
            }
            else {
                if((courseTime.compareTo(cardTime) < 0 || cardTime.equals("") ) && courseCodes.length>coursePointer) { //time of course checkpoint is earlier than the checkpoint on the si card we're comparing to
                    allControls[i][0] = String.valueOf(courseCode);
                    allControls[i][1] = courseTime;
                    allControls[i][2] = "correct";
                    coursePointer++;
                } else { //time of card checkpoint is earlier than the checkpoint on the course
                    allControls[i][0] = String.valueOf(extraCodes.get(cardPointer));
                    allControls[i][1] = extraTimes.get(cardPointer);
                    allControls[i][2] = "extra";
                    cardPointer++;
                }
            }
        }
        return allControls;
    }

    public String toHtml()
    {
        //debug print
       // System.out.println(getPuce().getPartiels().length);
        for(Partiel partiel : getPuce().getPartiels()) {
            //System.out.println(partiel);
        }

        calculOkPm();
        depart = 0;
        StringBuilder retour = new StringBuilder(1000);

        // Identification
        retour.append("<b style='font-size: 15px'>" + EasyGec.getLangages().getText("96", EasyGec.getLang()) + "</b><span style='font-size: 15px'> " + getIdentifiant() + "</span><br>");
        retour.append("<b style='font-size: 15px'>" + EasyGec.getLangages().getText("97", EasyGec.getLang()) + "</b><span style='font-size: 15px'> " + getCircuit().getNom() + "</span>");

        //  R�sultats globaux de l'�tape
        //retour.append("<br>");
        retour.append("<br>");
        if(circuit.isDepartBoitier())
        {
            depart = getPuce().getStarttime().getTime();
        }
        else
        {
            depart = circuit.getHeureDepart().getTime();
        }
        arrivee = getPuce().getFinishtime().getTime();
        retour.append("<b style='font-size: 15px'>" + EasyGec.getLangages().getText("99", EasyGec.getLang()) + "</b> ");
        retour.append("<span style='font-size: 15px'>" +TimeManager.fullTime(arrivee - depart) + "</span><br>");
        retour.append("<table><tr align=center><th></th><th style='font-size: 15px'>" + EasyGec.getLangages().getText("100", EasyGec.getLang()) + "</th><th style='font-size: 15px'>" + EasyGec.getLangages().getText("101", EasyGec.getLang()) + "</th><th style='font-size: 15px'>" + EasyGec.getLangages().getText("102", EasyGec.getLang()) + "</th></tr>");
        retour.append("<tr align=center><td></td><td><b style='font-size: 15px'>D</b></td>");
        retour.append("<td style='font-size: 15px'>" + TimeManager.fullTime(depart) + "</td><td></td></tr>");
        if(!circuit.isEnLigne())
        {
            triResultatsScore();
        }

        String[][] ordered = orderedControlsList();
        int j=0;
        String partiel = "----";
        int index = 1;

        for(int i=0; i<ordered.length; i++) {
            String designation = ordered[i][2];
            //add html elements with the correct color
            if(designation.equals("correct")) { //correct - plain text
                retour.append("<tr align=center color=black>" +
                        "<td style='font-size: 15px'>" + (index) + "</td>" +
                        "<td style='font-size: 15px'><b>" + ordered[i][0] + "</b></td>" +
                        "<td style='font-size: 15px'>"+ordered[i][1]+"</td>" +
                        "<td style='font-size: 15px'>"+ getPartiel(index-1) +"</td>" +
                        "</tr>");
                index++;
            } else if(!designation.equals("PM")) { //extra punch - blue text
                retour.append("<tr align=center color=blue>" +
                        "<td style='font-size: 15px'>" + " " + "</td>" +
                        "<td style='font-size: 15px'><b>" + ordered[i][0] + "</b></td>" +
                        "<td style='font-size: 15px'>"+ordered[i][1]+"</td>" +
                        "<td style='font-size: 15px'>"+ "----" +"</td>" +
                        "</tr>");
            } else { //missed punch - red text
                retour.append("<tr align=center color=red>" +
                        "<td style='font-size: 15px'>" + (index) + "</td>" +
                        "<td style='font-size: 15px'><b>" + ordered[i][0] + "</b></td>" +
                        "<td style='font-size: 15px'>"+ordered[i][1]+"</td>" +
                        "<td style='font-size: 15px'>"+ "----" +"</td>" +
                        "</tr>");
                index++;
            }

        }
        retour.append("<tr align=center><td></td><td><b style='font-size: 15px'>A</b></td>");
        retour.append("<td style='font-size: 15px'>" + TimeManager.fullTime(arrivee) + "</td><td style='font-size: 15px'>" + getPartiel(codesATrouver.length) + "</td></tr>");
        retour.append("</table>");

        return retour.toString();
    }

    public void saveHtml()
    {
        HtmlResultatPuce.save(this, "temp.html");
    }

    private Vector<Integer> getCodesEnPlus()
    {
        Vector<Integer> retour = new Vector<Integer>();

        for(int i=0; i<getPuce().getPartiels().length; i++)
        {
            if(!existeCode(getPuce().getPartiels()[i].getCode()))
            {
                retour.add(getPuce().getPartiels()[i].getCode());
            }
        }

        return retour;
    }

    public boolean existeCode(int code)
    {
        boolean retour = false;

        for(int i=0; i<circuit.getCodes().getCodes().size(); i++)
        {
            if(circuit.getCodes().getCodes().get(i)==code)
            {
                retour = true;
            }
        }

        return retour;
    }

    public int getPositionPoste(int code)
    {
        return puce.getPositionPoste(code);
    }

    public Vector<String> getTemps()
    {
        return puce.getTemps();
    }

    public Vector<Integer> getCodes()
    {
        return puce.getCodes();
    }

    /**
     * @return the puce
     */
    public Puce getPuce()
    {
        return puce;
    }

    /**
     * @param puce the puce to set
     */
    public void setPuce(Puce puce)
    {
        this.puce = puce;
    }

    /**
     * @return the circuit
     */
    public Circuit getCircuit()
    {
        return circuit;
    }

    /**
     * @param circuit the circuit to set
     */
    public void setCircuit(Circuit circuit)
    {
        this.circuit = circuit;
    }

    /**
     * @return the identifiant
     */
    public String getIdentifiant()
    {
        return identifiant;
    }

    /**
     * @param identifiant the identifiant to set
     */
    public void setIdentifiant(String identifiant)
    {
        this.identifiant = identifiant;
    }

    /**
     * @return the nbPostes
     */
    public int getNbPostes()
    {
        return nbPostes;
    }

    /**
     * @param nbPostes the nbPostes to set
     */
    public void setNbPostes(int nbPostes)
    {
        this.nbPostes = nbPostes;
    }

    /**
     * @return the tempsDeCourse
     */
    public long getTempsDeCourse()
    {
        return tempsDeCourse;
    }

    /**
     * @param tempsDeCourse the tempsDeCourse to set
     */
    public void setTempsDeCourse(long tempsDeCourse)
    {
        this.tempsDeCourse = tempsDeCourse;
    }

    /**
     * @return the classement
     */
    public int getClassement()
    {
        return classement;
    }

    /**
     * @param classement the classement to set
     */
    public void setClassement(int classement)
    {
        this.classement = classement;
    }

    /**
     * Sorts by minimum nbPostes, then minimum tempsDeCourse, then by CSV string equality.
     */
    @Override
    public int compareTo(ResultatPuce o)
    {
        if(nbPostes != o.getNbPostes()) return (nbPostes < o.getNbPostes()) ? 1 : -1;
        
        if(tempsDeCourse != o.getTempsDeCourse()) return (tempsDeCourse > o.getTempsDeCourse()) ? 1 : -1;
        
        return (this.toCSV().compareTo(o.toCSV()));
    }

    /**
     * @param o other object to compare
     * @return true iff this and o output the same toCSV() string
     */
    public boolean equals(ResultatPuce o)
    {
        // quick disqualifiers
        return nbPostes == o.getNbPostes() &&
            tempsDeCourse == o.getTempsDeCourse() &&
            this.toCSV().equals(o.toCSV());
    }

    /**
     * @return the data
     */
    public Vector<String> getDatas()
    {
        return datas;
    }

    /**
     * @param data the data to set
     */
    public void setDatas(Vector<String> datas)
    {
        this.datas = datas;
    }

    public String getData0()
    {
        if(datas.size()>0)
        {
            return datas.get(0);
        }
        else
        {
            return "";
        }
    }

    public String getDatasToCSV()
    {
        StringBuffer retour = new StringBuffer();
        for(int i=0; i<datas.size(); i++)
        {
            retour.append(datas.get(i) + ";");
        }

        return retour.toString();
    }

    public String getData1()
    {
        if(datas.size()>1)
        {
            return datas.get(1);
        }
        else
        {
            return "";
        }
    }

    public long getStart() {
        return depart;
    }

    public long getFinish() {
        return arrivee;
    }

}
