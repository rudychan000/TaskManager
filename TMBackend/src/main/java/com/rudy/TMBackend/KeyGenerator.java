package com.rudy.TMBackend;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class KeyGenerator {
    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String base64EncodedKey = Encoders.BASE64.encode(key.getEncoded());
        System.out.println("Base64-encoded key: " + base64EncodedKey);
    }
}
