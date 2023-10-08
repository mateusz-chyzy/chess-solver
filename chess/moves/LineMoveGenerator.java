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

public class LineMoveGenerator extends AbstractMoveGenerator {
    private final MoveDelta moveDelta;

    public LineMoveGenerator(MoveDelta moveDelta) {
        this.moveDelta = moveDelta;
    }

    @Override
    public List<AbstractMoveStrategy> getPossibleMoves(Piece movingPiece, Board board) {
        List<AbstractMoveStrategy> possibleMoves = new ArrayList<>();

        for (Optional<Position> pos = getNext(movingPiece.getPosition(), moveDelta); pos.isPresent(); pos = getNext(pos.get(), moveDelta)) {
            possibleMoves.add(new SimpleMoveStrategy(board, new Move(movingPiece.getPosition(), pos.get())));

            if (board.getPieceAt(pos.get()).isPresent()) {
                break;
            }
        }

        return possibleMoves;
    }
}
