package com.example.messagingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.messagingapp.R;

import java.util.ArrayList;


//then upon message user authenticate then get chat with messaging key


public class MessagingActivity extends AppCompatActivity {

    ArrayList<String> messageList = new ArrayList<>();

    LinearLayout messageLayoutList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // getting info passed from previous activity
        Intent i = getIntent();
        String recipientName = i.getStringExtra("recipientInfo");
        Log.d("recipientName",recipientName);

        setTitle("Messaging "+recipientName);
        setContentView(R.layout.activity_messaging);

        messageLayoutList = findViewById(R.id.messageLayout);

        this.updateHeader(recipientName);
        this.updateMessageLayout();
        this.startEnterMessageListener();
    }

    public void saveMessage(View v) {
        // extract current message input
        // TODO save as Message class which also contains timestamp, ...
        EditText e = findViewById(R.id.messageField);
        String s = e.getText().toString();

        // TODO this part would interact with message log backend
        messageList.add(s);

        // reset the text field
        e.setText("");
        Log.d("saved message: ", s); // debug log

        // update view
        this.updateMessageLayout();
    }



    // TODO pull from chat log instead of messageList to update the view
    private void updateMessageLayout() {

        // one approach, clear all views and add the entirety of log for this
        // sender/recipient
        messageLayoutList.removeAllViews();
        for (String message : messageList) {
            View v = getLayoutInflater().inflate(R.layout.message,null,false);
            TextView tv = v.findViewById(R.id.messageFromSender);
            tv.setText(message);
            messageLayoutList.addView(v);
        }
    }

    private void updateHeader(String recipName) {
        TextView t = findViewById(R.id.msgHeader);
        t.setText(recipName);
    }

    private void startEnterMessageListener() {
        EditText e = findViewById(R.id.messageField);
        e.addTextChangedListener(new TextWatcher() {
            Button send = findViewById(R.id.sendMsgButton);
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    send.setEnabled(true);
                    send.setAlpha(1);
                }
                else {
                    send.setEnabled(false);
                    send.setAlpha(0.5F);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}