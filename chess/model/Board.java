package chess.model;

import chess.exception.IllegalMoveException;
import chess.pieces.base.Piece;
import edu.uj.po.interfaces.Color;
import edu.uj.po.interfaces.File;
import edu.uj.po.interfaces.Position;
import edu.uj.po.interfaces.Rank;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    private final Piece[][] board;

    private Board(Piece[][] board) {
        this.board = board;
    }

    public Optional<Piece> getPieceAt(Position position) {
        Piece piece = board[position.file().ordinal()][position.rank().ordinal()];
        if(piece != null){
            return Optional.of(piece);
        }
        return Optional.empty();
    }

    public List<Piece> getPieces(Color playerColor) {
        List<Piece> playerPieces = new ArrayList<>();

        for (Piece[] row : board) {
            for (Piece piece : row) {
                if (piece != null && piece.getColor() == playerColor) {
                    playerPieces.add(piece);
                }
            }
        }

        return playerPieces;
    }

    public List<Piece> getPiecesExceptKing(Color playerColor) {
        List<Piece> piecesExceptKing = new ArrayList<>();
        List<Piece> pieces = getPieces(playerColor);

        for (Piece chessPiece : pieces) {
            if (!chessPiece.isKing()) {
                piecesExceptKing.add(chessPiece);
            }
        }

        return piecesExceptKing;
    }


    public Piece getKing(Color playerColor) {
        List<Piece> playerPieces = getPieces(playerColor);

        for (Piece piece : playerPieces) {
            if (piece.isKing()) {
                return piece;
            }
        }

        throw new IllegalMoveException(String.format("%s king is not on the board!", playerColor));
    }
    
    public static class BoardStateBuilder {
        public Piece[][] board;

        public BoardStateBuilder() {
            this.board = new Piece[File.values().length][Rank.values().length];
        }

        public BoardStateBuilder(Board existing) {
            board = new Piece[existing.board.length][];

            for (int i = 0; i < existing.board.length; i++) {
                board[i] = existing.board[i].clone();
            }
        }


        public BoardStateBuilder add(Piece chessPiece) {
            Position position = chessPiece.getPosition();
            board[position.file().ordinal()][position.rank().ordinal()] = chessPiece;
            return this;
        }

        public BoardStateBuilder update(Position position, Piece chessPiece) {
            board[position.file().ordinal()][position.rank().ordinal()] = chessPiece;
            return this;
        }

        public Board build() {
            return new Board(board);
        }
    }
}
