package example.com.chatbot.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.com.chatbot.model.ChatMessageContainer;
import example.com.chatbot.model.ChatThread;
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
        return thread.getThreads().get(position).getSender().equals(ChatMessageContainer.SenderType.SENDER_ME) ? ChatActivity.VIEW_MY_MSG : ChatActivity.VIEW_BOT_MSG;
    }

    @Override
    public int getItemCount() {
        return thread.getThreads().size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tvBotMsg) TextView tvBotMsg;
        @BindView(R.id.tvMyMsg) TextView tvMyMsg;
        @BindView(R.id.rvBotMsg) View rvBotMsg;
        @BindView(R.id.rvMyMsg) View rvMyMsg;
        public View mainView;


        public ChatViewHolder(View itemView) {
            super(itemView);
            mainView = itemView;
            ButterKnife.bind(this, mainView);
        }

        void inflate(ChatMessageContainer message){
            switch(message.getSender()) {
                case SENDER_BOT:
                        showBotView(message);
                    break;
                case SENDER_ME:
                        showMyView(message);
                    break;
            }
        }

        void showBotView(ChatMessageContainer message) {
            rvBotMsg.setVisibility(View.VISIBLE);
            rvMyMsg.setVisibility(View.GONE);
            tvBotMsg.setText(message.getMessage().getMessage());
        }

        void showMyView(ChatMessageContainer message) {
            rvBotMsg.setVisibility(View.GONE);
            rvMyMsg.setVisibility(View.VISIBLE);
            tvMyMsg.setText(message.getMessage().getMessage());
        }
    }

}