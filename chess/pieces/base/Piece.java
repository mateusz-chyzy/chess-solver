package chess.pieces.base;

import chess.model.Board;
import chess.moves.base.AbstractMoveGenerator;
import chess.moves.strategies.base.AbstractMoveStrategy;
import edu.uj.po.interfaces.ChessPiece;
import edu.uj.po.interfaces.Color;
import edu.uj.po.interfaces.Position;

import java.util.ArrayList;
import java.util.List;

public class Piece {
    private final ChessPiece chessPiece;
    private final Color color;
    private final Position position;
    private final List<AbstractMoveGenerator> possibleMoveGenerators;

    public Piece(ChessPiece chessPiece, Color color, Position position, List<AbstractMoveGenerator> possibleMoveGenerators) {
        this.chessPiece = chessPiece;
        this.color = color;
        this.position = position;
        this.possibleMoveGenerators = possibleMoveGenerators;
    }

    public boolean isKing() {
        return chessPiece == ChessPiece.KING;
    }

    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    public Color getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }


    public List<AbstractMoveStrategy> getPossibleMoves(Board board) {
        List<AbstractMoveStrategy> moves = new ArrayList<>();

        for (AbstractMoveGenerator moveGenerator: possibleMoveGenerators) {
            moves.addAll(moveGenerator.getPossibleMoves(this, board));
        }

        return moves;
    }

    @Override
    public String toString() {
        switch (chessPiece) {
            case PAWN:
                return getColor() == Color.BLACK ? "\u2659" : "\u265F";

            case KNIGHT:
                return getColor() == Color.BLACK ? "\u2658" : "\u265E";

            case BISHOP:
                return getColor() == Color.BLACK ? "\u2657" : "\u265D";

            case ROOK:
                return getColor() == Color.BLACK ? "\u2656" : "\u265C";

            case QUEEN:
                return getColor() == Color.BLACK ? "\u2655" : "\u265B";

            case KING:
                return getColor() == Color.BLACK ? "\u2654" : "\u265A";

            default: throw new IllegalArgumentException("Invalid chess piece: " + chessPiece);
        }
    }
}
