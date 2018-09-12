package example.com.chatbot.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mallika Priya Khullar on 12/09/18.
 */
public class ChatMessageData {
    String senderName;
    int chatBotId;
    String message;

    public static String BOTNAME = "chatBotName";
    public static String BOTID = "chatBotID";
    public static String MESSAGE = "message";


    public ChatMessageData() {
    }

    public ChatMessageData(String chatBotName, int chatBotId, String message) {
        this.senderName = chatBotName;
        this.chatBotId = chatBotId;
        this.message = message;
    }

    public ChatMessageData(String message) {
        this.senderName = "Mallika";
        this.chatBotId = 0;
        this.message = message;
    }


    public ChatMessageData setChatBotName(String chatBotName) {
        this.senderName = chatBotName;
        return this;
    }

    public ChatMessageData setChatBotId(int chatBotId) {
        this.chatBotId = chatBotId;
        return this;
    }

    public ChatMessageData setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getSenderName() {
        return senderName;
    }

    public int getChatBotId() {
        return chatBotId;
    }

    public String getMessage() {
        return message;
    }


    public static ChatMessageData getFromJSON(JSONObject userJson) {
        try {
            ChatMessageData chat = new ChatMessageData()
                    .setChatBotId(userJson.getInt(BOTID))
                    .setChatBotName(userJson.getString(BOTNAME))
                    .setMessage(userJson.getString(MESSAGE));
            return chat;
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject putToJSON() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(BOTNAME, senderName);
            jsonObject.put(BOTID, chatBotId);
            jsonObject.put(MESSAGE, message);
            return jsonObject;
        } catch (JSONException e) {
            return null;
        }
    }
}
