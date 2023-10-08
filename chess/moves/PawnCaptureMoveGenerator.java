package chess.moves;

import chess.model.Board;
import chess.moves.base.AbstractMoveGenerator;
import chess.moves.base.MoveDelta;
import chess.moves.strategies.PawnPromotionMoveStrategy;
import chess.moves.strategies.SimpleMoveStrategy;
import chess.moves.strategies.base.AbstractMoveStrategy;
import chess.pieces.base.Piece;
import edu.uj.po.interfaces.ChessPiece;
import edu.uj.po.interfaces.Move;
import edu.uj.po.interfaces.Position;
import edu.uj.po.interfaces.Rank;

import java.util.*;

public class PawnCaptureMoveGenerator extends AbstractMoveGenerator {
    private final MoveDelta moveDelta;
    private final Rank promotionRank;
    private static final ChessPiece[] promotionPieces = new ChessPiece[] { ChessPiece.QUEEN, ChessPiece.KNIGHT};

    public PawnCaptureMoveGenerator(MoveDelta moveDelta, Rank promotionRank) {
        this.moveDelta = moveDelta;
        this.promotionRank = promotionRank;
    }

    @Override
    public List<AbstractMoveStrategy> getPossibleMoves(Piece movingPiece, Board board) {
        List<AbstractMoveStrategy> possibleMoves = new ArrayList<>();
        Optional<Position> nextPosition = getNext(movingPiece.getPosition(), moveDelta);

        if (nextPosition.isPresent()) {
            Position pos = nextPosition.get();

            Optional<Piece> attackingPiece = board.getPieceAt(pos);

            if (attackingPiece.isPresent() && attackingPiece.get().getColor() != movingPiece.getColor()) {
                Move move = new Move(movingPiece.getPosition(), pos);
                if (move.initialPosition().rank() == promotionRank) {
                    for (ChessPiece promotion : promotionPieces) {
                        AbstractMoveStrategy moveStrategy = new PawnPromotionMoveStrategy(board, move, promotion);
                        possibleMoves.add(moveStrategy);
                    }
                } else {
                    AbstractMoveStrategy moveStrategy = new SimpleMoveStrategy(board, move);
                    possibleMoves.add(moveStrategy);
                }
            }
        }

        return possibleMoves;
    }

}
