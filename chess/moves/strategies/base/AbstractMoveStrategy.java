package chess.moves.strategies.base;

import chess.exception.IllegalMoveException;
import chess.model.Board;
import chess.pieces.base.Piece;
import chess.pieces.base.PieceFactoryMethod;
import edu.uj.po.interfaces.Move;

import java.util.Optional;

public abstract class AbstractMoveStrategy {
    protected final Board board;
    protected final Move move;

    protected AbstractMoveStrategy(Board board, Move move) {
        this.board = board;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    public Optional<Piece> getTargetPiece() {
        return this.board.getPieceAt(move.finalPosition());
    }

    public Piece getMovingPiece() {
        return board.getPieceAt(move.initialPosition())
                .orElseThrow(() -> new IllegalMoveException(String.format("No piece at the position: %s!", move.initialPosition())));
    }

    public abstract Board makeMove(PieceFactoryMethod IPieceFactoryMethod);
}
