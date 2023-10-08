package chess.engine;

import chess.model.Board;
import chess.moves.strategies.base.AbstractMoveStrategy;
import chess.pieces.base.*;
import chess.pieces.factory.*;
import edu.uj.po.interfaces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static edu.uj.po.interfaces.ChessPiece.*;
public class ChessEngineFacade {
    private final PieceFactoryMethod pieceFactoryMethod;
    private Board.BoardStateBuilder boardStateBuilder;
    private Board board;

    public ChessEngineFacade() {
        Map<ChessPiece, IPieceFactory> pieceFactories = Map.of(
                PAWN, new PawnFactory(),
                KNIGHT, new KnightFactory(),
                BISHOP, new BishopFactory(),
                ROOK, new RookFactory(),
                QUEEN, new QueenFactory(),
                KING, new KingFactory());

        this.boardStateBuilder = new Board.BoardStateBuilder();
        this.pieceFactoryMethod = new PieceFactoryMethod(pieceFactories);
    }

    public Optional<Move> findMateInOneMove(Color color) {
        this.board = boardStateBuilder.build();

        if (this.isCheckmate(board, color)) {
            return Optional.empty();
        }

        return this.bruteForceMove(board, board.getPieces(color), (updatedBoard) -> this.isCheckmate(updatedBoard, color));
    }

    public Optional<Move> findStalemateInOneMove(Color color) {
        this.board = boardStateBuilder.build();

        if (this.isStalemate(board, color)) {
            return Optional.empty();
        }

        if (this.isCheckmate(board, color)) {
            return Optional.empty();
        }

        return this.bruteForceMove(board, board.getPieces(color), (updatedBoard) -> this.isStalemate(updatedBoard, this.getOpponentColor(color)));
    }

    public void reset() {
        this.boardStateBuilder = new Board.BoardStateBuilder();
    }

    public void addChessPiece(Position position, Color color, ChessPiece piece) {
        Piece chessPiece = pieceFactoryMethod.getPiece(piece, color, position);
        boardStateBuilder.add(chessPiece);
    }

    public String getBoardPrettyPrint() {
        return boardStateBuilder.build().toString();
    }

    private Optional<Move> bruteForceMove(Board board, List<Piece> pieces, Predicate<Board> condition) {
        for (Piece piece: pieces) {
            for (AbstractMoveStrategy moveStrategy: getLegalMoves(piece, board)) {
                Board updatedBoard = moveStrategy.makeMove(pieceFactoryMethod);
                if (condition.test(updatedBoard)) {
                    return Optional.of(moveStrategy.getMove());
                }
            }
        }

        return Optional.empty();
    }

    private boolean isCheckmate(Board board, Color playerColor) {
        Color opponentColor = getOpponentColor(playerColor);

        return isKingInCheck(board, opponentColor)
                && getLegalMoves(board.getKing(opponentColor), board).size() == 0
                && !this.canBlockCheck(board, opponentColor);
    }

    private boolean isStalemate(Board board, Color playerColor) {
        if (isKingInCheck(board, playerColor)) {
            return false;
        }

        List<Piece> playerPieces = board.getPieces(playerColor);
        int totalLegalMoves = 0;

        for (Piece piece : playerPieces) {
            List<AbstractMoveStrategy> legalMoves = getLegalMoves(piece, board);
            totalLegalMoves += legalMoves.size();
        }

        return totalLegalMoves == 0;
    }


    private boolean isKingInCheck(Board board, Color playerColor) {
        List<Piece> opponentPieces = board.getPieces(getOpponentColor(playerColor));

        for (Piece piece : opponentPieces) {
            List<AbstractMoveStrategy> possibleMoves = piece.getPossibleMoves(board);

            for (AbstractMoveStrategy move : possibleMoves) {
                Optional<Piece> targetPieceOptional = move.getTargetPiece();

                if (targetPieceOptional.isPresent()) {
                    Piece targetPiece = targetPieceOptional.get();

                    if (targetPiece.getColor() == playerColor && targetPiece.isKing()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean canBlockCheck(Board board, Color opponentColor) {
        return bruteForceMove(board, board.getPiecesExceptKing(opponentColor), (updatedBoard) -> !isKingInCheck(updatedBoard, opponentColor)).isPresent();
    }

    private List<AbstractMoveStrategy> getLegalMoves(Piece piece, Board board) {
        List<AbstractMoveStrategy> legalMoves = new ArrayList<>();
        List<AbstractMoveStrategy> possibleMoves = piece.getPossibleMoves(board);

        for (AbstractMoveStrategy move : possibleMoves) {
            if (isLegalMove(move)) {
                legalMoves.add(move);
            }
        }

        return legalMoves;
    }

    private boolean isLegalMove(AbstractMoveStrategy moveStrategy) {
        Piece piece = moveStrategy.getMovingPiece();

        if (moveStrategy.getTargetPiece().isPresent() && moveStrategy.getTargetPiece().get().getColor() == piece.getColor()) {
            return false;
        }

        if (moveStrategy.getTargetPiece().isPresent() && moveStrategy.getTargetPiece().get().isKing()) {
            return false;
        }


        return !isKingInCheck(moveStrategy.makeMove(pieceFactoryMethod), piece.getColor());
    }

    private Color getOpponentColor(Color playerColor) {
        return playerColor == Color.WHITE ? Color.BLACK : Color.WHITE;
    }
}
