package buildings;

import game.deathWatcher;
import javafx.geometry.Point3D;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import robots.Component;

public class buildingBlock extends Component {
	
	public buildingBlock(deathWatcher world, deathWatcher building){
		super(world, building);
		System.out.println("making cc sphere");
		spherePieces.add(new Sphere(65));
		for(Sphere sphere : spherePieces){
			this.getChildren().add(sphere);
		}
		
		System.out.println("cc sphere made");
	}

}
