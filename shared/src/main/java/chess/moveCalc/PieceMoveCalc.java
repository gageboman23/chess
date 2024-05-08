package chess.moveCalc;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

/**
 * I am going to create an interface for calculating piece moves
 */

public interface PieceMoveCalc {
    Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition position);
}
