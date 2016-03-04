package Robots;

import java.util.ArrayList;

import Game.Xform;
import Game.deathWatcher;
import Game.killable;
import Game.World;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.util.Duration;

public abstract class Actor extends Xform implements CommandListener, killable{

	protected double speed;
	protected Timeline movementTL;
	protected Timeline collisionTL;
	protected Timeline combatTL;
	private KeyFrame movementFrame;
	private KeyFrame collisionKey;
	private KeyFrame combatKey;
	private Boolean collisionDetected = false;
	protected int team;
	protected boolean isMeleeCombatant = false;
	private int rotation= 90;
	protected int health;
	protected int[] damageRange;
	private int combatTick = 0;
	protected ArrayList<deathWatcher> deathWatchers;

	public Actor(int teamNumber, deathWatcher world) {
		speed = 15;
		movementTL = new Timeline();
		collisionTL = new Timeline();
		combatTL = new Timeline();
		team = teamNumber;
		damageRange = new int[2];
		damageRange[0] = 0;
		damageRange[1] = 1;
		deathWatchers = new ArrayList<deathWatcher>();
		deathWatchers.add(world);
		
	}
	public abstract void explode();

	public void coloring(PhongMaterial mat) {
		for (int i = 0; i < this.getChildren().size(); i++) {
			((Component) this.getChildren().get(i)).coloring(mat);
		}
	}

	@Override
	public void move(double mouseOldX, double mouseOldZ, ArrayList<Node> allNodes) {
		animateNode(this, allNodes, mouseOldX, mouseOldZ, speed);
	}

	private void animateNode(Xform node, ArrayList<Node> allNodes, double mouseOldX, double mouseOldZ, double speed) {

		double animationMouseX = mouseOldX;
		double animationMouseZ = mouseOldZ;

		double distanceX = Math.abs(mouseOldX - this.getTranslateX());
		double distanceZ = Math.abs(mouseOldZ - this.getTranslateZ());
		this.setRotateY(Math.toDegrees(Math.atan2(mouseOldX - this.getTranslateX(), mouseOldZ - this.getTranslateZ())));

		int animationCount = (int) ((distanceX + distanceZ) / (speed / 10)) + 1;
		double dX = distanceX / (double) animationCount;
		double dZ = distanceZ / (double) animationCount;

		movementTL.setCycleCount(animationCount);
		collisionTL.setCycleCount(20);
		movementFrame = new KeyFrame(Duration.seconds(.005), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (animationMouseX > node.getTranslateX()) {
					node.setTranslateX((node.getTranslateX() + dX));
				} else if (animationMouseX < node.getTranslateX()) {
					node.setTranslateX(node.getTranslateX() - dX);
				}
				if (animationMouseZ > node.getTranslateZ()) {
					node.setTranslateZ((node.getTranslateZ() + dZ));
				} else if (animationMouseZ < node.getTranslateZ()) {
					node.setTranslateZ(node.getTranslateZ() - dZ);
				}
				for (Node otherShape : allNodes) {
					if (otherShape != node) {
						if (collisionDetector(node, otherShape) == true) {
							collisionDetected = true;
							System.out.println("collisionDetected turned to True");
							System.out.println("collision detected");
							collisionKey = new KeyFrame(Duration.seconds(.005), new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent event) {

									if (otherShape.getTranslateZ() > node.getTranslateZ()) {
										node.setTranslateZ(node.getTranslateZ() - .02);
										node.setTranslateX((node.getTranslateX() + .2));
									} else if (otherShape.getTranslateZ() < node.getTranslateZ()) {
										node.setTranslateX(node.getTranslateX() - .2);
										node.setTranslateZ(node.getTranslateZ() + .02);
									}

									if (otherShape.getTranslateX() > node.getTranslateX()) {
										node.setTranslateZ((node.getTranslateZ() + .2));
										node.setTranslateX(node.getTranslateX() - .02);

									} else if (otherShape.getTranslateX() < node.getTranslateX()) {
										node.setTranslateZ(node.getTranslateZ() - .2);
										node.setTranslateX(node.getTranslateX() + .02);

									}
								}

							});
							if (collisionDetected == true) {
								handleCollision(mouseOldX, mouseOldZ, allNodes, node, otherShape);
							}
						}else{
//							System.out.println("collisionDetected turned to false");
							collisionDetected = false;
						}

					}
				}

			}

		});

		if (!movementTL.getKeyFrames().isEmpty()) {
			movementTL.stop();
			movementTL.getKeyFrames().remove(0);
		}
		movementTL.getKeyFrames().add(movementFrame);
		movementTL.play();
		for (Node otherShape : allNodes) {
			if (otherShape != node) {
			
			}
		}
	}

	private boolean checkForMeleeCombat(Node node, Actor otherShape) {
//		System.out.println("this.team = " + this.team + " otherShape.team = " + otherShape.team + " collisisonDetected = " + collisionDetected);
		if (this.team != otherShape.team && collisionDetected == true) {
//			System.out.println("combat detected");
			return true;
		}else{
			return false;
		}

	}

	private void handleCollision(double mouseOldX, double mouseOldZ, ArrayList<Node> allNodes, Node node, Node otherShape) {
		System.out.println("collision handled");
		if (!movementTL.getKeyFrames().isEmpty()) {
			movementTL.stop();
			movementTL.getKeyFrames().remove(0);
		}
		if(checkForMeleeCombat(node, (Robot) otherShape)){
			handleMeleeCombat((Robot) node, (Robot) otherShape);
		}else{
			collisionTL.getKeyFrames().add(collisionKey);
			collisionTL.play();
			collisionTL.setOnFinished(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent event) {
					if (!(Math.abs(mouseOldX - node.getTranslateX()) < 25)
							&& !(Math.abs(mouseOldX - otherShape.getTranslateX()) < 25)) {
						move(mouseOldX, mouseOldZ, allNodes);
						collisionTL.getKeyFrames().remove(0);
					}
				}
			});
		}
	}

	private void handleMeleeCombat(Actor node, Actor otherShape) {
//		for(int i = 0; i < 100; i ++){
//			if ( i % 10 == 0){
//				System.out.println("combat stuff");
//			}
//		}
		
		combatTL.setCycleCount(200);
		combatKey = new KeyFrame(Duration.seconds(.005), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				node.setRotateY((rotation+=3)%360);
				otherShape.setRotateY((rotation+=3)%360);
				combatTick++;
				if(combatTick % 20 == 0){
					System.out.println("hit");
				}
				
			}

		});
		if (!combatTL.getKeyFrames().isEmpty()) {
			combatTL.stop();
			combatTL.getKeyFrames().remove(0);
		}
		combatTL.getKeyFrames().add(combatKey);
		combatTL.play();
		combatTL.setOnFinished(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				node.setRotateY(Math.toDegrees(Math.atan2(otherShape.getTranslateX(), otherShape.getTranslateZ())));
				combatTick = 0;
				node.explode();
				otherShape.explode();
				
			}
		});
		
		
	}

	@Override
	public abstract void isSelected();

	@Override
	public abstract void deSelected();
	
	protected abstract void setHealth();
	
	protected abstract void setDamageRange();

	private boolean collisionDetector(Xform node, Node otherShape) {
		if (otherShape != node) {
			if (node.getTranslateX() >= otherShape.getTranslateX() - 25
					&& node.getTranslateX() <= otherShape.getTranslateX() + 25) {
				if (node.getTranslateZ() >= otherShape.getTranslateZ() - 25
						&& node.getTranslateZ() <= otherShape.getTranslateZ() + 25) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

}
