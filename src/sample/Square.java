package sample;

public class Square {
    boolean isAttacked;
    boolean isVacant;
    String color;
    public Piece pieceOnMe;

    Square(){
        pieceOnMe = null;
        isVacant = true;
        isAttacked = false;
    }
}
