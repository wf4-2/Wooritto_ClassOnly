package util;

import java.security.SecureRandom;

public class RoomCodeGenerator {
    private static final SecureRandom random = new SecureRandom();
    private static final String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";

    public static String generateRoomCode() {
        StringBuilder code = new StringBuilder();
        int alphaCount = 0, numberCount = 0;

        while (code.length() < 6) {
            boolean addAlpha = (alphaCount < 2) || (numberCount >= 4);
            boolean addNumber = (numberCount < 2) || (alphaCount >= 4);

            if (addAlpha && (!addNumber || random.nextBoolean())) {
                code.append(ALPHABETS.charAt(random.nextInt(ALPHABETS.length())));
                alphaCount++;
            } else {
                code.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
                numberCount++;
            }
        }
        return code.toString();
    }
}
