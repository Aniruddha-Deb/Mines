package com.sensei.mines.ui;

import javafx.scene.control.ToggleButton;

public class MineButton extends ToggleButton {

	private boolean mine = false;
	private boolean flag = false;
	private int row = -1;
	private int col = -1;
	private int value = -1;
	
	public MineButton( int row, int col ) {
		super( "" );
		super.setMaxSize( Double.MAX_VALUE, Double.MAX_VALUE );
		this.row = row;
		this.col = col;
	}
	
	public int getValue() {
		return value;		
	}
	
	public void showValue() {
		if( value != 0 )  
		super.setText( value+"" );
	}
	
	public void flag() {
		flag = true; 
	}
	
	public void unflag() {
		flag = false;
	}
	
	public boolean getFlag() {
		return flag;
	}
		
	public void setValue( int value ) {
		this.value = value;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	public void setMine( boolean mine ) {
		this.mine = mine;
	}
	
	public boolean hasMine() {
		return mine;
	}
}
