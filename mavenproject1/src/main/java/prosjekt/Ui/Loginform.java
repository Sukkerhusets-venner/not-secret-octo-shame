
package prosjekt.Ui;

import prosjekt.Beans.Bruker;


public class Loginform {
    
    
    
    private Bruker bruker;
    
    public Loginform () {
            setBruker (new Bruker());
    }
    
    public Bruker getBruker () {
        return bruker;
    }
    
    public void setBruker (Bruker bruker) {
        this.bruker = bruker;
    }
}
