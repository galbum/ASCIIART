package ascii_art;

import java.util.HashMap;
import java.util.HashSet;

public class Algorithms {

    private static int ONE = 1;
    public Algorithms() {
        //empty constructor
    } // end of constructor

    /**
     * Finds the duplicate number between 1-n
     *
     * @param numList - List of numbers between 1-n
     * @return The single duplicate number
     */
    public static int findDuplicate(int[] numList) {
        // find the loop
        int slowRunner = numList[0];
        int fastRunner = slowRunner;
        while (true){
            slowRunner = numList[slowRunner];
            fastRunner = numList[numList[fastRunner]];
            if(slowRunner == fastRunner)
                break;
        } // end of while
        // find the duplicate number
        int pointerOne = numList[0];
        int pointerTwo = slowRunner;
        while (pointerOne != pointerTwo){
            pointerOne = numList[pointerOne];
            pointerTwo = numList[pointerTwo];
        } // end of while
        return pointerOne;
    } // end of findDuplicate

    /**
     * Finds out how many unique morse representations are there in the array of strings.
     *
     * @param words Array of words
     * @return Amount of unique morse representations in the array
     */
    public static int uniqueMorseRepresentations(String[] words) {
        HashSet<String> answerSet = new HashSet<String>();
        String[] morse = new String[]{".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.",
                "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
        for (String word : words) {
            StringBuilder morseCodeWord = new StringBuilder(); // StringBuilder is a mutable sequence of characters
            for (char character : word.toCharArray())
                morseCodeWord.append(morse[character - 'a']); // appends the morse character to
            answerSet.add(morseCodeWord.toString()); // adds the word to the set
        } // end of outer for
        return answerSet.size(); // the size of the set
    } // end of uniqueMorseRepresentations method

} // end of Algorithms class


