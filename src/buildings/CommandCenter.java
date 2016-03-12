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
		damageRange[0] = 0;
		damageRange[1] = 0;
		selectedCircle = new Cylinder(75,2);
		selectedCircle.setTranslateY(-2);
		selectedCircle.setMaterial(PM);
		coloring(mat);
		
		width = 75;
		
		currentHealth = 1000;
		maxHealth = 1000;
		healthBarGreen.setWidth(maxHealth/3);
		healthBarRed.setWidth(maxHealth/3);
		healthBarRed.setTranslateY(-100);
		healthBarGreen.setTranslateY(-100);
		this.getChildren().add(healthBarRed);
		this.getChildren().add(healthBarGreen);
		originalHealthBarWidth = healthBarGreen.getWidth();
		healthBarGreen.setTranslateX(0);
	}

	@Override
	public void isSelected() {
		deSelected();
		this.getChildren().add(selectedCircle);

	}

	@Override
	public void deSelected() {
		if (this.getChildren().lastIndexOf(selectedCircle) != -1) {
			this.getChildren().remove(selectedCircle);
		}

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
	protected double getHealth() {
		return currentHealth;

	}

	@Override
	public void setDamageRange(int[] newDamageRange) {
		// TODO Auto-generated method stub

	}

	@Override
	protected int[] getDamageRange() {
		// TODO Auto-generated method stub
		return damageRange;
	}

	@Override
	public double getBlockChance() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public void setBlockChance(double newBlockChance) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void move(double mouseOldX, double mouseOldZ, ArrayList<Actor> allActors){
		
	}
}
