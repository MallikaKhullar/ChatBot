package example.com.chatbot.view;

import android.app.Activity;
import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import example.com.chatbot.controllers.ChatController;
import example.com.chatbot.controllers.NetworkController;
import example.com.chatbot.controllers.SharedPreferencesController;
import example.com.chatbot.model.ChatMessageContainer;
import example.com.chatbot.model.ChatMessageData;
import example.com.chatbot.model.ChatThread;
import example.com.chatbot.utils.Utils;

/**
 * Created by Mallika Priya Khullar on 12/09/18.
 */
public class ChatActivityController {

    public static void queueMessage(Activity activity, String getMsg) {
        SharedPreferencesController.getInstance(activity).addToAllChats(getMyPendingChat(getMsg));
    }

    public static void storeMessage(Activity activity, ChatMessageContainer newMessage) {
        SharedPreferencesController.getInstance(activity).addToAllChats(newMessage);
    }

    public static void messageReceived(ChatActivity activity, JSONObject response) {
        ChatMessageContainer chat = ChatMessageContainer.getFromJSON(response);
        storeMessage(activity, chat); // server message
        activity.updateChatUI(chat);
    }

    public static ChatThread getAllMsgs(Activity activity) {
        return SharedPreferencesController.getInstance(activity).getAllChats(SharedPreferencesController.Str.allChats);
    }

    public static ChatMessageContainer getMyNewChat(String getMsg) {
        return getNewChat(getMsg).setPending(false);
    }

    public static ChatMessageContainer getMyPendingChat(String getMsg) {
        return getNewChat(getMsg).setPending(true);
    }

    public static void sendOldChatMessage(final ChatActivity activity, String inputMessage) {
        sendMessage(activity, inputMessage, false);
    }

    public static void sendNewChatMessage(ChatActivity activity, String inputMessage) {
        sendMessage(activity, inputMessage, true);
    }

    public static ChatMessageContainer getNewChat(String getMsg) {
        return new ChatMessageContainer()
                .setSender(ChatMessageContainer.SenderType.SENDER_ME)
                .setMessage(new ChatMessageData(getMsg))
                .setErrorMsg("")
                .setSuccess(1);
    }

    public static void sendMessage(final ChatActivity activity, String inputMessage, boolean isNew) {
        if (inputMessage == null || inputMessage.length() == 0) return;

        if (!Utils.isNetworkAvailable()) {
            if(isNew) activity.updateChatUI(getMyPendingChat(inputMessage)); // my message -> show in list
            queueMessage(activity, inputMessage); // my message -> persis
            return;
        }

        storeMessage(activity, (ChatActivityController.getMyNewChat(inputMessage)));
        if(isNew) activity.updateChatUI(ChatActivityController.getMyNewChat(inputMessage)); // my message -> show in list

        ChatController
                .getInstance()
                .sendMessage(inputMessage, new NetworkController.NetworkCallback() {
                    @Override
                    public void onResponse(JSONObject response) {
                        messageReceived(activity, response);
                    }

                    @Override
                    public void onError(VolleyError error) {
                    } // on fail, do something here
                });
    }

    public static void sendQueuedMessages(ChatActivity activity) {
        ChatThread queuedChats;

        //fetch chat
        queuedChats = SharedPreferencesController
                .getInstance(activity)
                .getAllChats(SharedPreferencesController.Str.allChats);


        // dispatch all previously pending messages
        for (int i = 0; i < queuedChats.getThreads().size(); i++) {
            if (queuedChats.getThreads().get(i).isPending()) {
                sendOldChatMessage(activity, queuedChats.getThreads().get(i).getMessage().getMessage());
                queuedChats.getThreads().get(i).setPending(false);
            }
        }

        // dispatch done, refresh persisted data
        SharedPreferencesController.getInstance(activity).refreshAllChats(queuedChats);
    }
}
