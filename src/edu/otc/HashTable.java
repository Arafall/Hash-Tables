package edu.otc;

/**
 * Class to create a mutable hash table that stores key value pairs.
 * Allows for resizing of the hash array upon inserting a pair when it's full.
 * @author Daniel Wade
 */
public class HashTable {
    public String[][] hashArray;

    /**
     * Instantiates the hash table with the given size.
     * @param tableSize Length to instantiate the hash table array to.
     */
    public HashTable(int tableSize) {
        hashArray = new String[tableSize][2];
    }

    /**
     * Counts the ascii unicode integer for every character in the key provided and
     * returns the hash number from the string's characters.
     * @param keyToHash Key used in a key value pair specified to be hashed for finding its original index in the hash table.
     * @return The hashed key used for accessing the default index the pair will have.
     */
    public int hash(String keyToHash) {
        int hash = 0;
        for (char asciiChar : keyToHash.toCharArray()) {
            hash += asciiChar;
        }
        return hash;
    }

    /**
     * Checks if the table position for the default index is taken and returns the result.
     * @param keyCollisionToCheck Key used to test if there's a collision at its index.
     * @return Whether the position for the pair is open/null or not.
     */
    public boolean keyCollides(String keyCollisionToCheck) {
        // Get default hash index.
        int hashIndex = hash(keyCollisionToCheck) % hashArray.length;
        return hashArray[hashIndex][0] != null;
    }

    /**
     * Re-defines hash Array to the new incremented size and loops through the given array.
     * The loop inserts all hash pairs from the array param since the length changed.
     * @param increment Amount to increase the size of the hast table array.
     * @param arrCopy Acts as a copy of the hash array before resizing.
     */
    public void resizeHashTable(int increment, String[][] arrCopy) {
        hashArray = new String[arrCopy.length + increment][2];
        // Re-inserts each hash pair from old sized array into new larger array.
        // No need to set hashArray to values since insertion method does that.
        for (String[] keyValPair : arrCopy) {
            // Insert pair when key exists.
            if (keyValPair[0] != null)
                insertion(keyValPair[1], keyValPair[0]);
        }
    }

    /**
     * Attempts to insert a key value pair into the hash table based on a hash index using the key.
     * If table is full then it will be resized to fit the new pair.
     * @param value Value in which a key value pair points to using a unique key.
     * @param key Key in which a key value pair uses to reference the value.
     */
    public void insertion(String value, String key) {
        // Original hash index retrieved by calling hash().
        int hashIndex = hash(key) % hashArray.length;
        // When the key has a collision set index to be located,
        // otherwise set it to the original hash index.
        int insertionIndex = (keyCollides(key)) ? -1 : hashIndex;

        // During collision search future indexes for an open position.
        if (keyCollides(key)) {
            for (int i = hashIndex; i < hashArray.length; i++) {
                // Open index found, quit loop.
                if (hashArray[i][0] == null) {
                    insertionIndex = i;
                    break;
                }
            }
        }

        // Open index found to insert key value pair.
        if (insertionIndex != -1) {
            hashArray[insertionIndex][0] = key;
            hashArray[insertionIndex][1] = value;
        }
        // No open index found, need to increase array size.
        else {
            // Original hash index is less than length.
            if (hashIndex >= hashArray.length)
                resizeHashTable(hashIndex + 1 - hashArray.length, hashArray);
            // Original index collided and had to search for one, empty space not found.
            else
                resizeHashTable(1, hashArray);
            // Re-insert the pair to the table after it failed to do so.
            insertion(value, key);
        }
    }

    /**
     *
     * @param keyToFind Key to reference the value it's paired with in the hash table.
     * @return Sends the pair value if the key was found, otherwise null is given,
     */
    public String lookup(String keyToFind) {
        // Default key index in hash table.
        int hashIndex = hash(keyToFind) % hashArray.length;
        // Searches through table beginning at starting position to find the key.
        for (int i = hashIndex; i < hashArray.length; i++) {
            // Key found exits loop, method, and returns the value.
            if (hashArray[i][0].equals(keyToFind))
                return hashArray[i][1];
        }

        return null;
    }

    /**
     * Writes each key pair value in the hash table to the user by looping through the array.
     * @return Sends the String used to display the hash table in the console for other uses.
     */
    public String display() {
        StringBuilder tableContents = new StringBuilder();
        // Loop through hash array and add each piece of data about each pair (Pair number, key, and value).
        for (int i = 0; i < hashArray.length; i++) {
            var hash = hashArray[i];
            tableContents.append("Hash #").append(i + 1).append(System.lineSeparator());
            tableContents.append("Key:   ").append(hash[0]).append("  ");
            tableContents.append("Value: ").append(hash[1]).append(System.lineSeparator());
        }
        System.out.println(tableContents);
        return tableContents.toString();
    }

}
