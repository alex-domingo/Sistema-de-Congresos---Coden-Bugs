package com.codenbugs.sistemacongresos.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class PasswordUtil {

    // Calculamos SHA-256 y lo devolvemos codificado en Base64
    public static String sha256Base64(String plain) {
        if (plain == null) {
            return null;
        }
        try {
            // Creamos un objeto de MessageDigest configurado con SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Convertimos el texto a bytes UTF-8 y calculamos el hash
            byte[] hash = md.digest(plain.getBytes(StandardCharsets.UTF_8));
            // Codificamos esos 32 bytes en Base64 (para almacenarlo en la DB como texto)
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo calcular SHA-256", e);
        }
    }
}
