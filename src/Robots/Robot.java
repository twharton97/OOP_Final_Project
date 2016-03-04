package Robots;

import java.util.ArrayList;

import Game.World;
import Game.Xform;
import Game.deathWatcher;
import Game.killable;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;

public class Robot extends Actor implements deathWatcher {
	Head head;
	Arm leftArm;
	Arm rightArm;
	Torso torso;
	Leg rightLeg;
	Leg leftLeg;
	Weapon leftWeapon;
	Weapon rightWeapon;
	ArrayList<Component> components;
	PhongMaterial PM = new PhongMaterial();
	Cylinder selectedCircle;
	
	
	public Robot(PhongMaterial mat, int teamNumber, deathWatcher world){
		super(teamNumber, world);
		PM.setDiffuseColor(Color.GREEN);
		this.setLayoutY(-10);
		this.setTranslateY(-2);
//		Chasis frame = new Chasis();
//		Head head = new Head();
//		Arm leftArm = new Arm();
//		Arm rightArm = new Arm();
//		Torso torso = new Torso();
		
		rightLeg = new Leg(world, this);
		rightLeg.setTranslateX(7);
		this.getChildren().add(rightLeg);
		
		leftLeg = new Leg(world, this);
		leftLeg.setTranslateX(-7);
		this.getChildren().add(leftLeg);
		
		torso = new Torso(world, this);
		torso.setTranslateY(-leftLeg.height*1.1);
		this.getChildren().add(torso);
		
		head = new Head(world, this);
		head.setTranslateY(torso.getTranslateY()*.91-torso.height*.81);
		this.getChildren().add(head);
		
		rightArm = new Arm(world, this);
		rightArm.setTranslateY(torso.getTranslateY()*.81-torso.height*.81);
		rightArm.setTranslateX(10);
		this.getChildren().add(rightArm);
		
		leftArm = new Arm(world, this);
		leftArm.setTranslateY(torso.getTranslateY()*.81-torso.height*.81);
		leftArm.setTranslateX(-10);
		this.getChildren().add(leftArm);
		
		selectedCircle = new Cylinder(20,2);
		selectedCircle.setTranslateY(-2);
		selectedCircle.setMaterial(PM);
		coloring(mat);
		
		setHealth();
		setDamageRange();
		
	}

	@Override
	public void isSelected() {
		deSelected();
		this.getChildren().add(selectedCircle);
		
		
	}

	@Override
	public void deSelected() {
		if(this.getChildren().lastIndexOf(selectedCircle) != -1){
			this.getChildren().remove(selectedCircle);
		}
		
		
	}
	
	public double getWidth(){
		return leftArm.getWidth() + rightArm.getWidth() + torso.getWidth();
	}

	@Override
	protected void setHealth() {
		health = 150;
		
	}

	@Override
	protected void setDamageRange() {
		damageRange[0] = 10;
		damageRange[1] = 20;
		
	}

	@Override
	public void explode() {
		for (int i = 0; i < this.getChildren().size(); i++){
			if(!this.getChildren().contains(selectedCircle)){
				((killable) this.getChildren().get(i)).explode();
			}
		}
	}

	@Override
	public void somethingDied(Object o) {
		for(deathWatcher watcher : deathWatchers){
			watcher.somethingDied(this);
		}
	}
}
