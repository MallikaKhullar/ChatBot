package example.com.chatbot.model;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Mallika Priya Khullar on 12/09/18.
 */
public class ChatThread {
    private ArrayList<ChatMessageContainer> listMessageData;

    public ChatThread(ArrayList<ChatMessageContainer> listMessageData) {
        this.listMessageData = listMessageData;
    }

    public ChatThread(){
        listMessageData = new ArrayList<>();
    }
    public ArrayList<ChatMessageContainer> getThreads() {
        return listMessageData;
    }

    public static ChatThread getFromJSON(JSONArray obj) throws JSONException {
        ArrayList<ChatMessageContainer> list = new ArrayList<>();
        for(int i=0;i<obj.length();i++){
            list.add(ChatMessageContainer.getFromJSON(obj.getJSONObject(i)));
        }

        return new ChatThread(list);
    }

}
