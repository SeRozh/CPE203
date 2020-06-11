import processing.core.*;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class drawPoints extends PApplet {

	public void settings() {
		size(500, 500);
	}

	public void setup() {
		background(180);
		noLoop();
	}

	public static void main(String[] args) {
		PApplet.main("drawPoints");
		List<Point> points = new ArrayList<>();
		readFile(points);

		List<Point> zChange = points.stream().filter(p -> p.getZ() <= 2.0)
				.collect(Collectors.toList());

		List<Point> scale = zChange.stream().map(p -> new Point(p.getX() * 0.5,
				p.getY() * 0.5, p.getZ() * 0.5)).collect(Collectors.toList());

		List<Point> translate = scale.stream().map(p -> new Point(p.getX() - 157,
				p.getY() - 37, p.getZ() + 0)).collect(Collectors.toList());

		writeFile(translate);
	}

	public static void readFile(List<Point> lst) {
		try {
			File input = new File
					("C:\\Users\\shad1\\Documents\\Cal Poly\\Computer Science\\CPE203\\Lab 7\\lab8helper\\positions.txt");
			Scanner sc = new Scanner(input);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] split = line.split(",");
				lst.add(new Point(Double.parseDouble(split[0]),
						Double.parseDouble(split[1]), Double.parseDouble(split[2])));
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void writeFile(List<Point> lst) {
		try {
			PrintStream output = new PrintStream
					("drawMe.txt");
			for (Point p : lst) {
				output.println(p.getX() + ", " + p.getY() + ", " + p.getZ());
			}
		}
		catch (Exception e) {
			System.out.println("Error");
		}
	}

	public void draw() {
		double x, y;
		String[] lines = loadStrings("drawMe.txt");
		println("there are " + lines.length);
		for (String line : lines) {
			if (line.length() > 0) {
				String[] words = line.split(",");
				x = Double.parseDouble(words[0]);
				y = Double.parseDouble(words[1]);
				println("xy: " + x + " " + y);
				ellipse((int) x, (int) y, 1, 1);
			}
		}
	}
}