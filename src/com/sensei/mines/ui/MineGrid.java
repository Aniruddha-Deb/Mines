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
	private int numMinesRevealed = 0;
	
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
		if( !b.isSelected() && !b.getFlag() ) { 
			b.setSelected( true );
			b.setDisable( true );
			numMinesRevealed++;
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
		for( int i=0; i<prefs.getRows(); i++ ) {
			for( int j=0; j<prefs.getCols(); j++ ) {
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
		
		if( numMinesFlagged == prefs.getMines() && 
				numMinesRevealed+numMinesFlagged == ( prefs.getRows()*prefs.getCols() ) ) {
			application.showEndGameMenu( true );
		}
	}
	
	private void revealBasedOnRules( MineButton button ) {
		MineButton[] buttons = getAdjacentMineButtons( button );
		int numAdj = button.getValue();
		int numFlagged = 0;
		for( MineButton b : buttons ) {
			if( b.getFlag() ) {
				numFlagged++;
			}
		}
				
		if( numFlagged == numAdj ) {
			for( MineButton b : buttons ) {
				onLeftClick( b );
			}
		}
	}
	
	private MineButton[] getAdjacentMineButtons( MineButton b ) {
		int row = b.getRow();
		int col = b.getCol();
		List<MineButton> buttons = new ArrayList<MineButton>();
		for( int i=row-1; i<=row+1; i++ ) {
			for( int j=col-1; j<=col+1; j++ ) {
				if( (!((i==row)&&(j==col))) && inBounds(i, j) ) {
					buttons.add( mineButtons[i][j] );
				}
			}
		}
		
		return buttons.toArray( new MineButton[buttons.size()]);
	}
	
	private boolean inBounds( int y, int x ) {
		return ((y>=0)&&(y<prefs.getRows())) && ((x>=0)&&(x<prefs.getCols()));
	}
 
	@Override
	public void handle( MouseEvent event ){
		event.consume();
		MineButton b = (MineButton)event.getSource();
		if( event.isPrimaryButtonDown() && event.isSecondaryButtonDown() ) {
			revealBasedOnRules( b );
		}
		else if( event.isPrimaryButtonDown() ) {
			onLeftClick( b );
		}
		else if( event.isSecondaryButtonDown() ) {
			onRightClick( b );
		}
	}	
}
