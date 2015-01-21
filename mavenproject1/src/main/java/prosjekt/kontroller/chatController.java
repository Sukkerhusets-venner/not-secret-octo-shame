/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package prosjekt.kontroller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import prosjekt.Domene.Chat;
import prosjekt.Domene.Message;
import prosjekt.Domene.User;
import prosjekt.Ui.Chatform;
import prosjekt.Ui.Loginform;
import prosjekt.database.DatabaseConnection;

@Controller
@SessionAttributes({"loginform","chatform"})
public class chatController {
    
    @Autowired
    private DatabaseConnection database;   
    
    @ModelAttribute("chatform")
    public Chatform makeChatform(){
        return new Chatform();
    }
    
    @RequestMapping (value = "chat")
    public String chat(@ModelAttribute(value="loginform") Loginform loginform,
            @ModelAttribute(value="chatform") Chatform chatform, Model model) {
        chatform.setInChat(false);
        List<User> users = database.getUsers();
        List<Chat> chats = database.getChatList(loginform.getUser());
        for (Chat chat : chats) {
            users.remove(chat.getUserOther());
        }
        users.remove(loginform.getUser());
        chatform.setChatlist(chats);
        chatform.setUserlist(users);
        return "chat";
    }
    
    @RequestMapping (value = "velgChat")
    public String chatValgt(@ModelAttribute(value="loginform") Loginform loginform, 
             @ModelAttribute(value="chatform") Chatform chatform, Model model) {
        chatform.setMessages(database.getChat(loginform.getUser(), database.getUser(chatform.getChosen())));
        return "chat";
    }
    
    @RequestMapping (value = "sendMeldning")
    public String sendMessage(@ModelAttribute(value="loginform") Loginform loginform,  
            @ModelAttribute(value="chatform") Chatform chatform, Model model) {
        if(!database.isChatRegistered(loginform.getUser(), database.getUser(chatform.getChosen()))){
            database.registerChat(new Chat(loginform.getUser(),database.getUser(chatform.getChosen()),false,false));
        } 
        Message message = new Message(loginform.getUser(), chatform.getMelding());
        int chatId = database.getChatId(loginform.getUser(), database.getUser(chatform.getChosen()));
        database.registerMessage(message, chatId);
       
        chatform.setMessages(database.getChat(loginform.getUser(), database.getUser(chatform.getChosen())));
        return "chat";
    }
}
