package edu.otc;

/**
 * Program to create a hash table class to contain a mutable array of key value pairs,
 * and manipulate how the table is structured, changing its size, inserting new pairs,
 * and collision handling of indexes.
 * @author Daniel Wade
 */
public class Main {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable(1);
        String[] keys = {
            "AaAaAaA",
            "LongTestCaseToGetHighHashCode",
            "Qwerty101",
            "user12345"
        };
        String[] values = new String[keys.length];
        // Loop for each key to add its corresponding value.
        for (int i = 0; i < keys.length; i++) {
            // Define the key's value as its intended hash index.
            int keyHash = hashTable.hash(keys[i]); //% hashTable.hashArray.length;
            values[i] = Integer.toString(keyHash);
            // Insert key value pair into hash table array.
            hashTable.insertion(values[i], keys[i]);
        }
        System.out.println("Original Hash Table");
        hashTable.display();

        // Insert duplicate value into index 4 after colliding with original hash index.
        System.out.println("Collision Check");
        hashTable.insertion(values[1], keys[1]);
        hashTable.display();

        // Insert items into a full hash table, so it'll resize repeatedly.
        System.out.println(System.lineSeparator() + "Table Resize Check");
        hashTable.insertion("Bananas", "favorite_fruit");
        hashTable.insertion("Apples", "worst_fruit");
        hashTable.insertion("Peaches", "average_fruit");
        hashTable.display();

        // Retrieve several values utilizing their keys.
        System.out.println("user12345: " + hashTable.lookup("user12345"));
        System.out.println("favorite_fruit: " + hashTable.lookup("favorite_fruit"));
        System.out.println("average_fruit: " + hashTable.lookup("average_fruit"));
        System.out.println("LongTestCaseToGetHighHashCode " + hashTable.lookup("LongTestCaseToGetHighHashCode"));
    }
}