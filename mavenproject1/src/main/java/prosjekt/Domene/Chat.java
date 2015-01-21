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
    boolean readBy1;
    boolean readBy2;

    public Chat(User userCurrent, User userOther, boolean readBy1, boolean readBy2) {
        this.userCurrent = userCurrent;
        this.userOther = userOther;
        this.readBy1 = readBy1;
        this.readBy2 = readBy2;
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

    public boolean isReadBy1() {
        return readBy1;
    }
    
    public boolean isReadBy2() {
        return readBy2;
    }
}
