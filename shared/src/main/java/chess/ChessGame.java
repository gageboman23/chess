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
        this.teamTurn = team;
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
        }
        Collection<ChessMove> AllMoves = board.getPiece(startPosition).pieceMoves(board, startPosition);
        Collection<ChessMove> validMoves = new ArrayList<>(AllMoves);

        for (ChessMove move : AllMoves) {
            ChessBoard TempBoard = board.deepCopy();
            TempBoard.addPiece(new ChessPosition(move.getEndPosition().getRow(), move.getEndPosition().getColumn()),TempBoard.getPiece(move.getStartPosition()));
            TempBoard.addPiece(new ChessPosition(move.getStartPosition().getRow(), move.getStartPosition().getColumn()), new ChessPiece(null, null));

            if (isInCheckAfterMove(TempBoard, board.getPiece(startPosition).getTeamColor())){
                validMoves.remove(move);
            }

        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        boolean isValid = false;
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());

        for (ChessMove Vmove : validMoves){
            if (move.equals(Vmove)){
                isValid = true;
                break;
            }
        }
        if (!isValid){
            throw new InvalidMoveException("Sorry Suckaaaaa");
        }
        board.addPiece(new ChessPosition(move.getEndPosition().getRow(), move.getEndPosition().getColumn()),board.getPiece(move.getStartPosition()));
        board.addPiece(new ChessPosition(move.getStartPosition().getRow(), move.getStartPosition().getColumn()), new ChessPiece(null, null));

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition KingPos = findPosition(board, teamColor, ChessPiece.PieceType.KING);
        Collection<ChessMove> enemyMoves = findEnemyMoves(board, teamColor);
        Collection<ChessPosition> endpoints = new ArrayList<>();
        boolean CheckOrNah = false;

        for (ChessMove move : enemyMoves){
            endpoints.add(move.getEndPosition());
        }

        for (ChessPosition point : endpoints){
            if (point.equals(KingPos)){
                CheckOrNah = true;
            }
        }


        return CheckOrNah;
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
        // Check if the king is in check
        if (!isInCheck(teamColor)) {
            return false; // The king is not in check, hence not in checkmate
        }

        // Get the position of the king
        ChessPosition kingPosition = findPosition(board, teamColor, ChessPiece.PieceType.KING);

        // Get all possible moves of the king
        Collection<ChessMove> kingMoves = board.getPiece(kingPosition).pieceMoves(board, kingPosition);

        // Check if the king can move to a safe position
        for (ChessMove move : kingMoves) {
            ChessPosition newKingPosition = move.getEndPosition();
            ChessBoard newBoard = board.deepCopy(); // Hypothetical move
            try {
                newBoard = makeMove(new ChessMove(kingPosition, newKingPosition, null));
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }
            if (!isInCheckAfterMove(newBoard, teamColor, kingPosition, newKingPosition)) {
                return false; // The king can move to a safe position
            }
        }

        // Get all possible enemy moves to check if the king can be captured or blocked
        Collection<ChessMove> enemyMoves = findEnemyMoves(board, teamColor);
        for (ChessMove enemyMove : enemyMoves) {
            ChessPosition enemyMoveEnd = enemyMove.getEndPosition();
            if (enemyMoveEnd.equals(kingPosition)) {
                // Check if any piece of the current team can capture the enemy piece or block the attack
                Collection<ChessMove> teamMoves = findTeamMoves(board, teamColor);
                for (ChessMove teamMove : teamMoves) {
                    ChessPosition teamMoveEnd = teamMove.getEndPosition();
                    if (teamMoveEnd.equals(enemyMove.getStartPosition()) || canBlock(teamMove, kingPosition, enemyMove)) {
                        ChessBoard newBoard = board.makeMove(teamMove.getStartPosition(), teamMoveEnd); // Hypothetical move
                        if (!isInCheckAfterMove(newBoard, teamColor, teamMove.getStartPosition(), teamMoveEnd)) {
                            return false; // A piece can capture the enemy piece or block the check
                        }
                    }
                }
            }
        }

        return true; // The king cannot escape check
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
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
        return board;
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

    private Collection<ChessMove> findEnemyMoves(ChessBoard board, ChessGame.TeamColor teamColor) {
        Collection<ChessMove> enemyMoves = new ArrayList<>();
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition currentPosition = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(currentPosition);
                if (piece != null && piece.getTeamColor() != teamColor) {
                    enemyMoves.addAll(piece.pieceMoves(board, currentPosition));
                }
            }
        }
        return enemyMoves;
    }

    private boolean isInCheckAfterMove(ChessBoard board, TeamColor teamColor) {
        return isInCheck(teamColor);
    }

    private Collection<ChessMove> findTeamMoves(ChessBoard board, TeamColor teamColor) {
        Collection<ChessMove> teamMoves = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board.getPiece(row, col);
                if (piece != null && piece.getColor() == teamColor) {
                    ChessPosition position = new ChessPosition(row, col);
                    teamMoves.addAll(piece.pieceMoves(board, position));
                }
            }
        }
        return teamMoves;
    }

    private boolean canBlock(ChessMove teamMove, ChessPosition kingPosition, ChessMove enemyMove) {
        // Logic to determine if a team move can block the enemy move
        ChessPosition teamMoveEnd = teamMove.getEndPosition();
        // Implement specific logic for checking block conditions
        return false; // Placeholder, need specific block logic
    }






}
