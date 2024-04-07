package com.example.messagingapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.TextView;

import com.example.messagingapp.R;
import com.example.messagingapp.models.User;
import com.example.messagingapp.singleton.MainUser;

public class HomePageActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = MainUser.getInstance().getUserData();

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.updateHeader();
    }

    private void updateHeader() {
        TextView v = findViewById(R.id.homePageHeader);
        v.setText("Hello, "+user.getEmployeeName().getFirst());
    }

    // navigation stuff, can move to a common component later
    public void navHomePressed(View v) {
        // ...
    }

    public void navChatPressed(View v) {
        Intent i = new Intent(this, ChooseRecipientActivity.class);
        i.putExtra("user",user);

        // can pass in the user's details here
        // i.putExtra(..., ...)
        startActivity(i);
    }

    public void navPaymentPressed(View v) {
        Intent i = new Intent(this, PaymentActivity.class);
        i.putExtra("user",user);
        startActivity(i);
    }

    public void navAccountPressed(View v) {
        Intent i = new Intent(this, AccountActivity.class);
        i.putExtra("user",user);
        startActivity(i);
    }
}