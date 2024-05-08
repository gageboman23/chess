package chess;

import java.util.ArrayList;
import java.util.Collection;
/** I am going to create an interface for calculating piece moves
 *
 */

interface PieceMoveCalc {
    Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition position);
    }

class BishopMoveCalc implements PieceMoveCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition position) {
        Collection<ChessMove> moves = new ArrayList<>();
        int[] directions = {-1, 1};

        int startRow = position.getRow();
        int startCol = position.getColumn();

        //bishops color
        ChessPiece MyPiece = board.getPiece(new ChessPosition(startRow, startCol));
        ChessGame.TeamColor MyPieceColor = MyPiece.getTeamColor();


        for (int dRow : directions) {
            for (int dCol : directions) {
                int row = startRow;
                int col = startCol;

                while (true) {
                    row += dRow;
                    col += dCol;

                    // Check if the new position is within the bounds of the board
                    if (row < 1 || row > 8 || col < 1 || col > 8) {
                        break;
                    }

                    ChessPiece pieceAtPosition = board.getPiece(new ChessPosition(row, col));

                    if (pieceAtPosition != null &&  pieceAtPosition != MyPiece) {
                        // If it's an enemy piece, add the move (capture)
                        if (pieceAtPosition.getTeamColor() != MyPieceColor) {
                            moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                        }
                        // Stop adding moves in this direction
                        break;
                    }
                    moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                }
            }
        }
        return moves;
    }
}

class RookMoveCalc implements PieceMoveCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition position) {
        Collection<ChessMove> moves = new ArrayList<>();
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        int startRow = position.getRow();
        int startCol = position.getColumn();

        //rooks color
        ChessPiece MyPiece = board.getPiece(new ChessPosition(startRow, startCol));
        ChessGame.TeamColor MyPieceColor = MyPiece.getTeamColor();

        for (int[] direction : directions) {
            int row = startRow;
            int col = startCol;

            while (true) {
                row += direction[0];
                col += direction[1];

                // Check if the new position is within the bounds of the board
                if (row < 1 || row > 8 || col < 1 || col > 8) {
                    break;
                }

                ChessPiece pieceAtPosition = board.getPiece(new ChessPosition(row, col));

                if (pieceAtPosition != null && pieceAtPosition != MyPiece) {
                    // If it's an enemy piece, add the move (capture)
                    if (pieceAtPosition.getTeamColor() != MyPieceColor) {
                        moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                    }
                    // Stop adding moves in this direction
                    break;
                }
                moves.add(new ChessMove(position, new ChessPosition(row, col), null));
            }
        }
        return moves;
    }
}

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor pieceColor;
    private PieceType type;
    private PieceMoveCalc moveCalculator;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
        this.moveCalculator = createMoveCalculator(type);
    }

    private PieceMoveCalc createMoveCalculator(PieceType type) {
        switch (type) {
            case BISHOP:
                return new BishopMoveCalc();
            case KING:
                break;
            case QUEEN:
                break;
            case KNIGHT:
                break;
            case ROOK:
                return new RookMoveCalc();
            case PAWN:
                break;
        }
        return null;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */

    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return moveCalculator.calculateMoves(board, myPosition);
    }
}
