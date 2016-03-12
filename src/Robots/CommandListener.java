package robots;

import java.util.ArrayList;

import javafx.scene.Node;

public interface CommandListener {

	public void move(double mouseOldX, double mouseOldZ, ArrayList<Actor> allActors);
	
	public void isSelected();
	
	public void deSelected();
}
