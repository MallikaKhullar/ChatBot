package example.com.chatbot.controllers;

/**
 * Created by Mallika Priya Khullar on 12/09/18.
 */
public class ChatController {

    static ChatController instance;

    public static ChatController getInstance() {
        if (instance == null) instance = new ChatController();
        return instance;
    }

    public void sendMessage(String textMsg, NetworkController.NetworkCallback callback){
        NetworkController.sendMessage(textMsg, callback);
    }

}
