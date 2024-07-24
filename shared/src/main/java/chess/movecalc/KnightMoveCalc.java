package chess.movecalc;

import chess.*;

import java.util.Collection;

public class KnightMoveCalc implements PieceMoveCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition position) {
        int[][] directions = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {-1, 2}, {1, -2}, {-1, -2}};
        return KnightKingBase.knightKingBase(board, position, directions);
    }
}
