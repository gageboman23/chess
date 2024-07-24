package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard board;
    private TeamColor teamTurn;


    public ChessGame() {
        this.board = new ChessBoard();
        this.teamTurn = TeamColor.WHITE;
        board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        if (board.getPiece(startPosition) == null) {
            return null;
        } else {
            ChessPiece piece = board.getPiece(startPosition);
            Collection<ChessMove> allMoves = piece.pieceMoves(board, startPosition);
            Collection<ChessMove> validMoves = new ArrayList<>(allMoves);

            ChessGame testGame = new ChessGame();
            ChessBoard boardCopy = board.deepCopy();
            testGame.setBoard(boardCopy);

            for (ChessMove move : allMoves) {
                ChessPosition oldPiecePos = move.getEndPosition();
                ChessPiece oldPiece = testGame.board.getPiece(oldPiecePos);
                testGame.testMove(move);
                ChessGame.TeamColor pieceColor = piece.getTeamColor();
                if (testGame.isInCheck(pieceColor)) {
                    validMoves.remove(move);
                }
                testGame.undoMove(move, oldPiece);
            }
            return validMoves;
        }
    }


    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if (board.getPiece(move.getStartPosition()) == null) {
            throw new InvalidMoveException("Invalid Move");
        }
        ChessPosition startPosition = move.getStartPosition();
        ChessGame.TeamColor pieceColor = board.getPiece(startPosition).getTeamColor();
        if (pieceColor != teamTurn) {
            throw new InvalidMoveException("Invalid Move");
        }

        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        ChessPiece.PieceType promoPiece = move.getPromotionPiece();

        if (validMoves != null && validMoves.contains(move)) {
            ChessPosition startPos = move.getStartPosition();
            ChessPosition endPos = move.getEndPosition();
            ChessPiece newPiece = board.getPiece(startPos);
            if (promoPiece != null) {
                ChessPiece promotedPiece = new ChessPiece(newPiece.getTeamColor(), promoPiece);
                newPiece = promotedPiece;
            }
            board.addPiece(endPos, newPiece);
            board.addPiece(startPos, null);

            if (teamTurn == TeamColor.WHITE) {
                setTeamTurn(TeamColor.BLACK);
            } else {
                setTeamTurn(TeamColor.WHITE);
            }
        } else {
            throw new InvalidMoveException("Invalid Move");
        }
    }

    public void testMove(ChessMove move) {
        ChessPosition startPos = move.getStartPosition();
        ChessPosition endPos = move.getEndPosition();
        ChessPiece newPiece = board.getPiece(startPos);
        board.addPiece(endPos, newPiece);
        board.addPiece(startPos, null);
    }

    public void undoMove(ChessMove move, ChessPiece PrevPiece) {
        ChessPosition startPos = move.getStartPosition();
        ChessPosition endPos = move.getEndPosition();
        ChessPiece newPiece = board.getPiece(endPos);
        board.addPiece(startPos, newPiece);
        board.addPiece(endPos, PrevPiece);
    }


    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPos = findPosition(board, teamColor, ChessPiece.PieceType.KING);
        Collection<ChessMove> enemyMoves = new ArrayList<>();
        boolean checkOrNah = false;

        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition pos = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(pos);
                if (piece == null) {
                } else if (piece.getTeamColor() == teamColor) {
                } else {
                    enemyMoves = piece.pieceMoves(getBoard(), pos);
                    for (ChessMove move : enemyMoves) {
                        ChessPosition endPos = move.getEndPosition();
                        if (endPos.equals(kingPos)) {
                            checkOrNah = true;
                            break;
                        }
                    }
                }
            }
        }
        return checkOrNah;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return Objects.deepEquals(getBoard(), chessGame.getBoard()) && getTeamTurn() == chessGame.getTeamTurn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBoard(), getTeamTurn());
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        Collection<ChessMove> teamMoves = new ArrayList<>();
        // Check if the king is in check
        if (isInCheck(teamColor)) {
            for (int row = 1; row < 9; row++) {
                for (int col = 1; col < 9; col++) {
                    ChessPosition pos = new ChessPosition(row, col);
                    ChessPiece piece = board.getPiece(pos);
                    if (piece == null) {
                    } else if (piece.getTeamColor() != teamColor) {
                    } else {
                        teamMoves.addAll(piece.pieceMoves(board, pos));
                    }
                }
            }

            for (ChessMove move : teamMoves) {
                ChessPiece prevPiece = board.getPiece(move.getEndPosition());
                testMove(move);
                if (!isInCheck(teamColor)) {
                    undoMove(move, prevPiece);
                    return false;
                }
                undoMove(move, prevPiece);
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        Collection<ChessMove> teamMoves = new ArrayList<>();
        if (!isInCheck(teamColor)) {
            for (int row = 1; row < 9; row++) {
                for (int col = 1; col < 9; col++) {
                    ChessPosition pos = new ChessPosition(row, col);
                    ChessPiece piece = board.getPiece(pos);
                    if (piece == null) {
                    } else if (piece.getTeamColor() != teamColor) {
                    } else {
                        teamMoves.addAll(validMoves(pos));
                    }
                }
            }

        } else {
            return false;
        }
        return teamMoves.isEmpty();
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }

    private ChessPosition findPosition(ChessBoard board, ChessGame.TeamColor teamColor, ChessPiece.PieceType type) {
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPiece piece = board.getPiece(new ChessPosition(row, col));
                if (piece != null && piece.getPieceType() == type && piece.getTeamColor() == teamColor) {
                    return new ChessPosition(row, col);
                }
            }
        }
        return null; // Piece not found
    }
}
