import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class levelController {

	@FXML
	private ToggleGroup difficulty;

	@FXML
	private RadioButton level1;

	@FXML
	private RadioButton level2;

	@FXML
	private RadioButton level3;

	@FXML
	private RadioButton level4;

	@FXML
	void onStartClick(ActionEvent event) throws Exception {

		if(level1.isSelected()){
			hangmanGame.setLevel(5);
		}else if(level2.isSelected()){
			hangmanGame.setLevel(6);
		}else if(level3.isSelected()){
			hangmanGame.setLevel(7);
		}else if(level4.isSelected()){
			hangmanGame.setLevel(8);
		}
		Parent gameView = FXMLLoader.load(getClass().getResource("gameView.fxml"));
		Scene gameScene = new Scene(gameView);
		Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(gameScene);
		primaryStage.show();
	}
   
	@FXML
    void onCloseClick(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText("Close Confirmation Dialog");
    	alert.setContentText("Are you sure you want to close the game?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		File file = new File("serialise.bin");
        	file.delete();
        	   	Platform.exit();
    	} else {
    	    alert.close();
    	}
    }
    
    @FXML
    void onBackClick(ActionEvent event) throws IOException {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText("Back Confirmation Dialog");
    	alert.setContentText("Are you sure you want to go back?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
			Parent levelView = FXMLLoader.load(getClass().getResource("indexView.fxml"));
			Scene levelScene = new Scene(levelView);
			Stage secondaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			secondaryStage.setResizable(false);
			secondaryStage.setScene(levelScene);
			secondaryStage.show();
    	} else {
    	    alert.close();
    	}
    }
	
    @FXML
	void onLoadClick(ActionEvent event) {
    	//File chooser pop up
    	FileChooser load = new FileChooser();
    	load.setTitle("Choose a saved file of the game");
    	File file = load.showOpenDialog(null);
    	String path = file.getPath();
    	String extention = path.substring(path.lastIndexOf("."),path.length());
    	if(extention.equals(".data")){    	
    		try{
    			//Read file and set variable
    			fileHandler.loadGame(path);
    			hangmanGame.setGameLoaded(true);
    		
    			Parent gameView = FXMLLoader.load(getClass().getResource("gameView.fxml"));
    			Scene gameScene = new Scene(gameView);
    			Stage secondaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    			secondaryStage.setResizable(false);
    			secondaryStage.setScene(gameScene);
    			secondaryStage.show();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}else{
    		Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error Dialog");
        	alert.setHeaderText("Load Dialog");
        	alert.setContentText("The wrong file was selected");
        	alert.showAndWait();
    	}
	}
   
   
    @FXML
    void onAboutClick(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Information Dialog");
    	alert.setHeaderText("Created by Khoa Phan");
    	alert.setContentText("Each level varies by the length of the word . Word of length 5 being easiest and 8 the hardest.");
    	alert.showAndWait();
    }
}
