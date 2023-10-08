package chess.pieces.factory;

import chess.moves.PawnCaptureMoveGenerator;
import chess.moves.PawnDoubleForwardMoveGenerator;
import chess.moves.PawnEnPassantCaptureMoveGenerator;
import chess.moves.PawnForwardMoveGenerator;
import chess.moves.base.AbstractMoveGenerator;
import chess.moves.base.MoveDelta;
import chess.pieces.base.IPieceFactory;
import chess.pieces.base.Piece;
import edu.uj.po.interfaces.ChessPiece;
import edu.uj.po.interfaces.Color;
import edu.uj.po.interfaces.Position;
import edu.uj.po.interfaces.Rank;

import java.util.ArrayList;
import java.util.List;

public class PawnFactory implements IPieceFactory {
    @Override
    public Piece getPiece(ChessPiece chessPiece, Color color, Position position) {
        List<AbstractMoveGenerator> moveGenerators;

        if (color == Color.WHITE) {
            moveGenerators = getPawnMoveGenerators(position.rank(), Rank.SECOND, Rank.SEVENTH, MoveDelta.North, MoveDelta.FirstPawnNorth, MoveDelta.NorthWest, MoveDelta.NorthEast);
        } else {
            moveGenerators = getPawnMoveGenerators(position.rank(), Rank.SEVENTH, Rank.SECOND, MoveDelta.South, MoveDelta.FirstPawnSouth, MoveDelta.SouthWest, MoveDelta.SouthEast);
        }

        return new Piece(chessPiece, color, position, moveGenerators);
    }

    public List<AbstractMoveGenerator> getPawnMoveGenerators(Rank rank,
                                                             Rank startRank,
                                                             Rank promotionRank,
                                                             MoveDelta forwardMove,
                                                             MoveDelta firstPawnMove,
                                                             MoveDelta westMove,
                                                             MoveDelta eastMove) {
        List<AbstractMoveGenerator> possibleMoves = new ArrayList<>();

        possibleMoves.add(new PawnForwardMoveGenerator(forwardMove, promotionRank));

        possibleMoves.add(new PawnCaptureMoveGenerator(westMove, promotionRank));

        possibleMoves.add(new PawnCaptureMoveGenerator(eastMove, promotionRank));

        if (rank == startRank) {
            possibleMoves.add(new PawnDoubleForwardMoveGenerator(firstPawnMove));
        } else if (rank != promotionRank) {
            possibleMoves.add(new PawnEnPassantCaptureMoveGenerator(westMove));
            possibleMoves.add(new PawnEnPassantCaptureMoveGenerator(eastMove));
        }

        return possibleMoves;
    }
}
