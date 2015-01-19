/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prosjekt.Domene;

import java.sql.Date;

/**
 *
 * @author Solheim
 */
public class Message implements Comparable<Message>{
    private java.sql.Date date;
    private String text;
    
    // TIL DATABASE
    public Message(User sender, String text){
        date = new java.sql.Date(new java.util.Date().getTime());
        text = sender.getUsername() + ": " + text;
    }
    
    // FRA DATABASE 
    public Message(java.sql.Date date, String text){
        this.date = date;
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    @Override
    public int compareTo(Message other){
        if (this.date.getTime() > other.date.getTime()){
            return 1;
        } else if (this.date.getTime() < other.date.getTime()){
            return -1;
        } else {
            return 0;
        }
    }
    
    @Override
    public String toString() {
        return "Message{" + "date=" + date.toString() + ", text=" + text + '}';
    }
}
