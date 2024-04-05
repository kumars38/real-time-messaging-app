package com.example.messagingapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.messagingapp.R;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
    ArrayList<String> dummyDatabase = new ArrayList<>(); // Dummy db  to log transactions --> pretty sure we were gonna centralize this guy tho??
    //where is the real db??? how we gonna format her???
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent i = getIntent();
        String recipientName = i.getStringExtra("recipientInfo");
        //is this a bad spot to assert lol
        assert recipientName != null;
        Log.d("recipientName", recipientName);

        // update header like in regular messaging app
        updateHeader("Sending Money to " + recipientName);

        // start listener to track the amount added
        startAmountEnteredListener();



    }

    public void sendPayment(View v) {
        EditText amountField;
        amountField = findViewById(R.id.amountField);
        double amount = Double.parseDouble(amountField.getText().toString());
        if (amount <= 0) {
            // idk if there are other things we gotta check
            Toast.makeText(this, "Amount must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }

        // call da api
        boolean paymentStatus = PaymentAPI.sendPayment("recipientName", amount);

        // alert when payment is done
        if (paymentStatus) {
            showAlert("Payment Transaction Success");
        } else {
            showAlert("Payment Transaction Failure");
        }
    }

    private void updateHeader(String recipientName) {
        TextView headerTextView = findViewById(R.id.messagingHeader);
        headerTextView.setText(recipientName);
    }

    private void startAmountEnteredListener() {
        EditText amountField = findViewById(R.id.amountField);
        Button sendPaymentButton = findViewById(R.id.sendPayment);
        amountField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // implementation if needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // implementation if needed
            }

            @Override
            public void afterTextChanged(Editable s) {
                // implementation after text has changed
            }
        });
    }


    private void showAlert(String message) {
        // overriding the general showAlert
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing or handle additional actions
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}