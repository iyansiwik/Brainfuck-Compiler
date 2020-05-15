package branfuckinterpreter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class Console2 implements KeyListener {

	private int pressed = -1;
	
	private JFrame frame;

	private JTextArea textArea;
	
	public Console2() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setSize(720, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.addKeyListener(this);
		textArea.setFont(new Font("Consolas", Font.PLAIN, 18));
		textArea.setForeground(Color.WHITE);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setBackground(Color.BLACK);
		scrollPane.setViewportView(textArea);
		frame.setVisible(true);
		
		textArea.requestFocus();
	}
	
	
	public void print(String string) {
		textArea.setText(textArea.getText()+string);
	}
	
	public void print(char character) {
		print(""+character);
	}
	
	public void println(String string) {
		print(string+"\n");
	}

	public void println(char character) {
		println(""+character);
	}
	
	public char getChar() {
		textArea.setText(textArea.getText()+"\n\n> ");
		char c;
		while(pressed == -1) {
			System.out.println(pressed);
		}
		c = (char)pressed;
		pressed = -1;
		textArea.setText(textArea.getText()+c+"\n\n");
		return c;
	}

	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		System.out.println("DONW");
		pressed = e.getKeyChar();
	}
	public void keyReleased(KeyEvent e) {
		pressed = -1;
	}
}
