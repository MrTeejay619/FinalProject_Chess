package sample;

public class Square {
    public String attackedBy;
    public boolean isVacant;
    public String color;
    public Piece pieceOnMe;

    Square(){
        pieceOnMe = null;
        isVacant = true;
        attackedBy = "";
    }
}

