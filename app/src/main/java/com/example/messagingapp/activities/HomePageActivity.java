package com.example.messagingapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;

import com.example.messagingapp.R;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    // navigation stuff, can move to a common component later
    public void navHomePressed(View v) {
        // ...
    }

    public void navChatPressed(View v) {
        Intent i = new Intent(this, ChooseRecipientActivity.class);

        // can pass in the user's details here
        // i.putExtra(..., ...)
        startActivity(i);
    }

    public void navPaymentPressed(View v) {
        Intent i = new Intent(this, PaymentActivity.class);
        startActivity(i);
    }

    public void navAccountPressed(View v) {
        Intent i = new Intent(this, AccountActivity.class);
        startActivity(i);
    }
}