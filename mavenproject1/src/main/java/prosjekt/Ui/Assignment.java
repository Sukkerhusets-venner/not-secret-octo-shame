
package prosjekt.Ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import prosjekt.Domene.Task;

public class Assignment {
    private int id;
    private List<Task> task = new ArrayList<Task>();
    private int currentTask = 0;
    private int[] delscores;
    private int score;
    private double randomNumber = 0;
    private double[] pastRandomNumber;
    private int timescore = 10;

    public Assignment(){}
    
    public int sumUp(){
        int sum = 0;
        for (int i = 0; i < delscores.length; i++) {
            sum += delscores[i];
        }
        return sum;
    }
    
    public int getMaxScore(){
        int sum = 0;
        for (Task t : task) {
            sum += t.getMaxPoeng();
        }
        return sum;
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int nextTask(){
        setDelscore();
        timescore = 10;
        if(currentTask < task.size()-1){
            return currentTask++;
        }
        currentTask = -1;
        return currentTask;
    }
    public Task getCurrentTask() {return task.get(currentTask);}
    public int getCurrentTaskNr() {return currentTask+1;}
    public void setCurrentTask(int currentTask) {this.currentTask = currentTask;}
    // retur = antall oppgaver
    public int getTaskNumber(){
        return task.size();
    }
    // return = antall oppgaver besvart
    public int getCompleted() {
        int ant = 0;
        for(Task t : task){
            if(t.getAnswerHtml() != null){
                ant++;
            }
        }
        return ant;
    }
    public List<Task> getAllTasks(){return task;}
    public Task getTask(int tnr){
        return task.get(id);
    }
    public void setAllTasks(List<Task> allTasks){
        List<ArrayList<Task>> tasks = new ArrayList<ArrayList<Task>>();
        for (int i = 0; i < 3; i++) {
            tasks.add(new ArrayList<Task>());
        }
        for (Task t : allTasks) {
            tasks.get(t.getDiff()-1).add(t);
        }
        for (ArrayList<Task> arrayList : tasks) {
            Collections.shuffle(arrayList);
            for (Task t : arrayList) {
                task.add(t);
            }
        }
        delscores = new int[task.size()];
        pastRandomNumber = new double[task.size()];
    }
    
    public void setDelscore(){
        if(currentTask < task.size()){
            delscores[currentTask] = score;
        }
    }
    public int getDelscore(int nummer){
        if (nummer > -1 && nummer < task.size()){
            return delscores[nummer];
        } else {
            return -1;
        }
    }
    public int getMaxPoeng(int nummer){
        if (nummer > -1 && nummer < task.size()){
            return task.get(nummer).getMaxPoeng();
        } else {
            return -1;
        }
    }
    public int getTaskNr(){
        return delscores.length;
    }
    public double getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(double rn) {
        pastRandomNumber[currentTask] = randomNumber;
        randomNumber = rn;
    }
    
    public boolean checkNumbers(){
        for (int i = 0; i < pastRandomNumber.length; i++) {
            if(randomNumber == pastRandomNumber[i]){
                return false;
            }
        }
        return true;
    }
    public int getTimescore() {
        return timescore;
    }

    public void setTimescore(int timescore) {
        this.timescore = timescore;
    }
}
