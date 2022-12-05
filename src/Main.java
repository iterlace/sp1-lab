import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

import java.util.*;

public class Main {
    public static final ArrayList<Character> wordCharset = new ArrayList<Character>(Arrays.asList(
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
        'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '\''
    ));
    public static final ArrayList<Character> consonants = new ArrayList<Character>(Arrays.asList(
        'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z',
        'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'
    ));

    public static void main(String[] args) {
        try {
            File file = new File("input.txt");
            Scanner reader = new Scanner(file);
            LinkedList<String> allWords = new LinkedList<String>();
            while (reader.hasNextLine()) {
                String row = reader.nextLine();
                for (String word : extractWords(row)) {
                    if (!allWords.contains(word)) {
                        allWords.add(word);
                    }
                };
            }
            reader.close();

            LinkedList<String> validWords = filterWords(allWords);
            for (String word : validWords) {
                System.out.println(word);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static LinkedList<String> extractWords(String row) {
        LinkedList<String> words = new LinkedList<String>();
        ArrayList<Character> wordChars = new ArrayList<Character>();
        for (char ch : row.toCharArray()) {
            if (wordChars.size() >= 30) {
                break;
            }

            if (wordCharset.contains(ch)) {
                wordChars.add(ch);
            } else if (wordChars.isEmpty()) {
                continue;
            } else {
                // the word is finished
                words.add(
                    wordChars.stream()
                    .map(e -> e.toString())
                    .reduce((acc, e) -> acc  + e)
                    .get()
                );
                wordChars.clear();
            }
        }
        if (!wordChars.isEmpty()) {
            // the word is finished
            words.add(
                wordChars.stream()
                .map(e -> e.toString())
                .reduce((acc, e) -> acc  + e)
                .get()
            );
            wordChars.clear();
        }

        return words;
    }

    private static LinkedList<String> filterWords(LinkedList<String> words) {
        LinkedList<String> validWords = new LinkedList<String>();

        for (String word : words) {
            char previous = '0';
            boolean isValid = false;
            int len = 0;
            for (char c : word.toCharArray()) {
                if (consonants.contains(c) && consonants.contains(previous)) {
                    isValid = true;
                    break;
                }
                previous = c;
            }
            if (isValid) {
                validWords.add(word);
            }
        }
        return validWords;
    }
}