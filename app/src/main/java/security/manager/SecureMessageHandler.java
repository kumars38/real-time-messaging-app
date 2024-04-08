package security.manager;

import android.util.Log;

import com.example.messagingapp.models.Message;
import com.example.messagingapp.utils.Firebase_CollectionFields;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class SecureMessageHandler extends Thread{
    private int port;
    private AuthenticationServer AS;
    // Write to DB
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public SecureMessageHandler(int port, AuthenticationServer AS){
        this.port = port;
        this.AS = AS;
    }

    public boolean sendMessage(Message msg, String pairID){

        AtomicBoolean state = new AtomicBoolean(false);
        db.collection(Firebase_CollectionFields.ATTR_COLLECTION_MESSAGING)
                // generate a unique UUID for the document
                .document(UUID.randomUUID().toString().substring(0,20))
                .set(msg)
                .addOnSuccessListener(aVoid -> {Log.d("DEBUG_MESSAGING", "Message (pairID=" + pairID +
                        ", contents="+msg.getMessage()+") written to DB");
                    state.set(true);
                }

                )
                .addOnFailureListener(aVoid ->
                        Log.d("DEBUG_MESSAGING", "Error writing message: "+msg.getMessage()));

        return state.get();
    }

    public void run(){

    }
}
