package CFBsimPack;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Class for the RB player.
 * @author Achi
 */
public class PlayerRB extends Player {
    
    //public String name;
    //Overall rating, combination of other ratings
    //public int ratOvr;
    //Potential, affects how much he gets better in offseason
    //public int ratPot;
    //FootIQ, affects how smart he plays
    //public int ratFootIQ;
    //RushPow affects how consistenly he can get past line of scrimmage
    public int ratRushPow;
    //RushSpd affects how long he can run
    public int ratRushSpd;
    //RushEva affects how easily he can dodge tackles
    public int ratRushEva;
    
    //public Vector ratingsVector;
    
    //Stats
    public int statsRushAtt;
    public int statsRushYards;
    public int statsTD;
    public int statsFumbles;
    
    public PlayerRB( String nm, Team t, int yr, int pot, int iq, int pow, int spd, int eva ) {
        team = t;
        name = nm;
        year = yr;
        ratOvr = (pow + spd + eva)/3;
        ratPot = pot;
        ratFootIQ = iq;
        ratRushPow = pow;
        ratRushSpd = spd;
        ratRushEva = eva;
        
        cost = (int)Math.pow(ratOvr/4,2) + (int)(Math.random()*100) - 50;
        
        ratingsVector = new Vector();
        ratingsVector.addElement(name+" ("+getYrStr()+")");
        ratingsVector.addElement(ratOvr+" (+"+ratImprovement+")");
        ratingsVector.addElement(ratPot);
        ratingsVector.addElement(ratFootIQ);
        ratingsVector.addElement(ratRushPow);
        ratingsVector.addElement(ratRushSpd);
        ratingsVector.addElement(ratRushEva);
        
        statsRushAtt = 0;
        statsRushYards = 0;
        statsTD = 0;
        statsFumbles = 0;

        position = "RB";
    }
    
    public PlayerRB( String nm, int yr, int stars, Team t ) {
        name = nm;
        year = yr;
        team = t;
        ratPot = (int) (50 + 50*Math.random());
        ratFootIQ = (int) (50 + stars*4 + 30*Math.random());
        ratRushPow = (int) (60 + year*5 + stars*5 - 25*Math.random());
        ratRushSpd = (int) (60 + year*5 + stars*5 - 25*Math.random());
        ratRushEva = (int) (60 + year*5 + stars*5 - 25*Math.random());
        ratOvr = (ratRushPow + ratRushSpd + ratRushEva)/3;
        
        cost = (int)Math.pow(ratOvr/4,2) + (int)(Math.random()*100) - 50;
        
        ratingsVector = new Vector();
        ratingsVector.addElement(name+" ("+getYrStr()+")");
        ratingsVector.addElement(ratOvr+" (+"+ratImprovement+")");
        ratingsVector.addElement(ratPot);
        ratingsVector.addElement(ratFootIQ);
        ratingsVector.addElement(ratRushPow);
        ratingsVector.addElement(ratRushSpd);
        ratingsVector.addElement(ratRushEva);
        
        statsRushAtt = 0;
        statsRushYards = 0;
        statsTD = 0;
        statsFumbles = 0;

        position = "RB";
    }
    
    public Vector getStatsVector() {
        Vector v = new Vector(5);
        v.add(statsRushAtt);
        v.add(statsRushYards);
        v.add(statsTD);
        v.add(statsFumbles);
        v.add((float)((int)((float)statsRushYards/statsRushAtt*100))/100);
        return v;
    }
    
    public Vector getRatingsVector() {
        ratingsVector = new Vector();
        ratingsVector.addElement(name+" ("+getYrStr()+")");
        ratingsVector.addElement(ratOvr+" (+"+ratImprovement+")");
        ratingsVector.addElement(ratPot);
        ratingsVector.addElement(ratFootIQ);
        ratingsVector.addElement(ratRushPow);
        ratingsVector.addElement(ratRushSpd);
        ratingsVector.addElement(ratRushEva);
        return ratingsVector;
    }
    
    @Override
    public void advanceSeason() {
        year++;
        int oldOvr = ratOvr;
        ratFootIQ += (int)(Math.random()*(ratPot - 25))/10;
        ratRushPow += (int)(Math.random()*(ratPot - 25))/10;
        ratRushSpd += (int)(Math.random()*(ratPot - 25))/10;
        ratRushEva += (int)(Math.random()*(ratPot - 25))/10;
        if ( Math.random()*100 < ratPot ) {
            //breakthrough
            ratRushPow += (int)(Math.random()*(ratPot - 30))/10;
            ratRushSpd += (int)(Math.random()*(ratPot - 30))/10;
            ratRushEva += (int)(Math.random()*(ratPot - 30))/10;
        }
        ratOvr = (ratRushPow + ratRushSpd + ratRushEva)/3;
        ratImprovement = ratOvr - oldOvr;
        //reset stats (keep career stats?)
        statsRushAtt = 0;
        statsRushYards = 0;
        statsTD = 0;
        statsFumbles = 0;
    }
    
    @Override
    public int getHeismanScore() {
        return statsTD * 100 - statsFumbles * 100 + (int)(statsRushYards * 2.25);
    }

    @Override
    public ArrayList<String> getDetailStatsList(int games) {
        ArrayList<String> pStats = new ArrayList<>();
        pStats.add("TDs/Fum: " + statsTD + "/" + statsFumbles + ">Rush Attempts: " + statsRushAtt);
        pStats.add("Rush Yards: " + statsRushYards + " yds>Yards per Att: " + ((double)(10*statsRushYards/(statsRushAtt+1))/10) + " yds");
        pStats.add("Yds/Game: " + (statsRushYards/games) + " yds/g>Rush Power: " + getLetterGrade(ratRushPow));
        pStats.add("Rush Speed: " + getLetterGrade(ratRushSpd) + ">Evasion: " + getLetterGrade(ratRushEva));
        return pStats;
    }
    
}