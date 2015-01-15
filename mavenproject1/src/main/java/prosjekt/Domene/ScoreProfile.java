/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prosjekt.Domene;


/**
 * BRUKES AV PROFILSIDEN
 * @author Solheim
 */
public class ScoreProfile {
    private int Taskid;
    private int maxPoints;
    private int points;
    private String date;
    private boolean passed;

    public ScoreProfile(int Taskid, int maxPoints, int points, String date) {
        this.Taskid = Taskid;
        this.maxPoints = maxPoints;
        this.points = points;
        this.date = date;
        
        passed = (points * 2) > maxPoints;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public int getTaskid() {
        return Taskid;
    }

    public void setTaskid(int Taskid) {
        this.Taskid = Taskid;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public boolean isPassed(){
        return passed;
    }
}
