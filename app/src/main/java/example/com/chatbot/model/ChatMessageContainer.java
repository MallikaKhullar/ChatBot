package example.com.chatbot.model;

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
    public static String SENDER = "sender";
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
        SENDER_ME(0), SENDER_BOT(1);

        private final int value;
        SenderType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static SenderType getType(int val) {
            return SenderType.values()[val];
        }
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


    public static ChatMessageContainer getFromJSON(JSONObject chatJson) {

        try {
            ChatMessageContainer chat = new ChatMessageContainer()
                    .setSuccess(chatJson.getInt(SUCCESS))
                    .setErrorMsg(chatJson.getString(ERROR))
                    .setMessage(ChatMessageData.getFromJSON(chatJson.getJSONObject(MESSAGE)));

            if(chatJson.has(SENDER)) chat.setSender(SenderType.getType(chatJson.getInt(SENDER)));
            else chat.setSender(SenderType.SENDER_BOT);

            return chat;
        } catch (JSONException e) {
            return null;
        }
    }


    public JSONObject putToJSON() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(SUCCESS, success);
            jsonObject.put(ERROR, errorMsg);
            jsonObject.put(MESSAGE, message.putToJSON());
            jsonObject.put(SENDER, sender.getValue());
            return jsonObject;
        } catch (JSONException e) {
            return null;
        }
    }
}
