package com.example.messagingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.messagingapp.R;

import java.util.ArrayList;

public class MessagingActivity extends AppCompatActivity {

    ArrayList<String> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // getting info passed from previous activity
        Intent i = getIntent();
        String recipientName = i.getStringExtra("recipientInfo");
        Log.d("recipientName",recipientName);

        setContentView(R.layout.activity_messaging);

        this.updateHeader(recipientName);
        this.startEnterMessageListener();
    }

    public void saveMessage(View v) {
        EditText e = findViewById(R.id.messageField);
        String s = e.getText().toString();
        messageList.add(s);
        // reset the field
        e.setText("");
        Log.d("asdfgh",s);
    }

    private void updateHeader(String recipName) {
        TextView t = findViewById(R.id.messagingHeader);
        t.setText("Messaging: "+recipName);
    }

    private void startEnterMessageListener() {
        EditText e = findViewById(R.id.messageField);
        e.addTextChangedListener(new TextWatcher() {
            Button send = findViewById(R.id.sendMsgButton);
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                send.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}