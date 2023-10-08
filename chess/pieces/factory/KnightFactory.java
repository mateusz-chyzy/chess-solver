package chess.pieces.factory;

import chess.moves.SingleSquareMoveGenerator;
import chess.moves.base.AbstractMoveGenerator;
import chess.moves.base.MoveDelta;
import chess.pieces.base.IPieceFactory;
import chess.pieces.base.Piece;
import edu.uj.po.interfaces.ChessPiece;
import edu.uj.po.interfaces.Color;
import edu.uj.po.interfaces.Position;

import java.util.List;


public class KnightFactory implements IPieceFactory {
    @Override
    public Piece getPiece(ChessPiece chessPiece, Color color, Position position) {
        List<AbstractMoveGenerator> moveGenerators = List.of(
                new SingleSquareMoveGenerator(MoveDelta.KnightNorthEast1),
                new SingleSquareMoveGenerator(MoveDelta.KnightNorthEast2),
                new SingleSquareMoveGenerator(MoveDelta.KnightNorthWest1),
                new SingleSquareMoveGenerator(MoveDelta.KnightNorthWest2),
                new SingleSquareMoveGenerator(MoveDelta.KnightSouthEast1),
                new SingleSquareMoveGenerator(MoveDelta.KnightSouthEast2),
                new SingleSquareMoveGenerator(MoveDelta.KnightSouthWest1),
                new SingleSquareMoveGenerator(MoveDelta.KnightSouthWest2)
        );

        return new Piece(chessPiece, color, position, moveGenerators);
    }
}
