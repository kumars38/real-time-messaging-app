package security.manager;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QueryDocumentSnapshot;

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

    public void updateUserPrivateKey(String user_Id, String key) {
        //god the firebase is killing me, totally
        //I need to set key for each user and create a seperate document to store
        mDatabase.collection("userProfile")
                .whereEqualTo("workNumber", user_Id)
                .get()
                .addOnCompleteListener(
                        task -> {
                            if(task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    updateUserKey(document.getId(), key);
                                }
                            } else {
                                Log.d("KDC", "Error getting documents: ", task.getException());
                            }

                        }
                );

    }

    public void updateMessagingSession(String user_Id, String key){
        mDatabase.collection("userProfile")
                .whereEqualTo("workNumber", user_Id)
                .get()
                .addOnCompleteListener(
                        task -> {
                            if(task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    updateMessagingKey(document.getId(), key);
                                }
                            } else {
                                Log.d("KDC", "Error getting documents: ", task.getException());
                            }

                        }
                );
    }

    private void updateUserKey(String documentId, String key) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("userProfile") // Replace with your actual collection name
                .document(documentId)
                .update("secretKey", key)
                .addOnSuccessListener(aVoid -> Log.d("KDC", "Document successfully updated!"))
                .addOnFailureListener(e -> Log.w("KDC", "Error updating document", e));
    }

    private void updateMessagingKey(String documentId, String key){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("userProfile") // Replace with your actual collection name
                .document(documentId)
                .update("messagingKey", key)
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
