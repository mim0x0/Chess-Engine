package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

public class Rook extends Piece{

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-1, -8, 1, 8};

    Rook(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculatedLegalMoves(final Board board) {
        
        final List<Move> legalMoves = new ArrayList<>();

        for(final int candidateCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES){

            int candidateDestinationCoordinate = this.piecePosition;

            while(BoardUtils.isValidCoordinate(candidateDestinationCoordinate)){

                if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                   isEightColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)){
                    break;
                   }

                candidateDestinationCoordinate += candidateCoordinateOffset;

                if(BoardUtils.isValidCoordinate(candidateDestinationCoordinate)){
                    
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if(!candidateDestinationTile.isTileOccupied()){
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    } else{
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                        if(this.pieceAlliance != pieceAlliance){
                            legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }

        }

        return ImmutableList.copyOf(legalMoves);

        // throw new UnsupportedOperationException("Unimplemented method 'calculatedLegalMoves'");
    } 

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);
    }

    private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == 1);
    }

}
