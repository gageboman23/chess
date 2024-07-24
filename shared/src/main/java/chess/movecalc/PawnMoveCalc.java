package chess.movecalc;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoveCalc implements PieceMoveCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition position) {
        int startRow = position.getRow();
        int startCol = position.getColumn();


        //pawn color
        ChessPiece myPiece = board.getPiece(new ChessPosition(startRow, startCol));
        ChessGame.TeamColor myPieceColor = myPiece.getTeamColor();

        if (myPieceColor == ChessGame.TeamColor.WHITE) {
            Collection<ChessMove> whiteMoves = new ArrayList<>();
            ChessPiece leftDiag = board.getPiece(new ChessPosition(startRow + 1, startCol - 1));
            ChessPiece rightDiag = board.getPiece(new ChessPosition(startRow + 1, startCol + 1));

            if (startRow == 2) {
                whitePawn(position, startRow, startCol, myPieceColor, whiteMoves, leftDiag, rightDiag);
                if (board.getPiece(new ChessPosition(startRow + 1, startCol)) == null) {
                    whiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol), null));
                    if (board.getPiece(new ChessPosition(startRow + 2, startCol)) == null) {
                        whiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 2, startCol), null));
                    }
                }

            } else if (startRow == 7) {
                if (leftDiag != null && leftDiag.getTeamColor() != myPieceColor) {
                    whiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol - 1), ChessPiece.PieceType.ROOK));
                    whiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol - 1), ChessPiece.PieceType.KNIGHT));
                    whiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol - 1), ChessPiece.PieceType.BISHOP));
                    whiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol - 1), ChessPiece.PieceType.QUEEN));
                }
                if (rightDiag != null && rightDiag.getTeamColor() != myPieceColor) {
                    whiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol + 1), ChessPiece.PieceType.ROOK));
                    whiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol + 1), ChessPiece.PieceType.KNIGHT));
                    whiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol + 1), ChessPiece.PieceType.BISHOP));
                    whiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol + 1), ChessPiece.PieceType.QUEEN));
                }
                if (board.getPiece(new ChessPosition(startRow + 1, startCol)) == null) {
                    whiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol), ChessPiece.PieceType.ROOK));
                    whiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol), ChessPiece.PieceType.KNIGHT));
                    whiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol), ChessPiece.PieceType.BISHOP));
                    whiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol), ChessPiece.PieceType.QUEEN));
                }

            } else {
                whitePawn(position, startRow, startCol, myPieceColor, whiteMoves, leftDiag, rightDiag);
                if (board.getPiece(new ChessPosition(startRow + 1, startCol)) == null) {
                    whiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol), null));
                }
            }
            return whiteMoves;
        }
        Collection<ChessMove> blackMoves = new ArrayList<>();
        ChessPiece leftDiag = board.getPiece(new ChessPosition(startRow - 1, startCol - 1));
        ChessPiece rightDiag = board.getPiece(new ChessPosition(startRow - 1, startCol + 1));

        if (startRow == 7) {
            blackPawn(position, startRow, startCol, myPieceColor, blackMoves, leftDiag, rightDiag);
            if (board.getPiece(new ChessPosition(startRow - 1, startCol)) == null) {
                blackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol), null));
                if (board.getPiece(new ChessPosition(startRow - 2, startCol)) == null) {
                    blackMoves.add(new ChessMove(position, new ChessPosition(startRow - 2, startCol), null));
                }
            }

        } else if (startRow == 2) {
            if (leftDiag != null && leftDiag.getTeamColor() != myPieceColor) {
                blackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol - 1), ChessPiece.PieceType.ROOK));
                blackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol - 1), ChessPiece.PieceType.KNIGHT));
                blackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol - 1), ChessPiece.PieceType.BISHOP));
                blackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol - 1), ChessPiece.PieceType.QUEEN));
            }
            if (rightDiag != null && rightDiag.getTeamColor() != myPieceColor) {
                blackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol + 1), ChessPiece.PieceType.ROOK));
                blackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol + 1), ChessPiece.PieceType.KNIGHT));
                blackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol + 1), ChessPiece.PieceType.BISHOP));
                blackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol + 1), ChessPiece.PieceType.QUEEN));
            }
            if (board.getPiece(new ChessPosition(startRow - 1, startCol)) == null) {
                blackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol), ChessPiece.PieceType.ROOK));
                blackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol), ChessPiece.PieceType.KNIGHT));
                blackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol), ChessPiece.PieceType.BISHOP));
                blackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol), ChessPiece.PieceType.QUEEN));
            }

        } else {
            blackPawn(position, startRow, startCol, myPieceColor, blackMoves, leftDiag, rightDiag);
            if (board.getPiece(new ChessPosition(startRow - 1, startCol)) == null) {
                blackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol), null));
            }
        }
        return blackMoves;
    }

    private void whitePawn(ChessPosition pos, int startRow, int startCol, ChessGame.TeamColor myPieceColor,
                           Collection<ChessMove> whiteMoves, ChessPiece leftDiag, ChessPiece rightDiag) {
        if (leftDiag != null && leftDiag.getTeamColor() != myPieceColor) {
            whiteMoves.add(new ChessMove(pos, new ChessPosition(startRow + 1, startCol - 1), null));
        }
        if (rightDiag != null && rightDiag.getTeamColor() != myPieceColor) {
            whiteMoves.add(new ChessMove(pos, new ChessPosition(startRow + 1, startCol + 1), null));
        }
    }

    private void blackPawn(ChessPosition pos, int startRow, int startCol, ChessGame.TeamColor myPieceColor,
                           Collection<ChessMove> blackMoves, ChessPiece leftDiag, ChessPiece rightDiag) {
        if (leftDiag != null && leftDiag.getTeamColor() != myPieceColor) {
            blackMoves.add(new ChessMove(pos, new ChessPosition(startRow - 1, startCol - 1), null));
        }
        if (rightDiag != null && rightDiag.getTeamColor() != myPieceColor) {
            blackMoves.add(new ChessMove(pos, new ChessPosition(startRow - 1, startCol + 1), null));
        }
    }
}
