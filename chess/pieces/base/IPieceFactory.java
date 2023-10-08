package chess.pieces.base;

import edu.uj.po.interfaces.ChessPiece;
import edu.uj.po.interfaces.Color;
import edu.uj.po.interfaces.Position;

public interface IPieceFactory {
    Piece getPiece(ChessPiece chessPiece, Color color, Position position);
}
