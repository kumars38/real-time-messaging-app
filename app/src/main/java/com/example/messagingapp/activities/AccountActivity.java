package com.example.messagingapp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import com.example.messagingapp.R;

public class AccountActivity extends AppCompatActivity {
    private EditText prefNameET, fontSizeET;
    private Spinner sysColorSpinner;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        prefNameET = findViewById(R.id.prefNameResult);
        fontSizeET = findViewById(R.id.fontSizeResult);
        sysColorSpinner = findViewById(R.id.sysColorSpinner);
        startEventColorSpinnerListener();

        Button updateProfileBtn = findViewById(R.id.updateProfileBtn);
        updateProfileBtn.setOnClickListener(this::updateProfileBtnClicked);
    }
    private void startEventColorSpinnerListener() {
        Spinner spinner = findViewById(R.id.sysColorSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.colorsArray,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

    private void updateProfileBtnClicked(View v){
        String prefName = prefNameET.getText().toString();
        String fontSize = fontSizeET.getText().toString();
        String sysColor = sysColorSpinner.getSelectedItem().toString();

       //logic for prefName

        //logic for fontSize


        //logic for systemTheme
        switch(sysColor){
            case "Blue": setTheme(R.style.BlueTheme); break;
            case "Red": setTheme(R.style.RedTheme); break;
            case "Green": setTheme(R.style.GreenTheme); break;
        }
        recreate();
    }


}