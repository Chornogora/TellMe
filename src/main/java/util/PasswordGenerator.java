package util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

public class PasswordGenerator {
    private static final int MIN_LENGTH_PASSWORD = 8;
    private static final int MAX_LENGTH_PASSWORD = 16;
    private static final int DIFF_PASSWORD_LENGTH = MAX_LENGTH_PASSWORD - MIN_LENGTH_PASSWORD;

    private static final int FIRST_ASCII_NUMBER = 48;
    private static final int LAST_ASCII_NUMBER = 57;
    private static final int DIFF_ASCII_NUMBER = LAST_ASCII_NUMBER - FIRST_ASCII_NUMBER;

    private static final int FIRST_UPPER_ASCII_LETTER = 65;
    private static final int LAST_UPPER_ASCII_LETTER = 90;
    private static final int DIFF_UPPER_ASCII_LETTER = LAST_UPPER_ASCII_LETTER - FIRST_UPPER_ASCII_LETTER;

    private static final int FIRST_LOWER_ASCII_LETTER = 97;
    private static final int LAST_LOWER_ASCII_LETTER = 122;
    private static final int DIFF_LOWER_ASCII_LETTER = LAST_LOWER_ASCII_LETTER - FIRST_LOWER_ASCII_LETTER;

    private static Random random = new Random();

    private List<Character> getCharacterPassword(int length, Random random) {
        List<Character> charPassword = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            int randNumber = random.nextInt(3) + 1;
            switch (randNumber) {
                case 1:
                    randNumber = random.nextInt(DIFF_ASCII_NUMBER) + FIRST_ASCII_NUMBER;
                    charPassword.add((char) randNumber);
                    break;
                case 2:
                    randNumber = random.nextInt(DIFF_UPPER_ASCII_LETTER) + FIRST_UPPER_ASCII_LETTER;
                    charPassword.add((char) randNumber);
                    break;
                case 3:
                    randNumber = random.nextInt(DIFF_LOWER_ASCII_LETTER) + FIRST_LOWER_ASCII_LETTER;
                    charPassword.add((char) randNumber);
                    break;
            }

        }
        return charPassword;
    }

    public String generatePassword() {

        int randomNumberForLength = random.nextInt(DIFF_PASSWORD_LENGTH + 1) + MIN_LENGTH_PASSWORD;

        List<Character> passwordSymbol = getCharacterPassword(randomNumberForLength, random);
        StringBuilder password = new StringBuilder();

        passwordSymbol.forEach(password::append);

        return password.toString();
    }

    public String encodePassword(String password){
        byte[] encodeBytes = Base64.getEncoder().encode(password.getBytes());
        return new String(encodeBytes);
    }

    public String decodePassword(String password){
        byte[] decodeBytes = Base64.getDecoder().decode(password.getBytes());
        return new String(decodeBytes);
    }
}
