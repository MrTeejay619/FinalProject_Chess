package sample;

public class Square{
    public boolean isVacant;
    public String color;
    public Piece pieceOnMe;
    int rank;
    int file;

    Square(int row, int col){
        pieceOnMe = null;
        isVacant = true;
        rank = row;
        file = col;
    }
}

