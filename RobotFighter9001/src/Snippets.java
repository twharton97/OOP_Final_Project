//import java.awt.Font;
//import java.awt.Point;
//
//public class Snippets {
//























//	for (int i = 0; i < s.length; i++) {
//		g.setFont((new Font("Arial", Font.PLAIN, s[i].getShapeLetterSize())));
//		Point sp = s[i].getPoint();
//
//		
//		//printSquare
//		if (s[i].getShapeDesign() == ShapeDesign.SQUARE) {	
//			for (int row = 0; row < s[i].getShapeHeight(); row++) {
//				for (int col = 0; col < s[i].getShapeLength(); col++) {
//					
//					//wrappingStart
//					int boardX = sp.x + col*s[i].getShapeLetterSize();
//					int boardY = sp.y + row*s[i].getShapeLetterSize();
//					if (boardX >= 1920) {
//						boardX = boardX % 1920;
//					}
//					if (boardY >= 1080) {
//						boardY = boardY % 1080;
//					}
//					if (boardX < 0) {
//						boardX = (1920 - 1) - ((boardX * -2) + boardX) % 1920;
//					}
//					if (boardY < 0) {
//						boardY = (1080 - 1) - ((boardY * -2) + boardY) % 1080;
//					}//wrappingEnd
//					g.drawString(s[i].getToken(), boardX, boardY);
//				}
//			}
//			
//			
//		}else if(s[i].getShapeDesign() == ShapeDesign.DIAMOND){	//printDiamond
//			
//			for (int row = 1; row <= s[i].getShapeHeight()/2 + 1; row++){
//				for ( int col = 1; col < s[i].getShapeLength() + 1; col++){
//					
//					//wrappingStart
//					int boardX = sp.x + (col-1)*s[i].getShapeLetterSize();
//					int boardY = sp.y + (row-1)*s[i].getShapeLetterSize();
//					if (boardX >= 1920) {
//						boardX = boardX % 1920;
//					}
//					if (boardY >= 1080) {
//						boardY = boardY % 1080;
//					}
//					if (boardX < 0) {
//						boardX = (1920 - 1) - ((boardX * -2) + boardX) % 1920;
//					}
//					if (boardY < 0) {
//						boardY = (1080 - 1) - ((boardY * -2) + boardY) % 1080;
//					}//wrappingEnd
//					
//					
//					if((col <= (s[i].getShapeLength() - s[i].getShapeLength()/2 -row)) ||( col > (s[i].getShapeLength()/2+row))){
//						//do nothing
//					}else{
//						g.drawString(s[i].getToken(), boardX, boardY);
//					}
//				}
//			}
//			
//			for (int row = s[i].getShapeHeight(); row > s[i].getShapeHeight()/2 + 1; row--){
//				for ( int col = 1; col < s[i].getShapeLength() + 1; col++){
//
//					//wrappingStart
//					int boardX = sp.x + (col-1)*s[i].getShapeLetterSize();
//					int boardY = sp.y + (row-1)*s[i].getShapeLetterSize();
//					if (boardX >= 1920) {
//						boardX = boardX % 1920;
//					}
//					if (boardY >= 1080) {
//						boardY = boardY % 1080;
//					}
//					if (boardX < 0) {
//						boardX = (1920 - 1) - ((boardX * -2) + boardX) % 1920;
//					}
//					if (boardY < 0) {
//						boardY = (1080 - 1) - ((boardY * -2) + boardY) % 1080;
//					}//wrappingEnd
//					
//					
//					
//					
//					if((col < (row*2 - (s[i].getShapeLength() + s[i].getShapeLength()/2)) + (s[i].getShapeLength() - row)) || (col > (s[i].getShapeLength() - row + (s[i].getShapeLength()/2 + 1)))){
//						//do nothing
//					}else{
//						g.drawString(s[i].getToken(), boardX, boardY);
//					}
//				}
//			}
//		}
//		
//	}
//























