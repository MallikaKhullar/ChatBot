package example.com.chatbot.Controller;

/**
 * Created by Mallika Priya Khullar on 12/09/18.
 */
public class ChatController {

    static ChatController instance;

    public void sendMessage(String textMsg, NetworkController.NetworkCallback callback){
        NetworkController.sendMessage(textMsg, callback);
    }

    public static ChatController getInstance(){
        return instance; //TODO - update later
    }
}
