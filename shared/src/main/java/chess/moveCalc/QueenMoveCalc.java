package chess.moveCalc;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMoveCalc implements PieceMoveCalc {
    private PieceMoveCalc bishopCalc = new BishopMoveCalc();
    private PieceMoveCalc rookCalc = new RookMoveCalc();

    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition position) {
        Collection<ChessMove> moves = new ArrayList<>();
        moves.addAll(bishopCalc.calculateMoves(board, position));
        moves.addAll(rookCalc.calculateMoves(board, position));
        return moves;
    }
}
