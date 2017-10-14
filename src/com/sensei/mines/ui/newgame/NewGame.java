package com.sensei.mines.ui.newgame;

import java.io.IOException;

import com.sensei.mines.Mines;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewGame extends Stage {
	
	@FXML
	public TextField numCols;
	@FXML
	public TextField numRows;
	@FXML
	public TextField numMines;
	
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
		super.setScene( s );
	}
	
	@FXML
	public void initialize() {
		numCols.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	                numCols.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });
		numRows.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	                numRows.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });
		numMines.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	                numMines.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });
	}

	@FXML
	public void onStartButtonClicked() {
		try {
			int cols = Integer.parseInt( numCols.getText() );
			int rows = Integer.parseInt( numRows.getText() );
			int mines = Integer.parseInt( numMines.getText() );
			
			if( mines > (rows*cols)/2 ) {
				numMines.setText( "" );
				throw new Exception( "too many mines" );
			}
			
			application.startGame( rows, cols, mines );
			this.hide();
		} catch( Exception ex ) {
			// swalla;
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
