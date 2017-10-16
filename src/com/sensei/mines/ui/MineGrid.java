package com.sensei.mines.ui;

import java.util.ArrayList;
import java.util.List;

import com.sensei.mines.Mines;
import com.sensei.mines.core.MinefieldGenerator;
import com.sensei.mines.core.MinesPreferences;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class MineGrid extends GridPane implements EventHandler<MouseEvent>{
	
	private MineButton[][] mineButtons = null; 
	private List<MineClickListener> listeners = null;
	private MinesPreferences prefs = null;
	
	private int numMinesFlagged = 0;
	
	private Mines application = null; 
	
	public MineGrid( MinesPreferences prefs, Mines application ) {		
		super();
		this.prefs = prefs;
		this.application = application;
		super.setMaxSize( Double.MAX_VALUE, Double.MAX_VALUE );
		VBox.setVgrow( this, Priority.ALWAYS );
		listeners = new ArrayList<>();
		initConstraints();
		populateButtons();
	}
	
	public MineButton[][] getMineButtons() {
		return mineButtons;
	}
	
	public void openAroundButton( int row, int col ) {
		
		MineButton b = mineButtons[row][col];
		if( !b.isDisabled() && !b.getFlag() ) { 
			b.setSelected( true );
			b.setDisable( true );
			b.showValue();
			if( b.getValue() == 0 ) {
				int y = row, x = col;
				
				int[][] cells = { {y-1, x-1}, {y-1, x}, {y-1, x+1},
								  {y, x-1}  ,           {y, x+1},
								  {y+1, x-1}, {y+1, x}, {y+1, x+1} };
				
				for( int[] c : cells ) {
					if( ((c[0]>=0)&&(c[0]<prefs.getRows())) && ((c[1]>=0)&&(c[1]<prefs.getCols())) ) {
						openAroundButton( c[0], c[1] );
					}
				}
			}
		}
	}
	
	public void addMineClickListener( MineClickListener l ) {
		listeners.add( l );
	}
	
	public void populateButtons() {
		mineButtons = new MineButton[prefs.getRows()][prefs.getCols()];
		for( int i=0; i<prefs.getCols(); i++ ) {
			for( int j=0; j<prefs.getRows(); j++ ) {
				mineButtons[i][j] = new MineButton( i, j );
				super.add( mineButtons[i][j], j, i );
				GridPane.setHgrow( mineButtons[i][j], Priority.ALWAYS );
				GridPane.setVgrow( mineButtons[i][j], Priority.ALWAYS );
				mineButtons[i][j].addEventHandler( MouseEvent.MOUSE_PRESSED, 
						this );
			}
		}
	}
	
	private void initConstraints() {
		double colPercentWidth = (double)(100/prefs.getCols());
		double rowPercentWidth = (double)(100/prefs.getRows());
		
		for( int i=0; i<prefs.getCols(); i++ ) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setPercentWidth( colPercentWidth );
			super.getColumnConstraints().add( cc );
		}
		
		for( int i=0; i<prefs.getRows(); i++ ) {
			RowConstraints cc = new RowConstraints();
			cc.setPercentHeight( rowPercentWidth );
			super.getRowConstraints().add( cc );			
		}
	}
	
	private void doFirstClickSetup( MineButton b ) {
		MinefieldGenerator.generate( prefs, b.getRow(), b.getCol(), mineButtons );
		System.out.println( "Updated buttons" );
	}

	private boolean firstClick = true;
	
	private void onLeftClick( MineButton b ) {
		if( firstClick ) {
			doFirstClickSetup( b );
			firstClick = false;
		}
		
		if( !b.getFlag() ) {		
			openAroundButton( b.getRow(), b.getCol() );
			
			if( b.hasMine() ) {
				application.showEndGameMenu( false );
			}
		}
	}
	
	private void onRightClick( MineButton b ) {
		if( b.hasMine() && b.getFlag() ) {
			numMinesFlagged--;
		}
		else if( b.hasMine() && !( b.getFlag() ) ) {
			numMinesFlagged++;
		}
		b.toggleFlag();
		
		if( numMinesFlagged == prefs.getMines() ) {
			application.showEndGameMenu( true );
		}
	}
 
	@Override
	public void handle( MouseEvent event ){
		MineButton b = (MineButton)event.getSource();	
		// TODO both right and left click implementation
		if( event.isPrimaryButtonDown() ) {
			onLeftClick( b );
		}
		else if( event.isSecondaryButtonDown() ) {
			onRightClick( b );
		}
	}	
}
