package Tool.RSA;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RSA {
    static Base64.Encoder encoder = Base64.getEncoder();

    public static Map<String, String> genKey(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");
        key.initialize(keySize);
        KeyPair keyPair = key.generateKeyPair();
        String pubKeyStr = encoder.encodeToString(keyPair.getPublic().getEncoded());
        String priKeyStr = encoder.encodeToString(keyPair.getPrivate().getEncoded());
        Map<String, String> keyMap = new HashMap<>();
        keyMap.put("publicKey", pubKeyStr);
        keyMap.put("privateKey", priKeyStr);
        return keyMap;
    }
}