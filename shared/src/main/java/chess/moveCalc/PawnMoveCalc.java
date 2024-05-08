package chess.moveCalc;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoveCalc implements PieceMoveCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition position) {
        int startRow = position.getRow();
        int startCol = position.getColumn();


        //pawn color
        ChessPiece MyPiece = board.getPiece(new ChessPosition(startRow, startCol));
        ChessGame.TeamColor MyPieceColor = MyPiece.getTeamColor();

        if (MyPieceColor == ChessGame.TeamColor.WHITE) {
            Collection<ChessMove> WhiteMoves = new ArrayList<>();
            ChessPiece LeftDiag = board.getPiece(new ChessPosition(startRow + 1, startCol - 1));
            ChessPiece RightDiag = board.getPiece(new ChessPosition(startRow + 1, startCol + 1));

            if (startRow == 2) {
                if (LeftDiag != null && LeftDiag.getTeamColor() != MyPieceColor) {
                    WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol - 1), null));
                }
                if (RightDiag != null && RightDiag.getTeamColor() != MyPieceColor) {
                    WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol + 1), null));
                }
                if (board.getPiece(new ChessPosition(startRow + 1, startCol)) == null) {
                    WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol), null));
                    if (board.getPiece(new ChessPosition(startRow + 2, startCol)) == null) {
                        WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 2, startCol), null));
                    }
                }

            } else if (startRow == 7) {
                if (LeftDiag != null && LeftDiag.getTeamColor() != MyPieceColor) {
                    WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol - 1), ChessPiece.PieceType.ROOK));
                    WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol - 1), ChessPiece.PieceType.KNIGHT));
                    WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol - 1), ChessPiece.PieceType.BISHOP));
                    WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol - 1), ChessPiece.PieceType.QUEEN));
                }
                if (RightDiag != null && RightDiag.getTeamColor() != MyPieceColor) {
                    WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol + 1), ChessPiece.PieceType.ROOK));
                    WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol + 1), ChessPiece.PieceType.KNIGHT));
                    WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol + 1), ChessPiece.PieceType.BISHOP));
                    WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol + 1), ChessPiece.PieceType.QUEEN));
                }
                if (board.getPiece(new ChessPosition(startRow + 1, startCol)) == null) {
                    WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol), ChessPiece.PieceType.ROOK));
                    WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol), ChessPiece.PieceType.KNIGHT));
                    WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol), ChessPiece.PieceType.BISHOP));
                    WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol), ChessPiece.PieceType.QUEEN));
                }

            } else {
                if (LeftDiag != null && LeftDiag.getTeamColor() != MyPieceColor) {
                    WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol - 1), null));
                }
                if (RightDiag != null && RightDiag.getTeamColor() != MyPieceColor) {
                    WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol + 1), null));
                }
                if (board.getPiece(new ChessPosition(startRow + 1, startCol)) == null) {
                    WhiteMoves.add(new ChessMove(position, new ChessPosition(startRow + 1, startCol), null));
                }
            }
            return WhiteMoves;
        }
        Collection<ChessMove> BlackMoves = new ArrayList<>();
        ChessPiece LeftDiag = board.getPiece(new ChessPosition(startRow - 1, startCol - 1));
        ChessPiece RightDiag = board.getPiece(new ChessPosition(startRow - 1, startCol + 1));

        if (startRow == 7) {
            if (LeftDiag != null && LeftDiag.getTeamColor() != MyPieceColor) {
                BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol - 1), null));
            }
            if (RightDiag != null && RightDiag.getTeamColor() != MyPieceColor) {
                BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol + 1), null));
            }
            if (board.getPiece(new ChessPosition(startRow - 1, startCol)) == null) {
                BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol), null));
                if (board.getPiece(new ChessPosition(startRow - 2, startCol)) == null) {
                    BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 2, startCol), null));
                }
            }

        } else if (startRow == 2) {
            if (LeftDiag != null && LeftDiag.getTeamColor() != MyPieceColor) {
                BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol - 1), ChessPiece.PieceType.ROOK));
                BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol - 1), ChessPiece.PieceType.KNIGHT));
                BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol - 1), ChessPiece.PieceType.BISHOP));
                BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol - 1), ChessPiece.PieceType.QUEEN));
            }
            if (RightDiag != null && RightDiag.getTeamColor() != MyPieceColor) {
                BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol + 1), ChessPiece.PieceType.ROOK));
                BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol + 1), ChessPiece.PieceType.KNIGHT));
                BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol + 1), ChessPiece.PieceType.BISHOP));
                BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol + 1), ChessPiece.PieceType.QUEEN));
            }
            if (board.getPiece(new ChessPosition(startRow - 1, startCol)) == null) {
                BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol), ChessPiece.PieceType.ROOK));
                BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol), ChessPiece.PieceType.KNIGHT));
                BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol), ChessPiece.PieceType.BISHOP));
                BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol), ChessPiece.PieceType.QUEEN));
            }

        } else {
            if (LeftDiag != null && LeftDiag.getTeamColor() != MyPieceColor) {
                BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol - 1), null));
            }
            if (RightDiag != null && RightDiag.getTeamColor() != MyPieceColor) {
                BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol + 1), null));
            }
            if (board.getPiece(new ChessPosition(startRow - 1, startCol)) == null) {
                BlackMoves.add(new ChessMove(position, new ChessPosition(startRow - 1, startCol), null));
            }
        }
    return BlackMoves;
    }
}
