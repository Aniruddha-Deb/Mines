package com.sensei.mines.test;

import com.sensei.mines.core.MinefieldGenerator;

public class MinefieldGeneratorTest {

	public static void main( String[] args ){
		int[][] mineField = MinefieldGenerator.generate( 10, 10, 15 );
		
		for( int[] i : mineField ) {
			for( int j : i ) {
				if( j == -1 ) {
					System.out.print( "* " );
				}
				else {
					System.out.print( j + " " );
				}
			}
			System.out.println();
		}
	}
}
