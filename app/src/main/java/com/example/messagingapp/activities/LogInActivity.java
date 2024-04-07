package com.example.messagingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.QuerySnapshot;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicReference;

import javax.crypto.SecretKey;

import security.manager.CryptoMethods;
import security.manager.KDC;

//upon login, KDC give user a key,
public class LogInActivity extends AppCompatActivity {

    private ActivityLogInBinding binding;
    private static final KDC kdc = new KDC();


    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    //add listeners to here when needing to go from one page to another later

    public void loginPressed(View v) throws Exception{

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

        SecretKey key = CryptoMethods.generateKey();
        SecretKey key2 = CryptoMethods.generateKey();
        String str_key = CryptoMethods.SKeyToString(key);
        String str_key2 = CryptoMethods.SKeyToString(key2);
        this.userId = getUserId(binding.EmailInput.getText().toString());

        try{
        PrintWriter cacheStore = new PrintWriter("cache");
        cacheStore.println(this.userId);
        cacheStore.close();
        }
        catch (Exception ex){
            Log.e("Login", "Cache failed to store, Please reset PC authentication");
        }

        //store userId in a local file
        kdc.updateUserPrivateKey(this.userId, str_key);
        kdc.updateMessagingSession(this.userId, str_key2);

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

    private String getUserId(String email){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        AtomicReference<String> workNumber = new AtomicReference<>();
        db.collection("userProfile")
                .whereEqualTo("email", email)  // Assuming 'email' is the field name
                .limit(1)  // Assumes email is unique and you only want one result
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (!querySnapshot.isEmpty()) {
                            // Assuming 'workNumber' is the field you're looking for
                            workNumber.set(querySnapshot.getDocuments().get(0).getString("workNumber"));

                            Log.d("WorkNumberFound", "Work number for email " + email + " is: " + workNumber);

                            // Do something with the workNumber, like updating UI
                        } else {
                            Log.d("NoDocumentFound", "No document found with the email " + email);
                            // Handle the case where no document was found
                        }
                    } else {
                        Log.e("FirestoreError", "Error getting documents: ", task.getException());
                        // Handle the error
                    }
                });

        return workNumber.toString();
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