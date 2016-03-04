package Robots;

import Game.deathWatcher;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;

public class Head extends Component {
	public Head(deathWatcher world, deathWatcher robot){
		super(world, robot);
		System.out.println("making head");
		cylinderPieces.add(new Cylinder(3.5,7));
		spherePieces.add(new Sphere(5.3));
		spherePieces.get(0).setTranslateY(-cylinderPieces.get(0).getHeight()*.9);
		
		for(Sphere sphere : spherePieces){
			this.getChildren().add(sphere);
		}
		for(Cylinder cylinder : cylinderPieces){
			this.getChildren().add(cylinder);
		}
		
		System.out.println("head made");
	}
}
