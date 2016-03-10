package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;

public class exampleListener implements EventHandler<InputEvent>{

	
	@Override
	public void handle(InputEvent event) {
		System.out.println("TEST");
		
	}

}
