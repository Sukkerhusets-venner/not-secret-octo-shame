
package prosjekt.Ui;

import java.util.ArrayList;
import java.util.List;
import prosjekt.Domene.Task;

public class Assignment {
    private int id;
    private List<Task> task = new ArrayList<Task>();
    private int currentTask = 0;
    private int[] delscores;
    private int score;
    
    public int sumUp(){
        int sum = 0;
        for (int i = 0; i < delscores.length; i++) {
            sum += delscores[i];
        }
        return sum;
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public Assignment(){}
    
    public void addTask(String strTask){
        Task t = new Task(task.size(), strTask);
        task.add(t);
    }
    public int nextTask(){
        setDelscore();
        if(currentTask < task.size()-1){
            return currentTask++;
        }
        return -1;
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
        this.task = allTasks;
        delscores = new int[task.size()];
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
}
