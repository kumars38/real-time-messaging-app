package com.example.messagingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.messagingapp.databinding.ActivityLogInBinding;
import com.example.messagingapp.utils.Firebase_CollectionFields;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

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
        if (!this.validLogInCredFormat()) {
            // TODO reject user, show error msg etc
        }
        else {
            Button b = (Button) v;
            user_signIn();
            //startActivity(i);
        }
    }


    public void user_signIn(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Firebase_CollectionFields.ATTR_COLLECTION)
                .whereEqualTo(Firebase_CollectionFields.ATTR_USERNAME, binding.EmailInput.getText().toString())
                .whereEqualTo(Firebase_CollectionFields.ATTR_PASSWORD, binding.pwdInput.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null && !task.getResult().getDocuments().isEmpty()) {
                        DocumentSnapshot docSnap = task.getResult().getDocuments().get(0);
                        String clientID = docSnap.getId();
                        Intent home_page = new Intent(this, HomePageActivity.class);
                        home_page.putExtra("uid",clientID);
                        displayHelpText("Company Login Successful");
                        startActivity(home_page);
                    }
                    else {
                        displayHelpText("User does not exist!");
                    }

                });
    }


    private void displayHelpText(String txt){
        Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_SHORT).show();
    }


    //TODO: if time permits, add this to a separate file to segregate concern
    private boolean validLogInCredFormat() {
        if(binding.EmailInput.getText().toString().trim().isEmpty()){
            displayHelpText("Username Required");
            return false;
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(binding.EmailInput.getText().toString()).matches()){
            displayHelpText("Please Enter a Valid Username");
            return false;
        }

        else if(binding.pwdInput.getText().toString().trim().isEmpty()){
            displayHelpText("Password Required");
            return false;
        }

        else {
            return true;
        }


    }

    /*
    // TODO REMOVE TESTING DB LATER
    private void test_addingtoFireDB(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> data = new HashMap<>();
        data.put("first_name", "Donald");
        data.put("last_name", "Duck");

        db.collection("users").add(data).addOnSuccessListener(documentReference -> {
            Toast.makeText(getApplicationContext(), "Insert Success", Toast.LENGTH_SHORT).show();
        })
                .addOnFailureListener(exception -> {
                    Toast.makeText(getApplicationContext(), "Insert Error", Toast.LENGTH_SHORT).show();
                });
    }
     */

}