package com.sensei.mines.ui.game;

import java.io.IOException;

import com.sensei.mines.Mines;
import com.sensei.mines.core.MinefieldGenerator;
import com.sensei.mines.core.MinesPreferences;
import com.sensei.mines.ui.MineButton;
import com.sensei.mines.ui.MineClickListener;
import com.sensei.mines.ui.MineGrid;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game extends Stage implements MineClickListener{
	
	private Mines application = null;
	private MineGrid grid = null;
	private MinesPreferences prefs = null;
	
	@FXML
	private VBox parent = null;
	
	public Game( Mines application, MinesPreferences prefs ) throws IOException {
		super();
		this.application = application;
		this.prefs = prefs;
		this.grid = new MineGrid( prefs );
		grid.addMineClickListener( this );
		
		FXMLLoader loader = new FXMLLoader();
		loader.setController( this );
		loader.setLocation( Game.class.getResource( "game.fxml" ) );
		AnchorPane ap = (AnchorPane)loader.load();
		
		Scene s = new Scene( ap );
		super.setTitle( "Mines by Deb" );
		super.setScene( s );
	}

	@FXML
	public void initialize() {
		parent.getChildren().add( grid );
	}
	
	private boolean firstClick = true;
	
	private void doFirstClickSetup( MineButton b ) {
		int[][] mines = MinefieldGenerator.generate( prefs, b.getRow(), b.getCol() );
		MineButton[][] mButtons = grid.getMineButtons();
		for( int i=0; i<prefs.getRows(); i++ ) {
			for( int j=0; j<prefs.getCols(); j++ ) {
				if( mines[i][j] == -1 ) {
					mButtons[i][j].setMine( true );
				}
				else {
					mButtons[i][j].setValue( mines[i][j] );
				}
			}
		}
	}

	@Override
	public void onClick( MineButton b ){
		if( firstClick ) {
			doFirstClickSetup( b );
			firstClick = false;
		}
		b.showValue();
		grid.openAroundButton( b.getRow(), b.getCol() );
		if( b.hasMine() ) {
			application.showEndGameMenu( false );
		}
	}
	
}
