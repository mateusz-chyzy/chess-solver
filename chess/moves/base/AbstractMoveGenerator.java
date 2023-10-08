package chess.moves.base;

import chess.model.Board;
import chess.moves.strategies.base.AbstractMoveStrategy;
import chess.pieces.base.Piece;
import edu.uj.po.interfaces.File;
import edu.uj.po.interfaces.Position;
import edu.uj.po.interfaces.Rank;

import java.util.List;
import java.util.Optional;

public abstract class AbstractMoveGenerator {
    public abstract List<AbstractMoveStrategy> getPossibleMoves(Piece movingPiece, Board board);
    //
    protected Optional<Position> getNext(Position initial, MoveDelta moveDelta) {
        int nextFile = initial.file().ordinal() + moveDelta.getDeltaFile();
        int nextRank = initial.rank().ordinal() + moveDelta.getDeltaRank();

        if (nextFile >= 0 && nextFile < File.values().length
                && nextRank >= 0 && nextRank < Rank.values().length) {
            return Optional.of(new Position(File.values()[nextFile], Rank.values()[nextRank]));
        }

        return Optional.empty();
    }
}
