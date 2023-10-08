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

public class KingFactory implements IPieceFactory {
    @Override
    public Piece getPiece(ChessPiece chessPiece, Color color, Position position) {
        List<AbstractMoveGenerator> moveGenerators = List.of(
                new SingleSquareMoveGenerator(MoveDelta.North),
                new SingleSquareMoveGenerator(MoveDelta.East),
                new SingleSquareMoveGenerator(MoveDelta.West),
                new SingleSquareMoveGenerator(MoveDelta.South),
                new SingleSquareMoveGenerator(MoveDelta.NorthEast),
                new SingleSquareMoveGenerator(MoveDelta.NorthWest),
                new SingleSquareMoveGenerator(MoveDelta.SouthEast),
                new SingleSquareMoveGenerator(MoveDelta.SouthWest)
        );

        return new Piece(chessPiece, color, position, moveGenerators);
    }
}
