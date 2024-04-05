package com.example.messagingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.messagingapp.R;

import java.util.ArrayList;

public class ChooseRecipientActivity extends AppCompatActivity {

    ArrayList<String>  recipientsList = new ArrayList<>();
    LinearLayout layoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_choose_recipient);

        layoutList = findViewById(R.id.recipientsLayout);

        this.getRecipientsFromDB();
        this.setRecipientButtons();
    }

    private void getRecipientsFromDB() {
        // TODO get the actual recipients from our mock account DB

        for (int i=0; i<10; i++) {
            recipientsList.add("Employee "+i);
        }
    }

    private void setRecipientButtons() {
        // TODO iterate over User class not Strings
        for (String recipientName: recipientsList) {
            View v = getLayoutInflater().inflate(R.layout.recipient,null,false);
            Button b = v.findViewById(R.id.recipientButton);
            b.setText(recipientName);
            layoutList.addView(v);
        }
    }

    public void launchMessaging(View v) {
        Intent i = new Intent(this, MessagingActivity.class);
        Button b = (Button) v;

        // replace with full recipient obj
        String chosenRecipient = b.getText().toString();
        i.putExtra("recipientInfo",chosenRecipient);
        startActivity(i);
    }
}