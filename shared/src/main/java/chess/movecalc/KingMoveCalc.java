package chess.movecalc;

import chess.*;

import java.util.Collection;

public class KingMoveCalc implements PieceMoveCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition position) {
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}};
        return KnightKingBase.knightKingBase(board, position, directions);
    }
}