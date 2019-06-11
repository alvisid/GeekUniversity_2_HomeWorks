package Lesson_3;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;

public class DZ_3_2 {
    static class PhoneBox{
        HashMap<String, HashSet<Integer>> hashMap;

        public PhoneBox(){
            this.hashMap = new HashMap<>();
        }
        public void add(String name, int phone){
            HashSet<Integer> hashSet = hashMap.getOrDefault(name, new HashSet<>());
            hashSet.add(phone);
            hashMap.put(name, hashSet);
        }
        public void findString(String name){
            if (hashMap.containsKey(name)){
                System.out.println(hashMap.get(name));
            } else {
                System.out.println("Такого сотрудника нет в базе");
            }
        }
        public static void main(String[] args) throws FileNotFoundException{
            PhoneBox book = new PhoneBox();
            book.add("Smirnov", 79123456);
            book.add("Ivanov", 79123451);
            book.add("Sidorov", 79123452);
            book.add("Grigorenko", 79123453);
            book.add("Miladze", 791234564);
            book.add("Skibo", 791234565);
            book.add("Skupko", 791234567);
            book.add("Borisenko", 791234568);
            book.add("Nikolenko", 791234566);
            book.add("Borschuk", 791234569);

            book.findString("Smirnov");
            book.findString("Sidorov");
            book.findString("Pupkin");
        }
    }
}
