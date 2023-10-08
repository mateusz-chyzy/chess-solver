package chess.moves;

import chess.model.Board;
import chess.moves.base.AbstractMoveGenerator;
import chess.moves.base.MoveDelta;
import chess.moves.strategies.EnPassantMoveStrategy;
import chess.moves.strategies.base.AbstractMoveStrategy;
import chess.pieces.base.Piece;
import edu.uj.po.interfaces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PawnEnPassantCaptureMoveGenerator extends AbstractMoveGenerator {
    private final MoveDelta moveDelta;

    public PawnEnPassantCaptureMoveGenerator(MoveDelta moveDelta) {
        this.moveDelta = moveDelta;
    }

    @Override
    public List<AbstractMoveStrategy> getPossibleMoves(Piece movingPiece, Board board) {
        List<AbstractMoveStrategy> possibleMoves = new ArrayList<>();
        Optional<Position> nextPosition = getNext(movingPiece.getPosition(), moveDelta);

        if (nextPosition.isPresent()) {
            Position pos = nextPosition.get();
            Move move = new Move(movingPiece.getPosition(), pos);

            Optional<Piece> enPassantPawn = getEnPassantPawn(movingPiece, board);

            if (enPassantPawn.isPresent()) {
                Piece pawn = enPassantPawn.get();
                AbstractMoveStrategy moveStrategy = new EnPassantMoveStrategy(board, move, pawn);
                possibleMoves.add(moveStrategy);
            }
        }

        return possibleMoves;
    }

    private Optional<Piece> getEnPassantPawn(Piece movingPiece, Board board) {
        Position pawnPos = movingPiece.getPosition();

        Position thirdPosition = new Position(deltaFile(pawnPos, moveDelta), pawnPos.rank());

        Position secondPosition = new Position(deltaFile(pawnPos, moveDelta), deltaRank(pawnPos, moveDelta));

        Position firstPosition = new Position(deltaFile(pawnPos, moveDelta), deltaRank(pawnPos, moveDelta.getDeltaRank() * 2));


        Optional<Piece> enPassantPawn = board.getPieceAt(thirdPosition)
                .filter(piece -> piece.getChessPiece() == ChessPiece.PAWN)
                .filter(piece -> piece.getColor() != movingPiece.getColor());


        if (enPassantPawn.isPresent()
                && board.getPieceAt(secondPosition).isEmpty()
                && board.getPieceAt(firstPosition).isEmpty()) {
            return enPassantPawn;
        }

        return Optional.empty();
    }

    private File deltaFile(Position initial, MoveDelta moveDelta) {
        return File.values()[initial.file().ordinal() + moveDelta.getDeltaFile()];
    }

    private Rank deltaRank(Position initial, MoveDelta moveDelta) {
        return Rank.values()[initial.rank().ordinal() + moveDelta.getDeltaRank()];
    }

    private Rank deltaRank(Position initial, int delta) {
        return Rank.values()[initial.rank().ordinal() + delta];
    }
}
