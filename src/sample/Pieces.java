package sample;

public interface Pieces {
    void accept(PieceVisitor pieceVisitor, Game game, Player player);
}
