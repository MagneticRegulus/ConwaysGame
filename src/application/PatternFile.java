package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import javafx.geometry.Point2D;

public class PatternFile {
	
	private String name;
	private String link;
	private File file;
	private int rows;
	private int cols;
	private int width;
	private int height;
	private Set<Point2D> points;
	
	public PatternFile(File file, int rows, int cols) {
		this.file = file;
		this.rows = rows;
		this.cols = cols;
	}
	
	public void loadFile() {
		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				
				if (line.startsWith("#N")) {
					String[] nameLine = line.split(" ");
					this.name = nameLine[1];
				} else if (line.startsWith("#C") && line.contains("www.conwaylife.com")) {
					String[] linkLine = line.split(" ");
					this.link = linkLine[1];
				} else if (line.startsWith("x")) {
					String[] dimensions = line.split(", ");
					this.width = getNum(dimensions[0]);
					this.height = getNum(dimensions[1]);
					String cellInfo = scan.nextLine();
					setPoints(cellInfo);
				}
			}
			
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}	
	
	public int getNum(String str) {
		char[] chars = str.toCharArray();
		String num = "";
		for (int i = 0; i < chars.length; i++) {
			if (Character.isDigit(chars[i])) {
				num = num + chars[i];
			}
		}
		
		return Integer.parseInt(num);
	}
		
	public void setPoints(String cellInfo) {
		char[] chars = cellInfo.toCharArray();
		int startY = (cols - width) / 2;
		int startX = (rows - height) / 2;
		int chIdx = 0;
		Stack<Character> stack = new Stack<>();
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				//
			}
		}
	}
	
	public void loadGame(Game game) {
		
	}

	public String getName() {
		return name;
	}

	public String getLink() {
		return link;
	}

}
