
import java.awt.Point;
import java.util.Random;

public class ShapeController {
	private static Random rand;
//	private Board gameBoard;
	private Shape[] unitArray;
//	private int boardH;
//	private int boardL;
	private ShapeDesign design;
	
	public ShapeController() {
		rand = new Random();
		unitArray = new Shape[rand.nextInt(100)+1];// numberOfShapes////////////////////////////
//		boardH = 48;// rand.nextInt(51 - 10) + 10; ////////////////////UsingTheseNumbersRoughlyFitsToANearFullScreenConsoleForSmootherAnimation
//		boardL = 119;// rand.nextInt(51 - 10) + 10;////////////////////TheCommentedRandomsAddressTheRandomBoardSizesRequested
//		gameBoard = new Board(boardH, boardL);
//		gameBoard.makeBoardClear();
		

	}

	public void createObjects() {
		for (int i = 0; i < unitArray.length; i++) {
			design = ShapeDesign.getRandomDesign();
			unitArray[i] = new Shape(1080, 1920, design);
			unitArray[i].setVelocity(Direction.getRandomVelocity(rand.nextInt(10) + 1));
		}
	}
	
	public Shape[] getShape(){
		return unitArray;
	}

//	public void animateBoard() {
//		for (int i = 0; i < unitArray.length; i++) {
//			unitArray[i].setVelocity(Direction.getRandomVelocity(rand.nextInt(3) + 1));
//		}
//
//		for (int time = 0; time < 1000; time++) {//////////////////////////////////SetFor100ForLongerAnimation,ThisWouldBe"<4"ForRequestedSpecs
//			gameBoard.makeBoardClear();
//			for (int i = 0; i < unitArray.length; i++) {
//				int yMovement = unitArray[i].getVelocity()[0];
//				int xMovement = unitArray[i].getVelocity()[1];
//				char shapeToken = unitArray[i].getToken();
//
//				Point shapePoint = unitArray[i].getPoint();
//				int x = (int) shapePoint.getX();
//				int y = (int) shapePoint.getY();
//				
//				shapePoint.translate(xMovement, yMovement);
//				for (int row = 0; row < unitArray[i].getShapeHeight(); row++) {
//					for (int col = 0; col < unitArray[i].getShapeLength(); col++) {
//						int boardX = x + col;
//						int boardY = y + row;
//						///////////////////////////////////////////////IfBlockHandlesWrappingOfTheShapesOnTheBoard
//						if (boardX >= boardL) {
//							boardX = boardX % boardL;
//						}
//						if (boardY >= boardH) {
//							boardY = boardY % boardH;
//						}
//						if (boardX < 0) {
//							boardX = (boardL - 1) - ((boardX * -2) + boardX) % boardL;
//						}
//						if (boardY < 0) {
//							boardY = (boardH - 1) - ((boardY * -2) + boardY) % boardH;
//						}
//						
//							if(unitArray[i].getShapeArray()[row][col] != '-'){
//								gameBoard.setBoardDisplay(boardY, boardX, shapeToken);							
//						}
//					}
//				}
//
//				unitArray[i].setShapePoint(shapePoint);
//			}
//
//			System.out.println(gameBoard.toString(gameBoard));
//			try {//////////////////////////////////////////////////////////this code makes the animating smoother.
//				Thread.sleep(120);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
//		}
//
//	}

}