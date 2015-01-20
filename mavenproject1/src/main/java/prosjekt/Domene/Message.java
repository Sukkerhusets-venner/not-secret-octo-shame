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
public class Message implements Comparable<Message>{
    private java.sql.Timestamp timestamp;
    private String text;
    
    // TIL DATABASE
    public Message(User sender, String text){
        timestamp = new java.sql.Timestamp(new java.util.Date().getTime());
        text = sender.getUsername() + ": " + text;
    }
    
    // FRA DATABASE 
    public Message(java.sql.Timestamp timestamp, String text){
        this.timestamp = timestamp;
        this.text = text;
    }

    public java.sql.Timestamp getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

    @Override
    public int compareTo(Message other){
        if (this.timestamp.getTime() > other.timestamp.getTime()){
            return 1;
        } else if (this.timestamp.getTime() < other.timestamp.getTime()){
            return -1;
        } else {
            return 0;
        }
    }
    
    @Override
    public String toString() {
        return "Message{" + "date=" + timestamp.toString() + ", text=" + text + '}';
    }
}