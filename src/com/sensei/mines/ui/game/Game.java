package com.sensei.mines.ui.game;

import java.io.IOException;

import com.sensei.mines.Mines;
import com.sensei.mines.core.MinesPreferences;
import com.sensei.mines.ui.MineGrid;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Stage{
	
	private MineGrid grid = null;
	
	@FXML
	private VBox parent;
	@FXML
	private Label timeLabel;
	@FXML
	private Label minesLeftLabel;
	
	private int numMines = -1;
	
	public Game( Mines application, MinesPreferences prefs ) throws IOException {
		super();
		this.grid = new MineGrid( prefs, application );
		
		this.numMines = prefs.getMines();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setController( this );
		loader.setLocation( Game.class.getResource( "game.fxml" ) );
		AnchorPane ap = (AnchorPane)loader.load();
		
		Scene s = new Scene( ap, prefs.getCols()*30, prefs.getRows()*30+10 );
		super.setTitle( "Mines by Deb" );
		super.setScene( s );
	}
	
	Timeline timer = null;
	
	@FXML
	public void initialize() {
		parent.getChildren().add( grid );
		timeLabel.setText( "0" );
		timer = new Timeline( new KeyFrame ( Duration.seconds(1), (e) -> {
			int i = Integer.parseInt( timeLabel.getText() );
			timeLabel.setText( (i+1)+"" );		  
		} ) );
		timer.setCycleCount(Timeline.INDEFINITE);
		timer.play();
	}
	
	@Override
	public void close(){
		timer.stop();
		super.close();
	}
}
