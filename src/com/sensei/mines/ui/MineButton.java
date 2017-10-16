package com.sensei.mines.ui;

import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;

public class MineButton extends ToggleButton {

	private boolean mine = false;
	private boolean flag = false;
	private int row = -1;
	private int col = -1;
	private int value = -1;
	
	public MineButton( int row, int col ) {
		super( "" );
		super.setStyle( "-fx-background-radius: 0px;" );
		super.setMaxSize( Double.MAX_VALUE, Double.MAX_VALUE );
		this.row = row;
		this.value = 0;
		this.col = col;
	}
	
	public int getValue() {
		return value;		
	}
	
	public void showValue() {
		if( value != 0 )  
		super.setText( value+"" );
	}
	
	public void toggleFlag() {
		flag = !flag;
		if( flag ) {
			super.setText( "X" );
			super.setTextFill( Color.RED );
		}
		else {
			super.setText( "" );
			super.setTextFill( Color.BLACK );
		}
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
