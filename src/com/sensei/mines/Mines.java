package com.sensei.mines;

import com.sensei.mines.core.MinefieldGenerator;
import com.sensei.mines.ui.Minefield;
import com.sensei.mines.ui.newgame.NewGame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Mines extends Application {
	
	private Stage stage = null;
	
	public void startGame( int numRows, int numCols, int numMines ) {
		int[][] mines = MinefieldGenerator.generate( numCols, numRows, numMines );
		Scene s = new Scene( new Minefield( mines, numMines ), numCols*40, numRows*35 );
		stage.setScene( s );
		stage.setTitle( "Mines by Deb" );
		stage.show();
	}
	
	@Override
	public void start( Stage primaryStage ) throws Exception{
		
		this.stage = primaryStage;
		
		NewGame ng = new NewGame( this );
		ng.show();
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
