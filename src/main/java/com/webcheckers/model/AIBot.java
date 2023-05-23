package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.webcheckers.model.Piece.PieceColor;

public class AIBot extends Player{

    private Level level;
    List<Move> singleMoves;
    List<Move> jumpMoves;
    List<Move> validSingleJumps;
    private MoveList moveList;

    /**
     * Create an AI player
     *
     * @param botName name of bot
     * @param level difficulty level of bot
     */
    public AIBot(String botName, Level level) {
        super(botName);
        this.level = level;
        this.singleMoves = new ArrayList<>();
        this.jumpMoves = new ArrayList<>();
        this.validSingleJumps = new ArrayList<>();
    }

    /**
     * Execute AI move
     *
     * @param game game object to execute move on
     * @param activeColor color of the player with current turn
     */
    public void makeMove(CheckersGame game, PieceColor activeColor) {
        Random r = new Random();
        initializeSingleJumpMoves(game, activeColor);
        initializeJumpMoves(game, activeColor);
        initializeSingleMoves(game, activeColor);
        if(validSingleJumps.size() > 0) {
            game.validateMove(validSingleJumps.get(r.nextInt(validSingleJumps.size())));
            validSingleJumps.clear();
        }
        else {
            game.validateMove(singleMoves.get(r.nextInt(singleMoves.size())));
            singleMoves.clear();
        }
        jumpMoves.clear();
        game.makeMove();
    }


    /**
     * Create a list of single step moves that AI can make
     *
     * @param game game object to execute move on
     * @param activeColor color of the player with current turn
     */
    private void initializeSingleMoves(CheckersGame game, PieceColor activeColor) {
        MoveList moveList = new MoveList(activeColor);
        List<Position> posList = new ArrayList<>();
        Board board = game.getGameBoard(PieceColor.RED);
        posList = board.getAllPieceLocations(activeColor);

        for (Position moveStart : posList) {
            List<Position> possibleMovePos = moveList.getSingleMoveList(moveStart);
            for (Position moveEnd: possibleMovePos) {
                Move aiMove = new Move(moveStart, moveEnd);
                if(moveList.validateMove(game, aiMove) == moveList.VALID)
                    singleMoves.add(aiMove);
            }
        }
    }

    /**
     * Create a list of single jump moves that AI can make
     *
     * @param game game object to execute move on
     * @param activeColor color of the player with current turn
     */
    private void initializeSingleJumpMoves(CheckersGame game, PieceColor activeColor) {
        MoveList moveList = new MoveList(activeColor);
        List<Position> posList = new ArrayList<>();
        Board board = game.getGameBoard(PieceColor.RED);
        posList = board.getAllPieceLocations(activeColor);

        for (Position moveStart : posList) {
            //int cell = moveStart.getCell() - 7
            Piece piece = board.getSpace(moveStart).getPiece();
            List<Position> possibleMovePos = moveList.getJumpMoveList(piece, moveStart);
            for (Position moveEnd: possibleMovePos) {
                Move aiMove = new Move(moveStart, moveEnd);
                if(moveList.validatePosition(aiMove.getStart()) && moveList.validatePosition(aiMove.getEnd())) {
                    if (moveList.validateCanJump(board, aiMove))
                        if (moveList.validateMove(game, aiMove) == moveList.VALID)
                            jumpMoves.add(aiMove);
                }
            }
        }
        this.moveList = moveList;
    }

    /**
     * Add valid jump moves to jump move list for AI
     *
     * @param game game object to execute move on
     * @param color color of the player with current turn
     */
    private void initializeJumpMoves(CheckersGame game, PieceColor color) {
        for(Move move: jumpMoves) {
            if(moveList.jumpFinished(game)) {
                validSingleJumps.add(move);
            }
        }
    }
    /**
     * Difficulty levels of AI bot
     */
    public enum Level {
        HARD, EASY
    }




}
