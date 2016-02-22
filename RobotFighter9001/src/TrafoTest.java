import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import static javafx.application.Application.launch;
import java.util.ArrayList;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class TrafoTest extends Application {

    final Group root = new Group();
    final Xform axisGroup = new Xform();
    final XformWorld world = new XformWorld();
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final XformCamera cameraXform = new XformCamera();
    final Xform shapeGroup = new Xform();
    private static final double CAMERA_INITIAL_DISTANCE = -2000;		
    private static final double CAMERA_INITIAL_X_ANGLE = 270.0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 270.0;
    private static final double CAMERA_INITIAL_Z_ANGLE = 0.0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    private static final double AXIS_LENGTH = 255.0;
    private double mousePosX, mousePosY, mouseOldX, mouseOldY, mouseDeltaX, mouseDeltaY, cameraY, cameraX, cameraZ, cameraXRotation;
    private  Point3D cameraFocus;
    private double shapeSpeed = 5;
//    private ArrayList<Node> selectedShapes;
    
    
    
    @Override
    public void start(Stage primaryStage) {
        root.getChildren().add(world);
        root.setDepthTest(DepthTest.ENABLE);
        buildCamera();
        buildBodySystem();
        buildAxes();
        Scene scene = new Scene(root, 1820, 980, true);
        scene.setFill(Color.GREY);
        handleInput(scene);
        primaryStage.setTitle("Transformationen");
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setCamera(camera);
//        selectedShapes = new ArrayList<Node>();
    }
    
    public static void main(String[] args){
    	launch(args);
    }


	private void buildCamera() {
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(camera);
        cameraXform.setRotate(270);
        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
//        camera.setTranslateY(-200);
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
        cameraXform.rz.setAngle(CAMERA_INITIAL_Z_ANGLE);
        changeCameraFocus();
    }
	
	 private void changeCameraFocus() {
		cameraFocus = new Point3D(cameraXform.getLayoutX(), cameraXform.getLayoutY(), 1);
		cameraXform.setRotationAxis(cameraFocus);
//	    cameraXform.ry.setPivotX(cameraFocus.getX());
//	    cameraXform.ry.setPivotY(cameraFocus.getY());
//	    cameraXform.ry.setPivotZ(cameraFocus.getZ());
//	    cameraXform.rx.setPivotX(cameraFocus.getX());
//	    cameraXform.rx.setPivotY(cameraFocus.getY());
//	    cameraXform.rx.setPivotZ(cameraFocus.getZ());
//	    cameraXform.rz.setPivotX(cameraFocus.getX());
//	    cameraXform.rz.setPivotY(cameraFocus.getY());
//	    cameraXform.rz.setPivotZ(cameraFocus.getZ());
//	    rotationCenter = point;
	  }
	
	private void buildAxes() {
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
 
        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);
 
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);
 
        final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
        final Box yAxis = new Box(1, AXIS_LENGTH, 1);
        final Box zAxis = new Box(1, 1, AXIS_LENGTH);
        final Box floor = new Box(1000,1000,1);
//        selectedShapes.add(floor);
        floor.setTranslateZ(-10);
        
        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);
 
        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis, floor);
        axisGroup.setVisible(true);
        world.getChildren().addAll(axisGroup);
    }

    private void buildBodySystem() {
        PhongMaterial whiteMaterial = new PhongMaterial();
        whiteMaterial.setDiffuseColor(Color.WHITE);
        whiteMaterial.setSpecularColor(Color.LIGHTBLUE);
        Box box = new Box(40, 20, 10);
        box.setMaterial(whiteMaterial);
        PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
        Sphere sphere = new Sphere(5);
        sphere.setMaterial(redMaterial);
        sphere.setTranslateZ(-50.0);
        Sphere sphere2 = new Sphere(20);
        sphere2.setMaterial(whiteMaterial);;
        sphere2.setTranslateZ(60);
        shapeGroup.getChildren().addAll(box, sphere, sphere2);
        shapeGroup.setVisible(true);
        world.getChildren().addAll(shapeGroup);
    }

    private void handleInput(Scene scene) {
        scene.setOnMousePressed((MouseEvent me) -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
            if (me.isSecondaryButtonDown()){
            	System.out.println("test right click");
            }
            // this is done after clicking and the rotations are apearently
            // performed in coordinates that are NOT rotated with the camera.
            // (pls activate the two lines below for clicking)
            //cameraXform.rx.setAngle(-90.0);
            //cameraXform.ry.setAngle(180.0);
        });
        scene.setOnMouseDragged((MouseEvent me) -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);
            if (me.isPrimaryButtonDown()) {
                // this is done when the mouse is dragged and each rotation is
                // performed in coordinates, that are rotated with the camera.            
//            	cameraXform.
                cameraXform.ry.setAngle(cameraXform.ry.getAngle() + mouseDeltaX * 0.2);
                cameraXform.rx.setAngle(cameraXform.rx.getAngle() - mouseDeltaY * 0.2);                
            }
        });
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

    		@Override
    		public void handle(KeyEvent ke) {
    			changeCameraFocus();
    			switch (ke.getCode()){
    			
    			case W: 
    			cameraY += 25;
    			cameraXform.setLayoutY(cameraXform.getLayoutY() + cameraY);
    				break;
    			
    			case A: 
    				cameraX -= 25;
    				cameraXform.setLayoutX(cameraXform.getLayoutX() + cameraX);
    				break;
    			
    			case S: 
    				cameraY -= 25;
    				cameraXform.setLayoutY(cameraXform.getLayoutY() + cameraY);
    				break;
    			
    			case D: 
    				cameraX += 25;
    				cameraXform.setLayoutX(cameraXform.getLayoutX() + cameraX);
    				break;
    			
    			case Q:

//    				cameraXRotation -= 5;
//    				cameraXform.setRotate(cameraXRotation);
//    				cameraXform.ry.setAngle(cameraXform.ry.getAngle() +  5);
    				cameraXform.setRotate(cameraXform.getRotate()-5);
    				break;
    			
    			case E:
//    				cameraXRotation += 5;
//    				cameraXform.setRotate(cameraXRotation);
//    				cameraXform.ry.setAngle(cameraXform.ry.getAngle() - 5);
    				cameraXform.setRotate(cameraXform.getRotate()+5);
    				
    				break;
    				
    			case R:
    				cameraXform.rx.setAngle(cameraXform.rx.getAngle() + 5);   
    				break;
    				
    			case F:
    				cameraXform.rx.setAngle(cameraXform.rx.getAngle() - 5); 
    				break;
    				
    			case Z:
    				cameraZ += 10;
    				cameraXform.t.setZ(cameraZ + camera.getLayoutBounds().getMinZ());
    				break;
    				
    			case X:
    				cameraZ -= 10;
    				cameraXform.t.setZ(cameraZ + camera.getLayoutBounds().getMaxZ());
    				break;
    				
    			case SPACE:
//    				shapeGroup.setLayoutX(shapeGroup.getLayoutX()+shapeSpeed);
    				shapeGroup.setLayoutY(shapeGroup.getLayoutY()+shapeSpeed);
    				break;
    				
    			case M:
    				System.out.println("changed camera focus");
//    				cameraFocus = shapeGroup.getLayout();
    				changeCameraFocus();
    				break;
    			    			
    			}
    		}
          });
    }

    

}

class XformWorld extends Group {

    final Translate t = new Translate(0.0, 0.0, 0.0);
    final Rotate rx = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
    final Rotate ry = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
    final Rotate rz = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);

    public XformWorld() {
        super();
        this.getTransforms().addAll(t, rx, ry, rz);
    }

}

class XformCamera extends Group {

     Translate t = new Translate(0.0, 0.0, 0.0);
     Rotate rx = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
     Rotate ry = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
     Rotate rz = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);

    public XformCamera() {
        super();
        this.getTransforms().addAll(t, rx, ry, rz);
    }

}