package Robots;



import java.util.Random;

public enum Direction {
	NORTH, EAST, SOUTH, WEST, NORTHEAST, SOUTHEAST, NORTHWEST, SOUTHWEST;

	/**
	 * @returns int[2] with (dY,dX)
	 */
	public static int[] getRandomVelocity(int movementMultiplier) {
		Random rand = new Random();
		int direction = rand.nextInt(8);
		int[] velocity = new int[2];// (y,x)

		switch (direction) {
		case (0):
			velocity[0] = (-1 * movementMultiplier);
			break;
		case (1):
			velocity[1] = (1 * movementMultiplier);
			break;
		case (2):
			velocity[0] = (1 * movementMultiplier);
			break;
		case (3):
			velocity[1] = (-1 * movementMultiplier);
			break;
		case (4):
			velocity[0] = (-1 * movementMultiplier);
			velocity[1] = (1 * movementMultiplier);
			break;
		case (5):
			velocity[0] = (1 * movementMultiplier);
			velocity[1] = (1 * movementMultiplier);
			break;
		case (6):
			velocity[0] = (1 * movementMultiplier);
			velocity[1] = (-1 * movementMultiplier);
			break;
		case (7):
			velocity[0] = (-1 * movementMultiplier);
			velocity[1] = (-1 * movementMultiplier);
			break;
		default:
			break;
		}

		return velocity;
	}
}
