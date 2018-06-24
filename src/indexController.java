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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class indexController {

	@FXML
	private Label time1;

	@FXML
	private Label time2;

	@FXML
	private Label error;

	@FXML
	private RadioButton mode2;

	@FXML
	private RadioButton mode1;

	@FXML
	void onSubmitClick(ActionEvent event) {
		if (mode1.isSelected()) {
			fileHandler.sequential(); // runs the sequential code if mode1 is selected
			time1.setText("Sequential time taken to choose words: " + fileHandler.getSequentiallyTime()+" milliseconds");	// displays time taken
			time1.setDisable(false);
		}else if (mode2.isSelected()) {
			fileHandler.parallel(); // runs the parallel code if mode2 is selected
			time2.setText("Parallel time taken to choose words: " + fileHandler.getParallelTime() +" milliseconds");	// displays time taken
			time2.setDisable(false);
		}
	}
	@FXML
	void onLevelClick(ActionEvent event) throws IOException {
	if(!(time1.isDisable() || time2.isDisable()) ){
			Parent levelView = FXMLLoader.load(getClass().getResource("levelView.fxml"));
			Scene levelScene = new Scene(levelView);
			Stage secondaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			secondaryStage.setResizable(false);
			secondaryStage.setScene(levelScene);
			secondaryStage.show();
		}else{
	    	Alert alert = new Alert(AlertType.WARNING);
	    	alert.setTitle("Warning Dialog");
	    	alert.setHeaderText("Mode Warning Dialog");
	    	alert.setContentText("Please compare the time of both the modes of choosing words.");
	    	alert.showAndWait();
		}
	}
    @FXML
    void onCloseClick(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText("Close Dialog");
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
    	alert.setContentText("Sequential mode is choosing words from each file one at a time. Parallel mode is choosing words from each file simultaneously.");
    	alert.showAndWait();
    }
}
