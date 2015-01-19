
package prosjekt.Ui;

import prosjekt.Domene.User;


public class Editform {
    
    User userOld;
    User userNew;
    
    public Editform () {
       setUserOld (new User());
       setUserNew (new User());
    }

    public User getUserOld() {
        return userOld;
    }

    public void setUserOld(User userOld) {
        this.userOld = userOld;
    }

    public User getUserNew() {
        return userNew;
    }

    public void setUserNew(User userNew) {
        this.userNew = userNew;
    }
}

