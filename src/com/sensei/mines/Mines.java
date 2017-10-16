package com.sensei.mines;

import java.io.IOException;

import com.sensei.mines.core.MinesPreferences;
import com.sensei.mines.ui.endgame.EndGame;
import com.sensei.mines.ui.game.Game;
import com.sensei.mines.ui.newgame.NewGame;

import javafx.application.Application;
import javafx.stage.Stage;

public class Mines extends Application {
	
	private Stage stage = null;
	
	public void startGame( MinesPreferences prefs ) {
		try {
			Game g = new Game( this, prefs );
			g.show();
		} catch( IOException ex ) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void stop() throws Exception {
		stage.close();
		super.stop();
	};
	
	@Override
	public void start( Stage primaryStage ) throws Exception{		
		this.stage = primaryStage;
		showNewGameMenu();
	}
	
	public void showNewGameMenu() {
		try {
			NewGame ng = new NewGame( this );
			ng.show();
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
	}
	
	public Stage getStage(){
		return stage;
	}
	
	public void showEndGameMenu( boolean won ) {
		try {
			EndGame eg = new EndGame( this, won );
			eg.show();
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
	}
	
	public static void debugMines( int[][] mines ) {
		for( int[] i : mines ) {
			for( int j : i ) {
				if( j == -1 ) {
					System.out.print( "* " );
				}
				else {
					System.out.print( j+" " );
				}
			}
				
			System.out.println(  );
		}
	}
	
	public static void main( String[] args ){
		launch( args );
	}
}
