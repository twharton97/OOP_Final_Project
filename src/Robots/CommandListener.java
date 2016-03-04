package Robots;

import java.util.ArrayList;

import javafx.scene.Node;

public interface CommandListener {

	public void move(double mouseOldX, double mouseOldZ, ArrayList<Node> allNodes);
	
	public void isSelected();
	
	public void deSelected();
}
