
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class gameController {
	
	private ImageView[] allImages = new ImageView[10];
	private int mistakeCounter = 0;
	private int lifeRemaining = 10;
	private String checkInput = "";
	private boolean gameFinished = false;
	private hangmanGame newGame;
	private ArrayList<String> loadFile = fileHandler.getLoadFile();
	@FXML
	private Label guessWord;

	@FXML
	private Label loadInfoLabel;

	@FXML
	private Label difficultyLabel;

	@FXML
	private Label lifeLabel;

	@FXML
	private TextField userInput;

    @FXML
    private Button playAgainBtn;

    @FXML
    private Label statusLabel;
    
    @FXML
    private Label gameLabel;
	@FXML
	private Label a;
	@FXML
	private Label b;
	@FXML
	private Label c;
	@FXML
	private Label d;
	@FXML
	private Label e;
	@FXML
	private Label f;
	@FXML
	private Label g;
	@FXML
	private Label h;
	@FXML
	private Label i;
	@FXML
	private Label j;
	@FXML
	private Label l;
	@FXML
	private Label m;
	@FXML
	private Label n;
	@FXML
	private Label o;
	@FXML
	private Label p;
	@FXML
	private Label q;
	@FXML
	private Label r;
	@FXML
	private Label s;
	@FXML
	private Label t;
	@FXML
	private Label u;
	@FXML
	private Label v;
	@FXML
	private Label w;
	@FXML
	private Label x;
	@FXML
	private Label y;
	@FXML
	private Label z;

	@FXML
	private ImageView hangmanImage1;
	@FXML
	private ImageView hangmanImage2;
	@FXML
	private ImageView hangmanImage3;
	@FXML
	private ImageView hangmanImage4; 
	@FXML
	private ImageView hangmanImage5;
	@FXML
	private ImageView hangmanImage6;
	@FXML
	private ImageView hangmanImage7;
	@FXML
	private ImageView hangmanImage8;
	@FXML
	private ImageView hangmanImage9;
	@FXML
	private ImageView hangmanImage10;

	//initials some code for the start of the game
	@FXML
	void initialize() {
		//assign all ImageView into an array
		allImages[0] = hangmanImage1;
		allImages[1] = hangmanImage2;
		allImages[2] = hangmanImage3;
		allImages[3] = hangmanImage4;
		allImages[4] = hangmanImage5;
		allImages[5] = hangmanImage6;
		allImages[6] = hangmanImage7;
		allImages[7] = hangmanImage8;
		allImages[8] = hangmanImage9;
		allImages[9] = hangmanImage10;
		//if the loadFile arrayList is empty so Load button has not been clicked
		if(hangmanGame.isGameLoaded()==true){
			System.out.println(loadFile);
			String loadWord = loadFile.get(0);
			String loadInput = loadFile.get(1);
			int life = Integer.valueOf(loadFile.get(2));
			mistakeCounter = life;
			int loadLevel = loadWord.length();
			newGame = new hangmanGame(loadLevel, loadWord);	
			setDifficulty(loadLevel);
			loadGame(loadWord, loadInput);
			loadInfoLabel.setVisible(true);
	    	allImages[mistakeCounter].setVisible(true);
	    	lifeRemaining = 10-life;
	    	lifeLabel.setText("Life: "+lifeRemaining);
			
			//alert box
	    	Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Information Dialog");
	    	alert.setHeaderText("Load Game Dialog");
	    	alert.setContentText("You have loaded the last saved game, but you have to click on the letters again.");
	    	alert.showAndWait();
		}else{
			int currentLevel = hangmanGame.getLevel();
			setDifficulty(currentLevel);
			newGame = new hangmanGame(currentLevel, "");
			newGame.selectWord(currentLevel);	//sets the word to correspond with the level
		}
		System.out.println("Current Word: " + newGame.getGuessWord()); // prints guess word for testing purposes
		

	}

	//Event for when the user clicks a letter
	@FXML
	void onLetterClicked(MouseEvent event) throws IOException{
		Label letterLabel = (Label)event.getSource();
		char inputChar = letterLabel.getId().charAt(0);
		//if game is finished
		if(gameFinished==false){
			if(newGame.guessChar(inputChar)){
				guessWord.setText(newGame.getMissingWord());
				letterLabel.setTextFill(Color.LAWNGREEN);
				letterLabel.setDisable(true);	//make it so user can not click on the button again
				checkInput+=inputChar;		//adds the inputChar to a String
				if(newGame.checkDuplicates()){
					checkInput+=inputChar;		//adds the inputChar to a String
				}
				if(newGame.checkMatch(checkInput, newGame.getGuessWord())){	//when the checkInput matches correctWord
					//reveals gameOver label
					gameLabel.setVisible(true);
					gameLabel.setTextFill(Color.LAWNGREEN);
					//reveals description
					statusLabel.setText("You have won! The correct word is: " +newGame.getGuessWord());
					statusLabel.setVisible(true);
					//reveals play again button
					playAgainBtn.setVisible(true);
					//set game is finished
					gameFinished = true;
				}
			}else{	//if it does not occur set imageView to visible
				allImages[mistakeCounter].setVisible(true);		//displays the next image
				mistakeCounter++; 	//used for allImages index
				lifeRemaining--;	//used for when the game is over and lifeLabel
				lifeLabel.setText("Life: " + lifeRemaining);
				letterLabel.setTextFill(Color.RED);
				letterLabel.setDisable(true);
				if(lifeRemaining==0){
					System.out.println("You have lost");
					//reveals gameOver label
					gameLabel.setVisible(true);
					gameLabel.setTextFill(Color.RED);
					//reveals description
					statusLabel.setText("You have lost, correct word is: " +newGame.getGuessWord());
					statusLabel.setVisible(true);
					//reveals play again button
					playAgainBtn.setVisible(true);
					//set game is finished
					gameFinished = true;
				}
			}
		}else{
			Alert alert = new Alert(AlertType.WARNING);
	    	alert.setTitle("Warning Dialog");
	    	alert.setHeaderText("Look, a warning dialog");
	    	alert.setContentText("The game is over!");
	    	alert.showAndWait();
		}
	}
	
    @FXML
    void onClickPlayAgain(ActionEvent event) throws IOException {
    	hangmanGame.setGameLoaded(false);
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText("Play Again Confirmation Dialog");
    	alert.setContentText("Press ok if you want to play again on the same difficulty.");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		loadFile.clear();	//clears the loadFile
			Parent levelView = FXMLLoader.load(getClass().getResource("gameView.fxml"));
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
    void onClickNewGame(ActionEvent event) throws IOException {
    	hangmanGame.setGameLoaded(false);
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText("New Game Confirmation Dialog");
    	alert.setContentText("Are you sure you want to start a new game?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		loadFile.clear();	//clears the loadFile
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
    void onSaveClick(ActionEvent event) {
    	FileChooser save = new FileChooser();
    	save.setTitle("Save Game");
    	File path = save.showSaveDialog(null);
    	String fileName = path.getPath();
    	fileHandler.saveGame(newGame.getGuessWord(), checkInput, mistakeCounter, fileName);
    	hangmanGame.setGameLoaded(false);
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Information Dialog");
    	alert.setHeaderText("Save Dialog");
    	alert.setContentText("Your game has been saved");
    	alert.showAndWait();

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
    	hangmanGame.setGameLoaded(false);
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText("Back Confirmation Dialog");
    	alert.setContentText("Are you sure you want to go back?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
			Parent levelView = FXMLLoader.load(getClass().getResource("levelView.fxml"));
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
    	alert.setContentText("Save your game in a memorable place so you can load it later.");
    	alert.showAndWait();
    }
    

    public void setDifficulty(int level){
		switch(level){
		case 5: difficultyLabel.setText("Difficulty: Easy");	
			guessWord.setText("_ _ _ _ _");
			break;
		case 6: difficultyLabel.setText("Difficulty: Moderate");
			guessWord.setText("_ _ _ _ _ _");
			break;
		case 7: difficultyLabel.setText("Difficulty: Hard");	
			guessWord.setText("_ _ _ _ _ _ _");
			break;
		case 8: difficultyLabel.setText("Difficulty: Expert");	
			guessWord.setText("_ _ _ _ _ _ _ _");	
			break;	
		default: break;
		}
    }

    public void loadGame(String word, String input){
    	char[] inputChar = input.toCharArray();
    	for(int i=0; i<inputChar.length; i++){
    		if(newGame.guessChar(inputChar[i])){
    			guessWord.setText(newGame.getMissingWord());
    		}
    	}
    }
    
}
