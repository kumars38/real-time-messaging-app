package com.example.messagingapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.messagingapp.R;
import com.example.messagingapp.utils.Firebase_CollectionFields;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Map;
import java.util.Objects;


public class HomePageActivity extends AppCompatActivity {
    public String uid;
    private TextView textView;

    private String fullName;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        uid = getIntent().getStringExtra("uid");
        //Toast.makeText(this,"uid: "+uid, Toast.LENGTH_SHORT).show();
        userProfileListener();

        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
    public void userProfileListener(){
        DocumentReference userDoc =  db.collection(Firebase_CollectionFields.ATTR_COLLECTION).document(uid);
        userDoc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("DocSnippet", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d("DocSnippet", "Current data: " + snapshot.getData());
                    // Retrieve the full name field
                    getFullName(snapshot);
                    getProfilePreferences(snapshot);

                } else {
                    Log.d("DocSnippet", "Current data: null");
                }
            }
        });
    }
    private void getProfilePreferences(DocumentSnapshot snapshot) {
        Map<String, Object> userProfilePref = (Map<String, Object>) Objects.requireNonNull(snapshot.getData()).get("profilePreferences");
        String userTheme = (String) userProfilePref.get("systemTheme");
        String fontSize = (String) userProfilePref.get("fontSize");

        Log.d("DocSnippet", "Getting profile preferences: " + userProfilePref);

        switch (userTheme){
            case "Blue": setTheme(R.style.BlueTheme); break;
            case "Red": setTheme(R.style.RedTheme); break;
            case "Green": setTheme(R.style.GreenTheme); break;
            default: setTheme(R.style.BlueTheme);
        }
        Log.d("DocSnippet", "Set theme: " + userTheme);

    }

    private void getFullName(DocumentSnapshot snapshot){
        Map<String, Object> preferredNameMap = (Map<String, Object>) snapshot.getData().get("preferredName");
        assert preferredNameMap != null;
        String firstName = (String) preferredNameMap.get("first");
        String lastName = (String) preferredNameMap.get("last");
        Log.d("DocSnippet", "Getting full name: " + preferredNameMap);
        String fullName = firstName +" "+ lastName;
        textView = findViewById(R.id.homePageHeader);
        textView.setText(fullName);
        Log.d("DocSnippet", "Set full name: " + fullName);
    }
    // navigation stuff, can move to a common component later
    public void navHomePressed(View v) {
        // ...
    }

    public void navChatPressed(View v) {
        Intent i = new Intent(this, ChooseRecipientActivity.class);
        i.putExtra("uid",uid);
        // can pass in the user's details here
        // i.putExtra(..., ...)
        startActivity(i);
    }

    public void navPaymentPressed(View v) {
        Intent i = new Intent(this, PaymentActivity.class);
        i.putExtra("uid",uid);
        startActivity(i);
    }

    public void navAccountPressed(View v) {
        Intent i = new Intent(this, AccountActivity.class);
        i.putExtra("uid",uid);
        startActivity(i);
    }
}