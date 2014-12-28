import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) {
		/*
		Crypto c = new Crypto();
		
		//RandomGenerator sg = new RandomGenerator();
		//String st = sg.getRandomSentence();
		String st = "abcd";
		System.out.println(c.vigenere(st));
		*/
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ProblemWindow ex = new ProblemWindow();
				ex.setVisible(true);
			}
		});
		
	}
}
