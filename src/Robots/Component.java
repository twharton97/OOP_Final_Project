package robots;

import java.util.ArrayList;
import java.util.Random;
import game.Xform;
import game.deathWatcher;
import game.killable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

/**
 * This class is used to piece together a robot through seperate nodes and/or shapes
 * This way you can manipulate each individual piece as they are all attached to the large 
 * robot group
 * @author Tyler
 *
 */
public abstract class Component extends Xform implements killable {
	protected int health;
	protected int armor;
	protected int weight;
	protected double storedWidth;
	protected double currentWidth;
	protected ArrayList<Box> recPieces;
	protected ArrayList<Sphere> spherePieces;
	protected ArrayList<Cylinder> cylinderPieces;
	protected ArrayList<deathWatcher> deathWatchers;
	private Timeline animateExplosion;
	private KeyFrame explosionKey;
	private int animationTick = -100;
	
	/**
	 * This constructor adds all deathWatchers to the components and initializes the 
	 * collection of pieces within the Component
	 * 
	 * @param world is used to add the game world or engine to the list of classes
	 * to be notified when this Component perishes
	 * 
	 * @param robot is used to add the parent unit to the list of classes to be
	 * notified when this Component perishes
	 */
	protected Component(deathWatcher world, deathWatcher robot) {
		recPieces = new ArrayList<Box>();
		spherePieces = new ArrayList<Sphere>();
		cylinderPieces = new ArrayList<Cylinder>();
		deathWatchers = new ArrayList<deathWatcher>();
		deathWatchers.add(world);
		deathWatchers.add(robot);
	}

	public ArrayList<Box> getRecPieces() {
		return recPieces;
	}

	public ArrayList<Sphere> getSpherePieces() {
		return spherePieces;
	}

	public ArrayList<Cylinder> getCylinderPieces() {
		return cylinderPieces;
	}
	
	/**
	 * 
	 * @return The combined width of all adjacent components within
	 * the component 
	 */
	public double getWidth() {
		for (Box box : recPieces) {
			currentWidth = box.getWidth();
			if (currentWidth > storedWidth) {
				storedWidth = currentWidth;
			}
		}
		return storedWidth;
	}
	
	/**
	 * This Method is used to color and texture all pieces
	 * of the component
	 * 
	 * @param mat (the Phong Material used to color texture the 
	 * pieces of this component
	 */
	public void coloring(PhongMaterial mat) {
		for (Box box : recPieces) {
			box.setMaterial(mat);
		}
		for (Sphere sphere : spherePieces) {
			sphere.setMaterial(mat);
		}
		for (Cylinder cylinder : cylinderPieces) {
			cylinder.setMaterial(mat);
		}
	}
	
	/**
	 * this method causes all pieces of the robot to follow an animation
	 * route reminiscent of an explosion.
	 */
	public void explode() {
		animateExplosion = new Timeline();
		animateExplosion.setCycleCount(200);

		explosionKey = new KeyFrame(Duration.seconds(.0138), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				animationTick++;
				Random rand = new Random();
				
				for (Box box : recPieces) {
					switch (rand.nextInt(4)) {
					case 0:
						box.setTranslateX(box.getTranslateX() + 5);
						break;
					case 1:
						box.setTranslateZ(box.getTranslateZ() + 5);
						break;
					case 2:
						box.setTranslateX(box.getTranslateX() - 5);
						break;
					case 3:
						box.setTranslateZ(box.getTranslateZ() - 5);
						break;
					}
					box.setTranslateY(-(-(1d/81d)*(animationTick*animationTick) -((5d/18d)*(animationTick))+75));

				}
				for (Sphere sphere : spherePieces) {
					switch (rand.nextInt(4)) {
					case 0:
						sphere.setTranslateX(sphere.getTranslateX() + 5);
						break;
					case 1:
						sphere.setTranslateZ(sphere.getTranslateZ() + 5);
						break;
					case 2:
						sphere.setTranslateX(sphere.getTranslateX() - 5);
						break;
					case 3:
						sphere.setTranslateZ(sphere.getTranslateZ() - 5);
						break;
					}
					sphere.setTranslateY(-(-(1d/81d)*(animationTick*animationTick) -((5d/18d)*(animationTick))+75));

				}
				for (Cylinder cylinder : cylinderPieces) {
					switch (rand.nextInt(4)) {
					case 0:
						cylinder.setTranslateX(cylinder.getTranslateX() + 5);
						break;
					case 1:
						cylinder.setTranslateZ(cylinder.getTranslateZ() + 5);
						break;
					case 2:
						cylinder.setTranslateX(cylinder.getTranslateX() - 5);
						break;
					case 3:
						cylinder.setTranslateZ(cylinder.getTranslateZ() - 5);
						break;
					}
					cylinder.setTranslateY(-(-(1d/81d)*(animationTick*animationTick) -((5d/18d)*(animationTick))+75));

				}
			}

		});

		animateExplosion.getKeyFrames().add(explosionKey);
		animateExplosion.play();
		animateExplosion.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				animateExplosion.getKeyFrames().removeAll(explosionKey);
				for(deathWatcher watcher : deathWatchers){
					watcher.somethingDied(this);
				}
				
			}
		});
	}

}
