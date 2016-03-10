package buildings;

import java.util.ArrayList;

import game.deathWatcher;
import game.killable;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import robots.Actor;
import robots.Component;

public class CommandCenter extends Actor implements deathWatcher {

	ArrayList<Component> components;
	PhongMaterial PM = new PhongMaterial();
	Cylinder selectedCircle;
	ArrayList<buildingBlock> buildingBlocks;
	

	public CommandCenter(PhongMaterial mat, int teamNumber, deathWatcher world) {
		super(teamNumber, world);
		PM.setDiffuseColor(Color.GREEN);
		this.setTranslateY(-2);
		buildingBlocks = new ArrayList<buildingBlock>();
		
		buildingBlocks.add(new buildingBlock(world, this));
		
		for (buildingBlock block : buildingBlocks){
			this.getChildren().add(block);
		}
		
		selectedCircle = new Cylinder(75,2);
		selectedCircle.setTranslateY(-2);
		selectedCircle.setMaterial(PM);
		coloring(mat);
		
	}

	@Override
	public void isSelected() {
		System.out.println("base deselecting");
		deSelected();
		System.out.println("base adding selection circle");
		this.getChildren().add(selectedCircle);
		System.out.println("circle added");

	}

	@Override
	public void deSelected() {
		if (this.getChildren().lastIndexOf(selectedCircle) != -1) {
			this.getChildren().remove(selectedCircle);
		}

	}

	public double getWidth() {
		return 0;
	}

	@Override
	protected void setHealth(int newHealth) {

		health = newHealth;

	}

	@Override
	public void explode() {
		for (int i = 0; i < this.getChildren().size(); i++) {
			if (!this.getChildren().contains(selectedCircle)) {
				((killable) this.getChildren().get(i)).explode();
			}
		}
	}

	@Override
	public void somethingDied(Object o) {
		for (deathWatcher watcher : deathWatchers) {
			watcher.somethingDied(this);
		}
	}

	@Override
	protected int getHealth() {
		return health;

	}

	@Override
	protected void setDamageRange(int[] newDamageRange) {
		// TODO Auto-generated method stub

	}

	@Override
	protected int[] getDamageRange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected double getBlockChance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void setBlockChance(double newBlockChance) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void move(double mouseOldX, double mouseOldZ, ArrayList<Node> allNodes){
		
	}
}
