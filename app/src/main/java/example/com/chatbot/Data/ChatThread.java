package example.com.chatbot.Data;

import java.util.ArrayList;

/**
 * Created by Mallika Priya Khullar on 12/09/18.
 */
public class ChatThread {
    private ArrayList<ChatMessage> listMessageData;
    public ChatThread(){
        listMessageData = new ArrayList<>();
    }
    public ArrayList<ChatMessage> getThreads() {
        return listMessageData;
    }
}