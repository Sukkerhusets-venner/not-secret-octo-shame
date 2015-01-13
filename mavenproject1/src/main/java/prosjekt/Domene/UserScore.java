/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prosjekt.Domene;

/**
 *
 * @author Solheim
 */
public class UserScore implements Comparable<UserScore> {

    String username;
    int highScore;

    
    public UserScore(String username, int highScore) {
        this.username = username;
        this.highScore = highScore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    @Override
    public int compareTo(UserScore other) {
        if (this.highScore > other.highScore){
            return 1;
        } else if (this.highScore < other.highScore){
            return -1;
        } else{
            return this.username.compareTo(other.username);
        }
    }
}
