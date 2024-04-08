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
import com.example.messagingapp.models.User;
import com.example.messagingapp.singleton.MainUser;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
    ArrayList<String> dummyDatabase = new ArrayList<>();
    // Dummy db  to log transactions --> pretty sure we were gonna centralize this guy tho??
    //where is the real db??? how we gonna format her???

    private EditText amountField;
    private EditText recipientNameField;
    private EditText paymentMessageField;
    private TextView headerTextView;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = MainUser.getInstance().getUserData();
        //EdgeToEdge.enable(this);
        updateTheme();
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        amountField = findViewById(R.id.amountField);
        recipientNameField = findViewById(R.id.recipientName);
        paymentMessageField = findViewById(R.id.paymentMessage);
        //headerTextView = findViewById(R.id.messagingHeader);
        PaymentAPI paymentAPI = new PaymentAPI();

        Button sendPaymentButton = findViewById(R.id.sendPayment);
        sendPaymentButton.setOnClickListener(v -> {
            double amount;
            amount = Double.parseDouble(amountField.getText().toString());
            String recipientName = recipientNameField.getText().toString();
            String paymentMessage = paymentMessageField.getText().toString();
            paymentAPI.sendPayment(recipientName, amount);
        });

        // update header like in regular messaging app


        // start listener to track the amount added
        startAmountEnteredListener();
        startRecipientNameEnteredListener();
        startPaymentMessageEnteredListener();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("recipientName")) {
            String recipientName = intent.getStringExtra("recipientName");
            updateHeader(recipientName);
            recipientNameField.setText(recipientName);
        }



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
        //boolean paymentStatus = PaymentAPI.sendPayment("recipientName", amount);
        JSONObject jsonResponse = PaymentAPI.sendPayment("recipientName", amount);
        //parse JSON response

        int paymentStatus = -1; // Default value for error
        try {
            if (jsonResponse != null) {
                paymentStatus = jsonResponse.getInt("status");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }




        // alert when payment is done
        if (paymentStatus==1) {
            showAlert("Payment Transaction Success");
        } else {
            showAlert("Payment Transaction Failure");
        }
    }

    private void updateHeader(String recipientName) {
        TextView headerTextView = findViewById(R.id.paymentInterfaceTitle);
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

    private void startRecipientNameEnteredListener() {
        recipientNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this example
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateHeader(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed for this example
            }
        });
    }

    private void startPaymentMessageEnteredListener() {
        paymentMessageField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this example
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Perform any actions related to the payment message
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed for this example
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

    private void updateTheme() {
        switch (user.getProfilePreferences().getSystemTheme()){
            case "Blue": setTheme(R.style.BlueTheme); break;
            case "Red": setTheme(R.style.RedTheme); break;
            case "Green": setTheme(R.style.GreenTheme); break;
            default: setTheme(R.style.BlueTheme);
        }
    }
}