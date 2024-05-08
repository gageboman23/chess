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

        int startRow = position.getRow() -1;
        int startCol = position.getColumn() -1;

        //bishops color
        ChessPiece MyPiece = board.getPiece(new ChessPosition(startRow +1, startCol +1));
        ChessGame.TeamColor MyPieceColor = MyPiece.getTeamColor();


        for (int dRow : directions) {
            for (int dCol : directions) {
                int row = startRow;
                int col = startCol;

                while (true) {
                    row += dRow;
                    col += dCol;

                    // Check if the new position is within the bounds of the board
                    if (row < 0 || row > 7 || col < 0 || col > 7) {
                        break;
                    }

                    ChessPiece pieceAtPosition = board.getPiece(new ChessPosition(row, col));

                    if (pieceAtPosition != null &&  pieceAtPosition != MyPiece) {
                        // If it's an enemy piece, add the move (capture)
                        if (pieceAtPosition.getTeamColor() != MyPieceColor) {
                            moves.add(new ChessMove(position, new ChessPosition(row + 1, col +1), null));
                        }
                        // Stop adding moves in this direction
                        break;
                    }
                    moves.add(new ChessMove(position, new ChessPosition(row + 1, col + 1), null));
                }
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
                break;
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
