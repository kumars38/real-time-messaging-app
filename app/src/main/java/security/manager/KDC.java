package security.manager;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;

public class KDC {
    Hashtable < String, SecretKey> user_cache;
    Hashtable < String, SecretKey> session_cache;
    public KDC(){
        user_cache = new Hashtable<>();
        session_cache = new Hashtable<>();
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
    public SecretKey getSessionKey(String user_Id) throws NoSuchAlgorithmException {
        if(session_cache.contains(user_Id)){
            return session_cache.get(user_Id);
        }
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);
        SecretKey key = generator.generateKey();
        session_cache.put(user_Id, key);
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
