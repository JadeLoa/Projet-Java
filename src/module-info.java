module ProjetJava {
	exports application;

	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires com.google.gson;

	opens application to gson, javafx.graphics, javafx.fxml;
}
