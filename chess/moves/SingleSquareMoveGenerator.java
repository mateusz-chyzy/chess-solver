package chess.moves;

import chess.model.Board;
import chess.moves.base.AbstractMoveGenerator;
import chess.moves.base.MoveDelta;
import chess.moves.strategies.SimpleMoveStrategy;
import chess.moves.strategies.base.AbstractMoveStrategy;
import chess.pieces.base.Piece;
import edu.uj.po.interfaces.Move;
import edu.uj.po.interfaces.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SingleSquareMoveGenerator extends AbstractMoveGenerator {
    private final MoveDelta moveDelta;

    public SingleSquareMoveGenerator(MoveDelta moveDelta) {
        this.moveDelta = moveDelta;
    }

    @Override
    public List<AbstractMoveStrategy> getPossibleMoves(Piece movingPiece, Board board) {
        List<AbstractMoveStrategy> possibleMoves = new ArrayList<>();
        Optional<Position> nextPosition = getNext(movingPiece.getPosition(), moveDelta);

        if (nextPosition.isPresent()) {
            Position pos = nextPosition.get();
            Move move = new Move(movingPiece.getPosition(), pos);
            AbstractMoveStrategy moveStrategy = new SimpleMoveStrategy(board, move);
            possibleMoves.add(moveStrategy);
        }

        return possibleMoves;
    }


}
