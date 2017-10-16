package com.sensei.mines.ui.newgame;

import java.io.IOException;

import com.sensei.mines.Mines;
import com.sensei.mines.core.MinesPreferences;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewGame extends Stage {
	
	private enum SettingsType {
		BEGINNER, INTERMEDIATE, EXPERT, CUSTOM
	}
	
	@FXML
	public TextField numCols;
	@FXML
	public TextField numRows;
	@FXML
	public TextField numMines;
	
	@FXML
	public RadioButton beginnerButton = null;
	@FXML
	public RadioButton intermediateButton = null;
	@FXML
	public RadioButton expertButton = null;
	@FXML
	public RadioButton customButton = null;
	
	private ToggleGroup group = null;
	
	private Mines application = null;
	
	public NewGame( Mines application ) throws IOException {
		super();
		this.application = application;
		FXMLLoader loader = new FXMLLoader();
		loader.setController( this );
		loader.setLocation( NewGame.class.getResource( "new_game.fxml" ) );
		AnchorPane ap = (AnchorPane)loader.load();
		
		Scene s = new Scene( ap );
		super.setTitle( "New Game" );
		if( application.getGameInstance() != null ) {
			this.initModality( Modality.APPLICATION_MODAL );
			this.initOwner( application.getGameInstance() );
		}
		super.setScene( s );
	}
	
	@FXML
	public void initialize() {
		
		ChangeListener<String> c = new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	                numCols.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    };
		
		numCols.textProperty().addListener( c );
		numRows.textProperty().addListener( c );
		numMines.textProperty().addListener( c );
		
		group = new ToggleGroup();
		beginnerButton.setToggleGroup( group );
		beginnerButton.setUserData( SettingsType.BEGINNER );
		intermediateButton.setToggleGroup( group );
		intermediateButton.setUserData( SettingsType.INTERMEDIATE );
		expertButton.setToggleGroup( group );
		expertButton.setUserData( SettingsType.EXPERT );
		customButton.setToggleGroup( group );
		customButton.setUserData( SettingsType.CUSTOM );
		
		group.selectToggle( beginnerButton );
	}

	@FXML
	public void onStartButtonClicked() {
		
		Toggle t = group.getSelectedToggle();
		SettingsType settings = (SettingsType)t.getUserData();
		switch( settings ) {
			case BEGINNER:
				application.startGame( MinesPreferences.BEGINNER );
				this.hide();		
			break;
			
			case INTERMEDIATE:
				application.startGame( MinesPreferences.INTERMEDIATE );
				this.hide();		
			break;
			
			case EXPERT:
				application.startGame( MinesPreferences.EXPERT );
				this.hide();		
			break;
			
			case CUSTOM:
				startCustomGame();
			break;			
		}
		
	}
	
	private void showAlert( String alertText ) {
		Alert alert = new Alert( AlertType.WARNING );
		alert.setTitle( "Warning" );
		alert.setHeaderText( null );
		alert.setContentText( alertText );

		alert.showAndWait();
	}
	
	private void startCustomGame() {
		try {
			int cols = Integer.parseInt( numCols.getText() );
			int rows = Integer.parseInt( numRows.getText() );
			int mines = Integer.parseInt( numMines.getText() );
			
			int mul = (rows*cols)/2;
			int mll = (rows*cols)/11;
			if( mines > mul || mines < mll ) {
				numMines.setText( "" );
				throw new IllegalArgumentException( "mines need to be in the "
						+ "range of " + mll + " to " + mul );
			}

			application.startGame( new MinesPreferences( rows, cols, mines ) );
			this.hide();		
		} catch( NumberFormatException ex ) {
			showAlert( "Enter a suitable value in the custom fields" );
			
		} catch( IllegalArgumentException ex ) {
			showAlert( ex.getMessage() );
		}				
		
	}
	
	@FXML
	public void onExitButtonClicked() {
		try {
			this.hide();
			application.stop();
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
	}
}
