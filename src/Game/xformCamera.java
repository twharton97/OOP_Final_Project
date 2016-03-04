package Game;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

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