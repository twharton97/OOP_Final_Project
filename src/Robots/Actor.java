package robots;

import java.util.ArrayList;
import java.util.Random;

import game.World;
import game.Xform;
import game.deathWatcher;
import game.killable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public abstract class Actor extends Xform implements CommandListener, killable{

	protected double speed;
	protected double currentHealth;
	protected double maxHealth;
	protected double blockChance;
	protected Timeline movementTL;
	protected Timeline collisionTL;
	protected Timeline combatTL;
	private KeyFrame movementFrame;
	private KeyFrame collisionKey;
	private KeyFrame combatKey;
	private Boolean collisionDetected = false;
	protected int team;
	protected boolean isMeleeCombatant = false;
	private int rotation = 90;
	private int counterRotation = 90;
	protected int[] damageRange;
	protected int width;
	private int combatTick = 0;
	protected ArrayList<deathWatcher> deathWatchers;
	protected Random rand = new Random();
	protected Box healthBarGreen;
	protected Box healthBarRed;
	protected double originalHealthBarWidth;

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
		PhongMaterial red = new PhongMaterial(Color.RED);
		PhongMaterial green = new PhongMaterial(Color.LIMEGREEN);
		healthBarRed = new Box(30,3,3);
		healthBarRed.setMaterial(red);
		healthBarGreen = new Box(32,6,6);
		healthBarGreen.setMaterial(green);
		healthBarGreen.setRotationAxis(Rotate.Y_AXIS);
		healthBarRed.setRotationAxis(Rotate.Y_AXIS);
		
	}
	public abstract void explode();

	public void coloring(PhongMaterial mat) {
		for (int i = 0; i < this.getChildren().size(); i++) {
			((Component) this.getChildren().get(i)).coloring(mat);
		}
	}

	@Override
	public void move(double mouseOldX, double mouseOldZ, ArrayList<Actor> allActors) {
		animateNode(this, allActors, mouseOldX, mouseOldZ, speed);
	}

	private void animateNode(Actor activeActor, ArrayList<Actor> allActors, double mouseOldX, double mouseOldZ, double speed) {

		double animationMouseX = mouseOldX;
		double animationMouseZ = mouseOldZ;

		double distanceX = Math.abs(mouseOldX - this.getTranslateX());
		double distanceZ = Math.abs(mouseOldZ - this.getTranslateZ());
		this.setRotateY(Math.toDegrees(Math.atan2(mouseOldX - this.getTranslateX(), mouseOldZ - this.getTranslateZ())));
		activeActor.healthBarGreen.setRotate(0 - Math.toDegrees(Math.atan2(mouseOldX - this.getTranslateX(), mouseOldZ - this.getTranslateZ())));
		activeActor.healthBarRed.setRotate(0 - Math.toDegrees(Math.atan2(mouseOldX - this.getTranslateX(), mouseOldZ - this.getTranslateZ())));
		

		int animationCount = (int) ((distanceX + distanceZ) / (speed / 10)) + 1;
		double dX = distanceX / (double) animationCount;
		double dZ = distanceZ / (double) animationCount;

		movementTL.setCycleCount(animationCount);
		collisionTL.setCycleCount(40);
		movementFrame = new KeyFrame(Duration.seconds(.005), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (animationMouseX > activeActor.getTranslateX()) {
					activeActor.setTranslateX((activeActor.getTranslateX() + dX));
				} else if (animationMouseX < activeActor.getTranslateX()) {
					activeActor.setTranslateX(activeActor.getTranslateX() - dX);
				}
				if (animationMouseZ > activeActor.getTranslateZ()) {
					activeActor.setTranslateZ((activeActor.getTranslateZ() + dZ));
				} else if (animationMouseZ < activeActor.getTranslateZ()) {
					activeActor.setTranslateZ(activeActor.getTranslateZ() - dZ);
				}
				for (Actor otherActor : allActors) {
					if (otherActor != activeActor) {
						if (collisionDetector(activeActor, otherActor) == true) {
							collisionDetected = true;
							collisionKey = new KeyFrame(Duration.seconds(.005), new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent event) {
//									if(activeActor.getTranslateX()<otherActor.getTranslateX()){
//										activeActor.setTranslateZ(activeActor.getTranslateZ() + Math.abs(activeActor.getTranslateX() - otherActor.getTranslateX())/200);	
//									}else{
//										activeActor.setTranslateZ(activeActor.getTranslateZ() - Math.abs(activeActor.getTranslateX() - otherActor.getTranslateX())/200);	
//									}
//									if(activeActor.getTranslateZ()<otherActor.getTranslateZ()){
//										activeActor.setTranslateX(activeActor.getTranslateZ() + Math.abs(activeActor.getTranslateZ() - otherActor.getTranslateZ())/200);	
//									}else{
//										activeActor.setTranslateX(activeActor.getTranslateZ() - Math.abs(activeActor.getTranslateZ() - otherActor.getTranslateZ())/200);	
//									}
									
									
									
									
//									if(Math.abs(otherActor.getTranslateZ() - activeActor.getTranslateZ()) <= Math.abs(otherActor.getTranslateZ() - activeActor.getTranslateZ())){
//										if(activeActor.getTranslateX()>otherActor.getTranslateX()){
//											activeActor.setTranslateX((activeActor.getTranslateX() - .2));											
//										}
//										activeActor.setTranslateX((activeActor.getTranslateX() + .2));
//									}else{
//										if(activeActor.getTranslateZ()>otherActor.getTranslateZ()){
//											activeActor.setTranslateZ((activeActor.getTranslateZ() - .2));
//										}
//										activeActor.setTranslateZ((activeActor.getTranslateZ() + .2));
//									}
									
									
									
//									if (otherActor.getTranslateZ() > activeActor.getTranslateZ()) {
//										activeActor.setTranslateZ(activeActor.getTranslateZ() - .1);
////										activeActor.setTranslateX((activeActor.getTranslateX() - .2));
//									} else if (otherActor.getTranslateZ() < activeActor.getTranslateZ()) {
////										activeActor.setTranslateX(activeActor.getTranslateX() - .2);
//										activeActor.setTranslateZ(activeActor.getTranslateZ() + .1);
//									}else if (otherActor.getTranslateX() > activeActor.getTranslateX()) {
////										activeActor.setTranslateZ((activeActor.getTranslateZ() + .2));
//										activeActor.setTranslateX(activeActor.getTranslateX() - .1);
//
//									} else if (otherActor.getTranslateX() < activeActor.getTranslateX()) {
////										activeActor.setTranslateZ(activeActor.getTranslateZ() - .2);
//										activeActor.setTranslateX(activeActor.getTranslateX() + .1);
//
//									}
//									double totalDistance = (Math.abs(activeActor.getTranslateX()-otherActor.getTranslateX()) + Math.abs(activeActor.getTranslateZ()-otherActor.getTranslateZ()));
//									activeActor.setTranslateX(1d * (totalDistance/Math.abs(activeActor.getTranslateX()-otherActor.getTranslateX())));
//									activeActor.setTranslateZ(1d * (totalDistance/Math.abs(activeActor.getTranslateZ()-otherActor.getTranslateZ())));
									
									
									
									if (Math.abs(otherActor.getTranslateZ()-mouseOldZ) >= Math.abs(otherActor.getTranslateX()-mouseOldX)){
										if(activeActor.getTranslateX() < otherActor.getTranslateX()){
											activeActor.setTranslateX(activeActor.getTranslateX()-1);											
										}else{
											activeActor.setTranslateX(activeActor.getTranslateX()+1);
										}
									}else {
										if(activeActor.getTranslateZ() < otherActor.getTranslateZ()){
											activeActor.setTranslateZ(activeActor.getTranslateZ()-1);											
										}else{
											activeActor.setTranslateZ(activeActor.getTranslateZ()+1);
										}
									}
									
								}

							});
							if (collisionDetected == true) {
								handleCollision(mouseOldX, mouseOldZ, allActors, activeActor, otherActor);
							}
						}else{
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
		for (Node otherShape : allActors) {
			if (otherShape != activeActor) {
			
			}
		}
	}

	/**
	 * this method is used for checking to see if two colliding robots will engage in combat
	 * @param node (node, this is the active shape)
	 * @param otherShape (Actor, this is the shape collided with)
	 * @return boolean is in combat == true
	 */
	private boolean checkForMeleeCombat(Node node, Actor otherShape) {
//		System.out.println("this.team = " + this.team + " otherShape.team = " + otherShape.team + " collisisonDetected = " + collisionDetected);
		if (this.team != otherShape.team && collisionDetected == true) {
//			System.out.println("combat detected");
			return true;
		}else{
			return false;
		}

	}

	private void handleCollision(double mouseOldX, double mouseOldZ, ArrayList<Actor> allActors, Actor activeActor, Actor otherActor) {
		if (!movementTL.getKeyFrames().isEmpty()) {
			movementTL.stop();
			movementTL.getKeyFrames().remove(0);
		}
		if(checkForMeleeCombat(activeActor, otherActor)){
			handleMeleeCombat(activeActor, otherActor);
		}else{
			collisionTL.getKeyFrames().add(collisionKey);
			collisionTL.play();
			collisionTL.setOnFinished(new EventHandler<ActionEvent>() {
		
				@Override
				public void handle(ActionEvent event) {
					if (!(Math.abs(mouseOldX - activeActor.getTranslateX()) < 25)
							&& !(Math.abs(mouseOldX - otherActor.getTranslateX()) < 25)) {
						move(mouseOldX, mouseOldZ, allActors);
						collisionTL.getKeyFrames().remove(0);
					}
				}
			});
		}
	}

	private void handleMeleeCombat(Actor activeActor, Actor otherActor) {
//		for(int i = 0; i < 100; i ++){
//			if ( i % 10 == 0){
//				System.out.println("combat stuff");
//			}
//		}
		
		combatTL.setCycleCount(combatTL.INDEFINITE);
		combatKey = new KeyFrame(Duration.seconds(.005), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				activeActor.setRotateY((rotation+=3)%360);
				otherActor.setRotateY((rotation)%360);
				activeActor.healthBarGreen.setRotate((counterRotation-=3)%360);
				activeActor.healthBarRed.setRotate((counterRotation)%360);
				otherActor.healthBarGreen.setRotate((counterRotation)%360);
				otherActor.healthBarRed.setRotate((counterRotation)%360);
				combatTick++;
				
				if(combatTick % 70 == 0){
					if(((rand.nextInt(100)+1)<=rand.nextInt((int)otherActor.getBlockChance()))){
						System.out.println("otherActor blocked");	
					}else{
						otherActor.setHealth(otherActor.getHealth()-(rand.nextInt(activeActor.getDamageRange()[1]-activeActor.getDamageRange()[0]+1)+activeActor.getDamageRange()[0]));
						System.out.println("otherActor health is now " + otherActor.getHealth());
					}
					if(((rand.nextInt(100)+1)<=rand.nextInt((int)activeActor.getBlockChance()))){
						System.out.println("activeActor blocked");
					}else{
						activeActor.setHealth(activeActor.getHealth()-(rand.nextInt(otherActor.getDamageRange()[1]-otherActor.getDamageRange()[0]+1)+otherActor.getDamageRange()[0]));
						System.out.println("activeActor health is now " + activeActor.getHealth());
					}
					
				}
				
				if(activeActor.getHealth() <= 0){
					combatTL.stop();
					combatTL.getKeyFrames().removeAll(combatKey);
					activeActor.explode();
				}
				if( otherActor.getHealth() <= 0){
					combatTL.stop();
					combatTL.getKeyFrames().removeAll(combatKey);
					otherActor.explode();
					
				}
				
			}

		});
		if (!combatTL.getKeyFrames().isEmpty()) {
			combatTL.stop();
			combatTL.getKeyFrames().removeAll(combatKey);
		}
		combatTL.getKeyFrames().add(combatKey);
		combatTL.play();
		combatTL.setOnFinished(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				combatTick = 0;
				
			}
		});
		
		
	}

	@Override
	public abstract void isSelected();

	@Override
	public abstract void deSelected();
	
	protected void setHealth(double newHealth){
		currentHealth = newHealth;
		healthBarGreen.setWidth(originalHealthBarWidth * (currentHealth/maxHealth));
//		healthBarGreen.setTranslateX(0 - Math.abs(((originalHealthBarWidth/2)-((originalHealthBarWidth/2)*(currentHealth/maxHealth)))));
	}
	
	public abstract void setDamageRange(int[] newDamageRange);
	
	protected abstract int[] getDamageRange();
	
	protected abstract double getHealth();
	
	public abstract double getBlockChance();
	
	public abstract void setBlockChance(double newBlockChance);
	
	public int getTeam(){
		return team;
	}

	public int getWidth(){
		return width;
	}
	
	private boolean collisionDetector(Actor node, Actor otherShape) {
		if (otherShape != node) {
			if (Math.abs(node.getTranslateX() - otherShape.getTranslateX()) < node.getWidth() + otherShape.getWidth() + 2) {
				if (Math.abs(node.getTranslateZ() - otherShape.getTranslateZ()) < node.getWidth() + otherShape.getWidth() +2) {
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

//
//if (node.getTranslateX() >= otherShape.getTranslateX() - node.getWidth()
//&& node.getTranslateX() <= otherShape.getTranslateX() + node.getWidth()) {
//if (node.getTranslateZ() >= otherShape.getTranslateZ() - node.getWidth()
//	&& node.getTranslateZ() <= otherShape.getTranslateZ() + node.getWidth()) {