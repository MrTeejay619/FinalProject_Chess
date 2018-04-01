package sample;


import javafx.event.*;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Control;
//import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.image.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import javax.swing.*;


public class BoardController {
    public int numClicks;

    public int startingRow;
    public int startingCol;


    @FXML
    private GridPane gridPane;


    public void initialize() {
        numClicks = 1;
        Player me = new Player(Main.colour);
        Player[] p = new Player[2];
        p[0] = (me);



        if (me.color.equals("White")){
            Player opponent = new Player("Black");
            p[1] = (opponent);
        } else {
            Player opponent = new Player("White");
            p[1] = (opponent);
        }



        Game game = new Game(p);
        game.accept(new ChessBoardMoveVisitor(), game, p[0]);

        for (Player player : p){
            if (player.color.equals("White")){
                player.myTurn = true;
            } else {
                player.myTurn = false;
            }
        }

        renderBoard(game);

        for(Node n: gridPane.getChildren()){
            n.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (numClicks == 1 ){
                        startingRow = gridPane.getRowIndex(n) ;
                        startingCol = gridPane.getColumnIndex(n);
                        System.out.println(startingRow);
                        System.out.println(startingCol);
                        System.out.println("Clicks"+ numClicks);
                        numClicks ++;
                    } else if (numClicks == 2){
                        System.out.println(gridPane.getRowIndex(n));
                        System.out.println(gridPane.getColumnIndex(n));
                        System.out.println("Clicks"+ numClicks);
                        game.accept(new ChessBoardMoveVisitor(), game, p[0]);
                        switch(game.movePiece(p[0] ,game.board[startingRow ][startingCol].pieceOnMe ,gridPane.getRowIndex(n) , gridPane.getColumnIndex(n))){
                            case 0: System.out.println("illegalMove");
                                break;
                            case 1: // TODO move is made , update the server
                                renderBoard(game);
                                break;
                            case 2: System.out.println("illegal move ");
                                break;

                            case 3: System.out.println("not your colour");
                                break;

                            case 4: System.out.println("not your turn");
                                break;

                            case 5: System.out.println("no piece");
                                break;

                            case 6: System.out.println("This shouldn't happen whoopsie");
                                break;
                        }
                        numClicks = 1;
                    } else {
                        numClicks = 1;
                    }

                }
            });


        }


    }

    public void addPane(GridPane gp,int colIndex, int rowIndex) {
        Pane pane = new Pane();
        pane.setOnMouseClicked(e -> {
            System.out.printf("Mouse enetered cell [%d, %d]%n", colIndex, rowIndex);
        });
        gp.add(pane, colIndex, rowIndex);
    }


    public void renderBoard(Game game){
        final int size = 8;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                StackPane square = new StackPane();

                String color;
                if (game.board[row][col].pieceOnMe != null) {
                    ImageView iV = new ImageView(game.board[row][col].pieceOnMe.image);
                    square.getChildren().add(iV);
                }
                if ((row + col) % 2 == 0) {
                    color = "Gray";
                } else {
                    color = "White";
                }
                square.setStyle("-fx-background-color: " + color + ";");
                gridPane.add(square, col, row);
            }
        }
        for (int i = 0; i < size; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            gridPane.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }

    }
    public void sendMove(){
        //TODO Taabish
    }

    public void recieveMove(){
        //TODO Taabish
    }

}




/*public void renderBoard(Game game) {
        final int size = 8;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (game.board[row][col].pieceOnMe != null) {
                    ImageView iV = new ImageView(game.board[row][col].pieceOnMe.image)
                    getSquare(gridPane, row, col).getChildren().add(iV);
                }
            }
        }
    }*/

   /* public Node getSquare(GridPane gP, int row, int col){
        for(Node n: gP.getChildren()){
            if (gP.getRowIndex(n) == row && gP.getColumnIndex(n) == col){
                return n;
            }
        }
    }*/
