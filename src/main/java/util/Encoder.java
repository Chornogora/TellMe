package util;

import java.util.Base64;

public class Encoder {
    public static String encode(String line) {
        byte[] encodeBytes = Base64.getEncoder().encode(line.getBytes());
        return new String(encodeBytes);
    }

    public static String decode(String line) {
        byte[] decodeBytes = Base64.getDecoder().decode(line.getBytes());
        return new String(decodeBytes);
    }
}
