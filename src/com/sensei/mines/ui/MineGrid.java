package com.sensei.mines.ui;

import java.util.ArrayList;
import java.util.List;

import com.sensei.mines.core.MinesPreferences;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class MineGrid extends GridPane implements EventHandler<ActionEvent>{
	
	private MineButton[][] mineButtons = null; 
	private List<MineClickListener> listeners = null;
	
	private int numRows = -1;
	private int numCols = -1;
	
	public MineGrid( MinesPreferences prefs ) {
		super();
		super.setMaxSize( Double.MAX_VALUE, Double.MAX_VALUE );
		VBox.setVgrow( this, Priority.ALWAYS );
		listeners = new ArrayList<>();
		this.numRows = prefs.getRows();
		this.numCols = prefs.getCols();
		initConstraints( prefs.getRows(), prefs.getCols() );
		populateButtons( prefs.getRows(), prefs.getCols() );
	}
	
	public MineButton[][] getMineButtons() {
		return mineButtons;
	}
	
	public void openAroundButton( int row, int col ) {
		
		MineButton b = mineButtons[row][col];
		if( !b.isDisabled() ) { 
			b.setSelected( true );
			b.setDisable( true );
			b.showValue();
			if( b.getValue() == 0 ) {
				int y = row, x = col;
				
				int[][] cells = { {y-1, x-1}, {y-1, x}, {y-1, x+1},
								  {y, x-1}  ,           {y, x+1},
								  {y+1, x-1}, {y+1, x}, {y+1, x+1} };
				
				for( int[] c : cells ) {
					if( ((c[0]>=0)&&(c[0]<numRows)) && ((c[1]>=0)&&(c[1]<numCols)) ) { 
						openAroundButton( c[0], c[1] );
					}
				}
			}
		}
	}
	
	public void addMineClickListener( MineClickListener l ) {
		listeners.add( l );
	}
	
	public void populateButtons( int rows, int cols ) {
		mineButtons = new MineButton[rows][cols];
		for( int i=0; i<rows; i++ ) {
			for( int j=0; j<cols; j++ ) {
				mineButtons[i][j] = new MineButton( i, j );
				super.add( mineButtons[i][j], j, i );
				GridPane.setHgrow( mineButtons[i][j], Priority.ALWAYS );
				GridPane.setVgrow( mineButtons[i][j], Priority.ALWAYS );
				mineButtons[i][j].setOnAction( this );
			}
		}
	}
	
	private void initConstraints( int rows, int cols ) {
		double colPercentWidth = (double)(100/cols);
		double rowPercentWidth = (double)(100/rows);
		
		for( int i=0; i<cols; i++ ) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setPercentWidth( colPercentWidth );
			super.getColumnConstraints().add( cc );
		}
		
		for( int i=0; i<rows; i++ ) {
			RowConstraints cc = new RowConstraints();
			cc.setPercentHeight( rowPercentWidth );
			super.getRowConstraints().add( cc );			
		}
	}

	@Override
	public void handle( ActionEvent event ){
		MineButton b = (MineButton)event.getSource();
		for( MineClickListener l : listeners ) {
			l.onClick( b );
		}
	}	
}
