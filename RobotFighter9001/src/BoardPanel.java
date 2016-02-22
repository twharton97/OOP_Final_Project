

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	Robot r;
	Shape[] s;

	public BoardPanel(Shape[] s) {
		setLayout(null); // use absolute positioning in this panel
		this.s = s;
		r = new Robot();
	}

	

	public void paint(Graphics g) {
		System.out.println("Painting BoardPanel...");
		/*
		 * colors in hex I picked using https://color.adobe.com
		 * https://color.adobe.com/Color-Theme-50-color-theme-7439677/?
		 * showPublished=true
		 */
		g.setColor(Color.decode("#FFFFFF"));
		
		
		for( int row = 0; row < r.legDimensions.length; row++){
				g.fillRect(r.legDimensions[row][0], r.legDimensions[row][1], r.legDimensions[row][2], r.legDimensions[row][3]);
		}
		// paint the background of the frame... "erases" what was there before
		g.fillOval(0, 0, 100, 200);
		int xPoints[] = new int[]{100, 117, 120, 122, 120, 117, 100};
		int yPoints[] = new int[]{900, 898, 903, 905, 907, 912, 910};
		int nPoints = 7;
		g.fillPolygon(xPoints, yPoints, nPoints);
		
//		leftKnee = new int[]{100,1740,17,10};
//		rightKnee = new int[]{150,1740,17,10};

		
		// painting an oval that will change size and position
//		g.setColor(Color.decode("#37434A"));
//		g.fillOval(anIntPlaceholder, anIntPlaceholder, anIntPlaceholder, anIntPlaceholder);

		// painting text that will change size
		//oldColor=517289
		g.setColor(Color.decode("#FF0000"));
		
		

	}

	public void moveShapes() {
		/*
		 * Repaint forces the BoardPanel to refresh its appearance on the UI
		 * 
		 */
		repaint();
		for( int i = 0; i < s.length; i++){
			Point sp = s[i].getPoint();
			sp.translate(s[i].getVelocity()[0], s[i].getVelocity()[1]);
			s[i].setShapePoint(sp);
		}
	}

}
