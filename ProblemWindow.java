import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.FlowLayout;
//quit button
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class ProblemWindow extends JFrame {

	private JMenuBar menubar;
	private JMenu cipherSwitch;
	private ArrayList<String> ciphers;
	private Map<String,JCheckBoxMenuItem> cipherBoxes;
	
	private Crypto crypto;
	private RandomGenerator sg;
	private String curPlainText;
	private String curCipherText;
	private String solutionSet;
	
	private JTextPane plainPane;
	private JTextPane cipherPane;
	private JTextPane solPane; 

	private ActionListener quitListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	};

	private void initMenu() {
		menubar = new JMenuBar();
		cipherSwitch = new JMenu("switch");
		
		cipherBoxes = new HashMap<String,JCheckBoxMenuItem>();
		
		JCheckBoxMenuItem monoalpha = new JCheckBoxMenuItem("monoalpha");
		monoalpha.setState(true);
		cipherBoxes.put("monoalpha", monoalpha);
		
		JCheckBoxMenuItem caesarian = new JCheckBoxMenuItem("caesarian");
		caesarian.setState(true);
		cipherBoxes.put("caesarian", caesarian);
		
		
		JCheckBoxMenuItem atbash = new JCheckBoxMenuItem("atbash");
		atbash.setState(true);
		cipherBoxes.put("atbash", atbash);
		
		JCheckBoxMenuItem affine = new JCheckBoxMenuItem("affine");
		affine.setState(true);
		cipherBoxes.put("affine", affine);
		
		JCheckBoxMenuItem vigenere = new JCheckBoxMenuItem("vigenere");
		vigenere.setState(true);
		cipherBoxes.put("vigenere", vigenere);
		
		cipherSwitch.add(monoalpha);
		cipherSwitch.add(caesarian);
		cipherSwitch.add(atbash);
		cipherSwitch.add(affine);
		cipherSwitch.add(vigenere);
		
		menubar.add(cipherSwitch);
		
		ciphers = new ArrayList<String>();
		SortedSet<String> sortedset = new TreeSet<String>(
				cipherBoxes.keySet());
		Iterator<String> keys = sortedset.iterator();
		while (keys.hasNext()) {
			String curCipher = keys.next();
			if (cipherBoxes.get(curCipher).getState()) {
				ciphers.add(curCipher);
			}
		}
	}
	
	private void initCrypto() {
		crypto = new Crypto();
		sg = new RandomGenerator();
		curPlainText = sg.getRandomSentence();
		curCipherText = crypto.randomEncrypt(curPlainText, ciphers);
		solutionSet = crypto.getSolutionSet();
	}

	private void initUI() {
		// initialize panel
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		add(panel);
		
		panel.add(menubar);
		setJMenuBar(menubar);

		cipherPane = new JTextPane();
		cipherPane.setContentType("text/html");
		cipherPane.setText(curCipherText);
		// cipherPane.setText("<p>cipher test</p>");
		cipherPane.setEditable(false);
		panel.add(cipherPane);

		plainPane = new JTextPane();
		plainPane.setContentType("text/html");
		plainPane.setText(curPlainText);
		// plainPane.setText("<p>plain test</p>");
		plainPane.setEditable(false);
		plainPane.setVisible(false);
		panel.add(plainPane);
		
		solPane = new JTextPane();
		solPane.setContentType("text/html");
		solPane.setText(solutionSet);
		solPane.setEditable(false);
		solPane.setVisible(false);
		panel.add(solPane);

		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton update = new JButton("update");
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				updateCrypto();
			}
		});
		update.setMnemonic(KeyEvent.VK_U);
		
		JButton solution = new JButton("Solution");
		solution.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				plainPane.setVisible(true);
			}
		});
		solution.setMnemonic(KeyEvent.VK_N);
		
		JButton solutionSet = new JButton("Solution Set");
		solutionSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				solPane.setVisible(true);
			}
		});
		solutionSet.setMnemonic(KeyEvent.VK_S);
		
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		close.setMnemonic(KeyEvent.VK_C);

		bottom.add(update);
		bottom.add(solutionSet);
		bottom.add(solution);
		bottom.add(close);
		panel.add(bottom);

		bottom.setMaximumSize(new Dimension(600, 0));

		pack();

		// configure window
		setTitle("Practice problem");
		setSize(600, 450);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void updateCrypto() {
		ciphers = new ArrayList<String>();
		SortedSet<String> sortedset = new TreeSet<String>(
				cipherBoxes.keySet());
		Iterator<String> keys = sortedset.iterator();
		while (keys.hasNext()) {
			String curCipher = keys.next();
			if (cipherBoxes.get(curCipher).getState()) {
				ciphers.add(curCipher);
			}
		}
		crypto = new Crypto();
		sg = new RandomGenerator();
		curPlainText = sg.getRandomSentence();
		curCipherText = crypto.randomEncrypt(curPlainText, ciphers);
		solutionSet = crypto.getSolutionSet();
		
		plainPane.setText(curPlainText);
		cipherPane.setText(curCipherText);
		solPane.setText(solutionSet);
		
		cipherPane.setVisible(true);
		plainPane.setVisible(false);
		solPane.setVisible(false);
		
	}

	public ProblemWindow() {
		initMenu();
		initCrypto();
		initUI();
	}
}
