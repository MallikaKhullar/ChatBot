package example.com.chatbot.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.chatbot.Controller.ChatController;
import example.com.chatbot.Controller.NetworkController;
import example.com.chatbot.R;

public class ChatActivity extends AppCompatActivity {
    int layout = R.layout.activity_chat; //just for reference

    @BindView(R.id.etMsg) EditText etMsg;
    @BindView(R.id.emptyLayout) View emptyLayout;
    @BindView(R.id.chatContainerLayout) View chatContainerLayout;

    @OnClick(R.id.btnSend)
    void sendClicked() {
        String getMsg = etMsg.getText().toString();
        if (getMsg.length() > 0) {
            ChatController.getInstance().sendMessage(getMsg, new NetworkController.NetworkCallback() {
                @Override
                public void onResponse(JSONObject response) {

                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        } else {
            // please type in a message TOAST / feedback
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        fetchChats();
    }

    void initView() {
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
    }

    void fetchChats(){
        //TODO IF CHATS
        // INFLATE CHATS

        // ELSE
        showEmptyLayout(true);
    }

    void showEmptyLayout(boolean show) {
        emptyLayout.setVisibility(show?View.VISIBLE:View.GONE);
        chatContainerLayout.setVisibility(show?View.GONE:View.VISIBLE);
    }
}
