import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class DemoJavaFX3D1 extends Application {
	
	int xPosition = 0;
	int yPosition = 0;
	int zPosition = 0;
	
	public static void main(String[] args) {
	      Application.launch(args);
	   }
	
   @Override
   public void start(Stage primaryStage) {

	  Sphere sphere = new Sphere(100);
	  PhongMaterial sphereMaterial = new PhongMaterial();
	  sphereMaterial.setDiffuseColor(Color.BLUE);
	  sphereMaterial.setSpecularColor(Color.LIGHTBLUE);
	  sphereMaterial.setSpecularPower(10.0);
	  sphere.setMaterial(sphereMaterial);
      Group root=new Group(sphere);
      
      
      
      AmbientLight light=new AmbientLight(Color.AQUA);
      light.setTranslateX(-180);
      light.setTranslateY(-90);
      light.setTranslateZ(-120);
      light.getScope().addAll(sphere);

      PointLight light2=new PointLight(Color.AQUA);
      light2.setTranslateX(180);
      light2.setTranslateY(190);
      light2.setTranslateZ(180);
      light2.getScope().addAll(sphere);
      

      Scene scene = new Scene(root, 1820, 980);
      scene.setFill(Color.BLACK);
      Camera camera = new PerspectiveCamera(true);
      Rotate rz = new Rotate(180.0, Rotate.Z_AXIS);
      camera.getTransforms().add(rz);
      camera.setNearClip(0.1);
      camera.setFarClip(1000.0);
      camera.setTranslateZ(-1000);
      scene.setCamera(camera);
      

      
      root.getChildren().addAll(light,light2);
      
      
      
      sphere.setOnMousePressed(new EventHandler<MouseEvent>(){
    	  @Override
  		public void handle(MouseEvent arg0) {
        	  changeColor(sphereMaterial, camera);
  		}
      });
      
      sphere.setOnMouseReleased(new EventHandler<MouseEvent>(){
    	  @Override
  		public void handle(MouseEvent arg0) {
        	  changeColorBack(sphereMaterial);
  		}
      });
      
      scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

		@Override
		public void handle(KeyEvent ke) {
			switch (ke.getCode()){
			
			case UP: 
			yPosition -= 25;
			camera.setLayoutY(yPosition - camera.getLayoutBounds().getMinX());
			break;
			
			case LEFT: 
				xPosition -= 25;
				camera.setLayoutX(xPosition - camera.getLayoutBounds().getMinX());
			break;
			
			case DOWN: 
				yPosition += 25;
				camera.setLayoutY(yPosition - camera.getLayoutBounds().getMinX());
			break;
			
			case RIGHT: 
				xPosition += 25;
				camera.setLayoutX(xPosition - camera.getLayoutBounds().getMinX());
			break;
			}
		}
      });

      primaryStage.setTitle("Arena");
      primaryStage.setScene(scene);
      primaryStage.show();
      
      

		
   }

private Object changeColor(PhongMaterial sm, Camera camera) {
	sm.setDiffuseColor(Color.GREEN);
	sm.setSpecularColor(Color.LIGHTGREEN);
	sm.setSpecularPower(1);
	return null;
}

private Object changeColorBack(PhongMaterial sm) {
	sm.setDiffuseColor(Color.BLUE);
	sm.setSpecularColor(Color.LIGHTBLUE);
	sm.setSpecularPower(10);
	return null;
}

   

}