package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.transform.Rotate;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Sample.fxml"));
			StackPane root=new StackPane();
			Scene scene = new Scene(root, 900, 600, true);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Piece pieceRacine, pieceF1, pieceF2;
			
//          pieceRacine = new Piece(1, 8, 2, Color.RED);
//          pieceF1 = new Piece(1, 3, 2, Color.GREEN);
//          pieceF2 = new Piece(1, 1, 10, Color.BLUE);
//          pieceRacine.connecteursM[1][7] = new Connexion(pieceF1, new Position(0, 0));
//          pieceRacine.connecteursM[1][0] = new Connexion(pieceF2, new Position(0, 0));

//          pieceRacine = new Piece(1, 8, 4, Color.YELLOW);
//          pieceF1 = new Piece(1, 3, 5, Color.GREEN);
//          pieceRacine.connecteursM[1][6] = new Connexion(pieceF1, new Position(4, 1));


//          pieceRacine = new Piece(1, 8, 5, Color.YELLOW);
//          pieceF1 = new Piece(1, 4, 5, Color.GREEN);
//          pieceRacine.connecteursM[1][3] = new Connexion(pieceF1, new Position(0, 0));

			pieceRacine = generer_demo();
            
            pieceRacine.generate3DBoxes().forEach(box1 -> {
                root.getChildren().add(box1);
            });
            PerspectiveCamera camera = new PerspectiveCamera();
            camera.setTranslateX(90);
            camera.setTranslateY(90);
            camera.setTranslateZ(-500);
            scene.setCamera(camera);

            // box2.translateXProperty().set(box2.translateXProperty().get()-25);
            initMouseControl(camera, scene, primaryStage);

        } catch (Exception e) {
        }

    }

/*	private double anchorX, anchorY;

	private void initMouseControl(Scene scene, Camera camera, Stage stage) {

		scene.setOnMousePressed(event -> {
			// Save start points
			anchorX = event.getSceneX();
			anchorY = event.getSceneY();
		});

		scene.setOnMouseDragged(event -> {
			camera.setTranslateY(anchorY - event.getSceneY());
			camera.setTranslateX(anchorX - event.getSceneX());
		});

		stage.addEventHandler(ScrollEvent.SCROLL, event -> {
			// box.setTranslateZ(box.getTranslateZ() + event.getDeltaY());
		});
	}
*/
	
	
	
	//Tracks drag starting point for x and y
    private double anchorX, anchorY;
//Keep track of current angle for x and y
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
//We will update these after drag. Using JavaFX property to bind with object
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    private void initMouseControl(Camera camera, Scene scene, Stage stage) {
        Rotate xRotate;
        Rotate yRotate;
        camera.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });
        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            camera.setTranslateY(camera.getTranslateY() + event.getDeltaY());
        });

    }
    
            
public static Piece generer_demo(){
        Piece pieceRacine,pieceF;
            pieceRacine = new Piece(1, 30, 30, Color.YELLOW);
            pieceF = new Piece(1, 30, 2, Color.GREEN);
            pieceRacine.connecteursM[0][0] = new Connexion(pieceF, new Position(0, 0));        
            pieceF = new Piece(1, 30, 2, Color.GREEN);
            pieceRacine.connecteursM[28][0] = new Connexion(pieceF, new Position(0, 0));        
            pieceF = new Piece(1, 2, 26, Color.RED);
            pieceRacine.connecteursM[2][0] = new Connexion(pieceF, new Position(0, 0));      
            pieceF = new Piece(1, 2, 26, Color.RED);
            pieceRacine.connecteursM[2][28] = new Connexion(pieceF, new Position(0, 0));      
            pieceF = new Piece(1, 10, 10, Color.PURPLE);
            pieceRacine.connecteursM[11][11] = new Connexion(pieceF, new Position(0, 0));              
        return pieceRacine;
        
    }

	
	
	public static void main(String[] args) {
		launch(args);
	}
}
