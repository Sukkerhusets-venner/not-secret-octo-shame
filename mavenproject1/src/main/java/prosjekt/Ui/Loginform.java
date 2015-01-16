
package prosjekt.Ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import prosjekt.Domene.User;
import prosjekt.Domene.UserScore;


public final class Loginform {
    
    @Valid
    private User user; // bruker som er logget inn
    private List<UserScore> hiScore = null;
     
    public Loginform () {
        setUser (new User());
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
}
