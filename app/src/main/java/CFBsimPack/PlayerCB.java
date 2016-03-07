package CFBsimPack;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Class for the CB player. 3 on the field each covering a WR.
 * @author Achi
 */
public class PlayerCB extends Player {
    
    //public String name;
    //Overall rating, combination of other ratings
    //public int ratOvr;
    //Potential, affects how much he gets better in offseason
    //public int ratPot;
    //FootIQ, affects how smart he plays
    //public int ratFootIQ;
    //CBCov affects how good he is at covering the pass
    public int ratCBCov;
    //CBSpd affects how good he is at not letting up deep passes
    public int ratCBSpd;
    //CBTkl affects how good he is at tackling
    public int ratCBTkl;
    
    //public Vector ratingsVector;
    
    public PlayerCB( String nm, Team t, int yr, int pot, int iq, int cov, int spd, int tkl ) {
        team = t;
        name = nm;
        year = yr;
        ratOvr = (cov*2 + spd + tkl)/4;
        ratPot = pot;
        ratFootIQ = iq;
        ratCBCov = cov;
        ratCBSpd = spd;
        ratCBTkl = tkl;
        position = "CB";
        cost = (int)Math.pow(ratOvr/6,2) + (int)(Math.random()*100) - 50;
        
        ratingsVector = new Vector();
        ratingsVector.addElement(name+" ("+getYrStr()+")");
        ratingsVector.addElement(ratOvr+" (+"+ratImprovement+")");
        ratingsVector.addElement(ratPot);
        ratingsVector.addElement(ratFootIQ);
        ratingsVector.addElement(ratCBCov);
        ratingsVector.addElement(ratCBSpd);
        ratingsVector.addElement(ratCBTkl);
    }
    
    public PlayerCB( String nm, int yr, int stars ) {
        name = nm;
        year = yr;
        ratPot = (int) (50 + 50*Math.random());
        ratFootIQ = (int) (50 + stars*4 + 30*Math.random());
        ratCBCov = (int) (60 + year*5 + stars*5 - 25*Math.random());
        ratCBSpd = (int) (60 + year*5 + stars*5 - 25*Math.random());
        ratCBTkl = (int) (60 + year*5 + stars*5 - 25*Math.random());
        ratOvr = (ratCBCov*2 + ratCBSpd + ratCBTkl)/4;
        position = "CB";
        cost = (int)Math.pow(ratOvr/6,2) + (int)(Math.random()*100) - 50;
        
        ratingsVector = new Vector();
        ratingsVector.addElement(name+" ("+getYrStr()+")");
        ratingsVector.addElement(ratOvr+" (+"+ratImprovement+")");
        ratingsVector.addElement(ratPot);
        ratingsVector.addElement(ratFootIQ);
        ratingsVector.addElement(ratCBCov);
        ratingsVector.addElement(ratCBSpd);
        ratingsVector.addElement(ratCBTkl);
    }
    
    public Vector getRatingsVector() {
        ratingsVector = new Vector();
        ratingsVector.addElement(name+" ("+getYrStr()+")");
        ratingsVector.addElement(ratOvr+" (+"+ratImprovement+")");
        ratingsVector.addElement(ratPot);
        ratingsVector.addElement(ratFootIQ);
        ratingsVector.addElement(ratCBCov);
        ratingsVector.addElement(ratCBSpd);
        ratingsVector.addElement(ratCBTkl);
        return ratingsVector;
    }
    
    @Override
    public void advanceSeason() {
        year++;
        int oldOvr = ratOvr;
        ratFootIQ += (int)(Math.random()*(ratPot - 25))/10;
        ratCBCov += (int)(Math.random()*(ratPot - 25))/10;
        ratCBSpd += (int)(Math.random()*(ratPot - 25))/10;
        ratCBTkl += (int)(Math.random()*(ratPot - 25))/10;
        if ( Math.random()*100 < ratPot ) {
            //breakthrough
            ratCBCov += (int)(Math.random()*(ratPot - 30))/10;
            ratCBSpd += (int)(Math.random()*(ratPot - 30))/10;
            ratCBTkl += (int)(Math.random()*(ratPot - 30))/10;
        }
        ratOvr = (ratCBCov*2 + ratCBSpd + ratCBTkl)/4;
        ratImprovement = ratOvr - oldOvr;
    }

    @Override
    public ArrayList<String> getDetailStatsList(int games) {
        ArrayList<String> pStats = new ArrayList<>();
        pStats.add("Potential: " + ratPot + ">Coverage: " + getLetterGrade(ratCBCov));
        pStats.add("Speed: " + getLetterGrade(ratCBSpd) + ">Tackling: " + getLetterGrade(ratCBTkl));
        return pStats;
    }
}