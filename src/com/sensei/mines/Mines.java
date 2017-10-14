package com.sensei.mines;

import com.sensei.mines.core.MinefieldGenerator;
import com.sensei.mines.ui.Minefield;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Mines extends Application{

	@Override
	public void start( Stage primaryStage ) throws Exception{
		
		int[][] mines = MinefieldGenerator.generate( 10, 10, 10 );
		
		Scene s = new Scene( new Minefield( mines, 10 ), 400, 400 );
		primaryStage.setTitle( "Mines by Deb" );
		primaryStage.setScene( s );
		primaryStage.show();
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
