package chess.moves.strategies;

import chess.model.Board;
import chess.moves.strategies.base.AbstractMoveStrategy;
import chess.pieces.base.Piece;
import chess.pieces.base.PieceFactoryMethod;
import edu.uj.po.interfaces.Move;


public class EnPassantMoveStrategy extends AbstractMoveStrategy {
    private final Piece attackingPiece;
    public EnPassantMoveStrategy(Board board, Move move, Piece attackingPiece) {
        super(board, move);
        this.attackingPiece = attackingPiece;
    }

    @Override
    public Board makeMove(PieceFactoryMethod IPieceFactoryMethod) {
        Piece piece = getMovingPiece();

        return new Board.BoardStateBuilder(this.board)
                .update(this.move.initialPosition(), null)
                .update(attackingPiece.getPosition(), null)
                .update(this.move.finalPosition(), IPieceFactoryMethod.getPiece(piece.getChessPiece(), piece.getColor(), this.move.finalPosition()))
                .build();
    }
}
