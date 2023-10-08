package chess.moves.strategies;

import chess.model.Board;
import chess.moves.strategies.base.AbstractMoveStrategy;
import chess.pieces.base.Piece;
import chess.pieces.base.PieceFactoryMethod;
import edu.uj.po.interfaces.Move;

public class SimpleMoveStrategy extends AbstractMoveStrategy {
    public SimpleMoveStrategy(Board board, Move move) {
        super(board, move);
    }

    @Override
    public Board makeMove(PieceFactoryMethod IPieceFactoryMethod) {
        Piece piece = getMovingPiece();

        return new Board.BoardStateBuilder(this.board)
                .update(this.move.initialPosition(), null)
                .update(this.move.finalPosition(), IPieceFactoryMethod.getPiece(piece.getChessPiece(), piece.getColor(), this.move.finalPosition()))
                .build();
    }
}
