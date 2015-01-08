/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package prosjekt.Ui;

import prosjekt.Domene.User;

/**
 *
 * @author balder
 */
public class Registreringform {
    private User user;
    
    public Registreringform () {
            setUser (new User());
    }
    
    public User getUser () {
        return user;
    }
    
    public void setUser (User user) {
        this.user = user;
    }
}
