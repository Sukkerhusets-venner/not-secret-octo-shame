
package prosjekt.Ui;

import java.util.ArrayList;
import java.util.List;
import prosjekt.Domene.Task;

public class Assignment {
    private int id;
    private List<Task> task = new ArrayList<Task>();
    private int currentTask = 0;
    
    public Assignment(){}
    
    public void addTask(String strTask){
        Task t = new Task(task.size(), strTask);
        task.add(t);
    }
    public int nextTask(){
        if(currentTask < task.size()-1){
            return currentTask++;
        }
        return -1;
    }
    public Task getCurrentTask() {return task.get(currentTask);}
    public int getCurrentTaskNr() {return currentTask;}
    public void setCurrentTask(int currentTask) {this.currentTask = currentTask;}
    // retur = antall oppgaver
    public int getTaskNumber(){
        return task.size();
    }
    // return = antall oppgaver besvart
    public int getCompleted() {
        int ant = 0;
        for(Task t : task){
            if(t.getAnswer() != null){
                ant++;
            }
        }
        return ant;
    }
    public List<Task> getAllTasks(){return task;}
    public Task getTask(int tnr){
        /*for (Task t : task){
            if(t.getTasknr() == tnr) return t;
        }
        return null;*/
        return task.get(id);
    }
    public void setAllTasks(List<Task> allTasks){this.task = allTasks;}
}
