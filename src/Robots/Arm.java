package robots;

import game.deathWatcher;
import javafx.geometry.Point3D;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Arm extends Component {
	Point3D p3d;
	public Arm(deathWatcher world, deathWatcher robot){
		super(world, robot);
		System.out.println("making arm");
		
		recPieces.add(new Box(7,7,20));
		recPieces.get(0).setTranslateZ(5);
		
		recPieces.add(new Box(6,6,16));
		recPieces.get(1).setTranslateZ(21);
		recPieces.get(1).setTranslateY(-recPieces.get(0).getHeight()*.65);
		
		recPieces.add(new Box(4,4,4));
//		recPieces.add(new Box(20))
		
		
		for(Box box : recPieces){
			this.getChildren().add(box);
			
		}
//		for(Sphere sphere : spherePieces){
//			this.getChildren().add(sphere);
//		}
		
		System.out.println("arm made");
	}

}
