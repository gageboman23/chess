package chess.movecalc;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class KnightKingBase {

    public static Collection<ChessMove> knightKingBase (ChessBoard board, ChessPosition position, int[][] directions) {
        Collection<ChessMove> moves = new ArrayList<>();
        int startRow = position.getRow();
        int startCol = position.getColumn();

        ChessPiece myPiece = board.getPiece(new ChessPosition(startRow, startCol));
        ChessGame.TeamColor myPieceColor = myPiece.getTeamColor();

        for (int[] direction : directions) {
            int row = startRow + direction[0];
            int col = startCol + direction[1];

            // Check if the new position is within the bounds of the board
            if (row < 1 || row > 8 || col < 1 || col > 8) {
                continue;
            }

            ChessPiece pieceAtPosition = board.getPiece(new ChessPosition(row, col));

            if (pieceAtPosition != null) {
                // If it's an enemy piece, add the move (capture)
                if (pieceAtPosition.getTeamColor() != myPieceColor) {
                    moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                }
                continue;
            }
            moves.add(new ChessMove(position, new ChessPosition(row, col), null));
        }
        return moves;
    }
}