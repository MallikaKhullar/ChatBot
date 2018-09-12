package example.com.chatbot.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.chatbot.controllers.ChatController;
import example.com.chatbot.controllers.NetworkController;
import example.com.chatbot.controllers.SharedPreferencesController;
import example.com.chatbot.model.ChatMessageContainer;
import example.com.chatbot.model.ChatMessageData;
import example.com.chatbot.model.ChatThread;
import example.com.chatbot.R;
import example.com.chatbot.utils.Utils;

public class ChatActivity extends AppCompatActivity {
    int layout = R.layout.activity_chat; //just for reference

    @BindView(R.id.etMsg) EditText etMsg;
    @BindView(R.id.emptyLayout) View emptyLayout;
    @BindView(R.id.recyclerChat) RecyclerView recyclerChat;

    private ChatThread thread;
    private LinearLayoutManager linearLayoutManager;
    private ChatAdapter adapter;
    public static final int VIEW_MY_MSG = 0;
    public static final int VIEW_BOT_MSG = 1;


    @OnClick(R.id.btnSend)
    void sendClicked() {
        String getMsg = etMsg.getText().toString();
        etMsg.setText("");
        if (getMsg.length() > 0) sendNewChatMessage(getMsg);
    }

    void sendNewChatMessage(String getMsg){
        if(!Utils.isNetworkAvailable()){

            return;
        }

        addChat(new ChatMessageContainer()
                .setSender(ChatMessageContainer.SenderType.SENDER_ME)
                .setMessage(new ChatMessageData(getMsg))
                .setErrorMsg("")
                .setSuccess(1), true); // my message

        ChatController.getInstance().sendMessage(getMsg, new NetworkController.NetworkCallback() {
            @Override
            public void onResponse(JSONObject response) {
                addChat(ChatMessageContainer.getFromJSON(response), true); // my message
            }

            @Override
            public void onError(VolleyError error) {
                // please type in a message TOAST / feedback
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        thread = new ChatThread();
        fetchChats();
    }

    void initView() {
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    void fetchChats(){
        thread = SharedPreferencesController.getInstance(this).getAllChats();
        //TODO IF CHATS
        // INFLATE CHATS

        // ELSE
        showEmptyLayout(thread.getThreads() == null || thread.getThreads().size() == 0? true:false);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerChat.setLayoutManager(linearLayoutManager);
        adapter = new ChatAdapter(this, thread);
        recyclerChat.setAdapter(adapter);
    }

    void showEmptyLayout(boolean show) {
        emptyLayout.setVisibility(show?View.VISIBLE:View.GONE);
        recyclerChat.setVisibility(show?View.GONE:View.VISIBLE);
    }

    void addChat(ChatMessageContainer newMessage, boolean scroll){
        showEmptyLayout(false);
        thread.getThreads().add(newMessage);
        SharedPreferencesController.getInstance(this).addToAllChats(newMessage);
        Log.d("La", "kjjh");
        recyclerChat.getAdapter().notifyDataSetChanged();
        if(scroll)linearLayoutManager.scrollToPosition(thread.getThreads().size() - 1);
    }
}
