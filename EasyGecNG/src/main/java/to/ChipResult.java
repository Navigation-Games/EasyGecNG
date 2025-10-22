/**
 *
 */
package to;

import io.HtmlChipResult;

import java.util.Vector;

import core.EasyGec;

import utils.ScoreCourseEvaluator;
import utils.ClassicCourseEvaluator;
import utils.TimeManager;



/**
 * <P>
 * Titre : ChipResult.java
 * </P>
 * <P>
 * Description : 
 * </P>
 * @author thierry
 *
 */
public class ChipResult implements Cloneable, Comparable<ChipResult>
{
    private Course course = null;
    private String identifier = null;
    private Vector<String> dataEntries = new Vector<String>();
    private int ranking = -1;
    public int [] codesToFind;
    public boolean[] controlStatus;
    public String[] time;
    public int [] sortedCodesToFind;
    public boolean[] controlStatusClasser;
    public String[] timeClasser;
    private long courseDuration = 0;
    private int controlCount = 0;
    public long start = -1;
    public long finish = -1;
    private Chip chip = new Chip();
    public Vector<Integer> cardCodes;
    public Vector<String> cardTimes;
    public Vector<Integer> extraCodes = new Vector<Integer>();
    public Vector<String> extraTimes = new Vector<String>();

    public ChipResult()
    {

    }

    public String getTexteFormate()
    {
        StringBuffer resultValue = new StringBuffer("<html><body>");
    /*int index = 0;
    for(int i=0; i<codesToFind.length; i++)
    {
      if(codesToFind[i]!=31)
      {
        if(controlStatus[i])
        {
          resultValue.append("<font color='green'>" + course.getName().substring(index, index+1) + "</font>");
        }
        else
        {
          //resultValue.append("<font color='red'>" + course.getName().substring(index, index+1) + "</font>");
          if(i<chip.getSplits().length)
          {
            resultValue.append("<font color='red'>" + EasyGec.mappingsCourant.getMap(chip.getSplits()[i].getCode()) + "</font>");
          }
          else
          {
            resultValue.append("<font color='red'>" + "?" + "</font>");
          }
        }
        index++;
      }
    }*/
        resultValue.append("<font color='red'>" + EasyGec.getCourseEnMapping(chip) + "</font>");
        resultValue.append("<font color='black'>  ---  " + course.getName() + "</font>");
        resultValue.append("</body></html>");
        return resultValue.toString();
    }

    public void triResultsScore()
    {
        int longueur = time.length;
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
                if (time[i].compareTo( time[i + 1])>0)
                {
                    // s'ils ne le sont pas, on �change leurs positions
                    tamponString = time[i];
                    time[i] = time[i + 1];
                    time[i + 1] = tamponString;
                    tamponInt = codesToFind[i];
                    codesToFind[i] = codesToFind[i + 1];
                    codesToFind[i + 1] = tamponInt;
                    tamponBool = controlStatus[i];
                    controlStatus[i] = controlStatus[i + 1];
                    controlStatus[i + 1] = tamponBool;
                    permut = true;
                }
            }
        } while (permut);
    }

    public String toCSV()
    {
        StringBuffer tampon = new StringBuffer ( ) ;
        for(int i=0; i<codesToFind.length; i++)
        {
            tampon . append( ";" ) ;
            tampon . append( codesToFind[i]) ;
            tampon . append( ";" ) ;
            tampon . append( time[i]) ;
        }

        return tampon . toString ( ) ;
    }

    public String getSplit(int index)
    {
        String resultValue = "----";
        if(index==0) //gets split between start and first control
        {
            if(controlStatus.length>0 && controlStatus[0] && start>-1)
            {
                resultValue = TimeManager.fullTime(TimeManager.toLong(TimeManager.safeParse(time[0])) - start);
            }
        }
        else if(index == time.length)
        {
            if(finish>-1 && controlStatus[index-1])
            {
                resultValue = TimeManager.fullTime(finish - TimeManager.toLong(TimeManager.safeParse(time[index - 1])));
            }
        }
        else
        {
            if(controlStatus[index] && controlStatus[index-1])
            {
                resultValue = TimeManager.fullTime(TimeManager.toLong(TimeManager.safeParse(time[index])) - TimeManager.toLong(TimeManager.safeParse(time[index-1])));
            }
        }
        if(resultValue.substring(0, 1).compareTo("-")==0)
        {
            resultValue = "----";
        }
        return resultValue;
    }

    public void calculTimePostes()
    {
        if(course.isStartBoitier())
        {
            start = getChip().getStarttime().getTime();
        }
        else
        {
            start = course.getHeureStart().getTime();
        }
        finish = getChip().getFinishtime().getTime();
        courseDuration = finish-start;
        calculControlStatus();
        setControlCount();
        verifPm();
    }

    private void verifPm()
    {
        for(int i=0; i<controlStatus.length; i++)
        {
            if(!controlStatus[i])
            {
                time[i] = "PM";
            }
        }
    }

    private void setControlCount()
    {
        controlCount = 0;
        for(int i=0; i <controlStatus.length; i++)
        {
            if(controlStatus[i])
            {
                controlCount++;
            }
        }
    }

    private void calculControlStatus()
    {
        // r�cup�ration des codes � trouver
        codesToFind = course.getCodesToArray();
        // r�cup�ration des codes de la chip
        Vector<Integer> codesChip = getCodes();
        cardCodes = new Vector<>(codesChip); //independent copy
        Vector<String> timeChip = getTime();
        cardTimes = new Vector<>(timeChip); //independent copy
        // calcul des OK et PM
        controlStatus = new boolean[codesToFind.length];
        time = new String[codesToFind.length];
        if(course.isClassicCourseEvaluator())
        {
            ClassicCourseEvaluator el = new ClassicCourseEvaluator(codesToFind, codesChip, timeChip);
            controlStatus = el.getControlStatus();
            time = el.getTime();
            nullToPM();
        }
        else
        {
            ScoreCourseEvaluator as = new ScoreCourseEvaluator(codesToFind, codesChip, timeChip);
            controlStatus =as.getControlStatus();
            time = as.getTime();
        }
    }

    /**
     * noNull() corrects the time list so that any indexes that are null become PM.
     */
    public void nullToPM() {
        for(int i = 0; i<time.length; i++) {
            if(time[i] == null) {
                time[i] = "PM";
            }
        }
    }

    public int getNbPM()
    {
        int resultValue = 0;
        for(int i=0; i<controlStatus.length; i++)
        {
            if(!controlStatus[i])
            {
                resultValue++;
            }
        }
        return resultValue;
    }

    /**
     * accessible from other classes
     * @return vector of indexes of missed checkpoints
     */
    public Vector<Integer> getMissed() {
        Vector<Integer> missedCheckpoints = new Vector<>();
        for(int i=0; i<controlStatus.length; i++)
        {
            if(!controlStatus[i])
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
            for (int coursecode : codesToFind) {
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
     * codesToFind: list of codes on the guessed course
     * time: list of times on the guessed course
     */
    public String[][] orderedControlsList() {

        filterExtra();
        int size = codesToFind.length + extraCodes.size();

        String[][] allControls = new String[size][3];
        //column 1: codes
        //column 2: times
        //column 3: designation (missed, correct, extra)

        int[] courseCodes = codesToFind;
        String[] courseTimes = time;
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
       // System.out.println(getChip().getSplits().length);
        for(Split split : getChip().getSplits()) {
            //System.out.println(split);
        }

        calculControlStatus();
        start = 0;
        StringBuilder resultValue = new StringBuilder(1000);

        // Identification
        resultValue.append("<b style='font-size: 15px'>" + EasyGec.getLanguages().getText("96", EasyGec.getLang()) + "</b><span style='font-size: 15px'> " + getIdentifier() + "</span><br>");
        resultValue.append("<b style='font-size: 15px'>" + EasyGec.getLanguages().getText("97", EasyGec.getLang()) + "</b><span style='font-size: 15px'> " + getCourse().getName() + "</span>");

        //  R�sultats globaux de l'�tape
        //resultValue.append("<br>");
        resultValue.append("<br>");
        if(course.isStartBoitier())
        {
            start = getChip().getStarttime().getTime();
        }
        else
        {
            start = course.getHeureStart().getTime();
        }
        finish = getChip().getFinishtime().getTime();
        resultValue.append("<b style='font-size: 15px'>" + EasyGec.getLanguages().getText("99", EasyGec.getLang()) + "</b> ");
        resultValue.append("<span style='font-size: 15px'>" +TimeManager.fullTime(finish - start) + "</span><br>");
        resultValue.append("<table><tr align=center><th></th><th style='font-size: 15px'>" + EasyGec.getLanguages().getText("100", EasyGec.getLang()) + "</th><th style='font-size: 15px'>" + EasyGec.getLanguages().getText("101", EasyGec.getLang()) + "</th><th style='font-size: 15px'>" + EasyGec.getLanguages().getText("102", EasyGec.getLang()) + "</th></tr>");
        resultValue.append("<tr align=center><td></td><td><b style='font-size: 15px'>D</b></td>");
        resultValue.append("<td style='font-size: 15px'>" + TimeManager.fullTime(start) + "</td><td></td></tr>");
        if(!course.isClassicCourseEvaluator())
        {
            triResultsScore();
        }

        String[][] ordered = orderedControlsList();
        int j=0;
        String split = "----";
        int index = 1;

        for(int i=0; i<ordered.length; i++) {
            String designation = ordered[i][2];
            //add html elements with the correct color
            if(designation.equals("correct")) { //correct - plain text
                resultValue.append("<tr align=center color=black>" +
                        "<td style='font-size: 15px'>" + (index) + "</td>" +
                        "<td style='font-size: 15px'><b>" + ordered[i][0] + "</b></td>" +
                        "<td style='font-size: 15px'>"+ordered[i][1]+"</td>" +
                        "<td style='font-size: 15px'>"+ getSplit(index-1) +"</td>" +
                        "</tr>");
                index++;
            } else if(!designation.equals("PM")) { //extra punch - blue text
                resultValue.append("<tr align=center color=blue>" +
                        "<td style='font-size: 15px'>" + " " + "</td>" +
                        "<td style='font-size: 15px'><b>" + ordered[i][0] + "</b></td>" +
                        "<td style='font-size: 15px'>"+ordered[i][1]+"</td>" +
                        "<td style='font-size: 15px'>"+ "----" +"</td>" +
                        "</tr>");
            } else { //missed punch - red text
                resultValue.append("<tr align=center color=red>" +
                        "<td style='font-size: 15px'>" + (index) + "</td>" +
                        "<td style='font-size: 15px'><b>" + ordered[i][0] + "</b></td>" +
                        "<td style='font-size: 15px'>"+ordered[i][1]+"</td>" +
                        "<td style='font-size: 15px'>"+ "----" +"</td>" +
                        "</tr>");
                index++;
            }

        }
        resultValue.append("<tr align=center><td></td><td><b style='font-size: 15px'>A</b></td>");
        resultValue.append("<td style='font-size: 15px'>" + TimeManager.fullTime(finish) + "</td><td style='font-size: 15px'>" + getSplit(codesToFind.length) + "</td></tr>");
        resultValue.append("</table>");

        return resultValue.toString();
    }

    public void saveHtml()
    {
        HtmlChipResult.save(this, "temp.html");
    }

    private Vector<Integer> getCodesEnPlus()
    {
        Vector<Integer> resultValue = new Vector<Integer>();

        for(int i=0; i<getChip().getSplits().length; i++)
        {
            if(!existeCode(getChip().getSplits()[i].getCode()))
            {
                resultValue.add(getChip().getSplits()[i].getCode());
            }
        }

        return resultValue;
    }

    public boolean existeCode(int code)
    {
        boolean resultValue = false;

        for(int i=0; i<course.getCodes().getCodes().size(); i++)
        {
            if(course.getCodes().getCodes().get(i)==code)
            {
                resultValue = true;
            }
        }

        return resultValue;
    }

    public int getPositionPoste(int code)
    {
        return chip.getPositionPoste(code);
    }

    public Vector<String> getTime()
    {
        return chip.getTime();
    }

    public Vector<Integer> getCodes()
    {
        return chip.getCodes();
    }

    /**
     * @return the chip
     */
    public Chip getChip()
    {
        return chip;
    }

    /**
     * @param chip the chip to set
     */
    public void setChip(Chip chip)
    {
        this.chip = chip;
    }

    /**
     * @return the course
     */
    public Course getCourse()
    {
        return course;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(Course course)
    {
        this.course = course;
    }

    /**
     * @return the identifier
     */
    public String getIdentifier()
    {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }

    /**
     * @return the controlCount
     */
    public int getControlCount()
    {
        return controlCount;
    }

    /**
     * @param controlCount the controlCount to set
     */
    public void setControlCount(int controlCount)
    {
        this.controlCount = controlCount;
    }

    /**
     * @return the courseDuration
     */
    public long getCourseDuration()
    {
        return courseDuration;
    }

    /**
     * @param courseDuration the courseDuration to set
     */
    public void setCourseDuration(long courseDuration)
    {
        this.courseDuration = courseDuration;
    }

    /**
     * @return the ranking
     */
    public int getRanking()
    {
        return ranking;
    }

    /**
     * @param ranking the ranking to set
     */
    public void setRanking(int ranking)
    {
        this.ranking = ranking;
    }

    /**
     * Sorts by minimum controlCount, then minimum courseDuration, then by CSV string equality.
     */
    @Override
    public int compareTo(ChipResult o)
    {
        if(controlCount != o.getControlCount()) return (controlCount < o.getControlCount()) ? 1 : -1;
        
        if(courseDuration != o.getCourseDuration()) return (courseDuration > o.getCourseDuration()) ? 1 : -1;
        
        return (this.toCSV().compareTo(o.toCSV()));
    }

    /**
     * @param o other object to compare
     * @return true iff this and o output the same toCSV() string
     */
    public boolean equals(ChipResult o)
    {
        // quick disqualifiers
        return controlCount == o.getControlCount() &&
            courseDuration == o.getCourseDuration() &&
            this.toCSV().equals(o.toCSV());
    }

    /**
     * @return the data
     */
    public Vector<String> getDatas()
    {
        return dataEntries;
    }

    /**
     * @param data the data to set
     */
    public void setDatas(Vector<String> dataEntries)
    {
        this.dataEntries = dataEntries;
    }

    public String getData0()
    {
        if(dataEntries.size()>0)
        {
            return dataEntries.get(0);
        }
        else
        {
            return "";
        }
    }

    public String getDatasToCSV()
    {
        StringBuffer resultValue = new StringBuffer();
        for(int i=0; i<dataEntries.size(); i++)
        {
            resultValue.append(dataEntries.get(i) + ";");
        }

        return resultValue.toString();
    }

    public String getData1()
    {
        if(dataEntries.size()>1)
        {
            return dataEntries.get(1);
        }
        else
        {
            return "";
        }
    }

    public long getStart() {
        return start;
    }

    public long getFinish() {
        return finish;
    }

}
