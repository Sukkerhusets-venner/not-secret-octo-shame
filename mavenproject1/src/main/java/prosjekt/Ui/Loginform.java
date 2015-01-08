
package prosjekt.Ui;

import prosjekt.Beans.User;


public class Loginform {
    
    
    
    private User user;
    
    public Loginform () {
            setUser (new User());
    }
    
    public User getUser () {
        return user;
    }
    
    public void setUser (User user) {
        this.user = user;
    }
}
