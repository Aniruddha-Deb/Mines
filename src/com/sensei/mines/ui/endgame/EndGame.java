package com.sensei.mines.ui.endgame;

import java.io.IOException;

import com.sensei.mines.Mines;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EndGame extends Stage {
	
	private Mines application = null;
	private boolean won = false;
	
	@FXML
	public Label wonLabel;
	
	public EndGame( Mines application, boolean won ) throws IOException {
		super();
		this.won = won;
		this.application = application;
		FXMLLoader loader = new FXMLLoader();
		loader.setController( this );
		loader.setLocation( EndGame.class.getResource( "end_game.fxml" ) );
		AnchorPane ap = (AnchorPane)loader.load();
		
		Scene s = new Scene( ap );
		super.setTitle( "End Game" );
		super.initModality( Modality.APPLICATION_MODAL );
		super.initOwner( application.getStage() );
		super.setScene( s );
	}
	
	@FXML 
	public void initialize() {
		if( won ) {
			wonLabel.setText( "You Won!" );
		}
		else {
			wonLabel.setText( "You Lost." );
		}
	}
	
	@FXML
	public void onStartButtonClicked() {
		application.showNewGameMenu();
		this.hide();
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
