
package prosjekt.Ui;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import prosjekt.Domene.User;
import prosjekt.Domene.UserScore;


public final class Loginform {
    
    @Valid
    private User user; // bruker som er logget inn
    private List<UserScore> hiScore = null;
    private boolean inGame;
    private int messages;
    Assignment assignment;
    private boolean ferdig; 
    
    public Loginform () {
        setUser (new User());
        inGame = false;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }
    
    public boolean isFerdig() {
        return ferdig;
    }

    public void setFerdig(boolean ferdig) {
        if(ferdig){
            inGame = false;
        }
        this.ferdig = ferdig;
    }
    
    public int getMessages() {
        return messages;
    }

    public void setMessages(int messages) {
        this.messages = messages;
    }
    
    public User getUser () {
        return user;
    }
    public void setUser (User user) {
        this.user = user;
    }
    public void setHiScore(ArrayList<UserScore> list){
        this.hiScore = list;
    }
    public UserScore getUserScore(int nr){
        return hiScore.get(nr);
    }
    public List<UserScore> getHiScore(){
        return hiScore;
    }
    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
}
