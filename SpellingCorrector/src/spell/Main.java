package spell;

import java.io.IOException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */

public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws IOException {
		
		String dictionaryFileName = args[0];
		String inputWord = args[1];
		
		/**
		 * Create an instance of your corrector here
		 */
		ISpellCorrector corrector = new SpellCorrector();// = null;
		
		corrector.useDictionary(dictionaryFileName);
		String suggestion = corrector.suggestSimilarWord(inputWord);
		if (suggestion == null) {
		    suggestion = "No similar word found";
		}
		
		System.out.println("Suggestion is: " + suggestion);
	}

}

/*
Help session:
	HashCode: ITrie -> bulk of project,
		classes: toString classes (see Dr. R's class files because he reveals all his secrets)
		recursion for ToString

		Hashcode: returns an int, to give an ID to whatever class your implementing
		Inside trie you have a word count, node count (# of letters being used)
		word count * node count * prime number (i.e. 7)

		Don't actually use at all for anything. Just to look pretty and show you can make one


	equals function (not actually useful in the project)
	params: Object o (could be anything - parent of any object),
	Object o.getClass() will return Object
	so Trie.getClass() should return a Trie class
		1. check if o is null
		2. if (o.getClass() != Trie.getClass()) => true?
			2.5. then downcast tot a Trie (Trie) o;

	Node class -> array of 26 characters symbolizing the 26 characters of the alphabet
		-> Node[] alphabet = new Node[26];
		-> use positions (index) to get actual letter - to do this add or subtract letters,
				-> if you're at position 0 and you want 'a' then you do:
				 	-> int 0+'a' = asqii value
				 -> Node-ception: nodes point to each other
				 -> frequency (>0) is found at the last element (how many times that word is in dictionary)
				 -> iterate through the nodes that point to nodes to find a frequency > 0 (a.k.a. a full word)
				 -> each NODE has an ARRAY of 29 NODES (letters) (each space in array will be NULL until you initialize space to letter)

		-> different edits
		-> use string builder
		-> add every letter at every point and see if it exists in the dictionary
		"cat" test acat, caat, cata, bcat, cbat.........catz. (use a set to store all these)
			-> use find function in trie
			-> deletion is the same but where you remove a letter and check in dictionary
			-> for replace just go through all the different words and replace each letter with 26 letters and see if they are there
			-> for swap just swap all the letters
				-> "cat" test "cat, act, cta"
		-> compare frequencies if there are multiple words, and then alphabet

		-> then check for the secondary level (check up-to 2 edit distances)
			-> insertion would have "Cat" test "aacat, acaat, caaat, caata, cataa, abcat, acbat, cabat, cabta, ...bacat....bbcat....catzz"
			-> if you still can't find any words after a distance of 2 return null

		ITrie.INode for interfaces

		and another class, Spell Corrector class where you'll actually do the suggestion
 */
