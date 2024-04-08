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

import javax.crypto.SecretKey;

import security.manager.CryptoMethods;
import security.manager.KDC;

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


        String userId = MainUser.getUserData().getWorkNumber();
        SecretKey key = CryptoMethods.generateKey();
        SecretKey key2 = CryptoMethods.generateKey();
        String str_key = CryptoMethods.SKeyToString(key);
        String str_key2 = CryptoMethods.SKeyToString(key2);
        KDC kdc = new KDC();
        kdc.updateUserPrivateKey(userId, str_key);
        kdc.updateMessagingSession(userId, str_key2);

        this.updateHeader();

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