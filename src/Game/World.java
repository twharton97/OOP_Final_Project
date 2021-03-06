package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Point3D;
import static javafx.application.Application.launch;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import buildings.CommandCenter;
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
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.InputEvent;
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
import robots.*;

public class World extends Application implements deathWatcher {

	final Group root = new Group();
	final Xform axisGroup = new Xform();
	final XformWorld world = new XformWorld();
	final PerspectiveCamera camera = new PerspectiveCamera(true);
	final XformCamera cameraXform = new XformCamera();
	private ArrayList<Actor> allActors;
	private ArrayList<Robot> robots;
	private ArrayList<Actor> buildings;
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
	private Stage primaryStage;
	private SubScene subScene;

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("hi");
		allActors = new ArrayList<Actor>();
		root.getChildren().add(world);
		root.setDepthTest(DepthTest.ENABLE);
		buildCamera();
		buildFloor();
		subScene = new SubScene(root, 1920, 940, true, SceneAntialiasing.BALANCED);
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
		buildings = new ArrayList<Actor>();

		primaryStage.setTitle("Robotic Combat");
		primaryStage.setScene(sceneBuilder(null));
		primaryStage.getIcons().add(new Image("file:src/resources/robotIcon.png"));
		primaryStage.show();
		primaryStage.centerOnScreen();
		subScene.setCamera(camera);

		// makeCommandCenter(red, 1);

		world.getChildren().addAll(allActors);

		makeCommandCenter(red, 1);
		makeCommandCenter(blue, 2);
		this.primaryStage = primaryStage;
		
	}

	public Scene sceneBuilder(Actor a) {
		paneBuilder(a);
		System.out.println("test1");
			System.out.println("tester false");
			Scene scene = new Scene(pane);
			System.out.println("test2");
			scene.setFill(Color.GREY);
			System.out.println("test3");
			handleInput(scene);
			System.out.println("test4");
			return scene;

	}

	private BorderPane paneBuilder(Actor a) {
		pane = new BorderPane();
		pane.setCenter(subScene);
		if (a != null) {
			Button buttonMakeT1 = new Button("Make Normal Bot");
			buttonMakeT1.setOnAction(e -> {
				makeRobot(red, 1);
			});
			Button buttonMakeT2 = new Button("Make Normal Bot");
			buttonMakeT2.setOnAction(e -> {
				makeRobot(blue, 2);
			});
			if (a.getTeam() == 1) {
				ToolBar toolBar = new ToolBar(buttonMakeT1);
				toolBar.setOrientation(Orientation.HORIZONTAL);
				pane.setBottom(toolBar);
				pane.setPrefSize(300, 300);

			} else if (a.getTeam() == 2) {
				System.out.println("inside team 2");
				ToolBar toolBar = new ToolBar(buttonMakeT2);
				toolBar.setOrientation(Orientation.HORIZONTAL);
				pane.setBottom(toolBar);
				pane.setPrefSize(300, 300);

			}
		}
		return pane;
	}

	private class myCustomEventHanlder implements EventHandler<InputEvent> {
		public void handle(InputEvent event) {
			System.out.println("Handling event " + event.getEventType());
			event.consume();
		}
	}

	public void makeCommandCenter(PhongMaterial mat, int team) {

		buildings.add(new CommandCenter(mat, team, this));
		if (team == 1) {
			buildings.get(buildings.size() - 1).setTranslateX(800);
			
		} else {
			buildings.get(buildings.size() - 1).setTranslateX(-800);
			
		}
		allActors.add(buildings.get(buildings.size() - 1));

		pane.getChildren().removeAll(allActors);

		pane.getChildren().addAll(allActors);

		world.getChildren().removeAll(allActors);

		world.getChildren().addAll(allActors);

	}

	public void makeRobot(PhongMaterial mat, int team) {
		robots.add(new Robot(mat, team, this));
		for(Actor building : buildings){
			if(building.getTeam() == robots.get(robots.size() - 1).getTeam()){
				if(building.getTeam()!=2){
					robots.get(robots.size() - 1).setTranslateX(building.getTranslateX() + (rand.nextInt(350)+70)*-1);	
					robots.get(robots.size()-1).setBlockChance(95);
					robots.get(robots.size()-1).setDamageRange(new int[]{5,10});
				}else{
					robots.get(robots.size() - 1).setTranslateX(building.getTranslateX() + rand.nextInt(350)+70);
				}
				robots.get(robots.size() - 1).setTranslateZ(building.getTranslateZ() + rand.nextInt(350)-175);
			}
		}
		
		robots.get(robots.size() - 1).setRotateY(0);
		allActors.add(robots.get(robots.size() - 1));
		pane.getChildren().removeAll(allActors);
		pane.getChildren().addAll(allActors);
		world.getChildren().removeAll(allActors);
		world.getChildren().addAll(allActors);

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

		floor.setOnMouseExited((MouseEvent me) -> {
			handleInput(scene);
			System.out.println("scene updated");
		});

		for (Actor building : buildings) {
			building.setOnMousePressed((MouseEvent me) -> {
				if (me.isShiftDown() && me.isPrimaryButtonDown()) {

				} else if (me.isPrimaryButtonDown()) {
					for (CommandListener listener : commandListeners) {
						listener.deSelected();
					}
					commandListeners.clear();
					System.out.println("listeners cleared");
					commandListeners.add(building);
					for (CommandListener listener : commandListeners) {
						listener.isSelected();
					}
					primaryStage.setScene(sceneBuilder(building));
				}
			});
		}

		for (Robot robot : robots) {
			robot.setOnMousePressed((MouseEvent me) -> {
				primaryStage.setScene(sceneBuilder(null));
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
				} else if (me.isSecondaryButtonDown()) {
					if (!commandListeners.isEmpty()) {
						for (CommandListener listener : commandListeners) {
							listener.move(robot.getTranslateX(), robot.getTranslateZ(), allActors);
						}
					}
				}
			});
		}

		floor.setOnMousePressed((MouseEvent me) -> {
			
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
					listener.move(mouseOldX, mouseOldZ, allActors);
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
			primaryStage.setScene(sceneBuilder(null));
			handleInput(primaryStage.getScene());
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

	public void somethingDied(Object o) {

		allActors.remove(o);
		world.getChildren().remove(o);
		if (commandListeners.contains(o)) {
			commandListeners.remove(o);
		}
		if (robots.contains(o)) {
			robots.remove(o);
		}

	}

}
