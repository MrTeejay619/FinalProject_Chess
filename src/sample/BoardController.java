package sample;


import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.concurrent.Task;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.*;


public class BoardController {
    public int numClicks;

    public int startingRow;
    public int startingCol;

    public Game game;

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



        game = new Game(p);
        game.accept(new ChessBoardMoveVisitor(), game, game.players[0]);
        game.accept(new ChessBoardMoveVisitor(), game, game.players[1]);

        for (Player player : game.players){
            if (player.color.equals("White")){
                player.myTurn = true;

            } else {
                player.myTurn = false;
            }

        }


        renderBoard(game);
        if (game.players[0].color.equals("White")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "YOU ARE WHITE", ButtonType.CLOSE);
            //TODO implement go back to lobby on close
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "YOU ARE BLACK", ButtonType.CLOSE);
            //TODO implement go back to lobby on close
            alert.showAndWait();
        }

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
                        game.accept(new ChessBoardMoveVisitor(), game, game.players[0]);
                        game.accept(new ChessBoardMoveVisitor(), game, game.players[1]);
                        switch(game.movePiece(game.players[0] ,game.board[startingRow ][startingCol].pieceOnMe ,gridPane.getRowIndex(n) , gridPane.getColumnIndex(n))){
                            case 0: System.out.println("illegalMove");
                                break;
                            case 1: // TODO move is made , update the server
                                renderBoard(game);

                                try {
                                    sendMove(startingRow, startingCol, gridPane.getRowIndex(n), gridPane.getColumnIndex(n));
                                } catch (IOException e){
                                    e.printStackTrace();
                                }
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
        if (game.players[0].color.equals("Black")){
            try {
                renderBoard(game);
                //receiveMove();
            } catch (Exception e){
                e.printStackTrace();
                System.err.println("receive failed");
            }
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
                square.setMinSize(50,50);

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
        gridPane.resize(300,300);
        for (int i = 0; i < size; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            gridPane.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
    }


    public void sendMove(int move1, int move2, int move3, int move4) throws IOException{
        //TODO Taabish
        Socket socket = new Socket(Main.address, Main.port);

        PrintWriter out = new PrintWriter(socket.getOutputStream());

        out.println("Send Move");
        out.println(Main.opponent);
        out.println(move1);
        out.println(move2);
        out.println(move3);
        out.println(move4);
        out.flush();
        socket.close();

        game.accept(new MateCheckVisitor(), game, game.players[1]);
        if (game.players[1].inMate){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "MATE YOU WIN", ButtonType.CLOSE);
            //TODO implement go back to lobby on close
            alert.showAndWait();
        }
        System.out.println(game.players[0].myKing.currRank);
        System.out.println(game.players[0].myKing.currFile);

    }

    public void receiveMove() throws IOException{
        String selection = null;
        String temp[] = {"", "", "", ""};

        try {
            //while(true) {
            Socket socket2 = new Socket(Main.address, Main.port);
            PrintWriter out = new PrintWriter(socket2.getOutputStream());
            out.println("Get Move");
            out.println(Main.currentUsername);
            out.flush();
            socket2.shutdownOutput();
            ObjectInputStream in = new ObjectInputStream(socket2.getInputStream());

            selection = in.readUTF();

            System.out.println(selection);

            if (selection.equals("Found")) {
                temp[0] = in.readUTF();
                temp[1] = in.readUTF();
                temp[2] = in.readUTF();
                temp[3] = in.readUTF();
            } else if (selection.equals("No Response")) {
                socket2.shutdownInput();
                socket2.close();
            }
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }

        game.movePiece(game.players[1], game.board[Integer.valueOf(temp[0])][Integer.valueOf(temp[1])].pieceOnMe,
                Integer.valueOf(temp[2]), Integer.valueOf(temp[3]));

        game.accept(new MateCheckVisitor(), game, game.players[0]);
        if (game.players[0].inMate){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "MATE YOU LOOSE", ButtonType.CLOSE);
            //TODO implement go back to lobby on close
            alert.showAndWait();
        }
        System.out.println(game.players[1].myKing.currRank);
        System.out.println(game.players[1].myKing.currFile);
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
                        game.accept(new ChessBoardMoveVisitor(), game, game.players[0]);
                        game.accept(new ChessBoardMoveVisitor(), game, game.players[1]);
                        switch(game.movePiece(game.players[0] ,game.board[startingRow ][startingCol].pieceOnMe ,gridPane.getRowIndex(n) , gridPane.getColumnIndex(n))){
                            case 0: System.out.println("illegalMove");
                                break;
                            case 1:
                                renderBoard(game);

                                try {
                                    sendMove(startingRow, startingCol, gridPane.getRowIndex(n), gridPane.getColumnIndex(n));
                                } catch (IOException e){
                                    e.printStackTrace();
                                }
                                break;
                            case 2: System.out.println("illegal move ");
                                Alert alert0 = new Alert(Alert.AlertType.WARNING, "ILLEGAL MOVE", ButtonType.OK);
                                alert0.showAndWait();
                                break;

                            case 3: System.out.println("not your colour");
                                Alert alert1 = new Alert(Alert.AlertType.WARNING, "NOT YOUR COLOUR", ButtonType.OK);
                                alert1.showAndWait();
                                break;

                            case 4: System.out.println("not your turn");
                                Alert alert2 = new Alert(Alert.AlertType.WARNING, "NOT YOUR TURN", ButtonType.OK);
                                alert2.showAndWait();
                                break;

                            case 5: System.out.println("no piece");
                                Alert alert3 = new Alert(Alert.AlertType.WARNING, "NO PIECE THERE", ButtonType.OK);
                                alert3.showAndWait();
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
}
