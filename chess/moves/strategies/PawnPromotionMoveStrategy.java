package chess.moves.strategies;

import chess.model.Board;
import chess.moves.strategies.base.AbstractMoveStrategy;
import chess.pieces.base.Piece;
import chess.pieces.base.PieceFactoryMethod;
import edu.uj.po.interfaces.ChessPiece;
import edu.uj.po.interfaces.Move;

public class PawnPromotionMoveStrategy extends AbstractMoveStrategy {
    private final ChessPiece promoteTo;

    public PawnPromotionMoveStrategy(Board board, Move move, ChessPiece promoteTo) {
        super(board, move);
        this.promoteTo = promoteTo;
    }

    @Override
    public Board makeMove(PieceFactoryMethod pieceFactory) {
        Piece piece = getMovingPiece();

        return new Board.BoardStateBuilder(this.board)
                .update(this.move.initialPosition(), null)
                .update(this.move.finalPosition(), pieceFactory.getPiece(promoteTo, piece.getColor(), this.move.finalPosition()))
                .build();
    }
}
