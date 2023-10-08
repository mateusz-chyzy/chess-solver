package chess.moves;

import chess.model.Board;
import chess.moves.base.AbstractMoveGenerator;
import chess.moves.base.MoveDelta;
import chess.moves.strategies.SimpleMoveStrategy;
import chess.moves.strategies.base.AbstractMoveStrategy;
import chess.pieces.base.Piece;
import edu.uj.po.interfaces.Color;
import edu.uj.po.interfaces.Move;
import edu.uj.po.interfaces.Position;
import edu.uj.po.interfaces.Rank;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PawnDoubleForwardMoveGenerator extends AbstractMoveGenerator {
    private final MoveDelta moveDelta;

    public PawnDoubleForwardMoveGenerator(MoveDelta moveDelta) {
        this.moveDelta = moveDelta;
    }

    @Override
    public List<AbstractMoveStrategy> getPossibleMoves(Piece movingPiece, Board board) {
        List<AbstractMoveStrategy> possibleMoves = new ArrayList<>();
        Optional<Position> nextPosition = getNext(movingPiece.getPosition(), moveDelta);

        Position pos = nextPosition.get();

        if (board.getPieceAt(pos).isEmpty() && isFirstPlaceEmpty(board, movingPiece.getPosition(),movingPiece.getColor())) {
            Move move = new Move(movingPiece.getPosition(), pos);
            AbstractMoveStrategy moveStrategy = new SimpleMoveStrategy(board, move);
            possibleMoves.add(moveStrategy);
        }

        return possibleMoves;
    }

    private boolean isFirstPlaceEmpty(Board board, Position pos, Color color) {
        int deltaRank = Color.BLACK == color ? -1 : 1;

        return board.getPieceAt(new Position(pos.file(), deltaRank(pos, deltaRank))).isEmpty();
    }

    private Rank deltaRank(Position initial, int delta) {
        return Rank.values()[initial.rank().ordinal() + delta];
    }

}
