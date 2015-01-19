/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prosjekt.Domene;

/**
 *
 * @author kak140
 */
public class Chat {
    User userCurrent;
    User userOther;
    boolean read;

    public Chat(User userCurrent, User userOther, boolean read) {
        this.userCurrent = userCurrent;
        this.userOther = userOther;
        this.read = read;
    }

    public User getUserCurrent() {
        return userCurrent;
    }

    public void setUserCurrent(User userCurrent) {
        this.userCurrent = userCurrent;
    }

    public User getUserOther() {
        return userOther;
    }

    public void setUserOther(User userOther) {
        this.userOther = userOther;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
    
    
}
