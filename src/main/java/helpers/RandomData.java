package helpers;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomData {
    public static String generateRandomString() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    public static String generateRandomString(int x) {
        return RandomStringUtils.randomAlphanumeric(x);
    }

    public static String getRandomEmail() {
        return generateRandomString(10) + "@" + generateRandomString(4) + ".com";
    }

    public static String getRandomPassportId() {
        return getRandomPassportId(2, 6);
    }

    public static String getRandomPassportId(int countOfChars, int countOfDigits) {
        return RandomStringUtils.random(countOfChars, "ABCDEFGHJKLMNOPQRSTUVWXYZ") + RandomStringUtils.randomNumeric(countOfDigits);
    }

    public static String getRandomPhoneNumber() {
        return getRandomPhoneNumber(9);
    }

    public static String getRandomPhoneNumber(int length) {
        return RandomStringUtils.randomNumeric(length);
    }
}
