package chess.pieces.base;

import chess.exception.IllegalMoveException;
import edu.uj.po.interfaces.ChessPiece;
import edu.uj.po.interfaces.Color;
import edu.uj.po.interfaces.Position;

import java.util.Map;

public class PieceFactoryMethod {
    private final Map<ChessPiece, IPieceFactory> pieceFactories;

    public PieceFactoryMethod(Map<ChessPiece, IPieceFactory> pieceFactories) {
        this.pieceFactories = pieceFactories;
    }


    public Piece getPiece(ChessPiece chessPiece, Color color, Position position) {
        IPieceFactory factory = pieceFactories.get(chessPiece);

        if (factory == null) {
            throw new IllegalMoveException(String.format("Chess piece type: %s is not supported!", chessPiece));
        }

        return factory.getPiece(chessPiece, color, position);
    }
}
