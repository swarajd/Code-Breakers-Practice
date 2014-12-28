import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class Crypto {
	private RandomGenerator rg = new RandomGenerator();
	private Random rng = new Random();
	private Map<Character, Character> solutionSet;
	private String[] ciphers;

	Crypto() {
		solutionSet = new HashMap<Character, Character>();
	}

	// division B
	String monoalpha(String plaintext) {
		plaintext = plaintext.toUpperCase();
		Map<Character, Character> letters = new HashMap<Character, Character>();
		char newLetter;
		for (char ch = 'A'; ch != '['; ch++) {
			newLetter = Character
					.toChars(65 + (int) (Math.random() * ((90 - 65) + 1)))[0];
			while (letters.containsValue(newLetter) || newLetter == ch) {
				newLetter = Character
						.toChars(65 + (int) (Math.random() * ((90 - 65) + 1)))[0];
			}
			if (!letters.containsValue(newLetter) && newLetter != ch) {
				letters.put(ch, newLetter);
			}
		}
		
		solutionSet = letters;
		String ret = "";
		for (int i = 0; i < plaintext.length(); i++) {
			char curChar = plaintext.charAt(i);
			if (letters.containsKey(curChar)) {
				ret += letters.get(curChar);
			} else {
				ret += curChar;
			}
		}
		return ret;
	}

	String caesarian(String plaintext) {
		plaintext = plaintext.toUpperCase();
		Map<Character, Character> letters = new HashMap<Character, Character>();
		int shift = 1 + (int) (Math.random() * ((25 - 1) + 1));
		for (char ch = 'A'; ch != '['; ch++) {
			char curChar = ch;
			for (int k = 0; k < shift; k++) {
				curChar++;
			}
			if (curChar >= '[') {
				curChar -= 26;
			}
			letters.put(ch, curChar);
		}
		solutionSet = letters;
		String ret = "";
		for (int i = 0; i < plaintext.length(); i++) {
			char curChar = plaintext.charAt(i);
			if (letters.containsKey(curChar)) {
				ret += letters.get(curChar);
			} else {
				ret += curChar;
			}
		}
		return ret;
	}

	String atbash(String plaintext) {
		plaintext = plaintext.toUpperCase();
		Map<Character, Character> letters = new HashMap<Character, Character>();
		for (char ch = 'A'; ch != '['; ch++) {
			letters.put(ch, (char) (('Z' - ch) + 65));
		}
		solutionSet = letters;
		String ret = "";
		for (int i = 0; i < plaintext.length(); i++) {
			char curChar = plaintext.charAt(i);
			if (letters.containsKey(curChar)) {
				ret += letters.get(curChar);
			} else {
				ret += curChar;
			}
		}
		return ret;
	}

	// division C
	String affine(String plaintext) {
		plaintext = plaintext.toUpperCase();
		ArrayList<Integer> avals = new ArrayList<Integer>();
		Map<Character, Character> letters = new HashMap<Character, Character>();
		for (int i = 0; i < 1000; i++) {
			if (gcd(i, 26) == 1) {
				avals.add(i);
			}
		}
		int a = avals.get((int) (Math.random() * ((avals.size()) + 1)));
		int b = (int) (Math.random() * ((avals.size()) + 1));

		for (char ch = 'A'; ch != '['; ch++) {
			letters.put(ch, (char) ('A' + (a * (ch - 'A') + b) % 26));
		}
		solutionSet = letters;
		String ret = "";
		for (int i = 0; i < plaintext.length(); i++) {
			char curChar = plaintext.charAt(i);
			if (letters.containsKey(curChar)) {
				ret += letters.get(curChar);
			} else {
				ret += curChar;
			}
		}
		return ret;
	}

	int gcd(int a, int b) {
		int min = a > b ? b : a, max = a + b - min, div = min;
		for (int i = 1; i < min; div = min / ++i)
			if (max % div == 0)
				return div;
		return 1;
	}
	String vigenere(String plaintext) {
		plaintext = plaintext.toUpperCase();
		String key = rg.getRandomWord();
		int keySize = key.length();
		Map<Character, Character> letters = new HashMap<Character, Character>();
		for (int i = 0; i < key.length(); i++) {
			letters.put((char)('0'+i), key.charAt(i));
		}
		solutionSet = letters;
		String ret = "";
		for (int i = 0; i < plaintext.length(); i++) {
			char rawChar = plaintext.charAt(i);
			if (rawChar >= 'A' && rawChar <= 'Z') {
				char curChar = (char)(plaintext.charAt(i) - 'A');
				char keyChar = (char)((key.charAt((i % keySize)) - 'A'));
				ret += (char)(( 'A' + (curChar + keyChar) % 25) + 1);
			} else {
				ret += rawChar;
			}
		}
		return ret;
	}

	String randomEncrypt(String plaintext, ArrayList<String> ciphers) {
		boolean spaces = rng.nextBoolean();
		if (spaces) {
			plaintext = cutSpaces(plaintext);
		}
		int cipherSize = ciphers.size() - 1;
		if (cipherSize == -1) {
			return plaintext;
		}
		String selection = ciphers.get((int)(Math.random() * (cipherSize)));
		//System.out.println(selection);
		switch (selection) {
		case "monoalpha":
			return monoalpha(plaintext);
		case "caesarian":
			return caesarian(plaintext);
		case "atbash":
			return atbash(plaintext);
		case "affine":
			return affine(plaintext);
		case "vigenere":
			return vigenere(plaintext);
		default:
			return plaintext;
		}
	}

	String getSolutionSet() {
		String ret = "";

		SortedSet<Character> sortedset = new TreeSet<Character>(
				solutionSet.keySet());
		Iterator<Character> keys = sortedset.iterator();

		while (keys.hasNext()) {
			char curChar = keys.next();
			ret += curChar;
		}
		ret += '\n';
		Iterator<Character> vals = sortedset.iterator();
		while (vals.hasNext()) {
			char curChar = solutionSet.get(vals.next());
			ret += curChar;
		}
		return ret;
	}
	
	String cutSpaces(String plaintext) {
		plaintext = plaintext.replaceAll("[^a-zA-Z0-9]", "");
		ArrayList<String> parts = new ArrayList<String>();
		int len = plaintext.length();
		for (int i = 0; i < len; i += 50) {
			parts.add(plaintext.substring(i, Math.min(len, i + 50)));
		}
		plaintext = join(parts, " ");
		return plaintext;
	}
	 static String join(Collection<?> s, String delimiter) {
	     StringBuilder builder = new StringBuilder();
	     Iterator<?> iter = s.iterator();
	     while (iter.hasNext()) {
	         builder.append(iter.next());
	         if (!iter.hasNext()) {
	           break;                  
	         }
	         builder.append(delimiter);
	     }
	     return builder.toString();
	 }
}
