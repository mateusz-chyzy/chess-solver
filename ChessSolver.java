import chess.engine.ChessEngineFacade;

import edu.uj.po.interfaces.*;

import java.util.Optional;

public class ChessSolver implements Setup, Solver {
    private final ChessEngineFacade chessEngine;

    public ChessSolver() {
        this.chessEngine = new ChessEngineFacade();
    }

    @Override
    public Optional<Move> findMateInOneMove(Color color) {
        return chessEngine.findMateInOneMove(color);
    }

    @Override
    public Optional<Move> findStalemateInOneMove(Color color) {
        return chessEngine.findStalemateInOneMove(color);
    }

    @Override
    public void reset() {
        chessEngine.reset();
    }

    @Override
    public void addChessPiece(Position position, Color color, ChessPiece piece) {
        chessEngine.addChessPiece(position, color, piece);
    }

    public String getBoardPrettyPrint() {
        return chessEngine.getBoardPrettyPrint();
    }
}
