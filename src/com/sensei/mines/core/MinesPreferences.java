package com.sensei.mines.core;

public class MinesPreferences {
	
	public static final MinesPreferences BEGINNER = 
							new MinesPreferences( 10, 10, 10 );
	public static final MinesPreferences INTERMEDIATE = 
							new MinesPreferences( 16, 16, 40 );
	public static final MinesPreferences EXPERT = 
							new MinesPreferences( 16, 30, 99 );
	
	
	private int rows, cols, mines;
	
	public MinesPreferences( int rows, int cols, int mines ) {
		this.rows = rows;
		this.cols = cols;
		this.mines = mines;
	}
	
	public int getRows(){
		return rows;
	}

	public int getCols(){
		return cols;
	}
	
	public int getMines(){
		return mines;
	}
}
