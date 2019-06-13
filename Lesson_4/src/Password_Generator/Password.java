package Lesson_4;

import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.util.Random;

public class Password {

    private static final String PUNCTUATION = "!@#$%&*()_+-=[]/?><";

    public static void main(String[] args) {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Random random = new Random();

        // digits
        for (int i = 0; i < 3; i++) {
            byteArrayOutputStream.write(48 + random.nextInt(10));
        }

        // lower letters
        for (int i = 0; i < 3; i++) {
            byteArrayOutputStream.write(65 + random.nextInt(26));
        }

        // capital letters
        for (int i = 0; i < 2; i++) {
            byteArrayOutputStream.write(97 + random.nextInt(26));
        }

        for (int i = 0; i < 1; i++) {
            byteArrayOutputStream.write(PUNCTUATION.toUpperCase().charAt((int) (Math.random()*22)));
        }

        return byteArrayOutputStream;
    }
}
