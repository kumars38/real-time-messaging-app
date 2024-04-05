package com.example.messagingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.messagingapp.databinding.ActivityLogInBinding;

public class LogInActivity extends AppCompatActivity {

    private ActivityLogInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    //add listeners to here when needing to go from one page to another later

    public void loginPressed(View v) {
        if (!this.validUserCredentials()) {
            // reject user, show error msg etc
        }
        else {
            Intent i = new Intent(this, HomePageActivity.class);
            Button b = (Button) v;

            // put whatever info is needed to be passed from login to home
            // i.putExtra("name",name);
            startActivity(i);
        }
    }

    private boolean validUserCredentials() {
        // TODO check with db
        return true;
    }
}