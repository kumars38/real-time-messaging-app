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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.messagingapp.R;
import com.example.messagingapp.utils.Firebase_CollectionFields;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class MessagingActivity extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String uid;
    ArrayList<String> messageList = new ArrayList<>();

    LinearLayout messageLayoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // getting info passed from previous activity
        Intent i = getIntent();
        uid = getIntent().getStringExtra("uid");

        String recipientName = i.getStringExtra("recipientInfo");
        Log.d("recipientName",recipientName);

        setTitle("Messaging "+recipientName);
        userProfileListener();

        setContentView(R.layout.activity_messaging);

        messageLayoutList = findViewById(R.id.messageLayout);

        this.updateHeader(recipientName);
        this.updateMessageLayout();
        this.startEnterMessageListener();
    }
    public void userProfileListener(){
        DocumentReference userDoc =  db.collection(Firebase_CollectionFields.ATTR_COLLECTION).document(uid);
        userDoc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("DocSnippet", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d("DocSnippet", "Current data: " + snapshot.getData());
                    getProfilePreferences(snapshot);

                } else {
                    Log.d("DocSnippet", "Current data: null");
                }
            }
        });
    }
    private void getProfilePreferences(DocumentSnapshot snapshot) {
        Map<String, Object> userProfilePref = (Map<String, Object>) Objects.requireNonNull(snapshot.getData()).get("profilePreferences");
        String userTheme = (String) userProfilePref.get("systemTheme");
        String fontSize = (String) userProfilePref.get("fontSize");

        Log.d("DocSnippet", "Getting profile preferences: " + userProfilePref);

        switch (userTheme){
            case "Blue": setTheme(R.style.BlueTheme); break;
            case "Red": setTheme(R.style.RedTheme); break;
            case "Green": setTheme(R.style.GreenTheme); break;
            default: setTheme(R.style.BlueTheme);
        }
        Log.d("DocSnippet", "Set theme: " + userTheme);

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