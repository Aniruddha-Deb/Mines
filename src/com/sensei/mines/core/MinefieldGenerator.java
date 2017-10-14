package com.sensei.mines.core;

public class MinefieldGenerator {

	public static int[][] generate( int width, int height, int numMines ) {
		int[][] mineField = new int[height][width];
		for( int i=0; i<numMines; i++ ) {
			boolean put = false;
			while( !put ) {
				int xLoc = (int)(Math.random()*width);
				int yLoc = (int)(Math.random()*height);
				if( mineField[yLoc][xLoc] != -1 ) {
					put = true;
					mineField[yLoc][xLoc] = -1;
				}
			}
		}
		
		for( int i=0; i<height; i++ ) {
			for( int j=0; j<width; j++ ) {
				if( mineField[i][j] == -1 ) {
					padAround( i, j, mineField );
				}
			}
		}
		
		return mineField;
	}
	
	private static void padAround( int i, int j, int[][] mineField ) {
		for( int y=i-1; y<=i+1; y++ ) {
			for( int x=j-1; x<=j+1; x++ ) {
				if( (y>=0 && y<mineField.length) && (x>=0 && x<mineField[0].length ) ) {
					if( !(y == i && x == j) && mineField[y][x] != -1 ) {
						mineField[y][x]++;
					}
				}
			}
		}		
	}
}
