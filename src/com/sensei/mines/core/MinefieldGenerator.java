package com.sensei.mines.core;

import com.sensei.mines.ui.MineButton;

public class MinefieldGenerator {

	public static void generate( MinesPreferences prefs, int y, 
			 int x, MineButton[][] buttons ) {
		
		int height = prefs.getRows();
		int width = prefs.getCols();
		int numMines = prefs.getMines();
		
		for( int i=0; i<numMines; i++ ) {
			boolean put = false;
			while( !put ) {
				int xLoc = (int)(Math.random()*width);
				int yLoc = (int)(Math.random()*height);
				int yDiff = Math.abs( yLoc-y );
				int xDiff = Math.abs( xLoc-x );
				boolean putable = (yDiff > 0) && (xDiff > 0);
						
				if( !(buttons[yLoc][xLoc].hasMine()) && putable ) {
					put = true;					
					buttons[yLoc][xLoc].setMine( true );
				}
			}
		}
		
		for( int i=0; i<height; i++ ) {
			for( int j=0; j<width; j++ ) {
				if( buttons[i][j].hasMine() ) {
					padAround( i, j, buttons );
				}
			}
		}		
	}
	
	private static void padAround( int i, int j, MineButton[][] mineField ) {
		for( int y=i-1; y<=i+1; y++ ) {
			for( int x=j-1; x<=j+1; x++ ) {
				if( (y>=0 && y<mineField.length) && (x>=0 && x<mineField[0].length ) ) {
					if( !(y == i && x == j) && !( mineField[y][x].isArmed() ) ) {
						mineField[y][x].setValue( mineField[y][x].getValue()+1 );
					}
				}
			}
		}		
	}
}
