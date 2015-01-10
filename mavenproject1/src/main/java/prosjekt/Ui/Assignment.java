
package prosjekt.Ui;

import java.util.List;
import prosjekt.Domene.Task;

public class Assignment {
    
    private int id;
    private List<Task> task = null;
    private List<Task> completed = null;
    private int currentTask = 0;
    
    public Assignment(int id){
        this.id = id;
    }
    public Task nextTask(){
        if(currentTask >= task.size()){
            return null;
        }else{
            currentTask++;
            return task.get(currentTask);
        }
    }
    public Task getCurrentTask() {return task.get(currentTask);}
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
        for (Task t : task){
            if(t.getTasknr() == tnr) return t;
        }
        return null;
    }
    public void setAllTasks(List<Task> allTasks){this.task = allTasks;}
}
