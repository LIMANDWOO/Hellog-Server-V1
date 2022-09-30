package com.hellog.global.lib.encrypt;

import io.jsonwebtoken.SignatureAlgorithm;

public interface Encrypt {

    SignatureAlgorithm getSignatureAlgorithm();

    String encode(String data);

    boolean match(String originalData, String encryptedData);
}