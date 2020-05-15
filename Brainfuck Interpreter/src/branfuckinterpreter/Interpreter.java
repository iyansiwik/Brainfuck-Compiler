package branfuckinterpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Interpreter {
	
//	private static Console console = new Console();

	public static void interpret(char[] code) throws Exception {
		long startTime = System.currentTimeMillis();
		int index = 0;
		
		List<Cell> cells = new ArrayList<Cell>();
		cells.add(new Cell());
		int cell = 0;
		
		code = optimize(code);
		
		while(index < code.length) {
			switch(code[index]) {
			case '+':
				cells.get(cell).increment();
				break;
			case '-':
				cells.get(cell).decrement();
				break;
			case '>':
				cell++;
				if(cell == cells.size()) cells.add(new Cell());
				break;
			case '<':
				if(cell < 0) cells.add(0, new Cell());
				else cell--;
				break;
			case ',':
//				cells.get(cell).setValue(console.getChar());
				Scanner scanner = new Scanner(System.in);
				scanner.useDelimiter("");
				cells.get(cell).setValue(scanner.next().toCharArray()[0]);
				break;
			case '.':
//				console.print((char)cells.get(cell).getValue());
				System.out.print((char)cells.get(cell).getValue());
				break;
			case '[':
				if(cells.get(cell).getValue() == 0) {
					int open_bracs = 1;
					while(open_bracs > 0) {
						index++;
						if(index >= code.length) throw new Exception("Unmatched open bracket '['.");
						if(code[index] == '[') open_bracs++;
						if(code[index] == ']') open_bracs--;
					}
				}
				break;
			case ']':
				int close_bracs = 1;
				while(close_bracs > 0) {
					index --;
					if(index <= 0) throw new Exception("Unmatched closed bracket ']'.");
					if(code[index] == '[') close_bracs--;
					if(code[index] == ']') close_bracs++;
				}
				continue;
			}
			index++;
		}
//		console.print("\n\nFinished in "+(System.currentTimeMillis()-startTime)+"ms");
		System.out.println("\n\nFinished in "+(System.currentTimeMillis()-startTime)+"ms!");
	}
	
	private static char[] optimize(char[] chars) {
		List<Character> output = new ArrayList<Character>();
		
		for(char c : chars) {
			if(Arrays.asList('.', ',', '[', ']', '+', '-', '>', '<').contains(c))
				output.add(c);
		}
		for(int i=1;i<output.size();i++) {
			char c1 = output.get(i-1);
			char c2 = output.get(i);
			if((c1 == '+' && c2 == '-') || (c1 == '-' && c2 == '+') ||
			   (c1 == '>' && c2 == '<') || (c1 == '<' && c2 == '>')) {
				output.remove(i);
				output.remove(i-1);
				i-=2;
			}
		}
	
		char[] ret = new char[output.size()];
		for(int i=0;i<output.size();i++) {
			ret[i] = output.get(i);
		}
		return ret;
	}
	
	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("Please supply the path to a brainfuck file.");
			System.exit(0);
		}
		String text = "";
		try {
			File code = new File(args[0]);
			Scanner scanner = new Scanner(code);
			while(scanner.hasNextLine()) {
				text+=scanner.nextLine();
			}
			scanner.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			interpret(text);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void interpret(String code) throws Exception {
		interpret(code.toCharArray());
	}
	
	private static class Cell {
		private int value;
		
		public Cell(int value) {
			this.value = value;
		}
		
		public Cell() {
			this(0);
		}
		
		public void increment() {
			value++;
		}
		
		public void decrement() {
			value--;
		}
		
		public void setValue(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
}
