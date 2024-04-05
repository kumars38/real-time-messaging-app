package com.example.messagingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.messagingapp.R;

import java.util.ArrayList;

public class ChooseRecipientActivity extends AppCompatActivity {

    ArrayList<String>  recipientsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_choose_recipient);
        this.getRecipientsFromDB();
        this.setRecipientButtons();
    }

    private void getRecipientsFromDB() {
        // *need to get the recipients from account DB
        // mock data
        recipientsList.add("A B");
        recipientsList.add("C D");
        recipientsList.add("E F");
        recipientsList.add("G H");
        // recipientsList.add("I J");
    }

    // can expand for however many people we want for our demo DB
    // right now its 5
    private void setRecipientButtons() {
        final int[] buttons = {R.id.recipientButton1, R.id.recipientButton2,
                R.id.recipientButton3, R.id.recipientButton4, R.id.recipientButton5};

        for (int i=0; i<5; i++) {
            Button b = findViewById(buttons[i]);
            if (i >= recipientsList.size()) {
                b.setVisibility(View.GONE);
            }
            else {
                String r = recipientsList.get(i);
                Log.d("recipient "+(i+1), r);
                b.setText(r);
            }
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