package util;

import java.util.Random;

public class CodeGenerator {
    private static final int MAX_LENGTH_CODE = 5;

    private static final int FIRST_ASCII_NUMBER = 48;
    private static final int LAST_ASCII_NUMBER = 57;
    private static final int DIFF_ASCII_NUMBER = LAST_ASCII_NUMBER - FIRST_ASCII_NUMBER;

    private static final int FIRST_UPPER_ASCII_LETTER = 65;
    private static final int LAST_UPPER_ASCII_LETTER = 90;
    private static final int DIFF_UPPER_ASCII_LETTER = LAST_UPPER_ASCII_LETTER - FIRST_UPPER_ASCII_LETTER;

    private static final int FIRST_LOWER_ASCII_LETTER = 97;
    private static final int LAST_LOWER_ASCII_LETTER = 122;
    private static final int DIFF_LOWER_ASCII_LETTER = LAST_LOWER_ASCII_LETTER - FIRST_LOWER_ASCII_LETTER;


    public static String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < MAX_LENGTH_CODE; i++) {
            int randNumber = random.nextInt(3) + 1;
            switch (randNumber) {
                case 1:
                    randNumber = random.nextInt(DIFF_ASCII_NUMBER) + FIRST_ASCII_NUMBER;
                    code.append((char) randNumber);
                    break;
                case 2:
                    randNumber = random.nextInt(DIFF_UPPER_ASCII_LETTER) + FIRST_UPPER_ASCII_LETTER;
                    code.append((char) randNumber);
                    break;
                case 3:
                    randNumber = random.nextInt(DIFF_LOWER_ASCII_LETTER) + FIRST_LOWER_ASCII_LETTER;
                    code.append((char) randNumber);
                    break;
            }

        }

        return code.toString();
    }

}
