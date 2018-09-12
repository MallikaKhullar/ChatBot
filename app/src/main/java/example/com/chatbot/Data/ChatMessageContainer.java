package example.com.chatbot.Data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mallika Priya Khullar on 12/09/18.
 */
public class ChatMessageContainer {
    ChatMessageData message;
    String errorMsg;
    int success;
    SenderType sender = SenderType.SENDER_BOT;


    public static String SUCCESS = "success";
    public static String ERROR = "errorMessage";
    public static String MESSAGE = "message";

    public ChatMessageContainer() {
    }

    public ChatMessageContainer(ChatMessageData message, String errorMsg, int success, SenderType sender) {
        this.message = message;
        this.errorMsg = errorMsg;
        this.success = success;
        this.sender = sender;
    }


    public ChatMessageData getMessage() {
        return message;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getSuccess() {
        return success;
    }

    public SenderType getSender() {
        return sender;
    }

    public enum SenderType {
        SENDER_ME, SENDER_BOT
    }

    public ChatMessageContainer setMessage(ChatMessageData message) {
        this.message = message;
        return this;
    }

    public ChatMessageContainer setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public ChatMessageContainer setSuccess(int success) {
        this.success = success;
        return this;
    }

    public ChatMessageContainer setSender(SenderType sender) {
        this.sender = sender;
        return this;
    }


    public static ChatMessageContainer getFromJSON(JSONObject userJson) {
        try {
            ChatMessageContainer chat = new ChatMessageContainer()
                    .setSuccess(userJson.getInt(SUCCESS))
                    .setErrorMsg(userJson.getString(ERROR))
                    .setMessage(ChatMessageData.getFromJSON(userJson.getJSONObject(MESSAGE)));
            return chat;
        } catch (JSONException e) {
            return null;
        }
    }
}
