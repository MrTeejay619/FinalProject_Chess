package sample;

public class Square {
    public boolean isAttacked;
    public boolean isVacant;
    public String color;
    public Piece pieceOnMe;

    Square(){
        pieceOnMe = null;
        isVacant = true;
        isAttacked = false;
    }
}
