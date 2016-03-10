package robots;

import game.deathWatcher;
import javafx.scene.Node;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;

public class Leg extends Component {
	int height = 0;
	public Leg(deathWatcher world, deathWatcher robot){
		super(world, robot);
		System.out.println("making leg");
		recPieces.add(new Box(10,4,4));
		recPieces.get(0).setTranslateY(-recPieces.get(0).getHeight());
		recPieces.add(new Box(5,15,5));
		recPieces.get(1).setTranslateY(-recPieces.get(0).getHeight()*2 + recPieces.get(0).getTranslateY());
		spherePieces.add(new Sphere(2.5));
		spherePieces.get(0).setTranslateY( -(spherePieces.get(0).getRadius()*2) + recPieces.get(1).getTranslateY());
		recPieces.add(new Box(7,20,7));
		recPieces.get(2).setTranslateY(- recPieces.get(2).getHeight()/2 + spherePieces.get(0).getTranslateY() );
		
		for(Box box : recPieces){
			this.getChildren().add(box);
			height += box.getHeight();
		}
		for(Sphere sphere : spherePieces){
			this.getChildren().add(sphere);
		}
		
		System.out.println("leg made");
		
	}
	
}
