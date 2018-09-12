package example.com.chatbot.view;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

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
import example.com.chatbot.utils.InternetReceiver;
import example.com.chatbot.utils.Utils;

public class ChatActivity extends AppCompatActivity {
    static int layout = R.layout.activity_chat; //just for reference

    public static final int VIEW_MY_MSG = 0;
    public static final int VIEW_BOT_MSG = 1;

    @BindView(R.id.etMsg) EditText etMsg;
    @BindView(R.id.emptyLayout) View emptyLayout;
    @BindView(R.id.recyclerChat) RecyclerView recyclerChat;

    InternetReceiver internetReceiver;
    ChatAdapter adapter;

    boolean dispatchInProgress = false;
    private LinearLayoutManager linearLayoutManager;
    private ChatThread thread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        internetReceiver = new InternetReceiver(callback);
        init();
        fetchChats();
    }

    InternetReceiver.InternetCallback callback = new InternetReceiver.InternetCallback() {
        @Override
        public void internetBack() {
            sendQueuedMessages();
        }
    };

    @OnClick(R.id.btnSend)
    void sendClicked() {
        String inputMessage = etMsg.getText().toString();
        etMsg.setText("");
        ChatActivityController.sendNewChatMessage(this, inputMessage);
    }

    void init() {
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    void fetchChats() {
        // fetch messages from sharedprefs
        thread = ChatActivityController.getAllMsgs(this);

        // set up adapter
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerChat.setLayoutManager(linearLayoutManager);
        adapter = new ChatAdapter(this, thread);
        recyclerChat.setAdapter(adapter);
        linearLayoutManager.scrollToPosition(thread.getThreads().size() - 1);

        // show empty layout if threads are empty
        showEmptyLayout(thread.getThreads() == null || thread.getThreads().size() == 0 ? true : false);
    }

    void showEmptyLayout(boolean show) {
        emptyLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        recyclerChat.setVisibility(show ? View.GONE : View.VISIBLE);
    }


    void updateChatUI(ChatMessageContainer newMessage) {
        // hide empty layout on message received
        showEmptyLayout(false);

        // add msg to recyclerview -> notify adapter -> scroll to new msg

        thread.getThreads().add(newMessage);
        recyclerChat.getAdapter().notifyDataSetChanged();
        linearLayoutManager.scrollToPosition(thread.getThreads().size() - 1);
    }


    void sendQueuedMessages(){
        if(dispatchInProgress) return;
        setDispatchInProgress(true);

        // dispatch the queued messages
        ChatActivityController.sendQueuedMessages(this);
        setDispatchInProgress(false);
    }


    void setDispatchInProgress(boolean ongoing){
        dispatchInProgress = ongoing;
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(internetReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)); //register
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (internetReceiver != null) unregisterReceiver(internetReceiver); //unregister
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Utils.isNetworkAvailable()) sendQueuedMessages(); //check for pending messages
    }
}
