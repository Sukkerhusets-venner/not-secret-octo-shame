/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prosjekt.Domene;

import java.util.ArrayList;

/**
 * BRUKES AV GODKJENNINGSSIDE
 * @author Solheim
 */
public class UserScoreOverview {

    private User user;
    private ArrayList<ScoreProfile> completedTasks;
    private boolean passed;

    public UserScoreOverview(User user, ArrayList<ScoreProfile> completedTasks) {
        this.user = user;
        this.completedTasks = completedTasks;
        
        passed = checkIfPassed();
    }

    private boolean checkIfPassed() {
        if (completedTasks != null) {
            for (ScoreProfile completedTask : completedTasks) {
                if (completedTask.isPassed()) {
                    return true;
                }
            }
        }
        return false;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<ScoreProfile> getCompletedTasks() {
        return completedTasks;
    }

    public boolean isPassed() {
        return passed;
    }
    
    
}
