/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package prosjekt.Ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import prosjekt.Domene.Chat;
import prosjekt.Domene.Message;
import prosjekt.Domene.User;

/**
 *
 * @author balder
 */
public class Chatform {
    private List<Chat> chatlist = new ArrayList<Chat>();
    private List<User> userlist = new ArrayList<User>();
    private List<Message> messages = new ArrayList<Message>();
    private String chosen;
    private boolean inChat = false;
    private String melding;

    public Chatform(){}

    public String getMelding() {
        return melding;
    }

    public void setMelding(String melding) {
        this.melding = melding;
    }
 
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        Collections.sort(messages);
        this.messages = messages;
    }
    
    public boolean isInChat() {
        return inChat;
    }

    public void setInChat(boolean inChat) {
        this.inChat = inChat;
    }
    
    public String getChosen() {
        return chosen;
    }

    public void setChosen(String chosen) {
        this.chosen = chosen;
        if(chosen == null){
            inChat = false;
        } else {
            inChat = true;
        }
    }
    
    public List<User> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<User> userlist) {
        this.userlist = userlist;
    }
    
    public List<Chat> getChatlist() {
        return chatlist;
    }
    public List<User> getChatUserlist() {
        List<User> chatUserList = new ArrayList<User>();
        for (Chat chat : chatlist) {
            chatUserList.add(chat.getUserOther());
        }
        return chatUserList;
    }

    public void setChatlist(List<Chat> chatlist) {
        this.chatlist = chatlist;
    }
}
