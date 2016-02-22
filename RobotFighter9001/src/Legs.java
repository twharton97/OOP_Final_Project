
public class Legs extends Component {
	int[] waist;
	int[] leftThigh;
	int[] rightThigh;
	int[] leftKnee;
	int[] rightKnee;
	int[] leftShin;
	int[] rightShin;
	int[] leftFoot;
	int[] rightFoot;
	int[][] dimensions;
	public Legs() {
		System.out.println("making legs");
		waist = new int[]{100,1670,50,10};
		leftThigh = new int[]{100,1680,22,60};
		rightThigh = new int[]{150,1680,22,60};
		leftKnee = new int[]{100,1740,17,10};
		rightKnee = new int[]{150,1740,17,10};
		leftShin = new int[]{100,1750,15,55};
		rightShin = new int[]{150,1750,15,55};
		leftFoot = new int[]{100,1805,30,15};
		rightFoot = new int[]{150,1805,30,15};
		dimensions = new int[][]{{100,830,72,10},{100,840,22,60},{150,840,22,60},{100,900,17,10},{150,900,17,10},{100,910,15,55},{150,910,15,55},{100,965,30,15},{150,965,30,15}};
		System.out.println("legs made");
	}
}
