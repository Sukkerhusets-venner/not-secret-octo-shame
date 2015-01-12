
package prosjekt.Ui;

import java.util.ArrayList;
import java.util.List;
import prosjekt.Domene.Task;

public class Assignment {
    /*
    *   Codes:
    *   §T = new Task
    *   §D = Divide task ( HTML §D CSS §D Type )
    *   § = not code (if code) or code (if not code) (default = code)
    *   Example: p§My paragraph!§P§D§Dsnake = <p>My paragraph!</p> (assignment type=snake)
    */
    
    private int id;
    private List<Task> task = new ArrayList<Task>();
    private int currentTask = 0;
    
    public Assignment(){}
    public Assignment(String strAssignment){
        String[] tasks = strAssignment.split("§T");
    }
    
    public void addTask(String strTask){
        Task t = new Task(task.size(), strTask);
        task.add(t);
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
