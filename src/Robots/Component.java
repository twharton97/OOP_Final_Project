package Robots;

import java.util.ArrayList;
import java.util.Random;

import Game.Xform;
import Game.deathWatcher;
import Game.killable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

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

	public double getWidth() {
		for (Box box : recPieces) {
			currentWidth = box.getWidth();
			if (currentWidth > storedWidth) {
				storedWidth = currentWidth;
			}
		}
		return storedWidth;
	}

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

	public void explode() {
		animateExplosion = new Timeline();
		animateExplosion.setCycleCount(200);

		explosionKey = new KeyFrame(Duration.seconds(.0138), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println(animationTick);
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
				animateExplosion.getKeyFrames().remove(0);
				for(deathWatcher watcher : deathWatchers){
					watcher.somethingDied(this);
				}
				
			}
		});
	}

}
