package Robots;

import Game.deathWatcher;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;

public class Torso extends Component {

	int height;
	public Torso(deathWatcher world, deathWatcher robot){
		super(world, robot);
		System.out.println("making torso");
		recPieces.add(new Box(10,20,10));
		recPieces.add(new Box(16,10,12));
		recPieces.get(1).setTranslateY(-recPieces.get(0).getHeight()*.65);
//		recPieces.add(new Box(20))
		
		
		for(Box box : recPieces){
			this.getChildren().add(box);
			height += box.getHeight();
		}
//		for(Sphere sphere : spherePieces){
//			this.getChildren().add(sphere);
//		}
		
		System.out.println("torso made");
	}
}
