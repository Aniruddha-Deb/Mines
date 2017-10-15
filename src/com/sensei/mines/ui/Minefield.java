package com.sensei.mines.ui;

import com.sensei.mines.Mines;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class Minefield extends AnchorPane {

	private int[][] mines = null;
	private int[][] mineLoc = null;
	private int numMines = -1;
	private GridPane gp = null;
	private Mines app = null;
	
	public Minefield( Mines application, int[][] mines, int numMines ) {
		super();
		this.app = application;
		gp = new GridPane();
		
		for( int i=0; i<mines[0].length; i++ ) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setPercentWidth( (100/mines[0].length) );
			gp.getColumnConstraints().add( cc );
		}
		
		for( int i=0; i<mines.length; i++ ) {
			RowConstraints cc = new RowConstraints();
			cc.setPercentHeight( (100/mines.length) );
			gp.getRowConstraints().add( cc );			
		}
		
		maxCellsUndone = (mines.length * mines[0].length)/5; 
		
		this.mines = mines;
		this.numMines = numMines;
		this.mineLoc = new int[mines.length][mines[0].length];
		int yc = 0;
		
		for( int[] i : mines ) {
			int xc = 0;
			for( int j : i ) {
				Button b = new Button();
				b.setMaxSize( Double.MAX_VALUE, Double.MAX_VALUE );
				GridPane.setHgrow( b, Priority.ALWAYS );
				GridPane.setVgrow( b, Priority.ALWAYS );
				b.addEventHandler( MouseEvent.MOUSE_CLICKED, e -> {
					int y = GridPane.getRowIndex( b );
					int x = GridPane.getColumnIndex( b );
					if( e.getButton().equals( MouseButton.PRIMARY ) ) {
						if( mines[y][x] == -1 ) {
							app.showEndGameMenu( false );
						}
						else {
							b.setText( mines[y][x]+"" );
							if( mines[y][x] == 0 ) {
								uncoverCellsAround( y, x, x, y, true );
								numCellsUndone = 0;
							}
						}
					}
					else {
						if( b.getText().equals( "X" ) ) {
							mineLoc[y][x]--;
							b.setText( "" );
							b.setTextFill( Color.BLACK );
						}
						else {
							mineLoc[y][x]++;
							b.setText( "X" );
							b.setTextFill( Color.RED );
						}
						calculateMines();
					}
				});
				gp.add( b, xc, yc );
				xc++;
			}
			yc++;
		}
		
		AnchorPane.setBottomAnchor( gp, 0d );
		AnchorPane.setTopAnchor( gp, 0d );
		AnchorPane.setLeftAnchor( gp, 0d );
		AnchorPane.setRightAnchor( gp, 0d );
		super.getChildren().add( gp );
    }
	
	private void calculateMines() {
		int msum = 0;
		for( int i=0; i<mines.length; i++ ) {
			for( int j=0; j<mines[0].length; j++ ) {
				if( mines[i][j] == -1 && mineLoc[i][j] == 1 ) {
					msum++;
				}
			}
		}
		
		if( msum == numMines ) {
			app.showEndGameMenu( true );
		}
	}

	int numCellsUndone = 0;
	int maxCellsUndone = -1;
	
	private void uncoverCellsAround( int y, int x, int sx, int sy, boolean first ) {
		if( (y < 0 || y >= mines.length) || (x<0 || x>=mines[0].length) ) {
			return;
		}
		else {
			
		    for( Node node : gp.getChildren() ) {
		        if( GridPane.getRowIndex(node) == y && GridPane.getColumnIndex(node) == x) {
		        	Button b = (Button)node;
		        	if( !( b.getText().equals( "" ) ) && !first ) return;
		        	if( mines[y][x] > -1 && mines[y][x] < 4 && 
		        		numCellsUndone < maxCellsUndone && 
		        		Math.abs( sx-x ) < 3 && Math.abs( sy-y ) < 3 ) {
		        		b.setText( mines[y][x]+"" );
		        		numCellsUndone++;
		        	}
		        	else {
		        		return;
		        	}
		        }
		    }

			int[][] cells = { {y-1, x-1}, {y-1, x}, {y-1, x+1},
							  {y, x-1}  ,           {y, x+1},
							  {y+1, x-1}, {y+1, x}, {y+1, x+1} };
			for( int[] c : cells ) {
				uncoverCellsAround( c[0], c[1], sx, sy, false );
			}
		}
	}
}
