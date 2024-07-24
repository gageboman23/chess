package chess.moveCalc;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMoveCalc implements PieceMoveCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition position) {
        Collection<ChessMove> moves = new ArrayList<>();
        int[][] directions = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {-1, 2}, {1, -2}, {-1, -2}};

        int startRow = position.getRow();
        int startCol = position.getColumn();

        //bishops color
        ChessPiece MyPiece = board.getPiece(new ChessPosition(startRow, startCol));
        ChessGame.TeamColor MyPieceColor = MyPiece.getTeamColor();


        for (int[] direction : directions) {
            int row = startRow;
            int col = startCol;

            row += direction[0];
            col += direction[1];

            // Check if the new position is within the bounds of the board
            if (row < 1 || row > 8 || col < 1 || col > 8) {
                continue;
            }

            ChessPiece pieceAtPosition = board.getPiece(new ChessPosition(row, col));

            if (pieceAtPosition != null && pieceAtPosition != MyPiece) {
                // If it's an enemy piece, add the move (capture)
                if (pieceAtPosition.getTeamColor() != MyPieceColor) {
                    moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                }
                // Stop adding moves in this direction
                continue;
            }
            moves.add(new ChessMove(position, new ChessPosition(row, col), null));
        }
        return moves;
    }
}
