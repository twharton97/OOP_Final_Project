package Game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Point3D;
import static javafx.application.Application.launch;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import Robots.*;

public class World extends Application implements deathWatcher {

	final Group root = new Group();
	final Xform axisGroup = new Xform();
	final XformWorld world = new XformWorld();
	final PerspectiveCamera camera = new PerspectiveCamera(true);
	final XformCamera cameraXform = new XformCamera();
	private ArrayList<Node> allNodes;
	private ArrayList<Robot> robots;
	private static final double CAMERA_INITIAL_DISTANCE = -2000;
	private static final double CAMERA_INITIAL_X_ANGLE = -30.0;
	private static final double CAMERA_INITIAL_Y_ANGLE = 0.0;
	private static final double CAMERA_INITIAL_Z_ANGLE = 0.0;
	private static final double CAMERA_NEAR_CLIP = 0.1;
	private static final double CAMERA_FAR_CLIP = 10000.0;
	private double mousePosX, mousePosZ, mouseOldX, mouseOldZ, mousePosBuffer, cameraY, cameraX, cameraZ;
	private Point3D cameraFocus;
	private Box floor, selectionLine1, selectionLine2, selectionLine3, selectionLine4;
	private PhongMaterial green, red, blue;
	private ArrayList<CommandListener> commandListeners;
	private Random rand = new Random();
	private BorderPane pane;

	@Override
	public void start(Stage primaryStage) throws Exception{
		allNodes = new ArrayList<Node>();
		root.getChildren().add(world);
		root.setDepthTest(DepthTest.ENABLE);
		buildCamera();
		buildFloor();
	    SubScene subScene = new SubScene(root, 1820, 980, true, SceneAntialiasing.BALANCED);
		
		    Button buttonMakeT1 = new Button("Make Team 1 Bot");
		    buttonMakeT1.setOnAction(e->{
		       makeRobot(red, 1);
		    });
		    Button buttonMakeT2 = new Button("Make Team 2 Bot");
		    buttonMakeT2.setOnAction(e->{
		    	makeRobot(blue, 2);
		    });
		    pane = new BorderPane();
		    pane.setCenter(subScene);
		    CheckBox checkBox = new CheckBox("Line");
		    checkBox.setOnAction(e->{
		        
		    });
		    ToolBar toolBar = new ToolBar(buttonMakeT1, buttonMakeT2, checkBox);
		    toolBar.setOrientation(Orientation.HORIZONTAL);
		    pane.setBottom(toolBar);
		    pane.setPrefSize(300,300);
		    
		    
	        
	        
	        
	        

		green = new PhongMaterial();
		green.setDiffuseColor(Color.GREEN);
		green.setSpecularColor(Color.RED);
		red = new PhongMaterial();
		red.setDiffuseColor(Color.RED);
		red.setSpecularColor(Color.RED);
		blue = new PhongMaterial();
		blue.setDiffuseColor(Color.BLUE);
		blue.setSpecularColor(Color.RED);
		
		
		
		
		
		
		robots = new ArrayList<Robot>();
		commandListeners = new ArrayList<CommandListener>();

		
		Scene scene = new Scene(pane);
		scene.setFill(Color.GREY);
		primaryStage.setTitle("Robotic Combat");
		primaryStage.setScene(scene);
		primaryStage.show();
		subScene.setCamera(camera);
		
		
		
		world.getChildren().addAll(allNodes);
		handleInput(scene);
		
		
	}
	
	public void makeRobot(PhongMaterial mat, int team){
		robots.add(new Robot(mat, team, this));
		robots.get(robots.size()-1).setRotateY(rand.nextInt(361));
		robots.get(robots.size()-1).setTranslateX((rand.nextInt(501)*team)-500);
		robots.get(robots.size()-1).setTranslateZ((rand.nextInt(501)*team)-500);
		allNodes.add(robots.get(robots.size()-1));
		pane.getChildren().removeAll(allNodes);
		pane.getChildren().addAll(allNodes);
		world.getChildren().removeAll(allNodes);
		world.getChildren().addAll(allNodes);
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void buildCamera() {
		root.getChildren().add(cameraXform);
		cameraXform.getChildren().add(camera);
		camera.setNearClip(CAMERA_NEAR_CLIP);
		camera.setFarClip(CAMERA_FAR_CLIP);
		cameraZ = CAMERA_INITIAL_DISTANCE;
		camera.setTranslateZ(cameraZ);
		cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
		cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
		cameraXform.rz.setAngle(CAMERA_INITIAL_Z_ANGLE);
		changeCameraFocus();
	}

	private void changeCameraFocus() {
		cameraFocus = new Point3D(cameraXform.getTranslateX(), cameraXform.getTranslateY(),
				cameraXform.getTranslateZ());
		cameraXform.setRotationAxis(cameraFocus);
	}

	private void buildFloor() {
		floor = new Box(10000, 1, 10000);
		world.getChildren().addAll(floor);
	}

	private void handleInput(Scene scene) {

		for (Robot robot : robots) {
			robot.setOnMousePressed((MouseEvent me) -> {
				if (me.isShiftDown() && me.isPrimaryButtonDown()) {
					if (!commandListeners.contains(robot)) {
						commandListeners.add(robot);
						for (CommandListener listener : commandListeners) {
							listener.isSelected();
						}
						System.out.println("robot added");
					}
				} else if (me.isPrimaryButtonDown()) {
					for (CommandListener listener : commandListeners) {
						listener.deSelected();
					}
					commandListeners.clear();
					System.out.println("robots cleareed");
					commandListeners.add(robot);
					for (CommandListener listener : commandListeners) {
						listener.isSelected();
					}
					System.out.println("robot added");
				}
			});
		}
		

		floor.setOnMousePressed((MouseEvent me) -> {
			for (Robot robot : robots) {
				System.out.println("I am a robot");
			}
			
			for (Node robot : allNodes) {
				System.out.println("I am a robot(Node)");
			}
			

			mouseOldX = me.getX();
			mouseOldZ = me.getZ();
			createSelectionLines();
			if (me.isPrimaryButtonDown()) {
				for (CommandListener listener : commandListeners) {
					listener.deSelected();
				}
				commandListeners.clear();
			}
			if (me.isSecondaryButtonDown()) {
				for (CommandListener listener : commandListeners) {
					listener.move(mouseOldX, mouseOldZ, allNodes);
				}
			}

		});
		floor.setOnMouseReleased((MouseEvent me) -> {
			world.getChildren().removeAll(selectionLine1, selectionLine2, selectionLine3, selectionLine4);
			mousePosX = me.getX();
			mousePosZ = me.getZ();
			if (mousePosX < mouseOldX) {
				mousePosBuffer = mousePosX;
				mousePosX = mouseOldX;
				mouseOldX = mousePosBuffer;
			}
			if (mousePosZ < mouseOldZ) {
				mousePosBuffer = mousePosZ;
				mousePosZ = mouseOldZ;
				mouseOldZ = mousePosBuffer;
			}

			for (Robot robot : robots) {
				if (robot.getTranslateX() < mousePosX && robot.getTranslateX() > mouseOldX) {
					if (robot.getTranslateZ() < mousePosZ && robot.getTranslateZ() > mouseOldZ) {
						commandListeners.add(robot);
						for (CommandListener listener : commandListeners) {
							listener.isSelected();
						}
					}
				}
			}

		});

		floor.setOnMouseDragged((MouseEvent me) -> {
			if (me.isPrimaryButtonDown()) {
				mousePosX = me.getX();
				mousePosZ = me.getZ();
				updateSelectionLines();
			}
		});

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				changeCameraFocus();
				switch (ke.getCode()) {

				case W:
					cameraZ += 25;
					camera.setTranslateZ(cameraZ);
					break;

				case A:
					cameraX -= 25;
					cameraXform.setTranslateX(cameraX);
					break;

				case S:
					cameraZ -= 25;
					camera.setTranslateZ(cameraZ);

					break;

				case D:
					cameraX += 25;
					cameraXform.setTranslateX(cameraX);
					break;

				case Q:
					cameraXform.ry.setAngle(cameraXform.ry.getAngle() + 5);
					break;

				case E:
					cameraXform.ry.setAngle(cameraXform.ry.getAngle() - 5);
					break;

				case R:
					cameraY -= 25;
					camera.setTranslateY(cameraY);
					break;

				case F:
					cameraY += 25;
					camera.setTranslateY(cameraY);
					break;

				case Z:
					cameraXform.rx.setAngle(cameraXform.rx.getAngle() + 5);
					break;

				case X:
					cameraXform.rx.setAngle(cameraXform.rx.getAngle() - 5);
					break;

				}
			}
		});
	}

	private void updateSelectionLines() {
		selectionLine1.setTranslateX((mouseOldX + mousePosX) / 2);
		selectionLine2.setTranslateX(mouseOldX);
		selectionLine3.setTranslateX(mousePosX);
		selectionLine4.setTranslateX((mousePosX - (mousePosX - mouseOldX) / 2));
		selectionLine1.setTranslateZ(mouseOldZ);
		selectionLine2.setTranslateZ((mouseOldZ + mousePosZ) / 2);
		selectionLine3.setTranslateZ((mousePosZ - (mousePosZ - mouseOldZ) / 2));
		selectionLine4.setTranslateZ(mousePosZ);

		if (mousePosX < mouseOldX) {
			selectionLine1.setWidth(mouseOldX - mousePosX);
			selectionLine4.setWidth(mouseOldX - mousePosX);
		} else {
			selectionLine1.setWidth(mousePosX - mouseOldX);
			selectionLine4.setWidth(mousePosX - mouseOldX);
		}
		if (mousePosZ < mouseOldZ) {
			selectionLine2.setDepth(mouseOldZ - mousePosZ);
			selectionLine3.setDepth(mouseOldZ - mousePosZ);
		} else {
			selectionLine2.setDepth(mousePosZ - mouseOldZ);
			selectionLine3.setDepth(mousePosZ - mouseOldZ);
		}
		System.out.println(mousePosZ);
		System.out.println(mouseOldZ);
	}

	private void createSelectionLines() {

		selectionLine1 = new Box(3, 3, 3);
		selectionLine1.setTranslateY(-4);
		selectionLine1.setTranslateX(mouseOldX);
		selectionLine1.setTranslateZ(mouseOldZ);
		selectionLine1.setMaterial(green);

		selectionLine2 = new Box(3, 3, 3);
		selectionLine1.setTranslateY(-4);
		selectionLine1.setTranslateX(mouseOldX);
		selectionLine1.setTranslateZ(mouseOldZ);
		selectionLine2.setMaterial(green);

		selectionLine3 = new Box(3, 3, 3);
		selectionLine1.setTranslateY(-4);
		selectionLine1.setTranslateX(mouseOldX);
		selectionLine1.setTranslateZ(mouseOldZ);
		selectionLine3.setMaterial(green);

		selectionLine4 = new Box(3, 3, 3);
		selectionLine1.setTranslateY(-4);
		selectionLine1.setTranslateX(mouseOldX);
		selectionLine1.setTranslateZ(mouseOldZ);
		selectionLine4.setMaterial(green);

		world.getChildren().addAll(selectionLine1, selectionLine2, selectionLine3, selectionLine4);
	}
	
	public void somethingDied(Object o){
		System.out.println("I'm removing object");
		allNodes.remove(o);
		world.getChildren().remove(o);
		commandListeners.remove(o);
		robots.remove(o);
		
	}

}
