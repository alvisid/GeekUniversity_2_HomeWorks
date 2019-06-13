package Lesson_3;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class DZ_3_1 {
    public static void main(String[] args) throws FileNotFoundException {
        String[] arr = {"Slovo", "Delo", "Mir", "Ritm", "Virus", "Slovo",
                "Nebo", "Voda", "Voda", "Nebo", "Voda",
                "Voda", "Mir", "Ritm", "Mir", "Ritm",};
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (String o : arr) {
            hashMap.put(o, hashMap.getOrDefault(o, 0) + 1);
        }
        System.out.println(hashMap);
    }
}
