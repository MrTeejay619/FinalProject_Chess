package sample;

public class ChessBoardMoveVisitor implements PieceVisitor {

    @Override
    public void visit(Piece piece, Game game, Player player){
        piece.legalMoves.clear();
        piece.getLegalMoves(game);
        if (piece.legalMoves.contains(game.board[game.getOpponent(piece.color).myKing.currRank][game.getOpponent(piece.color).myKing.currFile])){
            game.getOpponent(piece.color).inCheck = true;
        }
    }

    public void visit(Game game, Player player){
        if (player.color.equals("White")){
            game.attackedByWhite.clear();
        } else {
            game.attackedByBlack.clear();
        }
        game.getOpponent(player.color).inCheck = false;

    }

}
