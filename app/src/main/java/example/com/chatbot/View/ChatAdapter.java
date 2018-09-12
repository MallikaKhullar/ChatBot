package example.com.chatbot.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.com.chatbot.Data.ChatMessage;
import example.com.chatbot.Data.ChatThread;
import example.com.chatbot.R;

/**
 * Created by Mallika Priya Khullar on 12/09/18.
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ChatThread thread;

    public ChatAdapter(Context context, ChatThread thread){
        this.context = context;
        this.thread = thread;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_msg, null);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ChatViewHolder) holder).inflate(thread.getThreads().get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return thread.getThreads().get(position).sender.equals(ChatMessage.SenderType.SENDER_ME) ? ChatActivity.VIEW_MY_MSG : ChatActivity.VIEW_BOT_MSG;
    }

    @Override
    public int getItemCount() {
        return thread.getThreads().size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.rvBotMsg) View rvBotMsg;
        @BindView(R.id.rvMyMsg) View rvMyMsg;
//        @BindView(R.id.rvBotMsg) View tvMsg;
        public View mainView;


        public ChatViewHolder(View itemView) {
            super(itemView);
            mainView = itemView;
            ButterKnife.bind(this, mainView);
        }

        void inflate(ChatMessage message){
            showMainView(message.sender);
        }

        void showMainView(ChatMessage.SenderType sender) {
            switch(sender) {
                case SENDER_BOT:
                    rvBotMsg.setVisibility(View.VISIBLE);
                rvMyMsg.setVisibility(View.GONE);
                    break;
                case SENDER_ME:
                    rvBotMsg.setVisibility(View.GONE);
                    rvMyMsg.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

}