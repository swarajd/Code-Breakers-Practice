public class Tester {
	public static void main(String[] args) {
		Crypto crypto = new Crypto();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String ciphered = crypto.monoalpha(alphabet);
		for (int i = 0; i < alphabet.length(); i++) {
			String curAlpha = alphabet.charAt(i) + "";
			String curCiphr = ciphered.charAt(i) + "";
			//System.out.println("al: " + curAlpha + " ci: " + curCiphr);
		}
		System.out.println(alphabet);
		System.out.println(ciphered);
	}
}
