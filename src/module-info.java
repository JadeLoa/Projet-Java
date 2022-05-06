module ProjetJava {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires com.google.gson;

	opens application to javafx.graphics, javafx.fxml;
}
