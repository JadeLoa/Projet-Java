package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Vue.fxml"));
			// StackPane root = new StackPane();
			Scene scene = new Scene(root, 900, 600, true);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/* @formatter:off
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
	}@formatter:on*/

	public static void main(String[] args) {
		launch(args);
	}
}
