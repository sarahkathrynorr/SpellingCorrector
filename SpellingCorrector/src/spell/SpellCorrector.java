package spell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;

public class SpellCorrector implements ISpellCorrector {
    private ArrayList<String> suggestions =  new ArrayList<>();
    private static char[] ALPHABET = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private Dictionary myDictionary;

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        //read in all the words from the dictionary as strings
        myDictionary = new Dictionary();

        File input = new File(dictionaryFileName);
        Scanner sc = new Scanner(input);

        //add in the strings using dictionary.add(string name);
        while (sc.hasNext()) {
            myDictionary.add(sc.next());
        }

        suggestions = new ArrayList<>();
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
         inputWord = inputWord.toLowerCase();

        suggestions = new ArrayList<>();


        if (myDictionary.find(inputWord) != null) {
             return inputWord;
         }
         else {
             getDeletionSuggestion(inputWord);
             getTranspositionSuggestion(inputWord);
             getInsertionSuggestion(inputWord);
             getAlterationSuggestion(inputWord);

             return lookForMatches();
         }
    }

    private void getDeletionSuggestion(String inputWord) {
        for (int i = 0; i < inputWord.length(); i++) {
            suggestions.add(inputWord.substring(0, i)+(inputWord.substring(i+1)));
        }
    }

    private void getInsertionSuggestion (String inputWord) {
        for (char c : ALPHABET) {
            suggestions.add(c + inputWord);
        }
        for (int i = 0; i < inputWord.length(); i++) {
            for (char c : ALPHABET) {
                suggestions.add(inputWord.substring(0, i + 1) + c + inputWord.substring(i + 1));
            }
        }
    }

    private void getTranspositionSuggestion (String inputWord) {
        for (int i = 0; i < inputWord.length() - 1; i++) {
            suggestions.add(inputWord.substring(0, i) + inputWord.charAt(i + 1) + inputWord.charAt(i) + inputWord.substring(i+2)); //mmmm I don't know... out of bounds?
        }
    }

    private void getAlterationSuggestion (String inputWord) {
        for (int i = 0; i < inputWord.length(); i++) {
            for (char c : ALPHABET) {
                suggestions.add(inputWord.substring(0, i) + c + inputWord.substring(i + 1));
            }
        }
    }

    private String lookForMatches () {

        ArrayList<String> options = new ArrayList<>();
        for (String suggestion : suggestions) {
            if (myDictionary.find(suggestion) != null) { //if null do nothing, otherwise ask for frequency count, find highest frequency (loop 1), loop through and remove anything but those with highest frequency (loop2)
                options.add(suggestion);
            }
        }

        if (options.size() == 1) {
            return options.get(0);
        }
        else if (options.size() != 0) {
            int highestFreq = 0;
            for (String option : options) {
                ITrie.INode currentNode = myDictionary.find(option);
                if (currentNode.getValue() > highestFreq) {
                    highestFreq = currentNode.getValue();
                }
            }

            ArrayList<String> newOptions = new ArrayList<>();
            for (int i = 0; i < options.size(); i++) {
                ITrie.INode currentNode = myDictionary.find(options.get(i));
                if (currentNode.getValue() == highestFreq) {
                    newOptions.add(options.get(i));
                }
            }

            //options alphabetize and grab the first one
            Collections.sort(newOptions);

            return newOptions.get(0);
        }
        else if (options.size() == 0) {
            /* rerun with 2 variations*/
            int suggestSize = suggestions.size();
            for (int i = 0; i < suggestSize; i++) {
                getDeletionSuggestion(suggestions.get(i));
                getAlterationSuggestion(suggestions.get(i));
                getInsertionSuggestion(suggestions.get(i));
                getTranspositionSuggestion(suggestions.get(i));
            }

            for (String suggestion : suggestions) {
                if (myDictionary.find(suggestion) != null) { //if null do nothing, otherwise ask for frequency count, find highest frequency (loop 1), loop through and remove anything but those with highest frequency (loop2)
                    options.add(suggestion);
                }
            }

            if (options.size() == 1) {
                return options.get(0);
            }
            else if (options.size() != 0) {
                int highestFreq = 0;
                for (String option : options) {
                    ITrie.INode currentNode = myDictionary.find(option);
                    if (currentNode.getValue() > highestFreq) {
                        highestFreq = currentNode.getValue();
                    }
                }
                ArrayList<String> newOptions = new ArrayList<>();
                for (String option : options) {
                    ITrie.INode currentNode = myDictionary.find(option);
                    if (currentNode.getValue() == highestFreq) {
                        newOptions.add(option);
                    }
                }
                //options alphabetize and grab the first one
                Collections.sort(newOptions);
                String wordToReturn = newOptions.get(0);
                newOptions.clear();
                options.clear();
                suggestions.clear();
                return wordToReturn;
            }
            else
            {
                return null;
            }
        }
        else {
            return null;
        }
    }
}
