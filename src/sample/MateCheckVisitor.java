package sample;

public class MateCheckVisitor implements PieceVisitor {

    @Override
    public void visit(Piece piece, Game game, Player player){
        if (player.inCheck){
            piece.getLegalMoves(game);
            if (piece.legalMoves.size() > 0){
                for (Square square: piece.legalMoves){
                    if (game.checkTempMove(player, piece, square.rank, square.file)){
                        player.inMate = false;
                        return;
                    }
                }
            }
        }

    }
    public void visit(Game game, Player player){
        if (player.inCheck == true){
            player.inMate = true;
        }
    }
}
