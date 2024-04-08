package com.example.messagingapp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.messagingapp.R;
import com.example.messagingapp.models.User;
import com.example.messagingapp.singleton.MainUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class AccountActivity extends AppCompatActivity {
    private EditText prefNameET, fontSizeET;
    private Spinner colorSpinner;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        user = MainUser.getInstance().getUserData();
        this.updateTheme();
        setContentView(R.layout.activity_account);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.updateFontSize();
        startEventColorSpinnerListener();
        startEventTextSizeSpinnerListener();
    }
    private void updateButtonPress(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //DocumentReference docRef = db.collection("userProfile").document();

    }
    private void startEventColorSpinnerListener() {
        Spinner spinner = findViewById(R.id.sysColorSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.colorsArray,
                R.layout.spinner_list
        );
        adapter.setDropDownViewResource(R.layout.spinner_list);
        spinner.setAdapter(adapter);

        // Set an item selected listener to perform actions when an item is selected
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the selected item from the spinner
                String selectedColor = parent.getItemAtPosition(position).toString();

                // Display a toast with the selected color
                //Toast.makeText(AccountActivity.this, "Selected color: " + selectedColor, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing when nothing is selected
            }
        });
    }
    private void startEventTextSizeSpinnerListener() {
        Spinner spinner = findViewById(R.id.fontSizeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.fontSizeArray,
                R.layout.spinner_list
        );
        adapter.setDropDownViewResource(R.layout.spinner_list);
        spinner.setAdapter(adapter);

        // Set an item selected listener to perform actions when an item is selected
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the selected item from the spinner
                String selectedColor = parent.getItemAtPosition(position).toString();

                // Display a toast with the selected color
                //Toast.makeText(AccountActivity.this, "Selected color: " + selectedColor, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing when nothing is selected
            }
        });
    }
    private void updateTheme() {
        switch (user.getProfilePreferences().getSystemTheme()){
            case "Blue": setTheme(R.style.BlueTheme); break;
            case "Red": setTheme(R.style.RedTheme); break;
            case "Green": setTheme(R.style.GreenTheme); break;
            default: setTheme(R.style.BlueTheme);
        }
    }
    private void updateFontSize(){
        float fontSize = 15;
        switch(user.getProfilePreferences().getFontSize()){
            case "Small": fontSize = 12; break;
            case "Medium": fontSize = 15; break;
            case "Large": fontSize= 18; break;
        }

        TextView workRole = findViewById(R.id.workRole);
        TextView workRoleR = findViewById(R.id.workRoleResult);
        TextView phoneNumber = findViewById(R.id.phoneNumber);
        TextView phoneNumberR = findViewById(R.id.phoneNumberResult);
        TextView prefName = findViewById(R.id.prefName);
        EditText prefNameR = findViewById(R.id.prefNameResult);
        TextView fSize = findViewById(R.id.fontSize);
        TextView sysColor = findViewById(R.id.sysColor);

        workRole.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSize);
        workRoleR.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSize);
        phoneNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSize);
        phoneNumberR.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSize);
        prefName.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSize);
        prefNameR.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSize);
        fSize.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSize);
        sysColor.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSize);
    }



}