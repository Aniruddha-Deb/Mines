package com.sensei.mines;

import java.io.IOException;

import com.sensei.mines.core.MinesPreferences;
import com.sensei.mines.ui.endgame.EndGame;
import com.sensei.mines.ui.game.Game;
import com.sensei.mines.ui.newgame.NewGame;

import javafx.application.Application;
import javafx.stage.Stage;

public class Mines extends Application {
	
	private Game instance = null;
	
	public void startGame( MinesPreferences prefs ) {
		try {
			instance = new Game( this, prefs );
			instance.show();
		} catch( IOException ex ) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void stop() throws Exception {
		instance.close();
		super.stop();
	};
	
	@Override
	public void start( Stage primaryStage ) throws Exception{		
		showNewGameMenu();
	}
	
	public Stage getGameInstance() {
		return instance;
	}
	
	public void showNewGameMenu() {
		try {
			if( instance != null ) 
				instance.close();
			NewGame ng = new NewGame( this );
			ng.show();
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
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
