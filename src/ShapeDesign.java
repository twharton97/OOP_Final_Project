import java.util.Random;

public enum ShapeDesign {
	SQUARE, DIAMOND, TRIANGLE;
	private static Random rand = new Random();

	public static ShapeDesign getRandomDesign() {
		switch (rand.nextInt(2)) {
		case (0):
			return SQUARE;
		case (1):
			return DIAMOND;
		case (2):
			return TRIANGLE;
		default:
			return null;
		}
	}

	public static char[][] formatShape(ShapeDesign design, char token, int shapeHeight, int shapeLength) {
		char[][] shape = new char[shapeHeight][shapeLength];
		switch (design) {
		case SQUARE:
			for (int row = 0; row < shapeHeight; row++) {
				for (int col = 0; col < shapeLength; col++) {
					shape[row][col] = token;
				}
			}
			return shape;
		case DIAMOND:
			for (int row = 1; row <= shapeHeight/2 + 1; row++){
				for ( int col = 1; col < shapeLength + 1; col++){
					if((col <= (shapeLength - shapeLength/2 -row)) ||( col > (shapeLength/2+row))){
						shape[row-1][col-1] = '-';
					}else{
						shape[row-1][col-1] = token;
					}
				}
			}
			
			for (int row = shapeHeight; row > shapeHeight/2 + 1; row--){
				for ( int col = 1; col < shapeLength + 1; col++){
				
					if((col < (row*2 - (shapeLength + shapeLength/2)) + (shapeLength - row)) || (col > (shapeLength - row + (shapeLength/2 + 1)))){
						shape[row-1][col-1] = '-';
					}else{
						shape[row-1][col-1] = token;
					}
				}
			}
				
			return shape;
			case TRIANGLE:
				return null;
			default:
				return null;

	}

	}

	public static void shapeSizer(ShapeDesign design, Shape shape) {
		int shapeHeight = 0;
		int shapeLength = 0;
		switch (design) {
		case DIAMOND:
			int size = rand.nextInt(5)+3;
			if (size % 2 == 0){
				size--;
			}
			shape.setShapeHeight(size);
			shape.setShapeLength(size);
			break;
		case SQUARE:
			shapeHeight = rand.nextInt(4) + 2;
			shapeLength = shapeHeight;
			shape.setShapeHeight(shapeHeight);
			shape.setShapeLength(shapeLength);
			break;
		case TRIANGLE:
			break;
		default:
			break;
		}

	}
}
