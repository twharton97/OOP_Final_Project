import java.awt.Point;
import java.util.Random;

public class Shape {
	private static Random rand = new Random();
	private int shapeHeight;
	private int shapeLength;
	private char[][] shape;
	private char token;
	private int[] velocity;
	private Point shapePoint;
	ShapeDesign sd;
	int shapeLetterSize;

	public Shape(int boardH, int boardL, ShapeDesign design) {
		shapeLetterSize = rand.nextInt(15)+15;
		token = (char) (rand.nextInt(90 - 65) + 65);// A,B,C,....,Z
		velocity = new int[2];
		shapePoint = new Point();
		sd = design;
		shapePoint.setLocation(rand.nextInt(boardL - 5 + 1), rand.nextInt(boardH - 5 + 1));// RnumbersEnsureEveryObjectStartsOnTheBoard
		ShapeDesign.shapeSizer(design, Shape.this);
		shape = new char[shapeHeight][shapeLength];
		for (int row = 0; row < shapeHeight; row++){
			for (int col = 0; col < shapeLength; col++){
				shape[row][col] = ShapeDesign.formatShape(design, token, shapeHeight, shapeLength)[row][col];
				
			}
		}
	}

	public String toString(Shape givenUnit) {
		String result = "";
		for (int i = 0; i < givenUnit.shapeHeight; i++) {
			for (int k = 0; k < givenUnit.shapeLength; k++) {
				result += shape[i][k];
			}
			result += "\n";
		}
		return result;
	}

	public String getToken() {
		String tokenDupe = "";
		tokenDupe += token;
		return tokenDupe;
	}

	public int getShapeHeight() {
		return shapeHeight;
	}
	
	public int getShapeLength() {
		return shapeLength;
	}
	
	public void setShapeHeight(int shapeHeight) {
		this.shapeHeight = shapeHeight;
	}
	
	public void setShapeLength(int shapeLength) {
		this.shapeLength = shapeLength;
	}

	public void setVelocity(int[] travelDirection) {
		for (int i = 0; i < travelDirection.length; i++) {
			this.velocity[i] = travelDirection[i];			
		}
	}

	public int[] getVelocity() {
		return velocity;
	}

	public Point getPoint() {

		return (Point) shapePoint.clone();
	}

	public void setShapePoint(Point shapePoint2) {
		shapePoint = (Point) shapePoint2.clone();

	}
	
	public char[][] getShapeArray(){
		return shape;
	}
	
	public ShapeDesign getShapeDesign(){
		return sd;
	}
	
	public int getShapeLetterSize(){
		return shapeLetterSize;
	}
}
