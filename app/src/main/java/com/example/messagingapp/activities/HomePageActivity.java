package com.example.messagingapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
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
        this.updateTheme();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.updateHeader();
        this.updateFontSize();
    }

    private void updateTheme() {
        switch (user.getProfilePreferences().getSystemTheme()){
            case "Blue": setTheme(R.style.BlueTheme); break;
            case "Red": setTheme(R.style.RedTheme); break;
            case "Green": setTheme(R.style.GreenTheme); break;
            default: setTheme(R.style.BlueTheme);
        }
    }

    private void updateHeader() {
        TextView v = findViewById(R.id.homePageHeader);
        v.setText("Hello, "+user.getPreferredName().getFirst()+" "+user.getPreferredName().getLast());
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
    private void updateFontSize(){
        float fontSizeHeader = 18;
        float fontSizeSmall = 15;
        switch(user.getProfilePreferences().getFontSize()) {
            case "Small":
                fontSizeSmall = 12;
                fontSizeHeader = 15;
                break;
            case "Medium":
                fontSizeSmall = 15;
                fontSizeHeader = 18;
                break;
            case "Large":
                fontSizeSmall = 18;
                fontSizeHeader = 21;
                break;
        }
        TextView header = findViewById(R.id.homePageHeader);
        TextView smaller = findViewById(R.id.homePageSecondaryMsg);


        header.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSizeHeader);
        smaller.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSizeSmall);

    }
}