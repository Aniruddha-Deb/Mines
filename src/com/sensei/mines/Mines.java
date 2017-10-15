package com.sensei.mines;

import com.sensei.mines.core.MinefieldGenerator;
import com.sensei.mines.ui.Minefield;
import com.sensei.mines.ui.endgame.EndGame;
import com.sensei.mines.ui.newgame.NewGame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Mines extends Application {
	
	private Stage stage = null;
	
	public void startGame( int numRows, int numCols, int numMines ) {
		int[][] mines = MinefieldGenerator.generate( numCols, numRows, numMines );
		Scene s = new Scene( new Minefield( this, mines, numMines ), numCols*40, numRows*35 );
		stage.setScene( s );
		stage.setTitle( "Mines by Deb" );
		stage.show();
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
