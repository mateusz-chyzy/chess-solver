package chess.pieces.factory;

import chess.moves.LineMoveGenerator;
import chess.moves.base.AbstractMoveGenerator;
import chess.moves.base.MoveDelta;
import chess.pieces.base.IPieceFactory;
import chess.pieces.base.Piece;
import edu.uj.po.interfaces.ChessPiece;
import edu.uj.po.interfaces.Color;
import edu.uj.po.interfaces.Position;

import java.util.List;

public class QueenFactory implements IPieceFactory {
    @Override
    public Piece getPiece(ChessPiece chessPiece, Color color, Position position) {
        List<AbstractMoveGenerator> moveGenerators = List.of(
                new LineMoveGenerator(MoveDelta.North),
                new LineMoveGenerator(MoveDelta.East),
                new LineMoveGenerator(MoveDelta.West),
                new LineMoveGenerator(MoveDelta.South),
                new LineMoveGenerator(MoveDelta.NorthEast),
                new LineMoveGenerator(MoveDelta.NorthWest),
                new LineMoveGenerator(MoveDelta.SouthEast),
                new LineMoveGenerator(MoveDelta.SouthWest)
        );

        return new Piece(chessPiece, color, position, moveGenerators);
    }
}
