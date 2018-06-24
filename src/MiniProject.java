
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MiniProject extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("indexView.fxml"));
		primaryStage.setTitle("Hangman Game");
		primaryStage.setScene(new Scene(root));
		primaryStage.setHeight(625);
		primaryStage.setWidth(905);
		primaryStage.setResizable(false);
		primaryStage.show();

	}
	public static void main(String[] args) {
		launch(args);
	}
}
