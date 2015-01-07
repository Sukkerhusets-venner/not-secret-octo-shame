/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package prosjekt.Ui;

import prosjekt.Beans.Bruker;

/**
 *
 * @author balder
 */
public class Registreringform {
    private Bruker bruker;
    
    public Registreringform () {
            setBruker (new Bruker());
    }
    
    public Bruker getBruker () {
        return bruker;
    }
    
    public void setBruker (Bruker bruker) {
        this.bruker = bruker;
    }
}
