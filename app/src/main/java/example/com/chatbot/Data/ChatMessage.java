package example.com.chatbot.Data;

import butterknife.Optional;

/**
 * Created by Mallika Priya Khullar on 12/09/18.
 */
public class ChatMessage {
    ChatMessageData message;
    String errorMsg;
    int success;
    public enum SenderType {
        SENDER_ME, SENDER_BOT
    }

    public SenderType sender = SenderType.SENDER_BOT;
}
