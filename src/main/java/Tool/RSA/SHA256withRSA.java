package Tool.RSA;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SHA256withRSA {

    private static final String ALGORITHM = "SHA256withRSA";

    public static String sign(String data, PrivateKey privateKey) throws Exception {

        Signature signature = Signature.getInstance(ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data.getBytes());

        byte[] signedBytes = signature.sign();

        return Base64.getEncoder().encodeToString(signedBytes);
    }

    public static String signWithKeyFile(String data, String privateKeyFilePath) throws Exception {

        PrivateKey privateKey = loadPrivateKeyFromFile(privateKeyFilePath);
        return sign(data, privateKey);
    }

    public static boolean verify(String data, String signatureBase64, PublicKey publicKey) throws Exception {

        Signature signature = Signature.getInstance(ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data.getBytes());

        byte[] signatureBytes = Base64.getDecoder().decode(signatureBase64);

        return signature.verify(signatureBytes);
    }

    public static boolean verifyWithKeyFile(String data, String signatureBase64, String publicKeyFilePath) throws Exception {

        PublicKey publicKey = loadPublicKeyFromFile(publicKeyFilePath);
        return verify(data, signatureBase64, publicKey);
    }

    public static PrivateKey loadPrivateKeyFromFile(String filePath) throws Exception {

        byte[] keyBytes = Base64.getDecoder().decode(readKeyFile(filePath));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePrivate(keySpec);
    }

    public static PublicKey loadPublicKeyFromFile(String filePath) throws Exception {

        byte[] keyBytes = Base64.getDecoder().decode(readKeyFile(filePath));
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePublic(keySpec);
    }

    private static String readKeyFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(new File(filePath).toPath())).trim();
    }
}