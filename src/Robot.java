
public class Robot {
	Chasis frame;
	Head head;
	Arm leftArm;
	Arm rightArm;
	Torso torso;
	Legs legs;
	Weapon leftWeapon;
	Weapon rightWeapon;
	int[][] legDimensions;
	
	public Robot(){
		
		Chasis frame = new Chasis();
		Head head = new Head();
		Arm leftArm = new Arm();
		Arm rightArm = new Arm();
		Torso torso = new Torso();
		Legs legs = new Legs();
		Weapon leftWeapon = new Weapon();
		Weapon rightWeapon = new Weapon();
		legDimensions = legs.dimensions;
	}
}
