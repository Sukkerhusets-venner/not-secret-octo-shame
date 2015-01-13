
package prosjekt.Ui;

import prosjekt.Domene.User;


public class EditUser {
    
   private User oldUser;
   private User newUser;
   
   public EditUser () {
       
       setNewUser (new User ());
       setOldUser (new User ());
   }

    public User getOldUser() {
        return oldUser;
    }

    public void setOldUser(User oldUser) {
        this.oldUser = oldUser;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }
}
