package sample;

public interface PieceVisitor {
    void visit(Piece piece, Game game, Player player);
    void visit(Game game, Player player);
}
