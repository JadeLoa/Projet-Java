package application;

import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// AnchorPane root = (AnchorPane)
			// FXMLLoader.load(getClass().getResource("Sample.fxml"));
			StackPane root = new StackPane();
			Scene scene = new Scene(root, 900, 600, true);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

			PhongMaterial redMaterial = new PhongMaterial();
			redMaterial.setDiffuseColor(Color.DARKRED);
			redMaterial.setSpecularColor(Color.RED);

			Piece pieceRacine = new Piece(1, 2, 5, Color.RED);
			Piece pieceF1 = new Piece(1, 4, 2, Color.RED);

			pieceRacine.connecteursM[0][1] = new Connexion(pieceF1, new Position(0, 0));

			for (Box box1 : pieceRacine.generate3DBoxes()) {

				box1.setMaterial(redMaterial);
				root.getChildren().add(box1);
				System.out.println(box1.getDepth() + "," + box1.getHeight() + "," + box1.getWidth());
			}
			PerspectiveCamera camera = new PerspectiveCamera();
			camera.setTranslateX(0);
			camera.setTranslateY(90);
			camera.setTranslateZ(150);
			scene.setCamera(camera);

			// box2.translateXProperty().set(box2.translateXProperty().get()-25);
			initMouseControl(scene, camera, primaryStage);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private double anchorX, anchorY;

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

	public static void main(String[] args) {
		launch(args);
	}
}
