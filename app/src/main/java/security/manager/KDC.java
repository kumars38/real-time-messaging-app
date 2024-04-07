package security.manager;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.messagingapp.activities.HomePageActivity;
import com.example.messagingapp.utils.Firebase_CollectionFields;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;



public class KDC {
    Hashtable < String, SecretKey> user_cache;
    Hashtable < String, SecretKey> session_cache;

    FirebaseFirestore mDatabase;

    //integration to the database db.child is basically query
    public KDC(){
        user_cache = new Hashtable<>();
        session_cache = new Hashtable<>();
        mDatabase = FirebaseFirestore.getInstance();

    }
    public void updateKey(String user_Id,String key) {
        //god the firebase is killing me, totally
        //I need to set key for each user and create a seperate document to store
        mDatabase.collection("userProfile")
                .whereEqualTo("workNumber", user_Id)
                .get()
                .addOnCompleteListener(
                        task -> {
                            if(task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    updateDocument(document.getId(), key);
                                }
                            } else {
                                Log.d("KDC", "Error getting documents: ", task.getException());
                            }

                        }
                );

    }

    private void updateDocument(String documentId, String key) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("userProfile") // Replace with your actual collection name
                .document(documentId)
                .update("secretKey", key)
                .addOnSuccessListener(aVoid -> Log.d("KDC", "Document successfully updated!"))
                .addOnFailureListener(e -> Log.w("KDC", "Error updating document", e));
    }



    public SecretKey getKey(String user_Id) throws NoSuchAlgorithmException {


        if(user_cache.contains(user_Id)){
            return user_cache.get(user_Id);
        }
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);
        SecretKey key = generator.generateKey();
        user_cache.put(user_Id, key);
        return key;

    }
    public SecretKey getSessionKey(String session_Id) throws NoSuchAlgorithmException {
        if(session_cache.contains(session_Id)){
            return session_cache.get(session_Id);
        }
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);
        SecretKey key = generator.generateKey();
        session_cache.put(session_Id, key);
        return key;
    }

    public SecretKey getAuthenticationServerKey(String user_Id) throws NoSuchAlgorithmException {
        if(user_cache.contains(user_Id)){
            return user_cache.get(user_Id);
        }
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);
        SecretKey key = generator.generateKey();
        user_cache.put(user_Id, key);
        return key;
    }

    public SecretKey getTGS_ServerKey(String user_Id) throws NoSuchAlgorithmException {
        if(user_cache.contains(user_Id)){
            return user_cache.get(user_Id);
        }
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);
        SecretKey key = generator.generateKey();
        user_cache.put(user_Id, key);
        return key;
    }

}
